import java.util.ArrayList;

public class DebugginUtils {
    public DebugginUtils() {
    }
    public ArrayList<Integer> arrToList(int[] arr) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i : arr) {
            list.add(i);
        }
        return list;
    }
}
