/**
 * Palindrome checks whether a string is a palindrome using a deque.
 * @author Struggler A
 */
public class Palindrome {
    /**
     * Builds a deque of characters from a string, ordered the same way as the string.
     * @param word word to build the deque from.
     * @return deque whose elements are characters in the same order as the string.
     */
    public static Deque<Character> wordToDeque(String word ){
        Deque<Character> result = new LinkedListDeque<>();

        for (Character c : word.toCharArray()) {
            result.addLast(c);
        }

        return result;
    }

    /**
     * Helper method for recursive implementation. Base case are when there are no more characters
     * left to compare or the middle is reached (odd length words) with only one character left.
     * As words with 1 or 0 characters are palindrome, the base case is to return true.
     * @param characters deque of remaining characters to be compared.
     * @return whether the word is a palindrome or not.
     */
    private static boolean isPalindrome(Deque<Character> characters) {
        if (characters.isEmpty() || characters.size() == 1) {
            return true;
        }
        boolean result = characters.removeFirst() == characters.removeLast();
        return result && isPalindrome(characters);
    }

    /**
     * Helper method for recursive implementation. Base case are when there are no more characters
     * left to compare or the middle is reached (odd length words) with only one character left.
     * As words with 1 or 0 characters are palindrome, the base case is to return true.
     * @param characters deque of remaining characters to be compared.
     * @param cc character comparator to test for equality.
     * @return whether the word is a palindrome or not.
     */
    private static boolean isPalindrome(Deque<Character> characters, CharacterComparator cc) {
        if (characters.isEmpty() || characters.size() == 1) {
            return true;
        }
        boolean result = cc.equalChars(characters.removeFirst(), characters.removeLast());
        return result && isPalindrome(characters, cc);
    }

    /**
     * Tests whether a word is a palindrome with a user-defined comparator. Returns true if
     * the word is a palindrome.
     * @param word the word to be tested
     * @param cc the character comparator to be used when testing for palindromes
     * @return true if the word is a palindrome.
     */
    public static boolean isPalindrome(String word, CharacterComparator cc) {
        return isPalindrome(wordToDeque(word), cc);
    }

    /**
     * Checks whether a given word is a palindrome. Returns true if word is a palindrome.
     * @param word the word to be tested
     * @return true if word is a palindrome.
     */
    public static boolean isPalindrome(String word) {
        return isPalindrome(wordToDeque(word));
    }

    public static void main(String[] args) {
        StdOut.print(isPalindrome("flake", new OffByOne()));
        Deque<Character> test = wordToDeque("1234");
        test.printDeque();
        StdOut.println(test.removeFirst());
        StdOut.println(test.removeLast());
        StdOut.println(test.removeFirst());
        StdOut.println(test.removeLast());

    }
}
