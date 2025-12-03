package someMath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.util.Pair;
import someMath.exceptions.MathException;

public class VariableIndizies
{

	private Map<Integer, Integer> map = new HashMap<>();
	private final int size;

	public VariableIndizies(int n)
	{

		this.size = n;
		//Start with a boring oldIndex = newIndex.
		for(int m=1;m<n+1;m++)
		{
			map.put(m, m);
		}
	}

	public int getNewIndexOf(int oldIndex) throws MathException
	{

		if(oldIndex>=size)throw new MathException("No such old index.");
		return map.get(oldIndex);
	}

	public void setNewIndexOf(int oldIndex, int newIndex) throws MathException
	{
		if(oldIndex>=size)throw new MathException("No such old index.");
		if(newIndex>=size)throw new MathException("No such new index.");
	
		map.put(oldIndex, newIndex);
	}

	public int getOldIndexOf(Integer newIndex) throws MathException
	{

		if(newIndex>=size)throw new MathException("No such new index.");

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
	
	public int getSize()
	{
		return size;
	}
}