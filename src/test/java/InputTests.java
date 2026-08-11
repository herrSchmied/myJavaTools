

import org.junit.jupiter.api.Test;

import allgemein.LittleTimeTools;

import org.junit.jupiter.api.BeforeEach;

import consoleTools.InputArgumentException;
import consoleTools.InputStreamSession;
import consoleTools.TestInputReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class InputTests 
{

	
	LocalDateTime ancient;
	
	@BeforeEach
	public void prepare()
	{
		
		ancient = LocalDateTime.of(LocalDate.of(2, 1, 1), LocalTime.of(0, 0));
		System.out.println("Ancient: " + LittleTimeTools.timeString(ancient));
		System.out.println("Ancient: " + LittleTimeTools.timeString(ancient.plusDays(8)));
		
	}
	
	@Test
	public void testGetString() throws IOException 
	{
		
		String gruss = "Hi u";
		TestInputReader testInput = new TestInputReader(gruss);
		InputStreamSession inTaker = new InputStreamSession(testInput);

		String greetings = inTaker.getString("Hi u");
		
		assert(greetings.equals(gruss));
		inTaker.close();
	}
	
	@Test
	public void testGetDateTime() throws InputArgumentException, IOException
	{
		int hour = 0;
		int minute = 0;
		int year = 2;
		int month = 1;
		int day = 6;
		String data = hour+"\n"+minute+"\n"+year+"\n"+month+"\n"+day+"\n";
		TestInputReader testInput = new TestInputReader(data);
		InputStreamSession inTaker = new InputStreamSession(testInput);

		LocalDateTime ldt;
		try
		{
			ldt = inTaker.getDateTime("hi", ancient, ancient.plusDays(8));
			assert(ldt.isAfter(ancient)&&ldt.isBefore(ancient.plusDays(8)));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		inTaker.close();
	}

	@Test
	public void testGetDateTimeInOneLine() throws InputArgumentException, IOException
	{

		String year = "0002";
		String month = "JAN";
		int day = 6;
		String data = "0" + day + month + year + "T" + "00" + ":" + "00"+ "\n";

		TestInputReader testInput = new TestInputReader(data);
		
		InputStreamSession inTaker = new InputStreamSession(testInput);

		LocalDateTime ldt;
		ldt = inTaker.getDateTimeInOneLine("hi", ancient, ancient.plusDays(8));
		assert(ldt.isAfter(ancient)&&ldt.isBefore(ancient.plusDays(8)));
		inTaker.close();
	}

}