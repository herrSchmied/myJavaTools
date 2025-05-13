package someMath;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import org.junit.jupiter.api.Test;

import consoleTools.TerminalXDisplay;


public class SequenzSearchTest
{

	@Test
	public void toolTest()
	{
		Point startPoint = new Point(0, 0);
		Point centerPoint = new Point(1, 1);
		Point leftCenter = new Point(0, 1);
		Point centerUp = new Point(1, 2);
		Point destPoint = new Point(2, 2);
		
		
		System.out.println(TerminalXDisplay.formatBashStringBoldAndGreen("\ntool Test!"));
		
		List<Point> snakeSequenz = new ArrayList<>(Arrays.asList(startPoint, centerPoint, leftCenter, centerUp, destPoint));
		List<Point> snakeSubSequenz = snakeSequenz.subList(1, 4);
		
		assert(SequenzInListSearch.containingThisSequenzAtPosition(snakeSubSequenz, snakeSequenz, 1));
		
		assert(SequenzInListSearch.containingThisSequenz(snakeSubSequenz, snakeSequenz));

		List<Point> reversedSeq = new ArrayList<>();
		
		for(int n=snakeSequenz.size()-1;n>=0;n--)reversedSeq.add(snakeSequenz.get(n));

		assert(!SequenzInListSearch.containingThisSequenz(reversedSeq, snakeSequenz));
		
		for(int n=0;n<snakeSequenz.size();n++)
		{
			
			for(int k=n;k<snakeSequenz.size();k++)
			{
				List<Point> subSequenz = snakeSequenz.subList(n, k);
				assert(SequenzInListSearch.containingThisSequenz(subSequenz, snakeSequenz));
			}
		}
	}
}
