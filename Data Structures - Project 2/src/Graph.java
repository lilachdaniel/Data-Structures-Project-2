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
        return this.table.find(node_id).getNeighborhoodWeight();
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
        //check if nodes exist in the graph
        if (this.table.find(node1_id) == null ||  this.table.find(node2_id) == null) {
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

        for (NeighborsDLList.NeighborNode nnode : node.getNList()) {  // nnode     = representation of edge in *my* list

            NeighborsDLList.NeighborNode twinNnode = nnode.getTwin(); // twinNnode = representation of edge in *his* list

            Node twin = nnode.getGraphNode();                         // twin      = Graph.Node object of neighbor

            // delete "me" from my neighbor's list
            twin.getNList().delete(twinNnode);

            this.numEdges--;

            // decrease neighbor's key
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

    // ************* for tests only *****************
//    public Node createNode(int id, int weight) {
//        return new Node(id, weight);
//    }

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
        public int getId(){
            return this.id;
        }

        /**
         * Returns the weight of the node.
         * @return the weight of the node.
         */
        public int getWeight(){
            return this.weight;
        }

        public int getNeighborhoodWeight() {
            return this.nWeight;
        }

        public void increaseNeighborhoodWeight(int diff) {
            this.nWeight += diff;
        }

        public void decreaseNeighborhoodWeight(int diff) {
            this.nWeight -= diff;
        }

        public int getHeapIndex() {  return this.heapIndex;  }

        public void setHeapIndex(int heapIndex) {  this.heapIndex = heapIndex;  }

        public NeighborsDLList getNList() {    return this.nList;    }



    }
}


