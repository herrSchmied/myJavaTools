package someMath;

import java.util.HashMap;
import java.util.Map;

public class MapOfAlgebraicFields
{

	public static  Map<Class<? extends Number>, AlgebraicField<?>> map = new HashMap<>();
	
	public MapOfAlgebraicFields()
	{
		map.put(Double.class, new DoubleField());
	}
}
