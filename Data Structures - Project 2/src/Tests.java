public class Tests {
    //tests for MaxHeap

    public static void main(String[] args) {
        testHeap(500, 20, 30);
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
                    testDeleteMin(heap);
                else
                    testDelete(heap);
            }
        }

        System.out.println("success!");
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
            System.out.println("Error: heap is invalid after delete");
            displayHeap(heap);
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

    public static void testDeleteMin(MaxHeap heap) {
        Graph.Node toDeleteNode = heap.getValue(0);
        int prevSize = heap.getSize();

        heap.deleteMin();

        if (heap.getSize() + 1 != prevSize) {
            System.out.println("Error: *size* field is invalid after delete-min");
            displayHeap(heap);
            return;
        }

        boolean valid = checkIfMaxHeap(heap);

        if (!valid) {
            System.out.println("Error: heap is invalid after delete-min");
            displayHeap(heap);
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
        Graph g = new Graph(new Graph.Node[size]);
        Graph.Node[] nodes = new Graph.Node[size];

        for (int j = 0; j < size; j++) {
            nodes[j] = g.createNode(j, (int)(Math.random() * (size * 100)));
        }

        return nodes;
    }


    public static void displayHeap(MaxHeap heap) {
        final int height = 4, width = 80;
        // final int height = 10, width = 128;

        int len = width * height * 2 + 2;
        StringBuilder sb = new StringBuilder(len);
        for (int i = 1; i <= len; i++)
            sb.append(i < len - 2 && i % width == 0 ? "\n" : ' ');

        displayR(heap, sb, width / 2, 1, width / 4, width, 0, " ");
        System.out.println(sb);
        System.out.println("\n\n\n");
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
}
