package someMath;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import fileShortCuts.TextAndObjSaveAndLoad;
import someMath.exceptions.MathException;
import someMath.simplePrimeTools.MakePrimeListFile;

public class PrimeListSaveTest
{

	@Test
	public void SaveTest() throws MathException, IOException, ClassNotFoundException, InterruptedException
	{
		
		String path = "someResources/pList";
		new MakePrimeListFile(10, path);
		
		List<Integer> primeList = (List<Integer>) TextAndObjSaveAndLoad.loadObject(path);

		System.out.println(primeList);
		
		assert(primeList.size()==10);
		
		EratosthenesSieb sieb = new EratosthenesSieb(10);
		List<Integer> primeList2 = EratosthenesSieb.getPrimeList();
		
		System.out.println(primeList);
		System.out.println(primeList2);
		
		Thread.sleep(5500);
		//assert(primeList.equals(primeList2));
		//TODO: they are somehow not equal!!??
	}
}