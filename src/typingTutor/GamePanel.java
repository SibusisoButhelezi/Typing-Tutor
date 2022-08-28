package typingTutor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
		private AtomicBoolean done ; //REMOVE
		private AtomicBoolean started ; //REMOVE
		private AtomicBoolean won ; //REMOVE

		private FallingWord[] words;
		private FallingWord hungryWord;
		private int noWords;
		private final static int borderWidth=25; //appearance - border
		private static Score score; //user score
		static Color fontColor;
		static Color rectangleBackground;

		GamePanel(FallingWord[] words, int maxY,	
				 AtomicBoolean d, AtomicBoolean s, AtomicBoolean w, FallingWord hungryWord, Score score, Color font, Color background) {
			this.words=words; //shared word list
			noWords = words.length; //only need to do this once
			done=d; //REMOVE
			started=s; //REMOVE
			won=w; //REMOVE
			this.hungryWord = hungryWord;
			this.score = score;
			rectangleBackground = background;
			fontColor = font;
		}
		
		public void paintComponent(Graphics g) {
		    int width = getWidth()-borderWidth*2;
		    int height = getHeight()-borderWidth*2;
		    //g.clearRect(borderWidth,borderWidth,width,height);//the active space
			// g.clearRect(0, 0, getWidth(), getHeight());
			g.setColor(rectangleBackground);
			g.fillRect(0, 0, getWidth(), getHeight());
		    g.setColor(Color.pink); //change colour of pen
		    g.fillRect(borderWidth,height,width,borderWidth); //draw danger zone

		    g.setColor(fontColor);
		    g.setFont(new Font("Arial", Font.PLAIN, 26));
		   //draw the words
		    if (!started.get()) {
		    	g.setFont(new Font("Arial", Font.BOLD, 26));
				g.drawString("Type all the words before they hit the red zone,press enter after each one.",borderWidth*2,height/2);	
		    	
		    }
		    else if (!done.get()) {
				// draw hungryword
				g.setColor(new Color(11,218, 18));
				g.drawString(hungryWord.getWord(), hungryWord.getX(), hungryWord.getY());
				
				// draw words
				g.setColor(fontColor);
		    	for (int i=0;i<noWords;i++){	    	
		    		g.drawString(words[i].getWord(), words[i].getX()+borderWidth, words[i].getY());	
		    	}		    	
				
				// get dimensions of the hungry word 
				FontMetrics fm = g.getFontMetrics(new Font("Arial", Font.BOLD, 26));
				int Hlength = fm.stringWidth(hungryWord.getWord());
				int Hheight = fm.getHeight();

				for (int i=0;i<noWords;i++){	    	
					int Wlength = fm.stringWidth(words[i].getWord()); // width of each word

					// check for collision between word and hungry word
					if (words[i].getX() <= hungryWord.getX() + Hlength &&
						words[i].getX() + Wlength >= hungryWord.getX() &&
						words[i].getY() <= hungryWord.getY() + Hheight &&
						words[i].getY() + Hheight >= hungryWord.getY()){
								score.missedWord();
								words[i].resetWord();
					}
		    	}
		    	g.setColor(Color.lightGray); //change colour of pen
		    	g.fillRect(borderWidth,0,width,borderWidth);
		   }
		   else { if (won.get()) {
			   g.setFont(new Font("Arial", Font.BOLD, 36));
			   g.drawString("Well done!",width/3,height/2);	
		   } else {
			   g.setFont(new Font("Arial", Font.BOLD, 36));
			   g.drawString("Game over!",width/2,height/2);	
		   }
		   }
		}
		
		public int getValidXpos() {
			int width = getWidth()-borderWidth*4;
			int x= (int)(Math.random() * width);
			return x;
		}
		
		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(10); 
				} catch (InterruptedException e) {
					e.printStackTrace();
				};
			}
		}

	}


