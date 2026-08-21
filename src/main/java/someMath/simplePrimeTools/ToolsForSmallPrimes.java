package someMath.simplePrimeTools;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

	List<Integer> primeList;
	int largestPrime =2;
	String primeListPathStr;
	int maxPrimeNr;

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

				primeList = (List<Integer>)TextAndObjSaveAndLoad.loadObject(primeListPathStr);
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
		primeList = (List<Integer>)TextAndObjSaveAndLoad.loadObject(primeListPathStr);
		Collections.sort(primeList);
		largestPrime = primeList.get(maxPrimeNr-1);
	}

	public boolean isPrime(int n) throws MathException
	{

		if(n>largestPrime)throw new MathException("Exceeds this prime Scope.");
		return primeList.contains(n);
	}
	
	public Pair<Long[],Long[]> factorize(long toBeFactored)throws MathException
	{
		
		if(primeList.contains(toBeFactored))
		{
			Long[] primeBase = new Long[1];
			primeBase[0] = toBeFactored;
			Long[] primeExponent = new Long[1];
			primeExponent[0] = (long) 1;
			return new Pair<>(primeBase, primeExponent);
		}
		
		if(toBeFactored>largestPrime*largestPrime)throw new MathException("Exceeds factorization Scope.");
		long squareRoot = (long)( Math.sqrt((double)toBeFactored) + 1);

		Long[] primesNew = new Long[0];
		Long[] exponentNew = new Long[0];
		List<Long> smallerThanPrimes = primesSmallerThen(squareRoot);
		for(long possibleFactor: smallerThanPrimes)
		{
			if(toBeFactored%possibleFactor!=0)continue;
			else
			{
				long otherFactor = toBeFactored/possibleFactor;
				Pair<Long[],Long[]> factorsOne = factorize(possibleFactor);
				Long[] pList1 = factorsOne.getKey();
				Long[] exList1 = factorsOne.getValue();
				
				Pair<Long[],Long[]> factorsTwo = factorize(otherFactor);
				Long[] pList2 = factorsTwo.getKey();
				Long[] exList2 = factorsTwo.getValue();
				
				Long[] biggerArr = pList1;
				Long[] biggerArrEx = exList1;
				Long[] smallerArr = pList2;
				Long[] smallerArrEx = exList2;

				if(pList2.length>pList1.length)
				{
					biggerArr = pList2;
					biggerArrEx = exList2;
					smallerArr = pList1;
					smallerArrEx = exList1;
				}

				for(int n=0;n<biggerArr.length;n++)
				{
					long prime = biggerArr[n];
					
					if(arrayContainsValue(prime, pList2))
					{
						int indexPrime1 = arraySmallestIndexOf(prime, biggerArr);
						long exponentPrime1 = biggerArrEx[indexPrime1];
						
						int indexPrime2 = arraySmallestIndexOf(prime, smallerArr);
						long exponentPrime2 = smallerArrEx[indexPrime2];
						
						Long[] primeSingelton = new Long[1];
						primeSingelton[0] = prime;
						Long[] exponentSingelton = new Long[1];
						exponentSingelton[0] = exponentPrime1 + exponentPrime2;
						
						primesNew = append(primesNew, primeSingelton);
						exponentNew = append(exponentNew, exponentSingelton);

					}
					else
					{
						
						int index = arraySmallestIndexOf(prime, biggerArr);
						long exponent = biggerArrEx[index];
						
						Long[] primeSingelton = new Long[1];
						primeSingelton[0] = prime;
						Long[] exponentSingelton = new Long[1];
						exponentSingelton[0] = exponent;
						
						primesNew = append(primesNew, primeSingelton);
						exponentNew = append(exponentNew, exponentSingelton);

					}
				}
			}
		}

		return new Pair(primesNew, exponentNew);
	}
	
	public List<Long> primesSmallerThen(long n)throws MathException
	{
		
		if(n>largestPrime)throw new MathException("Exceeds largest saved Prime.");
		List<Long> smallerThanPrimes = new ArrayList<>();
		if(n<=2)return smallerThanPrimes;

		int m = 0;
		while(true)
		{

			long k = primeList.get(m);
			if(k<n)
			{
				smallerThanPrimes.add(k);
			}
			else break;
		}

		return smallerThanPrimes;
	}
}
