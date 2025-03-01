package someMath;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import someMath.exceptions.MathException;

public class Partitions
{

	private int callCounter = 0;
	private int invalideCalls = 0;
	private int emptyCalls = 0;
	

	public Set<List<Integer>> summandsBiggerSet(int minSizeOfSummand, int nrOfSummands, int sum) throws MathException
	{
		
		callCounter++;
		
		Set<List<Integer>> output = new HashSet<>();
		
		PartitionsSetsFrame frame = new PartitionsSetsFrame(minSizeOfSummand, nrOfSummands, sum);
		
		try
		{
			boolean valide = PartitionsSetsFrame.validator(frame);
			
			if(!valide)
			{
				emptyCalls++;
				return output;
			}
		}
		catch(MathException mExce)
		{
			invalideCalls++;
			return output;
		}
		
		if(sum==1)
		{
			List<Integer> list = new ArrayList<>();
			list.add(1);
			output.add(list);
			return output;

		}

		if(nrOfSummands==1)
		{
			List<Integer> list = new ArrayList<>();
			list.add(sum);
			output.add(list);
			return output;
		}
		
		for(int i=minSizeOfSummand;i<=sum-nrOfSummands+1;i++)
		{
			
			PartitionsSetsFrame frame2 = new PartitionsSetsFrame(i, nrOfSummands-1, sum-i);
			if(!PartitionsSetsFrame.validator(frame2))continue;//Gets the nr of useless calls down.
			
			Set<List<Integer>> set =summandsBiggerSet(i, nrOfSummands-1, sum-i);
			
			for(List<Integer> listRight: set)
			{
				List<Integer> list = new ArrayList<>();
				list.add(i);
				list.addAll(listRight);
				output.add(list);
			}
		}

		return output;
	}

	public Set<List<Integer>> partionsOfNAsLists(int sum) throws MathException
	{
		Set<List<Integer>> output = new HashSet<>();
		
		if(sum<1)return output;
		
		for(int e=1;e<sum;e++)
		{
			output.addAll(summandsBiggerSet(1, e, sum));
		}
		
		List<Integer> bunchOfOnes = new ArrayList<>();
		for(int n=0;n<sum;n++)bunchOfOnes.add(1);
		output.add(bunchOfOnes);
		
		return output;

	}
		
	public void resetCalls()
	{
		callCounter = 0;
		emptyCalls = 0;
		invalideCalls = 0;
	}
	
	public int getEmptyCalls()
	{
		return emptyCalls;
	}

	public int getCallCounter()
	{
		return callCounter;
	}

	public int getInValideCalls()
	{
		return invalideCalls;
	}

	public static int sumOfListEntries(List<Integer> list)
	{
		int sum = 0;
		for(int n=0;n<list.size();n++)
		{
			sum = sum + list.get(n);
		}
		return sum;
	}
}
