// AvlTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x (unimplemented)
// boolean contains( x )  --> Return true if x is present
// boolean remove( x )    --> Return true if x was present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements an AVL tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class AVLTree<AnyType extends Comparable<? super AnyType>>
{
    /**
     * Construct the tree.
     */
    public AVLTree( )
    {
        root = null;
    }

    /**
     * @param toInsert the item to insert.
     */
    public void insert( AnyType toInsert )//helper function
    {
        root = insert( toInsert, root );
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param toRemove the item to remove.
     */
    public void remove( AnyType toRemove )//helper function
    {
        root = remove( toRemove, root );
    }


    /**
     * Internal method to remove from a subtree.
     * @param toRemove the item to remove.
     * @param node the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode<AnyType> remove( AnyType toRemove, AvlNode<AnyType> node )
    {
        if( node == null )
            return node;   // Item not found; do nothing

        int compareResult = toRemove.compareTo( node.element );

        if( compareResult < 0 )//if to left go to left basically
            node.left = remove( toRemove, node.left );
        else if( compareResult > 0 )
            node.right = remove( toRemove, node.right );
        else if( node.left != null && node.right != null ) // Two children
        {
            node.element = findMin( node.right ).element;//when we remove a node with both children, we bring of the lowest on the right side and it goes where the node we deleted was
            node.right = remove( node.element, node.right );
        }
        else
            node = ( node.left != null ) ? node.left : node.right;
        return balance( node );
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public AnyType findMin( )//helper
    {
        if( isEmpty( ) )
            throw new RuntimeException( );
        return findMin( root ).element;
    }

    public  AnyType  deleteMin( ){//helper
        if(root == null) {return null;}
        else if(root.left != null) {
            //balance(root);
            return deleteMin(root).element;
        }
        else{
            AvlNode<AnyType> tempNode = root;
            root = root.right;
            tempNode.right = null;
            //balance(root);
            return tempNode.element;
        }
     }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public AnyType findMax( )
    {
        if( isEmpty( ) )
            throw new RuntimeException( );
        return findMax( root ).element;
    }

    /**
     * Find an item in the tree.
     * @param searchFor the item to search for.
     * @return true if x is found.
     */
    public boolean contains( AnyType searchFor )
    {
        return contains( searchFor, root );
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( )
    {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree( String label)
    {
        System.out.println(label);
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printTree( root,"" );
    }

    private static final int ALLOWED_IMBALANCE = 1;

    // Assume t is either balanced or within one of being balanced
    private AvlNode<AnyType> balance( AvlNode<AnyType> node )
    {
        if( node == null )
            return node;

        if( height( node.left ) - height(node.right ) > ALLOWED_IMBALANCE )
            if( height( node.left.left ) >= height( node.left.right ) )
                node = rightRotation( node );
            else
                node = doubleRightRotation( node );//zig zag
        else
        if( height( node.right ) - height( node.left ) > ALLOWED_IMBALANCE )
            if( height( node.right.right ) >= height( node.right.left ) )
                node = leftRotation( node );
            else
                node = doubleLeftRotation( node );

        node.height = Math.max( height( node.left ), height( node.right ) ) + 1;
        return node;
    }

    public void checkBalance( )
    {
        checkBalance( root );
    }

    private int checkBalance( AvlNode<AnyType> node )
    {
        if( node == null )
            return -1;

        if( node != null )
        {
            int hl = checkBalance( node.left );
            int hr = checkBalance( node.right );
            if( Math.abs( height( node.left ) - height( node.right ) ) > 1 ||
                    height( node.left ) != hl || height(node.right ) != hr )
                System.out.println( "\n\n***********************OOPS!!" );
        }

        return height( node );
    }


    /**
     * Internal method to insert into a subtree.  Duplicates are allowed
     * @param toInsert the item to insert.
     * @param node the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode<AnyType> insert( AnyType toInsert, AvlNode<AnyType> node )
    {
        if(node == null )
            return new AvlNode<>( toInsert, null, null );

        int compareResult = toInsert.compareTo(node.element );

        if( compareResult < 0 )
            node.left = insert( toInsert, node.left );
        else
            node.right = insert( toInsert, node.right );

        return balance( node );
    }



    private class Tuple<E>{//stole this tuple class from my recursion assignment
        AnyType deletedValue;
        AvlNode<E> roott;
        Tuple(AnyType deletedValue, AvlNode<E> node) {
            this.deletedValue = deletedValue;
            this.roott = node;
        }
    }
    /**
     * Internal method to find the smallest item in a subtree.
     * @param node the node that roots the tree.
     * @return node containing the smallest item.
     */

    private AvlNode<AnyType> findMin( AvlNode<AnyType> node)
    {
        if( node == null )
            return node;

        while( node.left != null )
            node = node.left;
        return node;
    }

    private AvlNode<AnyType> deleteMin( AvlNode<AnyType> node )//might want this to return the new root and the deleted value
    {//idea. Replace this node with the inorder so we don't lose any info. If we are only deleting the min, we only need to check that we don't lose the parents right childi
        if(node.left.left == null) {
            AvlNode<AnyType> tempnode = node.left;
            node.left = node.left.right;
            tempnode.right = null;
            //balance(root);
            return tempnode;

        }

        return deleteMin(node.left);
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param node the node that roots the tree.
     * @return node containing the largest item.
     */
    private AvlNode<AnyType> findMax( AvlNode<AnyType> node )
    {
        if( node == null )
            return node;

        while( node.right != null )
            node = node.right;
        return node;
    }

    /**
     * Internal method to find an item in a subtree.
     * @param toContain is item to search for.
     * @param node the node that roots the tree.
     * @return true if x is found in subtree.
     */
    private boolean contains( AnyType toContain, AvlNode<AnyType> node )
    {
        while( node != null )
        {
            int compareResult = toContain.compareTo( node.element );

            if( compareResult < 0 )
                node = node.left;
            else if( compareResult > 0 )
                node = node.right;
            else
                return true;    // Match
        }

        return false;   // No match
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param node the node that roots the tree.
     */
    private void printTree( AvlNode<AnyType> node, String indent )
    {
        if( node != null )
        {
            printTree( node.right, indent+"   " );
            System.out.println( indent+ node.element + "("+ node.height  +")" );
            printTree( node.left, indent+"   " );
        }
    }

    /**
     * Return the height of node t, or -1, if null.
     */
    private int height( AvlNode<AnyType> node )
    {   if (node==null) return -1;
        return node.height;
    }

    /**
     * Rotate binary tree node with left child.
     * For AVL trees, this is a single rotation for case 1.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> rightRotation(AvlNode<AnyType> node )
    {
        AvlNode<AnyType> theLeft = node.left;
        node.left = theLeft.right;
        theLeft.right = node;
        node.height = Math.max( height( node.left ), height( node.right ) ) + 1;
        theLeft.height = Math.max( height( theLeft.left ), node.height ) + 1;
        return theLeft;
    }

    /**
     * Rotate binary tree node with right child.
     * For AVL trees, this is a single rotation for case 4.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> leftRotation(AvlNode<AnyType> node )
    {
        AvlNode<AnyType> theRight = node.right;
        node.right = theRight.left;
        theRight.left = node;
        node.height = Math.max( height( node.left ), height( node.right ) ) + 1;
        theRight.height = Math.max( height( theRight.right ), node.height ) + 1;
        return theRight;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child.
     * For AVL trees, this is a double rotation for case 2.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> doubleRightRotation( AvlNode<AnyType> node )
    {
        node.left = leftRotation( node.left );
        return rightRotation( node );

    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child.
     * For AVL trees, this is a double rotation for case 3.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> doubleLeftRotation(AvlNode<AnyType> node )
    {
        node.right = rightRotation( node.right );
        return leftRotation( node );
    }

    private static class AvlNode<AnyType>
    {
        // Constructors
        AvlNode( AnyType theElement )
        {
            this( theElement, null, null );
        }

        AvlNode( AnyType theElement, AvlNode<AnyType> lt, AvlNode<AnyType> rt )
        {
            element  = theElement;
            left     = lt;
            right    = rt;
            height   = 0;
        }

        AnyType           element;      // The data in the node
        AvlNode<AnyType>  left;         // Left child
        AvlNode<AnyType>  right;        // Right child
        int               height;       // Height
    }

    /** The tree root. */
    private AvlNode<AnyType> root;


    // Test program
    public static void main( String [ ] args ) {
        AVLTree<Integer> t = new AVLTree<>();
        AVLTree<Dwarf> t2 = new AVLTree<>();

        String[] nameList = {"Snowflake", "Sneezy", "Doc", "Grumpy", "Bashful", "Dopey", "Happy", "Doc", "Grumpy", "Bashful", "Doc", "Grumpy", "Bashful"};
        for (int i=0; i < nameList.length; i++)
            t2.insert(new Dwarf(nameList[i]));

        t2.printTree( "The Tree" );

        t2.remove(new Dwarf("Bashful"));

        t2.printTree( "The Tree after delete Bashful" );
        for (int i=0; i < 8; i++) {
            t2.deleteMin();
            t2.printTree( "\n\n The Tree after deleteMin" );
        }
    }

}
