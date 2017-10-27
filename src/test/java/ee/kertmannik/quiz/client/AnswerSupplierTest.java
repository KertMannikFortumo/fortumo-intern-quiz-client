package ee.kertmannik.quiz.client;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class AnswerSupplierTest {

    @Rule
    public final TextFromStandardInputStream systemInMock
            = emptyStandardInputStream();

    @Test
    public void should_return_validated_answer_from_server_when_POST_request_is_sent() throws Exception {
        //given
        AnswerRequest mockRequest = mock(AnswerRequest.class);
        CommandLineScanner mockScanner = mock(CommandLineScanner.class);
        AnswerSupplier answerSupplier = new AnswerSupplier("anyPlayer", mockRequest, mockScanner);
        given(mockScanner.getUserInputWithoutMessage()).willReturn("anyAnswer");
        given(mockRequest.postAnswerToServer("anyAnswer", "anyPlayer")).willReturn("anyServerAnswer");

        //when
        String result = answerSupplier.getAndSendUserAnswer("anyId");

        //then
        assertThat(result).isEqualTo("anyServerAnswer");
    }
}