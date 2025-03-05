package someMath;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import consoleTools.BashSigns;
import consoleTools.TerminalXDisplay;
import someMath.exceptions.CollectionException;
import someMath.exceptions.MathException;

public class PartionsTests
{

	@Test
	public void partitionsTest2() throws MathException
	{
		
		System.out.println(BashSigns.bBCPX + "Partitions of N Test." + BashSigns.bBCSX);
		System.out.println(BashSigns.bBCPX + "Using SummandsSmallerSet Method" + BashSigns.bBCSX);

		Partitions partitions = new Partitions();
		int sum = 6;

		Set<List<Integer>> set = partitions.partitionsOfN(sum);
		int nrPartitions = set.size();
		assert(nrPartitions==11);//only if sum is 6!!!!

		printStats(false, 1, null, sum, nrPartitions, set);


		for(List<Integer> list: set)
		{
			
			assert(1<=list.size()&&sum>=list.size());
			assert(Partitions.sumOfListEntries(list)==sum);
		}
	}

	@Test
	public void partitionsTest() throws MathException
	{
		
		System.out.println(BashSigns.bBCPX + "Partitions of N Test." + BashSigns.bBCSX);
		System.out.println(BashSigns.bBCPX + "Using summandsBiggerSet-Method." + BashSigns.bBCSX);
		Partitions partitions = new Partitions();
		int sum = 6;

		Set<List<Integer>> set = partitions.partitionsOfNAsLists(sum);
		int nrPartitions = set.size();
		assert(nrPartitions==11);//only if sum is 6!!!!

		printStats(true, 1, null, sum, nrPartitions, set);

		
		for(List<Integer> list: set)
		{
			
			assert(1<=list.size()&&sum>=list.size());
			assert(Partitions.sumOfListEntries(list)==sum);
		}
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
		printStats(false, minSize,nrOfSummands, sum, nrPartitions, set);
		
		for(List<Integer> list: set)
		{
			assert(list.size()==nrOfSummands);
			assert(Partitions.sumOfListEntries(list)==sum);
		}
		
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
		
		printStats(true, maxSize,nrOfSummands, sum, nrPartitions, set);
		
		for(List<Integer> list: set)
		{
			//assert(list.size()==nrOfSummands);
			assert(Partitions.sumOfListEntries(list)==sum);
		}
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
}
