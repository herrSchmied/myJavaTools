

import java.awt.Color;
import java.awt.Point;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;


import consoleTools.TerminalTableDisplay;
import javafx.util.Pair;



public class StringMatrixTest 
{

	@Test
	public void anotherTest()
	{
		
		List<String> headers = Arrays.asList("Name", "Status", "Time");
		
		List<String> K = Arrays.asList("Robocop", "Vintage", "80's");

		List<String> M = Arrays.asList("Terminator",  "mild Cult in times of Dispair there is air", "This decade.");

		List<String> L = Arrays.asList("Wall-E", "cuteButRude", "2010's");
		
		List<List<String>> values = Arrays.asList(K, M, L);
		
        TerminalTableDisplay show = new TerminalTableDisplay(headers, values, '|',12);
        System.out.println(show);

        Pair<Color, Point> pair =new Pair(Color.RED, new Point(1,1));
        Set<Pair<Color, Point>> set = new HashSet<>();
        set.add(pair);
        
        show = new TerminalTableDisplay(headers, values, '|',12, set);
        System.out.println(show);

        
        assert(true);
	}
}
