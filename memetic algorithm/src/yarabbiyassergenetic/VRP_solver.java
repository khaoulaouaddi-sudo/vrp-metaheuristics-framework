/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yarabbiyassergenetic;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author asus
 */
public class VRP_solver {
    private VRPS problem;
    private VRPD problemD;
    TourGroup sol_preced;
     ArrayList<Camion> camions =new ArrayList<Camion>();
     ArrayList<Customer> dynamic =new ArrayList<Customer>();
     public double temps_service=0;
     private double temps_total_voyage=0;
    private double temps_total_dernier_voyage;
     public int populationSize = 30;
     public int maxGenwithoutAmelioration = 2000;
       public int lastGeneration = 1;
       public double mutationProbability = 0;
        public double croisementProbability = 0.6;
    public int maxGenerations = 2000;//5000;
      private Random rnd = new Random();
      public double time_slice=0;
      public int nb_mutation = 0;
      public ArrayList<Customer> clients_old = new  ArrayList<Customer>();
    public ArrayList<Depotfictif> DEPO_FIC = new  ArrayList<Depotfictif>();

    public ArrayList<Customer> getDynamic() {
        return dynamic;
    }

    public void setDynamic(ArrayList<Customer> dynamic) {
        this.dynamic = dynamic;
    }

    public TourGroup getSol_preced() {
        return sol_preced;
    }

    public void setSol_preced(TourGroup sol_preced) {
        this.sol_preced = sol_preced;
    }

    public VRPS getProblem() {
        return problem;
    }

    public void setProblem(VRPS problem) {
        this.problem = problem;
    }

    public double getTemps_service() {
        return temps_service;
    }

    public void setTemps_service(double temps_service) {
        this.temps_service = temps_service;
    }
    

    public VRPD getProblemD() {
        return problemD;
    }

    public void setProblemD(VRPD problemD) {
        this.problemD = problemD;
    }

    public double getTime_slice() {
        return time_slice;
    }

    public void setTime_slice(double time_slice) {
        this.time_slice = time_slice;
    }
    

    public ArrayList<Camion> getCamions() {
        return camions;
    }

    public void setCamions(ArrayList<Camion> camions) {
        this.camions = camions;
    }

    public double getTemps_total_voyage() {
        return temps_total_voyage;
    }

    public void setTemps_total_voyage(double temps_total_voyage) {
        this.temps_total_voyage = temps_total_voyage;
    }
   public int calculer_trg(TourGroup trg){
       int k=0;
       int m=0;
       int n=0;
       for (int i=0;i<trg.getTours().size();i++)
    {
       if (trg.getTours().get(i).getId_fictif()!=0)
       {System.out.println("haaa depot fictif machi nuuuuuuuuuul: "+trg.getTours().get(i).getId_fictif()); 
        m=m+1; }
       else n=n+1;
       k=k+trg.getTours().get(i).getCustomers().size();
        
    }
         System.out.println("taille tougroupe:"+k); 
         System.out.println("nommmmmmmmmmmmmmmmmmmmmmmbreeeeeee dépot fictif non null:"+m); 
          System.out.println("nommmmmmmmmmmmmmmmmmmmmmmbreeeeeee dépot fictif nullll:"+n); 
         return k;
   }
public void afficher_solution_dynamic(){
    for (int i=0;i<this.sol_preced.getTours().size();i++)
    {
        System.out.println("Tour:"+i );
        System.out.println("Id fictif final tour :" +this.sol_preced.getTours().get(i).getId_fictif());
        for (int j=0;j<this.sol_preced.getTours().get(i).getCustomers().size();j++)
        {
            System.out.println("Customer:"+this.sol_preced.getTours().get(i).getCustomers().get(j).id ); 
        }
        
    }
    for (int j=0;j<this.DEPO_FIC.size();j++)
        {
            System.out.println("depo fic:"+this.DEPO_FIC.get(j).getId_client_fictif()); 
        }
    
}
public void afficher_solution(){
    for (int i=0;i<this.sol_preced.getTours().size();i++)
    {
        System.out.println("Tour:"+i );
        System.out.println("Id fictif tour :" +this.sol_preced.getTours().get(i).getId_fictif());
        for (int j=0;j<this.sol_preced.getTours().get(i).getCustomers().size();j++)
        {
            System.out.println("Customer:"+this.sol_preced.getTours().get(i).getCustomers().get(j).id ); 
        }
        
    }
    for (int j=0;j<this.DEPO_FIC.size();j++)
        {
            System.out.println("depo fic:"+this.DEPO_FIC.get(j).getId_client_fictif()); 
        }
    
}
public void afficher_best(){
    for (int i=0;i<this.getBest().getTourGroup().getTours().size();i++)
    {
        System.out.println("Tour:"+i );
        System.out.println("Id fictif tour :" +this.getBest().getTourGroup().getTours().get(i).getId_fictif());
        for (int j=0;j<this.getBest().getTourGroup().getTours().get(i).getCustomers().size();j++)
        {
            System.out.println("Customer:"+this.getBest().getTourGroup().getTours().get(i).getCustomers().get(j).id ); 
        }
        
    }
    for (int j=0;j<this.DEPO_FIC.size();j++)
        {
            System.out.println("depo fic:"+this.DEPO_FIC.get(j).getId_client_fictif()); 
        }
    
}
    public double getTemps_total_dernier_voyage() {
         
        return this.getBest().getTourGroup().getdistance_dynamic();
    }

    public void setTemps_total_dernier_voyage(double temps_total_dernier_voyage) {
        this.temps_total_dernier_voyage = temps_total_dernier_voyage;
    }
    
    

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public int getMaxGenwithoutAmelioration() {
        return maxGenwithoutAmelioration;
    }

    public void setMaxGenwithoutAmelioration(int maxGenwithoutAmelioration) {
        this.maxGenwithoutAmelioration = maxGenwithoutAmelioration;
    }

    public int getLastGeneration() {
        return lastGeneration;
    }

    public void setLastGeneration(int lastGeneration) {
        this.lastGeneration = lastGeneration;
    }

    public double getMutationProbability() {
        return mutationProbability;
    }

    public void setMutationProbability(double mutationProbability) {
        this.mutationProbability = mutationProbability;
    }

    public double getCroisementProbability() {
        return croisementProbability;
    }

    public void setCroisementProbability(double croisementProbability) {
        this.croisementProbability = croisementProbability;
    }

    public int getMaxGenerations() {
        return maxGenerations;
    }

    public void setMaxGenerations(int maxGenerations) {
        this.maxGenerations = maxGenerations;
    }

    public Random getRnd() {
        return rnd;
    }

    public void setRnd(Random rnd) {
        this.rnd = rnd;
    }

    public int getNb_mutation() {
        return nb_mutation;
    }

    public void setNb_mutation(int nb_mutation) {
        this.nb_mutation = nb_mutation;
    }

    public ArrayList<Customer> getClients_old() {
        return clients_old;
    }

    public void setClients_old(ArrayList<Customer> clients_old) {
        this.clients_old = clients_old;
    }

    public ArrayList<Depotfictif> getDEPO_FIC() {
        return DEPO_FIC;
    }
    public ArrayList<Depotfictif> getDEPO_FIC_dynamic() {
        return DEPO_FIC;
    }

    public void setDEPO_FIC(ArrayList<Depotfictif> DEPO_FIC) {
        this.DEPO_FIC = DEPO_FIC;
    }

    public ArrayList<TourGroup> getPopulation() {
        return population;
    }

    public void setPopulation(ArrayList<TourGroup> population) {
        this.population = population;
    }
    
      private ArrayList<TourGroup> population = new ArrayList<TourGroup>();    
        public class Solution implements Comparable<Solution> {

        TourGroup tourGroup;
        long CPU;
        int generation;

        public Solution(TourGroup tourGroup,long CPU, int generation) {
            this.tourGroup = tourGroup;
            this.CPU = CPU;
            this.generation = generation;
        }
      

       
        

        public TourGroup getTourGroup() {
            return tourGroup;
        }

        public long getCPU() {
            return CPU;
        }

        public int getGeneration() {
            return generation;
        }

        @Override
        public int compareTo(Solution solution) {
            return this.tourGroup.compareTo(solution.tourGroup);
        }
         public int compareTo_dynamic(Solution solution) {
            return this.tourGroup.compareTo_dynamic(solution.tourGroup);
        }
        
    }
          public Solution best;
         public Solution getBest() {
            return best;
        }

        public void setBest(Solution best) {
            this.best = best;
        }
       public enum InitiationType {
	CW, // Clarck & Wright
	SEQ, // Intsertion séquentielle
	PPV //Plus proche voisin 
        }
        public VRP_solver (VRPS problem) {
		super();
		this.problem = problem;
	}
         public VRP_solver (VRPD problemD) {
		super();
		this.problemD = problemD;
	}
	
      public TourGroup solve(TourGroup tourGroupCW){
             /***************Initialisation************************/
            ArrayList<Solution> bests = new ArrayList<Solution>();
            //boolean feasibleFound = false;
            //System.out.println("/********Initialisation**********/");
            //double lastcost;
             population.clear();
            int generation = 1,count = 0;
            boolean feasibleFound = false;
            boolean transformationDone;
            //problem.extendMaxTemps(1);
            long dateDeb = new Date().getTime();
            long dateFin , duree = 0;
            TourGroup tourGroupCW1 = null;//new ClarkAndWrightDistanceVrpSolver(problem).solve();
            //createInitialPopulation(tourGroupCW1,InitiationType.PPV,InitiationType.SEQ,1);
             System.out.println("/befooore create/");
             //tester_duplicate_camion(this.problem.camions);
            createInitialPop();
            //createInitialPopulation(tourGroupCW1,InitiationType.PPV,InitiationType.SEQ,1);
            
              System.out.println("/********Initialisation**********/");
            //createInitialPopulation(tourGroupCW1,InitiationType.SEQ,null,1);// pour E10
           // afficherPopulation(population.size());       
            Collections.sort(population);
            //afficherPopulation(population.size());       
            System.out.println("Moyenne " + moyennePopulation());
            while (generation <= maxGenerations && generation-lastGeneration<maxGenwithoutAmelioration) {
                System.out.println("genération: "+generation);
                //tester_duplicate_camion(this.problem.camions);
                for (int m=0;m<population.size();m++)
                {
                 // test_capacite_camion_initial(this.problem.camions.get(0),population.get(m));  
                //verify_population(this.problem.getCustomers(), population.get(m));
                    //tester_duplicate_camion(this.problem.camions);
                    
                //System.out.println("ha test dyal camions dyal kolla waèed f population case statique: ");
                // tester_duplicate_camion(population.get(m).getCamions());
                }
               /* for (int i=0; i<population.size();i++)
               {
                 System.out.println("taille tournée population initial de cette génération"+ population.get(i).getTours().size())  ;
               }*/
                if(generation%500==0){
                    System.out.println("*************best for Generation " + generation);
                    System.out.println("best " + population.get(0));
                    System.out.println("***********************************************");
                    //double bestCost = population.get(0).getObj1();
                }
                
               
                TourGroup trGroupFather = null; 
                TourGroup trGroupMother = null; 

                do{
                    trGroupFather = selectRandomFather(population.size());//20
                    trGroupMother = selectRandomFather(population.size());
                }
                while(trGroupFather.similarTourGroup(trGroupMother));
                //System.out.println("après selection lawla");
                   // testpopulation();
                        
                         
                         
                //initialiser les enfants avec les parents;
                ArrayList<TourGroup> children = new ArrayList<TourGroup> ();
                /******************CROISEMENT***************************/
                // croiser les deux meilleurs individus, et ajouter les enfants � la nouvelle g�n�ration
                System.out.println("/********Croisement**********/");
                //Container myContainer = new Container();
                transformationDone = false;
                if (rnd.nextDouble() <= croisementProbability) {
                    //System.out.println("****croisement****");
                    children.clear();
                    if(problem.getCamions().size()==1)
                    {  children = new TourGroup(problem).crossOverTours_twoPositions(trGroupFather, trGroupMother);
                   // System.out.println("cas avec croisement taille tournées attribuée camion : "+ children.get(0).getCamions().get(0).getTournee_attribuees().size());
             
                    System.out.println("après croisement");
                    //for (TourGroup child : children)
                    //{
                     //System.out.println("haa get temps dernier voyage b get distance dynamic : "+  child.getdistance());
                //System.out.println("haa get temps b calculer get camion dyal tour groupe  : "+ child.getCamions().get(0).calucler_temps_total_tournees());
                    //}
                    //testpopulation();
                   
                    }
                                //crossOver(trGroupFather, trGroupMother,CrossoverType.OneToOne);
                    else
                        children = new TourGroup(problem).crossOver_LTR(trGroupFather, trGroupMother);
                    /*for (TourGroup trg: children) 
                    {
                    
                        //trg.removeemptytours();
                    }*/
                   // System.out.println("arprès crossoooverrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
                                //crossOver(trGroupFather, trGroupMother,CrossoverType.LTR);
                    if(!children.isEmpty()){
                        
                       // System.out.println("arprès crossoooverrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
                        transformationDone = true;
                    }
                    
                }
                else {
                    TourGroup child1 = trGroupFather.clone();//new TourGroup(problem);
                    TourGroup child2 = trGroupMother.clone();//new TourGroup(problem);
                    children.add(child1);
                   // testcamions(child1);
                    children.add(child2);
                   /* System.out.println("cas sans croisement taille tournées attribuée camion : "+ child1.getCamions().get(0).getTournee_attribuees().size());
                  System.out.println("haa get temps dernier voyage b get distance dynamic : "+  child1.getdistance());
                System.out.println("haa get temps b calculer get camion dyal tour groupe  : "+ child1.getCamions().get(0).calucler_temps_total_tournees());
              System.out.println("haa get temps dernier voyage b get distance dynamic : "+  child2.getdistance());
                System.out.println("haa get temps b calculer get camion dyal tour groupe  : "+ child2.getCamions().get(0).calucler_temps_total_tournees());
              */
                     //testcamions(child2);
                   // System.out.println("makaynch croisement");
                    //testpopulation();
                   
                }
                
                /******************FIN CROISEMENT***************************/
                
                /******************MUTATION******************************/
                boolean mutationDone = false;
                if (rnd.nextDouble() <= mutationProbability) {
                System.out.println("/********Mutation**********/");
                for (TourGroup child:children) {
                        
                            child.mutate_noConstraints();
                          /*  System.out.println("après mutation taille tournées attribuée camion : "+ child.getCamions().get(0).getTournee_attribuees().size());
             System.out.println("haa get temps dernier voyage b get distance dynamic : "+  child.getdistance());
                System.out.println("haa get temps b calculer get camion dyal tour groupe  : "+ child.getCamions().get(0).calucler_temps_total_tournees());
              */
                             //testcamions(child);
                           // System.out.println("après mutation");
                           // testpopulation();
                            
                   
                    //child.removeemptytours();
                  
                        transformationDone = true;
                        mutationDone = true;
                        nb_mutation++;
                    }
                
                
                
                }
                /******************FIN MUTATION******************************/
             
                /******************Correction******************************/
                if(transformationDone == true){
                System.out.println("/********Insertion**********/");
                for (TourGroup trgChild:children) {
                  //  System.out.println("before improve ");
                    trgChild.improveTours();
                  /*  System.out.println("après improve taille tournées attribuée camion : "+ trgChild.getCamions().get(0).getTournee_attribuees().size());
             System.out.println("haa get temps dernier voyage b get distance dynamic : "+  trgChild.getdistance());
                System.out.println("haa get temps b calculer get camion dyal tour groupe  : "+ trgChild.getCamions().get(0).calucler_temps_total_tournees());
              */
                    //System.out.println("après improve lawla");
                    //testpopulation();
                   
                         
                   // System.out.println("after improve ");
            //System.out.println("hooooooooooooooooooooooooooooooooo :"+trgChild.getTours().size());
                                 
                  
                    
                    if(mutationDone==true)
                    {
                      //System.out.println("before spliiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiit:");
                    
                        trgChild.splitTours();
                        /*System.out.println("après split taille tournées attribuée camion : "+ trgChild.getCamions().get(0).getTournee_attribuees().size());
             System.out.println("haa get temps dernier voyage b get distance dynamic : "+  trgChild.getdistance());
                System.out.println("haa get temps b calculer get camion dyal tour groupe  : "+ trgChild.getCamions().get(0).calucler_temps_total_tournees());
              */
                        // testcamions(trgChild);
                          
                    //testpopulation();
                        
                        //trgChild.removeemptytours();
                        //trgChild.CheckCamionsConstraint();
                       //System.out.println("after split:");
                    }
                                    
                     trgChild.removeemptytours();
                      //System.out.println("après remoooove emptytours");
                   // testpopulation();
                     
                    // System.out.println("after remooooooooooooooooooooooooooooooove");
                      
                       trgChild.AmeliorateBySaving();
                     /*  System.out.println("après ameliorate taille tournées attribuée camion : "+ trgChild.getCamions().get(0).getTournee_attribuees().size());
             System.out.println("haa get temps dernier voyage b get distance dynamic : "+  trgChild.getdistance());
                System.out.println("haa get temps b calculer get camion dyal tour groupe  : "+ trgChild.getCamions().get(0).calucler_temps_total_tournees());
              */
                       // testcamions(trgChild);
                      // System.out.println("après ameliorate b saving");
                   // testpopulation();
                         
                     
                    // System.out.println("after ameliorate ");
                      //trgChild.removeemptytours();
                      trgChild.two_opt(problem.getNbImprove());
                    /*  System.out.println("après twopt taille tournées attribuée camion : "+ trgChild.getCamions().get(0).getTournee_attribuees().size());
             System.out.println("haa get temps dernier voyage b get distance dynamic : "+  trgChild.getdistance());
                System.out.println("haa get temps b calculer get camion dyal tour groupe  : "+ trgChild.getCamions().get(0).calucler_temps_total_tournees());
             */
//testcamions(trgChild);
                     // System.out.println("après twoopt");
                    //();
                      
                       /*if (trgChild.getTours().isEmpty())
                            {
                                 System.out.println("hoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
                                               
                            }*/
                  
                     
                     
                    if( (trgChild.CheckCamionsConstraint() 
                                && population.get(population.size()-1).compareTo(trgChild)>=0) //comaprer avec le dernier
                                && (checkInPopulation(trgChild,population)==false))
                        {
                            
                      //System.out.println("dans la condition if taille tournées attribuée camion : "+ trgChild.getCamions().get(0).getTournee_attribuees().size());
                     //  System.out.println("dans la condition if taille camion to use : "+ trgChild.getCamionsToUse().size());
                            //supprimer le plus mauvais
                            population.remove(population.size()-1);
                            
                            int position = insertChildInPopulation(population, trgChild);
                            
                            if(trgChild.getLTR()<=problem.getMaxTemps()){
                                bests.add(new Solution(trgChild,duree,lastGeneration));
                                
                            }
                            count++;
                            if(position==0){
                               
                                lastGeneration = generation;
                                dateFin = new Date().getTime();
                                duree = dateFin - dateDeb;
                                System.out.println("Generation " + generation+", nb insertion " + count+", obj1 " + trgChild.getObj1()+", obj2 " + trgChild.getObj1()+", dist " + trgChild.getdistance()+", costCamions " +", iterationBest " + lastGeneration+", duree " + duree+"ms");
                                System.out.println("best:"+trgChild);
                            }

                     // System.out.println("khrejna men if lawla");
                    //testpopulation();
                        }
                    //System.out.println("khrejna men if 2");
                    //testpopulation();
                    
                       
                    }
                //System.out.println("khrejna men if 3");
                    //testpopulation();
                }
                //System.out.println("wsalna l generaion ++");
                    //testpopulation();
                //JOptionPane.showMessageDialog(null, myContainer);
                    
                /******************FIN INSERTION******************************/
                generation ++;
                //afficherPopulation(poulation.size());
            }
            /**Aficher les 10 permeiers**/
            afficherPopulation(population.size());
            System.out.println("Moyenne " + moyennePopulation());
            System.out.println("count " + count);
            //System.out.println("Genetic algorithm endend after " + maxGenerations + " generations.");
            //System.out.println("Best TourGroup: " + population.get(population.size() - 1));
            //Collections.sort(population);
            TourGroup best =  population.get(0);
            /* System.out.println("hadchi flakher best taille tournées attribuée camion : "+ best.getCamions().get(0).getTournee_attribuees().size());
              System.out.println("hadchi flakher bests get0 taille tournées attribuée camion : "+ bests.get(0).getTourGroup().getCamions().get(0).getTournee_attribuees().size());
             */        
            //new TspSolver(problem).solve(best);
            //System.out.println("End of generation " + generation+" " + lastGeneration);
                 if(bests.isEmpty())
                
                bests.add(new Solution(best,duree,lastGeneration));
                 System.out.println("taille bestssssssssssssssssssssssssssssssssss:  "+bests.size() );
        //System.out.println("hadchi flakher après if etavant collection sort bests get0 taille tournées attribuée camion : "+ bests.get(0).getTourGroup().getCamions().get(0).getTournee_attribuees().size());
              
            Collections.sort (bests);
        //System.out.println("hadchi flakher bests get0 après collection sort taille tournées attribuée camion : "+ bests.get(0).getTourGroup().getCamions().get(0).getTournee_attribuees().size());
              
            this.setBest(bests.get(0));
        //System.out.println("hadchi flakher best après setbest get0 taille tournées attribuée camion : "+ this.getBest().getTourGroup().getCamions().get(0).getTournee_attribuees().size());
              
            this.afficher_best();
            //System.out.println("hadchi flakher best après afficher get0 taille tournées attribuée camion : "+ this.getBest().getTourGroup().getCamions().get(0).getTournee_attribuees().size());
        
            this.get_depo_fic();
            this.afficher_solution();
            bests.clear();
            return sol_preced;
	}
        public TourGroup solve_dynamic(TourGroup sol_preced){
             /***************Initialisation************************/
            ArrayList<Solution> bests = new ArrayList<Solution>();
           
            //boolean feasibleFound = false;
            //System.out.println("/********Initialisation**********/");
            //double lastcost;
             population.clear();
            int generation = 1,count = 0;
            boolean feasibleFound = false;
            boolean transformationDone;
            //problem.extendMaxTemps(1);
            long dateDeb = new Date().getTime();
            long dateFin , duree = 0;
          //  initialiser_system();
            //TourGroup tourGroupCW1 = null;//new ClarkAndWrightDistanceVrpSolver(problem).solve();
            //createInitialPopulation(tourGroupCW1,InitiationType.PPV,InitiationType.SEQ,1);
            System.out.println("befooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooore create");
           // testpopulation();
            TourGroup trgrrr = new TourGroup(problemD);
            trgrrr.getTours().clear();
            for (Tour t:sol_preced.getTours() )
            {
                trgrrr.getTours().add(t);
            }  
                
            sol_preced.setProblemD(problemD);
           
                 // test_capacite_camion_initial(this.problemD.camions_dynamic.get(0),population.get(m));  
              int id_fic=0;
          for (int j=0;j<trgrrr.getTours().size();j++)
          {
              if (trgrrr.getTours().get(j).getId_fictif()!=0)
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
         // System.out.println("haaaaaaaaaaaaaaaaaaaaaa 7riiiraaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa :"+trgrrr.getTours().get(100000));    
        
         }     
               // System.out.println("ha test dyal camions dyal kolla waèed f population : ");
                 //tester_duplicate_camion(population.get(m).getCamions());
                //System.out.println("haa get temps b calculer get camion dyal tour groupe  : "+ population.get(m).getCamions().get(0).calucler_temps_total_tournees_dynamic());
    
                
            createInitialPop_dynamic(trgrrr);
             //System.out.println("after create");
             // bests.clear();
           // testpopulation();
            //createInitialPopulation(tourGroupCW1,InitiationType.PPV,InitiationType.SEQ,1);
            
              System.out.println("/********Initialisation**********/");
            //createInitialPopulation(tourGroupCW1,InitiationType.SEQ,null,1);// pour E10
           // afficherPopulation(population.size());       
            sort_dynamic(population);
            //afficherPopulation(population.size());       
            System.out.println("Moyenne " + moyennePopulation_dynamic());
            while (generation <= maxGenerations && generation-lastGeneration<maxGenwithoutAmelioration) {
         System.out.println("genération: "+generation);
                 for (int m=0;m<population.size();m++)
                {
                  test_capacite_camion_initial(this.problemD.camions_dynamic.get(0),population.get(m));  
                verify_population(this.problemD.getCustomers_dynamic(), population.get(m));
                    tester_duplicate_camion(this.problemD.camions_dynamic);
                    
               // System.out.println("ha test dyal camions dyal kolla waèed f population : ");
                 tester_duplicate_camion(population.get(m).getCamions());
                //System.out.println("haa get temps b calculer get camion dyal tour groupe  : "+ population.get(m).getCamions().get(0).calucler_temps_total_tournees_dynamic());
    
                }
               /* for (int i=0; i<population.size();i++)
               {
                 System.out.println("taille tournée population initial de cette génération"+ population.get(i).getTours().size())  ;
               }*/
                if(generation%500==0){
                    System.out.println("*************best for Generation " + generation);
                    System.out.println("best " + population.get(0));
                    System.out.println("***********************************************");
                    //double bestCost = population.get(0).getObj1();
                }
                
                
                TourGroup trGroupFather = null; 
                TourGroup trGroupMother = null; 

                do{
                    trGroupFather = selectRandomFather_dynamic(population.size());//20
                    trGroupMother = selectRandomFather_dynamic(population.size());
                }
                while(trGroupFather.similarTourGroup_dynamic(trGroupMother));
               //test_capacite_camion(this.problemD.camions_dynamic.get(0),trGroupMother); 
               //test_capacite_camion(this.problemD.camions_dynamic.get(0),trGroupFather);  
              
               // System.out.println("après selection lawla");
                //calculer_trg(trGroupFather);
                //calculer_trg(trGroupMother);
                   //testpopulation();
                   //trGroupFather.afficher_camion();
                  // trGroupMother.afficher_camion();
                   //verify_population(this.problemD.getCustomers_dynamic(), trGroupFather);
                   //verify_population(this.problemD.getCustomers_dynamic(), trGroupMother);
                   
                //initialiser les enfants avec les parents;
                ArrayList<TourGroup> children = new ArrayList<TourGroup> ();
                /******************CROISEMENT***************************/
                // croiser les deux meilleurs individus, et ajouter les enfants � la nouvelle g�n�ration
                System.out.println("/********Croisement**********/");
                //Container myContainer = new Container();
                transformationDone = false;
                if (rnd.nextDouble() <= croisementProbability) {
                    //System.out.println("****croisement****");
                    children.clear();
                    if(problemD.getCamions_dynamic().size()==1)
                    {  children = new TourGroup(problemD).crossOverTours_twoPositions_dynamic(trGroupFather, trGroupMother);
                        
                   /*for (TourGroup trg: children)
                    {
                        test_capacite_camion(this.problemD.camions_dynamic.get(0),trg); 
                   // verify_population(this.problemD.getCustomers_dynamic(), trg);
                    //System.out.println("haa get temps dernier voyage b get distance dynamic : "+ trg.getdistance_dynamic());
                    //System.out.println("haa get temps b calculer get camion dyal tour groupe  : "+ trg.getCamions().get(0).calucler_temps_total_tournees_dynamic());
    
                    }*/
                    //transformationDone = true;
                    //System.out.println("après croisement");
                    /*for (TourGroup child: children)
                    {
                        calculer_trg(child);
                    }*/
                   //testpopulation();
                   
                    }
                                //crossOver(trGroupFather, trGroupMother,CrossoverType.OneToOne);
                    else
                        children = new TourGroup(problemD).crossOver_LTR_dynamic(trGroupFather, trGroupMother);
                  /*  System.out.println("après croisement");
                    for (TourGroup child: children)
                    {
                        calculer_trg(child);
                    }*/
                   
                    if(!children.isEmpty()){
                        
                     
                        transformationDone = true;
                    }
                    
                }
                else {
                    TourGroup child1 = trGroupFather.clone_dynamic();//new TourGroup(problem);
                    TourGroup child2 = trGroupMother.clone_dynamic();//new TourGroup(problem);
                    children.add(child1);
                    children.add(child2);
                    System.out.println("makaynch croisement");
                   /* for (TourGroup trg: children)
                    {
                        test_capacite_camion(this.problemD.camions_dynamic.get(0),trg); 
                   // verify_population(this.problemD.getCustomers_dynamic(), trg);
                    //System.out.println("haa get temps dernier voyage b get distance dynamic : "+ trg.getdistance_dynamic());
                    //System.out.println("haa get temps b calculer get camion dyal tour groupe  : "+ trg.getCamions().get(0).calucler_temps_total_tournees_dynamic());
    
                    }*/
                  // testpopulation();
                   
                }
                
                /******************FIN CROISEMENT***************************/
                
                /******************MUTATION******************************/
                boolean mutationDone = false;
                if (rnd.nextDouble() <= mutationProbability) {
                System.out.println("/********Mutation**********/");
                for (TourGroup child:children) {
                            //child.afficher_camion();
                            child.mutate_noConstraints_dynamic();
                             //test_capacite_camion(this.problemD.camions_dynamic.get(0),child); 
                  
                             
                   // verify_population(this.problemD.getCustomers_dynamic(), child);
                   // System.out.println("haa get temps dernier voyage b get distance dynamic : "+ child.getdistance_dynamic());
                //System.out.println("haa get temps b calculer get camion dyal tour groupe  : "+ child.getCamions().get(0).calucler_temps_total_tournees_dynamic());
    
                    
                           // System.out.println("après mutation");
                            //calculer_trg(child);
                           // testpopulation();
                            
                   
                    //child.removeemptytours();
                  
                        transformationDone = true;
                        mutationDone = true;
                        nb_mutation++;
                    }
               
                
                }
              /*  else {
                     for (TourGroup trgChild:children) {
                         trgChild.allocateTours2_dynamic();
                     }
                    
                }*/
                /******************FIN MUTATION******************************/
             
                /******************Correction******************************/
                if(transformationDone == true){
                System.out.println("/********Insertion**********/");
                for (TourGroup trgChild:children) {
                  System.out.println("before improve ");
                    trgChild.improveTours_dynamic();
                     
                    System.out.println("après improve lawla");
                   //test_capacite_camion(this.problemD.camions_dynamic.get(0),trgChild); 
                  
                    //verify_population(this.problemD.getCustomers_dynamic(), trgChild);
                     //System.out.println("haa get temps dernier voyage b get distance dynamic : "+ trgChild.getdistance_dynamic());
                     //System.out.println("haa get temps b calculer get camion dyal tour groupe  : "+ trgChild.getCamions().get(0).calucler_temps_total_tournees_dynamic());
    
                    //calculer_trg(trgChild);
                   //testpopulation();
                   
                            
                   
                    //trgChild.two_opt(problem.getNbImprove());
                    //System.out.println("after improve ");
           // System.out.println("hooooooooooooooooooooooooooooooooo :"+trgChild.getTours().size());
                                 
                  
                    
                   // if(mutationDone==true)
                    //{
                      //System.out.println("before spliiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiit:");
                    
                        trgChild.splitTours_dynamic();
                         
                           System.out.println("après seplitt");
                          //test_capacite_camion(this.problemD.camions_dynamic.get(0),trgChild); 
                  
                           //verify_population(this.problemD.getCustomers_dynamic(), trgChild);
                           //calculer_trg(trgChild);
                          // trgChild.allocateTours2_dynamic();
                     // testpopulation();
                         /*if (trgChild.getTours().isEmpty())
                            {
                                 System.out.println("hoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
                                               
                            }*/
                        //trgChild.removeemptytours();
                        //trgChild.CheckCamionsConstraint();
                       //System.out.println("after split:");
                   // }
                                    
                     
                     
                     //System.out.println("after ");
                      
                       trgChild.AmeliorateBySaving_dynamic();
                       
                       System.out.println("après ameliorate b saving");
                      //test_capacite_camion(this.problemD.camions_dynamic.get(0),trgChild); 
                  
                       //verify_population(this.problemD.getCustomers_dynamic(), trgChild);
                       // System.out.println("haa get temps dernier voyage b get distance dynamic : "+ trgChild.getdistance_dynamic());
                       // System.out.println("haa get temps b calculer get camion dyal tour groupe  : "+ trgChild.getCamions().get(0).calucler_temps_total_tournees_dynamic());
    
                       //calculer_trg(trgChild);
                       //testpopulation();
                        /* if (trgChild.getTours().isEmpty())
                            {
                                 System.out.println("hoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
                                               
                            }
                     */
                     
                     trgChild.allocateTours2_dynamic();
                     System.out.println("after allocate ");
                    //test_capacite_camion(this.problemD.camions_dynamic.get(0),trgChild); 
                  
                     //verify_population(this.problemD.getCustomers_dynamic(), trgChild);
                      //System.out.println("haa get temps dernier voyage b get distance dynamic : "+ trgChild.getdistance_dynamic());
                      //System.out.println("haa get temps b calculer get camion dyal tour groupe  : "+ trgChild.getCamions().get(0).calucler_temps_total_tournees_dynamic());
    
                      //calculer_trg(trgChild);
                      trgChild.two_opt_dynamic(problemD.getNbImprove());
                       
                      //trgChild.allocateTours2_dynamic();
                      System.out.println("après twoopt");
                     //test_capacite_camion(this.problemD.camions_dynamic.get(0),trgChild); 
                  
                     // verify_population(this.problemD.getCustomers_dynamic(), trgChild);
                       //System.out.println("haa get temps dernier voyage b get distance dynamic : "+ trgChild.getdistance_dynamic());
                       //System.out.println("haa get temps b calculer get camion dyal tour groupe  : "+ trgChild.getCamions().get(0).calucler_temps_total_tournees_dynamic());
    
                     //calculer_trg(trgChild);
                       /*if (trgChild.getTours().isEmpty())
                            {
                                 System.out.println("hoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
                                               
                            }*/
                  
                     
                     
                    if( (trgChild.CheckCamionsConstraint()
                                && population.get(population.size()-1).compareTo_dynamic(trgChild)>=0) //comaprer avec le dernier
                                && (checkInPopulation_dynamic(trgChild,population)==false))
                        {
                           
                       //System.out.println("dans la condition if taille camion du problem : "+ trgChild.getProblemD().getCamions_dynamic().size());
                     //  System.out.println("dans la condition if taille camion to use : "+ trgChild.getCamionsToUse().size());
                            //supprimer le plus mauvais
                            population.remove(population.size()-1);
                          
                            int position = insertChildInPopulation_dynamic(population, trgChild);
                            
                            if(trgChild.getLTR_dynamic()<=problemD.getMaxTemps_dynamic()){
                                bests.add(new Solution(trgChild,duree,lastGeneration));
                               
                            }
                            count++;
                            if(position==0){
                               
                                lastGeneration = generation;
                                dateFin = new Date().getTime();
                                duree = dateFin - dateDeb;
                                System.out.println("Generation " + generation+", nb insertion " + count+", LTR" + trgChild.getLTR_dynamic()+", distance " + trgChild.getdistance_dynamic() +", iterationBest " + lastGeneration+", duree " + duree+"ms");
                                System.out.println("best:"+trgChild);
                                //calculer_trg(trgChild);
                            }

                    // System.out.println("khrejna men if lawla");
                    //testpopulation();
                        }
                    //System.out.println("khrejna men if 2");
                    //testpopulation();
                    
                       
                    }
                //System.out.println("khrejna men if 3");
                 //testpopulation();
                }
                //System.out.println("wsalna l generaion ++");
                 // testpopulation();
                //JOptionPane.showMessageDialog(null, myContainer);
                    
                /******************FIN INSERTION******************************/
                generation ++;
                //afficherPopulation(poulation.size());
            }
            /**Aficher les 10 permeiers**/
            afficherPopulation(population.size());
            System.out.println("Moyenne " + moyennePopulation_dynamic());
            System.out.println("count " + count);
            //System.out.println("Genetic algorithm endend after " + maxGenerations + " generations.");
            //System.out.println("Best TourGroup: " + population.get(population.size() - 1));
            //Collections.sort(population);
            TourGroup best =  population.get(0);
            //new TspSolver(problem).solve(best);
            //System.out.println("End of generation " + generation+" " + lastGeneration);
            if(bests.isEmpty())
                
                bests.add(new Solution(best,duree,lastGeneration));
             System.out.println("taille bestssssssssssssssssssssssssssssssssss:  "+bests.size() );
           
            sort_dynamic_sol(bests);
            
            this.setBest(bests.get(0));
           // calculer_trg(best);
            this.afficher_best();
            this.get_depo_fic_dynamic();
           bests.clear();
           this.afficher_solution_dynamic();
            return sol_preced;
	}
       public TourGroup solve_dynamic_definitif(TourGroup sol_preced){
             /***************Initialisation************************/
            ArrayList<Solution> bests = new ArrayList<Solution>();
           
            //boolean feasibleFound = false;
            //System.out.println("/********Initialisation**********/");
            //double lastcost;
             population.clear();
            int generation = 1,count = 0;
            boolean feasibleFound = false;
            boolean transformationDone;
            //problem.extendMaxTemps(1);
            long dateDeb = new Date().getTime();
            long dateFin , duree = 0;
          //  initialiser_system();
            //TourGroup tourGroupCW1 = null;//new ClarkAndWrightDistanceVrpSolver(problem).solve();
            //createInitialPopulation(tourGroupCW1,InitiationType.PPV,InitiationType.SEQ,1);
            System.out.println("befooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooore create");
           // testpopulation();
            TourGroup trgrrr = new TourGroup(problemD);
            trgrrr.getTours().clear();
            for (Tour t:sol_preced.getTours() )
            {
                trgrrr.getTours().add(t);
            }  
                
            sol_preced.setProblemD(problemD);
            createInitialPop_dynamic(trgrrr);
             //System.out.println("after create");
             // bests.clear();
           // testpopulation();
            //createInitialPopulation(tourGroupCW1,InitiationType.PPV,InitiationType.SEQ,1);
            
              System.out.println("/********Initialisation**********/");
            //createInitialPopulation(tourGroupCW1,InitiationType.SEQ,null,1);// pour E10
           // afficherPopulation(population.size());       
            sort_dynamic(population);
            //afficherPopulation(population.size());       
            System.out.println("Moyenne " + moyennePopulation_dynamic());
            while (generation <= maxGenerations && generation-lastGeneration<maxGenwithoutAmelioration) {
                System.out.println("genération: "+generation);
                /*for (int m=0;m<population.size();m++)
                {
                verify_population(this.problemD.getCustomers_dynamic(), population.get(m));
                //System.out.println("haa get temps dernier voyage b get distance dynamic : "+ population.get(m).getdistance_dynamic());
                //System.out.println("haa get temps b calculer get camion dyal tour groupe  : "+ population.get(m).getCamions().get(0).calucler_temps_total_tournees_dynamic());
    
                }*/
               /* for (int i=0; i<population.size();i++)
               {
                 System.out.println("taille tournée population initial de cette génération"+ population.get(i).getTours().size())  ;
               }*/
                if(generation%500==0){
                    System.out.println("*************best for Generation " + generation);
                    System.out.println("best " + population.get(0));
                    System.out.println("***********************************************");
                    //double bestCost = population.get(0).getObj1();
                }
                
                
                TourGroup trGroupFather = null; 
                TourGroup trGroupMother = null; 

                do{
                    trGroupFather = selectRandomFather_dynamic(population.size());//20
                    trGroupMother = selectRandomFather_dynamic(population.size());
                }
                while(trGroupFather.similarTourGroup_dynamic(trGroupMother));
               // System.out.println("après selection lawla");
                //calculer_trg(trGroupFather);
                //calculer_trg(trGroupMother);
                   //testpopulation();
                   //trGroupFather.afficher_camion();
                  // trGroupMother.afficher_camion();
                  // verify_population(this.problemD.getCustomers_dynamic(), trGroupFather);
                  // verify_population(this.problemD.getCustomers_dynamic(), trGroupMother);
                   
                //initialiser les enfants avec les parents;
                ArrayList<TourGroup> children = new ArrayList<TourGroup> ();
                /******************CROISEMENT***************************/
                // croiser les deux meilleurs individus, et ajouter les enfants � la nouvelle g�n�ration
                System.out.println("/********Croisement**********/");
                //Container myContainer = new Container();
                transformationDone = false;
                if (rnd.nextDouble() <= croisementProbability) {
                    //System.out.println("****croisement****");
                    children.clear();
                    if(problemD.getCamions_dynamic().size()==1)
                    {  children = new TourGroup(problemD).crossOverTours_twoPositions_dynamic(trGroupFather, trGroupMother);
                      
                    /*for (TourGroup trg: children)
                    {
                    verify_population(this.problemD.getCustomers_dynamic(), trg);
                    //System.out.println("haa get temps dernier voyage b get distance dynamic : "+ trg.getdistance_dynamic());
                    //System.out.println("haa get temps b calculer get camion dyal tour groupe  : "+ trg.getCamions().get(0).calucler_temps_total_tournees_dynamic());
    
                    }*/
                    //transformationDone = true;
                    //System.out.println("après croisement");
                    /*for (TourGroup child: children)
                    {
                        calculer_trg(child);
                    }*/
                   //testpopulation();
                   
                    }
                                //crossOver(trGroupFather, trGroupMother,CrossoverType.OneToOne);
                    else
                        children = new TourGroup(problemD).crossOver_LTR_dynamic(trGroupFather, trGroupMother);
                  /*  System.out.println("après croisement");
                    for (TourGroup child: children)
                    {
                        calculer_trg(child);
                    }*/
                   
                    if(!children.isEmpty()){
                        
                     
                        transformationDone = true;
                    }
                    
                }
                else {
                    TourGroup child1 = trGroupFather.clone_dynamic();//new TourGroup(problem);
                    TourGroup child2 = trGroupMother.clone_dynamic();//new TourGroup(problem);
                    children.add(child1);
                    children.add(child2);
                    //System.out.println("makaynch croisement");
                  // testpopulation();
                   
                }
                
                /******************FIN CROISEMENT***************************/
                
                /******************MUTATION******************************/
                boolean mutationDone = false;
                if (rnd.nextDouble() <= mutationProbability) {
                System.out.println("/********Mutation**********/");
                for (TourGroup child:children) {
                            //child.afficher_camion();
                            child.mutate_noConstraints_dynamic();
                             
                   // verify_population(this.problemD.getCustomers_dynamic(), child);
                   // System.out.println("haa get temps dernier voyage b get distance dynamic : "+ child.getdistance_dynamic());
                //System.out.println("haa get temps b calculer get camion dyal tour groupe  : "+ child.getCamions().get(0).calucler_temps_total_tournees_dynamic());
    
                    
                           // System.out.println("après mutation");
                            //calculer_trg(child);
                           // testpopulation();
                            
                   
                    //child.removeemptytours();
                  
                        transformationDone = true;
                        mutationDone = true;
                        nb_mutation++;
                    }
               
                
                }
              /*  else {
                     for (TourGroup trgChild:children) {
                         trgChild.allocateTours2_dynamic();
                     }
                    
                }*/
                /******************FIN MUTATION******************************/
             
                /******************Correction******************************/
                if(transformationDone == true){
                System.out.println("/********Insertion**********/");
                for (TourGroup trgChild:children) {
                  System.out.println("before improve ");
                    trgChild.improveTours_dynamic();
                     
                    System.out.println("après improve lawla");
                    //verify_population(this.problemD.getCustomers_dynamic(), trgChild);
                     //System.out.println("haa get temps dernier voyage b get distance dynamic : "+ trgChild.getdistance_dynamic());
                     //System.out.println("haa get temps b calculer get camion dyal tour groupe  : "+ trgChild.getCamions().get(0).calucler_temps_total_tournees_dynamic());
    
                    //calculer_trg(trgChild);
                   //testpopulation();
                   
                            
                   
                    //trgChild.two_opt(problem.getNbImprove());
                    //System.out.println("after improve ");
           // System.out.println("hooooooooooooooooooooooooooooooooo :"+trgChild.getTours().size());
                                 
                  
                    
                   // if(mutationDone==true)
                    //{
                      //System.out.println("before spliiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiit:");
                    
                        trgChild.splitTours_dynamic();
                         
                           System.out.println("après seplitt");
                          // verify_population(this.problemD.getCustomers_dynamic(), trgChild);
                           //calculer_trg(trgChild);
                          // trgChild.allocateTours2_dynamic();
                     // testpopulation();
                         /*if (trgChild.getTours().isEmpty())
                            {
                                 System.out.println("hoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
                                               
                            }*/
                        //trgChild.removeemptytours();
                        //trgChild.CheckCamionsConstraint();
                       //System.out.println("after split:");
                   // }
                                    
                     
                     
                     //System.out.println("after ");
                      
                       trgChild.AmeliorateBySaving_dynamic();
                       
                       System.out.println("après ameliorate b saving");
                       //verify_population(this.problemD.getCustomers_dynamic(), trgChild);
                       // System.out.println("haa get temps dernier voyage b get distance dynamic : "+ trgChild.getdistance_dynamic());
                       // System.out.println("haa get temps b calculer get camion dyal tour groupe  : "+ trgChild.getCamions().get(0).calucler_temps_total_tournees_dynamic());
    
                       //calculer_trg(trgChild);
                       //testpopulation();
                        /* if (trgChild.getTours().isEmpty())
                            {
                                 System.out.println("hoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
                                               
                            }
                     */
                     
                     trgChild.allocateTours2_dynamic();
                     System.out.println("after allocate ");
                     //verify_population(this.problemD.getCustomers_dynamic(), trgChild);
                      //System.out.println("haa get temps dernier voyage b get distance dynamic : "+ trgChild.getdistance_dynamic());
                      //System.out.println("haa get temps b calculer get camion dyal tour groupe  : "+ trgChild.getCamions().get(0).calucler_temps_total_tournees_dynamic());
    
                      //calculer_trg(trgChild);
                      trgChild.two_opt_dynamic(problemD.getNbImprove());
                       
                      //trgChild.allocateTours2_dynamic();
                      System.out.println("après twoopt");
                      //verify_population(this.problemD.getCustomers_dynamic(), trgChild);
                       //System.out.println("haa get temps dernier voyage b get distance dynamic : "+ trgChild.getdistance_dynamic());
                       //System.out.println("haa get temps b calculer get camion dyal tour groupe  : "+ trgChild.getCamions().get(0).calucler_temps_total_tournees_dynamic());
    
                     //calculer_trg(trgChild);
                       /*if (trgChild.getTours().isEmpty())
                            {
                                 System.out.println("hoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
                                               
                            }*/
                  
                     
                     
                    if( (trgChild.CheckCamionsConstraint()
                                && population.get(population.size()-1).compareTo_dynamic(trgChild)>=0) //comaprer avec le dernier
                                && (checkInPopulation_dynamic(trgChild,population)==false))
                        {
                           
                       //System.out.println("dans la condition if taille camion du problem : "+ trgChild.getProblemD().getCamions_dynamic().size());
                     //  System.out.println("dans la condition if taille camion to use : "+ trgChild.getCamionsToUse().size());
                            //supprimer le plus mauvais
                            population.remove(population.size()-1);
                          
                            int position = insertChildInPopulation_dynamic(population, trgChild);
                            
                            if(trgChild.getLTR_dynamic()<=problemD.getMaxTemps_dynamic()){
                                bests.add(new Solution(trgChild,duree,lastGeneration));
                               
                            }
                            count++;
                            if(position==0){
                               
                                lastGeneration = generation;
                                dateFin = new Date().getTime();
                                duree = dateFin - dateDeb;
                                System.out.println("Generation " + generation+", nb insertion " + count+", LTR" + trgChild.getLTR_dynamic()+", distance " + trgChild.getdistance_dynamic() +", iterationBest " + lastGeneration+", duree " + duree+"ms");
                                System.out.println("best:"+trgChild);
                                //calculer_trg(trgChild);
                            }

                    // System.out.println("khrejna men if lawla");
                    //testpopulation();
                        }
                    //System.out.println("khrejna men if 2");
                    //testpopulation();
                    
                       
                    }
                //System.out.println("khrejna men if 3");
                 //testpopulation();
                }
                //System.out.println("wsalna l generaion ++");
                 // testpopulation();
                //JOptionPane.showMessageDialog(null, myContainer);
                    
                /******************FIN INSERTION******************************/
                generation ++;
                //afficherPopulation(poulation.size());
            }
            /**Aficher les 10 permeiers**/
            afficherPopulation(population.size());
            System.out.println("Moyenne " + moyennePopulation_dynamic());
            System.out.println("count " + count);
            //System.out.println("Genetic algorithm endend after " + maxGenerations + " generations.");
            //System.out.println("Best TourGroup: " + population.get(population.size() - 1));
            //Collections.sort(population);
            TourGroup best =  population.get(0);
            //new TspSolver(problem).solve(best);
            //System.out.println("End of generation " + generation+" " + lastGeneration);
            if(bests.isEmpty())
                
                bests.add(new Solution(best,duree,lastGeneration));
             System.out.println("taille bestssssssssssssssssssssssssssssssssss:  "+bests.size() );
           
            sort_dynamic_sol(bests);
            
            this.setBest(bests.get(0));
           // calculer_trg(best);
            this.afficher_best();
             this.getBest().getTourGroup().update_tournes_attribues();
             this.getCamions().clear();
     for(int k=1 ;k<=this.getBest().getTourGroup().getCamions().size(); k++)
    {
      this.getCamions().add(this.getBest().getTourGroup().getCamions().get(k-1));
    }
            //this.get_depo_fic_dynamic();hjhj
          // bests.clear();
           //this.afficher_solution_dynamic();
            return this.getBest().getTourGroup();
	}
       public void test_capacite_camion(Camion c,TourGroup trg)
       {
           classer_tournees(trg.getTours());
           //if (c.getDepot_fictif_final()!=null)
           for (int i=0;i<trg.getTours().size();i++)
           {
           if (trg.getTours().get(i).getQuantity()>trg.getTours().get(i).getcapacity())
           {
        System.out.println("hoooooooooooooooooooooooooooooooooooooooooooooooooooo quantity: "+trg.getTours().get(i).getQuantity());
         System.out.println("hooooooooooooooooooooooooooooooooooooooooooooooooooooo capacité: "+trg.getTours().get(i).getcapacity());
       
           }
            
           }
       
           
       }
        public void test_capacite_camion_initial(Camion c,TourGroup trg)
       {
           classer_tournees(trg.getTours());
           //if (c.getDepot_fictif_final()!=null)
           for (int i=0;i<trg.getTours().size();i++)
           {
           if (trg.getTours().get(i).getQuantity()>trg.getTours().get(i).getcapacity())
           {
        System.out.println("hoooooooooooooooooooooooooooooooooooooooooooooooooooo quantityyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy: "+trg.getTours().get(i).getQuantity());
         System.out.println("hooooooooooooooooooooooooooooooooooooooooooooooooooooo capacitéééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééé: "+trg.getTours().get(i).getcapacity());
       System.out.println("hooooooooooooooooooooooooooooooo "+trg.getTours().get(10000).getcapacity());
       
           }
            
           }
       
           
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
       public void testcamions(TourGroup tr){
            for (int i=0; i<tr.getCamions().size();i++)
            {
                if (tr.getCamions().get(i).getTournee_attribuees().size()>0) 
                {
                    System.out.println("hada 3ando des tournées attribuée:  "+tr.getCamions().get(i).getTournee_attribuees().size() );
                         
                }
            }
            
        }
      public void testpopulation(){
         System.out.println("taille dépot fictif "+ this.problemD.getDepots_fictif().size());
           System.out.println("taille client:  "+ this.problemD.getCustomers_dynamic().size());
             System.out.println("taille liste dynamic des nouveau client:  "+ this.dynamic.size());
                 
          /*for (int i=0; i<population.size();i++)
                {
                    if (nbcustomerspop(population.get(i))<this.problemD.getCustomers_dynamic().size())
                    {
                  System.out.println("awaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaah "+ nbcustomerspop(population.get(i)));
                  System.out.println("iiiiiiiiiiiiiiiiiii:  "+ i);
                  System.out.println("hriiiiiiiraaaaa:  "+ this.problemD.getCustomers_dynamic().size());
                  System.out.println("oooooooooooooooooooo:  "+ population.size());
                    }
                */
      }

        private void createInitialPopulation(TourGroup tourGroupCW, InitiationType type1, InitiationType type2, int nb_first_Type){
            //try{
               assert (populationSize > 1);
                int first = 0;
                // Clarck & Wright
                if(tourGroupCW!=null){
                    //tourGroupCW.sort();
                    population.add(0,tourGroupCW);
                    first = 1;
                }
                int second = first+nb_first_Type;
                if(type2==null)             
                    second = populationSize;
            TourGroup trgr = new TourGroup(problem);
            //Premier type
            for (int j = first; j < second; j++) {
               // System.out.println("add ind "+j);
                do{ 
                    System.out.println("add ind "+j);
                    trgr= getTourGroup(type1);
                }
                while(trgr.CheckCapacityConstraint()==false
                        || trgr.CheckAllCustomersConstraint()==false);
                 //trgr.sort();
                 System.out.println("hoooooooooooooo:  "+j);
                System.out.println(trgr);
                /*if((problem.getObjective1().equals("LTR") 
                                     && checkSimilarInPopulation (trgr,population)==false)
                     || */
                        /*if((!problem.getObjective1().equals("DIST") 
                         && checkInPopulation(trgr,population)==false))*/
                     population.add(j,trgr);
                //else j--;
            }
            if(second!=populationSize){
            //Second type
                for (int i = second; i < populationSize; i++) {
                    //System.out.println("add ind "+i);
                    do{ 
                        trgr= getTourGroup(type2);
                    }
                    while(trgr.CheckCapacityConstraint()==false
                        || trgr.CheckAllCustomersConstraint()==false);
                    //trgr.sort();
                    //System.out.println("trgr to add:"+trgr);
                    System.out.println("trgr to add:"+i);
                    if(checkInPopulation(trgr,population)==false)
                        population.add(i,trgr);
                    else i--;
                }
            }
            /*} catch (Exception e) {
                System.out.println("exception: "+e);
            }*/
	}
            
        private TourGroup getTourGroup(InitiationType type){
		
         TourGroup trgr = new TourGroup(problem);
            
        do{
            if(type == InitiationType.PPV)
                System.out.println("hna fin 7saal 1 ");
                trgr= new TourGroup(problem).solve_PPV();
            if(type == InitiationType.SEQ){
                  System.out.println("hna fin 7saal 2 ");
                trgr= new TourGroup(problem).solve_insertion();//fonctionnel pr cas HOMO et HETE
                //trgr.improve(50); en comment pour le cas de E_10
            }
            if(type == InitiationType.CW)
                trgr= new TourGroup(problem).clarckwhrite();
            //System.out.println("tour "+j+" ,type1 "+type1+", cost "+problem.getCost(trgr)+" :"+trgr);
        } 
        while(!trgr.CheckCamionsConstraint());
        return trgr;
         
	}
      private void createInitialPop(){
                  assert (populationSize > 1);
                  TourGroup trgrcla = new TourGroup(problem);
                  TourGroup trgrcseq = new TourGroup(problem);
                 population.add(trgrcla.clarckwhrite());
                 int k=0;
                        for (int j=0; j<population.get(0).getTours().size();j++){
                        k=k+population.get(0).getTours().get(j).getCustomers().size();
                        }
                        if (k<50)
                  System.out.println("++++++++++++++++++++++++++++++++++++++hadi clarckandwrite+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++  : "+k);
              
                 
                  System.out.println("fin clarck and whriteeeeeeeeeeeee");
                   do{ 
                      trgrcseq=null; //TourGroup trgr = new TourGroup(problem);
                      trgrcseq= new TourGroup(problem).solve_PPV();
                       //trgrcseq= trgr;
                    }
                    while(trgrcseq.CheckCapacityConstraint()==false
                        || trgrcseq.CheckAllCustomersConstraint()==false || checkInPopulation(trgrcseq,population)==true);
                        
                   
                population.add(trgrcseq);
                int p=0;
                        for (int j=0; j<trgrcseq.getTours().size();j++){
                        p=p+trgrcseq.getTours().get(j).getCustomers().size();
                        }
                        if (p<50)
                  System.out.println("++++++++++++++++++++++++++++++++++++++hadi ppv+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++  : "+p);
               
                 for (int i=0; i<populationSize-2;i++)
                 {
                       TourGroup trgrcale = new TourGroup(problem);
                       do{ 
                      trgrcale=null; //TourGroup trgr = new TourGroup(problem);
                      trgrcale= new TourGroup(problem).solve_insertion();
                       //trgrcseq= trgr;
                    }
                    while(trgrcale.CheckCapacityConstraint()==false
                        || trgrcale.CheckAllCustomersConstraint()==false|| checkInPopulation(trgrcale,population)==true);
                        population.add(trgrcale);
                        
                        int l=0;
                        for (int j=0; j<trgrcseq.getTours().size();j++){
                        l=l+trgrcseq.getTours().get(j).getCustomers().size();
                        }
                        if (l<50)
                  System.out.println("++++++++++++++++++++++++++++++++++++++hadi insertion+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++  : "+l);
               
                     
                 }
               for (int i=0; i<population.size();i++)
               {
                 System.out.println("taille tournée population initial"+ population.get(i).getTours().size())  ;
                 System.out.println("nombre clients popultaion"+nbcustomerspop( population.get(i)));
                 System.out.println("numéro trg"+i)  ;
               }
                  
                  
              }
    /*  private void createInitialPop_dynamic(TourGroup sol_preced){
                  assert (populationSize > 1);
                  TourGroup trg = new TourGroup(problemD);
                  ArrayList<TourGroup> pop = new ArrayList<TourGroup>();
                  pop=trg.HeuristiqueInsertion(sol_preced, this.dynamic);
                   System.out.println("haaahowa f createinitialpop men wra ma sala heuristique d'insertion"+pop.size());
                  int size_init=pop.size();
                  if (size_init<=(populationSize/2))
                  {
                       System.out.println("première if");
                     for(int i=0; i<size_init;i++) 
                         population.add(pop.get(i));
                     for (int i=size_init;i<populationSize;i++)
                          
                 { int j=0;
                      System.out.println("à l'intérieur de la boucle fooooooor");
                       TourGroup trgrcale = new TourGroup(problemD);
                       do{ 
                      System.out.println("à l'intérieur de la boucle while: "+j);
                      trgrcale=null; //TourGroup trgr = new TourGroup(problem);
                      trgrcale= new TourGroup(problemD).solve_insertion_dynamic();
                      j++;
                       //trgrcseq= trgr;
                    }
                    while(trgrcale.CheckCapacityConstraint()==false
                        || trgrcale.CheckAllCustomersConstraint_dynamic()==false|| checkInPopulation_dynamic(trgrcale,population)==true);
                        population.add(i,trgrcale);
                        
                        
                     
                 }
                  }
                  else
                  {
                        System.out.println("hadi else avant sort");
                      sort_dynamic(pop);
                      System.out.println("hadi else après sort");
                   for(int i=0; i<(int)(populationSize/2);i++) 
                         population.add(pop.get(i));
                     for (int i=(int)(populationSize/2);i<populationSize;i++)
                          
                 {
                       TourGroup trgrcale = new TourGroup(problemD);
                       do{ 
                      trgrcale=null; //TourGroup trgr = new TourGroup(problem);
                      trgrcale= new TourGroup(problemD).solve_insertion_dynamic();
                       //trgrcseq= trgr;
                    }
                    while(trgrcale.CheckCapacityConstraint()==false
                        || trgrcale.CheckAllCustomersConstraint_dynamic()==false|| checkInPopulation_dynamic(trgrcale,population)==true);
                        population.add(i,trgrcale);
                        
                        
                     
                 }   
                  }
                
               
                  
              }*/
      private void createInitialPop_dynamic(TourGroup sol_preced){
                  assert (populationSize > 1);
                  TourGroup trg = new TourGroup(problemD);
                  ArrayList<TourGroup> pop = new ArrayList<TourGroup>();
                  pop=trg.HeuristiqueInsertion(sol_preced, this.dynamic);
                   System.out.println("haaahowa f createinitialpop men wra ma sala heuristique d'insertion"+pop.size());
                  int size_init=pop.size();
                  sort_dynamic(pop);
                      System.out.println("hadi else après sort");
                   for(TourGroup tr: pop) 
                   {
                         population.add(tr);
                          
                   }
                   
                  
              }
              public int nbcustomerspop(TourGroup trg)
              {
                  int l=0;
                 for (int j=0; j<trg.getTours().size();j++){
                        l=l+trg.getTours().get(j).getCustomers().size();
                        } 
                 return l;
              }
         public void afficherPopulation(int numberBest){
        DecimalFormat df = new DecimalFormat ( ) ; 
        df.setMaximumFractionDigits ( 2 ) ; //arrondi à 2 chiffres apres la virgules 

        for (int i=0;i<numberBest;i++) {
                TourGroup ind = population.get(i);
               /* System.out.println("dist="+ df.format(ind.getdistance())
                    
                    + ", totalCost=" + df.format(ind.getObj1())
                    + ", LTR=" + df.format(ind.getLTR())
                        );//+"\n"+ind);*/
                //System.out.println(population.get(i));
            }
    }
           public double moyennePopulation(){
        double sum = 0;
        for (int i=0;i<population.size();i++) {
                sum += population.get(i).getdistance();
            }
        return sum/populationSize;
    }
           public double moyennePopulation_dynamic(){
        double sum = 0;
        for (int i=0;i<population.size();i++) {
                sum += population.get(i).getdistance_dynamic();
            }
        return sum/populationSize;
    }
            private TourGroup selectRandomFather(int echantillon) {
                TourGroup trg= new TourGroup(problem);
                //trg.getTours().clear();
                trg=population.get(rnd.nextInt(echantillon)).clone();
        return trg;
    }
            private TourGroup selectRandomFather_dynamic(int echantillon) {
                TourGroup trg= new TourGroup(problemD);
                //trg.getTours().clear();
                trg=population.get(rnd.nextInt(echantillon)).clone_dynamic();
        return trg;
    }
     public Boolean checkInPopulation(TourGroup trgp,ArrayList<TourGroup> pop) {
        for(TourGroup ind: pop){
             if (trgp.similarTourGroup(ind)){
                 return true;
             }
         }
         return false;
    } 
      public Boolean checkInPopulation_dynamic(TourGroup trgp,ArrayList<TourGroup> pop) {
        for(TourGroup ind: pop){
             if (trgp.similarTourGroup_dynamic(ind)){
                 return true;
             }
         }
         return false;
    } 
       private int insertChildInPopulation(ArrayList<TourGroup> population, TourGroup tourGroup){
        //tourGroup.sort();
        if(tourGroup.compareTo(population.get(0))<=0){//best
            System.out.println("tourgroup "+tourGroup.getObj1()+" compare to pop0 "+population.get(0).getObj1());
            population.add(0, tourGroup);
            return 0;
        }
        
        for(int i=0;i<population.size()-1;i++){
            if(tourGroup.compareTo(population.get(i))>0
                    && tourGroup.compareTo(population.get(i+1))<=0){
                //System.out.println(problem.getCost(population.get(i)));
                //System.out.println(problem.getCost(population.get(i+1)));
                population.add(i+1, tourGroup);
                return i+1;
            }
        }
        //worst
        population.add(population.size(), tourGroup);
        return population.size();
    }
         private int insertChildInPopulation_dynamic(ArrayList<TourGroup> population, TourGroup tourGroup){
        //tourGroup.sort();
        if(tourGroup.compareTo_dynamic(population.get(0))<=0){//best
            System.out.println("tourgroup "+tourGroup.getLTR_dynamic()+" compare to pop0 "+population.get(0).getLTR_dynamic());
            population.add(0, tourGroup);
            return 0;
        }
        
        for(int i=0;i<population.size()-1;i++){
            if(tourGroup.compareTo_dynamic(population.get(i))>0
                    && tourGroup.compareTo_dynamic(population.get(i+1))<=0){
                //System.out.println(problem.getCost(population.get(i)));
                //System.out.println(problem.getCost(population.get(i+1)));
                population.add(i+1, tourGroup);
                return i+1;
            }
        }
        //worst
        population.add(population.size(), tourGroup);
        return population.size();
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
               System.out.println("haaaaaaaaaaaaaaaaaaaaaa 7riiiraaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa :"+trg.getTours().get(100000));    
        
          }
          if (size_t!=size_crs)
          {
              System.out.println("haaaaaaaaaaaaaaaaaaaaaa 7riiiraaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa ");    
          }
          size_t=0;
      }
       int id_fic=0;
          for (int j=0;j<trg.getTours().size();j++)
          {
              if (trg.getTours().get(j).getId_fictif()!=0)
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
          System.out.println("haaaaaaaaaaaaaaaaaaaaaa 7riiiraaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa :"+trg.getTours().get(100000));    
        
         }
      
  }  
  public void tester_duplicate_camion(ArrayList<Camion> camionss)
  {
      int soz_cam=50;
      
      for (int i=0;i<camionss.size();i++)
      {
          // System.out.println("haa camions i :"+camionss.get(i).getIdcamion());
         int id_cam=0 ;
         for (int j=0;j<camionss.size();j++)
         {
             //System.out.println("haa camions j :"+camionss.get(j).getIdcamion());
               
             if (camionss.get(j).getIdcamion()==camionss.get(i).getIdcamion())
             {
              id_cam=id_cam+1;   
             }
         }
         if (id_cam!=1)
             {
                 System.out.println("camionnnnnnnnnnnnn problèèèèèèèèèèèèèèèèèèèèèèèèèèèèèèèèèèèème  :"+id_cam);
                 System.out.println("camionnnnnnnnnnnnn problèèèèèèèèèèèèèèèèèèèèèèèèèèèèèèèèèèèème iiid dyalo  :"+camionss.get(i).getIdcamion());
            
             }
          
      }
      /*if (camionss.size()!=soz_cam)
             {
                 System.out.println("camionnnnnnnnnnnnn problèèèèèèèèèèèèèèèèèèèèèèèèèèèèèèèèèèèème taiiiiiiillle :"+camionss.size());
                 
             }*/
  }
   public void get_depo_fic ()
{
     System.out.println("temps total voyage au début : "+ this.getBest().getTourGroup().getdistance());
        
    ArrayList<Depotfictif> depo_fics = new ArrayList<Depotfictif> () ;
   ArrayList<Tour> trs = new ArrayList<Tour> () ;
   //System.out.println("hadchi après les déclaration dyal get_depo_fic taille tournées attribuée camion : "+ this.getBest().getTourGroup().getCamions().get(0).getTournee_attribuees().size());
     
   this.DEPO_FIC.clear();
   //System.out.println("hadchi après clear depo_fitif get_depo_fic taille tournées attribuée camion : "+ this.getBest().getTourGroup().getCamions().get(0).getTournee_attribuees().size());
   
   //TourGroup tourgrpnew=new TourGroup(problem);
   trs.clear();
   //System.out.println("hadchi après clear trs dyal get_depo_fic taille tournées attribuée camion : "+ this.getBest().getTourGroup().getCamions().get(0).getTournee_attribuees().size());
   
   this.sol_preced=new TourGroup(problem);
   //System.out.println("hadchi après  déclaration sol_preced get_depo_fic taille tournées attribuée camion : "+ this.getBest().getTourGroup().getCamions().get(0).getTournee_attribuees().size());
   
     //System.out.println("camions : "+ this.getGlobalBestAnt().getCamions().size());
     this.clients_old.clear();
     //System.out.println("hadchi après clear dyal  cloent old taille tournées attribuée camion : "+ this.getBest().getTourGroup().getCamions().get(0).getTournee_attribuees().size());
   
     this.setTemps_total_voyage(0);
     //System.out.println("hadchi après set temps total voyage get_depo_fic taille tournées attribuée camion : "+ this.getBest().getTourGroup().getCamions().get(0).getTournee_attribuees().size());
   
    this.getBest().getTourGroup().update_tournes_attribues();
     int b=1;
      /*for(int k=0 ;k<this.getBest().getTourGroup().getTours().size(); k++)
      {
           System.out.println("haaa l id dyal son camions : "+ this.getBest().getTourGroup().getTours().get(k).getC().idcamion);
           System.out.println("haaa l le nombre de tournées affecté à ce camions : "+ this.getBest().getTourGroup().getTours().get(k).getC().getTournee_attribuees().size());
      }*/
      System.out.println("hadchi avant for dyal get_depo_fic taille tournées attribuée camion : "+ this.getBest().getTourGroup().getCamions().get(0).getTournee_attribuees().size());
     
    for(int k=1 ;k<=this.getBest().getTourGroup().getCamions().size(); k++)
    {
        Camion c = this.getBest().getTourGroup().getCamions().get(k-1);
        System.out.println("haaa le camion : "+c.getIdcamion());
                System.out.println("haaa sum temps tour : "+c.getSum_temps_tour());
                // System.out.println("haaaada sumtemps tour ktar men time slice ");
             System.out.println("haaa temps total tournées : "+c.getTemps_tournees_avant());
             System.out.println("haaa taille tournées attribués  : "+c.getTournee_attribuees().size());
        double sum_temps_tour=0;
        int i=0;
       // System.out.println("haaa le nombre de tournées affecté à ce camions : "+ c.getTournee_attribuees().size());
        if (c.calucler_temps_total_tournees()<=this.getTime_slice())
        {
          //  System.out.println("dkhal l la condition if lawla");
            //this.setTemps_total_voyage(c.calucler_temps_total_tournees());
            c.setTemps_tournees_avant(c.getTemps_tournees_avant()+c.calucler_temps_total_tournees());
            c.setId_fictif_final(0);
            this.setTemps_total_voyage(this.getTemps_total_voyage()+c.calucler_temps_total_tournees());
             System.out.println("hha temps totaaaaal voyaaage  "+this.getTemps_total_voyage());
            
            c.setDepot_fictif_final(null);
            c.setSum_temps_tour(0);
            
        }
       // System.out.println("time_slice : "+ this.getTime_slice());
        else
        {
        while (sum_temps_tour < this.getTime_slice())
        {
            sum_temps_tour= sum_temps_tour+c.getTournee_attribuees().get(i).getTemps();
            
            i++;
           System.out.println("sumtemp : "+ sum_temps_tour);
        }
        if (sum_temps_tour==this.getTime_slice())
        {
           System.out.println("dkhal l la condition if thaanyaa");
            c.setTemps_tournees_avant(c.getTemps_tournees_avant()+ sum_temps_tour);
            c.setId_fictif_final(0);
            c.setDepot_fictif_final(null);
            c.setSum_temps_tour(0);
            this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour);
             System.out.println("hha temps totaaaaal voyaaage  "+this.getTemps_total_voyage());
            
            for (int j=i; j<c.getTournee_attribuees().size(); j++)
            {
               Tour tr= new Tour (this.sol_preced);
               for (int r=0; r< c.getTournee_attribuees().get(j).getCustomers().size();r++)
               {
                   tr.getCustomers().add(c.getTournee_attribuees().get(j).getCustomers().get(r));
               }
                   tr.setId_fictif(0);
               trs.add(tr);
       
                for (Customer c1: c.getTournee_attribuees().get(j).getCustomers())
                {
                    this.clients_old.add(c1);
             //       System.out.println("id client à ajouter_dynamic "+ c1.getId());
                    
                }
                
            }
        }
        else
        {
           System.out.println("dkhal l l lelllllllse");
        int j=0;
        double cap_cl_fic=this.problem.getMaxCapacity();
        sum_temps_tour= sum_temps_tour-c.getTournee_attribuees().get(i-1).getTemps();
         
        sum_temps_tour= sum_temps_tour+ this.getProblem().gettimes(0, c.getTournee_attribuees().get(i-1).getCustomers().get(0).getId())+this.getTemps_service();
          cap_cl_fic=cap_cl_fic-c.getTournee_attribuees().get(i-1).getCustomers().get(0).getDemande();
        while (sum_temps_tour< this.getTime_slice()&&(c.getTournee_attribuees().get(i-1).getCustomers().size()>(j+1)))
        {
            j++;
            sum_temps_tour=sum_temps_tour+this.getProblem().gettimes(c.getTournee_attribuees().get(i-1).getCustomers().get(j-1).getId(),c.getTournee_attribuees().get(i-1).getCustomers().get(j).getId())+this.getTemps_service();
            cap_cl_fic=cap_cl_fic-c.getTournee_attribuees().get(i-1).getCustomers().get(j).getDemande();
           
             
        }
        System.out.println("haaa sumtemps tour 9bel chwiya "+sum_temps_tour);
        if ((j==(c.getTournee_attribuees().get(i-1).getCustomers().size()-1))&&(sum_temps_tour< this.getTime_slice()))
        {
            sum_temps_tour=sum_temps_tour+this.getProblem().gettimes(c.getTournee_attribuees().get(i-1).getCustomers().get(j).getId(),0);
            
      System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
          this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour);
        c.setTemps_tournees_avant(c.getTemps_tournees_avant()+ sum_temps_tour);
        c.setId_fictif_final(0);
        c.setDepot_fictif_final(null);
        c.setSum_temps_tour(sum_temps_tour-this.getTime_slice());
            
              
        }
        else {
            int id_cl_fic=c.getTournee_attribuees().get(i-1).getCustomers().get(j).getId();
        Tour tr= new Tour(this.sol_preced);
        tr.setId_fictif(b);
        trs.add(tr);
        for (int l=j+1; l<c.getTournee_attribuees().get(i-1).getCustomers().size();l++)
        {
            this.clients_old.add(c.getTournee_attribuees().get(i-1).getCustomers().get(l));
            tr.getCustomers().add(c.getTournee_attribuees().get(i-1).getCustomers().get(l));
           //System.out.println("sum temps tour "+ c.getTournee_attribuees().get(i-1).getCustomers().get(l).getId());
        }
             c.setTemps_tournees_avant(c.getTemps_tournees_avant()+ sum_temps_tour);
         c.setId_fictif_final(id_cl_fic);  
        depo_fics.add(new     Depotfictif(id_cl_fic,cap_cl_fic, b));
        c.setDepot_fictif_final(depo_fics.get(b-1));
        c.setSum_temps_tour(sum_temps_tour-this.getTime_slice());
       this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour);
        System.out.println("haaa temps total voyage "+this.getTemps_total_voyage()); 
        b++;
        System.out.println("haaa sumtemps tour "+sum_temps_tour); 
        }
        
        for (int m=i;m<c.getTournee_attribuees().size();m++)
        {
        Tour trr= new Tour(this.sol_preced);
        trr.setId_fictif(0);
        
            for (int n=0;n<c.getTournee_attribuees().get(m).getCustomers().size();n++)
            {
               trr.getCustomers().add(c.getTournee_attribuees().get(m).getCustomers().get(n));
               this.clients_old.add(c.getTournee_attribuees().get(m).getCustomers().get(n));  
            }
            trs.add(trr);
        }
        
        
        }
    }
    }
    this.getCamions().clear();
     for(int k=1 ;k<=this.getBest().getTourGroup().getCamions().size(); k++)
    {
      this.getCamions().add(this.getBest().getTourGroup().getCamions().get(k-1));
    }
    // this.sol_preced.setTours(trs);
    
    if ( this.sol_preced.getTours()!=null)
       this.sol_preced.getTours().clear();
     for (int k=0; k<trs.size();k++)
     {
       this.sol_preced.getTours().add(trs.get(k));
     }
    System.out.println(" haa temps total g0 haaayhaaaayhaaaayhaaaaayhaaaaayhaaaaaaaaayjhdsgqqqqqqqqqqqqqqqnbvhgfqsyCHKvgfCGFCJbjhvhgscf  :"+this.getTemps_total_voyage());
     for (Depotfictif dep: depo_fics)
                {
                    this.DEPO_FIC.add(dep);
             //       System.out.println("id client à ajouter_dynamic "+ c1.getId());
                    
                }
    
    //return depo_fics;
}

public void get_depo_fic_dynamic ()
{
    /*System.out.println("haa get temps dernier voyage au debut : "+this.getTemps_total_dernier_voyage());
    for (int i=0;i<this.getBest().getTourGroup().getTours().size();i++)
    {
        System.out.println("id tour:"+this.getBest().getTourGroup().getTours().get(i).getId_fictif());
        for (int j=0;j<this.getBest().getTourGroup().getTours().get(i).getCustomers().size();j++)
        {System.out.println("id client:"+this.getBest().getTourGroup().getTours().get(i).getCustomers().get(j).getId());}
    }
    
    System.out.println("haa get temps dernier voyage b calculer get camion dyal tour groupe au debut : "+ this.getBest().getTourGroup().getCamions().get(0).calucler_temps_total_tournees_dynamic());
    for (int i=0;i<this.getBest().getTourGroup().getCamions().get(0).getTournee_attribuees().size();i++)
    {
        System.out.println("id tour:"+this.getBest().getTourGroup().getCamions().get(0).getTournee_attribuees().get(i).getId_fictif());
        for (int j=0;j<this.getBest().getTourGroup().getCamions().get(0).getTournee_attribuees().get(i).getCustomers().size();j++)
        {System.out.println("id client:"+this.getBest().getTourGroup().getCamions().get(0).getTournee_attribuees().get(i).getCustomers().get(j).getId());}
    }*/
                          
   
    ArrayList<Depotfictif> depo_fics = new ArrayList<Depotfictif> () ;
   ArrayList<Tour> trs = new ArrayList<Tour> () ;
   //TourGroup tourgrpnew=new TourGroup(problemD);
   this.sol_preced=new TourGroup(problemD);
   this.DEPO_FIC.clear();
   trs.clear();
    this.clients_old.clear();
    this.setTemps_total_voyage(0);
     this.getBest().getTourGroup().update_tournes_attribues();
    int b=1;
    for(int k=1 ;k<=this.getBest().getTourGroup().getCamions().size(); k++)
    {
        Camion c = this.getBest().getTourGroup().getCamions().get(k-1);
                if (c.getDepot_fictif_final()!=null)
                { System.out.println(" le camion : "+c.getIdcamion());
                    System.out.println(" depot fictif dyalo : "+c.getDepot_fictif_final().getId_client_fictif());
                     System.out.println(" capacité dyalo : "+c.getDepot_fictif_final().capacité_restante);
                }
                else {
                     System.out.println("depot fic null: ");
                System.out.println(" le camion : "+c.getIdcamion());
                System.out.println(" sum temps tour : "+c.getSum_temps_tour());
                  System.out.println(" temps total tournées : "+c.getTemps_tournees_avant());
             System.out.println(" taille tournées attribués  : "+c.getTournee_attribuees().size());
                } 
         
        if (c.getSum_temps_tour()>this.getTime_slice())
        {
                /* System.out.println("haaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa le camion : "+c.getIdcamion());
                System.out.println("haaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa sum temps tour : "+c.getSum_temps_tour());
                    System.out.println("haaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa depot fictif dyalo : "+c.getDepot_fictif_final().getId_client_fictif());
                     System.out.println("haaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa capacité dyalo : "+c.getDepot_fictif_final().capacité_restante);
                             System.out.println("haaaadaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa sumtemps tour ktar men time slice ");
             System.out.println("haaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa temps total tournées : "+c.getTemps_tournees_avant());
             System.out.println("haaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa taille tournées attribués  : "+c.getTournee_attribuees().size());
            */     
            if (c.getDepot_fictif_final()==null)
            {
              c.setDepot_fictif_final(null);  
              c.setTemps_tournees_avant(c.getTemps_tournees_avant());
              c.setId_fictif_final(0); 
              c.setSum_temps_tour(c.getSum_temps_tour()-this.getTime_slice());
               for (int m=0;m<c.getTournee_attribuees().size();m++)
                       {
                           Tour tr= new Tour(this.sol_preced);
                           tr.setId_fictif(0);
                           
                      for (int n=0;n<c.getTournee_attribuees().get(m).getCustomers().size();n++)
                           {
                            this.clients_old.add(c.getTournee_attribuees().get(m).getCustomers().get(n));
                            tr.getCustomers().add(c.getTournee_attribuees().get(m).getCustomers().get(n));
                            //System.out.println("haaa les clients old dyawlo  1 : "+c.getTournee_attribuees().get(m).getCustomers().get(n).getId());
                           }
                      trs.add(tr);
                         }
                     
            }
            else {     // c.setDepot_fictif_final(c.getDepot_fictif_final());
                     
                       depo_fics.add(new     Depotfictif(c.getDepot_fictif_final().getId_client_fictif(),c.getDepot_fictif_final().getCapacité_restante(), b));
                       //this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour);
                        c.setDepot_fictif_final(depo_fics.get(b-1));
                       c.setTemps_tournees_avant(c.getTemps_tournees_avant());
                       c.setId_fictif_final(depo_fics.get(b-1).getId_client_fictif()); 
                       c.setSum_temps_tour(c.getSum_temps_tour()-this.getTime_slice());
                       Tour trr= new Tour(this.sol_preced);
                       trr.setId_fictif(b);
                      
                       
                       for (int n=0;n<c.getTournee_attribuees().get(0).getCustomers().size();n++)
                           {
                         trr.getCustomers().add(c.getTournee_attribuees().get(0).getCustomers().get(n)); 
                            this.clients_old.add(c.getTournee_attribuees().get(0).getCustomers().get(n));  
                           // System.out.println("haaa les clients old dyawlo  2 : "+c.getTournee_attribuees().get(m).getCustomers().get(n).getId());
                           }
                       trs.add(trr);
                       /* System.out.println("haaa le camion : "+c.getIdcamion());
                        System.out.println("haaa sum temps tour : "+c.getSum_temps_tour());
                        System.out.println("haaada 7etta howa  sum temps tour ktar men time slice: ");
                        System.out.println("haaa temps total tournées : "+c.getTemps_tournees_avant());*/
                       b++;
                       
                       for (int m=1;m<c.getTournee_attribuees().size();m++)
                       {
                       Tour tr= new Tour(this.sol_preced);
                       tr.setId_fictif(0);
                      for (int n=0;n<c.getTournee_attribuees().get(m).getCustomers().size();n++)
                           {
                         tr.getCustomers().add(c.getTournee_attribuees().get(m).getCustomers().get(n)); 
                            this.clients_old.add(c.getTournee_attribuees().get(m).getCustomers().get(n));  
                           // System.out.println("haaa les clients old dyawlo  2 : "+c.getTournee_attribuees().get(m).getCustomers().get(n).getId());
                           }
                      trs.add(tr);
                         }
            }
        }
        else 
        {
            double  sum=c.getSum_temps_tour();
            double sum_temps_tour=c.getSum_temps_tour();
        int i=0;
        if ((c.calucler_temps_total_tournees_dynamic()+sum_temps_tour)<=this.getTime_slice())
        {
            this.setTemps_total_voyage(this.getTemps_total_voyage()+c.calucler_temps_total_tournees_dynamic());
             //System.out.println("hha temps totaaaaal voyaaage  "+this.getTemps_total_voyage());
            c.setTemps_tournees_avant(c.getTemps_tournees_avant()+ c.calucler_temps_total_tournees_dynamic());
             c.setId_fictif_final(0);
             c.setDepot_fictif_final(null);
             c.setSum_temps_tour(0);
           /*  System.out.println("haaa le camion : "+c.getIdcamion());
             System.out.println("haaa temps total tournées : "+c.getTemps_tournees_avant());
              System.out.println("haaa sum temps tour : "+c.getSum_temps_tour());*/
             
          
        }
       // System.out.println("time_slice : "+ this.getTime_slice());
        else
        {
          
        while ((sum_temps_tour < this.getTime_slice())&& (c.getTournee_attribuees().size()>i))
        {
            sum_temps_tour= sum_temps_tour+c.getTournee_attribuees().get(i).getTemps_dynamic();
            i++;
        }
        if (sum_temps_tour==this.getTime_slice()){ //&& (c.getTournee_attribuees().size()>j)){
               // for (int l=0; l<c.getTournee_attribuees().get(j).getCustomers().size();l++)
               this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour-sum);
               c.setTemps_tournees_avant(c.getTemps_tournees_avant()+ sum_temps_tour-sum);
               c.setId_fictif_final(0);
               c.setDepot_fictif_final(null);
               c.setSum_temps_tour(0);
             /*   System.out.println("haaa le camion : "+c.getIdcamion());
                System.out.println("haaa temps total tournées : "+c.getTemps_tournees_avant());
                System.out.println("haaa sum temps tour : "+c.getSum_temps_tour());
               */
                 for (int j=i; j<c.getTournee_attribuees().size(); j++)
            {
                Tour tr= new Tour(this.sol_preced);
                tr.setId_fictif(0);
                
               // for (int l=0; l<c.getTournee_attribuees().get(j).getCustomers().size();l++)
                for (Customer c1: c.getTournee_attribuees().get(j).getCustomers())
                {
                    this.clients_old.add(c1);
                     tr.getCustomers().add(c1); 
                   //  System.out.println("haaa les clients old  3: "+c1.getId());
                    //System.out.println("id client à ajouter_dynamic "+ c1.getId());
                    
                }
                trs.add(tr);
            }
            }
        else
        {
            if (c.getTournee_attribuees().get(i-1).getId_fictif()==0)
            {
                if (c.getTournee_attribuees().get(i-1).getCustomers().size()<2 )
                {
                    sum_temps_tour= sum_temps_tour-c.getTournee_attribuees().get(i-1).getTemps_dynamic();
                    if ((sum_temps_tour= sum_temps_tour+this.getProblemD().gettimes_dynamic(0, c.getTournee_attribuees().get(i-1).getCustomers().get(0).getId())+this.temps_service)> this.getTime_slice())
                    {
                         double cap_cl_fic=this.getProblemD().getCapacitycamion_dynamic();
                       cap_cl_fic=cap_cl_fic-c.getTournee_attribuees().get(i-1).getCustomers().get(0).getDemande();
                      int id_cl_fic=c.getTournee_attribuees().get(i-1).getCustomers().get(0).getId();
                       depo_fics.add(new     Depotfictif(id_cl_fic,cap_cl_fic, b));
                       this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour-sum);
                       c.setTemps_tournees_avant(c.getTemps_tournees_avant()+ sum_temps_tour-sum);
                       c.setId_fictif_final(id_cl_fic); 
                       c.setDepot_fictif_final(depo_fics.get(b-1));
                       c.setSum_temps_tour(sum_temps_tour-this.getTime_slice());
                      //  System.out.println("haaa le camion : "+c.getIdcamion());
                         //System.out.println("haaa temps total tournées : "+c.getTemps_tournees_avant());
                           //System.out.println("haaa sum temps tour : "+c.getSum_temps_tour());
                       Tour trr= new Tour (this.sol_preced);
                       trr.setId_fictif(b);
                       trs.add(trr);
                          
                          b++;
                    }
                    else {this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour+this.getProblemD().gettimes_dynamic(c.getTournee_attribuees().get(i-1).getCustomers().get(0).getId(),0)-sum);
                    c.setTemps_tournees_avant(c.getTemps_tournees_avant()+ sum_temps_tour+this.getProblemD().gettimes_dynamic(c.getTournee_attribuees().get(i-1).getCustomers().get(0).getId(),0)-sum);
                     c.setSum_temps_tour(sum_temps_tour+this.getProblemD().gettimes_dynamic(c.getTournee_attribuees().get(i-1).getCustomers().get(0).getId(),0)-this.getTime_slice());
                   // System.out.println("haaa le camion : "+c.getIdcamion());
                    c.setId_fictif_final(0);
                    c.setDepot_fictif_final(null);
                    // System.out.println("haaa temps total tournées : "+c.getTemps_tournees_avant());
                    //  System.out.println("haaa sum temps tour : "+c.getSum_temps_tour());
                    }
                    
                       for (int m=i;m<c.getTournee_attribuees().size();m++)
                       {
                           Tour tr= new Tour (this.sol_preced);
                           tr.setId_fictif(0);
                      for (int n=0;n<c.getTournee_attribuees().get(m).getCustomers().size();n++)
                           {
                            tr.getCustomers().add(c.getTournee_attribuees().get(m).getCustomers().get(n)); 
                            this.clients_old.add(c.getTournee_attribuees().get(m).getCustomers().get(n)); 
                            //System.out.println("haaa client old 4 : "+c.getTournee_attribuees().get(m).getCustomers().get(n).getId());
                           }
                      trs.add(tr);
                         }
                    
                    
                   
                }
                else
                {
                int j=0;
        double cap_cl_fic=this.getProblemD().getCapacitycamion_dynamic();
        sum_temps_tour= sum_temps_tour-c.getTournee_attribuees().get(i-1).getTemps_dynamic();
        sum_temps_tour= sum_temps_tour+ this.getProblemD().gettimes_dynamic(0, c.getTournee_attribuees().get(i-1).getCustomers().get(0).getId())+this.getTemps_service();
          cap_cl_fic=cap_cl_fic-c.getTournee_attribuees().get(i-1).getCustomers().get(0).getDemande();
        while ((sum_temps_tour< this.getTime_slice())&&(c.getTournee_attribuees().get(i-1).getCustomers().size()>(j+1)))
        {
            j++;
            sum_temps_tour=sum_temps_tour+this.getProblemD().gettimes_dynamic(c.getTournee_attribuees().get(i-1).getCustomers().get(j-1).getId(),c.getTournee_attribuees().get(i-1).getCustomers().get(j).getId())+this.getTemps_service();
            cap_cl_fic=cap_cl_fic-c.getTournee_attribuees().get(i-1).getCustomers().get(j).getDemande();
           
             
        }
        if ((j==(c.getTournee_attribuees().get(i-1).getCustomers().size()-1))&&(sum_temps_tour< this.getTime_slice()))
        {
            sum_temps_tour=sum_temps_tour+this.getProblemD().gettimes_dynamic(c.getTournee_attribuees().get(i-1).getCustomers().get(j).getId(),0);

      System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
          this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour-sum);
        c.setTemps_tournees_avant(c.getTemps_tournees_avant()+ sum_temps_tour-sum);
        c.setId_fictif_final(0);
        c.setDepot_fictif_final(null);
        c.setSum_temps_tour(sum_temps_tour-this.getTime_slice());
              
        }
        else {
            int id_cl_fic=c.getTournee_attribuees().get(i-1).getCustomers().get(j).getId();
       Tour trr = new Tour(this.sol_preced);
       trr.setId_fictif(b);
        for (int l=j+1; l<c.getTournee_attribuees().get(i-1).getCustomers().size();l++)
        {
            trr.getCustomers().add(c.getTournee_attribuees().get(i-1).getCustomers().get(l));
            this.clients_old.add(c.getTournee_attribuees().get(i-1).getCustomers().get(l));
            //
           //System.out.println("haa les clients old  5"+ c.getTournee_attribuees().get(i-1).getCustomers().get(l).getId());
        }
        trs.add(trr);
         depo_fics.add(new     Depotfictif(id_cl_fic,cap_cl_fic, b));
        this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour-sum);
        c.setTemps_tournees_avant(c.getTemps_tournees_avant()+ sum_temps_tour-sum);
        c.setId_fictif_final(id_cl_fic);
        c.setDepot_fictif_final(depo_fics.get(b-1));
        c.setSum_temps_tour(sum_temps_tour-this.getTime_slice());
         b++;
        }
        
        
        for (int m=i;m<c.getTournee_attribuees().size();m++)
        {
             Tour tr= new Tour(this.sol_preced);
             tr.setId_fictif(0);
            for (int n=0;n<c.getTournee_attribuees().get(m).getCustomers().size();n++)
            {
                tr.getCustomers().add(c.getTournee_attribuees().get(m).getCustomers().get(n));
               this.clients_old.add(c.getTournee_attribuees().get(m).getCustomers().get(n)); 
              // System.out.println("haa les clients old  5"+ c.getTournee_attribuees().get(m).getCustomers().get(n).getId());
            }
            trs.add(tr);
        }
            
       
       /*  System.out.println("haaa le camion : "+c.getIdcamion());
             System.out.println("haaa temps total tournées : "+c.getTemps_tournees_avant());
              System.out.println("haaa sum temps tour : "+c.getSum_temps_tour());*/
        
       
        //System.out.println("id depot fictif à ajouter "+id_cl_fic); 
        
            }
            }
            else
            {
            if(c.getTournee_attribuees().get(i-1).getCustomers().size()> 0)
            {
                int j=0;   
               Depotfictif d;
               //System.out.println("ooooooooooooo:   "+c.getTournee_attribuees().get(i-1).getId_fictif());
         d=this.getProblemD().getdepotfictifById_dynamic(c.getTournee_attribuees().get(i-1).getId_fictif());
         double cap_cl_fic=d.getCapacité_restante();

        int from_client_fictif= d.getId_client_fictif();
        sum_temps_tour= sum_temps_tour-c.getTournee_attribuees().get(i-1).getTemps_dynamic();
        sum_temps_tour= sum_temps_tour+ this.getProblemD().gettimes_dynamic(from_client_fictif, c.getTournee_attribuees().get(i-1).getCustomers().get(0).getId())+this.getTemps_service();
          cap_cl_fic=cap_cl_fic-c.getTournee_attribuees().get(i-1).getCustomers().get(0).getDemande();
        while ((sum_temps_tour< this.getTime_slice())&&(c.getTournee_attribuees().get(i-1).getCustomers().size()>(j+1)) )
        {
            j++;
            sum_temps_tour=sum_temps_tour+this.getProblemD().gettimes_dynamic(c.getTournee_attribuees().get(i-1).getCustomers().get(j-1).getId(),c.getTournee_attribuees().get(i-1).getCustomers().get(j).getId())+this.getTemps_service();
            cap_cl_fic=cap_cl_fic-c.getTournee_attribuees().get(i-1).getCustomers().get(j).getDemande();
           
                
        }
        if ((j==(c.getTournee_attribuees().get(i-1).getCustomers().size()-1))&&(sum_temps_tour< this.getTime_slice()))
        {
            sum_temps_tour=sum_temps_tour+this.getProblemD().gettimes_dynamic(c.getTournee_attribuees().get(i-1).getCustomers().get(j).getId(),0);

      System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
          this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour-sum);
        c.setTemps_tournees_avant(c.getTemps_tournees_avant()+ sum_temps_tour-sum);
        c.setId_fictif_final(0);
        c.setDepot_fictif_final(null);
        c.setSum_temps_tour(sum_temps_tour-this.getTime_slice());
              
        }
        else
        {
        int id_cl_fic=c.getTournee_attribuees().get(i-1).getCustomers().get(j).getId();
        Tour trr=new Tour (this.sol_preced);
        trr.setId_fictif(b);
       
        for (int l=j+1; l<c.getTournee_attribuees().get(i-1).getCustomers().size();l++)
        {
            trr.getCustomers().add(c.getTournee_attribuees().get(i-1).getCustomers().get(l));
            this.clients_old.add(c.getTournee_attribuees().get(i-1).getCustomers().get(l));
          // System.out.println("haa les clients old 6"+ c.getTournee_attribuees().get(i-1).getCustomers().get(l).getId());
        }
        trs.add(trr);
        depo_fics.add(new     Depotfictif(id_cl_fic,cap_cl_fic, b));
        this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour-sum);
        c.setTemps_tournees_avant(c.getTemps_tournees_avant()+ sum_temps_tour-sum);
        c.setSum_temps_tour(sum_temps_tour-this.getTime_slice());
        c.setDepot_fictif_final(depo_fics.get(b-1));
        c.setId_fictif_final(id_cl_fic);
        /* System.out.println("haaa le camion : "+c.getIdcamion());
             System.out.println("haaa temps total tournées : "+c.getTemps_tournees_avant());
              System.out.println("haaa sum temps tour : "+c.getSum_temps_tour());*/
        b++;
        }
        for (int m=i;m<c.getTournee_attribuees().size();m++)
        {
            Tour tr  = new Tour(this.sol_preced);
            tr.setId_fictif(0);
            for (int n=0;n<c.getTournee_attribuees().get(m).getCustomers().size();n++)
            {
               tr.getCustomers().add(c.getTournee_attribuees().get(m).getCustomers().get(n));
               this.clients_old.add(c.getTournee_attribuees().get(m).getCustomers().get(n));  
            //   System.out.println("haaa client old  6: "+c.getTournee_attribuees().get(m).getCustomers().get(n).getId());
            }
            trs.add(tr);
        }
            
        
        }
            
            else {
                this.setTemps_total_voyage(this.getTemps_total_voyage()+sum_temps_tour-sum);
                c.setTemps_tournees_avant(c.getTemps_tournees_avant()+ sum_temps_tour-sum);
                
                c.setSum_temps_tour(sum_temps_tour-this.getTime_slice());
                c.setDepot_fictif_final(null);
                c.setId_fictif_final(0);
                 /*System.out.println("haaa le camion : "+c.getIdcamion());
                 System.out.println("haaa temps total tournées : "+c.getTemps_tournees_avant());
                  System.out.println("haaa sum temps tour : "+c.getSum_temps_tour());*/
                for (int m=i;m<c.getTournee_attribuees().size();m++)
                  {
                      Tour tr= new Tour (this.sol_preced);
                     for (int n=0;n<c.getTournee_attribuees().get(m).getCustomers().size();n++)
                         {
                             tr.getCustomers().add(c.getTournee_attribuees().get(m).getCustomers().get(n));
                          this.clients_old.add(c.getTournee_attribuees().get(m).getCustomers().get(n)); 
                         // System.out.println("haaa client old   7: "+c.getTournee_attribuees().get(m).getCustomers().get(n).getId());
                          }
                     trs.add(tr);
                  }
                 }   
            }
        }
    }
        }
    }
    
   if ( this.sol_preced.getTours()!=null)
   this.sol_preced.getTours().clear();
     for (int k=0; k<trs.size();k++)
     {
       this.sol_preced.getTours().add(trs.get(k));
     }
    this.getCamions().clear();
     for(int k=1 ;k<=this.getBest().getTourGroup().getCamions().size(); k++)
    {
      this.getCamions().add(this.getBest().getTourGroup().getCamions().get(k-1));
    }
      //System.out.println("hoooooooooooooooooooooooooooooooooohiiiiiiiiiiiiii : "+depo_fics.size());
      for (Depotfictif dep: depo_fics)
                {
                    this.DEPO_FIC.add(dep);
             //       System.out.println("id client à ajouter_dynamic "+ c1.getId());
                    
                }
      System.out.println("haa get temps dernier voyage à la fin: "+this.getTemps_total_dernier_voyage());
      System.out.println("haa get temps dernier voyage b calculer à la fin : "+ this.getBest().getTourGroup().getCamions().get(0).calucler_temps_total_tournees_dynamic());
 System.out.println("haa get temps dernier voyage b calculer au debut : "+ this.getCamions().get(0).calucler_temps_total_tournees_dynamic());
    for (int i=0;i<this.getCamions().get(0).getTournee_attribuees().size();i++)
    {
        System.out.println("id tour:"+this.getCamions().get(0).getTournee_attribuees().get(i).getId_fictif());
        for (int j=0;j<this.getCamions().get(0).getTournee_attribuees().get(i).getCustomers().size();j++)
        {System.out.println("id client:"+this.getCamions().get(0).getTournee_attribuees().get(i).getCustomers().get(j).getId());}
    }
    
                       
    //return depo_fics;
}
public void sort_dynamic(ArrayList<TourGroup> trg){
    
          boolean permut;
         TourGroup tampon1=null;
         TourGroup tampon2= null;
          do
          {
              permut= false;
              for (int i=0; i<trg.size()-1;i++)
              {
                if (trg.get(i).compareTo_dynamic(trg.get(i+1))>0)
                {
                    
                    tampon1=trg.get(i);
                    tampon2=trg.get(i+1);
                    trg.set(i, tampon2);
                    trg.set(i+1, tampon1);
                    permut=true;
                }   
              }
          }while (permut);
      }
public void sort_dynamic_sol(ArrayList<Solution> sol){
    
          boolean permut;
         Solution tampon1=null;
         Solution tampon2= null;
          do
          {
              permut= false;
              for (int i=0; i<sol.size()-1;i++)
              {
                if (sol.get(i).compareTo_dynamic(sol.get(i+1))>0)
                {
                    
                    tampon1=sol.get(i);
                    tampon2=sol.get(i+1);
                    sol.set(i, tampon2);
                    sol.set(i+1, tampon1);
                    permut=true;
                }   
              }
          }while (permut);
      }
/*public void initialiser_system(){
     for (int i=0; i< this.clients_old.size();i++)
         {
             this.getProblemD().getCustomers_dynamic().add(this.clients_old.get(i));
         }
     for (int i=0; i< this.DEPO_FIC.size();i++)
         {
             this.getProblemD().getDepots_fictif().add(this.DEPO_FIC.get(i));
         }
}*/
    
}
    

