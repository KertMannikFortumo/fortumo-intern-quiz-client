package ee.kertmannik.quiz.client;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class GreetingPlayerTest {

    private GreetingPlayer greetingPlayer;

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Rule
    public final TextFromStandardInputStream systemInMock
            = emptyStandardInputStream();

    @Before
    public void initialize() throws Exception {
        this.greetingPlayer = new GreetingPlayer();
    }

    @Test
    public void should_return_input_when_asking_for_player_name() {
        //given
        String userName = "anyName";

        //when
        this.systemInMock.provideLines(userName);

        //then
        assertThat(this.greetingPlayer.getPlayerName()).isEqualTo("anyName");
    }

    @Test
    public void should_greet_player() {
        //given
        String userName = "anyPlayer";

        //when
        String result = greetingPlayer.greetPlayer(userName);
        //then
        assertThat(result).isEqualTo("Hello, anyPlayer");
    }
}
