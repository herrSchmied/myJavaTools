package allgemein.execeptions;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class throwsExceptionIfNull
{

	@SafeVarargs
	public static<T> void nullCheck(Class<? extends Exception> clazz, String msg, T... array) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, Exception
	{
		
		List<T> list = Arrays.asList(array);
		if(list.contains(null))throw clazz.getDeclaredConstructor().newInstance(msg);
	}
}
