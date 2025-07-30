import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import CollectionTools.CollectionManipulation;
import javafx.util.Pair;
import someMath.exceptions.CollectionException;

public class BetweenWhichElementsTest 
{

	@Test
	public void testTheLayers() throws CollectionException 
	{
		List<Pair<Character, Double>> list = new ArrayList<>( Arrays.asList
			(
				new Pair<Character, Double>('@', 1.0),
				new Pair<Character, Double>('#', 1.5),
				new Pair<Character, Double>('Q', 2.3), 
				new Pair<Character, Double>('€', 0.5),
				new Pair<Character, Double>('€', 0.5),
				new Pair<Character, Double>('Q', 2.3),
				new Pair<Character, Double>('#', 1.5),
				new Pair<Character, Double>('@', 1.0)
			)
		);
		int size = list.size();
		
		
		List<Double> l2 = CollectionManipulation.getRidOfTheGeneric(list);
		double z = 2.0d;
		int k = CollectionManipulation.betweenWhichElements(z, list);
		assert(k==1);
		
		List<Double> sumUpToIndex = CollectionManipulation.sumUpToIndex(l2);
		
		for(int n=0;n<size;n++)
		{
			String note = "";
			if(n==k)note = " <- " + z;
			String lowerBound = "0.0";
			if(n>0)lowerBound = sumUpToIndex.get(n-1)+"";
			String upperBound = sumUpToIndex.get(n)+"";
			System.out.println(list.get(n).getKey()+":: " + lowerBound + "-" + upperBound + note);
		}
	}
}