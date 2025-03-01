package someMath;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import someMath.exceptions.MathException;

public class Partitions
{	

	public Set<List<Integer>> summandsBiggerSet(int minSizeOfSummand, int nrOfSummands, int sum) throws MathException
	{
		
		
		Set<List<Integer>> output = new HashSet<>();
		
		if(minSizeOfSummand<=0||nrOfSummands<=0||sum<=0)throw new MathException("Hi");
		
		if(minSizeOfSummand*nrOfSummands>sum)return output;
		
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
			
			if(i<=0||nrOfSummands-1<=0||sum-i<=0)return output;
			
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

	public Set<List<Integer>> summandsSmallerSet(int maxSizeOfSummand, int nrOfSummands, int sum) throws MathException
	{
		
		
		Set<List<Integer>> output = new HashSet<>();
		
		
		if(maxSizeOfSummand<=0||nrOfSummands<=0||sum<=0)throw new MathException("Hi");
		
		if(maxSizeOfSummand*nrOfSummands<sum)return output;

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
		
		for(int i=maxSizeOfSummand;i>=1;i--)
		{
			
			if(i<=0||nrOfSummands-1<=0||sum-i<=0)return output;
			
			Set<List<Integer>> set = summandsSmallerSet(i, nrOfSummands-1, sum-i);
			if(set.isEmpty())continue;
			
			for(List<Integer> listRight: set)
			{
				
				if(listRight.size()!=nrOfSummands-1)continue;
				
				List<Integer> list = new ArrayList<>();
				list.add(i);
				list.addAll(listRight);
				output.add(list);
			}
		}

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
	
	public Set<List<Integer>> partitionsOfN(int sum) throws MathException
	{
		Set<List<Integer>> output = new HashSet<>();
		
		if(sum<1)return output;
		
		for(int e=1;e<sum;e++)
		{
			output.addAll(summandsSmallerSet(sum, e, sum));
		}
		
		
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
}
