/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yarabbiyasser;

import java.util.ArrayList;

/**
 *
 * @author asus
 */
public class AntSystem {
       ArrayList<Ant> ants;
       ArrayList<Camion> camions ;
    ArrayList<Ant> solution;
    ArrayList<Ant> bests;
    VRPS problem;
    VRPD problem_dynamic;
    Ant globalBestAnt = null;
    int globalBestAntIteration = 0;
    int antNumber;
    private double[][] pheromones;
    private double[][] pheromones_dynamic;
    private double[][] pheromones_dynamic_old;
     int iterationNumber;
    public ArrayList<Customer> clients_old = new  ArrayList<Customer>();
    public ArrayList<Depotfictif> DEPO_FIC = new  ArrayList<Depotfictif>();
    private double constantPheromones;
    private double constantPheromones_dynamic;
    private double constantPheromones_old;
    double alpha = 1;
    double beta1 = 1;  
    private double evaporation;
    private double q0;
    private double q0_dynamique;
    private double taux_max;
    private double taux_max_dynamic;
    private double time_slice;
    private double temps_service;
    public VRP_total VRPT;
    private double temps_total_voyage=0;
    private double temps_total_dernier_voyage;
     private double overtime_maximal;

    public double getOvertime_maximal() {
        return overtime_maximal;
    }

    public void setOvertime_maximal(double overtime_maximal) {
        this.overtime_maximal = overtime_maximal;
    }
     

    public double getConstantPheromones_old() {
        return constantPheromones_old;
    }

    public void setConstantPheromones_old(double constantPheromones_old) {
        this.constantPheromones_old = constantPheromones_old;
    }
    

    public ArrayList<Depotfictif> getDEPO_FIC() {
        return DEPO_FIC;
    }

    public void setDEPO_FIC(ArrayList<Depotfictif> DEPO_FIC) {
        this.DEPO_FIC = DEPO_FIC;
    }
    

    public double getTemps_total_dernier_voyage() {
        return temps_total_dernier_voyage;
    }

    public void setTemps_total_dernier_voyage(double temps_total_dernier_voyage) {
        this.temps_total_dernier_voyage = temps_total_dernier_voyage;
    }
    

    public double getTemps_total_voyage() {
        return temps_total_voyage;
    }

    public void setTemps_total_voyage(double temps_total_voyage) {
        this.temps_total_voyage = temps_total_voyage;
    }
    

    public ArrayList<Customer> getClients_old() {
        return clients_old;
    }

    public void setClients_old(ArrayList<Customer> clients_old) {
        this.clients_old = clients_old;
    }

    public VRP_total getVRPT() {
        return VRPT;
    }

    public void setVRPT(VRP_total VRPT) {
        this.VRPT = VRPT;
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

    public ArrayList<Camion> getCamions() {
        return camions;
    }

    public void setCamions(ArrayList<Camion> camions) {
        this.camions = camions;
    }
    
    
    public AntSystem(VRP_total VRPT, VRPS problem, int iterationNumber, double constantPheromones, 
             double evaporation,double alpha, double beta, double q0, double taux_max, double time_slice, double temps_service, ArrayList<Camion> camions) {
       
        this.setCamions(camions);
        this.setProblem(problem);
        this.setVRPT(VRPT);
        
        //System.out.println("nombre de ville problem statique rriginaiiiiiiire"+this.problem.getNbCities());
        //this.problem.calculer_distance_temps(this.problem.getCustomers());
        ants = new ArrayList<Ant>();
        solution = new ArrayList<Ant>();
        bests = new ArrayList<Ant>();
        
        this.setAntNumber(this.problem.getCustomers().size());
        this.setAlpha(alpha);
        this.setBeta1(beta);
        this.setTemps_service(temps_service);
        this.setQ0(q0);
        this.setTaux_max(taux_max);
        this.setTime_slice(time_slice);
        this.getProblem().setDistances(this.getVRPT().getDistances());
          this.getProblem().setTimes(this.getVRPT().getTimes());
          this.setConstantPheromones(constantPheromones);
          this.setEvaporation(evaporation);
          this.setIterationNumber(iterationNumber);
       
        
        this.pheromones = new double[this.VRPT.clients_total.size()+ 1][this.VRPT.clients_total.size() + 1];
       // this.iterationNumber = iterationNumber;
        //initialiser_pheromone();

    }
     public AntSystem(VRP_total VRPT, VRPD problem_dynamic,int iterationNumber, double constantPheromones_dynamic, 
             double evaporation,double alpha, double beta,double[][] pheromones,double constantepheromone, double q0_dynamic, double taux_max_dynamic, double time_slice, double temps_service, ArrayList<Customer> client_olds,ArrayList<Camion> camions) {
       // this.problem = problem;
         //System.out.println("nombre de ville problem statique flawel       "+this.problem.getNbCities());
         this.setCamions(camions);
         this.VRPT=VRPT;
         
        this.problem_dynamic=problem_dynamic;
        this.clients_old=client_olds;
       // this.VRPT.calculer_distance_temps_total;
       // this.problem_dynamic.setDistances_dynamic(new double[problem_dynamic.Tout_les_clients.size()+1][problem_dynamic.Tout_les_clients.size()+1]);
       //this.problem_dynamic.setTimes_dynamic(new double[problem_dynamic.Tout_les_clients.size()+1][problem_dynamic.Tout_les_clients.size()+1]);
       // this.problem_dynamic.calculer_distance_temps_dynamic(this.problem_dynamic.getTout_les_clients());
        ants = new ArrayList<Ant>();
        solution = new ArrayList<Ant>();
        bests = new ArrayList<Ant>();
        if (this.problem_dynamic.getDepots_fictif().size()==0)
        {
        this.antNumber = this.problem_dynamic.getCustomers_dynamic().size();  
        }
        else
        {   
        this.antNumber = this.problem_dynamic.getDepots_fictif().size();
        }
        this.alpha=alpha;
        this.time_slice=time_slice;
        this.beta1=beta;
        this.temps_service=temps_service;
         this.problem_dynamic.setDistances_dynamic(this.VRPT.getDistances());
        this.problem_dynamic.setTimes_dynamic(this.VRPT.getTimes());
       
        this.constantPheromones=constantepheromone;
        this.q0_dynamique=q0_dynamic;
        this.taux_max_dynamic=taux_max_dynamic;
       
        
        //this.constantPheromones = constantPheromones;
        this.constantPheromones_dynamic = constantPheromones_dynamic;
        //this.constantPheromones =5000;// constantWin1*(problem.CustomersSize()+1)/(problem.getCamions().size());
        
        this.evaporation = evaporation;
       //this.pheromones_dynamic = new double[this.VRPT.clients_total.size()+1][this.VRPT.clients_total.size()+1];
        this.pheromones_dynamic = pheromones;
        this.pheromones_dynamic_old = new double[this.VRPT.clients_total.size()+1][this.VRPT.clients_total.size()+1];
        this.iterationNumber = iterationNumber;
        

    }

    public double getTaux_max() {
        return taux_max;
    }

    public void setTaux_max(double taux_max) {
        this.taux_max = taux_max;
    }

    public double getTaux_max_dynamic() {
        return taux_max_dynamic;
    }

    public void setTaux_max_dynamic(double taux_max_dynamic) {
        this.taux_max_dynamic = taux_max_dynamic;
    }

    public double getQ0() {
        return q0;
    }

    public void setQ0(double q0) {
        this.q0 = q0;
    }

    public double getQ0_dynamique() {
        return q0_dynamique;
    }

    public void setQ0_dynamique(double q0_dynamique) {
        this.q0_dynamique = q0_dynamique;
    }

    public double[][] getPheromones() {
        return pheromones;
    }

    public void setConstantPheromones(double constantPheromones) {
        this.constantPheromones = constantPheromones;
    }
    

    public VRPD getProblem_dynamic() {
        return problem_dynamic;
    }

    public void setProblem_dynamic(VRPD problem_dynamic) {
        this.problem_dynamic = problem_dynamic;
        
    }

    public double[][] getPheromones_dynamic() {
        return pheromones_dynamic;
    }

    public void setPheromones_dynamic(double[][] pheromones_dynamic) {
        this.pheromones_dynamic = pheromones_dynamic;
    }

    public double getConstantPheromones_dynamic() {
        return constantPheromones_dynamic;
    }

    public void setConstantPheromones_dynamic(double constantPheromones_dynamic) {
        this.constantPheromones_dynamic = constantPheromones_dynamic;
    }
    

    public ArrayList<Ant> getAnts() {
        return ants;
    }

    public void setAnts(ArrayList<Ant> ants) {
        this.ants = ants;
    }

    public ArrayList<Ant> getSolution() {
        return solution;
    }

    public void setSolution(ArrayList<Ant> solution) {
        this.solution = solution;
    }

    public ArrayList<Ant> getBests() {
        return bests;
    }

    public void setBests(ArrayList<Ant> bests) {
        this.bests = bests;
    }

    public Ant getGlobalBestAnt() {
        return globalBestAnt;
    }

    public void setGlobalBestAnt(Ant globalBestAnt) {
        this.globalBestAnt = globalBestAnt;
    }

    public int getGlobalBestAntIteration() {
        return globalBestAntIteration;
    }

    public void setGlobalBestAntIteration(int globalBestAntIteration) {
        this.globalBestAntIteration = globalBestAntIteration;
    }

    public int getAntNumber() {
        return antNumber;
    }

    public void setAntNumber(int antNumber) {
        this.antNumber = antNumber;
    }

    public int getIterationNumber() {
        return iterationNumber;
    }

    public void setIterationNumber(int iterationNumber) {
        this.iterationNumber = iterationNumber;
    }

    public double getConstantPheromones() {
        return constantPheromones;
    }

    public void setConstantPheromones(int constantPheromones) {
        this.constantPheromones = constantPheromones;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta1() {
        return beta1;
    }

    public void setBeta1(double beta1) {
        this.beta1 = beta1;
    }

    public double getEvaporation() {
        return evaporation;
    }

    public void setEvaporation(double evaporation) {
        this.evaporation = evaporation;
    }
     public VRPS getProblem() {
        return problem;
    }
   public double getPheromones(int i, int j) {
        //System.out.println(pheromones[i][j]);
        return pheromones[i][j]; 
    }
     public void setPheromones(double[][] pheromones) {
        this.pheromones = pheromones;
    }

    public void setPheromones(int i, int j, double v) {
        pheromones[i][j] = v;
        pheromones[j][i] = pheromones[i][j];
    }
     public double getPheromones_dynamic(int i, int j) {
        //System.out.println(pheromones[i][j]);
        return pheromones_dynamic[i][j]; 
    }
     public void setPheromones_dynamic(int i, int j, double v) {
        pheromones_dynamic[i][j] = v;
        pheromones_dynamic[j][i] = pheromones_dynamic[i][j];
    }
     
 public void addPheromones(int i, int j, double v) {
        pheromones[i][j] += v;
        pheromones[j][i] = pheromones[i][j];
    }
 public void addPheromones_dynamic(int i, int j, double v) {
        pheromones_dynamic[i][j] += v;
        pheromones_dynamic[j][i] = pheromones_dynamic[i][j];
    }
 public void update_pheromone_fourmi(Ant fourmi){
     
     for (int i=0; i<=this.VRPT.clients_total.size();i++)
     {
      for (int j=0; j<=this.VRPT.clients_total.size();j++)
      {
          if (fourmi.getPheromones_ant(i, j)==1)
          {
       double evap=this.getPheromones(i, j)*(1-this.getEvaporation()); 
                
        double sum=this.evaporation*this.constantPheromones/(1+fourmi.get_overtime_maximal());
        //double sum=this.constantPheromones/(1+fourmi.get_overtime_maximal());
        
        if( (evap+sum)< this.taux_max) this.setPheromones(i, j, (evap+(sum)));
        
        else this.setPheromones(i, j, this.constantPheromones);
          }
       
     
 }
     }
 }
  public void update_pheromone_fourmi_dynamic(Ant fourmi){
     
     for (int i=0; i<=this.VRPT.clients_total.size();i++)
     {
      for (int j=0; j<=this.VRPT.clients_total.size();j++)
      {
          if (fourmi.getPheromones_ant_dynamic(i, j)==1)
          {
       double evap=this.getPheromones_dynamic(i, j)*(1-this.getEvaporation()); 
                
        double sum=this.evaporation*this.constantPheromones/(1+fourmi.get_overtime_maximal_dynamic());
        //double sum=this.constantPheromones/(1+fourmi.get_overtime_maximal());
        
        if( (evap+sum)< this.taux_max_dynamic) this.setPheromones_dynamic(i, j, (evap+(sum)));
        
        else this.setPheromones_dynamic(i, j, this.constantPheromones);
          }
       
     
 }
     }
 }
 public final void initialiser_pheromone(){
     for (int i=0; i<=this.VRPT.clients_total.size();i++)
     {

         for (int j=0; j<=this.VRPT.clients_total.size();j++)
         {
         this.pheromones[i][j]=this.constantPheromones;
         this.pheromones[j][i]=this.pheromones[i][j];
         }
             
     }
 }
  public final void initialiser_pheromone_dynamic(){
    //  System.out.println("pheromone dyal chi la3ba"+this.pheromones[1][4]);
   // System.out.println("taille clients dynamique avant ajout : "+ problem_dynamic.customers_dynamic.size());
        for (int i=0; i< this.clients_old.size();i++)
         {
             this.getProblem_dynamic().getCustomers_dynamic().add(this.clients_old.get(i));
         }
        this.setConstantPheromones(1/(this.getProblem_dynamic().getCustomers_dynamic().size()* this.constantPheromones));
   // System.out.println("taille clients dynamique après ajout : "+ problem_dynamic.customers_dynamic.size());
     for (int i=0; i<=this.VRPT.clients_total.size();i++)
     {

         for (int j=0; j<=this.VRPT.clients_total.size();j++)
         {
               //System.out.println("cstante pheromoone : "+ this.constantPheromones);
              //System.out.println("pheromoone dynamic : "+ this.pheromones_dynamic[i][j]);
             if (this.pheromones_dynamic[i][j]==this.constantPheromones_old)
             {  
                this.pheromones_dynamic[i][j]=this.constantPheromones;
                this.pheromones_dynamic_old[i][j]=this.constantPheromones;
                this.pheromones_dynamic[j][i]=this.pheromones_dynamic[i][j];
                this.pheromones_dynamic_old[j][i]= this.pheromones_dynamic_old[i][j];
                 
             }
             
             else
             {
              //System.out.println("pheromones dyal we7Da 9diima:   "+this.pheromones[i][j]);
                 this.pheromones_dynamic_old[i][j]=  this.pheromones_dynamic[i][j]*(1-this.constantPheromones_dynamic)+this.constantPheromones_dynamic*this.constantPheromones;
                 this.pheromones_dynamic[i][j]=this.pheromones_dynamic[i][j]*(1-this.constantPheromones_dynamic)+this.constantPheromones_dynamic*this.constantPheromones;
                 this.pheromones_dynamic[j][i]=this.pheromones_dynamic[i][j];
                 this.pheromones_dynamic_old[j][i]=this.pheromones_dynamic_old[i][j];
             }
         
         }
             
     }
      //  System.out.println("Initialisisation términée");
 }
  public final void initialiser_pheromone_dynamic_2(){
    //  System.out.println("pheromone dyal chi la3ba"+this.pheromones[1][4]);
   // System.out.println("taille clients dynamique avant ajout : "+ problem_dynamic.customers_dynamic.size());
        
   // System.out.println("taille clients dynamique après ajout : "+ problem_dynamic.customers_dynamic.size());
     for (int i=0; i<=this.VRPT.clients_total.size();i++)
     {

         for (int j=0; j<=this.VRPT.clients_total.size();j++)
         {
               //System.out.println("cstante pheromoone : "+ this.constantPheromones);
              //System.out.println("pheromoone dynamic : "+ this.pheromones_dynamic[i][j]);
               
                this.pheromones_dynamic[i][j]=this.pheromones_dynamic_old[i][j];
               
                this.pheromones_dynamic[j][i]=this.pheromones_dynamic_old[j][i];
                  
         }
             
     }
      //  System.out.println("Initialisisation términée");
 }
 public void update_pheromone(ArrayList<Ant> fourmis){
     double F= fourmis.size();
     for (int i=0; i<=this.VRPT.clients_total.size();i++)
     {
      for (int j=0; j<=this.VRPT.clients_total.size();j++)
      {
        double evap= this.getPheromones(i, j)*(1-this.evaporation);
        double sum=0;
               for(Ant f: fourmis)
               {
                    
                       sum=sum+f.getPheromones_ant(i, j);
                  
               }
         this.setPheromones(i, j, (evap+(sum/F)));
       
     
 }
     }
 }
 public void update_pheromone_meuilleur_fourmi(Ant fourmi){
     
     for (int i=0; i<=this.VRPT.clients_total.size();i++)
     {
      for (int j=0; j<=this.VRPT.clients_total.size();j++)
      {
          if (fourmi.getPheromones_ant(i, j)==1)
          {
       double evap=this.getPheromones(i, j)*(1-this.getEvaporation()); 
                
        double sum=this.evaporation/fourmi.getTemps_total_tournees();
        //double sum=1/fourmi.getTemps_total_tournees();
        
        if( (evap+sum)< this.taux_max) {this.setPheromones(i, j, (evap+(sum)));}
        
        else {this.setPheromones(i, j, this.constantPheromones); }    
         
          }
       
     
 }
     }
 }
 public void update_pheromone_meuilleur_fourmi_dynamic(Ant fourmi){
     
     for (int i=0; i<=this.VRPT.clients_total.size();i++)
     {
      for (int j=0; j<=this.VRPT.clients_total.size();j++)
      {
          if (fourmi.getPheromones_ant_dynamic(i, j)==1)
          {
       double evap=this.getPheromones_dynamic(i, j)*(1-this.getEvaporation()); 
                
        double sum=this.evaporation/fourmi.getTemps_total_tournees();
        //double sum=1/fourmi.getTemps_total_tournees();
        
        if( (evap+sum)< this.taux_max_dynamic) {this.setPheromones_dynamic(i, j, (evap+(sum)));}
        
        else {this.setPheromones_dynamic(i, j, this.constantPheromones); }    
         
          }
       
     
 }
     }
 }
  public void update_pheromone_meuilleur_fourmi_apres_amelioration(Ant f){
       //System.out.println("bismillaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaah");
      for (int k=0; k<f.getCamions().size();k++)
               {
                   
                   for(int l=0; l<f.getCamions().get(k).getTournee_attribuees().size();l++)
                      
                   {
                       if (f.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().size()==0)
                       {
                           
                       }
                       else
                       {
                      /* System.out.println("haaa k :"+k);
                       System.out.println("haaa l :"+l);
                       System.out.println("haaa size :"+f.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().size());*/
                     Customer c1=f.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().get(0);
                     Customer c2=f.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().get(f.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().size()-1);
                     f.setPheromones_ant(0, c1.getId(),1);
                     f.setPheromones_ant(0, c2.getId(),1);
                     
                      for (int m=1;m<f.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().size()-1;m++)
                      {
                         // System.out.println("ha howa dkhal l dik la boucle sghiwra :"+k);
                      Customer c3=f.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().get(m);
                      Customer c4=f.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().get(m+1);
                      f.setPheromones_ant(c3.getId(), c4.getId(),1);

                      } 
                       }
                   }
                   //System.out.println("haaa kkkkkkkkkkkkkkkk2222222222222 :"+k);
               
               }
      this.update_pheromone_meuilleur_fourmi(f);
 }
   public void update_pheromone_meuilleur_fourmi_apres_amelioration_dynamic(Ant f){
      for (int k=0; k<f.getCamions().size();k++)
               {
                   for(int l=0; l<f.getCamions().get(k).getTournee_attribuees().size();l++)
                      
                   {//
                       if (f.getCamions().get(k).getTournee_attribuees().get(l).getId_fictif()==0){
                           Customer c1=f.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().get(0);
                     Customer c2=f.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().get(f.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().size()-1);
                     
                     f.setPheromones_ant_dynamic(0,c1.getId(),1);
                     f.setPheromones_ant_dynamic(0, c2.getId(),1);
                       }
                       else {
                     Depotfictif d1=this.problem_dynamic.getdepotfictifById_dynamic(f.getCamions().get(k).getTournee_attribuees().get(l).getId_fictif());
                    //int from_client_fictif= d1.getId_client_fictif();
                    if (f.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().size()==0)
                    {
                       f.setPheromones_ant_dynamic(d1.getId_client_fictif(),0,1);  
                    }
                        else
                    {
                        Customer c1=f.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().get(0);
                     Customer c2=f.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().get(f.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().size()-1);
                     
                     f.setPheromones_ant_dynamic(d1.getId_client_fictif(),c1.getId(),1);
                     f.setPheromones_ant_dynamic(0, c2.getId(),1);
                               }
                     
                      for (int m=1;m<f.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().size()-1;m++)
                      {
                      Customer c3=f.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().get(m);
                      Customer c4=f.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().get(m+1);
                      f.setPheromones_ant_dynamic(c3.getId(), c4.getId(),1);

                      } 
                       }
                   }
               
               }
      this.update_pheromone_meuilleur_fourmi_dynamic(f);
 }
 public void update_pheromone_dynamic(ArrayList<Ant> fourmis)
 {
     double F= fourmis.size();
     for (int i=0; i<=this.VRPT.clients_total.size();i++)
     {
      for (int j=0; j<=this.VRPT.clients_total.size();j++)
      {
        double evap= this.getPheromones_dynamic(i, j)*(1-this.evaporation);
        double sum=0;
               for(Ant f: fourmis)
               {
                    
                       sum=sum+f.getPheromones_ant_dynamic(i, j);
                  
               }
         this.setPheromones_dynamic(i, j, (evap+(sum/F)));
       
     
 }
     }
     
 }
    
    

    public void setProblem(VRPS problem) {
        this.problem = problem;
    }
      void addAnt(Ant ant) {
        ants.add(ant);
    }
     private void initialize_system_fourmis(int n) {
        this.ants.clear();
        for (int k = 0; k < n; k++) {
            this.addAnt(new Ant(this, k + 1));
        }
        for (Ant a:ants)
        {
           
             
            a.initializeant();
             
             a.getCamions().clear();
            
            for (int i=0; i<this.getCamions().size();i++)
            {
              a.getCamions().add(this.getCamions().get(i));
            }
            
           
        }
         //System.out.println("Initialisation du system de fourmis términée");
    }
     private void initialize_system_fourmis_dynamic(int n) {
        this.ants.clear();
        for (int k = 0; k < n; k++) {
            this.addAnt(new Ant(this, k + 1,this.problem_dynamic));
        }
        for (Ant a:ants)
        {
            a.initializeant_dynamic();
            a.getCamions().clear();
            
            for (int i=0; i<this.getCamions().size();i++)
            {
              a.getCamions().add(this.getCamions().get(i));
            }
            
        }
         //System.out.println("Initialisation du system de fourmis términée");
    }public Ant bestTour_OVTM(ArrayList<Ant> ants) {
        if (ants == null || ants.isEmpty()) {
            return null;
        }
        int bestTour = 0;
        double min = 100000;
        for (int i = 0; i < ants.size(); i++) {
            //System.out.println("ant "+i+" "+ants.get(i).visitedCities);
            //double dist = ants.get(i).getDistanceTour();
            Ant ant = ants.get(i);
            double cost = ant.get_overtime_total();
            //+ coef3*ant.getCostEmptyVolume() ;
            if (cost != 0 && min > cost) {
                min = cost;
                bestTour = i;
            }
        }
        return ants.get(bestTour);
    }
         public Ant bestTour_OVTM_dynamic(ArrayList<Ant> ants) {
        if (ants == null || ants.isEmpty()) {
            return null;
        }
        int bestTour = 0;
        double min = 100000;
        for (int i = 0; i < ants.size(); i++) {
            //System.out.println("ant "+i+" "+ants.get(i).visitedCities);
            //double dist = ants.get(i).getDistanceTour();
            Ant ant = ants.get(i);
            double cost = ant.get_overtime_total_dynamic();
            //+ coef3*ant.getCostEmptyVolume() ;
            if (cost != 0 && min > cost) {
                min = cost;
                bestTour = i;
            }
        }
        return ants.get(bestTour);
    }
        public Ant bestTour_LTR(ArrayList<Ant> ants) {
        if (ants == null || ants.isEmpty()) {
            return null;
        }
        int bestTour = 0;
        double min = 100000;
        for (int i = 0; i < ants.size(); i++) {
            //System.out.println("ant "+i+" "+ants.get(i).visitedCities);
            //double dist = ants.get(i).getDistanceTour();
            Ant ant = ants.get(i);
            double cost = ant.get_plus_long_voyage();
            //+ coef3*ant.getCostEmptyVolume() ;
            if (cost != 0 && min > cost) {
                min = cost;
                bestTour = i;
            }
        }
        return ants.get(bestTour);
    }
         public Ant bestTour_LTR_dynamic(ArrayList<Ant> ants) {
        if (ants == null || ants.isEmpty()) {
            return null;
        }
        int bestTour = 0;
        double min = 100000;
        for (int i = 0; i < ants.size(); i++) {
            //System.out.println("ant "+i+" "+ants.get(i).visitedCities);
            //double dist = ants.get(i).getDistanceTour();
            Ant ant = ants.get(i);
            double cost = ant.get_plus_long_voyage_dynamic();
            //+ coef3*ant.getCostEmptyVolume() ;
            if (cost != 0 && min > cost) {
                min = cost;
                bestTour = i;
            }
        }
        return ants.get(bestTour);
    }
          public Ant bestTour_distance(ArrayList<Ant> ants) {
        if (ants == null || ants.isEmpty()) {
            return null;
        }
        int bestTour = 0;
        double min = 100000;
        for (int i = 0; i < ants.size(); i++) {
            //System.out.println("ant "+i+" "+ants.get(i).visitedCities);
            //double dist = ants.get(i).getDistanceTour();
            Ant ant = ants.get(i);
            double cost = ant.getTemps_total_tournees();
            //+ coef3*ant.getCostEmptyVolume() ;
            if (cost != 0 && min > cost) {
                min = cost;
                bestTour = i;
            }
        }
        return ants.get(bestTour);
    }
         public Ant bestTour_distance_dynamic(ArrayList<Ant> ants) {
        if (ants == null || ants.isEmpty()) {
            return null;
        }
        int bestTour = 0;
        double min = 100000;
        for (int i = 0; i < ants.size(); i++) {
            //System.out.println("ant "+i+" "+ants.get(i).visitedCities);
            //double dist = ants.get(i).getDistanceTour();
            Ant ant = ants.get(i);
            double cost = ant.getTemps_total_tournees();
            //+ coef3*ant.getCostEmptyVolume() ;
            if (cost != 0 && min > cost) {
                min = cost;
                bestTour = i;
            }
        }
        return ants.get(bestTour);
    }
         

        
      public Ant bestTour(ArrayList<Ant>  ants) {
        if (ants == null || ants.isEmpty()) {
            return null;
        }
        int bestTour = 0;
        double min = 100000;
        for (int i = 0; i < ants.size(); i++) {
            //System.out.println("ant "+i+" "+ants.get(i).visitedCities);
            //double dist = ants.get(i).getDistanceTour();
            Ant ant = ants.get(i);
            double cost = problem.getCoef_distance_parcourue()* ant.getTemps_total_tournees()
                    + problem.getCoef_overtime()* ant.get_overtime_maximal();
            //+ coef3*ant.getCostEmptyVolume() ;
            if (cost != 0 && min > cost) {
                min = cost;
                bestTour = i;
            }
        }
        return ants.get(bestTour);
    }
      public Ant bestTour_dynamic(ArrayList<Ant>  ants) {
        if (ants == null || ants.isEmpty()) {
            return null;
        }
        int bestTour = 0;
        double min = 100000;
        for (int i = 0; i < ants.size(); i++) {
            //System.out.println("ant "+i+" "+ants.get(i).visitedCities);
            //double dist = ants.get(i).getDistanceTour();
            Ant ant = ants.get(i);
           /*System.out.println("ba9i ma7sebna l cost ");
         System.out.println("state ant "+ants.get(i).getState());
         System.out.println("nombre de tournees "+ants.get(i).getTournee_effecuee().size());*/

            double cost = problem_dynamic.getCoef_distance_parcourue()* ant.getTemps_total_tournees()
                    + problem_dynamic.getCoef_overtime()* ant.get_overtime_maximal_dynamic();
          //  System.out.println("ha 7na 7sebnah ");
            //+ coef3*ant.getCostEmptyVolume() ;
            if (cost != 0 && min > cost) {
                min = cost;
                bestTour = i;
            }
        }
        return ants.get(bestTour);
    }
     
         
               //globalBestAnt.classer_tournes_dynamic_defenitif();
      
      public void run() {
        // this.bests.clear();
        
           int i = 0;
           //ArrayList<Camion> Camion_old  = new ArrayList<Camion> () ;
            //System.out.println( "antsystem camion 00 :"+ this.getCamions().size());
           this.initialiser_pheromone();
           
          // System.out.println ("nombre d'itération:"+iterationNumber);
         /* for (int k=0; k<this.getCamions().size(); k++)
          {
           Camion_old.add (this.getCamions().get(k).clone()); 
           Camion_old.get(k).setTemps_tournees_avant(this.getCamions().get(k).getTemps_tournees_avant());
           Camion_old.get(k).setTemps_tournees_avant(this.getCamions().get(k).getSum_temps_tour());
           Camion_old.get(k).setTemps_tournees_avant(this.getCamions().get(k).getId_fictif_final());
          }*/
          double over=this.problem.getOvertimepermis();
          this.problem.setOvertimepermis(0);
          this.problem.setCoef_distance_parcourue(1);
           this.problem.setCoef_overtime(0);
           
               while (i < iterationNumber) {//globalBestAntIteration+this.conditionArret){
                   this.solution.clear();
                    // System.out.println( "ant number 1 :"+ this.antNumber);
                  this.initialize_system_fourmis(this.antNumber);
                  // System.out.println( "ant number 2 :"+ i);
                  int c = 0;
             
                   while (c < ants.size()) {
                   Ant ant = ants.get(c);
                   
                  //System.out.println( "antsystem camion temps total tournées avant hadchi bla overtime:"+ ant.tournee_effecuee.size());
                   
                   
                   ant.run_ant();
                    //System.out.println( "antsystem camion temps total tournées hadchi bla overtime:"+ ant.tournee_effecuee.size());
                    //System.out.println( "antsystem camion 2:"+ this.getCamions().size());
                   
                       
                   this.update_pheromone_fourmi(ant);
                    //System.out.println( "antsystem camion 3:"+ this.getCamions().size());
                   //System.out.println("plus long voyage de la fourmis :  "+c +"  :"+ant.get_plus_long_voyage());
                   //System.out.println("plus petit voyage  de la fourmis :  "+c +"  :"+ant.camions.get(0).calucler_temps_total_tournees());
                      //System.out.println("fourmis  "+c);
                   solution.add(ant);
                   c++;
                                           }
                   
                   //System.out.println("ooooooooooooooooooooooooo");
                 //this.update_pheromone(solution);
                   //Ant localBestAnt=bestTour(this.solution);
                  //  System.out.println( "antsystem camion 4 :"+ this.getCamions().size());
                    // Ant localBestAnt=bestTour_OVTM(this.solution);
                   // Ant localBestAnt=bestTour(this.solution);
                     //Ant localBestAnt=bestTour_time_slice(this.solution);
                   Ant localBestAnt=bestTour_distance(this.solution);
                   //this.update_pheromone(solution);
                 // this.update_pheromone_meuilleur_fourmi(localBestAnt);
                 // Ant localBestAnt=bestTour_LTR(this.solution);
                 // System.out.println("iteration:" + i); //+ " solution local:" + localBestAnt.getCost_ant());
                  // System.out.println("LTR avant:  "+localBestAnt.getCamions().get(localBestAnt.getCamions().size()-1).calucler_temps_total_tournees()/this.problem.getMaxTemps());
                 //  System.out.println("yarabbiyasser.AntSystem.run()"+  localBestAnt.tournee_effecuee.size());
               //ArrayList<Tour> meuilleur_voyage=localBestAnt.tournee_effecuee;
                 for (int k=0; k<localBestAnt.getCamions().size();k++)
                  {
                   for(int l=0; l<localBestAnt.getCamions().get(k).getTournee_attribuees().size();l++)
                   {
                      //  System.out.println( "antsystem camion 5 :"+ this.getCamions().size());
                   localBestAnt.tournee_effecuee.add(localBestAnt.getCamions().get(k).getTournee_attribuees().get(l));
                  
                   }
                   }
                 
                 // vider la liste des camions
                 // System.out.println( "antsystem camion 7 :"+ this.getCamions().size());
                 localBestAnt.getCamions().clear();
                 
                 // System.out.println( "antsystem camion 8:"+ this.getCamions().size());
               // System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
             
                 localBestAnt.classer_tournees(localBestAnt.tournee_effecuee);
                // System.out.println("ooooooooooooooooooooooooo");
                //Recherche_locale_inter_intra_tournee(localBestAnt.tournee_effecuee);
              //  System.out.println("ooooooooooooooooooooooooo");
              //Recherche_locale_inter_intra_tournee(localBestAnt.tournee_effecuee);
              Recherche_locale_inter_intra_tournee(localBestAnt.tournee_effecuee);
                 localBestAnt.setTemps_total_tournees(localBestAnt.calculer_temps_total_tournees(localBestAnt.tournee_effecuee));
                 
               
                 
              
            
            for (int d=0; d<this.getCamions().size();d++)
            {
              localBestAnt.getCamions().add(this.getCamions().get(d));
            }
             //System.out.println( "antsystem camion :"+ this.getCamions().size());
             //System.out.println( "localbest ant cmion  :"+ localBestAnt.getCamions().size());
             /*long totalMemory = Runtime.getRuntime().totalMemory(); 
                    System.out.println( "memoire:"+ totalMemory);*/
                          
               localBestAnt.affecter_vehicule();
                
               localBestAnt.classer_camion(localBestAnt.getCamions());
               
               localBestAnt.get_overtime_maximal();
              /* System.out.println("ooooooooooooooooooooops");
               
               for (int h=0; h<localBestAnt.getCamions().size(); h++)
               {
               System.out.println("héééééééééééééééééééééééééééééééééééééééééééééééééééé: " + localBestAnt.getCamions().get(h).getTournee_attribuees().size()); 
               for(int d=0; d<localBestAnt.getCamions().get(h) .getTournee_attribuees().size();d++)
               {
                   System.out.println("hooooooooooooooooooooooooooooooo: " + localBestAnt.getCamions().get(h).getTournee_attribuees().get(d).getCustomers().size()); 
                   
               }
               }*/
                     
                 //System.out.println("LTR après:  "+localBestAnt.getCamions().get(localBestAnt.getCamions().size()-1).calucler_temps_total_tournees()/this.problem.getMaxTemps());
             
         this.update_pheromone_meuilleur_fourmi_apres_amelioration(localBestAnt);
  
          //System.out.println("taille camion localbestant3333:"+localBestAnt.getCamions().size());
           
                 //  bests.add(localBestAnt);
                   
                    if (globalBestAnt == null) {
                  globalBestAnt = localBestAnt;
                  for(int e=0; e<localBestAnt.getCamions().size();e++)
                   {
                  globalBestAnt.getCamions().set(e,localBestAnt.getCamions().get(e).clone());
                  globalBestAnt.getCamions().get(e).setTemps_tournees_avant(localBestAnt.getCamions().get(e).getTemps_tournees_avant());

                   }
                 
                   
                       
                 } 
                    else 
                    {
                       
                 // if (localBestAnt.getCost_ant()<globalBestAnt.getCost_ant())
                     //if (localBestAnt.calculer_temps_total_tournees(localBestAnt.tournee_effecuee)<globalBestAnt.calculer_temps_total_tournees(globalBestAnt.tournee_effecuee))
              //**************           if (localBestAnt.get_plus_long_voyage()<=globalBestAnt.get_plus_long_voyage())**************/
            // if (localBestAnt.getTemps_total_tournees()<=globalBestAnt.getTemps_total_tournees())
          
             if ((localBestAnt.getTemps_total_tournees())<(globalBestAnt.getTemps_total_tournees()))
                 //if (localBestAnt.get_depo_fic_ant()<=globalBestAnt.get_depo_fic_ant())
                         {
                globalBestAnt = localBestAnt;
              
                   for(int e=0; e<localBestAnt.getCamions().size();e++)
                   {
                  globalBestAnt.getCamions().set(e,localBestAnt.getCamions().get(e).clone());
                  globalBestAnt.getCamions().get(e).setTemps_tournees_avant(localBestAnt.getCamions().get(e).getTemps_tournees_avant());

                   }
                  
                        
               
                    }
                    }
                   
                  //System.out.println( "phéromone" + this.getPheromones(globalBestAnt.getCamions().get(0).getTournee_attribuees().get(0).getCustomers().get(0).getId(),0));   
           i++;
                                  }
               if (globalBestAnt.get_overtime_maximal()==0)
               {
                   System.out.println( " hooooooooooooo makaynch overtiiiiime" );
               
                 this.getCamions().clear(); 
                // System.out.println( " haaaaa le nbr de camions avant :" +this.getCamions().size());
                 this.setCamions(globalBestAnt.getCamions());
                 //System.out.println( " haaaaa le nbr de camions avant :" +this.getCamions().size());
               
               this.setDEPO_FIC(this.get_depo_fic());
             
               //System.out.println( " haaaaa le nbr de tournées effectué:" +globalBestAnt.tour_effec.size());
               System.out.println( " solution global:");
               for (int k=0; k<globalBestAnt.getCamions().size();k++)
               {
                    //System.out.println( "tournées du Camion numéro " + globalBestAnt.getCamions().get(k).getIdcamion()+" :");
                    System.out.print( "tournées du Camion numéro " +(k+1)+" :");
                   for(int l=0; l<globalBestAnt.getCamions().get(k).getTournee_attribuees().size();l++)
                   {
                      // System.out.println( "tournée numéro " + globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getId_tour()+" :\t");
                       System.out.println( "tournée numéro " + (l+1));
                      for (int m=0;m<globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().size();m++)
                      {
                       System.out.println( "\t," + globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().get(m).getId()+"\t");   
                      } 
                      //System.out.println( "\n"); 
                   }
               
               }
              //globalBestAnt.affecter_vehicule();
              
             // System.out.println("haaa le résultat sans calule avant clasercamion 2222::  "+globalBestAnt.getTemps_total_tournees()); 
             //System.out.println("haaaa le calcule avant classer camions222222::  "+globalBestAnt.getCamions().get(globalBestAnt.getCamions().size()-1).calucler_temps_total_tournees());
    
             //globalBestAnt.classer_camion(globalBestAnt.getCamions());
               System.out.println("LTR:  "+((globalBestAnt.get_overtime_maximal()+this.problem.getMaxTemps())/this.problem.getMaxTemps()));
              System.out.println("le plus long voyage:  "+(globalBestAnt.getCamions().get(globalBestAnt.getCamions().size()-1).calucler_temps_total_tournees()));
               System.out.println("coût total :"+ (globalBestAnt.getTemps_total_tournees()+globalBestAnt.get_overtime_maximal()));
// System.out.println("le plus court voyage:  "+globalBestAnt.getCamions().get(0).calucler_temps_total_tournees());
                System.out.println("temps_totale_de_voyage:  "+globalBestAnt.getTemps_total_tournees());
                this.setTemps_total_dernier_voyage(globalBestAnt.getTemps_total_tournees());
                System.out.println( "dépot fictif: ");
               for (int m=0;m<this.getDEPO_FIC().size();m++)
                      {
                       System.out.print( ", " + this.getDEPO_FIC().get(m).getId_client_fictif() );   
                      } 
               System.out.println( "\tclients old: ");
               for (int m=0;m<this.getClients_old().size();m++)
                      {
                       System.out.print( ", " + this.getClients_old().get(m).getId() );   
                      } 
               }
               else
               {
   System.out.println( " hééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééé kaaayen overtime" );
                    Yarabbiyasser.test=1;
                  this.initialiser_pheromone();
                  System.out.println( "haaaaaaaaaaaaaaaaaaa la valeur dyal test: " +Yarabbiyasser.test);
                 this.problem.setOvertimepermis(0);//over);
                 this.problem.setCoef_distance_parcourue(1);
                 this.problem.setCoef_overtime(0);
                // this.getCamions().clear(); 
                /* for (int k=0; k<Camion_old.size(); k++)
                 {
                     this.getCamions().add(Camion_old.get(k));
                 }*/
                 
                 globalBestAnt=null;
                 // System.out.println("le plus long voyage:  "+(this.getCamions().get(this.getCamions().size()-1).calucler_temps_total_tournees()));
                 i=0;
           
               while (i < iterationNumber) {//globalBestAntIteration+this.conditionArret){
                   this.solution.clear();
                    // System.out.println( "ant number 1 :"+ this.antNumber);
                  this.initialize_system_fourmis(this.antNumber);
                  // System.out.println( "ant number 2 :"+ i);
                  int c = 0;
             
                   while (c < ants.size()) {
                   Ant ant = ants.get(c);
            
                 //  System.out.println( "antsystem camion temps total tournées avant hadchi bla overtime:"+ ant.tournee_effecuee.size());
                   
                   
                    ant.run_ant();
                   // System.out.println( "antsystem camion temps total tournées hadchi bla overtime:"+ ant.tournee_effecuee.size());
                       
                   this.update_pheromone_fourmi(ant);
                    //System.out.println( "antsystem camion 3:"+ this.getCamions().size());
                   //System.out.println("plus long voyage de la fourmis :  "+c +"  :"+ant.get_plus_long_voyage());
                   //System.out.println("plus petit voyage  de la fourmis :  "+c +"  :"+ant.camions.get(0).calucler_temps_total_tournees());
                      //System.out.println("fourmis  "+c);
                   solution.add(ant);
                   c++;
                                           }
                   
                   //System.out.println("ooooooooooooooooooooooooo");
                 //this.update_pheromone(solution);
                   //Ant localBestAnt=bestTour(this.solution);
                  //  System.out.println( "antsystem camion 4 :"+ this.getCamions().size());
                    // Ant localBestAnt=bestTour_OVTM(this.solution);
                    Ant localBestAnt=bestTour_LTR(this.solution);
                     //Ant localBestAnt=bestTour_time_slice(this.solution);
                   // Ant localBestAnt=bestTour_distance_parcourru(this.solution);
                   //this.update_pheromone(solution);
                 // this.update_pheromone_meuilleur_fourmi(localBestAnt);
                 // Ant localBestAnt=bestTour_LTR(this.solution);
                 // System.out.println("iteration:" + i); //+ " solution local:" + localBestAnt.getCost_ant());
                  // System.out.println("LTR avant:  "+localBestAnt.getCamions().get(localBestAnt.getCamions().size()-1).calucler_temps_total_tournees()/this.problem.getMaxTemps());
                 //  System.out.println("yarabbiyasser.AntSystem.run()"+  localBestAnt.tournee_effecuee.size());
               //ArrayList<Tour> meuilleur_voyage=localBestAnt.tournee_effecuee;
                 for (int k=0; k<localBestAnt.getCamions().size();k++)
                  {
                   for(int l=0; l<localBestAnt.getCamions().get(k).getTournee_attribuees().size();l++)
                   {
                      //  System.out.println( "antsystem camion 5 :"+ this.getCamions().size());
                   localBestAnt.tournee_effecuee.add(localBestAnt.getCamions().get(k).getTournee_attribuees().get(l));
                  
                   }
                   }
                 
                 // vider la liste des camions
                 // System.out.println( "antsystem camion 7 :"+ this.getCamions().size());
                 localBestAnt.getCamions().clear();
                 
                 // System.out.println( "antsystem camion 8:"+ this.getCamions().size());
               // System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
             
                 localBestAnt.classer_tournees(localBestAnt.tournee_effecuee);
                // System.out.println("ooooooooooooooooooooooooo");
                //Recherche_locale_inter_intra_tournee(localBestAnt.tournee_effecuee);
              //  System.out.println("ooooooooooooooooooooooooo");
              Recherche_locale_inter_intra_tournee(localBestAnt.tournee_effecuee);
              
                 localBestAnt.setTemps_total_tournees(localBestAnt.calculer_temps_total_tournees(localBestAnt.tournee_effecuee));
                 
               
                 
              
            
            for (int d=0; d<this.getCamions().size();d++)
            {
              localBestAnt.getCamions().add(this.getCamions().get(d));
            }
             //System.out.println( "antsystem camion :"+ this.getCamions().size());
             //System.out.println( "localbest ant cmion  :"+ localBestAnt.getCamions().size());
             /*long totalMemory = Runtime.getRuntime().totalMemory(); 
                    System.out.println( "memoire:"+ totalMemory);*/
                          
               localBestAnt.affecter_vehicule();
                
               localBestAnt.classer_camion(localBestAnt.getCamions());
               
               localBestAnt.get_overtime_maximal();
                 //System.out.println("LTR après:  "+localBestAnt.getCamions().get(localBestAnt.getCamions().size()-1).calucler_temps_total_tournees()/this.problem.getMaxTemps());
          System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");     
         this.update_pheromone_meuilleur_fourmi_apres_amelioration(localBestAnt);
  
          
           
                 //  bests.add(localBestAnt);
                   
                    if (globalBestAnt == null) {
                  globalBestAnt = localBestAnt;
                  for(int e=0; e<localBestAnt.getCamions().size();e++)
                   {
                  globalBestAnt.getCamions().set(e,localBestAnt.getCamions().get(e).clone());
                  globalBestAnt.getCamions().get(e).setTemps_tournees_avant(localBestAnt.getCamions().get(e).getTemps_tournees_avant());

                   }
                 
                   
                       
                 } 
                    else 
                    {
                       
                 // if (localBestAnt.getCost_ant()<globalBestAnt.getCost_ant())
                     //if (localBestAnt.calculer_temps_total_tournees(localBestAnt.tournee_effecuee)<globalBestAnt.calculer_temps_total_tournees(globalBestAnt.tournee_effecuee))
              //**************           if (localBestAnt.get_plus_long_voyage()<=globalBestAnt.get_plus_long_voyage())**************/
            // if (localBestAnt.getTemps_total_tournees()<=globalBestAnt.getTemps_total_tournees())
          
             if ((/*localBestAnt.getTemps_total_tournees()+*/localBestAnt.get_overtime_maximal())<(/*globalBestAnt.getTemps_total_tournees()+*/globalBestAnt.get_overtime_maximal()))
                 //if (localBestAnt.get_depo_fic_ant()<=globalBestAnt.get_depo_fic_ant())
                         {
                globalBestAnt = localBestAnt;
              
                   for(int e=0; e<localBestAnt.getCamions().size();e++)
                   {
                  globalBestAnt.getCamions().set(e,localBestAnt.getCamions().get(e).clone());
                  globalBestAnt.getCamions().get(e).setTemps_tournees_avant(localBestAnt.getCamions().get(e).getTemps_tournees_avant());

                   }
                  
                        
               
                    }
                    }
                   
                  //System.out.println( "phéromone" + this.getPheromones(globalBestAnt.getCamions().get(0).getTournee_attribuees().get(0).getCustomers().get(0).getId(),0));   
           i++;
            
               }
                this.getCamions().clear(); 
                // System.out.println( " haaaaa le nbr de camions avant :" +this.getCamions().size());
                 this.setCamions(globalBestAnt.getCamions());
                 //System.out.println( " haaaaa le nbr de camions avant :" +this.getCamions().size());
               
               this.setDEPO_FIC(this.get_depo_fic());
             
               //System.out.println( " haaaaa le nbr de tournées effectué:" +globalBestAnt.tour_effec.size());
               System.out.println( " solution global:");
               for (int k=0; k<globalBestAnt.getCamions().size();k++)
               {
                    //System.out.println( "tournées du Camion numéro " + globalBestAnt.getCamions().get(k).getIdcamion()+" :");
                    System.out.print( "tournées du Camion numéro " +(k+1)+" :");
                   for(int l=0; l<globalBestAnt.getCamions().get(k).getTournee_attribuees().size();l++)
                   {
                      // System.out.println( "tournée numéro " + globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getId_tour()+" :\t");
                       System.out.println( "tournée numéro " + (l+1));
                      for (int m=0;m<globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().size();m++)
                      {
                       System.out.println( "\t," + globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().get(m).getId()+"\t");   
                      } 
                      //System.out.println( "\n"); 
                   }
               
               }
              //globalBestAnt.affecter_vehicule();
              
             // System.out.println("haaa le résultat sans calule avant clasercamion 2222::  "+globalBestAnt.getTemps_total_tournees()); 
             //System.out.println("haaaa le calcule avant classer camions222222::  "+globalBestAnt.getCamions().get(globalBestAnt.getCamions().size()-1).calucler_temps_total_tournees());
    
             //globalBestAnt.classer_camion(globalBestAnt.getCamions());
               System.out.println("LTR:  "+((globalBestAnt.get_overtime_maximal()+this.problem.getMaxTemps())/this.problem.getMaxTemps()));
              System.out.println("le plus long voyage:  "+(globalBestAnt.getCamions().get(globalBestAnt.getCamions().size()-1).calucler_temps_total_tournees()));
               System.out.println("coût total :"+ (globalBestAnt.getTemps_total_tournees()+globalBestAnt.get_overtime_maximal()));
// System.out.println("le plus court voyage:  "+globalBestAnt.getCamions().get(0).calucler_temps_total_tournees());
                System.out.println("temps_totale_de_voyage:  "+globalBestAnt.getTemps_total_tournees());
                this.setTemps_total_dernier_voyage(globalBestAnt.getTemps_total_tournees());
                System.out.println( "dépot fictif: ");
               for (int m=0;m<this.getDEPO_FIC().size();m++)
                      {
                       System.out.print( ", " + this.getDEPO_FIC().get(m).getId_client_fictif() );   
                      } 
               System.out.println( "\tclients old: ");
               for (int m=0;m<this.getClients_old().size();m++)
                      {
                       System.out.print( ", " + this.getClients_old().get(m).getId() );   
                      } 
               }
               
               // this.setTemps_total_voyage(globalBestAnt.getCamions().size()*this.time_slice);
               // System.out.println("GAP:  "+(100*((globalBestAnt.getTemps_total_tournees()/this.z_etoile)-1)));
               //System.out.println("capacité restante de la première tournée de ce camion  "+globalBestAnt.getCamions().get(globalBestAnt.getCamions().size()-1).getTournee_attribuees().get(0).getCurrent_capacity());
               //System.out.println("temps de la première tournée de ce camion  "+globalBestAnt.getCamions().get(globalBestAnt.getCamions().size()-1).getTournee_attribuees().get(0).calculer_temps_tournee());
               //System.out.println("le plus court voyage:  "+globalBestAnt.getCamions().get(0).calucler_temps_total_tournees());
     }
     public void run_dynamic() {
         if (Yarabbiyasser.test==1){
             
                  System.out.println( "haaaaaaaaaaaaaaaaaaa la valeur dyal test: " +Yarabbiyasser.test);
                   globalBestAnt=null;
                   int i=0;
                  this.initialiser_pheromone_dynamic();
           //System.out.println ("nombre d'itération:"+iterationNumber);
           
           
            this.problem_dynamic.setCoef_distance_parcourue(0);
           this.problem_dynamic.setCoef_overtime(1);
           
               while (i < this.iterationNumber) {//globalBestAntIteration+this.conditionArret){
                  
                   this.solution.clear();
                  this.initialize_system_fourmis_dynamic(this.antNumber);
                   //System.out.println ("nombre d'itération:"+i);
                  int c = 0;
             
                   while (c < ants.size()) {
                   Ant ant = ants.get(c);
                   
                   ant.run_ant_dynamic();
                   this.update_pheromone_fourmi_dynamic(ant);
                   //this.update_pheromone_fourmi(ant);
                   //System.out.println ("nombre de fourmis:"+c);
                   
                    
                   solution.add(ant);
                   c++;
                                           }
                   
                   
                      Ant localBestAnt=bestTour_LTR_dynamic(this.solution);
                    
             for (int k=0; k<localBestAnt.getCamions().size();k++)
                  {
                   for(int l=0; l<localBestAnt.getCamions().get(k).getTournee_attribuees().size();l++)
                   {
                   localBestAnt.tournee_effecuee.add(localBestAnt.getCamions().get(k).getTournee_attribuees().get(l));
                   }
                   }
                 
                 // vider la liste des camions
                 localBestAnt.getCamions().clear();
               
                 localBestAnt.classer_tournees_dynamic(localBestAnt.tournee_effecuee);
                Recherche_locale_inter_intra_tournee_dynamic(localBestAnt.tournee_effecuee);
                //Recherche_locale_inter_intra_tournee_dynamic(localBestAnt.tournee_effecuee);
                //Recherche_locale_inter_intra_tournee_dynamic(localBestAnt.tournee_effecuee);
                 //Recherche_locale_inter_intra_tournee_dynamic_slice(localBestAnt.tournee_effecuee);
                 localBestAnt.setTemps_total_tournees(localBestAnt.calculer_temps_total_tournees_dynamic(localBestAnt.tournee_effecuee));
                  /*for(int e=0; e<localBestAnt.tournee_effecuee.size();e++)
                   {
                  localBestAnt.tour_effec.add(localBestAnt.tournee_effecuee.get(e));
                   }*/
                 
                   for (int d=0; d<this.getCamions().size();d++)
            {
              localBestAnt.getCamions().add(this.getCamions().get(d));
            }
                 localBestAnt.affecter_vehicule_dynamic();
                 localBestAnt.classer_camion_dynamic(localBestAnt.getCamions());
                 localBestAnt.get_overtime_maximal_dynamic();

                 //localBestAnt.classer_tournes_dynamic_defenitif();

                // System.out.println("LTR après:  "+localBestAnt.getCamions().get(localBestAnt.getCamions().size()-1).calucler_temps_total_tournees_dynamic()/this.problem_dynamic.getMaxTemps_dynamic());
               
              this.update_pheromone_meuilleur_fourmi_apres_amelioration_dynamic(localBestAnt);
                 //  bests.add(localBestAnt);
                   
                    if (globalBestAnt == null) {
                  globalBestAnt = localBestAnt;
                  for(int e=0; e<localBestAnt.getCamions().size();e++)
                   {
                  globalBestAnt.getCamions().set(e,localBestAnt.getCamions().get(e).clone());
                  globalBestAnt.getCamions().get(e).setTemps_tournees_avant(localBestAnt.getCamions().get(e).getTemps_tournees_avant());

                   }
                  
               
                  //globalBestAnt.tour_effec = localBestAnt.tour_effec;
                 } 
                    else 
                    {
                 // if (localBestAnt.getCost_ant()<globalBestAnt.getCost_ant())
                  //  if (localBestAnt.getTemps_total_tournees()<globalBestAnt.getTemps_total_tournees())
                if ((/*localBestAnt.getTemps_total_tournees()+*/localBestAnt.get_overtime_maximal_dynamic())<=(/*globalBestAnt.getTemps_total_tournees()+*/globalBestAnt.get_overtime_maximal_dynamic()))

                        // if (localBestAnt.get_plus_long_voyage_dynamic()<=globalBestAnt.get_plus_long_voyage_dynamic())
                       //if (localBestAnt.get_depo_fic_dynamic_ant()<globalBestAnt.get_depo_fic_dynamic_ant())
                         {
                globalBestAnt = localBestAnt;
                for(int e=0; e<localBestAnt.getCamions().size();e++)
                   {
                  globalBestAnt.getCamions().set(e,localBestAnt.getCamions().get(e).clone());
                  globalBestAnt.getCamions().get(e).setTemps_tournees_avant(localBestAnt.getCamions().get(e).getTemps_tournees_avant());

                   }
                //globalBestAnt.tour_effec = localBestAnt.tour_effec;
               
                    }
                    }
                   
                  //System.out.println( "phéromone" + this.getPheromones(globalBestAnt.getCamions().get(0).getTournee_attribuees().get(0).getCustomers().get(0).getId(),0));   
           i++;
                                        }
                   
                   this.getCamions().clear(); 
                // System.out.println( " haaaaa le nbr de camions avant :" +this.getCamions().size());
                 this.setCamions(globalBestAnt.getCamions());
           
               this.setDEPO_FIC(this.get_depo_fic_dynamic());
               
               System.out.println( " solution global:");
               for (int k=0; k<globalBestAnt.getCamions().size();k++)
               {
                    //System.out.println( "tournées du Camion numéro " + globalBestAnt.getCamions().get(k).getIdcamion()+" :");
                    System.out.println( "tournées du Camion numéro " +(k+1)+" :");
                   for(int l=0; l<globalBestAnt.getCamions().get(k).getTournee_attribuees().size();l++)
                   {
                      // System.out.println( "tournée numéro " + globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getId_tour()+" :\t");
                       System.out.println( "tournée numéro " + (l+1));
                       if (globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getId_fictif()==0)
                       {
                        System.out.println( "dépot fictif : 0");
                       }
                       else { Depotfictif de;
                                de =  this.getProblem_dynamic().getdepotfictifById_dynamic(globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getId_fictif());
                                int from_client_fictif= de.getId_client_fictif();
                                 System.out.println( "dépot fictif : "+ from_client_fictif);
                            }
                      for (int m=0;m<globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().size();m++)
                      {
                       System.out.println( "\t," + globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().get(m).getId()+"\t");   
                      } 
                      //System.out.println( "\n"); 
                   }
               
               }
              //globalBestAnt.affecter_vehicule();
             
              // globalBestAnt.classer_camion_dynamic(globalBestAnt.getCamions());
              // this.problem_dynamic.setCamions_dynamic(globalBestAnt.getCamions());
               System.out.println("LTR:  "+((globalBestAnt.get_overtime_maximal_dynamic()+this.problem_dynamic.getMaxTemps_dynamic())/this.problem_dynamic.getMaxTemps_dynamic()));
                System.out.println("le plus long voyage:  "+(globalBestAnt.getCamions().get(globalBestAnt.getCamions().size()-1).calucler_temps_total_tournees_dynamic()));
               System.out.println("temps_totale_de_voyage:  "+globalBestAnt.getTemps_total_tournees());
               System.out.println("coût total :"+ (globalBestAnt.getTemps_total_tournees()+globalBestAnt.get_overtime_maximal_dynamic()));
               //  this.setTemps_total_voyage((globalBestAnt.getCamions().size()*this.time_slice));
               this.setTemps_total_dernier_voyage(globalBestAnt.getTemps_total_tournees());
               System.out.print( "dépot fictif: ");
               for (int m=0;m<this.getDEPO_FIC().size();m++)
                      {
                       System.out.print( ", " + this.getDEPO_FIC().get(m).getId_client_fictif() );   
                      } 
                System.out.println( "\tclients old: ");
               for (int m=0;m<this.getClients_old().size();m++)
                      {
                       System.out.print( ", " + this.getClients_old().get(m).getId() );   
                      }
               System.out.println( "kaaaaaaaaaaaaaayen overtime ");
               
         }
         else {
         this.bests.clear();
        
           int i = 0;
           this.initialiser_pheromone_dynamic();
           //System.out.println ("nombre d'itération:"+iterationNumber);
           double over=this.problem_dynamic.getOvetime_dynamic();
           this.problem_dynamic.setOvetime_dynamic(0);
            this.problem_dynamic.setCoef_distance_parcourue(1);
           this.problem_dynamic.setCoef_overtime(0);
           
               while (i < this.iterationNumber) {//globalBestAntIteration+this.conditionArret){
                  
                   this.solution.clear();
                  this.initialize_system_fourmis_dynamic(this.antNumber);
                   //System.out.println ("nombre d'itération:"+i);
                  int c = 0;
             
                   while (c < ants.size()) {
                   Ant ant = ants.get(c);
                   
                   ant.run_ant_dynamic();
                   this.update_pheromone_fourmi_dynamic(ant);
                   //this.update_pheromone_fourmi(ant);
                   //System.out.println ("nombre de fourmis:"+c);
                   
                    
                   solution.add(ant);
                   c++;
                                           }
                   
                   
                   
                   
                        //Ant localBestAnt=bestTour_OVTM_dynamic(this.solution);
                     // Ant localBestAnt=bestTour_dynamic(this.solution);
                     Ant localBestAnt=bestTour_distance_dynamic(this.solution);
                    // Ant localBestAnt=bestTour_time_slice_dynamic(this.solution);
                   //Ant localBestAnt=bestTour_distance_dynamic(this.solution);
                    //Ant localBestAnt=bestTour_dynamic(this.solution);
                   //this.update_pheromone(solution);
                 // this.update_pheromone_meuilleur_fourmi(localBestAnt);
                 // Ant localBestAnt=bestTour_LTR(this.solution);
                //  System.out.println("iteration:" + i); //+ " solution local:" + localBestAnt.getCost_ant());
                  // System.out.println("LTR avant:  "+localBestAnt.getCamions().get(localBestAnt.getCamions().size()-1).calucler_temps_total_tournees_dynamic()/this.problem_dynamic.getMaxTemps_dynamic());
               //ArrayList<Tour> meuilleur_voyage=localBestAnt.tournee_effecuee;
              // System.out.println("tail camions: "+ localBestAnt.getCamions().size());
             for (int k=0; k<localBestAnt.getCamions().size();k++)
                  {
                   for(int l=0; l<localBestAnt.getCamions().get(k).getTournee_attribuees().size();l++)
                   {
                   localBestAnt.tournee_effecuee.add(localBestAnt.getCamions().get(k).getTournee_attribuees().get(l));
                   }
                   }
                 
                 // vider la liste des camions
                 localBestAnt.getCamions().clear();
               
                 localBestAnt.classer_tournees_dynamic(localBestAnt.tournee_effecuee);
                Recherche_locale_inter_intra_tournee_dynamic(localBestAnt.tournee_effecuee);
                //Recherche_locale_inter_intra_tournee_dynamic(localBestAnt.tournee_effecuee);
                //Recherche_locale_inter_intra_tournee_dynamic(localBestAnt.tournee_effecuee);
                 //Recherche_locale_inter_intra_tournee_dynamic_slice(localBestAnt.tournee_effecuee);
                 localBestAnt.setTemps_total_tournees(localBestAnt.calculer_temps_total_tournees_dynamic(localBestAnt.tournee_effecuee));
                  /*for(int e=0; e<localBestAnt.tournee_effecuee.size();e++)
                   {
                  localBestAnt.tour_effec.add(localBestAnt.tournee_effecuee.get(e));
                   }*/
                 
                   for (int d=0; d<this.getCamions().size();d++)
            {
              localBestAnt.getCamions().add(this.getCamions().get(d));
            }
                 localBestAnt.affecter_vehicule_dynamic();
                 localBestAnt.classer_camion_dynamic(localBestAnt.getCamions());
                 localBestAnt.get_overtime_maximal_dynamic();

                 localBestAnt.classer_tournes_dynamic_defenitif();

                // System.out.println("LTR après:  "+localBestAnt.getCamions().get(localBestAnt.getCamions().size()-1).calucler_temps_total_tournees_dynamic()/this.problem_dynamic.getMaxTemps_dynamic());
               
              this.update_pheromone_meuilleur_fourmi_apres_amelioration_dynamic(localBestAnt);
                 //  bests.add(localBestAnt);
                   
                    if (globalBestAnt == null) {
                  globalBestAnt = localBestAnt;
                  for(int e=0; e<localBestAnt.getCamions().size();e++)
                   {
                  globalBestAnt.getCamions().set(e,localBestAnt.getCamions().get(e).clone());
                  globalBestAnt.getCamions().get(e).setTemps_tournees_avant(localBestAnt.getCamions().get(e).getTemps_tournees_avant());

                   }
                  
               
                  //globalBestAnt.tour_effec = localBestAnt.tour_effec;
                 } 
                    else 
                    {
                 // if (localBestAnt.getCost_ant()<globalBestAnt.getCost_ant())
                  //  if (localBestAnt.getTemps_total_tournees()<globalBestAnt.getTemps_total_tournees())
                if ((localBestAnt.getTemps_total_tournees()/*+localBestAnt.get_overtime_maximal_dynamic()*/)<=(globalBestAnt.getTemps_total_tournees()/*+globalBestAnt.get_overtime_maximal_dynamic()*/))

                        // if (localBestAnt.get_plus_long_voyage_dynamic()<=globalBestAnt.get_plus_long_voyage_dynamic())
                       //if (localBestAnt.get_depo_fic_dynamic_ant()<globalBestAnt.get_depo_fic_dynamic_ant())
                         {
                globalBestAnt = localBestAnt;
                for(int e=0; e<localBestAnt.getCamions().size();e++)
                   {
                  globalBestAnt.getCamions().set(e,localBestAnt.getCamions().get(e).clone());
                  globalBestAnt.getCamions().get(e).setTemps_tournees_avant(localBestAnt.getCamions().get(e).getTemps_tournees_avant());

                   }
                //globalBestAnt.tour_effec = localBestAnt.tour_effec;
               
                    }
                    }
                   
                  //System.out.println( "phéromone" + this.getPheromones(globalBestAnt.getCamions().get(0).getTournee_attribuees().get(0).getCustomers().get(0).getId(),0));   
           i++;
                                        }
               if (globalBestAnt.get_overtime_maximal_dynamic()==0)
               {
                 this.getCamions().clear(); 
                // System.out.println( " haaaaa le nbr de camions avant :" +this.getCamions().size());
                 this.setCamions(globalBestAnt.getCamions());
           
               this.setDEPO_FIC(this.get_depo_fic_dynamic());
               
               System.out.println( " solution global:");
               for (int k=0; k<globalBestAnt.getCamions().size();k++)
               {
                    //System.out.println( "tournées du Camion numéro " + globalBestAnt.getCamions().get(k).getIdcamion()+" :");
                    System.out.println( "tournées du Camion numéro " +(k+1)+" :");
                   for(int l=0; l<globalBestAnt.getCamions().get(k).getTournee_attribuees().size();l++)
                   {
                      // System.out.println( "tournée numéro " + globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getId_tour()+" :\t");
                       System.out.println( "tournée numéro " + (l+1));
                       if (globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getId_fictif()==0)
                       {
                        System.out.println( "dépot fictif : 0");
                       }
                       else { Depotfictif de;
                                de =  this.getProblem_dynamic().getdepotfictifById_dynamic(globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getId_fictif());
                                int from_client_fictif= de.getId_client_fictif();
                                 System.out.println( "dépot fictif : "+ from_client_fictif);
                            }
                      for (int m=0;m<globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().size();m++)
                      {
                       System.out.println( "\t," + globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().get(m).getId()+"\t");   
                      } 
                      //System.out.println( "\n"); 
                   }
               
               }
              //globalBestAnt.affecter_vehicule();
             
              // globalBestAnt.classer_camion_dynamic(globalBestAnt.getCamions());
              // this.problem_dynamic.setCamions_dynamic(globalBestAnt.getCamions());
               System.out.println("LTR:  "+((globalBestAnt.get_overtime_maximal_dynamic()+this.problem_dynamic.getMaxTemps_dynamic())/this.problem_dynamic.getMaxTemps_dynamic()));
                System.out.println("le plus long voyage:  "+(globalBestAnt.getCamions().get(globalBestAnt.getCamions().size()-1).calucler_temps_total_tournees_dynamic()));
               System.out.println("temps_totale_de_voyage:  "+globalBestAnt.getTemps_total_tournees());
               System.out.println("coût total :"+ (globalBestAnt.getTemps_total_tournees()+globalBestAnt.get_overtime_maximal_dynamic()));
               //  this.setTemps_total_voyage((globalBestAnt.getCamions().size()*this.time_slice));
               this.setTemps_total_dernier_voyage(globalBestAnt.getTemps_total_tournees());
               System.out.print( "dépot fictif: ");
               for (int m=0;m<this.getDEPO_FIC().size();m++)
                   
                      {
                       System.out.print( ", " + this.getDEPO_FIC().get(m).getId_client_fictif() );   
                      } 
                System.out.println( "\tclients old: ");
               for (int m=0;m<this.getClients_old().size();m++)
                      {
                       System.out.print( ", " + this.getClients_old().get(m).getId() );   
                      } 
                System.out.println( "makkaaaaaaaaaaaaaayenech overtime ");
               }
               else
               {
                   Yarabbiyasser.test=1;
                  System.out.println( "haaaaaaaaaaaaaaaaaaa la valeur dyal test: " +Yarabbiyasser.test);
                   globalBestAnt=null;
                   i=0;
                  this.initialiser_pheromone_dynamic_2();
           //System.out.println ("nombre d'itération:"+iterationNumber);
           
           this.problem_dynamic.setOvetime_dynamic(over);
            this.problem_dynamic.setCoef_distance_parcourue(0);
           this.problem_dynamic.setCoef_overtime(1);
           
               while (i < this.iterationNumber) {//globalBestAntIteration+this.conditionArret){
                  
                   this.solution.clear();
                  this.initialize_system_fourmis_dynamic(this.antNumber);
                   //System.out.println ("nombre d'itération:"+i);
                  int c = 0;
             
                   while (c < ants.size()) {
                   Ant ant = ants.get(c);
                   
                   ant.run_ant_dynamic();
                   this.update_pheromone_fourmi_dynamic(ant);
                   //this.update_pheromone_fourmi(ant);
                   //System.out.println ("nombre de fourmis:"+c);
                   
                    
                   solution.add(ant);
                   c++;
                                           }
                   
                   
                      Ant localBestAnt=bestTour_LTR_dynamic(this.solution);
                    
             for (int k=0; k<localBestAnt.getCamions().size();k++)
                  {
                   for(int l=0; l<localBestAnt.getCamions().get(k).getTournee_attribuees().size();l++)
                   {
                   localBestAnt.tournee_effecuee.add(localBestAnt.getCamions().get(k).getTournee_attribuees().get(l));
                   }
                   }
                 
                 // vider la liste des camions
                 localBestAnt.getCamions().clear();
               
                 localBestAnt.classer_tournees_dynamic(localBestAnt.tournee_effecuee);
                Recherche_locale_inter_intra_tournee_dynamic(localBestAnt.tournee_effecuee);
                //Recherche_locale_inter_intra_tournee_dynamic(localBestAnt.tournee_effecuee);
                //Recherche_locale_inter_intra_tournee_dynamic(localBestAnt.tournee_effecuee);
                 //Recherche_locale_inter_intra_tournee_dynamic_slice(localBestAnt.tournee_effecuee);
                 localBestAnt.setTemps_total_tournees(localBestAnt.calculer_temps_total_tournees_dynamic(localBestAnt.tournee_effecuee));
                  /*for(int e=0; e<localBestAnt.tournee_effecuee.size();e++)
                   {
                  localBestAnt.tour_effec.add(localBestAnt.tournee_effecuee.get(e));
                   }*/
                 
                   for (int d=0; d<this.getCamions().size();d++)
            {
              localBestAnt.getCamions().add(this.getCamions().get(d));
            }
                 localBestAnt.affecter_vehicule_dynamic();
                 localBestAnt.classer_camion_dynamic(localBestAnt.getCamions());
                 localBestAnt.get_overtime_maximal_dynamic();

                 localBestAnt.classer_tournes_dynamic_defenitif();

                // System.out.println("LTR après:  "+localBestAnt.getCamions().get(localBestAnt.getCamions().size()-1).calucler_temps_total_tournees_dynamic()/this.problem_dynamic.getMaxTemps_dynamic());
               
              this.update_pheromone_meuilleur_fourmi_apres_amelioration_dynamic(localBestAnt);
                 //  bests.add(localBestAnt);
                   
                    if (globalBestAnt == null) {
                  globalBestAnt = localBestAnt;
                  for(int e=0; e<localBestAnt.getCamions().size();e++)
                   {
                  globalBestAnt.getCamions().set(e,localBestAnt.getCamions().get(e).clone());
                  globalBestAnt.getCamions().get(e).setTemps_tournees_avant(localBestAnt.getCamions().get(e).getTemps_tournees_avant());

                   }
                  
               
                  //globalBestAnt.tour_effec = localBestAnt.tour_effec;
                 } 
                    else 
                    {
                 // if (localBestAnt.getCost_ant()<globalBestAnt.getCost_ant())
                  //  if (localBestAnt.getTemps_total_tournees()<globalBestAnt.getTemps_total_tournees())
                if ((/*localBestAnt.getTemps_total_tournees()+*/localBestAnt.get_overtime_maximal_dynamic())<=(/*globalBestAnt.getTemps_total_tournees()+*/globalBestAnt.get_overtime_maximal_dynamic()))

                        // if (localBestAnt.get_plus_long_voyage_dynamic()<=globalBestAnt.get_plus_long_voyage_dynamic())
                       //if (localBestAnt.get_depo_fic_dynamic_ant()<globalBestAnt.get_depo_fic_dynamic_ant())
                         {
                globalBestAnt = localBestAnt;
                for(int e=0; e<localBestAnt.getCamions().size();e++)
                   {
                  globalBestAnt.getCamions().set(e,localBestAnt.getCamions().get(e).clone());
                  globalBestAnt.getCamions().get(e).setTemps_tournees_avant(localBestAnt.getCamions().get(e).getTemps_tournees_avant());

                   }
                //globalBestAnt.tour_effec = localBestAnt.tour_effec;
               
                    }
                    }
                   
                  //System.out.println( "phéromone" + this.getPheromones(globalBestAnt.getCamions().get(0).getTournee_attribuees().get(0).getCustomers().get(0).getId(),0));   
           i++;
                                        }
                   
                   this.getCamions().clear(); 
                // System.out.println( " haaaaa le nbr de camions avant :" +this.getCamions().size());
                 this.setCamions(globalBestAnt.getCamions());
           
               this.setDEPO_FIC(this.get_depo_fic_dynamic());
               
               System.out.println( " solution global:");
               for (int k=0; k<globalBestAnt.getCamions().size();k++)
               {
                    //System.out.println( "tournées du Camion numéro " + globalBestAnt.getCamions().get(k).getIdcamion()+" :");
                    System.out.println( "tournées du Camion numéro " +(k+1)+" :");
                   for(int l=0; l<globalBestAnt.getCamions().get(k).getTournee_attribuees().size();l++)
                   {
                      // System.out.println( "tournée numéro " + globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getId_tour()+" :\t");
                       System.out.println( "tournée numéro " + (l+1));
                       if (globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getId_fictif()==0)
                       {
                        System.out.println( "dépot fictif : 0");
                       }
                       else { Depotfictif de;
                                de =  this.getProblem_dynamic().getdepotfictifById_dynamic(globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getId_fictif());
                                int from_client_fictif= de.getId_client_fictif();
                                 System.out.println( "dépot fictif : "+ from_client_fictif);
                            }
                      for (int m=0;m<globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().size();m++)
                      {
                       System.out.println( "\t," + globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().get(m).getId()+"\t");   
                      } 
                      //System.out.println( "\n"); 
                   }
               
               }
              //globalBestAnt.affecter_vehicule();
             
              // globalBestAnt.classer_camion_dynamic(globalBestAnt.getCamions());
              // this.problem_dynamic.setCamions_dynamic(globalBestAnt.getCamions());
               System.out.println("LTR:  "+((globalBestAnt.get_overtime_maximal_dynamic()+this.problem_dynamic.getMaxTemps_dynamic())/this.problem_dynamic.getMaxTemps_dynamic()));
                System.out.println("le plus long voyage:  "+(globalBestAnt.getCamions().get(globalBestAnt.getCamions().size()-1).calucler_temps_total_tournees_dynamic()));
               System.out.println("temps_totale_de_voyage:  "+globalBestAnt.getTemps_total_tournees());
               System.out.println("coût total :"+ (globalBestAnt.getTemps_total_tournees()+globalBestAnt.get_overtime_maximal_dynamic()));
               //  this.setTemps_total_voyage((globalBestAnt.getCamions().size()*this.time_slice));
               this.setTemps_total_dernier_voyage(globalBestAnt.getTemps_total_tournees());
               System.out.print( "dépot fictif: ");
               for (int m=0;m<this.getDEPO_FIC().size();m++)
                      {
                       System.out.print( ", " + this.getDEPO_FIC().get(m).getId_client_fictif() );   
                      } 
                System.out.println( "\tclients old: ");
               for (int m=0;m<this.getClients_old().size();m++)
                      {
                       System.out.print( ", " + this.getClients_old().get(m).getId() );   
                      }
               System.out.println( "kaaaaaaaaaaaaaayen overtime ");
               }
         }
               //globalBestAnt.classer_tournes_dynamic_defenitif();
               
           
     }
        
     
     
   
  /*    public void run_dynamic_defenitif() {
         this.bests.clear();
        
           int i = 0;1
           this.initialiser_pheromone_dynamic();
           //System.out.println ("nombre d'itération:"+iterationNumber);
           
               while (i < this.iterationNumber) {//globalBestAntIteration+this.conditionArret){
                  
                   this.solution.clear();
                  this.initialize_system_fourmis_dynamic(this.antNumber);
                   //System.out.println ("nombre d'itération:"+i);
                  int c = 0;
             
                   while (c < ants.size()) {
                   Ant ant = ants.get(c);
                   
                   ant.run_ant_dynamic();
                   this.update_pheromone_fourmi_dynamic(ant);
                   //this.update_pheromone_fourmi(ant);
                   //System.out.println ("nombre de fourmis:"+c);
                   
                    
                   solution.add(ant);
                   c++;
                                           }
                   
                   
                   
                   
                   // Ant localBestAnt=bestTour_LTR_dynamic(this.solution);
                   /// Ant localBestAnt=bestTour_distance_dynamic(this.solution);
                   //Ant localBestAnt=bestTour_OVTM_dynamic(this.solution);
                  Ant localBestAnt=bestTour_LTR_dynamic(this.solution);
                   // Ant localBestAnt=bestTour_distance_parcourru(this.solution);
                   //this.update_pheromone(solution);
                 // this.update_pheromone_meuilleur_fourmi(localBestAnt);
                 // Ant localBestAnt=bestTour_LTR(this.solution);
                //  System.out.println("iteration:" + i); //+ " solution local:" + localBestAnt.getCost_ant());
                  // System.out.println("LTR avant:  "+localBestAnt.getCamions().get(localBestAnt.getCamions().size()-1).calucler_temps_total_tournees_dynamic()/this.problem_dynamic.getMaxTemps_dynamic());
               //ArrayList<Tour> meuilleur_voyage=localBestAnt.tournee_effecuee;
                 for (int k=0; k<localBestAnt.getCamions().size();k++)
                  {
                   for(int l=0; l<localBestAnt.getCamions().get(k).getTournee_attribuees().size();l++)
                   {
                   localBestAnt.tournee_effecuee.add(localBestAnt.getCamions().get(k).getTournee_attribuees().get(l));
                   }
                   }
                 
                 // vider la liste des camions
                 localBestAnt.getCamions().clear();
               
                 localBestAnt.classer_tournees_dynamic(localBestAnt.tournee_effecuee);
                 Recherche_locale_inter_intra_tournee_dynamic(localBestAnt.tournee_effecuee);
                 Recherche_locale_inter_intra_tournee_dynamic(localBestAnt.tournee_effecuee);
                 //Recherche_locale_inter_intra_tournee_dynamic(localBestAnt.tournee_effecuee);
                 localBestAnt.setTemps_total_tournees(localBestAnt.calculer_temps_total_tournees_dynamic(localBestAnt.tournee_effecuee));
                
                   for (int d=0; d<this.getCamions().size();d++)
            {
              localBestAnt.getCamions().add(this.getCamions().get(d));
            }
                 localBestAnt.affecter_vehicule_dynamic();
                 localBestAnt.classer_camion_dynamic(localBestAnt.getCamions());
                 localBestAnt.get_overtime_maximal_dynamic();
                 localBestAnt.classer_tournes_dynamic_defenitif();
                // System.out.println("LTR après:  "+localBestAnt.getCamions().get(localBestAnt.getCamions().size()-1).calucler_temps_total_tournees_dynamic()/this.problem_dynamic.getMaxTemps_dynamic());
               
                   this.update_pheromone_meuilleur_fourmi_apres_amelioration_dynamic(localBestAnt);
                   //localBestAnt.setOvertime_maximal_ant(localBestAnt.get_overtime_maximal_dynamic());
                   
                    if (globalBestAnt == null) {
                  globalBestAnt = localBestAnt;
                  for(int e=0; e<localBestAnt.getCamions().size();e++)
                   {
                  globalBestAnt.getCamions().set(e,localBestAnt.getCamions().get(e).clone());
                  globalBestAnt.getCamions().get(e).setTemps_tournees_avant(localBestAnt.getCamions().get(e).getTemps_tournees_avant());

                   }
                 } 
                    else 
                    {
                 // if (localBestAnt.getCost_ant()<globalBestAnt.getCost_ant())
                     //if (localBestAnt.calculer_temps_total_tournees(localBestAnt.tournee_effecuee)<globalBestAnt.calculer_temps_total_tournees(globalBestAnt.tournee_effecuee))
                       //  if (localBestAnt.get_plus_long_voyage_dynamic()<=globalBestAnt.get_plus_long_voyage_dynamic())
                     //  if (localBestAnt.getTemps_total_tournees()<globalBestAnt.getTemps_total_tournees())
     if ((/*localBestAnt.getTemps_total_tournees()+localBestAnt.get_overtime_maximal_dynamic())<=(/*globalBestAnt.getTemps_total_tournees()+globalBestAnt.get_overtime_maximal_dynamic()))

            
                         {
                globalBestAnt = localBestAnt;
                for(int e=0; e<localBestAnt.getCamions().size();e++)
                   {
                  globalBestAnt.getCamions().set(e,localBestAnt.getCamions().get(e).clone());
                  globalBestAnt.getCamions().get(e).setTemps_tournees_avant(localBestAnt.getCamions().get(e).getTemps_tournees_avant());
                   }
               
                    }
                    }
                   
                  //System.out.println( "phéromone" + this.getPheromones(globalBestAnt.getCamions().get(0).getTournee_attribuees().get(0).getCustomers().get(0).getId(),0));   
           i++;
                                        }
              this.getCamions().clear(); 
                // System.out.println( " haaaaa le nbr de camions avant :" +this.getCamions().size());
                 this.setCamions(globalBestAnt.getCamions());
           
               
               System.out.println( " solution global:");
               for (int k=0; k<globalBestAnt.getCamions().size();k++)
               {
                    //System.out.println( "tournées du Camion numéro " + globalBestAnt.getCamions().get(k).getIdcamion()+" :");
                    System.out.println( "tournées du Camion numéro " +(k+1)+" :");
                   for(int l=0; l<globalBestAnt.getCamions().get(k).getTournee_attribuees().size();l++)
                   {
                      // System.out.println( "tournée numéro " + globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getId_tour()+" :\t");
                       System.out.println( "tournée numéro " + (l+1));
                       if (globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getId_fictif()==0)
                       {
                        System.out.println( "dépot fictif : 0");
                       }
                       else { Depotfictif de;
                                de =  this.getProblem_dynamic().getdepotfictifById_dynamic(globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getId_fictif());
                                int from_client_fictif= de.getId_client_fictif();
                                 System.out.println( "dépot fictif : "+ from_client_fictif);
                            }
                      for (int m=0;m<globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().size();m++)
                      {
                       System.out.println( "\t," + globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().get(m).getId()+"\t");   
                      } 
                      //System.out.println( "\n"); 
                   }
               
               }
              //globalBestAnt.affecter_vehicule();
             
              // globalBestAnt.classer_camion_dynamic(globalBestAnt.getCamions());
               this.problem_dynamic.setCamions_dynamic(globalBestAnt.getCamions());
              System.out.println("LTR:  "+((globalBestAnt.get_overtime_maximal_dynamic()+this.problem_dynamic.getMaxTemps_dynamic())/this.problem_dynamic.getMaxTemps_dynamic()));
              System.out.println("le plus long voyage:  "+(globalBestAnt.getCamions().get(globalBestAnt.getCamions().size()-1).calucler_temps_total_tournees_dynamic()));
               System.out.println("temps_totale_de_voyage:  "+globalBestAnt.getTemps_total_tournees());
     
             System.out.println("coût total :"+ (globalBestAnt.getTemps_total_tournees()+globalBestAnt.get_overtime_maximal_dynamic()));

              //  this.setTemps_total_voyage((globalBestAnt.getCamions().size()*this.time_slice));
            this.setTemps_total_dernier_voyage(globalBestAnt.getTemps_total_tournees());
            this.setOvertime_maximal(globalBestAnt.get_overtime_maximal_dynamic());
           
     }*/
        public void run_dynamic_defenitif() {
         if (Yarabbiyasser.test==1){
             
                  System.out.println( "haaaaaaaaaaaaaaaaaaa la valeur dyal test: " +Yarabbiyasser.test);
                   globalBestAnt=null;
                   int i=0;
                  this.initialiser_pheromone_dynamic();
           //System.out.println ("nombre d'itération:"+iterationNumber);
           
           
            this.problem_dynamic.setCoef_distance_parcourue(0);
           this.problem_dynamic.setCoef_overtime(1);
           
               while (i < this.iterationNumber) {//globalBestAntIteration+this.conditionArret){
                  
                   this.solution.clear();
                  this.initialize_system_fourmis_dynamic(this.antNumber);
                   //System.out.println ("nombre d'itération:"+i);
                  int c = 0;
             
                   while (c < ants.size()) {
                   Ant ant = ants.get(c);
                   
                   ant.run_ant_dynamic();
                   this.update_pheromone_fourmi_dynamic(ant);
                   //this.update_pheromone_fourmi(ant);
                   //System.out.println ("nombre de fourmis:"+c);
                   
                    
                   solution.add(ant);
                   c++;
                                           }
                   
                   
                      Ant localBestAnt=bestTour_LTR_dynamic(this.solution);
                    
             for (int k=0; k<localBestAnt.getCamions().size();k++)
                  {
                   for(int l=0; l<localBestAnt.getCamions().get(k).getTournee_attribuees().size();l++)
                   {
                   localBestAnt.tournee_effecuee.add(localBestAnt.getCamions().get(k).getTournee_attribuees().get(l));
                   }
                   }
                 
                 // vider la liste des camions
                 localBestAnt.getCamions().clear();
               
                 localBestAnt.classer_tournees_dynamic(localBestAnt.tournee_effecuee);
                Recherche_locale_inter_intra_tournee_dynamic(localBestAnt.tournee_effecuee);
                //Recherche_locale_inter_intra_tournee_dynamic(localBestAnt.tournee_effecuee);
                //Recherche_locale_inter_intra_tournee_dynamic(localBestAnt.tournee_effecuee);
                 //Recherche_locale_inter_intra_tournee_dynamic_slice(localBestAnt.tournee_effecuee);
                 localBestAnt.setTemps_total_tournees(localBestAnt.calculer_temps_total_tournees_dynamic(localBestAnt.tournee_effecuee));
                  /*for(int e=0; e<localBestAnt.tournee_effecuee.size();e++)
                   {
                  localBestAnt.tour_effec.add(localBestAnt.tournee_effecuee.get(e));
                   }*/
                 
                   for (int d=0; d<this.getCamions().size();d++)
            {
              localBestAnt.getCamions().add(this.getCamions().get(d));
            }
                 localBestAnt.affecter_vehicule_dynamic();
                 localBestAnt.classer_camion_dynamic(localBestAnt.getCamions());
                 localBestAnt.get_overtime_maximal_dynamic();

                 //localBestAnt.classer_tournes_dynamic_defenitif();

                // System.out.println("LTR après:  "+localBestAnt.getCamions().get(localBestAnt.getCamions().size()-1).calucler_temps_total_tournees_dynamic()/this.problem_dynamic.getMaxTemps_dynamic());
               
              this.update_pheromone_meuilleur_fourmi_apres_amelioration_dynamic(localBestAnt);
                 //  bests.add(localBestAnt);
                   
                    if (globalBestAnt == null) {
                  globalBestAnt = localBestAnt;
                  for(int e=0; e<localBestAnt.getCamions().size();e++)
                   {
                  globalBestAnt.getCamions().set(e,localBestAnt.getCamions().get(e).clone());
                  globalBestAnt.getCamions().get(e).setTemps_tournees_avant(localBestAnt.getCamions().get(e).getTemps_tournees_avant());

                   }
                  
               
                  //globalBestAnt.tour_effec = localBestAnt.tour_effec;
                 } 
                    else 
                    {
                 // if (localBestAnt.getCost_ant()<globalBestAnt.getCost_ant())
                  //  if (localBestAnt.getTemps_total_tournees()<globalBestAnt.getTemps_total_tournees())
                if ((/*localBestAnt.getTemps_total_tournees()+*/localBestAnt.get_overtime_maximal_dynamic())<=(/*globalBestAnt.getTemps_total_tournees()+*/globalBestAnt.get_overtime_maximal_dynamic()))

                        // if (localBestAnt.get_plus_long_voyage_dynamic()<=globalBestAnt.get_plus_long_voyage_dynamic())
                       //if (localBestAnt.get_depo_fic_dynamic_ant()<globalBestAnt.get_depo_fic_dynamic_ant())
                         {
                globalBestAnt = localBestAnt;
                for(int e=0; e<localBestAnt.getCamions().size();e++)
                   {
                  globalBestAnt.getCamions().set(e,localBestAnt.getCamions().get(e).clone());
                  globalBestAnt.getCamions().get(e).setTemps_tournees_avant(localBestAnt.getCamions().get(e).getTemps_tournees_avant());

                   }
                //globalBestAnt.tour_effec = localBestAnt.tour_effec;
               
                    }
                    }
                   
                  //System.out.println( "phéromone" + this.getPheromones(globalBestAnt.getCamions().get(0).getTournee_attribuees().get(0).getCustomers().get(0).getId(),0));   
           i++;
                                        }
                   
                   this.getCamions().clear(); 
                // System.out.println( " haaaaa le nbr de camions avant :" +this.getCamions().size());
                 this.setCamions(globalBestAnt.getCamions());
           
              // this.setDEPO_FIC(this.get_depo_fic_dynamic());
               
               System.out.println( " solution global:");
               for (int k=0; k<globalBestAnt.getCamions().size();k++)
               {
                    //System.out.println( "tournées du Camion numéro " + globalBestAnt.getCamions().get(k).getIdcamion()+" :");
                    System.out.println( "tournées du Camion numéro " +(k+1)+" :");
                   for(int l=0; l<globalBestAnt.getCamions().get(k).getTournee_attribuees().size();l++)
                   {
                      // System.out.println( "tournée numéro " + globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getId_tour()+" :\t");
                       System.out.println( "tournée numéro " + (l+1));
                       if (globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getId_fictif()==0)
                       {
                        System.out.println( "dépot fictif : 0");
                       }
                       else { Depotfictif de;
                                de =  this.getProblem_dynamic().getdepotfictifById_dynamic(globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getId_fictif());
                                int from_client_fictif= de.getId_client_fictif();
                                 System.out.println( "dépot fictif : "+ from_client_fictif);
                            }
                      for (int m=0;m<globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().size();m++)
                      {
                       System.out.println( "\t," + globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().get(m).getId()+"\t");   
                      } 
                      //System.out.println( "\n"); 
                   }
               
               }
              //globalBestAnt.affecter_vehicule();
             
              // globalBestAnt.classer_camion_dynamic(globalBestAnt.getCamions());
              // this.problem_dynamic.setCamions_dynamic(globalBestAnt.getCamions());
               System.out.println("LTR:  "+((globalBestAnt.get_overtime_maximal_dynamic()+this.problem_dynamic.getMaxTemps_dynamic())/this.problem_dynamic.getMaxTemps_dynamic()));
                System.out.println("le plus long voyage:  "+(globalBestAnt.getCamions().get(globalBestAnt.getCamions().size()-1).calucler_temps_total_tournees_dynamic()));
               System.out.println("temps_totale_de_voyage:  "+globalBestAnt.getTemps_total_tournees());
               System.out.println("coût total :"+ (globalBestAnt.getTemps_total_tournees()+globalBestAnt.get_overtime_maximal_dynamic()));
               //  this.setTemps_total_voyage((globalBestAnt.getCamions().size()*this.time_slice));
               this.setTemps_total_dernier_voyage(globalBestAnt.getTemps_total_tournees());
               
               System.out.println( "kaaaaaaaaaaaaaayen overtime ");
               
         }
         else {
         this.bests.clear();
        
           int i = 0;
           this.initialiser_pheromone_dynamic();
           //System.out.println ("nombre d'itération:"+iterationNumber);
           double over=this.problem_dynamic.getOvetime_dynamic();
           this.problem_dynamic.setOvetime_dynamic(0);
            this.problem_dynamic.setCoef_distance_parcourue(1);
           this.problem_dynamic.setCoef_overtime(0);
           
               while (i < this.iterationNumber) {//globalBestAntIteration+this.conditionArret){
                  
                   this.solution.clear();
                  this.initialize_system_fourmis_dynamic(this.antNumber);
                   //System.out.println ("nombre d'itération:"+i);
                  int c = 0;
             
                   while (c < ants.size()) {
                   Ant ant = ants.get(c);
                   
                   ant.run_ant_dynamic();
                   this.update_pheromone_fourmi_dynamic(ant);
                   //this.update_pheromone_fourmi(ant);
                   //System.out.println ("nombre de fourmis:"+c);
                   
                    
                   solution.add(ant);
                   c++;
                                           }
                   
                   
                   
                   
                        //Ant localBestAnt=bestTour_OVTM_dynamic(this.solution);
                     // Ant localBestAnt=bestTour_dynamic(this.solution);
                     Ant localBestAnt=bestTour_distance_dynamic(this.solution);
                    // Ant localBestAnt=bestTour_time_slice_dynamic(this.solution);
                   //Ant localBestAnt=bestTour_distance_dynamic(this.solution);
                    //Ant localBestAnt=bestTour_dynamic(this.solution);
                   //this.update_pheromone(solution);
                 // this.update_pheromone_meuilleur_fourmi(localBestAnt);
                 // Ant localBestAnt=bestTour_LTR(this.solution);
                //  System.out.println("iteration:" + i); //+ " solution local:" + localBestAnt.getCost_ant());
                  // System.out.println("LTR avant:  "+localBestAnt.getCamions().get(localBestAnt.getCamions().size()-1).calucler_temps_total_tournees_dynamic()/this.problem_dynamic.getMaxTemps_dynamic());
               //ArrayList<Tour> meuilleur_voyage=localBestAnt.tournee_effecuee;
              // System.out.println("tail camions: "+ localBestAnt.getCamions().size());
             for (int k=0; k<localBestAnt.getCamions().size();k++)
                  {
                   for(int l=0; l<localBestAnt.getCamions().get(k).getTournee_attribuees().size();l++)
                   {
                   localBestAnt.tournee_effecuee.add(localBestAnt.getCamions().get(k).getTournee_attribuees().get(l));
                   }
                   }
                 
                 // vider la liste des camions
                 localBestAnt.getCamions().clear();
               
                 localBestAnt.classer_tournees_dynamic(localBestAnt.tournee_effecuee);
                Recherche_locale_inter_intra_tournee_dynamic(localBestAnt.tournee_effecuee);
                //Recherche_locale_inter_intra_tournee_dynamic(localBestAnt.tournee_effecuee);
                //Recherche_locale_inter_intra_tournee_dynamic(localBestAnt.tournee_effecuee);
                 //Recherche_locale_inter_intra_tournee_dynamic_slice(localBestAnt.tournee_effecuee);
                 localBestAnt.setTemps_total_tournees(localBestAnt.calculer_temps_total_tournees_dynamic(localBestAnt.tournee_effecuee));
                  /*for(int e=0; e<localBestAnt.tournee_effecuee.size();e++)
                   {
                  localBestAnt.tour_effec.add(localBestAnt.tournee_effecuee.get(e));
                   }*/
                 
                   for (int d=0; d<this.getCamions().size();d++)
            {
              localBestAnt.getCamions().add(this.getCamions().get(d));
            }
                 localBestAnt.affecter_vehicule_dynamic();
                 localBestAnt.classer_camion_dynamic(localBestAnt.getCamions());
                 localBestAnt.get_overtime_maximal_dynamic();

                 localBestAnt.classer_tournes_dynamic_defenitif();

                // System.out.println("LTR après:  "+localBestAnt.getCamions().get(localBestAnt.getCamions().size()-1).calucler_temps_total_tournees_dynamic()/this.problem_dynamic.getMaxTemps_dynamic());
               
              this.update_pheromone_meuilleur_fourmi_apres_amelioration_dynamic(localBestAnt);
                 //  bests.add(localBestAnt);
                   
                    if (globalBestAnt == null) {
                  globalBestAnt = localBestAnt;
                  for(int e=0; e<localBestAnt.getCamions().size();e++)
                   {
                  globalBestAnt.getCamions().set(e,localBestAnt.getCamions().get(e).clone());
                  globalBestAnt.getCamions().get(e).setTemps_tournees_avant(localBestAnt.getCamions().get(e).getTemps_tournees_avant());

                   }
                  
               
                  //globalBestAnt.tour_effec = localBestAnt.tour_effec;
                 } 
                    else 
                    {
                 // if (localBestAnt.getCost_ant()<globalBestAnt.getCost_ant())
                  //  if (localBestAnt.getTemps_total_tournees()<globalBestAnt.getTemps_total_tournees())
                if ((localBestAnt.getTemps_total_tournees()/*+localBestAnt.get_overtime_maximal_dynamic()*/)<=(globalBestAnt.getTemps_total_tournees()/*+globalBestAnt.get_overtime_maximal_dynamic()*/))

                        // if (localBestAnt.get_plus_long_voyage_dynamic()<=globalBestAnt.get_plus_long_voyage_dynamic())
                       //if (localBestAnt.get_depo_fic_dynamic_ant()<globalBestAnt.get_depo_fic_dynamic_ant())
                         {
                globalBestAnt = localBestAnt;
                for(int e=0; e<localBestAnt.getCamions().size();e++)
                   {
                  globalBestAnt.getCamions().set(e,localBestAnt.getCamions().get(e).clone());
                  globalBestAnt.getCamions().get(e).setTemps_tournees_avant(localBestAnt.getCamions().get(e).getTemps_tournees_avant());

                   }
                //globalBestAnt.tour_effec = localBestAnt.tour_effec;
               
                    }
                    }
                   
                  //System.out.println( "phéromone" + this.getPheromones(globalBestAnt.getCamions().get(0).getTournee_attribuees().get(0).getCustomers().get(0).getId(),0));   
           i++;
                                        }
               if (globalBestAnt.get_overtime_maximal_dynamic()==0)
               {
                 this.getCamions().clear(); 
                // System.out.println( " haaaaa le nbr de camions avant :" +this.getCamions().size());
                 this.setCamions(globalBestAnt.getCamions());
           
               //this.setDEPO_FIC(this.get_depo_fic_dynamic());
               
               System.out.println( " solution global:");
               for (int k=0; k<globalBestAnt.getCamions().size();k++)
               {
                    //System.out.println( "tournées du Camion numéro " + globalBestAnt.getCamions().get(k).getIdcamion()+" :");
                    System.out.println( "tournées du Camion numéro " +(k+1)+" :");
                   for(int l=0; l<globalBestAnt.getCamions().get(k).getTournee_attribuees().size();l++)
                   {
                      // System.out.println( "tournée numéro " + globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getId_tour()+" :\t");
                       System.out.println( "tournée numéro " + (l+1));
                       if (globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getId_fictif()==0)
                       {
                        System.out.println( "dépot fictif : 0");
                       }
                       else { Depotfictif de;
                                de =  this.getProblem_dynamic().getdepotfictifById_dynamic(globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getId_fictif());
                                int from_client_fictif= de.getId_client_fictif();
                                 System.out.println( "dépot fictif : "+ from_client_fictif);
                            }
                      for (int m=0;m<globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().size();m++)
                      {
                       System.out.println( "\t," + globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().get(m).getId()+"\t");   
                      } 
                      //System.out.println( "\n"); 
                   }
               
               }
              //globalBestAnt.affecter_vehicule();
             
              // globalBestAnt.classer_camion_dynamic(globalBestAnt.getCamions());
              // this.problem_dynamic.setCamions_dynamic(globalBestAnt.getCamions());
               System.out.println("LTR:  "+((globalBestAnt.get_overtime_maximal_dynamic()+this.problem_dynamic.getMaxTemps_dynamic())/this.problem_dynamic.getMaxTemps_dynamic()));
                System.out.println("le plus long voyage:  "+(globalBestAnt.getCamions().get(globalBestAnt.getCamions().size()-1).calucler_temps_total_tournees_dynamic()));
               System.out.println("temps_totale_de_voyage:  "+globalBestAnt.getTemps_total_tournees());
               System.out.println("coût total :"+ (globalBestAnt.getTemps_total_tournees()+globalBestAnt.get_overtime_maximal_dynamic()));
               //  this.setTemps_total_voyage((globalBestAnt.getCamions().size()*this.time_slice));
               this.setTemps_total_dernier_voyage(globalBestAnt.getTemps_total_tournees());
              
                System.out.println( "makkaaaaaaaaaaaaaayenech overtime ");
               }
               else
               {
                   Yarabbiyasser.test=1;
                  System.out.println( "haaaaaaaaaaaaaaaaaaa la valeur dyal test: " +Yarabbiyasser.test);
                   globalBestAnt=null;
                   i=0;
                  this.initialiser_pheromone_dynamic_2();
           //System.out.println ("nombre d'itération:"+iterationNumber);
           
           this.problem_dynamic.setOvetime_dynamic(over);
            this.problem_dynamic.setCoef_distance_parcourue(0);
           this.problem_dynamic.setCoef_overtime(1);
           
               while (i < this.iterationNumber) {//globalBestAntIteration+this.conditionArret){
                  
                   this.solution.clear();
                  this.initialize_system_fourmis_dynamic(this.antNumber);
                   //System.out.println ("nombre d'itération:"+i);
                  int c = 0;
             
                   while (c < ants.size()) {
                   Ant ant = ants.get(c);
                   
                   ant.run_ant_dynamic();
                   this.update_pheromone_fourmi_dynamic(ant);
                   //this.update_pheromone_fourmi(ant);
                   //System.out.println ("nombre de fourmis:"+c);
                   
                    
                   solution.add(ant);
                   c++;
                                           }
                   
                   
                      Ant localBestAnt=bestTour_LTR_dynamic(this.solution);
                    
             for (int k=0; k<localBestAnt.getCamions().size();k++)
                  {
                   for(int l=0; l<localBestAnt.getCamions().get(k).getTournee_attribuees().size();l++)
                   {
                   localBestAnt.tournee_effecuee.add(localBestAnt.getCamions().get(k).getTournee_attribuees().get(l));
                   }
                   }
                 
                 // vider la liste des camions
                 localBestAnt.getCamions().clear();
               
                 localBestAnt.classer_tournees_dynamic(localBestAnt.tournee_effecuee);
                Recherche_locale_inter_intra_tournee_dynamic(localBestAnt.tournee_effecuee);
                //Recherche_locale_inter_intra_tournee_dynamic(localBestAnt.tournee_effecuee);
                //Recherche_locale_inter_intra_tournee_dynamic(localBestAnt.tournee_effecuee);
                 //Recherche_locale_inter_intra_tournee_dynamic_slice(localBestAnt.tournee_effecuee);
                 localBestAnt.setTemps_total_tournees(localBestAnt.calculer_temps_total_tournees_dynamic(localBestAnt.tournee_effecuee));
                  /*for(int e=0; e<localBestAnt.tournee_effecuee.size();e++)
                   {
                  localBestAnt.tour_effec.add(localBestAnt.tournee_effecuee.get(e));
                   }*/
                 
                   for (int d=0; d<this.getCamions().size();d++)
            {
              localBestAnt.getCamions().add(this.getCamions().get(d));
            }
                 localBestAnt.affecter_vehicule_dynamic();
                 localBestAnt.classer_camion_dynamic(localBestAnt.getCamions());
                 localBestAnt.get_overtime_maximal_dynamic();

                 localBestAnt.classer_tournes_dynamic_defenitif();

                // System.out.println("LTR après:  "+localBestAnt.getCamions().get(localBestAnt.getCamions().size()-1).calucler_temps_total_tournees_dynamic()/this.problem_dynamic.getMaxTemps_dynamic());
               
              this.update_pheromone_meuilleur_fourmi_apres_amelioration_dynamic(localBestAnt);
                 //  bests.add(localBestAnt);
                   
                    if (globalBestAnt == null) {
                  globalBestAnt = localBestAnt;
                  for(int e=0; e<localBestAnt.getCamions().size();e++)
                   {
                  globalBestAnt.getCamions().set(e,localBestAnt.getCamions().get(e).clone());
                  globalBestAnt.getCamions().get(e).setTemps_tournees_avant(localBestAnt.getCamions().get(e).getTemps_tournees_avant());

                   }
                  
               
                  //globalBestAnt.tour_effec = localBestAnt.tour_effec;
                 } 
                    else 
                    {
                 // if (localBestAnt.getCost_ant()<globalBestAnt.getCost_ant())
                  //  if (localBestAnt.getTemps_total_tournees()<globalBestAnt.getTemps_total_tournees())
                if ((/*localBestAnt.getTemps_total_tournees()+*/localBestAnt.get_overtime_maximal_dynamic())<=(/*globalBestAnt.getTemps_total_tournees()+*/globalBestAnt.get_overtime_maximal_dynamic()))

                        // if (localBestAnt.get_plus_long_voyage_dynamic()<=globalBestAnt.get_plus_long_voyage_dynamic())
                       //if (localBestAnt.get_depo_fic_dynamic_ant()<globalBestAnt.get_depo_fic_dynamic_ant())
                         {
                globalBestAnt = localBestAnt;
                for(int e=0; e<localBestAnt.getCamions().size();e++)
                   {
                  globalBestAnt.getCamions().set(e,localBestAnt.getCamions().get(e).clone());
                  globalBestAnt.getCamions().get(e).setTemps_tournees_avant(localBestAnt.getCamions().get(e).getTemps_tournees_avant());

                   }
                //globalBestAnt.tour_effec = localBestAnt.tour_effec;
               
                    }
                    }
                   
                  //System.out.println( "phéromone" + this.getPheromones(globalBestAnt.getCamions().get(0).getTournee_attribuees().get(0).getCustomers().get(0).getId(),0));   
           i++;
                                        }
                   
                   this.getCamions().clear(); 
                // System.out.println( " haaaaa le nbr de camions avant :" +this.getCamions().size());
                 this.setCamions(globalBestAnt.getCamions());
           
              
               
               System.out.println( " solution global:");
               for (int k=0; k<globalBestAnt.getCamions().size();k++)
               {
                    //System.out.println( "tournées du Camion numéro " + globalBestAnt.getCamions().get(k).getIdcamion()+" :");
                    System.out.println( "tournées du Camion numéro " +(k+1)+" :");
                   for(int l=0; l<globalBestAnt.getCamions().get(k).getTournee_attribuees().size();l++)
                   {
                      // System.out.println( "tournée numéro " + globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getId_tour()+" :\t");
                       System.out.println( "tournée numéro " + (l+1));
                       if (globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getId_fictif()==0)
                       {
                        System.out.println( "dépot fictif : 0");
                       }
                       else { Depotfictif de;
                                de =  this.getProblem_dynamic().getdepotfictifById_dynamic(globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getId_fictif());
                                int from_client_fictif= de.getId_client_fictif();
                                 System.out.println( "dépot fictif : "+ from_client_fictif);
                            }
                      for (int m=0;m<globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().size();m++)
                      {
                       System.out.println( "\t," + globalBestAnt.getCamions().get(k).getTournee_attribuees().get(l).getCustomers().get(m).getId()+"\t");   
                      } 
                      //System.out.println( "\n"); 
                   }
               
               }
              //globalBestAnt.affecter_vehicule();
             
              // globalBestAnt.classer_camion_dynamic(globalBestAnt.getCamions());
              // this.problem_dynamic.setCamions_dynamic(globalBestAnt.getCamions());
               System.out.println("LTR:  "+((globalBestAnt.get_overtime_maximal_dynamic()+this.problem_dynamic.getMaxTemps_dynamic())/this.problem_dynamic.getMaxTemps_dynamic()));
                System.out.println("le plus long voyage:  "+(globalBestAnt.getCamions().get(globalBestAnt.getCamions().size()-1).calucler_temps_total_tournees_dynamic()));
               System.out.println("temps_totale_de_voyage:  "+globalBestAnt.getTemps_total_tournees());
               System.out.println("coût total :"+ (globalBestAnt.getTemps_total_tournees()+globalBestAnt.get_overtime_maximal_dynamic()));
               //  this.setTemps_total_voyage((globalBestAnt.getCamions().size()*this.time_slice));
               this.setTemps_total_dernier_voyage(globalBestAnt.getTemps_total_tournees());
               
               System.out.println( "kaaaaaaaaaaaaaayen overtime ");
               }
         }
               //globalBestAnt.classer_tournes_dynamic_defenitif();
               
           
     }
   
    public void best_MoveCustomerFromTourToTour(Tour tour1, Tour tour2) {
       double quantityTour2 = tour2.getCurrent_capacity();
        if((tour1!=tour2)&&(tour1.getLowerDemand()<=quantityTour2))
        {
         
        double cout=0;
        double cout_best=0;
        int k_best=-1;
       /* TourGroup newTourGroup;
        double thisCost = this.getObj1();
        if(problem.getObjective1().equals("LTR") && thisCost<=problem.getInitialMaxTemps()){
            thisCost = this.getObj2();
        }*/
        for(int k=0;k<tour1.getCustomers().size();k++){ 
            //if(tour1.getCustomer(k).getDemande()+quantityTour2<=problem.getMaxCapacity()){
                cout = checkMoveCustomerFromTourToTour(tour1, tour2, k);
                if(cout<cout_best){
                    cout_best=cout;
                    k_best=k;
                }
                //if(cout<cout_best) cout_best=cout;
            //}
                    }
        if (cout_best<0)
        {
            Movecustomer_from_tour_to_tour(tour1, tour2, k_best);
        }
        }
       
    }
    public void Movecustomer_from_tour_to_tour(Tour t1,Tour t2,int k){
   Customer c1 = t1.getCustomers().get(k);
    t1.getCustomers().remove(k);
    t1.set_capacity_initial(t1.getCurrent_capacity()+c1.getDemande());
        /*Tour newTour2 = new Tour(newTrGp);
        for (int a = 0; a < tour2.size(); a++) {
            newTour2.addCustomer(tour2.getCustomer(a));
        }*/
        //System.out.println("newTour2 before adding "+newTour2);
        t2.addCustomerInBestPositionWithoutCheckConstraint(c1);
        t2.set_capacity_initial(t2.getCurrent_capacity()-c1.getDemande());
   
        if( t2.getCustomers().size()>=4)
                   {
                      
                              t2.checkDecroisement();
                      
                               t2.two_opt_best();
                      
                   }
                   else if (t2.getCustomers().size()>=2)
                   {
                               t2.two_opt_best();
                   } 
                  
    }
     
    public double checkMoveCustomerFromTourToTour(Tour tour1, Tour tour2, int positionTour1) {
        //********Insertion du client dans position1 de tour1 dans la tour 2 à la position2
        double cout_avant= tour1.calculer_temps_tournee()+tour2.calculer_temps_tournee();
        Customer c1 = tour1.getCustomers().get(positionTour1);
        //double newQuantity2 = tour2.getCurrent_capacity()+ c1.getDemande();
        if (c1.getDemande() > tour2.getCurrent_capacity()) {
            return 0;
        }
        //créer un nouveau tourGroup
        // newTrGp = new TourGroup(problem);
        //Construre les nouvelles tournées
        Tour newTour1 = tour1.clone();//tour without positionTour1
        /*for (int a = 0; a < tour1.size(); a++) {
            if (a != positionTour1) {
                newTour1.addCustomer(tour1.getCustomer(a));
            }
        }*/
       
        newTour1.getCustomers().remove(positionTour1);
        /*Tour newTour2 = new Tour(newTrGp);
        for (int a = 0; a < tour2.size(); a++) {
            newTour2.addCustomer(tour2.getCustomer(a));
        }*/
        //System.out.println("newTour2 before adding "+newTour2);
        //if(newTour2.addCustomerInBestPosition(c1)==false)
        Tour newTour2 = tour2.clone();
        if(newTour2.addCustomerInBestPositionWithoutCheckConstraint(c1)==false)
            return 0;
        if( newTour2.getCustomers().size()>=4)
                   {
                      
                              newTour2.checkDecroisement();
                      
                               newTour2.two_opt_best();
                      
                   }
                   else if (newTour2.getCustomers().size()>=2)
                   { 
                               newTour2.two_opt_best();
                     
                   }
        //System.out.println("newTour2  after adding"+newTour2);
       // newTour2.decroisement_iterative();  
        if(newTour2.calculer_temps_tournee()>(this.problem.getMaxTemps()+ this.problem.getOvertimepermis()))
            return 0;
        else {
            double cout_apres=newTour1.calculer_temps_tournee()+newTour2.calculer_temps_tournee();
            if (cout_apres< cout_avant) return (cout_apres-cout_avant);
            else return 0;
            
            
        }
        
        }
  public double checkExchangeCustomers(Tour tour1, Tour tour2, int positionTour1, int positionTour2) {
        //********Insertion du client dans position1 de tour1 dans la tour 2 à la position2
        //créer un nouveau tourGroup
        double cout_avant= tour1.calculer_temps_tournee()+tour2.calculer_temps_tournee();
        Customer c1 = tour1.getCustomers().get(positionTour1);
        Customer c2 = tour2.getCustomers().get(positionTour2);
        double newQuantity1 = tour1.getCurrent_capacity() //quantit� distribu�e lors de la tourn�e
                + c1.getDemande()
                - c2.getDemande();  //quantit� demand�e du client jPrime de la tourn�e j
        if (newQuantity1 <0) 
            return 0;
        // => nouvelle quantit� de la tourn�e i
        double newQuantity2 = tour2.getCurrent_capacity()
                - c1.getDemande()
                + c2.getDemande();
        if (newQuantity2 <0) 
            return 0;
        //********Construire les nouvelles tourxnées
        //System.out.println("trg"+this);
       // TourGroup newTrGp = new TourGroup(problem);
        //newTrGp.remove(positionTour1);newTrGp.remove(positionTour2);
        //System.out.println("new trg"+newTrGp);
        
        Tour newTour1 = tour1.clone();//new Tour(newTrGp);//tour without positionTour1
        //newTrGp.addTour(newTour1);
        newTour1.getCustomers().remove(c1);
        /*for (Customer c : tour1.getCustomers()) {
            if (c != c1) 
                newTour1.addCustomer(c);
            }*/
        
        if(newTour1.addCustomerInBestPositionWithoutCheckConstraint(c2)==false){
        //if(newTour1.addCustomerInBestPosition(c2)==false){
            return 0;
        }
        //System.out.println("after add customer "+newTour1);

       // newTour1.decroisement_iterative();
        if( newTour1.getCustomers().size()>=4)
                   {
                      
                              newTour1.checkDecroisement();
                      
                               newTour1.two_opt_best();
                      
                   }
                   else if (newTour1.getCustomers().size()>=2)
                   { 
                               newTour1.two_opt_best();
                     
                   }
        //System.out.println("after decross "+newTour1);
        if(newTour1.calculer_temps_tournee()>(problem.getMaxTemps()+problem.getOvertimepermis()))
            return 0;
        //System.out.println("newTour1 after "+newTour1);
        
        Tour newTour2 = tour2.clone();//new Tour(newTrGp);
        //newTrGp.addTour(newTour2);
        newTour2.getCustomers().remove(c2);
        /*for (Customer c : tour2.getCustomers()) {
            if (c != 1c2) 
                newTour2.addCustomer(c);
        }*/
        //if(newTour2.addCustomerInBestPosition(c1)==false){
        if(newTour2.addCustomerInBestPositionWithoutCheckConstraint(c1)==false){
            return 0;
        }
        //System.out.println("after add customer 2"+newTour2);
        //newTour2.decroisement_iterative();
         if( newTour2.getCustomers().size()>=4)
                   {
                      
                              newTour2.checkDecroisement();
                      
                               newTour2.two_opt_best();
                      
                   }
                   else if (newTour2.getCustomers().size()>=2)
                   { 
                               newTour2.two_opt_best();
                     
                   }
        //System.out.println("after decross 2"+newTour2);
        if(newTour2.calculer_temps_tournee()>(problem.getMaxTemps()+problem.getOvertimepermis()))
            return 0;
        double cout_apres=newTour1.calculer_temps_tournee()+newTour2.calculer_temps_tournee();
            if (cout_apres< cout_avant) return (cout_apres-cout_avant);
            else return 0;
  }
  
    public int[] checkBestExchangeCustomers(Tour tour1, Tour tour2) {
        if(tour1==tour2)
            return null;
        int valeur_a_retourner[]=new int[2];
        double diff=0 ; double maxDiff =0;
        //TourGroup newTourGroup, bestNewTourGroup=null;
        for(int k=0;k<tour1.getCustomers().size();k++){
            for(int m=0;m<tour2.getCustomers().size();m++){
                diff = checkExchangeCustomers(tour1, tour2, k, m);
                    if(diff<maxDiff){
                        
                        
                            maxDiff = diff; 
                            valeur_a_retourner[0]=k;
                            valeur_a_retourner[1]=m;
                            
                        }
                    }
            }
    if (maxDiff<0) 
        return valeur_a_retourner;
    else return null;
    }
     public  void BestExchangeCustomers(Tour tour1, Tour tour2) {
         if (checkBestExchangeCustomers(tour1, tour2)!=null)
         {
        int position1=checkBestExchangeCustomers(tour1, tour2)[0];
        int position2=checkBestExchangeCustomers(tour1, tour2)[1];
        Exchange_customers(tour1, tour2, position1, position2);
        //Customer c1=tour1.
        //tour1.set_capacity_initial(tour1.getCurrent_capacity()-);
         }
    }
    public void Exchange_customers(Tour t1,Tour t2, int positionTour1,int positionTour2){
        
        Customer c1 = t1.getCustomers().get(positionTour1);
        Customer c2 = t2.getCustomers().get(positionTour2);
        t1.getCustomers().remove(c1);
        
        
        t1.addCustomerInBestPositionWithoutCheckConstraint(c2);
        t1.set_capacity_initial(t1.getCurrent_capacity()-c2.getDemande()+c1.getDemande());
        
      
        if( t1.getCustomers().size()>=4)
                   {
                      
                              t1.checkDecroisement();
                      
                               t1.two_opt_best();
                      
                   }
                   else if (t1.getCustomers().size()>=2)
                   { 
                               t1.two_opt_best();
                     
                   }
        
        
        t2.getCustomers().remove(c2);
       
        t2.addCustomerInBestPositionWithoutCheckConstraint(c1);
        t2.set_capacity_initial(t2.getCurrent_capacity()-c1.getDemande()+c2.getDemande());
            
     
        
         if( t2.getCustomers().size()>=4)
                   {
                      
                             t2.checkDecroisement();
                      
                              t2.two_opt_best();
                      
                   }
                   else if (t2.getCustomers().size()>=2)
                   { 
                               t2.two_opt_best();
                     
                   }
        
        
        
        
}
public void Recherche_local_intra_tournee(Tour t) 
{
   if( t.getCustomers().size()>=4)
                   {
                      
                              t.checkDecroisement();
                      
                               t.two_opt_best();
                      
                   }
                   else if (t.getCustomers().size()>=2)
                   { 
                               t.two_opt_best();
                     
                   } 
}
public void Recherche_locale_inter_intra_tournee(ArrayList<Tour> Tours)
{
    for (int k=0; k<Tours.size()-1;k++)
    {
        Recherche_local_intra_tournee(Tours.get(k));
    }
    
    for (int i=0; i<Tours.size()-1;i++)
          {
              for(int j=i+1;j<Tours.size();j++)
              {
                  BestExchangeCustomers(Tours.get(i), Tours.get(j));
                  best_MoveCustomerFromTourToTour(Tours.get(i), Tours.get(j));
                  BestExchangeCustomers(Tours.get(i), Tours.get(j));
                  best_MoveCustomerFromTourToTour(Tours.get(i), Tours.get(j));
                  
                  //System.out.println("taille de la tournee "+i+ "est"+Tours.get(i).getCustomers().size());
                  //if (Tours.get(i).getCustomers().isEmpty())
                 // { System.out.println("taille de la tournee "+i+ "est"+Tours.get(i).getCustomers().size());}
                  
                  
              }
        
          }
    for (int l=0; l<Tours.size()-1;l++)
    {
    if (Tours.get(l).getCustomers().isEmpty())
                  { //System.out.println("taille de la tournee avant suppression "+l+ "est"+Tours.get(l).getCustomers().size());
                      Tours.remove(l);
                     //System.out.println("taille de la tournee après remove"+l+ "est"+Tours.get(l).getCustomers().size());
                     l=l-1;
                  }
    
    }

    
}

   
    
   
  

  public void best_MoveCustomerFromTourToTour_dynamic(Tour tour1, Tour tour2) {
       double quantityTour2 = tour2.getCurrent_capacity();
        if((tour1!=tour2)&&(tour1.getLowerDemand()<=quantityTour2))
        {
         
        double cout=0;
        double cout_best=0;
        int k_best=-1;
       /* TourGroup newTourGroup;
        double thisCost = this.getObj1();
        if(problem.getObjective1().equals("LTR") && thisCost<=problem.getInitialMaxTemps()){
            thisCost = this.getObj2();
        }*/
        for(int k=0;k<tour1.getCustomers().size();k++){ 
            //if(tour1.getCustomer(k).getDemande()+quantityTour2<=problem.getMaxCapacity()){
                cout = checkMoveCustomerFromTourToTour_dynamic(tour1, tour2, k);
                if(cout<cout_best){
                    cout_best=cout;
                    k_best=k;
                }
                //if(cout<cout_best) cout_best=cout;
            //}
                    }
        if (cout_best<0)
        {
            Movecustomer_from_tour_to_tour_dynamic(tour1, tour2, k_best);
        }
        }
       
    }
  public void Movecustomer_from_tour_to_tour_dynamic(Tour t1,Tour t2,int k){
   Customer c1 = t1.getCustomers().get(k);
    t1.getCustomers().remove(k);
    t1.set_capacity_initial(t1.getCurrent_capacity()+c1.getDemande());
        /*Tour newTour2 = new Tour(newTrGp);
        for (int a = 0; a < tour2.size(); a++) {
            newTour2.addCustomer(tour2.getCustomer(a));
        }*/
        //System.out.println("newTour2 before adding "+newTour2);
        t2.addCustomerInBestPositionWithoutCheckConstraint_dynamic(c1);
         t2.set_capacity_initial(t2.getCurrent_capacity()-c1.getDemande());
   
        if( t2.getCustomers().size()>=4)
                   {
                      
                              t2.checkDecroisement_dynamic();
                      
                               t2.two_opt_best_dynamic();
                      
                   }
                   else if (t2.getCustomers().size()>=2)
                   {
                               t2.two_opt_best_dynamic();
                   } 
                  
    }
     
  public double checkMoveCustomerFromTourToTour_dynamic(Tour tour1, Tour tour2, int positionTour1) {
        //********Insertion du client dans position1 de tour1 dans la tour 2 à la position2
        double cout_avant= tour1.calculer_temps_tournee_dynamic()+tour2.calculer_temps_tournee_dynamic();
        Customer c1 = tour1.getCustomers().get(positionTour1);
        //double newQuantity2 = tour2.getCurrent_capacity()- c1.getDemande();
        if (c1.getDemande() > tour2.getCurrent_capacity()) {
            return 0;
        }
        //créer un nouveau tourGroup
        // newTrGp = new TourGroup(problem);
        //Construre les nouvelles tournées
        Tour newTour1 = tour1.clone();//tour without positionTour1
        newTour1.id_fictif=tour1.id_fictif;
        /*for (int a = 0; a < tour1.size(); a++) {
            if (a != positionTour1) {
                newTour1.addCustomer(tour1.getCustomer(a));
            }
        }*/
       
        newTour1.getCustomers().remove(positionTour1);
        /*Tour newTour2 = new Tour(newTrGp);
        for (int a = 0; a < tour2.size(); a++) {
            newTour2.addCustomer(tour2.getCustomer(a));
        }*/
        //System.out.println("newTour2 before adding "+newTour2);
        //if(newTour2.addCustomerInBestPosition(c1)==false)
        Tour newTour2 = tour2.clone();
        newTour2.id_fictif=tour2.id_fictif;
        if(newTour2.addCustomerInBestPositionWithoutCheckConstraint_dynamic(c1)==false)
            return 0;
        if( newTour2.getCustomers().size()>=4)
                   {
                      
                              newTour2.checkDecroisement_dynamic();
                      
                               newTour2.two_opt_best_dynamic();
                      
                   }
                   else if (newTour2.getCustomers().size()>=2)
                   { 
                               newTour2.two_opt_best_dynamic();
                     
                   }
        //System.out.println("newTour2  after adding"+newTour2);
       // newTour2.decroisement_iterative();  
        if(newTour2.calculer_temps_tournee_dynamic()>(this.problem_dynamic.getMaxTemps_dynamic()+ this.problem_dynamic.getOvetime_dynamic()))
            return 0;
        else {
           /* System.out.println("id fictif recherche locale tour1 :" +newTour1.id_fictif);
              System.out.println("size recherche locale tour1 :" +newTour1.getCustomers().size());
            System.out.println("id fictif recherche locale tour2 :" +newTour2.id_fictif);
            System.out.println("size recherche locale tour2 :" +newTour2.getCustomers().size());*/
            double cout_apres=newTour1.calculer_temps_tournee_dynamic()+
                    newTour2.calculer_temps_tournee_dynamic();
            if (cout_apres< cout_avant) return (cout_apres-cout_avant);
            else return 0;
            
            
        }
        
        }
  public double checkExchangeCustomers_dynamic(Tour tour1, Tour tour2, int positionTour1, int positionTour2) {
        //********Insertion du client dans position1 de tour1 dans la tour 2 à la position2
        //créer un nouveau tourGroup
        double cout_avant= tour1.calculer_temps_tournee_dynamic()+tour2.calculer_temps_tournee_dynamic();
        Customer c1 = tour1.getCustomers().get(positionTour1);
        Customer c2 = tour2.getCustomers().get(positionTour2);
        double newQuantity1 = tour1.getCurrent_capacity() //quantit� distribu�e lors de la tourn�e
                + c1.getDemande()
                - c2.getDemande();  //quantit� demand�e du client jPrime de la tourn�e j
        if (newQuantity1 <0) 
            return 0;
        // => nouvelle quantit� de la tourn�e i
        double newQuantity2 = tour2.getCurrent_capacity()
                - c1.getDemande()
                + c2.getDemande();
        if (newQuantity2 < 0) 
            return 0;
        //********Construire les nouvelles tourxnées
        //System.out.println("trg"+this);
       // TourGroup newTrGp = new TourGroup(problem);
        //newTrGp.remove(positionTour1);newTrGp.remove(positionTour2);
        //System.out.println("new trg"+newTrGp);
        
        Tour newTour1 = tour1.clone();//new Tour(newTrGp);//tour without positionTour1
        newTour1.id_fictif=tour1.id_fictif;
        //newTrGp.addTour(newTour1);
        newTour1.getCustomers().remove(c1);
        /*for (Customer c : tour1.getCustomers()) {
            if (c != c1) 
                newTour1.addCustomer(c);
            }*/
        
        if(newTour1.addCustomerInBestPositionWithoutCheckConstraint_dynamic(c2)==false){
        //if(newTour1.addCustomerInBestPosition(c2)==false){
            return 0;
        }
        //System.out.println("after add customer "+newTour1);

       // newTour1.decroisement_iterative();
        if( newTour1.getCustomers().size()>=4)
                   {
                      
                              newTour1.checkDecroisement_dynamic();
                      
                               newTour1.two_opt_best_dynamic();
                      
                   }
                   else if (newTour1.getCustomers().size()>=2)
                   { 
                               newTour1.two_opt_best_dynamic();
                     
                   }
        //System.out.println("after decross "+newTour1);
        if(newTour1.calculer_temps_tournee_dynamic()>problem_dynamic.getMaxTemps_dynamic()+problem_dynamic.getOvetime_dynamic())
            return 0;
        //System.out.println("newTour1 after "+newTour1);
        
        Tour newTour2 = tour2.clone();//new Tour(newTrGp);
         newTour2.id_fictif=tour2.id_fictif;        
//newTrGp.addTour(newTour2);
        newTour2.getCustomers().remove(c2);
        /*for (Customer c : tour2.getCustomers()) {
            if (c != 1c2) 
                newTour2.addCustomer(c);
        }*/
        //if(newTour2.addCustomerInBestPosition(c1)==false){
        if(newTour2.addCustomerInBestPositionWithoutCheckConstraint_dynamic(c1)==false){
            return 0;
        }
        //System.out.println("after add customer 2"+newTour2);
        //newTour2.decroisement_iterative();
         if( newTour2.getCustomers().size()>=4)
                   {
                      
                              newTour2.checkDecroisement_dynamic();
                      
                               newTour2.two_opt_best_dynamic();
                      
                   }
                   else if (newTour2.getCustomers().size()>=2)
                   { 
                               newTour2.two_opt_best_dynamic();
                     
                   }
        //System.out.println("after decross 2"+newTour2);
        if(newTour2.calculer_temps_tournee_dynamic()>problem_dynamic.getMaxTemps_dynamic()+problem_dynamic.getOvetime_dynamic())
            return 0;
        double cout_apres=newTour1.calculer_temps_tournee_dynamic()+newTour2.calculer_temps_tournee_dynamic();
            if (cout_apres< cout_avant) return (cout_apres-cout_avant);
            else return 0;
  }
  
    public int[] checkBestExchangeCustomers_dynamic(Tour tour1, Tour tour2) {
        if(tour1==tour2)
            return null;
        int valeur_a_retourner[]=new int[2];
        double diff=0 ; double maxDiff =0;
        //TourGroup newTourGroup, bestNewTourGroup=null;
        for(int k=0;k<tour1.getCustomers().size();k++){
            for(int m=0;m<tour2.getCustomers().size();m++){
                diff = checkExchangeCustomers_dynamic(tour1, tour2, k, m);
                    if(diff<maxDiff){
                        
                        
                            maxDiff = diff; 
                            valeur_a_retourner[0]=k;
                            valeur_a_retourner[1]=m;
                            
                        }
                    }
            }
    if (maxDiff<0) 
        return valeur_a_retourner;
    else return null;
    }
     public  void BestExchangeCustomers_dynamic(Tour tour1, Tour tour2) {
         if (checkBestExchangeCustomers_dynamic(tour1, tour2)!=null)
         {
        int position1=checkBestExchangeCustomers_dynamic(tour1, tour2)[0];
        int position2=checkBestExchangeCustomers_dynamic(tour1, tour2)[1];
        Exchange_customers_dynamic(tour1, tour2, position1, position2);
        //Customer c1=tour1.
        //tour1.set_capacity_initial(tour1.getCurrent_capacity()-);
         }
    }
    public void Exchange_customers_dynamic(Tour t1,Tour t2, int positionTour1,int positionTour2){
        
        Customer c1 = t1.getCustomers().get(positionTour1);
        Customer c2 = t2.getCustomers().get(positionTour2);
        t1.getCustomers().remove(c1);
        
        
        t1.addCustomerInBestPositionWithoutCheckConstraint_dynamic(c2);
        t1.set_capacity_initial(t1.getCurrent_capacity()+c1.getDemande()-c2.getDemande());
        
      
        if( t1.getCustomers().size()>=4)
                   {
                      
                              t1.checkDecroisement_dynamic();
                      
                               t1.two_opt_best_dynamic();
                      
                   }
                   else if (t1.getCustomers().size()>=2)
                   { 
                               t1.two_opt_best_dynamic();
                     
                   }
        
        
        t2.getCustomers().remove(c2);
       
        t2.addCustomerInBestPositionWithoutCheckConstraint_dynamic(c1);
        t2.set_capacity_initial(t2.getCurrent_capacity()-c1.getDemande()+c2.getDemande());
            
     
        
         if( t2.getCustomers().size()>=4)
                   {
                      
                             t2.checkDecroisement_dynamic();
                      
                              t2.two_opt_best_dynamic();
                      
                   }
                   else if (t2.getCustomers().size()>=2)
                   { 
                               t2.two_opt_best_dynamic();
                     
                   }
        
        
        
        
}
public void Recherche_local_intra_tournee_dynamic(Tour t) 
{
   if( t.getCustomers().size()>=4)
                   {
                      
                              t.checkDecroisement_dynamic();
                      
                               t.two_opt_best_dynamic();
                      
                   }
                   else if (t.getCustomers().size()>=2)
                   { 
                               t.two_opt_best_dynamic();
                     
                   } 
}
public void Recherche_locale_inter_intra_tournee_dynamic(ArrayList<Tour> Tours)
{
    for (int k=0; k<Tours.size()-1;k++)
    {
        Recherche_local_intra_tournee_dynamic(Tours.get(k));
    }
    
    for (int i=0; i<Tours.size()-1;i++)
          {
              for(int j=i+1;j<Tours.size();j++)
              {
                
               
                  BestExchangeCustomers_dynamic(Tours.get(i), Tours.get(j));
                                                   
                  best_MoveCustomerFromTourToTour_dynamic(Tours.get(i), Tours.get(j));
                  
                
                  BestExchangeCustomers_dynamic(Tours.get(i), Tours.get(j));
                 
                  best_MoveCustomerFromTourToTour_dynamic(Tours.get(i), Tours.get(j)); 
                  
                   
               
                 // }
                 
                           
                  //System.out.println("taille de la tournee "+i+ "est"+Tours.get(i).getCustomers().size());
                  //if (Tours.get(i).getCustomers().isEmpty())
                 // { System.out.println("taille de la tournee "+i+ "est"+Tours.get(i).getCustomers().size());}
                  
                  
              }
        
          }
    for (int l=0; l<Tours.size()-1;l++)
    {
    if ((Tours.get(l).getCustomers().isEmpty())&& (Tours.get(l).getId_fictif()==0))
                  { //System.out.println("taille de la tournee avant suppression "+l+ "est"+Tours.get(l).getCustomers().size());
                      Tours.remove(l);
                     //System.out.println("taille de la tournee après remove"+l+ "est"+Tours.get(l).getCustomers().size());
                     l=l-1;
                  }
    
    }
              /*  for (int i=0; i<Tours.size()-1;i++)
          {
              for(int j=i+1;j<Tours.size();j++)
              {
                  BestExchangeCustomers_dynamic(Tours.get(i), Tours.get(j));
                  best_MoveCustomerFromTourToTour_dynamic(Tours.get(i), Tours.get(j));
                
                  
                  
              }
        
          }*/
    
}


public ArrayList<Depotfictif> get_depo_fic ()
{
    ArrayList<Depotfictif> depo_fics = new ArrayList<Depotfictif> () ;
     //System.out.println("camions : "+ this.getGlobalBestAnt().getCamions().size());
     this.clients_old.clear();
     this.setTemps_total_voyage(0);
     int b=1;
    for(int k=1 ;k<=this.getGlobalBestAnt().getCamions().size(); k++)
    {
        Camion c = this.getGlobalBestAnt().getCamions().get(k-1);
        double sum_temps_tour=0;
        int i=0;
        if (c.calucler_temps_total_tournees()<=this.getTime_slice())
        {
            this.setTemps_total_voyage(c.calucler_temps_total_tournees());
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
        int id_cl_fic=c.getTournee_attribuees().get(i-1).getCustomers().get(j).getId();
       
        for (int l=j+1; l<c.getTournee_attribuees().get(i-1).getCustomers().size();l++)
        {
            this.clients_old.add(c.getTournee_attribuees().get(i-1).getCustomers().get(l));
           // System.out.println("id client à ajouter "+ c.getTournee_attribuees().get(i-1).getCustomers().get(l).getId());
        }
        for (int m=i;m<c.getTournee_attribuees().size();m++)
        {
            for (int n=0;n<c.getTournee_attribuees().get(m).getCustomers().size();n++)
            {
               this.clients_old.add(c.getTournee_attribuees().get(m).getCustomers().get(n));  
            }
        }
         c.setTemps_tournees_avant(c.getTemps_tournees_avant()+ sum_temps_tour);
         c.setId_fictif_final(id_cl_fic);  
        depo_fics.add(new     Depotfictif(id_cl_fic,cap_cl_fic, b));
        c.setDepot_fictif_final(depo_fics.get(b-1));
        c.setSum_temps_tour(sum_temps_tour-this.getTime_slice());
       this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour);
        b++;
        //System.out.println("id depot fictif à ajouter "+id_cl_fic); 
        
        }
    }
    }
    return depo_fics;
}

public ArrayList<Depotfictif> get_depo_fic_dynamic ()
{
    ArrayList<Depotfictif> depo_fics = new ArrayList<Depotfictif> () ;
    this.clients_old.clear();
    this.setTemps_total_voyage(0);
    int b=1;
    for(int k=1 ;k<=this.getGlobalBestAnt().getCamions().size(); k++)
    {
        Camion c = this.getGlobalBestAnt().getCamions().get(k-1);
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
        int id_cl_fic=c.getTournee_attribuees().get(i-1).getCustomers().get(j).getId();
       
        for (int l=j+1; l<c.getTournee_attribuees().get(i-1).getCustomers().size();l++)
        {
            this.clients_old.add(c.getTournee_attribuees().get(i-1).getCustomers().get(l));
            //
           //System.out.println("haa les clients old  5"+ c.getTournee_attribuees().get(i-1).getCustomers().get(l).getId());
        }
        for (int m=i;m<c.getTournee_attribuees().size();m++)
        {
            for (int n=0;n<c.getTournee_attribuees().get(m).getCustomers().size();n++)
            {
               this.clients_old.add(c.getTournee_attribuees().get(m).getCustomers().get(n)); 
              // System.out.println("haa les clients old  5"+ c.getTournee_attribuees().get(m).getCustomers().get(n).getId());
            }
        }
            
        depo_fics.add(new     Depotfictif(id_cl_fic,cap_cl_fic, b));
        this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour-sum);
        c.setTemps_tournees_avant(c.getTemps_tournees_avant()+ sum_temps_tour-sum);
        c.setId_fictif_final(id_cl_fic);
        c.setDepot_fictif_final(depo_fics.get(b-1));
        c.setSum_temps_tour(sum_temps_tour-this.getTime_slice());
       /*  System.out.println("haaa le camion : "+c.getIdcamion());
             System.out.println("haaa temps total tournées : "+c.getTemps_tournees_avant());
              System.out.println("haaa sum temps tour : "+c.getSum_temps_tour());*/
        
        b++;
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
        int id_cl_fic=c.getTournee_attribuees().get(i-1).getCustomers().get(j).getId();
       
        for (int l=j+1; l<c.getTournee_attribuees().get(i-1).getCustomers().size();l++)
        {
            this.clients_old.add(c.getTournee_attribuees().get(i-1).getCustomers().get(l));
          // System.out.println("haa les clients old 6"+ c.getTournee_attribuees().get(i-1).getCustomers().get(l).getId());
        }
        for (int m=i;m<c.getTournee_attribuees().size();m++)
        {
            for (int n=0;n<c.getTournee_attribuees().get(m).getCustomers().size();n++)
            {
               this.clients_old.add(c.getTournee_attribuees().get(m).getCustomers().get(n));  
            //   System.out.println("haaa client old  6: "+c.getTournee_attribuees().get(m).getCustomers().get(n).getId());
            }
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
/*public ArrayList<Depotfictif> get_depo_fic (){
   return this.getDEPO_FIC();
}
public ArrayList<Depotfictif> get_depo_fic_dynamic  (){
     return this.getDEPO_FIC();
    
}

public ArrayList<Depotfictif> get_depo_fic_tour (ArrayList<Tour> ts)
{
    ArrayList<Depotfictif> depo_fics = new ArrayList<Depotfictif> () ;
     //System.out.println("camions : "+ this.getGlobalBestAnt().getCamions().size());
     this.clients_old.clear();
     this.setTemps_total_voyage(0);
     int b=1;
    for(Tour t: ts)
    {
        
        double sum_temps_tour=0;
         int j=0;
       
        double cap_cl_fic=this.problem.getCapacitycamion();
           
        sum_temps_tour= sum_temps_tour+ this.getProblem().gettimes(0, t.getCustomers().get(0).getId())+this.getTemps_service();
         cap_cl_fic=cap_cl_fic-t.getCustomers().get(0).getDemande();
        while (sum_temps_tour< this.getTime_slice()&&(t.getCustomers().size()>(j+1)))
        {
            j++;
            sum_temps_tour=sum_temps_tour+this.getProblem().gettimes(t.getCustomers().get(j-1).getId(),t.getCustomers().get(j).getId())+this.getTemps_service();
            cap_cl_fic=cap_cl_fic-t.getCustomers().get(j).getDemande();
           
             
        }
        
        if (j==(t.getCustomers().size()-1))
        {
      int id_cl_fic=t.getCustomers().get(j).getId();
       depo_fics.add(new     Depotfictif(id_cl_fic,cap_cl_fic, b));
       this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour);
        b++;
        }
        else
        {
            for (int l=j+1; l<t.getCustomers().size();l++)
        {
            this.clients_old.add(t.getCustomers().get(l));
        }
       int id_cl_fic=t.getCustomers().get(j).getId();
       depo_fics.add(new     Depotfictif(id_cl_fic,cap_cl_fic, b));
       this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour);
        b++;
           // System.out.println("id client à ajouter "+ c.getTournee_attribuees().get(i-1).getCustomers().get(l).getId());
        }
        }
              
              
        
    return depo_fics;
}

public ArrayList<Depotfictif> get_depo_fic_dynamic_tour (ArrayList<Tour> ts)
{
    ArrayList<Depotfictif> depo_fics = new ArrayList<Depotfictif> () ;
    this.clients_old.clear();
    this.setTemps_total_voyage(0);
    int b=1;
     for(Tour t: ts)
    {
      double sum_temps_tour=0;
     // Depotfictif d;
        
         
        //d=this.getProblem_dynamic().getdepotfictifById_dynamic(t.getId_fictif());
        //double cap_cl_fic=d.getCapacité_restante();
        if (t.getId_fictif()==0)
            {
               
         int j=0;
        double cap_cl_fic=this.getProblem_dynamic().getCapacitycamion_dynamic();
        
        sum_temps_tour= sum_temps_tour+ this.getProblem_dynamic().gettimes_dynamic(0, t.getCustomers().get(0).getId())+this.getTemps_service();
          cap_cl_fic=cap_cl_fic-t.getCustomers().get(0).getDemande();
        while ((sum_temps_tour< this.getTime_slice())&&(t.getCustomers().size()>(j+1)))
        {
            j++;
            sum_temps_tour=sum_temps_tour+this.getProblem_dynamic().gettimes_dynamic(t.getCustomers().get(j-1).getId(),t.getCustomers().get(j).getId())+this.getTemps_service();
            cap_cl_fic=cap_cl_fic-t.getCustomers().get(j).getDemande();
           
             
        }
        
            if (j==(t.getCustomers().size()-1))
        {
      int id_cl_fic=t.getCustomers().get(j).getId();
       depo_fics.add(new     Depotfictif(id_cl_fic,cap_cl_fic, b));
       this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour);
        b++;
        }
        else
        {
            for (int l=j+1; l<t.getCustomers().size();l++)
        {
            this.clients_old.add(t.getCustomers().get(l));
        }
       int id_cl_fic=t.getCustomers().get(j).getId();
       depo_fics.add(new     Depotfictif(id_cl_fic,cap_cl_fic, b));
       this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour);
        b++;
           // System.out.println("id client à ajouter "+ c.getTournee_attribuees().get(i-1).getCustomers().get(l).getId());
        }
            }
            else
            
          {
            int j=0;   
         Depotfictif d;
               //System.out.println("ooooooooooooo:   "+c.getTournee_attribuees().get(i-1).getId_fictif());
         d=this.getProblem_dynamic().getdepotfictifById_dynamic(t.getId_fictif());
         double cap_cl_fic=d.getCapacité_restante();

        int from_client_fictif= d.getId_client_fictif();
              if(t.getCustomers().size()> 0)
            {
          

       
        sum_temps_tour= sum_temps_tour+ this.getProblem_dynamic().gettimes_dynamic(from_client_fictif, t.getCustomers().get(0).getId())+this.getTemps_service();
          cap_cl_fic=cap_cl_fic-t.getCustomers().get(0).getDemande();
        while ((sum_temps_tour< this.getTime_slice())&&(t.getCustomers().size()>(j+1)) )
        {
            j++;
            sum_temps_tour=sum_temps_tour+this.getProblem_dynamic().gettimes_dynamic(t.getCustomers().get(j-1).getId(),t.getCustomers().get(j).getId())+this.getTemps_service();
            cap_cl_fic=cap_cl_fic-t.getCustomers().get(j).getDemande();
           
                
        }
        int id_cl_fic=t.getCustomers().get(j).getId();
       
        for (int l=j+1; l<t.getCustomers().size();l++)
        {
            this.clients_old.add(t.getCustomers().get(l));
           // System.out.println("id client à ajouter_dynamic "+ c.getTournee_attribuees().get(i-1).getCustomers().get(l).getId());
        }
              
            
       depo_fics.add(new     Depotfictif(id_cl_fic,cap_cl_fic, b));
        this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour);
        b++;
        }
            else {this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour); 
              depo_fics.add(new     Depotfictif(d.getId_client_fictif(),d.getCapacité_restante(), b));
              b++;}   
            }
        }
        
        
        
          
    return depo_fics;
}
*/

      
}
