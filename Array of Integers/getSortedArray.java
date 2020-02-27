package Project1;

import java.util.*;

public class getSortedArray{

     public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        //return our list   
        int input = scan.nextInt();
        ArrayList<Integer> myList = new ArrayList<>();
        myList = getSortedArrayHelp(input);
        System.out.println(myList);

        scan.close();
      }
    
    private static ArrayList<Integer> getSortedArrayHelp(int n){

        ArrayList<Integer> randomArray = new ArrayList<>();
        //run through our values backward from n to 1
        for(int i = n; i > 0; i--){
            randomArray.add(i);
        }

        return randomArray;
    }
}
