import java.util.ArrayList;
import java.util.List;

/** This class outputs all palindromes in the words file in the current directory. */
public class PalindromeFinder {


    public static void main(String[] args) {
        int minLength = 4;
        In in = new In("words.txt");

        List<String> palindromes = new ArrayList<String>();
        List<String> offByOnedromes = new ArrayList<String>();
        List<String> offByNdromes = new ArrayList<String>();


        while (!in.isEmpty()) {
            String word = in.readString();

            if (word.length() >= minLength && Palindrome.isPalindrome(word, new OffByOne())) {
                offByOnedromes.add(word);
            }

            if (word.length() >= minLength && Palindrome.isPalindrome(word)) {
                palindromes.add(word);
            }

            if (word.length() >= minLength && Palindrome.isPalindrome(word, new OffByN(5))) {
                offByNdromes.add(word);
            }
        }


        StdOut.println("Palindromes:");
        for (String s : palindromes) {
            StdOut.println(s);
        }
        StdOut.println("Palindromes (off by 1):");
        for (String s : offByOnedromes) {
            StdOut.println(s);
        }
        StdOut.println("Palindromes (off by N):");
        for (String s : offByNdromes) {
            StdOut.println(s);
        }



    }
} 
