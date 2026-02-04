package consolToolsTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import consoleTools.InputArgumentException;
import consoleTools.InputStreamSession;
import consoleTools.TerminalTableDisplay;
import someMath.exceptions.ConsoleToolsException;

public class TestingConsoleTools
{

	@Test
	public void TerminalTableDisplayTest() throws ConsoleToolsException
	{
		
		List<String> headers = Arrays.asList("One", "Two", "Three");
		List<String> rowOne = Arrays.asList("0,0***", "uzgrieu", "31415");
		List<String> rowTwo = Arrays.asList("BlubBlub", "BlubBlaBlaBla", "hhhhhh");
		List<String> rowThree = Arrays.asList("ogggi", "vuqui", "texMexSex");
		List<List<String>> cellValues = Arrays.asList(rowOne, rowTwo, rowThree);
		TerminalTableDisplay ttd = new TerminalTableDisplay(headers, cellValues, '|', 10);
		System.out.println(ttd);
	}

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
