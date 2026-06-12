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
public class Depotfictif {
       
     
	int id_depot_fictif;
        int id_client_fictif;
        double capacité_restante;
        public double temps_tournees_avant_depot;

    public double getTemps_tournees_avant_depot() {
        return temps_tournees_avant_depot;
    }

    public void setTemps_tournees_avant_depot(double temps_tournees_avant_depot) {
        this.temps_tournees_avant_depot = temps_tournees_avant_depot;
    }
        
        
    public double getCapacité_restante() {
        return capacité_restante;
    }

    public void setCapacité_restante(double capacité_restante) {
        this.capacité_restante = capacité_restante;
    }

    public int getId_client_fictif() {
        return id_client_fictif;
    }

    public void setId_client_fictif(int id_client_fictif) {
        this.id_client_fictif = id_client_fictif;
    }
        

    public Depotfictif(int id_client_fictif, double capacité, int id_depot_fictif) {
        
        
        this.capacité_restante=capacité;
        this.id_depot_fictif = id_depot_fictif;
        this.id_client_fictif=id_client_fictif;
    }
    

    public int getId_depot_fictif() {
        return id_depot_fictif;
    }

    public void setId_depot_fictif(int id_depot_fictif) {
        this.id_depot_fictif = id_depot_fictif;
    }
    
}
