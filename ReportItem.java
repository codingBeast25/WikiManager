
/**
 * Author: Kabir Bhakta
 * Student Number: 7900098
 * Purpose: This class creates a report of the particular command
 */
public class ReportItem extends ListItem
{
    //Instance Variable
    String command; //command: create,append,delete,replace,restore
    Document oldDoc;    //Document before the edit was made
    Document newDoc;    //Document after the edit was made
    User user;          
    int time;           //curr Global time
    String line;        //String
    int lineNum;        //line Num to delete or replace
    int restoreTime;    //restore time
    
    //Constructor for Delete and Replace Command
    public ReportItem(String newCommand, Document oldDoc,Document newDoc, User newUser,String newLine,int newLineNum, int currTime){
        command = newCommand;
        this.oldDoc = oldDoc.clone();
        this.newDoc = newDoc.clone();
        user = newUser;
        time = currTime;
        line = newLine;
        lineNum = newLineNum;
    }
    
    //Constructor for Create command
    public ReportItem(String newCommand, Document newDoc,User newUser,int currTime){
        command = newCommand;
        this.newDoc = newDoc.clone();
        user = newUser;
        time = currTime;
    }

    //Constructor for append Command
    public ReportItem(String newCommand, Document oldDoc,Document newDoc, User newUser,String newLine, int currTime){
        command = newCommand;
        this.oldDoc = oldDoc.clone();
        this.newDoc = newDoc.clone();
        user = newUser;
        time = currTime;
        line = newLine;
    }
    
    //Constructor for Restore Command
    public ReportItem(String newCommand, Document oldDoc,Document newDoc, User newUser, int newRestoreTime, int currTime){
        command = newCommand;
        this.oldDoc = oldDoc.clone();
        this.newDoc = newDoc.clone();
        user = newUser;
        restoreTime = newRestoreTime;
        time = currTime;
    }

    //get the name of the command, returns string
    public String getName(){
        return command;
    }

    //Reurns the string report of the document
    public String printDHistory(){
        String returnStr = "";
        String bullet = "-> ";
        if(command.equals("CREATE")){
            returnStr += bullet + command+" performed by "+user.getName()+"\n";
        }
        else if(command.equals("APPEND")){
            returnStr += bullet + command+ " performed by "+user.getName()+", Line APPENDED was: "+line+"\n";
        }
        else if(command.equals("REPLACE")){
            returnStr += bullet + command+ " performed by "+user.getName()+","+lineNum+". "+oldDoc.getContent(lineNum)+" was REPLACED by "+line+"\n";
        }
        else if(command.equals("DELETE")){
            returnStr += bullet + command + " performed by "+user.getName()+", Line number "+lineNum+" was DELETED.\n";
        }
        else if(command.equals("RESTORE")){
            returnStr += bullet + command + " performed by "+user.getName()+", "+oldDoc.getName()+" was restored to time "+getRestoreTime()+"\n";
        }

        return returnStr;
    }

    //Returns the string report for the user
    public String printUReport(){
        String returnStr = "";
        String bullet = "-> ";
        if(command.equals("CREATE")){
            returnStr += bullet + "CREATED "+newDoc.getName()+"\n";
        }
        else if(command.equals("APPEND")){
            returnStr += bullet + line + " was APPENDED to "+oldDoc.getName()+".\n";
        }
        else if(command.equals("REPLACE")){
            returnStr += bullet + lineNum+"."+oldDoc.getContent(lineNum) + " was REPLACED by " + line + " in " + oldDoc.getName()+".\n";
        }
        else if(command.equals("DELETE")){
            returnStr += bullet + lineNum+"."+oldDoc.getContent(lineNum)+ " was DELETED in " + oldDoc.getName()+".\n";
        }
        else if(command.equals("RESTORE")){
            returnStr += bullet + "RESTORED "+oldDoc.getName()+ " to time "+getRestoreTime()+".\n";
        }

        return returnStr;
    }

    //Just to ensure polymorphism becuase we dont use equals of this method anywhere
    public boolean equals(ListItem item){
        return false;
    }

    public Document getDocument(){
        return newDoc;
    }

    public int getTime(){
        return time;
    }
    
    private int getRestoreTime(){
        return restoreTime;
    }

    //Just to follow polymorphism its just dummy
    public ReportItem clone(){
        return new ReportItem(null,null,null,null,0,0);
    }
}
