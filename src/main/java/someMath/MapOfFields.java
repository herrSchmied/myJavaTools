package someMath;

import java.util.HashMap;
import java.util.Map;

import someMath.exceptions.MathException;

public class MapOfFields
{

	private static  Map<Class<?>, Field<?>> map = new HashMap<>();

	static
	{
		map.put(Double.class, new DoubleField());
	}

	public static <T> Field<T> getField(Class<T> clazz) throws MathException
	{
		if(!map.keySet().contains(clazz))
			throw new MathException("Field for this class non existend.");
		
		return  (Field<T>) map.get(clazz);
	}
}
