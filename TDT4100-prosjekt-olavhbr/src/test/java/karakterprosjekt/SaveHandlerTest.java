package karakterprosjekt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import karakteroversikt.model.Course;
import karakteroversikt.model.Institute;
import karakteroversikt.model.SaveHandler;
import karakteroversikt.model.Student;

public class SaveHandlerTest {
    private SaveHandler saveHandler;
    private Institute institute;

    @BeforeEach
    public void setup() {
        institute = new Institute();
        saveHandler = new SaveHandler();
        Student student1 = new Student("Olav", "123456");
        Student student2 = new Student("Ola", "111111");
        Course course = new Course("Objektorientert programmering", "TDT4100");
        institute.addCourse(course);
        institute.addStudent(student1);
        institute.addStudent(student2);
        student1.addCourse(course, 'A');
        student2.addCourse(course, 'D');
    }

    @Test
    public void testSave() {
        try {
            saveHandler.save("src/test/resources/savedStudentsTest.txt", "src/test/resources/savedCoursesTest.txt", institute);
        } catch (FileNotFoundException e) {
            fail("kunne ikke lagre filen");
        }
        List<String> studentFile = null;
        List<String> courseFile = null;

        try {
            studentFile = Files.readAllLines(new File("src/test/resources/savedStudentsTest.txt").toPath());
            courseFile = Files.readAllLines(new File("src/test/resources/savedCoursesTest.txt").toPath());
        } catch (IOException e) {
            fail("Kunne ikke laste filen");
        }
        assertNotNull(studentFile);
        assertNotNull(courseFile);
    }

    @Test
    public void testLoad() {
        Institute savedInstitute = new Institute();
        try {
            savedInstitute = saveHandler.load("src/test/resources/savedStudentsTest.txt", "src/test/resources/savedCoursesTest.txt");
        } catch (FileNotFoundException e) {
            fail("kunne ikke hente filen");
        }

        assertEquals(institute.toString(), savedInstitute.toString());
    }

}
