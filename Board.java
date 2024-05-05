import java.util.ArrayList;

public class Board {
    private ArrayList<Integer> wantedBoard = new ArrayList<Integer>();
    private ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
    public Board(int[][] partitions){
        // initialises workingBoard and goalBoard as ArrayLists
        setWantedBoard(partitions[1]);
        setFirstMove(partitions[0]);
    }
    
    public void solveBoard(){
        boolean solved = false;
        while(!solved){
            if(moves.get(moves.size()-1).equals(wantedBoard)){
                solved = true;
            }
            // TODO: implement the solving algorithm
        }
    }
    // get methods
    public int[] getWorkingBoard(){
        int[] working = new int[wantedBoard.size()];
        for(int i = 0; i < wantedBoard.size(); i++){
            working[i] = wantedBoard.get(i);
        }
        return working;
    }
    // initialisation methods follow
    public void setWantedBoard(int[] init){
        for(int i = 0; i < init.length; i++){
            wantedBoard.add(init[i]);
        }
    }
    public void setFirstMove(int[] goal){
        moves.add(new ArrayList<Integer>());
        for(int i = 0; i < goal.length; i++){
            moves.get(0).add(goal[i]);
        }
    }
}
