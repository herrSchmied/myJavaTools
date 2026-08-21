package someMath.simplePrimeTools;

import static fileShortCuts.TextAndObjSaveAndLoad.*;

import java.io.IOException;
import java.util.List;

import someMath.exceptions.MathException;


public class MakePrimeListFile
{

	public MakePrimeListFile(int n, String path) throws MathException, IOException
	{

		if(n>200000)throw new MathException("Prime Scope to large.");
		EratosthenesSieb eSieb = new EratosthenesSieb(n);

		List<Long> primeList = eSieb.getPrimeList();

		saveObject(path, primeList);
	}
}
