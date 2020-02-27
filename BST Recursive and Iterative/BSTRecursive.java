package Project1;
import java.util.*;


class Node{
	int data;
	Node left;
	Node right;
	Node(int data) {		
		this.data =	data;
		this.left = null;
		this.right = null;
		
    }   
}
public class BSTRecursive{
     
  public static Node insertRec(Node root, int data){
     Node myNode = new Node(data);
      
      if(root == null) {
        return myNode;
      }  
      //insert nodes left and right based on heiarchy
      else if(root.data > data){
        root.left = insertRec(root.left, data);
      }
      else if(root.data <= data){
        root.right = insertRec(root.right, data);
      }
      return root;
   }

     public static Node deleteRec(Node root, int delete){
      if(root == null) {
        return root;
      }   
      //move the pointers to the node we want to delete
      if(root.data > delete){
        root.left = deleteRec(root.left, delete);
      }
      if(root.data < delete){
        root.right = deleteRec(root.right, delete);
      }
      else{
        //If the node has only one child and
        //we need to delete
        if(root.left == null){
           Node toDelete = root;
           root = root.right;
           toDelete = null;
           return root;
        }
        else if(root.right == null){
           Node toDelete = root;
           root = root.left;
           toDelete = null;
           return root;
        }
        //if we the node to delete has two children
        else if(root.data < delete){
          //get the in order successor and replace it
          Node successor = findNextRec(root, null, delete);
          root.data = successor.data;
          root.right = deleteRec(root.right,delete);
        }
        else {
        	Node predecessor = findPrevRec(root, null, delete);
            root.data = predecessor.data;
            root.left = deleteRec(root.left,delete);
        }
      }
      return root;
     }
     
     public static Node findNextRec(Node rootNode, Node next, int value){
      if(rootNode == null){
        return null;
      }
      //check if value matches
      if(rootNode.data == value){
        // we have a right subtree find the minimum of it
        if(rootNode.right != null){
          return findMinRec(rootNode.right);
        }
        //otherwise return our next or successor 
        else{
          return next;
        }
      }
      //recursive calls to traverse tree
      else if(rootNode.data > value){
        return findNextRec(rootNode.left, rootNode, value);
      }
      else{
        return findNextRec(rootNode.right, rootNode, value);
      }
     }

     public static Node findPrevRec(Node rootNode, Node previous ,int value){
        if(rootNode == null){
          return null;
        }
        
        if(rootNode.data == value){
          //find the maximum value of left tree if it exists and that will be our previous
          if(rootNode.left != null){
            return findMaxRec(rootNode.left);
          }
          else{
            //return the previous node 
            return previous;
          }
        }
        //recursive calls to traverse tree and find data
        else if(rootNode.data > value){
          return findPrevRec(rootNode.left, rootNode, value);
        }
        else{
          return findPrevRec(rootNode.right, rootNode, value);
        }
     }
     //The leftmost node will be the minimum unless root is empty
     public static Node findMinRec(Node rootNode){
        if(rootNode == null){
          return null;
        }
        //go till we find the left most node
        if(rootNode.left != null){
          rootNode = findMinRec(rootNode.left);
        }
        return rootNode;
     }
     //The rightmost node will be the maximum unless root is empty
     public static Node findMaxRec(Node rootNode){
      if(rootNode == null){
        return null;
      }
      //go till we find our right most Node
      if(rootNode.right != null){
        rootNode = findMaxRec(rootNode.right);
      }
      return rootNode;
   }
   public static void inOrderHelp(Node node){
    	   if(node != null) {
    		   inOrderHelp(node.left);
    		   System.out.print(node.data + " ");
    		   inOrderHelp(node.right);
    	   }
    }
 /*    
     public static void main(String[] args) {
         Scanner scan = new Scanner(System.in);
         Node root = null;
         root = insertRec(root,7);
         insertRec(root,8);
         insertRec(root,3);
         insertRec(root,4);
         insertRec(root,8);
         insertRec(root,5);
         insertRec(root,10);
         insertRec(root,9);
         insertRec(root,2); 
         insertRec(root,15);
         insertRec(root,17);
         insertRec(root,20);
         
         System.out.println(findMaxRec(root).data);
         System.out.println(findMinRec(root).data);
         
         System.out.println(findNextRec(root,null,10).data);
         System.out.println(findPrevRec(root,null,8).data);
         
         deleteRec(root,20);
         inOrderHelp(root);
         System.out.println();
         System.out.println();
         deleteRec(root,10);
         inOrderHelp(root);
         System.out.println();
         deleteRec(root,8);
         inOrderHelp(root);
         deleteRec(root,4);
         inOrderHelp(root);
         System.out.println();
     }
*/
	  
}
