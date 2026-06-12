/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mtdvrpot_cplex;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Khaoula
 */
public class TSP {
      public static void solveMe(int n) {
        // TODO code application logic here
        double [] xPos= new double[n];
        double [] yPos= new double[n];
        for (int i=0; i<n;i++)
        {
            xPos[i]=Math.random()*100;
            yPos[i]=Math.random()*100;
        }
        double [][] c= new double [n][n];
        for (int i=0; i<n; i++){
            for (int j=0;j<n;j++)
            {
                c[i][j]=Math.sqrt(Math.pow(xPos[i]-xPos[j], 2)+Math.pow(yPos[i]-yPos[j],2));
            }
        }
        //model
        try {        
            IloCplex cplex= new IloCplex ();
            // Variables
            IloNumVar[][] x = new IloNumVar[n][];
            for (int i=0; i<n; i++)
            {
                x[i]=cplex.boolVarArray(n);
            }
            for (IloNumVar[] xv: x)
            {
               cplex.add(xv) ;
            }
            IloNumVar[] u=cplex.numVarArray(n,1,Double.MAX_VALUE);
            // objectives
            IloLinearNumExpr obj = cplex.linearNumExpr();
            for (int i=0;i<n;i++)
            {
                for (int j=0;j<n;j++){
                    if (j!=i){
                        obj.addTerm(c[i][j],x[i][j]);
                    }
                }
            }
            cplex.addMinimize(obj);
            // contraintes
            for (int j=0;j<n;j++){
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int i=0;i<n;i++)
                {
                    if (i!=j){
                        expr.addTerm(1.0, x[i][j]);
                    }
                }
                cplex.addEq(expr, 1.0);
                
            }
            for (int i=0;i<n;i++){
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int j=0;j<n;j++)
                {
                    if (j!=i){
                        expr.addTerm(1.0, x[i][j]);
                    }
                }
                cplex.addEq(expr, 1.0);
                
            }
            for (int i=1;i<n;i++)
            {
                for(int j=1;j<n;j++)
                {
                    if(i!=j){
                        IloLinearNumExpr expr=cplex.linearNumExpr();
                        expr.addTerm(1.0, u[i]);
                        expr.addTerm(-1.0, u[j]);
                        expr.addTerm(n,x[i][j]);
                        cplex.addLe(expr, n-1);
                    }
                }
            }
            //solve node
            cplex.solve();
      double objval = cplex.getObjValue();
      //double [] uval = cplex.getValues(u);
    for (int i=0;i<n;i++)
    {
     for (int j=0;j<n;j++)
     {
     System.out.println("valeur de x pour i: "+i+" et j : "+j+" est: "+cplex.getValue(x[i][j]));
     
        
     }
    } 
            System.out.println("valeur optimal:" +objval);
            //System.out.println("valeur de u:" +uval);

            //end
            cplex.end();
            
        } catch (IloException e) {
            e.printStackTrace();
        }
       
    }
    
}
