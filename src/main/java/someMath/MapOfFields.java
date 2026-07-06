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

	public static <O> Field<O> getField(Class<?> clazz)
	{
		return (Field<O>) map.get(clazz);
	}
}
