import java.nio.BufferUnderflowException;
import java.util.TooManyListenersException;

public class MinHeapLeftistPQ<E extends Comparable<? super E>> {
    Node<E> root;

    public boolean isEmpty() {
        return root == null;
    }

    public MinHeapLeftistPQ() {
        root = null;
    }

    public void insert(E toInsert) {
        root = merge(new Node<>(toInsert), root);
    }

    private Node<E> merge(Node<E> firstRoot, Node<E> secondRoot) {
        if (firstRoot == null) {
            return secondRoot;
        }
        if (secondRoot == null) {
            return firstRoot;
        }
        if (firstRoot.element.compareTo(secondRoot.element) < 0) {
            return actualMerge(firstRoot, secondRoot);
        } else {
            return actualMerge(secondRoot, firstRoot);
        }
    }

    private Node<E> actualMerge(Node<E> firstNode, Node<E> secondNode) {

        firstNode.right = merge(firstNode.right, secondNode);
        if (getNullPathLenght(firstNode.left) < getNullPathLenght(firstNode.right)) {
            swapChildren(firstNode);
        }
        firstNode.nullPathLength = getNullPathLenght(firstNode.right) + 1;

        return firstNode;
    }

    private int getNullPathLenght(Node<E> node) {
        if (node == null) {
            return -1;
        }
        return node.nullPathLength;
    }

    private void swapChildren(Node<E> node) {
        Node<E> temp = node.left;
        node.left = node.right;
        node.right = temp;
    }

    public Node<E> deleteMin() {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        Node<E> toReturn = root;
        root = merge(root.left, root.right);
        return toReturn;
    }

    public static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
        int nullPathLength;

        Node(E element) {
            this(element, null, null);
        }

        Node(E element, Node<E> left, Node<E> right) {
            this.element = element;
            this.left = left;
            this.right = right;
            this.nullPathLength = 0;
        }

        //private Node<E> root;

    }

    public static void main(String[] args) {

        MinHeapLeftistPQ<Integer> leftist = new MinHeapLeftistPQ<>();
        for (int i = 0; i <= 20; i++) {
            leftist.insert(i);
        }
        MinHeapLeftistPQ<String> leftistString = new MinHeapLeftistPQ<>();
        for (int i = 0; i <= 20; i++) {
            leftistString.insert(i + "");
        }
        System.out.println(leftist.deleteMin().element);
        System.out.println(leftistString.deleteMin().element);
        System.out.println(leftist.deleteMin().element);
        System.out.println(leftistString.deleteMin().element);
        leftist.insert(0);
        System.out.print(leftist.root.element);
    }
}
