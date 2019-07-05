import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MainStream {

    private static int minValue(int[] values) {
        AtomicInteger ai = new AtomicInteger(0);
        return Arrays.stream(values).distinct()
                .map(x -> -x)  // the trick to sort in reversed order
                .sorted()
                .map(x -> -x * ((int) Math.pow(10, ai.getAndIncrement())))
                .reduce(0, Integer::sum);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream()
                .reduce(0, Integer::sum);
        return integers.stream()
                .filter(x -> (x + sum) % 2 != 0)
                .collect(Collectors.toList());
    }
    
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 8}));

        List<Integer> list = new ArrayList<>();
        list.add(4);
        list.add(8);
        list.add(10);
        list.add(2);
        list.add(7);
        System.out.println(oddOrEven(list));
    }

}
