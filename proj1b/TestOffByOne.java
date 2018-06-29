import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testCorrectness() {
        /* Should return true */
        assertTrue( offByOne.equalChars('a', 'b'));

        assertTrue( offByOne.equalChars('A', '@'));
        assertTrue( offByOne.equalChars('A', 'B'));
        assertTrue( offByOne.equalChars('1', '2'));
        assertTrue( offByOne.equalChars('2', '1'));

        /* Should return false */
        assertFalse( offByOne.equalChars('z', 'A'));
        assertFalse( offByOne.equalChars('a', 'e'));
        assertFalse( offByOne.equalChars('z', 'a'));
        assertFalse( offByOne.equalChars('a', 'a'));
        assertFalse( offByOne.equalChars('a', 'A'));
        assertFalse( offByOne.equalChars('a', 'B'));
    }

}
