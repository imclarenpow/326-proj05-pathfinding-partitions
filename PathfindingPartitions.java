import java.util.*;

public class PathfindingPartitions {

    public static void main(String[] args) {
        // TODO: handle stdin() better
        // TODO: handle impossible cases
        debugAlgorithm(); // only includes possible test cases
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
                for(ArrayList<Integer> state : path){
                    System.out.println(state);
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
        System.out.println("No solution found");
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

    public static ArrayList<ArrayList<Integer>> stdin() {
        Scanner sc = new Scanner(System.in);
        ArrayList<ArrayList<Integer>> output = new ArrayList<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.startsWith("#") || line.startsWith("-")) {
                continue;
            }
            ArrayList<Integer> numbers = new ArrayList<>();
            for (String s : line.split(" ")) {
                numbers.add(Integer.parseInt(s));
            }
            output.add(numbers);
        }
        sc.close();
        return output;
    }
    /** hueristic for playing partitions */
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
        }
        return dist;
    }
    public static void debugAlgorithm(){
        DebugginUtils d = new DebugginUtils();
        ArrayList<Integer> frstPos = d.arrToList(new int[] { 4, 3, 1 });
        ArrayList<Integer> goalPos = d.arrToList(new int[] { 2, 2, 2, 2 });
        // expected 4, 3, 1 | 3, 3, 2 | 2, 2, 2, 2
        aStar(frstPos, goalPos);
        System.out.println("----");
        frstPos = d.arrToList(new int[] {2, 2});
        goalPos = d.arrToList(new int[] {3, 1});
        // expected 2, 2 | 2, 1, 1 | 3, 1
        aStar(frstPos, goalPos);
        System.out.println("----");
        frstPos = d.arrToList(new int[] {3, 2, 1});
        goalPos = d.arrToList(new int[] {3, 3});
        // expected 3, 2, 1 | 2, 2, 1, 1 | 4, 1, 1 | 3, 3
        aStar(frstPos, goalPos);
        System.out.println("----");
        frstPos = d.arrToList(new int[] {4, 4, 3, 2, 2, 1});
        goalPos = d.arrToList(new int[] {6, 5, 5});
        // expected 4, 4, 3, 2, 2, 1 | 3, 3, 3, 2, 2, 2, 1 | 7, 2, 2, 2, 1, 1, 1 | 7, 6, 1, 1, 1 | 6, 5, 5
        aStar(frstPos, goalPos);
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
