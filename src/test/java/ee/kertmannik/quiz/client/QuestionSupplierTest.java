package ee.kertmannik.quiz.client;

import ee.kertmannik.quiz.client.model.Question;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class QuestionSupplierTest {

    @Test
    public void should_return_question_object() throws IOException, QuizException {
        //given
        QuestionRequest mock = mock(QuestionRequest.class);
        QuestionSupplier questionSupplier = new QuestionSupplier("anyPlayer", mock);
        given(mock.getQuestionFromServer("anyPlayer")).willReturn(
                "{\"questionId\":\"anyId\",\"question\":\"anyQuestion\",\"category\":\"anyCategory\",\"difficulty\":1}");

        //when
        Question question = questionSupplier.requestQuestion();

        //then
        assertThat(question.getCategory()).isEqualTo("anyCategory");
        assertThat(question.getDifficulty()).isEqualTo(1);
        assertThat(question.getQuestion()).isEqualTo("anyQuestion");
        assertThat(question.getQuestionId()).isEqualTo("anyId");
    }
}