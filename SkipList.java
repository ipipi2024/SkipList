import java.util.Random;

public class SkipList {
    private Node head;
    private Node tail;

    private final int NEG_INFINITY = Integer.MIN_VALUE;
    private final int POS_INFINITY = Integer.MAX_VALUE;

    private int height = 0;

    public Random random = new Random();

    public SkipList() {
        head = new Node(NEG_INFINITY);
        tail = new Node(POS_INFINITY);
        head.next = tail;
        tail.prev = head;
    }

    public Node search(int key) {
        Node current = head;
        
        // Traverse down through levels
        while (current.below != null) {
            // Move right as far as possible on current level
            while (current.next.key <= key) {
                current = current.next;
            }
            // Move down to next level
            current = current.below;
        }
        
        // At the bottom level, continue moving right until we find the insertion point
        while (current.next != null && current.next.key <= key) {
            current = current.next;
        }
        
        return current;
    }

    public void insert(int key) {
        // find the 0th level position of the Node with key less inserting key
        Node prevNode = search(key);

        // if the key is already present
        if (prevNode.key == key) {
            return;
        }

        // create a newNode and adjust refrences
        Node newNode = new Node(key);
        newNode.next = prevNode.next;
        newNode.prev = prevNode;
        prevNode.next = newNode;
        newNode.next.prev = newNode;

        // determine if next top level node should be created
        // create another node on the next level
        Node currentNode = newNode;
        while (random.nextBoolean() == true) {

            // return the topnode to be used in the next top node creation
            Node topNode = createTopNode(currentNode);

            // set current node to be the new top node
            currentNode = topNode;

        }
    }

    // method to create another node on the top level
    public Node createTopNode(Node currentNode) {
        // create new Node
        Node topNode = new Node(currentNode.key);
        topNode.below = currentNode;
        currentNode.above = topNode;

        // adjust the references for the new level node
        Node currentNode1 = currentNode;
        Node currentNode2 = currentNode;

        // adjust left pointers
        while (currentNode1 != null && currentNode1.prev != null) {
            if (currentNode1.prev == head && currentNode1.prev.above == null) {
                // increase height by adjusting head node
                Node newHead = new Node(NEG_INFINITY);
                newHead.below = head;
                head.above = newHead;
                head = newHead;
                topNode.prev = head;
                head.next = topNode;
                break;
            }
            if (currentNode1.prev.above != null) {
                topNode.prev = currentNode.prev.above;
                currentNode1.prev.above.next = topNode;
                break;
            }

            currentNode1 = currentNode1.prev;
        }

        while (currentNode2 != null  && currentNode2.next != null) {
            if (currentNode2.next == tail && currentNode2.next.above == null) {
                // increase height by adjusting tail node
                Node newTail = new Node(POS_INFINITY);
                newTail.below = tail;
                tail.above = newTail;
                tail = newTail;
                topNode.next = tail;
                tail.prev = topNode;
                break;
            }
            if (currentNode2.next.above != null) {
                topNode.next = currentNode2.next.above;
                currentNode2.next.above.prev = topNode;
                break;
            }
            currentNode2 = currentNode2.next;
        }

        return topNode;

    }

    public boolean delete(int key) {
        Node nodeToDelete = search(key);

        if (nodeToDelete.key != key) {
            System.out.println("Cannot delete. Node does not exist!");
            return false;
        }
        
            Node prevNode = nodeToDelete.prev;
            Node nextNode = nodeToDelete.next;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;

        while (nodeToDelete.above != null) {
            nodeToDelete = nodeToDelete.above;
            prevNode = nodeToDelete.prev;
            nextNode = nodeToDelete.next;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
            nodeToDelete.below = null;
        }
        

        return true;
    }

    public void printSkipList() {
        // Find the bottom-most head node
        Node currentNode = head;
        while (currentNode.below != null) {
            currentNode = currentNode.below;
        }
        
        // Traverse horizontally and print all keys
        while (currentNode != null) {
            System.out.print(currentNode.key + " ");
            currentNode = currentNode.next;
        }
        System.out.println(); // Add a newline at the end
    }
}
