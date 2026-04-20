package someMathTest;

import org.junit.jupiter.api.Test;

import javafx.util.Pair;
import someMath.exceptions.MathException;

import static someMath.SmallTools.*;

import java.util.Arrays;
import java.util.List;


public class SmallToolsTest
{

	@Test
	public void superRootTest() throws MathException
	{
		
		List<Double> input = Arrays.asList(1.1, 2.0, 10.0, Math.pow(10, 10));
		
		for(Double i: input)
		{
			double x = superRoot(i);
		
			double erg = Math.pow(x, x);
		
			assert(erg-i<prettySmall);
			
			System.out.println("SuperRoot of " + i + " equal to " + x);
		}
	}
	
	@Test
	public void lambertWFunctionTest()
	{
	
		List<Double> input = Arrays.asList(0.5, 1.1, 2.0, 10.0, Math.pow(10, 10));
		
		for(Double i: input)
		{
			double x = lambertW(i);
		
			double erg = x*Math.pow(Math.E, x);
		
			assert(erg-i<prettySmall);
			
			System.out.println("W Value of " + i + " equal to " + x);
		}

	}
	
	@Test
	public void cantorPairFunctionAndReverseTest() throws MathException
	{
		
		for(int n=0;n<100;n++)
		{
			int a = randomInt(0,100);
			int b = randomInt(0,100);
			Pair<Integer, Integer> pair = new Pair<>(a,b);
			int c1 = cantorPair(pair);
			Pair<Integer, Integer> aPlusOne = new Pair<>(a+1,b);
			int c2 = cantorPair(aPlusOne);
			Pair<Integer, Integer> bPlusOne = new Pair<>(a,b+1);
			int c3 = cantorPair(bPlusOne);
			Pair<Integer, Integer> aAndBPlusOne = new Pair<>(a+1,b+1);
			int c4 = cantorPair(aAndBPlusOne);

			assert(a==reverseCantorPair(c1).getKey());
			assert(b==reverseCantorPair(c1).getValue());
			assert((a+1)==reverseCantorPair(c2).getKey());
			assert(b==reverseCantorPair(c2).getValue());
			assert((b+1)==reverseCantorPair(c3).getValue());
			assert(a==reverseCantorPair(c3).getKey());
			assert((a+1)==reverseCantorPair(c4).getKey());
			assert((b+1)==reverseCantorPair(c4).getValue());
			
			assert(c2>c1);
			assert(c3>c1);
			assert(c4>c2);
			assert(c4>c3);
		}
	}
}
