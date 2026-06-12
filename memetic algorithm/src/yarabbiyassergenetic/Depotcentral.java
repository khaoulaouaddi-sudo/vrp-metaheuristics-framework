/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yarabbiyassergenetic;

/**
 *
 * @author asus
 */
public class Depotcentral {
    String namedepot;
        double Xdepot;
        double Ydepot;
	
	int id_depot;

    public Depotcentral(int id_depot,String namedepot, double Xdepot, double Ydepot) {
        this.namedepot = namedepot;
        this.Xdepot = Xdepot;
        this.Ydepot = Ydepot;
        this.id_depot = id_depot;
    }

    public String getNamedepot() {
        return namedepot;
    }

    public void setNamedepot(String namedepot) {
        this.namedepot = namedepot;
    }

    public double getXdepot() {
        return Xdepot;
    }

    public void setXdepot(double Xdepot) {
        this.Xdepot = Xdepot;
    }

    public double getYdepot() {
        return Ydepot;
    }

    public void setYdepot(double Ydepot) {
        this.Ydepot = Ydepot;
    }

    public int getId_depot() {
        return id_depot;
    }

    public void setId_depot(int id_depot) {
        this.id_depot = id_depot;
    }
        
    
}
