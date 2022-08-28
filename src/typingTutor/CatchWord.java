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
	
	// public void run() {
	// 	int i=0;
	// 	while (i<noWords) {		
	// 		while(pause.get()) {};
	// 		if (words[i].matchWord(target)) {
	// 			System.out.println( " score! '" + target); //for checking
	// 			score.caughtWord(target.length());	
	// 			//FallingWord.increaseSpeed();
	// 			break;
	// 		}
	// 	   i++;
	// 	}
		
	// }
//Change
	public void run() {
		int max = -1;
		int index = -1;
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
		if (index > -1){
			if (hungryWord.matchWord(target) && hungryWord.getY() > words[index].getY()){
				System.out.println( " score! '" + target); //for checking
				score.caughtWord(target.length(), hungryWord.collidedWords.get() + 1);	
				hungryWord.caught.set(true);
				hungryWord.resetHWord();
				HungryWordMover.slumber();
			}
			else{
				System.out.println( " score! '" + target); //for checking
				score.caughtWord(target.length());	
				words[index].resetWord();
			}
		}
		else if (hungryWord.matchWord(target)){
			System.out.println( " score! '" + target); //for checking
			score.caughtWord(target.length(), hungryWord.collidedWords.get() + 1);	
			hungryWord.caught.set(true);
			hungryWord.resetHWord();
			HungryWordMover.slumber();
		}
		
	}	
}
