public class HelloNumbers {
    public static void main(String[] args) {
        int x = 0;
		int cumSum = 0;
        while (x < 10) {
			cumSum += x;
            System.out.print(cumSum + " ");			
            x = x + 1;			
        }
    }
}