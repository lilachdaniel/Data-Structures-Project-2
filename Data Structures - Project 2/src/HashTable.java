public class HashTable {
    private DLList[] table;
    private final int p = (int)Math.pow(10, 9) + 9;
    private int a;
    private int b;

    public HashTable(Graph.Node[] heap) {
        // create hash table
        this.table = new DLList[heap.length * 2];
        for (int i = 0; i < this.table.length; i++) {
            this.table[i] = new DLList();
        }

        // generate hash function
        this.a = (int)(Math.random() * this.p-2 + 1);
        this.b = (int)(Math.random() * this.p-1);

        // insert info
        for (int i = 0; i < heap.length; i++) {
            this.insert(heap[i]);
        }
    }

    //
    private void insert(Graph.Node node) {
        long index = (((long) node.getId() * this.a + this.b) % this.p) % this.table.length;

        this.table[(int) index].add(node);

    }

    public Graph.Node find(int nodeId) {
        long index = (((long) nodeId * this.a + this.b) % this.p) % this.table.length;

        DLList.DLNode curr = this.table[(int)index].getFirst();

        while (curr != null) {
            if (curr.getGraphNode().getId() == nodeId) {
                return curr.getGraphNode();
            }

            curr = curr.getNext();
        }

        return null;
    }

    public void delete(int nodeId) {
        long index = (((long) nodeId * this.a + this.b) % this.p) % this.table.length;

        DLList lst = this.table[(int)index];
        DLList.DLNode curr = lst.getFirst();

        while (curr != null) {
            if (curr.getGraphNode().getId() == nodeId) {
                lst.delete(curr);
                return;
            }

            curr = curr.getNext();
        }
    }

}
