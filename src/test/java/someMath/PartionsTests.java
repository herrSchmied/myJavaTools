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
		
		Partitions partitions = new Partitions();
		int minSize = 3;
		int nrOfSummands = 5;
		int sum = 30;
		Set<List<Integer>> set =partitions.summandsBiggerSet(minSize, nrOfSummands, sum);
		int nrPartitions = set.size();
		int callCount = partitions.getCallCounter();
		int invalideCalls = partitions.getInValideCalls();
		int emptyCalls = partitions.getEmptyCalls();
		
		printStats(minSize,nrOfSummands, sum, nrPartitions, callCount, emptyCalls, invalideCalls, set);
		
		for(List<Integer> list: set)
		{
			assert(list.size()==nrOfSummands);
			assert(Partitions.sumOfListEntries(list)==sum);
		}
		
		sum = 6;
		partitions.resetCalls();
		set = partitions.partionsOfNAsLists(sum);
		nrPartitions = set.size();
		assert(nrPartitions==11);//only if sum is 6!!!!
		callCount = partitions.getCallCounter();
		emptyCalls = partitions.getEmptyCalls();
		invalideCalls = partitions.getInValideCalls();
		
		printStats(1, null, sum, nrPartitions, callCount, 
				emptyCalls, invalideCalls, set);

		
		for(List<Integer> list: set)
		{
			
			assert(1<=list.size()&&sum>=list.size());
			assert(Partitions.sumOfListEntries(list)==sum);
		}
	}
	
	public void printStats(Integer minSize, Integer nrOfSummands, Integer sum, Integer partitions, 
			int callCount, int emptyCalls, int invalideCalls, Set<List<Integer>> set)
	{

		
		System.out.println("min Size: " + minSize);
		
		if(nrOfSummands==null)System.out.println("Nr of Summands: any");
		else System.out.println("Nr of Summands: " + nrOfSummands);

		System.out.println("Partitions: " + partitions);
		System.out.println("Sum: " + sum);
		System.out.println("Calls: " + callCount);
		System.out.println("Empty Calls: " + emptyCalls);
		System.out.println("Invalide Calls: " + invalideCalls);
		System.out.println(set + "\n");
		

	}
}
