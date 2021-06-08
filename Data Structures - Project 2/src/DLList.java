import java.util.Iterator;

public class DLList{
    protected DLNode head;
    protected int size;

    public DLList() {
        this.head = null;
        this.size = 0;
    }

    public int getSize() { return this.size; }

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
            if (toDelete.hasNext())
            {
                toDelete.getNext().setPrev(toDelete.getPrev());
            }
        }

        this.size--;
    }

    public DLNode getFirst() {
        return this.head;
    }


    public class DLNode {
        private Graph.Node graphNode;
        private DLNode prev;
        private DLNode next;

        public DLNode(Graph.Node graphNode, DLNode prev, DLNode next) {
            this.graphNode = graphNode;
            this.prev = prev;
            this.next = next;
        }

        public Graph.Node getGraphNode() { return this.graphNode; }
        public DLNode getPrev() { return this.prev; }
        public DLNode getNext() { return this.next; }

        public void setPrev(DLNode newPrev) { this.prev = newPrev; }
        public void setNext(DLNode newNext) { this.next = newNext; }

        public boolean hasNext() { return this.next != null; }
        public boolean hasPrev() { return this.prev != null; }

    }
}

class NeighborsDLList extends DLList implements Iterable<NeighborsDLList.NeighborNode>{
    @Override
    public NeighborNode getFirst() {
        return (NeighborNode) super.getFirst();
    }

    // DO NOT FORGET: this method leaves the new neighbor without a twin
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

    @Override
    public Iterator<NeighborNode> iterator() {
        return new NeighborDLListIterator();
    }

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


    protected class NeighborNode extends DLNode{
        private NeighborNode twin;

        private NeighborNode(Graph.Node graphNode, NeighborNode prev, NeighborNode next, NeighborNode twin) {
            super(graphNode, prev, next);
            this.twin = twin;
        }

        public NeighborNode getNext() { return (NeighborNode) super.getNext(); }
        public NeighborNode getPrev() { return (NeighborNode) super.getPrev(); }

        public NeighborNode getTwin() {
            return twin;
        }
        public void setTwin(NeighborNode twin) {
            this.twin = twin;
        }
    }
}
