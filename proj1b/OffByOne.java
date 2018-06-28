/**
 * Character comparator for checking whether two characters are off by 1 character.
 * @author Struggler A
 */
public class OffByOne implements CharacterComparator {

    /**
     * Returns whether two characters are equal to each other, off by 1.
     * @param x character one to be compared.
     * @param y character two to be compared.
     * @return true if characters are equal but off by 1.
     */
    @Override
    public boolean equalChars(char x, char y) {
        return  x - y == 1 || x - y == -1;
    }

    /**
     * Small test cases from the specification.
     */
    public static void main(String[] args) {
        OffByOne obo = new OffByOne();
        boolean result;

        /* Should return true */
        result = obo.equalChars('a', 'b');
        result = obo.equalChars('r', 'q');

        /* Should return false */
        result = obo.equalChars('a', 'e');
        result = obo.equalChars('z', 'a');
        result = obo.equalChars('a', 'a');
    }
}
