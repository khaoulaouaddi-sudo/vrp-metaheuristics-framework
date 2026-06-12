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
public class VRP_total {
   // public ArrayList<VRPD> VRPD_dynamic = new  ArrayList<VRPD>();
    // public VRPS VRPStatiq ;
     ArrayList<Customer> clients_total= new ArrayList<Customer>();
     public double[][] distances;
     
     public double[][] times;
     public double vitesse;
     public Depotcentral depot ;

    public VRP_total(ArrayList<Customer> clients_total, Depotcentral depot,double vitesse) {
        //this.VRPStatiq = VRPStatiq;
        //this.VRPD_dynamic=VRPD_dynamic;
        this.clients_total=clients_total;
         this.times = new double[clients_total.size() + 1][clients_total.size() + 1]; 
        this.depot=depot;
         this.vitesse= vitesse;
         
            this.distances = new double[clients_total.size() + 1][clients_total.size() + 1];
            this.calculer_distance_temps_total(clients_total);
         
       
    }
    
    
     public void addCustomer(Customer c) {
        if (clients_total == null) {
           clients_total = new ArrayList<Customer>();
        }
        clients_total.add(c);
    }

    public double getVitesse() {
        return vitesse;
    }

    public void setVitesse(double vitesse) {
        this.vitesse = vitesse;
    }

    public Depotcentral getDepot() {
        return depot;
    }

    public void setDepot(Depotcentral depot) {
        this.depot = depot;
    }
     
     public double getDistances(int i, int j) {
        return distances[i][j];
    }
    public void setDistance(int i, int j, double v) {
        distances[i][j] = v;
        distances[j][i] = v;
        
    }
    public double gettimes(int i, int j) {
        return times[i][j];
    }
    public void settime(int i, int j, double v) {
        times[i][j] = v;
        times[j][i] = v;
    }
 public void calculer_distance_temps_total (ArrayList<Customer> customers) 
    {
        int i=0;
        double temps;
     for (Customer currentCustomer_1 : customers) {
            i++;
            Customer c1 = currentCustomer_1;
           // System.out.println("ha les clients    :   " +this.depot.getXdepot());
            double distance = Math.sqrt(Math.pow(c1.getX() - this.depot.getXdepot(), 2) + Math.pow(c1.getY() - this.depot.getYdepot(), 2));
            this.setDistance(0, i, distance);
             temps= distance/this.vitesse;
            this.settime(0, i, temps);
            int j = 0;
            for (Customer currentCustomer_2 : customers) {
                j++;
                Customer c2 = currentCustomer_2;
                distance = Math.sqrt(Math.pow(c2.getX() - c1.getX(), 2) + Math.pow(c2.getY() - c1.getY(), 2));
                this.setDistance(i, j, distance);
                temps= distance/this.vitesse;
                this.settime(i, j, temps);
            }
        }   
    }
    
    public double[][] getTimes() {
        return times;
    }

    public void setTimes(double[][] times) {
        this.times = times;
    }
     

  

    public ArrayList<Customer> getClients_total() {
        return clients_total;
    }

    public void setClients_total(ArrayList<Customer> clients_total) {
        this.clients_total = clients_total;
    }

    public double[][] getDistances() {
        return distances;
    }

    public void setDistances(double[][] distances) {
        this.distances = distances;
    }

  
     
    
}
