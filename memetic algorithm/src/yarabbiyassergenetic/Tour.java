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
import java.util.ArrayList;

   


public  class Tour implements Comparable<Tour>, Cloneable {
    private ArrayList<Customer> customers = new ArrayList<>();
    int id_tour;
    
    private double temps_tour;
    private double distance_tour;
    private double current_capacity;
    private boolean Tournee_terminee;
    //private boolean tournee_terminee=false;
    Camion c;
    int id_fictif;
    public TourGroup tourgroupe;

    public TourGroup getTourgroupe() {
        return tourgroupe;
    }

    public void setTourgroupe(TourGroup tourgroupe) {
        this.tourgroupe = tourgroupe;
    }

    public double getDistance_tour() {
        return distance_tour;
    }

    public void setDistance_tour(double distance_tour) {
        this.distance_tour = distance_tour;
    }

    public boolean isTournee_terminee() {
        return Tournee_terminee;
    }

    public void setTournee_terminee(boolean Tournee_terminee) {
        this.Tournee_terminee = Tournee_terminee;
    }
    

    public Camion getC() {
        return c;
    }
    public int size(){
        return this.getCustomers().size();
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
     public void init() {
        if (customers.isEmpty()) {
            return;
        }
        while (this.getCustomers().isEmpty() == false) {
            removeCustomer(this.getCustomers().get(0));
        }
        removeCamion();
    }
    
    public void addAll(ArrayList<Customer> cs) {
        for (Customer c : cs) {
            addCustomer(c);
        }
    }

    
    //private 
    public  double getTemps(){
        if (this.getCustomers().isEmpty()) { this.setTemps_tour(0);this.setDistance_tour(0);return 0;}
        else
        {
        //System.out.println("hooooooooooooooooooo"+this.getCustomers().get(0).getId());
        double temps= this.getTourgroupe().getProblem().gettime(0, this.getCustomers().get(0).getId())+
                this.getTourgroupe().getProblem().getVRPtotal().getTemps_service();
        // System.out.println("Waaaaa3 9bel men la boucle dyal calcul :  "+temps);
        double distance=this.getTourgroupe().getProblem().getVRPtotal().getDistances(0, this.getCustomers().get(0).getId());
       // if (this.this.getCustomers().size())
           
      
        for (int i=1; i< this.getCustomers().size();i++)
            
        { 
             //System.out.println("hooooooooooooooooooo"+this.getCustomers().get(i).getId());
           //System.out.println("Waaaaa3 men weset la boucle dyal calcul :  "+temps);
            temps= temps+this.getTourgroupe().getProblem().getVRPtotal().gettimes(this.getCustomers().get(i-1).getId(), this.getCustomers().get(i).getId())+this.getTourgroupe().getProblem().getVRPtotal().getTemps_service();
            distance=distance+this.getTourgroupe().getProblem().getVRPtotal().getDistances(this.getCustomers().get(i-1).getId(), this.getCustomers().get(i).getId());
        }
        //System.out.println("Waaaaa3"+temps);
        
        temps=temps+this.getTourgroupe().getProblem().getVRPtotal().gettimes(this.getCustomers().get(this.getCustomers().size()-1).getId(),0);
        distance=distance+getTourgroupe().getProblem().getVRPtotal().getDistances(this.getCustomers().get(this.getCustomers().size()-1).getId(),0);
        
          this.setTemps_tour(temps); 
          this.setDistance_tour(distance);
        return temps;
        }
    }
      public  double getTemps_dynamic(){
        if (this.getCustomers().isEmpty()) {
            
            if (this.getId_fictif()==0) 
            {
            this.setTemps_tour(0);
            this.setDistance_tour(0);
            return 0;}
            else {
                 Depotfictif d=this.getTourgroupe().getProblemD().getdepotfictifById_dynamic(this.getId_fictif());
               int id_cl_fict=d.getId_client_fictif();
               this.setTemps_tour( this.getTourgroupe().getProblemD().gettimes_dynamic(id_cl_fict, 0)); 
               this.setDistance_tour(this.getTourgroupe().getProblemD().getDistances_dynamic(id_cl_fict, 0));
               return this.getTemps_tour();
       
                
            }
                }
        else
        {
          double temps=0 ;
          double distance=0 ;
        if(this.getId_fictif()==0)
        {
           /* System.out.println("taille tournées: "+this.getCustomers().size());
            System.out.println("id clients : "+this.getCustomers().get(0).getId());
             System.out.println("probleeeem brasso : "+this.getTourgroupe().getProblemD().toString());
             System.out.println("taille matrice times: "+this.getTourgroupe().getProblemD().getTimes_dynamic().length);*/
        temps= this.getTourgroupe().getProblemD().gettimes_dynamic(0, this.getCustomers().get(0).getId())+
                this.getTourgroupe().getProblemD().getTemps_service();
        distance=this.getTourgroupe().getProblemD().getDistances_dynamic(0, this.getCustomers().get(0).getId());
        }
        else
        {
           // System.out.println("haa id fictif lli dayer had rwiina :  "+ this.getId_fictif());
           // System.out.println("haa lproblem lli dayer rwiina :  "+ this.getTourgroupe().getProblemD());
            Depotfictif d=this.getTourgroupe().getProblemD().getdepotfictifById_dynamic(this.getId_fictif());
            int id_cl_fict=d.getId_client_fictif();
        temps= this.getTourgroupe().getProblemD().gettimes_dynamic(id_cl_fict, this.getCustomers().get(0).getId())+this.getTourgroupe().getProblemD().getTemps_service();    
        distance=this.getTourgroupe().getProblemD().getDistances_dynamic(id_cl_fict, this.getCustomers().get(0).getId());
        }
      
        for (int i=1; i< this.getCustomers().size();i++)
            
        { 
             //System.out.println("hooooooooooooooooooo"+this.getCustomers().get(i).getId());
            temps= temps+this.getTourgroupe().getProblemD().gettimes_dynamic(this.getCustomers().get(i-1).getId(), this.getCustomers().get(i).getId())+this.getTourgroupe().getProblemD().getTemps_service();
            distance=distance+this.getTourgroupe().getProblemD().getDistances_dynamic(this.getCustomers().get(i-1).getId(), this.getCustomers().get(i).getId());
        }
        //System.out.println("Waaaaa3"+temps);
        
        
        temps=temps+this.getTourgroupe().getProblemD().gettimes_dynamic(this.getCustomers().get(this.getCustomers().size()-1).getId(),0);
        distance=distance+this.getTourgroupe().getProblemD().getDistances_dynamic(this.getCustomers().get(this.getCustomers().size()-1).getId(),0);
        this.setDistance_tour(distance);
         this.setTemps_tour(temps);  
        return temps;
        }
    }
      public int get_id_fic_final(){
           Depotfictif d=this.getTourgroupe().getProblemD().getdepotfictifById_dynamic(this.getId_fictif());
            int id_cl_fict=d.getId_client_fictif();
            return id_cl_fict;
      }
      public double getTempsRestant()
      {
           double temps= 100000000;
          if (this.id_fictif==0)
              return this.tourgroupe.getProblemD().maxTemps_dynamic+this.tourgroupe.getProblemD().getOvetime_dynamic();
         
          else 
          {
              for (Camion c: this.tourgroupe.getCamions())
              {
                  if (c.getDepot_fictif_final()==null)
                      continue;
                  
                  if (this.id_fictif==c.getDepot_fictif_final().getId_depot_fictif())
                 temps=this.tourgroupe.getProblemD().maxTemps_dynamic-c.sum_temps_tour+this.tourgroupe.getProblemD().getOvetime_dynamic();
              }
          }
          return temps;
      }
    
    public Customer getFirstCustomer() {
        return this.customers.get(0);
    }

    public Customer getLastCustomer() {
        return this.customers.get(customers.size() - 1);
    }

    public Customer getCustomerBefore(Customer customer) {
        for (int i = 0; i < this.size(); i++) {
            if (this.getCustomers().get(i) == customer && i > 0) {
                return this.getCustomers().get(i);
            }
        }
        return null;
    }
  
     public boolean CheckCustomerInTour(Customer customer) {
        for (Customer c : this.getCustomers()) {
            if (c == customer) {
                return true;
            }
        }
        return false;
    }
     public boolean removeCustomer(Customer c) {
        if(customers.remove(c)==true){
            //setEvaluated(false);
            return true;
        }
        return false;
    }

    public void removeCustomer(int position) {
        //setEvaluated(false);
        customers.remove(position);
    }

    public void removeCustomers(int firstPosition, int lastPosition) {
        ArrayList<Customer> part = new ArrayList<Customer>();
        for(int i=firstPosition;i<=lastPosition;i++){
            customers.remove(firstPosition);
        }
    } 
     public void addCustomer(Customer c) {
        customers.add(c);
       // setEvaluated(false);
    }

    public Tour( TourGroup tourgroupe) {
        
        this.setTourgroupe(tourgroupe);
        
       
        //this.setCurrent_capacity(current_cap);
      
       
        
    }
    public Tour( TourGroup tourgroupe, double current_cap, int idfictif) {
        
        this.setTourgroupe(tourgroupe);
        
       
        this.setCurrent_capacity(current_cap);
        this.setId_fictif(id_fictif);
      
       
        
    }
    public double getQuantity() {
        double v_quantity = 0;
        for (Customer c : customers) {
            v_quantity =v_quantity+ c.getDemande();
        }
        return v_quantity;
    }
   
     public void addCustomer_tour( Customer c) {
       
            this.getCustomers().add(c);
       
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
     public Camion setBestCamion() {
         
        //System.out.println("haaa le nombre de camion lli 3aned had tourgroupe:  "+tourgroupe.getProblem().getCamions().size());
        Camion best =tourgroupe.BestCamionforTour(this);
       
         if (best==null)
         {
        System.out.println("raaaah beeeeeeeeeeeest lli nullllllllllllllll  "); 
             return null;
         }
          setCamion(best);
          return best;
    }
     public Camion setBestCamion_dynamic() {
         Camion best=null;
        
      if (this.getId_fictif()!=0)
      {
         //System.out.println("waaaaaaaaaaaaaaaaaaaaaaaaaaaa333 hadi id fictif dyalha machi null:"+  this.getId_fictif());
         
        
         Depotfictif d=this.getTourgroupe().getProblemD().getdepotfictifById_dynamic(this.getId_fictif());
               int id_cl_fict=d.getId_client_fictif();
               // System.out.println("ha id client fictif dyal depot "+ id_cl_fict);
               for (int i=0; i<this.getTourgroupe().getCamions().size();i++)
               {
                //  System.out.println("haa id fictif final dyal lcamion:"+this.getTourgroupe().getCamions().get(i).getId_fictif_final());
                   /*System.out.println("ha lid dyal depot fictif: "+ d.id_depot_fictif);
                   System.out.println("ha id client fictif dyal depot "+ id_cl_fict);*/
                 if (this.getTourgroupe().getCamions().get(i).getId_fictif_final()==id_cl_fict)
                {
                    best=this.getTourgroupe().getCamions().get(i);
             
              /* System.out.println("ha 7na l9ina lcamion dyalha: "+ best.getId_fictif_final());
               System.out.println("ha lid dyal depot fictif: "+ d.id_depot_fictif);*/
                 // best.getTournee_attribuees().add(this); 
                  setCamion(best);
                  break;
                 
                             
                }    
               }
              //System.out.println(" cas id non null haaaaaaa l camion dyal hadik lli id fictif dyalha machi null:"+best.getIdcamion());
      }
      else {
             //System.out.println("waaaaaaaa333 hadi id fictif null:"+  this.getId_fictif());
        best =tourgroupe.BestCamionforTour_dynamic(this);
        setCamion(best);
       //System.out.println(" cas id fictif null haaa lcamion dyalha"+  best.getIdcamion());
        
      }
      return best;
    }
    
  
     
    public void addCustomer(int position, Customer c) {
        if (position == this.getCustomers().size()) {
            customers.add(c);
        } else {
            customers.add(position, c);
        }
    }
    public void improve1(){
        //this.decroisement_iterative();
        two_opt_iterative();
    }
    public void improve1_dynamic(){
        //this.decroisement_iterative();
        two_opt_iterative_dynamic();
    }
     public boolean checkConstraints() {
        return this.getTemps() <= tourgroupe.getProblem().getMaxTemps()+tourgroupe.getProblem().getOvertime()
                && this.getQuantity() <= tourgroupe.getProblem().getMaxCapacity();
    }
      public boolean checkConstraints_dynamic() {
          if (this.getId_fictif()==0)
          {
          return this.getTemps_dynamic()<= tourgroupe.getProblemD().getMaxTemps_dynamic()+tourgroupe.getProblemD().getOvetime_dynamic()
                && this.getQuantity() <= tourgroupe.getProblemD().getCapacitycamion_dynamic();    
          }
          
          
             // Camion c=this.camion_dynamic();
              /*System.out.println("problem :"+tourgroupe.getProblemD());
               System.out.println("camion :"+c.getIdcamion());
               System.out.println("sum_temps_tour :"+c.sum_temps_tour);*/
          if (this.getTemps_dynamic()>this.getTempsRestant()||this.getQuantity()>this.getcapacity())
          {return false;}
          else 
          {return true;}
         
    }
      public Camion camion_dynamic()
      {
          if( this.getId_fictif()==0)
          {return null;}
          else
          {
          for (Camion c: this.tourgroupe.getCamions()) 
          {
           if (c.id_fictif_final==this.id_fictif) 
           {
               return c;
           }
          }
          }
          return null;
      }
              
    
    public void two_opt_iterative(){
       
        boolean improved1=true;
        boolean improved2= true;
         //System.out.println("haaa howa dkhal ldecroisement ");
         int i=0;
        while((improved2==true )&&(i<this.getTourgroupe().getProblem().getNbImprove())){
          //  System.out.println("nchofo be3da wach dar decroisment tournée ");
            improved2 = this.checkDecroisement();
            i++;
        }
       // System.out.println("haaa howa khraj men ldecroisement ");
       //System.out.println("haaa howa dkhal l two opt ");
       int j=0;
        while((improved1==true)&&(j<this.getTourgroupe().getProblem().getNbImprove())){
          // System.out.println("nchofo be3da wach dar twoopt tournée  ");
            improved1 = this.two_opt();
            j++;
        }
       //System.out.println("haaa howa khraj men two opt ");
    }
    public void two_opt_iterative_dynamic(){
       
        boolean improved1=true;
        boolean improved2= true;
         //System.out.println("haaa howa dkhal ldecroisement ");
         int i=0;
        while((improved2==true )&&(i<this.getTourgroupe().getProblemD().getNbImprove())){
          //  System.out.println("nchofo be3da wach dar decroisment tournée ");
            improved2 = this.checkDecroisement_dynamic();
            i++;
        }
       // System.out.println("haaa howa khraj men ldecroisement ");
       //System.out.println("haaa howa dkhal l two opt ");
       int j=0;
        while((improved1==true)&&(j<this.getTourgroupe().getProblemD().getNbImprove())){
          // System.out.println("nchofo be3da wach dar twoopt tournée  ");
            improved1 = this.two_opt_dynamic();
            j++;
        }
       //System.out.println("haaa howa khraj men two opt ");
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

        double time1 = newTour1.getTemps();
        if (time1>this.getTourgroupe().getProblem().getMaxTemps()+this.tourgroupe.getProblem().getOvertime()) {
            return -1;
        }
        return (thisCost - newTour1.getTemps());
    }

    public double checkExchangeClient(int position1, int position2, double thisCost) {//pour éviter de le calculer à chaque fois
        //********Echange de deux clients
       // Customer c1 = this.get(position1);
       // Customer c2 = this.get(position2);
       Customer c1 = this.getCustomers().get(position1);
       Customer c2 = this.getCustomers().get(position2);
        //Créer la nouvelle tournée
        //public Tour( Ant ant, int numtour, double current_cap)
        Tour newTour1 = new Tour(this.tourgroupe);
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

        double time1 = newTour1.getTemps();
        if (time1 > this.getTourgroupe().getProblem().getMaxTemps()+this.tourgroupe.getProblem().getOvertime()) {
            return -2;
        }

        double diff = thisCost - newTour1.getTemps();
        
        //System.out.println("diff "+diff);
        if (diff >= 0) {
            return diff;
        }

        return -1;
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

        double time1 = newTour1.getTemps_dynamic();
        if (time1>this.getTourgroupe().getProblemD().getMaxTemps_dynamic()+this.tourgroupe.getProblemD().getOvetime_dynamic()) {
            return -1;
        }
        return (thisCost - newTour1.getTemps_dynamic());
    }

    public double checkExchangeClient_dynamic(int position1, int position2, double thisCost) {//pour éviter de le calculer à chaque fois
        //********Echange de deux clients
       // Customer c1 = this.get(position1);
       // Customer c2 = this.get(position2);
       Customer c1 = this.getCustomers().get(position1);
       Customer c2 = this.getCustomers().get(position2);
        //Créer la nouvelle tournée
        //public Tour( Ant ant, int numtour, double current_cap)
        Tour newTour1 = new Tour(this.tourgroupe);
        newTour1.setId_fictif(this.getId_fictif());
       
        for (Customer c : this.getCustomers()) {
            if (c == c1) {
                newTour1.getCustomers().add(c2);
            } else if (c == c2) {
                newTour1.getCustomers().add(c1);
            } else {
                newTour1.getCustomers().add(c);
            }
        }

        double time1 = newTour1.getTemps_dynamic();
        if (time1 > this.tourgroupe.getProblemD().getMaxTemps_dynamic()+this.tourgroupe.getProblemD().getOvetime_dynamic()) {
            return -2;
        }

        double diff = thisCost - newTour1.getTemps_dynamic();
        
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
         double cout_avant=this.getTemps();
       // Camion camion1 = this.getCamion();
        //this.removeCamion();//pour que camion1 soit considéré lors de la recherche de camion pour newTour1
        double maxDiff = 0;
        double diff;
        double thisCost = this.getTemps();
        
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
  /* public boolean two_opt(){
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
                return true;
               //  System.out.println("difference de temps echange entre deux client "+(cout_avant-this.calculer_temps_tournee()));
            }
            else {
                return false;
            }
            
       
       
   
   }
   public boolean two_opt_insertion(){
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
                 diff = this.checkChangePositionClient(k, m, thisCost);
                if (diff > maxDiff) {
                    best1 = k;
                    best2 = m;
                    typeOfChange = 2;
                    maxDiff = diff;
                }
            }
        }
          if (typeOfChange == 2 && best1!=-1) {
                // System.out.println("Permutation done 2");
               // Customer c1 = this.getCustomer(best1);
               Customer c1 = this.getCustomers().get(best1);
                this.getCustomers().remove(c1);
                this.getCustomers().add(best2, c1);
                return true;
                 //System.out.println("difference de temps permuation d'un client "+(cout_avant-this.calculer_temps_tournee()));
            }
          else {
              return false;
          }
       
   }*/
    
   
      public boolean two_opt() {
        double diff;
        //double thisCost = this.getObj2();
        for (int k = 0; k < this.size(); k++) {
            for (int m = k + 1; m < this.size(); m++) {
                 //System.out.println("********Avant11");
                diff = this.checkExchangeClient(k, m, this.getTemps());
                if (diff > 0) {
                     //System.out.println("diff1  :"+diff);
                    Customer c1 = this.getCustomers().get(k);
                    Customer c2 = this.getCustomers().get(m);
                //   System.out.println("********Avant");
                //    System.out.println("tour1 "+tour1);
                //    System.out.println("tour2 "+tour2);
                    // System.out.println("Permutation done 1");
                    this.removeCustomer(c1);
                    this.removeCustomer(c2);
                    this.addCustomer(k, c2);
                    this.addCustomer(m, c1);
                    return true;
                }
            }
        }
        for (int k = 0; k < this.size(); k++) {
            for (int m = k + 1; m < this.size(); m++) {
                // System.out.println("********Avant22222222222222é");
                 diff = this.checkChangePositionClient(k, m, this.getTemps());
                if (diff > 0) {
                     //System.out.println("********Après2222222222");
                      // System.out.println("diff2  :"+diff);
                    Customer c1 = this.getCustomers().get(k);
                    this.removeCustomer(c1);
                    this.addCustomer(m, c1);
                    return true;
                }
            }
        }
        return false;
    }
          public boolean two_opt_dynamic() {
        double diff;
        //double thisCost = this.getObj2();
        for (int k = 0; k < this.size(); k++) {
            for (int m = k + 1; m < this.size(); m++) {
                 //System.out.println("********Avant11");
                diff = this.checkExchangeClient_dynamic(k, m, this.getTemps_dynamic());
                if (diff > 0) {
                     //System.out.println("diff1  :"+diff);
                    Customer c1 = this.getCustomers().get(k);
                    Customer c2 = this.getCustomers().get(m);
                //   System.out.println("********Avant");
                //    System.out.println("tour1 "+tour1);
                //    System.out.println("tour2 "+tour2);
                    // System.out.println("Permutation done 1");
                    this.removeCustomer(c1);
                    this.removeCustomer(c2);
                    this.addCustomer(k, c2);
                    this.addCustomer(m, c1);
                    return true;
                }
            }
        }
        for (int k = 0; k < this.size(); k++) {
            for (int m = k + 1; m < this.size(); m++) {
                // System.out.println("********Avant22222222222222é");
                 diff = this.checkChangePositionClient_dynamic(k, m, this.getTemps_dynamic());
                if (diff > 0) {
                     //System.out.println("********Après2222222222");
                      // System.out.println("diff2  :"+diff);
                    Customer c1 = this.getCustomers().get(k);
                    this.removeCustomer(c1);
                    this.addCustomer(m, c1);
                    return true;
                }
            }
        }
        return false;
    }
        public boolean two_opt_best_dynamic() {
        int best1 = -1;
        int best2 = -1;
        int typeOfChange = 0;
         double cout_avant=this.getTemps_dynamic();
       // Camion camion1 = this.getCamion();
        //this.removeCamion();//pour que camion1 soit considéré lors de la recherche de camion pour newTour1
        double maxDiff = 0;
        double diff;
        double thisCost = this.getTemps_dynamic();
        
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
        public double getcapacity(){
            if (this.id_fictif==0)
                return this.tourgroupe.getProblemD().getCapacitycamion_dynamic();
            else {
                Depotfictif d =tourgroupe.getProblemD().getdepotfictifById_dynamic(this.id_fictif);
                return d.capacité_restante;           
            }
        }


    public ArrayList<Customer> getPartOfCustomers(int firstPosition, int lastPosition) {
        ArrayList<Customer> part = new ArrayList<Customer>();
        for(int i=firstPosition;i<=lastPosition;i++)
            part.add(customers.get(i));
        return part;
    }
   
    public Camion removeCamion() {
        Camion oldCamion = this.getC();
        if (oldCamion==null)
        {
           this.setC(null);
            return null;
        }
        //tourGroup.removeCamionFromCamionsToUse(oldCamion);inutile pour restricted fleet
       // tourgroupe.updateCamionsToUse_remove(oldCamion);
        else {
        this.setC(null);
        //oldCamion.getTournee_attribuees().remove(this);
        return oldCamion;
        }
    }
   
      public void setCamion(Camion newCamion) {
        if (newCamion == this.getC()) {
            return;
        }
        this.removeCamion();
        this.setC(newCamion);
         /* System.out.println("ha best avant add:"+ newCamion.getTournee_attribuees().size() );
        newCamion.getTournee_attribuees().add(this);
        System.out.println("ha best après add:"+ newCamion.getTournee_attribuees().size() );*/
       // tourgroupe.updateCamionsToUse_add(newCamion);
    }
     
     public Customer nearestnonVisitedCustomerForTour(Customer customer) {
        double bestDistance = 1000000000;
        VRPS problem = this.tourgroupe.getProblem();
        Customer other = null;
        Tour tmpTour = this.clone();
        double tourQuantity = this.getQuantity();
        for (Customer c : problem.getCustomers()) {
            if (c == customer || tourgroupe.CheckCustomerInTourGroup(c) 
                    || problem.getTemps(this, c)>problem.getMaxTemps()+problem.getOvertime()
                    || tourQuantity+c.getDemande()>problem.getMaxCapacity()) {
                continue;
            }
            tmpTour.addCustomer(c);
            if (bestDistance > problem.getDistances(customer.getId(), c.getId())
                    && tourgroupe.CheckCamionforTour(tmpTour)){ 
                bestDistance = problem.getDistances(customer.getId(), c.getId());
                other = c;
            }
            tmpTour.removeCustomer(c);
        }
        //System.out.print("***"+other);
        return other;
    }
   public boolean checkDecroisement() {
       /* VRPS p = this.getAnt().getProblem();
        Customer c1 , c2, c3, c4;
        int id1, id2,id3,id4;
        double X1, Y1,X4,Y4;*/
        double cout_avant=this.getTemps();
      //  double diff;
                
        for (int k = 0; k < this.getCustomers().size()-3; k++) {
           
            for (int m = k + 2; m <this.getCustomers().size(); m++) {
              
                    ArrayList part = this.getPartOfCustomers(k, m);
                    this.removeCustomers(k, m);
                    ArrayList <Customer> part_renverse= this.reverse(part);
                    //tourGroup.reverse(part);
                    this.addCustomersAtPosition(part_renverse, k);
                    if (cout_avant-this.getTemps()>0)
                    {
                     //System.out.println("difference de temps décroisement"+(cout_avant-this.calculer_temps_tournee()));
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
    public boolean checkDecroisement_dynamic() {
       /* VRPS p = this.getAnt().getProblem();
        Customer c1 , c2, c3, c4;
        int id1, id2,id3,id4;
        double X1, Y1,X4,Y4;*/
        double cout_avant=this.getTemps_dynamic();
      //  double diff;
                
        for (int k = 0; k < this.getCustomers().size()-3; k++) {
          
            for (int m = k + 2; m <this.getCustomers().size(); m++) {
                
            
                    ArrayList part = this.getPartOfCustomers(k, m);
                    this.removeCustomers(k, m);
                    ArrayList <Customer> part_renverse= this.reverse(part);
                    //tourGroup.reverse(part);
                    this.addCustomersAtPosition(part_renverse, k);
                    if (cout_avant-this.getTemps_dynamic()>=0)
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
   public boolean addCustomerInBestPositionWithoutCheckConstraint_dynamic(Customer c) {
        int bestPosition = bestPositionInsertClientWithoutCheckConstraint_dynamic(c);
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
        return newTour.getTemps();
    }
    public double[] checkInsertClientWithoutCheckConstraint_dynamic(Customer customer, int position) {
        // v�rifier si l'�change pr�serve les contraintes du probl�me
        double [] tableau= new double[2];
        //System.out.println("haa VRPD dyal tour : "+this.getTourgroupe().getProblemD());
         //System.out.println("haa tourgroupe dyalha : "+this.getTourgroupe());
        Tour newTour = new Tour(tourgroupe);
               newTour= this.clone_dynamic();
              
               
      //  System.out.println("problemdynamic dyal this :" +this.getTourgroupe().getProblem().toString());
        newTour.addCustomer(position, customer);
        tableau[0]=newTour.getTemps_dynamic();
        tableau[1]=newTour.getQuantity();
        return tableau;
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
        public int bestPositionInsertClientWithoutCheckConstraint_dynamic(Customer customer) {
        double bestCost = 1000000000;
        int bestPosition = -1;
        //if(this.getCustomers().size()==0)
          //  bestPosition = 0;
        for (int p = 0; p < this.getCustomers().size(); p++) {
            if (((this.checkInsertClientWithoutCheckConstraint_dynamic(customer, p)[1])> this.getcapacity())||(this.checkInsertClientWithoutCheckConstraint_dynamic(customer, p)[0])>this.getTempsRestant())
            {
             continue;   
            }
            else
            {
            
            double cost = this.checkInsertClientWithoutCheckConstraint_dynamic(customer, p)[0];
            if (cost > 0 && cost <= bestCost) {
                bestCost = cost;
                bestPosition = p;
            }
            }
        }
        return bestPosition;
    }
   public double nearTo(Tour other){
        double nbNearPoints = 0;
        for(Customer c1:customers)
            for(Customer c2:other.getCustomers())
                if(tourgroupe.getProblem().getVRPtotal().isNear(c1.getId(), c2.getId()))
                    nbNearPoints++;
        return nbNearPoints;
    }
    
    public Tour nearestTour(ArrayList<Tour> tours){
        double best = 0;
        double near = 0;
        Tour nearest = null;
        for(Tour t:tours){
            near = this.nearTo(t);
            if(near>=best){
                best = near;
                nearest = t;
            }
        }
        return nearest;
    }
       public Boolean Identique_2sens(Tour tour) {
        boolean result = true;
        if (this.size() != tour.size()) {
            return false;
        }
        for (int k = 0; k < this.size(); k++) {
            if (this.getCustomers().get(k) != tour.getCustomers().get(k)) {
                //System.out.println("Tours different");
                result = false;
                break;
            }
        }

        if(result==false){//identiques mais inversé
            result = true;
            for (int k = 0; k < this.size(); k++) {
                if (this.getCustomers().get(k) != tour.getCustomers().get(this.size()-1-k)) {
                    //System.out.println("Tours different");
                    result = false;
                    break;
                }
            }
        }
        //System.out.println("Tours identiques");
        return result;
    }
        public Boolean Identique_2sens_dynamic(Tour tour) {
        boolean result = true;
        if ((this.size() != tour.size())||(this.id_fictif!=tour.id_fictif)) {
            return false;
        }
        for (int k = 0; k < this.size(); k++) {
            if (this.getCustomers().get(k) != tour.getCustomers().get(k)) {
                //System.out.println("Tours different");
                result = false;
                break;
            }
        }

        if(result==false){//identiques mais inversé
            result = true;
            for (int k = 0; k < this.size(); k++) {
                if (this.getCustomers().get(k) != tour.getCustomers().get(this.size()-1-k)) {
                    //System.out.println("Tours different");
                    result = false;
                    break;
                }
            }
        }
        //System.out.println("Tours identiques");
        return result;
    }
   
     @Override
      public Tour clone() {
        Tour o = null;
        try {
                // On récupère l'instance à renvoyer par l'appel de la 
                // méthode super.clone()
                o = (Tour) super.clone();
                o.tourgroupe = tourgroupe;
                o.customers = (ArrayList<Customer>) customers.clone();
               // o.setId_fictif(id_fictif);
                
            } catch(CloneNotSupportedException cnse) {
                // Ne devrait jamais arriver car nous implémentons 
                // l'interface Cloneable
                cnse.printStackTrace(System.err);
        }
        // on renvoie le clone
        return o;
    }
   public Tour clone_dynamic() {
        Tour o = null;
        try {
                // On récupère l'instance à renvoyer par l'appel de la 
                // méthode super.clone()
                o = (Tour) super.clone();
                //o.tourgroupe = tourgroupe;
               // o.tourgroupe.setProblemD(tourgroupe.getProblemD());
                o.customers = (ArrayList<Customer>) customers.clone();
                o.setId_fictif(id_fictif);
                
                
            } catch(CloneNotSupportedException cnse) {
                // Ne devrait jamais arriver car nous implémentons 
                // l'interface Cloneable
                cnse.printStackTrace(System.err);
        }
        // on renvoie le clone
        return o;
    }
   
    @Override
    public int compareTo(Tour other) {//this is best si int negatif
        if (this.getTemps()- other.getTemps()<0)
        return 1;
        else return -1;
        }
     public int compareTo_dynamic(Tour other) {//this is best si int negatif
        if (this.getTemps_dynamic()- other.getTemps_dynamic()<0)
        return 1;
        else return -1;
        }
    
     
    
    
    
}

    
