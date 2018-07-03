package ee.kertmannik.quiz.client;

import ee.kertmannik.quiz.client.model.Question;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class QuizControllerTest {

    private QuestionSupplier mockQuestion;
    private AnswerSupplier mockAnswer;
    private Question question;

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Rule
    public final TextFromStandardInputStream systemInMock
            = emptyStandardInputStream();

    @Before
    public void initialize() {
        this.mockQuestion = mock(QuestionSupplier.class);
        this.mockAnswer = mock(AnswerSupplier.class);
        this.question = new Question("anyId", "AnyQuestion", "anyCategory", 999,
                Arrays.asList("anyAnswers"));
    }

    @Test
    public void should_return_question_object() throws IOException {
        //given
        given(mockQuestion.requestQuestion()).willReturn(new Question("anyId", "AnyQuestion", "anyCategory", 999,
                Arrays.asList("anyAnswers")));
        QuizController quizController = new QuizController(mockAnswer, mockQuestion, "anyAnswer", question);

        //when
        quizController.getQuestion();

        //then
        assertThat(this.systemOutRule.getLog().trim()).isEqualTo("(anyCategory, 999) AnyQuestion");
    }

    @Test
    public void should_continue_game_if_user_answer_is_correct_and_wants_to_start_the_game_again() throws IOException {
        //given
        QuestionSupplier mockQuestion = mock(QuestionSupplier.class);
        AnswerSupplier mockAnswer = mock(AnswerSupplier.class);
        QuizController quizController =
                new QuizController(mockAnswer, mockQuestion, "correct", question);
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
    public void should_end_game_if_user_answer_is_correct_and_wants_to_end_the_game() throws IOException {
        //given
        QuestionSupplier mockQuestion = mock(QuestionSupplier.class);
        AnswerSupplier mockAnswer = mock(AnswerSupplier.class);
        QuizController quizController =
                new QuizController(mockAnswer, mockQuestion, "correct", question);

        //when
        systemInMock.provideLines("n");
        quizController.decidingContinuation();
        String[] splitLog = this.systemOutRule.getLog().trim().split("\n");

        //then
        assertThat(splitLog[0].trim()).isEqualTo("Congrats! You answer was correct! Do you want to continue? [y/n]");
        assertThat(splitLog[1]).isEqualTo("Good game! THE END");
    }

    @Test
    public void should_end_game_if_user_answer_is_wrong_and_wants_to_end_the_game() throws IOException {
        //given
        QuestionSupplier mockQuestion = mock(QuestionSupplier.class);
        AnswerSupplier mockAnswer = mock(AnswerSupplier.class);
        QuizController quizController =
                new QuizController(mockAnswer, mockQuestion, "wrong", question);

        //when
        systemInMock.provideLines("n");
        quizController.decidingContinuation();
        String[] splitLog = this.systemOutRule.getLog().trim().split("\n");

        //then
        assertThat(splitLog[0].trim()).isEqualTo("Wrong answer! Do you want to continue? [y/n]");
        assertThat(splitLog[1].trim()).isEqualTo("Good game! THE END");
    }

    @Test
    public void should_display_answer_again_if_user_answer_is_wrong_and_wants_to_answer_again() throws IOException {
        //given
        QuestionSupplier mockQuestion = mock(QuestionSupplier.class);
        AnswerSupplier mockAnswer = mock(AnswerSupplier.class);
        QuizController quizController =
                new QuizController(mockAnswer, mockQuestion, "wrong", question);

        //when
        systemInMock.provideLines("y");
        quizController.decidingContinuation();
        String[] splitLog = this.systemOutRule.getLog().trim().split("\n");

        //then
        assertThat(splitLog[0].trim()).isEqualTo("Wrong answer! Do you want to continue? [y/n]");
        assertThat(splitLog[1]).isEqualTo("Answer again:");
    }

    @Test
    public void should_test_entire_quiz_client_game_flow_if_player_answer_is_wrong_and_doesnt_want_to_continue_integration_test()
            throws IOException
    {
        //given

        //when
        PlayerGreeting PlayerGreeting = new PlayerGreeting();

        systemInMock.provideLines("Kert", "wrongAnswer", "n");
        String username = PlayerGreeting.getPlayerName();
        PlayerGreeting.greetPlayer(username);

        try {
            QuizController quizController = new QuizController(username);
            quizController.getQuestion();
            quizController.postAnswer();
            quizController.decidingContinuation();
        } catch (QuizException exception) {
            System.out.println("\n" + exception.getMessage());
        }

        //then
        String[] splitLog = this.systemOutRule.getLog().trim().split("\n");
        assertThat(splitLog[0].trim()).isEqualTo("Enter your name:");
        assertThat(splitLog[1].trim()).isEqualTo("Hello, Kert.");
        assertThat(splitLog[3].trim()).isEqualTo("Wrong answer! Do you want to continue? [y/n]");
        assertThat(splitLog[4].trim()).isEqualTo("Good game! THE END");
    }

    @Test
    public void should_test_entire_quiz_client_game_flow_if_player_answer_is_correct_and_doesnt_want_to_continue_integration_test()
            throws IOException
    {
        //given
        Map<String, String> questionsAndAnswers = new HashMap<>();
        questionsAndAnswers.put("How old is Estonia?", "100");
        questionsAndAnswers.put("Kes on tubli poiss?", "Lars");

        //when
        PlayerGreeting PlayerGreeting = new PlayerGreeting();

        systemInMock.provideLines("Kert");
        String username = PlayerGreeting.getPlayerName();
        PlayerGreeting.greetPlayer(username);

        try {
            QuizController quizController = new QuizController(username);
            quizController.getQuestion();

            String[] splitLog = systemOutRule.getLog().split("\\)");
            systemInMock.provideLines(questionsAndAnswers.get(splitLog[1].trim()));
            quizController.postAnswer();

            systemInMock.provideLines("n");
            quizController.decidingContinuation();
        } catch (QuizException exception) {
            System.out.println("\n" + exception.getMessage());
        }

        //then
        String[] splitLog = this.systemOutRule.getLog().trim().split("\n");
        assertThat(splitLog[0].trim()).isEqualTo("Enter your name:");
        assertThat(splitLog[1].trim()).isEqualTo("Hello, Kert.");
        assertThat(splitLog[3].trim()).isEqualTo("Congrats! You answer was correct! Do you want to continue? [y/n]");
        assertThat(splitLog[4].trim()).isEqualTo("Good game! THE END");
    }
}