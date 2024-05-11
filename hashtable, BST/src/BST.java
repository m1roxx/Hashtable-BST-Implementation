import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.NoSuchElementException;

public class BST<K extends Comparable<K>, V> implements Iterable<BST.Entry<K, V>> {
    private Node root;
    private int size;

    private class Node {
        private K key;
        private V value;
        private Node left, right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public void put(K key, V value) {
        if (root == null) {
            root = new Node(key, value);
            size++;
            return;
        }

        Node current = root;
        while (true) {
            int cmp = key.compareTo(current.key);
            if (cmp < 0) {
                if (current.left == null) {
                    current.left = new Node(key, value);
                    size++;
                    return;
                }
                current = current.left;
            } else if (cmp > 0) {
                if (current.right == null) {
                    current.right = new Node(key, value);
                    size++;
                    return;
                }
                current = current.right;
            } else {
                current.value = value;
                return;
            }
        }
    }

    public V get(K key) {
        Node current = root;
        while (current != null) {
            int cmp = key.compareTo(current.key);
            if (cmp < 0) {
                current = current.left;
            } else if (cmp > 0) {
                current = current.right;
            } else {
                return current.value;
            }
        }
        return null;
    }

    public void delete(K key) {
        Node parent = null;
        Node current = root;

        while (current != null) {
            int cmp = key.compareTo(current.key);
            if (cmp < 0) {
                parent = current;
                current = current.left;
            } else if (cmp > 0) {
                parent = current;
                current = current.right;
            } else {
                if (current.left == null && current.right == null) {
                    if (parent == null) {
                        root = null;
                    } else if (current == parent.left) {
                        parent.left = null;
                    } else {
                        parent.right = null;
                    }
                } else if (current.left == null) {
                    if (parent == null) {
                        root = current.right;
                    } else if (current == parent.left) {
                        parent.left = current.right;
                    } else {
                        parent.right = current.right;
                    }
                } else if (current.right == null) {
                    if (parent == null) {
                        root = current.left;
                    } else if (current == parent.left) {
                        parent.left = current.left;
                    } else {
                        parent.right = current.left;
                    }
                } else {
                    Node successor = findSuccessor(current);
                    if (current == root) {
                        root = successor;
                    } else if (current == parent.left) {
                        parent.left = successor;
                    } else {
                        parent.right = successor;
                    }
                    successor.left = current.left;
                }
                size--;
                return;
            }
        }
    }

    private Node findSuccessor(Node node) {
        Node successorParent = node;
        Node successor = node;
        Node current = node.right;
        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.left;
        }

        if (successor != node.right) {
            successorParent.left = successor.right;
            successor.right = node.right;
        }
        return successor;
    }

    public Iterator<Entry<K, V>> iterator() {
        return new BSTIterator();
    }

    private class BSTIterator implements Iterator<Entry<K, V>> {
        private Queue<Entry<K, V>> queue;

        public BSTIterator() {
            queue = new LinkedList<>();
            inorder(root);
        }

        private void inorder(Node x) {
            if (x == null) return;
            LinkedList<Node> stack = new LinkedList<>();
            Node current = x;
            while (current != null || !stack.isEmpty()) {
                while (current != null) {
                    stack.push(current);
                    current = current.left;
                }
                current = stack.pop();
                queue.add(new Entry<>(current.key, current.value));
                current = current.right;
            }
        }

        public boolean hasNext() {
            return !queue.isEmpty();
        }

        public Entry<K, V> next() {
            if (!hasNext()) throw new NoSuchElementException();
            return queue.poll();
        }
    }

    public int size() {
        return size;
    }

    public static class Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}