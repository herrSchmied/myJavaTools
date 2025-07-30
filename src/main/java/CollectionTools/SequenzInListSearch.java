package CollectionTools;

import java.util.List;


public class SequenzInListSearch
{
	 
	public static <T> boolean containingThisSequenz(List<T> sequenzSearchedFor, List<T> sequenzSearchedIn)
	{

		if(sequenzSearchedFor.size()>sequenzSearchedIn.size())return false;
	
		int diff = sequenzSearchedIn.size()-sequenzSearchedFor.size();
		
		for(int i=0;i<diff+1;i++)
		{
			if(containingThisSequenzAtPosition(sequenzSearchedFor, sequenzSearchedIn, i))return true;
		}
		
		return false;
	}

	public static <T> boolean containingThisSequenzAtPosition(List<T> sequenzSearchedFor, List<T> sequenzSearchedIn, int n)
	{
		if(n>=sequenzSearchedIn.size())return false;
		
		if(sequenzSearchedFor.size()>sequenzSearchedIn.size())return false;
		if(sequenzSearchedFor.size()==0)return true;

		T t1 = sequenzSearchedFor.get(0);
		T t2 = sequenzSearchedIn.get(n);
		if(!t1.equals(t2))return false;
		
		List<T> cutOff = sequenzSearchedFor.subList(1, sequenzSearchedFor.size());
		
		return containingThisSequenzAtPosition(cutOff, sequenzSearchedIn, n+1);
	}
	
	public static <T> int firstOccurrenceAt(List<T> sequenzSearchedFor, List<T> sequenzSearchedIn)
	{
		
		if(sequenzSearchedFor.size()==0)return -1;
		if(sequenzSearchedFor.size()>sequenzSearchedIn.size())return -1;
		
		T t = sequenzSearchedFor.get(0);
		if(!sequenzSearchedIn.contains(t))return -1;
		int diff = sequenzSearchedIn.size()-sequenzSearchedFor.size();
		
		for(int n=0;n<diff+1;n++)
		{
			if(containingThisSequenzAtPosition(sequenzSearchedFor,sequenzSearchedIn, n))return n;
		}
			
		return -1;
	}
}