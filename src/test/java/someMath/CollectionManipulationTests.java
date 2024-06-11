package someMath;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import consoleTools.TerminalXDisplay;

class CollectionManipulationTests
{

	static Set<Character> originSet = new HashSet<>(Arrays.asList('a', 'b', 'c', 'd'));
	static Set<Character> otherSet = new HashSet<>(Arrays.asList('a', 'b', 'c', 'd', 'e'));

	@Test
	void testPowerSet() throws CollectionException
	{

		Set<Set<Character>> pSet = CollectionManipulation.powerSet(originSet);
		System.out.println(TerminalXDisplay.collectionToString(pSet));
		
		assert(pSet.size()==16);
	}
	
	@Test
	void testSubSetsOfSizeN() throws CollectionException
	{
		Set<Set<Character>> subSets = CollectionManipulation.allSubSetsOfSizeN(originSet, 2);
		System.out.println(TerminalXDisplay.collectionToString(subSets));
		
		assert(subSets.size()==6);

		subSets = CollectionManipulation.allSubSetsOfSizeN(otherSet, 3);
		System.out.println(TerminalXDisplay.collectionToString(subSets));
		assert(subSets.size()==10);
	}

}