
/**
 * Class: Node
 *
 * @Author: Kabir Bhakta 7900098
 * @Purpose: becomes the nodes for different objects for our linked list data structure
 */
public class Node
{
    //Instance variables
    private ListItem data;
    private Node next;
    
    //Constructor
    public Node(ListItem newData, Node newNext)
    {
        data = newData;
        next = newNext;
    }
    
    //retrive data
    public ListItem getData()
    {
        return data;
    }
    
    public void setData(ListItem item){
        data = item;
    }
    
    //retrive next pointer
    public Node getNext()
    {
        return next;
    }
    
    //set new next pointer
    public void setNext(Node newNext)
    {
        next = newNext;
    }

    public Node clone(){
        return new Node(this.getData(),this.getNext());
    }
    
}
