package Project_1;
import java.util.*;

class Node{
	int data, height;
	Node left;
    Node right;	
    Node parent;
	Node(int data) {	
    this.data =	data;
    this.height = 1;
    this.parent = null;
    this.left = null;
	this.right = null;		
    }   
}

public class AVLIterative {
   
    public static Node insertIter(Node root, int data){
   	 Node myNode = new Node(data);
        //start at root 
        if(root == null) {
      	  return myNode;
       }
        
        Node myParent = null;
        Node current = root;
        //go left and right till we hit a null pointer 
        while(current != null){
          myParent = current;
          if(current.data <= data){
              current = current.right;
          }
          else {
          	current = current.left;
          }
        }
        myNode.parent = myParent;
        //steps to attach a node based on 
        // if it > than root or < root
        if(myParent.data <= data){
            myParent.right = myNode;
        }else{
            myParent.left = myNode;
        }
        //System.out.println(myNode.parent.data);

        changeTreeHeight(myNode);
        
        //Node v = myNode.parent;
        //System.out.println(v.data);
        
        Node z = myNode;
        Node x = null;
        Node y = null;
        
        while(z != null ) {
        	if(getBalanceFactor(height(z.left),height(z.right)) <= 1){
                x = y;
                y = z;
                z = z.parent;
              }else{
                break;
              }
        }
        if(z != null) {
        	//System.out.println(z.data);
        	//System.out.println(x.data);
        	//System.out.println(y.data);
            Node blncdNode = balance(z,y,x); 
            //System.out.println(blncdNode.data);
            if(z == root) {
            	root = blncdNode;
            	blncdNode.parent = null;
            }
            else {
            	Node parent = z.parent;
            	Node parentLeft = parent.left;
            	if(parentLeft == z){
            		parent.left = blncdNode;
            	}else{
            		parent.right = blncdNode;
            	}
            }
        }
        return root;
     }


     public static void deleteIter(Node root, int delete){

        Node parent = null;
        Node current = root;
        //move current pointer to one we want to delete
        while(current.data != delete){
          parent = current;
          if(current.data < delete){
            current = current.right;
          }else {
        	  current = current.left;
          }
        }
        //System.out.println(parent.data);
        //3 cases
        //If node we have to delete has no children than just delete it
        if(hasChildren(current).equals("None")){
          current = null;
        }
        
        //If node only has one child
        //Check weather they have left or right child
        if(hasChildren(current).equals("Left")){
        	if(parent.left.data == current.data) {
        		parent.left = current.left;
        	}
            parent.right = current.right;
        }
        //check the left and right of pointer and just attach
        //next node to parent
        if(hasChildren(current).equals("Right")){
        	if(parent.left.data == current.data) {
        		parent.left = current.right;
        	}
            parent.right = current.right;       
        }
		
        //if node has two children
        if(hasChildren(current).equals("Two")){
          //we need to get the in order predecessor
          Node toReplace = findPrevIter(root, current.data);
          current.data = toReplace.data;
          //move the pointer
          current.left = toReplace.left;      
        }
     }
     //check how many children a node has
     public static String hasChildren(Node node){
      if(node.left == null && node.right == null){
        return "None";
      }
      if(node.left != null && node.right == null){
        return "Left";
      }
      if(node.left == null && node.right != null){
        return "Right";
      }
      return "Two";
     }

     public static Node findNextIter(Node rootNode, int value){
    	 Node current = rootNode;
    	 //move to the node that we want to find its next value
         while(current.data != value){
           if(current.data > value){
             current = current.left;
           }
           else if(current.data < value){
             current = current.right;
           }
         }
         Node move = null;

         //if there is right subtree we move to the left
         if(current.right != null){
           move = current.right;
           //then all the way to the left or minimum
           while(move.left != null){
             move = move.left;
           }
           return move;
         }
         
         //if the right subtree doesn't exist go to the ancestor node starting at the root
         while(rootNode.data != current.data){
           if(current.data > rootNode.data){
             rootNode = rootNode.right;
           }
           else if(current.data < rootNode.data){
        	   move = rootNode;
               rootNode = rootNode.left;
           }
         }
         return move;
       
     }

     public static Node findPrevIter(Node rootNode, int value){
        Node current = rootNode;
        while(current.data != value){
          if(current.data > value){
            current = current.left;
          }
          else if(current.data < value){
            current = current.right;
          }
        }
        Node move = null;

        //if there is left subtree we move to the right
        if(current.left != null){
          move = current.left;
          while(move.right != null){
            move = move.right;
          }
          return move;
        }
        //if the left subtree doesn't exist go to the ancestor node starting at the root
        while(rootNode != null){
          if(current.data > rootNode.data){
            move = rootNode;
            rootNode = rootNode.right;
          }
          else if(current.data < rootNode.data){
              rootNode = rootNode.left;
          }
          else{
            break;
          }
        }
        return move;
     }
     //The leftmost node will be the minimum unless root is empty
     public static Node findMinIter(Node rootNode){
        if(rootNode == null){
          return null;
        }
        //go till we find the left most node
        while(rootNode.left != null){
          rootNode = rootNode.left;
        }
        return rootNode;
     }
     //The rightmost node will be the minimum unless root is empty
     public static Node findMaxIter(Node rootNode){
      if(rootNode == null){
        return null;
      }
      //go till we find our right most Node
      while(rootNode.right != null){
        rootNode = rootNode.right;
      }
      return rootNode;
     }
     public static void postOrderHelp(Node node){
  	   if(node != null) {
  		   postOrderHelp(node.left);
  		   postOrderHelp(node.right);
  		   System.out.print(node.data + " ");
  	   }
     }
     public static int getBalanceFactor(int heightOfLft, int heightOfRght){
         return Math.abs(heightOfLft - heightOfRght);
     }
     public static int height(Node node){       
        if(node == null){
          return 0;
        }
        return node.height;
     }
     public static int max(int leftHeight, int rightHeight){
          if(leftHeight > rightHeight){
              return leftHeight;
          }
          return rightHeight;
      }
     public static Node leftRotate(Node root){
      Node y = root.right;
      Node Leaf3 = y.left;
      
      y.left = root;
      root.right = Leaf3;
      
      y.parent = root.parent;
      //root.parent = y;
      //System.out.println(y.data);
      //Node r = root.parent;
      //System.out.println(r.data);
      
      root.height = 1 + max(height(root.left), height(root.right));
      y.height = 1 + max(height(y.left), height(y.right));

      return y;
     }
     public static Node rightRotate(Node root){
      Node y = root.left;
      Node Leaf3 = y.right;
      
      y.right = root;
      root.left = Leaf3;

      y.parent = root.parent;
      root.parent = y;

      root.height = 1 + max(height(root.left), height(root.right));
      y.height = 1 + max(height(y.left), height(y.right));
      
      return y;
     }
     public static Node balance(Node z, Node y, Node x){
       //Left-Left Case 
       //Right rotation
       //start at z
       if(z.left == y && y.left == x){
             z = rightRotate(z);
             changeTreeHeight(z);
             return z;
       }
       //Left-Right Case
       if(z.left == y && y.right == x ){
            z.left = leftRotate(z.left);
            z = rightRotate(z);
            changeTreeHeight(z);
            return z;
       }
       //Right-Right Case
       if(z.right == y && y.right == x){
          z = leftRotate(z);
          changeTreeHeight(z);
          return z;
       }
       //Right-Left Case
       z.right = rightRotate(z.right);
       z = leftRotate(z);
       changeTreeHeight(z);
       return z;
       
     }
     public static void changeTreeHeight(Node startNode){
       while(startNode != null){
         startNode.height = 1 + max(height(startNode.left), height(startNode.right));
         startNode = startNode.parent;
       }
     }
 
     public static void main(String[] args) {
         Scanner scan = new Scanner(System.in);
         Node root = null;
         root = insertIter(root,44);
         postOrderHelp(root);
         System.out.println();
         root = insertIter(root,57);
         postOrderHelp(root);
         System.out.println();
         root = insertIter(root,47);
         postOrderHelp(root);        
         System.out.println();
         //inOrderHelp(root);
         //System.out.println(root.right.data);
         root = insertIter(root,78);
         postOrderHelp(root);
         System.out.println();
         root = insertIter(root,50);
         postOrderHelp(root);
         System.out.println();
         root = insertIter(root,88);
         postOrderHelp(root);
         System.out.println();
         root = insertIter(root,48);
         postOrderHelp(root);
         System.out.println();
         //postOrderHelp(root);
         root = insertIter(root,62);
         postOrderHelp(root);
         System.out.println();
         root = insertIter(root,66);
         postOrderHelp(root);
         System.out.println();
         root = insertIter(root,17);
         //root = insertIter(root,16);
         postOrderHelp(root);
         System.out.println(); 
         //There needs to be fix here
         root = insertIter(root,16);
         postOrderHelp(root);
         System.out.println(); 
         
     }
}
