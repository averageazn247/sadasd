// Khoa Bui 100%
// Use the driver file that I modified. 
import java.util.Iterator;
import java.util.List;

public class Driver
{
    public static void main( String[] args ) throws Exception
    {
        Course course= makeCourse();
        //creates the java object
        String filename="test.xml";
        //file name that will be read in
        System.out.println("Creating Java Objects");
        Course xml= XmlMarshaller.marshall( filename );
        // takes in a xml file and then returns a  java object
        print_Java_obj(xml);
        //prints out the java objects

        System.out.println("Creating XML output");
          XmlMarshaller.unmarshall(course);
        // takes a java object and creates xml string to file output. 
        // Check output file for correct java object course to xml
        
/*
        Course course2= (Course)XmlMarshaller.unmarshall( xml );
        if (coursesEqual( course, course2 ))
            System.out.println( "Success" );
        else
            System.out.println( "Failure" ); 
            */
    }
	private static void print_Java_obj(Course course)
	{
		
		System.out.println("Department "+course.getDepartment() ) ;
		System.out.println("number "+course.getNumber() ) ;
		System.out.println("Title "+course.getTitle() ) ;
		System.out.println("Teacher " ) ;
		Person teacher=course.getTeacher();
		System.out.println("Name "+teacher.getName() ) ;
		System.out.println("ID "+teacher.getId() ) ;
		List<Person> student= course.getStudents();
		
		
		for(int i=0;i<student.size();i++){
			//System.out.print("in loop");
			Person temp= student.get(i); 
			System.out.println("Name "+temp.getName()) ;
			System.out.println("ID "+teacher.getId() ) ;	
		} 
	}
    private static Course makeCourse()
    {
        Course course= new Course();
        course.setDepartment( "CSCE" );
        course.setNumber( 314 );
        course.setTitle( "Programming Languages" );

        Person person= new Person();
        person.setName( "Gabriel Dos Reis" );
        person.setId( 112358 );
        course.setTeacher( person );

        person= new Person();
        person.setName( "John Doe" );
        person.setId( 123456 );
        course.getStudents().add( person );

        person= new Person();
        person.setName( "Jane Roe" );
        person.setId( 234567 );
        course.getStudents().add( person );

        person= new Person();
        person.setName( "Wilma Flintstone" );
        person.setId( 345678 );
        course.getStudents().add( person );

        person= new Person();
        person.setName( "George Jetson" );
        person.setId( 456789 );
        course.getStudents().add( person );

        return course;
    }

    private static boolean coursesEqual( Course a, Course b )
    {
        if (a.getDepartment().equals( b.getDepartment() ) &&
            a.getNumber() == b.getNumber() &&
            a.getTitle().equals( b.getTitle() ) &&
            personsEqual( a.getTeacher(), b.getTeacher() ))
        {
            Iterator<Person> sa= a.getStudents().iterator();
            Iterator<Person> sb= b.getStudents().iterator();

            while (sa.hasNext() && sb.hasNext())
            {
                if (! personsEqual( sa.next(), sb.next() ))
                    return false;
            }

            return !(sa.hasNext() || sb.hasNext());
        }

        return false;
    }

    private static boolean personsEqual( Person a, Person b )
    {
        return
            a.getName().equals( b.getName() ) &&
            a.getId() == b.getId();
    }
}