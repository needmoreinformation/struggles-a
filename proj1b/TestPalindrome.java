import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void isPalindrome() {
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("aba"));
        assertTrue(palindrome.isPalindrome("aa"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("A"));
        assertFalse(palindrome.isPalindrome("cat"));
        assertFalse(palindrome.isPalindrome("ab"));
        assertFalse(palindrome.isPalindrome("aab"));
        assertFalse(palindrome.isPalindrome("aA"));
        assertFalse(palindrome.isPalindrome(" a"));
        assertFalse(palindrome.isPalindrome("aaAA"));
    }

    @Test
    public void isPalindromeWithCharComparator() {
        assertTrue(palindrome.isPalindrome("racecar", new CharacterComparator() {
            @Override
            public boolean equalChars(char x, char y) {
                return x == y;
            }
        }));

        assertFalse(palindrome.isPalindrome("ab", new CharacterComparator() {
            @Override
            public boolean equalChars(char x, char y) {
                return x == y;
            }
        }));

        assertFalse(palindrome.isPalindrome("racecar", new CharacterComparator() {
            @Override
            public boolean equalChars(char x, char y) {
                return x == y + 1;
            }
        }));
    }


}