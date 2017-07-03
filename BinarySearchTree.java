
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * 
 * 
 * @param <E> - generic type of comparable object
 */
public class BinarySearchTree<E extends Comparable> {
    private Node root;
    private int size;
    private static final String EXIT = "";

    //node subclass for holding data and node object references.
    private class Node{
        E data;
        Node left;
        Node right;
        
        private Node(E e){
            this.data = e;
            left = null;
            right = null;
        }
        
    }
    
    //constructor: root is null, size is 0
    public BinarySearchTree()
    {
        root = null;
        size = 0;
    }
    
    //adds an item of type E to the tree
    public void insert(E e)
    {
        Node ptr;
        Node added;
        
        //if we haven't items in the tree
        if(size == 0)
        {
            root = new Node(e);
            //System.out.println("The root is now: "+e);
        }
        //if there are already items in the tree
        else
        {
            ptr = root;
            added = new Node(e);
            while(true)//this loop traverses down the tree until a leaf node is found, going left or right as necessary
            {
                //if we need to go left..
                if(added.data.compareTo(ptr.data) < 1)
                {
                    if(ptr.left == null){ptr.left = added;break;}//but the left child is null, make the new node the left child of the pointer, then break out of the loop
                    else ptr = ptr.left;
                }
                else
                {
                    //same as if branch, just going right instead
                    if(ptr.right == null){ptr.right = added;break;}
                    else ptr = ptr.right;
                }
            }
        }
        
        //update the size
        size++;
        
    }
    
    //public method face
    public boolean has(E e)
    {
        return has(e,root);
    }  
    
    //recursive helper function
    private boolean has(E e, Node n){
        
        if(n == null)return false;
        else
        {
            if(e.compareTo(n.data)<0)return has(e,n.left);//go down the left branch
            else if(e.compareTo(n.data)>0)return has(e,n.right);//go down right branch
            else return true;//we have found the item
        }
    }
    
    //go left until we cant anymore
    public E findMin()
    {
        Node ptr = root;
        while(ptr.left != null)
        {
            ptr = ptr.left;
        }
        return ptr.data;
    }
    
    //go right until we cant anymore
    public E findMax()
    {
        Node ptr = root;
        while(ptr.right != null)
        {
            ptr = ptr.right;
        }
        return ptr.data;
    }    
    
    
    //prints the tree going as far left as possible, then printing the parent, then right child
    public void printTree()
    {
        printTree(root);
        System.out.println(size);
    }
    
    //recursive helper method
    private void printTree(Node n)
    {
        if(n.left != null)
            printTree(n.left);
        System.out.print(n.data+" ");
        if(n.right != null)
            printTree(n.right);
    }
    
    public int getSize()
    {
        return size;
    }
   
     
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        BinarySearchTree<String> tree = new BinarySearchTree<>();
        ArrayList<String> words = new ArrayList<>();
        File myFile;
        String fileName;
        Scanner fileScan;
        Scanner userInput;
        userInput = new Scanner(System.in);
        System.out.print("Please enter the name of a .lex file: ");
        fileName = userInput.nextLine();
        if(!fileName.endsWith(".lex"))fileName = fileName+".lex";
        myFile = new File(fileName);
        fileScan = null;
        try {
            fileScan = new Scanner(myFile);
        } catch (FileNotFoundException ex) {
            System.out.println("Could not find file \""+fileName+"\". Please make sure file is in the same directory as the program and your filename ends in .lex");
            System.exit(-1);
        }
        System.out.print("In order to exit the program, please press enter\n");
        //this loop adds all the words from the .lex file
        while(fileScan.hasNextLine())
        {
            String word = fileScan.nextLine();
            words.add(word);
        }
        fileScan.close();
        //System.out.println(words.toString());
        Collections.shuffle(words);//shuffle the words
        //System.out.println(words.toString());
        for(String s : words)
        {
            tree.insert(s);
        }
        String query;
        System.out.println("The tree contains "+tree.getSize()+" items. The first item is \""+tree.findMin()+"\", and the last item is \""+tree.findMax()+"\".");
        while(true){
            query = userInput.nextLine();
            if(query.equals(EXIT))break;
            if(tree.has(query))System.out.println("\""+query+"\" is a valid item within the tree.");
            else System.out.println("\""+query+"\" is not a valid item within the tree.");
        }
        userInput.close();
        
    }

}
