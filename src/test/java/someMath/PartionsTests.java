package someMath;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import consoleTools.TerminalXDisplay;
import someMath.exceptions.CollectionException;
import someMath.exceptions.MathException;

public class PartionsTests
{

	@Test
	public void summandsBiggerTest() throws CollectionException, MathException
	{
		
		int minSize = 1;
		int nrOfSummands = 8;
		int sum = 10;
		Set<List<Integer>> set =Partitions.summandsBiggerSet(minSize, nrOfSummands, sum);
		System.out.println("Min Size: " + minSize);
		System.out.println("Nr of Summands: " + nrOfSummands);
		System.out.println("Sum: " + sum + "\n");
		System.out.println(set + "\n");
		
		for(List<Integer> list: set)
		{
			assert(list.size()==nrOfSummands);
			assert(Partitions.sumOfListEntries(list)==sum);
		}
		
		sum = 6;
		set = Partitions.partionsOfNAsLists(sum);
		int p = set.size();
		
		System.out.println("Min Size: " + minSize);
		System.out.println("Nr of Summand: 1 to " + sum);
		System.out.println("Sum: " + sum);
		System.out.println("Partitions: " + p + "\n");
		System.out.println(set);
		
		for(List<Integer> list: set)
		{
			
			assert(1<=list.size()&&sum>=list.size());
			assert(Partitions.sumOfListEntries(list)==sum);
		}


	}
}
