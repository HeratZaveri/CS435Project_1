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

class BSTree {
    Node root = null;    
	  public BSTree(){
    }

     public Node insertIter(int data){
        Node myNode = new Node(data);
          if(root == null){
              root = myNode;
              return root;
          }
          
          Node temp = null;
          Node current = root;

          while(current != null){
            temp = current;
            if(current.data <= data){
                current = current.right;
            }
            current = current.left;
          }

          if(temp.data <= data){
              temp.right = myNode;
          }else{
              temp.left = myNode;
          }
         return myNode;
      }

     public void deleteIter(int delete){

        Node parent = null;
        Node current = root;

        while(current.data != delete){
          parent = current;
          if(current.data <= delete){
            current = current.right;
          }
          current = current.left;
        }

        //3 cases
        //If node we have to delete has no children than just delete it
        if(hasChildren(current).equals("None")){
          if(current != root){
            if(parent.right == current){
              parent.right = null;
             }
             parent.left = null;
          }
          root = null;
        }
        //If node has one child
        if(hasChildren(current).equals("Left")){
          if(current != root){
            if(parent.right == current){
              parent.right = current.left;
            }
            parent.left = current.left;
          }
          root = current.left;
        }
        if(hasChildren(current).equals("Right")){
          if(current == root){
            root = current.right;
          }else{
          if(parent.right == current){
            parent.right = current.right ;
          }
           parent.left = current.right;
          }
         }

        //if node has two children
        if(hasChildren(current).equals("Two")){
          //we need to get the in order Successor
          //to replace
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
     public String hasChildren(Node node){
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

     public Node findNextIterHelp(Node node, int value){

      Node current = null;
      
      if(node.right != null){
        current = node.right;
        while(current.left != null){
          current = current.left;
        }
        return current;
      }

      Node temp = root;
      while(temp != null){
        if(node.data <= temp.data){
          current = temp;
          temp = temp.left;
        }
        else if(node.data > temp.data){
          temp = temp.right;
        }
        else{
          break;
        }
      }
      return current;
     }
     public Node findNextIter(int value){
      return findNextIterHelp(root, value);
     }

     public Node findPrevIterHelp(Node rootNode, int value){
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

        if(current.left != null){
          move = current.left;
          while(move.right != null){
            move = move.right;
          }
          return move;
        }

        while(rootNode != null){
          if(current.data > rootNode.data){
            move = rootNode;
            rootNode = rootNode.right;
          }
          else if(current.data < rootNode.data){
              rootNode = rootNode.right;
          }
          else{
            break;
          }
        }
        return move;
     }

     public Node findPrevIter(int value){
            return findPrevIterHelp(root, value);
     }
     //The leftmost node will be the minimum unless root is empty
     public Node findMinIter(Node rootNode){
        if(rootNode == null){
          return null;
        }
        //go till we find the left most node
        while(rootNode.left != null){
          rootNode = rootNode.left;
        }
        return rootNode;
     }
     //The righttmost node will be the minimum unless root is empty
     public Node findMaxIter(Node rootNode){
      if(rootNode == null){
        return null;
      }
      //go till we find our right most Node
      while(rootNode.right != null){
        rootNode = rootNode.right;
      }
      return rootNode;
   }

	  
}
