package Project1;

import java.util.*;

public class getRandomArray{

     public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
            
        int input = scan.nextInt();
        HashSet<Integer> mySet = new HashSet<>();
        //convert set to array
        mySet = getRandomArrayHelp(input);
        Object[] arr = mySet.toArray();
        //print our list
        for(int i = 0; i < arr.length; i++){
            System.out.print(arr[i] + " ");
        }
        
     }
    private static HashSet<Integer> getRandomArrayHelp(int n){
        HashSet<Integer> randomArray = new HashSet<>();
        //ArrayList<Integer> myArray = new ArrayList<>();
        Random rand = new Random();
        int seen = 0;
        //Loop till we dont have duplicates
        while(seen < n){
            int myNum = rand.nextInt(n*10) + 1;
            //check if array has unique value
            if(!randomArray.contains(myNum)){
                randomArray.add(myNum);
                seen++;
            }
        }
        return randomArray;
    }
}
