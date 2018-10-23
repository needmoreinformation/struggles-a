package hw3.hash;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        Map<Integer, Integer> bucketCounts = new HashMap<>();
        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() % 0x7FFFFFF) % M;
            bucketCounts.putIfAbsent(bucketNum, 0);
            bucketCounts.put(bucketNum, bucketCounts.get(bucketNum) + 1);
        }

        int N = oomages.size();
        double lowerBound = N / 50;
        double higherBound = N / 2.5;
        for (int bucketNum : bucketCounts.keySet()) {
            int count = bucketCounts.get(bucketNum);
            if (count < lowerBound || count > higherBound) {
                return false;
            }
        }

        return true;
    }
}
