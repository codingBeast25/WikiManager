
/**
 * Author: Kabir Bhakta
 * Student Number: 7900098
 * Purpose: This class gets the input from the file increments the time and calls and execute the respective process by calling methods in ProcessCommand class
 */
import java.io.*;
import java.util.*;

public class ProcessInput
{

    public static int time; //our global time
    
    ProcessCommand commandTool = new ProcessCommand();
    
    //COnstructor
    public ProcessInput(){
        time = 0;
    }

    //This method interacts with the user and guides the entire project in respective direction
    public void inputScanner(){

        Scanner s = new Scanner(System.in);
        System.out.println("Enter File name with .txt extension: ");
        String input = s.nextLine();

        //File Processing
        File file = new File(input);
        BufferedReader reader;
        
        String[] parts; //used for split()

        try{

            reader = new BufferedReader(new FileReader(file));

            String line = reader.readLine();

            while( line != null){

                if(! line.startsWith("#")){ //if the statment is not a comment
                    line= line.strip();
                    parts = line.split(" ");
                    processMethod(parts);
                    time++;
                }

                line = reader.readLine();
                if( line == null){
                    System.out.println("QUIT COMMAND IS MISSING");
                }
            }

            reader.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //This method performs the respective method according to command in text file.
    //Takes array of line which is split as parameter
    //Call to ProcessCommand class() methods
    private void processMethod(String[] arr){

        if(arr[0].equals("USER")){
            commandTool.userCommand(arr[1],time);
        }
        else if(arr[0].equals("CREATE")){
            commandTool.createCommand(arr[1],arr[2],time);
        }
        else if(arr[0].equals("APPEND")){
            commandTool.appendCommand(arr,time);
        }

        else if(arr[0].equals("REPLACE")){

            commandTool.replaceCommand(arr,time);
        }

        else if(arr[0].equals("DELETE")){
            commandTool.deleteCommand(arr[1].trim(),arr[2].trim(),arr[3].trim(),time);
        }

        else if(arr[0].equals("PRINT")){
            commandTool.printCommand(arr[1].trim());
        }

        else if(arr[0].equals("RESTORE")){
            commandTool.restoreCommand(arr[1].trim(),arr[2].trim(),arr[3].trim(),time);
        }

        else if(arr[0].equals("HISTORY")){
            commandTool.historyCommand(arr[1].trim());
        }
        else if(arr[0].equals("USERREPORT")){
            commandTool.uRepoCommand(arr[1].trim());
        }
        else if(arr[0].equals("QUIT")){
            System.out.println("BYE");
            System.exit(0);
        } 
        else{
            System.out.println("Command invalid!");
        }
    }

}
