package someMathTest.primeToolsTest;


import java.util.List;

import org.junit.jupiter.api.Test;

import someMath.exceptions.MathException;
import someMath.simplePrimeTools.EratosthenesSieb;


public class EratosthenesSiebTest 
{

	@Test
	public void testPrimes() throws MathException
	{
		EratosthenesSieb es = new EratosthenesSieb(1000);
		List<Integer> primeList = es.getPrimeList();
		assert(primeList.get(0)==2);
		
		
		System.out.println("size: " + primeList.size()+" ### Last Prime: " + primeList.get(999));
	}
}
