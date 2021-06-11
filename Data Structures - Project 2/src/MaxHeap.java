//public class MaxHeap {
//    private int size;
//    private Graph.Node[] heap;
//
//    /**
//     * Initializes a heap containing the given nodes
//     *
//     * @param nodes - an array of nodes
//     * */
//    public MaxHeap(Graph.Node[] nodes) {
//        this.size = nodes.length;
//        this.heap = new Graph.Node[this.size];
//
//        //create heap and maintain node's heap index
//        for (int i = 0; i < this.size; i++) {
//            this.heap[i] = nodes[i];
//            nodes[i].setHeapIndex(i);
//        }
//
//        //turn the array into a max heap
//        for (int i = this.size - 1; i > -1 ; i--) {
//            heapifyDown(i);
//        }
//    }
//
//    // ************* for tests, delete later ***************
//    public MaxHeap(MaxHeap h) {
//        this.size = h.size;
//        this.heap = h.heap.clone();
//    }
//
//    //*************** for tests, delete later ******************
//    public Graph.Node[] getHeapArray() {
//        return this.heap.clone();
//    }
//
//    /**
//     * This method deletes the node in the specified index from the heap
//     *
//     * @param i - an index of a node in the heap
//     */
//    public void delete(int i) {
//        // only delete if the index is valid
//        if (i > -1 && i < size) {
//            // if deleting the last node, decrease size and finish.
//            // no switching and no heapify needed
//            if (i == size - 1) {
//                this.heap[i] = null;
//                this.size--;
//                return;
//            }
//
//            // replace node 'i' with the last node of the heap
//            this.heap[i] = this.heap[this.size - 1];
//            this.heap[i].setHeapIndex(i);
//            this.heap[this.size - 1] = null;
//
//            this.size--;
//
//            //if my key is smaller than my parent's key, heapify me down. else, heapify up
//            if (getKey(getParent(i)) == -1 || getKey(i) < getKey(getParent(i))) {
//                heapifyDown(i);
//            } else {
//                heapifyUp(i);
//            }
//        }
//    }
//
//    /**
//     * This method deletes the node with the maximum value from the heap
//     *
//     */
//    public void deleteMax() {
//        delete(0);
//    }
//
//    /**
//     * This method decreases a node's value, given the node index and the amount to decrease.
//     * It also changes the associated Graph.Node's neighborhood weight accordingly.
//     * @param i    - the index of the node in the heap
//     * @param diff - the amount to decrease by
//     */
//    public void decreaseKey(int i, int diff) {
//        // only perform the decrease if:
//        // 1. the index is valid
//        // 2. the action will not result in a value less than the node's own weight
//        if (i > -1 && i < size && diff <= this.heap[i].getNeighborhoodWeight() - this.heap[i].getWeight()) {
//            this.heap[i].decreaseNeighborhoodWeight(diff);
//            heapifyDown(i);
//        }
//    }
//
//    /**
//     * This method increases a node's value, given the node index and the amount to increase
//     * It also changes the associated Graph.Node's neighborhood weight accordingly.
//     * @param i    - the index of the node in the heap
//     * @param diff - the amount to increase by
//     */
//    public void increaseKey (int i, int diff) {
//        if (i > -1 && i < size) {
//            this.heap[i].increaseNeighborhoodWeight(diff);
//            heapifyUp(i);
//        }
//    }
//
//    /**
//     * This method performs the heapify-down process on a specified node
//     *
//     * @param i - node index
//     */
//    private void heapifyDown(int i) {
//        int l = getLeft(i);
//        int r = getRight(i);
//        int biggest = i;
//
//        // Update 'biggest' to hold the index of the largest value from i,r,l
//        if (l < this.size && getKey(l) > getKey(biggest)) {
//            biggest = l;
//        }
//        if (r < this.size && getKey(r) > getKey(biggest)) {
//            biggest = r;
//        }
//
//        // If the biggest value does not belong to 'i', swap places and keep going
//        if (biggest > i) {
//            swap(i,biggest);
//            heapifyDown(biggest);
//        }
//    }
//
//    /**
//     * This method performs the heapify-up process on a specified node
//     *
//     * @param i - node index
//     */
//    private void heapifyUp(int i) {
//        int p = getParent(i);
//
//        // while 'i' has a parent with a smaller value,
//        // swap 'i' with its parent and keep going
//        while (i > 0 && getKey(i) > getKey(p)) {
//            swap(i,p);
//            i = p;
//            p = getParent(i);
//        }
//    }
//
//    /**
//     * This method switches two nodes' place in the heap
//     *
//     * @param i - fist node index
//     * @param j - second node index
//     */
//    private void swap(int i, int j) {
//        //swap
//        Graph.Node temp = this.heap[i];
//        this.heap[i] = this.heap[j];
//        this.heap[j] = temp;
//
//        //maintain node's heap index field
//        this.heap[i].setHeapIndex(i);
//        this.heap[j].setHeapIndex(j);
//    }
//
//    /**
//     * This method returns the index of a specified node's parents
//     *
//     * @param i - specified node's index
//     * @return the index of the node's parent (or -1 for the root)
//     */
//    private int getParent(int i) {
//        return (int) ((i+1) / 2) - 1;
//    }
//
//    /**
//     * This method returns the index of a specified node's left son
//     *
//     * @pre   node i has a left son
//     * @param i - specified node's index
//     * @return the index of the node's left son
//     */
//    public int getLeft(int i) {
//        return 2 * (i + 1) - 1;
//    }
//
//    /**
//     * This method returns the index of a specified node's right son
//     *
//     * @pre   node i has a right son
//     * @param i - specified node's index
//     * @return the index of the node's right son
//     */
//    public int getRight(int i) {
//        return 2 * (i + 1);
//    }
//
//    /**
//     * This method returns the number of items in the heap
//     *
//     * @return the number of items in the heap
//     */
//    public int getSize() {
//        return this.size;
//    }
//
//    /**
//     * This method returns the key of the node in the specified index
//     *
//     * @param i - specified node's index
//     * @return the key of the specified node, or -1 if 'i' is invalid
//     */
//    public int getKey(int i) {
//        // if the index is invalid, or if the specified index does not hold a value
//        if (i < 0 || i >= this.size || this.heap[i] == null) {
//            return -1;
//        }
//        return this.heap[i].getNeighborhoodWeight();
//    }
//
//    /**
//     * This method returns the node with the maximum key
//     *
//     * @return the node with the maximum key
//     */
//    public Graph.Node getMax() { return this.heap[0]; }
//
//    // *********** for tests, delete later ***************
//    public Graph.Node getValue(int i) {
//        if (i >= this.size || this.heap[i] == null) {
//            return null;
//        }
//        return this.heap[i];
//    }
//
//    // *********** for tests, delete later ***************
//    public String[] infoToArray() {
//        String[] info = new String[this.size];
//        for (int i = 0; i < info.length; i++) {
//            info[i] = "[" + this.heap[i].getId() + " | " +
//                            this.heap[i].getNeighborhoodWeight() + "]";
//        }
//        return info;
//    }
//}
