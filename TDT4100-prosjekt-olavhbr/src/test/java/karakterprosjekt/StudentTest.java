package karakterprosjekt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import karakteroversikt.model.Course;
import karakteroversikt.model.Student;

public class StudentTest {
    
    private Student student;

    @BeforeEach
    public void setup() {
        student = new Student("Olav", "123456");
    }

    @Test
    public void testConstructor() {
        assertEquals(student.getName(), "Olav");
        assertEquals(student.getStudentID(), "123456");
        assertThrows(IllegalArgumentException.class, () -> new Student("Olav", "ikke tall"));
    }

    @Test
    public void testAddCourse() {
        Course course = new Course("Objektorientert programmering", "TDT4100");
        student.addCourse(course, 'B');
        assertTrue(student.getCourses().containsKey(course));
        assertEquals(student.getCourses().get(course), 'B');
        assertThrows(IllegalStateException.class, () -> student.addCourse(course, 'A'));
        assertThrows(IllegalArgumentException.class, () -> student.addCourse(new Course("Fag", "123456"), 'O'));
        }

    @Test
    public void testRemoveCourse() {
        Course course = new Course("Objektorientert programmering", "TDT4100");
        student.addCourse(course, 'B');
        student.removeCourse(course);
        assertTrue(student.getCourses().isEmpty());
        assertThrows(IllegalStateException.class,() -> student.removeCourse(course), "Kan ikke fjerne samme emne på nytt");
    }

    @Test
    public void testGrades() {
        Course course1 = new Course("Course1", "a1");
        Course course2 = new Course("Course2", "b2");
        Course course3 = new Course("Course3", "c3");
        student.addCourse(course1, 'B');
        student.addCourse(course2, 'E');
        student.addCourse(course3, 'B');
        assertEquals(student.getAverageGrade(), 3.0);
        assertEquals(student.getMedianGrade(), "B");
        assertEquals(student.getHighestGrade(), "B");
        assertEquals(student.getLowestGrade(), "E");
    }

}    

