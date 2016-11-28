package base;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import domain.PersonDomainModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person_Test {
		
	private static PersonDomainModel person1;
	private static UUID person1UUID = UUID.randomUUID();			
	
	@BeforeClass
	public static void personInstance() throws Exception{
		
		Date person1Birth = null;

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		person1 = new PersonDomainModel();
		 
		try {
			person1Birth = dateFormat.parse("1994-11-27");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		person1.setPersonID(person1UUID);
		person1.setFirstName("Mingkun");
		person1.setMiddleName("a");
		person1.setLastName("Chen");
		person1.setBirthday(person1Birth);
		person1.setCity("Elkton");
		person1.setStreet("702 Stone Gate Blvd");
		person1.setPostalCode(21921);
	}
	
	@AfterClass
	public static void TearDownAfterClass() throws Exception{
		//Cleans up data written to the database
		ArrayList<PersonDomainModel> persons;
   	 	persons = PersonDAL.getPersons();
   	 	for (PersonDomainModel p: persons){
   	 		PersonDAL.deletePerson(p.getPersonID());
   	 	}
	}
	
	@Test
	public void AddTest(){
		//Adds person1 and then checks the UUID to ensure the person was added
		PersonDAL.addPerson(person1);
		assertEquals(person1.getPersonID(), (PersonDAL.getPerson(person1UUID).getPersonID()));
		}
	
	@Test
	public void UpdateTest(){
		PersonDAL.addPerson(person1);
		assertEquals("Chen", (PersonDAL.getPerson(person1.getPersonID())).getLastName());
		person1.setLastName("Fowle");
		//Updates person1's last name and then checks to make sure it was updated
		PersonDAL.updatePerson(person1);
		assertEquals("Fowle", (PersonDAL.getPerson(person1.getPersonID())).getLastName());
	}

	@Test
	public void RemoveTest(){
		ArrayList<PersonDomainModel> persons;
		PersonDAL.addPerson(person1);
		persons = PersonDAL.getPersons();
		//Adds person1, checks that only one person has been added
		assertTrue(persons.size() == 1);
		PersonDAL.deletePerson(person1.getPersonID());
		persons = PersonDAL.getPersons();
		//Removes person1, checks that the arraylist is now empty
		assertTrue(persons.size() == 0);
	}
}


