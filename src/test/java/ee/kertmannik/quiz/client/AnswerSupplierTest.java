package ee.kertmannik.quiz.client;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class AnswerSupplierTest {

    CommandLineScanner mockScanner;
    AnswerSupplier answerSupplier;
    AnswerRequest mockRequest;

    @Rule
    public final TextFromStandardInputStream systemInMock
            = emptyStandardInputStream();

    @Before
    public void initialize() {
        this.mockRequest = mock(AnswerRequest.class);
        this.mockScanner = mock(CommandLineScanner.class);
        this.answerSupplier = new AnswerSupplier();
    }

    @Test
    public void should_return() throws IOException {
        //given
        given(this.mockScanner.getUserInputWithMessage("")).willReturn("anyAnswer");
        given(this.mockRequest.postAnswerToServer("anyAnswer", "anyPlayer")).willReturn("anyServerAnswer");

        //when
        String result = this.answerSupplier.getAndSendUserAnswer("anyId");

        //then
        assertThat(result).isEqualTo("anyServerAnswer");
    }
}