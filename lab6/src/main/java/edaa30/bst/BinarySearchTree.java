package main.java.edaa30.bst;

import java.util.*;


public final class BinarySearchTree<E> {
    public static void main(String[] args) {
        var bst = new BinarySearchTree<Integer>();
        var vis = new BSTVisualizer("MyTree", 640, 480);
        vis.drawTree(bst);

        var sc = new Scanner(System.in);

        int i;
        while(true) {
            try {
                i = sc.nextInt();
                bst.add(i);

                bst.rebuild();
                vis.drawTree(bst);
            }
            catch (IllegalFormatException e) {
                continue;
            }
        }
    }

    protected BinaryNode<E> m_root = null;  // Används också i BSTVisaulizer
    private int m_size = 0;            // Används också i BSTVisaulizer

    private Comparator<E> m_comparator;

    /**
     * Constructs an empty binary search tree.
     */
    public BinarySearchTree() {
        this((lhs, rhs) -> ((Comparable<E>)lhs).compareTo(rhs));
    }

    /**
     * Constructs an empty binary search tree, sorted according to the specified comparator.
     */
    public BinarySearchTree(Comparator<E> comparator) {
        m_comparator = comparator;
    }

    /**
     * Inserts the specified element in the tree if no duplicate exists.
     *
     * @param x element to be inserted
     * @return true if the the element was inserted
     */
    public boolean add(E x) {
        if(x == null) return false;

        if(m_root == null) {
            m_root = new BinaryNode<>(x);
            m_size++;
            return true;
        }

		var b = add(x, m_root);
        return b;
    }

    private boolean add(E x, BinaryNode<E> node) {
        var comp = m_comparator.compare(x, node.element);

        if(comp == 0) return false;

        if(comp > 0) {
            if(node.right == null) {
                node.right = new BinaryNode<>(x);
                m_size++;
                return true;
            }

            return add(x, node.right);
        }

        if(node.left == null) {
            node.left = new BinaryNode<>(x);
            m_size++;
            return true;
        }

        return add(x, node.left);
    }

    /**
     * Computes the height of tree.
     *
     * @return the height of the tree
     */
    public int height() {
        if(m_root == null) return 0;
        var l = height(m_root.left, 1);
        var r = height(m_root.right, 1);
        return l > r ? l : r;
    }

    private static <T> int height(BinaryNode<T> n, int height) {
        if(n == null) return height;
        var l = height(n.left, height + 1);
        var r = height(n.right, height + 1);
        return l > r ? l : r;
    }

    /**
     * Returns the number of elements in this tree.
     *
     * @return the number of elements in this tree
     */
    public int size() {
        return m_size;
    }

    /**
     * Removes all of the elements from this list.
     */
    public void clear() {
        m_size = 0;
        m_root = null;
    }

    /**
     * Print tree contents in inorder.
     */
    public void printTree() {
        System.out.print(toString());
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append('[');
        build_string(sb, m_root);
        sb.setLength(sb.length() - 2);
        sb.append(']');

        return sb.toString();
    }

    private static <T> void build_string(StringBuilder sb, BinaryNode<T> n) {
        if(n == null) return;
        build_string(sb, n.left);
        sb.append(n.element.toString() + ", ");
        build_string(sb, n.right);
    }

    /**
     * Builds a complete tree from the elements in the tree.
     */
    public void rebuild() {
        if(m_size < 3) return;

        var array = new ArrayList<E>();
        toArray(m_root, array);

        m_root = balance(array);
    }

    private BinaryNode<E> balance(List<E> a) {
        if(a.size() < 3) {
            if(a.size() == 1) return new BinaryNode<>(a.get(0));
            if(a.size() == 2) {
                var n = new BinaryNode<>(a.get(1));
                n.left = new BinaryNode<>(a.get(0));

                return n;
            }

            throw new RuntimeException("balance called w empty list, should never happen");
        }

        var n = new BinaryNode<>(a.get(a.size() / 2));
        n.left = balance(a.subList(0, a.size() / 2));
        n.right = balance(a.subList(a.size() / 2 + 1, a.size()));

        return n;
    }

    /*
     * Adds all elements from the tree rooted at n in inorder to the list sorted.
     */
    private static <T> void toArray(BinaryNode<T> n, ArrayList<T> sorted) {
        if(n == null) return;
        toArray(n.left, sorted);
        sorted.add(n.element);
        toArray(n.right, sorted);
    }

    static class BinaryNode<E> {
        E element;
        BinaryNode<E> left;
        BinaryNode<E> right;

        private BinaryNode(E element) {
            this.element = element;
        }
    }

}
