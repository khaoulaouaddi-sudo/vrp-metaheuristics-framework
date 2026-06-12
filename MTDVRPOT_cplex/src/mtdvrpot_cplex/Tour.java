/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mtdvrpot_cplex;

import java.util.ArrayList;

/**
 *
 * @author asus
 */
public  class Tour implements Cloneable {
    private ArrayList<Customer> customers = new ArrayList<>();
    int id_tour;
    private Ourdynamicmodel ant;
    private double temps_tour;
    private double distance_tour;
    private double current_capacity;
    private boolean tournee_terminee=false;
    Camion c;
    int id_fictif;

    public Ourdynamicmodel getAnt() {
        return ant;
    }

    public void setAnt(Ourdynamicmodel ant) {
        this.ant = ant;
    }

    public double getDistance_tour() {
        return distance_tour;
    }

    public void setDistance_tour(double distance_tour) {
        this.distance_tour = distance_tour;
    }

    public Camion getC() {
        return c;
    }

    public void setC(Camion c) {
        this.c = c;
    }
    
    

    public int getId_fictif() {
        return id_fictif;
    }

    public void setId_fictif(int id_fictif) {
        this.id_fictif = id_fictif;
    }
    
    

    public boolean isTournee_terminee() {
        return tournee_terminee;
    }

    public void setTournee_terminee(boolean tournee_terminee) {
        this.tournee_terminee = tournee_terminee;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public int getId_tour() {
        return id_tour;
    }

    public void setId_tour(int id_tour) {
        this.id_tour = id_tour;
    }

   
    public double getTemps_tour() {
        return temps_tour;
    }

    public void setTemps_tour(double temps_tour) {
        this.temps_tour = temps_tour;
    }

    public double getCurrent_capacity() {
        return current_capacity;
    }

    public void setCurrent_capacity(double current_capacity) {
        this.current_capacity = this.current_capacity-current_capacity;
    }
    public void set_capacity_initial(double current_capacity) {
        this.current_capacity = current_capacity;
    }
    
    
    //private 
    public  double calculer_temps_tournee(){
        if (this.getCustomers().isEmpty()) { this.setTemps_tour(0);this.setDistance_tour(0);return 0;}
        else
        {
        //System.out.println("hooooooooooooooooooo"+this.getCustomers().get(0).getId());
        double temps= this.getAnt().getProblem().gettimes(0, this.getCustomers().get(0).getId())+
                this.getAnt().getTemps_service();
        // System.out.println("Waaaaa3 9bel men la boucle dyal calcul :  "+temps);
        double distance=this.getAnt().getProblem().getDistances(0, this.getCustomers().get(0).getId());
       // if (this.this.getCustomers().size())
           
      
        for (int i=1; i< this.getCustomers().size();i++)
            
        { 
             //System.out.println("hooooooooooooooooooo"+this.getCustomers().get(i).getId());
           //System.out.println("Waaaaa3 men weset la boucle dyal calcul :  "+temps);
            temps= temps+this.getAnt().getProblem().gettimes(this.getCustomers().get(i-1).getId(), this.getCustomers().get(i).getId())+this.getAnt().getTemps_service();
            distance=distance+this.getAnt().getProblem().getDistances(this.getCustomers().get(i-1).getId(), this.getCustomers().get(i).getId());
        }
        //System.out.println("Waaaaa3"+temps);
        
        temps=temps+this.getAnt().getProblem().gettimes(this.getCustomers().get(this.getCustomers().size()-1).getId(),0);
        distance=distance+this.getAnt().getProblem().getDistances(this.getCustomers().get(this.getCustomers().size()-1).getId(),0);
        
          this.setTemps_tour(temps); 
          this.setDistance_tour(distance);
        return temps;
        }
    }
   
    public  double calculer_temps_tournee_tour(Tour t){
        if (t.getCustomers().isEmpty()) {t.setDistance_tour(0);return 0;}
        else
        {
        
        double temps= t.getAnt().getProblem().gettimes(0, t.getCustomers().get(0).getId())+this.getAnt().getTemps_service();
        double distance=t.getAnt().getProblem().getDistances(0, this.getCustomers().get(0).getId());
       // if (this.this.getCustomers().size())
           
      
        for (int i=1; i< t.getCustomers().size();i++)
            
        { 
             //System.out.println("hooooooooooooooooooo"+this.getCustomers().get(i).getId());
            temps= temps+t.getAnt().getProblem().gettimes(t.getCustomers().get(i-1).getId(), t.getCustomers().get(i).getId())+this.getAnt().getTemps_service();
           distance=distance+t.getAnt().getProblem().getDistances(this.getCustomers().get(this.getCustomers().size()-1).getId(),0);
        }
        //System.out.println("Waaaaa3"+temps);
        
        temps=temps+this.getAnt().getProblem().gettimes(this.getCustomers().get(this.getCustomers().size()-1).getId(),0);
       distance=distance+t.getAnt().getProblem().getDistances(this.getCustomers().get(this.getCustomers().size()-1).getId(),0);
         t.setDistance_tour(distance);   
        return temps;
        }
    }
    public  double calculer_temps_tournee_dynamic(){
        if (this.getCustomers().isEmpty()) {
            
            if (this.getId_fictif()==0 )
            {
            this.setTemps_tour(0);
            this.setDistance_tour(0);
            return 0;}
            else {
                 Depotfictif d=this.getAnt().getProblem_dynamic().getdepotfictifById_dynamic(this.getId_fictif());
               int id_cl_fict=d.getId_client_fictif();
               this.setTemps_tour( this.getAnt().getProblem_dynamic().gettimes_dynamic(id_cl_fict, 0)); 
               this.setDistance_tour(this.getAnt().getProblem_dynamic().getDistances_dynamic(id_cl_fict, 0));
               return this.getTemps_tour();
       
                
            }
                }
        else
        {
          double temps=0 ;
          double distance=0 ;
        if(this.getId_fictif()==0)
        {
           // System.out.println("this.getant:"+this.getAnt());
        temps= this.getAnt().getProblem_dynamic().gettimes_dynamic(0, this.getCustomers().get(0).getId())+this.getAnt().getTemps_service();
        distance=this.getAnt().getProblem_dynamic().getDistances_dynamic(0, this.getCustomers().get(0).getId());
        }
        else
        {
            Depotfictif d=this.getAnt().getProblem_dynamic().getdepotfictifById_dynamic(this.getId_fictif());
            int id_cl_fict=d.getId_client_fictif();
        temps= this.getAnt().getProblem_dynamic().gettimes_dynamic(id_cl_fict, this.getCustomers().get(0).getId())+this.getAnt().getTemps_service();    
        distance=this.getAnt().getProblem_dynamic().getDistances_dynamic(id_cl_fict, this.getCustomers().get(0).getId());
        }
      
        for (int i=1; i< this.getCustomers().size();i++)
            
        { 
             //System.out.println("hooooooooooooooooooo"+this.getCustomers().get(i).getId());
            temps= temps+this.getAnt().getProblem_dynamic().gettimes_dynamic(this.getCustomers().get(i-1).getId(), this.getCustomers().get(i).getId())+this.getAnt().getTemps_service();
            distance=distance+this.getAnt().getProblem_dynamic().getDistances_dynamic(this.getCustomers().get(i-1).getId(), this.getCustomers().get(i).getId());
        }
        //System.out.println("Waaaaa3"+temps);
        
        temps=temps+this.getAnt().getProblem_dynamic().gettimes_dynamic(this.getCustomers().get(this.getCustomers().size()-1).getId(),0);
        distance=distance+this.getAnt().getProblem_dynamic().getDistances_dynamic(this.getCustomers().get(this.getCustomers().size()-1).getId(),0);
        this.setDistance_tour(distance);
         this.setTemps_tour(temps);  
        return temps;
        }
    }
   
    public  double calculer_temps_tournee_dynamic_tour(Tour t){
        if (t.getCustomers().isEmpty()) {
            t.setTemps_tour(0);
            return 0;}
        else
        {
          double temps=0 ; 
        if(t.getId_fictif()==0)
        {
        temps= t.getAnt().getProblem_dynamic().gettimes_dynamic(0, t.getCustomers().get(0).getId())+this.getAnt().getTemps_service();
        }
        else
        {
            Depotfictif d=t.getAnt().getProblem_dynamic().getdepotfictifById_dynamic(t.getId_fictif());
            int id_cl_fict=d.getId_client_fictif();
        temps= t.getAnt().getProblem_dynamic().gettimes_dynamic(id_cl_fict, t.getCustomers().get(0).getId())+this.getAnt().getTemps_service();    
        }
      
        for (int i=1; i< t.getCustomers().size();i++)
            
        { 
             //System.out.println("hooooooooooooooooooo"+this.getCustomers().get(i).getId());
            temps= temps+t.getAnt().getProblem_dynamic().gettimes_dynamic(t.getCustomers().get(i-1).getId(), t.getCustomers().get(i).getId())+this.getAnt().getTemps_service();
           
        }
        //System.out.println("Waaaaa3"+temps);
       
        temps=temps+t.getAnt().getProblem_dynamic().gettimes_dynamic(t.getCustomers().get(t.getCustomers().size()-1).getId(),0);
       
         t.setTemps_tour(temps);  
        return temps;
        }
    }

    
    public Tour(int numtour, double current_cap,int id_fictif,Ourdynamicmodel o) {
        //this.customers=liste_client_tournee;
       this.setAnt(o);
        this.setId_tour(numtour);
        this.set_capacity_initial(current_cap);
        this.setTournee_terminee(false);
        this.id_fictif=id_fictif;
       
        
    }
  
     public void addCustomer_tour( Customer c) {
       
            this.getCustomers().add(c);
       
    }
          
  
  

   
}
