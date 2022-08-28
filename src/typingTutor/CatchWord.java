package typingTutor;

import java.util.concurrent.atomic.AtomicBoolean;

//Thread to monitor the word that has been typed.
public class CatchWord extends Thread {
	String target;
	static AtomicBoolean done ; //REMOVE
	static AtomicBoolean pause; //REMOVE
	
	private static  FallingWord[] words; //list of words
	private static FallingWord hungryWord;
	private static int noWords; //how many
	private static Score score; //user score
	
	CatchWord(String typedWord) {
		target=typedWord;
	}
	
	public static void setWords(FallingWord[] wordList) {
		words=wordList;	
		noWords = words.length;
	}
	
	public static void setHungryWord(FallingWord hWord){
		hungryWord = hWord;
	}

	public static void setScore(Score sharedScore) {
		score=sharedScore;
	}
	
	public static void setFlags(AtomicBoolean d, AtomicBoolean p) {
		done=d;
		pause=p;
	}
	
	public void run() {
		int max = -1; // lowest y coordinate
		int index = -1; // index of the word with the lowest coordinate
		for (int i = 0; i < noWords;i++){
			while(pause.get()) {};
			if (words[i].matchWord(target)) {
				if (words[i].getY() > max){
					index = i;
					max = words[i].getY();	
				}
				//FallingWord.increaseSpeed();
			}
			
		}
		// check if any of the words do make the target
		if (index > -1){
			// check if the hungry word also matched the target
			if (hungryWord.matchWord(target) && hungryWord.getY() > words[index].getY()){
				System.out.println( " score! '" + target); //for checking
				score.caughtWord(target.length());	
				hungryWord.caught.set(true);
				hungryWord.resetHWord();
			}
			else{
				System.out.println( " score! '" + target); //for checking
				score.caughtWord(target.length());	
				words[index].resetWord();
			}
		}
		// if only the hungry word matches the target
		else if (hungryWord.matchWord(target)){
			System.out.println( " score! '" + target); //for checking
			score.caughtWord(target.length());	
			hungryWord.caught.set(true);
			hungryWord.resetHWord();
		}
		
	}	
}
