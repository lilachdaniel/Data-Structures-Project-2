public class HashTable {
    private DLList<HashNode>[] table;
    private final int p = (int)Math.pow(10, 9) + 9;
    private int a;
    private int b;

    public HashTable(Graph.Node[] heap) {
        // create hash table
        this.table = new DLList[heap.length * 2];
        for (int i = 0; i < this.table.length; i++) {
            this.table[i] = new DLList<HashNode>();
        }

        // generate hash function
        this.a = (int)(Math.random() * this.p-2 + 1);
        this.b = (int)(Math.random() * this.p-1);

        // insert info
        for (int i = 0; i < heap.length; i++) {
            this.insert(i, heap[i]);
        }
    }

    private void insert(int heapIndex, Graph.Node node) {
        long index = (((long) node.getId() * this.a + this.b) % this.p) % this.table.length;

        HashNode newNode = new HashNode(heapIndex, node);

        this.table[(int) index].add(newNode);

    }

    public HashNode find(int nodeId) {
        long index = (((long) nodeId * this.a + this.b) % this.p) % this.table.length;

        DLList<HashNode>.DLNode<HashNode> curr = this.table[(int)index].getFirst();

        while (curr != null) {
            if (curr.getValue().getNode().getId() == nodeId) {
                return curr.getValue();
            }

            curr = curr.getNext();
        }

        return null;
    }

    public void delete(int nodeId) {
        long index = (((long) nodeId * this.a + this.b) % this.p) % this.table.length;

        DLList<HashNode> lst = this.table[(int)index];
        DLList<HashNode>.DLNode<HashNode> curr = lst.getFirst();

        while (curr != null) {
            if (curr.getValue().getNode().getId() == nodeId) {
                lst.delete(curr);
                return;
            }

            curr = curr.getNext();
        }
    }

    public class HashNode {
        private int heapIndex;
        private Graph.Node node;

        public HashNode(int heapIndex, Graph.Node node) {
            this.heapIndex = heapIndex;
            this.node = node;
        }

        public int getHeapIndex() { return this.heapIndex; }
        public Graph.Node getNode() { return this.node; }
    }
}
