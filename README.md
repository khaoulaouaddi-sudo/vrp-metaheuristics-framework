# vrp-metaheuristics-framework

This repository contains the official Java source code developed during my PhD research. It presents a comprehensive, comparative framework designed to solve complex variants of the **Vehicle Routing Problem (VRP)**—specifically focusing on logistics optimization, fleet efficiency, and operational cost reduction.

The core value of this framework lies in its **hybrid benchmarking approach**, balancing exact mathematical programming with advanced metaheuristics to tackle large-scale combinatorial optimization problems.

---

## 📁 Repository Structure

The framework is organized into three distinct, standalone Java projects:

* **[MTDVRPOT_cplex]**: Exact mathematical model implementation using the **IBM ILOG CPLEX** Java API. It serves as the exact optimization benchmark for verifying optimal solutions on small to medium instances.
* **[ant colony algorithm]**: A metaheuristic approach leveraging **Ant Colony Optimization (ACO)**, designed for fast convergence and high-quality solutions on larger routing instances.
* **[memetic algorithm]**: An advanced hybrid metaheuristic combining a population-based **Genetic Algorithm (GA)** with local search heuristics to prevent premature convergence and optimize solution quality.

---

## 🔬 Academic Publications & References

The algorithms and mathematical models implemented in this repository have been fully peer-reviewed and published in international scientific journals:

1. Khaoula OUADDI., Youssef BENADADA & Fatima-Zahra MHADA (2021). Heuristic based on iterative exact method for multi-tour dynamic vehicle routing problem with overtime. International Journal of Logistics Systems and Management (Q3, SJR 2021 : 0,35).
2. Khaoula OUADDI., Fatima-Zahra MHADA & Youssef BENADADA (2020). Memetic algorithm for multi-tour dynamic vehicle routing problem with overtime (MDVRPOT). International Journal of Industrial Engineering Computations (Q2, SJR 2020: 0.56), March
3. Khaoula OUADDI., Youssef BENADADA & Fatima-Zahra MHADA, (2018). Ant Colony System for Dynamic Vehicle Routing Problem with Overtime. International Journal of Advanced Computer Science and Applications (Q3, SJR
2019: 0.16), 9(6), 306–315.

---

## 🛠️ Technical Stack & Requirements

* **Language:** Java
* **Build System:** Apache Ant (configured via the local `build.xml` file in each project directory)
* **Optimization Solver:** IBM ILOG CPLEX (required to compile and run the exact method project)
* **IDE Compatibility:** Fully compatible with NetBeans or any modern Java IDE supporting Apache Ant.

### How to Run
Each folder contains its own standard `src/` directory and an Apache Ant `build.xml` file. You can compile and execute any of the approaches by opening the respective folder in your preferred Java IDE or running the following command inside a project directory:
```bash
ant run
