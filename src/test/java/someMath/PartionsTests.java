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
		
		int minSize = 3;
		int nrOfSummands = 3;
		int sum = 12;
		Set<List<Integer>> set =Partitions.summandsBiggerSet(minSize, nrOfSummands, sum);
		int partitions = set.size();
		
		printStats(minSize,nrOfSummands, sum, partitions, set);
		
		for(List<Integer> list: set)
		{
			assert(list.size()==nrOfSummands);
			assert(Partitions.sumOfListEntries(list)==sum);
		}
		
		sum = 6;
		set = Partitions.partionsOfNAsLists(sum);
		partitions = set.size();
		printStats(1, null, sum, partitions, set);

		
		for(List<Integer> list: set)
		{
			
			assert(1<=list.size()&&sum>=list.size());
			assert(Partitions.sumOfListEntries(list)==sum);
		}
	}
	
	public void printStats(Integer minSize, Integer nrOfSummands, Integer sum, Integer partitions, Set<List<Integer>> set)
	{

		
		System.out.println("min Size: " + minSize);
		
		if(nrOfSummands==null)System.out.println("Nr of Summands: any");
		else System.out.println("Nr of Summands: " + nrOfSummands);

		System.out.println("Partitions: " + partitions);
		System.out.println("Sum: " + sum + "\n");
		System.out.println(set + "\n");
		

	}
}
