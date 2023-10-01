package karakteroversikt.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Map.Entry;

public class SaveHandler {


    //TODO: lagre studenter og fag ved instituttet og kunne hente de ut ved hjelp av load

    public void save(String studentFilename, String courseFilename, Institute institute) throws FileNotFoundException{
        // try (PrintWriter studentWriter = new PrintWriter("src/main/resources/saves/savedStudents.txt")) {
        try (PrintWriter studentWriter = new PrintWriter(studentFilename)) {
            for (Student student : institute.getStudents()) {
                String studentPrintedLine = student.getName()+ "," +student.getStudentID();
                for (Entry<Course,Character> entry : student.getCourses().entrySet()) {
                    studentPrintedLine += ","+entry.getKey().getCourseCode()+","+entry.getKey().getCourseName()+","+entry.getValue();
                }
                System.out.println(studentPrintedLine);
                studentWriter.println(studentPrintedLine);
            }
            studentWriter.flush();
        }
        // try (PrintWriter courseWriter = new PrintWriter("src/main/resources/saves/savedCourses.txt")) {
        try (PrintWriter courseWriter = new PrintWriter(courseFilename)) {
            for (Course course : institute.getCourses()) {
                String coursePrintedLine = course.getCourseName() + "," + course.getCourseCode();
                for (Entry<String,Character> entry : course.getStudents().entrySet()) {
                    coursePrintedLine += "," + entry.getKey() + "," + entry.getValue();
                }
                System.out.println(coursePrintedLine);
                courseWriter.println(coursePrintedLine);
            }
            courseWriter.flush();
        }
    }

    public Institute load(String studentFilename, String courseFilename) throws FileNotFoundException {
        Institute institute = new Institute();
        try (Scanner studentScanner = new Scanner(new File(studentFilename))){ //new Scanner("src/main/resources/saves/savedStudents.txt")){
            while (studentScanner.hasNextLine()){
                String[] studentInfo = studentScanner.nextLine().split(",");
                String name = studentInfo[0];
                String studentID = studentInfo[1];
                Student student = new Student(name, studentID);
                for (int i = 2; i < studentInfo.length; i+=3) {
                    student.addCourse(new Course(studentInfo[i+1], studentInfo[i]), studentInfo[i+2].charAt(0));
                }
                institute.addStudent(student);
            }
        }
        try (Scanner courseScanner = new Scanner(new File(courseFilename))){
            while (courseScanner.hasNextLine()){
                String[] courseInfo = courseScanner.nextLine().split(",");
                String courseName = courseInfo[0];
                String courseCode = courseInfo[1];
                Course course = new Course(courseName, courseCode);
                for (int i = 2; i < courseInfo.length; i+=2) {
                    course.addStudent(institute.getStudent(courseInfo[i]), courseInfo[i+1].charAt(0));
                }
                institute.addCourse(course);
            }
        }
        return institute;
    }

    public static void main(String[] args) {
        SaveHandler saveHandler = new SaveHandler();
        Institute institute = new Institute();
        Student olav = new Student("Olav", "123");
        Student ola = new Student("Ola", "12");
        Course matte = new Course("Matte 1", "TMA4100");
        olav.addCourse(matte, 'A');
        ola.addCourse(matte, 'C');
        institute.addStudent(olav);
        institute.addStudent(ola);
        institute.addCourse(matte);

        try {
            saveHandler.save("src/main/resources/saves/savedStudents.txt", "src/main/resources/saves/savedCourses.txt", institute);
            saveHandler.load("src/main/resources/saves/savedStudents.txt", "src/main/resources/saves/savedCourses.txt");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
