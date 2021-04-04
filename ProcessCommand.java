
/**
 * Author: Kabir Bhakta
 * Student number: 7900098
 * Purpose: All the main processing and method calls to other classes are done here. We can say this is the centre of operation area ;)
 */
public class ProcessCommand
{
    private List DOC_LIST;  //Document directory. All the docs that are created in the input file are stored here-
    private List USER_LIST; //User directory. All the users that are created in input file are stored here.
    private ReportItem report;  //Creates a report for respective commands
    private List history;   //Holds all the history of commands in order they were performed from the input file

    //Constructor
    public ProcessCommand(){
        DOC_LIST = new List();
        USER_LIST = new List();
        history = new List();
    }

    //This method processes information for user command and prints appropriate output
    public void userCommand(String userName,int time){
        userName = userName.trim();
        boolean inserted = USER_LIST.insert(new User(userName));

        if(inserted){     
            System.out.println("\nCONFIRMED: User "+userName+" created.");
        }
        else{
            System.out.println("\nDUPLICATE: "+userName+" already exist.");
        }
    }

    //This method processes when create command is executed and prints appropriate output 
    public void createCommand(String docName,String userName,int time){
        userName = userName.trim();
        docName = docName.trim();
        User usr = new User(userName);          //temp objects to check,
        Document d = new Document(docName,usr); //if the user and document are present in our directories or not

        //if the user is not created yet
        if(USER_LIST.contains(new User(userName)) == null){

            System.out.println("\nNOT FOUND: User "+userName+" is not created yet.");
        }
        else{

            Document doc = (Document)DOC_LIST.contains(d);  //get the document object from the directory
            User user = (User)USER_LIST.contains(usr);      //get user object

            if(doc == null){                                //if document not created yet
                doc = new Document(docName,user);           //create new document 

                DOC_LIST.insert(doc);   

                report = new ReportItem("CREATE",doc,user,time);

                //Insert the report in particular user obj,document obj,and history list
                doc.addReport(report);
                user.addReport(report);
                history.insert(report);

                System.out.println("\nCONFIRMED: Document: "+docName+" by "+userName+" created successfully");
            }
            else{   //document is already created
                System.out.println("\nDUPLICATE: Document: "+docName+" is already created by "+doc.getUser().getName());
            }

        }

    }

    //Appends the given string at the end of the document
    public void appendCommand(String[] arr,int time){
        String userName = arr[2].trim();
        String docName = arr[1].trim();
        //temp objects to check,if the user and document are present in our directories or not
        User usr = new User(arr[2]);
        Document d = new Document(arr[1],usr);

        //get the user and document object from directories
        Document doc = (Document)DOC_LIST.contains(d);
        User user = (User)USER_LIST.contains(usr); 

        //Get the string to append
        String str = "";
        for(int i = 3;i < arr.length;i++){
            str += arr[i] + " ";
        }
        str = str.trim();

        if(doc == null){    //document not created yet
            System.out.println("\nNOT FOUND: Document "+arr[1]+" is not created yet.");
        }
        else if(user == null){  //user not created yet
            System.out.println("\nNOT FOUND: User "+arr[2]+" is not created yet.");
        }
        else if(doc instanceof Document){
            Document old = doc.clone();
            doc.append(str);    //call to append() in Document class which appends to the end of the document

            report = new ReportItem("APPEND",old,doc,user,str,time);    //create new report

            //Insert the report in particular user obj,document obj,and history list
            doc.addReport(report);
            user.addReport(report);
            history.insert(report);

            System.out.println("\nSUCCESS: "+ str+" appended successfully to "+arr[1]);
        }
    }

    /*
     * Replaces the given line to the document with line number specified
     * Parameter: arr(which stores info about command), time at which it was invoked
     */
    public void replaceCommand(String[] arr,int time){
        String userName = arr[2].trim();
        String docName = arr[1].trim();
        
        //temp objects to check,if the user and document are present in our directories or not
        User usr = new User(arr[2]);
        Document d = new Document(arr[1],usr);

        //get the user and document object from directories
        Document doc = (Document)DOC_LIST.contains(d);
        User user = (User)USER_LIST.contains(usr); 

        //Get the string to replace with
        String str = "";
        for(int i = 4;i < arr.length;i++){
            str += arr[i] + " ";
        }
        str = str.trim();
        String num = arr[3].trim();
        int lineToReplace = Integer.parseInt(num);   //line number

        if(doc == null){
            System.out.println("NOT FOUND: Document "+arr[1]+" is not created yet.");
        }
        else if(user == null){
            System.out.println("NOT FOUND: User "+arr[2]+" is not created yet.");
        }
        else if(doc.getTotalLine()-1 < lineToReplace){
            System.out.println("FAILED: This line is not present.");
        }
        else{
            Document oldDoc = doc.clone();  //clone the document before replacing

            doc.replace(lineToReplace,str); //replace method() from document class

            //create new Report and insert into documentRepo,userRepo,and history list
            report = new ReportItem("REPLACE",oldDoc,doc,user,str,lineToReplace,time);
            doc.addReport(report);
            user.addReport(report);
            history.insert(report);

            System.out.println("SUCCESS: "+oldDoc.getContent(lineToReplace)+" is replaced by "+doc.getContent(lineToReplace));
        }
    }

    /*
     * deletes the line number specified from the document
     * Parameter: docName,userName,lineNum,current time
     */
    public void deleteCommand(String docName,String userName, String lineNum,int time){
        //temp objects to check,if the user and document are present in our directories or not
        //get the user and document object from directories
        User usr = new User(userName);
        Document d = new Document(docName,usr);
        Document doc = (Document)DOC_LIST.contains(d);
        User user = (User)USER_LIST.contains(usr); 

        int lineToDel = Integer.parseInt(lineNum);

        if(doc == null){
            System.out.println("\nNOT FOUND: Document "+docName+" is not created yet.");
        }
        else if(user == null){
            System.out.println("\nNOT FOUND: User "+userName+" is not created yet.");
        }
        else {
            Document old = doc.clone();
            boolean deleted = doc.delete(lineToDel);    //deleteMethod in DOcumetn class
            if(deleted){ 
                //create new Report and insert into documentRepo,userRepo,and history list
                report = new ReportItem("DELETE",old,doc,user,null,lineToDel,time);
                doc.addReport(report);
                user.addReport(report);
                history.insert(report);

                System.out.println("\nSUCCESS: Line Number "+lineNum+" from "+docName+" is deleted.");
            }
            else{
                System.out.println("\nFAILED: Line Number "+lineNum+" in "+docName+" does not exist.");
            }
        }
    }

    /*
     * Restores the version of document that it was at a given time
     * if document wasnt edited at that time then restore the document to latest version before the given time
     * if document was edited at that time then perfrom the command and then restore it.
     * if document wasnt created at that time then print appropriate message
     */
     public void restoreCommand(String userName , String docName, String timeToRestore, int currTime){
        //temp objects to check,if the user and document are present in our directories or not
        //get the user and document object from directories
        User usr = new User(userName);
        Document d = new Document(docName,usr);
        Document doc = (Document)DOC_LIST.contains(d);
        User user = (User)USER_LIST.contains(usr); 

        int restoreTime = Integer.parseInt(timeToRestore);  //whhcih time to restore the document to

        if( restoreTime > currTime ){   //future time inputted
            System.out.println("\nWrong time requested, as time "+restoreTime+" didnt pass yet.Dont go to Future come Back."); 
        }
        else{
            if(doc == null){
                System.out.println("\nNOT FOUND: "+docName+" is not created yet.");
            }
            else if(user == null){
                System.out.println("\nNOT FOUND: User "+userName+" is not created yet.");
            }
            else{
                Document old = doc.clone();
                boolean found = doc.findReportDoc(restoreTime,currTime);    //method call to document clss which restores the document to previous version

                if( !found){    //doc was not created yet
                    System.out.println("\nNOT FOUND: "+docName+" was not created at or before "+restoreTime+" yet.");
                }
                else{
                    //create new Report and insert into documentRepo,userRepo,and history list
                    report = new ReportItem("RESTORE",old,doc,user,restoreTime,currTime);
                    doc.addReport(report);
                    user.addReport(report);
                    history.insert(report);

                    System.out.println("\nSUCCESS: "+docName+" is restored.");
                }
            }
        }
    }

    /*
     * Print the total history of command executed on this document.
     * Parameter: docName
     */
    public void historyCommand(String docName){
        //temp objects to check,if the document is present in our directory or not
        //get the document object from directory
        Document d = new Document(docName,null);
        Document doc = (Document)DOC_LIST.contains(d);

         if( doc == null){
            System.out.println("\nNOT FOUND: "+docName+" does not exists in Document Directory.");
        }
        else{
            
            String body = doc.getReport();  //getReport() call from Document class
            System.out.println("\n"+body);

        }
    }

    //Prints all the edits made by user
    public void uRepoCommand(String userName){
        User usr = new User(userName);
        User user = (User)USER_LIST.contains(usr);
        
        if(user == null){
            System.out.println("\nNOT FOUND: "+userName+" does not exists in User Directory.");
        }
        else{
            String title = userName;
            String body = user.getReport(); //getReport() from User class
            System.out.println("\n"+body);
        }
    }
    
    //prints the document with its content and line number
    public void printCommand(String docName){
        Document d = new Document(docName,null);
        Document doc = (Document)DOC_LIST.contains(d);
        if(doc == null){
            System.out.println("\nNOT FOUND: Document "+docName+" is not created yet.");
        }
        else{
            doc.print();
        }
    }

}
