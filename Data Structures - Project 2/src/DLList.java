public class DLList<T> {
    private DLNode head;
    private int size;

    public DLList() {
        this.head = null;
        this.size = 0;
    }

    public int getSize() { return this.size; }

    public void add(T val) {
        if (this.head == null) {
            this.head = new DLNode(val, null, null);
        }
        else {
            DLNode newNode = new DLNode(val, null, this.head);
            this.head.prev = newNode;
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
        private T value;
        private DLNode prev;
        private DLNode next;

        public DLNode(T val, DLNode prev, DLNode next) {
            this.value = val;
            this.prev = prev;
            this.next = next;
        }

        public T getValue() { return this.value; }
        public DLNode getPrev() { return this.prev; }
        public DLNode getNext() { return this.next; }

        public void setPrev(DLNode newPrev) { this.prev = newPrev; }
        public void setNext(DLNode newNext) { this.next = newNext; }
        public boolean hasNext() { return this.next != null; }
        public boolean hasPrev() { return this.prev != null; }

    }
}
