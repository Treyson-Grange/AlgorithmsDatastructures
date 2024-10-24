public class LinkedListQueue {
    public static class Node<E> {
        E element;
        Node<E> next;

        public Node(E element) {
            this(element,null);
        }
        public Node(E element, Node<E> n) {
            this.element = element;
            this.next = n;
        }
    }

    public static class LinkedListG<E> {
        private Node<E> head, tail;
        private int size;


        public void enque(E item) {
            if(tail==null) {
                tail = new Node<>(item);
                head = tail;
            }
            else{
                tail.next = new Node<>(item);
                tail = tail.next;
            }

        }


        public void deque() {
            if(head == tail) {
                head = null;
                tail = null;
            }
            else if(head == null && tail != null) {
                head = tail;
            }
            else if(head.next == null) {
                head = null;
            }
            else{
                head = head.next;
            }

        }

        public void printN (E printNValue) {
            Node<E> curr = null;

            for (curr = head; curr != null && !curr.element.equals(printNValue); curr = curr.next) {
                System.out.println(curr.element);
            }
            if (curr==null) {System.out.println("Not found!");}
            else {System.out.println(curr.element + " end");}

        }
        public boolean returnIfEmpty() {
            return head == null;
        }

        public Object getHead() {
            return head.element;
        }
    }


}
