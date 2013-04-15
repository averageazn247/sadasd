import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XmlMarshaller {
	Course course=new Course();
	public static void unmarshall(Course course)
	{
		//Course temp=new Course();
		String temp="";
		try {
				
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			
			docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Course");
			
			doc.appendChild(rootElement);
			// department elements
			Element dep = doc.createElement("department");
			rootElement.appendChild(dep);
			dep.appendChild(doc.createTextNode(course.getDepartment()));
			
			Element num = doc.createElement("number");
			rootElement.appendChild(num);
			num.appendChild(doc.createTextNode(Integer.toString(course.getNumber())));
			
			Element tit=doc.createElement("title");
			rootElement.appendChild(tit);
			tit.appendChild(doc.createTextNode(course.getTitle()));
			
			Element teac=doc.createElement("teacher");
			rootElement.appendChild(teac);
			
			Element pteac=doc.createElement("Person");
			teac.appendChild(pteac);
			
			Element tname=doc.createElement("name");
			pteac.appendChild(tname);
			tname.appendChild(doc.createTextNode(course.getTeacher().getName()));
			
			Element tid=doc.createElement("id");
			pteac.appendChild(tid);
			tid.appendChild(doc.createTextNode(Integer.toString(course.getTeacher().getId())));
			
			Element stud=doc.createElement("students");
			rootElement.appendChild(stud);
			
			for(int i=0;i<course.getStudents().size();i++)
			{
				//System.out.println("number " +i);
				Element tempP=doc.createElement("Person");
				stud.appendChild(tempP);
				
					Element tempS= doc.createElement("name");
					tempP.appendChild(tempS);
					tempS.appendChild(doc.createTextNode(course.getStudents().get(i).getName()));
					
					Element tempI= doc.createElement("id");
					tempP.appendChild(tempI);
					tempI.appendChild(doc.createTextNode(Integer.toString(course.getStudents().get(i).getId())));
					
					
					
			}
			
			 
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("output_file.xml"));
	 
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
	 
			transformer.transform(source, result);
	 
			System.out.println("File saved!");
			}
		catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
 
		// root elements
		
		
		 
	}
	public static Course marshall ( String filename)
	{ 
		

		 try {
			 
				File file = new File(filename);//reads in file
			 
				DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
			                             .newDocumentBuilder();
			 
				Document doc = dBuilder.parse(file); 
				return  readxml(doc);
				 
				 
			 
			    } catch (Exception e) {
				System.out.println(e.getMessage());
			    }
		return null;
			 
			  }
	    public static Person makeStudentcopy(Person stud)
	    {
	    	Person temp= new Person();
	    	temp.setName(stud.getName());
	    	temp.setId(stud.getId());
	    	
	    	
	    	return temp;
	    	
	    }
		private static Course readxml(Document doc)
		{
			Course course= new Course();
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
									 teacher.setName((node_person.item(k).getTextContent()));
								 }
								 if(node_person.item(k).getNodeName()=="id")
								 {
									 teacher.setId(Integer.parseInt(node_person.item(k).getTextContent()));
								 }
								 
								 
							 }
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
						 for(int j=0;j<node_student.getLength();j++)//person
						 {
							 node_person= node_student.item(j).getChildNodes();  
							 
							 for(int k=0;k<node_person.getLength();k++)
							 { 
								 if(node_person.item(k).getNodeName()=="name")
								 {
									 student.setName((node_person.item(k).getTextContent()));
									  // System.out.println("name" +node_person.item(k).getTextContent()+" k " +k);
									 
								 }
								 if(node_person.item(k).getNodeName()=="id")
								 {
									 student.setId(Integer.parseInt(node_person.item(k).getTextContent())); 
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
			}
		String	parsered=makestring(course);
		//System.out.print(parsered);
		return course;
		}
		public static String makestring(Course course)
		{
			 
			StringBuilder sb = new StringBuilder(1000);
			sb.append("Department :"+course.getDepartment()+"\n" );
			sb.append("number :"+course.getNumber() +'\n' );
			sb.append("Title "+course.getTitle() +'\n' );
			sb.append("Teacher \n" );
			Person teacher=course.getTeacher();
			sb.append("Name "+teacher.getName()+'\n');
			
			sb.append("ID :"+teacher.getId() +'\n' );
			List<Person> student= course.getStudents();
			sb.append("Student list \n");
			for(int i=0;i<student.size();i++){
				
				Person temp= student.get(i);
				if(temp!=null){
					sb.append("Name :" +temp.getName() +'\n');
				 	sb.append( "ID : "+ temp.getId() +'\n');
				}
			}
			 
			return sb.toString();
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
