import java.util.Random;
import java.util.TreeSet;

public class Measurements {
    public static void main(String[] args) {
        Random rand = new Random();

        for (int i = 6; i <= 21; i++) {
            long start = System.nanoTime();

            System.out.print(i + ":\t");

            int numNodes = (int)Math.pow(2, i);

            Graph g = createGraph(numNodes);

            long end = System.nanoTime();

            System.out.print("highest rank is: " + (g.maxNeighborhoodWeight().getNeighborhoodWeight() - 1));
            System.out.println("\t\tseconds: " + (end - start) / (double)1000000000);

        }

        System.out.println("done");
    }

    public static Graph createGraph(int size) {
        Graph.Node[] nodes = new Graph.Node[size];

        for (int j = 0; j < size; j++) {
            nodes[j] = new Graph.Node(j, 1);
        }

        Graph g = new Graph(nodes);

        Pair[] toBeEdge = getRandEdges(size, size);

        for (Pair p : toBeEdge) {
            g.addEdge(p.x, p.y);
        }

        return g;
    }


    public static Pair[] getRandEdges(int edgeNum, int graphSize) {
        long range = (long)((long)graphSize * (long)(graphSize-1) / 2);

        Pair[] edges = new Pair[edgeNum];

        long[] choices = new long[edgeNum];   // maintained to be sorted

        for (int i = 0; i < edgeNum; i++)
        {
            // generate random number from the altered range (altered according to exclusions)
            long newChoice = (long)(Math.random() * (range - i)) + 1;

            // alter newChoicie according to the exclusions
            int index = 0;
            while (index < i && choices[index] <= newChoice) {
                newChoice++;
                index++;
            }

            // maintain 'choices' array in a sorted order
            long toInsert = newChoice;
            while (index < i) {
                long temp = choices[index];
                choices[index] = toInsert;
                toInsert = temp;

                index++;
            }
            choices[i] = toInsert;

            // calculate the pair that matches the new random number
            // https://stackoverflow.com/questions/15793172/efficiently-generating-unique-pairs-of-integers
            // https://en.wikipedia.org/wiki/Pairing_function
            int x = (int)(Math.sqrt((long)(8 * (newChoice - 1) + 1)) / 2 + 1.5);
            int y = (int)(newChoice - (x-1) * (long)(x-2) / 2);
            edges[i] = new Pair(x-1,y-1);
        }

        for (int i = 1; i < choices.length; i++)
            if (choices[i] == choices[i-1])
                System.out.println("DUPLICATES");

        return edges;
    }
}

class Pair {
    int x;
    int y;

    public Pair(int x, int y) { this.x = x ; this.y = y; }
}