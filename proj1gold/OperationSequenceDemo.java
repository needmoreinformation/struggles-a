/** Demonstrates how the OperationSequence class works. */
public class OperationSequenceDemo {
    private static void main(String[] args) {
        OperationSequence fs = new OperationSequence();
        DequeOperation dequeOp1 = new DequeOperation("addFirst", 5);
        DequeOperation dequeOp2 = new DequeOperation("addFirst", 10);
        DequeOperation dequeOp3 = new DequeOperation("size");

        fs.addOperation(dequeOp1);
        fs.addOperation(dequeOp2);
        fs.addOperation(dequeOp3);
    }
} 
