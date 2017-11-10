
// Java core packages
import java.util.*;
import java.awt.*;

// Java extension packages
import javax.swing.*;

public class AddressBookEntryFrame extends JInternalFrame {

    // HashMap to store JTextField references for quick access
    private HashMap fields;

    // current AddressBookEntry set by AddressBook application
    private AddressBookEntry person;

    // panels to organize GUI
    private JPanel leftPanel, rightPanel;
    private JPanel leftPanel_2, rightPanel_2;

    // static integers used to determine new window positions
    // for cascading windows
    private static int xOffset = 0, yOffset = 0;

    // static Strings that represent name of each text field.
    // These are placed on JLabels and used as keys in
    // HashMap fields.
    private static final String FIRST_NAME = "First Name",
            LAST_NAME = "Last Name",
            ADDRESS1 = "Address 1", ADDRESS2 = "Address 2", CITY = "City", STATE = "State", EIRCODE = "Eircode",
            ADDRESS1_1 = "Address 1(2)", ADDRESS2_1 = "Address 2(2)", CITY_1 = "City(2)", STATE_1 = "State(2)", EIRCODE_1 = "Eircode(2)",
            ADDRESS1_2 = "Address 1(3)", ADDRESS2_2 = "Address 2(3)", CITY_2 = "City(3)", STATE_2 = "State(3)", EIRCODE_2 = "Eircode(3)",
            PHONE = "Phone", EMAIL = "Email",
            PHONE_1 = "Phone(2)", EMAIL_1 = "Email(2)",
            PHONE_2 = "Phone(3)", EMAIL_2 = "Email(3)";

    private int newAddressClickCount = 0;
    private int addPhoneNumbersClickCount = 0;
    private int addEmailAddressClickCount = 0;
    private int rowCount = 9;
    private int height = 300, width = 300;
    private Container container;

    // construct GUI
    public AddressBookEntryFrame()
    {
        super( "Address Book Entry", true, true );

        fields = new HashMap();

        leftPanel = new JPanel();
        leftPanel.setLayout( new GridLayout( rowCount, 1, 0, 5 ) );
        rightPanel = new JPanel();
        rightPanel.setLayout( new GridLayout( rowCount, 1, 0, 5 ) );

        leftPanel_2 = new JPanel();
        leftPanel_2.setLayout( new GridLayout( rowCount, 1, 0, 5 ) );
        rightPanel_2 = new JPanel();
        rightPanel_2.setLayout( new GridLayout( rowCount, 1, 0, 5 ) );

        createRow( FIRST_NAME , 1);
        createRow( LAST_NAME , 1);
        createRow( ADDRESS1 , 1);
        createRow( ADDRESS2 , 1);
        createRow( CITY , 1);
        createRow( STATE , 1);
        createRow( EIRCODE , 1);
        createRow( PHONE , 1);
        createRow( EMAIL , 1);

        container = getContentPane();

        GridLayout layout = new GridLayout(0,4);
        container.setLayout(layout);
        container.add(leftPanel);
        container.add(rightPanel);
        container.add(leftPanel_2);
        container.add(rightPanel_2);

        setBounds( xOffset, yOffset, width, height );
        xOffset = ( xOffset + 30 ) % 300;
        yOffset = ( yOffset + 30 ) % 300;
    }

    public void addPhone_Email(String selector) {
        int i=0;
        if(selector.equalsIgnoreCase("phone")) {
            addPhoneNumbersClickCount++;
            i = addPhoneNumbersClickCount;
        } else if(selector.equalsIgnoreCase("email")) {
            addEmailAddressClickCount++;
            i = addEmailAddressClickCount;
        }

        switch (i) {
            case 1:
                if(selector.equalsIgnoreCase("phone"))
                    createRow( PHONE_1 , 2);
                else if(selector.equalsIgnoreCase("email"))
                    createRow( EMAIL_1 ,2);
                rowCount += 1;
                leftPanel.setLayout( new GridLayout( rowCount, 1, 0, 5 ) );
                rightPanel.setLayout( new GridLayout( rowCount, 1, 0, 5 ) );
                setBounds(xOffset, yOffset, width, height);
                container.revalidate();
                break;
            case 2:
                if(selector.equalsIgnoreCase("phone"))
                    createRow( PHONE_2 , 2);
                else if(selector.equalsIgnoreCase("email"))
                    createRow( EMAIL_2 ,2);
                rowCount += 1;
                leftPanel.setLayout( new GridLayout( rowCount, 1, 0, 5 ) );
                rightPanel.setLayout( new GridLayout( rowCount, 1, 0, 5 ) );
                setBounds(xOffset, yOffset, width, height);
                container.revalidate();
                break;
        }
    }
    public void addAddresses() {
        newAddressClickCount++;
        switch (newAddressClickCount) {
            case 1:
                createRow( ADDRESS1_1 , 1);
                createRow( ADDRESS2_1 , 1);
                createRow( CITY_1 , 1);
                createRow( STATE_1 , 1);
                createRow( EIRCODE_1 , 1);
                rowCount += 5;
                leftPanel.setLayout( new GridLayout( rowCount, 1, 0, 5 ) );
                rightPanel.setLayout( new GridLayout( rowCount, 1, 0, 5 ) );
                height += 150;
                setBounds(xOffset, yOffset, width, height);
                container.revalidate();
                break;
            case 2:
                createRow( ADDRESS1_2 , 2);
                createRow( ADDRESS2_2 , 2);
                createRow( CITY_2 , 2);
                createRow( STATE_2 , 2);
                createRow( EIRCODE_2 , 2);
                rowCount += 5;
                leftPanel.setLayout( new GridLayout( rowCount, 1, 0, 5 ) );
                rightPanel.setLayout( new GridLayout( rowCount, 1, 0, 5 ) );
                width += 300;
                setBounds(xOffset, yOffset, width, height);
                container.revalidate();
                break;
        }

    }

    // set AddressBookEntry then use its properties to
    // place data in each JTextField
    public void setAddressBookEntry( AddressBookEntry entry )
    {
        person = entry;

        setField( FIRST_NAME, person.getFirstName() );
        setField( LAST_NAME, person.getLastName() );

        setField( ADDRESS1, person.getAddress1() );
        setField( ADDRESS2, person.getAddress2() );
        setField( CITY, person.getCity() );
        setField( STATE, person.getState() );
        setField( EIRCODE, person.getZipcode() );
        if(newAddressClickCount == 1) {
            setField( ADDRESS1_1, person.getAddress1_1() );
            setField( ADDRESS2_1, person.getAddress2_1() );
            setField( CITY_1, person.getCity_1() );
            setField( STATE_1, person.getState_1() );
            setField( EIRCODE_1, person.getZipcode_1() );
        } else if(newAddressClickCount == 2) {
            setField( ADDRESS1_2, person.getAddress1_2() );
            setField( ADDRESS2_2, person.getAddress2_2() );
            setField( CITY_2, person.getCity_2() );
            setField( STATE_2, person.getState_2() );
            setField( EIRCODE_2, person.getZipcode_2() );
        }

        setField( PHONE, person.getPhoneNumber() );
        if(addPhoneNumbersClickCount == 1)
            setField( PHONE_1, person.getPhoneNumber_1() );
        else if(addPhoneNumbersClickCount == 2)
            setField( PHONE_2, person.getPhoneNumber_2() );

        setField( EMAIL, person.getEmailAddress() );
        if(addEmailAddressClickCount == 1)
            setField( EMAIL_1, person.getEmailAddress_1() );
        else if(addEmailAddressClickCount == 2)
            setField( EMAIL_2, person.getEmailAddress_2() );
    }

    // store AddressBookEntry data from GUI and return
    // AddressBookEntry
    public AddressBookEntry getAddressBookEntry()
    {
        person.setFirstName( getField( FIRST_NAME ) );
        person.setLastName( getField( LAST_NAME ) );

        person.setAddress1( getField( ADDRESS1 ) );
        person.setAddress2( getField( ADDRESS2 ) );
        person.setCity( getField( CITY ) );
        person.setState( getField( STATE ) );
        person.setZipcode( getField(EIRCODE) );
        if(newAddressClickCount == 1) {
            person.setAddress1_1( getField( ADDRESS1_1 ) );
            person.setAddress2_1( getField( ADDRESS2_1 ) );
            person.setCity_1( getField( CITY_1 ) );
            person.setState_1( getField( STATE_1 ) );
            person.setZipcode_1( getField(EIRCODE_1) );
        } else if(newAddressClickCount == 2) {
            person.setAddress1_2( getField( ADDRESS1_2 ) );
            person.setAddress2_2( getField( ADDRESS2_2 ) );
            person.setCity_2( getField( CITY_2 ) );
            person.setState_2( getField( STATE_2 ) );
            person.setZipcode_2( getField(EIRCODE_2) );
        }

        person.setPhoneNumber( getField( PHONE ) );
        if(addPhoneNumbersClickCount == 1)
            person.setPhoneNumber_1( getField( PHONE_1 ) );
        else if(addPhoneNumbersClickCount == 2)
            person.setPhoneNumber_2( getField( PHONE_2 ));

        person.setEmailAddress( getField( EMAIL ) );
        if(addEmailAddressClickCount == 1)
            person.setEmailAddress_1( getField( EMAIL_1 ) );
        else if(addEmailAddressClickCount == 2)
            person.setEmailAddress_2( getField( EMAIL_2 ) );

        return person;
    }

    // set text in JTextField by specifying field's
    // name and value
    private void setField( String fieldName, String value )
    {
        JTextField field =
                ( JTextField ) fields.get( fieldName );

        field.setText( value );
    }

    // get text in JTextField by specifying field's name
    private String getField( String fieldName )
    {
        JTextField field =
                ( JTextField ) fields.get( fieldName );

        return field.getText();
    }

    // utility method used by constructor to create one row in
    // GUI containing JLabel and JTextField
    private void createRow( String name, int side) {

        JLabel label = new JLabel(name, SwingConstants.RIGHT);
        label.setBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JTextField field = new JTextField(30);

        switch (side) {
            case 1:
                leftPanel.add(label);
                rightPanel.add(field);

                fields.put(name, field);
                break;
            case 2:
                leftPanel_2.add(label);
                rightPanel_2.add(field);

                fields.put(name, field);
                break;
        }
    }
}  // end class AddressBookEntryFrame


/**************************************************************************
 * (C) Copyright 2001 by Deitel & Associates, Inc. and Prentice Hall.     *
 * All Rights Reserved.                                                   *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/
