public class Main {
    public static void main(String[] args) {
        SkipList skipList = new SkipList();
        skipList.insert(3);
        skipList.insert(5);
        skipList.insert(1);
        skipList.insert(7);
        skipList.insert(14);
        skipList.insert(2);
        
        System.out.println("AFter insert:");
        skipList.printSkipList();
        skipList.delete(3);
        
        System.out.println("After delete: ");
        skipList.printSkipList();
    }
}
