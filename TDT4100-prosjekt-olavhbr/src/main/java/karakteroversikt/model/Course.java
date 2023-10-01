package karakteroversikt.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Course implements Comparable<Course>{
    
    private static final List<Character> charGrades = new ArrayList<>(Arrays.asList('F', 'E', 'D', 'C', 'B', 'A'));
    private String courseName;
    private String courseCode;
    private Map<String, Character> students = new HashMap<>();
    private Map<Character, Integer> gradeDistribution = new HashMap<>(Map.of('A', 0, 'B', 0, 'C', 0, 'D', 0, 'E', 0, 'F', 0));

    public Course(String courseName, String courseCode){
        if (!isValidCourseCode(courseCode) || courseName.length() == 0){
            throw new IllegalArgumentException("Ugyldig navn/emnekode");
        }
        this.courseName = courseName;
        this.courseCode = courseCode;
    }

    private boolean isValidCourseCode(String courseCode) {
        boolean containsLetter = false;
        boolean containsDigit = false;
        for (int i = 0; i < courseCode.length(); i++) {
            char c = courseCode.charAt(i);
            if (Character.isLetter(c)) {
                containsLetter = true;
            } else if (Character.isDigit(c)) {
                containsDigit = true;
            }
        }
        return containsLetter && containsDigit;
    }

    public String getCourseName(){
        return courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public int getNumberOfStudents(){
        return students.size();
    }

    public Map<String, Character> getStudents() {
        return students;
    }

    public Map<Character, Integer> getGradeDistribution() {
        return gradeDistribution;
    }

    public double getAverageGrade(){
        if (students.size() == 0){
            return 0;
        }
        return students.values()
        .stream()
        .mapToDouble(grade -> charGrades.indexOf(grade))
        .average()
        .getAsDouble();
    }

    public String getMedianGrade() {
        if (students.size() == 0){
            return "";
        }        
        List<Character> grades = new ArrayList<>(students.values());
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
        if (students.size() == 0){
            return "";
        }        
        return students.values()
        .stream()
        .max((grade1,grade2) -> grade2.compareTo(grade1))
        .get()
        .toString();
    }

    public String getLowestGrade() {
        if (students.size() == 0){
            return "";
        }        
        return students.values()
        .stream()
        .min((grade1,grade2) -> grade2.compareTo(grade1))
        .get()
        .toString();
    }

    public void addStudent(Student student, char grade) {
        if (students.containsKey(student.getStudentID())){
            throw new IllegalStateException("Tar allerede faget");
        }
        if (!charGrades.contains(grade)){
            throw new IllegalArgumentException("Ugyldig karakter");
        }
        students.put(student.getStudentID(), grade);
        gradeDistribution.put(grade, gradeDistribution.get(grade) + 1);
    }

    public void removeStudent(Student student) {
        if (students.containsKey(student.getStudentID())){
            char grade = students.get(student.getStudentID());
            gradeDistribution.put(grade, gradeDistribution.get(grade) - 1);
            students.remove(student.getStudentID());
        }
        else{
            throw new IllegalStateException("Tar ikke faget");
        }
    }

    @Override
    public String toString() {
        return getCourseCode() + "-" + getCourseName() + " (" + getAverageGrade() + ")";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((courseName == null) ? 0 : courseName.hashCode());
        result = prime * result + ((courseCode == null) ? 0 : courseCode.hashCode());
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
        Course other = (Course) obj;
        if (courseName == null) {
            if (other.courseName != null)
                return false;
        } else if (!courseName.equals(other.courseName))
            return false;
        if (courseCode == null) {
            if (other.courseCode != null)
                return false;
        } else if (!courseCode.equals(other.courseCode))
            return false;
        return true;
    }

    @Override
    public int compareTo(Course o) {
        return Double.compare(o.getAverageGrade(), this.getAverageGrade());
    }
}
