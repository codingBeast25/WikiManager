// CLASS: TestWikiManager...
//
// Author:  Kabir Bhakta
// Student number: 7900098
//
// Purpose:
//          Only for testing the whole program including the data structure
//          Mostly unit Testing
//-----------------------------------------
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TestWikiManager {

    /*--------------------------------------------------DATA STRUCTURE TESTING STARTS HERE-------------------------------------------------------------------*/
    @Test
    public void testCreateList() {
        List itemList = new List();
        //itemList should not be null since it has been instantiated already
        assertNotNull(itemList);
        //our head should be null as no data is entered yet
        assertNull(itemList.getHead());
    }

    @Test
    public void testInsertList() {
        //Test to see if the list can have instances of different classes;
        List myList = new List();
        User usr = new User("kabir");
        Document doc = new Document("COMP2150",usr);
        Content con = new Content("Hi World!");
        ReportItem report = new ReportItem("CREATE",doc,usr,0);
        User dupUser = new User("kabir"); //this is a duplicate of usr since it has the same userID.

        myList.insert(usr);
        myList.insert(doc);
        myList.insert(con);
        myList.insert(report);

        assertNotNull(myList.getHead(),"SHould not be null as objects are previously inserted.");
        assertEquals(4, myList.getSize(),"Since 4 Objects were inserted it should be 4.");
        assertEquals(usr.getName() + doc.getName()+ con.getName() + report.getName()+"\n", myList.printList(), "The output should match when printed to indicate they were really inserted in the order they were inserted");
        assertEquals(usr, myList.getHead().getData(), "usr was inserted first");

        //Try inserting the user which is already inserted
        myList.insert(dupUser);
        assertEquals(4, myList.getSize(), "Duplicate user was inserted which shouldnt have been inserted.");
    }

    @Test
    public void testGetListSize() {
        List myList = new List();

        assertEquals(0, myList.getSize(), "Nothing inserted to list yet. Size should be 0");

        User user = new User("kabir");
        Document doc = new Document("COMP2150", user);

        myList.insert(user);
        myList.insert(doc);

        assertEquals(2, myList.getSize(), "2 Objects are inserted in the List total should be 2.");

    }

    @Test
    public void testGetHead() {

        List myList = new List();
        User user = new User("kabir");
        User user1 = new User("mike");
        myList.insert(user);
        myList.insert(user1);

        assertEquals(user, myList.getHead().getData(), "user object should be returned because it was inserted first");
    }

    @Test
    public void testContains() {
        List myList = new List();
        User user = new User("kabir");
        Document doc = new Document("History",user);

        myList.insert(user);
        myList.insert(doc);

        assertEquals(user, myList.contains(user), "user should match which the object returned by contains method");
        assertEquals(doc, myList.contains(doc), "doc should match with the object returned by contains method");
        assertNull(myList.contains(new User("mike")), "Should return null as no user with userID mike was inserted");
    }

    @Test
    public void testGetData()
    {
        List myList=new List();

        User user = new User("kabir");
        Document doc = new Document("History",user);
        myList.insert(user);
        myList.insert(doc);

        assertEquals( user.getName(),myList.getHead().getData().getName(),"Should be equal to 'kabir'");

    }

    @Test
    public void testRemove(){
        List myList = new List();
        User user = new User("kabir");
        Document doc = new Document("History",user);
        User user1 = new User("mike");
        Document doc1 = new Document("compScience",user1);

        myList.insert(user);   myList.insert(doc);
        myList.insert(user1);   myList.insert(doc1);

        myList.remove(user1);
        assertEquals(3,myList.getSize(),"Should be 3 as we removed one object");
        assertNull(myList.contains(user1),"Should be null as we removed user1 from the list");
        assertEquals(myList.contains(user).getName()+myList.contains(doc).getName()+myList.contains(doc1).getName()+"\n",myList.printList(),
                "Order should chage after an element is deleted");
    }

    @Test
    public void testClone(){

        List myList = new List();
        User user = new User("kabir");
        Document doc = new Document("History",user);

        myList.insert(user);    myList.insert(doc);

        List clonedList = myList.clone();
        assertEquals(myList.contains(user),clonedList.contains(user),"Both objects should be same as we have cloned it.");
        assertEquals(myList.getSize(),clonedList.getSize(),"Size of both list should be same");
        myList.remove(doc);     myList.insert(new User("abc"));
        //checking if they are not pointing to each other, changes made to one should not affect changes made to other
        assertNotNull(clonedList.contains(doc),"doc should be present in clonedLIst");
        assertNotEquals(myList.printList(),clonedList.printList(),"Both have different contents");
    }
    /*--------------------------------------------------DATA STRUCTURE TESTING ENDS HERE-------------------------------------------------------------------*/

    /*--------------------------------------------------DOCUMENT CLASS TESTING STARTS HERE-------------------------------------------------------------------*/

    @Test
    public void testDoc(){
        Document doc = new Document("COMP2150",new User("Kabir"));
        assertNotNull(doc,"Object is instantiated so shouldn't be null");

        doc.append("Hi World!");
        doc.append("Assignment 1 in progress");
        assertEquals("Hi World!",doc.getContent(0),"Both of these lines should be equal");
        assertEquals(2,doc.getTotalLine(),"2 lines are inserted so total lines should be 2");

        Document doc1 = new Document("COMP2080",new User("Kabir"));
        assertFalse(doc.equals(doc1),"Both docs are different.");

        doc.replace(1,"Assignment 1 complete.");
        assertEquals("Assignment 1 complete.",doc.getContent(1),"Both of these lines should be equal");

        doc.delete(1);
        assertEquals(1,doc.getTotalLine(),"One line was deleted so total lines should be 1.");

        doc1 = doc.clone();
        assertNotEquals(doc,doc1,"Both should not be equal as they are different objects pointing to different things.");
        doc.delete(0);
        assertEquals("Hi World!",doc1.getContent(0),"Delete operation on doc shouldnt affect doc1.");
    }
    /*--------------------------------------------------DOCUMENT CLASS TESTING ENDS HERE-------------------------------------------------------------------*/

    /*--------------------------------------------------USER CLASS TESTING STARTS HERE-------------------------------------------------------------------*/
    @Test
    public void testUser(){
        User user = new User("Kabir");
        assertNotNull(user,"user obj is created already shouldnt be null");

        assertEquals("Kabir",user.getName(),"User id should be equal");

        User user1 = user.clone();
        assertNotEquals(user,user1,"Both obj points to seperate things.");
    }
    /*--------------------------------------------------USER CLASS TESTING STARTS HERE-------------------------------------------------------------------*/

    /*--------------------------------------------------REPORTITEM CLASS TESTING STARTS HERE-------------------------------------------------------------------*/
    @Test
    public void testReportItem(){
        User user = new User("kabir");
        Document doc = new Document("COMP2150",user);
        Document doc1 = doc.clone();
        ReportItem report = new ReportItem("CREATE",doc,user,0);

        assertNotNull(report,"Obj is already instantiated");

        doc.append("Hi World!");

        List repoList = new List();
        repoList.insert(report);

        ReportItem report1 = new ReportItem("APPEND",doc1,doc,user,"Hi World!",1);
        assertNotNull(report1,"Obj is already instantiated");
        repoList.insert(report1);

        assertEquals("CREATE",repoList.getHead().getData().getName(),"First command was to create so both should match.");


    }
    /*--------------------------------------------------REPORTITEM CLASS TESTING ENDS HERE-------------------------------------------------------------------*/

    /*--------------------------------------------------CONTENT CLASS TESTING STARTS HERE-------------------------------------------------------------------*/
    @Test
    public void testContent(){
        Content new1 = new Content("ABCD");
        assertNotNull(new1,"Obj is created already");

        Content new2 = new1.clone();
        assertNotNull(new2,"Obj is created already and cloned");

        assertEquals(new1.getName(),new2.getName(),"Both the string are same so this should be equals.");
        assertTrue(new1.equals(new Content("ABCD")),"Should be true.");

    }
    /*--------------------------------------------------CONTENT CLASS TESTING ENDS HERE-------------------------------------------------------------------*/
}
