package someMath;

import java.util.HashMap;
import java.util.Map;

public class MapOfFields
{

	private static  Map<Class<?>, Field<?>> map = new HashMap<>();

	static
	{
		map.put(Double.class, new DoubleField());
	}

	public static <T> Field<T> getField(Class<T> clazz)
	{
		return  (Field<T>) map.get(clazz);
	}
}
