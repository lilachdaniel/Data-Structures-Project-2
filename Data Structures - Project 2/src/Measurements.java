public class Measurements {
    public static void main(String[] args) {
        for (int i = 6; i < 21; i++) {

            int numNodes = (int)Math.pow(2, i);

            Graph g = new Graph(getNodes(numNodes));

            boolean[][] edges = new boolean[numNodes][numNodes];


            for (int k = 0; k < numNodes; k++) {
                int n1 = (int)(Math.random() * numNodes);
                int n2 = (int)(Math.random() * numNodes);

                while (edges[n1][n2]) {
                    n1 = (int)(Math.random() * numNodes);
                    n2 = (int)(Math.random() * numNodes);
                }

                edges[n1][n2] = true;
                g.addEdge(n1, n2);
            }
        }

        System.out.println("done");
    }


    public static Graph.Node[] getNodes(int size) {
        Graph.Node[] nodes = new Graph.Node[size];

        for (int j = 0; j < size; j++) {
            nodes[j] = new Graph.Node(j, 1);
        }

        return nodes;
    }
}
