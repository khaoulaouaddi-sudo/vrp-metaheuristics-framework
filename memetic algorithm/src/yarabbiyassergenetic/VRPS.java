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
public class VRPS {
     
    
    public double maxTemps;
   
    public double MaxCapacity;
    public Depotcentral depot ;
    public ArrayList<Camion> camions = new ArrayList<Camion>();
    public ArrayList<Customer> customers = new  ArrayList<Customer>();
    public VRPT VRPtotal ;
    public double overtime;
    public double PenaltyOvertime;
    public double NbTripsByVehicle;
    private double costCamion;
    private int nbImprove =50;
    
    public double[][] distances;
     public double[][] times;
     public double[][] nearCustomers;
    private String Objective1 = "LTR";
private String Objective2 = "DIST";

    public int getNbImprove() {
        return nbImprove;
    }

    public void setNbImprove(int nbImprove) {
        this.nbImprove = nbImprove;
    }


    public double[][] getNearCustomers() {
        return nearCustomers;
    }

    public void setNearCustomers(double[][] nearCustomers) {
        this.nearCustomers = nearCustomers;
    }

    public String getObjective1() {
        return Objective1;
    }

    public void setObjective1(String Objective1) {
        this.Objective1 = Objective1;
    }

    public String getObjective2() {
        return Objective2;
    }

    public void setObjective2(String Objective2) {
        this.Objective2 = Objective2;
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
    
    public double[][] getTimes() {
        return times;
    }

    public void setTimes(double[][] times) {
        this.times = times;
    }

    public double getCostCamion() {
        return costCamion;
    }

    public void setCostCamion(double costCamion) {
        this.costCamion = costCamion;
    }
     
     

    public double getOvertime() {
        return overtime;
    }

    public void setOvertime(double overtime) {
        this.overtime = overtime;
    }

    public double getPenaltyOvertime() {
        return PenaltyOvertime;
    }

    public void setPenaltyOvertime(double PenaltyOvertime) {
        this.PenaltyOvertime = PenaltyOvertime;
    }

    public double getNbTripsByVehicle() {
        return NbTripsByVehicle;
    }

    public void setNbTripsByVehicle(double NbTripsByVehicle) {
        this.NbTripsByVehicle = NbTripsByVehicle;
    }
    
    public Customer getCustomerById(int id) {
        return customers.get(id-1);
    }

    public double getMaxTemps() {
        return maxTemps;
    }

    public void setMaxTemps(double maxTemps) {
        this.maxTemps = maxTemps;
    }

    public double getMaxCapacity() {
        return MaxCapacity;
    }

    public void setMaxCapacity(double capacitycamion) {
        this.MaxCapacity = capacitycamion;
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

    public VRPT getVRPtotal() {
        return VRPtotal;
    }

    public void setVRPtotal(VRPT VRPtotal) {
        this.VRPtotal = VRPtotal;
    }

    public double[][] getDistances() {
        return distances;
    }

    public void setDistances(double[][] distances) {
        this.distances = distances;
    }
   
    public double gettime(int i, int j) {
        return times[i][j];}
    
    public double getTemps(Tour tour, Customer customer) {
            double time = 0;
            if(tour != null && tour.size() > 0){
                time= tour.getTemps();
                time -= this.gettime(tour.getLastCustomer().getId(),0);
                time += this.gettime(tour.getLastCustomer().getId(), customer.getId());
                time += this.gettime(customer.getId(),0);
                time += this.getVRPtotal().temps_service;
            }
            else
                time = 2*this.gettime(customer.getId(),0)+this.getVRPtotal().temps_service;
            return time;
        }
       public double getTemps(Tour firstTour, Tour secondTour) {
            if(firstTour.size()==0)
                return secondTour.getTemps();
            if(secondTour.size()==0)
                return firstTour.getTemps();
            return firstTour.getTemps()
                    + secondTour.getTemps()
                    + this.gettime(firstTour.getLastCustomer().getId(), secondTour.getFirstCustomer().getId())
                    - this.gettime(firstTour.getLastCustomer().getId(),0)
                    - this.gettime(secondTour.getFirstCustomer().getId(),0);
        }
    
     public VRPS( VRPT VRPtotal, ArrayList<Customer> customers, double maxTemps, Depotcentral depot,  int nombrecamions, int capacitycamion, ArrayList<Camion> camions) {
       
        this.setCustomers(customers);
        this.setCamions(camions);
        this.setVRPtotal(VRPtotal);
        this.setDistances(this.getVRPtotal().getDistances());
        this.setTimes(this.getVRPtotal().getTimes());
        this.setDepot(depot);
        this.setMaxTemps(maxTemps);
        this.setMaxCapacity(capacitycamion);
        this.setOvertime(this.VRPtotal.getOvertime());
        this.setNearCustomers(this.getVRPtotal().getNearCustomers());
        
       
        
        
    }
    
}
