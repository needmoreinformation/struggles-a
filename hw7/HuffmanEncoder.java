import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {

    /**
     * Builds a map of characters to ther frequency from given input char array.
     * @param inputSymbols
     * @return a map of characters to ther frequency from given input char array.
     */
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> freqTbl = new HashMap<>();
        for (char c : inputSymbols) {
            freqTbl.putIfAbsent(c, 0);
            freqTbl.put(c, freqTbl.get(c) + 1);
        }
        return freqTbl;
    }

    /**
     * Usage: java HuffmanEncoder watermelonsugar.txt
     *
     * @param args
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            return;
        }

        /* 1 Read the file as 8-bit symbols. */
        String fileName = args[0];
        char[] fileContents = FileUtils.readFile(fileName);

        /* 2 Build up frequency table. */
        Map<Character, Integer> freqTbl = buildFrequencyTable(fileContents);

        /* 3 Build binary Huffman trie. */
        BinaryTrie trie = new BinaryTrie(freqTbl);

        /* 4 */
        ObjectWriter objWriter = new ObjectWriter(fileName + ".huf");
        objWriter.writeObject(trie);

        /* 5 */
        System.out.printf("Encoded %d symbols\n", freqTbl.keySet().size());
        objWriter.writeObject(freqTbl.keySet().size());

        /* 6 */
        Map<Character, BitSequence> lookupTbl = trie.buildLookupTable();

        /* 7 */
        List<BitSequence> encodedBits = new ArrayList<>();

        /* 8 */
        for (char c : fileContents) {
            BitSequence seq = lookupTbl.get(c);
            encodedBits.add(seq);
        }

        /* 9 */
        BitSequence finalEncodedBits = BitSequence.assemble(encodedBits);
        System.out.println("Generated bitsequence is " + finalEncodedBits.length() + " bits long.");
        /* 10 */
        objWriter.writeObject(finalEncodedBits);
    }
}