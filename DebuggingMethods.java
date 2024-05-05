// a support class that contains methods for debugging

import java.util.ArrayList;

public class DebuggingMethods {
    public DebuggingMethods(){

    }
    public void partitionHandlingCheck(ArrayList<int[][]> partitions){
        for(int i = 0; i < partitions.size(); i++){
            System.out.println("Partition " + (i+1) + ":");
            for(int j = 0; j<2; j++){
                for(int part : partitions.get(i)[j]){
                    System.out.print(part + " ");
                }
                System.out.println();
            }
        }
    }
}
