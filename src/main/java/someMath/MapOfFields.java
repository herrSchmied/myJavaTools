package someMath;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import someMath.exceptions.MathException;

public class MapOfFields
{

	private static  Map<Class<?>, Field<?>> map = new HashMap<>();

	static
	{
		map.put(Double.class, new DoubleField());
		map.put(RationalNumber.class, new RationalField());
		
		/*
			????????????????
			Not even mentioning GF(p^n)!!!!!
		map.put(ComplexNumber.class, ComplexField(new DoubleField()));
			????????????????
		*/
	}

	@SuppressWarnings("unchecked")
	public static <T> Field<T> getField(Class<T> clazz) throws MathException
	{
		if(!map.keySet().contains(clazz))
			throw new MathException("Field for this class non existend.");
		
		return  (Field<T>) map.get(clazz);
	}
	
	@SuppressWarnings("rawtypes")
	public static Set<Field> values()
	{
		return new HashSet<Field>(map.values());	
	}

	@SuppressWarnings("rawtypes")
	public static Set<Class> keySet()
	{
		return new HashSet<Class>(map.keySet());	
	}
}
