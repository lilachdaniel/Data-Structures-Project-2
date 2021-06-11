//import java.util.*;
//
//public class OtherMeasurements {
//    public static void main(String[] args) {
//        for (int i = 6; i < 22; i++) {
//
//            double sum = 0;
//
//            for (int z = 0; z < 10; z++) {
//                double numOfNodes = Math.pow(2, i);
//                Graph.Node[] graph_nodes = new Graph.Node[(int) numOfNodes];
//
//                for (int j = 1; j < (int) numOfNodes + 1; j++) {
//                    Graph.Node node = new Graph.Node(j, 1);
//                    graph_nodes[j - 1] = node;
//                }
//
//                Graph graph = new Graph(graph_nodes);
//                HashSet<Set<Integer>> sets = new HashSet<>();
//                HashMap<Integer, Integer> mapSets = new HashMap<>();
//
//                int k = 0;
//                while (k < (int) numOfNodes) {
//                    Random rnd = new Random();
//                    int x = rnd.nextInt((int) numOfNodes);
//                    int y = rnd.nextInt((int) numOfNodes);
//                    while (y == x)
//                        y = rnd.nextInt((int) numOfNodes);
//                    if (!(mapSets.containsKey(x) && mapSets.get(x) == y || mapSets.containsKey(y) && mapSets.get(y) == x)) {
//                        graph.addEdge(x, y);
//                        mapSets.put(x, y);//
//                        k++;
//                    }
//                }
//
//
//                // sum += graph.getNeighborhoodWeight(graph.maxNeighborhoodWeight().getId());
//
//                sum += graph.maxNeighborhoodWeight().getNeighborhoodWeight();
//            }
//
//            System.out.println("i = " + i);
//            System.out.println("The weight of the edge which has the biggest weight is: " + (int) (sum / 10));
//
//        }
//    }
//}