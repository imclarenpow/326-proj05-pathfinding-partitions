import java.util.*;

public class PathfindingPartitions {
    private static ArrayList<ArrayList<Integer>> bestMoves = new ArrayList<>();
    private static int amtMoves = 0;

    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> input = stdin();
        if(input.size()%2 != 0){
            input.remove(input.size()-1);
        }
        for(int i = 0; i < input.size(); i+=2){
            bestMoves = new ArrayList<>();
            amtMoves = 0;
            int sum1 = input.get(i).stream().mapToInt(Integer::intValue).sum();
            int sum2 = input.get(i+1).stream().mapToInt(Integer::intValue).sum();
            if(sum1 == sum2){
                aStar(input.get(i), input.get(i+1));
                System.out.println("# Moves Required: " + amtMoves);
                for(ArrayList<Integer> move : bestMoves){
                    System.out.println(partitionFormatting(move));
                }
            }else{ 
                System.out.println("# No solution\n" + partitionFormatting(input.get(i)) + "\n" + partitionFormatting(input.get(i+1))); 
            }
            if(i!=input.size()-2){ System.out.println("----"); }
        }
        //debugAlgorithm(); // only includes possible test cases
    }

    public static void aStar(ArrayList<Integer> frstPos, ArrayList<Integer> goalPos){
        // sort all states by cost
        PriorityQueue<State> costs = new PriorityQueue<>(Comparator.comparingInt(s -> s.cost));
        Map<ArrayList<Integer>, Integer> currCost = new HashMap<>();
        Map<ArrayList<Integer>, ArrayList<Integer>> from = new HashMap<>();

        costs.add(new State(frstPos, 0)); // add 1st state
        currCost.put(frstPos, 0); // & its cost

        while(!costs.isEmpty()){
            State curr = costs.poll(); // get state with lowest cost
            if(curr.configuration.equals(goalPos)){ // if goal reached reconstruct path
                ArrayList<ArrayList<Integer>> path = new ArrayList<>();
                ArrayList<Integer> currSt8 = curr.configuration;
                while(currSt8 != null){ // add until null
                    path.add(currSt8);
                    currSt8 = from.get(currSt8);
                }
                Collections.reverse(path); // path is currently goal to init, we want the reverse
                amtMoves = path.size()-1;
                for(ArrayList<Integer> state : path){
                    bestMoves.add(state);
                }
                return;
            }
            // neighbours
            for(int i = 0; i < curr.configuration.size(); i++){
                for(int j = 0; j <= curr.configuration.get(i); j++){
                    ArrayList<Integer> naybr = makeMove(curr.configuration, i, j);
                    int newCost = currCost.get(curr.configuration) + 1; // neighbour is right beside so +1
                    if(!currCost.containsKey(naybr) || newCost < currCost.get(naybr)){
                        currCost.put(naybr, newCost);
                        int priority = newCost + heuris(naybr, goalPos); // A* cost (specific to this problem)
                        costs.add(new State(naybr, priority));
                        from.put(naybr, curr.configuration);
                    }
                }
            }
        }
        System.out.println("# No solution "+ "\n" + partitionFormatting(frstPos) + "\n" + partitionFormatting(goalPos));
        bestMoves.clear();
    }

    public static ArrayList<Integer> makeMove(ArrayList<Integer> current, int index, int sizeToMove) {
        ArrayList<Integer> newConfiguration = new ArrayList<>(current);
        int newRow = 0;
        for (int i = 0; i < newConfiguration.size(); i++) {
            if (newConfiguration.get(i) > sizeToMove) {
                int temp = newConfiguration.get(i);
                if (temp - 1 != 0) {
                    newConfiguration.set(i, temp - 1);
                    newRow++;
                } else {
                    newConfiguration.remove(i);
                    newRow++;
                    i--;
                }
            }
        }
        newConfiguration.add(newRow);
        Collections.sort(newConfiguration, Collections.reverseOrder());
        return newConfiguration;
    }
    
    /** hueristic for playing partitions, this is probably going to be as optimal as possible for this problem. */
    public static int heuris(ArrayList<Integer> curr, ArrayList<Integer>goal){
        int max;
        if(curr.size() > goal.size()){ max = curr.size(); }
        else{ max = goal.size(); }
        int dist = 0;
        // iterate through both lists (i<max) for different sized lists
        for(int i=0; i < max; i++){
            // use 0 if nothing at indexes
            int currVal = (i < curr.size()) ? curr.get(i) : 0;
            int goalVal = (i < goal.size()) ? goal.get(i) : 0;
            // calc x & y, obv we want +ve vals
            int x = Math.abs(currVal - goalVal);
            // bit more complex bc if there is nothing at i we have to return 0
            int y = Math.abs((i<curr.size()) ? curr.indexOf(currVal) : 0 - 
                    ((i<goal.size()) ? goal.indexOf(goalVal) : 0));
            dist += x + y;
        }
        return dist;
    }
    /** handles stdin and returns as expected input to algorithm */
    public static ArrayList<ArrayList<Integer>> stdin() {
        Scanner sc = new Scanner(System.in);
        ArrayList<ArrayList<Integer>> output = new ArrayList<>();
        ArrayList<String> rawInput = new ArrayList<>();
        while (sc.hasNextLine()) {
            rawInput.add(sc.nextLine());
        }
        sc.close();
        // make sure we only accept the first two args since a seperator
        int twoArgs = 0;
        for(String input : rawInput){
            if(input.trim().isEmpty()){ continue; }
            if(input.trim().startsWith("-")){ twoArgs = 0; continue; }
            if(input.trim().startsWith("#")){ continue;}
            if(Character.isDigit(input.trim().charAt(0)) && twoArgs < 2){
                String wsComma = input.replaceAll("(?<=\\d)\\s+(?=,)", "").replaceAll(",", ", ");
                String[] parts = wsComma.split("\\s+");
                int[] nums = new int[parts.length];
                try{
                    for (int i = 0; i < parts.length; i++) {
                            nums[i] = Integer.parseInt(parts[i]);
                    }
                    twoArgs++;
                } catch (NumberFormatException e) {
                    continue;
                }
                ArrayList<Integer> inputList = new ArrayList<>();
                for (int num : nums) {
                    inputList.add(num);
                }
                Collections.sort(inputList, Collections.reverseOrder());
                output.add(inputList);
            }
        }
        
        return output;
    }
    public static String partitionFormatting(ArrayList<Integer> partition) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < partition.size(); i++) {
            sb.append(partition.get(i));
            if (i != partition.size() - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
    static class State {
        ArrayList<Integer> configuration;
        int cost;

        public State(ArrayList<Integer> configuration, int cost) {
            this.configuration = configuration;
            this.cost = cost;
        }
    }
}
