package karakterprosjekt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import karakteroversikt.model.Course;
import karakteroversikt.model.Student;

public class CourseTest {
    
    private Course course;

    @BeforeEach
    public void setup() {
        course = new Course("Objektorientert programmering", "TDT4100");
    }

    @Test
    public void testConstructor() {
        assertEquals(course.getCourseName(), "Objektorientert programmering");
        assertEquals(course.getCourseCode(), "TDT4100");
        assertThrows(IllegalArgumentException.class, () -> new Course("", ""));
    }

    @Test
    public void testAddStudent() {
        Student student = new Student("Olav", "123456");
        course.addStudent(student, 'A');
        assertTrue(course.getStudents().containsKey(student.getStudentID()));
        assertEquals(course.getStudents().get(student.getStudentID()), 'A');
        assertEquals(course.getGradeDistribution().get('A'), 1);
        assertThrows(IllegalStateException.class,() -> course.addStudent(student, 'B'), "Kan ikke legge til samme student på nytt");
        assertThrows(IllegalArgumentException.class, () -> course.addStudent(new Student("Navn", "111"), 'K'), "Ugyldig karakter");
    }

    @Test
    public void testRemoveStudent() {
        Student student = new Student("Olav", "123456");
        course.addStudent(student, 'A');
        course.removeStudent(student);
        assertTrue(course.getStudents().isEmpty());
        assertEquals(course.getGradeDistribution().get('A'), 0);
        assertThrows(IllegalStateException.class,() -> course.removeStudent(student), "Kan ikke fjerne samme student på nytt");
    }

    @Test
    public void testGrades() {
        Student student1 = new Student("student1", "111111");
        Student student2 = new Student("student2", "222222");
        course.addStudent(student1, 'A');
        course.addStudent(student2, 'D');
        assertEquals(course.getAverageGrade(), 3.5);
        assertEquals(course.getMedianGrade(), "B/C");
        assertEquals(course.getHighestGrade(), "A");
        assertEquals(course.getLowestGrade(), "D");
    }

    
    
}
