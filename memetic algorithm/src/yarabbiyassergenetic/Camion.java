package yarabbiyassergenetic;


import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author asus
 */
public class Camion implements Cloneable{
    public int idcamion;
    
    public double temps_tournees_avant;
    public double sum_temps_tour;
    public int id_fictif_final=0;
    
    public ArrayList<Tour> tournee_attribuees=new ArrayList<Tour>();
    double temps_total_tournees;
    double capacity;
    double cost; 

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    

    public double getCapacity() {
        
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }
    
   
     public Depotfictif depot_fictif_final;
    //public double sum_temps_tour;

    

    public Depotfictif getDepot_fictif_final() {
        return depot_fictif_final;
    }

    public void setDepot_fictif_final(Depotfictif depot_fictif_final) {
        this.depot_fictif_final = depot_fictif_final;
    }
    

    public double getSum_temps_tour() {
        return sum_temps_tour;
    }

    public void setSum_temps_tour(double sum_temps_tour) {
        this.sum_temps_tour = sum_temps_tour;
    }
    

  

    public int getId_fictif_final() {
        return id_fictif_final;
    }

    public void setId_fictif_final(int id_fictif_final) {
        this.id_fictif_final = id_fictif_final;
    }
    
    public double getTemps_tournees_avant() {
        return temps_tournees_avant;
    }

    public void setTemps_tournees_avant(double temps_tournees_avant) {
        this.temps_tournees_avant = temps_tournees_avant;
    }

    
    
    

    public Camion(int idcamion) {
        this.idcamion = idcamion;
        //this.ant = ant;
        this.tournee_attribuees.clear();
        this.setTemps_tournees_avant(0);
  
    }
    

    public int getIdcamion() {
        return idcamion;
    }

    public void setIdcamion(int idcamion) {
        this.idcamion = idcamion;
    }

   

    public ArrayList<Tour> getTournee_attribuees() {
        return tournee_attribuees;
    }

    public void setTournee_attribuees(ArrayList<Tour> tournee_attribuees) {
        this.tournee_attribuees = tournee_attribuees;
    }

    public double getTemps_total_tournees() {
        return temps_total_tournees;
    }

    public void setTemps_total_tournees(double temps_total_tournees) {
        this.temps_total_tournees = temps_total_tournees;
    }
    
    
    public double calucler_temps_total_tournees(){
        double T=0;
        for (Tour t:this.getTournee_attribuees())
        {
            T=T+t.getTemps();
        }
        return T;
    }
    public double calucler_temps_total_tournees_dynamic(){
        double T=0;
        for (Tour t:this.tournee_attribuees)
        {
            T=T+t.getTemps_dynamic();
        }
        return T;
    }
  
    @Override
   public Camion clone() {
        Camion o = null;
        try {
                // On récupère l'instance à renvoyer par l'appel de la 
                // méthode super.clone()
                o = (Camion) super.clone();
                o.capacity=capacity;
                o.depot_fictif_final=depot_fictif_final;
                o.id_fictif_final=id_fictif_final;
                o.sum_temps_tour=sum_temps_tour;
                o.temps_total_tournees=temps_total_tournees;
                o.temps_tournees_avant=temps_tournees_avant;
                //o.tourGroup = tourGroup;
                //o.tournee_attribuees = (ArrayList<Tour>) tournee_attribuees.clone();
                
                            
            } catch(CloneNotSupportedException cnse) {
                // Ne devrait jamais arriver car nous implémentons 
                // l'interface Cloneable
                cnse.printStackTrace(System.err);
        }
        // on renvoie le clone
        return o;
    }
    public Camion clone_dynamic() {
        Camion o = null;
        try {
                // On récupère l'instance à renvoyer par l'appel de la 
                // méthode super.clone()
                o = (Camion) super.clone();
                //o.tourGroup = tourGroup;
                o.tournee_attribuees = (ArrayList<Tour>) tournee_attribuees.clone();
                o.sum_temps_tour=sum_temps_tour;
                o.depot_fictif_final=depot_fictif_final;
                o.id_fictif_final=id_fictif_final;
                o.idcamion=idcamion;
                o.temps_tournees_avant=temps_tournees_avant;
                o.temps_total_tournees=temps_total_tournees;
               
                
                
                            
            } catch(CloneNotSupportedException cnse) {
                // Ne devrait jamais arriver car nous implémentons 
                // l'interface Cloneable
                cnse.printStackTrace(System.err);
        }
        // on renvoie le clone
        return o;
    }
}

