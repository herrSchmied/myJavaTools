package CollectionTools;

import static CollectionTools.CollectionManipulation.arrayContainsValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.util.Pair;
import someMath.exceptions.CollectionException;
import someMath.exceptions.MathException;


public class CollectionManipulation 
{

	public static <O> Set<O> valuesNotShared(O[] origin, O[] other)
	{
		
		Set<O> notSharedValues = new HashSet<>();
		for(O o: origin)
		{
			if(!arrayContainsValue(o, other))notSharedValues.add(o);
		}
		
		return notSharedValues;
	}
	
	public static <O> Set<O> valuesShared(O[] origin, O[] other)
	{
		
		Set<O> sharedValues = new HashSet<>();
		for(O o: origin)
		{
			if(arrayContainsValue(o, other))sharedValues.add(o);
		}
		
		return sharedValues;
	}

	public static <O> O[] append(O[] first, O[] second)
	{
		   O[] result = Arrays.copyOf(first, first.length + second.length);

		    System.arraycopy(
		        second, 0,
		        result, first.length,
		        second.length
		    );

		    return result;
	}

	public static <O> int arraySmallestIndexOf(O o, O[] orray) throws MathException
	{

		int len = orray.length;
		
		for(int n=0;n<len;n++)
		{
			O o2 = orray[n];
			if(o2.equals(o))return n;
		}

		throw new MathException("Array does not contain that");
	}

	
	public static <O> boolean arrayContainsValue(O o, O[] orray)
	{

		for(O toBeTested: orray)if(toBeTested.equals(o))return true;

		return false;
	}

	public static boolean isRegularArray(Object array)
	{

	    if (array == null || !array.getClass().isArray())
	        return false;

	    int length = java.lang.reflect.Array.getLength(array);
	    if (length == 0)
	        return true; // empty arrays count as regular

	    // Determine expected structure from first element
	    Object first = java.lang.reflect.Array.get(array, 0);
	    int expectedDim = getArrayDimension(first);

	    // Check every element matches the structure
	    for (int i = 1; i < length; i++) {
	        Object elem = java.lang.reflect.Array.get(array, i);
	        int dim = getArrayDimension(elem);

	        // Elements must have same dimensionality
	        if (dim != expectedDim)
	            return false;
	    }

	    // If elements are themselves arrays, recurse
	    if (expectedDim > 0) {
	        for (int i = 0; i < length; i++) {
	            Object elem = java.lang.reflect.Array.get(array, i);
	            if (!isRegularArray(elem))
	                return false;
	        }
	    }

	    return true;
	}

	public static int getArrayDimension(Object array)
	{
	    int dim = 0;

	    Class<?> cls = array.getClass();

	    while (cls.isArray())
	    {
	        dim++;
	        cls = cls.getComponentType();
	    }
	    
	    return dim;
	}

	public static boolean containsNull(Object array) throws MathException
	{
	    if(array == null) throw new MathException("Can't test null.");
	    if(!array.getClass().isArray()) throw new MathException("Can't test somethin that is not an array.");

	    int length = java.lang.reflect.Array.getLength(array);

	    for (int i = 0; i < length; i++)
	    {
	        Object element = java.lang.reflect.Array.get(array, i);

	        if (element == null) return true;

	        if (element.getClass().isArray())
	        {
	            if (containsNull(element)) return true;
	        }
	    }
	    
	    return false;
	}

	public static List<Double> sumUpToIndex(List<Double> layers)
	{

		List<Double> sumList = new ArrayList<>();
		

		if(layers==null)return sumList;
		
		sumList.add(layers.get(0));
		int nrOfLayers = layers.size();
		
		//Careful it starts with one.
		for(int n=1;n<nrOfLayers;n++)
		{
			
			Double d = layers.get(n);
			Double d2 = sumList.get(n-1);
			
			if(d==null||d2==null)throw new IllegalArgumentException("At least one Argument is null.");
			sumList.add(d + sumList.get(n-1));
		}

		return sumList;
	}

	public static double randomNrBoundBetween(List<Double> layers)
	{
		if(layers==null||layers.isEmpty())return 0.0d;

		double sum = layers.stream().reduce(0.0, Double::sum);
		
		return Math.random()*sum;
	}

	public static <A> int  betweenWhichElements(double betweener, List<Pair<A, Double>> layers)
	{
		
		if(layers==null||layers.isEmpty())return 0;
		
		int nrOfLayers = layers.size();
		if(betweener<=0)return -1;//Before the first Layer.
		
		List<Double> upStacking = new ArrayList<>();
		for(int n=0;n<nrOfLayers;n++)
		{
			
			Double d = layers.get(n).getValue();
			if(d==null)throw new IllegalArgumentException("At least one value is null");
			upStacking.add(d);
		}
		
		List<Double> layerSumUpToIndex = sumUpToIndex(upStacking);
		
		if(betweener>layerSumUpToIndex.get(nrOfLayers-1))return nrOfLayers;//Beyond the Last Layer
		
		for(int n=0;n<nrOfLayers;n++)
		{
			if(betweener<=layerSumUpToIndex.get(n))return n;
		}
		
		throw new IllegalArgumentException("Something is Wrong.");
	}

	public static <A> List<Double> getRidOfTheGeneric(List<Pair<A, Double>> layers) throws CollectionException
	{
		
		if(layers==null)throw new CollectionException("List is Null.");
		
		int nrOfLayers = layers.size();
		
		List<Double> upStacking = new ArrayList<>();
		for(int n=0;n<nrOfLayers;n++)
		{
			upStacking.add(layers.get(n).getValue());
		}
		
		return upStacking;
	}

	public static <T> T catchRandomElementOfCollection(Collection<T> collection) throws CollectionException
	{
		
		if(collection==null)throw new CollectionException("Set is Null.");
		if(collection.isEmpty())return null;
		
		List<T> list = new ArrayList<>(collection);
		
		return catchRandomElementOfList(list);
	}

	public static <T> T catchRandomElementOfSet(Set<T> set) throws CollectionException
	{
		
		if(set==null)throw new CollectionException("Set is Null.");
		if(set.isEmpty())return null;

		List<T> list = new ArrayList<>(set);
		
		return catchRandomElementOfList(list);
	}

	public static <T> T catchRandomElementOfList(List<T> list) throws CollectionException
	{
		
		if(list==null)throw new CollectionException("List is Null.");
		if(list.isEmpty())return null;
		int n = list.size();
		int r = (int)(Math.random()*n);
		
		return list.get(r);
	}

	public static <T> Set<List<T>> cartesianProduct(List<Set<T>> input) throws CollectionException
	{
		
		if(input==null)throw new CollectionException("List is Null.");

		Set<List<T>> output = new HashSet<>();
		int s = input.size();
		
		if(s==0)return output;
				
		Set<T> insider = input.get(0);
		List<Set<T>> smaller = new ArrayList<>(input);
		smaller.remove(insider);
		
		if(s==1)
		{
			for(T t: insider)
			{
				List<T> list = new ArrayList<>();
				list.add(t);
				output.add(list);
			}
			
			return output;
		}
		
		for(T t: insider)
		{
			
			for(List<T> list: cartesianProduct(smaller))
			{
				
				list.add(t);
				output.add(list);
			}
		}
		
		return output;
	}

	public static <T> Set<Set<T>> powerSet(Set<T> input) throws CollectionException
	{

		if(input==null)throw new CollectionException("Set can't be Null.");
		if(input.contains(null))throw new CollectionException("Set contains Null.");
	
		Set<Set<T>> output = new HashSet<>();
		if(input.size()==0)
		{
			output.add(new HashSet<T>());//Add an (the) emptySet;
			return output;
		}
	
		Set<T> copy = new HashSet<>(input);
		T cutOut = CollectionManipulation.catchRandomElementOfSet(copy);
		copy.remove(cutOut);
		for(Set<T> set: powerSet(copy))
		{
			output.add(set);
			Set<T> withCutOut = new HashSet<>(set);
			withCutOut.add(cutOut);
			output.add(withCutOut);
		}
	
		return output;
	}

	public static <T> Set<List<T>> permutationsWithoutRepition(Set<T> originSet)throws CollectionException
	{
		
		List<T> emptyList = new ArrayList<>();
		
		Set<List<T>> output = new HashSet<>();
		if(originSet==null)throw new CollectionException("Set can't be null.");
		if(originSet.contains(null))throw new CollectionException("Set contains null.");
		
		int n = originSet.size();
		
		if(n==0) 
		{
			output.add(emptyList);
			return output;
		}

		for(T t: originSet)
		{		
			Set<T> copy = new HashSet<>(originSet);
			copy.remove(t);

			for(List<T> list: permutationsWithoutRepition(copy))
			{
				list.add(t);
				output.add(list);
			}
		}

		return output;
	}

	public static <T extends Comparable<T>> Set<List<T>> permutationsWithRepition(List<T> originList) throws CollectionException
	{
		
		
		List<T> emptyList = new ArrayList<>();
		Set<List<T>> output = new HashSet<>();
		if(originList==null)throw new CollectionException("Set can't be null.");
		if(originList.contains(null))throw new CollectionException("Set contains null.");
		
		int n = originList.size();

		if(n==0) 
		{
			output.add(emptyList);
			return output;
		}

		for(int i=0;i<originList.size();i++)
		{		
			T t = originList.get(i);
			List<T> copy = new ArrayList<>(originList);
			copy.remove(t);

			for(List<T> list: permutationsWithRepition(copy))
			{
				list.add(t);
				output.add(list);
			}
		}

		return output;
	}

	public static <T> Set<List<T>> variationsOfSizeNWithRepition(Set<T> originSet, int n) throws CollectionException
	{
		
		List<T> emptyList = new ArrayList<>();
		Set<List<T>> output = new HashSet<>();
		if(originSet==null)throw new CollectionException("Set can't be null.");
		if(n<0) throw new CollectionException("n is to small. It is below Zero.");
		if(originSet.contains(null))throw new CollectionException("Set contains null.");
		
		if(n==0||originSet.isEmpty()) 
		{
			output.add(emptyList);
			return output;
		}

		for(T t: originSet)
		{

			for(List<T> list: variationsOfSizeNWithRepition(originSet, n-1))
			{
				list.add(t);
				output.add(list);
			}
		}
		
		return output;
	}

	public static <T extends Comparable<T>> Set<List<T>> combinationsOfSizeNWithRepition(Set<T> originSet, int n) throws CollectionException
	{
		
		List<T> emptyList = new ArrayList<>();
		Set<List<T>> output = new HashSet<>();
		if(originSet==null)throw new CollectionException("Set can't be null.");
		if(n<0) throw new CollectionException("n is to small. It is below Zero.");
		if(originSet.contains(null))throw new CollectionException("Set contains null.");
		
		if(n==0||originSet.isEmpty()) 
		{
			output.add(emptyList);
			return output;
		}

		List<T> sortedList = new ArrayList<>(originSet);
		Collections.sort(sortedList);
		
		for(int i=0;i<sortedList.size();i++)
		{
		
			T t = sortedList.get(i);
			
			for(int j=0;j<sortedList.size();j++)
			{
				
				for(List<T> list: combinationsOfSizeNWithRepition(originSet, n-1))
				{
					
					list.add(t);
					Collections.sort(list);
					output.add(list);
				}
			}
		}
		
		return output;
	}

	public static <T> Set<Set<T>> combinationsOfSizeNWithoutRepition(Set<T> originSet, int n) throws CollectionException
	{

		Set<T> emptySet = new HashSet<>();
		Set<Set<T>> output = new HashSet<>();
		if(originSet==null)throw new CollectionException("Set can't be null.");
		if(n<0) throw new CollectionException("n is to small. It is below Zero.");
		if(n>originSet.size())throw new CollectionException("n is bigger then the Set size.");
		if(originSet.contains(null))throw new CollectionException("Set contains null.");
		
		if(n==0||originSet.isEmpty()) 
		{
			output.add(emptySet);
			return output;
		}

		if(n==originSet.size())
		{
			output.add(originSet);
			return output;
		}

		for(T t: originSet)
		{		
			Set<T> copy = new HashSet<>(originSet);
			copy.remove(t);

			for(Set<T> set: combinationsOfSizeNWithoutRepition(copy, n))
			{
				output.add(set);
			}
		}

		return output;
	}

	public static <T> Set<List<T>> discriminate(Set<List<T>> origin, Predicate<List<T>> predicate)
	{
		
		Set<List<T>> output = new HashSet<>();
		
		for(List<T> list: origin)if(predicate.test(list))output.add(list);

		return output;
	}

	public static <T> Set<List<T>> discriminateByIndexWrapper(Set<List<T>> origin, BiPredicate<List<T>, List<Integer>> biPredicate)
	{
		
		Set<List<T>> output = new HashSet<>();
		
		for(List<T> list: origin)
		{
			int begin = 0;
			int end = list.size()-1;
		    List<Integer> indizes = IntStream.rangeClosed(begin, end).boxed().collect(Collectors.toList());  
			if(biPredicate.test(list, indizes))output.add(list);
		}

		return output;
	}

	public static <T extends Number> Double multiplyListElements(List<T> origin, int n) throws CollectionException
	{
		int s = origin.size();
		if(n>s)throw new CollectionException("Multiply index to high.");
		if(n<0)throw new CollectionException("Multiply index to low.");
		if(origin.contains(null))throw new CollectionException("List contains null.");

		Double product = 1.0;
		for(int i=0;i<n;i++)
		{
			
			T t = origin.get(i);
			if(t.doubleValue()==0.0)return t.doubleValue();
			
			product = t.doubleValue()*product;
		}

		return product;
	}

	public static <T extends Number> Double addSetElements(Set<T> origin) throws CollectionException
	{

		if(origin==null)throw new CollectionException("Set is null.");
		if(origin.contains(null))throw new CollectionException("origin contains null.");
		
		Double sum = 0.0;
		if(origin.isEmpty())return sum;

		for(T t: origin)
		{
			sum = t.doubleValue()+sum;
		}

		return sum;
	}

	public static <T extends Number> Double multiplySetElements(Set<T> origin) throws CollectionException
	{
		
		if(origin==null)throw new CollectionException("Set is null.");
		if(origin.contains(null))throw new CollectionException("origin contains null.");
		
		Double product = 1.0;
		if(origin.isEmpty())return 0.0;

		for(T t: origin)
		{
			
			if(t.doubleValue()==0.0)return t.doubleValue();
			
			product = t.doubleValue()*product;
		}

		return product;
	}

	public static <T extends Number> Double addListElements(List<T> origin, int n) throws CollectionException
	{

		if(origin==null)throw new CollectionException("Set is null.");
		if(origin.contains(null))throw new CollectionException("origin contains null.");

		int s = origin.size();
		if(n>s)throw new CollectionException("Sum index to high.");
		if(n<0)throw new CollectionException("Sum index to low.");
		
		Double sum = 0.0;
		if(origin.isEmpty())return sum;

		for(int i=0;i<n;i++)
		{
			T t = origin.get(i);
			sum = t.doubleValue()+sum;
		}

		return sum;
	}

	public static <T extends Number> Double productSumSet(Set<T> set, int n) throws CollectionException
	{
		
		Double ps = 0.0;

		Set<Set<T>> selections = combinationsOfSizeNWithoutRepition(set, n);
		
		for(Set<T> s: selections)ps = ps+multiplySetElements(s);
		
		return ps;
	}

	public static <T extends Number> Double sumProductSet(Set<T> set, int n) throws CollectionException
	{
		
		Double ps = 1.0;

		Set<Set<T>> selections = combinationsOfSizeNWithoutRepition(set, n);

		for(Set<T> s: selections)ps = ps*addSetElements(s);
		
		return ps;
	}

	public static Set<Integer> getSetOfFirstNIntegers(int n)
	{

		Set<Integer> output = new HashSet<>();

		for(int i=0;i<n;i++)output.add(i);
		
		return output;
	}

	public static <T> Set<T> catchRandomNAndRemove(int n, Set<T> source) throws CollectionException
	{
		
		Set<T> output = new HashSet<>();
		if(n>source.size())throw new IllegalArgumentException("Not enough Elements in Set.");
		
		for(int i=0;i<n;i++)
		{
			T t = CollectionManipulation.catchRandomElementOfSet(source);
			source.remove(t);
			output.add(t);
		}
		
		return output;
	}

	public static Set<Character> getSetOfFirstNLatinLettersUppercase(int n)
	{

		Set<Character> output = new HashSet<>();

		
		for(int i=0;i<n;i++)
		{
			int num = 'A';
			int newNum = num+i;
			char newChar = (char)newNum;
			output.add(newChar);
		}
		
		return output;
	}

	public static Set<Character> getSetOfFirstNLatinLettersLowerCase(int n)
	{

		Set<Character> output = new HashSet<>();

		
		for(int i=0;i<n;i++)
		{
			int num = 'a';
			int newNum = num+i;
			char newChar = (char)newNum;
			output.add(newChar);
		}
		
		return output;
	}

	public static List<Character> getListOfFirstNLettersLowerCase(int n)
	{
		Set<Character> set = getSetOfFirstNLatinLettersLowerCase(n);
		
		List<Character> output = new ArrayList<>(set);
		Collections.sort(output);

		return output;
	}

	public static List<Character> getListOfFirstNLettersUpperCase(int n)
	{
		Set<Character> set = getSetOfFirstNLatinLettersUppercase(n);
		
		List<Character> output = new ArrayList<>(set);
		Collections.sort(output);

		return output;
	}

	public static <E> Set<E> implode(Set<Set<E>> origin)
	{
		
		Set<E> implosion = new HashSet<>();
		
		for(Set<E> set: origin)implosion.addAll(set);
		
		return implosion;
	}
}