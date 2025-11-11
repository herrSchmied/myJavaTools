package consolToolsTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import consoleTools.InputArgumentException;
import consoleTools.InputStreamSession;

public class TestingConsoleTools
{

	@Test
	public void InputStreamSessionTest()
	{
		
		LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
		
		String data = InputStreamSession.translateTimeToAnswerString(yesterday);
		
		ByteArrayInputStream bais = new ByteArrayInputStream(data.getBytes());

		InputStreamSession iss = new InputStreamSession(bais);
		
		InputArgumentException iae = assertThrows(InputArgumentException.class, ()->
		{
			iss.getDateTimeInOneLine("DateTime please.", LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));
		});
	}
}
