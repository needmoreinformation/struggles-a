import java.util.ArrayList;
import java.util.List;

public class HuffmanDecoder {
    /**
     * Usage: java HuffmanDecoder watermelonsugar.txt.huf originalwatermelon.txt
     *
     * @param args
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            return;
        }

        String encodedFileName = args[0];
        String outputFileName = args[1];

        /* 1 */
        ObjectReader objReader = new ObjectReader(encodedFileName);
        BinaryTrie trie = (BinaryTrie) objReader.readObject();

        /* 2 */
        int symbolCount = (int) objReader.readObject();
        System.out.printf("There are %d symbols.\n", symbolCount);

        /* 3 */
        BitSequence bits = (BitSequence) objReader.readObject();
        System.out.printf("There are %d bits.\n", bits.length());

        /* 4 */
        List<Character> chars = new ArrayList<>();
        while (bits.length() > 0) {
            Match m = trie.longestPrefixMatch(bits);
            if (m == null) {
                break;
            }
            chars.add(m.getSymbol());
            bits = bits.allButFirstNBits(m.getSequence().length());
        }

        /* 5 */
        char[] origFileContents = new char[chars.size()];
        for (int i = 0; i < chars.size(); i += 1) {
            origFileContents[i] = chars.get(i);
        }
        FileUtils.writeCharArray(outputFileName, origFileContents);
    }
}