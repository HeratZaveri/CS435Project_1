package Prjct1;
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

public class AVLTrees {
  //Problem 6 a
  public static int avlCount = 0;
  public static int bstCount = 0;

  public static Node insertRecBST(Node root, int data){
    Node myNode = new Node(data);
     
     if(root == null) {
       return myNode;
     }  
     //insert nodes left and right based on heiarchy
     else if(root.data > data){
       root.left = insertRecBST(root.left, data);
     }
     else if(root.data <= data){
       root.right = insertRecBST(root.right, data);
     }
     return root;
  }
  public static Node insertIterBST(Node root, int data){
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
        if(current.data <= data){
            current = current.right;
        }
        else {
          current = current.left;
        }
        bstCount++;
      }
      //Count2++;
      //steps to attach a node based on 
      // if it > than root or < root
      if(temp.data > data){
          temp.left = myNode;
      }else{
          temp.right = myNode;
      }
      
      return temp;
   }
  public static Node insertIter(Node root, int data){
   	    Node myNode = new Node(data);
        //start at root 
        if(root == null) {
          return myNode;
        }
        Stack<Node> myStack = new Stack<Node>();

        Node myParent = null;
        Node current = root;
        //go left and right till we hit a null pointer 
        while(current != null){
          myParent = current;
          myStack.push(myParent);
          if(current.data <= data){
              current = current.right;
          }
          else {
          	  current = current.left;
          }
          avlCount++;
        }
        // myNode.parent = myParent;
        //System.out.println(myParent.data);
        //steps to attach a node based on 
        // if it > than root or < root
        if(myParent.data <= data){
            myParent.right = myNode;
        }else{
            myParent.left = myNode;
        }
        //inOrderHelp(root);
        //System.out.println(myNode.parent.data);
        //Update height of tree
        //changeTreeHeight(myNode);
      
        while(!(myStack.empty())){
          Node prev = myStack.pop();
          prev.height = 1+max(height(prev.left), height(prev.right));
          // check if node is a subset of {-1,0,1} or not 
          if(!(balanced(prev))){
            //check placement of node see if it is at root
            // than just attach it to the root
             if(prev == root){
             root = balanceTree(root);
             return root;
             }
             //check placement and attach it with root
             if(myStack.peek().left == prev){
              myStack.peek().left = balanceTree(prev);
             }else{
              myStack.peek().right = balanceTree(prev);
             }
           }         
         }

        //return root
        return root;
     }

     public static Node deleteIter(Node root, int delete){
        Stack<Node> myStack = new Stack<Node>();
        Node parent = null;
        Node current = root;
        //move current pointer to one we want to delete
        while(current.data != delete){
          parent = current;
          myStack.push(parent);
          if(current.data < delete){
            current = current.right;
          }else {
        	  current = current.left;
          }
        }
        //Node actionPos;
        //System.out.println(parent.data);
        //3 cases
        //If node we have to delete has no children than just delete it
        if(hasChildren(current).equals("None")){
           //myStack.pop();
           //actionPos = myStack.peek();
           current = null;           
        }
        //If node only has one child
        //Check weather they have left or right child
        if(hasChildren(current).equals("Left")){
          //Pop the current node and its parent
          //myStack.pop();
        	if(parent.left == current) {
        		parent.left = current.left;
        	}
            parent.right = current.left;
        }
        //check the left and right of pointer and just attach
        //next node to parent
        if(hasChildren(current).equals("Right")){
          //myStack.pop();
          //actionPos = myStack.peek();
        	if(parent.left == current) {
        		parent.left = current.right;
        	}
            parent.right = current.right;
            //actionPos.height = 1 + getBalanceFactor(height(actionPos.left), height(actionPos.right));      
        }		
        //if node has two children
        if(hasChildren(current).equals("Two")){
          //myStack.pop();
          //actionPos = myStack.peek();
          //we need to get the in order successor
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
          //actionPos.height = 1 + getBalanceFactor(height(actionPos.left), height(actionPos.right));
        }
        while(!myStack.empty()){
          //parent of deleted node
          Node actionPos = myStack.pop();
          if(!(balanced(actionPos))){
              //check placement of node see if it is at root
              // than just attach it to the root
               if(actionPos == root){
               root = balanceDeletedTree(root);
               return root;
               }
               //check placement and attach it with root
               if(myStack.peek().left == actionPos){
                myStack.peek().left = balanceDeletedTree(actionPos);
               }else{
                myStack.peek().right = balanceDeletedTree(actionPos);
               }
           }
         }
       return root;
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
     //find balance factor
     public static int getBalanceFactor(int heightOfLft, int heightOfRght){
         return heightOfLft - heightOfRght;
     }
     //to get node height
     public static int height(Node node){       
        if(node == null){
          return 0;
        }
        return node.height;
     }
     // find maximum of two height
     public static int max(int leftHeight, int rightHeight){
          if(leftHeight > rightHeight){
              return leftHeight;
          }
          return rightHeight;
      }
     //left rotation from given node
     public static Node leftRotate(Node node){
      
    	  Node y = node.right;
        node.right = y.left;
        y.left = node;
      
      //System.out.println(y.data);
      //Node r = root.parent;
      //System.out.println(r.data);
      
      //update height
      node.height = 1 + max(height(node.left), height(node.right));
      y.height = 1 + max(height(y.left), height(y.right));

      return y;
     }
     //right rotation from given node
     public static Node rightRotate(Node node){
      //starting point for rotate
      Node y = node.left;
      node.left = y.right;
      y.right = node;
      
      //change pointers
      //y.parent = root.parent;
      //root.parent = y;
      
      //update heights
      node.height = 1 + max(height(node.left), height(node.right));
      y.height = 1 + max(height(y.left), height(y.right));
      
      return y;
     }
    
     //update height of tree
     public static void changeTreeHeight(Node startNode){
       while(startNode != null){
         startNode.height = 1 + max(height(startNode.left), height(startNode.right));
         startNode = startNode.parent;
       }
     }
     
     public static Node balanceTree(Node someNode) {
    	   int balanceFactor = getBalanceFactor(height(someNode.left), height(someNode.right));
         
         //left heavy if bf > 0 
         // > 1 left imbalance 
         //left - left case
         //if node is 2 it is left imbalance 
         if(balanceFactor > 1){
           //left - right case
           Node lft = someNode.left;
           int bfLft = getBalanceFactor(height(lft.left),height(lft.right));
           if(bfLft == -1){
               someNode.left = leftRotate(someNode.left);
           }
           //left - left case
           return rightRotate(someNode);
         }
         //right heavy if bf < 0
         // < -1 right imbalance 
         //if node is -2 it is right imbalance 
         else if(balanceFactor < -1){
          //right - left case
          Node rght = someNode.right;
          int bfRght = getBalanceFactor(height(rght.left),height(rght.right));
          if(bfRght == 1){
              someNode.right = rightRotate(someNode.right);
          }
         }
         //right - right case
         return leftRotate(someNode);
     }
     //Different case when we delete from tree
     public static balanceDeletedTree(Node somNode){
      int balanceFactor = getBalanceFactor(height(someNode.left), height(someNode.right));
         
      //R-0 and R1 and R-1 rotate
      //if node is 2 it is left imbalance 
      if(balanceFactor > 1){
        //left - right case
        Node lft = someNode.left;
        int bfLft = getBalanceFactor(height(lft.left),height(lft.right));
        //if R and -1 rotate left
        if(bfLft < 0){
            someNode.left = leftRotate(someNode.left);
        }
        //left - left case
        return rightRotate(someNode);
      }
      // 
      else if(balanceFactor < -1){
       //right - left case
       Node rght = someNode.right;
       int bfRght = getBalanceFactor(height(rght.left),height(rght.right));
       //if L0  rotate and L1
       if(bfRght >= 0){
           someNode.right = rightRotate(someNode.right);
       }
      }
      //left -1 case or right - right
      return leftRotate(someNode);
     }
     //balanced node 
     public static boolean balanced(Node node){
            int bFactor = getBalanceFactor(height(node.left), height(node.right));
            // check if node is a subset of -1,0,1 
            if(bFactor >= -1 && bFactor <= 1){
              return true;
            }
            return false;
     }
     public static Node getTallest(Node someNode) {
    	 if(height(someNode.left) > height(someNode.right)) {
    		 return someNode.left;
    	 }
    	 return someNode.right;
     }
     private static ArrayList<Integer> getSortedArrayHelp(int n){

      ArrayList<Integer> randomArray = new ArrayList<>();
      //run through our values backward from n to 1
      for(int i = n; i > 0; i--){
          randomArray.add(i);
      }

      return randomArray;
     }
     
     private static ArrayList<Integer> getRandomArrayHelp(int n){
         HashSet<Integer> randomArray = new HashSet<>();
         ArrayList<Integer> myArray = new ArrayList<>();
         Random rand = new Random();
         int seen = 0;
         //Loop till we dont have duplicates
         while(seen < n){
             int myNum = rand.nextInt(n*2) + 1;
             //check if array has unique value
             if(!randomArray.contains(myNum)){
                 myArray.add(myNum);
                 seen++;
             }
             randomArray.add(myNum);
         }
         return myArray;
     }
     public static void inOrderHelp(Node node){
        if(node != null){
    		   inOrderHelp(node.left);
    		   System.out.print(node.data + " ");
           inOrderHelp(node.right);
        }
       }
 
     public static void main(String[] args) {
         Scanner scan = new Scanner(System.in);
         //int input = scan.nextInt();
         
         /*
         //Problem 5 and 6b
         ArrayList<Integer> mySet = new ArrayList<>();
         mySet = getRandomArrayHelp(10000);
         
         //Problem 5a, 5c and 6b
         
         Node root = null;
         root = insertIter(root,mySet.get(0));
         for(int i = 1; i < mySet.size(); i++){
             root = insertIter(root, mySet.get(i));
         }

         //postOrderHelp(root);
         //System.out.println();
         //System.out.println();
        
         //Problem 5a
         Node rootRec = null;
         //starting point
         rootRec = insertRecBST(rootRec,mySet.get(0));
         for(int i = 1; i < mySet.size(); i++){
             insertRecBST(rootRec, mySet.get(i));
         }
         //inOrderHelp(rootRec);
         //System.out.println();

         //Problem 5c and 6b
         Node rootIterBst = null;
         rootIterBst = insertIterBST(rootIterBst,mySet.get(0));
         for(int i = 1; i < mySet.size(); i++){
             insertIterBST(rootIterBst, mySet.get(i));
         }
         //System.out.println("In Order");
         //inOrderHelp(rootIterBst);

         //System.out.println();
         System.out.println("----------Problem 6b -----------");
         System.out.println("BST Traversal: " + bstCount);
         System.out.println("AVL Traversal: " + avlCount);
         */
         
         
         //Prolem 6c Sorted Array
         /*
         ArrayList<Integer> mySorted = new ArrayList<>();
         mySorted = getSortedArrayHelp(10000);
         //System.out.println(mySorted);
         
         Node rootSort = null;
         rootSort = insertIter(rootSort,mySorted.get(0));
         for(int i = 1; i < mySorted.size(); i++){
             rootSort = insertIter(rootSort, mySorted.get(i));
         }

         
         Node rootBST = null;
         rootBST = insertIterBST(rootBST,mySorted.get(0));
         for(int i = 1; i < mySorted.size(); i++){
             insertIterBST(rootBST, mySorted.get(i));
         }
         //inOrderHelp(rootBST);
         System.out.println("----------Problem 6c -----------");
         System.out.println("BST Traversal: " + bstCount);
         System.out.println("AVL Traversal: " + avlCount);
         
         //postOrderHelp(rootSort);
         */

         /*
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
         root = insertIter(root,58);
         postOrderHelp(root);
         System.out.println();
         root = insertIter(root,43);
         postOrderHelp(root);
         System.out.println();
         root = insertIter(root,42);
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
         root = insertIter(root,16);
         postOrderHelp(root);
         System.out.println(); 
         //There needs to be fix here
         root = insertIter(root,63);
         postOrderHelp(root);
         System.out.println();
         root = insertIter(root,23);
         postOrderHelp(root);
         System.out.println(); 
         root = insertIter(root,12);
         postOrderHelp(root);
         System.out.println();
         root = insertIter(root,56);
         postOrderHelp(root);
         System.out.println();
         root = insertIter(root,99);
         postOrderHelp(root);
         System.out.println();
         scan.close();
         */
     }
}

