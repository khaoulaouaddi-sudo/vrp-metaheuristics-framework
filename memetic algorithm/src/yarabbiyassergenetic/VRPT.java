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
public class VRPT {
    
    ArrayList<Customer> clients_total= new ArrayList<Customer>();
     public double[][] distances;
     
     public double[][] times;
     public double vitesse;
     public Depotcentral depot; 
     public double overtime;
     public double coef_distance_parcourue;
     public double coef_overtime;
     public double temps_service;
     public double[][] nearCustomers;
     public double moyenneDistance;

    public double[][] getNearCustomers() {
        return nearCustomers;
    }

    public void setNearCustomers(double[][] nearCustomers) {
        this.nearCustomers = nearCustomers;
    }

    public double getMoyenneDistance() {
        return moyenneDistance;
    }

    public void setMoyenneDistance(double moyenneDistance) {
        this.moyenneDistance = moyenneDistance;
    }

    public double getTemps_service() {
        return temps_service;
    }

    public void setTemps_service(double temps_service) {
        this.temps_service = temps_service;
    }
     

    public double getOvertime() {
        return overtime;
    }

    public void setOvertime(double overtime) {
        this.overtime = overtime;
    }

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

    public VRPT(ArrayList<Customer> clients_total, Depotcentral depot,double vitesse, double overtime_permis, double coef_dis, double coef_overtime, double moyen) {
        //this.VRPStatiq = VRPStatiq;
        //this.VRPD_dynamic=VRPD_dynamic;
        this.clients_total=clients_total;
        this.setMoyenneDistance(moyen);
         this.times = new double[clients_total.size() + 1][clients_total.size() + 1]; 
        this.depot=depot;
         this.vitesse= vitesse;
         this.coef_distance_parcourue=coef_dis;
         this.coef_overtime=coef_overtime;
         this.nearCustomers = new double[clients_total.size()+1][clients_total.size()+1];
        for(int i=0;i<clients_total.size();i++)
            for(int j=i;j<clients_total.size();j++){
                nearCustomers[i][j]=0;
                nearCustomers[j][i]=0;
            }
         
            this.distances = new double[clients_total.size() + 1][clients_total.size() + 1];
            this.calculer_distance_temps_total(clients_total);
            this.overtime=overtime_permis;
         
       
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
        if(distances[i][j]<=this.moyenneDistance){
            nearCustomers[i][j] = 1;
            nearCustomers[j][i] = 1;
        }
        
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
     

  public boolean isNear(int idCustomer1, int idCustomer2) {
        //System.out.println(i+" "+j);
        //System.out.println(distances[i][j]);
        if(nearCustomers[idCustomer1][idCustomer2]==1)
            return true;
        else
            return false;
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
