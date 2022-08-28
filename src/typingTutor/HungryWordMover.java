package typingTutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class HungryWordMover extends Thread{
	private FallingWord myWord;
	private AtomicBoolean done;
	private AtomicBoolean pause; 
	private AtomicBoolean started; 
	private Score score;
	//CountDownLatch startLatch; //so all can start at once

	HungryWordMover( FallingWord word) {
		myWord = word;
	}
	
	HungryWordMover( FallingWord word, Score score, AtomicBoolean d, AtomicBoolean p, AtomicBoolean s) {
		this(word);
		//this.startLatch = startLatch;
		this.score=score;
		this.done=d;
		this.pause=p;
		this.started=s;
	}

	public void run() {
		while(!started.get()){}

		System.out.println(myWord.getWord() + " falling speed = " + myWord.getSpeed());
		/*try {
			System.out.println(myWord.getWord() + " waiting to start " );
			startLatch.await();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //wait for other threads to start*/
		
		System.out.println(myWord.getWord() + " started" );
		while (!done.get()) {				
			//animate the word
			while (!myWord.shifted() && !done.get()) {
					if (myWord.caught.get()){
						slumber();
						myWord.caught.set(false);
					}
				    myWord.shiftRight(10);
					try {
						sleep(myWord.getSpeed());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};		
					while(pause.get()&&!done.get()) {};
			}
			if (!done.get() && myWord.shifted()) {
				score.missedWord();	
				myWord.setWord("");
				slumber();
				myWord.resetHWord();
			}
		}
	}

	public static void slumber(){
		try{
			sleep((int)(Math.random() * 10000) + 3000);		
		}
		catch(InterruptedException ex){
			ex.getStackTrace();
		}
	}
}