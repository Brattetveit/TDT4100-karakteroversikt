package karakterprosjekt;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import karakteroversikt.model.Course;
import karakteroversikt.model.Institute;
import karakteroversikt.model.Student;

public class InstituteTest {
    
    private Institute institute = new Institute();


    @Test
    public void testAddStudent() {
        Student student = new Student("Olav", "123456");
        institute.addStudent(student);
        assertTrue(institute.getStudents().contains(student));
        assertThrows(IllegalStateException.class, () -> institute.addStudent(student));
    }

    @Test
    public void testAddCourse() {
        Course course = new Course("Objektorientert programmering", "TDT4100");
        institute.addCourse(course);
        assertTrue(institute.getCourses().contains(course));
        assertThrows(IllegalStateException.class, () -> institute.addCourse(new Course("Ny objektorientert programmering", "TDT4100")), "kan ikke legge til nytt fag med samme emnekode");
    }

    @Test
    public void testRemoveStudent() {
        Student student = new Student("Olav", "123456");
        institute.addStudent(student);
        institute.removeStudent(student);
        assertTrue(institute.getStudents().isEmpty());
        assertThrows(IllegalStateException.class, () -> institute.removeStudent(student));
    }

    @Test
    public void testRemoveCourse() {
        Course course = new Course("Objektorientert programmering", "TDT4100");
        institute.addCourse(course);
        institute.removeCourse(course);
        assertTrue(institute.getCourses().isEmpty());
        assertThrows(IllegalStateException.class, () -> institute.removeCourse(new Course("Objektorientert programmering", "TDT4100")), "kan ikke fjerne emne som ikke eksisterer");
    }


}
