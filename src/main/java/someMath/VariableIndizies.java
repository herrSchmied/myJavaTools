package someMath;


import java.util.HashMap;
import java.util.Map;


import someMath.exceptions.MathException;



public class VariableIndizies
{

	//Indizies start at 1!!!
	private final int minIndex = 1;
	private Map<Integer, Integer> map = new HashMap<>();
	private final int size;
	
	public VariableIndizies(int n) throws MathException
	{

		if(n<minIndex)throw new MathException("Index space smaller than 1.");
		this.size = n;
		//Start with a boring oldIndex = newIndex.
		for(int m=1;m<n+1;m++)
		{
			map.put(m, m);
		}
	}

	public int getNewIndexOf(int oldIndex) throws MathException
	{

		if(oldIndex>size||oldIndex<minIndex)throw new MathException("No such old index.");
		return map.get(oldIndex);
	}

	public void setNewIndexOf(int oldIndex, int newIndex) throws MathException
	{
		if(oldIndex>size||oldIndex<minIndex)throw new MathException("No such old index.");
		if(newIndex>size||newIndex<minIndex)throw new MathException("No such new index.");
	
		map.put(oldIndex, newIndex);
	}

	public int getOldIndexOf(Integer newIndex) throws MathException
	{

		if(newIndex>size||newIndex<minIndex)throw new MathException("No such new index.");

		for(Integer oldIndex: map.keySet())
		{
			Integer currentNewIndex = map.get(oldIndex);
			if(currentNewIndex.equals(newIndex))return oldIndex;
		}

		throw new MathException("Should not happen.");
	}

	public String indexToName(int index)
	{
		return "x"+index;
	}
	
	public Map<Integer, Integer> getMap()
	{
		return map;
	}

	public int getSize()
	{
		return size;
	}
}