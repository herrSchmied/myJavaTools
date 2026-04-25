package allgemeinTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import allgemein.ExactPeriode;
import someMath.NaturalNumber;
import someMath.NaturalNumberException;

public class ExactPeriodeTest
{

	@Test
	public void test() throws NaturalNumberException
	{

		DateTimeFormatter formatter =
			    DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSSSSSSS");


		String s = LocalDateTime.now().format(formatter);
		LocalDateTime jetzt = LocalDateTime.parse(s, formatter);
		LocalDateTime later = jetzt.plusMinutes(3);
		
		ExactPeriode ep = new ExactPeriode(jetzt, later);
		
		System.out.println("" + ep.getNanos() + " " + ep.getAbsoluteNanos());
		System.out.println("" + jetzt.getNano());
		
		//Remember: Time does not go on. Why?
	}
}
