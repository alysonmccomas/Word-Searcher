package objectserialization;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class ObjectSerialization {

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException  {
		File file = new File("students.txt");
		ArrayList<Student> students = new ArrayList<Student>();
		students.add(new Student("Tom", 3.921));
		students.add(new Student("Dave", 4.0));
		students.add(new Student("Bill", 2.0));
		
		//serialize the collection of students
		FileOutputStream fo = new FileOutputStream(file);
		ObjectOutputStream output = new ObjectOutputStream(fo);
		for (Student s : students) {
			output.writeObject(s);			
		}
		output.close();
		fo.close();
		//deserialize the file back into the collection
		FileInputStream fi = new FileInputStream(file);
		ObjectInputStream input = new ObjectInputStream(fi);
		ArrayList<Student> students2 = new ArrayList<Student>();
		try {
			while (true) {
				Student s = (Student)input.readObject();
				students2.add(s);
			}
		} catch (EOFException ex) {
		}
		for(Student s : students2) {
			System.out.println(s);
		}
		
	}
	

}