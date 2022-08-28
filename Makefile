JAVAC=/usr/bin/javac
.SUFFIXES: .java .class
SRCDIR=src/typingTutor
BINDIR=bin
JAVADOC=Javadocs

# User defined arguements
# Enter <totalWords> <noWords> <DictionaryTextFile>
# Example: 20 3 words.txt
ARGS = 	20 3 words.txt

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
	rm $(BINDIR)/typingTutor/*.class
