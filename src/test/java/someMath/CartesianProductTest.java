package someMath;



import java.util.*;

import org.junit.jupiter.api.Test;

import CollectionTools.CollectionManipulation;
import someMath.exceptions.CollectionException;


public class CartesianProductTest 
{

	@Test
	public void testProducts() throws CollectionException 
	{
		

		Set<Character> alpha = new HashSet<>();
		alpha.add('a');
		alpha.add('b');
		alpha.add('c');
		
		Set<Character> beta = new HashSet<>();
		beta.add('x');
		beta.add('y');
		beta.add('z');
		
		Set<Character> sonderZeichen = new HashSet<>();
		sonderZeichen.add('#');
		sonderZeichen.add('@');
		
		List<Set<Character>> input = new ArrayList<>();
		input.add(alpha);
		input.add(beta);
		input.add(sonderZeichen);
		
		System.out.println("Input before c.Production: "+input);

		char a = CollectionManipulation.catchRandomElementOfSet(alpha);
		char b = CollectionManipulation.catchRandomElementOfSet(beta);
		char s = CollectionManipulation.catchRandomElementOfSet(sonderZeichen);
		List<Character> testList = new ArrayList<>();
		testList.add(s);
		testList.add(b);
		testList.add(a);
		
		
		Set<List<Character>> output = CollectionManipulation.cartesianProduct(input);

		assert(output.contains(testList));
		assert(output.size()==(alpha.size()*beta.size()*sonderZeichen.size()));
		System.out.println("Output Size: "+output.size());
		System.out.println(output);
	}
}
