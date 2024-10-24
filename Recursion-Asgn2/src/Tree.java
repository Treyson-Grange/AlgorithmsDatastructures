

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Tree<E extends Comparable<? super E>> {
    private BinaryNode<E> root;  // Root of tree
    private String treeName;     // Name of tree

    /**
     * Create an empty tree
     *
     * @param label Name of tree
     */
    public Tree(String label) {
        treeName = label;
        root = null;
    }


    /**
     * Create non ordered tree from list in preorder
     *
     * @param arr   List of elements
     * @param label Name of tree
     */
    public Tree(E[] arr, String label, boolean ordered) {
        treeName = label;
        if (ordered) {
            root = null;
            for (int i = 0; i < arr.length; i++) {
                bstInsert(arr[i]);
            }
        } else root = buildUnordered(arr, 0, arr.length - 1);
    }

    /**
     * Build a NON BST tree by preorder
     *
     * @param arr nodes to be added
     * @return new tree
     */
    private BinaryNode<E> buildUnordered(E[] arr, int low, int high) {
        if (low > high) return null;
        int mid = (low + high) / 2;
        BinaryNode<E> curr = new BinaryNode<>(arr[mid], null, null);
        curr.left = buildUnordered(arr, low, mid - 1);
        curr.right = buildUnordered(arr, mid + 1, high);
        return curr;
    }

    /**
     * Create BST from Array
     *
     * @param arr   List of elements to be added
     * @param label Name of  tree
     */
    public Tree(E[] arr, String label) {
        root = null;
        treeName = label;
        for (int i = 0; i < arr.length; i++) {
            bstInsert(arr[i]);
        }
    }

    /**
     * Change name of tree
     *
     * @param name new name of tree
     */
    public void changeName(String name) {
        this.treeName = name;
    }

    /**
     * Return a string displaying the tree contents as a tree with one node per line
     */
    public String toStringM() {
        if (root == null)
            return (treeName + " \nEmpty tree");
        else
            //return treeName + "\n" + toString2(root);

            return treeName+"\n" + toString(root,"");
    }

    private String toString(BinaryNode<E> t, String indent) {//O(N)
        StringBuilder sb = new StringBuilder();

        if(t != null) {
            sb.append(toString(t.right,indent +  " "));
            sb.append(indent + t.element + "\n");
            sb.append(toString(t.left,indent +  " "));
        }
        return sb.toString();

    }

    /**
     * Return a string displaying the tree contents as a single line
     */
    public String toString2() {
        if (root == null)
            return treeName + " Empty tree";
        else
            return treeName + " " + toString2(root);
    }

    /**
     * Internal method to return a string of items in the tree in order
     * This routine runs in O(??)
     *
     * @param t the node that roots the subtree.
     */
    private String toString2(BinaryNode<E> t) {
        if (t == null) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(toString2(t.left));
        sb.append(t.element.toString() + " ");
        sb.append(toString2(t.right));
        return sb.toString();
    }



    private static class Tuple<E>{//decided to create a tuple class in order to return more info.
        int depth;
        BinaryNode<E> node;
        Tuple(int depth, BinaryNode<E> node) {
            this.depth = depth;
            this.node = node;
        }
    }


    public BinaryNode<E> deepestNode() {
        return deepestNode(root,0).node;
    }
    public Tuple<E> deepestNode(BinaryNode<E> t,int level) {//this function is of complexity O(n)
        Tuple bestNode = new Tuple<>(level,t);
        if(t != null) {
            Tuple left = deepestNode(t.left, level + 1);
            Tuple right = deepestNode(t.right, level + 1);
            if(left.depth >= right.depth && left.node != null) {
                return left;
            }
            if(right.depth > left.depth && right.node != null) {
                return right;
            }
            if(left.node == null && right.node != null) {
                return right;
            }
            if(left.node != null && right.node == null) {
                return left;
            }

        }
        return bestNode;
    }
    /**
     * reverse left and right children recursively
     */

    public void flip() {
        if(root == null) {
            return;
        }
        else {
            flip(root);
        }

    }
    public void flip(Tree.BinaryNode<E> t) {
        if(t == null) {// Complexity is O(N)
            return;
        }
        if(t.right == null && t.left == null) {
            return;
        }
        if(t.left != null && t.right != null) {
            BinaryNode<E> holder = t.right;
            t.right = t.left;
            t.left = holder;
            flip(t.left);
            flip(t.right);

        }
        if(t.left != null && t.right == null) {
            t.right = t.left;
            t.left = null;
            flip(t.right);
        }
        if(t.right != null && t.left == null) {
            t.left = t.right;
            t.right = null;
            flip(t.left);
        }
    }

    /**
     * Counts number of nodes in specified level
     *
     * @param level Level in tree, root is zero
     * @return count of number of nodes at specified level
     */
    public int nodesInLevel(int level) {
        if(root != null) {
            return nodesInLevel(root, level, 0,0);
        }
        return 0;
    }
    private int nodesInLevel(BinaryNode t, int level, int currentLevel, int total) {//complexity = O(N)
        if(t != null) {
            if(currentLevel == level) {
                total += 1;
                return total;
            }
            if(t.right!=null) {
                if(t.left!= null) {
                    return total + nodesInLevel(t.left,level,currentLevel + 1,total) + nodesInLevel(t.right,level,currentLevel + 1,total);
                }
                return total + nodesInLevel(t.right,level,currentLevel + 1 ,total);
            }
            if(t.left != null) {return total + nodesInLevel(t.left,level,currentLevel + 1, total);}
            return total;

        }
        return 0;
    }

    /**
     * Print all paths from root to leaves
     */
    public void printAllPaths() {
        if(root != null) {
            ArrayList list = new ArrayList();
            printAllPaths(root,list,root.element);
        }

    }
    private void printAllPaths(BinaryNode t, ArrayList path, Object root) {//complexity is o(n)
        if(t != null) {
            path.add(t.element);
            if(t.left == null && t.right == null) {//check for LEAF node
                for(Object number: path) {
                    System.out.print(number + " ");
                }
                System.out.println();
                path.remove(t.element);//remove leaf from list and tree
                //return from call to go to next
                return;
            }
            else {
                printAllPaths(t.right, path,root);
                printAllPaths(t.left, path,root);
            }
            path.remove(t.element);
        }
        return;
    }


    /**
     * Counts all non-null binary search trees embedded in tree
     *
     * @return Count of embedded binary search trees
     */
    public Integer countBST() {
        int zero1 = 0;
        if (root == null) {return 0;}
        Integer zero = zero1;
        return countBST(zero,root);
    }
    private Integer countBST(Integer numberOfSub, BinaryNode<E> t) {//complexity = 0(n)
        numberOfSub = 0;
        if(root != null) {
            if(t.right == null && t.left == null) {//if there is a leaf node,it is automatically a BST
               numberOfSub += 1;
            }

            if(t.right != null && t.left != null) {
                if(t.right.element.compareTo(t.element) > 0 && t.left.element.compareTo(t.element) < 0) {numberOfSub += 1;}
            }
            else if(t.right == null && t.left != null) {
                if(t.left.element.compareTo(t.element) < 0) {numberOfSub += 1;}
            }
            else if(t.left == null && t.right != null) {
                if(t.right.element.compareTo(t.element) > 0) {numberOfSub += 1;}
            }
            //if both not null and if we compare and left < t < right then we add one to the number of Sub variable
            if(t.left != null) {
                 numberOfSub += countBST(numberOfSub,t.left);
            }//go to left
            if(t.right != null) {
                numberOfSub += countBST(numberOfSub,t.right);
            }


        }
        return numberOfSub;
    }

    /**
     * Insert into a bst tree; duplicates are allowed
     *
     * @param x the item to insert.
     */
    public void bstInsert(E x) {

        root = bstInsert(x, root);
    }

    /**
     * Internal method to insert into a subtree.
     * In tree is balanced, this routine runs in O(log n)
     *
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<E> bstInsert(E x, BinaryNode<E> t) {
        if (t == null)
            return new BinaryNode<E>(x, null, null);
        int compareResult = x.compareTo(t.element);
        if (compareResult < 0) {
            t.left = bstInsert(x, t.left);
        } else {
            t.right = bstInsert(x, t.right);
        }
        return t;
    }

    /**
     * Determines if item is in tree
     *
     * @param item the item to search for.
     * @return true if found.
     */
    public boolean contains(E item) {
        return contains(item, root);
    }

    /**
     * Internal method to find an item in a subtree.
     * This routine runs in O(log n) as there is only one recursive call that is executed and the work
     * associated with a single call is independent of the size of the tree: a=1, b=2, k=0
     *
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     * @return node containing the matched item.
     */
    private boolean contains(E x, BinaryNode<E> t) {
        if (t == null)
            return false;

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            return contains(x, t.left);
        else if (compareResult > 0)
            return contains(x, t.right);
        else {
            return true;    // Match
        }
    }

    /**
     * Remove all paths from tree that sum to less than given value
     *
     * @param sum: minimum path sum allowed in final tree
     */
    public void pruneKStarter(Integer sum) {
        if(root!= null) {
            pruneK(sum,root);
            if(root.left == null && root.right == null) {
                root = null;
            }
        }
    }


    public BinaryNode pruneK(Integer sum, BinaryNode t) {//complexity is o(n)
        if(t == null) {
            return null;
        }
        String left = t.element.toString();
        int newSum = Integer.parseInt(left);
        t.left = pruneK(sum - newSum,t.left);
        String right = t.element.toString();
        int newSum2 = Integer.parseInt(right);
        t.right = pruneK(sum - newSum2,t.right);

        if(t.left == null && t.right == null) {
            String elementToString = t.element.toString();
            int elementToInt = Integer.parseInt(elementToString);
            if (sum > elementToInt) {
                t = null;
            }
        }
        return t;
    }

    /**
     * Build tree given inOrder and preOrder traversals.  Each value is unique
     *
     * @param inOrder  List of tree nodes in inorder
     * @param preOrder List of tree nodes in preorder
     */
    public void buildTreeTraversals(E[] inOrder, E[] preOrder) {
        root = null;
    }

    /**
     * Find the least common ancestor of two nodes
     *
     * @param a first node
     * @param b second node
     * @return String representation of ancestor
     */
    public String lca(E a, E b) {
        if (root != null) {
            return lca(a,b,root);
        }
        return "No Tree!";

    }
    public String lca(E a, E b, BinaryNode<E> t) {//idea: if we find that both nodes are on the left side, we go to the left, vice verse for right, but if there is a seperation, we return where we are, giving us LCA!
        //complexity is O(n)
        if(t != null) {
            if(t.element.compareTo(a) > 0 && t.element.compareTo(b) > 0) {
                return lca(a,b,t.left);
            }
            if(t.element.compareTo(a) < 0 && t.element.compareTo(b) < 0) {
                return lca(a,b,t.right);
            }
            return t.element.toString();
        }
        return "No!";
    }
    /**
     * Balance the tree
     */

    public void balanceTree() {
        if (root == null) {
            return;
        }

        ArrayList<BinaryNode<E>> listOfAllNodes = turnTreeIntoList(root,new ArrayList<BinaryNode<E>>());
        balanceTree(listOfAllNodes,0,listOfAllNodes.size() - 1);
    }

    public ArrayList<BinaryNode<E>> turnTreeIntoList(BinaryNode<E> t,ArrayList<BinaryNode<E>> listOfNodes) {

        if(t != null) {
            turnTreeIntoList(t.left,listOfNodes);
            listOfNodes.add(t);
            turnTreeIntoList(t.right,listOfNodes);
        }
        return listOfNodes;
    }

    private BinaryNode<E> balanceTree(ArrayList<BinaryNode<E>> listOfNodes, int beginningIndex, int endingIndex) {//of complexity o(n)
        if(beginningIndex > endingIndex) {
            return null;
        }
        int middleIndex = (beginningIndex + endingIndex) / 2;
        BinaryNode<E> child = listOfNodes.get(middleIndex);
        child.left = balanceTree(listOfNodes,beginningIndex,middleIndex - 1);
        child.right = balanceTree(listOfNodes,middleIndex + 1,endingIndex);
        return child;

    }

//    /**
//     * In a BST, keep only nodes between range
//     *
//     * @param a lowest value
//     * @param b highest value
//     */
    public int turnElementToInt(Object t) {
        String stringHolder = t.toString();
        return Integer.parseInt(stringHolder);
    }
    public void keepRange(E a, E b) {
        if(root!=null) {
            keepRange(a,b,root);
        }
    }

    public BinaryNode<E> keepRange(E a, E b, BinaryNode<E> t) {//complexity O(n)
        if(t == null) {
            return null;
        }
        t.left = keepRange(a, b, t.left);
        t.right = keepRange(a, b, t.right);
        if(turnElementToInt(t.element) < turnElementToInt(a)) {
            BinaryNode holder = t.right;
            t = null;
            return holder;
        }
        if(turnElementToInt(t.element) > turnElementToInt(b)) {
            BinaryNode holder = t.left;
            t = null;
            return holder;
        }
        return t;
    }

    // Basic node stored in unbalanced binary  trees
    private static class BinaryNode<E> {
        E element;            // The data in the node
        BinaryNode<E> left;   // Left child
        BinaryNode<E> right;  // Right child

        // Constructors
        BinaryNode(E theElement) {
            this(theElement, null, null);
        }

        BinaryNode(E theElement, BinaryNode<E> lt, BinaryNode<E> rt) {
            element = theElement;
            left = lt;
            right = rt;
        }

        // toString for BinaryNode
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Node:");
            sb.append(element);
            return sb.toString();
        }

    }

}
