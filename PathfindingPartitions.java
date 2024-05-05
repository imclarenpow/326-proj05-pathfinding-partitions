import java.util.*;

public class PathfindingPartitions{
    private static ArrayList<int[][]> partitions = new ArrayList<int[][]>();
    private static ArrayList<Board> boards = new ArrayList<>();
    public static void main(String[] args){
        // handles input   
        Scanner sc = new Scanner(System.in);
        ArrayList<String> lines = new ArrayList<String>();
        while (sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }
        sc.close();
        inputHandler(lines);
        // some debugging
        //DebuggingMethods debug = new DebuggingMethods();
        //debug.partitionHandlingCheck(partitions);
        initBoardArray();
        task();   
    }
    public static void inputHandler(ArrayList<String> input){
        // just makes it easier to iterate through is all not by much but it makes it a bit easier to read
        String[] lines = input.toArray(new String[0]);
        boolean first = true;
        // a bunch of checks and stuff to make sure everything is being put in the right place
        for(int i = 0; i < lines.length; i++){
            // if empty continue
            if(i == 0){
                partitions.add(new int[2][]);
            }else if(lines[i].trim().isEmpty()){
                continue;
            }else if(lines[i].trim().startsWith("#")){
                continue;
            }else if(lines[i].trim().equals("---")){
                partitions.add(new int[2][]);
            }else if(first){
                partitions.get(partitions.size()-1)[0] = partitionHandler(lines[i].split(" "));
                first = false;
            }else{
                partitions.get(partitions.size()-1)[1] = partitionHandler(lines[i].split(" "));
                first = true;
            }
        }
    }
    public static int[] partitionHandler(String[] input){
        // partitions in array will be {1, 2, 3} etc but as strings so we need to parse them
        int[] partition = new int[input.length];
        for(int i = 0; i < input.length; i++){
            partition[i] = Integer.parseInt(input[i]);
        }
        return partition;
    }
    // initialise the arraylist of boards
    public static void initBoardArray(){
        for(int i = 0; i < partitions.size(); i++){
            boards.add(new Board(partitions.get(i)));
        }
    }
    public static void task(){
        // something something boards.solve(i)
        // return the amount of moves needed to solve (if possible)
            // if not possible return # No solution possible
        // return each move required
            // if not possible return the starting partition and the wanted partitions
    }
}