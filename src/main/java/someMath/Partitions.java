package someMath;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import someMath.exceptions.MathException;

public class Partitions
{

	public static Set<List<Integer>> summandsBiggerSet(int minSizeOfSummand, int nrOfSummands, int sum) throws MathException
	{

		Set<List<Integer>> output = new HashSet<>();
		
		if(minSizeOfSummand*nrOfSummands>sum)return output;

		if(minSizeOfSummand<=0||nrOfSummands<=0||sum<=0)throw new MathException("At least one of the Arguments is Zero or Below.");
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
		
		for(int i=minSizeOfSummand;i<sum-nrOfSummands+1;i++)
		{
			
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
	
	
	public static Set<List<Integer>> partionsOfNAsLists(int sum) throws MathException
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
}
