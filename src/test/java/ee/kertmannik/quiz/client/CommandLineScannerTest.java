package ee.kertmannik.quiz.client;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class CommandLineScannerTest {

    private CommandLineScanner commandLineScanner;

    @Rule
    public final TextFromStandardInputStream systemInMock
            = emptyStandardInputStream();

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Before
    public void initialize() {
        this.commandLineScanner = new CommandLineScanner();
    }

    @Test
    public void should_return_user_input_with_one_word() {
        //given
        String anyString = "anyString";

        //when
        this.systemInMock.provideLines(anyString);

        //then
        assertThat(commandLineScanner.getUserInputWithMessage("Do you like this quiz?")).isEqualTo(anyString);
    }

    @Test
    public void should_return_user_input_with_more_than_one_word() {
        //given
        String anyString = "anyString and anyString";

        //when
        this.systemInMock.provideLines(anyString);

        //then
        assertThat(commandLineScanner.getUserInputWithMessage("Do you like this quiz?")).isEqualTo(anyString);
    }

    @Test
    public void should_print_message_parameter(){
        //given
        String message = "anyMessage";

        //when
        this.systemInMock.provideLines("anyString");
        commandLineScanner.getUserInputWithMessage(message);
        String print = this.systemOutRule.getLog();

        //then
        assertThat(print).isEqualTo(message);
    }
}