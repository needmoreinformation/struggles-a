import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class BinaryTrie implements Serializable {

    private Node root;

    private class Node implements Comparable<Node>, Serializable {
        private char ch;
        private int freq;
        private Node left;
        private Node right;

        @Override
        public int compareTo(Node o) {
            return this.freq - o.freq;
        }

        public Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }
    }

    /**
     * Constructor. Builds up a Huffman decoding trie.
     * @param frequencyTable maps symbols of type V to relative frequencies.
     */
    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        PriorityQueue<Node> pq = new PriorityQueue<>();

        for (char c : frequencyTable.keySet()) {
            pq.offer(new Node(c, frequencyTable.get(c), null, null));
        }

        while (pq.size() > 1) {
            Node x = pq.poll();
            Node y = pq.poll();
            Node parent = new Node('\0', x.freq + y.freq, x, y);
            pq.offer(parent);
        }

        root = pq.poll();
    }

    /**
     * Finds the longest prefix that matches the given querySequence and returns
     * a Match object for that match.
     * @param querySequence
     * @return
     */
    public Match longestPrefixMatch(BitSequence querySequence) {

        int index = 0;
        Node curr = root;
        int bit = 0;

        while (index <= querySequence.length()) {
            bit = querySequence.bitAt(index);
            if (curr.isLeaf()) {
                BitSequence bits = querySequence.firstNBits(index);
                Match match = new Match(bits, curr.ch);
                return match;
            }

            if (bit == 0) {
                if (curr.left != null) {
                    index += 1;
                    curr = curr.left;
                } else {
                    return null;
                }
            } else {
                if (curr.right != null) {
                    index += 1;
                    curr = curr.right;
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * @return Returns the sequence table for the characters this binary trie
     *         represents.
     */
    public Map<Character, BitSequence> buildLookupTable() {
        Map<Character, BitSequence> sequenceTable = new HashMap<>();

        buildLookupTableHelper(root, sequenceTable, new StringBuilder());

        return sequenceTable;
    }

    private void buildLookupTableHelper(Node root,
            Map<Character, BitSequence> sequenceTable,
            StringBuilder buffer) {

        if (root.isLeaf()) {
            sequenceTable.put(root.ch, new BitSequence(buffer.toString()));
            return;
        }

        buffer.append('0');
        buildLookupTableHelper(root.left, sequenceTable, buffer);
        buffer.setLength(buffer.length() - 1);

        buffer.append('1');
        buildLookupTableHelper(root.right, sequenceTable, buffer);
        buffer.setLength(buffer.length() - 1);
    }
}