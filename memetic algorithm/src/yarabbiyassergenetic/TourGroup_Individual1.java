package yarabbiyassergenetic;

import java.util.ArrayList;
import java.util.Random;
/*
 * Un tourgoup est un ensemble de tours élémentaires permétant de visiter tous les clients (elementaryTours).
 * 
 */

public final class TourGroup_Individual1 implements Comparable<TourGroup_Individual1> {

    ArrayList<Trip> trips = new ArrayList<Trip>();
    private VRPS problem;
    private VRPD problemD;
    //private ArrayList<Camion> camionsToUse = new ArrayList<Camion>();

    public TourGroup_Individual1(VRPS problem) {
        trips = new ArrayList<Trip>();
        this.problem = problem;
//        initialiseCamionsToUse();
    }
    
    public TourGroup_Individual1(TourGroup tourGroup) {
 
       //super();
       trips = new ArrayList<Trip>();
       this.problem = tourGroup.getProblem();
       //for(Camion c:tourGroup.getCamionsUsed()){
       Trip trip1 = new Trip(this);
        for(Tour t:tourGroup.getTours())
            if(t.getC()==null){
                trip1.addTour(t);
            }
        if(trip1.size()>0)
            trips.add(trip1);
       
        for(Camion c:problem.getCamions()){
            Trip trip = new Trip(this);
            trip.camion = c;
            for(Tour t:tourGroup.getTours())
                if(t.getC()==c){
                    trip.addTour(t);
                }
            trips.add(trip);
        }
         
       
    }
 public TourGroup_Individual1(VRPD problemD) {
        trips = new ArrayList<Trip>();
        this.problemD = problemD;
//        initialiseCamionsToUse();
    }
    
    public TourGroup_Individual1(TourGroup tourGroup, VRPD problemD) {
 
       //super();
       trips = new ArrayList<Trip>();
       this.problemD = tourGroup.getProblemD();
       //for(Camion c:tourGroup.getCamionsUsed()){
       Trip trip1 = new Trip(this);
        for(Tour t:tourGroup.getTours())
            if(t.getC()==null){
                trip1.addTour(t);
            }
        if(trip1.size()>0)
            trips.add(trip1);
       
        for(Camion c:problemD.getCamions_dynamic()){
            Trip trip = new Trip(this);
            trip.camion = c;
            for(Tour t:tourGroup.getTours())
                if(t.getC()==c){
                    trip.addTour(t);
                }
            trips.add(trip);
        }
         
       
    }
    public VRPD getProblemD() {
        return problemD;
    }

    public void setProblemD(VRPD problemD) {
        this.problemD = problemD;
    }

    public VRPS getProblem() {
        return problem;
    }

    public int size() {
        return trips.size();
    }

    //*******Tours*************
    public ArrayList<Trip> getTrips() {
        return trips;
    }
    
    public boolean CheckCustomerInTourGroup_Individual1(Customer customer) {
        for (Trip trip : this.getTrips()) {
            if (trip.CheckCustomerInTrip(customer) == true) {
                return true;
            }
        }
        return false;
    }

    public int getTripOfCustomerInTourGroup_Individual1(Customer customer) {
        for (int i = 0; i < this.size(); i++) {
            if (this.getTrips().get(i).CheckCustomerInTrip(customer) == true) {
                return i;
            }
        }
        return -1;
    }

    public Customer nearestnonVisitedCustomer(Customer customer) {
        double bestDistance = 1000000000;
        Customer other = null;
        for (Customer c : problem.getCustomers()) {
            if (c == customer || CheckCustomerInTourGroup_Individual1(c)) {
                continue;
            }
            if (bestDistance >= problem.getDistances(customer.getId(), c.getId())) {
                bestDistance = problem.getDistances(customer.getId(), c.getId());
                other = c;
            }
        }
        //System.out.print("***"+other);
        return other;
    }
    
    /*public Customer nearestnonVisitedCustomerForTour(Customer customer, Tour tour) {
        double bestDistance = 1000000000;
        Customer other = null;
        Tour tmpTour = new Tour(tour, this);
        for (Customer c : problem.getCustomers()) {
            if (c == customer || CheckCustomerInTourGroup_Individual1(c)) {
                continue;
            }
            tmpTour.addCustomer(c);
            if (bestDistance >= problem.getDistance(customer.getId(), c.getId())
                    && CheckCamionforTour(tmpTour)){ 
                bestDistance = problem.getDistance(customer.getId(), c.getId());
                other = c;
            }
            tmpTour.removeCustomer(c);
        }
        //System.out.print("***"+other);
        return other;
    }*/

    public Customer nearestnonVisitedCustomerToDepot() {
        double bestDistance = 1000000000;
        Customer other = null;
        for (Customer c : problem.getCustomers()) {
            if (CheckCustomerInTourGroup_Individual1(c)) {
                continue;
            }
            if (bestDistance >= problem.getDistances(0, c.getId())) {
                bestDistance = problem.getDistances(0, c.getId());
                other = c;
            }
        }
        //System.out.print("***"+other);
        return other;
    }

    //*************Camions To Use*****************
    /*public ArrayList<Camion> getCamionsToUse() {
        return camionsToUse;
    }*/

   /* public void initialiseCamionsToUse() {
        //*****************A revoir
        /*for(Camion c: problem.getCamions()){
         c.reset();
         }*/
     /*   camionsToUse = new ArrayList<Camion>();
        for (int i = 1; i <= problem.getCapacityCamion().length; i++) {
            //System.out.println("i="+i);
            Camion c = FreeCamion(problem.getCapaciteByType(i));
            if (c != null) {
                camionsToUse.add(c);
            }
        }

    }*/

   /* public void updateCamionsToUse(int capacite) {
        for (Camion c : this.getProblem().getCamions())// on cherche s'il y a deja un camion free de cette capcaité dans camionsToUse
        {
            if (c.capacity == capacite && this.isFreeCamion(c)) {
                return;
            }
        }
        //sinon on l'ajouter
        Camion c = FreeCamion(capacite);
        if (c != null) {
            camionsToUse.add(c);
        }
    }*/

    /*public boolean checkFreeCapacityInCamionsToUse(int capacite) {
        for (Camion c : camionsToUse) {
            if (c.capacity == capacite && isFreeCamion(c)) {
                return true;
            }
        }
        return false;
    }*/

    /*public void removeCamionFromCamionsToUse(Camion camion) {
        if (camion == null) {
            return;
        }
        if (this.checkCamionInTourGroup_Individual1(camion) == false) {// on l'enleve sil n'est utilisé par aucune tournée
            this.getCamionsToUse().remove(camion);
        }
    }*/

    //************Camions*************************
    public void removeAllCamionsFromTourGroup_Individual1() {
        for (Trip t : this.getTrips()) {
            t.removeCamion();
        }
    }

    /*public Camion FreeCamion(int capacite) {
        for (Camion c : problem.getCamions()) {
            if (c.capacity == capacite
                    && this.checkCamionInTourGroup_Individual1(c) == false) {
                return c;
            }
        }
        return null;
    }*/

    public Camion FreeCamion() {
        for (Camion c : problem.getCamions()) {
            if (this.checkCamionInTourGroup_Individual1(c) == false) {
                return c;
            }
        }
        return null;
    }

    public boolean isFreeCamion(Camion camion) {
        if (checkCamionInTourGroup_Individual1(camion)) {
            //System.out.println(camion+" is used");
            return false;
        } else {
            //System.out.println(camion+" is free");
            return true;
        }
    }

    public Camion selectCamion() {
        Random rnd = new Random();
        int r = rnd.nextInt(this.getProblem().getCamions().size());
        if (this.checkFreeCamion()) {
            while (checkCamionInTourGroup_Individual1(this.getProblem().getCamions().get(r)) == true) {
                r = rnd.nextInt(this.getProblem().getCamions().size());
            }
        }
        return this.getProblem().getCamions().get(r);
    }

    public boolean checkFreeCamion() {
        for (Camion c : problem.getCamions()) {
            if (this.checkCamionInTourGroup_Individual1(c) == false) {
                return true;
            }
        }
        return false;
    }

    public boolean CheckCamionForTimeAndQuantity(double time, int quantity) {

        for (Camion c : this.getProblem().getCamions()) {
            if (c.capacity >= quantity
                    && getTemps(c) + time <= problem.getMaxTemps()) {
                return true;
            }
        }
        return false;
    }

    public boolean CheckCamionforTour(Tour tour) {
        double timeTour = tour.getTemps();
        //System.out.println("camionsToUse "+camionsToUse);
        for (Camion c : this.getProblem().getCamions()) {
            //System.out.println("camion "+c);
            double diff = c.capacity - tour.getQuantity();
            double newTime = getTemps(c) + timeTour;
            //System.out.println("diff "+diff+">=0, newTime "+newTime+" <= "+this.getMaxTemps());
            if (diff >= 0
                    && (newTime <= problem.getMaxTemps())) {
                return true;
            }
        }
        return false;
    }

    public boolean checkCamionInTourGroup_Individual1(Camion camion) {
        for (Trip t : this.getTrips()) {
            if (t.getCamion() == camion) {
                //System.out.println(camion+" is used for "+t);
                return true;
            }
        }
        return false;
    }

    public int NbToursOfCamion(Camion camion) {
        int count = 0;
        for (Trip t : this.getTrips()) {
            if (t.getCamion() == camion) {
                count++;
            }
        }
        return count;
    }

    public Camion camionNotRespectingTimeConstraint() {
        for (Camion c : this.getCamionsUsed()) {
            if (getTemps(c) > problem.getMaxTemps()) {
                return c;
            }
        }
        return null;
    }

    public Trip FindTimeTourConstraint() {
        for (Trip t : this.getTrips()) {
            if (t.getTemps() > problem.getMaxTemps()) {
                return t;
            }
        }
        return null;
    }

    public boolean CheckCamionforTours(Tour firstTour, Customer customer) {
        double totalTime = problem.getTemps(firstTour, customer);
        double totalQuantity = firstTour.getQuantity() + customer.getDemande();
        //
        if (totalQuantity > problem.getMaxCapacity() || totalTime > problem.getMaxTemps()) {
            //System.out.println("total time " + totalTime + ", total quantity " + totalQuantity);
            return false;
        }
        Camion firstCamion = null;
        if (firstTour.getC() != null) {
            firstCamion = firstTour.getC();
            if (firstCamion.capacity >= totalQuantity
                    && this.getTemps(firstCamion) - firstTour.getTemps() + totalTime <= problem.getMaxTemps()) {
                //System.out.println("first camion "+firstCamion+" temps "+totalTime);
                return true;
            }
        }
        for (Camion c : this.getProblem().getCamions()) {
            if (c != firstCamion) {
                double diff = c.getCapacity() - totalQuantity;
                if (diff >= 0
                        && this.getTemps(c) + totalTime <= problem.getMaxTemps()) {
                    //System.out.println("camion "+c+" temps "+totalTime);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean CheckCamionforTours(Camion camion, Tour firstTour, Tour secondTour) {

        double totalTime = problem.getTemps(firstTour, secondTour);
        double totalQuantity = firstTour.getQuantity() + secondTour.getQuantity();
        //System.out.println("total time "+totalTime);
        if (totalQuantity > problem.getMaxCapacity() || totalTime > problem.getMaxTemps()) {
            return false;
        }
        if (camion == firstTour.getC()) {
            if (camion.getCapacity() >= totalQuantity
                    && this.getTemps(camion) - firstTour.getTemps() + totalTime <= problem.getMaxTemps()) {
                return true;
            }
        }
        if (camion == secondTour.getC()) {
            if (camion.getCapacity() >= totalQuantity
                    && this.getTemps(camion) - secondTour.getTemps() + totalTime <= problem.getMaxTemps()) {
                return true;
            }
        }
        if (camion.getCapacity() - totalQuantity >= 0
                && this.getTemps(camion) + totalTime <= problem.getMaxTemps()) {
            return true;
        }
        return false;
    }

    public boolean CheckCamionforTours(Tour firstTour, Tour secondTour) {

        double totalTime = problem.getTemps(firstTour, secondTour);
        double totalQuantity = firstTour.getQuantity() + secondTour.getQuantity();
        //System.out.println("total time "+totalTime);
        if (totalQuantity > problem.getMaxCapacity()
                || totalTime > problem.getMaxTemps()) {
            return false;
        }
        Camion firstCamion = null;
        Camion secondCamion = null;
        if (firstTour.getC() != null) {
            firstCamion = firstTour.getC();
            if (firstCamion.getCapacity() >= totalQuantity
                    && this.getTemps(firstCamion) - firstTour.getTemps() + totalTime
                    <= problem.getMaxTemps()) {
                //System.out.println("first camion "+firstCamion+" temps "+totalTime);
                return true;
            }
        }
        if (secondTour.getC() != null) {
            secondCamion = secondTour.getC();
            if (secondCamion.getCapacity() >= totalQuantity
                    && this.getTemps(secondCamion) - secondTour.getTemps() + totalTime <= problem.getMaxTemps()) {
                //System.out.println("second camion "+secondCamion+" temps "+totalTime);
                return true;
            }
        }
        for (Camion c : this.getProblem().getCamions()) {
            if (c != firstCamion && c != secondCamion) {
                double diff = c.getCapacity() - totalQuantity;
                if (diff >= 0
                        && this.getTemps(c) + totalTime <= problem.getMaxTemps()) {
                    //System.out.println("camion "+c+" temps "+totalTime);
                    return true;
                }
            }
        }
        return false;
    }

    
    //************Moves*****************************
    /*public double checkMoveCustomerFromTourToTour(Tour tour1, Tour tour2, int positionTour1, int positionTour2) {
        //********Insertion du client dans position1 de tour1 dans la tour 2 à la position2
        double LTR1 = this.getLTR();
        
        //créer un nouveau tourGroup
        TourGroup_Individual1 newTrGp = new TourGroup_Individual1(problem);
        for(Tour t: this.getTours())
            if(t!=tour1 && t!=tour2)
                newTrGp.addTourToCopy(t);
        
        Camion camion1 = tour1.getCamion();
        Camion camion2 = tour2.getCamion();
        tour1.removeCamion();
        tour2.removeCamion();

        Customer c1 = tour1.getCustomer(positionTour1);
        int newQuantity1 = tour1.getQuantity() //quantit� distribu�e lors de la tourn�e
                - c1.getDemande();  //quantit� demand�e du client jPrime de la tourn�e j
        // => nouvelle quantit� de la tourn�e i
        int newQuantity2 = tour2.getQuantity()
                + c1.getDemande();

        double time1 = problem.getTempsCustomerToRemove(tour1, c1, positionTour1);
        double time2 = problem.getTempsCustomerToAdd(tour2, c1, positionTour2);

        //System.out.println("********deb**********");
        //System.out.println("tour1 "+tour1);
        //System.out.println("tour2 "+tour2);

        // v�rifier si l'�change pr�serve les contraintes du probl�me

        if (!CheckCamionForTimeAndQuantity(time1, newQuantity1)
                || !CheckCamionForTimeAndQuantity(time2, newQuantity2)) {
            tour1.setCamion(camion1);
            tour2.setCamion(camion2);
            return -1;
        }
        Tour newTour1 = new Tour(this);//tour without positionTour1
        for (int a = 0; a < tour1.size(); a++) {
            if (a != positionTour1) {
                newTour1.addCustomer(tour1.getCustomer(a));
            }
        }

        Tour newTour2 = new Tour(this);
        for (int a = 0; a < tour2.size(); a++) {
            if (a == positionTour2) {
                newTour2.addCustomer(c1);
            }
            newTour2.addCustomer(tour2.getCustomer(a));
        }
        newTour1.setBestCamion();
        if (newTour1.getCamion() == null) {
            tour1.setCamion(camion1);
            tour2.setCamion(camion2);
            return -1;
        }
        newTour2.setBestCamion();
        if (newTour2.getCamion() == null) {
            tour1.setCamion(camion1);
            tour2.setCamion(camion2);
            return -1;
        }

        double newRate1 = newTour1.getLoadRate();
        double newRate2 = newTour2.getLoadRate();
        
        newTrGp.add(newTour1);
        newTrGp.add(newTour2);
        double diff = LTR1 - newTrGp.getLTR();
        if (diff >= 0
                && (tour1.getLoadRate() + tour2.getLoadRate() <= newRate1 + newRate2 // il suffit que les nouvelles tournées soient mieux en terme de taux et non pas les meilleures
                || (newRate1 <= problem.getMaxEmptyRate()
                && newRate2 <= problem.getMaxEmptyRate()))// soit ils sont bien remplis soit ils sont meilleure que l'existant
                ) {
            return diff;
        }

        tour1.setCamion(camion1);
        tour2.setCamion(camion2);
        newTour1.init();
        newTour2.init();

        return -1;
    }*/

    /*public double checkExchangeCustomers(Tour tour1, Tour tour2, int positionTour1, int positionTour2) {
        //********Insertion du client dans position1 de tour1 dans la tour 2 à la position2
        double LTR1 = this.getLTR();
        //créer un nouveau tourGroup
        TourGroup_Individual1 newTrGp = new TourGroup_Individual1(problem);
        for(Tour t: this.getTours())
            if(t!=tour1 && t!=tour2)
                newTrGp.addTourToCopy(t);
        
        Camion camion1 = tour1.getCamion();
        Camion camion2 = tour2.getCamion();
        tour1.removeCamion();
        tour2.removeCamion();

        Customer c1 = tour1.getCustomer(positionTour1);
        Customer c2 = tour2.getCustomer(positionTour2);
        int newQuantity1 = tour1.getQuantity() //quantit� distribu�e lors de la tourn�e
                - c1.getDemande()
                + c2.getDemande();  //quantit� demand�e du client jPrime de la tourn�e j
        // => nouvelle quantit� de la tourn�e i
        int newQuantity2 = tour2.getQuantity()
                + c1.getDemande()
                - c2.getDemande();

        // v�rifier si l'�change pr�serve les contraintes du probl�me

        if (newQuantity1 > problem.getMaxTemps()
                || newQuantity2 > problem.getMaxTemps()) {
            return -1;
        }
        Tour newTour1 = new Tour(this);//tour without positionTour1
        for (Customer c : tour1.getCustomers()) {
            if (c != c1) {
                newTour1.addCustomer(c);
            }
        }
        if(newTour1.addCustomerInBestPosition(c2)==false)
            return -1;

        Tour newTour2 = new Tour(this);
        for (Customer c : tour2.getCustomers()) {
            if (c != c2) {
                newTour2.addCustomer(c);
            }
        }
        if(newTour2.addCustomerInBestPosition(c1)==false)
            return -1;

        newTour1.setBestCamion();
        if (newTour1.getCamion() == null) {
            return -1;
        }
        newTour2.setBestCamion();
        if (newTour2.getCamion() == null) {
            return -1;
        }

        double newRate1 = newTour1.getLoadRate();
        double newRate2 = newTour2.getLoadRate();
        newTrGp.add(newTour1);
        newTrGp.add(newTour2);
        double diff = LTR1 - newTrGp.getLTR();
        
        if (diff >= 0
                && (tour1.getLoadRate() + tour2.getLoadRate() <= newRate1 + newRate2 // il suffit que les nouvelles tournées soient mieux en terme de taux et non pas les meilleures
                || (newRate1 <= problem.getMaxEmptyRate()
                && newRate2 <= problem.getMaxEmptyRate()))// soit ils sont bien remplis soit ils sont meilleure que l'existant
                ) {
            return diff;
        }

        tour1.setCamion(camion1);
        tour2.setCamion(camion2);
        newTour1.init();
        newTour2.init();

        return -1;
    }*/

    /*public ArrayList<Tour> tourGroupReversed() {
        ArrayList<Tour> tourGroup = new ArrayList<Tour>();
        for (Tour t : trips) {
            Camion c = this.selectCamion();
            while (this.checkCamionInTourGroup_Individual1(c) || problem.getCapacite(c) < t.getQuantity()) {
                c = this.selectCamion();
            }
            Tour tour = t.reverseTour();
            tour.setCamion(this.selectCamion());
            tourGroup.add(tour);
        }
        return tourGroup;
    }*/

    /*public boolean mutate() {
        //echande de deux clients
        Random rnd = new Random();
        int nbTours = this.size();
        if (nbTours < 2) {
            //System.out.println("Not enought tours to mutate.");  
            return false;
        }
        // choix des indices de Tour
        int i, j;
        i = j = rnd.nextInt(nbTours);
        while (i == j) {
            j = rnd.nextInt(nbTours);
            //System.out.println("Oups, i is still equal to j:" + i + ", nbTours = " + nbTours);
        }
        // choix des indices de client dans les deux tours choisis
        Tour iTour = this.get(i);
        Tour jTour = this.get(j);
        Camion iCamion = iTour.getCamion();
        Camion jCamion = jTour.getCamion();
        iTour.removeCamion();
        jTour.removeCamion();
        int iPrime, jPrime;
        int iSize = iTour.size();//taille de la tourn�e i
        int jSize = jTour.size();

        if (iSize < 2 || jSize < 2) {
            return false;
        }

        iPrime = rnd.nextInt(iSize);
        jPrime = rnd.nextInt(jSize);
        Customer iCustomer = iTour.get(iPrime);
        Customer jCustomer = jTour.get(jPrime);
        while (iCustomer == jCustomer) {
            iPrime = rnd.nextInt(iSize);
            jPrime = rnd.nextInt(jSize);
            iCustomer = iTour.get(iPrime);
            jCustomer = jTour.get(jPrime);
        }
        //System.out.println("Mutation: (i, iPrime, j, jPrime) = " + i + iPrime + j + jPrime);
        // v�rifier si l'�change pr�serve les contraintes du probl�me
        int newIQuantity = iTour.getQuantity() - //quantit� distribu�e lors de la tourn�e
                iCustomer.getDemande() + //quantit� demand�e du client iPrime de la tourn�e i
                jCustomer.getDemande();  //quantit� demand�e du client jPrime de la tourn�e j
        // => nouvelle quantit� de la tourn�e i
        int newJQuantity = jTour.getQuantity()
                - jCustomer.getDemande()
                + iCustomer.getDemande();

        double timeI = problem.getTemps(iTour, jCustomer, iCustomer);
        double timeJ = problem.getTemps(jTour, iCustomer, jCustomer);

        if (!this.CheckCamionForTimeAndQuantity(timeI, newIQuantity)
                || !this.CheckCamionForTimeAndQuantity(timeJ, newJQuantity)) {
            iTour.setCamion(iCamion);
            jTour.setCamion(jCamion);
            return false;
        }
        // �changer les deux magasins
        iTour.removeCustomer(iCustomer);
        jTour.removeCustomer(jCustomer);
        iTour.addCustomer(iPrime, jCustomer);
        jTour.addCustomer(jPrime, iCustomer);
        iTour.setBestCamion();
        jTour.setBestCamion();
        //System.out.println("***mutation done ");
        //System.out.println("iTour "+iTour);
        //System.out.println("jTour "+jTour);

        //System.out.println("***mutation done ");
        return true;
    }*/

    /*public boolean two_opt() {
        int nbTours = this.size();
        if (nbTours < 2) {
            //System.out.println("Not enought tours to mutate.");
            return false;
        }
        // Echange entre les tours deux à deux
        for (int i = 0; i < this.size(); i++) {
            for (int j = i + 1; j < this.size(); j++) {
                Tour tour1 = this.get(i);
                Tour tour2 = this.get(j);
                bestTwoOpt(tour1, tour2, 3);
            }
        }
        for (int i = 0; i < this.size(); i++) {
            Tour tour = this.get(i);
            if (tour.size() == 0) {
                this.remove(i);
            }
        }
        return true;
    }
*/
    /*public int bestTwoOpt(Tour tour1, Tour tour2, int iteration) {
        int count = 0;
        for (int k = 0; k < iteration; k++) {
            if (bestTwoOpt(tour1, tour2)) {
                count++;
            }
        }
        return count;
    }*/

    /*public boolean bestTwoOpt(Tour tour1, Tour tour2) {
        ArrayList<Exchange> bestExchanges = new ArrayList<Exchange>();
        int best1 = -1;
        int best2 = -1;
        int typeOfChange = 0;
        double maxDiff = 0;
        double diff;
        for (int k = 0; k < tour1.size(); k++) {
            for (int m = k; m < tour2.size(); m++) {
                diff = checkExchangeCustomers(tour1, tour2, k, m);
                if (diff >= maxDiff) {
                    best1 = k;
                    best2 = m;
                    typeOfChange = 1;
                    maxDiff = diff;
                }

                diff = checkMoveCustomerFromTourToTour(tour1, tour2, k, m);
                if (diff >= maxDiff) {
                    best1 = k;
                    best2 = m;
                    typeOfChange = 2;
                    maxDiff = diff;
                }

                diff = checkMoveCustomerFromTourToTour(tour2, tour1, m, k);
                if (diff >= maxDiff) {
                    best1 = k;
                    best2 = m;
                    typeOfChange = 3;
                    maxDiff = diff;
                }
            }
        }
        if (best1 != -1 && best2 != -1) {
            Customer c1 = tour1.getCustomer(best1);
            Customer c2 = tour2.getCustomer(best2);
            //   System.out.println("********Avant");
            //    System.out.println("tour1 "+tour1);
            //    System.out.println("tour2 "+tour2);
            if (typeOfChange == 1) {
                // System.out.println("Permutation done 1");
                tour1.removeCustomer(c1);
                tour1.addCustomerInBestPosition(c2);
                tour2.removeCustomer(c2);
                tour2.addCustomerInBestPosition(c1);
            }
            if (typeOfChange == 2) {
                // System.out.println("Permutation done 2");
                tour1.removeCustomer(c1);
                tour2.addCustomer(best2, c1);
            }
            if (typeOfChange == 3) {
                // System.out.println("Permutation done 3");
                tour2.removeCustomer(c2);
                tour1.addCustomer(best1, c2);
            }
            tour1.setBestCamion();
            tour2.setBestCamion();
            //    System.out.println("Après");
            //     System.out.println("tour1 "+tour1);
            //     System.out.println("tour2 "+tour2);
            return true;
        }
        return false;
    }
*/
    /*public boolean bestTwoOpt_many(Tour tour1, Tour tour2, int iteration) {
        ArrayList<Exchange> bestExchanges = new ArrayList<Exchange>();
        //int best1 = -1;
        //int best2 = -1;
        //int typeOfChange = 0;
        //double maxDiff = 0;
        Camion camion1 = tour1.getCamion();
        tour1.removeCamion();//pour inclure son camion dans la recherche du meilleur camion
        Camion camion2 = tour2.getCamion();
        tour2.removeCamion();
        double diff;
        for (int k = 0; k < tour1.size(); k++) {
            for (int m = k; m < tour2.size(); m++) {
                diff = checkExchangeCustomers(tour1, tour2, k, m);
                if (diff > 0) {
                    insertExchangeInList(new Exchange(k, m, 1, diff), bestExchanges, iteration);
                }
                diff = checkMoveCustomerFromTourToTour(tour1, tour2, k, m);
                if (diff > 0) {
                    insertExchangeInList(new Exchange(k, m, 2, diff), bestExchanges, iteration);
                }
                diff = checkMoveCustomerFromTourToTour(tour2, tour1, m, k);
                if (diff > 0) {
                    insertExchangeInList(new Exchange(k, m, 3, diff), bestExchanges, iteration);
                }
            }
        }
        if (bestExchanges.size() > 0) {
            for (Exchange exchange : bestExchanges) {
                Customer c1 = tour1.getCustomer(exchange.bestTour1);
                Customer c2 = tour2.getCustomer(exchange.bestTour2);
                //   System.out.println("********Avant");
                //    System.out.println("tour1 "+tour1);
                //    System.out.println("tour2 "+tour2);
                if (exchange.typeExchange == 1) {
                    // System.out.println("Permutation done 1");
                    tour1.removeCustomer(c1);
                    tour1.addCustomerInBestPosition(c2);
                    tour2.removeCustomer(c2);
                    tour2.addCustomerInBestPosition(c1);
                }
                if (exchange.typeExchange == 2) {
                    // System.out.println("Permutation done 2");
                    tour1.removeCustomer(c1);
                    tour2.addCustomer(exchange.bestTour2, c1);
                }
                if (exchange.typeExchange == 3) {
                    // System.out.println("Permutation done 3");
                    tour2.removeCustomer(c2);
                    tour1.addCustomer(exchange.bestTour1, c2);
                }
            }
            tour1.setBestCamion();
            tour2.setBestCamion();
            //    System.out.println("Après");
            //     System.out.println("tour1 "+tour1);
            //     System.out.println("tour2 "+tour2);
            return true;
        }
        tour1.setCamion(camion1);
        tour2.setCamion(camion2);//parce qu'on a enlever leurs camionsToUse
        return false;
    }
*/
/*    public void insertExchangeInList(Exchange exchange, ArrayList<Exchange> bestExchanges, int maxSize) {

        if (bestExchanges.size() == maxSize) {
            bestExchanges.remove(bestExchanges.get(maxSize));
        }

        if (exchange.win > bestExchanges.get(0).win) {//best a 0
            bestExchanges.add(0, exchange);
            return;
        }
        for (int i = 0; i < bestExchanges.size() - 1; i++) {
            if (exchange.win <= bestExchanges.get(i).win
                    && exchange.win > bestExchanges.get(i + 1).win) {
                bestExchanges.add(i + 1, exchange);
                return;
            }
        }
        //worst
        bestExchanges.add(bestExchanges.size(), exchange);

    }
*/
    /*public boolean bestMove(Tour tour1, Tour tour2) {
        int best1 = -1;
        int best2 = -1;
        double maxDiff = 0;
        double diff;
        for (int k = 0; k < tour1.size(); k++) {
            for (int m = k; m < tour2.size(); m++) {
                diff = checkMoveCustomerFromTourToTour(tour1, tour2, k, m);
                if (diff >= maxDiff) {
                    best1 = k;
                    best2 = m;
                    maxDiff = diff;
                }
            }
        }
        if (best1 != -1 && best2 != -1) {
            Customer c1 = tour1.getCustomer(best1);
            tour1.removeCustomer(c1);
            tour2.addCustomer(best2, c1);
            
            tour1.setBestCamion();
            tour2.setBestCamion();
            return true;
        }
        return false;
    }
*/
    
public boolean bestTwoOpt(Trip trip1, ArrayList<Trip> trips) {
        int best1 = -1;
        int best2 = -1;
        Trip bestTrip2= null;
        int typeOfChange = 0;
        double maxDiff = 0;
        double diff;
        for (int k = 0; k < trip1.size(); k++) {// Pour chaque tour de trip1, chercher le meilleure emplacement
            for(Trip trip2:trips){
                for (int m = k; m < trip2.size(); m++) {
                    diff = checkExchangeTours(trip1, trip2, trip1.getTour(k), trip2.getTour(m));
                    if (diff > maxDiff) {
                        best1 = k;
                        best2 = m;
                        typeOfChange = 1;
                        maxDiff = diff;
                        bestTrip2 = trip2;
                    }
                }

                    diff = checkMoveTour(trip1, trip2, trip1.getTour(k));
                    if (diff > maxDiff) {
                        best1 = k;
                        best2 = 0;
                        typeOfChange = 2;
                        maxDiff = diff;
                        bestTrip2 = trip2;
                    }

            }
        }
        if (best1 != -1 && best2 != -1) {
            Tour t1 = trip1.getTour(best1);
            //   System.out.println("********Avant");
            //    System.out.println("tour1 "+tour1);
            //    System.out.println("tour2 "+tour2);
            if (typeOfChange == 1) {
                // System.out.println("Permutation done 1");
                Tour t2 = bestTrip2.getTour(best2);
                trip1.getTours().remove(t1);
                trip1.getTours().add(t2);
                bestTrip2.getTours().remove(t2);
                bestTrip2.getTours().add(t1);
            }
            if (typeOfChange == 2) {
                // System.out.println("Permutation done 2");
                trip1.getTours().remove(t1);
                if(trip1.size()==0){
                    this.getTrips().remove(trip1);
                }
                bestTrip2.addTour(t1);
            }
            return true;
        }
        return false;
    }

public boolean bestTwoOpt(Trip trip1, Trip trip2) {
        if(trip1.size()<=1 && trip2.size()<=1)
            return false;
        int best1 = -1;
        int best2 = -1;
        //Trip bestTrip2= null;
        int typeOfChange = 0;
        double maxDiff = 0;
        double diff;
        if(trip1.getCamion()!=null && trip2.getCamion()!=null)
            for (int k = 0; k < trip1.size(); k++) {// Pour chaque tour de trip1, chercher le meilleure emplacement
                for (int m = k; m < trip2.size(); m++) {
                    diff = checkExchangeTours(trip1, trip2, trip1.getTour(k), trip2.getTour(m));
                    if (diff > maxDiff) {
                        best1 = k;
                        best2 = m;
                        typeOfChange = 1;
                        maxDiff = diff;
                    }
                }
            }
        if(trip2.getCamion()!=null)
            for (int k = 0; k < trip1.size(); k++) {// Pour chaque tour de trip1, chercher le meilleure emplacement
                diff = checkMoveTour(trip1, trip2, trip1.getTour(k));
                if (diff > maxDiff) {
                    best1 = k;
                    best2 = 0;
                    typeOfChange = 2;
                    maxDiff = diff;
                }
            }
            if(trip1.getCamion()!=null)
            for (int k = 0; k < trip2.size(); k++) {// Pour chaque tour de trip1, chercher le meilleure emplacement
                diff = checkMoveTour(trip2, trip1, trip2.getTour(k));
                if (diff > maxDiff) {
                    best1 = 0;
                    best2 = k;
                    typeOfChange = 3;
                    maxDiff = diff;
                }
            }
    
            if (best1 != -1 && best2 != -1) {
                //   System.out.println("********Avant");
            //    System.out.println("tour1 "+tour1);
            //    System.out.println("tour2 "+tour2);
            if (typeOfChange == 1) {
                // System.out.println("Permutation done 1");
                Tour t1 = trip1.getTour(best1);
                Tour t2 = trip2.getTour(best2);
                trip1.getTours().remove(t1);
                trip1.getTours().add(t2);
                trip2.getTours().remove(t2);
                trip2.getTours().add(t1);
            }
            if (typeOfChange == 2) {
                // System.out.println("Permutation done 2");
                Tour t1 = trip1.getTour(best1);
                trip1.getTours().remove(t1);
                /*if(trip1.getCamion()== null && trip1.size()==0){
                    this.getTrips().remove(trip1);
                }*/
                trip2.addTour(t1);
            }
            if (typeOfChange == 3) {
                // System.out.println("Permutation done 2");
                Tour t2 = trip2.getTour(best2);
                trip2.getTours().remove(t2);
                /*if(trip1.getCamion()== null && trip1.size()==0){
                    this.getTrips().remove(trip1);
                }*/
                trip1.addTour(t2);
            }
            return true;
        }
        return false;
    }
public boolean bestTwoOpt_dynamic(Trip trip1, Trip trip2) {
        if(trip1.size()<=1 && trip2.size()<=1)
            return false;
        int best1 = -1;
        int best2 = -1;
        //Trip bestTrip2= null;
        int typeOfChange = 0;
        double maxDiff = 0;
        double diff;
        if(trip1.getCamion()!=null && trip2.getCamion()!=null)
            for (int k = 0; k < trip1.size(); k++) {// Pour chaque tour de trip1, chercher le meilleure emplacement
                for (int m = k; m < trip2.size(); m++) {
                    
                        if ((trip1.getTour(k).id_fictif!=0)||(trip2.getTour(m).id_fictif!=0))
                            continue;
                    
                    diff = checkExchangeTours_dynamic(trip1, trip2, trip1.getTour(k), trip2.getTour(m));
                    if (diff > maxDiff) {
                        best1 = k;
                        best2 = m;
                        typeOfChange = 1;
                        maxDiff = diff;
                    }
                }
            }
        if(trip2.getCamion()!=null)
            for (int k = 0; k < trip1.size(); k++) {// Pour chaque tour de trip1, chercher le meilleure emplacement
                if (trip1.getTour(k).id_fictif!=0)
                            continue;
                diff = checkMoveTour_dynamic(trip1, trip2, trip1.getTour(k));
                if (diff > maxDiff) {
                    best1 = k;
                    best2 = 0;
                    typeOfChange = 2;
                    maxDiff = diff;
                }
            }
            if(trip1.getCamion()!=null)
            for (int k = 0; k < trip2.size(); k++) {// Pour chaque tour de trip1, chercher le meilleure emplacement
                if (trip2.getTour(k).id_fictif!=0)
                            continue;
                diff = checkMoveTour_dynamic(trip2, trip1, trip2.getTour(k));
                if (diff > maxDiff) {
                    best1 = 0;
                    best2 = k;
                    typeOfChange = 3;
                    maxDiff = diff;
                }
            }
    
            if (best1 != -1 && best2 != -1) {
                //   System.out.println("********Avant");
            //    System.out.println("tour1 "+tour1);
            //    System.out.println("tour2 "+tour2);
            if (typeOfChange == 1) {
                // System.out.println("Permutation done 1");
                Tour t1 = trip1.getTour(best1);
                Tour t2 = trip2.getTour(best2);
                trip1.getTours().remove(t1);
                trip1.getTours().add(t2);
                trip2.getTours().remove(t2);
                trip2.getTours().add(t1);
            }
            if (typeOfChange == 2) {
                // System.out.println("Permutation done 2");
                Tour t1 = trip1.getTour(best1);
                trip1.getTours().remove(t1);
                /*if(trip1.getCamion()== null && trip1.size()==0){
                    this.getTrips().remove(trip1);
                }*/
                trip2.addTour(t1);
            }
            if (typeOfChange == 3) {
                // System.out.println("Permutation done 2");
                Tour t2 = trip2.getTour(best2);
                trip2.getTours().remove(t2);
                /*if(trip1.getCamion()== null && trip1.size()==0){
                    this.getTrips().remove(trip1);
                }*/
                trip1.addTour(t2);
            }
            return true;
        }
        return false;
    }

public double checkExchangeTours(Trip trip1, Trip trip2, Tour tour1, Tour tour2) {
        //********Insertion du client dans position1 de tour1 dans la tour 2 à la position2
        //créer un nouveau tourGroup
        
        Camion camion1 = trip1.getCamion();
        Camion camion2 = trip2.getCamion();
        
        // v�rifier si l'�change pr�serve les contraintes du probl�me
        
        if(camion1==null || camion2==null)
            return -1;

        if (tour1.getQuantity() > camion2.getCapacity()
                || tour2.getQuantity() > camion1.getCapacity()) {
            return -1;
        }
        
        double newTime1 = trip1.getTemps()
                            - tour1.getTemps()
                            + tour2.getTemps();
        
        if (newTime1 > problem.getMaxTemps())
            return -1;
        
        double newTime2 = trip2.getTemps()
                            - tour2.getTemps()
                            + tour1.getTemps();
        if(newTime2 > problem.getMaxTemps()) 
            return -1;
        
        double time1 = trip1.getTemps();
        double time2 = trip2.getTemps();
        double diff = Math.max(time1,time2) - Math.max(newTime1,newTime2);
        
        if (diff >= 0
             //   && (tour1.getLoadRate() + trip2.getLoadRate() <= newRate1 + newRate2 // il suffit que les nouvelles tournées soient mieux en terme de taux et non pas les meilleures
             //   || (newRate1 <= problem.getMaxEmptyRate()
             //   && newRate2 <= problem.getMaxEmptyRate()))// soit ils sont bien remplis soit ils sont meilleure que l'existant
                ) {
            return diff;
        }
        return -1;
    }

    public double checkMoveTour(Trip trip1, Trip trip2, Tour tour1) {
        //********Insertion du client dans position1 de tour1 dans la tour 2 à la position2
        double LTR1 = this.getLTR();
        //créer un nouveau tourGroup
        
        Camion camion2 = trip2.getCamion();
        double time1 = trip1.getTemps();
        double time2 = trip2.getTemps();
        
        // v�rifier si l'�change pr�serve les contraintes du probl�me

        if(camion2==null)
            return -1;
        if (tour1.getQuantity() > camion2.getCapacity()) {
            return -1;
        }
        
        double newTime2 = trip2.getTemps()
                            + tour1.getTemps();
        
        if (newTime2 > problem.getMaxTemps()) {
            return -1;
        }
        double diff = 0;
        if(trip1.getCamion()==null)
            diff = 10000;
        else{
            double newTime1 = trip1.getTemps()
                            - tour1.getTemps();
            diff = Math.max(time1,time2) - Math.max(newTime1,newTime2);
        }
        
        if (diff > 0
             //   && (tour1.getLoadRate() + trip2.getLoadRate() <= newRate1 + newRate2 // il suffit que les nouvelles tournées soient mieux en terme de taux et non pas les meilleures
             //   || (newRate1 <= problem.getMaxEmptyRate()
             //   && newRate2 <= problem.getMaxEmptyRate()))// soit ils sont bien remplis soit ils sont meilleure que l'existant
                ) {
            return diff;
        }
        return -1;
    }
public double checkExchangeTours_dynamic(Trip trip1, Trip trip2, Tour tour1, Tour tour2) {
        //********Insertion du client dans position1 de tour1 dans la tour 2 à la position2
        //créer un nouveau tourGroup
        
        Camion camion1 = trip1.getCamion();
        Camion camion2 = trip2.getCamion();
        
        // v�rifier si l'�change pr�serve les contraintes du probl�me
        
        if(camion1==null || camion2==null)
            return -1;

        if (tour1.getQuantity() > camion2.getCapacity()
                || tour2.getQuantity() > camion1.getCapacity()) {
            return -1;
        }
        
        double newTime1 = trip1.getTemps_dynamic()
                            - tour1.getTemps_dynamic()
                            + tour2.getTemps_dynamic();
        
        if (newTime1 > (problemD.getMaxTemps_dynamic()-trip1.getCamion().getSum_temps_tour()))
            return -1;
        
        double newTime2 = trip2.getTemps_dynamic()
                            - tour2.getTemps_dynamic()
                            + tour1.getTemps_dynamic();
        if(newTime2 > (problemD.getMaxTemps_dynamic()-trip2.getCamion().getSum_temps_tour()))
            return -1;
        
        double time1 = trip1.getTemps_dynamic();
        double time2 = trip2.getTemps_dynamic();
        double diff = Math.max(time1,time2) - Math.max(newTime1,newTime2);
        
        if (diff >= 0
             //   && (tour1.getLoadRate() + trip2.getLoadRate() <= newRate1 + newRate2 // il suffit que les nouvelles tournées soient mieux en terme de taux et non pas les meilleures
             //   || (newRate1 <= problem.getMaxEmptyRate()
             //   && newRate2 <= problem.getMaxEmptyRate()))// soit ils sont bien remplis soit ils sont meilleure que l'existant
                ) {
            return diff;
        }
        return -1;
    }

    public double checkMoveTour_dynamic(Trip trip1, Trip trip2, Tour tour1) {
        //********Insertion du client dans position1 de tour1 dans la tour 2 à la position2
        double LTR1 = this.getLTR_dynamic();
        //créer un nouveau tourGroup
        
        Camion camion2 = trip2.getCamion();
        double time1 = trip1.getTemps_dynamic();
        double time2 = trip2.getTemps_dynamic();
        
        // v�rifier si l'�change pr�serve les contraintes du probl�me

        if(camion2==null)
            return -1;
        if (tour1.getQuantity() > camion2.getCapacity()) {
            return -1;
        }
        
        double newTime2 = trip2.getTemps_dynamic()
                            + tour1.getTemps_dynamic();
        
        if (newTime2 >( problemD.maxTemps_dynamic-trip2.getCamion().sum_temps_tour)) {
            return -1;
        }
        double diff = 0;
        if(trip1.getCamion()==null)
            diff = 10000;
        else{
            double newTime1 = trip1.getTemps_dynamic()
                            - tour1.getTemps_dynamic();
            diff = Math.max(time1,time2) - Math.max(newTime1,newTime2);
        }
        
        if (diff > 0
             //   && (tour1.getLoadRate() + trip2.getLoadRate() <= newRate1 + newRate2 // il suffit que les nouvelles tournées soient mieux en terme de taux et non pas les meilleures
             //   || (newRate1 <= problem.getMaxEmptyRate()
             //   && newRate2 <= problem.getMaxEmptyRate()))// soit ils sont bien remplis soit ils sont meilleure que l'existant
                ) {
            return diff;
        }
        return -1;
    }

//************Calculs*******************
    public int getQuantity() {
        int quantity = 0;
        for (Trip trip : trips)
            for (Tour t : trip.getTours()) {
                quantity += t.getQuantity();
            }
        return quantity;
    }

    public double getCostDistance() {
        double cost = 0;
        if (this == null) {
            return 0;
        }
        for (Trip trip : this.getTrips())
            for (Tour tour : trip.getTours()) {
                cost += tour.getTemps();
            }
        return cost;
    }

    public double getCost() {
        double cost = 0;
        if (this == null) {
            return 0;
        }
       
        return getLTR();
    }

   

    public double getTemps(Camion camion) {
        double time = 0;
        if (this.getTrips() != null) {
            for (Trip trip : this.getTrips()) {
                if(trip.getCamion()==camion){
                    for (Tour tour : trip.getTours()) {
                            if (tour.getC() == camion) {
                                time += tour.getTemps();
                            }
                        }
                }
            }
        }
        return time;
    }
     public double getCostDistance_dynamic() {
        double cost = 0;
        if (this == null) {
            return 0;
        }
        for (Trip trip : this.getTrips())
            for (Tour tour : trip.getTours()) {
                cost += tour.getTemps_dynamic();
            }
        return cost;
    }

    public double getCost_dynamic() {
        double cost = 0;
        if (this == null) {
            return 0;
        }
       
        return getLTR_dynamic();
    }

   

    public double getTemps_dynamic(Camion camion) {
        double time = 0;
        if (this.getTrips() != null) {
            for (Trip trip : this.getTrips()) {
                if(trip.getCamion()==camion){
                    for (Tour tour : trip.getTours()) {
                            if (tour.getC() == camion) {
                                time += tour.getTemps_dynamic();
                            }
                        }
                }
            }
        }
        return time;
    }
    
    public ArrayList<Tour> getTours(Camion camion) {
        if(camion == null)
            return null;
        if (this.getTrips() != null) {
            for (Trip trip : this.getTrips()) {
                if(trip.getCamion()==camion){
                    return trip.getTours();
                }
            }
        }
        return null;
    }
    
    public double getLTR(Camion camion) {
        double LTR = this.getTemps(camion)/problem.getMaxTemps();
        return LTR;
    }
    
    public double getLTR() {
        double longest = 0;
        for(Camion c:getCamionsUsed()){
            double time = getTemps(c);
            if(time>longest)
                longest=time;
        }
        return longest;///problem.getInitialMaxTemps();
    }
    
    public Trip longestTrip() {
        //System.out.println(this);
        Trip longestTrip=null;
        double longest = 0;
        for(Trip trip:getTrips()){
            Camion c= trip.getCamion();
            double time = getTemps(c);
            if(time>longest){
                longest=time;
                longestTrip = trip;
            }
        }
        return longestTrip;//problem.getInitialMaxTemps();
    }

    public boolean CheckTimeConstraint() {
        for (Camion c : this.getCamionsUsed()) {
            if (getTemps(c) > problem.getMaxTemps()) {
                System.out.println("contrainte de temps, camion " + c);
                return false;
            }
        }
        return true;
    }
    public double getLTR_dynamic(Camion camion) {
        double LTR = (this.getTemps_dynamic(camion)+camion.getSum_temps_tour())/problemD.getMaxTemps_dynamic();
        return LTR;
    }
    
    public double getLTR_dynamic() {
        double longest = 0;
        for(Camion c:getCamionsUsed()){
            double time = getTemps_dynamic(c);
            if(time>longest)
                longest=time;
        }
        return longest;///problem.getInitialMaxTemps();
    }
    
    public Trip longestTrip_dynamic() {
        //System.out.println(this);
        Trip longestTrip=null;
        double longest = 0;
        for(Trip trip:getTrips()){
            Camion c= trip.getCamion();
            double time = getTemps_dynamic(c);
            if(time>longest){
                longest=time;
                longestTrip = trip;
            }
        }
        return longestTrip;//problem.getInitialMaxTemps();
    }

    public boolean CheckTimeConstraint_dynamic() {
        for (Camion c : this.getCamionsUsed()) {
            if (getTemps_dynamic(c) > (problemD.getMaxTemps_dynamic()-c.sum_temps_tour)) {
                System.out.println("contrainte de temps, camion " + c);
                return false;
            }
        }
        return true;
    }

    /*public int getCostCamionUsed() {
        int cost = 0;
        //System.out.println("camions used "+camionsUsed);
        for (Camion c : getCamionsUsed()) {
            if (c != null)//le null ne doit pas etre dans la liste!!!
            {
                cost += problem.getCost(c);
            }
        }
        return cost;
    }
*/
    private ArrayList<Camion> getCamionsUsed() {
        int cost = 0;
        ArrayList<Camion> camionsUsed = new ArrayList<Camion>();
        //System.out.println("camions used "+camionsUsed);
        for (Trip t : getTrips()) {
            Camion c = t.getCamion();
            if (!camionsUsed.contains(c)) {
                camionsUsed.add(c);
            }
        }
        return camionsUsed;
    }

    //**********Check constraints**********
    public boolean CheckCamionsConstraint() {
        int c = 0, q = 0;
        for (Trip trip : this.getTrips()) {
            if (trip.getCamion() == null) {
                return false;
            }
        }
        return true;
    }

    public boolean CheckCapacityConstraint() {
        double c = 0, q = 0;
        for (Trip trip : this.getTrips()) {
            Camion camion = trip.getCamion();
                for (Tour tour : trip.getTours()) {
                    c = camion.getCapacity();
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
            if (!this.CheckCustomerInTourGroup_Individual1(customer)) {
                System.out.println("customer " + customer + " n'est pas visité");
                System.out.println("TourGroup_Individual1 " + this);
                return false;
            }
        }
        return true;
    }

    /*public void sort() {
        Collections.sort(this.getTrips());
    }*/

   public Boolean IdenticTourGroup_Individual1(TourGroup_Individual1 other) {
        if (this.size() != other.size()) {
            return false;
        }
        //if(getCost(trgp1)!=getCost(trgp2))
        //    return false;
        //this.sort(); other.sort();
        
        for (Trip trip1 : this.getTrips()){
            boolean s1=false;
            for (Trip trip2 : other.getTrips()){
                int s2=0;
                for (Tour t1 : trip1.getTours()){
                    for (Tour t2 : trip2.getTours()){
                        if (t1.Identique_2sens(t2) == true)
                                {
                            s2++;
                            break;
                        }
                    }
                }
                if(s2==trip1.getTours().size()){//aucune t2 pour t1
                            s1=true;
                            break;
                }
            }
            if(s1==false)
                return false;
        }
        System.out.println("identiques");
        return true;
    }
   public Boolean IdenticTourGroup_Individual1_dynamic(TourGroup_Individual1 other) {
        if (this.size() != other.size()) {
            return false;
        }
        //if(getCost(trgp1)!=getCost(trgp2))
        //    return false;
        //this.sort(); other.sort();
        
        for (Trip trip1 : this.getTrips()){
            boolean s1=false;
            for (Trip trip2 : other.getTrips()){
                int s2=0;
                for (Tour t1 : trip1.getTours()){
                    for (Tour t2 : trip2.getTours()){
                        if (t1.Identique_2sens_dynamic(t2) == true)
                                {
                            s2++;
                            break;
                        }
                    }
                }
                if(s2==trip1.getTours().size()){//aucune t2 pour t1
                            s1=true;
                            break;
                }
            }
            if(s1==false)
                return false;
        }
        System.out.println("identiques");
        return true;
    }

    //**********Ameliorate
    /*public void AmeliorateBySaving() {
        boolean improved = true;
        while (improved) {
            improved = new ClarkAndWrightDistanceVrpSolver(problem)
                    .optimizeTour(this);
        }
    }*/

    /*public void AmeliorateByPPV() {
        for (Trip trip : this.getTrips()) {
            for (Tour t : trip.getTours()) {
                Tour tour = t.PPV();
                if (tour.getDistance() < t.getDistance()) {
                    t = tour;
                }
            }
        }
    }
    */
    /*public boolean minimizeLTR() {
        ArrayList<Tour> longestTrip = longestTrip().getTours();
        ArrayList<Tour> otherTours = new ArrayList<Tour>();
        boolean done = false;
        
        for(Trip trip:trips)
            for(Tour t:trip.getTours())
                if(!longestTrip.contains(t))
                    otherTours.add(t);

        for (int i = 0; i < longestTrip.size(); i++) {
            for (int j = 0; j < otherTours.size(); j++) {
                Tour tour1 = longestTrip.get(i);
                Tour tour2 = otherTours.get(j);
                if(TourGroup.bestMove(tour1, tour2)==true)
                    done = true;
            }
        }   
        return done;
    }*/
    
    public boolean minimizeLTRByExchangeTours_1() {
        Trip longestTrip = longestTrip();
        if(longestTrip ==null){
            longestTrip();
            return false;
        }
        ArrayList<Trip> otherTrips = new ArrayList<Trip>();
        boolean done = false;
        for(Trip t:trips)
            if(longestTrip.getCamion()!=t.getCamion())
                otherTrips.add(t);
        
        
        if(this.bestTwoOpt(longestTrip, otherTrips)==true)
            done = true;

        return done;
    }
    
    public boolean minimizeLTRByExchangeTours() {
        //System.out.println("trGp_trip "+this);
        boolean done = false;
        
        for(int i=0;i<trips.size();i++){
            Trip trip1 = trips.get(i);
            for(int j=i+1;j<trips.size();j++){
                Trip trip2 = trips.get(j);
                if(this.bestTwoOpt(trip1, trip2)==true)
                    done = true;
            }
        }

        return done;
    }
     public boolean minimizeLTRByExchangeTours_dynamic() {
        //System.out.println("trGp_trip "+this);
        boolean done = false;
        
        for(int i=0;i<trips.size();i++){
            Trip trip1 = trips.get(i);
            for(int j=i+1;j<trips.size();j++){
                Trip trip2 = trips.get(j);
                if(this.bestTwoOpt_dynamic(trip1, trip2)==true)
                    done = true;
            }
        }

        return done;
    }
    
    public boolean allocateTours(){
        System.out.println("before affectation1:"+this);
        for(int i=0;i<trips.size();i++){
            Trip trip1 = trips.get(i);
            if(trip1.getCamion()==null){// Pour un trip non affecté
                for (int k = 0; k < trip1.size(); k++) {// Pour chaque tour de trip1, chercher le meilleure emplacement
                    Tour t1 = trip1.getTour(k);
                    Trip bestTripForTour = null;
                    double maxDiff = -1;
                    for(int j=0;j<trips.size();j++){
                        if(j==i)
                            continue;
                        Trip trip2 = trips.get(j);
                        if(trip2.getCamion()!=null){
                            double diff = checkMoveTour(trip1, trip2, t1);
                            if (diff > maxDiff) {
                                maxDiff = diff;
                                bestTripForTour = trip2;
                            }
                        }
                    }
                    if(bestTripForTour!=null){
                        trip1.getTours().remove(t1);
                        /*if(trip1.getCamion()== null && trip1.size()==0){
                            this.getTrips().remove(trip1);
                        }*/
                        bestTripForTour.addTour(t1);
                        k--;
                    }
                }
                if(trip1.size()==0)
                    this.getTrips().remove(trip1);
                System.out.println("after affectation1:"+this); 
                return true;
            }
        }
        return false;        
    }
    
    public boolean minimizeLTRByTours() {
        boolean done1 = false;
        boolean done = true;
        while(done==true){
            done = minimizeLTRByExchangeTours();
            if(done==true)
                done1 = true;
        }
            
        return done1;
    }
      public boolean minimizeLTRByTours_dynamic() {
        boolean done1 = false;
        boolean done = true;
        while(done==true){
            done = minimizeLTRByExchangeTours_dynamic();
            if(done==true)
                done1 = true;
        }
            
        return done1;
    }
    
    public boolean minimizeNbCamionsByTours() {
        boolean done1 = false;
        boolean done = true;
        while(done==true){
            done = minimizeLTRByExchangeTours();
            if(done==true)
                done1 = true;
        }
        return done1;
    }
    
   // @Override
   /* public String toString() {
        if (this == null) {
            return "vide";
        }
        double d = 0, c = 0, v = 0;
        if (problem.getCoef1() != 0) {
            d = this.getCostDistance();
        }
        if (problem.getCoef2() != 0) {
            c = this.getCostCamionUsed();
        }
        if (problem.getCoef3() != 0) {
            v = this.getCostEmptyQuantity();
        }
        return "{TourGroup_Individual1: quantity=" + this.getQuantity()
                + ", cost=" + (d * problem.getCoef1() + c * problem.getCoef2() + v * problem.getCoef3())
                + ", cost Distance=" + d
                + ", cost Camion=" + c
                + ", empty Quantity=" + v
                + ", LTR=" + getLTR()
                + "," + "\n" + "		trips=" + this.getTrips()
                + "}";
    }
*/
    @Override
    public int compareTo(TourGroup_Individual1 tourGroup) {

        double diff = this.getLTR()- tourGroup.getLTR();
        if(diff>0)
            return 1;
        if(diff<0)
            return -1;
        return 0;
    }
}
