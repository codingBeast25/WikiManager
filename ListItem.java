
/**
 * Class: ListItem
 *
 * Author: Kabir Bhakta 7900098
 * Purpose: Parent class for Document,User,Content,ReportItem 
 */
public abstract class ListItem
{
    public abstract boolean equals (ListItem item);

    public abstract String getName();
    
    public abstract ListItem clone();

    public String toString(){ return super.toString(); }
}

