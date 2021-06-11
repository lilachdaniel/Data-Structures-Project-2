
public class Tests {
    //tests for MaxHeap

    public static void main(String[] args) {
        // testHeap(200, 60, 50);
        // testList(100, 500);
        // testHashTable(15,200,100);

        testGraph(5000, 700, 300);
    }

    public static void testHeap(int numHeap, int numIterations, int heapSize) {
        for (int i = 0; i < numHeap; i++) {
            // create heap
            Graph.Node[] nodes = getRandNodeArray(heapSize);
            MaxHeap heap = new MaxHeap(nodes);

            // test MaxHeap's constructor
            boolean valid = checkIfMaxHeap(heap);
            if (!valid) {
                System.out.println("iteration " + i + ": Error in heap constructor");
                displayHeap(heap);
                break;
            }

            // test increase-key
            for (int j = 0; j < numIterations; j++) {
                testIncreaseKey(heap);
            }

            // test decrease-key
            for (int j = 0; j < numIterations; j++) {
                testDecreaseKey(heap);
            }

            if (heap.getSize() != heapSize) {
                System.out.println("Error: size changed");
            }

            // test delete and deleteMin
            while (heap.getSize() != 0) {
                double choice = Math.random();

                if (choice < 0.1)
                    testDeleteMax(heap);
                else
                    testDelete(heap);
            }
        }

        System.out.println("MaxHeap - success!");
    }

    public static void testIncreaseKey(MaxHeap heap) {
        int toIncreaseIndex = (int)(Math.random() * heap.getSize());
        Graph.Node toIncreaseNode = heap.getValue(toIncreaseIndex);
        int toIncreaseAmount = (int)(Math.random() * heap.getSize() * 100);

        int prevKey = toIncreaseNode.getNeighborhoodWeight();

        heap.increaseKey(toIncreaseIndex, toIncreaseAmount);

        if (toIncreaseNode.getNeighborhoodWeight() != prevKey + toIncreaseAmount) {
            System.out.println("Error: IncreaseKey did not increase key of node with index " + toIncreaseIndex);
            displayHeap(heap);
            return;
        }

        boolean valid = checkIfMaxHeap(heap);
        if (!valid) {
            System.out.println("Error: heap is invalid after increase-key");
            displayHeap(heap);
            return;
        }
    }

    public static void testDecreaseKey(MaxHeap heap) {
        int toDecreaseIndex = (int)(Math.random() * heap.getSize());
        Graph.Node toDecreaseNode = heap.getValue(toDecreaseIndex);
        int toDecreaseAmount = (int)(Math.random() * heap.getSize() * 100);

        if (toDecreaseAmount >= toDecreaseNode.getNeighborhoodWeight() - toDecreaseNode.getWeight()) {
            return;
        }

        int prevKey = toDecreaseNode.getNeighborhoodWeight();

        heap.decreaseKey(toDecreaseIndex, toDecreaseAmount);

        if (toDecreaseNode.getNeighborhoodWeight() != prevKey - toDecreaseAmount) {
            System.out.println("Error: DecreaseKey did not decrease key of node with index " + toDecreaseIndex);
            displayHeap(heap);
            return;
        }

        boolean valid = checkIfMaxHeap(heap);
        if (!valid) {
            System.out.println("Error: heap is invalid after decrease-key");
            displayHeap(heap);
            return;
        }
    }

    public static void testDelete(MaxHeap heap) {
        StringBuilder heapBefore = displayHeap(heap);
        MaxHeap before = new MaxHeap(heap);

        int toDeleteIndex = (int)(Math.random() * heap.getSize());
        Graph.Node toDeleteNode = heap.getValue(toDeleteIndex);
        int prevSize = heap.getSize();

        heap.delete(toDeleteIndex);

        if (heap.getSize() + 1 != prevSize) {
            System.out.println("Error: *size* field is invalid after delete");
            displayHeap(heap);
            return;
        }

        boolean valid = checkIfMaxHeap(heap);

        if (!valid) {
            before.delete(toDeleteIndex);
            System.out.println("Error: heap is invalid after delete");
            StringBuilder heapAfter = displayHeap(heap);

            System.out.println("Attempted to delete id: " + toDeleteNode.getId());
            System.out.println("Before: ");
            System.out.println(heapBefore);
            System.out.println("\n\n\n");
            System.out.println("After: ");
            System.out.println(heapAfter);
            System.out.println("\n\n\n");

            return;
        }

        for (int i = 0; i < heap.getSize(); i++) {
            if (heap.getValue(i).getId() == toDeleteNode.getId())
            {
                System.out.println("Error: delete did not delete");
                displayHeap(heap);
                return;
            }
        }

    }

    public static void testDeleteMax(MaxHeap heap) {
        StringBuilder heapBefore = displayHeap(heap);
        MaxHeap before = new MaxHeap(heap);

        Graph.Node toDeleteNode = heap.getValue(0);
        int prevSize = heap.getSize();

        heap.deleteMax();

        if (heap.getSize() + 1 != prevSize) {
            System.out.println("Error: *size* field is invalid after delete-min");
            displayHeap(heap);
            return;
        }

        boolean valid = checkIfMaxHeap(heap);

        if (!valid) {
            System.out.println("Error: heap is invalid after delete-max");
            StringBuilder heapAfter = displayHeap(heap);

            System.out.println("Attempted to delete id: " + toDeleteNode.getId());
            System.out.println("Before: ");
            System.out.println(heapBefore);
            System.out.println("\n\n\n");
            System.out.println("After: ");
            System.out.println(heapAfter);
            System.out.println("\n\n\n");
            return;
        }

        for (int i = 0; i < heap.getSize(); i++) {
            if (heap.getValue(i).getId() == toDeleteNode.getId())
            {
                System.out.println("Error: delete-min did not delete-min");
                displayHeap(heap);
                return;
            }
        }
    }

    public static boolean checkIfMaxHeap(MaxHeap maxHeap) {
        for (int i = 0; i < maxHeap.getSize(); i++) {
            int l = maxHeap.getLeft(i);
            int r = maxHeap.getRight(i);

            try {
                maxHeap.getKey(i);
            }
            catch (NullPointerException e) {
                return false;
            }
            if (l < maxHeap.getSize() && maxHeap.getKey(l) > maxHeap.getKey(i)) {
                return false;
            }

            if (r < maxHeap.getSize() && maxHeap.getKey(r) > maxHeap.getKey(i)) {
                return false;
            }
        }
        return true;
    }

    public static Graph.Node[] getRandNodeArray(int size) {
//        Graph g = new Graph(new Graph.Node[size]);
        Graph.Node[] nodes = new Graph.Node[size];

        for (int j = 0; j < size; j++) {
            nodes[j] = new Graph.Node(j, (int)(Math.random() * (size * 100)));
        }

        return nodes;
    }

    public static StringBuilder displayHeap(MaxHeap heap) {
        final int height = 5, width = 80;
        // final int height = 10, width = 128;

        int len = width * height * 2 + 2;
        StringBuilder sb = new StringBuilder(len);
        for (int i = 1; i <= len; i++)
            sb.append(i < len - 2 && i % width == 0 ? "\n" : ' ');

        displayR(heap, sb, width / 2, 1, width / 4, width, 0, " ");

        return sb;

//        System.out.println(sb);
//        System.out.println("\n\n\n");
    }

    private static void displayR(MaxHeap heap, StringBuilder sb, int c, int r, int d, int w, int index,
                                 String edge) {
        if (heap.getKey(index) != -1) {
            displayR(heap, sb, c - d, r + 2, d / 2, w, heap.getLeft(index), " /");

            String s = String.valueOf(heap.infoToArray()[index]);
            int idx1 = r * w + c - (s.length() + 1) / 2;
            int idx2 = idx1 + s.length();
            int idx3 = idx1 - w;
            if (idx2 < sb.length())
                sb.replace(idx1, idx2, s).replace(idx3, idx3 + 2, edge);

            displayR(heap, sb, c + d, r + 2, d / 2, w, heap.getRight(index), "\\ ");
        }
    }


//
//    public static void testList(int numList, int numIterations) {
//        for (int i = 0; i < numList; i++) {
//            DLList ourList = new DLList();
//
//            for (int j = 0; j < numIterations; j++) {
//
//                double action = Math.random();
//
//                // add
//                if (action < 0.5 || ourList.getSize() == 0) {
//                    ourList.add(j);
//
//                    if (!isNumInList(ourList, j, true)) {
//                        displayList(ourList);
//                        System.out.println("Error after add - node not in list");
//                    }
//
//                    if (!isListValid(ourList)) {
//                        displayList(ourList);
//                        System.out.println("Error after add - size is false");
//                    }
//                }
//                // delete
//                else {
//                    int randIndex = (int)(Math.random() * ourList.getSize());
//                    DLList.DLNode toDelete = ourList.getFirst();
//
//                    for (int k = 0; k < randIndex; k++) {
//                        toDelete = toDelete.getNext();
//                    }
//
//                    ourList.delete(toDelete);
//
//                    if (!isNumInList(ourList, (int)(toDelete.getValue()), false)) {
//                        displayList(ourList);
//                        System.out.println("Error after delete - node still in list");
//                    }
//
//                    if (!isListValid(ourList)) {
//                        displayList(ourList);
//                        System.out.println("Error after delete - size is false");
//                    }
//                }
//
//            }
//        }
//
//        System.out.println("DLList - success!");
//    }
//
//    public static boolean isNumInList(DLList list, int num, boolean inList) {
//        DLList.DLNode curr = list.getFirst();
//
//        while (curr != null) {
//            if ((int)(curr.getValue()) == num) {
//                return inList;
//            }
//            curr = curr.getNext();
//        }
//
//        return !inList;
//    }
//
//    public static boolean isListValid(DLList list) {
//        int actualSize = 0;
//        DLList.DLNode curr = list.getFirst();
//
//        while (curr != null) {
//            actualSize++;
//            curr = curr.getNext();
//        }
//
//        return actualSize == list.getSize();
//    }

    public static void displayList(DLList list) {
        String str = "";
        DLList.DLNode curr = list.getFirst();

        while (curr != null) {
            str += "[" + curr.getGraphNode().getId() + "] --> ";

            curr = curr.getNext();
        }

        System.out.println(str);
    }


    public static void testHashTable(int numTables, int numIterations, int heapSize) {
        for (int i = 0; i < numTables; i++) {
            // create heap
            Graph.Node[] nodes = getRandNodeArray(heapSize);
            MaxHeap heap = new MaxHeap(nodes);

            // create hash table
            HashTable ht = new HashTable(heap.getHeapArray());

            //check if all nodes exist
            for (Graph.Node node : nodes) {
                if (ht.find(node.getId()) == null) {
                    System.out.println("Error - initializing hash table");
                    return;
                }
            }

            //random delete
            for (int j = 0; j < numIterations; j++) {
                int indOfNode = (int) (Math.random() * heapSize);
                int toDelete = nodes[indOfNode].getId();

                ht.delete(toDelete);

                if (ht.find(toDelete) != null) {
                    System.out.println("Error in delete");
                    return;
                }

            }
        }
        System.out.println("success - hash table! :)");
    }





    public static void testGraph(int numGraphs, int numIterations, int graphSize) {
        for (int i = 0; i < numGraphs; i++) {
            Graph.Node[] nodes = getRandNodeArray(graphSize);
            Graph g = new Graph(nodes);

            boolean[] deleted = new boolean[graphSize];
            int numDeleted = 0;

            for (int j = 0; j < numIterations; j++) {
                double  action = Math.random();
                String actionStr = "addEdge";

                // if true, delete
                if (action < 0.2 && numDeleted < graphSize) {
                    actionStr = "delete";
                   boolean res = testDelete(g, nodes, deleted);
                   if (res) {
                       numDeleted++;
                   }
                }
                // else, addEdge
                else {
                    testAddEdge(g, nodes, deleted);
                }


                if (!checkMaxWeightValid(g, nodes, deleted)) {
                    System.out.println("Error - graph's maxWeight does not match heap data");
                    System.out.println("Last op: " + actionStr);
                    displayGraph(g, nodes, deleted);
                    return;
                }

                if (!checkAllWeightsValid(g, nodes, deleted)) {
                    System.out.println("Error - graph node's weight is wrong");
                    System.out.println("Last op: " + actionStr);
                    displayGraph(g, nodes, deleted);
                    return;
                }
            }
        }

        System.out.println("success - Graph!");
    }

    public static boolean testDelete(Graph g, Graph.Node[] nodes, boolean[] deleted) {
        int toDeleteIndex = (int)(Math.random() * nodes.length);

        if (deleted[toDeleteIndex]) {
            return false;
        }

        Graph.Node toDeleteNode = nodes[toDeleteIndex];
        deleted[toDeleteIndex] = true;


        boolean result = g.deleteNode(toDeleteNode.getId());

        if (!result) {
            System.out.println("Error - delete did not succeed");
            displayGraph(g, nodes, deleted);
            return result;
        }

        // check that the node was removed from heap
        for (int i = 0; i < g.heap.getSize(); i++) {
            if (g.heap.getValue(0) == toDeleteNode) {
                System.out.println("Error - delete did not delete from heap");
                displayGraph(g, nodes, deleted);
                return result;
            }
        }

        // check that the node was removed from hashtable
        if (g.table.find(toDeleteNode.getId()) != null) {
            System.out.println("Error - delete did not delete from table");
            displayGraph(g, nodes, deleted);
            return result;
        }

        // check that the node was removed from all neighbor lists
        for (int i = 0; i < nodes.length; i++) {
            if (!deleted[i] && i != toDeleteIndex) {
                for (NeighborsDLList.NeighborNode nnode : nodes[i].getNList()) {
                    if (nnode.getGraphNode() == toDeleteNode) {
                        System.out.println("Error - delete did not remove from neighbor list");
                        displayGraph(g, nodes, deleted);
                        return result;

                    }
                }
            }
        }

        return result;
    }

    public static void testAddEdge(Graph g, Graph.Node[] nodes, boolean[] deleted) {
        int node1Index = (int)(Math.random() * nodes.length);
        int node2Index = (int)(Math.random() * nodes.length);
        Graph.Node node1 = nodes[node1Index];
        Graph.Node node2 = nodes[node2Index];
        if (node1 == node2) {
            return;
        }

        // if one of the nodes was deleted, expect "false"
        if (deleted[node1Index] || deleted[node2Index]) {
            boolean res = g.addEdge(node1.getId(), node2.getId());

            if (res) {
                System.out.println("Error - graph edge added when not supposed to");
                displayGraph(g, nodes, deleted);
            }
        }
        else {
            // make sure the edge does not exist yet
            for (NeighborsDLList.NeighborNode nnode : node1.getNList()) {
                if (nnode.getGraphNode() == node2) {
                    return;
                }
            }

            // perform addition
            boolean res = g.addEdge(node1.getId(), node2.getId());

            // if returned false, print error
            if (!res) {
                System.out.println("Error - graph edge *not* added: " + node1.getId() + " , " + node2.getId());
                displayGraph(g, nodes, deleted);
            }

            // make sure the neighbor lists were updated

            boolean found = false;
            for (NeighborsDLList.NeighborNode nnode : node1.getNList()) {
                if (nnode.getGraphNode() == node2) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Error - new edge does not apprear in neighbor list: " +
                                   node1.getId() + " , " + node2.getId());
                displayGraph(g, nodes, deleted);
            }

            found = false;
            for (NeighborsDLList.NeighborNode nnode : node2.getNList()) {
                if (nnode.getGraphNode() == node1) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Error - new edge does not apprear in neighbor list: " +
                        node1.getId() + " , " + node2.getId());
                displayGraph(g, nodes, deleted);
            }
        }
    }

    public static boolean checkMaxWeightValid(Graph g, Graph.Node[] nodes, boolean[] deleted) {
        if (g.heap.getSize() == 0) {
            return true;
        }
        int maxNWeight = 0;

        for (int i = 0; i < nodes.length; i++) {
            if (!deleted[i]) {
                if (nodes[i].getNeighborhoodWeight() > maxNWeight) {
                    maxNWeight = nodes[i].getNeighborhoodWeight();
                }
            }
        }

        return g.maxNeighborhoodWeight().getNeighborhoodWeight() == maxNWeight;
    }

    public static boolean checkAllWeightsValid(Graph g, Graph.Node[] nodes, boolean[] deleted) {
        for (int i = 0; i < nodes.length; i++) {
            if (!deleted[i]) {
                // acuumulate the weight from all neighbors (using neighbor list)
                int weightFromNeighborList = nodes[i].getWeight();
                for (NeighborsDLList.NeighborNode nnode : nodes[i].getNList()) {
                    weightFromNeighborList += nnode.getGraphNode().getWeight();
                }

                // check if accumulated weight matches the node's nWeight field
                if (nodes[i].getNeighborhoodWeight() != weightFromNeighborList) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void displayGraph(Graph g, Graph.Node[] nodes, boolean[] deleted) {
        System.out.println(displayHeap(g.heap));
        for (int i = 0; i < nodes.length; i++) {
            if (!deleted[i]) {
                System.out.print("\n(" + nodes[i].getId() + "," + nodes[i].getWeight() + ")" + ": ");
                displayList(nodes[i].getNList());
            }
            else {
                System.out.print("\nXXX (" + nodes[i].getId() + "," + nodes[i].getWeight() + ")" + ": ");
                displayList(nodes[i].getNList());
            }
        }
    }
}
