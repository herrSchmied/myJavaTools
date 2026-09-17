package someMath.algebraicStructures.Storage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import someMath.AlgebraicStructures.Interfaces.Field;
import someMath.algebraicStructures.DoubleField;
import someMath.algebraicStructures.RationalField;
import someMath.exceptions.MathException;

public class MapOfFields
{

	private static  Map<Class<?>, Field<?,Integer>> map = new HashMap<>();

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
	public static <T> Field<T, Integer> getField(Class<T> clazz) throws MathException
	{
		if(!map.keySet().contains(clazz))
			throw new MathException("Field for this class non existend.");
		
		return  (Field<T, Integer>) map.get(clazz);
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
