package ee.kertmannik.quiz.client;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class QuestionControllerTest {

    private QuestionController questionController;

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Before
    public void initialize() {
        this.questionController = new QuestionController("Kert");
    }

    @Test
    public void should_return_question() throws IOException, QuizException {
        //given

        //when
        this.questionController.getQuestion();
        //then
        assertThat(this.systemOutRule.getLog()).isEqualTo("Tere");
    }
}