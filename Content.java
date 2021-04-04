
/**
 * Name: Kabir Bhakta
 * Student Number: 7900098
 * Purpose: just create an Object to store as string in our contents of Document
 */
public class Content extends ListItem
{
    private String strContent;
    
    //Constructor
    public Content(String newContent){
        strContent = newContent;
    }
    
    public String getName(){
        return strContent;
    }
    
    public boolean equals(ListItem item){
        return item.getName().equals(strContent);
    }
    
    public Content clone(){
        return new Content(strContent);
    }
}
