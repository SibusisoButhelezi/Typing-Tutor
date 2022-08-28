# CSC2002S_Assignment_2

src/typingTutor = Contains source code
bin/typingTutor = Contains compiled code and the .txt files to be used as dictionaries

Running the Game:
There are 2 ways to run the TypingTutor game while being able to pass in parameters from the terminal

Method 1 - make run:
	Add your .txt dictionary files into this working directory
	In the make file, change the ARGS assigned value with the following :
		<totalWords> <noWords>		OR
		<totalWords> <noWords> <dictionaryTextFile>		OR
		delete/comment out assigned values
	Save the makefile and run "make run" in the terminal

Method 2 - java command:
	Run "make" to compile the .java files
	Change the current working directory to /bin
	Add your .txt dictionary files into this working directory
	In the terminal, run:
		"java typingTutor.TypingTutorApp"	OR
		"java typingTutor.TypingTutorApp <totalWords> <noWords>"	OR
		"java typingTutor.TypingTutorApp <totalWords> <noWords> <dictionaryTextFile>"
