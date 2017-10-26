package ee.kertmannik.quiz.client;

import ee.kertmannik.quiz.client.model.Question;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class QuestionControllerTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Test
    public void should_return_question_object() throws IOException, QuizException {
        //given
        QuestionSupplier mock = mock(QuestionSupplier.class);
        QuestionController questionController = new QuestionController(mock);
        given(mock.requestQuestion()).willReturn(new Question("anyId", "AnyQuestion", "anyCategory", 999,
                Arrays.asList("anyAnswers")));

        //when
        questionController.getQuestion();

        //then
        assertThat(this.systemOutRule.getLog()).isEqualTo("\n" + "(anyCategory, 999) AnyQuestion");
    }
}