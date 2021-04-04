
/**
 * Class: List
 *
 * @Author: Kabir Bhakta 7900098
 * @Purpose: Linked List data structure implementation
 * All our ListItem type data will be stored in this list no other data type
 */
public class List
{
    // instance variables
    private Node head; //head
    private int size;   //size of the list

    //Constructor
    public List()
    {
        head = null;
        size = 0;
    }

    //Accessor for head
    public Node getHead() { return head; }

    //Accessor for number of objects in the list
    public int getSize() { return size; }

    /*
     * Inserts the given item at the end of the list
     * Returns true if the item is inserted successfully,
     * Return false if the item is already present.
     */
    public boolean insert (ListItem item){

        boolean inserted = false;   //return variable

        if( contains(item) == null ){
            //create a new Node
            Node newNode = new Node(item, null);
            inserted = true;

            if(head == null)
                head = newNode;
            else{
                //list traversal
                Node curr = head;
                while(curr.getNext() != null){
                    curr = curr.getNext();
                }
                curr.setNext(newNode);

            }
            size ++;
        }
        return inserted;

    }

    //This method removes the contents from the Document not used for ReportItem class
    public boolean remove (ListItem item){
        boolean removed = false;

        if (contains(item) != null){    //if item is present in the list
            Node curr = head;
            Node prev = null;
            if(curr.getNext() == null){ //if the item is the head of the list

                head = null;
                removed = true;
                size--;
            }
            else{   //for all other items other than head location
                while ( curr != null){

                    if ( item.getName().equals(curr.getData().getName())){
                        if(curr == head){
                            head = curr.getNext();
                            curr.setNext(null);
                            removed = true;
                            size--;
                            break;
                        }
                        else{
                            prev.setNext(curr.getNext());
                            curr = prev.getNext();
                            removed = true;
                            size--;
                            break;
                        }
                    }
                    prev = curr;
                    curr = curr.getNext();
                }
            }
        }
        return removed;
    }

    /*
     * Contains method will help us check if the current item is present in the list or not.
     * Returns item if found
     * returns null if not found
     */
    public ListItem contains(ListItem itemName){

        ListItem foundItem = null;
        Node curr = head;

        while(curr != null){
            ListItem currData = curr.getData();
            if (currData.equals(itemName)){
                foundItem = curr.getData();
                break;
            }
            curr = curr.getNext();
        }
        return foundItem;
    }

    //Used to print the list ofd ItemList type
    public String printList() {
        Node curr = head;
        String str = "";
        while (curr != null){
            str = str + curr.getData().getName();
            curr = curr.getNext();
        }
        return str+"\n";
    }
    
    //Helps to make a deep clone of the list
    public List clone(){
        Node old = getHead();
        List newList = new List();
        while (old != null){
            Node newN = old.clone();
            newList.insert(newN.getData());
            old = old.getNext();
        }
        return newList;
    }

    /*  
     * --------------------------Iterator class----------------------
     */

    public class ListIterator
    {
        private Node curr;  //next location in list

        //constructor
        public ListIterator() {
            curr = head;
        }

        //does list have next item?
        public boolean hasNext() { return curr != null; }

        //get next item from the list
        public ListItem next(){
            Node temp = curr;
            curr = curr.getNext();
            return temp.getData();
        }

    }

    public ListIterator iterator() {
        return new ListIterator();
    }
}
