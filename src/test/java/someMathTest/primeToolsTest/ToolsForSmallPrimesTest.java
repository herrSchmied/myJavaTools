package someMathTest.primeToolsTest;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import javafx.util.Pair;
import someMath.SmallTools;
import someMath.exceptions.MathException;
import someMath.simplePrimeTools.ToolsForSmallPrimes;

public class ToolsForSmallPrimesTest
{
	
	@Test
	public void testFactorization() throws ClassNotFoundException, MathException, IOException, InterruptedException
	{

		ToolsForSmallPrimes tfsp = new ToolsForSmallPrimes("someResources/testPrimes", 2000);
		
		for(int n=0;n<10;n++)
		{
			
			Long zufi = (long) SmallTools.randomInt(10000, 100);
			Pair<Long[], Long[]> pair = tfsp.factorize(zufi);

			int len = pair.getKey().length;
			Long[] primes = pair.getKey();
			Long[] exponents = pair.getValue();
			
			Long erg = (long) 1;
			String s = "";
			for(int k=0;k<len;k++)
			{
				
				Long prime = primes[k];
				Long ex = exponents[k];
				erg = (long) (erg * Math.pow(prime, ex));
				if(ex!=1)
				{
					if(k>0)s = s + "*(" + prime + "**" + ex + ")";
					else s = s + "(" + prime + "**" + ex + ")";
				}
				else
				{
					if(k>0)s = s + "*(" + prime + ")";
					else s = s + "(" + prime + ")";

				}
			}
			System.out.println(zufi + " = " + s);
			assert(erg.equals(zufi));
		}
		Thread.sleep(1000);
	}
}