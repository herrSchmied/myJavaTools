package someMath;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import someMath.exceptions.MathException;

public class Partitions
{	

	public static Set<List<Integer>> summandsBiggerSet(int minSizeElement, int nrOfSummands, int sum) throws MathException
	{
		
		
		Set<List<Integer>> output = new HashSet<>();
		
		if(minSizeElement<=0||nrOfSummands<=0||sum<=0)return output;
		
		if(minSizeElement*nrOfSummands>sum)return output;
		
		if(nrOfSummands==1)
		{
			List<Integer> list = new ArrayList<>();
			list.add(sum);
			output.add(list);
			return output;
		}
		
		for(int mse=minSizeElement;mse<=sum;mse++)
		{
			
			Set<List<Integer>> set =summandsBiggerSet(mse, nrOfSummands-1, sum-mse);
			
			for(List<Integer> listRight: set)
			{
				List<Integer> list = new ArrayList<>();
				list.add(mse);
				list.addAll(listRight);
				output.add(list);
			}
		}

		return output;
	}

	public static Set<List<Integer>> summandsSmallerSet(int maxSize, int nrOfSummands, int sum) throws MathException
	{
		
		
		Set<List<Integer>> output = partitionsOfNAsLists(sum);
		
		output = sizeFilter(true, maxSize, output);
		output = nrOfSummandsFilter(nrOfSummands, output);

		return output;
	}

	public static Set<List<Integer>> partitionsOfNAsLists(int sum) throws MathException
	{
		Set<List<Integer>> output = new HashSet<>();
		
		if(sum<1)return output;
		
		int minSizeElement = 1;
		for(int nrOfSummands=1;nrOfSummands<=sum;nrOfSummands++)
		{
			output.addAll(summandsBiggerSet(minSizeElement, nrOfSummands, sum));
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
	
	public static Set<List<Integer>> sizeFilter(boolean maxOrMin, int maxSize, Set<List<Integer>> input)
	{
		
		Set<List<Integer>> output = new HashSet<>();
		
		for(List<Integer> list: input)
		{
			boolean hasOneTooLarge = false;
			boolean hasOneTooSmall = false;
			for(Integer i: list)
			{
				if((i>maxSize)&&(maxOrMin))
				{
					hasOneTooLarge = true;
					break;
				}

				if((i<maxSize)&&(!maxOrMin))
				{
					hasOneTooSmall = true;
					break;
				}
			}
			
			if(!(hasOneTooLarge||hasOneTooSmall))output.add(list);
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