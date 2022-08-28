JAVAC=/usr/bin/javac
.SUFFIXES: .java .class
SRCDIR=src/typingTutor
BINDIR=bin
JAVADOC=Javadocs

# User defined arguements
# Enter <inputImageName> <outputImageName> <windowWidth>
# Example: Image1.jpg Image1Out.jpg 3
ARGS = 	50 3 #words.txt #same.txt

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<	
	
CLASSES = WordDictionary.class FallingWord.class Score.class WordMover.class HungryWordMover.class GamePanel.class \
			ScoreUpdater.class CatchWord.class TypingTutorApp.class 
			
CLASS_FILES = $(CLASSES:%.class=$(BINDIR)/%.class)
	
default: $(CLASS_FILES) 

docs: 
	javadoc -d Javadocs $(SRCDIR)/*.java

# Running user input

run: $(CLASS_FILES)
	java -cp bin typingTutor.TypingTutorApp $(ARGS)

clean:
	rm $(BINDIR)/*.class
