public class DLList<K> {
    private DLNode<K> head;
    private int size;

    public DLList() {
        this.head = null;
        this.size = 0;
    }

    public int getSize() { return this.size; }

    public void add(K val) {
        if (this.head == null) {
            this.head = new DLNode<K>(val, null, null);
        }
        else {
            DLNode<K> newNode = new DLNode<K>(val, null, this.head);
            this.head.prev = newNode;
            this.head = newNode;
        }

        this.size++;
    }

    public void delete(DLNode<K> toDelete) {
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

    public DLNode<K> getFirst() {
        return this.head;
    }

    public class DLNode<T> {
        private T value;
        private DLNode<T> prev;
        private DLNode<T> next;

        public DLNode(T val, DLNode<T> prev, DLNode<T> next) {
            this.value = val;
            this.prev = prev;
            this.next = next;
        }

        public T getValue() { return this.value; }
        public DLNode<T> getPrev() { return this.prev; }
        public DLNode<T> getNext() { return this.next; }

        public void setPrev(DLNode<T> newPrev) { this.prev = newPrev; }
        public void setNext(DLNode<T> newNext) { this.next = newNext; }
        public boolean hasNext() { return this.next != null; }
        public boolean hasPrev() { return this.prev != null; }

    }
}
