import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.List;
public class xmlparse {
	public static void main(String argv[]) {
		Course course= new Course();
		
		
		 try {
			 
				File file = new File("test.xml");//reads in file
			 
				DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
			                             .newDocumentBuilder();
			 
				Document doc = dBuilder.parse(file);
			 
				//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			 
				if (doc.hasChildNodes()) {
 
					NodeList root=doc.getChildNodes();
					Node node_course= getNode("Course",root);// read from most outer 
					NodeList node_inner= node_course.getChildNodes();
					for(int i=0;i<node_inner.getLength();i++)// reads into inner loops which has
//department, number, title, teacher, student
					{
						 if(node_inner.item(i).getNodeName() == "department")
						 {	
							 course.setDepartment(node_inner.item(i).getTextContent());
							 
							   //System.out.print(node_inner.item(i).getTextContent());
						 } 
						 if(node_inner.item(i).getNodeName() == "number")
						 {	
							  course.setNumber( Integer.parseInt( node_inner.item(i).getTextContent()));
							 
							   // System.out.print(node_inner.item(i).getTextContent());
						 } 
						 if(node_inner.item(i).getNodeName() == "title")
						 {	
							 course.setTitle( (node_inner.item(i).getTextContent()));
							 
							   //System.out.print(node_inner.item(i).getTextContent());
						 } 		
						 if(node_inner.item(i).getNodeName() == "teacher")
						 {	
							// course.setTitle( (node_inner.item(i).getTextContent()));
							  Person teacher=new Person();
							 NodeList node_teacher= node_inner.item(i).getChildNodes();
							 for(int j=0; j<node_teacher.getLength();j++){
							//reads into node teacher
								 NodeList node_person= node_teacher.item(j).getChildNodes();
								
								 for(int k=0;k<node_person.getLength();k++)
								 {
									 
									 
									 if(node_person.item(k).getNodeName()=="name")
									 {
										  //System.out.print("name1"+(node_person.item(k).getTextContent()));
										 teacher.setName((node_person.item(k).getTextContent()));
									 }
									 if(node_person.item(k).getNodeName()=="id")
									 {
										 teacher.setId(Integer.parseInt(node_person.item(k).getTextContent()));
									 }
									 
									 
								 }
								//System.out.println("name"+teacher.getName());
								//System.out.println("id"+teacher.getId()); 
								 
							 } 	
							 course.setTeacher(teacher);  
						 }
						 if(node_inner.item(i).getNodeName() == "students"){
							 //reads into node students 
							 // System.out.println( node_inner.item(i).getTextContent()+" I " + Integer.toString(i));
							  
							 List<Person> studentlist = new ArrayList<Person>() ;// temp list to add to main
							 Person student=new Person();//temp student
							  NodeList node_person;
							 NodeList node_student= node_inner.item(i).getChildNodes(); 
							 //System.out.print( node_student.getLength()+ "node ");
							 for(int j=0;j<node_student.getLength();j++)//person
							 {
								 //System.out.print(j);
								 //System.out.println("name" +node_student.item(3).getTextContent()+" k " +3);
								 node_person= node_student.item(j).getChildNodes();  
								 
								 for(int k=0;k<node_person.getLength();k++)
								 {

									//System.out.println(node_person.item(k).getNodeName());
									 if(node_person.item(k).getNodeName()=="name")
									 {
										 student.setName((node_person.item(k).getTextContent()));
										  // System.out.println("name" +node_person.item(k).getTextContent()+" k " +k);
										 
									 }
									 if(node_person.item(k).getNodeName()=="id")
									 {
										 student.setId(Integer.parseInt(node_person.item(k).getTextContent()));
										 //System.out.println("id" +node_person.item(k).getTextContent()+" k " +k);
										 
									 }
									 
									  
								 }	
								if(student.getName()!= null & j%2==0){
									//System.out.println( student.getName());
									Person temp= new Person();
									temp=makeStudentcopy(student);
									studentlist.add( temp);//  adds the temp student to the list
								}
							 }
								//System.out.println( "adding ");
							 course.setStudents(studentlist) ;// adds to main list
							
						 }
						 
						 
						 
						 
					}
					
					
			 	 testinput(course);
				}
			 
			    } catch (Exception e) {
				System.out.println(e.getMessage());
			    }
			 
			  }
	    public static Person makeStudentcopy(Person stud)
	    {
	    	Person temp= new Person();
	    	temp.setName(stud.getName());
	    	temp.setId(stud.getId());
	    	
	    	
	    	return temp;
	    	
	    }
			 
		private static void testinput(Course course)
		{
			
			System.out.println("Department "+course.getDepartment() ) ;
			System.out.println("number "+course.getNumber() ) ;
			System.out.println("Title "+course.getTitle() ) ;
			System.out.println("Teacher " ) ;
			Person teacher=course.getTeacher();
			System.out.println("Name "+teacher.getName() ) ;
			System.out.println("ID "+teacher.getId() ) ;
			List<Person> student= course.getStudents();
			student.iterator();
			
			for(int i=0;i<student.size();i++){
				 //System.out.print("in loop");
				Person temp= student.get(i); 
				 System.out.println("Name "+temp.getName()) ;
				 System.out.println("ID "+teacher.getId() ) ;	
			}
			
			
			 
			
		}
			  protected static Node getNode(String tagName, NodeList nodes) {
				    for ( int i = 0; i < nodes.getLength(); i++ ) {
				        Node temp = nodes.item(i);
				        if (temp.getNodeName().equalsIgnoreCase(tagName)) {
				            return temp;
				        }
				    }
				 
				    return null;
				}
 
			   
	}

