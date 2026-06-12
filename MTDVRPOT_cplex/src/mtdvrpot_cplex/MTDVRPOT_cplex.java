/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mtdvrpot_cplex;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author asus
 */
public class MTDVRPOT_cplex {

    /**
     * @param args the command line arguments
     * 
     */
    /*problem_dynamic.setCustomers_dynamic(dynamic);
//problem_dynamic.setTout_les_clients(Tout);
problem_dynamic.setDepots_fictif(depo_fic);
problem_dynamic.setDepot_central(depot);
problem_dynamic.setMaxTemps_dynamic(200);
problem_dynamic.setNombrecamions_dynamic(8);
problem_dynamic.setCapacitycamion_dynamic(160);
//problem_dynamic.setVitesse(1);
//problem_dynamic.setNombre_nouveaux_clients(25);
problem_dynamic.setNombre_depot_fictifs(4);
problem_dynamic.setOvetime_dynamic(0);
problem_dynamic.setTemps_service(15);
    problem.setMaxTemps(551);
problem.setDepot(depot);
problem.setNombrecamions(8);
problem.setCapacitycamion(160);
problem.setOvertimepermis(0);
    sys.setIterationNumber(100);
sys.setConstantPheromones(1);
sys.setEvaporation(0.1);
sys.setAlpha(1);
sys.setBeta1(1);
sys.setQ0(0.9)
sys.setTaux_max(2);
sys.setTime_slice(15);
sys.setTemps_service(15);*/
    public static int test=0;
   public static double max_temps= 355;//351;//1500;
    public static int nbr_camions=4;
    public static int capacity_camion=1122;
    public static double ovetime=300;
    public static double vitesse=1;
    public static int t_max=6;
    public static double time_slice=14;
    public static double temp_service=0;
     public static double taille_prob=10;
   // public double temps_total=0;
    
  public static void get_data(){
    

    
Customer	s1	=	new	Customer(	1	,	"1	"	,	              -7     ,         9      ,       94					);  
Customer	s2	=	new	Customer(	2	,	"2	"	,	              -7     ,         8     ,        12					);
Customer	s3	=	new	Customer(	3	,	"3	"	,	             -15     ,        16     ,        17					);
Customer	s4	=	new	Customer(	4	,	"4	"	,	              -2     ,        13     ,       619					);
Customer	s5	=	new	Customer(	5	,	"5	"	,	               0     ,         3     ,        61					);
Customer	s6	=	new	Customer(	6	,	"6	"	,	              -9     ,         6     ,         3					);
Customer	s7	=	new	Customer(	7	,	"7	"	,	               2     ,         5     ,         4					);
Customer	s8	=	new	Customer(	8	,	"8	"	,	             -13     ,         9     ,        13					);
Customer	s9	=	new	Customer(	9	,	"9	"	,	             -15     ,        10     ,        44					);
Customer	s10	=	new	Customer(	10	,	"10	"	,	             -14     ,         2     ,        12					);
Customer	s11	=	new	Customer(	11	,	"11	"	,	               1     ,         0     ,        35					);
Customer	s12	=	new	Customer(	12	,	"12	"	,	             -40     ,        24     ,      114					);
Customer	s13	=	new	Customer(	13	,	"13	"	,	             -54     ,       -15     ,        29					);
Customer	s14	=	new	Customer(	14	,	"14	"	,	             -43     ,        10     ,        76					);
Customer	s15	=	new	Customer(	15	,	"15	"	,	             -73     ,        -2     ,       106					);
Customer	s16	=	new	Customer(	16	,	"16	"	,	             -76     ,         4     ,       157					);
Customer	s17	=	new	Customer(	17	,	"17	"	,	             -45     ,        31     ,        43					);
Customer	s18	=	new	Customer(	18	,	"18	"	,	             -29     ,        36     ,         4					);
Customer	s19	=	new	Customer(	19	,	"19	"	,	             -78     ,        11     ,        38					);
Customer	s20	=	new	Customer(	20	,	"20	"	,	             -31     ,        25     ,       212					);
Customer	s21	=	new	Customer(	21	,	"21	"	,	             -67     ,        17     ,        42					);
Customer	s22	=	new	Customer(	22	,	"22	"	,	             -31     ,         4     ,        10					);
Customer	s23	=	new	Customer(	23	,	"23	"	,	             -51     ,        10     ,        19					);
Customer	s24	=	new	Customer(	24	,	"24	"	,	             -30     ,        -9     ,       856					);
Customer	s25	=	new	Customer(	25	,	"25	"	,	             -51     ,        24     ,        13					);
Customer	s26	=	new	Customer(	26	,	"26	"	,	             -80     ,       -10     ,        67					);
Customer	s27	=	new	Customer(	27	,	"27	"	,	             -34     ,         4     ,       144					);
Customer	s28	=	new	Customer(	28	,	"28	"	,	             -45     ,        25     ,       310					);
Customer	s29	=	new	Customer(	29	,	"29	"	,	              73     ,        32     ,        85					);
Customer	s30	=	new	Customer(	30	,	"30	"	,	              78     ,        59     ,      1061					);
Customer	s31	=	new	Customer(	31	,	"31	"	,	              58     ,        53     ,       344					);
Customer	s32	=	new	Customer(	32	,	"32	"	,	              57     ,        55     ,        22					);
Customer	s33	=	new	Customer(	33	,	"33	"	,	              20     ,        85     ,        15					);
Customer	s34	=	new	Customer(	34	,	"34	"	,	             -12     ,        81     ,       219					);
Customer	s35	=	new	Customer(	35	,	"35	"	,	              -8     ,        86     ,        44					);
Customer	s36	=	new	Customer(	36	,	"36	"	,	              12     ,        33     ,       370					);
Customer	s37	=	new	Customer(	37	,	"37	"	,	              19     ,        86     ,        74					);
Customer	s38	=	new	Customer(	38	,	"38	"	,	             -11     ,        44     ,        82					);
Customer	s39	=	new	Customer(	39	,	"39	"	,	              -2     ,        67     ,         3					);
Customer	s40	=	new	Customer(	40	,	"40	"	,	              25     ,        76     ,        39					);
Customer	s41	=	new	Customer(	41	,	"41	"	,	             -29     ,        44     ,        54					);
Customer	s42	=	new	Customer(	42	,	"42	"	,	               4     ,        80     ,        22					);
Customer	s43	=	new	Customer(	43	,	"43	"	,	             -2       ,      84      ,      171					);
Customer	s44	=	new	Customer(	44	,	"44	"	,	              -8       ,      54     ,        65					);
Customer	s45	=	new	Customer(	45	,	"45	"	,	             -16       ,      34     ,       405					);
Customer	s46	=	new	Customer(	46	,	"46	"	,	             -14       ,      80     ,        19					);
Customer	s47	=	new	Customer(	47	,	"47	"	,	             -17       ,      32     ,         7					);
Customer	s48	=	new	Customer(	48	,	"48	"	,	              19       ,      39     ,       586					);
Customer	s49	=	new	Customer(	49	,	"49	"	,	             -20       ,      82     ,        15					);
Customer	s50	=	new	Customer(	50	,	"50	"	,	              17       ,      42     ,       149					);
Customer	s51	=	new	Customer(	51	,	"51	"	,	              -4       ,       5     ,       141					);
Customer	s52	=	new	Customer(	52	,	"52	"	,	             -20       ,      25     ,         9					);
Customer	s53	=	new	Customer(	53	,	"53	"	,	             -12       ,      14     ,       261					);
Customer	s54	=	new	Customer(	54	,	"54	"	,	             -11       ,      11     ,         4					);
Customer	s55	=	new	Customer(	55	,	"55	"	,	             -12       ,       4     ,         5					);
Customer	s56	=	new	Customer(	56	,	"56	"	,	              -1       ,      21     ,        21					);
Customer	s57	=	new	Customer(	57	,	"57	"	,	              -1       ,       1     ,        25					);
Customer	s58	=	new	Customer(	58	,	"58	"	,	              -9       ,      21     ,        86					);
Customer	s59	=	new	Customer(	59	,	"59	"	,	               9       ,       3     ,        86					);
Customer	s60	=	new	Customer(	60	,	"60	"	,	               5       ,      14     ,       124					);
Customer	s61	=	new	Customer(	61	,	"61	"	,	              -9       ,      27     ,       123					);
Customer	s62	=	new	Customer(	62	,	"62	"	,	             -20       ,      11     ,        11					);
Customer	s63	=	new	Customer(	63	,	"63	"	,	               0       ,      30     ,        41					);
Customer	s64	=	new	Customer(	64	,	"64	"	,	             -12       ,      15     ,       279					);
Customer	s65	=	new	Customer(	65	,	"65	"	,	              -3       ,      17     ,       149					);
Customer	s66	=	new	Customer(	66	,	"66	"	,	             -58       ,     -60     ,         9					);
Customer	s67	=	new	Customer(	67	,	"67	"	,	             -71       ,     -58     ,        65					);
Customer	s68	=	new	Customer(	68	,	"68	"	,	             -32       ,     -34     ,       155					);
Customer	s69	=	new	Customer(	69	,	"69	"	,	             -59       ,     -37     ,         6					);
Customer	s70	=	new	Customer(	70	,	"70	"	,	             -48       ,     -19     ,        83					);
Customer	s71	=	new	Customer(	71	,	"71	"	,	             -71       ,     -49     ,        11					);
Customer	s72	=	new	Customer(	72	,	"72	"	,	              22       ,      -4     ,       735					);
Customer	s73	=	new	Customer(	73	,	"73	"	,	              13       ,     -10     ,         4					);
Customer	s74	=	new	Customer(	74	,	"74	"	,	              22       ,     -17     ,        56					);
Customer	s75	=	new	Customer(	75	,	"75	"	,	              13       ,      12     ,        26					);


    
 Depotcentral depot= new Depotcentral(0,"Depot",0,0);




ArrayList<Customer> Tout = new ArrayList<Customer> () ;
ArrayList<Customer> statiq = new ArrayList<Customer> () ;
ArrayList<Customer> dynamic1 = new ArrayList<Customer> () ;
ArrayList<Customer> dynamic2 = new ArrayList<Customer> () ;
ArrayList<Customer> dynamic3 = new ArrayList<Customer> () ;
ArrayList<Customer> dynamic4 = new ArrayList<Customer> () ;
ArrayList<Customer> dynamic5 = new ArrayList<Customer> () ;
ArrayList<Customer> dynamic6 = new ArrayList<Customer> () ;
ArrayList<Customer> dynamic7 = new ArrayList<Customer> () ;
ArrayList<Customer> dynamic8 = new ArrayList<Customer> () ;
ArrayList<Customer> dynamic9 = new ArrayList<Customer> () ;
ArrayList<Customer> dynamic10 = new ArrayList<Customer> () ;
ArrayList<Customer> dynamic11 = new ArrayList<Customer> () ;
ArrayList<Customer> dynamic12 = new ArrayList<Customer> () ;
ArrayList<Customer> dynamic13 = new ArrayList<Customer> () ;



//ArrayList<Depotfictif> depo_fic = new ArrayList<Depotfictif> () ;
//public Depotfictif(int id_client_fictif,double capacité, int id_depot_fictif)




Tout.add(s1);Tout.add(s2);Tout.add(s3);Tout.add(s4);Tout.add(s5);Tout.add(s6);Tout.add(s7);Tout.add(s8);Tout.add(s9);Tout.add(s10);
Tout.add(s11);Tout.add(s12);Tout.add(s13);Tout.add(s14);Tout.add(s15);Tout.add(s16);Tout.add(s17);Tout.add(s18);Tout.add(s19);Tout.add(s20);
Tout.add(s21);Tout.add(s22);Tout.add(s23);Tout.add(s24);Tout.add(s25);Tout.add(s26);Tout.add(s27);Tout.add(s28);Tout.add(s29);Tout.add(s30);
Tout.add(s31);Tout.add(s32);Tout.add(s33);Tout.add(s34);Tout.add(s35);Tout.add(s36);Tout.add(s37);Tout.add(s38);Tout.add(s39);Tout.add(s40);
Tout.add(s41);Tout.add(s42);Tout.add(s43);Tout.add(s44);Tout.add(s45);Tout.add(s46);Tout.add(s47);Tout.add(s48);Tout.add(s49);Tout.add(s50);
Tout.add(s51);Tout.add(s52);Tout.add(s53);Tout.add(s54);Tout.add(s55);Tout.add(s56);Tout.add(s57);Tout.add(s58);Tout.add(s59);Tout.add(s60);
Tout.add(s61);Tout.add(s62);Tout.add(s63);Tout.add(s64);Tout.add(s65);Tout.add(s66);Tout.add(s67);Tout.add(s68);Tout.add(s69);Tout.add(s70);
Tout.add(s71);Tout.add(s72);Tout.add(s73);Tout.add(s74);Tout.add(s75);

/*statiq.add(s43);statiq.add(s44);statiq.add(s45);statiq.add(s46);statiq.add(s47);statiq.add(s48);statiq.add(s49);
statiq.add(s50);statiq.add(s51);statiq.add(s52);statiq.add(s53);statiq.add(s54);statiq.add(s55);statiq.add(s56);statiq.add(s57);statiq.add(s58);statiq.add(s59);
statiq.add(s60);statiq.add(s61);statiq.add(s62);statiq.add(s63);statiq.add(s64);statiq.add(s65);statiq.add(s66);statiq.add(s67);statiq.add(s68);statiq.add(s69);
statiq.add(s70);statiq.add(s71);statiq.add(s72);statiq.add(s73);*/statiq.add(s74);statiq.add(s75);

dynamic1.add(s1);//dynamic1.add(s2);dynamic1.add(s3);

dynamic2.add(s4);//dynamic2.add(s5);dynamic2.add(s6);dynamic2.add(s7);

dynamic3.add(s8);//dynamic3.add(s9);dynamic3.add(s10);dynamic3.add(s11);

dynamic4.add(s12);//dynamic4.add(s13);dynamic4.add(s14);

dynamic5.add(s15);//dynamic5.add(s16);dynamic5.add(s17);

dynamic6.add(s18);//dynamic6.add(s19);dynamic6.add(s20);

dynamic7.add(s21);//dynamic7.add(s22);dynamic7.add(s23);

dynamic8.add(s24);//dynamic8.add(s25);dynamic8.add(s26);

dynamic9.add(s27);

dynamic10.add(s28);//dynamic10.add(s29);dynamic10.add(s30);dynamic10.add(s31);dynamic10.add(s32);dynamic10.add(s33);

dynamic11.add(s34);//dynamic11.add(s35);dynamic11.add(s36);dynamic11.add(s37);dynamic11.add(s38);

dynamic12.add(s39);//dynamic12.add(s40);

dynamic13.add(s41);//dynamic13.add(s42);



Camion c1= new Camion (1);
    Camion c2= new Camion (2);
    Camion c3= new Camion (3);
    Camion c4= new Camion (4);
    Camion c5= new Camion (5);
    Camion c6= new Camion (6);
   
    

ArrayList<Camion> camions = new ArrayList<Camion> () ;
//ArrayList<Camion> camions2 = new ArrayList<Camion> () ;
//camions2.clear();
 /*Camion c11= new Camion (11);
 Camion c22= new Camion (22);
 Camion c33= new Camion (33);
 Camion c44= new Camion (44);*/


camions.clear();
camions.add(c1);
/*camions.add(c2);
camions.add(c3);
/*camions.add(c4);
camions.add(c5);
camions.add(c6);*/






VRP_total VRPT= new VRP_total (Tout, depot, MTDVRPOT_cplex.vitesse);



VRPS problem= new VRPS(statiq,MTDVRPOT_cplex.max_temps,depot,MTDVRPOT_cplex.nbr_camions,MTDVRPOT_cplex.capacity_camion,MTDVRPOT_cplex.ovetime);
Ourdynamicmodel sys= new Ourdynamicmodel(VRPT,problem,MTDVRPOT_cplex.t_max,MTDVRPOT_cplex.time_slice,MTDVRPOT_cplex.temp_service, camions, MTDVRPOT_cplex.ovetime,MTDVRPOT_cplex.max_temps);
sys.setOver(0);
sys.solveMe_static();
sys.setDEPO_FIC(sys.get_depo_fic());
          System.out.println("sum_temps_tour camionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnns: "+camions.get(0).getSum_temps_tour());  
// System.out.println("gettemps totalvoyage juste après la fonction:"+t.getTemps_total_voyage());
      System.out.println("temps total tournées avant camionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnns: "+camions.get(0).getTemps_tournees_avant());
      System.out.println("temps total voyaage syyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyys: "+sys.getTemps_total_voyage());

VRPD problem_dynamic1= new VRPD(VRPT,dynamic1,sys.getDEPO_FIC(),depot,MTDVRPOT_cplex.max_temps-MTDVRPOT_cplex.time_slice,MTDVRPOT_cplex.nbr_camions,MTDVRPOT_cplex.capacity_camion,MTDVRPOT_cplex.ovetime,MTDVRPOT_cplex.temp_service);
Ourdynamicmodel sys_dynamic1= new Ourdynamicmodel (VRPT,problem_dynamic1,MTDVRPOT_cplex.time_slice,MTDVRPOT_cplex.temp_service, sys.getClients_old(), sys.getCamions(),MTDVRPOT_cplex.t_max, MTDVRPOT_cplex.ovetime, MTDVRPOT_cplex.max_temps-MTDVRPOT_cplex.time_slice );
sys_dynamic1.setOver(sys.getOver());
sys_dynamic1.solveMe_dynamic();
//Ourdynamicmodel(VRP_total VRPT, VRPD problem_dynamic, double time_slice, double temps_service, ArrayList<Customer> client_olds,ArrayList<Camion> camions, int nombre_max_tour) {
  
   System.out.println("sum_temps_tour camionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnns: "+camions.get(0).getSum_temps_tour());       
System.out.println("temps total tournées avant camionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnns: "+camions.get(0).getTemps_tournees_avant());
      System.out.println("temps total voyaage syyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyys 1111111111111111111111111: "+sys_dynamic1.getTemps_total_voyage());

     // System.out.println("temps total tournées avant camions22222222222222: "+camions.get(0).getTemps_tournees_avant());
     // System.out.println("temps total tournées avant camions2222222222: "+(sys.getTemps_total_voyage()+sys_dynamic1.getTemps_total_voyage()));
   
VRPD problem_dynamic2= new VRPD(VRPT,dynamic2,sys_dynamic1.getDEPO_FIC(),depot,MTDVRPOT_cplex.max_temps-(2*MTDVRPOT_cplex.time_slice),MTDVRPOT_cplex.nbr_camions,MTDVRPOT_cplex.capacity_camion,MTDVRPOT_cplex.ovetime,MTDVRPOT_cplex.temp_service);
Ourdynamicmodel sys_dynamic2= new Ourdynamicmodel (VRPT,problem_dynamic2,MTDVRPOT_cplex.time_slice,MTDVRPOT_cplex.temp_service, sys_dynamic1.getClients_old(),sys_dynamic1.getCamions(),MTDVRPOT_cplex.t_max, MTDVRPOT_cplex.ovetime, MTDVRPOT_cplex.max_temps-(2*MTDVRPOT_cplex.time_slice) );
sys_dynamic2.setOver(sys_dynamic1.getOver());
sys_dynamic2.solveMe_dynamic();
              
   System.out.println("sum_temps_tour camionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnns: "+camions.get(0).getSum_temps_tour());        
System.out.println("temps total tournées avant camionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnns: "+camions.get(0).getTemps_tournees_avant());
      System.out.println("temps total voyaage syyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyys 22222222222222222222222222: "+sys_dynamic2.getTemps_total_voyage());

     //  System.out.println("temps total tournées avant camions22222222222222: "+camions.get(0).getTemps_tournees_avant());
     // System.out.println("temps total tournées avant camions2222222222: "+(sys.getTemps_total_voyage()+sys_dynamic1.getTemps_total_voyage()+sys_dynamic2.getTemps_total_voyage()));
   
  
VRPD problem_dynamic3= new VRPD(VRPT,dynamic3,sys_dynamic2.getDEPO_FIC(),depot,MTDVRPOT_cplex.max_temps-(3*MTDVRPOT_cplex.time_slice),MTDVRPOT_cplex.nbr_camions,MTDVRPOT_cplex.capacity_camion,MTDVRPOT_cplex.ovetime,MTDVRPOT_cplex.temp_service);
Ourdynamicmodel sys_dynamic3= new Ourdynamicmodel (VRPT,problem_dynamic3,MTDVRPOT_cplex.time_slice,MTDVRPOT_cplex.temp_service, sys_dynamic2.getClients_old(),sys_dynamic2.getCamions(),MTDVRPOT_cplex.t_max, MTDVRPOT_cplex.ovetime, MTDVRPOT_cplex.max_temps-(3*MTDVRPOT_cplex.time_slice));
sys_dynamic3.setOver(sys_dynamic2.getOver());
sys_dynamic3.solveMe_dynamic();
           
   System.out.println("sum_temps_tour camionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnns: "+camions.get(0).getSum_temps_tour());        
System.out.println("temps total tournées avant camionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnns: "+camions.get(0).getTemps_tournees_avant());
      System.out.println("temps total voyaage syyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyys 33333333333333333333333333333: "+sys_dynamic3.getTemps_total_voyage());


 //System.out.println("temps total tournées avant camions22222222222222: "+camions.get(0).getTemps_tournees_avant());

VRPD problem_dynamic4= new VRPD(VRPT,dynamic4,sys_dynamic3.getDEPO_FIC(),depot,MTDVRPOT_cplex.max_temps-(4*MTDVRPOT_cplex.time_slice),MTDVRPOT_cplex.nbr_camions,MTDVRPOT_cplex.capacity_camion,MTDVRPOT_cplex.ovetime,MTDVRPOT_cplex.temp_service);
Ourdynamicmodel sys_dynamic4= new Ourdynamicmodel (VRPT,problem_dynamic4,MTDVRPOT_cplex.time_slice,MTDVRPOT_cplex.temp_service, sys_dynamic3.getClients_old(),sys_dynamic3.getCamions(),MTDVRPOT_cplex.t_max, MTDVRPOT_cplex.ovetime, MTDVRPOT_cplex.max_temps-(4*MTDVRPOT_cplex.time_slice));
sys_dynamic4.setOver(sys_dynamic3.getOver());
sys_dynamic4.solveMe_dynamic();

   System.out.println("sum_temps_tour camionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnns: "+camions.get(0).getSum_temps_tour());            
System.out.println("temps total tournées avant camionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnns: "+camions.get(0).getTemps_tournees_avant());
          System.out.println("temps total voyaage syyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyys 44444444444444444444444444444444: "+sys_dynamic4.getTemps_total_voyage());


VRPD problem_dynamic5= new VRPD(VRPT,dynamic5,sys_dynamic4.getDEPO_FIC(),depot,MTDVRPOT_cplex.max_temps-(5*MTDVRPOT_cplex.time_slice),MTDVRPOT_cplex.nbr_camions,MTDVRPOT_cplex.capacity_camion,MTDVRPOT_cplex.ovetime,MTDVRPOT_cplex.temp_service);
Ourdynamicmodel sys_dynamic5= new Ourdynamicmodel (VRPT,problem_dynamic5,MTDVRPOT_cplex.time_slice,MTDVRPOT_cplex.temp_service, sys_dynamic4.getClients_old(),sys_dynamic4.getCamions(),MTDVRPOT_cplex.t_max, MTDVRPOT_cplex.ovetime, MTDVRPOT_cplex.max_temps-(5*MTDVRPOT_cplex.time_slice));
sys_dynamic5.setOver(sys_dynamic4.getOver());
sys_dynamic5.solveMe_dynamic();

              System.out.println("sum_temps_tour camionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnns: "+camions.get(0).getSum_temps_tour()); 
           System.out.println("temps total tournées avant camionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnns: "+camions.get(0).getTemps_tournees_avant());
           System.out.println("temps total voyaage syyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyys 5555555555555555555555555555555555: "+sys_dynamic5.getTemps_total_voyage());


VRPD problem_dynamic6= new VRPD(VRPT,dynamic6,sys_dynamic5.getDEPO_FIC(),depot,MTDVRPOT_cplex.max_temps-(6*MTDVRPOT_cplex.time_slice),MTDVRPOT_cplex.nbr_camions,MTDVRPOT_cplex.capacity_camion,MTDVRPOT_cplex.ovetime,MTDVRPOT_cplex.temp_service);
Ourdynamicmodel sys_dynamic6= new Ourdynamicmodel (VRPT,problem_dynamic6,MTDVRPOT_cplex.time_slice,MTDVRPOT_cplex.temp_service, sys_dynamic5.getClients_old(),sys_dynamic5.getCamions(),MTDVRPOT_cplex.t_max, MTDVRPOT_cplex.ovetime, MTDVRPOT_cplex.max_temps-(6*MTDVRPOT_cplex.time_slice));
sys_dynamic6.setOver(sys_dynamic5.getOver());
sys_dynamic6.solveMe_dynamic();
           System.out.println("sum_temps_tour camionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnns: "+camions.get(0).getSum_temps_tour());  
           System.out.println("temps total tournées avant camionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnns: "+camions.get(0).getTemps_tournees_avant());
           System.out.println("temps total voyaage syyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyys 66666666666666666666666666666666666666: "+sys_dynamic6.getTemps_total_voyage());

VRPD problem_dynamic7= new VRPD(VRPT,dynamic7,sys_dynamic6.getDEPO_FIC(),depot,MTDVRPOT_cplex.max_temps-(7*MTDVRPOT_cplex.time_slice),MTDVRPOT_cplex.nbr_camions,MTDVRPOT_cplex.capacity_camion,MTDVRPOT_cplex.ovetime,MTDVRPOT_cplex.temp_service);
Ourdynamicmodel sys_dynamic7= new Ourdynamicmodel (VRPT,problem_dynamic7,MTDVRPOT_cplex.time_slice,MTDVRPOT_cplex.temp_service, sys_dynamic6.getClients_old(),sys_dynamic6.getCamions(),MTDVRPOT_cplex.t_max, MTDVRPOT_cplex.ovetime, MTDVRPOT_cplex.max_temps-(7*MTDVRPOT_cplex.time_slice));
sys_dynamic7.setOver(sys_dynamic6.getOver());
sys_dynamic7.solveMe_dynamic();
            
           System.out.println("sum_temps_tour camionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnns: "+camions.get(0).getSum_temps_tour());  
           System.out.println("temps total tournées avant camionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnns: "+camions.get(0).getTemps_tournees_avant());
           System.out.println("temps total voyaage syyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyys 777777777777777777777777777777777777777: "+sys_dynamic7.getTemps_total_voyage());


VRPD problem_dynamic8= new VRPD(VRPT,dynamic8,sys_dynamic7.getDEPO_FIC(),depot,MTDVRPOT_cplex.max_temps-(8*MTDVRPOT_cplex.time_slice),MTDVRPOT_cplex.nbr_camions,MTDVRPOT_cplex.capacity_camion,MTDVRPOT_cplex.ovetime,MTDVRPOT_cplex.temp_service);
Ourdynamicmodel sys_dynamic8= new Ourdynamicmodel (VRPT,problem_dynamic8,MTDVRPOT_cplex.time_slice,MTDVRPOT_cplex.temp_service, sys_dynamic7.getClients_old(),sys_dynamic7.getCamions(),MTDVRPOT_cplex.t_max, MTDVRPOT_cplex.ovetime, MTDVRPOT_cplex.max_temps-(8*MTDVRPOT_cplex.time_slice));
sys_dynamic8.setOver(sys_dynamic7.getOver());
sys_dynamic8.solveMe_dynamic();
              
           System.out.println("sum_temps_tour camionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnns: "+camions.get(0).getSum_temps_tour());  
           System.out.println("temps total tournées avant camionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnns: "+camions.get(0).getTemps_tournees_avant());
           System.out.println("temps total voyaage syyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyys 88888888888888888888888888888888888888888: "+sys_dynamic8.getTemps_total_voyage());


VRPD problem_dynamic9= new VRPD(VRPT,dynamic9,sys_dynamic8.getDEPO_FIC(),depot,MTDVRPOT_cplex.max_temps-(9*MTDVRPOT_cplex.time_slice),MTDVRPOT_cplex.nbr_camions,MTDVRPOT_cplex.capacity_camion,MTDVRPOT_cplex.ovetime,MTDVRPOT_cplex.temp_service);
Ourdynamicmodel sys_dynamic9= new Ourdynamicmodel (VRPT,problem_dynamic9,MTDVRPOT_cplex.time_slice,MTDVRPOT_cplex.temp_service, sys_dynamic8.getClients_old(),sys_dynamic8.getCamions(),MTDVRPOT_cplex.t_max, MTDVRPOT_cplex.ovetime, MTDVRPOT_cplex.max_temps-(9*MTDVRPOT_cplex.time_slice));
sys_dynamic9.setOver(sys_dynamic8.getOver());
sys_dynamic9.solveMe_dynamic();
             System.out.println("sum_temps_tour camionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnns: "+camions.get(0).getSum_temps_tour());  
           System.out.println("temps total tournées avant camionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnns: "+camions.get(0).getTemps_tournees_avant());
           System.out.println("temps total voyaage syyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyys 99999999999999999999999999999999999999999: "+sys_dynamic9.getTemps_total_voyage());


VRPD problem_dynamic10= new VRPD(VRPT,dynamic10,sys_dynamic9.getDEPO_FIC(),depot,MTDVRPOT_cplex.max_temps-(10*MTDVRPOT_cplex.time_slice),MTDVRPOT_cplex.nbr_camions,MTDVRPOT_cplex.capacity_camion,MTDVRPOT_cplex.ovetime,MTDVRPOT_cplex.temp_service);
Ourdynamicmodel sys_dynamic10= new Ourdynamicmodel (VRPT,problem_dynamic10,MTDVRPOT_cplex.time_slice,MTDVRPOT_cplex.temp_service, sys_dynamic9.getClients_old(),sys_dynamic9.getCamions(),MTDVRPOT_cplex.t_max, MTDVRPOT_cplex.ovetime, MTDVRPOT_cplex.max_temps-(10*MTDVRPOT_cplex.time_slice));
sys_dynamic10.setOver(sys_dynamic9.getOver());
sys_dynamic10.solveMe_dynamic();

           System.out.println("sum_temps_tour camionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnns: "+camions.get(0).getSum_temps_tour());             
           System.out.println("temps total tournées avant camionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnns: "+camions.get(0).getTemps_tournees_avant());
           System.out.println("temps total voyaage syyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyys 10101010101000000000000000000000000000000000: "+sys_dynamic10.getTemps_total_voyage());
           System.out.println("Temps disponible: "+(MTDVRPOT_cplex.max_temps-(11*MTDVRPOT_cplex.time_slice)+MTDVRPOT_cplex.ovetime));

VRPD problem_dynamic11= new VRPD(VRPT,dynamic11,sys_dynamic10.getDEPO_FIC(),depot,MTDVRPOT_cplex.max_temps-(11*MTDVRPOT_cplex.time_slice),MTDVRPOT_cplex.nbr_camions,MTDVRPOT_cplex.capacity_camion,MTDVRPOT_cplex.ovetime,MTDVRPOT_cplex.temp_service);
Ourdynamicmodel sys_dynamic11= new Ourdynamicmodel (VRPT,problem_dynamic11,MTDVRPOT_cplex.time_slice,MTDVRPOT_cplex.temp_service, sys_dynamic10.getClients_old(),sys_dynamic10.getCamions(),MTDVRPOT_cplex.t_max, MTDVRPOT_cplex.ovetime, MTDVRPOT_cplex.max_temps-(11*MTDVRPOT_cplex.time_slice));
sys_dynamic11.setOver(sys_dynamic10.getOver());
sys_dynamic11.solveMe_dynamic();

           System.out.println("sum_temps_tour camionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnns: "+camions.get(0).getSum_temps_tour());  
           System.out.println("temps total tournées avant camionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnns: "+camions.get(0).getTemps_tournees_avant());
           System.out.println("temps total voyage syyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyys 11111111111111111111111111111111111111111111111: "+sys_dynamic11.getTemps_total_voyage());
           System.out.println("Temps disponible: "+(MTDVRPOT_cplex.max_temps-(12*MTDVRPOT_cplex.time_slice)+MTDVRPOT_cplex.ovetime));

VRPD problem_dynamic12= new VRPD(VRPT,dynamic12,sys_dynamic11.getDEPO_FIC(),depot,MTDVRPOT_cplex.max_temps-(12*MTDVRPOT_cplex.time_slice),MTDVRPOT_cplex.nbr_camions,MTDVRPOT_cplex.capacity_camion,MTDVRPOT_cplex.ovetime,MTDVRPOT_cplex.temp_service);
Ourdynamicmodel sys_dynamic12= new Ourdynamicmodel (VRPT,problem_dynamic12,MTDVRPOT_cplex.time_slice,MTDVRPOT_cplex.temp_service, sys_dynamic11.getClients_old(),sys_dynamic11.getCamions(),MTDVRPOT_cplex.t_max, MTDVRPOT_cplex.ovetime, MTDVRPOT_cplex.max_temps-(12*MTDVRPOT_cplex.time_slice));
sys_dynamic12.setOver(sys_dynamic11.getOver());
sys_dynamic12.solveMe_dynamic_definitif();

double over_tot=0;
        for (int i=0;i<sys_dynamic12.getCamions().size();i++)
        {
            //System.out.println("capacity camion : ");
            System.out.println("temps tournées avant sys 12: "+sys_dynamic12.getCamions().get(i).getTemps_tournees_avant());
           // System.out.println("temps total tournées dynamique: "+sys_dynamic12.getCamions().get(i).calucler_temps_total_tournees_dynamic());
           System.out.println("temps total dernier voyage: "+sys_dynamic12.getTemps_total_dernier_voyage());
            over_tot=over_tot+Math.max(0,(sys_dynamic12.getCamions().get(i).getTemps_tournees_avant()+sys_dynamic12.getCamions().get(i).calucler_temps_total_tournees_dynamic()-(MTDVRPOT_cplex.max_temps)));
        }
 double over_max=0;
        for (int i=0; i<sys_dynamic12.getCamions().size();i++)
        {
           over_max=Math.max(over_max,(sys_dynamic12.getCamions().get(i).getTemps_tournees_avant()+sys_dynamic12.getCamions().get(i).calucler_temps_total_tournees_dynamic()-(MTDVRPOT_cplex.max_temps)));
            
        }  
        
   /*double over_tot2=0;
        for (int i=0;i<sys_dynamic12.getCamions().size();i++)
        {

            over_tot2=over_tot2+Math.max(0,(sys_dynamic12.getCamions().get(i).calucler_temps_total_tournees_dynamic()-(Yarabbiyasser.max_temps*0.5)));
        }*/

    System.out.println("distance totaaaaaaaaaaal  :"+ ((sys_dynamic12.getTemps_total_dernier_voyage()+
                                                          sys_dynamic11.getTemps_total_voyage()+sys_dynamic10.getTemps_total_voyage()+
                                                         sys_dynamic9.getTemps_total_voyage()+sys_dynamic8.getTemps_total_voyage()+
                                                          sys_dynamic7.getTemps_total_voyage()+sys_dynamic6.getTemps_total_voyage()+sys_dynamic5.getTemps_total_voyage()+
                                                          sys_dynamic4.getTemps_total_voyage()+sys_dynamic3.getTemps_total_voyage()+sys_dynamic2.getTemps_total_voyage()+
                                                          sys_dynamic1.getTemps_total_voyage()+sys.getTemps_total_voyage())-(MTDVRPOT_cplex.temp_service* MTDVRPOT_cplex.taille_prob)));
     /* System.out.println("cout totaaaaaaaaaaaaaaaaaaaaaaaaaal :"+ ((sys_dynamic13.getTemps_total_dernier_voyage()+sys_dynamic12.getTemps_total_voyage()+
                                                         sys_dynamic11.getTemps_total_voyage()+sys_dynamic10.getTemps_total_voyage()+
                                                         sys_dynamic9.getTemps_total_voyage()+sys_dynamic8.getTemps_total_voyage()+
                                                          sys_dynamic7.getTemps_total_voyage()+sys_dynamic6.getTemps_total_voyage()+sys_dynamic5.getTemps_total_voyage()+
                                                          sys_dynamic4.getTemps_total_voyage()+sys_dynamic3.getTemps_total_voyage()+sys_dynamic2.getTemps_total_voyage()+
                                                          sys_dynamic1.getTemps_total_voyage()+sys.getTemps_total_voyage()-(Yarabbiyasser.temp_service* Tout.size()))+over_tot));
        
   System.out.println("temps totaaaaaaaaaaal  :"+ ((sys_dynamic3.getTemps_total_dernier_voyage()+sys_dynamic2.getTemps_total_voyage()+
                                                          sys_dynamic1.getTemps_total_voyage()+sys.getTemps_total_voyage())));
    */
  // System.out.println("temps totaaaaaaaaaaal dernier voyage  :"+ ((sys_dynamic12.getTemps_total_dernier_voyage())));
    
        //  System.out.println("temps total tournées avant camions222444444444444444444444: "+sys_dynamic13.getCamions().get(sys_dynamic13.getCamions().size()-1).getTemps_tournees_avant());

        
         System.out.println("overtime  totaaaaaaaaaaaaaaaaaaaaaaaaaal : "+over_tot);
        // System.out.println("overtime  totaaaaaaaaaaaaaaaaaaaaaaaaaal 2222 : "+over_tot2);
       System.out.println("overtime  maximaaaaaaaaaaaaaaaaaaaaaaaaaal : "+ over_max);
      // System.out.println("temps total tournées avant camions222444444444444444444444: "+sys_dynamic12.getCamions().get(sys_dynamic12.getCamions().size()-1).getTemps_tournees_avant());

                                         
                                                                  

    } 
    
      
    public static void main(String[] args) {
        
        get_data();
        //System.out.println("hello word");
        

     
        
    }
    
}
