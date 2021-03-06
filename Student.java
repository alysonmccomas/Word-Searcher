package objectserialization;

import java.io.Serializable;

public class Student implements Serializable {
    private String Name;
    private double GPA;

    public Student(String name, double gpa) {
        this.Name = name;
        this.GPA = gpa;
    }
    
    public String setName(String newName) {
        return (this.Name = newName);
    }
    
    public String getName() {
        return this.Name;
    }
    
    public double setGPA(double newGPA) {
        return (this.GPA = newGPA);
     }

    public double getGPA() {
        return this.GPA;
    }

    @Override
    public String toString() {
    	return String.format("%s\t%f",this.Name, this.GPA);
    }
}