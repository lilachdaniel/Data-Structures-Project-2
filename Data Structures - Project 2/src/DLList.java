// import java.util.Iterator;
//
///**
// * This class represents a doubly-linked list, where each node contains a Graph.Node object
// */
//public class DLList{
//    protected DLNode head;
//    protected int size;
//
//    /**
//     * Initializes a new list as an empty list
//     */
//    public DLList() {
//        this.head = null;
//        this.size = 0;
//    }
//
//    /**
//     * Adds a new node to the top of the list, with the given object as its value
//     * @param node - the value for the new list's node
//     */
//    public void add(Graph.Node node) {
//        if (this.head == null) {
//            this.head = new DLNode(node, null, null);
//        }
//        else {
//            DLNode newNode = new DLNode(node, null, this.head);
//            this.head.setPrev(newNode);
//            this.head = newNode;
//        }
//
//        this.size++;
//    }
//
//    /**
//     * Deletes the given node from the list
//     * @param toDelete - the node to delete
//     */
//    public void delete(DLNode toDelete) {
//        // if deleting the head
//        if (this.head == toDelete) {
//            this.head = toDelete.getNext();
//            // if new head is not null
//            if (this.head != null) {
//                this.head.setPrev(null);
//            }
//        }
//        else {
//            toDelete.getPrev().setNext(toDelete.getNext());
//
//            // if toDelete has a next node
//            if (toDelete.hasNext()) {
//                toDelete.getNext().setPrev(toDelete.getPrev());
//            }
//        }
//
//        this.size--;
//    }
//
//    /**
//     * @return the first node in the list
//     */
//    public DLNode getFirst() {
//        return this.head;
//    }
//
//
//    /**
//     * This class represents a node in a linked list, containing a Graph.Node as a value
//     */
//    public class DLNode {
//        private Graph.Node graphNode;
//        private DLNode prev;
//        private DLNode next;
//
//        /**
//         * Initializes a DLNode object with the specified value, prev and next nodes
//         * @param graphNode - the value to keep
//         * @param prev - the previous node in the list
//         * @param next - the next node in the list
//         */
//        public DLNode(Graph.Node graphNode, DLNode prev, DLNode next) {
//            this.graphNode = graphNode;
//            this.prev = prev;
//            this.next = next;
//        }
//
//        /**
//         * This method returns the node's value, the Graph.Node it keeps
//         * @return the node's value
//         */
//        public Graph.Node getGraphNode() { return this.graphNode; }
//
//        /**
//         * @return the previous node
//         */
//        public DLNode getPrev() { return this.prev; }
//
//        /**
//         * @return the next node
//         */
//        public DLNode getNext() { return this.next; }
//
//        /**
//         * This method sets this node's 'previous' field
//         * @param newPrev the new node to preceed this node
//         */
//        public void setPrev(DLNode newPrev) { this.prev = newPrev; }
//
//        /**
//         * This method sets this node's 'next' field
//         * @param newNext the new node to follow this node
//         */
//        public void setNext(DLNode newNext) { this.next = newNext; }
//
//        /**
//         * Returns whether the node points to a next one
//         * @return true if there is a next node, false otherwise
//         */
//        public boolean hasNext() { return this.next != null; }
//    }
//}
//
///**
// * This class represents a Graph.Node's neighbors list
// */
//class NeighborsDLList extends DLList implements Iterable<NeighborsDLList.NeighborNode>{
//
//
//    /**
//     * Returns the first neighbor-node in the list
//     */
//    @Override
//    public NeighborNode getFirst() {
//        return (NeighborNode) super.getFirst();
//    }
//
//    /**
//     * Adds a new neighbor to the list.
//     * ! Leaves the node's 'twin' field empty!
//     * @param node - the Graph.Node representing our new neighbor (the node's value)
//     * @return The newly added neighbor-node
//     */
//    public NeighborNode addNewNeighbor(Graph.Node node) {
//        if (this.head == null) {
//            this.head = new NeighborNode(node, null, null, null);
//        }
//        else {
//            NeighborNode newNode = new NeighborNode(node, null, (NeighborNode) this.head, null);
//            this.head.setPrev(newNode);
//            this.head = newNode;
//        }
//
//        this.size++;
//
//        return (NeighborNode) this.head;
//    }
//
//    /**
//     * @return a list iterator
//     */
//    @Override
//    public Iterator<NeighborNode> iterator() {
//        return new NeighborDLListIterator();
//    }
//
//    /**
//     * This class represent's an iterator for a NeighborDLLList object
//     */
//    public class NeighborDLListIterator implements Iterator<NeighborNode> {
//        private NeighborNode curr = getFirst();
//        @Override
//        public boolean hasNext() {
//            return curr != null;
//        }
//
//        @Override
//        public NeighborNode next() {
//            NeighborNode res = curr;
//            curr = curr.getNext();
//            return res;
//        }
//    }
//
//    /**
//     * This class represents a node in a graph's neighbor list.
//     * Each object from this class represents an edge between to Graph.Nodes
//     */
//    protected class NeighborNode extends DLNode{
//        private NeighborNode twin;
//
//        /**
//         * Initializes a new neighbor-list node with the given parameters
//         * @param graphNode - the Graph.Node representing the neighbor
//         * @param prev - the previous node
//         * @param next - the next node
//         * @param twin - a pointer to another neighbor-list node, representing the same edge
//         */
//        private NeighborNode(Graph.Node graphNode, NeighborNode prev, NeighborNode next, NeighborNode twin) {
//            super(graphNode, prev, next);
//            this.twin = twin;
//        }
//
//        /**
//         * @return the next node
//         */
//        public NeighborNode getNext() { return (NeighborNode) super.getNext(); }
//
//        /**
//         * @return the previous node
//         */
//        public NeighborNode getPrev() { return (NeighborNode) super.getPrev(); }
//
//        /**
//         * @return the neighbor-node representing the same edge on the
//         * other Graph.Node's neighbor list
//         */
//        public NeighborNode getTwin() {
//            return twin;
//        }
//
//        /**
//         * Sets the current node's twin - the other neighbor-node representing the smae edge
//         * @param twin - the other neighbor-node
//         */
//        public void setTwin(NeighborNode twin) {
//            this.twin = twin;
//        }
//    }
//}
