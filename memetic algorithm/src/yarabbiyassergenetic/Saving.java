package yarabbiyassergenetic;



/*
 * Un Saving représente le gain (en terme de distance) obtenu
 * en fusionnant deux tours élémentaires.
 */
public class Saving implements Comparable<Saving> {

    TourGroup tourGroup;
    Tour firstTour;
    Tour secondTour;
    MergeType mergeType;
    private VRPS problem;
    public VRPD problemD;
    

    public Saving(TourGroup tourGroup, Tour firstTour, Tour secondTour) {
        super();
        this.tourGroup = tourGroup;
        this.firstTour = firstTour;
        this.secondTour = secondTour;
        this.problem = tourGroup.getProblem();
    }
    public Saving(TourGroup tourGroup, Tour firstTour, Tour secondTour, VRPD problemD) {
        super();
        this.tourGroup = tourGroup;
        this.firstTour = firstTour;
        this.secondTour = secondTour;
        this.problemD = tourGroup.getProblemD();
    }

    public TourGroup getTourGroup() {
        return tourGroup;
    }

    public void setTourGroup(TourGroup tourGroup) {
        this.tourGroup = tourGroup;
    }

    public VRPS getProblem() {
        return problem;
    }

    public void setProblem(VRPS problem) {
        this.problem = problem;
    }

    public VRPD getProblemD() {
        return problemD;
    }

    public void setProblemD(VRPD problemD) {
        this.problemD = problemD;
    }

    /*
     * Cette méthode essaye toutes les 4 manières de fusionner deux bouts de parcourt,
     * et renvoie le meuilleur gain.
     */
    public double getSaving() {
        double saving = -1000000, s;
        double firstTemps = firstTour.getTemps();
        double secondTemps = secondTour.getTemps();

        if (firstTour.getQuantity() + secondTour.getQuantity() > problem.getMaxCapacity()) {
            return saving;
        }

        double temps = firstTemps + secondTemps
                + problem.gettime(firstTour.getLastCustomer().getId(), secondTour.getFirstCustomer().getId())
                - problem.gettime(firstTour.getLastCustomer().getId(), 0)
                - problem.gettime(secondTour.getFirstCustomer().getId(), 0);

        if (temps <= problem.getMaxTemps()) {
            s = computeSaving(firstTour.getLastCustomer(), secondTour.getLastCustomer());
            if (s > saving) {
                saving = s;
                mergeType = MergeType.TAIL_TO_HEAD;
            }
        }

        temps = firstTemps + secondTemps
                + problem.gettime(secondTour.getLastCustomer().getId(), firstTour.getFirstCustomer().getId())
                - problem.gettime(secondTour.getLastCustomer().getId(), 0)
                - problem.gettime(firstTour.getFirstCustomer().getId(), 0);
        if (temps <= problem.getMaxTemps()) {
            s = computeSaving(firstTour.getFirstCustomer(), secondTour.getLastCustomer());
            if (s > saving) {
                saving = s;
                mergeType = MergeType.HEAD_TO_TAIL;
            }
        }

        if (firstTour.size() > 1 && secondTour.size() > 1) {//sinon les deux types dessus sont suffisants
            temps = firstTemps + secondTemps
                    + problem.gettime(secondTour.getFirstCustomer().getId(), firstTour.getFirstCustomer().getId())
                    - problem.gettime(secondTour.getFirstCustomer().getId(), 0)
                    - problem.gettime(firstTour.getFirstCustomer().getId(), 0);
            if (temps <= problem.getMaxTemps()) {
                s = computeSaving(firstTour.getFirstCustomer(), secondTour.getFirstCustomer());
                if (s > saving) {
                    saving = s;
                    mergeType = MergeType.HEAD_TO_HEAD;
                }
            }

            temps = firstTemps + secondTemps
                    + problem.gettime(secondTour.getLastCustomer().getId(), firstTour.getLastCustomer().getId())
                    - problem.gettime(secondTour.getLastCustomer().getId(), 0)
                    - problem.gettime(firstTour.getLastCustomer().getId(), 0);
            if (temps <= problem.getMaxTemps()) {
                s = computeSaving(firstTour.getLastCustomer(), secondTour.getLastCustomer());
                if (s > saving) {
                    saving = s;
                    mergeType = MergeType.TAIL_TO_TAIL;
                }
            }
        }

        return saving;
    }
      public double getSaving_dynamic() {
        double saving = -1000000, s;
        double firstTemps = firstTour.getTemps_dynamic();
        double secondTemps = secondTour.getTemps_dynamic();

        if ((firstTour.getQuantity() + secondTour.getQuantity() > firstTour.getcapacity())||(secondTour.id_fictif!=0)) {
            return saving;
        }
        if (firstTour.id_fictif!=0)
    {  Depotfictif d=this.getProblemD().getdepotfictifById_dynamic(firstTour.id_fictif);
            double temps=0;
           if (firstTour.size()==0)
    { 
      temps = firstTemps + secondTemps
                + problemD.gettimes_dynamic(d.id_client_fictif, secondTour.getFirstCustomer().getId())
                - problemD.gettimes_dynamic(d.id_client_fictif, 0)
                - problemD.gettimes_dynamic(secondTour.getFirstCustomer().getId(), 0);
      s=problemD.gettimes_dynamic(d.id_client_fictif, 0)
       +problemD.gettimes_dynamic(secondTour.getFirstCustomer().getId(), 0)
       -problemD.gettimes_dynamic(d.id_client_fictif,secondTour.getFirstCustomer().getId());
       if (temps <= firstTour.getTempsRestant()) {
           
            if (s > saving) {
                saving = s;
                mergeType = MergeType.TAIL_TO_HEAD;
            }
        } 
       temps = firstTemps + secondTemps
                + problemD.gettimes_dynamic(d.id_client_fictif, secondTour.getLastCustomer().getId())
                - problemD.gettimes_dynamic(d.id_client_fictif, 0)
                - problemD.gettimes_dynamic(secondTour.getLastCustomer().getId(), 0);
      s=problemD.gettimes_dynamic(d.id_client_fictif, 0)
       +problemD.gettimes_dynamic(secondTour.getLastCustomer().getId(), 0)
       -problemD.gettimes_dynamic(d.id_client_fictif,secondTour.getLastCustomer().getId());
      if (temps <= firstTour.getTempsRestant()) {
           
            if (s > saving) {
                saving = s;
                mergeType = MergeType.HEAD_TO_TAIL;
            }
       
    }
    }
           else {
               temps = firstTemps + secondTemps
                + problemD.gettimes_dynamic(firstTour.getLastCustomer().getId(), secondTour.getFirstCustomer().getId())
                - problemD.gettimes_dynamic(firstTour.getLastCustomer().getId(), 0)
                - problemD.gettimes_dynamic(secondTour.getFirstCustomer().getId(), 0);
               if (temps <= firstTour.getTempsRestant()) {
           s = computeSaving_dynamic(firstTour.getLastCustomer(), secondTour.getFirstCustomer());
            if (s > saving) {
                saving = s;
                mergeType =  MergeType.TAIL_TO_HEAD;
            }
               
           }
               temps = firstTemps + secondTemps
                + problemD.gettimes_dynamic(firstTour.getFirstCustomer().getId(), secondTour.getLastCustomer().getId())
                - problemD.gettimes_dynamic(firstTour.getFirstCustomer().getId(), d.id_client_fictif)
                - problemD.gettimes_dynamic(secondTour.getLastCustomer().getId(), 0)
                -problemD.gettimes_dynamic(firstTour.getLastCustomer().getId(), 0)
               +problemD.gettimes_dynamic(firstTour.getLastCustomer().getId(), d.id_client_fictif);
               if (temps <= firstTour.getTempsRestant()) {
            s=problemD.gettimes_dynamic(firstTour.getFirstCustomer().getId(), d.id_client_fictif)
               +problemD.gettimes_dynamic(secondTour.getLastCustomer().getId(), 0)
               +problemD.gettimes_dynamic(firstTour.getLastCustomer().getId(), 0)
              -problemD.gettimes_dynamic(firstTour.getFirstCustomer().getId(), secondTour.getLastCustomer().getId())
              -problemD.gettimes_dynamic(firstTour.getLastCustomer().getId(), d.id_client_fictif);
            if (s > saving) {
                saving = s;
                mergeType =  MergeType.HEAD_TO_TAIL;
            }
               
           }
               if (firstTour.size() > 1 && secondTour.size() > 1) {//sinon les deux types dessus sont suffisants
            temps = firstTemps + secondTemps
                    + problemD.gettimes_dynamic(secondTour.getFirstCustomer().getId(), firstTour.getFirstCustomer().getId())
                    - problemD.gettimes_dynamic(secondTour.getFirstCustomer().getId(), 0)
                    - problemD.gettimes_dynamic(firstTour.getFirstCustomer().getId(), d.id_client_fictif)
                    - problemD.gettimes_dynamic(firstTour.getLastCustomer().getId(), 0)
                    + problemD.gettimes_dynamic(firstTour.getLastCustomer().getId(),  d.id_client_fictif);
            if (temps <= firstTour.getTempsRestant()) {
                s =  problemD.gettimes_dynamic(secondTour.getFirstCustomer().getId(), 0)
                    + problemD.gettimes_dynamic(firstTour.getFirstCustomer().getId(), d.id_client_fictif)
                    + problemD.gettimes_dynamic(firstTour.getLastCustomer().getId(), 0)
                    -problemD.gettimes_dynamic(secondTour.getFirstCustomer().getId(), firstTour.getFirstCustomer().getId())
                    
                    - problemD.gettimes_dynamic(firstTour.getLastCustomer().getId(),  d.id_client_fictif);
                if (s > saving) {
                    saving = s;
                    mergeType = MergeType.HEAD_TO_HEAD;
                }
            }

            temps = firstTemps + secondTemps
                    + problemD.gettimes_dynamic(secondTour.getLastCustomer().getId(), firstTour.getLastCustomer().getId())
                    - problemD.gettimes_dynamic(secondTour.getLastCustomer().getId(), 0)
                    - problemD.gettimes_dynamic(firstTour.getLastCustomer().getId(), 0);
            if (temps <= firstTour.getTempsRestant()) {
                s = computeSaving_dynamic(firstTour.getLastCustomer(), secondTour.getLastCustomer());
                if (s > saving) {
                    saving = s;
                    mergeType = MergeType.TAIL_TO_TAIL;
                }
            }
        }
        }
    }
        
    
        else{
        double temps = firstTemps + secondTemps
                + problemD.gettimes_dynamic(firstTour.getLastCustomer().getId(), secondTour.getFirstCustomer().getId())
                - problemD.gettimes_dynamic(firstTour.getLastCustomer().getId(), 0)
                - problemD.gettimes_dynamic(secondTour.getFirstCustomer().getId(), 0);

        if (temps <= problemD.getMaxTemps_dynamic()) {
            s = computeSaving_dynamic(firstTour.getLastCustomer(), secondTour.getLastCustomer());
            if (s > saving) {
                saving = s;
                mergeType = MergeType.TAIL_TO_HEAD;
            }
        }

        temps = firstTemps + secondTemps
                +problemD.gettimes_dynamic(secondTour.getLastCustomer().getId(), firstTour.getFirstCustomer().getId())
                - problemD.gettimes_dynamic(secondTour.getLastCustomer().getId(), 0)
                - problemD.gettimes_dynamic(firstTour.getFirstCustomer().getId(), 0);
        if (temps <= problemD.getMaxTemps_dynamic()) {
            s = computeSaving_dynamic(firstTour.getFirstCustomer(), secondTour.getLastCustomer());
            if (s > saving) {
                saving = s;
                mergeType = MergeType.HEAD_TO_TAIL;
            }
        }

        if (firstTour.size() > 1 && secondTour.size() > 1) {//sinon les deux types dessus sont suffisants
            temps = firstTemps + secondTemps
                    + problemD.gettimes_dynamic(secondTour.getFirstCustomer().getId(), firstTour.getFirstCustomer().getId())
                    - problemD.gettimes_dynamic(secondTour.getFirstCustomer().getId(), 0)
                    - problemD.gettimes_dynamic(firstTour.getFirstCustomer().getId(), 0);
            if (temps <= problemD.getMaxTemps_dynamic()) {
                s = computeSaving_dynamic(firstTour.getFirstCustomer(), secondTour.getFirstCustomer());
                if (s > saving) {
                    saving = s;
                    mergeType = MergeType.HEAD_TO_HEAD;
                }
            }

            temps = firstTemps + secondTemps
                    + problemD.gettimes_dynamic(secondTour.getLastCustomer().getId(), firstTour.getLastCustomer().getId())
                    - problemD.gettimes_dynamic(secondTour.getLastCustomer().getId(), 0)
                    - problemD.gettimes_dynamic(firstTour.getLastCustomer().getId(), 0);
            if (temps <= problemD.getMaxTemps_dynamic()) {
                s = computeSaving_dynamic(firstTour.getLastCustomer(), secondTour.getLastCustomer());
                if (s > saving) {
                    saving = s;
                    mergeType = MergeType.TAIL_TO_TAIL;
                }
            }
        }
        }

        return saving;
    }

    public double getCombinedSaving_withoutCamionCost() {//heterogene
        double saving = -1000000, s;
        double tempsBestCamion = 0;
        Camion bestCamion = null;
        double firstTemps = firstTour.getTemps();
        double secondTemps = secondTour.getTemps();
        double totalQuantity = firstTour.getQuantity() + secondTour.getQuantity();
        if (totalQuantity > problem.getMaxCapacity()) {
            return saving;
        }
        //System.out.println("Tour1 :" + firstTour);
        //System.out.println("Tour2 :" + secondTour);
        
        Camion firstCamion = firstTour.getC();
        Camion secondCamion = secondTour.getC();

        firstTour.removeCamion();
        secondTour.removeCamion();

        double temps = firstTemps + secondTemps
                + problem.gettime(firstTour.getLastCustomer().getId(), secondTour.getFirstCustomer().getId())
                - problem.gettime(firstTour.getLastCustomer().getId(), 0)
                - problem.gettime(secondTour.getFirstCustomer().getId(), 0);
        if (temps <= problem.getMaxTemps()) {
            s = computeSaving(firstTour.getLastCustomer(), secondTour.getLastCustomer());
            Camion bestCamionTmp = tourGroup.BestCamionforTimeAndQuantity(temps, totalQuantity);
            tempsBestCamion = tourGroup.getTemps(bestCamionTmp);
            s-= (tourGroup.getOvertime(tempsBestCamion + temps) - tourGroup.getOvertime(tempsBestCamion))
                  * (problem.getPenaltyOvertime() + tourGroup.getOvertime(tempsBestCamion));
            
            if (s > saving) { //sij+cost1+cost2-costnew
                saving = s;
                mergeType = MergeType.TAIL_TO_HEAD;
                bestCamion = bestCamionTmp;
            }
        }

        temps = firstTemps + secondTemps
                + problem.gettime(secondTour.getLastCustomer().getId(), firstTour.getFirstCustomer().getId())
                - problem.gettime(secondTour.getLastCustomer().getId(), 0)
                - problem.gettime(firstTour.getFirstCustomer().getId(), 0);
        if (temps <= problem.getMaxTemps()) {
            s = computeSaving(firstTour.getFirstCustomer(), secondTour.getLastCustomer());
            Camion bestCamionTmp = tourGroup.BestCamionforTimeAndQuantity(temps, totalQuantity);
            if (bestCamionTmp != bestCamion) {
                tempsBestCamion = tourGroup.getTemps(bestCamionTmp);
            }
            s-= (tourGroup.getOvertime(tempsBestCamion + temps) - tourGroup.getOvertime(tempsBestCamion))
                  * (problem.getPenaltyOvertime() + tourGroup.getOvertime(tempsBestCamion));
            if (s > saving) { //sij+cost1+cost2-costnew
                saving = s;
                mergeType = MergeType.HEAD_TO_TAIL;
                bestCamion = bestCamionTmp;
            }
        }

        if (firstTour.size() > 1 && secondTour.size() > 1) {//sinon les deux types dessus sont suffisants
            temps = firstTemps + secondTemps
                    + problem.gettime(secondTour.getFirstCustomer().getId(), firstTour.getFirstCustomer().getId())
                    - problem.gettime(secondTour.getFirstCustomer().getId(), 0)
                    - problem.gettime(firstTour.getFirstCustomer().getId(), 0);
            if (temps <= problem.getMaxTemps()) {
                s = computeSaving(firstTour.getFirstCustomer(), secondTour.getFirstCustomer());
                Camion bestCamionTmp = tourGroup.BestCamionforTimeAndQuantity(temps, totalQuantity);
                if (bestCamionTmp != bestCamion) {
                    tempsBestCamion = tourGroup.getTemps(bestCamionTmp);
                }
                s-= (tourGroup.getOvertime(tempsBestCamion + temps) - tourGroup.getOvertime(tempsBestCamion))
                      * (problem.getPenaltyOvertime() + tourGroup.getOvertime(tempsBestCamion));
                if (s > saving) { //sij+cost1+cost2-costnew
                    saving = s;
                    mergeType = MergeType.HEAD_TO_HEAD;
                    bestCamion = bestCamionTmp;
                }
            }

            temps = firstTemps + secondTemps
                    + problem.gettime(secondTour.getLastCustomer().getId(), firstTour.getLastCustomer().getId())
                    - problem.gettime(secondTour.getLastCustomer().getId(), 0)
                    - problem.gettime(firstTour.getLastCustomer().getId(), 0);
            if (temps <= problem.getMaxTemps()) {
                s = computeSaving(firstTour.getLastCustomer(), secondTour.getLastCustomer());
                Camion bestCamionTmp = tourGroup.BestCamionforTimeAndQuantity(temps, totalQuantity);
                if (bestCamionTmp != bestCamion) {
                    tempsBestCamion = tourGroup.getTemps(bestCamionTmp);
                }
                s-= (tourGroup.getOvertime(tempsBestCamion + temps) - tourGroup.getOvertime(tempsBestCamion))
                      * (problem.getPenaltyOvertime() + tourGroup.getOvertime(tempsBestCamion));
                if (s > saving) { //sij+cost1+cost2-costnew
                    saving = s;
                    mergeType = MergeType.TAIL_TO_TAIL;
                    bestCamion = bestCamionTmp;
                }
            }
        }
        if (bestCamion == null) {
            firstTour.setC(firstCamion);
            secondTour.setC(secondCamion);
            return -1000000;
        }

        
        if (firstCamion != null && secondCamion != null) {
            saving += gainOvertime_BeforeAllocation(firstCamion, secondCamion);            
            firstTour.setC(firstCamion);
            secondTour.setC(secondCamion);
        } else if (firstCamion == null && secondCamion != null) {
            firstCamion = tourGroup.BestCamionforTour(firstTour);
            saving += gainOvertime_BeforeAllocation(firstCamion, secondCamion) ;           
            secondTour.setC(secondCamion);
        } else if (firstCamion != null && secondCamion == null) {
            secondCamion = tourGroup.BestCamionforTour(secondTour);
            saving += gainOvertime_BeforeAllocation(firstCamion, secondCamion);            
            firstTour.setC(firstCamion);
        } else if (firstCamion == null && secondCamion == null) {
            firstCamion = tourGroup.BestCamionforTour(firstTour);
            //affecter first Tour pr chercher secondTour    
                firstTour.setC(firstCamion);
                secondCamion = tourGroup.BestCamionforTour(secondTour);
                firstTour.removeCamion();
            //Fin affectation
            saving += gainOvertime_BeforeAllocation(firstCamion, secondCamion);            
        }        /*if(firstCamion!=null && secondCamion!=null
         && bestCamion.getType() !=firstCamion.getType() && bestCamion.getType() !=secondCamion.getType() ){
         int type = tourGoup.bestCamionTypeForQuantity(problem.getCapacite(bestCamion)-totalQuantity);
         if(totalQuantity<problem.getCapacite(bestCamion) && type>1)
         type--;
         saving+=problem.getCostCamionType(type);
         */
        /*System.out.println("-savingBestCamion:" + (- problem.getCost(bestCamion)));
        System.out.println("newovertime BestCamion:" + ( tourGroup.getOvertime(tempsBestCamion + temps)));
        System.out.println("oldovertime BestCamion:" + ( tourGroup.getOvertime(tempsBestCamion)) );
        System.out.println("-savingOvertime:" + (- tourGroup.getOvertime(tempsBestCamion + temps)
                                                    + tourGroup.getOvertime(tempsBestCamion)) 
                                                        * (problem.getPenaltyOvertime()
                                                            + tourGroup.getOvertime(tempsBestCamion)));
        System.out.println("totalSaving:" + saving);
        */
        /*if (!tourGroup.checkCamionInTourGroup(bestCamion)) {
            saving += problem.getCost(bestCamion);
        }*/
        return saving;
    }
    
    public double getCombinedSaving() {//heterogene
        double saving = -1000000, s;
        double tempsBestCamion = 0;
        Camion bestCamion = null;
        double firstTemps = firstTour.getTemps();
        double secondTemps = secondTour.getTemps();
        double totalQuantity = firstTour.getQuantity() + secondTour.getQuantity();
        if (totalQuantity > problem.getMaxCapacity()) {
            return saving;
        }
        //System.out.println("Tour1 :" + firstTour);
        //System.out.println("Tour2 :" + secondTour);
        
        Camion firstCamion = firstTour.getC();
        Camion secondCamion = secondTour.getC();

        firstTour.removeCamion();
        secondTour.removeCamion();

        double temps = firstTemps + secondTemps
                + problem.gettime(secondTour.getLastCustomer().getId(), firstTour.getFirstCustomer().getId())
                - problem.gettime(secondTour.getLastCustomer().getId(), 0)
                - problem.gettime(firstTour.getFirstCustomer().getId(), 0);
        if (temps <= problem.getMaxTemps()) {
            s = computeSaving(firstTour.getFirstCustomer(), secondTour.getLastCustomer());
            Camion bestCamionTmp = tourGroup.BestCamionforTimeAndQuantity(temps, totalQuantity);
            if (bestCamionTmp != bestCamion) {
                tempsBestCamion = tourGroup.getTemps(bestCamionTmp);
            }
            if (!tourGroup.checkCamionInTourGroup(bestCamionTmp)) {
                s -= problem.getCostCamion();
            }
            s-= (tourGroup.getOvertime(tempsBestCamion + temps) - tourGroup.getOvertime(tempsBestCamion))
                  * (problem.getPenaltyOvertime() + tourGroup.getOvertime(tempsBestCamion));
            if (s > saving) { //sij+cost1+cost2-costnew
                saving = s;
                mergeType = MergeType.HEAD_TO_TAIL;
                bestCamion = bestCamionTmp;
            }
        }

        if (firstTour.size() > 1 && secondTour.size() > 1) {//sinon les deux types dessus sont suffisants
            temps = firstTemps + secondTemps
                    + problem.gettime(secondTour.getFirstCustomer().getId(), firstTour.getFirstCustomer().getId())
                    - problem.gettime(secondTour.getFirstCustomer().getId(), 0)
                    - problem.gettime(firstTour.getFirstCustomer().getId(), 0);
            if (temps <= problem.getMaxTemps()) {
                s = computeSaving(firstTour.getFirstCustomer(), secondTour.getFirstCustomer());
                Camion bestCamionTmp = tourGroup.BestCamionforTimeAndQuantity(temps, totalQuantity);
                if (bestCamionTmp != bestCamion) {
                    tempsBestCamion = tourGroup.getTemps(bestCamionTmp);
                }
                if (!tourGroup.checkCamionInTourGroup(bestCamionTmp)) {
                s -= problem.getCostCamion();
                }
                s-= (tourGroup.getOvertime(tempsBestCamion + temps) - tourGroup.getOvertime(tempsBestCamion))
                      * (problem.getPenaltyOvertime() + tourGroup.getOvertime(tempsBestCamion));
                if (s > saving) { //sij+cost1+cost2-costnew
                    saving = s;
                    mergeType = MergeType.HEAD_TO_HEAD;
                    bestCamion = bestCamionTmp;
                }
            }

            temps = firstTemps + secondTemps
                    + problem.gettime(secondTour.getLastCustomer().getId(), firstTour.getLastCustomer().getId())
                    - problem.gettime(secondTour.getLastCustomer().getId(), 0)
                    - problem.gettime(firstTour.getLastCustomer().getId(), 0);
            if (temps <= problem.getMaxTemps()) {
                s = computeSaving(firstTour.getLastCustomer(), secondTour.getLastCustomer());
                Camion bestCamionTmp = tourGroup.BestCamionforTimeAndQuantity(temps, totalQuantity);
                if (bestCamionTmp != bestCamion) {
                    tempsBestCamion = tourGroup.getTemps(bestCamionTmp);
                }
                if (!tourGroup.checkCamionInTourGroup(bestCamionTmp)) {
                s -= problem.getCostCamion();
                }
                s-= (tourGroup.getOvertime(tempsBestCamion + temps) - tourGroup.getOvertime(tempsBestCamion))
                      * (problem.getPenaltyOvertime() + tourGroup.getOvertime(tempsBestCamion));
                if (s > saving) { //sij+cost1+cost2-costnew
                    saving = s;
                    mergeType = MergeType.TAIL_TO_TAIL;
                    bestCamion = bestCamionTmp;
                }
            }
        }
        if (bestCamion == null) {
            firstTour.setC(firstCamion);
            secondTour.setC(secondCamion);
            return -1000000;
        }

        
        if (firstCamion != null && secondCamion != null) {
            saving += gainCamions_BeforeAllocation(tourGroup, firstCamion, secondCamion);
            saving += gainOvertime_BeforeAllocation(firstCamion, secondCamion);            
            firstTour.setC(firstCamion);
            secondTour.setC(secondCamion);
        } else if (firstCamion == null && secondCamion != null) {
            firstCamion = tourGroup.BestCamionforTour(firstTour);
            saving += gainCamions_BeforeAllocation(tourGroup, firstCamion, secondCamion);
            saving += gainOvertime_BeforeAllocation(firstCamion, secondCamion) ;           
            secondTour.setC(secondCamion);
        } else if (firstCamion != null && secondCamion == null) {
            secondCamion = tourGroup.BestCamionforTour(secondTour);
            saving += gainCamions_BeforeAllocation(tourGroup, firstCamion, secondCamion);
            saving += gainOvertime_BeforeAllocation(firstCamion, secondCamion);            
            firstTour.setC(firstCamion);
        } else if (firstCamion == null && secondCamion == null) {
            firstCamion = tourGroup.BestCamionforTour(firstTour);
            //affecter first Tour pr chercher secondTour    
                firstTour.setC(firstCamion);
                secondCamion = tourGroup.BestCamionforTour(secondTour);
                firstTour.removeCamion();
            //Fin affectation
            saving += gainCamions_BeforeAllocation(tourGroup, firstCamion, secondCamion);//calcul gain camions se fait en considérant que les tours sont sans camions
            saving += gainOvertime_BeforeAllocation(firstCamion, secondCamion);            
        }
        /*if(firstCamion!=null && secondCamion!=null
         && bestCamion.getType() !=firstCamion.getType() && bestCamion.getType() !=secondCamion.getType() ){
         int type = tourGoup.bestCamionTypeForQuantity(problem.getCapacite(bestCamion)-totalQuantity);
         if(totalQuantity<problem.getCapacite(bestCamion) && type>1)
         type--;
         saving+=problem.getCostCamionType(type);
         */
        /*System.out.println("-savingBestCamion:" + (- problem.getCost(bestCamion)));
        System.out.println("newovertime BestCamion:" + ( tourGroup.getOvertime(tempsBestCamion + temps)));
        System.out.println("oldovertime BestCamion:" + ( tourGroup.getOvertime(tempsBestCamion)) );
        System.out.println("-savingOvertime:" + (- tourGroup.getOvertime(tempsBestCamion + temps)
                                                    + tourGroup.getOvertime(tempsBestCamion)) 
                                                        * (problem.getPenaltyOvertime()
                                                            + tourGroup.getOvertime(tempsBestCamion)));
        System.out.println("totalSaving:" + saving);
        */
        /*if (!tourGroup.checkCamionInTourGroup(bestCamion)) {
            saving += problem.getCost(bestCamion);
        }*/
        return saving;
    }

    double getSaving2() {
        double saving = -1000000, s;
        double firstTemps = firstTour.getTemps();
        double secondTemps = secondTour.getTemps();

        if (firstTour.getQuantity() + secondTour.getQuantity() > problem.getMaxCapacity()) {
            return saving;
        }

        double temps = firstTemps + secondTemps
                + problem.gettime(firstTour.getLastCustomer().getId(), secondTour.getFirstCustomer().getId())
                - problem.gettime(firstTour.getLastCustomer().getId(), 0)
                - problem.gettime(secondTour.getFirstCustomer().getId(), 0);

        if (temps <= problem.getMaxTemps()) {
            s = computeSaving(firstTour.getLastCustomer(), secondTour.getLastCustomer());
            if (s > saving) {
                saving = s;
                mergeType = MergeType.TAIL_TO_HEAD;
            }
        }

        temps = firstTemps + secondTemps
                + problem.gettime(secondTour.getLastCustomer().getId(), firstTour.getFirstCustomer().getId())
                - problem.gettime(secondTour.getLastCustomer().getId(), 0)
                - problem.gettime(firstTour.getFirstCustomer().getId(), 0);
        if (temps <= problem.getMaxTemps()) {
            s = computeSaving(firstTour.getFirstCustomer(), secondTour.getLastCustomer());
            if (s > saving) {
                saving = s;
                mergeType = MergeType.HEAD_TO_TAIL;
            }
        }
        return saving;
    }
     double getSaving2_dynamic() {
        double saving = -1000000;
        double s;
        double firstTemps = firstTour.getTemps_dynamic();
        double secondTemps = secondTour.getTemps_dynamic();

        if (firstTour.getQuantity() + secondTour.getQuantity() > firstTour.getcapacity()) {
            return saving;
        }
        double temps=0;
    if (firstTour.size()==0)
    { Depotfictif d=this.getProblemD().getdepotfictifById_dynamic(firstTour.id_fictif);
      temps = firstTemps + secondTemps
                + problemD.gettimes_dynamic(d.id_client_fictif, secondTour.getFirstCustomer().getId())
                - problemD.gettimes_dynamic(d.id_client_fictif, 0)
                - problemD.gettimes_dynamic(secondTour.getFirstCustomer().getId(), 0);
      s=problemD.gettimes_dynamic(d.id_client_fictif, 0)
       +problemD.gettimes_dynamic(secondTour.getFirstCustomer().getId(), 0)
       -problemD.gettimes_dynamic(d.id_client_fictif,secondTour.getFirstCustomer().getId());
       if (temps <= firstTour.getTempsRestant()) {
           
            if (s > saving) {
                saving = s;
                mergeType = MergeType.TAIL_TO_HEAD;
            }
        }
    }
   
       
        if ((firstTour.id_fictif!=0))
        {
            if((firstTour.size()>0))
            {
         Depotfictif d=this.getProblemD().getdepotfictifById_dynamic(firstTour.id_fictif);
        temps = firstTemps + secondTemps
                + problemD.gettimes_dynamic(secondTour.getLastCustomer().getId(), firstTour.getFirstCustomer().getId())
                +problemD.gettimes_dynamic(d.id_client_fictif,secondTour.getLastCustomer().getId())
                - (2*problemD.gettimes_dynamic(secondTour.getLastCustomer().getId(), 0))
                - problemD.gettimes_dynamic(firstTour.getFirstCustomer().getId(), d.id_client_fictif);
       s= problemD.getDistances_dynamic(firstTour.getFirstCustomer().getId(), d.id_client_fictif)
                + 2*(problemD.getDistances_dynamic(secondTour.getLastCustomer().getId(), 0))
                - problemD.getDistances_dynamic(firstTour.getFirstCustomer().getId(), secondTour.getLastCustomer().getId())
                -problemD.getDistances_dynamic(secondTour.getLastCustomer().getId(),d.id_client_fictif);
            }
            else s=saving;
        }
        else {
             temps = firstTemps + secondTemps
                + problemD.gettimes_dynamic(secondTour.getLastCustomer().getId(), firstTour.getFirstCustomer().getId())
                - problemD.gettimes_dynamic(secondTour.getLastCustomer().getId(), 0)
                - problemD.gettimes_dynamic(firstTour.getFirstCustomer().getId(), 0);
              s = computeSaving_dynamic(firstTour.getFirstCustomer(), secondTour.getLastCustomer());
        }
        if (temps <= firstTour.getTempsRestant()) {
           // s = computeSaving_dynamic(firstTour.getFirstCustomer(), secondTour.getLastCustomer());
            if (s > saving) {
                saving = s;
                mergeType = MergeType.HEAD_TO_TAIL;
            }
        }
        return saving;
    }
    
    /*
     * Méthode utilitaire utilisée par getSaving: calcule le gain 
     * obtenu en ne retournant pas au dépot entre deux magasins donnés.
     */

    public double computeSaving(Customer aCustomer, Customer anotherCustomer) {
        return problem.getDistances(aCustomer.getId(), 0)
                + problem.getDistances(anotherCustomer.getId(), 0)
                - problem.getDistances(aCustomer.getId(), anotherCustomer.getId());
    }
       public double computeSaving_dynamic(Customer aCustomer, Customer anotherCustomer) {
        return problemD.getDistances_dynamic(aCustomer.getId(), 0)
                + problemD.getDistances_dynamic(anotherCustomer.getId(), 0)
                - problemD.getDistances_dynamic(aCustomer.getId(), anotherCustomer.getId());
    }

    /*	public double computeSaving2(Tour firstTour, Tour secondTour) {
     Customer firstTourCustomer = firstTour.getLastCustomer();
     Customer secondTourCustomer= secondTour.getFirstCustomer();
     LieuELV lieuELV = firstTour.getLieuELV();
     return firstTourCustomer.getDistanceToDepot() +
     problem.getInterLECustomerDistance(lieuELV, secondTourCustomer) +
     lieuELV.getDistanceToDepot() -
     problem.getInterCustomerDistance(firstTourCustomer, secondTourCustomer);
     }
     */
    public Tour getFirstTour() {
        return firstTour;
    }

    public Tour getSecondTour() {
        return secondTour;
    }

    @Override
    public int compareTo(Saving other) {//positif si s1 est meileur
        double s1 = getSaving();
        double s2 = other.getSaving();
        //if(s1!=s2)
        return (int) (s1 - s2);
        /*else //si les gains sont égaux choisir la tournée la plus rapide
         {
         double temps1 = problem.getTemps(other.firstTour, other.secondTour);
         double temps2 = problem.getTemps(firstTour, secondTour);
         if(temps1 != temps2)
         return (int) (temps2 - temps1);
         else return 1;
         }*/
    }

    @Override
    public String toString() {
        return "{Saving: " + getSaving() + "\n\tfrom tour \n\t<" + firstTour + ">\n\tand tour \n\t<" + secondTour + ">}";
    }

    public MergeType getMergeType() {
        return mergeType;
    }

    private double gainCamions_BeforeAllocation(TourGroup tourGp, Camion firstCamion, Camion secondCamion) {
        double cost1 = 0;
        double cost2 = 0;
        if (firstCamion != null && !tourGp.checkCamionInTourGroup(firstCamion)) {// si le camion ne visite pas d'autres tournées
            cost1 = problem.getCostCamion();
        }
        if (secondCamion != null && firstCamion != secondCamion
                && !tourGp.checkCamionInTourGroup(secondCamion)) {
            cost2 = problem.getCostCamion();

        }
        //System.out.println("savingCamion:" + (cost1 + cost2 ));
        return cost1 + cost2;
    }

    private double gainOvertime_BeforeAllocation(Camion firstCamion, Camion secondCamion) {
        if (firstCamion == null || secondCamion == null) {
            return 0;
        }
        double ov1 = 0, ov2 = 0;
        double firstTemps = firstTour.getTemps();
        double secondTemps = secondTour.getTemps();
        if (firstCamion != secondCamion) {
            double tempsFirstCamion = tourGroup.getTemps(firstCamion);
            double tempsSecondCamion = tourGroup.getTemps(secondCamion);
            ov1 = tourGroup.getOvertime(tempsFirstCamion+ firstTemps)
                    - tourGroup.getOvertime(tempsFirstCamion);
            ov1 *= (problem.getPenaltyOvertime() + tourGroup.getOvertime(tempsFirstCamion));
            ov2 = tourGroup.getOvertime(tempsSecondCamion + secondTemps)
                    - tourGroup.getOvertime(tempsSecondCamion);
            ov2 *= (problem.getPenaltyOvertime() + tourGroup.getOvertime(tempsSecondCamion));
        } else {
            double tempsFirstCamion = tourGroup.getTemps(firstCamion);
            ov1 = tourGroup.getOvertime(tempsFirstCamion + firstTemps + secondTemps)
                    - tourGroup.getOvertime(tempsFirstCamion);
            ov1 *= (problem.getPenaltyOvertime() + tourGroup.getOvertime(tempsFirstCamion));
        }
        //System.out.println("Ov1:" + ov1);
        //System.out.println("Ov2:" + ov2);
        return ov1 + ov2;
    }
}
