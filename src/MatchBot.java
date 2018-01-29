import java.util.ArrayList;
import java.util.List;

/**
 * TODO #2
 */

public class MatchBot extends TwitterBot {
  /**
   * Constructs a MatchBot to operate on the last numTweets of the given user.
   */
  public MatchBot(String user, int numTweets) {
    super(user, numTweets);
  }
  
  /**
   * TODO
   * 
   * Employs the KMP string matching algorithm to add all tweets containing 
   * the given pattern to the provided list. Returns the total number of 
   * character comparisons performed.
   */
  public int searchTweetsKMP(String pattern, List<String> ans) {
	  int count = 0;
	  for (String x : this.tweets) {
		  Result result = StringMatch.matchKMP(pattern.toLowerCase(), x.toLowerCase());
		  if (result.pos != -1) {
			  ans.add(x);
			  count += result.comps; 
		  }
	  }
	  return count;
  }
  
  /**
   * TODO
   * 
   * Employs the naive string matching algorithm to find all tweets containing 
   * the given pattern to the provided list. Returns the total number of 
   * character comparisons performed.
   */
  public int searchTweetsNaive(String pattern, List<String> ans) {
	  int count = 0;
	  for (String x : this.tweets) {
		  Result result = StringMatch.matchNaive(pattern.toLowerCase(), x.toLowerCase());
		  if (result.pos != -1) {
			  ans.add(x);
			  count += result.comps;  
		  }
	  }
	  return count;
  }
  
  public int searchTweetsBM(String pattern, List<String> ans) {
	  int count = 0;
	  for (String x : this.tweets) {
		  Result result = StringMatch.matchBoyerMoore(pattern.toLowerCase(), x.toLowerCase());
		  if (result.pos != -1) {
			  ans.add(x);
			  count += result.comps;  
		  }
	  }
	  return count;
  }
  
  public static void main(String... args) {
    String handle = "realDonaldTrump", pattern = "Mexico";
    MatchBot bot = new MatchBot(handle, 2000);
    
    // Search all tweets for the pattern.
    List<String> ansNaive = new ArrayList<>();
    int compsNaive = bot.searchTweetsNaive(pattern, ansNaive); 
    List<String> ansKMP = new ArrayList<>();
    int compsKMP = bot.searchTweetsKMP(pattern, ansKMP);
    List<String> ansBM = new ArrayList<>();
    int compsBM = bot.searchTweetsBM(pattern, ansBM);
    // Added comps for BM
    System.out.println("naive comps = " + compsNaive + ", KMP comps = " + compsKMP + " BM comps = " + compsBM);

    for (int i = 0; i < ansKMP.size(); i++) {
      String tweet = ansKMP.get(i);
      assert tweet.equals(ansNaive.get(i));
      System.out.println(i++ + ". " + tweet);
      System.out.println(pattern + " appears at index " + 
          tweet.toLowerCase().indexOf(pattern.toLowerCase()));
    }
    
    // Do something similar for the Boyer-Moore matching algorithm.
    // Checked BM against Naive and KMP
    for (int i = 0; i < ansBM.size(); i++) {
    	String tweet = ansBM.get(i);
    	assert tweet.equals(ansNaive.get(i));
    	assert tweet.equals(ansKMP.get(i));
    }
    handle = "1future";
    pattern = "Mask Off";
    MatchBot future = new MatchBot(handle, 1000);

    //
    List<String> ansFutureBM = new ArrayList<>();
    int compsFutureBM = future.searchTweetsBM(pattern, ansFutureBM);
    List<String> ansFutureNaive = new ArrayList<>();
    int compsFutureNaive = future.searchTweetsNaive(pattern, ansFutureNaive);
    List<String> ansFutureKMP = new ArrayList<>();
    int compsFutureKMP = future.searchTweetsKMP(pattern, ansFutureKMP);
    System.out.println("naive comps = " + compsFutureNaive + ", KMP comps = " + compsFutureKMP + " BM comps = " + compsFutureBM);
    for (int i = 0; i < ansFutureBM.size(); i++) {
        String tweet = ansFutureBM.get(i);
        assert tweet.equals(ansFutureNaive.get(i));
        assert tweet.equals(ansFutureKMP.get(i));
        System.out.println(i++ + ". " + tweet);
        System.out.println(pattern + " appears at index " + 
            tweet.toLowerCase().indexOf(pattern.toLowerCase()));
      }
  }
}
