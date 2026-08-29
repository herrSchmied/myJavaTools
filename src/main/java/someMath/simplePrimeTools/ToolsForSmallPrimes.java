package someMath.simplePrimeTools;


import java.util.ArrayList;
import java.util.Collections;

import java.util.List;
import java.util.Set;

import static CollectionTools.CollectionManipulation.*;
import fileShortCuts.TextAndObjSaveAndLoad;
import javafx.util.Pair;
import someMath.exceptions.MathException;

import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class ToolsForSmallPrimes
{

	private List<Long> primeList;
	private long largestPrime;
	private final String primeListPathStr;
	private final int maxPrimeNr;

	@SuppressWarnings("unchecked")
	public ToolsForSmallPrimes(String pathStr, int maxPrimeNr) throws MathException, IOException, ClassNotFoundException
	{

		primeListPathStr = pathStr;
		Path path = Paths.get(pathStr);
		boolean fileExists = Files.exists(path);
		this.maxPrimeNr = maxPrimeNr;

		if(!fileExists)
		{
			System.out.println("Primelist does not exist.");
			System.out.println("Making a new One. Wait a bit.");
			setupAndLoad();
		}
		else
		{
			
			try
			{

				primeList = (List<Long>)TextAndObjSaveAndLoad.loadObject(primeListPathStr);
				Collections.sort(primeList);
				largestPrime = primeList.get(maxPrimeNr-1);

				if(primeList.size()<20000)
				{
					System.out.println("Primelist a bit a bit short.");
					System.out.println("Making a new One. Wait a bit");
					setupAndLoad();
				}
			}
			catch(Exception e)
			{
				System.out.println("Couldn't load primelist.");
				System.out.println("Making a new One. Wait a bit.");
				setupAndLoad();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void setupAndLoad() throws ClassNotFoundException, IOException, MathException
	{
		new MakePrimeListFile(maxPrimeNr, primeListPathStr);
		primeList = (List<Long>)TextAndObjSaveAndLoad.loadObject(primeListPathStr);
		Collections.sort(primeList);
		largestPrime = primeList.get(maxPrimeNr-1);
	}

	public boolean isPrime(long n) throws MathException
	{

		if(n>largestPrime)throw new MathException("Exceeds this prime Scope.");
		return primeList.contains(n);
	}
	
	public Pair<Long[],Long[]> factorize(long toBeFactored)throws MathException
	{

		Long[] primesNew = new Long[0];
		Long[] exponentNew = new Long[0];

		Long factor = findFactor(toBeFactored);
		if(factor.equals(toBeFactored))
		{

			Long[] primeSingleton = new Long[1];
			primeSingleton[0]= factor;
			primesNew = append(primesNew, primeSingleton);
			
			Long[]exponent = new Long[1];
			exponent[0]= (long)1;
			exponentNew = append(exponentNew, exponent);
			return new Pair<>(primesNew, exponentNew);
		}

		long otherFactor = toBeFactored/factor;
		Pair<Long[],Long[]> factorsOne = factorize(factor);
		Long[] pList1 = factorsOne.getKey();
		Long[] exList1 = factorsOne.getValue();
				
		Pair<Long[],Long[]> factorsTwo = factorize(otherFactor);
		Long[] pList2 = factorsTwo.getKey();
		Long[] exList2 = factorsTwo.getValue();
				
				
		Set<Long> onlyInpList1 = valuesNotShared(pList1, pList2);
		Set<Long> onlyInpList2 = valuesNotShared(pList2, pList1);
		Set<Long> inBothLists = valuesShared(pList1, pList2);

					
		for(long prime: inBothLists)
		{
			int indexPrime1 = arraySmallestIndexOf(prime, pList1);
			long exponentPrime1 = exList1[indexPrime1];
					
			int indexPrime2 = arraySmallestIndexOf(prime, pList2);
			long exponentPrime2 = exList2[indexPrime2];
					
			Long[] primeSingelton = new Long[1];
			primeSingelton[0] = prime;
			Long[] exponentSingelton = new Long[1];
			exponentSingelton[0] = exponentPrime1 + exponentPrime2;
					
			primesNew = append(primesNew, primeSingelton);
			exponentNew = append(exponentNew, exponentSingelton);
		}
				

		for(long prime: onlyInpList1)
		{
		
			int index = arraySmallestIndexOf(prime, pList1);
			long exponent = exList1[index];
				
			Long[] primeSingelton = new Long[1];
			primeSingelton[0] = prime;
			Long[] exponentSingelton = new Long[1];
			exponentSingelton[0] = exponent;
						
			primesNew = append(primesNew, primeSingelton);
			exponentNew = append(exponentNew, exponentSingelton);

		}
					
				
		for(long prime: onlyInpList2)
		{
			int index = arraySmallestIndexOf(prime, pList2);
			long exponent = exList2[index];
				
			Long[] primeSingelton = new Long[1];
			primeSingelton[0] = prime;
			Long[] exponentSingelton = new Long[1];
			exponentSingelton[0] = exponent;
				
			primesNew = append(primesNew, primeSingelton);
			exponentNew = append(exponentNew, exponentSingelton);
		}

		return new Pair<>(primesNew, exponentNew);
	}
	
	private Long findFactor(Long toBeFactored) throws MathException
	{
		if(toBeFactored>largestPrime)throw new MathException("Exceeds factorization Scope.");
		if(primeList.contains(toBeFactored))return toBeFactored;
		
		long squareRoot = (long)( Math.sqrt((double)toBeFactored) + 1);

		List<Long> smallerThanPrimes = primesSmallerThen(squareRoot);
		
		for(long possibleFactor: smallerThanPrimes)
		{
			if(toBeFactored%possibleFactor!=0)continue;
			else return possibleFactor;
		}

		return (long)toBeFactored;
	}

	public List<Long> primesSmallerThen(long n)throws MathException
	{
		
		if(n>largestPrime)throw new MathException("Exceeds largest saved Prime.");
		List<Long> smallerThanPrimes = new ArrayList<>();
		if(n<=2)return smallerThanPrimes;

		long k = 0;
		for(int m=0;k<n;m++)
		{
			k = primeList.get(m);
			smallerThanPrimes.add(k);
		}

		return smallerThanPrimes;
	}

	public List<Long> getPrimeList()
	{
		return primeList;
	}

	public long getLargestPrime()
	{
		return largestPrime;
	}

	public String getPrimeListPathStr()
	{
		return primeListPathStr;
	}

	public int getMaxPrimeNr()
	{
		return maxPrimeNr;
	}
}
