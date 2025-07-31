package someMathTest;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import consoleTools.BashSigns;
import consoleTools.TerminalXDisplay;
import someMath.Partitions;
import someMath.exceptions.CollectionException;
import someMath.exceptions.MathException;

public class PartionsTests
{

	final boolean goForMax = true;

	@Test
	public void partitionsTest() throws MathException
	{

		System.out.println(BashSigns.bBCPX + "Partitions of 6, 7 and 10 Test." + BashSigns.bBCSX);

		runSumPartitionTest(6, 11);
		runSumPartitionTest(7, 15);
		runSumPartitionTest(10, 42);
	}
	

	@Test
	public void summandsBiggerTest() throws CollectionException, MathException
	{

		System.out.println(BashSigns.bBCPX + "SummandsBiggerSet-Method Test." + BashSigns.bBCSX);

		int minSize = 3;
		int nrOfSummands = 5;
		int sum = 30;
		Set<List<Integer>> set =Partitions.summandsBiggerSet(minSize, nrOfSummands, sum);
		int nrPartitions = set.size();
		printStats(!goForMax, minSize,nrOfSummands, sum, nrPartitions, set);

		for(List<Integer> list: set)
		{
			assert(list.size()==nrOfSummands);
			assert(Partitions.sumOfListEntries(list)==sum);
		}

		assert(Partitions.sizeFilter(false, minSize, set).size()==set.size());
		assert(Partitions.sizeFilter(true, minSize-1, set).isEmpty());
	}

	public void printStats(boolean maxOrMin, Integer size, Integer nrOfSummands, Integer sum, Integer partitions, 
			Set<List<Integer>> set)
	{

		
		if(!maxOrMin)System.out.println("min Size: " + size);
		else System.out.println("max Size: " + size);

		if(nrOfSummands==null)System.out.println("Nr of Summands: any");
		else System.out.println("Nr of Summands: " + nrOfSummands);

		System.out.println("Partitions: " + partitions);
		System.out.println("Sum: " + sum);
		System.out.println(set + "\n");
	}
	
	public void runSumPartitionTest(int sum, int expected) throws MathException
	{
		
		Set<List<Integer>> set = Partitions.partitionsOfNAsLists(sum);

		int nrPartitions = set.size();
		assert(nrPartitions==expected);
		
		printStats(!goForMax, 1, null, sum, nrPartitions, set);

		
		for(List<Integer> list: set)
		{
			
			assert(1<=list.size()&&sum>=list.size());
			assert(Partitions.sumOfListEntries(list)==sum);
		}
		
		assert(Partitions.sizeFilter(true, 1, set).size()==1);
	}
}
