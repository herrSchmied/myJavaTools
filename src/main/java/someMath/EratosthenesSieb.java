package someMath;

import java.util.ArrayList;
import java.util.List;

import someMath.exceptions.MathException;

public class EratosthenesSieb 
{

	int maxPrimNr = 20000;
	private static List<Integer> primeList = new ArrayList<>();
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
	
	public static List<Integer> getPrimeList()
	{
		return primeList;
	}
	
	public static List<Integer> primesSmallerThen(int n)
	{
	
		return null;
	}
}