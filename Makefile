JAVAC=/usr/bin/javac
.SUFFIXES: .java .class
SRCDIR=src/typingTutor
BINDIR=bin
JAVADOC=Javadocs

# User defined arguements
# Enter <inputImageName> <outputImageName> <windowWidth>
# Example: Image1.jpg Image1Out.jpg 3
ARGS = Image3.jpg Image3OutMedianP.jpg 11

 ARGSALL = $(ARGSALL1)




$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<	
	
CLASSES = WordDictionary.class FallingWord.class Score.class WordMover.class GamePanel.class \
			ScoreUpdater.class CatchWord.class TypingTutorApp.class 
			
CLASS_FILES = $(CLASSES:%.class=$(BINDIR)/%.class)
default: $(CLASS_FILES) 

docs: 
	javadoc -d Javadocs $(SRCDIR)/*.java

# Running user input

runMeanFilterSerial: $(CLASS_FILES)
	java -cp bin MeanFilterSerial $(ARGS) 

runMedianFilterSerial: $(CLASS_FILES)
	java -cp bin MedianFilterSerial $(ARGS)

runMeanFilterParallel: $(CLASS_FILES)
	java -cp bin MeanFilterParallel $(ARGS) 

runMedianFilterParallel: $(CLASS_FILES)
	java -cp bin MedianFilterParallel $(ARGS) 

clean:
	rm $(BINDIR)/*.class
