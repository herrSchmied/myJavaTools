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
	public void partitionsTest2() throws MathException
	{
		
		Partitions partitions = new Partitions();
		int sum = 6;

		Set<List<Integer>> set = partitions.summandsSmallerSet(sum, sum, sum);
		int nrPartitions = set.size();
		//assert(nrPartitions==11);//only if sum is 6!!!!

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
		
		Partitions partitions = new Partitions();
		int sum = 6;

		Set<List<Integer>> set = partitions.partitionsOfN(sum);
		int nrPartitions = set.size();
		//assert(nrPartitions==11);//only if sum is 6!!!!

		printStats(false, 1, null, sum, nrPartitions, set);

		
		for(List<Integer> list: set)
		{
			
			assert(1<=list.size()&&sum>=list.size());
			assert(Partitions.sumOfListEntries(list)==sum);
		}
	}

	@Test
	public void summandsBiggerTest() throws CollectionException, MathException
	{
		
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
		
		
		Partitions partitions = new Partitions();
		int maxSize = 3;
		int nrOfSummands = 5;
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
