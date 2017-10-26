package ee.kertmannik.quiz.client;

import org.junit.Test;

public class QuestionControllerTest {

    @Test
    public void should_return_java_object_when_rawQuestion_is_given() {
        //given
        QuestionController questionController = new QuestionController("anyPlayer");
        String jsonString = "{\"id\":\"anyId\",\"anyQuestion\":\"anything\",\"category\":\"anyCategory\",\"difficuly\":1}";
        //when
        String result = questionController.

        //then

    }
}