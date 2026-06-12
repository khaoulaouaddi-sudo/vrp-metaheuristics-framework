/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yarabbiyassergenetic;

import java.util.ArrayList;

/**
 *
 * @author Ayadi
 */
public class Trip implements Comparable<Trip>{
    
    Camion camion;
        ArrayList<Tour> tours;
        TourGroup_Individual1 tourGroup;

        public Trip(Camion camion, ArrayList<Tour> tours, TourGroup_Individual1 tourGroup) {
            this.camion = camion;
            this.tours = tours;
            this.tourGroup = tourGroup;
        }

        public Trip(TourGroup_Individual1 tourGroup) {
            this.tourGroup = tourGroup;
            tours = new ArrayList<Tour>();
        }
        
        public Camion getCamion() {
            return camion;
        }

        public ArrayList<Tour> getTours() {
            return tours;
        }
        
        public Tour getTour(int i) {
            return tours.get(i);
        }

        public void addTour(Tour tour) {
            tours.add(tour);
        }

        public TourGroup_Individual1 getTourGroup_Individual1() {
            return tourGroup;
        }
        
        public void removeCamion() {
            Camion oldCamion = this.getCamion();
            if (oldCamion == null) {
                return;
            }

            this.camion = null;
//            tourGroup.removeCamionFromCamionsToUse(camion);
        }
        
        public boolean CheckCustomerInTrip(Customer customer) {
        for (Tour tour : this.getTours()) {
            if (tour.CheckCustomerInTour(customer) == true) {
                return true;
            }
        }
        return false;
    }
        
    public double getTemps() {
        double time = 0;
        for (Tour tour : this.getTours()) {
                time += tour.getTemps();
            }
                
        return time;
    }
    public double getTemps_dynamic() {
        double time = 0;
        for (Tour tour : this.getTours()) {
                time += tour.getTemps_dynamic();
            }
                
        return time;
    }
        
    public double size() { 
        return tours.size();
    }
    
    public String toString() {
        if (this == null) {
            return "vide";
        }
        return "\n   {Trip:"
                + ", camion=" + this.getCamion()
                + ", Temps=" + getTemps()
                + "," + "\n" + "    tours=" + this.getTours()
                + "}";
    }

    @Override
    public int compareTo(Trip other) {
        return (int) (this.getTemps()-other.getTemps());
    }
     public int compareTo_dynamic(Trip other) {
        return (int) (this.getTemps_dynamic()-other.getTemps_dynamic());
    }
    
}
