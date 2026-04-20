package someMath;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Equations
{

	Set<Character> allowedChars = new HashSet<>(Arrays.asList());
	
	String regex = "^\\s*[()]*(?:[\\p{IsLatin}\\p{IsGreek}0-9]+(?:\\s*[\\+\\-\\*/\\^]\\s*[()]*(?:[\\p{IsLatin}\\p{IsGreek}0-9]+))*[)]*)\\s*=\\s*[()]*(?:[\\p{IsLatin}\\p{IsGreek}0-9]+(?:\\s*[\\+\\-\\*/\\^]\\s*[()]*(?:[\\p{IsLatin}\\p{IsGreek}0-9]+))*[)]*)\\s*$";
	
	Pattern patternMathEquation = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
	Matcher matcherMathEquation;
	
	public Equations()
	{
	}

	public boolean valideChars(String content)
	{
		return patternMathEquation.matcher(content).matches();
	}
	
	public boolean valideParenthesisStructure(String content)
	{
		return false;
	}
}