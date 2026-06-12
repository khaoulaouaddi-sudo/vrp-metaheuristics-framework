/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mtdvrpot_cplex;

import ilog.concert.IloException;
import ilog.concert.IloIntExpr;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloRange;
import ilog.cplex.IloCplex;
import ilog.cplex.IloCplexMultiCriterionExpr;
import static java.lang.Double.max;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Khaoula
 */
public class Ourdynamicmodel {
       int n;// nombre de véhicule
       int m;// nombre de client
       int f;// nombre de dépot fictifs
       int t_m;// nombre maximal de tournées pour chaque véhicules
       
       int over=0;
       double temps_legal;
       double overtime_parmis;
       ArrayList<Integer> K= new ArrayList<Integer>();// intervalle des indices des véhicules
       ArrayList<Integer> CF= new ArrayList<Integer>();// intervalle des indices des clients et dépots(fictifs et central)
       ArrayList<Integer> F= new ArrayList<Integer>();// intervalles des indices des depots fictifs
       ArrayList<Integer> FD= new ArrayList<Integer>();// intervalle des indices des depots fictifs et central
       ArrayList<Integer> CT= new ArrayList<Integer>();// intervalle des indices des clients uniquement
       ArrayList<Integer> CD= new ArrayList<Integer>();// intervalle des indices des clients et dépot central
       ArrayList<Integer> tours= new ArrayList<Integer>();// intervalles des indices des tournées
       ArrayList<Integer> CFF= new ArrayList<Integer>();// intervalle des indices des clients et depot fictifs
        
    ArrayList<Camion> camions ;
    
    VRPS problem;
    VRPD problem_dynamic;
    int iterationNumber;
    public ArrayList<Customer> clients_old = new  ArrayList<Customer>();
    public ArrayList<Tour> tournees = new  ArrayList<Tour>();
    public ArrayList<Depotfictif> DEPO_FIC = new  ArrayList<Depotfictif>();
    private double time_slice;
    private double temps_service;
    public VRP_total VRPT;
    private double temps_total_voyage=0;
    private double temps_total_dernier_voyage;
     private double overtime_maximal;

       
    static IloIntExpr[] arrayFromList(List<IloIntExpr> list) {
        return (IloIntExpr[])list.toArray(new IloIntExpr[list.size()]);
    }
    static Double[] arrayFromListD(List<Double> list) {
        return (Double[])list.toArray(new Double[list.size()]);
    }

    public int getOver() {
        return over;
    }

    public void setOver(int over) {
        this.over = over;
    }

    

    
public  void solveMe_dynamic_distance() {
        this.construct_input_tours_dynamic();
        //model
        try {
            IloCplex cplex = new IloCplex();
            
           //Variables
        IloNumVar[][][] x = new IloNumVar[CF.size()][][];
        for (int i: CF ){
        x[i] = new IloNumVar[CF.size()][];
        for (int j : CF){
            /*System.out.println(" size CF:"+CF.size());
            System.out.println(" size CD:"+CD.size());
            System.out.println(" size tours:"+tours.size());
            System.out.println(" i:"+i);
            System.out.println(" j:"+j);*/
          x[i][j] = cplex.boolVarArray(tours.size()+1);
          
          cplex.add(x[i][j]);
        }     
         }
         IloNumVar[] u=cplex.numVarArray(CD.size()+f,f,Double.MAX_VALUE);
       /*IloNumVar[] u= new IloNumVar[CT.size()+2];
        for (int i=2;i<CT.size()+2;i++ ){
            System.out.println("u de i:"+i);
        cplex.add(u[i]);
        }*/
            // objectives
            IloLinearNumExpr obj1 = cplex.linearNumExpr();
            for (int i:CF)
            {
                for (int j:CF){
                    if (j!=i){
                    for (int r: tours)
                    {
                        obj1.addTerm(this.getProblem_dynamic().getDistances_dynamic(this.getdistance_by_index_dynamic(i), this.getdistance_by_index_dynamic(j)),x[i][j][r]);
                    }
                }
                }
            }
            cplex.addMinimize(obj1);
  //dexpr float OTk[k in K]= maxl(0,(sum(i in CF, j in CD  , r in k..k+(t_m-1)*n) distance[i][j]*x[i, j ,r]-temps_legal)) ; //overtime performé par le véhicule k
  //dexpr float obj2= max(k in K) OTk[k];
        //   IloLinearNumExpr obj2 = cplex.linearNumExpr();
         List<IloIntExpr> ends = new ArrayList<IloIntExpr>();
            for (int k: K)
            {
               IloLinearNumExpr expr = cplex.linearNumExpr();
               for (int i:CF)
               {
                   for (int j :CD)
                   {
                       for (int r=k;r<k+1+(t_m-1)*n;r++)
                       {
                           if (j!=i)
                           {
                         expr.addTerm(this.getProblem_dynamic().getDistances_dynamic(this.getdistance_by_index_dynamic(i), this.getdistance_by_index_dynamic(j)), x[i][j][r]);
                     
                           }
                   }
                   }
                //  expr.setConstant(-temps_legal);
               }
              ends.add((IloIntExpr) cplex.max(0, cplex.sum(get_temps_tournees_avant(k)-temps_legal,expr)));   
            }
            //cplex.addMinimize(cplex.max(arrayFromList(ends)));
            // contraintes
            //Contrainte de respect de la capacité réstante du véhicule/tournée
           // forall(r in tours)
          //sum(i in CF,j in CD:j!=i) Demandes_client[i]*x[i,j,r]<= capacite_tour[r];
            for (int r: tours){
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int i: CF)
                {
                    for (int j: CD){
                    if (i!=j){
                        expr.addTerm(this.getdemande_by_index_dynamic(i), x[i][j][r]);
                    }
                    }
                }
                
                cplex.addLe(expr, this.getTournees().get(r-1).getCurrent_capacity());
                
            }
            // Contrainte de respect du temps maximal (inclus l'overtime maximal permis)  

         //forall (k in K)
         // sum(r in k..k+(t_m-1)*n, i in CF,j in CD)distance[i][j]*x[i, j ,r]-temps_legal<=overtime_legal;
                
       for (int k: K){
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int r=k;r<k+1+(t_m-1)*n;r++)
                {
                    for (int i: CF){
                        for (int j: CD)
                        {
                    if (i!=j){
                        expr.addTerm(this.getProblem_dynamic().getDistances_dynamic(this.getdistance_by_index_dynamic(i), this.getdistance_by_index_dynamic(j)), x[i][j][r]);
                    }
                    }
                    }
                }
                
                cplex.addLe(expr, temps_legal-get_temps_tournees_avant(k));
                
            }
         
  // Contraintes de conservation de flux au niveau des clients et du dépot cenral

//forall (j in CD, r in tours)  
//sum(i in CF: i!=j)x[i, j ,r]==sum(i in CD: i!=j)x[j, i ,r];
        for (int j: CD){
            for(int r : tours)
            {
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int i:CF)
                {
                    
                    if (i!=j){
                        expr.addTerm(1.0, x[i][j][r]);
                                        
                    }
                }
                for (int i:CD)
                {
                    
                    if (i!=j){
                        expr.addTerm(-1.0, x[j][i][r]);
                                        
                    }
                }
                
                cplex.addEq(expr, 0);
            } 
            }
    // Chaque véhicule initialement stationné sur un dépot fictifs doit éffectué une tournée au moins

     //forall (k in K)
     //sum(r in k..k+(t_m-1)*n, i in CF,j in CD: j!=i)x[i, j ,r]>=stationnee_initiallement[k];
  
      for (int k: K){
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int r=k;r<k+1+(t_m-1)*n;r++)
                {
                    for (int i: CF){
                        for (int j: CD)
                        {
                    if (i!=j){
                        expr.addTerm(-1.0, x[i][j][r]);
                    }
                    }
                    }
                }
                
                cplex.addLe(expr, -this.get_stationnee_initialement(k-1));
                
            } 
      // Chaque client doit être visité une seule fois 
 //forall (i in CT)
  // sum (r in tours, j in CD: j!=i)x[i][j][r]==1;
   for (int i :CFF)
   {
        IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int r : tours)
                {
                    for (int j: CD){
                       
                    if (i!=j){
                        expr.addTerm(1.0, x[i][j][r]);
                    }
                    }
                    
                }
                
                cplex.addEq(expr, 1);
                
   }
       
      // Contraintes d'élimination des soutours
//forall(i in CT , j in CT) 
//P[j] >= P[i]+1-card(CD)*(1-sum( r in tours) x[i,j,r]);
 
 //forall(j in CT) 
 //P[j] >= 0;
  
    
    for (int i:CT)
     {
      for(int j:CT)
        {
         if(i!=j){
             IloLinearNumExpr expr=cplex.linearNumExpr();
            expr.addTerm(1.0, u[i]);
            expr.addTerm(-1.0, u[j]);
            for (int r: tours)
                        {
                        expr.addTerm(CD.size(),x[i][j][r]);
                        }
                        cplex.addLe(expr, CD.size()-2);
                    }
                }
            }
             for (int i:CT)
            {
              IloLinearNumExpr expr=cplex.linearNumExpr();
                        expr.addTerm(-1.0, u[i]);
                        cplex.addLe(expr, 0);
            }
             // Un dépot fictif ne peut être destination
              for (int j:F)
                  
            {
                 IloLinearNumExpr expr=cplex.linearNumExpr();
                for (int i:CF)
                {
                    for (int r: tours)
                    {
              
                        expr.addTerm(1, x[i][j][r]);
                        
                    }
                }
                cplex.addEq(expr, 0);
            }
              
              // La tournées 0 est vide 
              { 
               IloLinearNumExpr expr=cplex.linearNumExpr();
               int r=0;
                for (int i:CF)
                {
                    for (int j: CF)
                    {
                     
                        expr.addTerm(1, x[i][j][r]);
                        
                    }
                }
                cplex.addEq(expr, 0);
              }
            //solve node
            if (cplex.solve())
            {
      double objval = cplex.getObjValue();
      double obj3=0;
        for (int i:CF)
            {
                for (int j:CF){
                    if (j!=i){
                    for (int r: tours)
                    {
                        obj3=obj3+this.getProblem_dynamic().getDistances_dynamic(this.getdistance_by_index_dynamic(i), this.getdistance_by_index_dynamic(j))*cplex.getValue(x[i][j][r]);
                    }
                }
                }
            }
      //double [] uval = cplex.getValues(u);
    for (int i:CF)
    {
     for (int j:CD)
     {
         for (int r:tours)
         {
             if (j!=i)
             {
     System.out.println("valeur de x pour i: "+i+" et j : "+j+" et r: " +r+ "est "+cplex.getValue(x[i][j][r]));
             }
         }
        
     }
    } 
            System.out.println("valeur optimal:" +objval);
              System.out.println("distance:" +obj3);
   double obj4=0;
   
     ArrayList<Double> endss = new ArrayList<Double>();
            for (int k: K)
            {
              double expr=0;
               for (int i:CF)
               {
                   for (int j :CD)
                   {
                       for (int r=k;r<k+1+(t_m-1)*n;r++)
                       {
                           if (j!=i)
                           {
                         expr=expr+this.getProblem_dynamic().getDistances_dynamic(this.getdistance_by_index_dynamic(i), this.getdistance_by_index_dynamic(j))*cplex.getValue(x[i][j][r]);
                     
                           }
                   }
                   }
                   System.out.println("expr avant -temps légal:::  "+expr);
                expr=expr+get_temps_tournees_avant(k)-temps_legal;
                     System.out.println("expr après - temps légal:::  "+expr);
               }
              endss.add(max(0,expr));
            }
            obj4=endss.get(0);
           System.out.println("valeur de u:" +obj4);
  for (int r=1;r<this.getTournees().size()+1;r++){
        for (int i:CF)
        {
            for(int j:CT)
            {
                if (cplex.getValue(x[i][j][r])==1)
                        {
                            this.getTournees().get(r-1).getCustomers().add(this.getclient_by_index_dynamic(j));
                            
                        }
            }
        }
  }
  for (int i=0;i<this.getTournees().size();i++)
  {
      Tour t=this.getTournees().get(i);
      if ((t.getCustomers().isEmpty())&&(t.getId_fictif()==0))
      {
        this.getTournees().remove(t);
        i--;
      }
  }
  // Affecter les tournées aux camions
  for (int i=0; i<this.getCamions().size();i++)
  {
      this.getCamions().get(i).getTournee_attribuees().clear();
   for (int j=0; j<this.getTournees().size();j++)  
   {
      for (int r: this.Trour_camion(this.getCamions().get(i)))
      {
          if (r==this.getTournees().get(j).getId_tour())
          {
             this.getCamions().get(i).getTournee_attribuees().add(this.getTournees().get(j));
          }
      }
   }
  }
   cplex.end();
           
            
       
        this.setDEPO_FIC(this.get_depo_fic_dynamic());
       
            }
           
            else {
                this.over=1;
               cplex.end(); 
            }
          
 } catch (IloException e) {
            e.printStackTrace();
        }
            //end
           
    }

public void solveMe_dynamic_overtime(){
          //this.construct_input_tours_dynamic();
        //model
        try {
            IloCplex cplex = new IloCplex();
            
           //Variables
        IloNumVar[][][] x = new IloNumVar[CF.size()][][];
        for (int i: CF ){
        x[i] = new IloNumVar[CF.size()][];
        for (int j : CF){
            /*System.out.println(" size CF:"+CF.size());
            System.out.println(" size CD:"+CD.size());
            System.out.println(" size tours:"+tours.size());
            System.out.println(" i:"+i);
            System.out.println(" j:"+j);*/
          x[i][j] = cplex.boolVarArray(tours.size()+1);
          
          cplex.add(x[i][j]);
        }     
         }
         IloNumVar[] u=cplex.numVarArray(CD.size()+f,f,Double.MAX_VALUE);
       /*IloNumVar[] u= new IloNumVar[CT.size()+2];
        for (int i=2;i<CT.size()+2;i++ ){
            System.out.println("u de i:"+i);
        cplex.add(u[i]);
        }*/
            // objectives
            IloLinearNumExpr obj1 = cplex.linearNumExpr();
            for (int i:CF)
            {
                for (int j:CF){
                    if (j!=i){
                    for (int r: tours)
                    {
                        obj1.addTerm(this.getProblem_dynamic().getDistances_dynamic(this.getdistance_by_index_dynamic(i), this.getdistance_by_index_dynamic(j)),x[i][j][r]);
                    }
                }
                }
            }
            //cplex.addMinimize(obj1);
  //dexpr float OTk[k in K]= maxl(0,(sum(i in CF, j in CD  , r in k..k+(t_m-1)*n) distance[i][j]*x[i, j ,r]-temps_legal)) ; //overtime performé par le véhicule k
  //dexpr float obj2= max(k in K) OTk[k];
        //   IloLinearNumExpr obj2 = cplex.linearNumExpr();
         List<IloIntExpr> ends = new ArrayList<IloIntExpr>();
            for (int k: K)
            {
               IloLinearNumExpr expr = cplex.linearNumExpr();
               for (int i:CF)
               {
                   for (int j :CD)
                   {
                       for (int r=k;r<k+1+(t_m-1)*n;r++)
                       {
                           if (j!=i)
                           {
                         expr.addTerm(this.getProblem_dynamic().getDistances_dynamic(this.getdistance_by_index_dynamic(i), this.getdistance_by_index_dynamic(j)), x[i][j][r]);
                     
                           }
                   }
                   }
                //  expr.setConstant(-temps_legal);
               }
              ends.add((IloIntExpr) cplex.max(0, cplex.sum(get_temps_tournees_avant(k)-temps_legal,expr)));   
              }
            cplex.addMinimize(cplex.max(arrayFromList(ends)));
            // contraintes
            //Contrainte de respect de la capacité réstante du véhicule/tournée
           // forall(r in tours)
          //sum(i in CF,j in CD:j!=i) Demandes_client[i]*x[i,j,r]<= capacite_tour[r];
            for (int r: tours){
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int i: CF)
                {
                    for (int j: CD){
                    if (i!=j){
                        expr.addTerm(this.getdemande_by_index_dynamic(i), x[i][j][r]);
                    }
                    }
                }
                
                cplex.addLe(expr, this.getTournees().get(r-1).getCurrent_capacity());
                
            }
            // Contrainte de respect du temps maximal (inclus l'overtime maximal permis)  

         //forall (k in K)
         // sum(r in k..k+(t_m-1)*n, i in CF,j in CD)distance[i][j]*x[i, j ,r]-temps_legal<=overtime_legal;
                
       for (int k: K){
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int r=k;r<k+1+(t_m-1)*n;r++)
                {
                    for (int i: CF){
                        for (int j: CD)
                        {
                    if (i!=j){
                        expr.addTerm(this.getProblem_dynamic().getDistances_dynamic(this.getdistance_by_index_dynamic(i), this.getdistance_by_index_dynamic(j)), x[i][j][r]);
                    }
                    }
                    }
                }
                
                cplex.addLe(expr, overtime_parmis+temps_legal-get_temps_tournees_avant(k));
                
            }
         
  // Contraintes de conservation de flux au niveau des clients et du dépot cenral

//forall (j in CT, r in tours)  
//sum(i in CF: i!=j)x[i, j ,r]==sum(i in CD: i!=j)x[j, i ,r];
        for (int j: CT){
            for(int r : tours)
            {
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int i:CF)
                {
                    
                    if (i!=j){
                        expr.addTerm(1.0, x[i][j][r]);
                                        
                    }
                }
                for (int i:CD)
                {
                    
                    if (i!=j){
                        expr.addTerm(-1.0, x[j][i][r]);
                                        
                    }
                }
                
                cplex.addEq(expr, 0);
            } 
            }
    // Chaque véhicule initialement stationné sur un dépot fictifs doit éffectué une tournée au moins

     //forall (k in K)
     //sum(r in k..k+(t_m-1)*n, i in CF,j in CD: j!=i)x[i, j ,r]>=stationnee_initiallement[k];
  
      for (int k: K){
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int r=k;r<k+1+(t_m-1)*n;r++)
                {
                    for (int i: CF){
                        for (int j: CD)
                        {
                    if (i!=j){
                        expr.addTerm(-1.0, x[i][j][r]);
                    }
                    }
                    }
                }
                
                cplex.addLe(expr, -this.get_stationnee_initialement(k-1));
                
            } 
      // Chaque client doit être visité une seule fois 
 //forall (i in CT)
  // sum (r in tours, j in CD: j!=i)x[i][j][r]==1;
   for (int i :CFF)
   {
        IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int r : tours)
                {
                    for (int j: CD){
                       
                    if (i!=j){
                        expr.addTerm(1.0, x[i][j][r]);
                    }
                    }
                    
                }
                
                cplex.addEq(expr, 1);
                
   }
       
      // Contraintes d'élimination des soutours
//forall(i in CT , j in CT) 
//P[j] >= P[i]+1-card(CD)*(1-sum( r in tours) x[i,j,r]);
 
 //forall(j in CT) 
 //P[j] >= 0;
  
    
    for (int i:CT)
     {
      for(int j:CT)
        {
         if(i!=j){
             IloLinearNumExpr expr=cplex.linearNumExpr();
            expr.addTerm(1.0, u[i]);
            expr.addTerm(-1.0, u[j]);
            for (int r: tours)
                        {
                        expr.addTerm(CD.size(),x[i][j][r]);
                        }
                        cplex.addLe(expr, CD.size()-2);
                    }
                }
            }
             for (int i:CT)
            {
              IloLinearNumExpr expr=cplex.linearNumExpr();
                        expr.addTerm(-1.0, u[i]);
                        cplex.addLe(expr, 0);
            }
             // Un dépot fictif ne peut être destination
              for (int j:F)
                  
            {
                 IloLinearNumExpr expr=cplex.linearNumExpr();
                for (int i:CF)
                {
                    for (int r: tours)
                    {
              
                        expr.addTerm(1, x[i][j][r]);
                        
                    }
                }
                cplex.addEq(expr, 0);
            }
              
              // La tournées 0 est vide 
              { 
               IloLinearNumExpr expr=cplex.linearNumExpr();
               int r=0;
                for (int i:CF)
                {
                    for (int j: CF)
                    {
                     
                        expr.addTerm(1, x[i][j][r]);
                        
                    }
                }
                cplex.addEq(expr, 0);
              }
            
            cplex.solve();
      double objval = cplex.getObjValue();
      double obj3=0;
        for (int i:CF)
            {
                for (int j:CF){
                    if (j!=i){
                    for (int r: tours)
                    {
                        obj3=obj3+this.getProblem_dynamic().getDistances_dynamic(this.getdistance_by_index_dynamic(i), this.getdistance_by_index_dynamic(j))*cplex.getValue(x[i][j][r]);
                    }
                }
                }
            }
      //double [] uval = cplex.getValues(u);
    for (int i:CF)
    {
     for (int j:CD)
     {
         for (int r:tours)
         {
             if (j!=i)
             {
     System.out.println("valeur de x pour i: "+i+" et j : "+j+" et r: " +r+ "est "+cplex.getValue(x[i][j][r]));
             }
         }
        
     }
    } 
            System.out.println("valeur optimal:" +objval);
              System.out.println("distance:" +obj3);
   double obj4=0;
   
     ArrayList<Double> endss = new ArrayList<Double>();
            for (int k: K)
            {
              double expr=0;
               for (int i:CF)
               {
                   for (int j :CD)
                   {
                       for (int r=k;r<k+1+(t_m-1)*n;r++)
                       {
                           if (j!=i)
                           {
                         expr=expr+this.getProblem_dynamic().getDistances_dynamic(this.getdistance_by_index_dynamic(i), this.getdistance_by_index_dynamic(j))*cplex.getValue(x[i][j][r]);
                     
                           }
                   }
                   }
                expr=expr-temps_legal;
               }
              endss.add(max(0,expr));
            }
            obj4=endss.get(0);
           System.out.println("valeur de u:" +obj4);
  for (int r=1;r<this.getTournees().size()+1;r++){
        for (int i:CF)
        {
            for(int j:CT)
            {
                if (cplex.getValue(x[i][j][r])==1)
                        {
                            this.getTournees().get(r-1).getCustomers().add(this.getclient_by_index_dynamic(j));
                            
                        }
            }
        }
  }
  for (int i=0;i<this.getTournees().size();i++)
  {
      Tour t=this.getTournees().get(i);
      if ((t.getCustomers().isEmpty())&&(t.getId_fictif()==0))
      {
        this.getTournees().remove(t);
        i--;
      }
  }
  // Affecter les tournées aux camions
  for (int i=0; i<this.getCamions().size();i++)
  {
      this.getCamions().get(i).getTournee_attribuees().clear();
   for (int j=0; j<this.getTournees().size();j++)  
   {
      for (int r: this.Trour_camion(this.getCamions().get(i)))
      {
          if (r==this.getTournees().get(j).getId_tour())
          {
             this.getCamions().get(i).getTournee_attribuees().add(this.getTournees().get(j));
          }
      }
   }
  }
   cplex.end();
           
            
       
        this.setDEPO_FIC(this.get_depo_fic_dynamic());
       
            
           
            
          
 } catch (IloException e) {
            e.printStackTrace();
        }
            //end
           
}
public void solveMe_static()
{
        this.over=0;
        solveMe_static_distance();
        if (this.over==1)
        {
            solveMe_static_overtime();
        }
    
    
    
}
public void solveMe_dynamic()
{
    if(this.over==0){
        solveMe_dynamic_distance();
        if (this.over==1)
        {
            solveMe_dynamic_overtime();
        }
    }
    else{
        this.construct_input_tours_dynamic();
        solveMe_dynamic_overtime();
    }
    
}
public  void solveMe_dynamic_distance_definitif() {
        this.construct_input_tours_dynamic();
        //model
        try {
            IloCplex cplex = new IloCplex();
            
           //Variables
        IloNumVar[][][] x = new IloNumVar[CF.size()][][];
        for (int i: CF ){
        x[i] = new IloNumVar[CF.size()][];
        for (int j : CF){
            /*System.out.println(" size CF:"+CF.size());
            System.out.println(" size CD:"+CD.size());
            System.out.println(" size tours:"+tours.size());
            System.out.println(" i:"+i);
            System.out.println(" j:"+j);*/
          x[i][j] = cplex.boolVarArray(tours.size()+1);
          
          cplex.add(x[i][j]);
        }     
         }
         IloNumVar[] u=cplex.numVarArray(CD.size()+f,f,Double.MAX_VALUE);
       /*IloNumVar[] u= new IloNumVar[CT.size()+2];
        for (int i=2;i<CT.size()+2;i++ ){
            System.out.println("u de i:"+i);
        cplex.add(u[i]);
        }*/
            // objectives
            IloLinearNumExpr obj1 = cplex.linearNumExpr();
            for (int i:CF)
            {
                for (int j:CF){
                    if (j!=i){
                    for (int r: tours)
                    {
                        obj1.addTerm(this.getProblem_dynamic().getDistances_dynamic(this.getdistance_by_index_dynamic(i), this.getdistance_by_index_dynamic(j)),x[i][j][r]);
                    }
                }
                }
            }
            cplex.addMinimize(obj1);
  //dexpr float OTk[k in K]= maxl(0,(sum(i in CF, j in CD  , r in k..k+(t_m-1)*n) distance[i][j]*x[i, j ,r]-temps_legal)) ; //overtime performé par le véhicule k
  //dexpr float obj2= max(k in K) OTk[k];
        //   IloLinearNumExpr obj2 = cplex.linearNumExpr();
         List<IloIntExpr> ends = new ArrayList<IloIntExpr>();
            for (int k: K)
            {
               IloLinearNumExpr expr = cplex.linearNumExpr();
               for (int i:CF)
               {
                   for (int j :CD)
                   {
                       for (int r=k;r<k+1+(t_m-1)*n;r++)
                       {
                           if (j!=i)
                           {
                         expr.addTerm(this.getProblem_dynamic().getDistances_dynamic(this.getdistance_by_index_dynamic(i), this.getdistance_by_index_dynamic(j)), x[i][j][r]);
                     
                           }
                   }
                   }
                //  expr.setConstant(-temps_legal);
               }
              ends.add((IloIntExpr) cplex.max(0, cplex.sum(get_temps_tournees_avant(k)-temps_legal,expr)));   
            }
            //cplex.addMinimize(cplex.max(arrayFromList(ends)));
            // contraintes
            //Contrainte de respect de la capacité réstante du véhicule/tournée
           // forall(r in tours)
          //sum(i in CF,j in CD:j!=i) Demandes_client[i]*x[i,j,r]<= capacite_tour[r];
            for (int r: tours){
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int i: CF)
                {
                    for (int j: CD){
                    if (i!=j){
                        expr.addTerm(this.getdemande_by_index_dynamic(i), x[i][j][r]);
                    }
                    }
                }
                
                cplex.addLe(expr, this.getTournees().get(r-1).getCurrent_capacity());
                
            }
            // Contrainte de respect du temps maximal (inclus l'overtime maximal permis)  

         //forall (k in K)
         // sum(r in k..k+(t_m-1)*n, i in CF,j in CD)distance[i][j]*x[i, j ,r]-temps_legal<=overtime_legal;
                
       for (int k: K){
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int r=k;r<k+1+(t_m-1)*n;r++)
                {
                    for (int i: CF){
                        for (int j: CD)
                        {
                    if (i!=j){
                        expr.addTerm(this.getProblem_dynamic().getDistances_dynamic(this.getdistance_by_index_dynamic(i), this.getdistance_by_index_dynamic(j)), x[i][j][r]);
                    }
                    }
                    }
                }
                
                cplex.addLe(expr, temps_legal-get_temps_tournees_avant(k));
                
            }
         
  // Contraintes de conservation de flux au niveau des clients et du dépot cenral

//forall (j in CT, r in tours)  
//sum(i in CF: i!=j)x[i, j ,r]==sum(i in CD: i!=j)x[j, i ,r];
        for (int j: CT){
            for(int r : tours)
            {
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int i:CF)
                {
                    
                    if (i!=j){
                        expr.addTerm(1.0, x[i][j][r]);
                                        
                    }
                }
                for (int i:CD)
                {
                    
                    if (i!=j){
                        expr.addTerm(-1.0, x[j][i][r]);
                                        
                    }
                }
                
                cplex.addEq(expr, 0);
            } 
            }
    // Chaque véhicule initialement stationné sur un dépot fictifs doit éffectué une tournée au moins

     //forall (k in K)
     //sum(r in k..k+(t_m-1)*n, i in CF,j in CD: j!=i)x[i, j ,r]>=stationnee_initiallement[k];
  
      for (int k: K){
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int r=k;r<k+1+(t_m-1)*n;r++)
                {
                    for (int i: CF){
                        for (int j: CD)
                        {
                    if (i!=j){
                        expr.addTerm(-1.0, x[i][j][r]);
                    }
                    }
                    }
                }
                
                cplex.addLe(expr, -this.get_stationnee_initialement(k-1));
                
            } 
      // Chaque client doit être visité une seule fois 
 //forall (i in CT)
  // sum (r in tours, j in CD: j!=i)x[i][j][r]==1;
   for (int i :CFF)
   {
        IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int r : tours)
                {
                    for (int j: CD){
                       
                    if (i!=j){
                        expr.addTerm(1.0, x[i][j][r]);
                    }
                    }
                    
                }
                
                cplex.addEq(expr, 1);
                
   }
       
      // Contraintes d'élimination des soutours
//forall(i in CT , j in CT) 
//P[j] >= P[i]+1-card(CD)*(1-sum( r in tours) x[i,j,r]);
 
 //forall(j in CT) 
 //P[j] >= 0;
  
    
    for (int i:CT)
     {
      for(int j:CT)
        {
         if(i!=j){
             IloLinearNumExpr expr=cplex.linearNumExpr();
            expr.addTerm(1.0, u[i]);
            expr.addTerm(-1.0, u[j]);
            for (int r: tours)
                        {
                        expr.addTerm(CD.size(),x[i][j][r]);
                        }
                        cplex.addLe(expr, CD.size()-2);
                    }
                }
            }
             for (int i:CT)
            {
              IloLinearNumExpr expr=cplex.linearNumExpr();
                        expr.addTerm(-1.0, u[i]);
                        cplex.addLe(expr, 0);
            }
             // Un dépot fictif ne peut être destination
              for (int j:F)
                  
            {
                 IloLinearNumExpr expr=cplex.linearNumExpr();
                for (int i:CF)
                {
                    for (int r: tours)
                    {
              
                        expr.addTerm(1, x[i][j][r]);
                        
                    }
                }
                cplex.addEq(expr, 0);
            }
              
              // La tournées 0 est vide 
              { 
               IloLinearNumExpr expr=cplex.linearNumExpr();
               int r=0;
                for (int i:CF)
                {
                    for (int j: CF)
                    {
                     
                        expr.addTerm(1, x[i][j][r]);
                        
                    }
                }
                cplex.addEq(expr, 0);
              }
            //solve node
            if (cplex.solve())
            {
      double objval = cplex.getObjValue();
      double obj3=0;
        for (int i:CF)
            {
                for (int j:CD){
                    if (j!=i){
                    for (int r: tours)
                    {
                        obj3=obj3+this.getProblem_dynamic().getDistances_dynamic(this.getdistance_by_index_dynamic(i), this.getdistance_by_index_dynamic(j))*cplex.getValue(x[i][j][r]);
                    }
                }
                }
            }
      //double [] uval = cplex.getValues(u);
    for (int i:CF)
    {
     for (int j:CD)
     {
         for (int r:tours)
         {
             if (j!=i)
             {
     System.out.println("valeur de x pour i: "+i+" et j : "+j+" et r: " +r+ "est "+cplex.getValue(x[i][j][r]));
             }
         }
        
     }
    } 
            System.out.println("valeur optimal:" +objval);
              System.out.println("distance:" +obj3);
   double obj4=0;
   
     ArrayList<Double> endss = new ArrayList<Double>();
            for (int k: K)
            {
              double expr=0;
               for (int i:CF)
               {
                   for (int j :CD)
                   {
                       for (int r=k;r<k+1+(t_m-1)*n;r++)
                       {
                           if (j!=i)
                           {
                         expr=expr+this.getProblem_dynamic().getDistances_dynamic(this.getdistance_by_index_dynamic(i), this.getdistance_by_index_dynamic(j))*cplex.getValue(x[i][j][r]);
                     
                           }
                   }
                   }
                expr=expr+get_temps_tournees_avant(k)-temps_legal;
                 System.out.println("get_temps_tournes avant (k):" +get_temps_tournees_avant(k));
               }
              endss.add(max(0,expr));
            }
            obj4=endss.get(0);
           System.out.println("valeur de u:" +obj4);
  for (int r=1;r<this.getTournees().size()+1;r++){
        for (int i:CF)
        {
            for(int j:CT)
            {
                if (cplex.getValue(x[i][j][r])==1)
                        {
                            this.getTournees().get(r-1).getCustomers().add(this.getclient_by_index_dynamic(j));
                            
                        }
            }
        }
  }
  for (int i=0;i<this.getTournees().size();i++)
  {
      Tour t=this.getTournees().get(i);
      if ((t.getCustomers().isEmpty())&&(t.getId_fictif()==0))
      {
        this.getTournees().remove(t);
        i--;
      }
  }
  // Affecter les tournées aux camions
  for (int i=0; i<this.getCamions().size();i++)
  {
      this.getCamions().get(i).getTournee_attribuees().clear();
   for (int j=0; j<this.getTournees().size();j++)  
   {
      for (int r: this.Trour_camion(this.getCamions().get(i)))
      {
          if (r==this.getTournees().get(j).getId_tour())
          {
             this.getCamions().get(i).getTournee_attribuees().add(this.getTournees().get(j));
          }
      }
   }
  }
   cplex.end();
           
            
       
        //this.setDEPO_FIC(this.get_depo_fic_dynamic());
       
            }
           
            else {
                this.over=1;
               cplex.end(); 
            }
          
 } catch (IloException e) {
            e.printStackTrace();
        }
            //end
           
    }

public void solveMe_dynamic_overtime_definitif(){
          //this.construct_input_tours_dynamic();
        //model
        try {
            IloCplex cplex = new IloCplex();
            
           //Variables
        IloNumVar[][][] x = new IloNumVar[CF.size()][][];
        for (int i: CF ){
        x[i] = new IloNumVar[CF.size()][];
        for (int j : CF){
            /*System.out.println(" size CF:"+CF.size());
            System.out.println(" size CD:"+CD.size());
            System.out.println(" size tours:"+tours.size());
            System.out.println(" i:"+i);
            System.out.println(" j:"+j);*/
          x[i][j] = cplex.boolVarArray(tours.size()+1);
          
          cplex.add(x[i][j]);
        }     
         }
         IloNumVar[] u=cplex.numVarArray(CD.size()+f,f,Double.MAX_VALUE);
       /*IloNumVar[] u= new IloNumVar[CT.size()+2];
        for (int i=2;i<CT.size()+2;i++ ){
            System.out.println("u de i:"+i);
        cplex.add(u[i]);
        }*/
            // objectives
            IloLinearNumExpr obj1 = cplex.linearNumExpr();
            for (int i:CF)
            {
                for (int j:CF){
                    if (j!=i){
                    for (int r: tours)
                    {
                        obj1.addTerm(this.getProblem_dynamic().getDistances_dynamic(this.getdistance_by_index_dynamic(i), this.getdistance_by_index_dynamic(j)),x[i][j][r]);
                    }
                }
                }
            }
            //cplex.addMinimize(obj1);
  //dexpr float OTk[k in K]= maxl(0,(sum(i in CF, j in CD  , r in k..k+(t_m-1)*n) distance[i][j]*x[i, j ,r]-temps_legal)) ; //overtime performé par le véhicule k
  //dexpr float obj2= max(k in K) OTk[k];
        //   IloLinearNumExpr obj2 = cplex.linearNumExpr();
         List<IloIntExpr> ends = new ArrayList<IloIntExpr>();
            for (int k: K)
            {
               IloLinearNumExpr expr = cplex.linearNumExpr();
               for (int i:CF)
               {
                   for (int j :CD)
                   {
                       for (int r=k;r<k+1+(t_m-1)*n;r++)
                       {
                           if (j!=i)
                           {
                         expr.addTerm(this.getProblem_dynamic().getDistances_dynamic(this.getdistance_by_index_dynamic(i), this.getdistance_by_index_dynamic(j)), x[i][j][r]);
                     
                           }
                   }
                   }
                //  expr.setConstant(-temps_legal);
               }
              ends.add((IloIntExpr) cplex.max(0, cplex.sum(get_temps_tournees_avant(k)-temps_legal,expr)));   
            }
            cplex.addMinimize(cplex.max(arrayFromList(ends)));
            // contraintes
            //Contrainte de respect de la capacité réstante du véhicule/tournée
           // forall(r in tours)
          //sum(i in CF,j in CD:j!=i) Demandes_client[i]*x[i,j,r]<= capacite_tour[r];
            for (int r: tours){
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int i: CF)
                {
                    for (int j: CD){
                    if (i!=j){
                        expr.addTerm(this.getdemande_by_index_dynamic(i), x[i][j][r]);
                    }
                    }
                }
                
                cplex.addLe(expr, this.getTournees().get(r-1).getCurrent_capacity());
                
            }
            // Contrainte de respect du temps maximal (inclus l'overtime maximal permis)  

         //forall (k in K)
         // sum(r in k..k+(t_m-1)*n, i in CF,j in CD)distance[i][j]*x[i, j ,r]-temps_legal<=overtime_legal;
                
       for (int k: K){
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int r=k;r<k+1+(t_m-1)*n;r++)
                {
                    for (int i: CF){
                        for (int j: CD)
                        {
                    if (i!=j){
                        expr.addTerm(this.getProblem_dynamic().getDistances_dynamic(this.getdistance_by_index_dynamic(i), this.getdistance_by_index_dynamic(j)), x[i][j][r]);
                    }
                    }
                    }
                }
                
                cplex.addLe(expr, overtime_parmis+temps_legal-get_temps_tournees_avant(k));
                
            }
         
  // Contraintes de conservation de flux au niveau des clients et du dépot cenral

//forall (j in CT, r in tours)  
//sum(i in CF: i!=j)x[i, j ,r]==sum(i in CD: i!=j)x[j, i ,r];
        for (int j: CT){
            for(int r : tours)
            {
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int i:CF)
                {
                    
                    if (i!=j){
                        expr.addTerm(1.0, x[i][j][r]);
                                        
                    }
                }
                for (int i:CD)
                {
                    
                    if (i!=j){
                        expr.addTerm(-1.0, x[j][i][r]);
                                        
                    }
                }
                
                cplex.addEq(expr, 0);
            } 
            }
    // Chaque véhicule initialement stationné sur un dépot fictifs doit éffectué une tournée au moins

     //forall (k in K)
     //sum(r in k..k+(t_m-1)*n, i in CF,j in CD: j!=i)x[i, j ,r]>=stationnee_initiallement[k];
  
      for (int k: K){
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int r=k;r<k+1+(t_m-1)*n;r++)
                {
                    for (int i: CF){
                        for (int j: CD)
                        {
                    if (i!=j){
                        expr.addTerm(-1.0, x[i][j][r]);
                    }
                    }
                    }
                }
                
                cplex.addLe(expr, -this.get_stationnee_initialement(k-1));
                
            } 
      // Chaque client doit être visité une seule fois 
 //forall (i in CT)
  // sum (r in tours, j in CD: j!=i)x[i][j][r]==1;
   for (int i :CFF)
   {
        IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int r : tours)
                {
                    for (int j: CD){
                       
                    if (i!=j){
                        expr.addTerm(1.0, x[i][j][r]);
                    }
                    }
                    
                }
                
                cplex.addEq(expr, 1);
                
   }
       
      // Contraintes d'élimination des soutours
//forall(i in CT , j in CT) 
//P[j] >= P[i]+1-card(CD)*(1-sum( r in tours) x[i,j,r]);
 
 //forall(j in CT) 
 //P[j] >= 0;
  
    
    for (int i:CT)
     {
      for(int j:CT)
        {
         if(i!=j){
             IloLinearNumExpr expr=cplex.linearNumExpr();
            expr.addTerm(1.0, u[i]);
            expr.addTerm(-1.0, u[j]);
            for (int r: tours)
                        {
                        expr.addTerm(CD.size(),x[i][j][r]);
                        }
                        cplex.addLe(expr, CD.size()-2);
                    }
                }
            }
             for (int i:CT)
            {
              IloLinearNumExpr expr=cplex.linearNumExpr();
                        expr.addTerm(-1.0, u[i]);
                        cplex.addLe(expr, 0);
            }
             // Un dépot fictif ne peut être destination
              for (int j:F)
                  
            {
                 IloLinearNumExpr expr=cplex.linearNumExpr();
                for (int i:CF)
                {
                    for (int r: tours)
                    {
              
                        expr.addTerm(1, x[i][j][r]);
                        
                    }
                }
                cplex.addEq(expr, 0);
            }
              
              // La tournées 0 est vide 
              { 
               IloLinearNumExpr expr=cplex.linearNumExpr();
               int r=0;
                for (int i:CF)
                {
                    for (int j: CF)
                    {
                     
                        expr.addTerm(1, x[i][j][r]);
                        
                    }
                }
                cplex.addEq(expr, 0);
              }
            //solve node
            cplex.solve();
            
      double objval = cplex.getObjValue();
      double obj3=0;
        for (int i:CF)
            {
                for (int j:CF){
                   
                    for (int r: tours)
                    {
                         if (cplex.getValue(x[i][j][r])==1)
                         {
                         System.out.println("obj3 avant ajout:::  "+obj3);
                         System.out.println("client i: "+this.getdistance_by_index_dynamic(i)+ "   client j: "+this.getdistance_by_index_dynamic(j));
                         obj3=obj3+this.getProblem_dynamic().getDistances_dynamic(this.getdistance_by_index_dynamic(i), this.getdistance_by_index_dynamic(j))*cplex.getValue(x[i][j][r]);
                         System.out.println("obj3 après ajout:::  "+obj3);
                    }
                }
                }
            }
         System.out.println("valeur optimal:" +objval);
          System.out.println("distance:" +obj3);
      //double [] uval = cplex.getValues(u);
   /* for (int i:CF)
    {
     for (int j:CD)
     {
         for (int r:tours)
         {
             if (j!=i)
             {
     System.out.println("valeur de x pour i: "+i+" et j : "+j+" et r: " +r+ "est "+cplex.getValue(x[i][j][r]));
             }
         }
        
     }
    } */
            
   double obj4=0;
   
     ArrayList<Double> endss = new ArrayList<Double>();
            for (int k: K)
            {
              double expr=0;
               for (int i:CF)
               {
                   for (int j :CD)
                   {
                       for (int r=k;r<k+1+(t_m-1)*n;r++)
                      
                       {
                            if (cplex.getValue(x[i][j][r])==1)
                           {
                                System.out.println("client i: "+this.getdistance_by_index_dynamic(i)+ "   client j: "+this.getdistance_by_index_dynamic(j));
                         expr=expr+this.getProblem_dynamic().getDistances_dynamic(this.getdistance_by_index_dynamic(i), this.getdistance_by_index_dynamic(j))*cplex.getValue(x[i][j][r]);
                     
                           }
                   }
                   }
                
               }
               System.out.println("expr avant -temps légal:::  "+expr);
                 System.out.println("haaaaaaaaaaa temps légal:::  "+temps_legal);
                expr=expr+get_temps_tournees_avant(k)-temps_legal;
                     System.out.println("expr après - temps légal:::  "+expr);
              endss.add(max(0,expr));
               System.out.println("get_temps_tournes avant (k):" +get_temps_tournees_avant(k));
            }
            obj4=endss.get(0);
           System.out.println("end.get(0):" +obj4);
            System.out.println("taille ends:" +ends.size());
  for (int r=1;r<this.getTournees().size()+1;r++){
        for (int i:CF)
        {
            for(int j:CT)
            {
                if (cplex.getValue(x[i][j][r])==1)
                        {
                            this.getTournees().get(r-1).getCustomers().add(this.getclient_by_index_dynamic(j));
                            
                        }
            }
        }
  }
  for (int i=0;i<this.getTournees().size();i++)
  {
      Tour t=this.getTournees().get(i);
      if ((t.getCustomers().isEmpty())&&(t.getId_fictif()==0))
      {
        this.getTournees().remove(t);
        i--;
      }
  }
  // Affecter les tournées aux camions
  for (int i=0; i<this.getCamions().size();i++)
  {
      this.getCamions().get(i).getTournee_attribuees().clear();
   for (int j=0; j<this.getTournees().size();j++)  
   {
      for (int r: this.Trour_camion(this.getCamions().get(i)))
      {
          if (r==this.getTournees().get(j).getId_tour())
          {
             this.getCamions().get(i).getTournee_attribuees().add(this.getTournees().get(j));
          }
      }
   }
  }
   cplex.end();
           
         
 } catch (IloException e) {
            e.printStackTrace();
        }
            //end
           
}
public void solveMe_dynamic_definitif()
{
    if(this.over==0){
        solveMe_dynamic_distance_definitif();
        if (this.over==1)
        {
            solveMe_dynamic_overtime_definitif();
        }
    }
    else{
        this.construct_input_tours_dynamic();
        solveMe_dynamic_overtime_definitif();
    }
    
}

Ourdynamicmodel(VRP_total VRPT, VRPS problem,int nombre_max_tour, double time_slice, double temps_service,ArrayList<Camion> camions, double overtime, double temps_legal) {
       this.setCamions(camions);
         this.VRPT=VRPT;
         
        this.problem=problem;
     
        this.time_slice=time_slice;
        
       
        this.temps_service=temps_service;
        this.problem.setDistances(this.VRPT.getDistances());
        this.problem.setTimes(this.VRPT.getTimes());
        this.n=this.getCamions().size();
         this.m=this.getProblem().getCustomers().size();
        this.t_m=nombre_max_tour;
        this.temps_legal = temps_legal;
        this.overtime_parmis = overtime;
        this.f=0;
        K.clear();CF.clear();F.clear();FD.clear();CT.clear();CD.clear();CT.clear();CFF.clear();
        for (int i=1;i<n+1;i++)
        {
            System.out.println("i  dyal K:"+i);
            K.add(i);
        }
        for (int i=0;i<m+f+1;i++)
        {
            System.out.println("i  dyal CF:"+i);
            CF.add(i);
        }
        for (int i=0;i<f;i++)
        {
            System.out.println("i  dyal F:"+i);
            F.add(i);
        }
         for (int i=0;i<f+1;i++)
        {
            System.out.println("i  dyal FD:"+i);
            FD.add(i);
        }
          for (int i=f+1;i<f+m+1;i++)
        {
            System.out.println("i  dyal CT:"+i);
            CT.add(i);
        }
           for (int i=f;i<f+m+1;i++)
        {
            System.out.println("i  dyal CD:"+i);
            CD.add(i);
        }
            for (int i=1;i<t_m*n+1;i++)
        {
            System.out.println("i  dyal tours:"+i);
            tours.add(i);
        }
            for (int i:CT)
            {
                CFF.add(i);
                
            }
            for (int i:F)
            {
                CFF.add(i);
            }
     }

Ourdynamicmodel(VRP_total VRPT, VRPD problem_dynamic, double time_slice, double temps_service, ArrayList<Customer> client_olds,ArrayList<Camion> camions, int nombre_max_tour, double overtime, double temps_legal) {
       this.setCamions(camions);
         this.VRPT=VRPT;
         
        this.problem_dynamic=problem_dynamic;
        this.clients_old=client_olds;
         for (int i=0; i< this.clients_old.size();i++)
         {
             this.getProblem_dynamic().getCustomers_dynamic().add(this.clients_old.get(i));
         } 
            
        this.time_slice=time_slice;
       
        this.temps_service=temps_service;
         this.problem_dynamic.setDistances_dynamic(this.VRPT.getDistances());
        this.problem_dynamic.setTimes_dynamic(this.VRPT.getTimes());
         this.n=this.getCamions().size();
         this.m=this.getProblem_dynamic().getCustomers_dynamic().size();
         this.f=this.getProblem_dynamic().getDepots_fictif().size();
         this.t_m=nombre_max_tour;
         
        this.temps_legal = temps_legal;
        this.overtime_parmis = overtime;
        K.clear();CF.clear();F.clear();FD.clear();CT.clear();CD.clear();CT.clear();CFF.clear();
        for (int i=1;i<n+1;i++)
        {
            System.out.println("i  dyal K:"+i);
            K.add(i);
        }
        for (int i=0;i<m+f+1;i++)
        {
            System.out.println("i  dyal CF:"+i);
            CF.add(i);
        }
        for (int i=0;i<f;i++)
        {
            System.out.println("i  dyal F:"+i);
            F.add(i);
        }
         for (int i=0;i<f+1;i++)
        {
            System.out.println("i  dyal FD:"+i);
            FD.add(i);
        }
          for (int i=f+1;i<f+m+1;i++)
        {
            System.out.println("i  dyal CT:"+i);
            CT.add(i);
        }
           for (int i=f;i<f+m+1;i++)
        {
            System.out.println("i  dyal CD:"+i);
            CD.add(i);
        }
            for (int i=1;i<t_m*n+1;i++)
        {
            System.out.println("i  dyal tours:"+i);
            tours.add(i);
        }
            for (int i:CT)
            {
                CFF.add(i);
                
            }
            for (int i:F)
            {
                CFF.add(i);
            }
     }

public ArrayList<Integer> Trour_camion(Camion c){
    ArrayList<Integer> List_tour= new ArrayList<Integer>();
    List_tour.clear();
    for (int q=0;q<this.t_m;q++)
    {
        List_tour.add(c.getIdcamion()+q*this.getCamions().size());
    }
     return List_tour;   
}
public void construct_input_tours_dynamic(){
    for (int i=0;i<this.getCamions().size();i++){
           //camions.get(i).getTournee_attribuees().clear();
        if (camions.get(i).getDepot_fictif_final()==null){
        Tour t= new Tour (this.getCamions().get(i).getIdcamion(),this.getProblem_dynamic().getCapacitycamion_dynamic(),0,this);
        this.getTournees().add(t);
        //camions.get(i).getTournee_attribuees().add(t);
        }
        else
        {
            Tour t= new Tour (this.getCamions().get(i).getIdcamion(),this.getCamions().get(i).getDepot_fictif_final().getCapacité_restante(),this.getCamions().get(i).getDepot_fictif_final().getId_depot_fictif(),this);
             this.getTournees().add(t);
          //  camions.get(i).getTournee_attribuees().add(t);
        }
        for (int j=1;j<this.t_m;j++)
        {
        Tour t= new Tour (this.getCamions().get(i).getIdcamion()+j*this.getCamions().size(),this.getProblem_dynamic().getCapacitycamion_dynamic(),0,this);
         this.getTournees().add(t);
       // camions.get(i).getTournee_attribuees().add(t);        }
         }
    }
    System.out.println("Nombre de tournées du modèle: "+ this.getTournees().size());
    System.out.println("Tailles de la liste tours: "+ this.getTours().size());
}

public int getdistance_by_index_dynamic(int i){
    if (i==0)
    {
       // System.out.println("get distance by index dans le cas 0:  "+i);
        return 0;
    }
    if (i>0&&i<f+1)
    {
        for (int j=1;j<f+1;j++)
    {
        if(j==i)
        {   // System.out.println("get distance by index hada howa le client depot fictif  "+this.getProblem_dynamic().getDepots_fictif().get(j-1).getId_client_fictif());
            return (this.getProblem_dynamic().getDepots_fictif().get(j-1).getId_client_fictif());}
    } 
    }
    for (int j=f+1;j<f+1+this.getProblem_dynamic().getCustomers_dynamic().size();j++)
    {
        if(j==i)
        {  //  System.out.println("get distance by index hada howa le client  "+this.getProblem_dynamic().getCustomers_dynamic().get(j-f-1).getId());
            return (this.getProblem_dynamic().getCustomers_dynamic().get(j-f-1).getId());}
    }
    System.out.println("mal9a 7tta client f getclient byindex");
    return -1 ;
    
}
public double getdemande_by_index_dynamic(int i){
    if (i==0)
    {
        return 0;
    }
    if (i>0&&i<f+1)
    {
       return 0;
    }
    for (int j=f+1;j<f+1+this.getProblem_dynamic().getCustomers_dynamic().size();j++)
    {
        if(j==i)
        {return (this.getProblem_dynamic().getCustomers_dynamic().get(j-f-1).getDemande());}
    }
    System.out.println("mal9a 7tta client f getclient byindex");
    return -1 ;
    
}
public int get_stationnee_initialement(int i){
    
    for (int j=0;j<this.getCamions().size();j++)
    {
        if(j==i)
        {
            if (this.getCamions().get(j).getDepot_fictif_final()==null)
            {return 0;}
            else
            {return 1;}
        }
    }
    System.out.println("mal9a 7tta dépot l had lcamion");
    return -1 ;
    
}
public double get_temps_tournees_avant(int i){
    
    for (int j=1;j<this.getCamions().size()+1;j++)
    {
        if(j==i)
        {
           
            return this.getCamions().get(j-1).getSum_temps_tour();
        }
    }
    System.out.println("mal9a 7tta dépot l had lcamionnnnnn");
    return 0;
    
}
public Customer getclient_by_index_dynamic(int i){
    if (i==0)
    {
        return null;
    }
    if (i>0&&i<f+1)
    {
       return null;
    }
    for (int j=f+1;j<f+1+this.getProblem_dynamic().getCustomers_dynamic().size();j++)
    {
        if(j==i)
        {return (this.getProblem_dynamic().getCustomers_dynamic().get(j-f-1));}
    }
    System.out.println("mal9a 7tta client f getclient byindex");
    return null ;
    
}
public  void solveMe_static_distance() {
    this.construct_input_tours_static();
        
        //model
        try {
            IloCplex cplex = new IloCplex();
            
           //Variables
        IloNumVar[][][] x = new IloNumVar[CF.size()][][];
        for (int i: CF ){
        x[i] = new IloNumVar[CF.size()][];
        for (int j : CF){
            /*System.out.println(" size CF:"+CF.size());
            System.out.println(" size CD:"+CD.size());
            System.out.println(" size tours:"+tours.size());
            System.out.println(" i:"+i);
            System.out.println(" j:"+j);*/
          x[i][j] = cplex.boolVarArray(tours.size()+1);
          
          cplex.add(x[i][j]);
        }     
         }
         IloNumVar[] u=cplex.numVarArray(CD.size()+1,1,Double.MAX_VALUE);
       /*IloNumVar[] u= new IloNumVar[CT.size()+2];
        for (int i=2;i<CT.size()+2;i++ ){
            System.out.println("u de i:"+i);
        cplex.add(u[i]);
        }*/
            // objectives
            IloLinearNumExpr obj1 = cplex.linearNumExpr();
            for (int i:CF)
            {
                for (int j:CF){
                    if (j!=i){
                    for (int r: tours)
                    {
                        obj1.addTerm(this.getProblem().getDistances(this.getdistance_by_index_static(i), this.getdistance_by_index_static(j)),x[i][j][r]);
                    }
                }
                }
            }
            cplex.addMinimize(obj1);
  //dexpr float OTk[k in K]= maxl(0,(sum(i in CF, j in CD  , r in k..k+(t_m-1)*n) distance[i][j]*x[i, j ,r]-temps_legal)) ; //overtime performé par le véhicule k
  //dexpr float obj2= max(k in K) OTk[k];
        //   IloLinearNumExpr obj2 = cplex.linearNumExpr();
         List<IloIntExpr> ends = new ArrayList<IloIntExpr>();
            for (int k: K)
            {
               IloLinearNumExpr expr = cplex.linearNumExpr();
               for (int i:CF)
               {
                   for (int j :CD)
                   {
                       for (int r=k;r<k+1+(t_m-1)*n;r++)
                       {
                           if (j!=i)
                           {
                         expr.addTerm(this.getProblem().getDistances(this.getdistance_by_index_static(i), this.getdistance_by_index_static(j)), x[i][j][r]);
                     
                           }
                   }
                   }
                //  expr.setConstant(-temps_legal);
               }
              ends.add((IloIntExpr) cplex.max(0, cplex.sum(-temps_legal,expr)));   
            }
            //cplex.addMinimize(cplex.max(arrayFromList(ends)));
            // contraintes
            //Contrainte de respect de la capacité réstante du véhicule/tournée
           // forall(r in tours)
          //sum(i in CF,j in CD:j!=i) Demandes_client[i]*x[i,j,r]<= capacite_tour[r];
            for (int r: tours){
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int i: CF)
                {
                    for (int j: CD){
                    if (i!=j){
                        expr.addTerm(this.getdemande_by_index_static(i), x[i][j][r]);
                    }
                    }
                }
                
                cplex.addLe(expr, this.getTournees().get(r-1).getCurrent_capacity());
                
            }
            // Contrainte de respect du temps maximal (inclus l'overtime maximal permis)  

         //forall (k in K)
         // sum(r in k..k+(t_m-1)*n, i in CF,j in CD)distance[i][j]*x[i, j ,r]-temps_legal<=overtime_legal;
                
       for (int k: K){
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int r=k;r<k+1+(t_m-1)*n;r++)
                {
              for (int i: CF){
                        for (int j: CD)
                             {
                    if (i!=j){
                        expr.addTerm(this.getProblem().getDistances(this.getdistance_by_index_static(i), this.getdistance_by_index_static(j)), x[i][j][r]);
                             }
                             }
                             }
                }
                
                cplex.addLe(expr, +temps_legal);
                
            }
         
  // Contraintes de conservation de flux au niveau des clients et du dépot cenral

//forall (j in CT, r in tours)  
//sum(i in CF: i!=j)x[i, j ,r]==sum(i in CD: i!=j)x[j, i ,r];
        for (int j: CT){
            for(int r : tours)
            {
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int i:CF)
                {
                    
                    if (i!=j){
                        expr.addTerm(1.0, x[i][j][r]);
                                        
                    }
                }
                for (int i:CD)
                {
                    
                    if (i!=j){
                        expr.addTerm(-1.0, x[j][i][r]);
                                        
                    }
                }
                
                cplex.addEq(expr, 0);
            } 
            }
    // Chaque véhicule initialement stationné sur un dépot fictifs doit éffectué une tournée au moins

     //forall (k in K)
     //sum(r in k..k+(t_m-1)*n, i in CF,j in CD: j!=i)x[i, j ,r]>=stationnee_initiallement[k];
  
      for (int k: K){
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int r=k;r<k+1+(t_m-1)*n;r++)
                {
                    for (int i: CF){
                        for (int j: CD)
                        {
                    if (i!=j){
                        expr.addTerm(-1.0, x[i][j][r]);
                    }
                    }
                    }
                }
                
                cplex.addLe(expr, -this.get_stationnee_initialement(k-1));
                
            } 
      // Chaque client doit être visité une seule fois 
 //forall (i in CT)
  // sum (r in tours, j in CD: j!=i)x[i][j][r]==1;
   for (int i :CFF)
   {
        IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int r : tours)
                {
                    for (int j: CD){
                       
                    if (i!=j){
                        expr.addTerm(1.0, x[i][j][r]);
                    }
                    }
                    
                }
                
                cplex.addEq(expr, 1);
                
   }
       
      // Contraintes d'élimination des soutours
//forall(i in CT , j in CT) 
//P[j] >= P[i]+1-card(CD)*(1-sum( r in tours) x[i,j,r]);
 
 //forall(j in CT) 
 //P[j] >= 0;
  
    
    for (int i:CT)
     {
      for(int j:CT)
        {
         if(i!=j){
             IloLinearNumExpr expr=cplex.linearNumExpr();
            expr.addTerm(1.0, u[i]);
            expr.addTerm(-1.0, u[j]);
            for (int r: tours)
                        {
                        expr.addTerm(CD.size(),x[i][j][r]);
                        }
                        cplex.addLe(expr, CD.size()-2);
                    }
                }
            }
             for (int i:CT)
            {
              IloLinearNumExpr expr=cplex.linearNumExpr();
                        expr.addTerm(-1.0, u[i]);
                        cplex.addLe(expr, 0);
            }
             // Un dépot fictif ne peut être destination
              for (int j:F)
                  
            {
                 IloLinearNumExpr expr=cplex.linearNumExpr();
                for (int i:CF)
                {
                    for (int r: tours)
                    {
              
                        expr.addTerm(1, x[i][j][r]);
                        
                    }
                }
                cplex.addEq(expr, 0);
            }
              
              // La tournées 0 est vide 
              { 
               IloLinearNumExpr expr=cplex.linearNumExpr();
               int r=0;
                for (int i:CF)
                {
                    for (int j: CF)
                    {
                     
                        expr.addTerm(1, x[i][j][r]);
                        
                    }
                }
                cplex.addEq(expr, 0);
              }
            //solve node
            if (cplex.solve())
            {
      double objval = cplex.getObjValue();
      double obj3=0;
        for (int i:CF)
            {
                for (int j:CF){
                    if (j!=i){
                    for (int r: tours)
                    {
                        obj3=obj3+this.getProblem().getDistances(this.getdistance_by_index_static(i), this.getdistance_by_index_static(j))*cplex.getValue(x[i][j][r]);
                    }
                }
                }
            }
      //double [] uval = cplex.getValues(u);
    for (int i:CF)
    {
     for (int j:CF)
     {
         for (int r:tours)
         {
             if (cplex.getValue(x[i][j][r])==1)
             {
                 
     System.out.println("valeur de x pour i  dans le cas static distance: "+i+" et j : "+j+" et r: " +r+ "est "+cplex.getValue(x[i][j][r]));
             }
         }
        
     }
    } 
            System.out.println("valeur optimal:" +objval);
              System.out.println("distance:" +obj3);
   double obj4=0;
   
     ArrayList<Double> endss = new ArrayList<Double>();
            for (int k: K)
            {
              double expr=0;
               for (int i:CF)
               {
                   for (int j :CD)
                   {
                       for (int r=k;r<k+1+(t_m-1)*n;r++)
                       {
                           if (j!=i)
                           {
                         expr=expr+this.getProblem().getDistances(this.getdistance_by_index_static(i), this.getdistance_by_index_static(j))*cplex.getValue(x[i][j][r]);
                     
                           }
                   }
                   }
                expr=expr-temps_legal;
               }
              endss.add(max(0,expr));
            }
            obj4=endss.get(0);
           System.out.println("valeur de u:" +obj4);
  for (int r=1;r<this.getTournees().size()+1;r++){
        for (int i:CF)
        {
            for(int j:CT)
            {
                if (cplex.getValue(x[i][j][r])==1)
                        {
                            this.getTournees().get(r-1).getCustomers().add(this.getclient_by_index_static(j));
                            
                        }
            }
        }
  }
  
      for(int i=0; i<this.getTournees().size();i++){
            Tour t = this.getTournees().get(i);
                if ((t.getCustomers().isEmpty())&&(t.getId_fictif()==0)){
                 this.getTournees().remove(t);
                i--;
            }
      }
     
  
  // Affecter les tournées aux camions
  for (int i=0; i<this.getCamions().size();i++)
  {
      this.getCamions().get(i).getTournee_attribuees().clear();
   for (int j=0; j<this.getTournees().size();j++)  
   {
      for (int r: this.Trour_camion(this.getCamions().get(i)))
      {
          if (r==this.getTournees().get(j).getId_tour())
          {
             this.getCamions().get(i).getTournee_attribuees().add(this.getTournees().get(j));
          }
      }
   }
  }
          

            //end
            cplex.end();
            }
            else {
                this.over=1;
                cplex.end();
            }
            
        } catch (IloException e) {
            e.printStackTrace();
        }
          this.setDEPO_FIC(this.get_depo_fic());
    }
public  void solveMe_static_overtime() {
    //this.construct_input_tours_static();
        
        //model
        try {
            IloCplex cplex = new IloCplex();
            
           //Variables
        IloNumVar[][][] x = new IloNumVar[CF.size()][][];
        for (int i: CF ){
        x[i] = new IloNumVar[CF.size()][];
        for (int j : CF){
            /*System.out.println(" size CF:"+CF.size());
            System.out.println(" size CD:"+CD.size());
            System.out.println(" size tours:"+tours.size());
            System.out.println(" i:"+i);
            System.out.println(" j:"+j);*/
          x[i][j] = cplex.boolVarArray(tours.size()+1);
          
          cplex.add(x[i][j]);
        }     
         }
         IloNumVar[] u=cplex.numVarArray(CD.size()+1,1,Double.MAX_VALUE);
       /*IloNumVar[] u= new IloNumVar[CT.size()+2];
        for (int i=2;i<CT.size()+2;i++ ){
            System.out.println("u de i:"+i);
        cplex.add(u[i]);
        }*/
            // objectives
            IloLinearNumExpr obj1 = cplex.linearNumExpr();
            for (int i:CF)
            {
                for (int j:CF){
                    if (j!=i){
                    for (int r: tours)
                    {
                        obj1.addTerm(this.getProblem().getDistances(this.getdistance_by_index_static(i), this.getdistance_by_index_static(j)),x[i][j][r]);
                    }
                }
                }
            }
            //cplex.addMinimize(obj1);
  //dexpr float OTk[k in K]= maxl(0,(sum(i in CF, j in CD  , r in k..k+(t_m-1)*n) distance[i][j]*x[i, j ,r]-temps_legal)) ; //overtime performé par le véhicule k
  //dexpr float obj2= max(k in K) OTk[k];
        //   IloLinearNumExpr obj2 = cplex.linearNumExpr();
         List<IloIntExpr> ends = new ArrayList<IloIntExpr>();
            for (int k: K)
            {
               IloLinearNumExpr expr = cplex.linearNumExpr();
               for (int i:CF)
               {
                   for (int j :CD)
                   {
                       for (int r=k;r<k+1+(t_m-1)*n;r++)
                       {
                           if (j!=i)
                           {
                         expr.addTerm(this.getProblem().getDistances(this.getdistance_by_index_static(i), this.getdistance_by_index_static(j)), x[i][j][r]);
                     
                           }
                   }
                   }
                //  expr.setConstant(-temps_legal);
               }
              ends.add((IloIntExpr) cplex.max(0, cplex.sum(-temps_legal,expr)));   
            }
            cplex.addMinimize(cplex.max(arrayFromList(ends)));
            // contraintes
            //Contrainte de respect de la capacité réstante du véhicule/tournée
           // forall(r in tours)
          //sum(i in CF,j in CD:j!=i) Demandes_client[i]*x[i,j,r]<= capacite_tour[r];
            for (int r: tours){
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int i: CF)
                {
                    for (int j: CD){
                    if (i!=j){
                        expr.addTerm(this.getdemande_by_index_static(i), x[i][j][r]);
                    }
                    }
                }
                
                cplex.addLe(expr, this.getTournees().get(r-1).getCurrent_capacity());
                
            }
            // Contrainte de respect du temps maximal (inclus l'overtime maximal permis)  

         //forall (k in K)
         // sum(r in k..k+(t_m-1)*n, i in CF,j in CD)distance[i][j]*x[i, j ,r]-temps_legal<=overtime_legal;
                
       for (int k: K){
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int r=k;r<k+1+(t_m-1)*n;r++)
                {
              for (int i: CF){
                        for (int j: CD)
                             {
                    if (i!=j){
                        expr.addTerm(this.getProblem().getDistances(this.getdistance_by_index_static(i), this.getdistance_by_index_static(j)), x[i][j][r]);
                             }
                             }
                             }
                }
                
                cplex.addLe(expr, this.overtime_parmis+this.temps_legal);
                
            }
         
  // Contraintes de conservation de flux au niveau des clients et du dépot cenral

//forall (j in CT, r in tours)  
//sum(i in CF: i!=j)x[i, j ,r]==sum(i in CD: i!=j)x[j, i ,r];
        for (int j: CT){
            for(int r : tours)
            {
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int i:CF)
                {
                    
                    if (i!=j){
                        expr.addTerm(1.0, x[i][j][r]);
                                        
                    }
                }
                for (int i:CD)
                {
                    
                    if (i!=j){
                        expr.addTerm(-1.0, x[j][i][r]);
                                        
                    }
                }
                
                cplex.addEq(expr, 0);
            } 
            }
    // Chaque véhicule initialement stationné sur un dépot fictifs doit éffectué une tournée au moins

     //forall (k in K)
     //sum(r in k..k+(t_m-1)*n, i in CF,j in CD: j!=i)x[i, j ,r]>=stationnee_initiallement[k];
  
      for (int k: K){
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int r=k;r<k+1+(t_m-1)*n;r++)
                {
                    for (int i: CF){
                        for (int j: CD)
                        {
                    if (i!=j){
                        expr.addTerm(-1.0, x[i][j][r]);
                    }
                    }
                    }
                }
                
                cplex.addLe(expr, -this.get_stationnee_initialement(k-1));
                
            } 
      // Chaque client doit être visité une seule fois 
 //forall (i in CT)
  // sum (r in tours, j in CD: j!=i)x[i][j][r]==1;
   for (int i :CFF)
   {
        IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int r : tours)
                {
                    for (int j: CD){
                       
                    if (i!=j){
                        expr.addTerm(1.0, x[i][j][r]);
                    }
                    }
                    
                }
                
                cplex.addEq(expr, 1);
                
   }
       
      // Contraintes d'élimination des soutours
//forall(i in CT , j in CT) 
//P[j] >= P[i]+1-card(CD)*(1-sum( r in tours) x[i,j,r]);
 
 //forall(j in CT) 
 //P[j] >= 0;
  
    
    for (int i:CT)
     {
      for(int j:CT)
        {
         if(i!=j){
             IloLinearNumExpr expr=cplex.linearNumExpr();
            expr.addTerm(1.0, u[i]);
            expr.addTerm(-1.0, u[j]);
            for (int r: tours)
                        {
                        expr.addTerm(CD.size(),x[i][j][r]);
                        }
                        cplex.addLe(expr, CD.size()-2);
                    }
                }
            }
             for (int i:CT)
            {
              IloLinearNumExpr expr=cplex.linearNumExpr();
                        expr.addTerm(-1.0, u[i]);
                        cplex.addLe(expr, 0);
            }
             // Un dépot fictif ne peut être destination
              for (int j:F)
                  
            {
                 IloLinearNumExpr expr=cplex.linearNumExpr();
                for (int i:CF)
                {
                    for (int r: tours)
                    {
              
                        expr.addTerm(1, x[i][j][r]);
                        
                    }
                }
                cplex.addEq(expr, 0);
            }
              
              // La tournées 0 est vide 
              { 
               IloLinearNumExpr expr=cplex.linearNumExpr();
               int r=0;
                for (int i:CF)
                {
                    for (int j: CF)
                    {
                     
                        expr.addTerm(1, x[i][j][r]);
                        
                    }
                }
                cplex.addEq(expr, 0);
              }
            //solve node
            cplex.solve();
            
      double objval = cplex.getObjValue();
      double obj3=0;
        for (int i:CF)
            {
                for (int j:CF){
                    if (j!=i){
                    for (int r: tours)
                    {
                        System.out.println("obj3 avant ajout distancs::  "+obj3);
                        System.out.println("haa i: "+i+" ha j: "+j+ " haa r:  "+r);
                        obj3=obj3+this.getProblem().getDistances(this.getdistance_by_index_static(i), this.getdistance_by_index_static(j))*cplex.getValue(x[i][j][r]);
                        System.out.println("obj3 après :  "+obj3);
                    }
                }
                }
            }
      //double [] uval = cplex.getValues(u);
    for (int i:CF)
    {
     for (int j:CF)
     {
         for (int r:tours)
         {
             if (cplex.getValue(x[i][j][r])==1)
             {
     System.out.println("valeur de x pour i static avec overtime: "+i+" et j : "+j+" et r: " +r+ "est "+cplex.getValue(x[i][j][r]));
             }
         }
        
     }
    } 
            System.out.println("valeur optimal:" +objval);
              System.out.println("distance:" +obj3);
   double obj4=0;
   
     ArrayList<Double> endss = new ArrayList<Double>();
            for (int k: K)
            {
              double expr=0;
               for (int i:CF)
               {
                   for (int j :CD)
                   {
                       for (int r=k;r<k+1+(t_m-1)*n;r++)
                       {
                           if (j!=i)
                           {
                         expr=expr+this.getProblem().getDistances(this.getdistance_by_index_static(i), this.getdistance_by_index_static(j))*cplex.getValue(x[i][j][r]);
                     
                           }
                   }
                   }
                expr=expr-temps_legal;
               }
              endss.add(max(0,expr));
            }
            obj4=endss.get(0);
           System.out.println("valeur de u:" +obj4);
  for (int r=1;r<this.getTournees().size()+1;r++){
        for (int i:CF)
        {
            for(int j:CT)
            {
                if (cplex.getValue(x[i][j][r])==1)
                        {
                            this.getTournees().get(r-1).getCustomers().add(this.getclient_by_index_static(j));
                            
                        }
            }
        }
  }
  
      for(int i=0; i<this.getTournees().size();i++){
            Tour t = this.getTournees().get(i);
                if ((t.getCustomers().isEmpty())&&(t.getId_fictif()==0)){
                 this.getTournees().remove(t);
                i--;
            }
      }
     
  
  // Affecter les tournées aux camions
  for (int i=0; i<this.getCamions().size();i++)
  {
      this.getCamions().get(i).getTournee_attribuees().clear();
   for (int j=0; j<this.getTournees().size();j++)  
   {
      for (int r: this.Trour_camion(this.getCamions().get(i)))
      {
          if (r==this.getTournees().get(j).getId_tour())
          {
             this.getCamions().get(i).getTournee_attribuees().add(this.getTournees().get(j));
          }
      }
   }
  }
          

            //end
            cplex.end();
           
            
        } catch (IloException e) {
            e.printStackTrace();
        }
          this.setDEPO_FIC(this.get_depo_fic());
    }
public void construct_input_tours_static(){
    for (int i=0;i<this.getCamions().size();i++){
          
        for (int j=0;j<this.t_m;j++)
        {
        Tour t= new Tour (getCamions().get(i).getIdcamion()+j*this.getCamions().size(),this.getProblem().getCapacitycamion(),0,this);
         this.getTournees().add(t);
        //camions.get(i).getTournee_attribuees().add(t);        }
       }
    System.out.println("Nombre de tournées du modèle: "+ this.getTournees().size());
    System.out.println("Tailles de la liste tours: "+ this.getTours().size());
                                    }
}

public int getdistance_by_index_static(int i){
    if (i==0)
    {
        return 0;
    }
   
    for (int j=1;j<1+this.getProblem().getCustomers().size();j++)
    {
        if(j==i)
        {//System.out.println("hada howa client static:  "+this.getProblem().getCustomers().get(j-1).getId());
            return (this.getProblem().getCustomers().get(j-1).getId());}
    }
    
    System.out.println("mal9a 7tta client f getclient byindex");
    return -1 ; 
}
public double getdemande_by_index_static(int i){
    if (i==0)
    {
        return 0;
    }
    
    for (int j=1;j<1+this.getProblem().getCustomers().size();j++)
    {
        if(j==i)
        {return (this.getProblem().getCustomers().get(j-1).getDemande());}
    }
    System.out.println("mal9a 7tta client f getclient byindex static");
    return -1 ;
    
}

public Customer getclient_by_index_static(int i){
    if (i==0)
    {
        return null;
    }
    
    for (int j=1;j<1+this.getProblem().getCustomers().size();j++)
    {
        if(j==i)
        {return (this.getProblem().getCustomers().get(j-1));}
    }
    System.out.println("mal9a 7tta client f getclient byindex");
    return null ;
    
}

    
    public ArrayList<Depotfictif> get_depo_fic_dynamic ()
{
    ArrayList<Depotfictif> depo_fics = new ArrayList<Depotfictif> () ;
    this.clients_old.clear();
    this.setTemps_total_voyage(0);
    int b=1;
    for(int k=1 ;k<=this.getCamions().size(); k++)
    {
        
        Camion c = this.getCamions().get(k-1);
        classer_tournees(c.getTournee_attribuees());
        if (c.getSum_temps_tour()>this.getTime_slice())
        {
                     
            if (c.getDepot_fictif_final()==null)
            {
              c.setDepot_fictif_final(c.getDepot_fictif_final());  
              c.setTemps_tournees_avant(c.getTemps_tournees_avant());
              c.setId_fictif_final(c.getId_fictif_final()); 
              c.setSum_temps_tour(c.getSum_temps_tour()-this.getTime_slice());
              /* System.out.println("haaa le camion : "+c.getIdcamion());
                System.out.println("haaa sum temps tour : "+c.getSum_temps_tour());
                 System.out.println("haaaada sumtemps tour ktar men time slice ");
             System.out.println("haaa temps total tournées : "+c.getTemps_tournees_avant());*/
             for (int m=0;m<c.getTournee_attribuees().size();m++)
                       {
                      for (int n=0;n<c.getTournee_attribuees().get(m).getCustomers().size();n++)
                           {
                            this.clients_old.add(c.getTournee_attribuees().get(m).getCustomers().get(n));  
                            System.out.println("haaa les clients old dyawlo  1 : "+c.getTournee_attribuees().get(m).getCustomers().get(n).getId());
                           }
                         }
                     
            }
            else {      c.setDepot_fictif_final(c.getDepot_fictif_final());
                     
                       depo_fics.add(new     Depotfictif(c.getDepot_fictif_final().getId_client_fictif(),c.getDepot_fictif_final().getCapacité_restante(), b));
                       //this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour);
                       c.setTemps_tournees_avant(c.getTemps_tournees_avant());
                       c.setId_fictif_final(c.getId_fictif_final()); 
                       c.setSum_temps_tour(c.getSum_temps_tour()-this.getTime_slice());
                       /* System.out.println("haaa le camion : "+c.getIdcamion());
                        System.out.println("haaa sum temps tour : "+c.getSum_temps_tour());
                        System.out.println("haaada 7etta howa  sum temps tour ktar men time slice: ");
                        System.out.println("haaa temps total tournées : "+c.getTemps_tournees_avant());*/
                       b++;
                       for (int m=0;m<c.getTournee_attribuees().size();m++)
                       {
                      for (int n=0;n<c.getTournee_attribuees().get(m).getCustomers().size();n++)
                           {
                            this.clients_old.add(c.getTournee_attribuees().get(m).getCustomers().get(n));  
                            System.out.println("haaa les clients old dyawlo  2 : "+c.getTournee_attribuees().get(m).getCustomers().get(n).getId());
                           }
                         }
            }
        }
        else 
        {
            double  sum=c.getSum_temps_tour();
            double sum_temps_tour=c.getSum_temps_tour();
        int i=0;
        if ((c.calucler_temps_total_tournees_dynamic()+sum_temps_tour)<=this.getTime_slice())
           {
            this.setTemps_total_voyage(this.getTemps_total_voyage()+c.calucler_temps_total_tournees_dynamic());
            c.setTemps_tournees_avant(c.getTemps_tournees_avant()+ c.calucler_temps_total_tournees_dynamic());
             c.setId_fictif_final(0);
             c.setDepot_fictif_final(null);
             c.setSum_temps_tour(0);
           /*  System.out.println("haaa le camion : "+c.getIdcamion());
             System.out.println("haaa temps total tournées : "+c.getTemps_tournees_avant());
              System.out.println("haaa sum temps tour : "+c.getSum_temps_tour());*/
             
          
        }
       // System.out.println("time_slice : "+ this.getTime_slice());
        else
        {
          
        while ((sum_temps_tour < this.getTime_slice())&& (c.getTournee_attribuees().size()>i))
        {
            sum_temps_tour= sum_temps_tour+c.getTournee_attribuees().get(i).calculer_temps_tournee_dynamic();
            i++;
        }
        if (sum_temps_tour==this.getTime_slice()){ //&& (c.getTournee_attribuees().size()>j)){
               // for (int l=0; l<c.getTournee_attribuees().get(j).getCustomers().size();l++)
               this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour-sum);
               c.setTemps_tournees_avant(c.getTemps_tournees_avant()+ sum_temps_tour-sum);
               c.setId_fictif_final(0);
               c.setDepot_fictif_final(null);
               c.setSum_temps_tour(0);
             /*   System.out.println("haaa le camion : "+c.getIdcamion());
                System.out.println("haaa temps total tournées : "+c.getTemps_tournees_avant());
                System.out.println("haaa sum temps tour : "+c.getSum_temps_tour());
               */
                 for (int j=i; j<c.getTournee_attribuees().size(); j++)
            {
               // for (int l=0; l<c.getTournee_attribuees().get(j).getCustomers().size();l++)
                for (Customer c1: c.getTournee_attribuees().get(j).getCustomers())
                {
                    this.clients_old.add(c1);
                     System.out.println("haaa les clients old  3: "+c1.getId());
                    //System.out.println("id client à ajouter_dynamic "+ c1.getId());
                    
                }
            }
            }
        else
        {
            if (c.getTournee_attribuees().get(i-1).getId_fictif()==0)
            {
                if (c.getTournee_attribuees().get(i-1).getCustomers().size()<2 )
                {
                    sum_temps_tour= sum_temps_tour-c.getTournee_attribuees().get(i-1).calculer_temps_tournee_dynamic();
                    if ((sum_temps_tour= sum_temps_tour+this.getProblem_dynamic().gettimes_dynamic(0, c.getTournee_attribuees().get(i-1).getCustomers().get(0).getId())+this.temps_service)> this.getTime_slice())
                    {
                         double cap_cl_fic=this.getProblem_dynamic().getCapacitycamion_dynamic();
                       cap_cl_fic=cap_cl_fic-c.getTournee_attribuees().get(i-1).getCustomers().get(0).getDemande();
                      int id_cl_fic=c.getTournee_attribuees().get(i-1).getCustomers().get(0).getId();
                       depo_fics.add(new     Depotfictif(id_cl_fic,cap_cl_fic, b));
                       this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour-sum);
                       c.setTemps_tournees_avant(c.getTemps_tournees_avant()+ sum_temps_tour-sum);
                       c.setId_fictif_final(id_cl_fic); 
                       c.setDepot_fictif_final(depo_fics.get(b-1));
                       c.setSum_temps_tour(sum_temps_tour-this.getTime_slice());
                       System.out.println("haaa le camion : "+c.getIdcamion());
                       System.out.println("haaa temps total tournées : "+c.getTemps_tournees_avant());
                       System.out.println("haaa sum temps tour : "+c.getSum_temps_tour());
                       b++;
                    }
                    else {this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour+this.getProblem_dynamic().gettimes_dynamic(c.getTournee_attribuees().get(i-1).getCustomers().get(0).getId(),0)-sum);
                    c.setTemps_tournees_avant(c.getTemps_tournees_avant()+ sum_temps_tour+this.getProblem_dynamic().gettimes_dynamic(c.getTournee_attribuees().get(i-1).getCustomers().get(0).getId(),0)-sum);
                     c.setSum_temps_tour(sum_temps_tour+this.getProblem_dynamic().gettimes_dynamic(c.getTournee_attribuees().get(i-1).getCustomers().get(0).getId(),0)-this.getTime_slice());
                    System.out.println("haaa le camion : "+c.getIdcamion());
                    c.setId_fictif_final(0);
                    c.setDepot_fictif_final(null);
                     System.out.println("haaa temps total tournées : "+c.getTemps_tournees_avant());
                      System.out.println("haaa sum temps tour : "+c.getSum_temps_tour());
                    }
                    
                    for (int m=i;m<c.getTournee_attribuees().size();m++)
                       {
                      for (int n=0;n<c.getTournee_attribuees().get(m).getCustomers().size();n++)
                           {
                            this.clients_old.add(c.getTournee_attribuees().get(m).getCustomers().get(n)); 
                            System.out.println("haaa client old 4 : "+c.getTournee_attribuees().get(m).getCustomers().get(n).getId());
                           }
                         }
                }
                else
                {
                int j=0;
        double cap_cl_fic=this.getProblem_dynamic().getCapacitycamion_dynamic();
        sum_temps_tour= sum_temps_tour-c.getTournee_attribuees().get(i-1).calculer_temps_tournee_dynamic();
        sum_temps_tour= sum_temps_tour+ this.getProblem_dynamic().gettimes_dynamic(0, c.getTournee_attribuees().get(i-1).getCustomers().get(0).getId())+this.getTemps_service();
          cap_cl_fic=cap_cl_fic-c.getTournee_attribuees().get(i-1).getCustomers().get(0).getDemande();
        while ((sum_temps_tour< this.getTime_slice())&&(c.getTournee_attribuees().get(i-1).getCustomers().size()>(j+1)))
        {
            j++;
            sum_temps_tour=sum_temps_tour+this.getProblem_dynamic().gettimes_dynamic(c.getTournee_attribuees().get(i-1).getCustomers().get(j-1).getId(),c.getTournee_attribuees().get(i-1).getCustomers().get(j).getId())+this.getTemps_service();
            cap_cl_fic=cap_cl_fic-c.getTournee_attribuees().get(i-1).getCustomers().get(j).getDemande();
           
             
        }
        if ((j==(c.getTournee_attribuees().get(i-1).getCustomers().size()-1))&&(sum_temps_tour< this.getTime_slice()))
        {
            sum_temps_tour=sum_temps_tour+this.getProblem_dynamic().gettimes_dynamic(c.getTournee_attribuees().get(i-1).getCustomers().get(j).getId(),0);

      System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
          this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour-sum);
        c.setTemps_tournees_avant(c.getTemps_tournees_avant()+ sum_temps_tour-sum);
        c.setId_fictif_final(0);
        c.setDepot_fictif_final(null);
        c.setSum_temps_tour(sum_temps_tour-this.getTime_slice());
              
        }
        else {
            int id_cl_fic=c.getTournee_attribuees().get(i-1).getCustomers().get(j).getId();
       
        for (int l=j+1; l<c.getTournee_attribuees().get(i-1).getCustomers().size();l++)
        {
            
            this.clients_old.add(c.getTournee_attribuees().get(i-1).getCustomers().get(l));
        }
      
         depo_fics.add(new     Depotfictif(id_cl_fic,cap_cl_fic, b));
        this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour-sum);
        c.setTemps_tournees_avant(c.getTemps_tournees_avant()+ sum_temps_tour-sum);
        c.setId_fictif_final(id_cl_fic);
        c.setDepot_fictif_final(depo_fics.get(b-1));
        c.setSum_temps_tour(sum_temps_tour-this.getTime_slice());
         b++;
        }
        for (int m=i;m<c.getTournee_attribuees().size();m++)
        {
            for (int n=0;n<c.getTournee_attribuees().get(m).getCustomers().size();n++)
            {
               this.clients_old.add(c.getTournee_attribuees().get(m).getCustomers().get(n)); 
              // System.out.println("haa les clients old  5"+ c.getTournee_attribuees().get(m).getCustomers().get(n).getId());
            }
        }
            
        
        //System.out.println("id depot fictif à ajouter "+id_cl_fic); 
        
            }
            }
            else
            {
            if(c.getTournee_attribuees().get(i-1).getCustomers().size()> 0)
            {
                int j=0;   
               Depotfictif d;
               //System.out.println("ooooooooooooo:   "+c.getTournee_attribuees().get(i-1).getId_fictif());
         d=this.getProblem_dynamic().getdepotfictifById_dynamic(c.getTournee_attribuees().get(i-1).getId_fictif());
         double cap_cl_fic=d.getCapacité_restante();

        int from_client_fictif= d.getId_client_fictif();
        sum_temps_tour= sum_temps_tour-c.getTournee_attribuees().get(i-1).calculer_temps_tournee_dynamic();
        sum_temps_tour= sum_temps_tour+ this.getProblem_dynamic().gettimes_dynamic(from_client_fictif, c.getTournee_attribuees().get(i-1).getCustomers().get(0).getId())+this.getTemps_service();
          cap_cl_fic=cap_cl_fic-c.getTournee_attribuees().get(i-1).getCustomers().get(0).getDemande();
        while ((sum_temps_tour< this.getTime_slice())&&(c.getTournee_attribuees().get(i-1).getCustomers().size()>(j+1)) )
        {
            j++;
            sum_temps_tour=sum_temps_tour+this.getProblem_dynamic().gettimes_dynamic(c.getTournee_attribuees().get(i-1).getCustomers().get(j-1).getId(),c.getTournee_attribuees().get(i-1).getCustomers().get(j).getId())+this.getTemps_service();
            cap_cl_fic=cap_cl_fic-c.getTournee_attribuees().get(i-1).getCustomers().get(j).getDemande();
           
                
        }
       if ((j==(c.getTournee_attribuees().get(i-1).getCustomers().size()-1))&&(sum_temps_tour< this.getTime_slice()))
        {
            sum_temps_tour=sum_temps_tour+this.getProblem_dynamic().gettimes_dynamic(c.getTournee_attribuees().get(i-1).getCustomers().get(j).getId(),0);

      System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
          this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour-sum);
        c.setTemps_tournees_avant(c.getTemps_tournees_avant()+ sum_temps_tour-sum);
        c.setId_fictif_final(0);
        c.setDepot_fictif_final(null);
        c.setSum_temps_tour(sum_temps_tour-this.getTime_slice());
              
        }
        else
        {
        int id_cl_fic=c.getTournee_attribuees().get(i-1).getCustomers().get(j).getId();
              
        for (int l=j+1; l<c.getTournee_attribuees().get(i-1).getCustomers().size();l++)
        {
           
            this.clients_old.add(c.getTournee_attribuees().get(i-1).getCustomers().get(l));
          // System.out.println("haa les clients old 6"+ c.getTournee_attribuees().get(i-1).getCustomers().get(l).getId());
        }
       
        depo_fics.add(new     Depotfictif(id_cl_fic,cap_cl_fic, b));
        this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour-sum);
        c.setTemps_tournees_avant(c.getTemps_tournees_avant()+ sum_temps_tour-sum);
        c.setSum_temps_tour(sum_temps_tour-this.getTime_slice());
        c.setDepot_fictif_final(depo_fics.get(b-1));
        c.setId_fictif_final(id_cl_fic);
        /* System.out.println("haaa le camion : "+c.getIdcamion());
             System.out.println("haaa temps total tournées : "+c.getTemps_tournees_avant());
              System.out.println("haaa sum temps tour : "+c.getSum_temps_tour());*/
        b++;
        }
        for (int m=i;m<c.getTournee_attribuees().size();m++)
        {
            for (int n=0;n<c.getTournee_attribuees().get(m).getCustomers().size();n++)
            {
               this.clients_old.add(c.getTournee_attribuees().get(m).getCustomers().get(n));  
            //   System.out.println("haaa client old  6: "+c.getTournee_attribuees().get(m).getCustomers().get(n).getId());
            }
        }
            
            
       
        
        }
            else {
                this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour-sum);
                c.setTemps_tournees_avant(c.getTemps_tournees_avant()+ sum_temps_tour-sum);
                
                c.setSum_temps_tour(sum_temps_tour-this.getTime_slice());
                c.setDepot_fictif_final(null);
                c.setId_fictif_final(0);
                 System.out.println("haaa le camion : "+c.getIdcamion());
                 System.out.println("haaa temps total tournées : "+c.getTemps_tournees_avant());
                  System.out.println("haaa sum temps tour : "+c.getSum_temps_tour());
                for (int m=i;m<c.getTournee_attribuees().size();m++)
                  {
                     for (int n=0;n<c.getTournee_attribuees().get(m).getCustomers().size();n++)
                         {
                          this.clients_old.add(c.getTournee_attribuees().get(m).getCustomers().get(n)); 
                          System.out.println("haaa client old   7: "+c.getTournee_attribuees().get(m).getCustomers().get(n).getId());
                          }
                  }
                 }   
            }
        }
    }
        }
    }
    return depo_fics;
}
   
      public void classer_tournees(ArrayList<Tour> Tours){
         
         Tour tampon1=null;
         Tour tampon2=null;
        
            
             for (int i=1; i<Tours.size();i++)
             {
                 if (Tours.get(i).getId_fictif()!=0)
                 {
                     tampon1=Tours.get(i);
                      tampon2=Tours.get(0);
                     Tours.set(i,tampon2);
                     Tours.set(0, tampon1);
                    
                 }
                 
             }
        
               
     }
    public ArrayList<Depotfictif> get_depo_fic ()
{
    ArrayList<Depotfictif> depo_fics = new ArrayList<Depotfictif> () ;
     //System.out.println("camions : "+ this.getGlobalBestAnt().getCamions().size());
     this.clients_old.clear();
     this.setTemps_total_voyage(0);
     int b=1;
    for(int k=1 ;k<=this.getCamions().size(); k++)
    {
        Camion c = this.getCamions().get(k-1);
         c.setTemps_tournees_avant(0);
         c.setSum_temps_tour(0);
         classer_tournees(this.getCamions().get(k-1).getTournee_attribuees());
       
        double sum_temps_tour=0;
        int i=0;
        if (c.calucler_temps_total_tournees()<=this.getTime_slice())
        {
           // this.setTemps_total_voyage(this.getTemps_total_voyage()+c.calucler_temps_total_tournees());
            c.setTemps_tournees_avant(c.getTemps_tournees_avant()+c.calucler_temps_total_tournees());
            c.setId_fictif_final(0);
            this.setTemps_total_voyage(this.getTemps_total_voyage()+c.calucler_temps_total_tournees());
            c.setDepot_fictif_final(null);
            c.setSum_temps_tour(0);
        }
       // System.out.println("time_slice : "+ this.getTime_slice());
        else
        {
        while (sum_temps_tour < this.getTime_slice())
        {
            sum_temps_tour= sum_temps_tour+c.getTournee_attribuees().get(i).calculer_temps_tournee();
            
            i++;
            //System.out.println("sumtemp : "+ sum_temps_tour);
        }
        if (sum_temps_tour==this.getTime_slice())
        {
            c.setTemps_tournees_avant(c.getTemps_tournees_avant()+ sum_temps_tour);
            c.setId_fictif_final(0);
            c.setDepot_fictif_final(null);
            c.setSum_temps_tour(0);
            this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour);
            System.out.println("haaa temps total voyage awal marra:"+this.getTemps_total_voyage());
            for (int j=i; j<c.getTournee_attribuees().size(); j++)
            {
               // for (int l=0; l<c.getTournee_attribuees().get(j).getCustomers().size();l++)
                for (Customer c1: c.getTournee_attribuees().get(j).getCustomers())
                {
                    this.clients_old.add(c1);
             //       System.out.println("id client à ajouter_dynamic "+ c1.getId());
                    
                }
            }
        }
        else
        {
        int j=0;
        double cap_cl_fic=this.problem.getCapacitycamion();
        sum_temps_tour= sum_temps_tour-c.getTournee_attribuees().get(i-1).calculer_temps_tournee();
         
        sum_temps_tour= sum_temps_tour+ this.getProblem().gettimes(0, c.getTournee_attribuees().get(i-1).getCustomers().get(0).getId())+this.getTemps_service();
          cap_cl_fic=cap_cl_fic-c.getTournee_attribuees().get(i-1).getCustomers().get(0).getDemande();
        while (sum_temps_tour< this.getTime_slice()&&(c.getTournee_attribuees().get(i-1).getCustomers().size()>(j+1)))
        {
            j++;
            sum_temps_tour=sum_temps_tour+this.getProblem().gettimes(c.getTournee_attribuees().get(i-1).getCustomers().get(j-1).getId(),c.getTournee_attribuees().get(i-1).getCustomers().get(j).getId())+this.getTemps_service();
            cap_cl_fic=cap_cl_fic-c.getTournee_attribuees().get(i-1).getCustomers().get(j).getDemande();
           
             
        }
        if ((j==(c.getTournee_attribuees().get(i-1).getCustomers().size()-1))&&(sum_temps_tour< this.getTime_slice()))
        {
            sum_temps_tour=sum_temps_tour+this.getProblem().gettimes(c.getTournee_attribuees().get(i-1).getCustomers().get(j).getId(),0);
            
      System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
          this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour);
        c.setTemps_tournees_avant(c.getTemps_tournees_avant()+ sum_temps_tour);
        c.setId_fictif_final(0);
        c.setDepot_fictif_final(null);
        c.setSum_temps_tour(sum_temps_tour-this.getTime_slice());
            
              
        }
        else {
            int id_cl_fic=c.getTournee_attribuees().get(i-1).getCustomers().get(j).getId();
      
        for (int l=j+1; l<c.getTournee_attribuees().get(i-1).getCustomers().size();l++)
        {
            this.clients_old.add(c.getTournee_attribuees().get(i-1).getCustomers().get(l));
        }
             c.setTemps_tournees_avant(c.getTemps_tournees_avant()+ sum_temps_tour);
         c.setId_fictif_final(id_cl_fic);  
        depo_fics.add(new     Depotfictif(id_cl_fic,cap_cl_fic, b));
        c.setDepot_fictif_final(depo_fics.get(b-1));
        c.setSum_temps_tour(sum_temps_tour-this.getTime_slice());
       this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour);
        System.out.println("haaa temps total voyage "+this.getTemps_total_voyage()); 
        b++;
        System.out.println("haaa sumtemps tour "+sum_temps_tour); 
        }
        
        for (int m=i;m<c.getTournee_attribuees().size();m++)
        {
            for (int n=0;n<c.getTournee_attribuees().get(m).getCustomers().size();n++)
            {
               this.clients_old.add(c.getTournee_attribuees().get(m).getCustomers().get(n));  
            }
        }
         
        //System.out.println("id depot fictif à ajouter "+id_cl_fic); 
        
        }
    }
    }
    System.out.println("Temps total voyage depuis la fonction "+this.getTemps_total_voyage()); 
    for (int i=0;i<depo_fics.size();i++)
    {
       System.out.println("id_fictif depuis la fonction "+depo_fics.get(i).getId_client_fictif());   
    }
    return depo_fics;
}

public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public int getT_m() {
        return t_m;
    }

    public void setT_m(int t_m) {
        this.t_m = t_m;
    }

    public double getTemps_legal() {
        return temps_legal;
    }

    public void setTemps_legal(float temps_legal) {
        this.temps_legal = temps_legal;
    }

    public double getOvertime_parmis() {
        return overtime_parmis;
    }

    public void setOvertime_parmis(float overtime_parmis) {
        this.overtime_parmis = overtime_parmis;
    }

    public ArrayList<Integer> getK() {
        return K;
    }

    public void setK(ArrayList<Integer> K) {
        this.K = K;
    }

    public ArrayList<Integer> getCF() {
        return CF;
    }

    public void setCF(ArrayList<Integer> CF) {
        this.CF = CF;
    }

    public ArrayList<Integer> getF_list() {
        return F;
    }

    public void setF(ArrayList<Integer> F) {
        this.F = F;
    }

    public ArrayList<Integer> getFD() {
        return FD;
    }

    public void setFD(ArrayList<Integer> FD) {
        this.FD = FD;
    }

    public ArrayList<Integer> getCT() {
        return CT;
    }

    public void setCT(ArrayList<Integer> CT) {
        this.CT = CT;
    }

    public ArrayList<Integer> getCD() {
        return CD;
    }

    public void setCD(ArrayList<Integer> CD) {
        this.CD = CD;
    }

    public ArrayList<Integer> getTours() {
        return tours;
    }

    public void setTours(ArrayList<Integer> tours) {
        this.tours = tours;
    }

    public ArrayList<Integer> getCFF() {
        return CFF;
    }

    public void setCFF(ArrayList<Integer> CFF) {
        this.CFF = CFF;
    }

    public ArrayList<Camion> getCamions() {
        return camions;
    }

    public void setCamions(ArrayList<Camion> camions) {
        this.camions = camions;
    }

    public VRPS getProblem() {
        return problem;
    }

    public void setProblem(VRPS problem) {
        this.problem = problem;
    }

    public VRPD getProblem_dynamic() {
        return problem_dynamic;
    }

    public void setProblem_dynamic(VRPD problem_dynamic) {
        this.problem_dynamic = problem_dynamic;
    }

    public int getIterationNumber() {
        return iterationNumber;
    }

    public void setIterationNumber(int iterationNumber) {
        this.iterationNumber = iterationNumber;
    }

    public ArrayList<Customer> getClients_old() {
        return clients_old;
    }

    public void setClients_old(ArrayList<Customer> clients_old) {
        this.clients_old = clients_old;
    }

    public ArrayList<Depotfictif> getDEPO_FIC() {
        return DEPO_FIC;
    }

    public void setDEPO_FIC(ArrayList<Depotfictif> DEPO_FIC) {
        this.DEPO_FIC = DEPO_FIC;
    }

    public double getTime_slice() {
        return time_slice;
    }

    public void setTime_slice(double time_slice) {
        this.time_slice = time_slice;
    }

    public double getTemps_service() {
        return temps_service;
    }

    public void setTemps_service(double temps_service) {
        this.temps_service = temps_service;
    }

    public VRP_total getVRPT() {
        return VRPT;
    }

    public void setVRPT(VRP_total VRPT) {
        this.VRPT = VRPT;
    }

    public double getTemps_total_voyage() {
        return temps_total_voyage;
    }

    public ArrayList<Tour> getTournees() {
        return tournees;
    }

    public void setTournees(ArrayList<Tour> tournees) {
        this.tournees = tournees;
    }
    

    public void setTemps_total_voyage(double temps_total_voyage) {
        this.temps_total_voyage = temps_total_voyage;
    }
  public double getdistance_dynamic(){
         double dist=0;
         int nbcstmrs=0;
         for (Tour t: this.getTournees())
         {
            // System.out.println("distance tournée :"+t.calculer_temps_tournee());
             //System.out.println("nombre de client tournnée :"+t.getCustomers().size());

             dist=dist+t.calculer_temps_tournee_dynamic();
             nbcstmrs=nbcstmrs+t.getCustomers().size();
            
         }
          /* System.out.println("nombre client :"+nbcstmrs);
            System.out.println("distance :"+dist);
            for (Camion c : this.getCamionsUsed())
            {
              System.out.println("temps camion :"+ getTemps(c));  
            }
           System.out.println("overtime :"+(this.getLTR()-this.getProblem().getMaxTemps()));*/
         return dist;
         
     }
    public double getTemps_total_dernier_voyage() {
         
        return this.getdistance_dynamic();
    }
    public void setTemps_total_dernier_voyage(double temps_total_dernier_voyage) {
        this.temps_total_dernier_voyage = temps_total_dernier_voyage;
    }

    public double getOvertime_maximal() {
        return overtime_maximal;
    }

    public void setOvertime_maximal(double overtime_maximal) {
        this.overtime_maximal = overtime_maximal;
    }
}