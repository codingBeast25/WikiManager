/**
 * Class: Document
 * Author: Kabir Bhakta
 * Student Number: 7900098
 * Purpose: This class is used to manipulate and store Document data and information.
 */
public class Document extends ListItem
{
    //Instance Variables
    private String docName; //documnet Name
    private User user;      //User who requested a command
    private int lineCount;  //total lines in the document
    private List content;   //Contents of the document   
    private Content line;   //Single line of text
   // private ReportItem report;  //Report
    List documentRepo;      //all the edits made to this document is stored in this list in order of time and with the command executed

    //Constructor
    public Document(String name, User newUser){
        docName = name;
        user = newUser;
        content = new List();
        lineCount = 0;
        line = new Content("");
        documentRepo = new List();
    }

    //Dummy constructor just for creating a dummy object used for checking the presence of the document in the directory
    public Document(){
        docName = "";
        user = null;
    }

    //Returns document name
    public String getName(){
        return docName;
    }

    /*Returns true if the document name is same as the other document's name passed as parameter
     * Returns false if vice-versa
     * Parameter: ListItem which is later casted to document
     */
    public boolean equals (ListItem item){
        if(item.getName().equals(docName))
            return true;
        else
            return false;
    }

    //Returns Total lines in the document
    public int getTotalLine(){
        return lineCount;
    }

    //Returns User object of this document
    public User getUser(){
        return user;
    }

    /*Returns the line of the document which is requested
     * Returns string
    
     * For eg: Document: OO: Powerful
     *                  0.Hi
     * if i call document.getContent(0) then it will return "Hi"
     */
    public String getContent(int lineCount){
        List.ListIterator curr = content.iterator();
        String str = "";
        int tempCount = 0;
        while(curr.hasNext()){

            Content currLine = (Content) curr.next();
            if(tempCount == lineCount){
                str += currLine.getName();
                break;
            }
            tempCount++;
        }
        return str;
    }

    //Adds the given string to end of the document
    //Called by ProcessCommand()
    public boolean append(String str){
        line = new Content(str);
        content.insert(line);
        lineCount++;
        return true;
    }

    
    //Replaces the given line number with new String
    //Called by ProcessCommand()
    public boolean replace(int lineNum,String str){
        boolean operation = false;
        Content newLine = new Content(str);

        if( lineNum <= lineCount){
            Node curr = content.getHead();
            int tempCount = 0;
            while(curr != null){

                if(tempCount == lineNum){
                    curr.setData(newLine);
                    operation = true;

                    break;
                }
                tempCount ++;
                curr = curr.getNext();
            }
        }
        return operation;
    }

    //Deletes given line from the Document
    //Called by ProcessCommand()
    public boolean delete(int lineNum){
        boolean operation = false;
        if( lineNum <= lineCount ){
            List.ListIterator itr = content.iterator();
            int tempCount = 0;
            while(itr.hasNext()){
                Content currLine = (Content) itr.next();
                if(tempCount == lineNum){
                    content.remove(currLine);
                    lineCount--;
                    operation = true;
                    break;
                }
                tempCount++;
            }
        }
        return operation;
    }

    //Prints the content of the document
    public void print(){
        System.out.println("\n--------------------------------\nDocument Name: "+docName);
        int lineNum = 0;
        List.ListIterator itr = content.iterator();
        while(itr.hasNext()){
            Content currLine = (Content) itr.next();
            System.out.println(lineNum + "." + currLine.getName());
            lineNum++;
        }
        System.out.println("--------------------------------");
    }

    //aDDS the report to document report History
    public void addReport(ReportItem repo){
        documentRepo.insert(repo);
    }

    //Prints all the edit made to the document
    public String getReport(){
        String str = "";
        List.ListIterator curr = documentRepo.iterator();
        String header = "\nHistory of Document: "+docName+"\n------------------------------\n";
        while(curr.hasNext()){
            ReportItem currReport = (ReportItem)curr.next();
            str += currReport.printDHistory();  //call to ReportItem() class
        }
        String footer = "------------------------------\n";
        return header + str + footer;
    }

    //Returns the entire history list
    public List getReportList(){
        return documentRepo;
    }

    //Clones the document without affecting the data of each other
    public Document clone(){
        Document newDoc = new Document();

        newDoc.docName = this.docName;
        newDoc.user = this.user;
        newDoc.lineCount = this.lineCount;
        newDoc.content = this.content.clone();
        newDoc.line = this.line.clone();
        newDoc.documentRepo = this.documentRepo.clone();

        return newDoc;
    }

    /*
     * This method restores the document to the time requested, which means
     * it makes the document look like it was at the given time
     */
    public boolean findReportDoc(int restoreTime,int time){
        
        int whichDocTime = findReportTime(restoreTime); //What time to restore our document to
        Document returnDoc;
        
        List.ListIterator curr = documentRepo.iterator();
        boolean found = false;
        
        if( whichDocTime != -1){    //if document was created before or on that time
            
            while(curr.hasNext()){
                
                ReportItem reportToFind = (ReportItem) curr.next();
                
                if(reportToFind.getTime() == whichDocTime){
                    //Clone the entire document.
                    returnDoc = reportToFind.getDocument().clone();
                    this.docName = returnDoc.getName();
                    this.user = returnDoc.getUser();
                    this.lineCount = returnDoc.getTotalLine();
                    this.content = returnDoc.content;
                    this.documentRepo = returnDoc.documentRepo;
                    
                    found = true;
                    break;
                }

            }
        }
        return found;
    }

    
    /*This is the helper method to our FindReportDoc method,
     * This returns the int time at which the document should be restored to from our document history
     * There is a difference between timeToRestore(passed by the user) and returnTime 
     * ,
     * timeToRestore is passed by user on which time he wants the document to restore,
     * and this timeToRestore can be if the document existed or not or a praticular command on this document was executed or not.
     * returnTime is time we get from document history, it returns the time of the command that was executed on the document at restoreToTime from the history list,
     * and if command was not executed at restoreTime time then look for the time where the latest edit was made to document in the histoy list
     */
    private int findReportTime(int time){
        
        List.ListIterator curr = documentRepo.iterator();
        ReportItem first = (ReportItem)documentRepo.getHead().getData();
        
        int returnTime = -1;    //if document was not created at time
        
        int smallest = first.getTime();//first edit made to doc
        while(curr.hasNext()){
            
            ReportItem reportToFind = (ReportItem) curr.next();
            int currTime = reportToFind.getTime();      //time of the current report
            
            if(currTime == time){
                returnTime = currTime;
                break;
            }
            
            else if(currTime > smallest && currTime < time){
                returnTime = currTime;
            }

        }
        return returnTime;
    }

}
