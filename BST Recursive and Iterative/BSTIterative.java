import java.util.*;

import Project_1.Node;

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

public class BSTIterative {
 
    public static Node insertIter(Node root, int data){
    	 Node myNode = new Node(data);
         //start at root 
         if(root == null) {
       	  return myNode;
         }
         
         Node temp = null;
         Node current = root;
         //go left and right till we hit a null pointer 
         while(current != null){
           temp = current;
           //Find out where the node will be placed on right or left subtree
           if(current.data <= data){
               current = current.right;
           }
           else {
           	current = current.left;
           }
         }
         //steps to attach a node based on 
         // if it > than current or < current
         if(temp.data <= data){
             temp.right = myNode;
         }else{
             temp.left = myNode;
         }
         
         return temp;
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
        	Node toReplace = findNextIter(root, current.data);
            //find the parent of toReplace
            Node parentOfreplc = null;
            Node start = root;
            while(start.data != toReplace.data){
              parentOfreplc = start;
              if(start.data < toReplace.data){
                start = start.right;
              }else {
                start = start.left;
              }
            }
            current.data = toReplace.data;
            //check position of to replace
            //then check if it has a right child
              if(toReplace.right != null){
                //replace toReplace with its child and attach it with its parent
                if(parentOfreplc.right == toReplace){
                  parentOfreplc.right = toReplace.right;
                }else{
                  parentOfreplc.left = toReplace.right;
                }
              }
            
            //otherwise delete pointer
            toReplace = null;
             
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
	//go till we find previous node
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
        while(rootNode.data != current.data){
          if(current.data > rootNode.data){
            move = rootNode;
            rootNode = rootNode.right;
          }
          else if(current.data < rootNode.data){
              rootNode = rootNode.left;
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
     public static void inOrderHelp(Node node){
  	   if(node != null) {
  		   inOrderHelp(node.left);
  		   System.out.print(node.data + " ");
  		   inOrderHelp(node.right);
  	   }
     }
 
     public static void main(String[] args) {
    	 /*
         Scanner scan = new Scanner(System.in);
         Node root = null;
         root = insertIter(root,7);
         insertIter(root,8);
         insertIter(root,8);
         insertIter(root,3);
         insertIter(root,4);
         insertIter(root,5);
         insertIter(root,10);
         insertIter(root,9);
         insertIter(root,2); 
         insertIter(root,15);
         insertIter(root,17);
         insertIter(root,20);
         */
         /*
         System.out.println(findMaxIter(root).data);
         System.out.println(findMinIter(root).data);
         
         System.out.println(findNextIter(root,5).data);
         System.out.println(findPrevIter(root,20).data + " ");
         
         deleteIter(root,8); 
         inOrderHelp(root);
         System.out.println();
         deleteIter(root,3);
         inOrderHelp(root);
         System.out.println();
         deleteIter(root,10);
         inOrderHelp(root);
         */
         
     }
}
