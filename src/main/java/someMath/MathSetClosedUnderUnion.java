package someMath;



import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import someMath.exceptions.CollectionException;

import static CollectionTools.CollectionManipulation.*;
import static consoleTools.BashSigns.*;



public class MathSetClosedUnderUnion
{

	public static <T> Set<Set<T>> createFamilie(Set<Set<T>> origin)
	{
		
		if(origin==null)throw new IllegalArgumentException("Set is null.");

		Set<Set<T>> output = new HashSet<>(origin);
		
		for(Set<T> e1: origin)
		{
			
			if(e1==null)throw new IllegalArgumentException("There is a null in the Set.");
			for(Set<T> e2: origin)
			{

				if(e2==null)throw new IllegalArgumentException("There is a null in the Set.");
				if(e1.equals(e2))continue;
				else
				{
					Set<T> a = new HashSet<>();
					a.addAll(e1);
					a.addAll(e2);
					output.add(a);

					if(famTest(output))return output;
				}
			}
		}

		if(!famTest(output))return createFamilie(output);

		return output;
	}

	public static <T> boolean famTest(Set<Set<T>> toBeTested)
	{
		
		if(toBeTested==null)return false;
		if(toBeTested.isEmpty())return true;
		
		for(Set<T> member: toBeTested)
		{
			
			if(member==null)throw new IllegalArgumentException("There is a null in the Set.");
			for(Set<T> anotherMember: toBeTested)
			{

				if(anotherMember==null)throw new IllegalArgumentException("There is a null in the Set.");

				Set<T> union = new HashSet<>();
				union.addAll(member);
				union.addAll(anotherMember);
				
				if(!toBeTested.contains(union))return false;
			}
		}
		
		return true;
	}

	/*
	public static <T> int[] typeOfSetOfSets(Set<Set<T>> origin)throws CollectionException
	{
		
		int size = origin.size();
		int [] type = new int[size];
		
		System.out.println("Origin: \n" + collectionToString(origin));

		for(int n=1;n<size+1;n++)
		{
			type[n-1]=0;
		}
		
		for(int n=1;n<size+1;n++)
		{
			Set<Set<T>> subSets = allSubSetsOfSizeN(origin, n);
			printlnBoldAndGreen("All SubSets of Size " + n);
			System.out.println(collectionToString(subSets)+"\n\n");
			ArrayList<Set<T>> listOfSubSets = new ArrayList<>(subSets);
			
			for(Set<T> cutOut: listOfSubSets)
			{

				printlnBoldAndGreen("New CutOut: " + collectionToString(cutOut));
			
				ArrayList<Set<T>> l2 = new ArrayList<>();
				l2.addAll(listOfSubSets);

				l2.remove(cutOut);
				assert(l2.size()+1==listOfSubSets.size());

				Set<T> intersection = intersectHoleSetOfSets(cutOut);
				System.out.println("CutOut Intersection: " + collectionToString(intersection));
			
				for(T t: intersection)
				{
				
					System.out.println("Testing: " + t);

					boolean isExclusive = true;
					for(Set<Set<T>> subSet: l2)
					{

						System.out.println("Test subSet: " + collectionToString(subSet));
						System.out.println("Intersection of the above: " + collectionToString(intersectHoleSetOfSets(subSet)));

						if(multipleContain(t, subSet))
						{
							isExclusive = false;
							System.out.println(t+" is not exclusive.");
							break;
						}
						else
						{
							System.out.println(t + " seems exclusive so far;");
						}
					
					}
					if(isExclusive)
					{
						type[n-1]++;
						break;
					}
				}
			}
		}
		
		return type;
	}
	*/
	
	public static <T> Set<Set<T>> allSubSetsOfSizeN(Set<Set<T>> origin, int n) {
		
		Set<Set<T>> output = new HashSet<>();
		
		for(Set<T> set: origin)
		{
			if(set.size()==n)output.add(set);
		}
		
		return output;
	}

	public static <C> Set<Set<C>> traverseCluster(Set<C> set, Set<Set<C>> origin) throws CollectionException
	{
		
		Set<Set<C>> output = new HashSet<>();

		if(set==null||origin==null)throw new IllegalArgumentException("Null Arguments.");
		if(origin.isEmpty())return output;
		if(!origin.contains(set))throw new IllegalArgumentException("Arguments are not valide.");

		for(C c: set)
		{
			Set<Set<C>> cluster = findContainingSets(c, origin);
			output.addAll(cluster);
			
			Set<Set<C>> copy = new HashSet<>(origin);
			copy.remove(set);

			for(Set<C> set2: cluster)
			{
				if(!set.equals(set2))output.addAll(traverseCluster(set2, copy));
			}
		}

		return output;
	}

	public static <C> Set<Set<Set<C>>> findClusters(Set<Set<C>> origin) throws CollectionException
	{

		Set<Set<Set<C>>> output = new HashSet<>();
		if(origin.isEmpty())return output;


		Set<C> set = catchRandomElementOfSet(origin);
		Set<Set<C>> cluster = traverseCluster(set, origin);
		output.add(cluster);
		
		Set<Set<C>> copy = new HashSet<>(origin);
		copy.removeAll(cluster);
		
		output.addAll(findClusters(copy));

		return output;
	}
	
	public static <C> boolean isAbundand(C c, Set<Set<C>> origin)
	{
		
		int size = origin.size();
		int count = 0;
		
		for(Set<C> set: origin)
		{
			if(set.contains(c))count++;
		}
		
		double rate = (double)count/(double)size;
		
		return (rate>=0.5);
	}

	public static <C> Set<Set<C>> findContainingSets(C c, Set<Set<C>> origin)
	{

		Set<Set<C>> output = new HashSet<>();
		
		for(Set<C> set: origin)
		{
			if(set.contains(c))output.add(set);
		}

		return output;
	}
	
	public static <T, G extends Collection<H>, H extends Collection<T>> boolean multipleContain(T t, G container)
	{
	
		for(H coll: container)
		{
			if(!coll.contains(t))return false;
		}
	
		return true;
	}
	
	public static <T> Set<T> intersectHoleSetOfSets(Set<Set<T>> origin) throws CollectionException
	{
		
		Set<T> output = new HashSet<>();
		
		for(Set<T> set: origin)
		{
			for(T t: set)
			{
				if(multipleContain(t, origin))output.add(t);
			}
		}

		return output;
	}
	
	public static void printlnBoldAndGreen(String s)
	{
		System.out.println(boldGBCPX+s+boldGBCSX);
	}

	public static <T>  Map<String, Set<T>> makeMapOfSets(Collection<Set<T>> origin) throws CollectionException
	{
		int size = origin.size();
		Set<Character> names = getSetOfFirstNLatinLettersUppercase(size);
		Map<String, Set<T>> output = new HashMap<>();

		for(Set<T> set: origin)
		{
			Character cName = catchRandomElementOfSet(names);
			String name = cName.toString();
			output.put(name, set);
			names.remove(cName);
		}
		
		
		return output;
	}
}