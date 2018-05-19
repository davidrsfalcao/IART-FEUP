import graph.Graph;
import graph.State;

import static java.lang.Thread.sleep;

import java.util.Scanner;


public class Launcher {

    private static String filename = "data/Evacuation_graph.xml";

    public static void main(String[] args) {

        Graph graph = new Graph(filename);

        State state = new State(graph, filename);

        state.displayState();

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("---------- Plano de evacuação ----------");
        System.out.println("Algoritmo:");
        System.out.println("1) Primeiro em profundidade");
        System.out.println("2) Primeiro em largura");
        System.out.println("3) Branch and Bound ");
        System.out.println("4) A star");

        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        switch (num) {
            case 1: {
                Search.algorithm = Search.ALGORITHM.DFS;
                break;
            }
            case 2: {
                Search.algorithm = Search.ALGORITHM.BFS;
                break;
            }
            case 3: {
                Search.algorithm = Search.ALGORITHM.BB;
                break;
            }
            case 4: {
                Search.algorithm = Search.ALGORITHM.ASTAR;
                break;
            }
            default: {
                System.out.println("Invalid option.");
                return;
            }
        }


        System.out.println("1) Primeira solução.");
        System.out.println("2) Todas as soluções.");
        num = in.nextInt();

        if (num == 1) {
            Search.one_solution = true;
        } else if (num == 2) {
            Search.one_solution = false;
        } else {
            System.out.println("Invalid option.");
            return;
        }

        long startTime = System.nanoTime();
        Search.search(state);
        long stopTime = System.nanoTime();
        System.out.println("Time: "+ (stopTime - startTime));


        Search.show_solutions();

    }
}

   