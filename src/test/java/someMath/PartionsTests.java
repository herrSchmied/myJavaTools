package someMath;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import consoleTools.BashSigns;
import consoleTools.TerminalXDisplay;
import someMath.exceptions.CollectionException;
import someMath.exceptions.MathException;

public class PartionsTests
{

	Partitions partitions;
	final boolean goForMax = true;

	@BeforeEach
	public void setup()
	{
		partitions = new Partitions();
	}

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

		Partitions partitions = new Partitions();
		int minSize = 3;
		int nrOfSummands = 5;
		int sum = 30;
		Set<List<Integer>> set =partitions.summandsBiggerSet(minSize, nrOfSummands, sum);
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
	
	@Test
	public void summandsSmallerTest() throws CollectionException, MathException
	{

		System.out.println(BashSigns.bBCPX + "SummandsSmallerSet Method Test." + BashSigns.bBCSX);

		Partitions partitions = new Partitions();
		int maxSize = 4;
		int nrOfSummands = 4;
		int sum = 10;
		Set<List<Integer>> set =partitions.summandsSmallerSet(maxSize, nrOfSummands, sum);
		int nrPartitions = set.size();
		
		printStats(goForMax, maxSize,nrOfSummands, sum, nrPartitions, set);
		
		for(List<Integer> list: set)
			assert(Partitions.sumOfListEntries(list)==sum);
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
		
		Set<List<Integer>> set = partitions.partitionsOfNAsLists(sum);

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
