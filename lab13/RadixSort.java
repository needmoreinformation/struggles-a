import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {

    private static String[] sorted;

    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        String[] copy = Arrays.copyOf(asciis, asciis.length);

        int maxDigits = getMaxLengthString(copy);

        /* Go from LSD to MSD. */
        sorted = new String[asciis.length];
        for (int i = maxDigits - 1; i >= 0; i -= 1) {
            sortHelperLSD(copy, i);
        }

        return copy;
    }

    /**
     * Returns character at index, or 1 if that index doesn't exist. Result is
     * the radix sort will treat this string as earlier in the ordering.
     * @param s
     * @param index
     * @return character at index, or 1 if index doesn't exist.
     */
    private static int getChar(String s, int index) {
        if (index >= s.length()) {
            return 1;
        }
        return (int) s.charAt(index);
    }

    private static int getMaxLengthString(String[] asciis) {
        int max = Integer.MIN_VALUE;
        for (String s : asciis) {
            if (s.length() > max) {
                max = s.length();
            }
        }
        return max;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        int[] chars = new int[asciis.length];

        for (int i = 0; i < asciis.length; i += 1) {
            chars[i] = getChar(asciis[i], index);
        }

        // find difference between min and max
        int max = Integer.MIN_VALUE;
        for (int i : chars) {
            max = max > i ? max : i;
        }

        int min = Integer.MAX_VALUE;
        for (int i : chars) {
            min = min < i ? min : i;
        }

        // gather all the counts for each value
        int[] counts = new int[max - min + 1];

        for (String s : asciis) {
            int charCode = getChar(s, index);
            charCode -= min;
            counts[charCode] += 1;
        }

        int[] starts = new int[max - min + 1];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += counts[i];
        }


        for (int i = 0; i < asciis.length; i += 1) {
            String item = asciis[i];
            int charIndex = getChar(item, index) - min;
            int place = starts[charIndex];
            sorted[place] = item;
            starts[charIndex] += 1;
        }

        for (int i = 0; i < asciis.length; i += 1) {
            asciis[i] = sorted[i];
        }
    }

    //
    // ab, a,
    // bcd,
    // ba
    //
    // ab
    // a

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
}
