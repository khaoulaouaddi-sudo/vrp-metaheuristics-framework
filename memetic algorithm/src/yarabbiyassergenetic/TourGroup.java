package yarabbiyassergenetic;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author asus
 */
public class TourGroup implements Comparable<TourGroup>, Cloneable {

    private ArrayList<Tour> tours = new ArrayList<Tour>();
    //public VRP_solver VRP_solver;
    private VRPS problem;
    private VRPT problemT;
    private VRPD problemD;
    public double calculer_distance;
    public   ArrayList<Camion> camions= new ArrayList<Camion>();
    private String obj;
     private Random rnd = new Random();

    public double getCalculer_distance() {
        return calculer_distance;
    }

    public void setCalculer_distance(double calculer_distance) {
        this.calculer_distance = calculer_distance;
    }

   

    public ArrayList<Camion> getCamions() {
        return camions;
    }

    public void setCamions(ArrayList<Camion> camions) {
        this.camions = camions;
    }

    public VRPD getProblemD() {
        return problemD;
    }

    public void setProblemD(VRPD problemD) {
        this.problemD = problemD;
    }

    public Random getRnd() {
        return rnd;
    }

    public void setRnd(Random rnd) {
        this.rnd = rnd;
    }
    
    
    //private ArrayList<Camion> camionsToUse = new ArrayList<Camion>();
    

    public TourGroup(VRPS problem){// ArrayList<Camion> camions) {
      
        tours = new ArrayList<Tour>();
        this.problem = problem;
       initialiser_camions();
        
       
    }
    public TourGroup(VRPD problem){// ArrayList<Camion> camions) {
      
        tours = new ArrayList<Tour>();
        this.problemD = problem;
        initialiser_camions_dynamic();
        
        
          }

    public void initialiser_camions(){
       //this.camions= new ArrayList<Camion>(); 
         this.camions.clear();
         for (int i=0; i<this.problem.getCamions().size();i++)
            {
              this.getCamions().add(this.problem.getCamions().get(i));
            }
        /* for (int i=0; i<this.getCamions().size(); i++)
         {
            this.getCamions().get(i).tournee_attribuees= new ArrayList<Tour>();
             this.getCamions().get(i).getTournee_attribuees().clear();
         }*/
            
    }
    public void initialiser_camions_dynamic(){
        this.getCamions().clear();
         for (int i=0; i<this.getProblemD().getCamions_dynamic().size();i++)
            {
              this.getCamions().add(this.getProblemD().getCamions_dynamic().get(i));
            }
        /* for (int i=0; i<this.getCamions().size(); i++)
         {
             this.getCamions().get(i).getTournee_attribuees().clear();
         }
           */ 
    }
    public void update_tournes_attribues(){
        for (int i=0; i<this.getCamions().size(); i++)
         {
             this.getCamions().get(i).getTournee_attribuees().clear();
             for (Tour t: this.getTours())
         {
            // System.out.println("haaa camion t:"+t.getC().getIdcamion());
              //System.out.println("haaa camion id camion:"+this.getCamions().get(i).getIdcamion());
              if ((t.getC().getIdcamion()==this.getCamions().get(i).getIdcamion())&&
                     (!this.getCamions().get(i).getTournee_attribuees().contains(t)))
             this.getCamions().get(i).getTournee_attribuees().add(t);
         }
             classer_tournees(this.getCamions().get(i).getTournee_attribuees());
         }
        
    }
    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }
    
    

    public ArrayList<Tour> getTours() {
        return tours;
    }
    
    public VRPT getProblemT()
    {
        return this.getProblem().getVRPtotal();
    }
    
    

    public void setTours(ArrayList<Tour> tours) {
        this.tours = tours;
    }

    public VRPS getProblem() {
        return problem;
    }

    public void setProblem(VRPS problem) {
        this.problem = problem;
    }

    /*public ArrayList<Camion> getCamionsToUse() {
        return camionsToUse;
    }*/

   /* public void setCamionsToUse(ArrayList<Camion> camionsToUse) {
        this.camionsToUse = camionsToUse;
    }*/
    
      public void addTour(Tour et) {
        tours.add(et);
        et.tourgroupe = this;
       
    }

    public void addTour(int position, Tour et) {
        tours.add(position, et);
        et.setTourgroupe(this); 
        
    }
    public boolean CheckCustomerInTourGroup(Customer customer) {
        for (Tour tour : this.getTours()) {
            if (tour.CheckCustomerInTour(customer) == true) {
                return true;
            }
        }
        return false;
    }
    /* public void initialiseCamionsToUse1() {
          System.out.println("ajout d'un camions à camion to use: "+problem.getCamions().size());
          camionsToUse.clear();
        for(Camion c: problem.getCamions()){
            
         camionsToUse.add(c);
        }
        System.out.println("ajout d'un camions à camion to use: "+problem.getCamions().size());
    }*/

    public TourGroup solve_insertion() {
        System.out.println("******SEQ*******");
        TourGroup solution = new TourGroup(problem);
        int nbCustomersUsed = 0;
        int nbCustomers = this.problem.getCustomers().size();
        Random rnd = new Random();
        Customer customer = this.problem.getCustomers().get(rnd.nextInt(nbCustomers));
        Tour tour = new Tour(solution);
        solution.addTour(tour);
   //     System.out.println("client avant :"+solution.getCamionsToUse().get(0).getTournee_attribuees().get(0).getCustomers().get(0).getId());

        while (nbCustomersUsed != nbCustomers) {

            while (solution.CheckCustomerInTourGroup(customer)) {
                customer = problem.getCustomers().get(rnd.nextInt(nbCustomers));
            }

            if (tour.getQuantity()+ customer.getDemande() > problem.MaxCapacity
                    || problem.getTemps(tour, customer) > problem.getMaxTemps()+problem.getOvertime()) {
                if (tour.size() > 0) {
                    //tour.setBestCamion();
                    //ystem.out.println("camion " + tour.getCamion());
                    tour = new Tour(solution);
                    solution.addTour(tour);
                }
            }
            tour.addCustomer(customer);
            nbCustomersUsed++;
           
        }
       
      /* if (tour.size() > 0) {
            tour.setBestCamion();
        }*/
       /* Camion ca = solution.camionNotRespectingTimeConstraint();
        if (ca != null) {
            System.out.println("pb contrainte de temps " + ca.toString());
        }
        Tour tr = solution.FindTimeTourConstraint();
        if (tr != null) {
            System.out.println("pb contrainte de temps " + tr.toString());
        }*/
        //solution.minimizeLTRByTours();
        //System.out.println(new Individual2(solution,problem));
//		problem.AffecterLieuELVs(solution);
        solution.allocateTours2();
        solution.improve(problem.getNbImprove());
        //System.out.println(solution);
        /*
        int k=1;
     for (int i=0; i<solution.getCamionsToUse().size();i++)
     {
         for(int j=0; j<solution.getCamionsToUse().get(i).getTournee_attribuees().size();j++)
         {
             for (int l=0;l<solution.getCamionsToUse().get(i).getTournee_attribuees().get(j).getCustomers().size(); l++)
             {
                 System.out.println("k  :"+k);
       System.out.println("client  :"+solution.getCamionsToUse().get(i).getTournee_attribuees().get(j).getCustomers().get(l).getId());
       k++;
        
             }
         }
        
     }*/
         return solution;
    }
    public TourGroup clarckwhrite() {
		// should clone the problem, so as to not modify it after resolution
	
            System.out.println("******C&W*******");
            TourGroup solution = createInitialTourGroup(problem);
            while(solution == null){
                //System.out.println("ha howa dkhal l boucle createintialtourgroupe:    jkjghcsqh");
                solution = createInitialTourGroup(problem);
            }
            System.out.println(solution);
            boolean improved = true;
            
                while ( improved ) {
                     //System.out.println("ha howa dkhal l boucle optimizetour:    jkjghcsqh");
                        improved = optimizeTour(solution);
                       // System.out.println(tourGroup);
                }
                solution.removeAllCamionsFromTourGroup();
                //System.out.println("TourGroup found after C&W: "+solution);
                //System.out.println("camionsToUse after remove: "+solution.getCamionsToUse().size());
              //  solution.initialiseCamionsToUse1();
               // System.out.println("camionsToUse after initialize: "+solution.getCamionsToUse().size());
               
                
                solution.allocateTours2();
                //System.out.println("camionsToUse after Initialize: "+solution.getCamionsToUse());
               /* int q =0;
                Tour t;
                while(q<solution.getTours().size()){
                    t = solution.getTours().get(q);
                    if(t.getC() == null){
                        //System.out.println("Search vehicle for tour: "+t);
                        if(solution.CheckCamionforTour(t) == true){
                            t.setBestCamion();
                        }
                    }
                    q++;
                }*/
            
           
               /* Camion ca = solution.camionNotRespectingTimeConstraint();
                if(ca!=null)
                    System.out.println("pb contrainte de temps "+ca.toString());
                Tour tr = solution.FindTimeTourConstraint();
                if(tr!=null)
                    System.out.println("pb contrainte de temps "+tr.toString());*/

                solution.improve(problem.getNbImprove());
                System.out.println(solution);
            /*    improved = true;
                while ( improved ) {
                        improved = solution.minimizeLTR();
                       // System.out.println(tourGroup);
                }
            */
                System.out.println("CW tourgroup: "+solution);
		return solution;
	}
    public void removeemptytours(){
        ArrayList<Tour> trs= new ArrayList<Tour>();
        for (Tour t: this.getTours())
                    {
                        if (t.getCustomers().isEmpty()){
                            trs.add(t);
                        }
                           
                    }
        this.getTours().removeAll(trs);
        
    }
    public TourGroup solve_PPV() {
		
            System.out.println("******PPV*******");
            TourGroup solution = new TourGroup(problem);
            Tour tour = new Tour(solution);
            solution.addTour(tour);
            int nbCustomersUsed = 0;
            int nbCustomers = problem.getCustomers().size();
            Customer lastCustomer = null;
            Customer customer ;

            while(nbCustomersUsed != nbCustomers) {

                    if(tour.size()==0){// On choisi aléatoirement le premier client à visiter
                       //  System.out.println("customer near to depot avaaant ");
                    
                        customer= solution.nearestnonVisitedCustomerToDepot();
                       // System.out.println("customer near to depot après "+customer);
                    }
                    else{// On choisi le plus proche client au client précédent
                        //if(lastCustomer.getId()==30)
                        //    System.out.println("customer 30");
                        customer= tour.nearestnonVisitedCustomerForTour(lastCustomer);
                        //System.out.println("customer men ghir hadak lli 7da depot "+customer);
                    }
                    if( customer==null ){
                        //System.out.println("Tour f le cas dyal customer null "+tour);
                        //tour.setBestCamion();
                        tour = new Tour(solution);
                        solution.addTour(tour);
                        continue;
                    }
                    else {
                        lastCustomer = customer;
                        tour.addCustomer(customer);
                        nbCustomersUsed++;                    
                    }
                } 
                // ajouter le dernier tour créé dans la boucle
		//tour.setBestCamion();
                
               /* Camion ca = solution.camionNotRespectingTimeConstraint();
                if(ca!=null)
                    System.out.println("pb contrainte de temps camion "+ca.toString());
                Tour tr = solution.FindTimeTourConstraint();
                if(tr!=null)
                    System.out.println("pb contrainte de temps "+tr.toString());*/
		
//		problem.AffecterLieuELVs(solution);
                solution.improve(problem.getNbImprove());
                System.out.println(solution);
		return solution;
	}
          public void improve(int iteration){
        
        
        //long datefin = new Date().getTime();
        //long duree0 = datefin-datedeb;
       // System.out.println("duree decroisement: "+duree0);       
        
       long datedeb = new Date().getTime();
        //System.out.println("tourgoup: "+this);       
        //for(Tour t:this.getTours()){
        for(int i=0; i<this.getTours().size();i++){
            Tour t = this.getTours().get(i);
             if(t.size()==0){
                this.remove(t);
                i--;
            }
                //System.out.println("t before: "+t); 
                t.two_opt_iterative();
                //System.out.println("t after: "+t); 
            
           
        }
        long datefin = new Date().getTime();
       long duree0 = datefin-datedeb;
        
        datedeb = new Date().getTime();
        this.two_opt(iteration);
       
        datefin = new Date().getTime();
        duree0 = datefin-datedeb;
        this.allocateTours2();
        System.out.println("allocation: ");       
      
       /* datedeb = new Date().getTime();
        TourGroup trgp = this.minimizeLTRByTours();
        if(trgp!=null)
            this.copyTourGroup(trgp);
        datefin = new Date().getTime();
        duree0 = datefin-datedeb;
        System.out.println("duree minimize LTR: "+duree0);   */    
                //System.out.println("before minimise LTR by tours: "+this);
        //myContainer.add(this.drawTourGroup());
        //this.CheckCamionsConstraint();
        
        
        System.out.println("duree 2opt tourGroup: "+duree0);       
        //this.CheckCamionsConstraint();
        //System.out.println(this);
        //myContainer.add(this.drawTourGroup());
        //JOptionPane.showMessageDialog(null, myContainer);
}
          public void improve_dynamic(int iteration){
        
        
        //long datefin = new Date().getTime();
        //long duree0 = datefin-datedeb;
       // System.out.println("duree decroisement: "+duree0);       
        
       long datedeb = new Date().getTime();
        //System.out.println("tourgoup: "+this);       
        //for(Tour t:this.getTours()){
        for(int i=0; i<this.getTours().size();i++){
            Tour t = this.getTours().get(i);
             if(t.size()==0){
                this.remove(t);
                i--;
            }
                //System.out.println("t before: "+t); 
                t.two_opt_iterative_dynamic();
                //System.out.println("t after: "+t); 
            
           
        }
        long datefin = new Date().getTime();
       long duree0 = datefin-datedeb;
        
        datedeb = new Date().getTime();
        this.two_opt_dynamic(iteration);
       
        datefin = new Date().getTime();
        duree0 = datefin-datedeb;
       
        
        System.out.println("duree 2opt tourGroup: "+duree0);       
        
}
           public boolean two_opt(int iteration) {
            boolean improved1=true; int c= 0;
            while(improved1==true && c<iteration){
                    improved1 = this.two_opt();    
                //System.out.println("Move "+c+"\nafter move"+this);
                c++;
            }
            //System.out.println("Nb moves "+c);    
            if(c>0)
                return true;
            return false;
    }
             public boolean two_opt_dynamic(int iteration) {
            boolean improved1=true; int c= 0;
            while(improved1==true && c<iteration){
               
                // System.out.println("résultaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaat two_opt: truuuuuuuuuuuue");
              
                    improved1 = this.two_opt_dynamic(); 
                  //  System.out.println("résultat two_opt: "+improved1);
                //System.out.println("Move "+c+"\nafter move"+this);
                c++;
            }
            //System.out.println("Nb moves "+c);    
            if(c>0)
                return true;
            return false;
    }
            public void sort() {
        Collections.sort(this.getTours());
    }
    public boolean two_opt() {
        int nbTours = this.getTours().size();
        if (nbTours < 2) {
            return false;
        }
        // Echange entre les tours deux à deux
        //System.out.println("before twoOpt: "+this);
        this.sort();
        //System.out.println("after sort: "+this);
       for(int i=0;i<this.getTours().size();i++){
            Tour tour1 = this.getTours().get(i);
            for(int j=i+1;j<this.getTours().size();j++){
              
                 Tour tour2 = this.getTours().get(j);
               //System.out.println("ch7al men tournée 3andi: "+this.getTours().size() );
                if( twoOpt(tour1, tour2)==true){
                   //System.out.println("ila dar chi twoopt bssa7");
                        if (tour1.size() == 0) 
                            this.remove(tour1);
                        if (tour2.size() == 0) 
                            this.remove(tour2);
                       
                        return true;
                    }
            }
                    /*
            }
        }
        for (int i = this.getTours().size()-1; i >0; i--) {
            Tour tour1 = this.getTours().get(i);
            
                
            ArrayList<Tour> list = new ArrayList<Tour>();
            
            for (int j = i - 1; j>=0; j--) 
                list.add(this.getTours().get(j));
            while(!list.isEmpty()){
                Tour tour2 = tour1.nearestTour(list);//list.get(new Random().nextInt(list.size()));//
                //System.out.println(" tour2:"+tour2);
        
                if( twoOpt(tour1, tour2)==true){
                        if (tour1.size() == 0) 
                            this.remove(tour1);
                        if (tour2.size() == 0) 
                            this.remove(tour2);
                        //System.out.println("after twoOpt: "+this);
                        //System.out.println("camionsToUse");
                        //for(Camion c:camionsToUse)
                        //    System.out.println(c.getId()+",");
                        //System.out.println("");

                        return true;
                    }
                list.remove(tour2);
            }
            /**/
                /*if(c>0){
                    tour1.two_opt_Decroisement();
                    tour2.two_opt_Decroisement();
                }*/
                   
        }
       // }
        return false;
    }
    public boolean two_opt_dynamic() {
        int nbTours = this.getTours().size();
        if (nbTours < 2) {
            return false;
        }
        // Echange entre les tours deux à deux
        //System.out.println("before twoOpt: "+this);
        sort_dynamic(this.tours);
        //System.out.println("after sort: "+this);
       for(int i=0;i<this.getTours().size();i++){
            Tour tour1 = this.getTours().get(i);
            for(int j=i+1;j<this.getTours().size();j++){
              
                 Tour tour2 = this.getTours().get(j);
               //System.out.println("ch7al men tournée 3andi: "+this.getTours().size() );
                if( twoOpt_dynamic(tour1, tour2)==true){
                   //System.out.println("ila dar chi twoopt bssa7");
                        if ((tour1.size() == 0)&&(tour1.id_fictif==0) )
                            this.remove(tour1);
                        if ((tour2.size() == 0)&&(tour2.id_fictif==0)) 
                            this.remove(tour2);
                       
                        return true;
                    }
            }
            
        }
       // }
        return false;
    }
      public boolean twoOpt(Tour tour1, Tour tour2) {
        TourGroup newTourGroup ;
        //System.out.println("before twoOpt \n"+tour1+"\n"+tour2);
        //System.out.println("cost tourGourp "+this.getObj1());
        //System.out.println("Exchange");
        newTourGroup =  checkExchangeCustomers(tour1, tour2);
        if(newTourGroup!=null){
           // System.out.println("Permutation done");
          //System.out.println("copy tougroup");//.getObj2());
            this.copyTourGroup(newTourGroup);
           // System.out.println("tourGourp2.......... "+this);//.getObj2());
            //System.out.println("cost tourGourp "+this);//.getObj2());
            return true;
        }  
       //System.out.println("Move");
        newTourGroup = checkMoveCustomerFromTourToTour(tour1, tour2);
        if(newTourGroup!=null){
          // System.out.println("Move 1 done");
           
            //System.out.println("tourGourp1 "+this);//.getObj2
            //System.out.println("copy move");
            this.copyTourGroup(newTourGroup);
           //System.out.println("Move 1 done");
           // System.out.println("tourGourp2 "+this);//.getObj2());
            return true;
        }
        newTourGroup = checkMoveCustomerFromTourToTour(tour2, tour1);
        if(newTourGroup!=null){
          //  if(this.getLTR()<=problem.getMaxTemps() &&
                 //   newTourGroup.getLTR()>problem.getMaxTemps())
               //System.out.println("Move 2 done");
            //System.out.println("tourGourp1 "+this);//.getObj2());
            this.copyTourGroup(newTourGroup);
           /// System.out.println("Move 2 done");
            //System.out.println("tourGourp2222222222 "+this);//.getObj2());
            return true;
        }
       // System.out.println("Exchange");
        
        return false;
    }
       public boolean twoOpt_dynamic(Tour tour1, Tour tour2) {
        TourGroup newTourGroup ;
        //System.out.println("before twoOpt \n"+tour1+"\n"+tour2);
        //System.out.println("cost tourGourp "+this.getObj1());
        //System.out.println("Exchange");
        newTourGroup =  checkExchangeCustomers_dynamic(tour1, tour2);
        if(newTourGroup!=null){
           // System.out.println("Permutation done");
          //System.out.println("copy tougroup");//.getObj2());
            this.copyTourGroup_dynamic(newTourGroup);
           // System.out.println("tourGourp2.......... "+this);//.getObj2());
            //System.out.println("cost tourGourp "+this);//.getObj2());
            return true;
        }  
       //System.out.println("Move");
        newTourGroup = checkMoveCustomerFromTourToTour_dynamic(tour1, tour2);
        if(newTourGroup!=null){
          // System.out.println("Move 1 done");
           
            //System.out.println("tourGourp1 "+this);//.getObj2
            //System.out.println("copy move");
            this.copyTourGroup_dynamic(newTourGroup);
           //System.out.println("Move 1 done");
           // System.out.println("tourGourp2 "+this);//.getObj2());
            return true;
        }
        newTourGroup = checkMoveCustomerFromTourToTour_dynamic(tour2, tour1);
        if(newTourGroup!=null){
          //  if(this.getLTR()<=problem.getMaxTemps() &&
                 //   newTourGroup.getLTR()>problem.getMaxTemps())
               //System.out.println("Move 2 done");
            //System.out.println("tourGourp1 "+this);//.getObj2());
            this.copyTourGroup_dynamic(newTourGroup);
           /// System.out.println("Move 2 done");
            //System.out.println("tourGourp2222222222 "+this);//.getObj2());
            return true;
        }
       // System.out.println("Exchange");
        
        return false;
    }
       public TourGroup checkMoveCustomerFromTourToTour(Tour tour1, Tour tour2) {
        if(tour1==tour2)
            return null;
        double quantityTour2 = tour2.getQuantity();
        if(tour1.getLowerDemand()+quantityTour2>problem.getMaxCapacity())
            return null;
        TourGroup newTourGroup;
       
        for(int k=0;k<tour1.size();k++){ 
            //if(tour1.getCustomer(k).getDemande()+quantityTour2<=problem.getMaxCapacity()){
                newTourGroup = checkMoveCustomerFromTourToTour(tour1, tour2, k);
                if(newTourGroup!=null){
                    return newTourGroup;
                }
            //}
        }
        return null;
    }
        public TourGroup checkMoveCustomerFromTourToTour(Tour tour1, Tour tour2, int positionTour1) {
        //********Insertion du client dans position1 de tour1 dans la tour 2 à la position2
        Customer c1 = tour1.getCustomers().get(positionTour1);
        double newQuantity2 = tour2.getQuantity() + c1.getDemande();
        if (newQuantity2 > problem.getMaxCapacity()) {
            return null;
        }
        //créer un nouveau tourGroup
        TourGroup newTrGp = new TourGroup(problem);
        //Construre les nouvelles tournées
        Tour newTour1 = new Tour(newTrGp);//tour without positionTour1
        /*for (int a = 0; a < tour1.size(); a++) {
            if (a != positionTour1) {
                newTour1.addCustomer(tour1.getCustomer(a));
            }
        }*/
        newTour1 = tour1.clone();
        newTour1.removeCustomer(positionTour1);
        /*Tour newTour2 = new Tour(newTrGp);
        for (int a = 0; a < tour2.size(); a++) {
            newTour2.addCustomer(tour2.getCustomer(a));
        }*/
        //System.out.println("newTour2 before adding "+newTour2);
        //if(newTour2.addCustomerInBestPosition(c1)==false)
        Tour newTour2 = tour2.clone();
        if(newTour2.addCustomerInBestPositionWithoutCheckConstraint(c1)==false)
            return null;
        //System.out.println("newTour2  after adding"+newTour2);
        //newTour2.two_opt_iterative();
        if(newTour2.getTemps()>problem.getMaxTemps()+problem.getOvertime())
            return null;
            //if(this.getCost()-newTrGp.getCost()>0){
        double diff;
       
       
        
            for(Tour t: this.getTours())
            if(t!=tour1 && t!=tour2)
                newTrGp.addTourToCopy(t);
        
            /*newTrGp = this.clone();
            newTrGp.addTour(this.tours.indexOf(tour1), newTour1);
            newTrGp.addTour(this.tours.indexOf(tour2), newTour2);*/
            //System.out.println("before allocate:"+newTrGp);
            newTour1.removeCamion();
            newTour2.removeCamion();
            newTrGp.addTour(newTour1);
            newTrGp.addTour(newTour2);
            newTrGp.allocateTours2();
            //System.out.println("after allocate newTrGp"+newTrGp);
            if(!newTrGp.CheckCamionNullConstraint())
                return null;
            //System.out.println("after allocate:"+newTrGp);
           /* TourGroup trgp = newTrGp.minimizeLTRByTours();
           

            //System.out.println("after minimize LTR:"+trgp);
            if(trgp!=null){
                newTrGp = trgp.clone();//.copyTourGroup(trgp);
               
            }*/
            if(this.getLTR()<=this.problem.maxTemps ){
                if (newTrGp.getLTR()> this.problem.maxTemps)
                {
                    return null;
                }
                else 
                {
                  diff = this.getdistance()-newTrGp.getdistance();
                     if(diff>0){
                    //System.out.println("obj2:"+newTrGp);
                    return newTrGp;
                }
                     else return null;
                }
               
            }
            else{ // cost(TourGroup) = somme (Cost(tours))
                diff = this.getLTR()-newTrGp.getLTR();
                if(diff>0){
                    //System.out.println("obj1:"+newTrGp);
                    return newTrGp;
                }
            }
        
        return null;
    }
      public TourGroup checkExchangeCustomers(Tour tour1, Tour tour2) {
        if(tour1==tour2)
            return null;
        TourGroup newTourGroup;
        for(int k=0;k<tour1.size();k++){
            for(int m=0;m<tour2.size();m++){
              //  System.out.println("check..............Exchange");
                newTourGroup = checkExchangeCustomers(tour1, tour2, k, m);
                //System.out.println("check..............Exchange afteeeeeeeeeeeeeeeeeeeeeeeeeeer");
                    if(newTourGroup!=null){
                        return newTourGroup;
                    }
            }
        }
        return null;
    }
    
    public TourGroup checkExchangeCustomers(Tour tour1, Tour tour2, int positionTour1, int positionTour2) {
        //********Insertion du client dans position1 de tour1 dans la tour 2 à la position2
        //créer un nouveau tourGroup
        Customer c1 = tour1.getCustomers().get(positionTour1);
        Customer c2 = tour2.getCustomers().get(positionTour2);
        double newQuantity1 = tour1.getQuantity() //quantit� distribu�e lors de la tourn�e
                - c1.getDemande()
                + c2.getDemande();  //quantit� demand�e du client jPrime de la tourn�e j
        if (newQuantity1 > problem.getMaxCapacity()) 
            return null;
        // => nouvelle quantit� de la tourn�e i
        double newQuantity2 = tour2.getQuantity()
                + c1.getDemande()
                - c2.getDemande();
        if (newQuantity2 > problem.getMaxCapacity()) 
            return null;
        //********Construire les nouvelles tourxnées
      //  System.out.println("trgggggggggggggg : "+this);
        TourGroup newTrGp = new TourGroup(problem);
        //newTrGp.remove(positionTour1);newTrGp.remove(positionTour2);
        //System.out.println("new trg"+newTrGp);
        
        Tour newTour1 = tour1.clone();//new Tour(newTrGp);//tour without positionTour1
        //newTrGp.addTour(newTour1);
       // System.out.println("befoooooooooooooooooooore remove: ");
        newTour1.removeCustomer(c1);
        /*for (Customer c : tour1.getCustomers()) {
            if (c != c1) 
                newTour1.addCustomer(c);
            }*/
         //System.out.println("befooooooooooooooooooooooooooore addcostomerrrrrrrrrrrrrrrrr ");
        if(newTour1.addCustomerInBestPositionWithoutCheckConstraint(c2)==false){
        //if(newTour1.addCustomerInBestPosition(c2)==false){
            return null;
        }
       //System.out.println("after add customer "+newTour1);
        //System.out.println("befoooooooooooooooooooooooooooore two opt iterative");
       // newTour1.two_opt_iterative();
       //System.out.println("after decross "+newTour1);
        if(newTour1.getTemps()>problem.getMaxTemps()+problem.getOvertime())
        { //System.out.println("temps depasse temps maximal "+newTour1);
            return null;}
        //System.out.println("newTour1 after "+newTour1);
        
        Tour newTour2 = tour2.clone();//new Tour(newTrGp);
       // System.out.println("befoooooooooooooooooooooooooooore remooooooooooooooooooooove");
        newTour2.removeCustomer(c2);
        /*for (Customer c : tour2.getCustomers()) {
            if (c != 1c2) 
                newTour2.addCustomer(c);
        }*/
       // System.out.println("befoooooooooooooooooooooooooooore addddddddddddddd costomer 2");
        if(newTour2.addCustomerInBestPositionWithoutCheckConstraint(c1)==false){
            return null;
        }
       // System.out.println("befoooooooooooooooooooooooooooore two opt iterative 2222222");
       // newTour2.two_opt_iterative();
       //System.out.println("after twoooooooooooooooooooooopt2");
        if(newTour2.getTemps()>problem.getMaxTemps()+problem.getOvertime())
            return null;
        //System.out.println("newTour2 after"+newTour2);
        //********Fin Construire les nouvelles tournées
        
        //boolean minObj2 = false;
        double diff ;
       
        
            for(Tour t: this.getTours())
            if(t!=tour1 && t!=tour2)
                newTrGp.addTourToCopy(t);
        
            /*newTrGp = this.clone();
            newTrGp.addTour(this.tours.indexOf(tour1), newTour1);
            newTrGp.addTour(this.tours.indexOf(tour2), newTour2);*/
            //System.out.println("before allocate:"+newTrGp);
            newTour1.removeCamion();
            newTour2.removeCamion();
            newTrGp.addTour(newTour1);
            newTrGp.addTour(newTour2);
            newTrGp.allocateTours2();
           // System.out.println("befooooooooooooooooooooooooore allocate_touuuuuuuuur lli ldaakheeel ");
            if(!newTrGp.CheckCamionNullConstraint())
                return null;
           // System.out.println("befooooooooooooooooooooore minimize LTR lli ldaakheeel ");
           /* TourGroup trgp = newTrGp.minimizeLTRByTours();
            
            //System.out.println("after minimize LTR lli ldaakheeel ");
            if(trgp!=null){
                newTrGp = trgp.clone();//.copyTourGroup(trgp);
                
            }*/
            if(this.getLTR()<=this.problem.maxTemps ){
                if (newTrGp.getLTR()> this.problem.maxTemps)
                {
                    return null;
                }
                else 
                {
                  diff = this.getdistance()-newTrGp.getdistance();
                     if(diff>0){
                    //System.out.println("obj2:"+newTrGp);
                    return newTrGp;
                }
                     else return null;
                }
               
            }
            else{ // cost(TourGroup) = somme (Cost(tours))
                diff = this.getLTR()-newTrGp.getLTR();
                if(diff>0){
                    //System.out.println("obj1:"+newTrGp);
                    return newTrGp;
                }
            }
            
        //System.out.println("fin ");
        
            return null;
    }
     public TourGroup checkMoveCustomerFromTourToTour_dynamic(Tour tour1, Tour tour2) {
        if(tour1==tour2)
            return null;
        double quantityTour2 = tour2.getQuantity();
        
        if(tour1.getLowerDemand()+quantityTour2>tour2.getcapacity())
            return null;
        TourGroup newTourGroup;
       
        for(int k=0;k<tour1.size();k++){ 
            //if(tour1.getCustomer(k).getDemande()+quantityTour2<=problem.getMaxCapacity()){
                newTourGroup = checkMoveCustomerFromTourToTour_dynamic(tour1, tour2, k);
                if(newTourGroup!=null){
                    return newTourGroup;
                }
            //}
        }
        return null;
    }
        public TourGroup checkMoveCustomerFromTourToTour_dynamic(Tour tour1, Tour tour2, int positionTour1) {
        //********Insertion du client dans position1 de tour1 dans la tour 2 à la position2
        Customer c1 = tour1.getCustomers().get(positionTour1);
        double newQuantity2 = tour2.getQuantity() + c1.getDemande();
        if (newQuantity2 > tour2.getcapacity()) {
            return null;
        }
        //créer un nouveau tourGroup
        TourGroup newTrGp = new TourGroup(problemD);
        //Construre les nouvelles tournées
        Tour newTour1 = new Tour(newTrGp);//tour without positionTour1
        /*for (int a = 0; a < tour1.size(); a++) {
            if (a != positionTour1) {
                newTour1.addCustomer(tour1.getCustomer(a));
            }
        }*/
        newTour1 = tour1.clone();
        newTour1.removeCustomer(positionTour1);
        /*Tour newTour2 = new Tour(newTrGp);
        for (int a = 0; a < tour2.size(); a++) {
            newTour2.addCustomer(tour2.getCustomer(a));
        }*/
        //System.out.println("newTour2 before adding "+newTour2);
        //if(newTour2.addCustomerInBestPosition(c1)==false)
        Tour newTour2 = tour2.clone_dynamic();
        if(newTour2.addCustomerInBestPositionWithoutCheckConstraint_dynamic(c1)==false)
            return null;
        //System.out.println("newTour2  after adding"+newTour2);
        //newTour2.two_opt_iterative();
        if(newTour2.getTemps_dynamic()>tour2.getTempsRestant())
            return null;
            //if(this.getCost()-newTrGp.getCost()>0){
        double diff;
       
       
        
            for(Tour t: this.getTours())
            if(t!=tour1 && t!=tour2)
                newTrGp.addTourToCopy_dynamic(t);
        
            /*newTrGp = this.clone();
            newTrGp.addTour(this.tours.indexOf(tour1), newTour1);
            newTrGp.addTour(this.tours.indexOf(tour2), newTour2);*/
            //System.out.println("before allocate:"+newTrGp);
            newTour1.removeCamion();
            newTour2.removeCamion();
            newTrGp.addTour(newTour1);
            newTrGp.addTour(newTour2);
            newTrGp.allocateTours2_dynamic();
            //System.out.println("after allocate newTrGp"+newTrGp);
            if(!newTrGp.CheckCamionNullConstraint())
                return null;
            //System.out.println("after allocate:"+newTrGp);
           /* TourGroup trgp = newTrGp.minimizeLTRByTours_dynamic();
           /* for (int i=0; i<newTrGp.getTours().size();i++)
                {
                    System.out.println("haa tour before clone :"+newTrGp.getTours().get(i));
                    System.out.println("haa tour size :"+newTrGp.getTours().get(i).getCustomers().size());
                    System.out.println("haa camion dyal tour :"+newTrGp.getTours().get(i).getC());
                }*/
            //System.out.println("after minimize LTR:"+trgp);
           // if(trgp!=null){
              //  newTrGp = trgp.clone_dynamic();//.copyTourGroup(trgp);
                /*for (int i=0; i<newTrGp.getTours().size();i++)
                {
                    System.out.println("haa tour after clone :"+newTrGp.getTours().get(i));
                    System.out.println("haa tour size :"+newTrGp.getTours().get(i).getCustomers().size());
                    System.out.println("haa camion dyal tour :"+newTrGp.getTours().get(i).getC());
                }*/
           // }
            if(this.getLTR_dynamic()<=this.problemD.getMaxTemps_dynamic() ){
                if (newTrGp.getLTR_dynamic()> this.problemD.getMaxTemps_dynamic())
                {
                    return null;
                }
                else 
                {
                  diff = this.getdistance_dynamic()-newTrGp.getdistance_dynamic();
                     if(diff>0){
                    //System.out.println("obj2:"+newTrGp);
                    return newTrGp;
                }
                     else return null;
                }
               
            }
            else{ // cost(TourGroup) = somme (Cost(tours))
                diff = this.getLTR_dynamic()-newTrGp.getLTR_dynamic();
                if(diff>0){
                    //System.out.println("obj1:"+newTrGp);
                    return newTrGp;
                }
            }
        
        return null;
    }
      public TourGroup checkExchangeCustomers_dynamic(Tour tour1, Tour tour2) {
        if(tour1==tour2)
            return null;
        TourGroup newTourGroup;
        for(int k=0;k<tour1.size();k++){
            for(int m=0;m<tour2.size();m++){
              //  System.out.println("check..............Exchange");
                newTourGroup = checkExchangeCustomers_dynamic(tour1, tour2, k, m);
                //System.out.println("check..............Exchange afteeeeeeeeeeeeeeeeeeeeeeeeeeer");
                    if(newTourGroup!=null){
                        return newTourGroup;
                    }
            }
        }
        return null;
    }
    
    public TourGroup checkExchangeCustomers_dynamic(Tour tour1, Tour tour2, int positionTour1, int positionTour2) {
        //********Insertion du client dans position1 de tour1 dans la tour 2 à la position2
        //créer un nouveau tourGroup
        Customer c1 = tour1.getCustomers().get(positionTour1);
        Customer c2 = tour2.getCustomers().get(positionTour2);
        double newQuantity1 = tour1.getQuantity() //quantit� distribu�e lors de la tourn�e
                - c1.getDemande()
                + c2.getDemande();  //quantit� demand�e du client jPrime de la tourn�e j
        if (newQuantity1 >tour1.getcapacity()) 
            return null;
        // => nouvelle quantit� de la tourn�e i
        double newQuantity2 = tour2.getQuantity()
                + c1.getDemande()
                - c2.getDemande();
        if (newQuantity2 > tour2.getcapacity()) 
            return null;
        //********Construire les nouvelles tourxnées
      //  System.out.println("trgggggggggggggg : "+this);
        TourGroup newTrGp = new TourGroup(problemD);
        //newTrGp.remove(positionTour1);newTrGp.remove(positionTour2);
        //System.out.println("new trg"+newTrGp);
        
        Tour newTour1 = tour1.clone_dynamic();//new Tour(newTrGp);//tour without positionTour1
        //newTrGp.addTour(newTour1);
       // System.out.println("befoooooooooooooooooooore remove: ");
        newTour1.removeCustomer(c1);
        /*for (Customer c : tour1.getCustomers()) {
            if (c != c1) 
                newTour1.addCustomer(c);
            }*/
         //System.out.println("befooooooooooooooooooooooooooore addcostomerrrrrrrrrrrrrrrrr ");
        if(newTour1.addCustomerInBestPositionWithoutCheckConstraint_dynamic(c2)==false){
        //if(newTour1.addCustomerInBestPosition(c2)==false){
            return null;
        }
       //System.out.println("after add customer "+newTour1);
        //System.out.println("befoooooooooooooooooooooooooooore two opt iterative");
       // newTour1.two_opt_iterative();
       //System.out.println("after decross "+newTour1);
        if(newTour1.getTemps_dynamic()>tour1.getTempsRestant())
        { //System.out.println("temps depasse temps maximal "+newTour1);
            return null;}
        //System.out.println("newTour1 after "+newTour1);
        
        Tour newTour2 = tour2.clone_dynamic();//new Tour(newTrGp);
       // System.out.println("befoooooooooooooooooooooooooooore remooooooooooooooooooooove");
        newTour2.removeCustomer(c2);
        /*for (Customer c : tour2.getCustomers()) {
            if (c != 1c2) 
                newTour2.addCustomer(c);
        }*/
       // System.out.println("befoooooooooooooooooooooooooooore addddddddddddddd costomer 2");
        if(newTour2.addCustomerInBestPositionWithoutCheckConstraint_dynamic(c1)==false){
            return null;
        }
       // System.out.println("befoooooooooooooooooooooooooooore two opt iterative 2222222");
       // newTour2.two_opt_iterative();
       //System.out.println("after twoooooooooooooooooooooopt2");
        if(newTour2.getTemps_dynamic()>tour2.getTempsRestant())
            return null;
        //System.out.println("newTour2 after"+newTour2);
        //********Fin Construire les nouvelles tournées
        
        //boolean minObj2 = false;
        double diff ;
       
        
            for(Tour t: this.getTours())
            if(t!=tour1 && t!=tour2)
                newTrGp.addTourToCopy_dynamic(t);
        
            /*newTrGp = this.clone();
            newTrGp.addTour(this.tours.indexOf(tour1), newTour1);
            newTrGp.addTour(this.tours.indexOf(tour2), newTour2);*/
            //System.out.println("before allocate:"+newTrGp);
            newTour1.removeCamion();
            newTour2.removeCamion();
            newTrGp.addTour(newTour1);
            newTrGp.addTour(newTour2);
            newTrGp.allocateTours2_dynamic();
           // System.out.println("befooooooooooooooooooooooooore allocate_touuuuuuuuur lli ldaakheeel ");
            if(!newTrGp.CheckCamionNullConstraint())
                return null;
           /// System.out.println("befooooooooooooooooooooore minimize LTR lli ldaakheeel ");
            //TourGroup trgp = newTrGp.minimizeLTRByTours_dynamic();
            /*for (int i=0; i<newTrGp.getTours().size();i++)
                {
                    System.out.println("haa tour before clone :"+newTrGp.getTours().get(i));
                    System.out.println("haa tour size :"+newTrGp.getTours().get(i).getCustomers().size());
                    System.out.println("haa camion dyal tour :"+newTrGp.getTours().get(i).getC());
                }*/
            //System.out.println("after minimize LTR lli ldaakheeel ");
           // if(trgp!=null){
               // newTrGp = trgp.clone_dynamic();//.copyTourGroup(trgp);
              /*  for (int i=0; i<newTrGp.getTours().size();i++)
                {
                    System.out.println("haa tour after clone:"+newTrGp.getTours().get(i));
                    System.out.println("haa tour size :"+newTrGp.getTours().get(i).getCustomers().size());
                    System.out.println("haa camion dyal tour :"+newTrGp.getTours().get(i).getC());
                }*/
            //}
            if(this.getLTR_dynamic()<=this.problemD.maxTemps_dynamic){
                if (newTrGp.getLTR_dynamic()> this.problemD.maxTemps_dynamic)
                {
                    return null;
                }
                else 
                {
                  diff = this.getdistance_dynamic()-newTrGp.getdistance_dynamic();
                     if(diff>0){
                    //System.out.println("obj2:"+newTrGp);
                    return newTrGp;
                }
                     else return null;
                }
               
            }
            else{ // cost(TourGroup) = somme (Cost(tours))
                diff = this.getLTR_dynamic()-newTrGp.getLTR_dynamic();
                if(diff>0){
                    //System.out.println("obj1:"+newTrGp);
                    return newTrGp;
                }
            }
            
        //System.out.println("fin ");
        
            return null;
    }

     public TourGroup minimizeLTRByTours() {
        boolean done = false;
        
            TourGroup_Individual1 chromosome = new TourGroup_Individual1(this);
            //System.out.println("chromo "+chromosome);
            //done = chromosome.allocateTours();
            if(chromosome.minimizeLTRByTours()==true){
                TourGroup trgr = convertChrosome(chromosome);
                //System.out.println("trgr after "+this);
                return trgr;
            }
        
        return null;
    }
      public TourGroup minimizeLTRByTours_dynamic() {
        boolean done = false;
        
            TourGroup_Individual1 chromosome = new TourGroup_Individual1(this,problemD);
            //System.out.println("chromo "+chromosome);
            //done = chromosome.allocateTours();
            if(chromosome.minimizeLTRByTours_dynamic()==true){
                TourGroup trgr = convertChrosome_dynamic(chromosome);
                //System.out.println("trgr after "+this);
                return trgr;
            }
        
        return null;
    }
      public TourGroup convertChrosome(TourGroup_Individual1 chromosome) {
        TourGroup tourGroup= new TourGroup(problem);
        for(Trip trip:chromosome.getTrips()){
            if(trip.size()!=0){
                Camion c = trip.getCamion();
                for(Tour t:trip.getTours()){
                    tourGroup.addTour(t);
                    t.setCamion(c);
                }
            }
        }
        //System.out.println("after convert: "+tourGroup);
        return tourGroup;
    }
       public TourGroup convertChrosome_dynamic(TourGroup_Individual1 chromosome) {
        TourGroup tourGroup= new TourGroup(problemD);
        for(Trip trip:chromosome.getTrips()){
            if(trip.size()!=0){
                Camion c = trip.getCamion();
                for(Tour t:trip.getTours()){
                    tourGroup.addTour(t);
                    t.setCamion(c);
                }
            }
        }
        //System.out.println("after convert: "+tourGroup);
        return tourGroup;
    }
        
        //System.out.println("after convert: "+tou
    public boolean CheckCamionNullConstraint() {
        for (Tour tour : this.getTours()) {
            Camion c=tour.getC();
            if (c == null) {
                               
           //System.out.println("Camion nullllllllllllllllllllllllllllllllllllllllllll!!!");
                //System.out.println("Camion null!!!");
                return false;
            }
           /* else {
                 System.out.println("Camion kaaaaaaaaaaaaaaaaaaayen!!!");
            }*/
        }
        return true;
    }
 public void addTourToCopy(Tour et) {
        Tour t = et.clone();
        //t.tourGroup = this;
        this.addTour(t);
    }
 public void addTourToCopy_dynamic(Tour et) {
        Tour t = et.clone_dynamic();
        //t.tourGroup = this;
        this.addTour(t);
    }
         
            public double getOvertime(Camion camion) {
        double overtime = this.getTemps(camion)-problem.getMaxTemps();
        if(overtime>0)
            return overtime;
        else 
            return 0;
    }
            public boolean isOvertime() {
        for(Camion c:this.getCamions())
            if(getOvertime(c)>0)
                return true;
        return false;
    }
    public boolean allocateTours1(){
               
        boolean done = true;
        //System.out.println("before affectation:"+this);
        for(Tour tour1:this.getTours()){
            Camion c = tour1.getC();
            if(c==null){
                
           //System.out.println("temps tour avant affectation:  "+tour1.getTemps());
            //System.out.println("quantity tour avant  affectation:  "+tour1.getQuantity());
                tour1.setBestCamion();
              // System.out.println("après  affectation:  "+tour1.getC().idcamion);
              if(tour1.getC()==null)
              {
                done = false;
              }
            }
        }
        return done;       
    }
    
    public boolean allocateTours2(){
            //removeBadCamions();
            affecter_vehicule();
           //return allocateTours1();
            return true;
    }
     public void removeBadCamions(){
        //System.out.println("before affectation:"+this);
        for(Tour tour1:this.getTours()){
            Camion c = tour1.getC();
            if(c!=null && (tour1.getQuantity()>problem.getMaxCapacity()
                    || getNbTours(c)>problem.getNbTripsByVehicle())){// Pour un trip non affecté
                tour1.removeCamion();
            }
        }
    }
      public boolean allocateTours1_dynamic(){
       // System.out.println("3aaaaaaaaaaaaaaaaaaaaad bdaaaaaaaaaaaaaaaa allocaaate ");
        boolean done = true;
        int i=0;
        //System.out.println("before affectation:"+this);
        for(Tour tour1:this.getTours()){
           // System.out.println("haaaa i :"+i);
            Camion c = tour1.getC();
            if(c==null){
                
           //System.out.println("temps tour avant affectation:  "+tour1.getTemps());
            //System.out.println("quantity tour avant  affectation:  "+tour1.getQuantity());
                tour1.setBestCamion_dynamic();
              // System.out.println("après  affectation:  "+tour1.getC().idcamion);
              if(tour1.getC()==null)
              {
            //System.out.println("haa lmosiiiba fictif dyalha   "+tour1.getId_fictif());
            // System.out.println("haa lmosiiiba quantity dyalha "+tour1.getQuantity());
                done = false;
              }
            }
        }
       // System.out.println("haaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaadi mzyana   ");
            
        return done;       
    }
    
    public boolean allocateTours2_dynamic(){
           // removeBadCamions_dynamic();
           //boolean allo=allocateTours1_dynamic();
            affecter_vehicule_dynamic();
            //System.out.println("haaa résultats allocate :"+allo);
            return true;
    }
     public void removeBadCamions_dynamic(){
        //System.out.println("before affectation:"+this);
        for(Tour tour1:this.getTours()){
            Camion c = tour1.getC();
            if(c!=null && tour1.getQuantity()>tour1.getcapacity()){// Pour un trip non affecté
                tour1.removeCamion();
            }
        }
    }
    private void copyTourGroup(TourGroup tourGroupToCopy) {
        //this.tours = new ArrayList<Tour>();
        this.tours.clear();
        //this.camionsToUse.clear();
        for(Tour t:tourGroupToCopy.tours)
            addTour(t.clone());
        this.problem = tourGroupToCopy.problem;
       // this.camionsToUse = (ArrayList<Camion>) tourGroupToCopy.camionsToUse.clone();
    }
      private void copyTourGroup_dynamic(TourGroup tourGroupToCopy) {
        //this.tours = new ArrayList<Tour>();
        this.tours.clear();
        //this.camionsToUse.clear();
        for(Tour t:tourGroupToCopy.tours)
            addTour(t.clone_dynamic());
        this.problemD = tourGroupToCopy.problemD;
       // this.camionsToUse = (ArrayList<Camion>) tourGroupToCopy.camionsToUse.clone();
    }
    public boolean CheckCamionforTour(Tour tour) {
        Camion oldCamion = tour.removeCamion();
        Camion newCamion = CheckCamionforTimeAndQuantity(tour.getTemps(), tour.getQuantity());
        tour.setCamion(oldCamion);
        if(newCamion!=null)
            return true;
        return false;
    }
    public boolean CheckCamionforTour_dynamic(Tour tour) {
        Camion oldCamion = tour.removeCamion();
        Camion newCamion = CheckCamionforTimeAndQuantity_dynamic(tour.getTemps_dynamic(), tour.getQuantity());
        tour.setCamion(oldCamion);
        if(newCamion!=null)
            return true;
        return false;
    }
    
      public Camion CheckCamionforTimeAndQuantity(double time, double quantity) {
        if(quantity>problem.getMaxCapacity())
            return null;
        Camion camion = null;
        
            for (Camion c : this.getCamions()) {
                if(time+this.getTemps(c)>problem.getMaxTemps()+problem.getOvertime()){
                    continue;
                }
                
                    int nbTours = getNbTours(c);
                    if(nbTours>=problem.getNbTripsByVehicle()){
                        continue;}
                
                return c;
            }
        
        
        return camion;
    }
        public Camion CheckCamionforTimeAndQuantity_dynamic(double time, double quantity) {
        if(quantity>problemD.getCapacitycamion_dynamic())
            return null;
        Camion camion = null;
        
            for (Camion c : this.getCamions()) {
                if (c.getId_fictif_final()!=0)
                {
              Depotfictif d= this.problemD.getdepotfictifById_fictif_final(c.getId_fictif_final());
                if((time+this.getTemps_dynamic(c)>(problemD.getMaxTemps_dynamic()+this.problemD.getOvetime_dynamic()-c.getSum_temps_tour()))||(quantity>d.capacité_restante)){
                     continue;
                    
                }
                else {return c;}
                }
                else 
                {
                    if((time+this.getTemps_dynamic(c)>(problemD.getMaxTemps_dynamic()+this.problemD.getOvetime_dynamic()))||(quantity>this.problemD.capacitycamion_dynamic)){
                     continue;
                    
                }
                else {return c;}
                }
                    //int nbTours = getNbTours(c);
                   // if(nbTours>=problem.getNbTripsByVehicle()){
                       // continue;}
                
                
            }
        
        
        return camion;
    }
      public boolean CheckCamionForTimeAndQuantity(double time, double quantity) {
        if(CheckCamionforTimeAndQuantity(time, quantity)!=null)
            return true;
        return false;
    }
       public boolean CheckCamionForTimeAndQuantity_dynamic(double time, double quantity) {
        if(CheckCamionforTimeAndQuantity_dynamic(time, quantity)!=null)
            return true;
        return false;
    }
       public void AmeliorateBySaving() {
        boolean improved = true;
   // ClarkAndWrightDistanceVrpSolver cw = new ClarkAndWrightDistanceVrpSolver(problem);
        while (improved) {
           // if(problem.getFleet().equals("HOMOGENE"))
                improved = optimizeTour(this);
            //else if(problem.getFleet().equals("HETEROGENE"))
              //  improved = cw.optimizeTour_HETEROGENE(this);
        }
    }
       public void AmeliorateBySaving_dynamic() {
        boolean improved = true;
       // ClarkAndWrightDistanceVrpSolver cw = new ClarkAndWrightDistanceVrpSolver(problem);
        while (improved) {
           // if(problem.getFleet().equals("HOMOGENE"))
                improved = optimizeTour_dynamic(this);
            //else if(problem.getFleet().equals("HETEROGENE"))
              //  improved = cw.optimizeTour_HETEROGENE(this);
        }
    }
    
  public boolean optimizeTour(TourGroup tourGroup) {
		//System.out.println(tourGroup);
                if(tourGroup.getTours().isEmpty())
                    return false;
                Tour firstTour ;
                Tour secondTour ;
                double bestSaving = 0;
                Saving best=null;
                //System.out.println("tours: "+tours);
                for (int i = 0; i < tourGroup.getTours().size(); i++) {
                    for (int j = i+1; j < tourGroup.getTours().size(); j++) {
                        firstTour = tourGroup.getTours().get(i);
                        secondTour = tourGroup.getTours().get(j);
                        //System.out.println("first: "+firstTour);
                        //System.out.println("second: "+secondTour);
                        //&& problem.CheckCapacity_TimeConstraint(firstTour, secondTour)){
                        Saving s = new Saving(tourGroup, firstTour, secondTour);
                        double gain = s.getSaving();
                        if (gain > bestSaving) {
                                bestSaving = gain;
                                best = s;
                         }
                    }
                }
                 //System.out.println("best "+bestSaving);
                 if(best == null || bestSaving<0)   {
                     return false;
                 }
                 //System.out.println("saving "+best);
                //System.out.println("haaa le nombre de camion 9bel link tours:  "+tourGroup.getCamionsToUse().size());
                linkTours(best, tourGroup);
                //System.out.println("haaa le nombre de camion après link tours:  "+tourGroup.getCamionsToUse().size());
                
                return true;

	}
  public boolean optimizeTour_dynamic(TourGroup tourGroup) {
		//System.out.println(tourGroup);
                if(tourGroup.getTours().isEmpty())
                    return false;
                Tour firstTour ;
                Tour secondTour ;
                double bestSaving = 0;
                Saving best=null;
                //System.out.println("tours: "+tours);
                for (int i = 0; i < tourGroup.getTours().size(); i++) {
                    for (int j = i+1; j < tourGroup.getTours().size(); j++) {
                        firstTour = tourGroup.getTours().get(i);
                        secondTour = tourGroup.getTours().get(j);
                        //System.out.println("first: "+firstTour);
                        //System.out.println("second: "+secondTour);
                        //&& problem.CheckCapacity_TimeConstraint(firstTour, secondTour)){
                        Saving s = new Saving(tourGroup, firstTour, secondTour,problemD);
                        double gain = s.getSaving_dynamic();
                        if (gain > bestSaving) {
                                bestSaving = gain;
                               /* System.out.println("haa merge type:"+s.mergeType);
                                System.out.println("haa id fictif firsttour :"+s.firstTour.id_fictif);
                                System.out.println("haa id fictif secondtour :"+s.secondTour.id_fictif);*/
                                best = s;
                         }
                    }
                }
                 //System.out.println("best "+bestSaving);
                 if(best == null || bestSaving<0)   {
                     return false;
                 }
                 //System.out.println("saving "+best);
                //System.out.println("haaa le nombre de camion 9bel link tours:  "+tourGroup.getCamionsToUse().size());
                linkTours_dynamic(best, tourGroup);
                //System.out.println("haaa le nombre de camion après link tours:  "+tourGroup.getCamionsToUse().size());
                
                return true;

	}
    private TourGroup createInitialTourGroup(VRPS vrpProblem) {
            TourGroup trGroup = new TourGroup(vrpProblem);
            for ( Customer customer: vrpProblem.getCustomers() ) {
                    if ( trGroup.CheckCustomerInTourGroup(customer) ) {
                            continue;
                    }
                    //Camion camion = null;
                    Tour tour = new Tour(trGroup);
                    tour.addCustomer(customer);
                    trGroup.addTour(tour);

            }
            return trGroup;
	}
    public boolean linkTours(Saving saving, TourGroup tourGroup){
		//System.out.println("before ");
		if(saving==null || saving.getSaving()<0) return false;
                Tour firstTour= saving.getFirstTour();
		Tour secondTour = saving.getSecondTour();
		//System.out.println("first: "+firstTour);
                //System.out.println("second: "+secondTour);
                //System.out.println("camions to use: ");
                //for(Camion c:tourGroup.getCamionsToUse())
                //    System.out.print(c+",");
                switch (saving.getMergeType()) {
                    case HEAD_TO_HEAD:
                         //System.out.println("le cas head to head avant:  "+tourGroup.getProblem().getCamions().size());
                            Collections.reverse(firstTour.getCustomers());
                          //  System.out.println("le cas head to head après:  "+tourGroup.getProblem().getCamions().size());
                            break;

                    case HEAD_TO_TAIL:
                        //System.out.println("le cas head to tail avant:  "+tourGroup.getProblem().getCamions().size());
                            Collections.reverse(firstTour.getCustomers());
                            Collections.reverse(secondTour.getCustomers());
                           //System.out.println("le cas head to tail après:  "+tourGroup.getProblem().getCamions().size());
                            break;
                    case TAIL_TO_TAIL:
                       //System.out.println("le cas tail to tail:  "+tourGroup.getProblem().getCamions().size());
                            Collections.reverse(secondTour.getCustomers());
                       //System.out.println("le cas tail to tail après:  "+tourGroup.getProblem().getCamions().size());
                            break;

                    case TAIL_TO_HEAD:
                            break; // tout est dans le bon ordre
                    }
                 //System.out.println("haa linktours avant dak tkharbii9 kaamel:    kjgjh:   "+ this.problem.getCamions().size());
                Tour childTour = new Tour(tourGroup);
                //System.out.println("haa linktours après new tour:    kjgjh:   "+ this.problem.getCamions().size());
                childTour.addAll(firstTour.getCustomers());
                //System.out.println("haa linktours après firsttour.addall:    kjgjh:   "+ this.problem.getCamions().size());
                childTour.addAll(secondTour.getCustomers());
                //System.out.println("haa linktours après secondtour.addall    kjgjh:   "+ this.problem.getCamions().size());
                tourGroup.remove(firstTour);
                //System.out.println("haa linktours après removefirst:    kjgjh:   "+ this.problem.getCamions().size());
                tourGroup.remove(secondTour);
                //System.out.println("haa linktours apeès remove secondtour:    kjgjh:   "+ this.problem.getCamions().size());
                /*************Amélioration 2-opt************/
                 //System.out.println("haa linktours 9bel improve:    kjgjh:   "+ this.problem.getCamions().size());
                childTour.improve1();
               // System.out.println("haa linktours apeès improve:    kjgjh:   "+ this.problem.getCamions().size());
               
                //childTour.setBestCamion();
                //System.out.println("haa linktours apeès setbestcamion:    kjgjh:   "+ this.problem.getCamions().size());
                tourGroup.addTour(0, childTour);
               // System.out.println("haa linktours apeès tourgroupe.addtou  00.lkh:   "+ this.problem.getCamions().size());
                return true;
	}
       public boolean linkTours_dynamic(Saving saving, TourGroup tourGroup){
		//System.out.println("before ");
		if(saving==null || saving.getSaving_dynamic()<0) return false;
                Tour firstTour= saving.getFirstTour();
		Tour secondTour = saving.getSecondTour();
		//System.out.println("first: "+firstTour);
                //System.out.println("second: "+secondTour);
                //System.out.println("camions to use: ");
                //for(Camion c:tourGroup.getCamionsToUse())
                //    System.out.print(c+",");
                switch (saving.getMergeType()) {
                    case HEAD_TO_HEAD:
                         //System.out.println("le cas head to head avant:  "+tourGroup.getProblem().getCamions().size());
                        if (firstTour.getCustomers().size()>0)
                        
                            Collections.reverse(firstTour.getCustomers());
                          //  System.out.println("le cas head to head après:  "+tourGroup.getProblem().getCamions().size());
                            break;

                    case HEAD_TO_TAIL:
                        //System.out.println("le cas head to tail avant:  "+tourGroup.getProblem().getCamions().size());
                        if (firstTour.getCustomers().size()>0)
                            Collections.reverse(firstTour.getCustomers());
                            Collections.reverse(secondTour.getCustomers());
                           //System.out.println("le cas head to tail après:  "+tourGroup.getProblem().getCamions().size());
                            break;
                    case TAIL_TO_TAIL:
                       //System.out.println("le cas tail to tail:  "+tourGroup.getProblem().getCamions().size());
                            Collections.reverse(secondTour.getCustomers());
                       //System.out.println("le cas tail to tail après:  "+tourGroup.getProblem().getCamions().size());
                            break;

                    case TAIL_TO_HEAD:
                            break; // tout est dans le bon ordre
                    }
                 //System.out.println("haa linktours avant dak tkharbii9 kaamel:    kjgjh:   "+ this.problem.getCamions().size());
                Tour childTour = new Tour(tourGroup);
                childTour.setId_fictif(firstTour.getId_fictif());
                //System.out.println("haa first tour size 9bel chwiya   "+ firstTour.size());
                childTour.addAll(firstTour.getCustomers());
                //System.out.println("haa linktours après firsttour.addall:    kjgjh:   "+ this.problem.getCamions().size());
                childTour.addAll(secondTour.getCustomers());
                //System.out.println("haa linktours après secondtour.addall    kjgjh:   "+ this.problem.getCamions().size());
                tourGroup.remove(firstTour);
                //System.out.println("haa linktours après removefirst:    kjgjh:   "+ this.problem.getCamions().size());
                tourGroup.remove(secondTour);
                //System.out.println("haa linktours apeès remove secondtour:    kjgjh:   "+ this.problem.getCamions().size());
                /*************Amélioration 2-opt************/
                 //System.out.println("haa linktours 9bel improve:    kjgjh:   "+ this.problem.getCamions().size());
                childTour.improve1_dynamic();
               // System.out.println("haa linktours apeès improve:    kjgjh:   "+ this.problem.getCamions().size());
               
                //childTour.setBestCamion();
                //System.out.println("haa linktours apeès setbestcamion:    kjgjh:   "+ this.problem.getCamions().size());
                tourGroup.addTour(0, childTour);
              /*System.out.println("haaa temps dyal had tour   "+ childTour.getTemps_dynamic());
              System.out.println("haaa merge type   "+ saving.mergeType);
              System.out.println("haaa first tour id fictif   "+ firstTour.getId_fictif());
              System.out.println("haaa first size   "+ firstTour.size());*/
                return true;
	}

      /*  public boolean linkTours(Tour firstTour, Tour secondTour, TourGroup tourGroup){
		Saving saving = new Saving(tourGroup, firstTour, secondTour);
		saving.getSaving();
		// produit de la fusion de firstTour et secondTour

                double quantity = firstTour.getQuantity()+secondTour.getQuantity();
		//Camion camion1 = problem.BestFreeCamionforTours(firstTour,secondTour);
                Camion camion = BestCamionforTours(firstTour,secondTour);
                if(camion != null ){
                        firstTour.removeCamion();
                        secondTour.removeCamion();

		/*	switch (saving.getMergeType()) {
			case HEAD_TO_HEAD:
				Collections.reverse(firstTour.getCustomers());
				break;

                        case HEAD_TO_TAIL:
				Collections.reverse(firstTour.getCustomers());
				Collections.reverse(secondTour.getCustomers());
				break;
			case TAIL_TO_TAIL:
				Collections.reverse(secondTour.getCustomers());
				break;

                         case TAIL_TO_HEAD:
				break; // tout est dans le bon ordre
			}
			*/
                       /* Tour childTour = new Tour(tourGroup, this.problem.MaxCapacity);
			childTour.addAll(firstTour.getCustomers());
			childTour.addAll(secondTour.getCustomers());/*

			/*if(camion == camion2)
                            tour.setCamion(problem.BestCamionforTour(tour));
*/

			/*tourGroup.remove(firstTour);
			tourGroup.remove(secondTour);
			childTour.setCamion(camion);
                        tourGroup.addTour(0, childTour);
                        return true;
                }

		//tour.getElementaryTours().

		//double newCost = problem.getCost(tourGroup);
		//System.out.println("Optimized TourGroup " + tourGroup + "'s cost: from " + oldCost + " to " + newCost);
		//System.out.println("Optimized TourGroup :" + firstTour + " + " + secondTour + " ==> " + childTour);
		//System.out.println("Optimized TourGroup :" + tourGroup);
            	return false;

	}*/
     

     
  
      public boolean checkCamionInTourGroup(Camion camion) {
        for (Tour t : this.getTours()) {
            if (t.getC() == camion) {
                //System.out.println(camion+" is used for "+t);
                return true;
            }
        }
        return false;
    }
         public Camion BestCamionforTimeAndQuantity(double time, double quantity) {
           
        if(quantity>problem.getMaxCapacity())
        {
           // System.out.println("haaaaaaaaaaaaaaaaaaaaaaaaaaaaaa quantity: "+quantity);
            return null;
        }
        if( time>problem.getMaxTemps()+problem.getOvertime())
        {//System.out.println("haaaaaaaaaaaaaaaaaaaaaaaaaaaaaa tiiiiiiiiime: "+time);
            return null;}
        double min = 100000000;
        double tmpTime ;
        double bestTime;
        Camion bestCamion = null;
        
        
            double lowerTime  = 9999999;
            for (Camion c : this.getCamions()) {
                
                    int nbTours = getNbTours(c);
                    if(nbTours>=problem.getNbTripsByVehicle())
                    { 
                        continue;}
                    else
                    {
                  //System.out.println("slaaaaaaaaaaaaaaaaaaaaaaaaaaaaa wslaaaaaaaaaaaaaaaaaaaaaaaaaaaaam 3la rasoul ALLAH : "+time);
                if(time+this.getTemps(c)<lowerTime){
                    lowerTime = time;
                    bestCamion = c;
                   // System.out.println("haaaaaaaaaaaaaaaaaaaaaaaaaaaaaa camion: "+c.getIdcamion());
                }
                    }
            }
        
        
           
        return bestCamion;
    }
        public Camion BestCamionforTour(Tour tour) {
            
            if (tour.getC()!=null)
            {
                
        Camion oldCamion = tour.removeCamion();
            }
            
        Camion newCamion = BestCamionforTimeAndQuantity(tour.getTemps(), tour.getQuantity());
        tour.setC(newCamion);
        return newCamion;
    }
           public Camion BestCamionforTimeAndQuantity_dynamic(double time, double quantity) {
           
        if(quantity>problemD.getCapacitycamion_dynamic())
        {
            //System.out.println("haaaaaaaaaaaaaaaaaaaaaaaaaaaaaa quantity: "+quantity);
            return null;
        }
        if( time>problemD.getMaxTemps_dynamic()+problemD.getOvetime_dynamic())
        {//System.out.println("haaaaaaaaaaaaaaaaaaaaaaaaaaaaaa tiiiiiiiiime: "+time);
            return null;}
        double min = 100000000;
        double tmpTime ;
        double bestTime;
        Camion bestCamion = null;
        
        
            double lowerTime  = 9999999;
            for (Camion c : this.getCamions()) {
                
                    //int nbTours = getNbTours(c);
                   
                  //System.out.println("slaaaaaaaaaaaaaaaaaaaaaaaaaaaaa wslaaaaaaaaaaaaaaaaaaaaaaaaaaaaam 3la rasoul ALLAH : "+time);
                if(time+this.getTemps_dynamic(c)<lowerTime){
                    lowerTime = time;
                    bestCamion = c;
                   //System.out.println("haaaaaaaaaaaaaaaaaaaaaaaaaaaaaa camion: "+c.getIdcamion());
                }
                    
            }
        
        
           
        return bestCamion;
    }
        public Camion BestCamionforTour_dynamic(Tour tour) {
            
            if (tour.getC()!=null)
            {
                //System.out.println("la cas camion 3andha 3ad suppriminah");
        Camion oldCamion = tour.removeCamion();
            }
           /* System.out.println("daba ghadi n9albo 3la dak new camion");
            System.out.println("ha temps dyal tour: "+tour.getTemps_dynamic());
            System.out.println("ha quantité dyal tour: "+tour.getQuantity());
            System.out.println("ha maxtemps problème: "+this.problemD.getMaxTemps_dynamic());*/
        Camion newCamion = BestCamionforTimeAndQuantity_dynamic(tour.getTemps_dynamic(), tour.getQuantity());
        //System.out.println("ha camion: "+newCamion);
        tour.setC(newCamion);
        return newCamion;
    }
           public Camion BestCamionforTours(Tour firstTour, Tour secondTour) {
        double totalTime = problem.getTemps(firstTour, secondTour);
        double totalQuantity = firstTour.getQuantity() + secondTour.getQuantity();
        Camion firstCamion = firstTour.getC();
        Camion secondCamion = secondTour.getC();
        firstTour.removeCamion();
        secondTour.removeCamion();

        Camion bestCamion = BestCamionforTimeAndQuantity(totalTime, totalQuantity);

        firstTour.setCamion(firstCamion);
        secondTour.setCamion(secondCamion);

        return bestCamion;
    }

     public void classer_camion(ArrayList<Camion> camions_a_trier){
          boolean permut;
          Camion tampon1=null;
          Camion tampon2= null;
          do
          {
              permut= false;
              for (int i=0; i<camions_a_trier.size()-1;i++)
              {
                if (camions_a_trier.get(i).calucler_temps_total_tournees()>camions_a_trier.get(i+1).calucler_temps_total_tournees())
                {
                    tampon1=camions_a_trier.get(i);
                    tampon2=camions_a_trier.get(i+1);
                    camions_a_trier.set(i, tampon2);
                    camions_a_trier.set(i+1, tampon1);
                    permut=true;
                }   
              }
          }while (permut);
      }
     public void classer_tournees(ArrayList<Tour> Tours){
         
         Tour tampon1=null;
         Tour tampon2=null;
        
            
             for (int i=1; i<Tours.size();i++)
             {
                 if (Tours.get(i).getId_fictif()!=0)
                 {
                     tampon1=Tours.get(i);
                      tampon2=Tours.get(0);
                     Tours.set(i,tampon2);
                     Tours.set(0, tampon1);
                    
                 }
                 
             }
        
         
         
     }
       public int getNbTours(Camion camion) {
        if(camion == null)
            return -1;
        int c =0;
        if (this.getTours() != null) {
            for (Tour tour : this.getTours()) {
                if (tour.getC() == camion) {
                    c++;
                }
            }
        }
        return c;
    }
         public double getTemps(Camion camion) {
        if(camion==null)
            return 0;
        double time = 0;
        if (this.getTours() != null) {
            for (Tour tour : this.getTours()) {
                if (tour.getC() == camion) {
                    time += tour.getTemps();
                }
            }
        }
        return time;
    }
         public double getTemps_dynamic(Camion camion) {
        if(camion==null)
            return 0;
        double time = 0;
        if (this.getTours() != null) {
            time=camion.getSum_temps_tour();
            for (Tour tour : this.getTours()) {
               // if (tour.getC() == null)
           //System.out.println("waaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaakjgluhfqsdluyyyyyyyyyggdghd hada nullllllllllllllllllllllllll");
                if (tour.getC() == camion) {
                    time =time+tour.getTemps_dynamic();
                }
            }
        }
        return time;
    }
   
     
    /*  public void updateCamionsToUse_add(Camion camion) {
        if(camion==null)
            return;
        int a = getNbTours(camion);
        if(a>0){
            if(a==problem.getNbTripsByVehicle()){//>10 multi trip
                camionsToUse.remove(camion);
            }
            /*for (Camion c : camionsToUse)// on cherche s'il y a deja un camion free de cette capcaité dans camionsToUse
            {
                if (c.getType() == camion.getType()){
                    if(checkCamionInTourGroup(c)==false)
                        return;
                }
            }*/
            //sinon on l'ajouter
           /* Camion c = FreeCamion();
            if (c != null) {
                camionsToUse.add(c);
            }*/
       /* }
    }*/
     public double getOvertime(double time) {
        double overtime = time-problem.getMaxTemps();
        if(overtime>0)
            return overtime;
         else 
            return 0;
    }
       public Camion FreeCamion() {
        for (Camion c :getCamions()) {
            if (this.checkCamionInTourGroup(c) == false) {
                return c;
            }
        }
        return null;
    }
       public void removeAllCamionsFromTourGroup() {
        for (Tour t : this.getTours()) {
            t.removeCamion();
        }
    }
        public ArrayList<TourGroup> crossOver_LTR(TourGroup father, TourGroup mother) {
           TourGroup child1 = Substitute_Longest_Tours(father, mother);
           TourGroup child2 = Substitute_Longest_Tours(mother, father);
           
                child1.removeAllCamionsFromTourGroup();
                //child1.improve_light();
                child2.removeAllCamionsFromTourGroup();
                //child2.improve_light();
          
           //child1.two_opt(20);
           //child2.two_opt(20); 
           ArrayList<TourGroup> list = new ArrayList<TourGroup>();
                list.add(child1);
                list.add(child2);
                             //System.out.println("crossover child1: "+list.get(1));
            return list;
    }
         public ArrayList<TourGroup> crossOverTours_twoPositions(TourGroup father, TourGroup mother) {
        //Echange entre deux client des deux parents
        TourGroup child1 = new TourGroup(problem);
        TourGroup child2 = new TourGroup(problem);
            /* if((father==null)||(mother==null))
           {
            System.out.println("haaaaaaaaaaaaaaaaaaaaaadchiiiiiiiiiiiiiiiiiiiiii null");   
           }*/
            int positionTourFather = rnd.nextInt(father.getTours().size());
            int positionTourMother = rnd.nextInt(mother.getTours().size());
            for (int i =0; i<father.getTours().size();i++) {
                if(i!=positionTourFather){
                    child1.addTourToCopy(i,father.getTours().get(i));// new Tour(father.get(i),child1));//copy tour
                }
                else
                    child1.addTourToCopy(i,mother.getTours().get(positionTourMother));//new Tour(mother.get(positionTourMother),child1));
            }
            for (int i =0; i<mother.getTours().size();i++) {
                if(i!=positionTourMother)
                    child2.addTour(i, mother.getTours().get(i));//new Tour(mother.get(i),child2));//copy tour
                else
                    child2.addTour(i, father.getTours().get(positionTourFather));//new Tour(father.get(positionTourFather),child2));
            }
            ArrayList<TourGroup> list = new ArrayList<TourGroup>();
            if(child1.complete_TourGroup())
                list.add(child1);
            if(child2.complete_TourGroup())
                list.add(child2);
            return list;
    }
         public ArrayList<TourGroup> crossOver_LTR_dynamic(TourGroup father, TourGroup mother) {
           TourGroup child1 = Substitute_Longest_Tours_dynamic(father, mother);
           TourGroup child2 = Substitute_Longest_Tours_dynamic(mother, father);
          // TourGroup child1 = father;
           //TourGroup child2 = mother;
           
                child1.removeAllCamionsFromTourGroup();
                //child1.improve_light();
                child2.removeAllCamionsFromTourGroup();
                //child2.improve_light();
          
           //child1.two_opt(20);
           //child2.two_opt(20); 
           ArrayList<TourGroup> list = new ArrayList<TourGroup>();
                list.add(child1);
                list.add(child2);
                             //System.out.println("crossover child1: "+list.get(1));
            return list;
    }
          public int calculer_trg(TourGroup trg){
       int k=0;
       for (int i=0;i<trg.getTours().size();i++)
    {
        
       k=k+trg.getTours().get(i).getCustomers().size();
        System.out.println("tour id fictif id:"+trg.getTours().get(i).getId_fictif());
        for (int j=0;j<trg.getTours().get(i).getCustomers().size();j++)
    {
        
        System.out.println("client id:"+trg.getTours().get(i).getCustomers().get(j).getId()); 
        
    }
        
    }
         System.out.println("taille tougroupe f subtitute:"+k); 
         return k;
   }
         private TourGroup Substitute_Longest_Tours_dynamic(TourGroup father, TourGroup mother) {
           /*Container myContainer = new Container();
            myContainer.setLayout(new GridLayout(2,2));
            myContainer.add(father.drawTourGroup2());
            myContainer.add(mother.drawTourGroup2());
           */
           //System.out.println("before clonage");
            // calculer_trg(father);
             //calculer_trg(mother);
            TourGroup child1 = father.clone_dynamic();
            TourGroup child2 = mother.clone_dynamic();
             //System.out.println("after clonage");
            // calculer_trg(child1);
            // calculer_trg(child2);
           //System.out.println("child1:"+child1);
           //System.out.println("child2:"+child2);
           ArrayList<Tour> longestTrip;
         //  if(problem.getObjective1().equals("LTR"))
         
               longestTrip = child1.longestTrip_dynamic();
        //System.out.println("after longestTrip");
            // calculer_trg(child1);
//child1.longestTours(2);//
          // else {
             /*  if(child1.getTours().size()>2)
                    longestTrip = child1.longestTours((int)child1.getTours().size()/3);
               else
                   longestTrip = child1.longestTours(1);//
           }*/
           ArrayList<Customer> customers = new ArrayList<Customer>();
           for(Tour t:longestTrip)
               customers.addAll(t.getCustomers());
           //System.out.println("after addall");
             /*for (Customer c : customers)
             {
                System.out.println("hado les clients dyal la liste cusomers: "+c.getId());  
             }*/
           //System.out.println("customers "+new Tour(child1).CustomerstoString(customers));
           //laisser que les clients de longest dans child2
           for(int i=0;i<child2.getTours().size();i++){
                Tour t=child2.getTours().get(i);
                for(int k=0;k<t.size();k++){
                    Customer c=t.getCustomers().get(k);
                    int d=0;
                    for (Customer cs: customers)
                    {
                        if (c.getId()==cs.getId())
                            d=1;
                    }
                           
                    // if(!customers.contains(c)){
                    if(d==0){
                         child2.removeCustomer_dynamic(c);//hfhtfuktf
                       //System.out.println("hado les clients qu'on supprime de child2 : "+c.getId());  
                         k--;
                         if((t.size()==0)&&(t.id_fictif==0))
                             i--;
                     }
                }
           }
          /* for(Tour t:child2.getTours() ){
                //Tour t=child2.getTours().get(i);
                for(Customer c: t.getCustomers() ){
                    //Customer c=t.getCustomers().get(k);
                    int d=0;
                    for (Customer cs: customers)
                    {
                        if (c.getId()==cs.getId())
                            d=1;
                    }
                           
                    // if(!customers.contains(c)){
                    if(d==0){
                         child2.removeCustomer_dynamic(c);//hfhtfuktf
                       System.out.println("hado les clients qu'on supprime de child2 : "+c.getId());  
                        // k--;
                        // if((t.size()==0)&&(t.id_fictif==0))
                          //   i--;
                     }
                }
           }*/
          // System.out.println("after boucle for hada child 2");
            // calculer_trg(child2);
              //System.out.println("after boucle for hada child 1");
             //calculer_trg(child1);
             
           child1.removeCustomers_dynamic(customers);
          // System.out.println("after remove customers");
            // calculer_trg(child1);
             for (Tour t: child2.getTours())
             {
                 t.setId_fictif(0);
             }
           child1.addAll(child2.getTours());
           //System.out.println("after addall tours");
             //calculer_trg(child1);
           //System.out.println("child1 after:"+child1);
           //System.out.println("child2 after:"+child2);
           /*child1.improveTours();
           TourGroup trgp = child1.minimizeLTRByTours();
            if(trgp!=null)
                child1.copyTourGroup(trgp);
           */
           /*myContainer.add(child1.drawTourGroup2());
           JOptionPane.showMessageDialog(null, myContainer);*/
                //list.add(child2);
            //System.out.println("crossover child1: "+list.get(1));

            return child1;
    }
         public ArrayList<TourGroup> crossOverTours_twoPositions_dynamic(TourGroup father, TourGroup mother) {
        //Echange entre deua        cmx client des deux parents
        TourGroup child1 = new TourGroup(problemD);
        TourGroup child2 = new TourGroup(problemD);
        System.out.println("before all father");
            // verify_population(this.problemD.getCustomers_dynamic(), father);
             //System.out.println("before all mother");
             //verify_population(this.problemD.getCustomers_dynamic(), mother);
            /* classer_tournees(father.getTours());
             classer_tournees(mother.getTours());
             System.out.println("taille  customer première tournée father : "+father.getTours().get(0).size()+" id fictif tour: "+father.getTours().get(0).getId_fictif());
            System.out.println("taille  customer deue tournée mother: "+father.getTours().get(0).size());
             test_capacite_camion(this.getCamions().get(0), father);
             test_capacite_camion(this.getCamions().get(0), mother);*/
         
            

            int positionTourFather = rnd.nextInt(father.getTours().size());
            int positionTourMother = rnd.nextInt(mother.getTours().size());
            for (int i =0; i<father.getTours().size();i++) {
                if(i!=positionTourFather){
                    child1.addTourToCopy_dynamic(i,father.getTours().get(i));// new Tour(father.get(i),child1));//copy tour
                }
                else
                {child1.addTourToCopy_dynamic(i,mother.getTours().get(positionTourMother));//new Tour(mother.get(positionTourMother),child1));
                child1.getTours().get(i).setId_fictif(father.getTours().get(i).getId_fictif());
                }
                
                }
            for (int i =0; i<mother.getTours().size();i++) {
                if(i!=positionTourMother)
                    child2.addTour(i, mother.getTours().get(i));//new Tour(mother.get(i),child2));//copy tour
                else
                
               {child2.addTour(i, father.getTours().get(positionTourFather));//new Tour(father.get(positionTourFather),child2));
                child2.getTours().get(i).setId_fictif(mother.getTours().get(i).getId_fictif());
               }
               }
            ArrayList<TourGroup> list = new ArrayList<TourGroup>();
            // System.out.println("before complete1");
            // verify_population(this.problemD.getCustomers_dynamic(), child1);
             //System.out.println("before complete2");
             //verify_population(this.problemD.getCustomers_dynamic(), child2);
             // classer_tournees(father.getTours());
             //classer_tournees(mother.getTours());
             //System.out.println("taille  customer première tournée child1 : "+child1.getTours().get(0).size()+" id fictif tour: "+child1.getTours().get(0).getId_fictif());
           // System.out.println("taille  customer deue tournée child2: "+child2.getTours().get(0).size()+"id fictif tour: "+child2.getTours().get(0).getId_fictif());
             //test_capacite_camion(this.getCamions().get(0), child1);
             //test_capacite_camion(this.getCamions().get(0), child2);
         
            if(child1.complete_TourGroup_dynamic())
                 //System.out.println("after complete  1");
            // System.out.println("taille  customer première tournée child1 : "+child1.getTours().get(0).size()+" id fictif tour: "+child1.getTours().get(0).getId_fictif());
            //System.out.println("taille  customer deue tournée child2: "+child2.getTours().get(0).size());
            // test_capacite_camion(this.getCamions().get(0), child1);
             //test_capacite_camion(this.getCamions().get(0), child2);
         
             //verify_population(this.problemD.getCustomers_dynamic(), child1);
                list.add(child1);
            if(child2.complete_TourGroup_dynamic())
                
               // System.out.println("after complete  2");
             //System.out.println("taille  customer première tournée child1 : "+child1.getTours().get(0).size()+" id fictif tour: "+father.getTours().get(0).getId_fictif());
            //System.out.println("taille  customer du tournée child2: "+child2.getTours().get(0).size());
            // test_capacite_camion(this.getCamions().get(0), child1);
            // test_capacite_camion(this.getCamions().get(0), child2);
         
             //verify_population(this.problemD.getCustomers_dynamic(), child2);
                list.add(child2);
                // System.out.println("fin crossover");
            return list;
    }
     public void verify_population(ArrayList<Customer> crs, TourGroup trg){
      int size_crs=crs.size();
      int size_t=0;
      for (Customer c: crs)
      {
          int k=0; 
          for (int j=0;j<trg.getTours().size();j++)
          {
            for (int l=0;l<trg.getTours().get(j).getCustomers().size(); l++) 
            {
                size_t=size_t+1;
              if (c.getId()==trg.getTours().get(j).getCustomers().get(l).getId())
              {k=k+1;}
            }
          }
          if (k!=1)
          {
              System.out.println("haaaaaaaaaaaaaaaaaaaaaa 7riiiraaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa :"+c.getId());    
          }
          int id_fic=0;
           for (int j=0;j<trg.getTours().size();j++)
          {
              if (trg.getTours().get(j).getId_fictif()!=0)
              {
                  id_fic=id_fic+1;
              }
          }
          if (id_fic!=trg.getProblemD().getDepots_fictif().size())
           System.out.println("nommmmmmmmmmmmmmmmmmmmmmmmmmmmmmmbrrrrrrrrrrrrrrrrre id fictif non null: "+ id_fic);  
      
          //if (size_t!=size_crs)
          //{
             // System.out.println("haaaaaaaaaaaaaaaaaaaaaa 7riiiraaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa ");    
          //}
          //size_t=0;
      }
      
      
  }   
         public int position_c(Customer c, ArrayList<Customer> List){
             int position_c=List.size();
                
                for (int j=0;j<List.size();j++)
                { if (c.getId()==List.get(j).getId())
                position_c=j;
                        }
               
             return position_c;
             
         }
            public boolean complete_TourGroup() {
        this.removeAllCamionsFromTourGroup();
       // this.initialiseCamionsToUse1();
        //Supprimer les doublons
        boolean[] visited = new boolean[problem.getCustomers().size()];
        for (int i = 0; i < visited.length; i++) {
            visited[i] = false;
        }
       
        for (Tour t : this.getTours()) {
            int i = 0;
            while (i < t.size()) {
                
                Customer c = t.getCustomers().get(i);
                int position_c=position_c(c,problem.getCustomers());
                if (visited[position_c] == true) {
                    t.removeCustomer(c);
                    
                } else {
                    visited[position_c] = true;
                    
                    i++;
                }
            }
        }

        splitTours();
        System.out.println("after split"+this);
       
        for (int i = 1; i <= problem.getCustomers().size(); i++) {
            Customer customer = problem.getCustomerById(i);
            
            //if (!visited[i - 1]) {
            if (!visited[position_c(customer, problem.getCustomers())]) {
               
                Saving savingforCustomer = this.BestSavingforCustomer(customer);
                if (savingforCustomer != null) {
                    
                    Tour tourforCustomer = savingforCustomer.getFirstTour();
                    if (savingforCustomer.getMergeType() == MergeType.TAIL_TO_HEAD) {
                        tourforCustomer.addCustomer(customer);
                  
                    } else if (savingforCustomer.getMergeType() == MergeType.HEAD_TO_TAIL) {
                        tourforCustomer.addCustomer(0, customer);
                      
                    }
                } else {
                  
                    Tour tr = new Tour(this);
                    tr.addCustomer(customer);
                   
                    //if (CheckCamionforTour(tr) == true) {
                        
                        this.addTour(tr);
                   // }
                }

            }

        }
        allocateTours2();
      
        return true;
    }
public boolean complete_TourGroup_dynamic() {
        this.removeAllCamionsFromTourGroup();
       // this.initialiseCamionsToUse1();
        //Supprimer les doublons
        boolean[] visited = new boolean[problemD.getCustomers_dynamic().size()];
        for (int i = 0; i < visited.length; i++) {
            visited[i] = false;
        }
       
        for (Tour t : this.getTours()) {
            int i = 0;
            //System.out.println("haa tour lli fiha mochkill  :"+t.id_fictif);
            //for (int k=0;k<problemD.getCustomers_dynamic().size();k++)
            //{
              // System.out.println("id client problem :"+problemD.getCustomers_dynamic().get(k).getId());  
            //}
            
            while (i < t.size()) {
                Customer c = t.getCustomers().get(i);
               // System.out.println("haa id dyal hadak lclient lli mochkil :"+c.getId());
                if (visited[position_c(c,problemD.getCustomers_dynamic())] == true) {
                    t.removeCustomer(c);
                    
                } else {
                    visited[position_c(c,problemD.getCustomers_dynamic())] = true;
                    
                    i++;
                }
            }
        }
        //System.out.println("before split");
      //  System.out.println("taille  customer première tournée a l'intérieur de complete : "+this.getTours().get(0).size()+" id fictif tour: "+this.getTours().get(0).getId_fictif());
        //test_capacite_camion(this.getCamions().get(0), this);
         
        splitTours_dynamic();
       // System.out.println("after split");
        //System.out.println("taille  customer première tournée a l'intérieur de complete : "+this.getTours().get(0).size()+" id fictif tour: "+this.getTours().get(0).getId_fictif());
       // test_capacite_camion(this.getCamions().get(0), this);
       
        //System.out.println("after split"+this);
       
        for (int i = 0; i < problemD.getCustomers_dynamic().size(); i++) {
            Customer customer = problemD.getCustomers_dynamic().get(i);
            
            if (!visited[position_c(customer,problemD.getCustomers_dynamic())]) {
               //System.out.println("haa client fih mochkil: "+customer.getId());
                Saving savingforCustomer = this.BestSavingforCustomer_dynamic(customer);
                if (savingforCustomer != null) {
                   // System.out.println("daba dkhal saving not  null");
                    Tour tourforCustomer = savingforCustomer.getFirstTour();
                    tourforCustomer.setId_fictif(savingforCustomer.getFirstTour().getId_fictif());
                    if (savingforCustomer.getMergeType() == MergeType.TAIL_TO_HEAD) {
                        // System.out.println("cas tail to haid");
                        tourforCustomer.addCustomer(customer);
                      //   System.out.println("case tail to head");
       // System.out.println("taille  customer première tournée a l'intérieur de complete : "+this.getTours().get(0).size()+" id fictif tour: "+this.getTours().get(0).getId_fictif());
        //test_capacite_camion(this.getCamions().get(0), this);
       
                        //verify_population(problemD.getCustomers_dynamic(), this);
                  
                    } else if (savingforCustomer.getMergeType() == MergeType.HEAD_TO_TAIL) {
                         //System.out.println("cas haid to tail");
                        tourforCustomer.addCustomer(0, customer);
                        // System.out.println("case head to tail");
        //System.out.println("taille  customer première tournée a l'intérieur de complete : "+this.getTours().get(0).size()+" id fictif tour: "+this.getTours().get(0).getId_fictif());
       // test_capacite_camion(this.getCamions().get(0), this);
       
                        //verify_population(problemD.getCustomers_dynamic(), this);
                      
                    }
                } else {
                   //System.out.println("cas tournée jdida");
                    Tour tr = new Tour(this);
                    tr.setId_fictif(0);
                    tr.addCustomer(customer);
                   
                    //if (CheckCamionforTour_dynamic(tr) == true) {
                        
                        this.addTour(tr);
                       //  System.out.println("other case");
       // System.out.println("taille  customer première tournée a l'intérieur de complete : "+this.getTours().get(0).size()+" id fictif tour: "+this.getTours().get(0).getId_fictif());
       // test_capacite_camion(this.getCamions().get(0), this);
       
                        //verify_population(problemD.getCustomers_dynamic(), this);
                   // }
                }

            }

        }
        allocateTours2_dynamic();
      
        return true;
    }
            public Saving BestSavingforCustomer(Customer customer) {
        Tour tourCustomer = new Tour(this);
        tourCustomer.addCustomer(customer);
        Saving selectedSaving = null;
        Saving tmpSaving;
        double bestSaving = 0;
        for (Tour t : this.getTours()) {
            if (CheckCamionforTours(t, tourCustomer)) {
                tmpSaving = new Saving(this,t, tourCustomer);
                double s = tmpSaving.getSaving2();
                if (s > bestSaving) {
                    selectedSaving = tmpSaving;
                    bestSaving = s;
                }
            }

        }
        return selectedSaving;
    }
            public Saving BestSavingforCustomer_dynamic (Customer customer) {
        Tour tourCustomer = new Tour(this);
        tourCustomer.addCustomer(customer);
        Saving selectedSaving = null;
        Saving tmpSaving;
        double bestSaving = 0;
        for (Tour t : this.getTours()) {
           // if (CheckCamionforTours_dynamic(t, tourCustomer)) {
                tmpSaving = new Saving(this,t, tourCustomer,problemD);
                double s = tmpSaving.getSaving2_dynamic();
                if (s > bestSaving) {
                    selectedSaving = tmpSaving;
                    bestSaving = s;
                }
           // }

        }
        return selectedSaving;
    }
          public boolean CheckCamionforTours(Tour firstTour, Tour secondTour) {

        double totalTime = problem.getTemps(firstTour, secondTour);
        double totalQuantity = firstTour.getQuantity() + secondTour.getQuantity();
        Camion firstCamion = firstTour.getC();
        Camion secondCamion = secondTour.getC();
        firstTour.removeCamion();
        secondTour.removeCamion();

        boolean found = CheckCamionForTimeAndQuantity(totalTime, totalQuantity);
        
        firstTour.setCamion(firstCamion);
        secondTour.setCamion(secondCamion);

        return found;
    }
              public boolean CheckCamionforTours_dynamic(Tour firstTour, Tour secondTour) {

        double totalTime = problemD.getTemps_dynamic(firstTour, secondTour);
        if(totalTime==-1)
            return false;
        double totalQuantity = firstTour.getQuantity()+ secondTour.getQuantity();
        if (firstTour.id_fictif==0)
                {
        Camion firstCamion = firstTour.getC();
        Camion secondCamion = secondTour.getC();
        firstTour.removeCamion();
        secondTour.removeCamion();

        boolean found = CheckCamionForTimeAndQuantity_dynamic(totalTime, totalQuantity);
        
        firstTour.setCamion(firstCamion);
        secondTour.setCamion(secondCamion);

        return found;
        }
        else 
        {
            firstTour.removeCamion();
            firstTour.setBestCamion_dynamic();
            Depotfictif d= this.problemD.getdepotfictifById_dynamic(firstTour.getId_fictif());
          
            return ((firstTour.getQuantity()+ secondTour.getQuantity()<=d.getCapacité_restante())&& (totalTime<=this.problemD.maxTemps_dynamic+this.problemD.ovetime_dynamic-firstTour.getC().sum_temps_tour));
         }
    }
         public void addTourToCopy(int position, Tour et) {
        Tour t = et.clone();
        //t.tourGroup = this;
        this.addTour(position, t);
    }
          public void addTourToCopy_dynamic(int position, Tour et) {
        Tour t = et.clone();
        t.setId_fictif(et.getId_fictif());
        //t.tourGroup = this;
        this.addTour(position, t);
    }
       private TourGroup Substitute_Longest_Tours(TourGroup father, TourGroup mother) {
           /*Container myContainer = new Container();
            myContainer.setLayout(new GridLayout(2,2));
            myContainer.add(father.drawTourGroup2());
            myContainer.add(mother.drawTourGroup2());
           */
            TourGroup child1 = father.clone();
            TourGroup child2 = mother.clone();
           //System.out.println("child1:"+child1);
           //System.out.println("child2:"+child2);
           ArrayList<Tour> longestTrip;
         //  if(problem.getObjective1().equals("LTR"))
               longestTrip = child1.longestTrip();//child1.longestTours(2);//
          // else {
             /*  if(child1.getTours().size()>2)
                    longestTrip = child1.longestTours((int)child1.getTours().size()/3);
               else
                   longestTrip = child1.longestTours(1);//
           }*/
           ArrayList<Customer> customers = new ArrayList<Customer>();
           for(Tour t:longestTrip)
               customers.addAll(t.getCustomers());
           //System.out.println("customers "+new Tour(child1).CustomerstoString(customers));
           //laisser que les clients de longest dans child2
           for(int i=0;i<child2.getTours().size();i++){
                Tour t=child2.getTours().get(i);
                for(int k=0;k<t.size();k++){
                    Customer c=t.getCustomers().get(k);
                     if(!customers.contains(c)){
                         child2.removeCustomer(c);
                         k--;
                         if(t.size()==0)
                             i--;
                     }
                }
           }
           child1.removeCustomers(customers);
           child1.addAll(child2.getTours());
           //System.out.println("child1 after:"+child1);
           //System.out.println("child2 after:"+child2);
           /*child1.improveTours();
           TourGroup trgp = child1.minimizeLTRByTours();
            if(trgp!=null)
                child1.copyTourGroup(trgp);
           */
           /*myContainer.add(child1.drawTourGroup2());
           JOptionPane.showMessageDialog(null, myContainer);*/
                //list.add(child2);
            //System.out.println("crossover child1: "+list.get(1));

            return child1;
    }
    
      public void addAll(ArrayList<Tour> trs) {
        for (Tour tr : trs) {
            this.addTour(tr);
        }
    }

   /* public void updateCamionsToUse_remove(Camion camion) {
        if(camion==null)
            return;
        int a = getNbTours(camion);
        System.out.println("haaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa a nbtours  :"+ a);
        if(a==0){//remove camion
            camionsToUse.add(camion);
            return;
        }        
    }*/
      public ArrayList<Camion> getCamionsUsed() {
        int cost = 0;
        ArrayList<Camion> camionsUsed = new ArrayList<Camion>();
        //System.out.println("camions used "+camionsUsed);
        for (Tour t : getTours()) {
            Camion c = t.getC();
            if (!camionsUsed.contains(c)) {
                camionsUsed.add(c);
            }
        }
        return camionsUsed;
    }
        public ArrayList<Tour> getTours(Camion camion) {
        if(camion == null)
            return null;
        ArrayList<Tour> toursCamion = new ArrayList<Tour>();
        if (this.getTours() != null) {
            for (Tour tour : this.getTours()) {
                if (tour.getC() == camion) {
                    toursCamion.add(tour);
                }
            }
        }
        return toursCamion;
    }
    public ArrayList<Tour> longestTrip() {
        Camion camion=null;
        double longest = 0;
        for(Camion c:this.getCamions()){
            double time = getTemps(c);
            if(time>longest){
                longest=time;
                camion = c;
            }
        }
        if(camion==null)
            return null;
        else
            return this.getTours(camion);///problem.getInitialMaxTemps();
    }

    public ArrayList<Tour> longestTrip_dynamic() {
        Camion camion=null;
        double longest = 0;
        for(Camion c:this.getCamions()){
            double time = getTemps_dynamic(c);
            if(time>longest){
                longest=time;
                camion = c;
            }
        }
        if(camion==null)
            return null;
        else
            return this.getTours(camion);///problem.getInitialMaxTemps();
    }
    public Tour longestTour() {
        Tour tour=null;
        double longest = 0;
        for(Tour t:getTours()){
            double cost = t.getTemps();
            if(cost>longest){
                longest=cost;
                tour = t;
            }
        }
        return tour;
   }

    public Tour longestTour(ArrayList<Tour> list) {
        Tour tour=null;
        double longest = 0;
        for(Tour t:list){
            double cost = t.getTemps();
            if(cost>longest){
                longest=cost;
                tour = t;
            }
        }
        return tour;
   }

    public ArrayList<Tour> longestTours(int number) {
        ArrayList<Tour> longestTours = new ArrayList<Tour>();
        Tour tourToAdd;
        TourGroup copy = this.clone();//.copyTourGroup(this);
        for(int i=0;i<number;i++){
            tourToAdd = copy.longestTour();
            longestTours.add(tourToAdd.clone());//new Tour(tourToAdd, copy));
            copy.remove(tourToAdd);
        }
        //System.out.println("longest:"+longestTours);
        return longestTours;
   }
    public void remove(Tour et) {
       // System.out.println("au debut dyal remove:  "+ this.getProblem().getCamions().size());
        tours.remove(et);
        et.init();
        //Camion camion = et.getC();
       // System.out.println("à la fin dyal remove:  "+ this.getProblem().getCamions().size());
       // removeCamionFromCamionsToUse(camion); //inutile pour restricted fleet
    }
      public boolean removeCustomer(Customer customer) {
        for(int i=0;i<this.getTours().size();i++){
            Tour t=tours.get(i);
            if(t.removeCustomer(customer)==true){
                if(t.size()==0){
                    this.remove(t);
                }
                return true;
            }
        }
        return false;
    }
          public boolean removeCustomer_dynamic(Customer customer) {
        for(int i=0;i<this.getTours().size();i++){
            Tour t=this.getTours().get(i);
           
            for(Customer cs:t.getCustomers())
            {
                if(cs.getId()==customer.getId())
                { if(t.removeCustomer(cs)==true){
                if((t.size()==0)&&(t.id_fictif==0)){
                    this.remove(t);
                }
                return true;
                }
            }
                    
                   
            }
           
        }
        return false;
    }
      public void removeCustomers(ArrayList<Customer> customers) {
        for(Customer c:customers){
            this.removeCustomer(c);
        }
    }
      
      public void removeCustomers_dynamic(ArrayList<Customer> customers) {
        for(Customer c:customers){
            this.removeCustomer_dynamic(c);
        }
    }
      public boolean mutate_noConstraints() {
        //echande de deux clients
        for(int i=0; i<3; i++){
           // Random rnd = new Random();
            int r = rnd.nextInt(2);
            if(r==0)
                random_Exchange_noConstraints();
            else //if(r==1)
                random_Move_noConstraints();
            //else
              //  random_Split();
            //System.out.println("mutate "+i+" "+this);
        }
        return true;
    }
         public boolean mutate_noConstraints_dynamic() {
        //echande de deux clients
        for(int i=0; i<3; i++){
           // Random rnd = new Random();
            int r = rnd.nextInt(2);
            if(r==0)
                random_Exchange_noConstraints();
            else //if(r==1)
                random_Move_noConstraints_dynamic();
            //else
              //  random_Split();
            //System.out.println("mutate "+i+" "+this);
        }
        return true;
    }
      public boolean random_Split() {
        Random rnd = new Random();
        boolean done = false;
        int nbTours = this.getTours().size();
            if (nbTours < 1) {
                //System.out.println("Not enought tours to mutate.");  
                return false;
            }
            int c =0;
        while(done == false && c<nbTours){
            c++;
            int k = rnd.nextInt(nbTours);
            Tour t = this.getTours().get(k);
            if (t.size() < 2) {
                continue;
            } else {//t nest pas vide
                int position = rnd.nextInt(t.size());
                Tour tour1 = new Tour(this);
                Tour tour2 = new Tour(this);
                for (int i = 0; i < t.getCustomers().size(); i++) {
                    if (i <= position) {
                        tour1.addCustomer(t.getCustomers().get(i));
                    } else {
                        tour2.addCustomer(t.getCustomers().get(i));
                    }
                }
                this.remove(t);
                this.addTour(tour1);
                this.addTour(tour2);
                done = true;
                }
            }
        return done;
        }

    public boolean random_Exchange_noConstraints() {
        //echande de deux clients
        Random rnd = new Random();
        boolean done = false;
        int nbTours = this.getTours().size();
        int count = 0;
            if (nbTours < 2) {
                //System.out.println("Not enought tours to mutate.");  
                return false;
            }
        while(done == false){
            if(count>=5)
                return false;
            count++;
            
            // choix des indices de Tour
            int i, j;
            i = j = rnd.nextInt(nbTours);
            while (i == j) {
                j = rnd.nextInt(nbTours);
                //System.out.println("Oups, i is still equal to j:" + i + ", nbTours = " + nbTours);
            }
            // choix des indices de client dans les deux tours choisis
            Tour iTour = this.getTours().get(i);
            Tour jTour = this.getTours().get(j);
            int iPrime, jPrime;
            int iSize = iTour.getCustomers().size();//taille de la tourn�e i
            int jSize = jTour.getCustomers().size();

            if (iSize < 2 || jSize < 2) {
                done = false;
                continue;
            }
            iPrime = rnd.nextInt(iSize);
            jPrime = rnd.nextInt(jSize);
            Customer iCustomer = iTour.getCustomers().get(iPrime);
            Customer jCustomer = jTour.getCustomers().get(jPrime);
            
            iTour.removeCustomer(iCustomer);
            jTour.removeCustomer(jCustomer);
            iTour.addCustomer(iPrime, jCustomer);
            jTour.addCustomer(jPrime, iCustomer);
            //jTour.addCustomerInBestPositionWithoutCheckConstraint(iCustomer);
            //System.out.println("***exchange mutation done ");
            return true;
        }
        return false;
    }

    public boolean random_Move_noConstraints() {
        //echande de deux clients
        Random rnd = new Random();
        boolean done = false;
        int nbTours = this.getTours().size();
            if (nbTours < 2) {
                //System.out.println("Not enought tours to mutate.");  
                return false;
            }
        int count = 0;
        while(done == false){
            // choix des indices de Tour
            if(count==5)
                return false;
            count++;
            int i, j;
            i = j = rnd.nextInt(nbTours);
            while (i == j) {
                j = rnd.nextInt(nbTours);
                //System.out.println("Oups, i is still equal to j:" + i + ", nbTours = " + nbTours);
            }
            // choix des indices de client dans les deux tours choisis
            Tour iTour = this.getTours().get(i);
            Tour jTour = this.getTours().get(j);
            int iSize = iTour.size();//taille de la tourn�e i
            int jSize = jTour.size();

            if(iSize==0 || jSize == 0){
                done = false;
                continue;
            }
            int iPrime = rnd.nextInt(iSize);
            int jPrime = rnd.nextInt(jSize);
            
            Customer iCustomer = iTour.getCustomers().get(iPrime);
            
            iTour.removeCustomer(iCustomer);
            if(iTour.size()==0)
                this.remove(iTour);
            jTour.addCustomer(jPrime, iCustomer);
            //System.out.println("***move mutation done ");
            return true;
        }
        return done;
    }
      public boolean random_Move_noConstraints_dynamic() {
        //echande de deux clients
        Random rnd = new Random();
        boolean done = false;
        int nbTours = this.getTours().size();
            if (nbTours < 2) {
                //System.out.println("Not enought tours to mutate.");  
                return false;
            }
        int count = 0;
        while(done == false){
            // choix des indices de Tour
            if(count==5)
                return false;
            count++;
            int i, j;
            i = j = rnd.nextInt(nbTours);
            while (i == j) {
                j = rnd.nextInt(nbTours);
                //System.out.println("Oups, i is still equal to j:" + i + ", nbTours = " + nbTours);
            }
            // choix des indices de client dans les deux tours choisis
            Tour iTour = this.getTours().get(i);
            Tour jTour = this.getTours().get(j);
            int iSize = iTour.size();//taille de la tourn�e i
            int jSize = jTour.size();

            if(iSize==0 || jSize == 0){
                done = false;
                continue;
            }
            int iPrime = rnd.nextInt(iSize);
            int jPrime = rnd.nextInt(jSize);
            
            Customer iCustomer = iTour.getCustomers().get(iPrime);
            
            iTour.removeCustomer(iCustomer);
            if((iTour.size()==0)&&(iTour.getId_fictif()==0))
                this.remove(iTour);
            jTour.addCustomer(jPrime, iCustomer);
            //System.out.println("***move mutation done ");
            return true;
        }
        return done;
    }
       public Customer nearestnonVisitedCustomerToDepot() {
        double bestDistance = 1000000000;
        Customer other = null;
        for (Customer c : problem.getCustomers()) {
            if (CheckCustomerInTourGroup(c)) {
                continue;
            }
            if (bestDistance > problem.getDistances(0, c.getId())) {
                bestDistance = problem.getDistances(0, c.getId());
                other = c;
            }
        }
        //System.out.print("***"+other);
        return other;
    }
       
       
/*  public double checkExchangeCustomers(Tour tour1, Tour tour2, int positionTour1, int positionTour2) {
        //********Insertion du client dans position1 de tour1 dans la tour 2 à la position2
        //créer un nouveau tourGroup
        double cout_avant= tour1.calculer_temps_tournee()+tour2.calculer_temps_tournee();
        Customer c1 = tour1.getCustomers().get(positionTour1);
        Customer c2 = tour2.getCustomers().get(positionTour2);
        double newQuantity1 = tour1.getCurrent_capacity() //quantit� distribu�e lors de la tourn�e
                + c1.getDemande()
                - c2.getDemande();  //quantit� demand�e du client jPrime de la tourn�e j
        if (newQuantity1 <0) 
            return 0;
        // => nouvelle quantit� de la tourn�e i
        double newQuantity2 = tour2.getCurrent_capacity()
                - c1.getDemande()
                + c2.getDemande();
        if (newQuantity2 <0) 
            return 0;
        //********Construire les nouvelles tourxnées
        //System.out.println("trg"+this);
       // TourGroup newTrGp = new TourGroup(problem);
        //newTrGp.remove(positionTour1);newTrGp.remove(positionTour2);
        //System.out.println("new trg"+newTrGp);
        
        Tour newTour1 = tour1.clone();//new Tour(newTrGp);//tour without positionTour1
        //newTrGp.addTour(newTour1);
        newTour1.getCustomers().remove(c1);
        /*for (Customer c : tour1.getCustomers()) {
            if (c != c1) 
                newTour1.addCustomer(c);
            }*/
        
       /* if(newTour1.addCustomerInBestPositionWithoutCheckConstraint(c2)==false){
        //if(newTour1.addCustomerInBestPosition(c2)==false){
            return 0;
        }
        //System.out.println("after add customer "+newTour1);

       // newTour1.decroisement_iterative();
        if( newTour1.getCustomers().size()>=4)
                   {
                      
                              newTour1.checkDecroisement();
                      
                               newTour1.two_opt();
                      
                   }
                   else if (newTour1.getCustomers().size()>=2)
                   { 
                               newTour1.two_opt();
                     
                   }
        //System.out.println("after decross "+newTour1);
        if(newTour1.calculer_temps_tournee()>(problem.getMaxTemps()+problem.getOvertime()))
            return 0;
        //System.out.println("newTour1 after "+newTour1);
        
        Tour newTour2 = tour2.clone();//new Tour(newTrGp);
        //newTrGp.addTour(newTour2);
        newTour2.getCustomers().remove(c2);
        /*for (Customer c : tour2.getCustomers()) {
            if (c != 1c2) 
                newTour2.addCustomer(c);
        }*/
        //if(newTour2.addCustomerInBestPosition(c1)==false){
       /* if(newTour2.addCustomerInBestPositionWithoutCheckConstraint(c1)==false){
            return 0;
        }
        //System.out.println("after add customer 2"+newTour2);
        //newTour2.decroisement_iterative();
         if( newTour2.getCustomers().size()>=4)
                   {
                      
                              newTour2.checkDecroisement();
                      
                               newTour2.two_opt();
                      
                   }
                   else if (newTour2.getCustomers().size()>=2)
                   { 
                               newTour2.two_opt();
                     
                   }
        //System.out.println("after decross 2"+newTour2);
        if(newTour2.calculer_temps_tournee()>(problem.getMaxTemps()+problem.getOvertime()))
            return 0;
        double cout_apres=newTour1.calculer_temps_tournee()+newTour2.calculer_temps_tournee();
            if (cout_apres< cout_avant) return (cout_apres-cout_avant);
            else return 0;
  }
  
    public int[] checkBestExchangeCustomers(Tour tour1, Tour tour2) {
        if(tour1==tour2)
            return null;
        int valeur_a_retourner[]=new int[2];
        double diff=0 ; double maxDiff =0;
        //TourGroup newTourGroup, bestNewTourGroup=null;
        for(int k=0;k<tour1.getCustomers().size();k++){
            for(int m=0;m<tour2.getCustomers().size();m++){
                diff = checkExchangeCustomers(tour1, tour2, k, m);
                    if(diff<maxDiff){
                        
                        
                            maxDiff = diff; 
                            valeur_a_retourner[0]=k;
                            valeur_a_retourner[1]=m;
                            
                        }
                    }
            }
    if (maxDiff<0) 
        return valeur_a_retourner;
    else return null;
    }
       */
     @Override
    public TourGroup clone() {
        TourGroup o = null;
        try {
                // On récupère l'instance à renvoyer par l'appel de la 
                // méthode super.clone()
                o = (TourGroup) super.clone();
                o.tours = new ArrayList<Tour>();
                for(Tour t:this.tours)
                    o.addTour(t.clone());
                o.problem = this.problem;
                o.camions = (ArrayList<Camion>) this.camions.clone();
                
            } catch(CloneNotSupportedException cnse) {
                // Ne devrait jamais arriver car nous implémentons 
                // l'interface Cloneable
                cnse.printStackTrace(System.err);
        }
        // on renvoie le clone
        return o;
    }
     public TourGroup clone_dynamic() {
        TourGroup o = null;
        try {
                // On récupère l'instance à renvoyer par l'appel de la 
                // méthode super.clone()
                o = (TourGroup) super.clone();
                o.tours = new ArrayList<Tour>();
                for(Tour t:this.tours)
                    o.addTour(t.clone_dynamic());
                o.problemD = this.problemD;
               o.camions = (ArrayList<Camion>) this.camions.clone();
                
            } catch(CloneNotSupportedException cnse) {
                // Ne devrait jamais arriver car nous implémentons 
                // l'interface Cloneable
                cnse.printStackTrace(System.err);
        }
        // on renvoie le clone
        return o;
    }
     public double getLTR() {
        double longest = 0;
        for(Camion c:getCamions()){
            double time = getTemps(c);
            if(time>longest)
                longest=time;
        }
        return longest;///problem.getInitialMaxTemps();
    }
        public double getLTR_dynamic() {
        double longest = 0;
        for(Camion c:getCamions()){
            double time = getTemps_dynamic(c);
            if(time>longest)
                longest=time;
        }
        return longest;///problem.getInitialMaxTemps();
    }
     public double getdistance(){
         double dist=0;
         int nbcstmrs=0;
         for (Tour t: this.getTours())
         {
            // System.out.println("distance tournée :"+t.calculer_temps_tournee());
             //System.out.println("nombre de client tournnée :"+t.getCustomers().size());

             dist=dist+t.getTemps();
             nbcstmrs=nbcstmrs+t.getCustomers().size();
            
         }
          /* System.out.println("nombre client :"+nbcstmrs);
            System.out.println("distance :"+dist);
            for (Camion c : this.getCamionsUsed())
            {
              System.out.println("temps camion :"+ getTemps(c));  
            }
           System.out.println("overtime :"+(this.getLTR()-this.getProblem().getMaxTemps()));*/
         return dist;
         
     }
       public double getdistance_dynamic(){
         double dist=0;
         int nbcstmrs=0;
         for (Tour t: this.getTours())
         {
            // System.out.println("distance tournée :"+t.calculer_temps_tournee());
             //System.out.println("nombre de client tournnée :"+t.getCustomers().size());

             dist=dist+t.getTemps_dynamic();
             nbcstmrs=nbcstmrs+t.getCustomers().size();
            
         }
          /* System.out.println("nombre client :"+nbcstmrs);
            System.out.println("distance :"+dist);
            for (Camion c : this.getCamionsUsed())
            {
              System.out.println("temps camion :"+ getTemps(c));  
            }
           System.out.println("overtime :"+(this.getLTR()-this.getProblem().getMaxTemps()));*/
         return dist;
         
     }
     public Camion camionNotRespectingTimeConstraint() {
        
        for (Camion c : this.getCamions()) {
            if (getTemps(c) > problem.getMaxTemps()) {
                return c;
            }
        }
        return null;
    }

    public Tour FindTimeTourConstraint() {
       
        for (Tour t : this.getTours()) {
            if (t.getTemps() > problem.getMaxTemps()) {
                return t;
            }
        }
        return null;
    }

    
     @Override
    public int compareTo(TourGroup tourGroup) {
        double diff;
        //System.out.println("compare "+this+"\nto"+tourGroup);
     //   if(problem.getObjective1().equals("LTR") ){
            if( this.getLTR()<=problem.getMaxTemps()
                && tourGroup.getLTR()<=problem.getMaxTemps()){
            //System.out.println("diff cost"+(this.getCostDistance()-(int)tourGroup.getCostDistance()));
                diff = this.getdistance()-tourGroup.getdistance();
                if(diff>0)
                    return 1;
                if(diff<0)
                    return -1;
                if(diff==0)
                    return 0;
            }
            else
            {
                if (this.getLTR()<=problem.getMaxTemps()&& tourGroup.getLTR()>problem.getMaxTemps())
                { return -1;}
                else
                {
                  if (this.getLTR()>problem.getMaxTemps()&& tourGroup.getLTR()<=problem.getMaxTemps())
                    return 1; 
                  else 
                  {
                      diff=this.getLTR()-tourGroup.getLTR();
                       if(diff>0)
                    return 1;
                if(diff<0)
                    return -1;
                if(diff==0)
                    return 0;
                  }
                }
            }
        
         //   else {
        
        return -2;
    
    }
     public int compareTo_dynamic(TourGroup tourGroup) {
        double diff;
        //System.out.println("compare "+this+"\nto"+tourGroup);
     //   if(problem.getObjective1().equals("LTR") ){
            if( this.getLTR_dynamic()<=problemD.getMaxTemps_dynamic()
                && tourGroup.getLTR_dynamic()<=problemD.getMaxTemps_dynamic()){
            //System.out.println("diff cost"+(this.getCostDistance()-(int)tourGroup.getCostDistance()));
                diff = this.getdistance_dynamic()-tourGroup.getdistance_dynamic();
                if(diff>0)
                    return 1;
                if(diff<0)
                    return -1;
                if(diff==0)
                    return 0;
            }
            else
            {
                if (this.getLTR_dynamic()<=problemD.getMaxTemps_dynamic()&& tourGroup.getLTR_dynamic()>problemD.getMaxTemps_dynamic())
                { return -1;}
                else
                {
                  if (this.getLTR_dynamic()>problemD.getMaxTemps_dynamic()&& tourGroup.getLTR_dynamic()<=problemD.getMaxTemps_dynamic())
                    return 1; 
                  else 
                  {
                      diff=this.getLTR_dynamic()-tourGroup.getLTR_dynamic();
                       if(diff>0)
                    return 1;
                if(diff<0)
                    return -1;
                if(diff==0)
                    return 0;
                  }
                }
            }
        
         //   else {
        
        return -2;
    
    }
    public void improveTours(){
        for(Tour t:this.getTours()){
           // t.decroisement_iterative();
            t.two_opt_iterative();
        }
    }
      public void improveTours_dynamic(){
        for(Tour t:this.getTours()){
           // t.decroisement_iterative();
            t.two_opt_iterative_dynamic();
            
        }
    }
      public boolean CheckCapacityConstraint() {
        double c, q;
        for (Tour tour : this.getTours()) {
            if (tour.getC() != null) {
                c = tour.getC().getCapacity();
                q = tour.getQuantity();
                if (q > c) {
                    System.out.println("quantité " + q + " << capacité " + c);
                    return false;
                }
            }
        }
        return true;
    }
      

    public boolean CheckAllCustomersConstraint() {
        for (Customer customer : problem.getCustomers()) {
            //found = found && tourGroup.CheckCustomer(customer);
            if (!this.CheckCustomerInTourGroup(customer)) {
                System.out.println("customer " + customer + " n'est pas visité");
                System.out.println("TourGroup " + this);
                return false;
            }
        }
        return true;
    }
     public boolean CheckAllCustomersConstraint_dynamic() {
        for (Customer customer : problemD.getCustomers_dynamic()) {
            //found = found && tourGroup.CheckCustomer(customer);
            if (!this.CheckCustomerInTourGroup(customer)) {
                System.out.println("customer " + customer + " n'est pas visité");
                System.out.println("TourGroup " + this);
                return false;
            }
        }
        return true;
    }
    public boolean CheckCamionsConstraint() {
        for (Tour tour : this.getTours()) {
            Camion c=tour.getC();
            if (c == null) {
                  //System.out.println("hnCamion null!!!a fin 7saal 3333");
                  //System.out.println("haa la tournée dyal had lcamion : "+tour.getCustomers().size());
                
                return false;
            }
            if (tour.getQuantity()>c.getCapacity()) {
               // System.out.println("Camion non suffisante!!!");
                //System.out.println("Capacité camiooooooooooooon : "+c.getCapacity());
               
                return false;
            }
           /* if (getNbTours(c)>problem.getNbTripsByVehicle()) {
                //System.out.println("Camion dépasse nb de routes!!!");
                return false;
            }*/
        }
        return true;
    }
    

    public void splitTours() {
        int k = 0;
        while (k < this.getTours().size()) {
            Tour t = this.getTours().get(k);
            if (t.size() == 0) {
                this.remove(t);
                
                
        System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm*************************************mmmmmmmmmm");
                                               
                            
            } else {//t nest pas vide
                if (t.checkConstraints() == false) {
                    //System.out.println("before split "+t);
                    ArrayList<Tour> splitedTours = this.splitTour(t,true);
                    //System.out.println("splitedTour "+splitedTour);
                    int l = 0;
                    if (splitedTours != null) {
                        this.remove(t);
                        for (Tour st : splitedTours) {
                            this.addTour(k + l, st);
                            l++;
                        }
                        //System.out.println("split done");
                    }
                }
                k++;
            }
        }
        }
     public void test_capacite_camion(Camion c,TourGroup trg)
       {
           classer_tournees(trg.getTours());
           if (c.getDepot_fictif_final()!=null)
           {
           if (trg.getTours().get(0).getQuantity()>c.getDepot_fictif_final().capacité_restante)
           {
        
               
               System.out.println("hooooooooooooooooooooooooooooooooooooooooooooooooooooo: "+trg.getTours().get(0).getQuantity());
           System.out.println("hooooooooooooooooooooooooooooooooooooooooooooooooooooo capacité depot fictif: "+this.getProblemD().getDepots_fictif().get(0).capacité_restante);

           }
            
           }
       
           
       }
    public void splitTours_dynamic() {
        int k = 0;
        while (k < this.getTours().size()) {
            Tour t = this.getTours().get(k);
            if ((t.size() == 0)&&(t.getId_fictif()==0)) {
                this.remove(t);
                
                
        //System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm*************************************mmmmmmmmmm");
                                               
                            
            } else {//t nest pas vide
                //System.out.println("before check");
             
                 
                //System.out.println("check constraint dynamique false: capacité tournées :"+t.getcapacity());
                 //  System.out.println("check constraint dynamique false: capacité tournées :"+t.getQuantity());
                 
                if (t.checkConstraints_dynamic()== false) {
                  // System.out.println("check constraint dynamique false: capacité tournées :"+t.getcapacity());
                  // System.out.println("check constraint dynamique false: capacité tournées :"+t.getQuantity());
                 
                    ArrayList<Tour> splitedTours = this.splitTour_dynamic(t,true);
                    //System.out.println("splitedTour "+splitedTour);
                    int l = 0;
                    if (splitedTours != null) {
                        this.remove(t);
                        for (Tour st : splitedTours) {
                            this.addTour(k + l, st);
                            l++;
                        }
                        //System.out.println("split done");
                    }
                }
                k++;
            }
        }
        }
     public ArrayList<Tour> splitTour(Tour tour,boolean improve) {
        TourGroup newTrGp = new TourGroup(problem);
        for(Tour t:this.tours)
            if(t!=tour)
                newTrGp.addTour(t.clone());
        //newTrGp.copyTourGroup(this);
        //newTrGp.remove(this.tours.indexOf(tour));
        
        Tour tmpTour1 = new Tour(newTrGp);//vide
        Tour tmpTour2 = tour.clone();//new Tour(tmpTour1, this);//copy de tour
        newTrGp.addTour(tmpTour1);
        newTrGp.addTour(tmpTour2);
        //System.out.println("before best split" + this);
        for (int i = 0; i < tour.getCustomers().size()-1; i++) {
            Customer customer = tour.getCustomers().get(i);
            tmpTour1.addCustomer(customer);
            tmpTour2.removeCustomer(customer);
            if (tmpTour1.getQuantity() <= problem.getMaxCapacity()
                    && tmpTour2.getQuantity() <= problem.getMaxCapacity()
                    && tmpTour1.getTemps() <= problem.getMaxTemps()+problem.getOvertime()
                    && tmpTour2.getTemps() <= problem.getMaxTemps()+problem.getOvertime()
                    ) {// check constraint
                if(improve==true){
                    tmpTour1.improve1();
                    tmpTour2.improve1();
                }
                if(tmpTour1.setBestCamion()==null)
                    continue;
                if(tmpTour2.setBestCamion()==null)
                    continue;
                ArrayList<Tour> list = new ArrayList<Tour>();
                list.add(tmpTour1);
                list.add(tmpTour2);
                //System.out.println("tmpTour 1"+tmpTour1);
                //System.out.println("tmpTour 2"+tmpTour2);
                return list;
                
            }
        }
        return null;
    }
       public ArrayList<Tour> splitTour_dynamic(Tour tour,boolean improve) {
        TourGroup newTrGp = new TourGroup(problemD);
        for(Tour t:this.tours)
        { if(t!=tour)
            
                newTrGp.addTour(t.clone_dynamic());
        }  
        //newTrGp.copyTourGroup(this);
        //newTrGp.remove(this.tours.indexOf(tour));
        
        Tour tmpTour1 = new Tour(newTrGp);//vide
        tmpTour1.setId_fictif(0);
        Tour tmpTour2 = tour.clone_dynamic();//new Tour(tmpTour1, this);//copy de tour
        tmpTour2.setId_fictif(tour.getId_fictif());
        newTrGp.addTour(tmpTour1);
        newTrGp.addTour(tmpTour2);
        //System.out.println("before best split" );
          /* for (int i = 0; i < tour.getCustomers().size(); i++) {
                System.out.println("client tour: "+tour.getCustomers().get(i).getId());
           }*/
               /*System.out.println("capacité Tour: "+tmpTour2.getcapacity());
              System.out.println("quantité Tour: "+tmpTour2.getQuantity());
               System.out.println("temps restant Tour2: "+tmpTour2.getTempsRestant());
             System.out.println("temps dynamique Tour2: "+tmpTour2.getTemps_dynamic());*/
        for (int i = 0; i < tour.getCustomers().size(); i++) {
            Customer customer = tour.getCustomers().get(i);
           
            tmpTour1.addCustomer(customer);
            tmpTour2.removeCustomer(customer);
            /* System.out.println("premier affichage ");
             System.out.println("client à échanger: "+customer.getId());
            System.out.println("capacité tmpTour1: "+tmpTour1.getcapacity());
              System.out.println("quantité tmpTour1: "+tmpTour1.getQuantity());
              System.out.println("temps restant tmpTour1: "+tmpTour1.getTempsRestant());
             System.out.println("temps dynamique tmpTour1: "+tmpTour1.getTemps_dynamic());
               System.out.println("capacité tmpTour2: "+tmpTour2.getcapacity());
              System.out.println("quantité tmpTour2: "+tmpTour2.getQuantity());
               System.out.println("temps restant tmpTour2: "+tmpTour2.getTempsRestant());
             System.out.println("temps dynamique tmpTour2: "+tmpTour2.getTemps_dynamic());
             
            System.out.println(" fin premier affichage ");*/
              if (tmpTour1.getQuantity()<= tmpTour1.getcapacity()
                    && tmpTour2.getQuantity() <= tmpTour2.getcapacity()
                   // && tmpTour1.getTemps_dynamic()<= tmpTour1.getTempsRestant()
                    //&& tmpTour2.getTemps_dynamic()<= tmpTour2.getTempsRestant()
                    ) {// check constraint
                 // System.out.println(" dkhal l if split l3adiya");
                  /*if (tmpTour1.getTemps_dynamic()> tmpTour1.getTempsRestant()
                    ||tmpTour2.getTemps_dynamic()> tmpTour2.getTempsRestant())
                  {
                    System.out.println("hadi if fisrt case probème de temps");
                        System.out.println("client à échangerrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr: "+customer.getId());
            System.out.println("capacité tmpTour1111111111111111111111111111111111111111111111111111111111111111111: "+tmpTour1.getcapacity());
              System.out.println("quantité tmpTour11111111111111111111111111111111111111111111111111: "+tmpTour1.getQuantity());
              System.out.println("temps restant tmpTour111111111111111111111111111111111111111111111: "+tmpTour1.getTempsRestant());
             System.out.println("temps dynamique tmpTour11111111111111111111111111111111111111111111111: "+tmpTour1.getTemps_dynamic());
               System.out.println("capacité tmpTour2222222222222222222222222222222222222222222222222222222: "+tmpTour2.getcapacity());
              System.out.println("quantité tmpTour2222222222222222222222222222222222222222222222222222222: "+tmpTour2.getQuantity());
               System.out.println("temps restant tmpTour222222222222222222222222222222222222222222222222222: "+tmpTour2.getTempsRestant());
             System.out.println("temps dynamique tmpTour222222222222222222222222222222222222222222222222222: "+tmpTour2.getTemps_dynamic());
                
                  }*/
                      
                if(improve==true){
                    tmpTour1.improve1_dynamic();
                    tmpTour2.improve1_dynamic();
                }
               /* System.out.println(" dakchi kaamel après improve");
                 System.out.println("capacité tmpTour1: "+tmpTour1.getcapacity());
              System.out.println("quantité tmpTour1: "+tmpTour1.getQuantity());
              System.out.println("temps restant tmpTour1: "+tmpTour1.getTempsRestant());
             System.out.println("temps dynamique tmpTour1: "+tmpTour1.getTemps_dynamic());
               System.out.println("capacité tmpTour2: "+tmpTour2.getcapacity());
              System.out.println("quantité tmpTour2: "+tmpTour2.getQuantity());
               System.out.println("temps restant tmpTour2: "+tmpTour2.getTempsRestant());
             System.out.println("temps dynamique tmpTour2: "+tmpTour2.getTemps_dynamic());*/
             
                //if(tmpTour1.setBestCamion_dynamic()==null)
                  //  continue;
               // if(tmpTour2.setBestCamion_dynamic()==null)
                  //  continue;
                ArrayList<Tour> list = new ArrayList<Tour>();
                list.add(tmpTour1);
                list.add(tmpTour2);
                //System.out.println("tmpTour 1"+tmpTour1);
                //System.out.println("tmpTour 2"+tmpTour2);
                return list;
                
            }
              
                  if (tmpTour1.getQuantity()> tmpTour1.getcapacity()
                    && tmpTour2.getQuantity() <= tmpTour2.getcapacity() )
                    
                    {
                         /*System.out.println("hadi if thanyaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                        System.out.println("client à échangerrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr: "+customer.getId());
            System.out.println("capacité tmpTour1111111111111111111111111111111111111111111111111111111111111111111: "+tmpTour1.getcapacity());
              System.out.println("quantité tmpTour11111111111111111111111111111111111111111111111111: "+tmpTour1.getQuantity());
              System.out.println("temps restant tmpTour111111111111111111111111111111111111111111111: "+tmpTour1.getTempsRestant());
             System.out.println("temps dynamique tmpTour11111111111111111111111111111111111111111111111: "+tmpTour1.getTemps_dynamic());
               System.out.println("capacité tmpTour2222222222222222222222222222222222222222222222222222222: "+tmpTour2.getcapacity());
              System.out.println("quantité tmpTour2222222222222222222222222222222222222222222222222222222: "+tmpTour2.getQuantity());
               System.out.println("temps restant tmpTour222222222222222222222222222222222222222222222222222: "+tmpTour2.getTempsRestant());
             System.out.println("temps dynamique tmpTour222222222222222222222222222222222222222222222222222: "+tmpTour2.getTemps_dynamic());
             */
          Tour tmpTour3 = new Tour(newTrGp);//vide
        tmpTour3.setId_fictif(0);
        Tour tmpTour4 = tmpTour1.clone_dynamic();//new Tour(tmpTour1, this);//copy de tour
        tmpTour4.setId_fictif(tmpTour1.getId_fictif());
        //newTrGp.remove(tmpTour1);
        newTrGp.addTour(tmpTour3);
        newTrGp.addTour(tmpTour4);
        //System.out.println("before best split" + this);
        for (int j = 0; j < tmpTour1.getCustomers().size(); j++) {
            Customer custome = tmpTour1.getCustomers().get(j);
            tmpTour3.addCustomer(custome);
            tmpTour4.removeCustomer(custome);
            /* System.out.println(" dakchi kaamel après improve f le cas split en 3 walakin 9bel mankholo l if quantité et temps");
                 System.out.println("capacité tmpTour3: "+tmpTour3.getcapacity());
              System.out.println("quantité tmpTour3: "+tmpTour3.getQuantity());
              System.out.println("temps restant tmpTour3: "+tmpTour3.getTempsRestant());
             System.out.println("temps dynamique tmpTour3: "+tmpTour3.getTemps_dynamic());
               System.out.println("capacité tmpTour4: "+tmpTour4.getcapacity());
              System.out.println("quantité tmpTour4: "+tmpTour4.getQuantity());
               System.out.println("temps restant tmpTour4: "+tmpTour4.getTempsRestant());
             System.out.println("temps dynamique tmpTour4: "+tmpTour4.getTemps_dynamic());
              System.out.println("capacité tmpTour2: "+tmpTour2.getcapacity());
              System.out.println("quantité tmpTour2: "+tmpTour2.getQuantity());
               System.out.println("temps restant tmpTour2: "+tmpTour2.getTempsRestant());
             System.out.println("temps dynamique tmpTour2: "+tmpTour2.getTemps_dynamic());*/
// check constraint
           if (tmpTour3.getQuantity()<= tmpTour3.getcapacity()
                    && tmpTour4.getQuantity() <= tmpTour4.getcapacity()
                    //&& tmpTour3.getTemps_dynamic()<= tmpTour3.getTempsRestant()
                    //&& tmpTour4.getTemps_dynamic()<= tmpTour4.getTempsRestant()
                    )
                {// check constraint
                if(improve==true){
                    tmpTour3.improve1_dynamic();
                    tmpTour4.improve1_dynamic();
                }
                /*System.out.println(" dakchi kaamel après improve f le cas split en 3");
                 System.out.println("capacité tmpTour3: "+tmpTour3.getcapacity());
              System.out.println("quantité tmpTour3: "+tmpTour3.getQuantity());
              System.out.println("temps restant tmpTour3: "+tmpTour3.getTempsRestant());
             System.out.println("temps dynamique tmpTour3: "+tmpTour3.getTemps_dynamic());
               System.out.println("capacité tmpTour4: "+tmpTour4.getcapacity());
              System.out.println("quantité tmpTour4: "+tmpTour4.getQuantity());
               System.out.println("temps restant tmpTour4: "+tmpTour4.getTempsRestant());
             System.out.println("temps dynamique tmpTour4: "+tmpTour4.getTemps_dynamic());
              System.out.println("capacité tmpTour2: "+tmpTour2.getcapacity());
              System.out.println("quantité tmpTour2: "+tmpTour2.getQuantity());
               System.out.println("temps restant tmpTour2: "+tmpTour2.getTempsRestant());
             System.out.println("temps dynamique tmpTour2: "+tmpTour2.getTemps_dynamic());*/
                //if(tmpTour1.setBestCamion_dynamic()==null)
                  //  continue;
               // if(tmpTour2.setBestCamion_dynamic()==null)
                  //  continue;
                ArrayList<Tour> list = new ArrayList<Tour>();
                list.add(tmpTour2);
                list.add(tmpTour3);
                 list.add(tmpTour4);
                //System.out.println("tmpTour 1"+tmpTour1);
                //System.out.println("tmpTour 2"+tmpTour2);
                return list;
                }
                
            }
              
                    }   
            
        }
        return null;
    }
      
       public double get_temps_dynamic_restant(){
           double min = 1000000000;
           for (Camion c : this.getCamions())
           {
               if (c.getSum_temps_tour()<min)
                   min= c.getSum_temps_tour();
           }
           return (this.problemD.maxTemps_dynamic+this.problemD.getOvetime_dynamic()-min);
       }
    public double getObj1() {
        double cost=0;
        if(this.getLTR()<=problem.maxTemps){
            
                cost=this.getdistance();
                this.setObj("dist");}
        else {
            cost=this.getLTR()-problem.maxTemps;
            this.setObj("over");
        }
       
        return cost;
    }
      public Boolean similarTourGroup(TourGroup other) {
        TourGroup trgp1 = this.clone();//trgp1.copyTourGroup(this);
        TourGroup trgp2 = other.clone();//trgp2.copyTourGroup(other);
        
        /*ArrayList<Tour> longestTrip1 = trgp1.longestTrip();
        ArrayList<Tour> longestTrip2 = trgp2.longestTrip();
        
        if (IdenticTours(longestTrip1, longestTrip2)) {
            trgp1.remove(longestTrip1);
            trgp2.remove(longestTrip2);
            if(NumberIdenticTours(trgp1.getTours(), trgp2.getTours())>=trgp1.size()-1);
                return true;
        }*/
        TourGroup_Individual1 ind1 = new TourGroup_Individual1(trgp1);
        TourGroup_Individual1 ind2 = new TourGroup_Individual1(trgp2);
        return ind1.IdenticTourGroup_Individual1(ind2);
    }
        public Boolean similarTourGroup_dynamic(TourGroup other) {
        TourGroup trgp1 = this.clone_dynamic();//trgp1.copyTourGroup(this);
        TourGroup trgp2 = other.clone_dynamic();//trgp2.copyTourGroup(other);
        
        /*ArrayList<Tour> longestTrip1 = trgp1.longestTrip();
        ArrayList<Tour> longestTrip2 = trgp2.longestTrip();
        
        if (IdenticTours(longestTrip1, longestTrip2)) {
            trgp1.remove(longestTrip1);
            trgp2.remove(longestTrip2);
            if(NumberIdenticTours(trgp1.getTours(), trgp2.getTours())>=trgp1.size()-1);
                return true;
        }*/
        TourGroup_Individual1 ind1 = new TourGroup_Individual1(trgp1,problemD);
        TourGroup_Individual1 ind2 = new TourGroup_Individual1(trgp2,problemD);
        return ind1.IdenticTourGroup_Individual1_dynamic(ind2);
    }
     
     public ArrayList<Tour> get_tours_of_camion (Camion c){
         ArrayList<Tour> trs= new ArrayList<Tour>() ;
         trs.clear();
         
         for (int i=0;i<this.getTours().size();i++)
         {
           if (this.getTours().get(i).getC().getIdcamion()==c.getIdcamion())
               trs.add(this.getTours().get(i));
         }
          return trs;   
         } 
     public ArrayList<TourGroup> insert_customer_tourgroup(TourGroup solution_preced, Customer c){
         ArrayList <TourGroup> trg= new ArrayList <TourGroup> ();
        trg.clear();
        int k;
             for (int j=0; j<solution_preced.getTours().size();j++)
             {
                //  System.out.println("haaa VRPD dyal sol preced: "+ solution_preced.getProblemD());
                 if  ((k=solution_preced.getTours().get(j).bestPositionInsertClientWithoutCheckConstraint_dynamic(c))>=0)
                 {
                     //System.out.println("haaa VRPD dyal sol preced: "+ solution_preced.getProblemD());
                     TourGroup tg = solution_preced.clone_dynamic();
                     tg.getTours().get(j).addCustomer(k,c);
                     trg.add(tg);
                 }
                 
             }
            if (!trg.isEmpty()){
                return trg;
            }
            else 
            {
                     TourGroup tg = solution_preced.clone_dynamic();
                     Tour t= new Tour(this);
                     t.addCustomer(c);
                     tg.addTour(t);
                     trg.add(tg);
                     return trg;
            }
           
         }
     public ArrayList<TourGroup> insert_customer_tourgroups(ArrayList<TourGroup>  solutions_preceds, Customer c){
         ArrayList <TourGroup> trg= new ArrayList <TourGroup> ();
        trg.clear();
        //System.out.println("taille solution preced"+solutions_preceds.size());
             for (int i=0; i<solutions_preceds.size();i++)
             {
                 if (insert_customer_tourgroup(solutions_preceds.get(i),c)!=null)
                 {
                //System.out.println("insert customer true haa taille dyalha"+insert_customer_tourgroup(solutions_preceds.get(i),c).size());
                  //   System.out.println("haa i:"+i);
                     trg.addAll(insert_customer_tourgroup(solutions_preceds.get(i),c));
                 }              
             }
            
                return trg;
     }
      public Boolean checkInPopulation_dynamic(TourGroup trgp,ArrayList<TourGroup> pop) {
        for(TourGroup ind: pop){
             if (trgp.similarTourGroup_dynamic(ind)){
                 return true;
             }
         }
         return false;
    } 
      public void afficher_camion() {
        for(Camion c: this.getCamions()){
            if (c.getId_fictif_final()!=0)
           System.out.println("id fictiiiif final camion: "+c.getId_fictif_final());
             
             }
         }
         
     
       public void afficher_trg_dynamic(TourGroup trg){
    for (int i=0;i<trg.getTours().size();i++)
    {
        System.out.println("Tour:"+i );
        System.out.println("Id fictif final tour :" +trg.getTours().get(i).getId_fictif());
        for (int j=0;j<trg.getTours().get(i).getCustomers().size();j++)
        {
            System.out.println("Customer:"+trg.getTours().get(i).getCustomers().get(j).id ); 
        }
        
    }
    
    
}
     public ArrayList<TourGroup> HeuristiqueInsertion( TourGroup solution, ArrayList<Customer> clients){
        // System.out.println("solution probleeeem:" +solution.getProblemD());
          ArrayList <TourGroup> trg=insert_customer_tourgroup(solution,clients.get(0));
         
         for (int i=1; i<clients.size(); i++)
         {
             ArrayList <TourGroup> trgrr=new  ArrayList <TourGroup>(); 
             trgrr.clear();
             for (int j=0; j<trg.size();j++ )
             {
                 
                 trgrr.add(trg.get(j));
             }
             // System.out.println("taille trg dkhal l for:"+trg.size());
             trg.clear();
          if (trgrr.size()>100)
             {
                  ArrayList <TourGroup> trggg=new ArrayList <TourGroup>();
             trggg.clear();
             int k=0;
            while(k<20)
            {
            TourGroup trgg= new TourGroup(problemD);
                //trg.getTours().clear();
            trgg=trgrr.get(rnd.nextInt(trgrr.size())).clone_dynamic();
            if (checkInPopulation_dynamic(trgg,trggg)==false)
            {
            //System.out.println("la bon caaaaaaaaaaaaaaaas");
            trggg.add(trgg);
            k++;
            }
            else
            {
                continue;
            }
            }
            trg= insert_customer_tourgroups(trggg,clients.get(i)); 
            }
          else
          trg= insert_customer_tourgroups(trgrr,clients.get(i));  
         }
        if (trg.size()>100)
        {
             //System.out.println("tailllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllle trg dkhal l if:"+trg.size());
             ArrayList <TourGroup> trggg=new ArrayList <TourGroup>();
             trggg.clear();
             int i=0;
            while(i<100)
            {
            TourGroup trgg= new TourGroup(problemD);
                //trg.getTours().clear();
            trgg=trg.get(rnd.nextInt(trg.size())).clone_dynamic();
            trggg.add(trgg);
            i++;
            }
            for (TourGroup tg: trggg)
            
         {
            
           tg.allocateTours2_dynamic();
            int id_fic=0;
          for (int j=0;j<tg.getTours().size();j++)
          {
              if (tg.getTours().get(j).getId_fictif()!=0)
              {
                  id_fic=id_fic+1;
               }
          }
          int camion_fic=0;
          for (int j=0;j<this.getProblemD().getCamions_dynamic().size();j++)
          {
              if (this.getProblemD().getCamions_dynamic().get(j).getDepot_fictif_final()!=null)
              {
                  camion_fic=camion_fic+1;
              }
          }
          
         if (id_fic!=camion_fic)
         {System.out.println("nommmmmmmmmmmmmmmmmmmmmmmmmmmmmmmbrrrrrrrrrrrrrrrrre tour id fictif non null: "+ id_fic); 
           System.out.println("nommmmmmmmmmmmmmmmmmmmmmmmmmmmmmmbrrrrrrrrrrrrrrrrre camion id fictif non null: "+ camion_fic);
          System.out.println("haaaaaaaaaaaaaaaaaaaaaa 7riiiraaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa nombre dépot fictif :"+tg.getProblemD().getDepots_fictif().size());    
        
         }
           // System.out.println("allocate if");
             
           //tg.improve_dynamic(problemD.NbImprove);
         }
          //System.out.println("ha howa sala allocate hadi mora if:"+trg.size());
           return trggg;  
              
        
        }
        for (TourGroup tg: trg)
            
         {
           tg.allocateTours2_dynamic();
                 int id_fic=0;
          for (int j=0;j<tg.getTours().size();j++)
          {
              if (tg.getTours().get(j).getId_fictif()!=0)
              {
                  id_fic=id_fic+1;
               }
          }
          int camion_fic=0;
          for (int j=0;j<this.getProblemD().getCamions_dynamic().size();j++)
          {
              if (this.getProblemD().getCamions_dynamic().get(j).getDepot_fictif_final()!=null)
              {
                  camion_fic=camion_fic+1;
              }
          }
          
         if (id_fic!=camion_fic)
         {System.out.println("nommmmmmmmmmmmmmmmmmmmmmmmmmmmmmmbrrrrrrrrrrrrrrrrre tour id fictif non null: "+ id_fic); 
           System.out.println("nommmmmmmmmmmmmmmmmmmmmmmmmmmmmmmbrrrrrrrrrrrrrrrrre camion id fictif non null: "+ camion_fic);
          System.out.println("haaaaaaaaaaaaaaaaaaaaaa 7riiiraaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa nombre dépot fictif :"+tg.getProblemD().getDepots_fictif().size());    
        
         }
             //.out.println("haado ghir l'aheuristique d'insertion 3adiya ");
                           //afficher_trg_dynamic(tg);
           //tg.improve_dynamic(problemD.NbImprove);
         }
        if (trg.size()<20)
        {
            for (int i=trg.size();i<20;i++)
                          
                 { int j=0;
                      //System.out.println("à l'intérieur de la boucle fooooooor  :"+trg.size());
                       TourGroup trgrcale = new TourGroup(problemD);
                       do{ 
                      
                      trgrcale=null; //TourGroup trgr = new TourGroup(problem);
                      trgrcale= new TourGroup(problemD).solve_insertion_dynamic();
                      //System.out.println("à l'intérieur de la boucle while dyal l'onsertion aléatoire: "+j);
                          // afficher_trg_dynamic(trgrcale);
                      j++;
          /*int id_fic=0;
          for (int l=0;l<trgrcale.getTours().size();l++)
          {
              if (trgrcale.getTours().get(l).getId_fictif()!=0)
              {
                  id_fic=id_fic+1;
               }
          }
          int camion_fic=0;
          for (int l=0;l<this.getProblemD().getCamions_dynamic().size();j++)
          {
              if (this.getProblemD().getCamions_dynamic().get(l).getDepot_fictif_final()!=null)
              {
                  camion_fic=camion_fic+1;
              }
          }
          
         if (id_fic!=camion_fic)
         {System.out.println("hadi insertion séquentiel: "); 
         System.out.println("nommmmmmmmmmmmmmmmmmmmmmmmmmmmmmmbrrrrrrrrrrrrrrrrre tour id fictif non null: "+ id_fic); 
           System.out.println("nommmmmmmmmmmmmmmmmmmmmmmmmmmmmmmbrrrrrrrrrrrrrrrrre camion id fictif non null: "+ camion_fic);
          System.out.println("haaaaaaaaaaaaaaaaaaaaaa 7riiiraaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa nombre dépot fictif :"+trgrcale.getProblemD().getDepots_fictif().size());    
        
         }*/
                       //trgrcseq= trgr;
                    } 
                       while(trgrcale.CheckCapacityConstraint()==false
                        || trgrcale.CheckAllCustomersConstraint_dynamic()==false|| checkInPopulation_dynamic(trgrcale,trg)==true);
                        trg.add(i,trgrcale);
                        
        }
        }
        // System.out.println("tailllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllle trg:"+trg.size());
         
          //System.out.println("ha howa sala allocate:"+trg.size());
           return trg;  
              
      }
        public TourGroup solve_insertion_dynamic() {
        System.out.println("******SEQ*******");
        TourGroup solution = new TourGroup(problemD);
        int nbCustomersUsed = 0;
        int nbCustomers = this.problemD.getCustomers_dynamic().size();
        Random rnd = new Random();
        ArrayList<Customer> clients= new ArrayList<Customer>();
        for (Customer c: this.problemD.getCustomers_dynamic() )
        {
            clients.add(c);
        }
        //Customer customer = this.problemD.getCustomers_dynamic().get(rnd.nextInt(nbCustomers));
        
   //     System.out.println("client avant :"+solution.getCamionsToUse().get(0).getTournee_attribuees().get(0).getCustomers().get(0).getId());
       if (this.problemD.getDepots_fictif().size()>0)
       {
           
           
           for (int k=0;k<this.getProblemD().getDepots_fictif().size();k++)
           {
       
        Tour tour = new Tour(solution);
        tour.setId_fictif(k+1);
        solution.addTour(tour);
       // System.out.println("id fictif tour vient d'etre crée: "+tour.getId_fictif());
        // System.out.println("taille solution: "+solution.getTours().size());
       
              
           }
        // System.out.println("nbcustomersused au début: "+solution.nbcustomers_solution());
         //System.out.println("nbcustomers : "+nbCustomers);
          //System.out.println("nbcustomersUsed : "+nbCustomersUsed);
          while(nbCustomersUsed < nbCustomers) {
              Customer customer = clients.get(rnd.nextInt(nbCustomers-nbCustomersUsed));
               //System.out.println("id clienttouur random : "+customer.getId());
              
               if (solution.CheckCustomerInTourGroup(customer)){
               // System.out.println("id clienttouur à l'intérieur de la première boucle hadi if, continue : "+customer.getId());
              //System.out.println("if continue nbcustomersused : "+nbCustomersUsed);
                continue;
            }
               //System.out.println("dkhalna l else");
                  // System.out.println("id client sélectionner : "+customer.getId());
                  // System.out.println("nbcustomersused au début: "+solution.nbcustomers_solution());
                    
        
              int cs=0;
          for (int k=0;k<solution.getTours().size();k++)
               {
                  // System.out.println("id client sélectionner à l'intérieur de for : "+customer.getId());
                  // System.out.println("taille solution : "+solution.getTours().size());
                  
       if ((cs==0)&&(solution.getTours().get(k).getQuantity()+ customer.getDemande() <=solution.getTours().get(k).getcapacity())
                    && (problemD.getTemps_dynamic(solution.getTours().get(k), customer) <=solution.getTours().get(k).getTempsRestant()))
       {
        solution.getTours().get(k).addCustomer(customer);
        nbCustomersUsed++;
        clients.remove(customer);
        cs=1;
        //System.out.println("id client sélectionner fach sad9aaat if : "+customer.getId());
         //System.out.println("k lli sad9aat : "+k);
 
       }
        //System.out.println("k fach khrej men if: "+k);
      
               }
               if (cs==0)
               {
        Tour tour = new Tour(solution);
       tour.setId_fictif(0);
       solution.addTour(tour);
       tour.addCustomer(customer);
        clients.remove(customer);
       //System.out.println("ma sd9aatch if o derna tour jdida : ");
         //System.out.println("id client sélectionner fach massad9aaatch if : "+customer.getId());
         
       nbCustomersUsed++;
               }
               // System.out.println("nbcustomers used à la fin dyal while: "+ nbCustomersUsed);
                //System.out.println("nbcustomers  à la fin dyal while: "+ nbCustomers);
                       
               }
          
        //System.out.println("daba bgha ydir allocate nbcustomersssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss "+ nbCustomers); 
    // System.out.println("daba bgha ydir allocate nbcustomersuseddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd "+ nbCustomersUsed); 
     /*for (int j=0;j<solution.getTours().size();j++)
     {
         System.out.println("id fictif solution tourrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr: "+ solution.getTours().get(j).getId_fictif());   
              
     for(int i=0;i< solution.getTours().get(j).getCustomers().size();i++)
               {
                System.out.println("id client tourrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr: "+ solution.getTours().get(j).getCustomers().get(i).getId());   
               }
     }*/
           //verify_population(clients, solution);
        solution.allocateTours2_dynamic();
        System.out.println("fin allocate");   
             
         return solution;
       }
       else {
       Tour tour = new Tour(solution);
       tour.setId_fictif(0);
       solution.addTour(tour);
         Customer customer = clients.get(rnd.nextInt(nbCustomers-nbCustomersUsed));
        while (nbCustomersUsed <nbCustomers) {
           //  System.out.println("à l'intérieur de la boucle while 3");

            while (solution.CheckCustomerInTourGroup(customer)) {
                // System.out.println("à l'intérieur de la boucle while 4");
               customer = clients.get(rnd.nextInt(nbCustomers-nbCustomersUsed));
            }

            if (tour.getQuantity()+ customer.getDemande() > problemD.getCapacitycamion_dynamic()
                    || problemD.getTemps_dynamic(tour, customer) > problemD.getMaxTemps_dynamic()+this.problemD.getOvetime_dynamic()) {
                if (tour.size() > 0) {
                    //tour.setBestCamion();
                    //ystem.out.println("camion " + tour.getCamion());
                    tour = new Tour(solution);
                    tour.setId_fictif(0);
                    solution.addTour(tour);
                }
            }
            tour.addCustomer(customer);
            nbCustomersUsed++;
            clients.remove(customer);
           
        }
     
        solution.allocateTours2_dynamic();
        //solution.improve_dynamic(problemD.getNbImprove());
       
         return solution;
           
       }
    }
        public int nbcustomers_solution(){
            int i=0;
            for (Tour t : this.getTours())
            {
                i=i+t.getCustomers().size();
            }
            return i;
        }
     public void sort_dynamic(ArrayList<Tour> trs){
    
          boolean permut;
         Tour tampon1=null;
         Tour tampon2= null;
          do
          {
              permut= false;
              for (int i=0; i<trs.size()-1;i++)
              {
                if (trs.get(i).compareTo_dynamic(trs.get(i+1))>0)
                {
                    
                    tampon1=trs.get(i);
                    tampon2=trs.get(i+1);
                    trs.set(i, tampon2);
                    trs.set(i+1, tampon1);
                    permut=true;
                }   
              }
          }while (permut);
      }
     public void affecter_vehicule(){
        // System.out.println("au début taille gettours: "+this.getTours().size());
         //System.out.println("au début taille camions: "+this.getCamions().size());
         
       if (this.getTours().size()<= this.getCamions().size())
       {
          
           int c=0;
            while( c<this.getTours().size()) 
            {
               // System.out.println("haa c: "+c);
               // System.out.println("tour 9al men camion taille tournées attribuée camion c: "+this.getCamions().get(c).getTournee_attribuees().size());
         
                this.getCamions().get(c).getTournee_attribuees().clear();
                this.getCamions().get(c).getTournee_attribuees().add(this.getTours().get(c));
                //System.out.println("tour 9al men camion taille tournées attribuée camion c après ajout: "+this.getCamions().get(c).getTournee_attribuees().size());
         
                this.getTours().get(c).setC(this.getCamions().get(c));
               
               
                c++;
            }
             //for (int l=0; l<this.getTours()
            // System.out.println("tour 9al men camion taille tournées attribuée camion 0 après gettours clear: "+this.getCamions().get(0).getTournee_attribuees().size());
         // System.out.println("tour 9al men camion taille tournées attribuée camion 0 après gettours clear: "+this.getTours().size());
         
       }
       else {
           int i=0;
          
           classer_tournees_t(this.getTours());
          // classer_tournees(this.getTours());
            
          while(i<this.getCamions().size())
        
               { 
                //System.out.println("haa i: "+i);
                //System.out.println("tours ktar men camions taille tournées attribuée camion i: "+this.getCamions().get(i).getTournee_attribuees().size());
         
                 this.getCamions().get(i).getTournee_attribuees().clear();
                // System.out.println("tournees attribuees camions avant ajout: "+ this.getCamions().get(i).tournee_attribuees.size());
                 this.getCamions().get(i).getTournee_attribuees().add(this.getTours().get(0));
                // System.out.println("tournees attribuees camions après ajout: "+ this.getCamions().get(i).tournee_attribuees.size());
                
                 this.getTours().get(0).setC(this.getCamions().get(i));
                
                this.getTours().remove(0);
               // System.out.println("tournees attribuees camions après supression: "+ this.getCamions().get(i).tournee_attribuees.size());
                
               // this.camions.add(camion);
               
       
               
                i++; 
               }
           
           while (this.getTours().isEmpty()==false)
           {
               classer_tournees_t(this.getTours());
               classer_camion(this.getCamions());
               this.getCamions().get(0).getTournee_attribuees().add(this.getTours().get(0));
               this.getTours().get(0).setC(this.getCamions().get(0));
               this.getTours().remove(0);
               //i++;
           }
           classer_camion(this.getCamions());
       
        //System.out.println("tournees attribuees camions avant clear: "+ this.getCamions().get(0).tournee_attribuees.size());
                
       this.getTours().clear();
        //System.out.println("tournees attribuees camions après clear: "+ this.getCamions().get(0).tournee_attribuees.size());
      
        for (int l=0; l<this.getCamions().size(); l++)
        {
             classer_tournees(this.getCamions().get(l).getTournee_attribuees());
            //System.out.println("id_fictif camion:  "+ this.getCamions().get(l).getId_fictif_final()); 
              for (int j=0; j<this.getCamions().get(l).getTournee_attribuees().size(); j++)
              {
                  //Tour t=this.getCamions().get(i).getTournee_attribuees().get(j).clone();
                  this.getTours().add(this.getCamions().get(l).getTournee_attribuees().get(j));
                //System.out.println("id_fictif tournée ajouté à this.gettours:  "+ this.getCamions().get(l).getTournee_attribuees().get(j).getId_fictif());   
              }
              //System.out.println("tournees attribuees camions après re ajout: "+ this.getCamions().get(l).tournee_attribuees.size());
      
        }
          
       }
       
         //System.out.println("gettours après re ajout: "+ this.getTours().size());
      
         
       //for (int k=0; k<this.getCamions.)
       
   }
          public Camion getcamionById_dynamic(int id) {
        int j=-1;
        //System.out.println("mmmmmmmmmmmmmmmmm : "+ this.depots_fictif.size() );
            for (int i=0;i<this.camions.size();i++)
            {
                //System.out.println("mmmmmmmmmmmmmmmmm 222: "+ this.depots_fictif.get(i).getId_depot_fictif() );
                if (id==this.camions.get(i).getId_fictif_final())
                {
                    j=i;
                   
                }
                    
                    
            }
           // System.out.println("haaa : "+ this.depots_fictif.size());
        return this.camions.get(j);
    }
    public void affecter_vehicule_dynamic(){
         //System.out.println("taille getTours au debut : "+ this.getTours().size());
        for (int i=0; i<this.getCamions().size();i++)
        {
            this.getCamions().get(i).getTournee_attribuees().clear();
        }
            for (int j=0;j<this.getTours().size();j++)
                  {
                      if (this.getTours().get(j).getId_fictif()!=0)
                       {
                  Depotfictif d=this.getProblemD().getdepotfictifById_dynamic(this.getTours().get(j).getId_fictif());
               int id_cl_fict=d.getId_client_fictif();
               Camion cm=this.getcamionById_dynamic(id_cl_fict);
              
                
             
                // System.out.println("tail tournées attribuées men weset hadik la bouuuuuuuuuuuuuuuuuucle: "+ this.getCamions().get(i).getTournee_attribuees().size());
               cm.tournee_attribuees.add(this.getTours().get(j));
                  this.getTours().get(j).setC(cm);
                  // System.out.println("tail tournées attribuées men weset hadik la bouuuuuuuuuuuuuuuuuucle after: "+ this.getCamions().get(i).getTournee_attribuees().size());
                
                  this.getTours().remove(this.getTours().get(j));
                 j=j-1;
                
                      }
                     
                     
            }
            
         
              // System.out.println("taille getTours au milieu : "+ this.getTours().size());
            
       
        while (this.getTours().isEmpty()==false)
           {
             // System.out.println("taille getTours àl'intérieur de while : "+ this.getTours().size());
            
               classer_tournees_dynamic(this.getTours());
               classer_camion_dynamic(this.getCamions());
             /*System.out.println("tail tournées attribuées: "+ this.getCamions().get(0).getTournee_attribuees().size());
              System.out.println("tail tournées EFFECTU2ES: "+ this.getTours().size());
               System.out.println("tail camions: "+ this.getCamions().size());
              
               */ 
               this.getCamions().get(0).tournee_attribuees.add(this.getTours().get(0));
               this.getTours().get(0).setC(this.getCamions().get(0));
               this.getTours().remove(this.getTours().get(0));
           }
        //this.getTours().clear();
        classer_camion_dynamic(this.getCamions());
         this.getTours().clear();
       for (int i=0; i<this.getCamions().size(); i++)
        {
           // System.out.println("id_fictif camion:  "+ this.getCamions().get(i).getId_fictif_final());
             classer_tournees(this.getCamions().get(i).getTournee_attribuees());
              for (int j=0; j<this.getCamions().get(i).getTournee_attribuees().size(); j++)
              {
                  Tour t=this.getCamions().get(i).getTournee_attribuees().get(j).clone_dynamic();
                  this.getTours().add(t);
                // System.out.println("id_fictif tournée:  "+ this.getCamions().get(i).getTournee_attribuees().get(j).getId_fictif());   
              }
        }
          
       
          
       }
      public void allocate3_dynamic(){
         System.out.println("taille getTours au debut : "+ this.getTours().size());
        for (int i=0; i<this.getCamions().size();i++)
        {
            this.getCamions().get(i).getTournee_attribuees().clear();
        }
            for (int j=0;j<this.getTours().size();j++)
                  {
                      System.out.println("haa id tour  :"+this.getTours().get(j).getId_fictif());
                      if (this.getTours().get(j).getId_fictif()!=0)
                       {
                  Depotfictif d=this.getProblemD().getdepotfictifById_dynamic(this.getTours().get(j).getId_fictif());
               int id_cl_fict=d.getId_client_fictif();
               Camion cm=this.getcamionById_dynamic(id_cl_fict);
              
                
             
         //System.out.println("tail tournées attribuées men weset hadik la bouuuuuuuuuuuuuuuuuucle: "+ this.getCamions().get(i).getTournee_attribuees().size());
               cm.tournee_attribuees.add(this.getTours().get(j));
                  this.getTours().get(j).setC(cm);
                  // System.out.println("tail tournées attribuées men weset hadik la bouuuuuuuuuuuuuuuuuucle after: "+ this.getCamions().get(i).getTournee_attribuees().size());
                
                  this.getTours().remove(this.getTours().get(j));
                 j=j-1;
                
                      }
                     
                     
            }
            
         
               System.out.println("taille getTours au milieu : "+ this.getTours().size());
            
       
        while (this.getTours().isEmpty()==false)
           {
              System.out.println("taille getTours àl'intérieur de while : "+ this.getTours().size());
            
               classer_tournees_dynamic(this.getTours());
               classer_camion_dynamic(this.getCamions());
             System.out.println("tail tournées attribuées: "+ this.getCamions().get(0).getTournee_attribuees().size());
              System.out.println("tail tournées EFFECTU2ES: "+ this.getTours().size());
               System.out.println("tail camions: "+ this.getCamions().size());
                
         System.out.println("id fictif tour: "+ this.getTours().get(0).getId_fictif());   
              
     for(int i=0;i< this.getTours().get(0).getCustomers().size();i++)
               {
                System.out.println("id client: "+ this.getTours().get(0).getCustomers().get(i).getId());   
               }
     
              
               
               this.getCamions().get(0).tournee_attribuees.add(this.getTours().get(0));
               this.getTours().get(0).setC(this.getCamions().get(0));
               this.getTours().remove(this.getTours().get(0));
           }
        System.out.println("taille getTours à la fin : "+ this.getTours().size());
            
        //this.getTours().clear();
        classer_camion_dynamic(this.getCamions());
        System.out.println("taille getTours après classer camion : "+ this.getTours().size());
        
         this.getTours().clear();
          System.out.println("taille getTours après clear gettours : "+ this.getTours().size());
       
       for (int i=0; i<this.getCamions().size(); i++)
        {
           System.out.println("id_fictif camion à l'intérieur de la boucle for1:  "+ this.getCamions().get(i).getId_fictif_final());
             classer_tournees(this.getCamions().get(i).getTournee_attribuees());
              System.out.println("id_fictif camion à l'intérieur de la boucle for1 après classer tournées:  "+ this.getCamions().get(i).getId_fictif_final());
          
              for (int j=0; j<this.getCamions().get(i).getTournee_attribuees().size(); j++)
              {
                  System.out.println("id_fictif camion à l'intérieur de la boucle for2 :  "+ this.getCamions().get(i).getId_fictif_final());
          
                  Tour t=this.getCamions().get(i).getTournee_attribuees().get(j).clone_dynamic();
                  System.out.println("id_fictif camion à l'intérieur de la boucle for2 après clone dynamic :  "+ this.getCamions().get(i).getId_fictif_final());
          
                  this.getTours().add(t);
                System.out.println("id_fictif tournée after add:  "+ this.getCamions().get(i).getTournee_attribuees().get(j).getId_fictif());   
              }
              System.out.println("khrej men for 2 ");   
              
        }
          
       
         System.out.println("khrej men for 1 ");   
               
       }
    public void classer_tournees_t(ArrayList<Tour> Tours){
         boolean permut;
         Tour tampon1=null;
         Tour tampon2=null;
         do {
             permut= false;
             for (int i=0; i<Tours.size()-1;i++)
             {
                 if (Tours.get(i).getTemps()<Tours.get(i+1).getTemps())
                 {
                     tampon1=Tours.get(i);
                      tampon2=Tours.get(i+1);
                     Tours.set(i,tampon2);
                     Tours.set(i+1, tampon1);
                     permut=true;
                 }
                 
             }
         } while (permut);
         
         
     }
   public void classer_tournees_dynamic(ArrayList<Tour> Tours){
         boolean permut;
         Tour tampon1=null;
         Tour tampon2=null;
         do {
             permut= false;
             for (int i=0; i<Tours.size()-1;i++)
             {
                 if (Tours.get(i).getTemps_dynamic()<Tours.get(i+1).getTemps_dynamic())
                 {
                     tampon1=Tours.get(i);
                      tampon2=Tours.get(i+1);
                     Tours.set(i,tampon2);
                     Tours.set(i+1, tampon1);
                     permut=true;
                 }
                 
             }
         } while (permut);
         
         
     }
 
    public void classer_camion_dynamic(ArrayList<Camion> camions_a_trier){
          boolean permut;
          Camion tampon1=null;
          Camion tampon2= null;
          do
          {
              permut= false;
              for (int i=0; i<camions_a_trier.size()-1;i++)
              {
                if ((camions_a_trier.get(i).calucler_temps_total_tournees_dynamic()+camions_a_trier.get(i).getTemps_tournees_avant())>(camions_a_trier.get(i+1).calucler_temps_total_tournees_dynamic()+camions_a_trier.get(i+1).getTemps_tournees_avant()))
                {
                    tampon1=camions_a_trier.get(i);
                    tampon2=camions_a_trier.get(i+1);
                    camions_a_trier.set(i, tampon2);
                    camions_a_trier.set(i+1, tampon1);
                    permut=true;
                }   
              }
          }while (permut);
      }
    
}
