package someMathTest;

import org.junit.jupiter.api.Test;

import javafx.util.Pair;
import someMath.exceptions.MathException;

import static someMath.SmallTools.*;

import java.util.ArrayList;
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
		}

	}
	
	@Test
	public void cantorPairFunctionAndReverseTest() throws MathException
	{
		
		for(int n=0;n<100;n++)
		{

			long a = randomInt(0,100);
			long b = randomInt(0,100);
			Pair<Long, Long> pair = new Pair<>(a,b);
			long c1 = cantorNumOfPair(pair);
			Pair<Long, Long> aPlusOne = new Pair<>(a+1,b);
			long c2 = cantorNumOfPair(aPlusOne);
			Pair<Long, Long> bPlusOne = new Pair<>(a,b+1);
			long c3 = cantorNumOfPair(bPlusOne);
			Pair<Long, Long> aAndBPlusOne = new Pair<>(a+1,b+1);
			long c4 = cantorNumOfPair(aAndBPlusOne);

			assert(a==cantorPair(c1).getKey());
			assert(b==cantorPair(c1).getValue());
			assert((a+1)==cantorPair(c2).getKey());
			assert(b==cantorPair(c2).getValue());
			assert((b+1)==cantorPair(c3).getValue());
			assert(a==cantorPair(c3).getKey());
			assert((a+1)==cantorPair(c4).getKey());
			assert((b+1)==cantorPair(c4).getValue());
			
			assert(c2>c1);
			assert(c3>c1);
			assert(c4>c2);
			assert(c4>c3);
		}
	}
	
	@Test
	public void cantorTupelFunctionAndReverseTest() throws MathException, InterruptedException
	{

		List<Long> tupel = new ArrayList<>();
		for(int n=0;n<10;n++)
		{

			Long s = (long) 5;
			for(int k=0;k<s;k++)tupel.add((long) randomInt(1,8));
			Long c1 = cantorNumOfList(tupel);
			System.out.println("Part " + n);
			System.out.println("Tupel: " + tupel + " Size: " + tupel.size());
			System.out.println("C1: " + c1);
			List<Long> sameSame = cantorTupel(c1, s);
			System.out.println("Same same: " + sameSame + " Size: " + sameSame.size());
			assert(sameSame.equals(tupel));
			tupel.clear();
		}
	}
}
