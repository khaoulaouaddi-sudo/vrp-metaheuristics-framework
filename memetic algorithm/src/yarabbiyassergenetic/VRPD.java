

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author asus
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yarabbiyassergenetic;

import java.util.ArrayList;

/**
 *
 * @author asus
 */
public class VRPD {
    //public int nonmbre_total_clients;
   // public int nombre_nouveaux_clients;
    //private int tempsChargement = 0;
    public double maxTemps_dynamic;
    public int NbImprove;
    public int nombrecamions_dynamic;
    public int nombre_depot_fictifs;
    public double capacitycamion_dynamic;
    public double temps_service;
    public Depotcentral depot_central ;
    public ArrayList<Camion> camions_dynamic = new ArrayList<Camion>();
    public ArrayList<Customer> customers_dynamic = new  ArrayList<Customer>();
    // public ArrayList<Customer> clients_old = new  ArrayList<Customer>();
    public ArrayList<Depotfictif> depots_fictif = new  ArrayList<Depotfictif>();
    public int vitesse;
    public double coef_distance_parcourue;
    public double coef_overtime;
    public double[][] distances_dynamic;
    public double ovetime_dynamic;
    public double[][] times_dynamic;
     VRPT VRPT;

    public VRPT getVRPT() {
        return VRPT;
    }

    public void setVRPT(VRPT VRPT) {
        this.VRPT = VRPT;
    }

    public int getNbImprove() {
        return NbImprove;
    }

    public void setNbImprove(int nbimprove) {
        this.NbImprove = nbimprove;
    }

   
    

    public double[][] getTimes_dynamic() {
        return times_dynamic;
    }

    public void setTimes_dynamic(double[][] times_dynamic) {
        this.times_dynamic = times_dynamic;
    }
    

    public double[][] getDistances_dynamic() {
        return distances_dynamic;
    }

    public void setDistances_dynamic(double[][] distances_dynamic) {
        this.distances_dynamic = distances_dynamic;
    }

    
    public int getNombre_depot_fictifs() {
        return nombre_depot_fictifs;
    }

    public void setNombre_depot_fictifs(int nombre_depot_fictifs) {
        this.nombre_depot_fictifs = nombre_depot_fictifs;
    }
    

    

    

    public double getMaxTemps_dynamic() {
        return maxTemps_dynamic;
    }

    public void setMaxTemps_dynamic(double maxTemps_dynamic) {
        this.maxTemps_dynamic = maxTemps_dynamic;
    }

    public int getNombrecamions_dynamic() {
        return nombrecamions_dynamic;
    }

    public void setNombrecamions_dynamic(int nombrecamions_dynamic) {
        this.nombrecamions_dynamic = nombrecamions_dynamic;
    }

    public double getCapacitycamion_dynamic() {
        return capacitycamion_dynamic;
    }

    public void setCapacitycamion_dynamic(int capacitycamion_dynamic) {
        this.capacitycamion_dynamic = capacitycamion_dynamic;
    }

    public Depotcentral getDepot_central() {
        return depot_central;
    }

    public void setDepot_central(Depotcentral depot_central) {
        this.depot_central = depot_central;
    }

    public ArrayList<Camion> getCamions_dynamic() {
        return camions_dynamic;
    }

    public void setCamions_dynamic(ArrayList<Camion> camions_dynamic) {
        this.camions_dynamic = camions_dynamic;
    }

    public ArrayList<Customer> getCustomers_dynamic() {
        return customers_dynamic;
    }

    public void setCustomers_dynamic(ArrayList<Customer> customers_dynamic) {
        this.customers_dynamic = customers_dynamic;
    }

    public ArrayList<Depotfictif> getDepots_fictif() {
        return depots_fictif;
    }

    public void setDepots_fictif(ArrayList<Depotfictif> depots_fictif) {
        this.depots_fictif = depots_fictif;
    }

    public int getVitesse() {
        return vitesse;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
        
    }

    public double getTemps_service() {
        return temps_service;
    }

    public void setTemps_service(double temps_service) {
        this.temps_service = temps_service;
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

    
    public Customer getCustomerById_dynamic(int id) {
        int j=-1;
            for (int i=0;i<this.customers_dynamic.size();i++)
            {
                if (id==this.customers_dynamic.get(i).getId())
                {
                    j=i;
                   
                }
                    
                    
            }
        return this.customers_dynamic.get(j);
    }
     public Depotfictif getdepotfictifById_dynamic(int id) {
        int j=-1;
        //System.out.println("mmmmmmmmmmmmmmmmm : "+ this.depots_fictif.size() );
            for (int i=0;i<this.depots_fictif.size();i++)
            {
                //System.out.println("mmmmmmmmmmmmmmmmm 222: "+ this.depots_fictif.get(i).getId_depot_fictif() );
                if (id==this.depots_fictif.get(i).getId_depot_fictif())
                {
                    j=i;
                   
                }
                    
                    
            }
           // System.out.println("haaa : "+ this.depots_fictif.size());
        return this.depots_fictif.get(j);
    }
   
     public Depotfictif getdepotfictifById_fictif_final(int id) {
        int j=-1;
        //System.out.println("mmmmmmmmmmmmmmmmm : "+ this.depots_fictif.size() );
            for (int i=0;i<this.depots_fictif.size();i++)
            {
                //System.out.println("mmmmmmmmmmmmmmmmm 222: "+ this.depots_fictif.get(i).getId_depot_fictif() );
                if (id==this.depots_fictif.get(i).getId_client_fictif())
                {
                    j=i;
                   
                }
                    
                    
            }
           // System.out.println("haaa : "+ this.depots_fictif.size());
        return this.depots_fictif.get(j);
    }
      public double getDistances_dynamic(int i, int j) {
        return distances_dynamic[i][j];
    }
    public void setDistance_dynamic(int i, int j, double v) {
        distances_dynamic[i][j] = v;
        distances_dynamic[j][i] = v;
        
    }
    public double gettimes_dynamic(int i, int j) {
        return times_dynamic[i][j];
    }
    public void settime_dynamic(int i, int j, double v) {
        times_dynamic[i][j] = v;
        times_dynamic[j][i] = v;
        
    }

    public double getOvetime_dynamic() {
        return ovetime_dynamic;
    }

    public void setOvetime_dynamic(double ovetime_dynamic) {
        this.ovetime_dynamic = ovetime_dynamic;
        
    }
    
    public void classer_customer(ArrayList<Customer> customers){
          boolean permut;
          Customer tampon1=null;
          Customer tampon2= null;
          do
          {
              permut= false;
              for (int i=0; i<customers.size()-1;i++)
              {
                if (customers.get(i).getDemande()<customers.get(i+1).getDemande())
                {
                    tampon1=customers.get(i);
                    tampon2=customers.get(i+1);
                    customers.set(i, tampon2);
                    customers.set(i+1, tampon1);
                    permut=true;
                }   
              }
          }while (permut);
      }
     public double getTemps_dynamic(Tour tour, Customer customer) {
            double time = 0;
            if(tour != null && tour.size() > 0){
                time= tour.getTemps_dynamic();
                time =time- this.gettimes_dynamic(tour.getLastCustomer().getId(),0);
                time =time+ this.gettimes_dynamic(tour.getLastCustomer().getId(), customer.getId());
                time =time+ this.gettimes_dynamic(customer.getId(),0);
                time =time+ this.temps_service;
            }
            else
                time = this.gettimes_dynamic(tour.getId_fictif(),customer.getId())
                        +this.gettimes_dynamic(customer.getId(),0)
                        +this.temps_service;
            return time;
        }
      public double getTemps_dynamic(Tour firstTour, Tour secondTour) {
            if((firstTour.size()==0)&&(firstTour.id_fictif==0))
                return secondTour.getTemps_dynamic();
            if((secondTour.size()==0)&& (secondTour.id_fictif==0))
                return firstTour.getTemps_dynamic();
            if (secondTour.id_fictif!=0)
                return -1;
            if (firstTour.size()==0)
            {
                Depotfictif d=this.getdepotfictifById_dynamic(firstTour.getId_fictif());
                 int id_cl_fict=d.getId_client_fictif();
                return firstTour.getTemps_dynamic()
                    + secondTour.getTemps_dynamic()
                    + this.gettimes_dynamic(id_cl_fict, secondTour.getFirstCustomer().getId())
                    - this.gettimes_dynamic(id_cl_fict,0)
                    - this.gettimes_dynamic(secondTour.getFirstCustomer().getId(),0);
            }
            return firstTour.getTemps_dynamic()
                    + secondTour.getTemps_dynamic()
                    + this.gettimes_dynamic(firstTour.getLastCustomer().getId(), secondTour.getFirstCustomer().getId())
                    - this.gettimes_dynamic(firstTour.getLastCustomer().getId(),0)
                    - this.gettimes_dynamic(secondTour.getFirstCustomer().getId(),0);
        }
     public VRPD( VRPT VRPT,ArrayList<Customer> customers, ArrayList<Depotfictif> depot_fictifs, Depotcentral depot, double maxTemps, int nombrecamions, int capacitycamion,  double overtime, double temps_service,ArrayList<Customer> client_old, ArrayList<Camion> camions_dynamic ) {
        ArrayList<Customer> customers_clone = new ArrayList<Customer>();
        customers_clone.clear();
         for (int i=0;i<customers.size();i++)
        {
          Customer c=customers.get(i).clone();
          customers_clone.add(c);
        }
         this.setCustomers_dynamic(customers_clone);
            for (int i=0; i< client_old.size();i++)
         {
             this.getCustomers_dynamic().add(client_old.get(i));
         }
         
       // this.Tout_les_clients=customers_total;
          
        this.depots_fictif=depot_fictifs;
        this.depot_central= depot;
        this.maxTemps_dynamic=maxTemps;
         this.setVRPT(VRPT);
        this.nombrecamions_dynamic=nombrecamions;
        this.capacitycamion_dynamic= capacitycamion;
       this.setDistances_dynamic(this.getVRPT().getDistances());
      
        this.setTimes_dynamic(this.getVRPT().getTimes());
           System.out.println("taillle times men constructeur:  "+this.getTimes_dynamic().length);
       
        //this.nombre_nouveaux_clients=nombre_nouveaux_clients;
        this.nombre_depot_fictifs=this.depots_fictif.size();
        this.setCoef_distance_parcourue(1);
        this.setCoef_overtime(1);
        this.ovetime_dynamic=overtime;
        this.temps_service= temps_service;
        this.setCamions_dynamic(camions_dynamic);
        
     }
}
