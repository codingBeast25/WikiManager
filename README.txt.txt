/* Name: Kabir Bhakta
* Student Number: 7900098
* Program: Wiki manager
* How to run: follow the procedure below.
*Additional; info: Output of my program is in output.txt
*/

1)Unzip folder
2)Transfer all the .java files into another folder including the .txt files required for using
  in the program EXCEPT the .jar and the Testfile(called TestWikiManager.java).
3)Compile all the .java files using,
	 javac *.java
4) If you want to run and check the output with test data.txt file provided by professor then run using,
	java Main
5) On prompt enter "data.txt"or any file you want but with .txt extension to run the entire program.(Also refer to NOTE below)
6)After all the files compile, now bring the .jar and TestWikiManager.java into the same folder.
7)Compile using   
	javac -cp .:junit-platform-console-standalone-1.6.0.jar TestWikiManager.java
8)For Running the testing, 
	java -jar junit-platform-console-standalone-1.6.0.jar --class-path . --scan-class-path

NOTE: Make sure .txt file that you are using to run the program is in the same directory and dont forget to include .txt extension while input.
Thank You