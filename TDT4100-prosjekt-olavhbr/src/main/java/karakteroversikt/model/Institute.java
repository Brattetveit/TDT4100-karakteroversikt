package karakteroversikt.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Institute {
    private static final List<Character> charGrades = new ArrayList<>(Arrays.asList('F', 'E', 'D', 'C', 'B', 'A'));
    private List<Student> students = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();

    public void addStudent(Student student) {
        if (students.stream().map(s -> s.getStudentID()).anyMatch(id -> id.equals(student.getStudentID()))){ //hvis det finnes en student med samme ID
            throw new IllegalStateException("Student med studentID finnes allerede");
        }
        students.add(student);
    }

    public void removeStudent(Student student) {
        // Student student = new Student(name, studentID);
        if (!students.contains(student)){
            throw new IllegalStateException("Studenten finnes ikke");
        }
        students.remove(student);
        courses.stream().forEach(course -> course.removeStudent(student));
    }
    
    public void addCourse(Course course) {
        if (courses.stream().map(c -> c.getCourseCode()).anyMatch(code -> code.equals(course.getCourseCode()))){ //hvis kurset allerede finnes
            throw new IllegalStateException("Kurset finnes allerede");
        }
        courses.add(course);
    }

    public void removeCourse(Course course) {
        // Course course = new Course(courseName, courseCode);
        if (!courses.contains(course)){
            throw new IllegalStateException("Kurset finnes ikke");
        }
        courses.remove(course);
        students.stream().forEach(student -> student.removeCourse(course));
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public Course getCourse(String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)){
                return course;
            }
        }
        throw new IllegalArgumentException("Kurset finnes ikke");
    }

    public Student getStudent(String studentID) {
        for (Student student : students) {
            if (student.getStudentID().equals(studentID)){
                return student;
            }
        }
        throw new IllegalArgumentException("Studenten finnes ikke");
    }

    public char handleComplain(char grade) {
        int gradeValue = charGrades.indexOf(grade);
        Random random = new Random();
        int number = random.nextInt(11);
        if (number < 5){
            return grade;
        }
        else if (number < 7){
            if (gradeValue + 1 < charGrades.size()){
                return charGrades.get(gradeValue + 1);
            }
            else {
                return grade;
            }
        }
        else if (number < 9){
            if (gradeValue - 1 >= 0){
                return charGrades.get(gradeValue - 1);
            }
            else {
                return grade;
            }
        }
        else if (number < 10){
            if (gradeValue + 2 < charGrades.size()){
                return charGrades.get(gradeValue + 2);
            }
            else if (gradeValue + 1 < charGrades.size()){
                return charGrades.get(gradeValue + 1);
            }
            else{
                return grade;
            }
        }
        else{
            if (gradeValue - 2 >= 0){
                return charGrades.get(gradeValue - 2);
            }
            else if (gradeValue - 1 >= 0){
                return charGrades.get(gradeValue - 1);
            }
            else{
                return grade;
            }
        }
    }

    @Override
    public String toString() {
        return "Institute [students=" + students + ", courses=" + courses + "]";
    }

    

}
