

import java.util.List;

import org.junit.jupiter.api.Test;

import someMath.EratosthenesSieb;
import someMath.exceptions.MathException;

public class EratosthenesSiebTest 
{

	@Test
	public void testPrimes() throws MathException
	{
		int maxPrimeNr = 20000;
		new EratosthenesSieb(maxPrimeNr);
		List<Integer> primeList = EratosthenesSieb.getPrimeList();
		assert(primeList.get(0)==2);
		
		
		System.out.println("size: " + primeList.size()+" ### Last Prime: " + primeList.get(maxPrimeNr-1));
	}
}
