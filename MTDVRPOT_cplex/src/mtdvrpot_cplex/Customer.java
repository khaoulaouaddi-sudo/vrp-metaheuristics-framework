/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mtdvrpot_cplex;


/**
 *
 * @author asus
 */
public class Customer {
        String name;
        double X;
        double Y;
	
	int id;
	double demande;
    public Customer( int id,String name,double X, double Y,   double demande) {
        this.name = name;
        this.X = X;
        this.Y = Y;
       
        this.id = id;
        this.demande = demande;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return X;
    }

    public void setX(double X) {
        this.X = X;
    }

    public double getY() {
        return Y;
    }

    public void setY(double Y) {
        this.Y = Y;
    }

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDemande() {
        return demande;
    }

    public void setDemande(double demande) {
        this.demande = demande;
    }
    
        
	
	
        
        
    
}
