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
        }
        myNode.parent = myParent;
        //System.out.println(myParent.data);
        //steps to attach a node based on 
        // if it > than root or < root
        if(myParent.data <= data){
            myParent.right = myNode;
        }else{
            myParent.left = myNode;
        }
        //System.out.println(myNode.parent.data);
        //Update height of tree
        //changeTreeHeight(myNode);
        
        while(!myStack.empty()){
          Node prev = myStack.pop();
          //If BF is 2 
          if(getBalanceFactor(height(prev.left), height(prev.right))>1){
             Node prevLeft = prevLeft.left;
             int bfLft = getBalanceFactor(height(prevLeft.left),height(prevRight.right));
             //Rotate right
             //if node greater than 0 it is left heavy
             //if node less than 0 it is right heavy
             //Left - Right Case
             //2 -> -1 > 0
             if(bfLft < 0){
              if(prev == root){
                prev.left = leftRotate(prev.left);
                root = rightRotate(prev);
              }
              //check placement of node
              //attach it with its parent
              if(myStack.peek().left == prev){
                 prev.left = leftRotate(prev.left);
                 myStack.peek().left = rightRotate(prev);
              }else{
                 prev.left = leftRotate(prev.left);
                 myStack.peek().right = rightRotate(prev);
              }
             }
             else{
               //Perform right rotation
               //if at root node attach its parent
               if(prev == root){
                 root = rightRotate(prev);
               }
               //check placement of node
               //attach it with its parent
               if(myStack.peek().left == prev){
                  myStack.peek().left = rightRotate(prev);
               }else{
                  myStack.peek().right = rightRotate(prev);
               }
             }
           }
          
         }

        /*
        //we want to start from w and move all the way up
        Node z = myNode;
        Node x = null;
        Node y = null;
       
        //we move till first unbalanced node
        while(z != null ) {
        	if(getBalanceFactor(height(z.left),height(z.right)) <= 1){
                x = y;
                y = z;
                z = z.parent;
              }else{
                break;
              }
        }
        //once we get the first unbalanced node we return our balanced tree root node
        if(z != null) {
        	Node par;
        	par = z.parent;
        	//System.out.println(z.data);
        	//System.out.println(y.data);
        	//System.out.println(x.data);
            Node blncdNode = balance(z,y,x); 
            //System.out.println(blncdNode.data);
            //insert the tree into correct position in AVL tree
            if(z == root) {
            	//if root node just update our root
            	root = blncdNode;
            	blncdNode.parent = null;
            }
            else {
            	//System.out.println(par.data);
                if(par.left == z){
                    par.left = blncdNode;
                }else{
                    par.right = blncdNode;
                }
            }
        }
        */
        //return root
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
           changeTreeHeight(parent);
           balanceTree(root,parent);
        }
        
        //If node only has one child
        //Check weather they have left or right child
        if(hasChildren(current).equals("Left")){
        	if(parent.left == current) {
        		parent.left = current.left;
        	}
            parent.right = current.right;
            changeTreeHeight(parent);
            balanceTree(root,parent);
        }
        //check the left and right of pointer and just attach
        //next node to parent
        if(hasChildren(current).equals("Right")){
        	if(parent.left == current) {
        		parent.left = current.right;
        	}
            parent.right = current.right;
            changeTreeHeight(parent);
            balanceTree(root,parent);
        }
		
        //if node has two children
        if(hasChildren(current).equals("Two")){
          //we need to get the in order predecessor
          Node toReplace = findNextIter(root, current.data);
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
     //find balance factor
     public static int getBalanceFactor(int heightOfLft, int heightOfRght){
         return heightOfRght - heightOfLft;
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
     public static Node leftRotate(Node root){
      
      Node y;
      y = node.left;

      //System.out.println(y.data);
      //Node r = root.parent;
      //System.out.println(r.data);
      
      //update height
      root.height = 1 + max(height(root.left), height(root.right));
      y.height = 1 + max(height(y.left), height(y.right));

      return y;
     }
     //right rotation from given node
     public static Node rightRotate(Node root){
      //starting point for rotate
      Node y;
      y =root.left;
      Node Leaf3;
      Leaf3 = y.right;
      
      //rotate tree at right
      y.right = root;
      root.left = Leaf3;
      
      //change pointers
      //y.parent = root.parent;
      //root.parent = y;
      
      //update heights
      root.height = 1 + max(height(root.left), height(root.right));
      y.height = 1 + max(height(y.left), height(y.right));
      
      return y;
     }
     public static Node balance(Node z, Node y, Node x){
        // In terms of z->y->x
       //Left-Left Case 
       //Right rotation
       //start at z
       Node toReturn;
       if(z.left == y && y.left == x){
             toReturn = rightRotate(z);
             changeTreeHeight(toReturn);
             return toReturn;
       }
       //Left-Right Case
       if(z.left == y && y.right == x ){
            z.left = leftRotate(z.left);
            toReturn = rightRotate(z);
            changeTreeHeight(toReturn);
            return toReturn;
       }
       //Right-Right Case
       if(z.right == y && y.right == x){
          toReturn = leftRotate(z);
          changeTreeHeight(toReturn);
          return toReturn;
       }
       //Right-Left Case
       z.right = rightRotate(z.right);
       toReturn = leftRotate(z);
       changeTreeHeight(toReturn);
       return toReturn;
       
     }
     //update height of tree
     public static void changeTreeHeight(Node startNode){
       while(startNode != null){
         startNode.height = 1 + max(height(startNode.left), height(startNode.right));
         startNode = startNode.parent;
       }
     }
     
     public static void balanceTree(Node root, Node start) {
    	 
    	 Node z;
    	 Node y;
    	 Node x;
    	 
    	 while(start != null) {
    		 if(getBalanceFactor(height(start.left),height(start.right)) > 1) {
    			 z = start;
    			 y = getTallest(z);
    			 x = getTallest(y);
    			 Node blncdNode;
    			 blncdNode = balance(z,y,x);
    		 }
    		 start = start.parent;
    	 }
    
     }
     
     public static Node getTallest(Node someNode) {
    	 if(height(someNode.left) > height(someNode.right)) {
    		 return someNode.left;
    	 }
    	 return someNode.right;
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
         }
         return myArray;
     }
 
     public static void main(String[] args) {
         Scanner scan = new Scanner(System.in);
         //int input = scan.nextInt();
         ArrayList<Integer> mySet = new ArrayList<>();
         //convert set to array
         mySet = getRandomArrayHelp(10000);
         //print our list
         /*
         for(int i = 0; i < arr.length; i++){
             System.out.print(arr[i] + " ");
         }
         */
         Node root = null;
         root = insertIter(root,mySet.get(0));
         for(int i = 1; i < mySet.size(); i++){
             root = insertIter(root, mySet.get(i));
             //postOrderHelp(root);
             //System.out.println();
         }
         //postOrderHelp(root);
         
         
         /*
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
         root = insertIter(root,63);
         postOrderHelp(root);
         System.out.println();
         root = insertIter(root,23);
         postOrderHelp(root);
         System.out.println(); 
         root = insertIter(root,12);
         postOrderHelp(root);
         System.out.println();
         root = insertIter(root,16);
         postOrderHelp(root);
         System.out.println();
         */ 
     
           
     }
}
