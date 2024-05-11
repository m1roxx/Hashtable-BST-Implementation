public class BSTMain {
    public static void main(String[] args) {
        BST<Integer, String> tree = new BST<>();

        tree.put(1, "One");
        tree.put(2, "Two");
        tree.put(3, "Three");
        tree.put(4, "Four");

        for (BST.Entry<Integer, String> entry : tree) {
            System.out.println("key is " + entry.getKey() + " and value is " + entry.getValue());
        }
    }
}
