//public class HashTable {
//    private DLList[] table;
//    private final int p = (int)Math.pow(10, 9) + 9;
//    private int a;
//    private int b;
//
//    /**
//     * Initializes a new HashTable storing the given Graph.Nodes
//     * @param nodes - an array of Graph.Nodes to store in the table
//     */
//    public HashTable(Graph.Node[] nodes) {
//        // create hash table
//        this.table = new DLList[nodes.length * 2];
//        for (int i = 0; i < this.table.length; i++) {
//            this.table[i] = new DLList();
//        }
//
//        // generate hash function
//        this.a = (int)(Math.random() * this.p-2 + 1);
//        this.b = (int)(Math.random() * this.p-1);
//
//        // insert Graph.Nodes
//        for (int i = 0; i < nodes.length; i++) {
//            this.insert(nodes[i]);
//        }
//    }
//
//    /**
//     * Inserts a new Graph.Node to the hashtable
//     * @param node - the new node to insert
//     */
//    private void insert(Graph.Node node) {
//        long index = (((long) node.getId() * this.a + this.b) % this.p) % this.table.length;
//
//        this.table[(int) index].add(node);
//    }
//
//    /**
//     * Returns the node with the specified id from the hashtable
//     * @param nodeId - the id of the node to retrieve
//     * @return the Graph.Node object
//     */
//    public Graph.Node find(int nodeId) {
//        // retrieve the right linked-list to search in
//        long index = (((long) nodeId * this.a + this.b) % this.p) % this.table.length;
//        DLList.DLNode curr = this.table[(int)index].getFirst();
//
//        // going over the list to find the node containing the specified Graph.Node
//        while (curr != null) {
//            if (curr.getGraphNode().getId() == nodeId) {
//                return curr.getGraphNode();
//            }
//
//            curr = curr.getNext();
//        }
//
//        // Only gets to this line if there is no Graph.Node saved with the given id
//        return null;
//    }
//
//    /**
//     * Deletes the Graph.Node with the specified id from the hashtable
//     * @param nodeId - id of Graph.Node to delete
//     */
//    public void delete(int nodeId) {
//        // retrieve the right linked-list to search in
//        long index = (((long) nodeId * this.a + this.b) % this.p) % this.table.length;
//        DLList lst = this.table[(int)index];
//        DLList.DLNode curr = lst.getFirst();
//
//        // going over the list to find the right node to delete
//        while (curr != null) {
//            if (curr.getGraphNode().getId() == nodeId) {
//                lst.delete(curr);
//                return;
//            }
//
//            curr = curr.getNext();
//        }
//    }
//}