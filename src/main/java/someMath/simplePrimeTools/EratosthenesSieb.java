package someMath.simplePrimeTools;



import java.util.ArrayList;
import java.util.List;

import someMath.exceptions.MathException;



public class EratosthenesSieb 
{

	int maxPrimNr = 20000;
	private List<Long> primeList = new ArrayList<>();
	private int primeNr=1;

	public EratosthenesSieb(int maxPrimeNr) throws MathException
	{

		if(maxPrimeNr<1) throw new MathException("maxPrimeNr too small.");
		if(this.maxPrimNr<maxPrimeNr) throw new MathException("This prime calculation takes to long.");
		long integerStart = 2;
		primeList.add(integerStart);

		for(long k = integerStart+1;primeNr<maxPrimeNr;k++)
		{

			boolean isPrime = true;
			for(long m: primeList)if(k%m==0)isPrime = false;

			if(isPrime)
			{
				primeList.add(k);
				primeNr++;
			}
		}
	}
	
	public List<Long> getPrimeList()
	{
		return primeList;
	}
	
}