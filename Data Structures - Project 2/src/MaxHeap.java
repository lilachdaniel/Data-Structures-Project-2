public class MaxHeap {
    private int size;
    private Graph.Node[] heap;

    public MaxHeap(Graph.Node[] nodes) {
        this.size = nodes.length;
        this.heap = nodes.clone();

        //turn the array into a max heap
        for (int i = this.size - 1; i > -1 ; i--) {
            heapifyDown(i);
        }
    }

    // ************* delete ***************
    public MaxHeap(MaxHeap h) {
        this.size = h.size;
        this.heap = h.heap.clone();
    }

    public Graph.Node[] getHeapArray() {
        return this.heap.clone();
    }

    public void delete(int i) {
        if (i > -1 && i < size) {
            // if deleting the last node, decrease size and finish.
            // no switching and no heapify needed
            if (i == size - 1) {
                this.heap[i] = null;
                this.size--;
                return;
            }
            this.heap[i] = this.heap[this.size - 1];
            this.heap[this.size - 1] = null;

            this.size--;

            //if my key is smaller than my parent's key, heapify me down
            if (getKey(getParent(i)) == -1 || getKey(i) < getKey(getParent(i))) {
                heapifyDown(i);
            } else {
                heapifyUp(i);
            }
        }
    }

    public void deleteMax() {
        delete(0);
    }

    public void decreaseKey(int i, int diff) {
        if (i > -1 && i < size && diff < this.heap[i].getNeighborhoodWeight() - this.heap[i].getWeight()) {

            this.heap[i].decreaseNeighborhoodWeight(diff);
            heapifyDown(i);
        }
    }

    public void increaseKey (int i, int diff) {
        if (i > -1 && i < size) {
            this.heap[i].increaseNeighborhoodWeight(diff);
            heapifyUp(i);
        }
    }

    private void heapifyDown(int i) {
        int l = getLeft(i);
        int r = getRight(i);
        int biggest = i;

        if (l < this.size && getKey(l) > getKey(biggest)) {
            biggest = l;
        }

        if (r < this.size && getKey(r) > getKey(biggest)) {
            biggest = r;
        }

        if (biggest > i) {
            //swap <->
            Graph.Node temp = this.heap[i];
            this.heap[i] = this.heap[biggest];
            this.heap[biggest] = temp;

            heapifyDown(biggest);
        }
    }

    private void heapifyUp(int i) {
        int p = getParent(i);
        while (i > 0 && getKey(i) > getKey(p)) {
            //swap <->
            Graph.Node temp = this.heap[i];
            this.heap[i] = this.heap[p];
            this.heap[p] = temp;

            i = p;
            p = getParent(i);
        }
    }

    private int getParent(int i) {
        return (int) ((i+1) / 2) - 1;
    }

    public int getLeft(int i) {
        return 2 * (i + 1) - 1;
    }

    public int getRight(int i) {
        return 2 * (i + 1);
    }

    public int getSize() {
        return this.size;
    }

    public int getKey(int i) {
        if (i < 0 || i >= this.size || this.heap[i] == null) {
            return -1;
        }
        return this.heap[i].getNeighborhoodWeight();
    }

    public Graph.Node getValue(int i) {
        if (i >= this.size || this.heap[i] == null) {
            return null;
        }
        return this.heap[i];
    }

    public String[] infoToArray() {
        String[] info = new String[this.size];
        for (int i = 0; i < info.length; i++) {
            info[i] = "[" + this.heap[i].getId() + " | " +
                            this.heap[i].getNeighborhoodWeight() + "]";
        }
        return info;
    }



//    private class HeapNode {
//        private int key;
//        private Graph.Node value;
//
//        private HeapNode(int key, Graph.Node value) {
//            this.key = key;
//            this.value = value;
//        }
//
//        private Graph.Node getValue() {
//            return this.value;
//        }
//
//        private void setValue(Graph.Node newValue) {
//            this.value = newValue;
//        }
//
//        private int getKey() {
//            return this.key;
//        }
//
//        private void setKey(int newKey) {
//            this.key = newKey;
//        }
//
//        private void decreaseKey(int k) {
//            this.key -= k;
//        }
//
//        private void increaseKey(int k) {
//            this.key += k;
//        }

//    }



}
