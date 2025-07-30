package someMath.simplePrimeTools;

import java.util.ArrayList;
import java.util.List;

import someMath.exceptions.MathException;

public class EratosthenesSieb 
{

	int maxPrimNr = 20000;
	private List<Integer> primeList = new ArrayList<>();
	private int primeNr=1;
	
	public EratosthenesSieb(int maxPrimeNr) throws MathException
	{
		
		if(maxPrimeNr<1) throw new MathException("maxPrimeNr too small.");
		if(this.maxPrimNr<maxPrimeNr) throw new MathException("This prime calculation takes to long.");
		int integerStart = 2;
		primeList.add(integerStart);
		
		for(int k = integerStart+1;primeNr<maxPrimeNr;k++)
		{
			
			boolean isPrime = true;
			for(int m: primeList)if(k%m==0)isPrime = false;

			if(isPrime)
			{
				primeList.add(k);
				primeNr++;
			}
		}
	}
	
	public List<Integer> getPrimeList()
	{
		return primeList;
	}
	
	public List<Integer> primesSmallerThen(int n)
	{
		//TODO
		return null;
	}
}