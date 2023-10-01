package karakteroversikt.UI;

import java.io.FileNotFoundException;
import java.util.Collections;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import karakteroversikt.model.Course;
import karakteroversikt.model.Institute;
import karakteroversikt.model.SaveHandler;
import karakteroversikt.model.Student;

public class ProjectController {

    private Institute institute;
    private SaveHandler saveHandler = new SaveHandler();
    private Student currentStudent;
    private Course currentCourse;
    
    @FXML private ListView<Course> coursesList = new ListView<>();
    @FXML private TextField nameForAddedCourse, codeForAddedCourse, nameForAddedStudent, studentIDForAddedStudent, studentIDForAddingGrade, textFieldAddedGrade;
    @FXML private Button addCourseButton, removeCourseButton, addStudentButton, removeStudentButton, addGradeButton;
    @FXML private Text studentAverageGrade, studentMedianGrade, studentHighestGrade, studentLowestGrade, studentGrades;
    @FXML private ComboBox<Student> studentChoices;
    @FXML private ListView<String> studentCourses = new ListView<>();
    @FXML private Text courseGrades, courseTotalAs, courseTotalBs, courseTotalCs, courseTotalDs, courseTotalEs, courseTotalFs;
    @FXML private Text courseTotalStudents, courseAverageGrade, courseMedianGrade, courseHighestGrade, courseLowestGrade;
    @FXML private Button saveButton, loadButton;
    @FXML private Button complainGradeButton;

    @FXML
    public void initialize() {
        institute = new Institute();
        System.out.println("det funker");
    }

    @FXML
    public void addStudent(){
        try {
            String name = nameForAddedStudent.getText();
            String studentID = studentIDForAddedStudent.getText();
            Student student = new Student(name, studentID);
            institute.addStudent(student);
            nameForAddedStudent.setText("");
            studentIDForAddedStudent.setText("");
            studentChoices.getItems().add(student);
        } catch (IllegalArgumentException e1) {
            showAlert(e1.getLocalizedMessage());
        } catch (IllegalStateException e2){
            showAlert(e2.getLocalizedMessage());
        }
    }

    @FXML
    public void removeStudent(){
        try {
            String name = nameForAddedStudent.getText();
            String studentID = studentIDForAddedStudent.getText();
            Student student = new Student(name, studentID);
            institute.removeStudent(student);
            nameForAddedStudent.setText("");
            studentIDForAddedStudent.setText("");
            studentChoices.getItems().remove(student);
            updateCourses();
            if (institute.getCourses().size() > 0){
                updateGradeDistribution(institute.getCourses().get(institute.getCourses().size()-1));
            } else{
                setDefaultGradeDistribution();
            }
            if (institute.getStudents().size() > 0){
                updateStudentCourses(institute.getStudents().get(institute.getStudents().size()-1));
            } else{
                setDefaultStudentCourses();
            } 
        } catch (IllegalArgumentException e1) {
            showAlert(e1.getLocalizedMessage());
        } catch (IllegalStateException e2){
            showAlert(e2.getLocalizedMessage());
        }
    }

    @FXML
    public void changeStudentShown() {
        currentStudent = studentChoices.getSelectionModel().getSelectedItem();
        updateStudentCourses(currentStudent);
    }

    @FXML
    public void addCourse(){
        try {
            Course course = new Course(nameForAddedCourse.getText(), codeForAddedCourse.getText());
            institute.addCourse(course);
            nameForAddedCourse.setText("");
            codeForAddedCourse.setText("");
            // System.out.println(institute.getCourses());
            updateCourses();    
        } catch (IllegalArgumentException e1) {
            showAlert(e1.getLocalizedMessage());
        } catch (IllegalStateException e2){
            showAlert(e2.getLocalizedMessage());
        }
    }

    @FXML
    public void removeCourse(){
        try {
            Course course = new Course(nameForAddedCourse.getText(), codeForAddedCourse.getText());
            institute.removeCourse(course);
            nameForAddedCourse.setText("");
            codeForAddedCourse.setText("");
            updateCourses();
            if (institute.getCourses().size() > 0){
                updateGradeDistribution(institute.getCourses().get(institute.getCourses().size()-1));
            } else{
                setDefaultGradeDistribution();
            }
            if (institute.getStudents().size() > 0){
                updateStudentCourses(institute.getStudents().get(institute.getStudents().size()-1));
            }
            else{
                setDefaultStudentCourses();
            }
        } catch (IllegalArgumentException e1) {
            showAlert(e1.getLocalizedMessage());
        } catch (IllegalStateException e2){
            showAlert(e2.getLocalizedMessage());
        }
    }

    @FXML
    public void addGrade() {
        try {
            String studentID = studentIDForAddingGrade.getText();
            char grade = textFieldAddedGrade.getText().charAt(0);
            currentCourse = coursesList.getSelectionModel().getSelectedItem();
            currentStudent = institute.getStudent(studentID);
            currentStudent.addCourse(currentCourse, grade);
            textFieldAddedGrade.setText("");
            studentIDForAddingGrade.setText("");
            coursesList.getSelectionModel().clearSelection();
            updateCourses();
            updateStudentCourses(currentStudent);
            updateGradeDistribution(currentCourse);
        } catch (IllegalArgumentException e1) {
            showAlert(e1.getLocalizedMessage());
        } catch (IllegalStateException e2){
            showAlert(e2.getLocalizedMessage());
        } catch (IndexOutOfBoundsException e3){
            showAlert("Du må fylle inn alle felter");
        }
    }

    @FXML
    public void complainGrade() {
        try {
            String courseString = studentCourses.getSelectionModel().getSelectedItem();
            studentCourses.getSelectionModel().clearSelection();
            currentCourse = institute.getCourse(courseString.split("-")[0]);
            char grade = currentStudent.getGrade(currentCourse);
            currentStudent.removeCourse(currentCourse);
            currentCourse.removeStudent(currentStudent);
            char newGrade = institute.handleComplain(grade);
            currentStudent.addCourse(currentCourse, newGrade);
            Alert info = new Alert(AlertType.INFORMATION);
            info.setTitle("Informasjon om klage på karakter");
            info.setHeaderText("Din klage har blitt behandlet");
            info.setContentText("Din nye karakter i "+currentCourse.getCourseName()+" er "+newGrade);
            info.showAndWait();
            updateCourses();
            updateStudentCourses(currentStudent);
            updateGradeDistribution(currentCourse);
        } catch (IllegalArgumentException e1) {
            showAlert(e1.getLocalizedMessage());
        } catch (IllegalStateException e2){
            showAlert(e2.getLocalizedMessage());
        } catch (NullPointerException e3){
            showAlert("Du må velge et fag");
        }
    }

    @FXML
    public void save(){
        try {
            saveHandler.save("src/main/resources/saves/savedStudents.txt","src/main/resources/saves/savedCourses.txt", institute);
        } catch (FileNotFoundException e) {
            showAlert("Finner ikke filen som skal lagres til");
        }
    }

    @FXML
    public void load() {
        try {
            institute = saveHandler.load("src/main/resources/saves/savedStudents.txt", "src/main/resources/saves/savedCourses.txt");
            updateCourses();
            institute.getStudents()
            .stream()
            .forEach(student -> studentChoices.getItems().add(student));
            if (institute.getCourses().size() > 0){
                updateGradeDistribution(institute.getCourses().get(institute.getCourses().size()-1));
            } else{
                setDefaultGradeDistribution();
            }
            if (institute.getStudents().size() > 0){
                updateStudentCourses(institute.getStudents().get(institute.getStudents().size()-1));
            }
            else{
                setDefaultStudentCourses();
            }
        } catch (FileNotFoundException e) {
            showAlert("Kunne ikke finne filen som skal hentes fra");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Feil");
        alert.setHeaderText("Det har skjedd en feil");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateStudentCourses(Student student) {
        currentStudent = student;
        studentCourses.getItems().clear();
        for (Course studentCourse : student.getCourses().keySet()) {
            studentCourses.getItems().add(studentCourse.getCourseCode() + "-" + studentCourse.getCourseName() + " (" + student.getGrade(studentCourse) + ")");
        }
        studentGrades.setText("Oversikt over "+student.getName()+" ("+student.getStudentID()+") sine karakterer");
        studentAverageGrade.setText("Snittkarakter: "+student.getAverageGrade());
        studentMedianGrade.setText("Mediankarakter: "+student.getMedianGrade());
        studentHighestGrade.setText("Høyeste karakter: "+student.getHighestGrade());
        studentLowestGrade.setText("Laveste karakter: "+student.getLowestGrade());
    }

    private void setDefaultStudentCourses() {
        studentCourses.getItems().clear();
        studentGrades.setText("Oversikt over student (studentID) sine karakterer");
        studentAverageGrade.setText("Snittkarakter: ");
        studentMedianGrade.setText("Mediankarakter: ");
        studentHighestGrade.setText("Høyeste karakter: ");
        studentLowestGrade.setText("Laveste karakter: ");
    }

    private void updateGradeDistribution(Course course){
        courseGrades.setText("Karakterfordeling "+course.getCourseCode()+" - "+course.getCourseName());
        courseTotalAs.setText("A: "+course.getGradeDistribution().get('A'));
        courseTotalBs.setText("B: "+course.getGradeDistribution().get('B'));
        courseTotalCs.setText("C: "+course.getGradeDistribution().get('C'));
        courseTotalDs.setText("D: "+course.getGradeDistribution().get('D'));
        courseTotalEs.setText("E: "+course.getGradeDistribution().get('E'));
        courseTotalFs.setText("F: "+course.getGradeDistribution().get('F'));
        courseTotalStudents.setText("Totalt antall studenter: "+course.getNumberOfStudents());
        courseAverageGrade.setText("Snittkarakter: "+course.getAverageGrade());
        courseMedianGrade.setText("Mediankarakter: "+course.getMedianGrade());
        courseHighestGrade.setText("Høyeste karakter: "+course.getHighestGrade());
        courseLowestGrade.setText("Laveste karakter: "+course.getLowestGrade());
    }

    private void setDefaultGradeDistribution() {
        courseGrades.setText("Karakterfordeling emnekode - fagnavn");
        courseTotalAs.setText("A: ");
        courseTotalBs.setText("B: ");
        courseTotalCs.setText("C: ");
        courseTotalDs.setText("D: ");
        courseTotalEs.setText("E: ");
        courseTotalFs.setText("F: ");
        courseTotalStudents.setText("Totalt antall studenter: ");
        courseAverageGrade.setText("Snittkarakter: ");
        courseMedianGrade.setText("Mediankarakter: ");
        courseHighestGrade.setText("Høyeste karakter: ");
        courseLowestGrade.setText("Laveste karakter: ");
    }

    private void updateCourses(){
        coursesList.getItems().setAll(institute.getCourses());
        Collections.sort(coursesList.getItems());
    }
}
