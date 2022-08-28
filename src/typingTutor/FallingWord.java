package typingTutor;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class FallingWord {
	private String word; // the word
	private int x; //position - width
	private int y; // postion - height
	private int maxY; //maximum height
	private int maxX; //change
	private int start;
	private boolean shifted; //chnage
	private boolean dropped; //flag for if user does not manage to catch word in time
	public AtomicInteger collidedWords;
	public AtomicBoolean caught;
	
	private int fallingSpeed; //how fast this word is
	private static int maxWait=1000;
	private static int minWait=100;

	public static WordDictionary dict;
	
	FallingWord() { //constructor with defaults
		word="computer"; // a default - not used
		x=0;
		y=0;	
		maxY=300;
		maxX = 800; //change
		dropped=false;
		shifted = false;
		fallingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait); 
		collidedWords = new AtomicInteger(0);
		caught = new AtomicBoolean(false);
	}
	
	FallingWord(String text) { 
		this();
		this.word=text;
	}
	
	FallingWord(String text,int x, int maxY) { //most commonly used constructor - sets it all.
		this(text);
		this.x=x; //only need to set x, word is at top of screen at start
		this.maxY=maxY;
	}
	
	public static void increaseSpeed( ) {
		minWait+=50;
		maxWait+=50;
	}
	
	public static void resetSpeed( ) {
		maxWait=1000;
		minWait=100;
	}

// all getters and setters must be synchronized
	public synchronized  void setY(int y) {
		if (y>maxY) {
			y=maxY;
			dropped=true; //user did not manage to catch this word
		}
		this.y=y;
	}
	// change
	public synchronized  void setX(int x) {
		if (x>maxX) {
			x=maxX;
			shifted=true; //user did not manage to catch this word
		}
		this.x=x;
	}
	
	public synchronized  void setWord(String text) {
		this.word=text;
	}

	public synchronized  String getWord() {
		return word;
	}
	
	public synchronized  int getX() {
		return x;
	}	
	
	public synchronized  int getY() {
		return y;
	}
	
	public synchronized  int getSpeed() {
		return fallingSpeed;
	}

	public synchronized void setPos(int x, int y) {
		setY(y);
		setX(x);
		start = x;
	}
	public synchronized void setXLimit(int x){
		maxX = x;
	}

	public synchronized void resetPos() {
		setY(0);
	}

	public synchronized void resetWord() {
		resetPos();
		word=dict.getNewWord();
		dropped=false;
		fallingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait); 
		//System.out.println(getWord() + " falling speed = " + getSpeed());
	}

	public synchronized void resetHWord(){
		setX(start);
		word = dict.getNewWord();
		shifted = false;
		fallingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait); 
		collidedWords.set(0);
	}
	
	public synchronized boolean matchWord(String typedText) {
		//System.out.println("Matching against: "+text);
		if (typedText.equals(this.word)) {
			//resetWord();
			return true;
		}
		else
			return false;
	}

	public synchronized  void drop(int inc) {
		setY(y+inc);
	}
	
	public synchronized  boolean dropped() {
		return dropped;
	}	
//changed
	public synchronized  void shiftRight(int inc) {
		setX(x+inc);
	}
	
//change
	public synchronized  boolean shifted() {
		return shifted;
	}

}
