
// Java core packages
import java.sql.*;
import java.util.ArrayList;

public class CloudscapeDataAccess
        implements AddressBookDataAccess {

    //region start
    // reference to database connection
    private Connection connection;

    // reference to prepared statement for locating entry
    private PreparedStatement sqlFindPersonID;
    private PreparedStatement sqlSingleFindPersonID;
    private PreparedStatement sqlFindName;
    private PreparedStatement sqlFindAddress;
    private PreparedStatement sqlFindPhone;
    private PreparedStatement sqlFindEmail;

    private PreparedStatement sqlFindAddressID;
    private PreparedStatement sqlFindPhoneID;
    private PreparedStatement sqlFindEmailID;

    private PreparedStatement sqlDeleteAddressID;
    private PreparedStatement sqlDeletePhoneUsingID;
    private PreparedStatement sqlDeleteEmailUsingID;

    // reference to prepared statement for determining personID
    private PreparedStatement sqlPersonID;

    // references to prepared statements for inserting entry
    private PreparedStatement sqlInsertName;
    private PreparedStatement sqlInsertAddress;
    private PreparedStatement sqlInsertPhone;
    private PreparedStatement sqlInsertEmail;

    // references to prepared statements for updating entry
    private PreparedStatement sqlUpdateName;
    private PreparedStatement sqlUpdateAddress;
    private PreparedStatement sqlUpdatePhone;
    private PreparedStatement sqlUpdateEmail;

    // references to prepared statements for updating entry
    private PreparedStatement sqlDeleteName;
    private PreparedStatement sqlDeleteAddress;
    private PreparedStatement sqlDeletePhone;
    private PreparedStatement sqlDeleteEmail;

    //endregion
    // set up PreparedStatements to access database
    public CloudscapeDataAccess() throws Exception
    {
        // connect to addressbook database
        connect();

        // locate person
        sqlFindPersonID = connection.prepareStatement("SELECT personID FROM names WHERE lastName LIKE ?");
        sqlSingleFindPersonID = connection.prepareStatement("SELECT personID FROM names WHERE firstName = ? AND lastName = ?");
        sqlFindName = connection.prepareStatement("SELECT firstName, lastName FROM names WHERE personID = ?");
        sqlFindAddress = connection.prepareStatement("SELECT address1, address2 ,city, state, zipcode FROM addresses" +
                " WHERE personID = ?");
        sqlFindEmail = connection.prepareStatement("SELECT emailAddress FROM emailaddresses WHERE personID = ?");
        sqlFindPhone = connection.prepareStatement("SELECT phoneNumber FROM phonenumbers WHERE personID = ?");

        sqlFindAddressID = connection.prepareStatement("SELECT addressID FROM addresses WHERE personID = ?");
        sqlFindPhoneID = connection.prepareStatement("SELECT phoneID FROM phonenumbers WHERE personID = ?");
        sqlFindEmailID = connection.prepareStatement("SELECT emailID FROM emailaddresses WHERE personID = ?");

//region Shite
        // Obtain personID for last person inserted in database.
        // [This is a Cloudscape-specific database operation.]
        //sqlPersonID = connection.prepareStatement(
          //      "VALUES ConnectionInfo.lastAutoincrementValue( " +
            //            "'APP', 'NAMES', 'PERSONID')" );
        sqlPersonID = connection.prepareStatement("SELECT MAX(personID) FROM names");

        // Insert first and last names in table names.
        // For referential integrity, this must be performed
        // before sqlInsertAddress, sqlInsertPhone and
        // sqlInsertEmail.
        sqlInsertName = connection.prepareStatement(
                "INSERT INTO names ( firstName, lastName ) " +
                        "VALUES ( ? , ? )" );

        // insert address in table addresses
        sqlInsertAddress = connection.prepareStatement(
                "INSERT INTO addresses ( personID, address1, " +
                        "address2, city, state, zipcode ) " +
                        "VALUES ( ? , ? , ? , ? , ? , ? )" );

        // insert phone number in table phoneNumbers
        sqlInsertPhone = connection.prepareStatement(
                "INSERT INTO phoneNumbers " +
                        "( personID, phoneNumber) " +
                        "VALUES ( ? , ? )" );

        // insert email in table emailAddresses
        sqlInsertEmail = connection.prepareStatement(
                "INSERT INTO emailAddresses " +
                        "( personID, emailAddress ) " +
                        "VALUES ( ? , ? )" );

        // update first and last names in table names
        sqlUpdateName = connection.prepareStatement(
                "UPDATE names SET firstName = ?, lastName = ? " +
                        "WHERE personID = ?" );

        // update address in table addresses
        sqlUpdateAddress = connection.prepareStatement(
                "UPDATE addresses SET address1 = ?, address2 = ?, " +
                        "city = ?, state = ?, zipcode = ? " +
                        "WHERE personID = ? AND addressID = ?");

        // update phone number in table phoneNumbers
        sqlUpdatePhone = connection.prepareStatement(
                "UPDATE phoneNumbers SET phoneNumber = ? WHERE personID = ? AND phoneID = ? ");

        // update email in table emailAddresses
        sqlUpdateEmail = connection.prepareStatement(
                "UPDATE emailAddresses SET emailAddress = ?  WHERE personID = ? AND emailID = ? ");


        // Delete row from table names. This must be executed
        // after sqlDeleteAddress, sqlDeletePhone and
        // sqlDeleteEmail, because of referential integrity.
        sqlDeleteName = connection.prepareStatement(
                "DELETE FROM names WHERE personID = ?" );

        // delete address from table addresses
        sqlDeleteAddress = connection.prepareStatement(
                "DELETE FROM addresses WHERE personID = ?" );

        // delete phone number from table phoneNumbers
        sqlDeletePhone = connection.prepareStatement(
                "DELETE FROM phoneNumbers WHERE personID = ?" );

        // delete email address from table emailAddresses
        sqlDeleteEmail = connection.prepareStatement(
                "DELETE FROM emailAddresses WHERE personID = ?" );

        // delete address from table addresses
        sqlDeleteAddressID = connection.prepareStatement(
                "DELETE FROM addresses WHERE personID = ? AND addressID = ?");

        sqlDeleteEmailUsingID = connection.prepareStatement(
                "DELETE FROM emailAddresses WHERE personID = ? AND emailID = ?");

        // delete phone number from table phoneNumbers
        sqlDeletePhoneUsingID = connection.prepareStatement(
                "DELETE FROM phoneNumbers WHERE personID = ? AND phoneID = ?");

//endregion
    }  // end CloudscapeDataAccess constructor

    //region moreStuff
    // Obtain a connection to addressbook database. Method may
    // may throw ClassNotFoundException or SQLException. If so,
    // exception is passed via this class's constructor back to
    // the AddressBook application so the application can display
    // an error message and terminate.
    private void connect() throws Exception
    {
        // Cloudscape database driver class name
        String driver = "com.mysql.jdbc.Driver";

        // URL to connect to books database
        String url = "jdbc:mysql://localhost:3306/addressbook?autoReconnect=true&useSSL=false";

        // load database driver class
        Class.forName( driver );

        // connect to database
        connection = DriverManager.getConnection( url, "root", "admin" );

        // Require manual commit for transactions. This enables
        // the program to rollback transactions that do not
        // complete and commit transactions that complete properly.
        connection.setAutoCommit( false );
    }

    // Locate specified person. Method returns AddressBookEntry
    // containing information.
//endregion
    public ArrayList<AddressBookEntry> findPerson(String firstName, String lastName)
    {
        try {
            // set query parameter and execute query
            ResultSet PersonIDset;
            System.out.println("first name: " + firstName);
            System.out.println("last name: " + lastName);
            if(firstName.equals("")) {
                System.out.println("multiple people");
                sqlFindPersonID.setString(1, lastName);

                PersonIDset = sqlFindPersonID.executeQuery();
            } else {
                System.out.println("single person");
                sqlSingleFindPersonID.setString(1, firstName);
                sqlSingleFindPersonID.setString(2, lastName);

                PersonIDset = sqlSingleFindPersonID.executeQuery();
            }
            ArrayList<AddressBookEntry> result = new ArrayList<>();
            while (PersonIDset.next()) {
                String personID = PersonIDset.getString(1);
                AddressBookEntry person = new AddressBookEntry(PersonIDset.getInt(1));
                System.out.println("Person ID: " + personID);

                // Get first and last name
                sqlFindName.setString(1, personID);
                ResultSet nameSet = sqlFindName.executeQuery();
                if(!nameSet.next())
                    System.out.println("No names exist for this ID");
                System.out.println("Name: " + nameSet.getString(1) + " " + nameSet.getString(2));
                person.setFirstName(nameSet.getString(1));
                person.setLastName(nameSet.getString(2));

                // Get address(s)
                sqlFindAddress.setString(1, personID);
                ResultSet addressSet = sqlFindAddress.executeQuery();
                if(!addressSet.next())
                    System.out.println("No address exist for this ID");
                person.setAddress1(addressSet.getString(1));
                person.setAddress2(addressSet.getString(2));
                person.setCity(addressSet.getString(3));
                person.setState(addressSet.getString(4));
                person.setZipcode(addressSet.getString(5));

                // If they have a second address
                if(addressSet.next()) {
                    person.setAddressCount(1);
                    person.setAddress1_1(addressSet.getString(1));
                    person.setAddress2_1(addressSet.getString(2));
                    person.setCity_1(addressSet.getString(3));
                    person.setState_1(addressSet.getString(4));
                    person.setZipcode_1(addressSet.getString(5));
                }

                // If they have a third address
                if(addressSet.next()) {
                    person.setAddressCount(2);
                    person.setAddress1_2(addressSet.getString(1));
                    person.setAddress2_2(addressSet.getString(2));
                    person.setCity_2(addressSet.getString(3));
                    person.setState_2(addressSet.getString(4));
                    person.setZipcode_2(addressSet.getString(5));
                }

                // Get phone number(s)
                sqlFindPhone.setString(1, personID);
                ResultSet phoneSet = sqlFindPhone.executeQuery();
                if(!phoneSet.next())
                    System.out.println("No phone numbers exist for tis entry");
                System.out.println("Phone: " + phoneSet.getString(1));
                person.setPhoneNumber(phoneSet.getString(1));

                // If the person has a second number
                if(phoneSet.next()) {
                    person.setPhoneCount(1);
                    System.out.println("Phone 2: " + phoneSet.getString(1));
                    person.setPhoneNumber_1(phoneSet.getString(1));
                }

                // If the person has a third second number
                if(phoneSet.next()) {
                    person.setPhoneCount(2);
                    System.out.println("Phone 3: " + phoneSet.getString(1));
                    person.setPhoneNumber_2(phoneSet.getString(1));
                }

                // Get email(s)
                sqlFindEmail.setString(1, personID);
                ResultSet emailSet = sqlFindEmail.executeQuery();
                if(!emailSet.next()) {
                    System.out.println("No email addresses exist for this ID");
                } else {
                    System.out.println("Email: " + emailSet.getString(1));
                    person.setEmailAddress(emailSet.getString(1));
                }

                // If the person has a second email
                if(emailSet.next()) {
                    person.setEmailCount(1);
                    System.out.println("Email 2: " + emailSet.getString(1));
                    person.setEmailAddress_1(emailSet.getString(1));
                }

                // If the person has a third email
                if(emailSet.next()) {
                    person.setEmailCount(2);
                    System.out.println("Email 3: " + emailSet.getString(1));
                    person.setEmailAddress_2(emailSet.getString(1));
                }

                result.add(person);
            }
            // return AddressBookEntry
            return result;
        }

        // catch SQLException
        catch ( SQLException sqlException ) {
            sqlException.printStackTrace();
            return null;
        }
    }  // end method findPerson

    // Update an entry. Method returns boolean indicating
    // success or failure.
    public boolean savePerson(AddressBookEntry person)
            throws DataAccessException {
        // update person in database
        try {
            int result;
            //region updateName
            sqlUpdateName.setString(1, person.getFirstName());
            sqlUpdateName.setString(2, person.getLastName());
            sqlUpdateName.setInt(3, person.getPersonID());
            result = sqlUpdateName.executeUpdate();
            if (result == 0) {
                connection.rollback(); // rollback update
                System.out.println("failed to update name");
                return false;          // update unsuccessful
            }
            //endregion

            //region updateAddress
            // set query parameter and execute query
            sqlFindAddressID.setInt(1, person.getPersonID());
            ResultSet addressIDset = sqlFindAddressID.executeQuery();

            System.out.println("Updating addresses");
            // if no records found, return immediately
            if (!addressIDset.next()) {
                System.out.println("No addresses");
            } else {
                int addressID = addressIDset.getInt(1);

                if (person.getAddress1().equals("") && person.getAddress2().equals("")
                        && person.getCity().equals("") && person.getZipcode().equals("")) {

                    sqlDeleteAddressID.setInt(1, person.getPersonID());
                    sqlDeleteAddressID.setInt(2, addressID);

                    result = sqlDeleteAddressID.executeUpdate();

                    if (result == 0) {
                        connection.rollback(); // rollback update
                        return false;          // update unsuccessful
                    }
                } else {
                    sqlUpdateAddress.setString(1, person.getAddress1());
                    sqlUpdateAddress.setString(2, person.getAddress2());
                    sqlUpdateAddress.setString(3, person.getCity());
                    sqlUpdateAddress.setString(4, person.getState());
                    sqlUpdateAddress.setString(5, person.getZipcode());
                    sqlUpdateAddress.setInt(6, person.getPersonID());
                    sqlUpdateAddress.setInt(7, addressID);
                    result = sqlUpdateAddress.executeUpdate();

                    if (result == 0) {
                        connection.rollback(); // rollback update
                        return false;          // update unsuccessful
                    }
                }

                if (addressIDset.next()) {
                    addressID = addressIDset.getInt(1);

                    if (person.getAddress1_1().equals("") && person.getAddress2_1().equals("")
                            && person.getCity_1().equals("") && person.getZipcode_1().equals("")) {

                        sqlDeleteAddressID.setInt(1, person.getPersonID());
                        sqlDeleteAddressID.setInt(2, addressID);

                        result = sqlDeleteAddressID.executeUpdate();

                        if (result == 0) {
                            connection.rollback(); // rollback update
                            return false;          // update unsuccessful
                        }
                    } else {
                        sqlUpdateAddress.setString(1, person.getAddress1_1());
                        sqlUpdateAddress.setString(2, person.getAddress2_1());
                        sqlUpdateAddress.setString(3, person.getCity_1());
                        sqlUpdateAddress.setString(4, person.getState_1());
                        sqlUpdateAddress.setString(5, person.getZipcode_1());
                        sqlUpdateAddress.setInt(6, person.getPersonID());
                        sqlUpdateAddress.setInt(7, addressID);

                        result = sqlUpdateAddress.executeUpdate();

                        if (result == 0) {
                            connection.rollback(); // rollback update
                            return false;          // update unsuccessful
                        }
                    }
                }

                if (addressIDset.next()) {
                    addressID = addressIDset.getInt(1);

                    if (person.getAddress1_2().equals("") && person.getAddress2_2().equals("")
                            && person.getCity_2().equals("") && person.getZipcode_2().equals("")) {

                        sqlDeleteAddressID.setInt(1, person.getPersonID());
                        sqlDeleteAddressID.setInt(2, addressID);

                        result = sqlDeleteAddressID.executeUpdate();

                        if (result == 0) {
                            connection.rollback(); // rollback update
                            return false;          // update unsuccessful
                        }

                    } else {
                        sqlUpdateAddress.setString(1, person.getAddress1_2());
                        sqlUpdateAddress.setString(2, person.getAddress2_2());
                        sqlUpdateAddress.setString(3, person.getCity_2());
                        sqlUpdateAddress.setString(4, person.getState_2());
                        sqlUpdateAddress.setString(5, person.getZipcode_2());
                        sqlUpdateAddress.setInt(6, person.getPersonID());
                        sqlUpdateAddress.setInt(7, addressID);

                        result = sqlUpdateAddress.executeUpdate();

                        if (result == 0) {
                            connection.rollback(); // rollback update
                            return false;          // update unsuccessful
                        }
                    }
                }
            }
            //endregion

            //region updatePhones
            // set query parameter and execute query
            sqlFindPhoneID.setInt(1, person.getPersonID());
            ResultSet phoneIDset = sqlFindPhoneID.executeQuery();
            // if no records found, return immediately
            if (!phoneIDset.next()) {
                System.out.println("No phone numbers");
            } else {
                int phoneID = phoneIDset.getInt(1);

                if (person.getPhoneNumber().equals("")) {
                    sqlDeletePhoneUsingID.setInt(1, person.getPersonID());
                    sqlDeletePhoneUsingID.setInt(2, phoneID);
                    result = sqlDeletePhoneUsingID.executeUpdate();

                    if (result == 0) {
                        connection.rollback(); // rollback update
                        return false;          // update unsuccessful
                    }
                } else {
                    sqlUpdatePhone.setString(1, person.getPhoneNumber());
                    sqlUpdatePhone.setInt(2, person.getPersonID());
                    sqlUpdatePhone.setInt(3, phoneID);
                    result = sqlUpdatePhone.executeUpdate();

                    if (result == 0) {
                        connection.rollback(); // rollback update
                        return false;          // update unsuccessful
                    }
                }

                if (phoneIDset.next()) {
                    phoneID = phoneIDset.getInt(1);

                    if (person.getPhoneNumber_1().equals("")) {
                        sqlDeletePhoneUsingID.setInt(1, person.getPersonID());
                        sqlDeletePhoneUsingID.setInt(2, phoneID);

                        result = sqlDeletePhoneUsingID.executeUpdate();

                        if (result == 0) {
                            connection.rollback(); // rollback update
                            return false;          // update unsuccessful
                        }

                    } else {
                        sqlUpdatePhone.setString(1, person.getPhoneNumber_1());
                        sqlUpdatePhone.setInt(2, person.getPersonID());
                        sqlUpdatePhone.setInt(3, phoneID);
                        result = sqlUpdatePhone.executeUpdate();

                        if (result == 0) {
                            connection.rollback(); // rollback update
                            return false;          // update unsuccessful
                        }
                    }
                }

                if (phoneIDset.next()) {
                    phoneID = phoneIDset.getInt(1);

                    if (person.getPhoneNumber_2().equals("")) {
                        sqlDeletePhoneUsingID.setInt(1, person.getPersonID());
                        sqlDeletePhoneUsingID.setInt(2, phoneID);
                        result = sqlDeletePhoneUsingID.executeUpdate();

                        if (result == 0) {
                            connection.rollback(); // rollback update
                            return false;          // update unsuccessful
                        }

                    } else {
                        sqlUpdatePhone.setString(1, person.getPhoneNumber_2());
                        sqlUpdatePhone.setInt(2, person.getPersonID());
                        sqlUpdatePhone.setInt(3, phoneID);
                        result = sqlUpdatePhone.executeUpdate();
                    }
                    if (result == 0) {
                        connection.rollback(); // rollback update
                        return false;          // update unsuccessful
                    }
                }
            }
            //endregion

            //region updateEmails
            // set query parameter and execute query
            sqlFindEmailID.setInt(1, person.getPersonID());
            ResultSet emailIDset = sqlFindEmailID.executeQuery();

            if (!emailIDset.next()) {
                System.out.println("No emails found");
            } else {
                int emailID =  emailIDset.getInt(1);

                if (person.getEmailAddress().equals("")) {
                    sqlDeleteEmailUsingID.setInt(1, person.getPersonID());
                    sqlDeleteEmailUsingID.setInt(2, emailID);

                    result = sqlDeleteEmailUsingID.executeUpdate();

                    if (result == 0) {
                        connection.rollback(); // rollback update
                        return false;          // update unsuccessful
                    }
                } else {
                    sqlUpdateEmail.setString(1, person.getEmailAddress());
                    sqlUpdateEmail.setInt(2, person.getPersonID());
                    sqlUpdateEmail.setInt(3, emailID);
                    result = sqlUpdateEmail.executeUpdate();

                    if (result == 0) {
                        connection.rollback(); // rollback update
                        return false;          // update unsuccessful
                    }
                }
                if (emailIDset.next()) {
                    emailID = emailIDset.getInt(1);

                    if (person.getEmailAddress_1().equals("")) {
                        sqlDeleteEmailUsingID.setInt(1, person.getPersonID());
                        sqlDeleteEmailUsingID.setInt(2, emailID);
                        result = sqlDeleteEmailUsingID.executeUpdate();

                        if (result == 0) {
                            connection.rollback(); // rollback update
                            return false;          // update unsuccessful
                        }

                    } else {
                        sqlUpdateEmail.setString(1, person.getEmailAddress_1());
                        sqlUpdateEmail.setInt(2, person.getPersonID());
                        sqlUpdateEmail.setInt(3, emailID);
                        result = sqlUpdateEmail.executeUpdate();

                        if (result == 0) {
                            connection.rollback(); // rollback update
                            return false;          // update unsuccessful
                        }
                    }
                }

                if (emailIDset.next()) {
                    emailID = emailIDset.getInt(1);

                    if (person.getEmailAddress_2().equals("")) {
                        sqlDeleteEmailUsingID.setInt(1, person.getPersonID());
                        sqlDeleteEmailUsingID.setInt(2, emailID);

                        result = sqlDeleteEmailUsingID.executeUpdate();

                        if (result == 0) {
                            connection.rollback(); // rollback update
                            return false;          // update unsuccessful
                        }

                    } else {
                        sqlUpdateEmail.setString(1, person.getEmailAddress_2());
                        sqlUpdateEmail.setInt(2, person.getPersonID());
                        sqlUpdateEmail.setInt(3, emailID);
                        result = sqlUpdateEmail.executeUpdate();

                        if (result == 0) {
                            connection.rollback(); // rollback update
                            return false;          // update unsuccessful
                        }
                    }
                }
            }
            connection.commit();   // commit update
            //endregion

            //region addingAddress
            int addressCount = person.getAddressCount();
            if(addressCount == 1){
                sqlInsertAddress.setInt(1, person.getPersonID());
                sqlInsertAddress.setString(2,
                        person.getAddress1_1());
                sqlInsertAddress.setString(3,
                        person.getAddress2_1());
                sqlInsertAddress.setString(4,
                        person.getCity_1());
                sqlInsertAddress.setString(5,
                        person.getState_1());
                sqlInsertAddress.setString(6,
                        person.getZipcode_1());
                result = sqlInsertAddress.executeUpdate();

                // if insert fails, rollback and discontinue
                if (result == 0) {
                    connection.rollback(); // rollback insert
                    return false;          // insert unsuccessful
                }
            }

            if(addressCount == 2){
                sqlInsertAddress.setInt(1, person.getPersonID());
                sqlInsertAddress.setString(2,
                        person.getAddress1_2());
                sqlInsertAddress.setString(3,
                        person.getAddress2_2());
                sqlInsertAddress.setString(4,
                        person.getCity_2());
                sqlInsertAddress.setString(5,
                        person.getState_2());
                sqlInsertAddress.setString(6,
                        person.getZipcode_2());
                result = sqlInsertAddress.executeUpdate();

                // if insert fails, rollback and discontinue
                if (result == 0) {
                    connection.rollback();
                    return false;
                }
            }
            connection.commit();   // commit update
            //endregion

            //region addingEmails
            int emailCount = person.getEmailCount();
            if(emailCount != 0){
                sqlInsertEmail.setInt(1, person.getPersonID());

                if(emailCount == 1) {
                    sqlInsertEmail.setString(2,
                            person.getEmailAddress_1());
                } else if(emailCount == 2) {
                    sqlInsertEmail.setString(2,
                            person.getEmailAddress_2());
                }

                result = sqlInsertEmail.executeUpdate();

                // if insert fails, rollback and discontinue
                if (result == 0) {
                    connection.rollback(); // rollback insert
                    return false;          // insert unsuccessful
                }
            }
            connection.commit();   // commit update
            //endregion

            //region addingPhone
            int phoneCount = person.getPhoneCount();
            if(phoneCount != 0){
                sqlInsertPhone.setInt(1, person.getPersonID());

                if(phoneCount == 1) {
                    sqlInsertPhone.setString(2,
                            person.getEmailAddress_1());
                } else if(phoneCount == 2) {
                    sqlInsertPhone.setString(2,
                            person.getEmailAddress_2());
                }
                result = sqlInsertPhone.executeUpdate();

                // if insert fails, rollback and discontinue
                if (result == 0) {
                    connection.rollback(); // rollback insert
                    return false;          // insert unsuccessful
                }
            }
            connection.commit();   // commit update
            //endregion

            return true;           // update successful
        }  // end try

        // detect problems updating database
        catch (SQLException sqlException) {

            // rollback transaction
            try {
                connection.rollback(); // rollback update
                return false;          // update unsuccessful
            }

            // handle exception rolling back transaction
            catch (SQLException exception) {
                throw new DataAccessException(exception);
            }
        }
    } // end method savePerson

    // Insert new entry. Method returns boolean indicating
    // success or failure.
    public boolean newPerson( AddressBookEntry person )
            throws DataAccessException
    {
        // insert person in database
        try {
            int result;

            // insert first and last name in names table
            sqlInsertName.setString( 1, person.getFirstName() );
            sqlInsertName.setString( 2, person.getLastName() );
            result = sqlInsertName.executeUpdate();

            // if insert fails, rollback and discontinue
            if ( result == 0 ) {
                connection.rollback(); // rollback insert
                return false;          // insert unsuccessful
            }

            // determine new personID
            ResultSet resultPersonID = sqlPersonID.executeQuery();

            if ( resultPersonID.next() ) {
                int personID =  resultPersonID.getInt( 1 );

                // insert address in addresses table
                System.out.println("Inserting first address");
                sqlInsertAddress.setInt( 1, personID );
                sqlInsertAddress.setString( 2,
                        person.getAddress1() );
                sqlInsertAddress.setString( 3,
                        person.getAddress2() );
                sqlInsertAddress.setString( 4,
                        person.getCity() );
                sqlInsertAddress.setString( 5,
                        person.getState() );
                sqlInsertAddress.setString( 6,
                        person.getZipcode() );
                result = sqlInsertAddress.executeUpdate();

                // if insert fails, rollback and discontinue
                if ( result == 0 ) {
                    connection.rollback(); // rollback insert
                    return false;          // insert unsuccessful
                }

                // if person has added a second address
                if(person.getAddress1_1() != "") {
                    System.out.println("Inserting second address");
                    // insert address in addresses table
                    sqlInsertAddress.setInt( 1, personID );
                    sqlInsertAddress.setString( 2,
                            person.getAddress1_1() );
                    sqlInsertAddress.setString( 3,
                            person.getAddress2_1() );
                    sqlInsertAddress.setString( 4,
                            person.getCity_1() );
                    sqlInsertAddress.setString( 5,
                            person.getState_1() );
                    sqlInsertAddress.setString( 6,
                            person.getZipcode_1() );
                    result = sqlInsertAddress.executeUpdate();

                    // if insert fails, rollback and discontinue
                    if ( result == 0 ) {
                        connection.rollback(); // rollback insert
                        return false;          // insert unsuccessful
                    }
                }
                if(person.getAddress1_2() != "") {
                    System.out.println("Inserting third address");
                    // insert address in addresses table
                    sqlInsertAddress.setInt( 1, personID );
                    sqlInsertAddress.setString( 2,
                            person.getAddress1_2() );
                    sqlInsertAddress.setString( 3,
                            person.getAddress2_2() );
                    sqlInsertAddress.setString( 4,
                            person.getCity_2() );
                    sqlInsertAddress.setString( 5,
                            person.getState_2() );
                    sqlInsertAddress.setString( 6,
                            person.getZipcode_2() );
                    result = sqlInsertAddress.executeUpdate();

                    // if insert fails, rollback and discontinue
                    if ( result == 0 ) {
                        connection.rollback(); // rollback insert
                        return false;          // insert unsuccessful
                    }
                }

                // insert phone number in phoneNumbers table
                System.out.println("Inserting first phone");
                sqlInsertPhone.setInt( 1, personID );
                sqlInsertPhone.setString( 2,
                        person.getPhoneNumber() );
                result = sqlInsertPhone.executeUpdate();

                // if insert fails, rollback and discontinue
                if ( result == 0 ) {
                    connection.rollback(); // rollback insert
                    return false;          // insert unsuccessful
                }

                if(person.getPhoneNumber_1() != "") {
                    // insert phone number in phoneNumbers table
                    System.out.println("Inserting second phone");
                    sqlInsertPhone.setInt( 1, personID );
                    sqlInsertPhone.setString( 2,
                            person.getPhoneNumber_1() );
                    result = sqlInsertPhone.executeUpdate();

                    // if insert fails, rollback and discontinue
                    if ( result == 0 ) {
                        connection.rollback(); // rollback insert
                        return false;          // insert unsuccessful
                    }
                }

                if(person.getPhoneNumber_2() != "") {
                    // insert phone number in phoneNumbers table
                    System.out.println("Inserting third phone");
                    sqlInsertPhone.setInt( 1, personID );
                    sqlInsertPhone.setString( 2,
                            person.getPhoneNumber_2() );
                    result = sqlInsertPhone.executeUpdate();

                    // if insert fails, rollback and discontinue
                    if ( result == 0 ) {
                        connection.rollback(); // rollback insert
                        return false;          // insert unsuccessful
                    }
                }

                // insert email address in emailAddresses table
                System.out.println("Inserting first email");
                sqlInsertEmail.setInt( 1, personID );
                sqlInsertEmail.setString( 2,
                        person.getEmailAddress() );
                result = sqlInsertEmail.executeUpdate();

                // if insert fails, rollback and discontinue
                if ( result == 0 ) {
                    connection.rollback(); // rollback insert
                    return false;          // insert unsuccessful
                }

                if(person.getEmailAddress_1() != "") {
                    // insert email address in emailAddresses table
                    System.out.println("Inserting second email");
                    sqlInsertEmail.setInt( 1, personID );
                    sqlInsertEmail.setString( 2,
                            person.getEmailAddress_1() );
                    result = sqlInsertEmail.executeUpdate();

                    // if insert fails, rollback and discontinue
                    if ( result == 0 ) {
                        connection.rollback(); // rollback insert
                        return false;          // insert unsuccessful
                    }
                }

                if(person.getEmailAddress_2() != "") {
                    // insert email address in emailAddresses table
                    System.out.println("Inserting third email");
                    sqlInsertEmail.setInt( 1, personID );
                    sqlInsertEmail.setString( 2,
                            person.getEmailAddress_2() );
                    result = sqlInsertEmail.executeUpdate();

                    // if insert fails, rollback and discontinue
                    if ( result == 0 ) {
                        connection.rollback(); // rollback insert
                        return false;          // insert unsuccessful
                    }
                }

                connection.commit();   // commit insert
                return true;           // insert successful
            }

            else
                return false;
        }  // end try

        // detect problems updating database
        catch ( SQLException sqlException ) {
            // rollback transaction
            try {
                sqlException.printStackTrace();
                connection.rollback(); // rollback update
                return false;          // update unsuccessful
            }

            // handle exception rolling back transaction
            catch ( SQLException exception ) {
                exception.printStackTrace();
                throw new DataAccessException( exception );
            }
        }
    }  // end method newPerson

    // Delete an entry. Method returns boolean indicating
    // success or failure.
    public boolean deletePerson( AddressBookEntry person )
            throws DataAccessException
    {
        // delete a person from database
        try {
            int result;

            // delete address from addresses table
            sqlDeleteAddress.setInt( 1, person.getPersonID() );
            result = sqlDeleteAddress.executeUpdate();

            // if delete fails, rollback and discontinue
            if ( result == 0 ) {
                connection.rollback(); // rollback delete
                return false;          // delete unsuccessful
            }

            // delete phone number from phoneNumbers table
            sqlDeletePhone.setInt( 1, person.getPersonID() );
            result = sqlDeletePhone.executeUpdate();

            // if delete fails, rollback and discontinue
            if ( result == 0 ) {
                connection.rollback(); // rollback delete
                return false;          // delete unsuccessful
            }

            // delete email address from emailAddresses table
            sqlDeleteEmail.setInt( 1, person.getPersonID() );
            result = sqlDeleteEmail.executeUpdate();

            // if delete fails, rollback and discontinue
            if ( result == 0 ) {
                connection.rollback(); // rollback delete
                return false;          // delete unsuccessful
            }

            // delete name from names table
            sqlDeleteName.setInt( 1, person.getPersonID() );
            result = sqlDeleteName.executeUpdate();

            // if delete fails, rollback and discontinue
            if ( result == 0 ) {
                connection.rollback(); // rollback delete
                return false;          // delete unsuccessful
            }

            connection.commit();   // commit delete
            return true;           // delete successful
        }  // end try

        // detect problems updating database
        catch ( SQLException sqlException ) {
            // rollback transaction
            try {
                connection.rollback(); // rollback update
                return false;          // update unsuccessful
            }

            // handle exception rolling back transaction
            catch ( SQLException exception ) {
                throw new DataAccessException( exception );
            }
        }
    }  // end method deletePerson

    // method to close statements and database connection
    public void close()
    {
        // close database connection
        try {
            sqlFindName.close();
            sqlFindAddress.close();
            sqlFindEmail.close();
            sqlFindPhone.close();
            sqlSingleFindPersonID.close();
            sqlFindPersonID.close();
            sqlPersonID.close();
            sqlFindAddressID.close();
            sqlFindPersonID.close();
            sqlFindEmailID.close();
            sqlInsertName.close();
            sqlInsertAddress.close();
            sqlInsertPhone.close();
            sqlInsertEmail.close();
            sqlUpdateName.close();
            sqlUpdateAddress.close();
            sqlUpdatePhone.close();
            sqlUpdateEmail.close();
            sqlDeleteName.close();
            sqlDeleteAddress.close();
            sqlDeletePhone.close();
            sqlDeleteEmail.close();
            connection.close();
        }  // end try

        // detect problems closing statements and connection
        catch ( SQLException sqlException ) {
            sqlException.printStackTrace();
        }
    }  // end method close

    // Method to clean up database connection. Provided in case
    // CloudscapeDataAccess object is garbage collected.
    protected void finalize()
    {
        close();
    }

    //endregion
}  // end class CloudscapeDataAccess