
public class AlgTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			long start = System.currentTimeMillis();
			long fibo = Fibo(i);
			System.out.println("Fibonacci " + (i) + ":\t" + fibo);
			System.out.println((System.currentTimeMillis() - start) + " ms");
		}
	}
	
	
	static long Fibo (int n) {
		if (n < 1) return 0;
		if (n==1) return 1;
		return Fibo(n-1) + Fibo (n-2);
	}

}
