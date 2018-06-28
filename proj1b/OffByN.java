/**
 * Character comparator for checking whether two characters are off by an arbitrary
 * distance, N. For example, if N = 2, then 'a' and 'c' are considered equal.
 * @author Struggler A
 */
public class OffByN implements CharacterComparator {
    /** Character offset for equality. */
    int N;

    /**
     * Returns whether two characters are equal to each other, off by N.
     * @param x character one to be compared.
     * @param y character two to be compared.
     * @return true if characters are equal but off by N.
     */
    @Override
    public boolean equalChars(char x, char y) {
        return  x - y == N || x - y == -N;
    }

    /**
     * Constructor for class.
     * @param N value used to compare characters.
     */
    public OffByN(int N) {
        this.N = N;
    }
}
