package someMathTest.primeToolsTest;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import fileShortCuts.TextAndObjSaveAndLoad;
import someMath.exceptions.MathException;
import someMath.simplePrimeTools.EratosthenesSieb;
import someMath.simplePrimeTools.MakePrimeListFile;

public class PrimeListSaveTest
{

	@Test
	public void SaveTest() throws MathException, IOException, ClassNotFoundException, InterruptedException
	{
		
		int maxPrimeNr = 10;
		String path = "someResources/pList";
		new MakePrimeListFile(maxPrimeNr, path);
		
		@SuppressWarnings("unchecked")
		List<Integer> primeList = (List<Integer>) TextAndObjSaveAndLoad.loadObject(path);
		
		assert(primeList.size()==maxPrimeNr);
		
		EratosthenesSieb sieb = new EratosthenesSieb(maxPrimeNr);
		List<Long> primeList2 = sieb.getPrimeList();
		
		System.out.println(primeList);
		System.out.println(primeList2);

		assert(primeList.equals(primeList2));
	}
}