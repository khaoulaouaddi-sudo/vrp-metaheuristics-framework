/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yarabbiyassergenetic;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author asus
 */

public class Yarabbiyassergenetic {
   
    
   

    /**
     * @param args the command line arguments
     * 
     */
    public static double max_temps=799;
    public static int nbr_camions=50;
    public static int capacity_camion=1842;
    public static double ovetime=0;
    public static double vitesse=1;
    public static int maxGenerations=200;//6000;
    public static int maxGenwithoutAmelioration=100;//6000;
    
    public static int populationSize=100;
    public static double croisementProbability=0.9;
    public static double mutationProbability=0.4;
    
    
     public static double time_slice=31.96;//60;
    public static double temp_service=29;//45;
    public static int nbtripsbyVehicle=20;
    public static int nbtImprove=8;
    
  
     public static void get_data(){
Customer	s1	=	new	Customer(	1	,	"1	"	,	           35       ,     -56    ,         50			);
Customer	s2	=	new	Customer(	2	,	"2	"	,	            72      ,      -58    ,         50			);
Customer	s3	=	new	Customer(	3	,	"3	"	,	             70    ,        -66     ,       170			);
Customer	s4	=	new	Customer(	4	,	"4	"	,	         45         ,   -40           , 297			);
Customer	s5	=	new	Customer(	5	,	"5	"	,	            39        ,    -40          ,    9			);
Customer	s6	=	new	Customer(	6	,	"6	"	,	            60         ,   -50    ,        630			);
Customer	s7	=	new	Customer(	7	,	"7	"	,	             42        ,    -59     ,       179			);
Customer	s8	=	new	Customer(	8	,	"8	"	,	             31        ,    -46    ,        179			);
Customer	s9	=	new	Customer(	9	,	"9	"	,	             44       ,     -58      ,      216			);
Customer	s10	=	new	Customer(	10	,	"10	"	,	             45        ,    -67      ,        4			);
Customer	s11	=	new	Customer(	11	,	"11	"	,	            69        ,    -46     ,         9			);
Customer	s12	=	new	Customer(	12	,	"12	"	,	            24         ,     0      ,      154			);
Customer	s13	=	new	Customer(	13	,	"13	"	,	            12        ,     -4      ,      117			);
Customer	s14	=	new	Customer(	14	,	"14	"	,	             1       ,     -21       ,      63			);
Customer	s15	=	new	Customer(	15	,	"15	"	,	             3        ,     29       ,     436			);
Customer	s16	=	new	Customer(	16	,	"16	"	,	            19          ,  -13         ,   905			);
Customer	s17	=	new	Customer(	17	,	"17	"	,	            13          ,  -14         ,    14			);
Customer	s18	=	new	Customer(	18	,	"18	"	,	            25         ,    11         ,     3			);
Customer	s19	=	new	Customer(	19	,	"19	"	,	            24          ,   23       ,      10			);
Customer	s20	=	new	Customer(	20	,	"20	"	,	             3           ,   7      ,      166			);
Customer	s21	=	new	Customer(	21	,	"21	"	,	            23           ,  19        ,    211			);
Customer	s22	=	new	Customer(	22	,	"22	"	,	             2           ,  -7        ,      8			);
Customer	s23	=	new	Customer(	23	,	"23	"	,	             5          ,   23        ,     25			);
Customer	s24	=	new	Customer(	24	,	"24	"	,	            32         ,     5        ,    139			);
Customer	s25	=	new	Customer(	25	,	"25	"	,	            14        ,     25        ,    213			);
Customer	s26	=	new	Customer(	26	,	"26	"	,	           -16        ,     -4       ,     758			);
Customer	s27	=	new	Customer(	27	,	"27	"	,	            24         ,    17         ,   429			);
Customer	s28	=	new	Customer(	28	,	"28	"	,	              0         ,    -7        ,      5			);
Customer	s29	=	new	Customer(	29	,	"29	"	,	           -74         ,   -22       ,     136			);
Customer	s30	=	new	Customer(	30	,	"30	"	,	           -64      ,      -24      ,      501			);
Customer	s31	=	new	Customer(	31	,	"31	"	,	           -71      ,      -19        ,     93			);
Customer	s32	=	new	Customer(	32	,	"32	"	,	           -91      ,      -15        ,     21			);
Customer	s33	=	new	Customer(	33	,	"33	"	,	           -65      ,      -14         ,   169			);
Customer	s34	=	new	Customer(	34	,	"34	"	,	           -91      ,      -26        ,     22			);
Customer	s35	=	new	Customer(	35	,	"35	"	,	           -76      ,       -7        ,      3			);
Customer	s36	=	new	Customer(	36	,	"36	"	,	           -66      ,       -4        ,    271			);
Customer	s37	=	new	Customer(	37	,	"37	"	,	           -87      ,      -10        ,    433			);
Customer	s38	=	new	Customer(	38	,	"38	"	,	            -73     ,        -8       ,       3			);
Customer	s39	=	new	Customer(	39	,	"39	"	,	           -81      ,       -1        ,   1079			);
Customer	s40	=	new	Customer(	40	,	"40	"	,	           -82      ,      -24        ,    233			);
Customer	s41	=	new	Customer(	41	,	"41	"	,	           -87      ,      -25        ,     11			);
Customer	s42	=	new	Customer(	42	,	"42	"	,	            -76     ,       -25       ,      10			);
Customer	s43	=	new	Customer(	43	,	"43	"	,	            -75     ,        -6       ,      78			);
Customer	s44	=	new	Customer(	44	,	"44	"	,	            -70     ,        -3       ,      63			);
Customer	s45	=	new	Customer(	45	,	"45	"	,	         -64        ,    -22          ,    4			);
Customer	s46	=	new	Customer(	46	,	"46	"	,	           -66      ,       -5        ,     59			);
Customer	s47	=	new	Customer(	47	,	"47	"	,	           -72      ,      -10        ,      8			);
Customer	s48	=	new	Customer(	48	,	"48	"	,	           -89      ,       -3        ,     34			);
Customer	s49	=	new	Customer(	49	,	"49	"	,	            -86     ,        -3       ,     234			);
Customer	s50	=	new	Customer(	50	,	"50	"	,	            -57     ,        -9       ,      30			);
Customer	s51	=	new	Customer(	51	,	"51	"	,	            -22     ,       -36       ,      40			);
Customer	s52	=	new	Customer(	52	,	"52	"	,	            -44     ,        19       ,     123			);
Customer	s53	=	new	Customer(	53	,	"53	"	,	            -21     ,         6       ,       7			);
Customer	s54	=	new	Customer(	54	,	"54	"	,	            -49     ,        -4       ,      33			);
Customer	s55	=	new	Customer(	55	,	"55	"	,	            -68     ,        -7       ,     369			);
Customer	s56	=	new	Customer(	56	,	"56	"	,	            -42    ,         11       ,      11			);
Customer	s57	=	new	Customer(	57	,	"57	"	,	            -69    ,          3       ,      23			);
Customer	s58	=	new	Customer(	58	,	"58	"	,	            -49    ,          9       ,     208			);
Customer	s59	=	new	Customer(	59	,	"59	"	,	            -68    ,        -19       ,       4			);
Customer	s60	=	new	Customer(	60	,	"60	"	,	            -57    ,         -7       ,       8			);
Customer	s61	=	new	Customer(	61	,	"61	"	,	            -61    ,        -34       ,      36			);
Customer	s62	=	new	Customer(	62	,	"62	"	,	           -36     ,        16        ,    504			);
Customer	s63	=	new	Customer(	63	,	"63	"	,	            -56    ,          2       ,      16			);
Customer	s64	=	new	Customer(	64	,	"64	"	,	           -67     ,         0        ,    574			);
Customer	s65	=	new	Customer(	65	,	"65	"	,	            -17    ,        -14       ,      19			);
Customer	s66	=	new	Customer(	66	,	"66	"	,	            -17    ,        -20       ,     235			);
Customer	s67	=	new	Customer(	67	,	"67	"	,	            -28    ,        -26       ,     445			);
Customer	s68	=	new	Customer(	68	,	"68	"	,	            -70    ,        -21       ,       6			);
Customer	s69	=	new	Customer(	69	,	"69	"	,	           -46     ,       -14        ,     43			);
Customer	s70	=	new	Customer(	70	,	"70	"	,	            -52    ,         36       ,     210			);
Customer	s71	=	new	Customer(	71	,	"71	"	,	            -33    ,         62       ,     268			);
Customer	s72	=	new	Customer(	72	,	"72	"	,	           -53     ,        49        ,    410			);
Customer	s73	=	new	Customer(	73	,	"73	"	,	           -39     ,        59        ,    124			);
Customer	s74	=	new	Customer(	74	,	"74	"	,	             33    ,         73        ,     11			);
Customer	s75	=	new	Customer(	75	,	"75	"	,	            38     ,        88        ,   1085			);
Customer	s76	=	new	Customer(	76	,	"76	"	,	             43    ,         77       ,       5			);
Customer	s77	=	new	Customer(	77	,	"77	"	,	            -60    ,         19       ,     529			);
Customer	s78	=	new	Customer(	78	,	"78	"	,	            -61    ,         27       ,     107			);
Customer	s79	=	new	Customer(	79	,	"79	"	,	           -66     ,        23        ,    274			);
Customer	s80	=	new	Customer(	80	,	"80	"	,	           -61     ,        20        ,     23			);
Customer	s81	=	new	Customer(	81	,	"81	"	,	          -55      ,       19         ,   156			);
Customer	s82	=	new	Customer(	82	,	"82	"	,	             -5    ,        -38       ,      32			);
Customer	s83	=	new	Customer(	83	,	"83	"	,	            -9     ,       -41        ,    177			);
Customer	s84	=	new	Customer(	84	,	"84	"	,	           -12     ,       -31        ,     16			);
Customer	s85	=	new	Customer(	85	,	"85	"	,	             -9    ,        -45       ,       8			);
Customer	s86	=	new	Customer(	86	,	"86	"	,	             -6    ,        -33       ,      19			);
Customer	s87	=	new	Customer(	87	,	"87	"	,	             -1    ,        -49       ,      52			);
Customer	s88	=	new	Customer(	88	,	"88	"	,	           -14     ,       -44        ,     47			);
Customer	s89	=	new	Customer(	89	,	"89	"	,	            85     ,        -1        ,      4			);
Customer	s90	=	new	Customer(	90	,	"90	"	,	            67     ,        -1        ,    372			);
Customer	s91	=	new	Customer(	91	,	"91	"	,	             60    ,         30       ,     525			);
Customer	s92	=	new	Customer(	92	,	"92	"	,	             78    ,         12       ,     101			);
Customer	s93	=	new	Customer(	93	,	"93	"	,	             57    ,         31       ,     898			);
Customer	s94	=	new	Customer(	94	,	"94	"	,	            63     ,        -1        ,     40			);
Customer	s95	=	new	Customer(	95	,	"95	"	,	             88    ,         -3       ,      32			);
Customer	s96	=	new	Customer(	96	,	"96	"	,	             85    ,        -13       ,    1017			);
Customer	s97	=	new	Customer(	97	,	"97	"	,	            78     ,        17        ,    103			);
Customer	s98	=	new	Customer(	98	,	"98	"	,	            56     ,         4        ,    109			);
Customer	s99	=	new	Customer(	99	,	"99	"	,	            99     ,       -14        ,     76			);
Customer	s100	=	new	Customer(	100	,	"100	"	,	            53     ,        16        ,   1025			);


Depotcentral depot= new Depotcentral(0,"Depot",0,0);



Camion c1= new Camion (1);Camion c2= new Camion (2);Camion c3= new Camion (3);Camion c4= new Camion (4);Camion c5= new Camion (5);
Camion c6= new Camion (6);Camion c7= new Camion (7);Camion c8= new Camion (8);Camion c9= new Camion (9);Camion c10= new Camion (10);
Camion c11= new Camion (11);Camion c12= new Camion (12);Camion c13= new Camion (13);Camion c14= new Camion (14);Camion c15= new Camion (15);
Camion c16= new Camion (16);Camion c17= new Camion (17);Camion c18= new Camion (18);Camion c19= new Camion (19);Camion c20= new Camion (20);
Camion c21= new Camion (21);Camion c22= new Camion (22);Camion c23= new Camion (23);Camion c24= new Camion (24);Camion c25= new Camion (25);
Camion c26= new Camion (26);Camion c27= new Camion (27);Camion c28= new Camion (28);Camion c29= new Camion (29);Camion c30= new Camion (30);
Camion c31= new Camion (31);Camion c32= new Camion (32);Camion c33= new Camion (33);Camion c34= new Camion (34);Camion c35= new Camion (35);
Camion c36= new Camion (36);Camion c37= new Camion (37);Camion c38= new Camion (38);Camion c39= new Camion (39);Camion c40= new Camion (40);
Camion c41= new Camion (41);Camion c42= new Camion (42);Camion c43= new Camion (43);Camion c44= new Camion (44);Camion c45= new Camion (45);
Camion c46= new Camion (46);Camion c47= new Camion (47);Camion c48= new Camion (48);Camion c49= new Camion (49);Camion c50= new Camion (50);

   
   
      
 // Depotcentral depot= new Depotcentral(0,"Depot",40,40);

    ArrayList<Camion> camions = new ArrayList<Camion> () ;




camions.clear();
camions.add(c1);camions.add(c2);camions.add(c3);camions.add(c4);camions.add(c5);camions.add(c6);camions.add(c7);camions.add(c8);camions.add(c9);camions.add(c10);
camions.add(c11);camions.add(c12);camions.add(c13);camions.add(c14);camions.add(c15);camions.add(c16);camions.add(c17);camions.add(c18);camions.add(c19);camions.add(c20);
camions.add(c21);camions.add(c22);camions.add(c23);camions.add(c24);camions.add(c25);camions.add(c26);camions.add(c27);camions.add(c28);camions.add(c29);camions.add(c30);
camions.add(c31);camions.add(c32);camions.add(c33);camions.add(c34);camions.add(c35);camions.add(c36);camions.add(c37);camions.add(c38);camions.add(c39);camions.add(c40);
camions.add(c41);camions.add(c42);camions.add(c43);camions.add(c44);camions.add(c45);camions.add(c46);camions.add(c47);camions.add(c48);camions.add(c49);camions.add(c50);

for (Camion c: camions)
{
    c.setCapacity(capacity_camion);
}


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



Tout.add(s1);Tout.add(s2);Tout.add(s3);Tout.add(s4);Tout.add(s5);Tout.add(s6);Tout.add(s7);Tout.add(s8);Tout.add(s9);Tout.add(s10);
Tout.add(s11);Tout.add(s12);Tout.add(s13);Tout.add(s14);Tout.add(s15);Tout.add(s16);Tout.add(s17);Tout.add(s18);Tout.add(s19);Tout.add(s20);
Tout.add(s21);Tout.add(s22);Tout.add(s23);Tout.add(s24);Tout.add(s25);Tout.add(s26);Tout.add(s27);Tout.add(s28);Tout.add(s29);Tout.add(s30);
Tout.add(s31);Tout.add(s32);Tout.add(s33);Tout.add(s34);Tout.add(s35);Tout.add(s36);Tout.add(s37);Tout.add(s38);Tout.add(s39);Tout.add(s40);
Tout.add(s41);Tout.add(s42);Tout.add(s43);Tout.add(s44);Tout.add(s45);Tout.add(s46);Tout.add(s47);Tout.add(s48);Tout.add(s49);Tout.add(s50);
Tout.add(s51);Tout.add(s52);Tout.add(s53);Tout.add(s54);Tout.add(s55);Tout.add(s56);Tout.add(s57);Tout.add(s58);Tout.add(s59);Tout.add(s60);
Tout.add(s61);Tout.add(s62);Tout.add(s63);Tout.add(s64);Tout.add(s65);Tout.add(s66);Tout.add(s67);Tout.add(s68);Tout.add(s69);Tout.add(s70);
Tout.add(s71);Tout.add(s72);Tout.add(s73);Tout.add(s74);Tout.add(s75);Tout.add(s76);Tout.add(s77);Tout.add(s78);Tout.add(s79);Tout.add(s80);
Tout.add(s81);Tout.add(s82);Tout.add(s83);Tout.add(s84);Tout.add(s85);Tout.add(s86);Tout.add(s87);Tout.add(s88);Tout.add(s89);Tout.add(s90);
Tout.add(s91);Tout.add(s92);Tout.add(s93);Tout.add(s94);Tout.add(s95);Tout.add(s96);Tout.add(s97);Tout.add(s98);Tout.add(s99);Tout.add(s100);

statiq.add(s57);statiq.add(s58);statiq.add(s59);statiq.add(s60);
statiq.add(s61);statiq.add(s62);statiq.add(s63);statiq.add(s64);statiq.add(s65);statiq.add(s66);statiq.add(s67);statiq.add(s68);statiq.add(s69);statiq.add(s70);
statiq.add(s71);statiq.add(s72);statiq.add(s73);statiq.add(s74);statiq.add(s75);statiq.add(s76);statiq.add(s77);statiq.add(s78);statiq.add(s79);statiq.add(s80);
statiq.add(s81);statiq.add(s82);statiq.add(s83);statiq.add(s84);statiq.add(s85);statiq.add(s86);statiq.add(s87);statiq.add(s88);statiq.add(s89);statiq.add(s90);
statiq.add(s91);statiq.add(s92);statiq.add(s93);statiq.add(s94);statiq.add(s95);statiq.add(s96);statiq.add(s97);statiq.add(s98);statiq.add(s99);statiq.add(s100);

dynamic1.add(s1);dynamic1.add(s2);dynamic1.add(s3);

dynamic2.add(s4);dynamic2.add(s5);

dynamic3.add(s6);dynamic3.add(s7);dynamic3.add(s8);dynamic3.add(s9);dynamic3.add(s10);dynamic3.add(s11);

dynamic4.add(s12);dynamic4.add(s13);dynamic4.add(s14);dynamic4.add(s15);dynamic4.add(s16);dynamic4.add(s17);dynamic4.add(s18);

dynamic5.add(s19);dynamic5.add(s20);dynamic5.add(s21);dynamic5.add(s22);

dynamic6.add(s23);dynamic6.add(s24);dynamic6.add(s25);

dynamic7.add(s26);dynamic7.add(s27);dynamic7.add(s28);dynamic7.add(s29);dynamic7.add(s30);dynamic7.add(s31);

dynamic8.add(s32);dynamic8.add(s33);dynamic8.add(s34);

dynamic9.add(s35);dynamic9.add(s36);dynamic9.add(s37);dynamic9.add(s38);dynamic9.add(s39);dynamic9.add(s40);dynamic9.add(s41);

dynamic10.add(s42);dynamic10.add(s43);dynamic10.add(s44);dynamic10.add(s45);dynamic10.add(s46);dynamic10.add(s47);dynamic10.add(s48);

dynamic11.add(s49);dynamic11.add(s50);dynamic11.add(s51);dynamic11.add(s52);

dynamic12.add(s53);dynamic12.add(s54);

dynamic13.add(s55);dynamic13.add(s56);

    
VRPT VRPT= new VRPT ( Tout, depot, 1, 0, 1, 1, 50000 );
VRPT.setTemps_service(temp_service);
 //public VRPS( VRPT VRPtotal, ArrayList<Customer> customers, double maxTemps, Depotcentral depot,  int nombrecamions, int capacitycamion, ArrayList<Camion> camions) {
    
VRPS p = new VRPS (VRPT, statiq,Yarabbiyassergenetic.max_temps, depot,1, 1409, camions);

p.setNbTripsByVehicle(nbtripsbyVehicle);
p.setMaxTemps(max_temps);
p.setNbImprove(nbtImprove);
DecimalFormat df = new DecimalFormat(); 
df.setMaximumFractionDigits(2) ;
 //TourGroup statique= new TourGroup(p, camions);
  VRP_solver g0 = new VRP_solver(p);
                g0.maxGenerations=maxGenerations;//10000
                g0.maxGenwithoutAmelioration=maxGenwithoutAmelioration;
                g0.populationSize=populationSize;
                g0.croisementProbability=croisementProbability;
                g0.mutationProbability=mutationProbability;
                g0.time_slice=time_slice;
                g0.temps_service=temp_service;
                
          
     TourGroup bests = g0.solve(null);
     
       //g0.afficher_solution();
                  

populationSize=20;
VRPD problem_dynamic1= new VRPD(VRPT,dynamic1,g0.getDEPO_FIC(),depot,Yarabbiyassergenetic.max_temps-Yarabbiyassergenetic.time_slice,Yarabbiyassergenetic.nbr_camions,Yarabbiyassergenetic.capacity_camion,Yarabbiyassergenetic.ovetime,Yarabbiyassergenetic.temp_service,g0.getClients_old(),g0.getCamions());
problem_dynamic1.setNbImprove(nbtImprove);
g0.getSol_preced().setProblemD(problem_dynamic1);
VRP_solver g1 = new VRP_solver(problem_dynamic1);
                g1.maxGenerations=maxGenerations;//10000
                g1.maxGenwithoutAmelioration=maxGenwithoutAmelioration;
                g1.populationSize=populationSize;
                g1.croisementProbability=croisementProbability;
                g1.mutationProbability=mutationProbability;
                g1.time_slice=time_slice;
                g1.temps_service=temp_service;
                g1.dynamic=dynamic1;
                
                /* for (int i=0; i<g0.getCamions().size();i++)
                {
                    System.out.println("id camion:"+g0.getCamions().get(i).getId_fictif_final());
                }
                for (int i=0; i<g0.DEPO_FIC.size();i++)
                {
                    System.out.println("id depot fic:"+g0.DEPO_FIC.get(i).getId_client_fictif());
                }*/
                System.out.println("taille depot fictif men classe main :"+g0.DEPO_FIC.size());
                System.out.println("taille client old men classe main :"+g0.clients_old.size());
                System.out.println("taille dynamic mten classe main :"+g1.dynamic.size());
                 System.out.println("taille clien kolchi men classe main :"+problem_dynamic1.customers_dynamic.size());
                
                
g1.solve_dynamic(g0.getSol_preced());
      
VRPD problem_dynamic2= new VRPD(VRPT,dynamic2,g1.getDEPO_FIC_dynamic(),depot,Yarabbiyassergenetic.max_temps-2*Yarabbiyassergenetic.time_slice,Yarabbiyassergenetic.nbr_camions,Yarabbiyassergenetic.capacity_camion,Yarabbiyassergenetic.ovetime,Yarabbiyassergenetic.temp_service,g1.getClients_old(),g1.getCamions());
problem_dynamic2.setNbImprove(nbtImprove);
g1.getSol_preced().setProblemD(problem_dynamic2);
VRP_solver g2 = new VRP_solver(problem_dynamic2);
                g2.maxGenerations=maxGenerations;//10000
                g2.maxGenwithoutAmelioration=maxGenwithoutAmelioration;
                g2.populationSize=populationSize;
                g2.croisementProbability=croisementProbability;
                g2.mutationProbability=mutationProbability;
                g2.time_slice=time_slice;
                g2.temps_service=temp_service;
                g2.dynamic=dynamic2;
                /* System.out.println("tailllllllllllllllllllllllllllllllllllllllllllllllllle camions:"+g1.getCamions().size());
                 System.out.println("taillllllllllllllllllllllllllllllllllllllllllllllllllle dépot fictif:"+problem_dynamic2.getDepots_fictif().size());
                for (int i=0; i<g1.getCamions().size();i++)
                {
                    System.out.println("id camion:"+g1.getCamions().get(i).getId_fictif_final());
                }
                for (int i=0; i<g1.DEPO_FIC.size();i++)
                {
                    System.out.println("id depot fic:"+problem_dynamic2.getDepots_fictif().get(i).getId_client_fictif());
                }*/
                System.out.println("taille depot fictif men classe main :"+g1.DEPO_FIC.size());
                System.out.println("taille client old men classe main :"+g1.clients_old.size());
                System.out.println("taille dynamic mten classe main :"+g2.dynamic.size());
                 System.out.println("taille clien kolchi men classe main :"+problem_dynamic2.customers_dynamic.size());
                
                
g2.solve_dynamic(g1.getSol_preced());
             

VRPD problem_dynamic3= new VRPD(VRPT,dynamic3,g2.getDEPO_FIC_dynamic(),depot,Yarabbiyassergenetic.max_temps-3*Yarabbiyassergenetic.time_slice,Yarabbiyassergenetic.nbr_camions,Yarabbiyassergenetic.capacity_camion,Yarabbiyassergenetic.ovetime,Yarabbiyassergenetic.temp_service,g2.getClients_old(),g2.getCamions());
problem_dynamic3.setNbImprove(nbtImprove);
g2.getSol_preced().setProblemD(problem_dynamic3);
VRP_solver g3 = new VRP_solver(problem_dynamic3);
                g3.maxGenerations=maxGenerations;//10000
                g3.maxGenwithoutAmelioration=maxGenwithoutAmelioration;
                g3.populationSize=populationSize;
                g3.croisementProbability=croisementProbability;
                g3.mutationProbability=mutationProbability;  
                g3.time_slice=time_slice;
                g3.temps_service=temp_service;
                g3.dynamic=dynamic3;
               /* for (int i=0; i<g2.getCamions().size();i++)
                {
                    System.out.println("id camion:"+g2.getCamions().get(i).getId_fictif_final());
                }
                for (int i=0; i<g2.DEPO_FIC.size();i++)
                {
                    System.out.println("id depot fic:"+problem_dynamic3.getDepots_fictif().get(i).getId_client_fictif());
                }
                */
               System.out.println("taille depot fictif men classe main :"+g2.DEPO_FIC.size());
                System.out.println("taille client old men classe main :"+g2.clients_old.size());
                System.out.println("taille dynamic mten classe main :"+g3.dynamic.size());
                 System.out.println("taille clien kolchi men classe main :"+problem_dynamic3.customers_dynamic.size());
                
                
g3.solve_dynamic(g2.getSol_preced());

VRPD problem_dynamic4= new VRPD(VRPT,dynamic4,g3.getDEPO_FIC_dynamic(),depot,Yarabbiyassergenetic.max_temps-4*Yarabbiyassergenetic.time_slice,Yarabbiyassergenetic.nbr_camions,Yarabbiyassergenetic.capacity_camion,Yarabbiyassergenetic.ovetime,Yarabbiyassergenetic.temp_service,g3.getClients_old(),g3.getCamions());
problem_dynamic4.setNbImprove(nbtImprove);
g3.getSol_preced().setProblemD(problem_dynamic4);
VRP_solver g4 = new VRP_solver(problem_dynamic4);
                g4.maxGenerations=maxGenerations;//10000
                g4.maxGenwithoutAmelioration=maxGenwithoutAmelioration;
                g4.populationSize=populationSize;
                g4.croisementProbability=croisementProbability;
                g4.mutationProbability=mutationProbability;
                g4.time_slice=time_slice;
                g4.temps_service=temp_service;
                g4.dynamic=dynamic4;
             /*   for (int i=0; i<g3.getCamions().size();i++)
                {
                    System.out.println("id camion:"+g3.getCamions().get(i).getId_fictif_final());
                }
                for (int i=0; i<g3.DEPO_FIC.size();i++)
                {
                    System.out.println("id depot fic:"+g3.DEPO_FIC.get(i).getId_client_fictif());
                }
                */
             System.out.println("taille depot fictif men classe main :"+g3.DEPO_FIC.size());
                System.out.println("taille client old men classe main :"+g3.clients_old.size());
                System.out.println("taille dynamic mten classe main :"+g4.dynamic.size());
                 System.out.println("taille clien kolchi men classe main :"+problem_dynamic4.customers_dynamic.size());
                
                  
g4.solve_dynamic(g3.getSol_preced());

VRPD problem_dynamic5= new VRPD(VRPT,dynamic5,g4.getDEPO_FIC_dynamic(),depot,Yarabbiyassergenetic.max_temps-5*Yarabbiyassergenetic.time_slice,Yarabbiyassergenetic.nbr_camions,Yarabbiyassergenetic.capacity_camion,Yarabbiyassergenetic.ovetime,Yarabbiyassergenetic.temp_service,g4.getClients_old(),g4.getCamions());
problem_dynamic5.setNbImprove(nbtImprove);
g4.getSol_preced().setProblemD(problem_dynamic5);
VRP_solver g5 = new VRP_solver(problem_dynamic5);
                g5.maxGenerations=maxGenerations;//10000
                g5.maxGenwithoutAmelioration=maxGenwithoutAmelioration;
                g5.populationSize=populationSize;
                g5.croisementProbability=croisementProbability;
                g5.mutationProbability=mutationProbability;
                g5.time_slice=time_slice;
                g5.temps_service=temp_service;
                g5.dynamic=dynamic5;
               /* for (int i=0; i<g4.getCamions().size();i++)
                {
                    System.out.println("id camion:"+g4.getCamions().get(i).getId_fictif_final());
                }
                for (int i=0; i<g4.DEPO_FIC.size();i++)
                {
                    System.out.println("id depot fic:"+g4.DEPO_FIC.get(i).getId_client_fictif());
                }*/
                System.out.println("taille depot fictif men classe main :"+g4.DEPO_FIC.size());
                System.out.println("taille client old men classe main :"+g4.clients_old.size());
                System.out.println("taille dynamic mten classe main :"+g5.dynamic.size());
                 System.out.println("taille clien kolchi men classe main :"+problem_dynamic5.customers_dynamic.size());
                
                  
g5.solve_dynamic(g4.getSol_preced());

VRPD problem_dynamic6= new VRPD(VRPT,dynamic6,g5.getDEPO_FIC_dynamic(),depot,Yarabbiyassergenetic.max_temps-6*Yarabbiyassergenetic.time_slice,Yarabbiyassergenetic.nbr_camions,Yarabbiyassergenetic.capacity_camion,Yarabbiyassergenetic.ovetime,Yarabbiyassergenetic.temp_service,g5.getClients_old(),g5.getCamions());
problem_dynamic6.setNbImprove(nbtImprove);
g5.getSol_preced().setProblemD(problem_dynamic6);
VRP_solver g6 = new VRP_solver(problem_dynamic6);
                g6.maxGenerations=maxGenerations;//10000
                g6.maxGenwithoutAmelioration=maxGenwithoutAmelioration;
                g6.populationSize=populationSize;
                g6.croisementProbability=croisementProbability;
                g6.mutationProbability=mutationProbability;
                g6.time_slice=time_slice;
                g6.temps_service=temp_service;
                g6.dynamic=dynamic6;
g6.solve_dynamic(g5.getSol_preced());

VRPD problem_dynamic7= new VRPD(VRPT,dynamic7,g6.getDEPO_FIC_dynamic(),depot,Yarabbiyassergenetic.max_temps-7*Yarabbiyassergenetic.time_slice,Yarabbiyassergenetic.nbr_camions,Yarabbiyassergenetic.capacity_camion,Yarabbiyassergenetic.ovetime,Yarabbiyassergenetic.temp_service,g6.getClients_old(),g6.getCamions());
problem_dynamic7.setNbImprove(nbtImprove);
g6.getSol_preced().setProblemD(problem_dynamic7);
VRP_solver g7 = new VRP_solver(problem_dynamic7);
                g7.maxGenerations=maxGenerations;//10000
                g7.maxGenwithoutAmelioration=maxGenwithoutAmelioration;
                g7.populationSize=populationSize;
                g7.croisementProbability=croisementProbability;
                g7.mutationProbability=mutationProbability;
                g7.time_slice=time_slice;
                g7.temps_service=temp_service;
                g7.dynamic=dynamic7;
g7.solve_dynamic(g6.getSol_preced());

VRPD problem_dynamic8= new VRPD(VRPT,dynamic8,g7.getDEPO_FIC_dynamic(),depot,Yarabbiyassergenetic.max_temps-8*Yarabbiyassergenetic.time_slice,Yarabbiyassergenetic.nbr_camions,Yarabbiyassergenetic.capacity_camion,Yarabbiyassergenetic.ovetime,Yarabbiyassergenetic.temp_service,g7.getClients_old(),g7.getCamions());
problem_dynamic8.setNbImprove(nbtImprove);
g7.getSol_preced().setProblemD(problem_dynamic8);
VRP_solver g8 = new VRP_solver(problem_dynamic8);
                g8.maxGenerations=maxGenerations;//10000
                g8.maxGenwithoutAmelioration=maxGenwithoutAmelioration;
                g8.populationSize=populationSize;
                g8.croisementProbability=croisementProbability;
                g8.mutationProbability=mutationProbability;
                g8.time_slice=time_slice;
                g8.temps_service=temp_service;
                g8.dynamic=dynamic8;
g8.solve_dynamic(g7.getSol_preced());

VRPD problem_dynamic9= new VRPD(VRPT,dynamic9,g8.getDEPO_FIC_dynamic(),depot,Yarabbiyassergenetic.max_temps-9*Yarabbiyassergenetic.time_slice,Yarabbiyassergenetic.nbr_camions,Yarabbiyassergenetic.capacity_camion,Yarabbiyassergenetic.ovetime,Yarabbiyassergenetic.temp_service,g8.getClients_old(),g8.getCamions());
problem_dynamic9.setNbImprove(nbtImprove);
g8.getSol_preced().setProblemD(problem_dynamic9);
VRP_solver g9 = new VRP_solver(problem_dynamic9);
                g9.maxGenerations=maxGenerations;//10000
                g9.maxGenwithoutAmelioration=maxGenwithoutAmelioration;
                g9.populationSize=populationSize;
                g9.croisementProbability=croisementProbability;
                g9.mutationProbability=mutationProbability;
                g9.time_slice=time_slice;
                g9.temps_service=temp_service;
                g9.dynamic=dynamic9;
g9.solve_dynamic(g8.getSol_preced());

VRPD problem_dynamic10= new VRPD(VRPT,dynamic10,g9.getDEPO_FIC_dynamic(),depot,Yarabbiyassergenetic.max_temps-10*Yarabbiyassergenetic.time_slice,Yarabbiyassergenetic.nbr_camions,Yarabbiyassergenetic.capacity_camion,Yarabbiyassergenetic.ovetime,Yarabbiyassergenetic.temp_service,g9.getClients_old(),g9.getCamions());
problem_dynamic10.setNbImprove(nbtImprove);
g9.getSol_preced().setProblemD(problem_dynamic10);
VRP_solver g10 = new VRP_solver(problem_dynamic10);
                g10.maxGenerations=maxGenerations;//10000
                g10.maxGenwithoutAmelioration=maxGenwithoutAmelioration;
                g10.populationSize=populationSize;
                g10.croisementProbability=croisementProbability;
                g10.mutationProbability=mutationProbability;
                g10.time_slice=time_slice;
                g10.temps_service=temp_service;
                g10.dynamic=dynamic10;
g10.solve_dynamic(g9.getSol_preced());

VRPD problem_dynamic11= new VRPD(VRPT,dynamic11,g10.getDEPO_FIC_dynamic(),depot,Yarabbiyassergenetic.max_temps-11*Yarabbiyassergenetic.time_slice,Yarabbiyassergenetic.nbr_camions,Yarabbiyassergenetic.capacity_camion,Yarabbiyassergenetic.ovetime,Yarabbiyassergenetic.temp_service,g10.getClients_old(),g10.getCamions());
problem_dynamic11.setNbImprove(nbtImprove);
g10.getSol_preced().setProblemD(problem_dynamic11);
VRP_solver g11 = new VRP_solver(problem_dynamic11);
                g11.maxGenerations=maxGenerations;//10000
                g11.maxGenwithoutAmelioration=maxGenwithoutAmelioration;
                g11.populationSize=populationSize;
                g11.croisementProbability=croisementProbability;
                g11.mutationProbability=mutationProbability;
                g11.time_slice=time_slice;
                g11.temps_service=temp_service;
                g11.dynamic=dynamic11;
g11.solve_dynamic(g10.getSol_preced());

VRPD problem_dynamic12= new VRPD(VRPT,dynamic12,g11.getDEPO_FIC_dynamic(),depot,Yarabbiyassergenetic.max_temps-12*Yarabbiyassergenetic.time_slice,Yarabbiyassergenetic.nbr_camions,Yarabbiyassergenetic.capacity_camion,Yarabbiyassergenetic.ovetime,Yarabbiyassergenetic.temp_service,g11.getClients_old(),g11.getCamions());
problem_dynamic12.setNbImprove(nbtImprove);
g11.getSol_preced().setProblemD(problem_dynamic12);
VRP_solver g12 = new VRP_solver(problem_dynamic12);
                g12.maxGenerations=maxGenerations;//10000
                g12.maxGenwithoutAmelioration=maxGenwithoutAmelioration;
                g12.populationSize=populationSize;
                g12.croisementProbability=croisementProbability;
                g12.mutationProbability=mutationProbability;
                g12.time_slice=time_slice;
                g12.temps_service=temp_service;
                g12.dynamic=dynamic12;
g12.solve_dynamic(g11.getSol_preced());

VRPD problem_dynamic13= new VRPD(VRPT,dynamic13,g12.getDEPO_FIC_dynamic(),depot,Yarabbiyassergenetic.max_temps-13*Yarabbiyassergenetic.time_slice,Yarabbiyassergenetic.nbr_camions,Yarabbiyassergenetic.capacity_camion,Yarabbiyassergenetic.ovetime,Yarabbiyassergenetic.temp_service,g12.getClients_old(),g12.getCamions());
problem_dynamic13.setNbImprove(nbtImprove);
g12.getSol_preced().setProblemD(problem_dynamic13);
VRP_solver g13 = new VRP_solver(problem_dynamic13);
                g13.maxGenerations=maxGenerations;//10000
                g13.maxGenwithoutAmelioration=maxGenwithoutAmelioration;
                g13.populationSize=populationSize;
                g13.croisementProbability=croisementProbability;
                g13.mutationProbability=mutationProbability;
                g13.time_slice=time_slice;
                g13.temps_service=temp_service;
                g13.dynamic=dynamic13;
g13.solve_dynamic_definitif(g12.getSol_preced());


                                          
     System.out.println("temps totaaaaaaaaaaal  :"+ ((g13.getTemps_total_dernier_voyage()+
                                                      g12.getTemps_total_voyage()+
                                                       g11.getTemps_total_voyage()+
                                                          g10.getTemps_total_voyage()+
                                                         g9.getTemps_total_voyage()+g8.getTemps_total_voyage()+
                                                         g7.getTemps_total_voyage()+g6.getTemps_total_voyage()+g5.getTemps_total_voyage()+
                                                          g4.getTemps_total_voyage()+g3.getTemps_total_voyage()+g2.getTemps_total_voyage()+
                                                          g1.getTemps_total_voyage()+g0.getTemps_total_voyage())-(Yarabbiyassergenetic.temp_service* Tout.size())));
      
}





//CW);
                //TourGroup ind0 = g0.solve(null);
               // for(VRP_solver.Solution tg:bests)
             /*       System.out.println("generation:"+tg.getGeneration()+", obj1:"+tg.getTourGroup().getObj1()+", obj2:"+tg.getTourGroup().getObj());
                    
                Collections.sort(bests);
                for(VRP_solver.Solution sol:bests){
                    TourGroup trg = sol.getTourGroup();
                    long duree0 = sol.getCPU();                
                  

                    System.out.println("*************");
                    System.out.println("Param:\t MaxGeneration:"+g0.maxGenerations+" cost:"+p.getObjective1()+" popSize:"+g0.populationSize+" ,mutation:"
                            +" pC:"+g0.croisementProbability+" pM:"+g0.mutationProbability);
                    System.out.println("NV="+p.getCamions().size()+" T="+p.getMaxTemps());
                    System.out.println("solution: "+trg);
                    
                    System.out.println("finalBest "+df.format(trg.getdistance())+ " "+df.format(trg.getLTR()/trg.getProblem().maxTemps)+" "+df.format(sol.getCPU())
                            +" "+sol.getGeneration()+"");
                          
                     System.out.println("Fin");*/
                
    
    public static void main(String[] args) {
        
         get_data();
        // TODO code application logic here
    }
   
    
}
