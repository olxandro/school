package ru.hogwarts.school;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SchoolApplicationTests {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private StudentController studentController;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void testAuthor() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost" + port +
                "/info",String.class))
                .isEqualTo("Author of this application is Good person!");
    }

    @Test
    public void testGetStudent() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:"
                        + port + "/student/1", String.class))
                .isNotEmpty();
    }

    @Test
    public void testPostUser() throws Exception {
        Student student = new Student();
        student.setName("Red");
        student.setAge(20);

        Assertions.assertThat(this.restTemplate.postForObject("http://localhost:" +
                port + "/student", student, String.class)).isNotNull();
    }
}
