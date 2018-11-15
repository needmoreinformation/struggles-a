import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class RadixSortTester {
    @Test
    public void testRadixSort() {
        String[] input = {"d", "a", "ab", "abcd", "ac"};
        String[] output = RadixSort.sort(input);
        String[] expected = {"a", "ab", "abcd", "ac", "d"};
        assertArrayEquals(expected, output);
    }
}
