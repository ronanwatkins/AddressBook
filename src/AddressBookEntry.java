
public class AddressBookEntry {
    private String firstName = "";
    private String lastName = "";

    private String address1 = "";
    private String address2 = "";
    private String city = "";
    private String state = "";
    private String zipcode = "";

    private String address1_1 = "";
    private String address2_1 = "";
    private String city_1 = "";
    private String state_1 = "";
    private String zipcode_1 = "";

    private String address1_2 = "";
    private String address2_2 = "";
    private String city_2 = "";
    private String state_2 = "";
    private String zipcode_2 = "";

    private String phoneNumber = "";
    private String emailAddress = "";

    private String phoneNumber_1 = "";
    private String emailAddress_1 = "";

    private String phoneNumber_2 = "";
    private String emailAddress_2 = "";

    private int personID;
    private int addressID;
    private int phoneID;
    private int emailID;

    private int addressCount;
    private int phoneCount;
    private int emailCount;

    // empty constructor
    public AddressBookEntry()
    {
    }

    public void setAddressCount( int addressCount ) { this.addressCount = addressCount; }

    public void setPhoneCount( int phoneCount ) {this.phoneCount = phoneCount; }

    public void setEmailCount( int emailCount ) { this.emailCount = emailCount; }

    public int getAddressCount() {
        return addressCount;
    }

    public int getEmailCount() {
        return emailCount;
    }

    public int getPhoneCount() {
        return phoneCount;
    }

    // set person's id
    public AddressBookEntry( int id )
    {
        personID = id;
    }

    // set person's first name
    public void setFirstName( String first )
    {
        firstName = first;
    }

    // get person's first name
    public String getFirstName()
    {
        return firstName;
    }

    // set person's last name
    public void setLastName( String last )
    {
        lastName = last;
    }

    // get person's last name
    public String getLastName()
    {
        return lastName;
    }

    // set first line of person's address
    public void setAddress1( String firstLine )
    {
        address1 = firstLine;
    }

    // get first line of person's address
    public String getAddress1()
    {
        return address1;
    }

    // set second line of person's address
    public void setAddress2( String secondLine )
    {
        address2 = secondLine;
    }

    // get second line of person's address
    public String getAddress2()
    {
        return address2;
    }

    // set city in which person lives
    public void setCity( String personCity )
    {
        city = personCity;
    }

    // get city in which person lives
    public String getCity()
    {
        return city;
    }

    // set state in which person lives
    public void setState( String personState )
    {
        state = personState;
    }

    // get state in which person lives
    public String getState()
    {
        return state;
    }

    // set person's zip code
    public void setZipcode(String zip )
    {
        zipcode = zip;
    }

    // get person's zip code
    public String getZipcode()
    {
        return zipcode;
    }

    // set first line of person's address
    public void setAddress1_1( String firstLine )
    {
        address1_1 = firstLine;
    }

    // get first line of person's address
    public String getAddress1_1()
    {
        return address1_1;
    }

    // set second line of person's address
    public void setAddress2_1( String secondLine )
    {
        address2_1 = secondLine;
    }

    // get second line of person's address
    public String getAddress2_1()
    {
        return address2_1;
    }

    // set city in which person lives
    public void setCity_1( String personCity )
    {
        city_1 = personCity;
    }

    // get city in which person lives
    public String getCity_1()
    {
        return city_1;
    }

    // set state in which person lives
    public void setState_1( String personState )
    {
        state_1 = personState;
    }

    // get state in which person lives
    public String getState_1()
    {
        return state_1;
    }

    // set person's zip code
    public void setZipcode_1(String zip )
    {
        zipcode_1 = zip;
    }

    // get person's zip code
    public String getZipcode_1()
    {
        return zipcode_1;
    }

    // set first line of person's address
    public void setAddress1_2( String firstLine )
    {
        address1_2 = firstLine;
    }

    // get first line of person's address
    public String getAddress1_2()
    {
        return address1_2;
    }

    // set second line of person's address
    public void setAddress2_2( String secondLine )
    {
        address2_2 = secondLine;
    }

    // get second line of person's address
    public String getAddress2_2()
    {
        return address2_2;
    }

    // set city in which person lives
    public void setCity_2( String personCity )
    {
        city_2 = personCity;
    }

    // get city in which person lives
    public String getCity_2()
    {
        return city_2;
    }

    // set state in which person lives
    public void setState_2( String personState )
    {
        state_2 = personState;
    }

    // get state in which person lives
    public String getState_2()
    {
        return state_2;
    }

    // set person's zip code
    public void setZipcode_2(String zip )
    {
        zipcode_2 = zip;
    }

    // get person's zip code
    public String getZipcode_2()
    {
        return zipcode_2;
    }

    // set person's phone number
    public void setPhoneNumber( String number )
    {
        phoneNumber = number;
    }

    // get person's phone number
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    // set person's email address
    public void setEmailAddress( String email )
    {
        emailAddress = email;
    }

    // get person's email address
    public String getEmailAddress()
    {
        return emailAddress;
    }

    // set person's phone number
    public void setPhoneNumber_1( String number )
    {
        phoneNumber_1 = number;
    }

    // get person's phone number
    public String getPhoneNumber_1()
    {
        return phoneNumber_1;
    }

    // set person's email address
    public void setEmailAddress_1( String email )
    {
        emailAddress_1 = email;
    }

    // get person's email address
    public String getEmailAddress_1()
    {
        return emailAddress_1;
    }

    // set person's phone number
    public void setPhoneNumber_2( String number )
    {
        phoneNumber_2 = number;
    }

    // get person's phone number
    public String getPhoneNumber_2()
    {
        return phoneNumber_2;
    }

    // set person's email address
    public void setEmailAddress_2( String email )
    {
        emailAddress_2 = email;
    }

    // get person's email address
    public String getEmailAddress_2()
    {
        return emailAddress_2;
    }


    // get person's ID
    public int getPersonID()
    {
        return personID;
    }

    // set person's addressID
    public void setAddressID( int id )
    {
        addressID = id;
    }

    // get person's addressID
    public int getAddressID()
    {
        return addressID;
    }

    // set person's phoneID
    public void setPhoneID( int id )
    {
        phoneID = id;
    }

    // get person's phoneID
    public int getPhoneID()
    {
        return phoneID;
    }

    // set person's emailID
    public void setEmailID( int id )
    {
        emailID = id;
    }

    // get person's emailID
    public int getEmailID()
    {
        return emailID;
    }
}  // end class AddressBookEntry


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
