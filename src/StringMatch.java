/**
 * TODO #1
 */

public class StringMatch {

	/**
	 * TODO
	 * 
	 * Returns the result of running the naive algorithm to match pattern in
	 * text.
	 */
	//@params String pattern String text
	// @returns the Result of running Naive string matching algorithm for the given
	// pattern and text
	public static Result matchNaive(String pattern, String text) {
		int m = pattern.length(), n = text.length(), ans = 0, i = 0, j = 0, compared = 0;
		if (m == 0) {
			return new Result(0, 0);
		}
		while (ans <= n - m) {
			if (pattern.charAt(i) == text.charAt(j)) {
				i++;
				j++;
				compared++;
				if (i == m) {
					return new Result(ans, compared);
				}
			} else {
				i = 0;
				compared++;
				ans++;
				j = ans;

			}
		}
		return new Result(-1, compared);
	}

	/**
	 * TODO
	 * 
	 * Populates flink with the failure links for the KMP machine associated
	 * with the given pattern, and returns the cost in terms of the number of
	 * character comparisons.
	 */
	// @params String pattern int[] flink
	// @returns the cost in terms of the number of char comps. Populates flink with
	// the failure links for the KMP machine that are associated with the given pattern
	public static int buildKMP(String pattern, int[] flink) {
		int m = pattern.length();
		if (m == 0) {
			flink[0] = -1;
			return 0;
		}
		flink[0] = -1;
		flink[1] = 0;
		int i = 2;
		int j = 0;
		int count = 0;
		while (i <= m) {
			j = flink[i - 1];
			while (j != -1 && ++count != 0 && pattern.charAt(j) != pattern.charAt(i - 1)) {
				j = flink[j];
			}
			flink[i] = j + 1;
			i++;
		}
		return count;
	}

	/**
	 * TODO
	 * 
	 * Returns the result of running the KMP machine specified by flink (built
	 * for the given pattern) on the text.
	 */
	// @params String pattern String text int[] flink
	// @returns the Result of running the KMP machine specified by the built flink
	// for the given text.
	public static Result runKMP(String pattern, String text, int[] flink) {
		int j = 0, state = -1, count = 0;
		j = state;
		int m = pattern.length(), n = text.length();
		if (m == 0) {
			return new Result(0, count);
		}
		char sigmoid = 0;
		while (true) {
			count++;
			if (state == -1 || sigmoid == pattern.charAt(state)) {
				if (state == -1) {
					count--;
				}
				// success
				state = state + 1;
				if (state == pattern.length()) {
					return new Result(j - m + 1, count);
				}
				j = j + 1;
				if (j == n) {
					return new Result(-1, count);
				}
				sigmoid = text.charAt(j);
			} else {
				// failure
				state = flink[state];
			}
		}
	}

	/**
	 * Returns the result of running the KMP algorithm to match pattern in text.
	 * The number of comparisons includes the cost of building the machine from
	 * the pattern.
	 */
	public static Result matchKMP(String pattern, String text) {
		int m = pattern.length();
		int[] flink = new int[m + 1];
		int comps = buildKMP(pattern, flink);
		Result ans = runKMP(pattern, text, flink);
		return new Result(ans.pos, comps + ans.comps);
	}

	/**
	 * TODO
	 * 
	 * Populates delta1 with the shift values associated with each character in
	 * the alphabet. Assume delta1 is large enough to hold any ASCII value.
	 */
	// @params String pattern int[] delta1
	// Populates delta1 with the shift values that are in Sigma. Assumes that delta1 is
	// large enough to hold any ASCII value.
	public static void buildDelta1(String pattern, int[] delta1) {
		int m = pattern.length();
		for (int i = 0; i < Constants.SIGMA_SIZE; i++) {
			int charIndex = pattern.lastIndexOf(i);
			if (charIndex == -1) {
				delta1[i] = m;
			} else {
				delta1[i] = m - charIndex - 1;
			}
		}
	}

	/**
	 * TODO
	 * 
	 * Returns the result of running the simplified Boyer-Moore algorithm using
	 * the delta1 table from the pre-processing phase.
	 */
	public static Result runBoyerMoore(String pattern, String text, int[] delta1) {
		int n = text.length(), m = pattern.length(), i = 0, j = 0, count = 0;
		if (m == 0) {
			return new Result(0,0);
		}
		while (j <= n - m) {
			for (i = m - 1; i >= 0 && ++count != 0 && pattern.charAt(i) == text.charAt(i + j); --i);
				if (i < 0) {
					return new Result(j, count);
				} else {
					int textChar = text.charAt(i+j);
					int newDelta1 = 0;
					if (textChar > Constants.SIGMA_SIZE) {
						newDelta1 = m;
					} else {
						newDelta1 = delta1[textChar];
					}
					
					j += Math.max(1, newDelta1 - m + 1 + i);
				}
		}
		return new Result(-1, count);
	}

	/**
	 * Returns the result of running the simplified Boyer-Moore algorithm to
	 * match pattern in text.
	 */
	public static Result matchBoyerMoore(String pattern, String text) {
		int[] delta1 = new int[Constants.SIGMA_SIZE];
		buildDelta1(pattern, delta1);
		return runBoyerMoore(pattern, text, delta1);
	}

}
