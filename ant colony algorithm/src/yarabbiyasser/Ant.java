/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yarabbiyasser;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author asus
 */
public class Ant {
    public int idant;
    public Tour currentTour;
    private AntSystem antSystem; // on donne à chaque fourmi les données du problème et la connaissance collective
    public ArrayList<Tour> tournee_effecuee;
    public ArrayList<Camion> camions=new ArrayList<Camion> ();
    public ArrayList<Integer> visitedCities= new ArrayList<Integer>(); // toutes les villes visitées par la fourmi
    public ArrayList<Integer> citiesStillToVisit= new ArrayList<Integer>();
    public ArrayList<Integer> visitedCities_dynamic= new ArrayList<Integer>();
    public ArrayList<Integer> depotfictif_visite= new ArrayList<Integer>();
    public ArrayList<Integer> depotfictif_non_visite= new ArrayList<Integer>();
    public ArrayList<Integer> citiesStillToVisit_dynamic= new ArrayList<Integer>(); //toutes les villes encore à visiter
    public Customer currentposition;
    public VRPS problem;
    public VRPD problem_dynamic;// int state;
     public ArrayList<Tour> tour_effec = new ArrayList<Tour>(); 
     
    int currentDestination; // seconde extrémité de l'arc actuellement parcouru
    boolean solution_complete=false;
    double temps_total_tournees;
    double distance_total_tournees;
    private double[][] pheromones_ant;
    private double[][] pheromones_ant_dynamic;
    private double Cost_ant=0;
    private double temps_total_time_slice=0;
    
    int state=2;
     double overtime_maximal_ant=0;// si state ==0 la fourmis se trouve au depot central si state== 1:
    //la fourmis se trouve chez un client normal, si state ==2 la fourmis se trouve chez un depot fictif

    public double getDistance_total_tournees() {
        return distance_total_tournees;
    }

    public void setDistance_total_tournees(double distance_total_tournees) {
        this.distance_total_tournees = distance_total_tournees;
    }

   /* public double getOvertime_maximal_ant() {
        return overtime_maximal_ant;
    }

    public void setOvertime_maximal_ant(double overtime_maximal_ant) {
        this.overtime_maximal_ant = overtime_maximal_ant;
    }*/

    public ArrayList<Tour> getTour_effec() {
        return tour_effec;
    }

    public void setTour_effec(ArrayList<Tour> tour_effec) {
        this.tour_effec = tour_effec;
    }

    public double getTemps_total_time_slice() {
        return temps_total_time_slice;
    }

    public void setTemps_total_time_slice(double temps_total_time_slice) {
        this.temps_total_time_slice = temps_total_time_slice;
    }
    
     public Ant(AntSystem antSystem, int idant) {
         
        this.setAntSystem(antSystem);
      //  this.setCamions(antSystem.getCamions());
        this.setProblem(antSystem.getProblem());
        this.setIdant(idant);
        
        visitedCities = new ArrayList<Integer>();
        citiesStillToVisit = new ArrayList<Integer>();
        for (int i = 0; i <this.getProblem().getNbCities(); i++) {
            //c=problem.getCustomerById(i);
            citiesStillToVisit.add(this.getProblem().getCustomers().get(i).getId());
            
        }
        this.setPheromones_ant(new double[this.antSystem.VRPT.clients_total.size() + 1][this.antSystem.VRPT.clients_total.size() + 1]) ;
        //currentCamion = problem.camions.get(0);
      // this.state = 2;
        //tourGroup = new TourGroup(problem);
    }
     public Ant(AntSystem antSystem, int idant,VRPD prob) {
        this.setAntSystem(antSystem);
        // this.setCamions(antSystem.getCamions());
        //this.setProblem(antSystem.getProblem());
        this.setProblem_dynamic(prob);
        this.setIdant(idant);
        
        visitedCities_dynamic = new ArrayList<Integer>();
        citiesStillToVisit_dynamic = new ArrayList<Integer>();
        for (int i = 0; i < this.getProblem_dynamic().getCustomers_dynamic().size(); i++) {
            //c=problem.getCustomerById(i);
            citiesStillToVisit_dynamic.add(this.getProblem_dynamic().getCustomers_dynamic().get(i).getId());
            
        }
        for (int i = 1; i <= this.getProblem_dynamic().nombre_depot_fictifs; i++) {
            //c=problem.getCustomerById(i);
            depotfictif_non_visite.add(i);
            
        }
        this.setPheromones_ant_dynamic(new double[this.antSystem.VRPT.clients_total.size()+1][this.antSystem.VRPT.clients_total.size()+1]) ;
        //currentCamion = problem.camions.get(0);
      // this.state = 2;
        //tourGroup = new TourGroup(problem);
    }


    public ArrayList<Integer> getDepotfictif_non_visite() {
        return depotfictif_non_visite;
    }

    public void setDepotfictif_non_visite(ArrayList<Integer> depotfictif_non_visite) {
        this.depotfictif_non_visite = depotfictif_non_visite;
    }
    

    public ArrayList<Integer> getVisitedCities_dynamic() {
        return visitedCities_dynamic;
    }

    public void setVisitedCities_dynamic(ArrayList<Integer> visitedCities_dynamic) {
        this.visitedCities_dynamic = visitedCities_dynamic;
    }

    public ArrayList<Integer> getDepotfictif_visite() {
        return depotfictif_visite;
    }

    public void setDepotfictif_visite(ArrayList<Integer> depotfictif_visite) {
        this.depotfictif_visite = depotfictif_visite;
    }

    public ArrayList<Integer> getCitiesStillToVisit_dynamic() {
        return citiesStillToVisit_dynamic;
    }

    public void setCitiesStillToVisit_dynamic(ArrayList<Integer> citiesStillToVisit_dynamic) {
        this.citiesStillToVisit_dynamic = citiesStillToVisit_dynamic;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    
        public VRPD getProblem_dynamic() {
        return problem_dynamic;
    }

    public void setProblem_dynamic(VRPD problem_dynamic) {
        this.problem_dynamic = problem_dynamic;
    }

    public double[][] getPheromones_ant_dynamic() {
        return pheromones_ant_dynamic;
    }

    public void setPheromones_ant_dynamic(double[][] pheromones_ant_dynamic) {
        this.pheromones_ant_dynamic = pheromones_ant_dynamic;
    }
    public int getIdant() {
        return idant;
    }

    public void setIdant(int idant) {
        this.idant = idant;
    }

    public Tour getCurrentTour() {
        return currentTour;
    }

    public void setCurrentTour(Tour currentTour) {
        this.currentTour = currentTour;
    }

    public AntSystem getAntSystem() {
        return antSystem;
    }

    public void setAntSystem(AntSystem antSystem) {
        this.antSystem = antSystem;
    }

    public ArrayList<Tour> getTournee_effecuee() {
        return tournee_effecuee;
    }

    public void setTournee_effecuee(ArrayList<Tour> tournee_effecuee) {
        this.tournee_effecuee = tournee_effecuee;
    }

    public ArrayList<Camion> getCamions() {
        return camions;
    }

    public void setCamions(ArrayList<Camion> camions) {
        this.camions = camions;
    }

    public ArrayList<Integer> getVisitedCities() {
        return visitedCities;
    }

    public void setVisitedCities(ArrayList<Integer> visitedCities) {
        this.visitedCities = visitedCities;
    }

    public ArrayList<Integer> getCitiesStillToVisit() {
        return citiesStillToVisit;
    }

    public void setCitiesStillToVisit(ArrayList<Integer> citiesStillToVisit) {
        this.citiesStillToVisit = citiesStillToVisit;
    }

    public Customer getCurrentposition() {
        return currentposition;
    }

    public void setCurrentposition(Customer currentposition) {
        this.currentposition = currentposition;
    }

    public VRPS getProblem() {
        return problem;
    }

    public void setProblem(VRPS problem) {
        this.problem = problem;
    }

    public int getCurrentDestination() {
        return currentDestination;
    }

    public void setCurrentDestination(int currentDestination) {
        this.currentDestination = currentDestination;
    }

    public boolean isSolution_complete() {
        return solution_complete;
    }

    public void setSolution_complete(boolean solution_complete) {
        this.solution_complete = solution_complete;
    }
   
    public double getCost_ant() {
        return Cost_ant;
    }

    public void setCost_ant(double Cost_ant) {
        this.Cost_ant = Cost_ant;
    }

    public double getPheromones_ant(int i,int j) {
        return pheromones_ant[i][j];
    }
    public double getPheromones_ant_dynamic(int i,int j) {
        return pheromones_ant_dynamic[i][j];
    }

    public void setPheromones_ant(double[][] pheromones_ant) {
        this.pheromones_ant = pheromones_ant;
    }

    public double getTemps_total_tournees() {
        return temps_total_tournees;
    }

    public void setTemps_total_tournees(double temps_total_tournees) {
        this.temps_total_tournees = temps_total_tournees;
    }
    
    public void initialiser_pheromone_ant(){
     for (int i=0; i<=this.antSystem.VRPT.clients_total.size();i++)
     {

         for (int j=0; j<=this.antSystem.VRPT.clients_total.size();j++)
         {
             this.setPheromones_ant(i, j, 0);
         
         }
             
     }
      //  System.out.println("Initialisisation términée");
 }
    public void initialiser_pheromone_ant_dynamic(){
     for (int i=0; i<=this.antSystem.VRPT.getClients_total().size();i++)
     {

         for (int j=0; j<=this.antSystem.VRPT.getClients_total().size();j++)
         {
             this.setPheromones_ant_dynamic(i, j, 0);
         
         }
             
     }
      //  System.out.println("Initialisisation términée");
 }
    public void update_pheromone_ant(){
     for (int i=0; i<=this.antSystem.VRPT.getClients_total().size();i++)
     {

         for (int j=0; j<=this.antSystem.VRPT.getClients_total().size();j++)
         {
           
         if(this.pheromones_ant[i][j]>0)
         {
             this.setPheromones_ant(i, j, 1/(this.get_overtime_maximal()+1));
         
         }
             
          }
      }
    }
    
public void update_pheromone_ant_dynamic(){
     for (int i=0; i<=this.antSystem.VRPT.getClients_total().size();i++)
     {

         for (int j=0; j<=this.antSystem.VRPT.getClients_total().size();j++)
         {
           
         if(this.pheromones_ant_dynamic[i][j]>0)
         {
             this.setPheromones_ant_dynamic(i, j, 1/(this.get_overtime_maximal_dynamic()+1));
         
         }
             
          }
      }
    }
public double get_overtime_total(){
       //classer_camion(this.getCamions()); 
       double OVm=0;
       for (int i=0;i<this.getCamions().size();i++)
       {
        OVm=OVm+Math.max(0,(this.getCamions().get(i).calucler_temps_total_tournees()+-this.problem.getMaxTemps()));
       }
       //double Lv=camions.get(camions.size()-1).calucler_temps_total_tournees();
       return OVm;
   }
public double get_overtime_total_dynamic(){
    double OVm=0;
       for (int i=0;i<this.getCamions().size();i++)
       {
        OVm=OVm+Math.max(0,(this.getCamions().get(i).calucler_temps_total_tournees_dynamic()+this.getCamions().get(i).getTemps_tournees_avant()-this.problem_dynamic.getMaxTemps_dynamic()));
       }
       
       return OVm;
   }
public double get_plus_long_voyage(){
       classer_camion(camions);
       double Lv=camions.get(camions.size()-1).calucler_temps_total_tournees();
       return Lv;
   }
public double get_plus_long_voyage_dynamic(){
       classer_camion_dynamic(camions);
       double Lv=camions.get(camions.size()-1).calucler_temps_total_tournees_dynamic();
       return Lv;
   }
  
     public double getOvertime(int i,int j) {
         
       double temps_total_tournees=0;
       for (int k=0; k< tournee_effecuee.size();k++)
         
        { 
            temps_total_tournees= temps_total_tournees+this.tournee_effecuee.get(k).calculer_temps_tournee();
        }
       double OTj=Math.max(0,(this.antSystem.getVRPT().gettimes(i, j)+temps_total_tournees)-(this.problem.getMaxTemps()*this.problem.getNombrecamions()));
         
     return OTj; 
    }
    public double getOvertime_actuelle(){
        double OTi;
        double temps_total_tournees=0;
       for (int k=0; k< tournee_effecuee.size();k++)
         
        { 
            temps_total_tournees= temps_total_tournees+this.tournee_effecuee.get(k).calculer_temps_tournee();
        }
       OTi=Math.max(0,(temps_total_tournees)-(this.problem.getMaxTemps()*this.problem.getNombrecamions()));
         
     return OTi; 
    
        
    }
    public void setPheromones_ant(int i, int j, double v) {
        pheromones_ant[i][j] = v;
        pheromones_ant[j][i] = pheromones_ant[i][j];
    }
     public void setPheromones_ant_dynamic(int i, int j, double v) {
        pheromones_ant_dynamic[i][j] = v;
        pheromones_ant_dynamic[j][i] = pheromones_ant_dynamic[i][j];
    }
    
   int initializeant() { // détermination du prochain n#ud à atteindre
        
                //Tour currentTour;
               
                int dest;
                Customer c;
                initialiser_pheromone_ant();
         
               // this.visitedCities.clear();
//Initialize ants
                    // chaque fourmi est positionné initialement sur un client différent 
                    if (this.idant % (problem.nbCities) == 0) {
                        /*System.out.println("nb cities  :  "+ problem.nbCities);
                        System.out.println("idant  :  "+ idant);
                        System.out.println("client 1  :  "+ problem.getCustomers().get(0).getId());*/
                        dest = problem.nbCities+problem.getCustomers().get(0).getId()-1;
                    } else {
                       /* System.out.println("nb cities  :  "+ problem.nbCities);
                        System.out.println("idant  :  "+ idant);
                        System.out.println("client 1  :  "+ problem.getCustomers().get(0).getId());*/
                        //dest = problem.nbCities+problem.getCustomers().get(0).getId()-1;
                        dest = this.idant % (problem.nbCities)+problem.getCustomers().get(0).getId()-1;
                        //System.out.println("dest :  "+ this.idant % (problem.nbCities));
                    }
                
                c=problem.getCustomerById(dest);
                visitedCities.add(dest);
                this.currentposition = c;
                currentDestination = dest;
              this.currentTour = new Tour(this,1,this.problem.getCapacitycamion());
               this.currentTour.addCustomer_tour(c);
               this.tournee_effecuee=new ArrayList<Tour>();
               //System.out.println("nombre de tournees effectuees"+tournee_effecuee.size());
               this.setPheromones_ant(dest,0 , 1);
               //System.out.println("fourmis initialisé"+dest);
               this.citiesStillToVisit.remove(citiesStillToVisit.indexOf(dest));
            // System.out.println("fourmis initialisé"+dest);
                return dest;
               
               
            }
   int initializeant_dynamic() { // détermination du prochain n#ud à atteindre
        
                //Tour currentTour;
               
                int dest;
              // Depotfictif d;
                initialiser_pheromone_ant_dynamic();
                
                if (this.getProblem_dynamic().getNombre_depot_fictifs()==0)
                {
                    //System.out.println("Nombre dépot fictif1: "+this.getProblem_dynamic().getNombre_depot_fictifs());
                 dest=0; 
                  this.setState(0);
                }
         
               else
                {
                 /* System.out.println("Nombre dépot fictif2: "+this.getProblem_dynamic().getNombre_depot_fictifs());
                  System.out.println("Nombre dépot fictif3: "+this.getProblem_dynamic().getDepots_fictif().size());
                   System.out.println("id dépot fictif44444444: "+this.getProblem_dynamic().getDepots_fictif().get(0).getId_depot_fictif());
*/
                  // chaque fourmi est positionné initialement sur un client différent 
                    if (this.idant % (this.getProblem_dynamic().getNombre_depot_fictifs()) == 0) {
                        dest = this.getProblem_dynamic().getNombre_depot_fictifs();
                    } else {
                        dest = this.idant % (this.getProblem_dynamic().getNombre_depot_fictifs());
                    }
                //d=problem_dynamic.getdepotfictifById_dynamic(dest);
                depotfictif_visite.add(dest);
                depotfictif_non_visite.remove(depotfictif_non_visite.indexOf(dest));
                
                 this.setState(2);
                }
                
                //this.currentposition = c;
               currentDestination = dest;
              
               
               this.tournee_effecuee=new ArrayList<Tour>();
               //System.out.println("nombre de tournees effectuees"+tournee_effecuee.size());
               //this.setPheromones_ant(dest,0 , 1);
              // this.citiesStillToVisit.remove(citiesStillToVisit.indexOf(dest));
            // System.out.println("fourmis initialisé"+dest);
               
                
                return dest;
               
               
            }
    private int find (ArrayList<Double>  vec1, ArrayList<Integer>  vec2, double val)
  {  
      
      int j=-1;
        if(vec2.size()==1){j=vec2.get(0);}
      else
      {
              for (int i =1; i<=vec1.size()-1;i++)
              {
                  if ((vec1.get(i-1)<=val) && (val<= vec1.get(i))) 
                  {
                  j= vec2.get(i-1);}
              }
             /* System.out.println("probabilité 1    "+ vec1.get(0));
              System.out.println("probabilité milieu    "+ vec1.get(1));
              System.out.println("probabilité 2    "+ vec1.get(vec1.size()-1));*/
      }
        
              
     return j;        
  }
     public int avoir_maximum_liste_double(ArrayList<Double> prob){
         double mavaleur = 0;
         int indice=0;

    for (int i = 0; i < prob.size(); i++) {
         if (prob.get(i) > mavaleur)
               {
                 mavaleur = prob.get(i);
                 indice=i;
                         }
					     }

return indice;
     }
   
  int get_next_dest (int from){
       Customer client_a_ajouter;
       boolean haveToReturn = false;
       int j=0; 
       double sum=0;
       double visibilite1;
       double pi;
       double q;
       int next_client=0;
       ArrayList<Double>   sums = new ArrayList<Double>();
       ArrayList<Double>   probabilite = new ArrayList<Double>();
       sums.add(0.0);
       ArrayList<Integer> ville_able_to_visite=new ArrayList<Integer>();
       if (from==0){
           //this.tournee_effecuee.add(currentTour);
           this.currentTour=new Tour(this,this.tournee_effecuee.size(),this.problem.capacitycamion);
          //  System.out.println("hana dkhalet"+ tournee_effecuee.size());
           
                   }
       
       for(Integer i: this.citiesStillToVisit)    
       {    
      Customer c = this.problem.getCustomerById(i);
           //System.out.println("yarabbiyasser.Ant.get_next_dest()");
       
           
      if(((this.currentTour.calculer_temps_tournee()+this.problem.gettimes(from, i)+ this.problem.gettimes (i,0)+ this.antSystem.getTemps_service())<= (this.problem.getMaxTemps()+this.problem.getOvertimepermis()))&&(this.currentTour.getCurrent_capacity()>=c.getDemande() ))
      {
           
          ville_able_to_visite.add(i);
        double tij= this.antSystem.getPheromones(from, i);
         if (problem.getDistances(from, i)==0)
        {
        tij=1;    
        }
        if (problem.getDistances(from, i)==0)
        {
        visibilite1=100000;    
        }
        else
        {
        visibilite1= 1/problem.getDistances(from, i);
        }
        pi=Math.pow(tij,this.antSystem.alpha)*Math.pow(visibilite1,this.antSystem.beta1);
        probabilite.add(pi);
          //System.out.println("distance"+problem.getDistances(from, i) +"   from:  "+from + "i  : "+i);
         
       sum=sum+pi;
       
       sums.add(sum);
        j++;
      }     
        }
       //System.out.println("tournée courante"+ this.currentTour.getCustomers().size());
       if (ville_able_to_visite.isEmpty()) { 
       haveToReturn = true ;
       this.currentTour.setTournee_terminee(haveToReturn);
       this.currentTour.calculer_temps_tournee();
       this.tournee_effecuee.add(currentTour);
       this.setPheromones_ant(from,0,1);
       
    // this.antSystem.setPheromones(from, 0, ((1-this.antSystem.getEvaporation())*this.antSystem.getPheromones(from, 0)+this.antSystem.getEvaporation()*this.antSystem.getConstantPheromones()));
       return -1;}
       else{
           q=Math.random();
           if (q<=this.getAntSystem().getQ0())
           {
             int indice=avoir_maximum_liste_double(probabilite);
             next_client=ville_able_to_visite.get(indice);
           }
           else
           {
           for (int k=0;k<sums.size();k++)
           {
               sums.set(k, sums.get(k)/sum);
           }
       
        
        next_client=find (sums,ville_able_to_visite,Math.random());
           }
        //System.out.println("find"+find(sums,ville_able_to_visite,Math.random()));
        //System.out.println("ville  able to visite"+ville_able_to_visite.size());
        //System.out.println("sums"+sums.size());
        
       client_a_ajouter=this.problem.getCustomerById(next_client);
        
       haveToReturn = false;
        this.visitedCities.add(next_client);
        this.citiesStillToVisit.remove(citiesStillToVisit.indexOf(next_client));
        this.currentposition = client_a_ajouter;
        currentDestination = next_client;
         this.currentTour.addCustomer_tour(client_a_ajouter);
         this.currentTour.setCurrent_capacity(client_a_ajouter.getDemande());
         //System.out.println("capacité courant"+ currentTour.getCurrent_capacity());
         this.currentTour.calculer_temps_tournee();
         //System.out.println("capacité courant"+ currentTour.getCurrent_capacity());
         this.setPheromones_ant(from,next_client,1);
       // this.antSystem.setPheromones(from, next_client, ((1-this.antSystem.getEvaporation())*this.antSystem.getPheromones(from, next_client)+this.antSystem.getEvaporation()*this.antSystem.getConstantPheromones()));
       return next_client;
      
       }
    
   }
   int get_next_dest_dynamic(int from){
       Depotfictif d;
       Customer client_a_ajouter;
       boolean haveToReturn = false;
       int j=0; 
       double sum=0;
       double visibilite1;
       double pi;
       double q;
       ArrayList<Double>   sums = new ArrayList<Double>();
       ArrayList<Double>   probabilite = new ArrayList<Double>();
       sums.add(0.0);
       ArrayList<Integer> ville_able_to_visite=new ArrayList<Integer>();
       int valeur_a_retourner=-3;
       switch (state)
       {
           case 2:
              //System.out.println("distaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaance : " + this.problem_dynamic.gettimes_dynamic(from_client_fictif, i));
               d=problem_dynamic.getdepotfictifById_dynamic(from);
               this.currentTour = new Tour(this,this.tournee_effecuee.size(),d.getCapacité_restante(),from);
               int from_client_fictif= d.getId_client_fictif();
               for(Integer i: this.citiesStillToVisit_dynamic)    
                   {    
      Customer c = this.problem_dynamic.getCustomerById_dynamic(i);
       //System.out.println("distaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaance : " + this.problem_dynamic.gettimes_dynamic(from_client_fictif, i));
           
           
      if(((this.problem_dynamic.gettimes_dynamic(from_client_fictif, i)+ this.problem_dynamic.gettimes_dynamic(i, 0)+ this.problem_dynamic.temps_service)<= (this.problem_dynamic.getMaxTemps_dynamic()+Yarabbiyasser.ovetime))&&(this.currentTour.getCurrent_capacity()>=c.getDemande() ))
      {
           
          ville_able_to_visite.add(i);
          
          
        double tij= this.antSystem.getPheromones_dynamic(from_client_fictif, i);
        if ((problem_dynamic.getDistances_dynamic(from_client_fictif, i)==0))
        {
            tij=1;
        }
       
        if (problem_dynamic.getDistances_dynamic(from_client_fictif, i)==0)
        {
        visibilite1=100000;    
        }
        else
        {
        visibilite1= 1/problem_dynamic.getDistances_dynamic(from_client_fictif, i);
        }
        
        pi=Math.pow(tij,this.antSystem.alpha)*Math.pow(visibilite1,this.antSystem.beta1);
         probabilite.add(pi);
       sum=sum+pi;
       /*System.out.println("i   "  +i); 
        System.out.println("client_fictif   "  +from_client_fictif);
        System.out.println("sum  "  +sum); */
        
       
       sums.add(sum);
        j++;
      }     
              }
               if (ville_able_to_visite.isEmpty()) { 
       haveToReturn = true ;
       this.currentTour.setTournee_terminee(haveToReturn);
       this.currentTour.calculer_temps_tournee_dynamic();
       this.tournee_effecuee.add(currentTour);
       this.setPheromones_ant_dynamic(from_client_fictif,0,1);
       this.setState(0);
       valeur_a_retourner=0;}
               
       else{ 
             int next_client;
                   
             q=Math.random();
           if (q<=this.getAntSystem().getQ0_dynamique())
           {
             int indice=avoir_maximum_liste_double(probabilite);
             next_client=ville_able_to_visite.get(indice);
           }
           else{
           for (int k=0;k<sums.size();k++)
           {
               sums.set(k, sums.get(k)/sum);
              
           }
       
        /* System.out.println("sums:" +sums);
         System.out.println("ville_able_to visite:" +ville_able_to_visite.size());
         */
        next_client=find (sums,ville_able_to_visite,Math.random());}
        // System.out.println("client_a_ajouter :" +next_client);
       client_a_ajouter=this.problem_dynamic.getCustomerById_dynamic(next_client);
        
       haveToReturn = false;
        this.visitedCities_dynamic.add(next_client);
        this.citiesStillToVisit_dynamic.remove(citiesStillToVisit_dynamic.indexOf(next_client));
        this.currentposition = client_a_ajouter;
        currentDestination = next_client;
         this.currentTour.addCustomer_tour(client_a_ajouter);
         this.currentTour.setCurrent_capacity(client_a_ajouter.getDemande());
         //System.out.println("capacité courant"+ currentTour.getCurrent_capacity());
         this.currentTour.calculer_temps_tournee_dynamic();
         //System.out.println("capacité courant"+ currentTour.getCurrent_capacity());
         this.setPheromones_ant_dynamic(from_client_fictif,next_client,1);
         this.setState(1);
       valeur_a_retourner= next_client;
      
      
       
               }
                break;
           case 1:
                        for(Integer i: this.citiesStillToVisit_dynamic)    
                   { 
                      // System.out.println("le i fin 7Aslat   "+i);
      Customer c = this.problem_dynamic.getCustomerById_dynamic(i);
          // System.out.println("had le i ma7asltch fih    "+i);
       //    System.out.println("distaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaance case 1 : " + problem_dynamic.gettimes_dynamic(from, i));
       
           
      if(((this.currentTour.calculer_temps_tournee_dynamic()+this.problem_dynamic.gettimes_dynamic(from, i)+this.problem_dynamic.gettimes_dynamic(i, 0)+this.problem_dynamic.getTemps_service())<= (this.problem_dynamic.getMaxTemps_dynamic()+Yarabbiyasser.ovetime))&&(this.currentTour.getCurrent_capacity()>=c.getDemande() ))
      {
           
          ville_able_to_visite.add(i);
        double tij= this.antSystem.getPheromones_dynamic(from, i);
         if ((problem_dynamic.getDistances_dynamic(from, i)==0))
        {
            tij=1;
        }
       
        if (problem_dynamic.getDistances_dynamic(from, i)==0)
        {
        visibilite1=100000;    
        }
        else
        {
        visibilite1= 1/problem_dynamic.getDistances_dynamic(from, i);
        }
        //visibilite1= 1/problem_dynamic.getDistances_dynamic(from, i);
        pi=Math.pow(tij,this.antSystem.alpha)*Math.pow(visibilite1,this.antSystem.beta1); 
       /*  System.out.println("distance    "+problem_dynamic.getDistances_dynamic(from, i));
          System.out.println("taux ij   "+tij);
          System.out.println("from   "+from+  "   i  "+i);*/
          probabilite.add(pi);
       sum=sum+pi;
            sums.add(sum);
        j++;
      }     
              }
               if (ville_able_to_visite.isEmpty()) {
                   //System.out.println("mal9At fin tmchi ");
       haveToReturn = true ;
       this.currentTour.setTournee_terminee(haveToReturn);
       this.currentTour.calculer_temps_tournee_dynamic();
       this.tournee_effecuee.add(currentTour);
       this.setPheromones_ant_dynamic(from,0,1);
       this.setState(0);
       valeur_a_retourner=0;}
       else{
                   int next_client;
                   
             q=Math.random();
           if (q<=this.getAntSystem().getQ0_dynamique())
           {
             int indice=avoir_maximum_liste_double(probabilite);
             next_client=ville_able_to_visite.get(indice);
           }
           else{
           for (int k=0;k<sums.size();k++)
           {
               sums.set(k, sums.get(k)/sum);
              
           }
       
        
        next_client=find (sums,ville_able_to_visite,Math.random());}
                  // System.out.println("ha hiya mchat ");
          
       //System.out.println("sums "+sums);
      // System.out.println("ville able to visite "+ville_able_to_visite.size());
       
       client_a_ajouter=this.problem_dynamic.getCustomerById_dynamic(next_client);
        //System.out.println("ha l7aslaaaaaaaaaaaaaaaaa ");
       haveToReturn = false;
        this.visitedCities_dynamic.add(next_client);
        this.citiesStillToVisit_dynamic.remove(citiesStillToVisit_dynamic.indexOf(next_client));
        this.currentposition = client_a_ajouter;
        currentDestination = next_client;
         this.currentTour.addCustomer_tour(client_a_ajouter);
         this.currentTour.setCurrent_capacity(client_a_ajouter.getDemande());
         //System.out.println("capacité courant"+ currentTour.getCurrent_capacity());
         this.currentTour.calculer_temps_tournee_dynamic();
         //System.out.println("capacité courant"+ currentTour.getCurrent_capacity());
         this.setPheromones_ant_dynamic(from,next_client,1);
         this.setState(1);
       valeur_a_retourner= next_client;
      
      
       }
               break;
           case 0: 
               if (depotfictif_non_visite.isEmpty()==false)
                   
               {
                valeur_a_retourner=depotfictif_non_visite.get(0);
                depotfictif_non_visite.remove(depotfictif_non_visite.indexOf(valeur_a_retourner));
                depotfictif_visite.add(valeur_a_retourner);
                this.setState(2);
               }
               else {
                   
                   this.currentTour=new Tour(this,this.tournee_effecuee.size(),this.problem_dynamic.capacitycamion_dynamic,0);
                        
                    for(Integer i: this.citiesStillToVisit_dynamic)    
       {    
      Customer c = this.problem_dynamic.getCustomerById_dynamic(i);
       /*  System.out.println("haaaaaaaaaaa i fin kayen lmochkiiil: "+i);
           System.out.println("haaaaaaaaaaa from fin kayen lmochkiiil: "+from);
            System.out.println("distaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaance case 0: " + problem_dynamic.gettimes_dynamic(from, i));*/
       
           
      if(((this.problem_dynamic.gettimes_dynamic(from, i)+
              this.problem_dynamic.gettimes_dynamic(i, 0)+
              this.problem_dynamic.temps_service)<= (this.problem_dynamic.getMaxTemps_dynamic()+Yarabbiyasser.ovetime))&&(this.currentTour.getCurrent_capacity()>=c.getDemande() ))
      {
           
          ville_able_to_visite.add(i);
        double tij= this.antSystem.getPheromones_dynamic(from, i);
         if ((problem_dynamic.getDistances_dynamic(from, i)==0))
        {
            tij=1;
        }
       
        if (problem_dynamic.getDistances_dynamic(from, i)==0)
        {
        visibilite1=100000;    
        }
        else
        {
        visibilite1= 1/problem_dynamic.getDistances_dynamic(from, i);
        }
        //visibilite1= 1/problem_dynamic.getDistances_dynamic(from, i);
        pi=Math.pow(tij,this.antSystem.alpha)*Math.pow(visibilite1,this.antSystem.beta1);
        probabilite.add(pi);
      
       sum=sum+pi;
      
       sums.add(sum);
        j++;
      }     
        }
       //System.out.println("tournée courante"+ this.currentTour.getCustomers().size());
       if (ville_able_to_visite.isEmpty()) { 
       
        valeur_a_retourner=  -3;}
       else{
           int next_client;
                   
             q=Math.random();
           if (q<=this.getAntSystem().getQ0_dynamique())
           {
             int indice=avoir_maximum_liste_double(probabilite);
             next_client=ville_able_to_visite.get(indice);
           }
           else{
           for (int k=0;k<sums.size();k++)
           {
               sums.set(k, sums.get(k)/sum);
              
           }
       
        
        next_client=find (sums,ville_able_to_visite,Math.random());}
               
       client_a_ajouter=this.problem_dynamic.getCustomerById_dynamic(next_client);
       haveToReturn = false;
        this.visitedCities_dynamic.add(next_client);
        this.citiesStillToVisit_dynamic.remove(citiesStillToVisit_dynamic.indexOf(next_client));
        this.currentposition = client_a_ajouter;
        this.currentDestination = next_client;
         this.currentTour.addCustomer_tour(client_a_ajouter);
         this.currentTour.setCurrent_capacity(client_a_ajouter.getDemande());
         //System.out.println("capacité courant"+ currentTour.getCurrent_capacity());
         this.currentTour.calculer_temps_tournee_dynamic();
         //System.out.println("capacité courant"+ currentTour.getCurrent_capacity());
         this.setPheromones_ant_dynamic(from,next_client,1);
          this.setState(1);
       valeur_a_retourner= next_client;
      
       }
                   
                   
               }
               break;
               
              
               
       }
       this.currentDestination=valeur_a_retourner;
       return valeur_a_retourner;
   }
   int get_next_dest_dis (int from){
       Customer client_a_ajouter;
       boolean haveToReturn = false;
       int j=0; 
       double sum=0;
       double visibilite1;
       double pi;
       double q;
       int next_client=0;
       ArrayList<Double>   sums = new ArrayList<Double>();
       ArrayList<Double>   probabilite = new ArrayList<Double>();
       sums.add(0.0);
       ArrayList<Integer> ville_able_to_visite=new ArrayList<Integer>();
       if (from==0){
           //this.tournee_effecuee.add(currentTour);
           this.currentTour=new Tour(this,this.tournee_effecuee.size(),this.problem.capacitycamion);
          //  System.out.println("hana dkhalet"+ tournee_effecuee.size());
           
                   }
       
       for(Integer i: this.citiesStillToVisit)    
       {    
      Customer c = this.problem.getCustomerById(i);
           //System.out.println("yarabbiyasser.Ant.get_next_dest()");
       
           
      if(((this.currentTour.calculer_temps_tournee()+this.problem.gettimes(from, i)+ this.problem.gettimes (i,0)+ this.antSystem.getTemps_service())<= (this.problem.getMaxTemps()))&&(this.currentTour.getCurrent_capacity()>=c.getDemande() ))
      {
           
          ville_able_to_visite.add(i);
        double tij= this.antSystem.getPheromones(from, i);
         if (problem.getDistances(from, i)==0)
        {
        tij=1;    
        }
        if (problem.getDistances(from, i)==0)
        {
        visibilite1=100000;    
        }
        else
        {
        visibilite1= 1/problem.getDistances(from, i);
        }
        pi=Math.pow(tij,this.antSystem.alpha)*Math.pow(visibilite1,this.antSystem.beta1);
        probabilite.add(pi);
          //System.out.println("distance"+problem.getDistances(from, i) +"   from:  "+from + "i  : "+i);
         
       sum=sum+pi;
       
       sums.add(sum);
        j++;
      }     
        }
       //System.out.println("tournée courante"+ this.currentTour.getCustomers().size());
       if (ville_able_to_visite.isEmpty()) { 
       haveToReturn = true ;
       this.currentTour.setTournee_terminee(haveToReturn);
       this.currentTour.calculer_temps_tournee();
       this.tournee_effecuee.add(currentTour);
       this.setPheromones_ant(from,0,1);
       
    // this.antSystem.setPheromones(from, 0, ((1-this.antSystem.getEvaporation())*this.antSystem.getPheromones(from, 0)+this.antSystem.getEvaporation()*this.antSystem.getConstantPheromones()));
       return -1;}
       else{
           q=Math.random();
           if (q<=this.getAntSystem().getQ0())
           {
             int indice=avoir_maximum_liste_double(probabilite);
             next_client=ville_able_to_visite.get(indice);
           }
           else
           {
           for (int k=0;k<sums.size();k++)
           {
               sums.set(k, sums.get(k)/sum);
           }
       
        
        next_client=find (sums,ville_able_to_visite,Math.random());
           }
        //System.out.println("find"+find(sums,ville_able_to_visite,Math.random()));
        //System.out.println("ville  able to visite"+ville_able_to_visite.size());
        //System.out.println("sums"+sums.size());
        
       client_a_ajouter=this.problem.getCustomerById(next_client);
        
       haveToReturn = false;
        this.visitedCities.add(next_client);
        this.citiesStillToVisit.remove(citiesStillToVisit.indexOf(next_client));
        this.currentposition = client_a_ajouter;
        currentDestination = next_client;
         this.currentTour.addCustomer_tour(client_a_ajouter);
         this.currentTour.setCurrent_capacity(client_a_ajouter.getDemande());
         //System.out.println("capacité courant"+ currentTour.getCurrent_capacity());
         this.currentTour.calculer_temps_tournee();
         //System.out.println("capacité courant"+ currentTour.getCurrent_capacity());
         this.setPheromones_ant(from,next_client,1);
       // this.antSystem.setPheromones(from, next_client, ((1-this.antSystem.getEvaporation())*this.antSystem.getPheromones(from, next_client)+this.antSystem.getEvaporation()*this.antSystem.getConstantPheromones()));
       return next_client;
      
       }
    
   }
   int get_next_dest_dis_dynamic(int from){
       Depotfictif d;
       Customer client_a_ajouter;
       boolean haveToReturn = false;
       int j=0; 
       double sum=0;
       double visibilite1;
       double pi;
       double q;
       ArrayList<Double>   sums = new ArrayList<Double>();
       ArrayList<Double>   probabilite = new ArrayList<Double>();
       sums.add(0.0);
       ArrayList<Integer> ville_able_to_visite=new ArrayList<Integer>();
       int valeur_a_retourner=-3;
       switch (state)
       {
           case 2:
              // System.out.println("ha hiya fro 3awttani : " + from);
               d=problem_dynamic.getdepotfictifById_dynamic(from);
               this.currentTour = new Tour(this,this.tournee_effecuee.size(),d.getCapacité_restante(),from);
               int from_client_fictif= d.getId_client_fictif();
               for(Integer i: this.citiesStillToVisit_dynamic)    
                   {    
      Customer c = this.problem_dynamic.getCustomerById_dynamic(i);
           
           
      if(((this.problem_dynamic.gettimes_dynamic(from_client_fictif, i)+ this.problem_dynamic.gettimes_dynamic(i, 0)+ this.problem_dynamic.temps_service)<= (this.problem_dynamic.getMaxTemps_dynamic()))&&(this.currentTour.getCurrent_capacity()>=c.getDemande() ))
      {
           
          ville_able_to_visite.add(i);
          
        double tij= this.antSystem.getPheromones_dynamic(from_client_fictif, i);
        if ((problem_dynamic.getDistances_dynamic(from_client_fictif, i)==0))
        {
            tij=1;
        }
       
        if (problem_dynamic.getDistances_dynamic(from_client_fictif, i)==0)
        {
        visibilite1=100000;    
        }
        else
        {
        visibilite1= 1/problem_dynamic.getDistances_dynamic(from_client_fictif, i);
        }
        
        pi=Math.pow(tij,this.antSystem.alpha)*Math.pow(visibilite1,this.antSystem.beta1);
         probabilite.add(pi);
       sum=sum+pi;
       /*System.out.println("i   "  +i); 
        System.out.println("client_fictif   "  +from_client_fictif);
        System.out.println("sum  "  +sum); */
        
       
       sums.add(sum);
        j++;
      }     
              }
               if (ville_able_to_visite.isEmpty()) { 
       haveToReturn = true ;
       this.currentTour.setTournee_terminee(haveToReturn);
       this.currentTour.calculer_temps_tournee_dynamic();
       this.tournee_effecuee.add(currentTour);
       this.setPheromones_ant_dynamic(from_client_fictif,0,1);
       this.setState(0);
       valeur_a_retourner=0;}
               
       else{ 
             int next_client;
                   
             q=Math.random();
           if (q<=this.getAntSystem().getQ0_dynamique())
           {
             int indice=avoir_maximum_liste_double(probabilite);
             next_client=ville_able_to_visite.get(indice);
           }
           else{
           for (int k=0;k<sums.size();k++)
           {
               sums.set(k, sums.get(k)/sum);
              
           }
       
        /* System.out.println("sums:" +sums);
         System.out.println("ville_able_to visite:" +ville_able_to_visite.size());
         */
        next_client=find (sums,ville_able_to_visite,Math.random());}
        // System.out.println("client_a_ajouter :" +next_client);
       client_a_ajouter=this.problem_dynamic.getCustomerById_dynamic(next_client);
        
       haveToReturn = false;
        this.visitedCities_dynamic.add(next_client);
        this.citiesStillToVisit_dynamic.remove(citiesStillToVisit_dynamic.indexOf(next_client));
        this.currentposition = client_a_ajouter;
        currentDestination = next_client;
         this.currentTour.addCustomer_tour(client_a_ajouter);
         this.currentTour.setCurrent_capacity(client_a_ajouter.getDemande());
         //System.out.println("capacité courant"+ currentTour.getCurrent_capacity());
         this.currentTour.calculer_temps_tournee_dynamic();
         //System.out.println("capacité courant"+ currentTour.getCurrent_capacity());
         this.setPheromones_ant_dynamic(from_client_fictif,next_client,1);
         this.setState(1);
       valeur_a_retourner= next_client;
      
      
       
               }
                break;
           case 1:
                        for(Integer i: this.citiesStillToVisit_dynamic)    
                   { 
                      // System.out.println("le i fin 7Aslat   "+i);
      Customer c = this.problem_dynamic.getCustomerById_dynamic(i);
          // System.out.println("had le i ma7asltch fih    "+i);
       
           
      if(((this.currentTour.calculer_temps_tournee_dynamic()+this.problem_dynamic.gettimes_dynamic(from, i)+this.problem_dynamic.gettimes_dynamic(i, 0)+this.problem_dynamic.getTemps_service())<= (this.problem_dynamic.getMaxTemps_dynamic()))&&(this.currentTour.getCurrent_capacity()>=c.getDemande() ))
      {
           
          ville_able_to_visite.add(i);
        double tij= this.antSystem.getPheromones_dynamic(from, i);
         if ((problem_dynamic.getDistances_dynamic(from, i)==0))
        {
            tij=1;
        }
       
        if (problem_dynamic.getDistances_dynamic(from, i)==0)
        {
        visibilite1=100000;    
        }
        else
        {
        visibilite1= 1/problem_dynamic.getDistances_dynamic(from, i);
        }
        //visibilite1= 1/problem_dynamic.getDistances_dynamic(from, i);
        pi=Math.pow(tij,this.antSystem.alpha)*Math.pow(visibilite1,this.antSystem.beta1); 
       /*  System.out.println("distance    "+problem_dynamic.getDistances_dynamic(from, i));
          System.out.println("taux ij   "+tij);
          System.out.println("from   "+from+  "   i  "+i);*/
          probabilite.add(pi);
       sum=sum+pi;
            sums.add(sum);
        j++;
      }     
              }
               if (ville_able_to_visite.isEmpty()) {
                   //System.out.println("mal9At fin tmchi ");
       haveToReturn = true ;
       this.currentTour.setTournee_terminee(haveToReturn);
       this.currentTour.calculer_temps_tournee_dynamic();
       this.tournee_effecuee.add(currentTour);
       this.setPheromones_ant_dynamic(from,0,1);
       this.setState(0);
       valeur_a_retourner=0;}
       else{
                   int next_client;
                   
             q=Math.random();
           if (q<=this.getAntSystem().getQ0_dynamique())
           {
             int indice=avoir_maximum_liste_double(probabilite);
             next_client=ville_able_to_visite.get(indice);
           }
           else{
           for (int k=0;k<sums.size();k++)
           {
               sums.set(k, sums.get(k)/sum);
              
           }
       
        
        next_client=find (sums,ville_able_to_visite,Math.random());}
                  // System.out.println("ha hiya mchat ");
          
       //System.out.println("sums "+sums);
      // System.out.println("ville able to visite "+ville_able_to_visite.size());
       
       client_a_ajouter=this.problem_dynamic.getCustomerById_dynamic(next_client);
        //System.out.println("ha l7aslaaaaaaaaaaaaaaaaa ");
       haveToReturn = false;
        this.visitedCities_dynamic.add(next_client);
        this.citiesStillToVisit_dynamic.remove(citiesStillToVisit_dynamic.indexOf(next_client));
        this.currentposition = client_a_ajouter;
        currentDestination = next_client;
         this.currentTour.addCustomer_tour(client_a_ajouter);
         this.currentTour.setCurrent_capacity(client_a_ajouter.getDemande());
         //System.out.println("capacité courant"+ currentTour.getCurrent_capacity());
         this.currentTour.calculer_temps_tournee_dynamic();
         //System.out.println("capacité courant"+ currentTour.getCurrent_capacity());
         this.setPheromones_ant_dynamic(from,next_client,1);
         this.setState(1);
       valeur_a_retourner= next_client;
      
      
       }
               break;
           case 0: 
               if (depotfictif_non_visite.isEmpty()==false)
                   
               {
                valeur_a_retourner=depotfictif_non_visite.get(0);
                depotfictif_non_visite.remove(depotfictif_non_visite.indexOf(valeur_a_retourner));
                depotfictif_visite.add(valeur_a_retourner);
                this.setState(2);
               }
               else {
                   
                   this.currentTour=new Tour(this,this.tournee_effecuee.size(),this.problem_dynamic.capacitycamion_dynamic,0);
                        
                    for(Integer i: this.citiesStillToVisit_dynamic)    
       {    
      Customer c = this.problem_dynamic.getCustomerById_dynamic(i);
         //System.out.println("haaaaaaaaaaa i fin kayen lmochkiiil: "+i);
           //System.out.println("haaaaaaaaaaa from fin kayen lmochkiiil: "+from);
           
      if(((this.problem_dynamic.gettimes_dynamic(from, i)+
              this.problem_dynamic.gettimes_dynamic(i, 0)+
              this.problem_dynamic.temps_service)<= (this.problem_dynamic.getMaxTemps_dynamic()))&&(this.currentTour.getCurrent_capacity()>=c.getDemande() ))
      {
           
          ville_able_to_visite.add(i);
        double tij= this.antSystem.getPheromones_dynamic(from, i);
         if ((problem_dynamic.getDistances_dynamic(from, i)==0))
        {
            tij=1;
        }
       
        if (problem_dynamic.getDistances_dynamic(from, i)==0)
        {
        visibilite1=100000;    
        }
        else
        {
        visibilite1= 1/problem_dynamic.getDistances_dynamic(from, i);
        }
        //visibilite1= 1/problem_dynamic.getDistances_dynamic(from, i);
        pi=Math.pow(tij,this.antSystem.alpha)*Math.pow(visibilite1,this.antSystem.beta1);
        probabilite.add(pi);
      
       sum=sum+pi;
      
       sums.add(sum);
        j++;
      }     
        }
       //System.out.println("tournée courante"+ this.currentTour.getCustomers().size());
       if (ville_able_to_visite.isEmpty()) { 
       
        valeur_a_retourner=  -3;}
       else{
           int next_client;
                   
             q=Math.random();
           if (q<=this.getAntSystem().getQ0_dynamique())
           {
             int indice=avoir_maximum_liste_double(probabilite);
             next_client=ville_able_to_visite.get(indice);
           }
           else{
           for (int k=0;k<sums.size();k++)
           {
               sums.set(k, sums.get(k)/sum);
              
           }
       
        
        next_client=find (sums,ville_able_to_visite,Math.random());}
               
       client_a_ajouter=this.problem_dynamic.getCustomerById_dynamic(next_client);
       haveToReturn = false;
        this.visitedCities_dynamic.add(next_client);
        this.citiesStillToVisit_dynamic.remove(citiesStillToVisit_dynamic.indexOf(next_client));
        this.currentposition = client_a_ajouter;
        this.currentDestination = next_client;
         this.currentTour.addCustomer_tour(client_a_ajouter);
         this.currentTour.setCurrent_capacity(client_a_ajouter.getDemande());
         //System.out.println("capacité courant"+ currentTour.getCurrent_capacity());
         this.currentTour.calculer_temps_tournee_dynamic();
         //System.out.println("capacité courant"+ currentTour.getCurrent_capacity());
         this.setPheromones_ant_dynamic(from,next_client,1);
          this.setState(1);
       valeur_a_retourner= next_client;
      
       }
                   
                   
               }
               break;
               
              
               
       }
       this.currentDestination=valeur_a_retourner;
       return valeur_a_retourner;
   }
       
   public void affecter_vehicule(){
       if (this.tournee_effecuee.size()<= this.getCamions().size())
       {
          
           int c=0;
            while( c<this.tournee_effecuee.size()) 
            {
                this.getCamions().get(c).tournee_attribuees.clear();
                this.getCamions().get(c).tournee_attribuees.add(this.tournee_effecuee.get(c));
               
               
                c++;
            }
             this.tournee_effecuee.clear();
       }
       else {
           int i=0;
          
           classer_tournees(this.tournee_effecuee);
            
          while(i<this.getCamions().size())
        
               { 
                   
                 this.getCamions().get(i).tournee_attribuees.clear();
                 // System.out.println("tournees attribuees camions: "+ this.getCamions().get(i).tournee_attribuees.size());
                 this.getCamions().get(i).tournee_attribuees.add(this.tournee_effecuee.get(0));
                
                this.tournee_effecuee.remove(0);
               // this.camions.add(camion);
               
       
               
                i++; 
               }
           
           while (this.tournee_effecuee.isEmpty()==false)
           {
               classer_tournees(this.tournee_effecuee);
               classer_camion(this.getCamions());
               this.getCamions().get(0).tournee_attribuees.add(this.tournee_effecuee.get(0));
               tournee_effecuee.remove(0);
           }
          
       }
       classer_camion(this.getCamions());
       //for (int k=0; k<this.getCamions.)
       
   }
    public void affecter_vehicule_dynamic(){
        for (int i=0; i<this.getCamions().size();i++)
        {
            this.getCamions().get(i).getTournee_attribuees().clear();
            for (int j=0;j<this.tournee_effecuee.size();j++)
                  {
                      if (this.tournee_effecuee.get(j).getId_fictif()==0)
                      {
                          continue;
                      }
                      else
                      {
                  Depotfictif d=this.getProblem_dynamic().getdepotfictifById_dynamic(this.tournee_effecuee.get(j).getId_fictif());
               int id_cl_fict=d.getId_client_fictif();
                 if (this.getCamions().get(i).getId_fictif_final()==id_cl_fict)
                {
             
                // System.out.println("tail tournées attribuées men weset hadik la bouuuuuuuuuuuuuuuuuucle: "+ this.getCamions().get(i).getTournee_attribuees().size());
                  this.getCamions().get(i).tournee_attribuees.add(this.tournee_effecuee.get(j)); 
                  // System.out.println("tail tournées attribuées men weset hadik la bouuuuuuuuuuuuuuuuuucle after: "+ this.getCamions().get(i).getTournee_attribuees().size());
                
                  this.tournee_effecuee.remove(j);
                 j=j-1;
                }
                      }
            }
        }    
       
        while (this.tournee_effecuee.isEmpty()==false)
           {
           /*  System.out.println("tail tournées attribuées: "+ this.getCamions().get(0).getTournee_attribuees().size());
              System.out.println("tail tournées EFFECTU2ES: "+ this.tournee_effecuee.size());
               System.out.println("tail camions: "+ this.getCamions().size());*/
              
               classer_tournees_dynamic(this.tournee_effecuee);
               classer_camion_dynamic(this.getCamions());
                
               this.getCamions().get(0).tournee_attribuees.add(this.tournee_effecuee.get(0));
               tournee_effecuee.remove(0);
           }
        
        classer_camion_dynamic(this.getCamions());
       /* for (int i=0; i<this.getCamions().size(); i++)
        {
            System.out.println("id_fictif camion:  "+ this.getCamions().get(i).getId_fictif_final()); 
              for (int j=0; j<this.getCamions().get(i).getTournee_attribuees().size(); j++)
              {
                 System.out.println("id_fictif tournée:  "+ this.getCamions().get(i).getTournee_attribuees().get(j).getId_fictif());   
              }
        }
          */
       
          
       }
     public void classer_tournes_dynamic_defenitif(){
        for (int i=0; i<this.getCamions().size();i++)
        {
           
            for (int j=0;j<this.getCamions().get(i).tournee_attribuees.size();j++)
            {
                if (this.getCamions().get(i).tournee_attribuees.get(j).getId_fictif()>0)
                {
                 Tour tompon1=this.getCamions().get(i).tournee_attribuees.get(j);
                 Tour tompon2=this.getCamions().get(i).tournee_attribuees.get(0);
                  this.getCamions().get(i).tournee_attribuees.set(j, tompon2);
                  this.getCamions().get(i).tournee_attribuees.set(0, tompon1);
                  
                }
            }
        }  
       /* for (int i=0; i<this.getCamions().size(); i++)
        {
            System.out.println("id_fictif camion dyal classer defeinitif:  "+ this.getCamions().get(i).getId_fictif_final()); 
              for (int j=0; j<this.getCamions().get(i).getTournee_attribuees().size(); j++)
              {
                 System.out.println("id_fictif tournée dyal classer definitif:  "+ this.getCamions().get(i).getTournee_attribuees().get(j).getId_fictif());   
              }
        }
          */
   
       
          
       }
    
   public void classer_tournees(ArrayList<Tour> Tours){
         boolean permut;
         Tour tampon1=null;
         Tour tampon2=null;
         do {
             permut= false;
             for (int i=0; i<Tours.size()-1;i++)
             {
                 if (Tours.get(i).calculer_temps_tournee()<Tours.get(i+1).calculer_temps_tournee())
                 {
                     tampon1=Tours.get(i);
                      tampon2=Tours.get(i+1);
                     Tours.set(i,tampon2);
                     Tours.set(i+1, tampon1);
                     permut=true;
                 }
                 
             }
         } while (permut);
         
         
     }
   public void classer_tournees_dynamic(ArrayList<Tour> Tours){
         boolean permut;
         Tour tampon1=null;
         Tour tampon2=null;
         do {
             permut= false;
             for (int i=0; i<Tours.size()-1;i++)
             {
                 if (Tours.get(i).calculer_temps_tournee_dynamic()<Tours.get(i+1).calculer_temps_tournee_dynamic())
                 {
                     tampon1=Tours.get(i);
                      tampon2=Tours.get(i+1);
                     Tours.set(i,tampon2);
                     Tours.set(i+1, tampon1);
                     permut=true;
                 }
                 
             }
         } while (permut);
         
         
     }
   
    public void classer_tournees_decroissant(ArrayList<Tour> Tours){
         boolean permut;
         Tour tampon1=null;
         Tour tampon2=null;
         do {
             permut= false;
             for (int i=0; i<Tours.size()-1;i++)
             {
                 if (Tours.get(i).calculer_temps_tournee()>Tours.get(i+1).calculer_temps_tournee())
                 {
                     tampon1=Tours.get(i);
                      tampon2=Tours.get(i+1);
                     Tours.set(i,tampon2);
                     Tours.set(i+1, tampon1);
                     permut=true;
                 }
                 
             }
         } while (permut);
         
         
     }
   public void classer_tournees_dynamic_decroissant(ArrayList<Tour> Tours){
         boolean permut;
         Tour tampon1=null;
         Tour tampon2=null;
         do {
             permut= false;
             for (int i=0; i<Tours.size()-1;i++)
             {
                 if (Tours.get(i).calculer_temps_tournee_dynamic()>Tours.get(i+1).calculer_temps_tournee_dynamic())
                 {
                     tampon1=Tours.get(i);
                      tampon2=Tours.get(i+1);
                     Tours.set(i,tampon2);
                     Tours.set(i+1, tampon1);
                     permut=true;
                 }
                 
             }
         } while (permut);
         
         
     }
   public void classer_camion(ArrayList<Camion> camions_a_trier){
          boolean permut;
          Camion tampon1=null;
          Camion tampon2= null;
          do
          {
              permut= false;
              for (int i=0; i<camions_a_trier.size()-1;i++)
              {
                if (camions_a_trier.get(i).calucler_temps_total_tournees()>camions_a_trier.get(i+1).calucler_temps_total_tournees())
                {
                    tampon1=camions_a_trier.get(i);
                    tampon2=camions_a_trier.get(i+1);
                    camions_a_trier.set(i, tampon2);
                    camions_a_trier.set(i+1, tampon1);
                    permut=true;
                }   
              }
          }while (permut);
      }
    public void classer_camion_dynamic(ArrayList<Camion> camions_a_trier){
          boolean permut;
          Camion tampon1=null;
          Camion tampon2= null;
          do
          {
              permut= false;
              for (int i=0; i<camions_a_trier.size()-1;i++)
              {
                if ((camions_a_trier.get(i).calucler_temps_total_tournees_dynamic()+camions_a_trier.get(i).getTemps_tournees_avant())>(camions_a_trier.get(i+1).calucler_temps_total_tournees_dynamic()+camions_a_trier.get(i+1).getTemps_tournees_avant()))
                {
                    tampon1=camions_a_trier.get(i);
                    tampon2=camions_a_trier.get(i+1);
                    camions_a_trier.set(i, tampon2);
                    camions_a_trier.set(i+1, tampon1);
                    permut=true;
                }   
              }
          }while (permut);
      }
   public double get_overtime_maximal_dynamic (){
    /*  System.out.println("temps tournées avant"+this.getCamions().get(this.getCamions().size()-1).getTemps_tournees_avant());
      System.out.println("temps tournes encours"+this.getCamions().get(this.getCamions().size()-1).calucler_temps_total_tournees_dynamic());
      System.out.println("temps max prob en cours"+this.problem_dynamic.getMaxTemps_dynamic());*/
      
       double OVm=Math.max(0,(this.getCamions().get(this.getCamions().size()-1).calucler_temps_total_tournees_dynamic()+this.getCamions().get(this.getCamions().size()-1).getTemps_tournees_avant()-Yarabbiyasser.max_temps));
       return OVm;    
      }
   public double get_overtime_maximal (){
       //classer_camion(this.getCamions());
       double OVm=Math.max(0,this.getCamions().get(this.getCamions().size()-1).calucler_temps_total_tournees()-this.problem.getMaxTemps());
       return OVm;    
      }
   public double calculer_temps_total_tournees(ArrayList<Tour> Tours){
      
        double T=0;
        for (Tour t:Tours)
        {
            //System.out.println("calculer temps tournées  :"+T);
            T=T+t.calculer_temps_tournee();
        }
        return T;
    
       
   }
   public double calculer_temps_total_tournees_dynamic(ArrayList<Tour> Tours){
      
        double T=0;
        for (Tour t:Tours)
        {
            T=T+t.calculer_temps_tournee_dynamic();
        }
        return T;
    
       
   }
   public double calculer_distance_total_tournees(ArrayList<Tour> Tours){
      
        double T=0;
        for (Tour t:Tours)
        {
            T=T+t.getDistance_tour();
        }
        return T;
    
       
   }
   public double calculer_distance_total_tournees_dynamic(ArrayList<Tour> Tours){
      
        double T=0;
        for (Tour t:Tours)
        {
            T=T+t.getDistance_tour();
        }
        return T;
    
       
   }
   
   
  
   public void run_ant(){
       int i=0;
       
       while (this.citiesStillToVisit.size() > 0)
          
       {
          //System.out.println("haa temps tournées effectuées men 3aned ant brasha men weset la boucle:"+this.calculer_temps_total_tournees(tournee_effecuee));
          i=this.get_next_dest(currentDestination);
         // System.out.println("ville non encore vistite:" +this.citiesStillToVisit.size() );
          
          if (i==-1)
          {
              //System.out.println("woooooooooooooooooooooooooooooo" +this.citiesStillToVisit.size() );  
              i=this.get_next_dest(0);
            // System.out.println("ville non encore vistite:" +this.citiesStillToVisit.size() );  
              
          }
           
       }
      // System.out.println("Salat lwez kamel");
       this.setPheromones_ant(i, 0, 1);
       
       this.tournee_effecuee.add(currentTour);
     //  System.out.println("haa temps tournées effectuées men 3aned ant brasha :"+this.calculer_temps_total_tournees(tournee_effecuee));
        
      this.setTemps_total_tournees(calculer_temps_total_tournees(this.tournee_effecuee));//this.tournee_effecuee.
      this.setDistance_total_tournees(calculer_distance_total_tournees(this.tournee_effecuee));
     
       this.solution_complete=true;
      this.affecter_vehicule();
     
       this.update_pheromone_ant();
       this.setCost_ant(problem.getCoef_distance_parcourue()* this.getTemps_total_tournees()
                    + problem.getCoef_overtime()* this.get_overtime_maximal());
        //System.out.println("Salat lwez kamel");
       
       
   }
   public void run_ant_dynamic(){
       int i=0;
       
       while ((this.citiesStillToVisit_dynamic.size() > 0)||(depotfictif_non_visite.isEmpty()==false))
          
       {
          /*System.out.println("nombre de ville pas encore vsité" +this.citiesStillToVisit_dynamic.size() );
          System.out.println("state" +this.getState() );
          System.out.println("current position" +this.currentDestination );*/
          i=this.get_next_dest_dynamic(currentDestination);
         //System.out.println("ha maxtemps" +this.problem_dynamic.getMaxTemps_dynamic() );
          if (i==-3)
          {
         System.out.println("Erreuuur f les données"  );
          }
       
           
       }
     /*  while (depotfictif_non_visite.isEmpty()==false)
                   
               {
                valeur_a_retourner=depotfictif_non_visite.get(0);
                depotfictif_non_visite.remove(depotfictif_non_visite.indexOf(valeur_a_retourner));
                depotfictif_visite.add(valeur_a_retourner);
                this.setState(2);
               }
       */
     switch (state)
     {
         case 1:
       this.setPheromones_ant_dynamic(i, 0, 1);
       this.currentTour.setTournee_terminee(true);
       
       this.tournee_effecuee.add(currentTour);
        
      this.setTemps_total_tournees(calculer_temps_total_tournees_dynamic(this.tournee_effecuee));
      this.setDistance_total_tournees(calculer_distance_total_tournees_dynamic(this.tournee_effecuee));//this.tournee_effecuee.
     
       this.solution_complete=true;
       this.affecter_vehicule_dynamic();
     
       this.update_pheromone_ant_dynamic();
       this.setCost_ant(problem_dynamic.getCoef_distance_parcourue()* this.getTemps_total_tournees()
                    + problem_dynamic.getCoef_overtime()* this.get_overtime_maximal_dynamic());
      break;
      
         case 2:  
         Depotfictif d;
        // System.out.println("id depot fictif:"+i);
         d=problem_dynamic.getdepotfictifById_dynamic(i);
        this.currentTour = new Tour(this,this.tournee_effecuee.size(),d.getCapacité_restante(),i);
        int from_client_fictif= d.getId_client_fictif();
        this.setPheromones_ant_dynamic(from_client_fictif, 0, 1);
        this.currentTour.setTournee_terminee(true);
        this.tournee_effecuee.add(currentTour);
       this.setTemps_total_tournees(calculer_temps_total_tournees_dynamic(this.tournee_effecuee));
       this.setDistance_total_tournees(calculer_distance_total_tournees_dynamic(this.tournee_effecuee));//+this.problem_dynamic.gettimes_dynamic(from_client_fictif, 0));
        this.solution_complete=true;
       this.affecter_vehicule_dynamic();
     
       this.update_pheromone_ant_dynamic();
       this.setCost_ant(problem_dynamic.getCoef_distance_parcourue()* this.getTemps_total_tournees()
                    + problem_dynamic.getCoef_overtime()* this.get_overtime_maximal_dynamic());
       break;
         case 0:
             this.solution_complete=true;
       this.affecter_vehicule_dynamic();
      this.setTemps_total_tournees(calculer_temps_total_tournees_dynamic(this.tournee_effecuee));
      this.setDistance_total_tournees(calculer_distance_total_tournees_dynamic(this.tournee_effecuee));
       this.update_pheromone_ant_dynamic();
       this.setCost_ant(problem_dynamic.getCoef_distance_parcourue()* this.getTemps_total_tournees()
                    + problem_dynamic.getCoef_overtime()* this.get_overtime_maximal_dynamic());
       
   }
   
   
     
   }
    public void run_ant_dis(){
       int i=0;
       
       while (this.citiesStillToVisit.size() > 0)
          
       {
          
          i=this.get_next_dest_dis(currentDestination);
         // System.out.println("ville non encore vistite:" +this.citiesStillToVisit.size() );
          
          if (i==-1)
          {
              //System.out.println("woooooooooooooooooooooooooooooo" +this.citiesStillToVisit.size() );  
              i=this.get_next_dest_dis(0);
            // System.out.println("ville non encore vistite:" +this.citiesStillToVisit.size() );  
              
          }
           
       }
      // System.out.println("Salat lwez kamel");
       this.setPheromones_ant(i, 0, 1);
       
       this.tournee_effecuee.add(currentTour);
        
      this.setTemps_total_tournees(calculer_temps_total_tournees(this.tournee_effecuee));//this.tournee_effecuee.
      this.setDistance_total_tournees(calculer_distance_total_tournees(this.tournee_effecuee));
     
       this.solution_complete=true;
      this.affecter_vehicule();
     
       this.update_pheromone_ant();
       this.setCost_ant(problem.getCoef_distance_parcourue()* this.getTemps_total_tournees()
                    + problem.getCoef_overtime()* this.get_overtime_maximal());
        //System.out.println("Salat lwez kamel");
       
       
   }
   public void run_ant_dis_dynamic(){
       int i=0;
       
       while ((this.citiesStillToVisit_dynamic.size() > 0)||(depotfictif_non_visite.isEmpty()==false))
          
       {
         /* System.out.println("nombre de ville pas encore vsité" +this.citiesStillToVisit_dynamic.size() );
          System.out.println("state" +this.getState() );
          System.out.println("current position" +this.currentDestination );*/
          i=this.get_next_dest_dis_dynamic(currentDestination);
          //System.out.println("ha i" +i );
          if (i==-3)
          {
         System.out.println("Erreuuur f les données"  );
          }
       
           
       }
     /*  while (depotfictif_non_visite.isEmpty()==false)
                   
               {
                valeur_a_retourner=depotfictif_non_visite.get(0);
                depotfictif_non_visite.remove(depotfictif_non_visite.indexOf(valeur_a_retourner));
                depotfictif_visite.add(valeur_a_retourner);
                this.setState(2);
               }
       */
     switch (state)
     {
         case 1:
       this.setPheromones_ant_dynamic(i, 0, 1);
       this.currentTour.setTournee_terminee(true);
       this.tournee_effecuee.add(currentTour);
        
      this.setTemps_total_tournees(calculer_temps_total_tournees_dynamic(this.tournee_effecuee));
      this.setDistance_total_tournees(calculer_distance_total_tournees_dynamic(this.tournee_effecuee));//this.tournee_effecuee.
     
       this.solution_complete=true;
       this.affecter_vehicule_dynamic();
     
       this.update_pheromone_ant_dynamic();
       this.setCost_ant(problem_dynamic.getCoef_distance_parcourue()* this.getTemps_total_tournees()
                    + problem_dynamic.getCoef_overtime()* this.get_overtime_maximal_dynamic());
      break;
      
         case 2:  
         Depotfictif d;
         System.out.println("id depot fictif:"+i);
         d=problem_dynamic.getdepotfictifById_dynamic(i);
        this.currentTour = new Tour(this,this.tournee_effecuee.size(),d.getCapacité_restante(),i);
        int from_client_fictif= d.getId_client_fictif();
        this.setPheromones_ant_dynamic(from_client_fictif, 0, 1);
        this.currentTour.setTournee_terminee(true);
        this.tournee_effecuee.add(currentTour);
       this.setTemps_total_tournees(calculer_temps_total_tournees_dynamic(this.tournee_effecuee));
       this.setDistance_total_tournees(calculer_distance_total_tournees_dynamic(this.tournee_effecuee));//+this.problem_dynamic.gettimes_dynamic(from_client_fictif, 0));
        this.solution_complete=true;
       this.affecter_vehicule_dynamic();
     
       this.update_pheromone_ant_dynamic();
       this.setCost_ant(problem_dynamic.getCoef_distance_parcourue()* this.getTemps_total_tournees()
                    + problem_dynamic.getCoef_overtime()* this.get_overtime_maximal_dynamic());
       break;
         case 0:
             this.solution_complete=true;
       this.affecter_vehicule_dynamic();
      this.setTemps_total_tournees(calculer_temps_total_tournees_dynamic(this.tournee_effecuee));
      this.setDistance_total_tournees(calculer_distance_total_tournees_dynamic(this.tournee_effecuee));
       this.update_pheromone_ant_dynamic();
       this.setCost_ant(problem_dynamic.getCoef_distance_parcourue()* this.getTemps_total_tournees()
                    + problem_dynamic.getCoef_overtime()* this.get_overtime_maximal_dynamic());
       
   }
   
   
     
   }
   
   public double get_depo_fic_ant ()
{
   
     this.setTemps_total_time_slice(0);
     
    for(int k=1 ;k<=this.getCamions().size(); k++)
    {
        Camion c = this.getCamions().get(k-1);
        double sum_temps_tour=0;
        int i=0;
        if (c.calucler_temps_total_tournees()<=this.getAntSystem().getTime_slice())
        {
            return c.calucler_temps_total_tournees();
        }
       // System.out.println("time_slice : "+ this.getTime_slice());
        while (sum_temps_tour < this.getAntSystem().getTime_slice())
        {
            sum_temps_tour= sum_temps_tour+c.getTournee_attribuees().get(i).calculer_temps_tournee();
            
            i++;
            //System.out.println("sumtemp : "+ sum_temps_tour);
        }
        if (sum_temps_tour==this.getAntSystem().getTime_slice())
        {
            this.setTemps_total_time_slice(this.getTemps_total_time_slice()+sum_temps_tour);
           
           
        }
        else
        {
        int j=0;
        
        sum_temps_tour= sum_temps_tour-c.getTournee_attribuees().get(i-1).calculer_temps_tournee();
         
        sum_temps_tour= sum_temps_tour+ this.getProblem().gettimes(0, c.getTournee_attribuees().get(i-1).getCustomers().get(0).getId())+this.getAntSystem().getTemps_service();
         
        while (sum_temps_tour< this.getAntSystem().getTime_slice())
        {
            j++;
            sum_temps_tour=sum_temps_tour+this.getProblem().gettimes(c.getTournee_attribuees().get(i-1).getCustomers().get(j-1).getId(),c.getTournee_attribuees().get(i-1).getCustomers().get(j).getId())+this.getAntSystem().getTemps_service();
            
           
             
        }
        int id_cl_fic=c.getTournee_attribuees().get(i-1).getCustomers().get(j).getId();
       
       
            
       
       this.setTemps_total_time_slice(this.getTemps_total_time_slice()+sum_temps_tour);
       
        //System.out.println("id depot fictif à ajouter "+id_cl_fic); 
        
        }
    }
    return this.getTemps_total_time_slice();
}

public double get_depo_fic_dynamic_ant ()
{
   
    this.setTemps_total_time_slice(0);
   
    for(int k=1 ;k<=this.getCamions().size(); k++)
    {
        Camion c = this.getCamions().get(k-1);
        double sum_temps_tour=0;
        int i=0;
        if (c.calucler_temps_total_tournees_dynamic()<=this.getAntSystem().getTime_slice())
        {
            return c.calucler_temps_total_tournees_dynamic();
        }
        while ((sum_temps_tour < this.getAntSystem().getTime_slice())&& (c.getTournee_attribuees().size()>i))
        {
            sum_temps_tour= sum_temps_tour+c.getTournee_attribuees().get(i).calculer_temps_tournee_dynamic();
            i++;
        }
        if (sum_temps_tour==this.getAntSystem().getTime_slice()){ //&& (c.getTournee_attribuees().size()>j)){
               // for (int l=0; l<c.getTournee_attribuees().get(j).getCustomers().size();l++)
               this.setTemps_total_time_slice(this.getTemps_total_time_slice()+sum_temps_tour);
               
            }
        else
        {
            if (c.getTournee_attribuees().get(i-1).getId_fictif()==0)
            {
                if (c.getTournee_attribuees().get(i-1).getCustomers().size()<2 )
                {
                    sum_temps_tour= sum_temps_tour-c.getTournee_attribuees().get(i-1).calculer_temps_tournee_dynamic();
                    if ((sum_temps_tour= sum_temps_tour+this.getProblem_dynamic().gettimes_dynamic(0, c.getTournee_attribuees().get(i-1).getCustomers().get(0).getId())+this.getAntSystem().getTemps_service())> this.getAntSystem().getTime_slice())
                    {
                        
                       this.setTemps_total_time_slice(this.getTemps_total_time_slice()+sum_temps_tour);
                          
                    }
                    else {this.setTemps_total_time_slice(this.getTemps_total_time_slice()+sum_temps_tour+this.getProblem_dynamic().gettimes_dynamic(c.getTournee_attribuees().get(i-1).getCustomers().get(0).getId(),0));}
                }
                else
                {
                int j=0;
       
        sum_temps_tour= sum_temps_tour-c.getTournee_attribuees().get(i-1).calculer_temps_tournee_dynamic();
        sum_temps_tour= sum_temps_tour+ this.getProblem_dynamic().gettimes_dynamic(0, c.getTournee_attribuees().get(i-1).getCustomers().get(0).getId())+this.getAntSystem().getTemps_service();
          
        while (sum_temps_tour< this.getAntSystem().getTime_slice())
        {
            j++;
            sum_temps_tour=sum_temps_tour+this.getProblem_dynamic().gettimes_dynamic(c.getTournee_attribuees().get(i-1).getCustomers().get(j-1).getId(),c.getTournee_attribuees().get(i-1).getCustomers().get(j).getId())+this.getAntSystem().getTemps_service();
         
           
             
        }
        
        this.setTemps_total_time_slice(this.getTemps_total_time_slice()+sum_temps_tour);
       
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
         

        int from_client_fictif= d.getId_client_fictif();
        sum_temps_tour= sum_temps_tour-c.getTournee_attribuees().get(i-1).calculer_temps_tournee_dynamic();
        sum_temps_tour= sum_temps_tour+ this.getProblem_dynamic().gettimes_dynamic(from_client_fictif, c.getTournee_attribuees().get(i-1).getCustomers().get(0).getId())+this.getAntSystem().getTemps_service();
        
        while ((sum_temps_tour< this.getAntSystem().getTime_slice())&&(c.getTournee_attribuees().get(i-1).getCustomers().size()>1) )
        {
            j++;
            sum_temps_tour=sum_temps_tour+this.getProblem_dynamic().gettimes_dynamic(c.getTournee_attribuees().get(i-1).getCustomers().get(j-1).getId(),c.getTournee_attribuees().get(i-1).getCustomers().get(j).getId())+this.getAntSystem().getTemps_service();
         
           
                
        }
       
            
            
       
        
        this.setTemps_total_time_slice(this.getTemps_total_time_slice()+sum_temps_tour);
     
        }
            else {this.setTemps_total_time_slice(this.getTemps_total_time_slice()+sum_temps_tour); }   
            }
        }
    }
    return this.getTemps_total_time_slice();
    
}

   
}
