package karakteroversikt.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student {
    
    private static final List<Character> charGrades = new ArrayList<>(Arrays.asList('F', 'E', 'D', 'C', 'B', 'A'));
    private String name;
    private String studentID;
    private Map<Course, Character> courses = new HashMap<>();

    public Student(String name, String studentID){
        if (name.length() == 0 || studentID.length() != 6){
            throw new IllegalArgumentException("Ugyldig navn/studentID");
        }
        for (int i = 0; i < studentID.length(); i++) {
            if (!Character.isDigit(studentID.charAt(i))){
                throw new IllegalArgumentException("Ugyldig studentID");
            }
        }
        this.name = name;
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public String getStudentID() {
        return studentID;
    }

    public Map<Course, Character> getCourses() {
        return courses;
    }

    public char getGrade(Course course){
        if (!courses.containsKey(course)){
            throw new IllegalStateException(name + " tar ikke faget "+course);
        }
        return courses.get(course);
    }

    public void addCourse(Course course, char grade){
        if (courses.containsKey(course)){
            throw new IllegalStateException("Tar allerede faget");
        }
        if (!charGrades.contains(grade)){
            throw new IllegalArgumentException("Ugyldig karakter");
        }
        if (course == null){
            throw new IllegalArgumentException("Du må velge fag");
        }
        courses.put(course, grade);
        course.addStudent(this, grade);
    }

    public void removeCourse(Course course) {
        if (courses.containsKey(course)){
            courses.remove(course);
        }
        else{
            throw new IllegalStateException("Tar ikke faget");
        }
    }

    public double getAverageGrade(){
        if (courses.size() == 0){
            return 0;
        }
        return courses.values()
        .stream()
        .mapToDouble(grade -> charGrades.indexOf(grade))
        .average()
        .getAsDouble();
    }

    public String getMedianGrade() {
        if (courses.size() == 0){
            return "";
        }        
        List<Character> grades = new ArrayList<>(courses.values());
        Collections.sort(grades); 
        if (grades.size() % 2 != 0){ //hvis oddetall antall karakterer
            return (grades.get(grades.size()/2)).toString();
        }
        else if (grades.get(grades.size()/2).equals(grades.get(grades.size()/2 - 1))){ //hvis partall og karakterene i midten er like
            return (grades.get(grades.size()/2)).toString();
        }
        double medianValue = ((double)charGrades.indexOf(grades.get(grades.size()/2)) + charGrades.indexOf(grades.get(grades.size()/2 - 1)))/2;
        if (medianValue % 1 == 0) {
            return charGrades.get((int)medianValue).toString();
        } else {
            char grade1 = charGrades.get((int)Math.floor(medianValue));
            char grade2 = charGrades.get((int)Math.ceil(medianValue));
            return grade2 + "/" + grade1;
        }

    }

    public String getHighestGrade() {
        if (courses.size() == 0){
            return "";
        }
        return courses.values()
        .stream()
        .max((grade1,grade2) -> grade2.compareTo(grade1))
        .get()
        .toString();
    }

    public String getLowestGrade() {
        if (courses.size() == 0){
            return "";
        }
        return courses.values()
        .stream()
        .min((grade1,grade2) -> grade2.compareTo(grade1))
        .get()
        .toString();
    }


    @Override
    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", studentID='" + getStudentID() + "'" +
            "}";
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((studentID == null) ? 0 : studentID.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (studentID == null) {
            if (other.studentID != null)
                return false;
        } else if (!studentID.equals(other.studentID))
            return false;
        return true;
    }

    public static void main(String[] args) {
        Course OOP = new Course("OOP", "1234");
        Course matte1 = new Course("matte 1", "TMA4100");
        Student student = new Student("Olav", "123");
        Student student2 = new Student("Ola", "1234");
        student.addCourse(OOP, 'A');
        student.addCourse(matte1, 'B');
        student2.addCourse(OOP, 'B');
        student.addCourse(new Course("matte", "123"), 'B');
        // System.out.println(student.getAverageGrade());
        // System.out.println(student.getMedianGrade());
        // System.out.println(student.getLowestGrade());
        System.out.println(OOP.getMedianGrade());

    }
    
}
