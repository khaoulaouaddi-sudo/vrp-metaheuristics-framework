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
public class VRPS {  
    public int nbCities;
    //private int tempsChargement = 0;
    public double maxTemps;
    public int nombrecamions;
    public int capacitycamion;
    public Depotcentral depot ;
    public ArrayList<Camion> camions = new ArrayList<Camion>();
    public ArrayList<Customer> customers = new  ArrayList<Customer>();
   // public int vitesse;
    public double coef_distance_parcourue;
    public double coef_overtime;
    public double[][] distances;
   

   
    
    //private double [][] nearCustomers;

    public double[][] times;
    public double Overtimepermis;

    public double getCoef_distance_parcourue() {
        return coef_distance_parcourue;
    }

    public void setCoef_distance_parcourue(double coef_distance_parcourue) {
        this.coef_distance_parcourue = coef_distance_parcourue;
    }

    public double getCoef_overtime() {
        return coef_overtime;
    }

    public void setCoef_overtime(double coef_overtime) {
        this.coef_overtime = coef_overtime;
    }
    

   
    public double[][] getDistances() {
        return distances;
    }

    public void setDistances(double[][] distances) {
        this.distances = distances;
    }

    public double[][] getTimes() {
        return times;
    }

    public void setTimes(double[][] times) {
        this.times = times;
    }

    public double getOvertimepermis() {
        return Overtimepermis;
    }

    public void setOvertimepermis(double Overtimepermis) {
        this.Overtimepermis = Overtimepermis;
    }
     
    public void addCustomer(Customer c) {
        if (customers == null) {
            customers = new ArrayList<Customer>();
        }
        customers.add(c);
    } 
     
    public VRPS( ArrayList<Customer> customers, double maxTemps, Depotcentral depot,  int nombrecamions, int capacitycamion,double Overtimepermis) {
       
        this.setCustomers(customers);
        this.setNbCities(customers.size());
        this.setDepot(depot);
        this.setMaxTemps(maxTemps);
        this.setNombrecamions(nombrecamions);
        this.setCapacitycamion(capacitycamion);
        this.setOvertimepermis(Overtimepermis);
        
       
        this.setCoef_distance_parcourue(1);
        this.setCoef_overtime(1);
        
    }
    
   public Customer getCustomerById(int id) {
        return customers.get(id- customers.get(0).getId());
    }
    public int getNbCities() {
        return nbCities;
    }

    public void setNbCities(int nbCities) {
        this.nbCities = nbCities;
    }

    

    public double getMaxTemps() {
        return maxTemps;
    }

    public void setMaxTemps(double maxTemps) {
        this.maxTemps = maxTemps;
    }

    public int getNombrecamions() {
        return nombrecamions;
    }

    public void setNombrecamions(int nombrecamions) {
        this.nombrecamions = nombrecamions;
    }

    public int getCapacitycamion() {
        return capacitycamion;
    }

    public void setCapacitycamion(int capacitycamion) {
        this.capacitycamion = capacitycamion;
    }

    public Depotcentral getDepot() {
        return depot;
    }

    public void setDepot(Depotcentral depot) {
        this.depot = depot;
    }

    public ArrayList<Camion> getCamions() {
        return camions;
    }

    public void setCamions(ArrayList<Camion> camions) {
        this.camions = camions;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
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
    
    
    
   
}
