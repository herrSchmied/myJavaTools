package someMath.simplePrimeTools;


import java.util.Collections;
import java.util.List;

import fileShortCuts.TextAndObjSaveAndLoad;
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
}
