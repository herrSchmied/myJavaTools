package someMath;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import someMath.exceptions.MathException;

public class Partitions
{	

	public Set<List<Integer>> summandsBiggerSet(int minSize, int nrOfSummands, int sum) throws MathException
	{
		
		
		Set<List<Integer>> output = new HashSet<>();
		
		if(minSize<=0||nrOfSummands<=0||sum<=0)throw new MathException("Hi");
		
		if(minSize*nrOfSummands>sum)return output;
		
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
		
		for(int size=minSize;size<=sum-nrOfSummands+1;size++)
		{
			
			if(size<=0||nrOfSummands-1<=0||sum-size<=0)return output;
			
			Set<List<Integer>> set =summandsBiggerSet(size, nrOfSummands-1, sum-size);
			
			for(List<Integer> listRight: set)
			{
				List<Integer> list = new ArrayList<>();
				list.add(size);
				list.addAll(listRight);
				output.add(list);
			}
		}

		return output;
	}

	public Set<List<Integer>> summandsSmallerSet(int maxSize, int nrOfSummands, int sum) throws MathException
	{
		
		
		Set<List<Integer>> output = partitionsOfNAsLists(sum);
		
		output = maxSizeFilter(maxSize, output);
		output = nrOfSummandsFilter(nrOfSummands, output);

		return output;
	}

	public Set<List<Integer>> partitionsOfNAsLists(int sum) throws MathException
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
	
	public static int sumOfListEntries(List<Integer> list)
	{
		int sum = 0;
		for(int n=0;n<list.size();n++)
		{
			sum = sum + list.get(n);
		}
		return sum;
	}
	
	public static Set<List<Integer>> maxSizeFilter(int maxSize, Set<List<Integer>> input)
	{
		
		Set<List<Integer>> output = new HashSet<>();
		
		for(List<Integer> list: input)
		{
			int cnt = 0;
			for(Integer i: list)
			{
				if(i>maxSize)break;
				else cnt++;
			}
			
			if(cnt==list.size())output.add(list);
		}
		
		return output;
	}
	
	
	public static Set<List<Integer>> nrOfSummandsFilter(int nrOfSummands, Set<List<Integer>> input)
	{
		
		Set<List<Integer>> output = new HashSet<>();
		
		for(List<Integer> list: input)
		{
			if(list.size()==nrOfSummands)output.add(list);
		}
		
		return output;
	}	
}
