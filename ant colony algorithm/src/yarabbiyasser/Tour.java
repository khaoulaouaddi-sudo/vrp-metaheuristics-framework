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
public  class Tour implements Cloneable {
    private ArrayList<Customer> customers = new ArrayList<>();
    int id_tour;
    private Ant ant;
    private double temps_tour;
    private double distance_tour;
    private double current_capacity;
    private boolean tournee_terminee=false;
    Camion c;
    int id_fictif;

    public double getDistance_tour() {
        return distance_tour;
    }

    public void setDistance_tour(double distance_tour) {
        this.distance_tour = distance_tour;
    }

    public Camion getC() {
        return c;
    }

    public void setC(Camion c) {
        this.c = c;
    }
    
    

    public int getId_fictif() {
        return id_fictif;
    }

    public void setId_fictif(int id_fictif) {
        this.id_fictif = id_fictif;
    }
    
    

    public boolean isTournee_terminee() {
        return tournee_terminee;
    }

    public void setTournee_terminee(boolean tournee_terminee) {
        this.tournee_terminee = tournee_terminee;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public int getId_tour() {
        return id_tour;
    }

    public void setId_tour(int id_tour) {
        this.id_tour = id_tour;
    }

    public Ant getAnt() {
        return ant;
    }

    public void setAnt(Ant ant) {
        this.ant = ant;
    }

    public double getTemps_tour() {
        return temps_tour;
    }

    public void setTemps_tour(double temps_tour) {
        this.temps_tour = temps_tour;
    }

    public double getCurrent_capacity() {
        return current_capacity;
    }

    public void setCurrent_capacity(double current_capacity) {
        this.current_capacity = this.current_capacity-current_capacity;
    }
    public void set_capacity_initial(double current_capacity) {
        this.current_capacity = current_capacity;
    }
    
    
    //private 
    public  double calculer_temps_tournee(){
        if (this.getCustomers().isEmpty()) { this.setTemps_tour(0);this.setDistance_tour(0);return 0;}
        else
        {
        //System.out.println("hooooooooooooooooooo"+this.getCustomers().get(0).getId());
        double temps= this.getAnt().getProblem().gettimes(0, this.getCustomers().get(0).getId())+
                this.getAnt().getAntSystem().getTemps_service();
        // System.out.println("Waaaaa3 9bel men la boucle dyal calcul :  "+temps);
        double distance=this.getAnt().getProblem().getDistances(0, this.getCustomers().get(0).getId());
       // if (this.this.getCustomers().size())
           
      
        for (int i=1; i< this.getCustomers().size();i++)
            
        { 
             //System.out.println("hooooooooooooooooooo"+this.getCustomers().get(i).getId());
           //System.out.println("Waaaaa3 men weset la boucle dyal calcul :  "+temps);
            temps= temps+this.getAnt().getProblem().gettimes(this.getCustomers().get(i-1).getId(), this.getCustomers().get(i).getId())+this.getAnt().getAntSystem().getTemps_service();
            distance=distance+this.getAnt().getProblem().getDistances(this.getCustomers().get(i-1).getId(), this.getCustomers().get(i).getId());
        }
        //System.out.println("Waaaaa3"+temps);
        if (this.isTournee_terminee()==false){
           // System.out.println("Waaaaa3 tournée términé :  "+temps);
             this.setTemps_tour(temps);
             this.setDistance_tour(distance);
            return temps;}
        else{
        temps=temps+this.getAnt().getProblem().gettimes(this.getCustomers().get(this.getCustomers().size()-1).getId(),0);
        distance=distance+this.getAnt().getProblem().getDistances(this.getCustomers().get(this.getCustomers().size()-1).getId(),0);
        
          this.setTemps_tour(temps); 
          this.setDistance_tour(distance);
        return temps;}
        }
    }
   
    public  double calculer_temps_tournee_tour(Tour t){
        if (t.getCustomers().isEmpty()) {t.setDistance_tour(0);return 0;}
        else
        {
        
        double temps= t.getAnt().getProblem().gettimes(0, t.getCustomers().get(0).getId())+this.getAnt().getAntSystem().getTemps_service();
        double distance=t.getAnt().getProblem().getDistances(0, this.getCustomers().get(0).getId());
       // if (this.this.getCustomers().size())
           
      
        for (int i=1; i< t.getCustomers().size();i++)
            
        { 
             //System.out.println("hooooooooooooooooooo"+this.getCustomers().get(i).getId());
            temps= temps+t.getAnt().getProblem().gettimes(t.getCustomers().get(i-1).getId(), t.getCustomers().get(i).getId())+this.getAnt().getAntSystem().getTemps_service();
           distance=distance+t.getAnt().getProblem().getDistances(this.getCustomers().get(this.getCustomers().size()-1).getId(),0);
        }
        //System.out.println("Waaaaa3"+temps);
        if (this.isTournee_terminee()==false){
            // System.out.println("Waaaaa3"+temps);
            t.setDistance_tour(distance);
            return temps;}
        else{
        temps=temps+this.getAnt().getProblem().gettimes(this.getCustomers().get(this.getCustomers().size()-1).getId(),0);
       distance=distance+t.getAnt().getProblem().getDistances(this.getCustomers().get(this.getCustomers().size()-1).getId(),0);
         t.setDistance_tour(distance);   
        return temps;}
        }
    }
    public  double calculer_temps_tournee_dynamic(){
        if (this.getCustomers().isEmpty()) {
            
            if ((this.isTournee_terminee()==false)||(this.getId_fictif()==0) )
            {
            this.setTemps_tour(0);
            this.setDistance_tour(0);
            return 0;}
            else {
                 Depotfictif d=this.getAnt().getProblem_dynamic().getdepotfictifById_dynamic(this.getId_fictif());
               int id_cl_fict=d.getId_client_fictif();
               this.setTemps_tour( this.getAnt().getProblem_dynamic().gettimes_dynamic(id_cl_fict, 0)); 
               this.setDistance_tour(this.getAnt().getProblem_dynamic().getDistances_dynamic(id_cl_fict, 0));
               return this.getTemps_tour();
       
                
            }
                }
        else
        {
          double temps=0 ;
          double distance=0 ;
        if(this.getId_fictif()==0)
        {
        temps= this.getAnt().getProblem_dynamic().gettimes_dynamic(0, this.getCustomers().get(0).getId())+this.getAnt().getAntSystem().getTemps_service();
        distance=this.getAnt().getProblem_dynamic().getDistances_dynamic(0, this.getCustomers().get(0).getId());
        }
        else
        {
            Depotfictif d=this.getAnt().getProblem_dynamic().getdepotfictifById_dynamic(this.getId_fictif());
            int id_cl_fict=d.getId_client_fictif();
        temps= this.getAnt().getProblem_dynamic().gettimes_dynamic(id_cl_fict, this.getCustomers().get(0).getId())+this.getAnt().getAntSystem().getTemps_service();    
        distance=this.getAnt().getProblem_dynamic().getDistances_dynamic(id_cl_fict, this.getCustomers().get(0).getId());
        }
      
        for (int i=1; i< this.getCustomers().size();i++)
            
        { 
             //System.out.println("hooooooooooooooooooo"+this.getCustomers().get(i).getId());
            temps= temps+this.getAnt().getProblem_dynamic().gettimes_dynamic(this.getCustomers().get(i-1).getId(), this.getCustomers().get(i).getId())+this.getAnt().getAntSystem().getTemps_service();
            distance=distance+this.getAnt().getProblem_dynamic().getDistances_dynamic(this.getCustomers().get(i-1).getId(), this.getCustomers().get(i).getId());
        }
        //System.out.println("Waaaaa3"+temps);
        if (this.isTournee_terminee()==false){
            // System.out.println("Waaaaa3"+temps);
            this.setTemps_tour(temps);
            this.setDistance_tour(distance);
            return temps;}
        else{
        temps=temps+this.getAnt().getProblem_dynamic().gettimes_dynamic(this.getCustomers().get(this.getCustomers().size()-1).getId(),0);
        distance=distance+this.getAnt().getProblem_dynamic().getDistances_dynamic(this.getCustomers().get(this.getCustomers().size()-1).getId(),0);
        this.setDistance_tour(distance);
         this.setTemps_tour(temps);  
        return temps;}
        }
    }
   
    public  double calculer_temps_tournee_dynamic_tour(Tour t){
        if (t.getCustomers().isEmpty()) {
            t.setTemps_tour(0);
            return 0;}
        else
        {
          double temps=0 ; 
        if(t.getId_fictif()==0)
        {
        temps= t.getAnt().getProblem_dynamic().gettimes_dynamic(0, t.getCustomers().get(0).getId())+this.getAnt().getAntSystem().getTemps_service();
        }
        else
        {
            Depotfictif d=t.getAnt().getProblem_dynamic().getdepotfictifById_dynamic(t.getId_fictif());
            int id_cl_fict=d.getId_client_fictif();
        temps= t.getAnt().getProblem_dynamic().gettimes_dynamic(id_cl_fict, t.getCustomers().get(0).getId())+this.getAnt().getAntSystem().getTemps_service();    
        }
      
        for (int i=1; i< t.getCustomers().size();i++)
            
        { 
             //System.out.println("hooooooooooooooooooo"+this.getCustomers().get(i).getId());
            temps= temps+t.getAnt().getProblem_dynamic().gettimes_dynamic(t.getCustomers().get(i-1).getId(), t.getCustomers().get(i).getId())+this.getAnt().getAntSystem().getTemps_service();
           
        }
        //System.out.println("Waaaaa3"+temps);
        if (t.isTournee_terminee()==false){
            // System.out.println("Waaaaa3"+temps);
            t.setTemps_tour(temps);
            return temps;}
        else{
        temps=temps+t.getAnt().getProblem_dynamic().gettimes_dynamic(t.getCustomers().get(t.getCustomers().size()-1).getId(),0);
        t.temps_tour=temps;
         t.setTemps_tour(temps);  
        return temps;}
        }
    }

    public Tour( Ant ant, int numtour, double current_cap) {
        //this.customers=liste_client_tournee;
        this.setAnt(ant);
        this.setId_tour(numtour);
        this.set_capacity_initial(current_cap);
        this.setTournee_terminee(false);
       
        
    }
    public Tour( Ant ant, int numtour, double current_cap,int id_fictif) {
        //this.customers=liste_client_tournee;
        this.setAnt(ant);
        this.setId_tour(numtour);
        this.set_capacity_initial(current_cap);
        this.setTournee_terminee(false);
        this.id_fictif=id_fictif;
       
        
    }
  
     public void addCustomer_tour( Customer c) {
       
            this.getCustomers().add(c);
       
    }
          
     public double checkChangePositionClient(int position_old, int position_new, double thisCost) {
        //********Insertion de k dans la tournée 2
      //  Customer c1 = this.getCustomer(position_old);
         Customer c1 = this.getCustomers().get(position_old);
        //System.out.println("********deb**********");
        //System.out.println("tour1 "+tour1);
        //System.out.println("tour2 "+tour2);
        Tour newTour1 = this.clone();//new Tour(this, tourGroup);
        //newTour1.removeCustomer(c1);
        newTour1.customers.remove(c1);
        //newTour1.addCustomer(position_new, c1);
        newTour1.customers.add(position_new, c1);

        double time1 = newTour1.calculer_temps_tournee();
        if (time1>this.getAnt().getProblem().getMaxTemps()) {
            return -1;
        }
        return (thisCost - newTour1.calculer_temps_tournee());
    }

    public double checkExchangeClient(int position1, int position2, double thisCost) {//pour éviter de le calculer à chaque fois
        //********Echange de deux clients
       // Customer c1 = this.get(position1);
       // Customer c2 = this.get(position2);
       Customer c1 = this.getCustomers().get(position1);
       Customer c2 = this.getCustomers().get(position2);
        //Créer la nouvelle tournée
        //public Tour( Ant ant, int numtour, double current_cap)
        Tour newTour1 = new Tour(this.ant,0,0);
        newTour1.setTournee_terminee(true);
        for (Customer c : this.getCustomers()) {
            if (c == c1) {
                newTour1.getCustomers().add(c2);
            } else if (c == c2) {
                newTour1.getCustomers().add(c1);
            } else {
                newTour1.getCustomers().add(c);
            }
        }

        double time1 = calculer_temps_tournee_tour(newTour1);
        if (time1 > this.ant.getProblem().getMaxTemps()) {
            return -2;
        }

        double diff = thisCost - calculer_temps_tournee_tour(newTour1);
        
        //System.out.println("diff "+diff);
        if (diff >= 0) {
            return diff;
        }

        return -1;
    }

    public boolean two_opt_best() {
        int best1 = -1;
        int best2 = -1;
        int typeOfChange = 0;
         double cout_avant=this.calculer_temps_tournee();
       // Camion camion1 = this.getCamion();
        //this.removeCamion();//pour que camion1 soit considéré lors de la recherche de camion pour newTour1
        double maxDiff = 0;
        double diff;
        double thisCost = this.calculer_temps_tournee();
        
        for (int k = 0; k < this.getCustomers().size(); k++) {
            for (int m = k + 1; m < this.getCustomers().size(); m++) {
                diff = this.checkExchangeClient(k, m, thisCost);
                if (diff > maxDiff) {
                    best1 = k;
                    best2 = m;
                    typeOfChange = 1;
                    maxDiff = diff;
                }
            }
        }
        for (int k = 0; k < this.getCustomers().size(); k++) {
            for (int m = k + 1; m < this.getCustomers().size(); m++) {
                 diff = this.checkChangePositionClient(k, m, thisCost);
                if (diff > maxDiff) {
                    best1 = k;
                    best2 = m;
                    typeOfChange = 2;
                    maxDiff = diff;
                }
            }
        }
        if (typeOfChange!=0) {
            if (typeOfChange==1 && best1 != -1 && best2 != -1) {
                Customer c1 = this.getCustomers().get(best1);
                Customer c2 = this.getCustomers().get(best2);
            //   System.out.println("********Avant");
            //    System.out.println("tour1 "+tour1);
            //    System.out.println("tour2 "+tour2);
                // System.out.println("Permutation done 1");
                this.getCustomers().remove(c1);
                this.getCustomers().remove(c2);
                this.getCustomers().add(best1, c2);
                this.getCustomers().add(best2, c1);
               //  System.out.println("difference de temps echange entre deux client "+(cout_avant-this.calculer_temps_tournee()));
            }
            if (typeOfChange == 2 && best1!=-1) {
                // System.out.println("Permutation done 2");
               // Customer c1 = this.getCustomer(best1);
               Customer c1 = this.getCustomers().get(best1);
                this.getCustomers().remove(c1);
                this.getCustomers().add(best2, c1);
                 //System.out.println("difference de temps permuation d'un client "+(cout_avant-this.calculer_temps_tournee()));
            }
            //this.setBestCamion();
            //this.setCamion(camion1);
            //    System.out.println("Après");
            //     System.out.println("tour1 "+tour1);
            //     System.out.println("tour2 "+tour2);


            return true;
        }
        //this.setCamion(camion1);//parce qu'on a enlever leurs camionsToUse
        return false;
    }


  
    public boolean checkDecroisement() {
       /* VRPS p = this.getAnt().getProblem();
        Customer c1 , c2, c3, c4;
        int id1, id2,id3,id4;
        double X1, Y1,X4,Y4;*/
        double cout_avant=this.calculer_temps_tournee();
      //  double diff;
                
        for (int k = 0; k < this.getCustomers().size()-3; k++) {
           /* if(k==0)
                {id1=0;  X1=this.getAnt().getProblem().getDepot().getXdepot();
                 Y1=this.getAnt().getProblem().getDepot().getYdepot();}
                 else
                { c1 = this.getCustomers().get(k);
                 id1=c1.getId(); 
                  X1=c1.getX();
                 Y1=c1.getY();}
                c2 = this.getCustomers().get(k+1);
                id2=c2.getId();
            double a1 = (Y1-c2.getY())/(X1-c2.getX());
            double b1 = Y1-a1*X1;*/
            for (int m = k + 2; m <this.getCustomers().size(); m++) {
                
              /*  c3 = this.getCustomers().get(m);
                id3=c3.getId();
             
            
                if(m<this.getCustomers().size()-2)
                {c4 = this.getCustomers().get(m+1);id4=c4.getId(); X4=c4.getX();Y4=c4.getY();}
                else
                { id4 = 0; X4=this.getAnt().getProblem().getDepot().getXdepot();
                           Y4=this.getAnt().getProblem().getDepot().getYdepot();}
                 double a2 = (c3.getY()-Y4)/(c3.getX()-X4);
                double b2 = c3.getY()-a2*c3.getX();
                
                double x = -(b2-b1)/(a2-a1);
                double y = a1*x+b1;
                double dist1 = p.getDistances(id1, id2);
                double dist2 = p.getDistances(id3, id4);
                double newDist1 = p.getDistances(id1, id3);
                double newDist2 = p.getDistances(id2, id4);
               /* Gain = Distance(Ville(i), Ville((j+1)%Nb_Villes) )
         + Distance(Ville((i+Nb_Villes-1)%Nb_Villes), Ville(j) )
         - Distance(Ville((i+Nb_Villes-1)%Nb_Villes, Ville(i) )
         - Distance(Ville(j), Ville((j+1)%Nb_Villes) )*/
               
                 /*if(between(x, y, X1, Y1, c2.getX(), c2.getY())
                        && between(x, y, c3.getX(),c3.getY(), X4,Y4)) {
                    //System.out.println("c1:"+customer1+", c2:"+customer2);
                    //System.out.println("c3:"+customer3+", c4:"+customer4);
                    ArrayList part = this.getPartOfCustomers(k+1, m);
                    this.removeCustomers(k+1, m);
                    ArrayList <Customer> part_renverse= this.reverse(part);
                    //tourGroup.reverse(part);
                    this.addCustomersAtPosition(part_renverse, k+1);
                     System.out.println("difference de temps décroisement"+(cout_avant-this.calculer_temps_tournee()));
                    //this.setBestCamion();
                    return true;
                }*/
                 
               // if (newDist1+newDist2 < dist1+dist2) {
                    ArrayList part = this.getPartOfCustomers(k, m);
                    this.removeCustomers(k, m);
                    ArrayList <Customer> part_renverse= this.reverse(part);
                    //tourGroup.reverse(part);
                    this.addCustomersAtPosition(part_renverse, k);
                    if (cout_avant-this.calculer_temps_tournee()>=0)
                    {
                    // System.out.println("difference de temps décroisement"+(cout_avant-this.calculer_temps_tournee()));
                    //this.setBestCamion();
                    return true;
                    }
                    else 
                    {
                    ArrayList part2 = this.getPartOfCustomers(k, m);
                    this.removeCustomers(k, m);
                    ArrayList <Customer> part_renverse_2= this.reverse(part2);
                    //tourGroup.reverse(part);
                    this.addCustomersAtPosition(part_renverse_2, k); 
                    //System.out.println("difference de temps décroisement"+(cout_avant-this.calculer_temps_tournee()));
                    return false;
                    }
                
            }
        }            
        return false;
    }
    
   
    public boolean addCustomerInBestPositionWithoutCheckConstraint(Customer c) {
        int bestPosition = bestPositionInsertClientWithoutCheckConstraint(c);
        if(bestPosition!=-1){
            addCustomer(bestPosition, c);
            return true;
        }
        else 
            return false;
    }
    public double checkInsertClientWithoutCheckConstraint(Customer customer, int position) {
        // v�rifier si l'�change pr�serve les contraintes du probl�me
        Tour newTour = this.clone();//new Tour(tour, tourGroup);
        newTour.addCustomer(position, customer);
        return newTour.calculer_temps_tournee();
    }
     public int bestPositionInsertClientWithoutCheckConstraint(Customer customer) {
        double bestCost = 1000000000;
        int bestPosition = -1;
        if(this.getCustomers().size()==0)
            bestPosition = 0;
        for (int p = 0; p < this.getCustomers().size(); p++) {
            double cost = this.checkInsertClientWithoutCheckConstraint(customer, p);
            if (cost > 0 && cost <= bestCost) {
                bestCost = cost;
                bestPosition = p;
            }
        }
        return bestPosition;
    }
     
    
     public boolean between(double x, double y, double X1, double Y1, double X2, double Y2) {
        if((X1<=x && X2>=x)
                        || (X1>=x && X2<=x))
            if((Y1<=y && Y2>=y)
                        || (Y1>=y && Y2<=y))
                return true;
        return false;
    }
    public Customer addCustomersAtPosition(ArrayList<Customer> customers,int position) {
        int i = position;
        for(Customer c:customers){
            this.addCustomer(i++, c);
        }
        return null;
    }
    public void addCustomer(int position, Customer c) {
        if (position == this.getCustomers().size()) {
            customers.add(c);
        } else {
            customers.add(position, c);
        }
    }
   
   ArrayList<Customer>  reverse(ArrayList<Customer> liste)
              {
	ArrayList<Customer> result = new ArrayList<Customer>();
	for(int i=liste.size()-1; i>=0; i--)
	    result.add(liste.get(i));
	return result;
    }
        public double getLowerDemand(){
        double lowerDemand = 100000;
        for(Customer c:this.getCustomers()){
            if(c.getDemande()<lowerDemand)
                lowerDemand = c.getDemande(); 
        }
            return lowerDemand;
    }


    public ArrayList<Customer> getPartOfCustomers(int firstPosition, int lastPosition) {
        ArrayList<Customer> part = new ArrayList<Customer>();
        for(int i=firstPosition;i<=lastPosition;i++)
            part.add(customers.get(i));
        return part;
    }
    public void removeCustomers(int firstPosition, int lastPosition) {
        ArrayList<Customer> part = new ArrayList<Customer>();
        for(int i=firstPosition;i<=lastPosition;i++){
            customers.remove(firstPosition);
        }
    }
    public double checkChangePositionClient_dynamic(int position_old, int position_new, double thisCost) {
        //********Insertion de k dans la tournée 2
      //  Customer c1 = this.getCustomer(position_old);
         Customer c1 = this.getCustomers().get(position_old);
        //System.out.println("********deb**********");
        //System.out.println("tour1 "+tour1);
        //System.out.println("tour2 "+tour2);
        Tour newTour1 = this.clone();//new Tour(this, tourGroup);
        //newTour1.removeCustomer(c1);
        newTour1.customers.remove(c1);
        //newTour1.addCustomer(position_new, c1);
        newTour1.customers.add(position_new, c1);

        double time1 = newTour1.calculer_temps_tournee_dynamic();
        if (time1>this.getAnt().getProblem_dynamic().getMaxTemps_dynamic()) {
            return -1;
        }
        return (thisCost - newTour1.calculer_temps_tournee_dynamic());
    }

    public double checkExchangeClient_dynamic(int position1, int position2, double thisCost) {//pour éviter de le calculer à chaque fois
        //********Echange de deux clients
       // Customer c1 = this.get(position1);
       // Customer c2 = this.get(position2);
       Customer c1 = this.getCustomers().get(position1);
       Customer c2 = this.getCustomers().get(position2);
        //Créer la nouvelle tournée
        //public Tour( Ant ant, int numtour, double current_cap)
        Tour newTour1 = new Tour(this.ant,0,0, this.id_fictif);
        newTour1.setTournee_terminee(true);
        for (Customer c : this.getCustomers()) {
            if (c == c1) {
                newTour1.getCustomers().add(c2);
            } else if (c == c2) {
                newTour1.getCustomers().add(c1);
            } else {
                newTour1.getCustomers().add(c);
            }
        }

        double time1 = calculer_temps_tournee_dynamic_tour(newTour1);
        if (time1 > this.ant.getProblem_dynamic().getMaxTemps_dynamic()) {
            return -2;
        }

        double diff = thisCost - calculer_temps_tournee_dynamic_tour(newTour1);
        
        //System.out.println("diff "+diff);
        if (diff >= 0) {
            return diff;
        }

        return -1;
    }

    public boolean two_opt_best_dynamic() {
        int best1 = -1;
        int best2 = -1;
        int typeOfChange = 0;
         double cout_avant=this.calculer_temps_tournee_dynamic();
       // Camion camion1 = this.getCamion();
        //this.removeCamion();//pour que camion1 soit considéré lors de la recherche de camion pour newTour1
        double maxDiff = 0;
        double diff;
        double thisCost = this.calculer_temps_tournee_dynamic();
        
        for (int k = 0; k < this.getCustomers().size(); k++) {
            for (int m = k + 1; m < this.getCustomers().size(); m++) {
                diff = this.checkExchangeClient_dynamic(k, m, thisCost);
                if (diff > maxDiff) {
                    best1 = k;
                    best2 = m;
                    typeOfChange = 1;
                    maxDiff = diff;
                }
            }
        }
        for (int k = 0; k < this.getCustomers().size(); k++) {
            for (int m = k + 1; m < this.getCustomers().size(); m++) {
                 diff = this.checkChangePositionClient_dynamic(k, m, thisCost);
                if (diff > maxDiff) {
                    best1 = k;
                    best2 = m;
                    typeOfChange = 2;
                    maxDiff = diff;
                }
            }
        }
        if (typeOfChange!=0) {
            if (typeOfChange==1 && best1 != -1 && best2 != -1) {
                Customer c1 = this.getCustomers().get(best1);
                Customer c2 = this.getCustomers().get(best2);
            //   System.out.println("********Avant");
            //    System.out.println("tour1 "+tour1);
            //    System.out.println("tour2 "+tour2);
                // System.out.println("Permutation done 1");
                this.getCustomers().remove(c1);
                this.getCustomers().remove(c2);
                this.getCustomers().add(best1, c2);
                this.getCustomers().add(best2, c1);
               //  System.out.println("difference de temps echange entre deux client "+(cout_avant-this.calculer_temps_tournee()));
            }
            if (typeOfChange == 2 && best1!=-1) {
                // System.out.println("Permutation done 2");
               // Customer c1 = this.getCustomer(best1);
               Customer c1 = this.getCustomers().get(best1);
                this.getCustomers().remove(c1);
                this.getCustomers().add(best2, c1);
                 //System.out.println("difference de temps permuation d'un client "+(cout_avant-this.calculer_temps_tournee()));
            }
            //this.setBestCamion();
            //this.setCamion(camion1);
            //    System.out.println("Après");
            //     System.out.println("tour1 "+tour1);
            //     System.out.println("tour2 "+tour2);


            return true;
        }
        //this.setCamion(camion1);//parce qu'on a enlever leurs camionsToUse
        return false;
    }
     
    public boolean checkDecroisement_dynamic() {
       /* VRPS p = this.getAnt().getProblem();
        Customer c1 , c2, c3, c4;
        int id1, id2,id3,id4;
        double X1, Y1,X4,Y4;*/
        double cout_avant=this.calculer_temps_tournee_dynamic();
      //  double diff;
                
        for (int k = 0; k < this.getCustomers().size()-3; k++) {
          
            for (int m = k + 2; m <this.getCustomers().size(); m++) {
                
            
                    ArrayList part = this.getPartOfCustomers(k, m);
                    this.removeCustomers(k, m);
                    ArrayList <Customer> part_renverse= this.reverse(part);
                    //tourGroup.reverse(part);
                    this.addCustomersAtPosition(part_renverse, k);
                    if (cout_avant-this.calculer_temps_tournee_dynamic()>=0)
                    {
                    // System.out.println("difference de temps décroisement"+(cout_avant-this.calculer_temps_tournee()));
                    //this.setBestCamion();
                    return true;
                    }
                    else 
                    {
                    ArrayList part2 = this.getPartOfCustomers(k, m);
                    this.removeCustomers(k, m);
                    ArrayList <Customer> part_renverse_2= this.reverse(part2);
                    //tourGroup.reverse(part);
                    this.addCustomersAtPosition(part_renverse_2, k); 
                    //System.out.println("difference de temps décroisement"+(cout_avant-this.calculer_temps_tournee()));
                    return false;
                    }
                
            }
        }            
        return false;
    }
     
    public boolean addCustomerInBestPositionWithoutCheckConstraint_dynamic(Customer c) {
        int bestPosition = bestPositionInsertClientWithoutCheckConstraint_dynamic(c);
        if(bestPosition!=-1){
            addCustomer(bestPosition, c);
            return true;
        }
        else 
            return false;
    }
    public double checkInsertClientWithoutCheckConstraint_dynamic(Customer customer, int position) {
        // v�rifier si l'�change pr�serve les contraintes du probl�me
        Tour newTour = this.clone();//new Tour(tour, tourGroup);
        newTour.addCustomer(position, customer);
        return newTour.calculer_temps_tournee_dynamic();
    }
    public int bestPositionInsertClientWithoutCheckConstraint_dynamic(Customer customer) {
        double bestCost = 1000000000;
        int bestPosition = -1;
        if(this.getCustomers().size()==0)
            bestPosition = 0;
        for (int p = 0; p < this.getCustomers().size(); p++) {
            double cost = this.checkInsertClientWithoutCheckConstraint_dynamic(customer, p);
            if (cost > 0 && cost <= bestCost) {
                bestCost = cost;
                bestPosition = p;
            }
        }
        return bestPosition;
    }
     
   
   
  
   
   
     @Override
   public Tour clone() {
        Tour o = null;
        try {
                // On récupère l'instance à renvoyer par l'appel de la 
                // méthode super.clone()
                o = (Tour) super.clone();
                //o.tourGroup = tourGroup;
                o.customers = (ArrayList<Customer>) customers.clone();
                
            } catch(CloneNotSupportedException cnse) {
                // Ne devrait jamais arriver car nous implémentons 
                // l'interface Cloneable
                cnse.printStackTrace(System.err);
        }
        // on renvoie le clone
        return o;
    }
   
    
    
    
}
