
/**
 * Class: User
 *
 * @Author: Kabir Bhakta
 * @Student Num: 7900098
 * @Purpose: This class is used to manipulate and store user data and information.
 */
public class User extends ListItem
{
    //instance variables
    private String usrId;   //user id
    List reportList;   // all edits user made
    ReportItem report;  //report of the edits
    
    //constructor
    public User( String id ){
        usrId = id;
        reportList = new List();
    }
    
    //returns the string of user id
    public String getName(){
        return usrId;
    }
    
    /*
     * This method used to compare user id
     * Returns true if both the ids are same
     * Returns false if both the ids are distinct
     */
    public boolean equals(ListItem item) {
        boolean found = false;  //return variable
        
        if( usrId.equals(item.getName()) ){
            found = true;
        }
        return found;
    }
    
    //adds report to reportList
    public void addReport(ReportItem repo){
        reportList.insert(repo);
    }
    
    //prints our report list used for USERREPORT command
    public String getReport(){
        String str = "";
        List.ListIterator curr = reportList.iterator();
        String header = "Report for userID: "+usrId+"\n------------------------------\n";
        while(curr.hasNext()){
            ReportItem currReport = (ReportItem)curr.next();
            str += currReport.printUReport();
            
        }
        String footer = "\n------------------------------\n";
        return header + str + footer;
    }
    
    //CLone the user
    public User clone(){
        return new User(usrId);
    }

}
