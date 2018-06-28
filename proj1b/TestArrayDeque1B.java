import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tester for an incorrect ArrayDeque. Calls random methods with random arguments on given student
 * implementation. Test runs until there is a mismatch in output between the student and
 * reference deque.
 * @author Struggler A
 */
public class TestArrayDeque1B {

    @Test
    public void testIncorrectArrayDeque() {
        StudentArrayDeque<Integer> actual = new StudentArrayDeque<Integer>();
        ArrayDequeSolution<Integer> expected = new ArrayDequeSolution<Integer>();
        Integer randomVal, actualVal, expectedVal;
        OperationSequence methodHistory = new OperationSequence();

        while (true) {
            /* Pick a random method to call from 5 available methods. */
            randomVal = StdRandom.uniform(0, 6);
            switch (randomVal) {
                case 0: /* Remove: first */
                    if (!actual.isEmpty()) {
                        actualVal =  actual.removeFirst();
                        expectedVal = expected.removeFirst();
                        methodHistory.addOperation(new DequeOperation("removeFirst"));
                        assertEquals(methodHistory.toString(), expectedVal, actualVal);

                    }
                    break;

                case 1: /* Remove: last */
                    if (!actual.isEmpty()) {
                        actualVal = actual.removeLast();
                        expectedVal = expected.removeLast();
                        methodHistory.addOperation(new DequeOperation("removeLast"));
                        assertEquals(methodHistory.toString(), expectedVal, actualVal);
                    }
                    break;

                case 2: /* Add: first*/
                    methodHistory.addOperation(new DequeOperation("addFirst", randomVal));
                    randomVal = StdRandom.uniform(0, 9);
                    actual.addFirst(randomVal);
                    expected.addFirst(randomVal);

                    break;

                case 3: /* Add: last */
                    methodHistory.addOperation(new DequeOperation("addLast", randomVal));
                    randomVal = StdRandom.uniform(0, 9);
                    actual.addLast(randomVal);
                    expected.addLast(randomVal);

                    break;

                case 4: /* size */
                    methodHistory.addOperation(new DequeOperation("size"));
                    actualVal = actual.size();
                    expectedVal = expected.size();
                    assertEquals(methodHistory.toString(), expectedVal, actualVal);

                    break;

                case 5: /* is empty */
                    methodHistory.addOperation(new DequeOperation("isEmpty"));
                    actualVal = actual.isEmpty() ? 1 : 0;
                    expectedVal = expected.isEmpty() ? 1 : 0;
                    assertEquals(methodHistory.toString(), expectedVal, actualVal);

                    break;
            }
        }
    }

}
