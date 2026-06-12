package yarabbiyassergenetic;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author asus
 */
public class Customer implements  Cloneable{
    
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
     @Override
      public Customer clone() {
        Customer o = null;
        try {
                // On récupère l'instance à renvoyer par l'appel de la 
                // méthode super.clone()
                o = (Customer) super.clone();
                o.X = X;
                o.Y= Y;
                o.demande= demande;
                o.id=id;
                 // o.setId_fictif(id_fictif);
                
            } catch(CloneNotSupportedException cnse) {
                // Ne devrait jamais arriver car nous implémentons 
                // l'interface Cloneable
                cnse.printStackTrace(System.err);
        }
        // on renvoie le clone
        return o;
    }  
        
	
	
        
        
    
}

    
    
    