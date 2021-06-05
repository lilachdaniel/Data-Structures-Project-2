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
    private HashTable table;
    private MaxHeap heap;

    /**
     * Initializes the graph on a given set of nodes. The created graph is empty, i.e. it has no edges.
     * You may assume that the ids of distinct nodes are distinct.
     *
     * @param nodes - an array of node objects
     */
    public Graph(Node [] nodes){
        this.heap = new MaxHeap(nodes);
        this.table = new HashTable(this.heap.getHeapArray());
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
        return this.table.find(node_id).getNode().getNeighborhoodWeight();
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
        // update the nodes' neighbor lists
        HashTable.HashNode hnode1 = this.table.find(node1_id);
        Node.NeighborListNode newNeighbor1 = hnode1.getNode().addNewNeighbor(node2_id);

        HashTable.HashNode hnode2 = this.table.find(node2_id);
        Node.NeighborListNode newNeighbor2 = hnode2.getNode().addNewNeighbor(node1_id);

        newNeighbor1.setTwin(newNeighbor2);
        newNeighbor2.setTwin(newNeighbor1);

        // update each node's neighborhood weight
        int weight1 = hnode1.getNode().getWeight();
        int weight2 = hnode2.getNode().getWeight();

        this.heap.increaseKey(hnode1.getHeapIndex(), weight2);
        this.heap.increaseKey(hnode2.getHeapIndex(), weight1);

        return false;
    }

    /**
     * Given the id of a node in the graph, deletes the node of that id from the graph, if it exists.
     *
     * @param node_id - the id of the node to delete.
     * @return returns 'true' if the function deleted a node, otherwise returns 'false'
     */
    public boolean deleteNode(int node_id){
        //TODO: implement this method.
        return false;
    }

    // ************* for tests only *****************
    public Node createNode(int id, int weight) {
        return new Node(id, weight);
    }

    /**
     * This class represents a node in the graph.
     */
    public class Node{
        private int id;
        private int weight;
        private int nWeight;
        private DLList<NeighborListNode> nList;

        /**
         * Creates a new node object, given its id and its weight.
         * @param id - the id of the node.
         * @param weight - the weight of the node.
         */
        public Node(int id, int weight){
            this.id = id;
            this.weight = weight;
            this.nWeight = weight;
            this.nList = new DLList<NeighborListNode>();
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

        // DO NOT FORGET: this method leaves the new neighbor without a twin
        private NeighborListNode addNewNeighbor(int neighborId) {
            NeighborListNode newNode = new NeighborListNode(neighborId);
            this.nList.add(newNode);
            return newNode;
        }

        private class NeighborListNode {
            private int neighborId;
            private NeighborListNode twin;

            public NeighborListNode(int id) {
                this.neighborId = id;
            }

            public int getNeighborId() { return this.neighborId; }
            public NeighborListNode getTwin() { return this.twin; }
            public void setTwin(NeighborListNode twin) { this.twin = twin; }
        }
    }
}


