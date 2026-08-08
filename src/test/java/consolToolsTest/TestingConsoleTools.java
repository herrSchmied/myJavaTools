package consolToolsTest;



import java.awt.Color;
import java.awt.Point;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.junit.jupiter.api.Test;


import consoleTools.TerminalTableDisplay;


import javafx.util.Pair;


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
		Set<Pair<Color, Point>> highLights = new HashSet<>();
		Pair<Color, Point> pair = new Pair<>(Color.RED, new Point(1, 2));
		highLights.add(pair);
		TerminalTableDisplay ttd = new TerminalTableDisplay(headers, cellValues, '|', 10, highLights);
		System.out.println(ttd);
	}

//	@Test
//	public void InputStreamSessionTest()
//	{
//		
//		LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
//		
//		String data = InputStreamSession.translateTimeToAnswerString(yesterday);
//		
//		ByteArrayInputStream bais = new ByteArrayInputStream(data.getBytes());
//
//		InputStreamSession iss = new InputStreamSession(bais);
//		
//		InputArgumentException iae = assertThrows(InputArgumentException.class, ()->
//		{
//			iss.getDateTimeInOneLine("DateTime please.", LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));
//		});
//	}
}
