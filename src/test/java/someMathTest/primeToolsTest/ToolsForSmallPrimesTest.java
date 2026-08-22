package someMathTest.primeToolsTest;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import javafx.util.Pair;
import someMath.exceptions.MathException;
import someMath.simplePrimeTools.ToolsForSmallPrimes;

public class ToolsForSmallPrimesTest
{
	
	@Test
	public void testFactorization() throws ClassNotFoundException, MathException, IOException
	{

		ToolsForSmallPrimes tfsp = new ToolsForSmallPrimes("someResources/testPrimes", 2000);
		Pair<Long[], Long[]> pair = tfsp.factorize(60);
		
		System.out.println("primes, exponent");
		int len = pair.getKey().length;
		Long[] primes = pair.getKey();
		Long[] exponents = pair.getValue();
		
		for(int n=0;n<len;n++)
		{
			System.out.println(primes[n] + ", " + exponents[n]);
		}
	}
}