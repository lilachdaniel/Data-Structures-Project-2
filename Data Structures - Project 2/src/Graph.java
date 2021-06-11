import java.util.Iterator;

/*
You must NOT change the signatures of classes/methods in this skeleton file.
You are required to implement the methods of this skeleton file according to the requirements.
You are allowed to add classes, methods, and members as required.
 */

/**
 * This class represents a graph that efficiently maintains the heaviest neighborhood over edge addition and
 * vertex deletion.
 *
 */
public class Graph {
    public HashTable table;
    public MaxHeap heap;
    private int numEdges;

    /**
     * Initializes the graph on a given set of nodes. The created graph is empty, i.e. it has no edges.
     * You may assume that the ids of distinct nodes are distinct.
     *
     * @param nodes - an array of node objects
     */
    public Graph(Node [] nodes){
        this.heap = new MaxHeap(nodes);
        this.table = new HashTable(nodes);
        this.numEdges = 0;
    }

    /**
     * This method returns the node in the graph with the maximum neighborhood weight.
     * Note: nodes that have been removed from the graph using deleteNode are no longer in the graph.
     * @return a Node object representing the correct node. If there is no node in the graph, returns 'null'.
     */
    public Node maxNeighborhoodWeight(){
        return this.heap.getMax();
    }

    /**
     * given a node id of a node in the graph, this method returns the neighborhood weight of that node.
     *
     * @param node_id - an id of a node.
     * @return the neighborhood weight of the node of id 'node_id' if such a node exists in the graph.
     * Otherwise, the function returns -1.
     */
    public int getNeighborhoodWeight(int node_id){
        Graph.Node node = this.table.find(node_id);

        if (node == null) {
            return -1;
        }
        return node.getNeighborhoodWeight();
    }

    /**
     * This function adds an edge between the two nodes whose ids are specified.
     * If one of these nodes is not in the graph, the function does nothing.
     * The two nodes must be distinct; otherwise, the function does nothing.
     * You may assume that if the two nodes are in the graph, there exists no edge between them prior to the call.
     *
     * @param node1_id - the id of the first node.
     * @param node2_id - the id of the second node.
     * @return returns 'true' if the function added an edge, otherwise returns 'false'.
     */
    public boolean addEdge(int node1_id, int node2_id){
        // if nodes aren't distinct, or one of the nodes does not exist, do nothing and return false
        if (node1_id == node2_id || this.table.find(node1_id) == null || this.table.find(node2_id) == null) {
            return false;
        }

        // update the nodes' neighbor lists
        Node node1 = this.table.find(node1_id);
        Node node2 = this.table.find(node2_id);

        NeighborsDLList neighbors1 = node1.getNList();
        NeighborsDLList neighbors2 = node2.getNList();

        NeighborsDLList.NeighborNode newNeighbor1 = neighbors1.addNewNeighbor(node2);
        NeighborsDLList.NeighborNode newNeighbor2 = neighbors2.addNewNeighbor(node1);

        newNeighbor1.setTwin(newNeighbor2);
        newNeighbor2.setTwin(newNeighbor1);


        // update each node's neighborhood weight
        int weight1 = node1.getWeight();
        int weight2 = node2.getWeight();

        this.heap.increaseKey(node1.getHeapIndex(), weight2);
        this.heap.increaseKey(node2.getHeapIndex(), weight1);

        this.numEdges++;

        return true;
    }

    /**
     * Given the id of a node in the graph, deletes the node of that id from the graph, if it exists.
     *
     * @param node_id - the id of the node to delete.
     * @return returns 'true' if the function deleted a node, otherwise returns 'false'
     */
    public boolean deleteNode(int node_id){
        Node node = this.table.find(node_id);

         if (node == null) {
            return false;
        }

        // Go over the node's neighbors. For each one, delete me
        // from their neighbor list and decrease their neighborhood weight
        for (NeighborsDLList.NeighborNode nnode : node.getNList()) {  // nnode     = representation of edge in *my* list

            NeighborsDLList.NeighborNode twinNnode = nnode.getTwin(); // twinNnode = representation of edge in *his* list

            Node twin = nnode.getGraphNode();                         // twin      = Graph.Node object of neighbor

            // delete "me" from my neighbor's list
            twin.getNList().delete(twinNnode);

            this.numEdges--;

            // decrease neighbor's key (also maintains the node's neighborhood weight)
            this.heap.decreaseKey(twin.getHeapIndex(), node.getWeight());
        }

        // commit suicide
        this.heap.delete(node.getHeapIndex());
        this.table.delete(node.getId());

        return true;
    }

    /**
     * Returns the number of nodes currently in the graph.
     * @return the number of nodes in the graph.
     */
    public int getNumNodes(){
        return this.heap.getSize();
    }

    /**
     * Returns the number of edges currently in the graph.
     * @return the number of edges currently in the graph.
     */
    public int getNumEdges(){ return this.numEdges; }


    /**
     * This class represents a node in the graph.
     */
    public static class Node{
        private int id;
        private int weight;
        private int nWeight;
        private NeighborsDLList nList;
        private int heapIndex;


        /**
         * Creates a new node object, given its id and its weight.
         * @param id - the id of the node.
         * @param weight - the weight of the node.
         */
        public Node(int id, int weight){
            this.id = id;
            this.weight = weight;
            this.nWeight = weight;
            this.nList = new NeighborsDLList();
            this.heapIndex = -1;
        }

        /**
         * Returns the id of the node.
         * @return the id of the node.
         */
        public int getId() { return this.id; }

        /**
         * Returns the weight of the node.
         * @return the weight of the node.
         */
        public int getWeight() { return this.weight; }

        /**
         * @return the node's neighborhood weight
         */
        public int getNeighborhoodWeight() {
            return this.nWeight;
        }

        /**
         * Increases the node's neighborhood weight
         * @param diff - the amount to increase by
         */
        public void increaseNeighborhoodWeight(int diff) {
            this.nWeight += diff;
        }

        /**
         * Decreases the node's neighborhood weight
         * @param diff - the amount to decrease by
         */
        public void decreaseNeighborhoodWeight(int diff) {
            this.nWeight -= diff;
        }

        /**
         * @return The node's index inside the graph's max heap
         */
        public int getHeapIndex() {  return this.heapIndex;  }

        /**
         * Updates the node's heapIndex field, which represents its location in the graph's max heap
         * @param heapIndex - the new index
         */
        public void setHeapIndex(int heapIndex) {  this.heapIndex = heapIndex;  }

        /**
         * @return The node's neighbor list
         */
        public NeighborsDLList getNList() {    return this.nList;    }
    }
}


/**
 * This class represents a maximum heap storing Graph.Nodes, sorted by their neighborhood-weight
 */
class MaxHeap {
    private int size;
    private Graph.Node[] heap;

    /**
     * Initializes a heap containing the given nodes
     *
     * @param nodes - an array of nodes
     * */
    public MaxHeap(Graph.Node[] nodes) {
        this.size = nodes.length;
        this.heap = new Graph.Node[this.size];

        //create heap and maintain node's heap index
        for (int i = 0; i < this.size; i++) {
            this.heap[i] = nodes[i];
            nodes[i].setHeapIndex(i);
        }

        //turn the array into a max heap
        for (int i = this.size - 1; i > -1 ; i--) {
            heapifyDown(i);
        }
    }

    // ************* for tests, delete later ***************
    public MaxHeap(MaxHeap h) {
        this.size = h.size;
        this.heap = h.heap.clone();
    }

    //*************** for tests, delete later ******************
    public Graph.Node[] getHeapArray() {
        return this.heap.clone();
    }

    /**
     * This method deletes the node in the specified index from the heap
     *
     * @param i - an index of a node in the heap
     */
    public void delete(int i) {
        // only delete if the index is valid
        if (i > -1 && i < size) {
            // if deleting the last node, decrease size and finish.
            // no switching and no heapify needed
            if (i == size - 1) {
                this.heap[i] = null;
                this.size--;
                return;
            }

            // replace node 'i' with the last node of the heap
            this.heap[i] = this.heap[this.size - 1];
            this.heap[i].setHeapIndex(i);
            this.heap[this.size - 1] = null;

            this.size--;

            //if my key is smaller than my parent's key, heapify me down. else, heapify up
            if (getKey(getParent(i)) == -1 || getKey(i) < getKey(getParent(i))) {
                heapifyDown(i);
            } else {
                heapifyUp(i);
            }
        }
    }

    /**
     * This method deletes the node with the maximum value from the heap
     *
     */
    public void deleteMax() {
        delete(0);
    }

    /**
     * This method decreases a node's value, given the node index and the amount to decrease.
     * It also changes the associated Graph.Node's neighborhood weight accordingly.
     * @param i    - the index of the node in the heap
     * @param diff - the amount to decrease by
     */
    public void decreaseKey(int i, int diff) {
        // only perform the decrease if:
        // 1. the index is valid
        // 2. the action will not result in a value less than the node's own weight
        if (i > -1 && i < size && diff <= this.heap[i].getNeighborhoodWeight() - this.heap[i].getWeight()) {
            this.heap[i].decreaseNeighborhoodWeight(diff);
            heapifyDown(i);
        }
    }

    /**
     * This method increases a node's value, given the node index and the amount to increase
     * It also changes the associated Graph.Node's neighborhood weight accordingly.
     * @param i    - the index of the node in the heap
     * @param diff - the amount to increase by
     */
    public void increaseKey (int i, int diff) {
        if (i > -1 && i < size) {
            this.heap[i].increaseNeighborhoodWeight(diff);
            heapifyUp(i);
        }
    }

    /**
     * This method performs the heapify-down process on a specified node
     *
     * @param i - node index
     */
    private void heapifyDown(int i) {
        int l = getLeft(i);
        int r = getRight(i);
        int biggest = i;

        // Update 'biggest' to hold the index of the largest value from i,r,l
        if (l < this.size && getKey(l) > getKey(biggest)) {
            biggest = l;
        }
        if (r < this.size && getKey(r) > getKey(biggest)) {
            biggest = r;
        }

        // If the biggest value does not belong to 'i', swap places and keep going
        if (biggest > i) {
            swap(i,biggest);
            heapifyDown(biggest);
        }
    }

    /**
     * This method performs the heapify-up process on a specified node
     *
     * @param i - node index
     */
    private void heapifyUp(int i) {
        int p = getParent(i);

        // while 'i' has a parent with a smaller value,
        // swap 'i' with its parent and keep going
        while (i > 0 && getKey(i) > getKey(p)) {
            swap(i,p);
            i = p;
            p = getParent(i);
        }
    }

    /**
     * This method switches two nodes' place in the heap
     *
     * @param i - fist node index
     * @param j - second node index
     */
    private void swap(int i, int j) {
        //swap
        Graph.Node temp = this.heap[i];
        this.heap[i] = this.heap[j];
        this.heap[j] = temp;

        //maintain node's heap index field
        this.heap[i].setHeapIndex(i);
        this.heap[j].setHeapIndex(j);
    }

    /**
     * This method returns the index of a specified node's parents
     *
     * @param i - specified node's index
     * @return the index of the node's parent (or -1 for the root)
     */
    private int getParent(int i) {
        return (int) ((i+1) / 2) - 1;
    }

    /**
     * This method returns the index of a specified node's left son
     *
     * @pre   node i has a left son
     * @param i - specified node's index
     * @return the index of the node's left son
     */
    public int getLeft(int i) {
        return 2 * (i + 1) - 1;
    }

    /**
     * This method returns the index of a specified node's right son
     *
     * @pre   node i has a right son
     * @param i - specified node's index
     * @return the index of the node's right son
     */
    public int getRight(int i) {
        return 2 * (i + 1);
    }

    /**
     * This method returns the number of items in the heap
     *
     * @return the number of items in the heap
     */
    public int getSize() {
        return this.size;
    }

    /**
     * This method returns the key of the node in the specified index
     *
     * @param i - specified node's index
     * @return the key of the specified node, or -1 if 'i' is invalid
     */
    public int getKey(int i) {
        // if the index is invalid, or if the specified index does not hold a value
        if (i < 0 || i >= this.size || this.heap[i] == null) {
            return -1;
        }
        return this.heap[i].getNeighborhoodWeight();
    }

    /**
     * This method returns the node with the maximum key
     *
     * @return the node with the maximum key
     */
    public Graph.Node getMax() { return this.heap[0]; }

    // *********** for tests, delete later ***************
    public Graph.Node getValue(int i) {
        if (i >= this.size || this.heap[i] == null) {
            return null;
        }
        return this.heap[i];
    }

    // *********** for tests, delete later ***************
    public String[] infoToArray() {
        String[] info = new String[this.size];
        for (int i = 0; i < info.length; i++) {
            info[i] = "[" + this.heap[i].getId() + " | " +
                    this.heap[i].getNeighborhoodWeight() + "]";
        }
        return info;
    }
}


/**
 * This class represents a hashtable storing Graph.Nodes
 */
class HashTable {
    private DLList[] table;
    private final int p = (int)Math.pow(10, 9) + 9;
    private int a;
    private int b;

    /**
     * Initializes a new HashTable storing the given Graph.Nodes
     * @param nodes - an array of Graph.Nodes to store in the table
     */
    public HashTable(Graph.Node[] nodes) {
        // create hash table
        this.table = new DLList[nodes.length * 2];
        for (int i = 0; i < this.table.length; i++) {
            this.table[i] = new DLList();
        }

        // generate hash function
        this.a = (int)(Math.random() * this.p-2 + 1);
        this.b = (int)(Math.random() * this.p-1);

        // insert Graph.Nodes
        for (int i = 0; i < nodes.length; i++) {
            this.insert(nodes[i]);
        }
    }

    /**
     * Inserts a new Graph.Node to the hashtable
     * @param node - the new node to insert
     */
    private void insert(Graph.Node node) {
        long index = (((long) node.getId() * this.a + this.b) % this.p) % this.table.length;

        this.table[(int) index].add(node);
    }

    /**
     * Returns the node with the specified id from the hashtable
     * @param nodeId - the id of the node to retrieve
     * @return the Graph.Node object
     */
    public Graph.Node find(int nodeId) {
        // retrieve the right linked-list to search in
        long index = (((long) nodeId * this.a + this.b) % this.p) % this.table.length;
        DLList.DLNode curr = this.table[(int)index].getFirst();

        // going over the list to find the node containing the specified Graph.Node
        while (curr != null) {
            if (curr.getGraphNode().getId() == nodeId) {
                return curr.getGraphNode();
            }

            curr = curr.getNext();
        }

        // Only gets to this line if there is no Graph.Node saved with the given id
        return null;
    }

    /**
     * Deletes the Graph.Node with the specified id from the hashtable
     * @param nodeId - id of Graph.Node to delete
     */
    public void delete(int nodeId) {
        // retrieve the right linked-list to search in
        long index = (((long) nodeId * this.a + this.b) % this.p) % this.table.length;
        DLList lst = this.table[(int)index];
        DLList.DLNode curr = lst.getFirst();

        // going over the list to find the right node to delete
        while (curr != null) {
            if (curr.getGraphNode().getId() == nodeId) {
                lst.delete(curr);
                return;
            }

            curr = curr.getNext();
        }
    }
}


/**
 * This class represents a doubly-linked list, where each node contains a Graph.Node object
 */
class DLList{
    protected DLNode head;
    protected int size;

    /**
     * Initializes a new list as an empty list
     */
    public DLList() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Adds a new node to the top of the list, with the given object as its value
     * @param node - the value for the new list's node
     */
    public void add(Graph.Node node) {
        if (this.head == null) {
            this.head = new DLNode(node, null, null);
        }
        else {
            DLNode newNode = new DLNode(node, null, this.head);
            this.head.setPrev(newNode);
            this.head = newNode;
        }

        this.size++;
    }

    /**
     * Deletes the given node from the list
     * @param toDelete - the node to delete
     */
    public void delete(DLNode toDelete) {
        // if deleting the head
        if (this.head == toDelete) {
            this.head = toDelete.getNext();
            // if new head is not null
            if (this.head != null) {
                this.head.setPrev(null);
            }
        }
        else {
            toDelete.getPrev().setNext(toDelete.getNext());

            // if toDelete has a next node
            if (toDelete.hasNext()) {
                toDelete.getNext().setPrev(toDelete.getPrev());
            }
        }

        this.size--;
    }

    /**
     * @return the first node in the list
     */
    public DLNode getFirst() {
        return this.head;
    }


    /**
     * This class represents a node in a linked list, containing a Graph.Node as a value
     */
    public class DLNode {
        private Graph.Node graphNode;
        private DLNode prev;
        private DLNode next;

        /**
         * Initializes a DLNode object with the specified value, prev and next nodes
         * @param graphNode - the value to keep
         * @param prev - the previous node in the list
         * @param next - the next node in the list
         */
        public DLNode(Graph.Node graphNode, DLNode prev, DLNode next) {
            this.graphNode = graphNode;
            this.prev = prev;
            this.next = next;
        }

        /**
         * This method returns the node's value, the Graph.Node it keeps
         * @return the node's value
         */
        public Graph.Node getGraphNode() { return this.graphNode; }

        /**
         * @return the previous node
         */
        public DLNode getPrev() { return this.prev; }

        /**
         * @return the next node
         */
        public DLNode getNext() { return this.next; }

        /**
         * This method sets this node's 'previous' field
         * @param newPrev the new node to preceed this node
         */
        public void setPrev(DLNode newPrev) { this.prev = newPrev; }

        /**
         * This method sets this node's 'next' field
         * @param newNext the new node to follow this node
         */
        public void setNext(DLNode newNext) { this.next = newNext; }

        /**
         * Returns whether the node points to a next one
         * @return true if there is a next node, false otherwise
         */
        public boolean hasNext() { return this.next != null; }
    }
}


/**
 * This class represents a Graph.Node's neighbors list
 */
class NeighborsDLList extends DLList implements Iterable<NeighborsDLList.NeighborNode>{


    /**
     * Returns the first neighbor-node in the list
     */
    @Override
    public NeighborNode getFirst() {
        return (NeighborNode) super.getFirst();
    }

    /**
     * Adds a new neighbor to the list.
     * ! Leaves the node's 'twin' field empty!
     * @param node - the Graph.Node representing our new neighbor (the node's value)
     * @return The newly added neighbor-node
     */
    public NeighborNode addNewNeighbor(Graph.Node node) {
        if (this.head == null) {
            this.head = new NeighborNode(node, null, null, null);
        }
        else {
            NeighborNode newNode = new NeighborNode(node, null, (NeighborNode) this.head, null);
            this.head.setPrev(newNode);
            this.head = newNode;
        }

        this.size++;

        return (NeighborNode) this.head;
    }

    /**
     * @return a list iterator
     */
    @Override
    public Iterator<NeighborNode> iterator() {
        return new NeighborDLListIterator();
    }

    /**
     * This class represent's an iterator for a NeighborDLLList object
     */
    public class NeighborDLListIterator implements Iterator<NeighborNode> {
        private NeighborNode curr = getFirst();
        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public NeighborNode next() {
            NeighborNode res = curr;
            curr = curr.getNext();
            return res;
        }
    }

    /**
     * This class represents a node in a graph's neighbor list.
     * Each object from this class represents an edge between to Graph.Nodes
     */
    protected class NeighborNode extends DLNode{
        private NeighborNode twin;

        /**
         * Initializes a new neighbor-list node with the given parameters
         * @param graphNode - the Graph.Node representing the neighbor
         * @param prev - the previous node
         * @param next - the next node
         * @param twin - a pointer to another neighbor-list node, representing the same edge
         */
        private NeighborNode(Graph.Node graphNode, NeighborNode prev, NeighborNode next, NeighborNode twin) {
            super(graphNode, prev, next);
            this.twin = twin;
        }

        /**
         * @return the next node
         */
        public NeighborNode getNext() { return (NeighborNode) super.getNext(); }

        /**
         * @return the previous node
         */
        public NeighborNode getPrev() { return (NeighborNode) super.getPrev(); }

        /**
         * @return the neighbor-node representing the same edge on the
         * other Graph.Node's neighbor list
         */
        public NeighborNode getTwin() {
            return twin;
        }

        /**
         * Sets the current node's twin - the other neighbor-node representing the smae edge
         * @param twin - the other neighbor-node
         */
        public void setTwin(NeighborNode twin) {
            this.twin = twin;
        }
    }
}
