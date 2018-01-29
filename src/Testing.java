import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class Testing {
  private static Random random = new Random();
  private static String alphabet = "abcdefghijklmnopqrstuvwxyz0123456789";

  @Test 
  public void testEmpty() {
    System.out.println("testEmpty");
    match("", "");
    match("", "ab");
    System.out.println();
  }
  @Test
  public void testBuildDelta1() {
	  
  }
  @Test 
  public void smallMachines() {
    String[] pats = new String[] {
        "",
        "A",
        "AB",
        "AA",
        "AAAA",
        "BAAA",
        "AAAB",
        "AAAC",
        "ABAB",
        "ABCD",
        "ABBA",
        "AABC",
        "ABAAB",
        "AABAACAABABA",
        "ABRACADABRA",
    };
    int[][] flinks = new int[][] {
        { -1 },
        { -1, 0 },
        { -1, 0, 0 },
        { -1, 0, 1 },
        { -1, 0, 1, 2, 3 },
        { -1, 0, 0, 0, 0 },
        { -1, 0, 1, 2, 0 },
        { -1, 0, 1, 2, 0 },
        { -1, 0, 0, 1, 2 },
        { -1, 0, 0, 0, 0 },
        { -1, 0, 0, 0, 1 },
        { -1, 0, 1, 0, 0 },
        { -1, 0, 0, 1, 1, 2 },
        { -1, 0, 1, 0, 1, 2, 0, 1, 2, 3, 4, 0, 1 },
        { -1, 0, 0, 0, 1, 0, 1, 0, 1, 2, 3, 4 },
    };
    int[] comps = new int[] { 0, 0, 1, 1, 3, 3, 5, 5, 3, 3, 3, 4, 5, 16, 12 };
    int i = 0;
    for (String pat : pats) {
      int[] flink = new int[pat.length() + 1];
      assertEquals(comps[i], StringMatch.buildKMP(pat, flink));
      assertArrayEquals(flinks[i], flink);
      i++;
    }
  }

  @Test
  public void lec13bKMP() {
    String[] pats = new String[] {
        "AABC",
        "ABCDE",
        "AABAACAABABA",
        "ABRACADABRA",
    };
    int[][] flinks = new int[][] {
        { -1, 0, 1, 0, 0 },
        { -1, 0, 0, 0, 0, 0 },
        { -1, 0, 1, 0, 1, 2, 0, 1, 2, 3, 4, 0, 1 },
        { -1, 0, 0, 0, 1, 0, 1, 0, 1, 2, 3, 4 },
    };
    String text = "AAAAAABRACADABAAAAAAAAAAAAAAAAAAAAAAABCAAAAAAAAAAABAABAAAAAAAAAAAAAAA";
    Result results[] = new Result[] {
        new Result(35, 68),
        new Result(-1, 128),
        new Result(-1, 123),
        new Result(-1, 126),
    };
    int i = 0;
    for (String pat : pats) {
      Result res = StringMatch.runKMP(pat, text, flinks[i]);
      assertEquals(results[i].pos, res.pos);
      assertEquals(results[i].comps, res.comps);
      i++;
    }
  }
  @Test 
  public void testKMP() {
	  String pattern = "AAB";
	  String text = "ABABABAAB";
	  assertEquals(9, text.length());
	  Result ans = StringMatch.matchKMP(pattern, text);
	  assertEquals(ans.pos, 6);
	  assertEquals(ans.comps, 15);
	  text = "AAAAAAAAAAB";
	  assertEquals(11, text.length());
	  ans = StringMatch.matchKMP(pattern, text);
	  assertEquals(8, ans.pos);
	  assertEquals(22, ans.comps);
	  text = "ABAABABAAB";
	  // Repeat test
	  ans = StringMatch.matchKMP(pattern, text);
	  assertEquals(2, ans.pos);
	  assertEquals(9, ans.comps);
  }
  @Test
  public void worstCases() {
	  
  }
  @Test
  public void buildKMP() {
	  String pattern = "AAB";
	  String text = "ABABABAAB";
	  int[] flink = new int[pattern.length()+1];
	  assertEquals(9, text.length());
	  int ans = StringMatch.buildKMP(pattern, flink);
	  assertEquals(ans, 3);
	  pattern = "BB";
	  text = "ABAAABBBABBAABBAA";
	  flink = new int[pattern.length()+1];
	  assertEquals(17, text.length());
	  ans = StringMatch.buildKMP(pattern, flink);
	  assertEquals(1, ans);
	  pattern = "A";
	  text = "BBBBBA";
	  assertEquals(6, text.length());
	  flink = new int[pattern.length()+1];
	  ans = StringMatch.buildKMP(pattern, flink);
	  assertEquals(0, ans);  
  }
  @Test
  public void testMatchBM() {
	  int[] delta1 = new int[Constants.SIGMA_SIZE];
	  String pattern = "00000";
	  String text = "00000000000000000000";
	  Result ans = StringMatch.matchBoyerMoore(pattern, text);
	  assertEquals(5, ans.comps);
	  pattern = "11111";
	  ans = StringMatch.matchBoyerMoore(pattern, text);
	  assertEquals(4, ans.comps);
	  pattern = "00011";
	  ans = StringMatch.matchBoyerMoore(pattern, text);
	  assertEquals(8, ans.comps);
	  pattern = "10100";
	  ans = StringMatch.matchBoyerMoore(pattern, text);
	  assertEquals(48, ans.comps);
	  pattern = "10000";
	  ans = StringMatch.matchBoyerMoore(pattern, text);
	  assertEquals(80, ans.comps);
  }
  
  @Test public void runKMP() {
	  String pattern = "AAB";
	  String text = "ABABABAAB";
	  int[] flink = new int[pattern.length()+1];
	  int comps = StringMatch.buildKMP(pattern, flink);
	  Result ans = StringMatch.runKMP(pattern, text, flink);
	  assertEquals(6, ans.pos);
	  assertEquals(12, ans.comps);
  }
  @Test
  public void testNaive() {
    String[] pats = new String[] {
        "AAAA",
        "BAAA",
        "AAAB",
        "AAAC",
        "ABAB",
    };
    String text = "AAAAAAAAABAAAAAAAAAB";
    assertEquals(20, text.length());
    Result[] results = new Result[] {
        new Result(0, 4),
        new Result(9, 13),
        new Result(6, 28),
        new Result(-1, 62),
        new Result(-1, 35),
    };
    int i = 0;
    for (String pat : pats) {
      Result res = StringMatch.matchNaive(pat, text);
      assertEquals(res.pos, results[i].pos);
      assertEquals(res.comps, results[i].comps);
      i++;
    }
  }

  @Test 
  public void testOneChar() {
    System.out.println("testOneChar");
    match("a", "a");
    match("a", "b");
    System.out.println();
  }

  @Test 
  public void testRepeat() {
    System.out.println("testRepeat");
    match("aaa", "aaaaa");
    match("aaa", "abaaba");
    match("abab", "abacababc");
    match("abab", "babacaba");
    System.out.println();
  }
  
  @Test
  public void realFavorableKMP() {
	  System.out.println("Favorable KMP Test");
	  String pattern = "10";
	  String text = "000000000100000000001";
	  Result ans = StringMatch.matchKMP(pattern, text);
	  System.out.println("KMP : "+ ans.comps);
	  ans = StringMatch.matchBoyerMoore(pattern, text);
	  System.out.println("BM : " +ans.comps);
	  pattern = "100";
	  ans = StringMatch.matchKMP(pattern, text);
	  System.out.println("KMP : "+ ans.comps);
	  ans = StringMatch.matchBoyerMoore(pattern, text);
	  System.out.println("BM : " +ans.comps);
  }
  // KMP BLOWS
  //@Test
  public void favorableKMP() {
	  System.out.println("Favorable KMP Test");
	  String[] patterns = new String[] { 
			  "AB",
			  "BABA",
			  "AAAA",
			  "AAAB",
			  "BBBB",
			  "BBBA"
	  };
	  for (String x : patterns) {
		  String text = makeRandomTextSmall(x);
		  Result ans = StringMatch.matchBoyerMoore(x, text);
		  System.out.println("BM : " + ans.comps);
		  ans = StringMatch.matchKMP(x, text);
		  System.out.println("KMP : " + ans.comps);
	  }
  }
  
  @Test
  public void favorableBM() {
	  System.out.println("Favorable BM Test");
//	  String[] pats = new String[] {
//			  "ABBA",
//			  "ABCA",
//			  "AAAA",
//			  "BBBB",
//			  "ABCDEFG"
//	  };
	  String[] pats = new String[] {
			  makeRandomPatternSmall(),
			  makeRandomPatternSmall(),
			  makeRandomPatternSmall(),
			  makeRandomPatternSmall(),
			  makeRandomPatternSmall(),
			  makeRandomPatternSmall(),
			  makeRandomPattern(),
			  makeRandomPattern(),
			  makeRandomPattern(),
			  makeRandomPattern(),
	  };
	  String text = makeRandomTextSmall(alphabet);
	  for (String x : pats) {
		  Result ans = StringMatch.matchKMP(x, text);
		  //System.out.println(x);
		  //System.out.println(text);
		  System.out.println("KMP : " +ans.comps);
		  ans = StringMatch.matchBoyerMoore(x, text);
		  System.out.println("BM : " + ans.comps);
	  }
	  String bigText = makeRandomText(alphabet);
	  for (String x : pats) {
		  Result ans = StringMatch.matchKMP(x, bigText);
		  //System.out.println(x);
		  //System.out.println(text);
		  System.out.println("KMP : " +ans.comps);
		  ans = StringMatch.matchBoyerMoore(x, bigText);
		  System.out.println("BM : " + ans.comps);
	  }
  }

  @Test 
  public void testPartialRepeat() {
    System.out.println("testPartialRepeat");
    match("aaacaaaaac", "aaacacaacaaacaaaacaaaaac");
    match("ababcababdabababcababdaba", "ababcababdabababcababdaba");
    System.out.println();
  }

  @Test 
  public void testRandomly() {
    System.out.println("testRandomly");
    for (int i = 0; i < 100; i++) {
      String pattern = makeRandomPattern();
      for (int j = 0; j < 100; j++) {
        String text = makeRandomText(pattern);
        match(pattern, text);
      }
    }
    System.out.println();
  }

  /* Helper functions */

  private static String makeRandomPattern() {
    StringBuilder sb = new StringBuilder();
    int steps = random.nextInt(10) + 1;
    for (int i = 0; i < steps; i++) {
      if (sb.length() == 0 || random.nextBoolean()) {  // Add literal
        int len = random.nextInt(5) + 1;
        for (int j = 0; j < len; j++)
          sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
      } 
      else {  // Repeat prefix
        int len = random.nextInt(sb.length()) + 1;
        int reps = random.nextInt(3) + 1;
        if (sb.length() + len * reps > 1000)
          break;
        for (int j = 0; j < reps; j++)
          sb.append(sb.substring(0, len));
      }
    }
    return sb.toString();
  }
  
  private static String makeRandomPatternSmall() {
	    StringBuilder sb = new StringBuilder();
	    int steps = random.nextInt(2) + 1;
	    for (int i = 0; i < steps; i++) {
	      if (sb.length() == 0 || random.nextBoolean()) {  // Add literal
	        int len = random.nextInt(5) + 1;
	        for (int j = 0; j < len; j++)
	          sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
	      } 
	      else {  // Repeat prefix
	        int len = random.nextInt(sb.length()) + 1;
	        int reps = random.nextInt(3) + 1;
	        if (sb.length() + len * reps > 100)
	          break;
	        for (int j = 0; j < reps; j++)
	          sb.append(sb.substring(0, len));
	      }
	    }
	    return sb.toString();
	  }
  
  private static String makeRandomTextSmall(String pattern) {
	    StringBuilder sb = new StringBuilder();
	    int steps = random.nextInt(100);
	    for (int i = 0; i < steps && sb.length() < 10; i++) {
	      if (random.nextDouble() < 0.7) {  // Add prefix of pattern
	        int len = random.nextInt(pattern.length()) + 1;
	        sb.append(pattern.substring(0, len));
	      } 
	      else {  // Add literal
	        int len = random.nextInt(30) + 1;
	        for (int j = 0; j < len; j++)
	          sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
	      }
	    }
	    return sb.toString();
	  }

  private static String makeRandomText(String pattern) {
    StringBuilder sb = new StringBuilder();
    int steps = random.nextInt(100);
    for (int i = 0; i < steps && sb.length() < 10000; i++) {
      if (random.nextDouble() < 0.7) {  // Add prefix of pattern
        int len = random.nextInt(pattern.length()) + 1;
        sb.append(pattern.substring(0, len));
      } 
      else {  // Add literal
        int len = random.nextInt(30) + 1;
        for (int j = 0; j < len; j++)
          sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
      }
    }
    return sb.toString();
  }

  private static void match(String pattern, String text) {
    // run all three algorithms and test for correctness
    Result ansNaive = StringMatch.matchNaive(pattern, text);
    int expected = text.indexOf(pattern);
    assertEquals(expected, ansNaive.pos);
    Result ansKMP = StringMatch.matchKMP(pattern, text);
    assertEquals(expected, ansKMP.pos);
    Result ansBoyerMoore = StringMatch.matchBoyerMoore(pattern, text);
    assertEquals(expected, ansBoyerMoore.pos);
    System.out.println(String.format("%5d %5d %5d : %s", 
        ansNaive.comps, ansKMP.comps, ansBoyerMoore.comps,
        (ansNaive.comps < ansKMP.comps && ansNaive.comps < ansBoyerMoore.comps) ?
            "Naive" :
              (ansKMP.comps < ansNaive.comps && ansKMP.comps < ansBoyerMoore.comps) ?
                  "KMP" : "Boyer-Moore"));
  }
}
