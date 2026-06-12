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
public class ourdynamicmodel_overtime {
       int n;
       int m;
       int f;
       int t_m;
       float [] demande_client;
       float[] capacité_tour;
       float [][] distance ;
       int [] stationné_initiallement;
       float temps_legal;
       float overtime_parmis;
       ArrayList<Integer> K= new ArrayList<Integer>();
       ArrayList<Integer> CF= new ArrayList<Integer>();
       ArrayList<Integer> F= new ArrayList<Integer>();
       ArrayList<Integer> FD= new ArrayList<Integer>();
       ArrayList<Integer> CT= new ArrayList<Integer>();
       ArrayList<Integer> CD= new ArrayList<Integer>();
       ArrayList<Integer> tours= new ArrayList<Integer>();
        ArrayList<Integer> CFF= new ArrayList<Integer>();

    public ourdynamicmodel_overtime(int n, int m, int f, int t_m, float[] demande_client, float[] capacité_tour, float[][] distance, int[] stationné_initiallement, float temps_legal, float overtime_parmis) {
        this.n = n;
        this.m = m;
        this.f = f;
        this.t_m = t_m;
        this.demande_client = demande_client;
        this.capacité_tour = capacité_tour;
        this.distance = distance;
        this.stationné_initiallement = stationné_initiallement;
        this.temps_legal = temps_legal;
        this.overtime_parmis = overtime_parmis;
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
       
    static IloIntExpr[] arrayFromList(List<IloIntExpr> list) {
        return (IloIntExpr[])list.toArray(new IloIntExpr[list.size()]);
    }
    static Double[] arrayFromListD(List<Double> list) {
        return (Double[])list.toArray(new Double[list.size()]);
    }
     public  void solveMe() {
        
        //model
        try {
            IloCplex cplex = new IloCplex();
            
           //Variables
        IloNumVar[][][] x = new IloNumVar[CF.size()][][];
        for (int i: CF ){
        x[i] = new IloNumVar[CF.size()][];
        for (int j : CF){
          
          x[i][j] = cplex.boolVarArray(tours.size()+1);
          
          cplex.add(x[i][j]);
        }     
         }
         IloNumVar[] u=cplex.numVarArray(CD.size()+1,1,Double.MAX_VALUE);
      
            // objectives
         /*   IloLinearNumExpr obj1 = cplex.linearNumExpr();
            for (int i:CF)
            {
                for (int j:CF){
                    if (j!=i){
                    for (int r: tours)
                    {
                        obj1.addTerm(distance[i][j],x[i][j][r]);
                    }
                }
                }
            }
            cplex.addMinimize(obj1);*/
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
                         expr.addTerm(distance[i][j], x[i][j][r]);
                     
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
                        expr.addTerm(demande_client[i], x[i][j][r]);
                    }
                    }
                }
                
                cplex.addLe(expr, capacité_tour[r-1]);
                
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
                        expr.addTerm(distance[i][j], x[i][j][r]);
                    }
                    }
                    }
                }
                
                cplex.addLe(expr, overtime_parmis+temps_legal);
                
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
                
                cplex.addLe(expr, -stationné_initiallement[k-1]);
                
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
      double obj_distance=0;
        for (int i:CF)
            {
                for (int j:CF){
                    if (j!=i){
                    for (int r: tours)
                    {
                        obj_distance=obj_distance+distance[i][j]*cplex.getValue(x[i][j][r]);
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
              System.out.println("distance:" +obj_distance);
   double obj_over=0;
   
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
                         expr=expr+distance[i][j]*cplex.getValue(x[i][j][r]);
                     
                           }
                   }
                   }
                expr=expr-temps_legal;
               }
              endss.add(max(0,expr));
            }
            obj_over=endss.get(0);
           System.out.println("valeur d'overtime:" +obj_over);

            //end
            cplex.end();
            
        } catch (IloException e) {
            e.printStackTrace();
        }
       
    }
    
}
