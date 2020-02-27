package HomeWork1;

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

class BSTSort {
	//to create our tree
    public static Node insertIter(Node root, int data){
          Node myNode = new Node(data);
          
          if(root == null) {
        	  return myNode;
          }
          
          Node temp = null;
          Node current = root;

          while(current != null){
            temp = current;
            if(current.data <= data){
                current = current.right;
            }
            else {
            	current = current.left;
            }
          }
          if(temp.data <= data){
              temp.right = myNode;
          }else{
              temp.left = myNode;
          }
          
          return temp;
   }
    //sort calls a function inOrder which puts the tree in order from low to high
   public static void sort(Node root){
	   inOrderHelp(root); 
   }
   //inOrder Traversal
   public static void inOrderHelp(Node node){
	   if(node != null) {
		   inOrderHelp(node.left);
		   System.out.print(node.data + " ");
		   inOrderHelp(node.right);
	   }
   }
   
   public static void main(String[] args) {
       Scanner scan = new Scanner(System.in);
       //we create a binary search tree and call sort
       Node root = null;
       root = insertIter(root,7);
       insertIter(root,8);
       insertIter(root,3);
       insertIter(root,4);
       insertIter(root,5);
       insertIter(root,10);
       sort(root);
   }
       
 }

