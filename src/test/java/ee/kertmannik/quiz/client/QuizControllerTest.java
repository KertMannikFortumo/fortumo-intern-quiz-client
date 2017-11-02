package ee.kertmannik.quiz.client;

import ee.kertmannik.quiz.client.model.Question;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class QuizControllerTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Rule
    public final TextFromStandardInputStream systemInMock
            = emptyStandardInputStream();

    @Test
    public void should_return_question_object() throws IOException {
        //given
        QuestionSupplier mock = mock(QuestionSupplier.class);
        QuizController quizController = new QuizController(mock);
        given(mock.requestQuestion()).willReturn(new Question("anyId", "AnyQuestion", "anyCategory", 999,
                Arrays.asList("anyAnswers")));

        //when
        quizController.getQuestion();

        //then
        assertThat(this.systemOutRule.getLog().trim()).isEqualTo("(anyCategory, 999) AnyQuestion");
    }

    @Test
    public void should_continue_game_if_user_answers_correctly_and_wants_to_start_the_game_again() throws IOException {
        //given
        QuestionSupplier mockQuestion = mock(QuestionSupplier.class);
        AnswerSupplier mockAnswer = mock(AnswerSupplier.class);
        CommandLineScanner mockScanner = mock(CommandLineScanner.class);
        QuizController quizController =
                new QuizController(mockAnswer, mockQuestion, new CommandLineScanner(), "correct");
        given(mockScanner.getPlayerDecisionWithValidation(Arrays.asList("anyValidInput"), "anyMessage")).willReturn(
                "anyPlayerDecision");
        given(mockQuestion.requestQuestion()).willReturn(
                new Question("anyId", "anyQuestion", "anyCategory", 1, Arrays.asList("anyAnswers")));

        //when
        systemInMock.provideLines("y");
        quizController.decidingContinuation();
        String[] splitLog = this.systemOutRule.getLog().trim().split("\n");

        //then
        assertThat(splitLog[0].trim()).isEqualTo("Congrats! You answer was correct! Do you want to continue? [y/n]");
        assertThat(splitLog[1]).isEqualTo("(anyCategory, 1) anyQuestion");
    }

    @Test
    public void should_end_game_if_user_answers_correctly_and_wants_to_end_the_game() throws IOException {
        //given
        QuestionSupplier mockQuestion = mock(QuestionSupplier.class);
        AnswerSupplier mockAnswer = mock(AnswerSupplier.class);
        CommandLineScanner mockScanner = mock(CommandLineScanner.class);
        QuizController quizController =
                new QuizController(mockAnswer, mockQuestion, new CommandLineScanner(), "correct");
        given(mockScanner.getPlayerDecisionWithValidation(Arrays.asList("anyValidInput"), "anyMessage")).willReturn(
                "anyPlayerDecision");

        //when
        systemInMock.provideLines("n");
        quizController.decidingContinuation();
        String[] splitLog = this.systemOutRule.getLog().trim().split("\n");

        //then
        assertThat(splitLog[0].trim()).isEqualTo("Congrats! You answer was correct! Do you want to continue? [y/n]");
        assertThat(splitLog[1]).isEqualTo("Good game! THE END");
    }

    @Test
    public void should_end_game_if_user_answers_wrongly_and_wants_to_end_the_game() throws IOException {
        //given
        QuestionSupplier mockQuestion = mock(QuestionSupplier.class);
        AnswerSupplier mockAnswer = mock(AnswerSupplier.class);
        CommandLineScanner mockScanner = mock(CommandLineScanner.class);
        QuizController quizController =
                new QuizController(mockAnswer, mockQuestion, new CommandLineScanner(), "wrong");
        given(mockScanner.getPlayerDecisionWithValidation(Arrays.asList("anyValidInput"), "anyMessage")).willReturn(
                "anyPlayerDecision");

        //when
        systemInMock.provideLines("n");
        quizController.decidingContinuation();
        String[] splitLog = this.systemOutRule.getLog().trim().split("\n");

        //then
        assertThat(splitLog[0].trim()).isEqualTo("Wrong answer! Do you want to continue? [y/n]");
        assertThat(splitLog[1].trim()).isEqualTo("Good game! THE END");
    }

    @Test
    public void should_display_answer_again_if_user_answers_wrongly_and_wants_to_answer_again() throws IOException {
        //given
        QuestionSupplier mockQuestion = mock(QuestionSupplier.class);
        AnswerSupplier mockAnswer = mock(AnswerSupplier.class);
        CommandLineScanner mockScanner = mock(CommandLineScanner.class);
        QuizController quizController =
                new QuizController(mockAnswer, mockQuestion, new CommandLineScanner(), "wrong");
        given(mockScanner.getPlayerDecisionWithValidation(Arrays.asList("anyValidInput"), "anyMessage")).willReturn(
                "anyPlayerDecision");
        given(mockAnswer.getAndSendUserAnswer("anyId")).willReturn("anyAnswer");

        //when
        systemInMock.provideLines("y");
        quizController.decidingContinuation();
        String[] splitLog = this.systemOutRule.getLog().trim().split("\n");

        //then
        assertThat(splitLog[0].trim()).isEqualTo("Wrong answer! Do you want to continue? [y/n]");
        assertThat(splitLog[1].trim()).isEqualTo("Answer again: ");
    }
}