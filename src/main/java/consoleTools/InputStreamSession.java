package consoleTools;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;


import allgemein.LittleTimeTools;


public class InputStreamSession implements AutoCloseable
{

	public static final Map<String, Month> monthMap = Map.ofEntries(
			  new AbstractMap.SimpleEntry<String, Month>("JAN", Month.JANUARY),
			  new AbstractMap.SimpleEntry<String, Month>("FEB", Month.FEBRUARY),
			  new AbstractMap.SimpleEntry<String, Month>("MAR", Month.MARCH),
			  new AbstractMap.SimpleEntry<String, Month>("APR", Month.APRIL),
			  new AbstractMap.SimpleEntry<String, Month>("MAY", Month.MAY),
			  new AbstractMap.SimpleEntry<String, Month>("JUN", Month.JUNE),
			  new AbstractMap.SimpleEntry<String, Month>("JUL", Month.JULY),
			  new AbstractMap.SimpleEntry<String, Month>("AUG", Month.AUGUST),
			  new AbstractMap.SimpleEntry<String, Month>("SEP", Month.SEPTEMBER),
			  new AbstractMap.SimpleEntry<String, Month>("OCT", Month.OCTOBER),
			  new AbstractMap.SimpleEntry<String, Month>("NOV", Month.NOVEMBER),
			  new AbstractMap.SimpleEntry<String, Month>("DEC", Month.DECEMBER)				  
			);

	final static int minYear = 0;
	
	final static int minMonth = 1;
	final static int maxMonth = 12;
	
	final static int minDay = 1;
	final static int maxDay = 31;
	
	final static int minHour = 0;
	final static int maxHour = 23;
	
	final static int minMinute = 0;
	final static int maxMinute = 59;
	
	final static String yes = "yes";
	final static char ja = 'y';

	final static String no = "no";
	final static char nein = 'n';
	
	final static String dontUnderstandTheAnswer = "I' don't Understand the answer.";
	
	final static String noNumber = "That's no Number";

	final static String answerOutOfBounds = "Answer out of Bounds.";
	
	final static String answerListBad = "Answer List Bad.(null or Empty)";
	
	final static String chooseANumber = "Choose a Number: ";
	
	final static int minNrForOutOfList = 1;
	
	final static String whichYear = "Which Year?: "; 
	final static String whichMonth = "Which Month?: ";
	final static String whichDay = "Which Day?: ";
	
	final static String whichHour = "Which Hour?: ";
	final static String whichMinute = "Which Minute?: ";
	
	final static String gatheredTimeOutOfBounds = "Gathered out of Bounds.";
	
	final static String timePlease = "Time Please.";
	final static String datePlease = "Date Please.";
	
	final static String dateTimeOutOfBounds = "Date out of Bounds.";
	
	/*
	 * I do not close scanner in any input Method. Because
	 * it's tied to System.in. If i would System.in would
	 * be closed to and never be opened again in the 
	 * running JVM. 
	 */
	

	private final InputReader inputReader;

	public InputStreamSession(InputStream is, Path historyFile)
	            throws IOException
	{    
		this(new JLineInputReader(is, historyFile));
    }

	// Constructor used by tests
	public InputStreamSession(InputReader inputReader)
	{
		this.inputReader = inputReader;
	}

	private String readLine(String question)
	{
		return inputReader.readLine(question);
	}
    
	@Override
	public void close() throws IOException
	{
	        inputReader.close();    
	}

	public boolean getYesOrNo(String qPhrase) throws IOException, InputArgumentException
	{

	    String answer = readLine(qPhrase);

	    String s = answer.toLowerCase().trim();

	    if (s.equals(ja + "") || s.equals(yes))
	        return true;

	    if (s.equals(nein + "") || s.equals(no))
	        return false;

	    throw new InputArgumentException(dontUnderstandTheAnswer);
		
	}

	public String getString(String qPhrase)
	{
	    return readLine(qPhrase);
	}

	public int getNrInput(String qPhrase, int startOfValideInput, int range) throws InputArgumentException
	{
		
	    int max = startOfValideInput + range;

	    String s = readLine(qPhrase);

	    int n;

	    try
	    {
	    	n = Integer.parseInt(s.trim());
	    	
		    if (n >= startOfValideInput && n <= max)
		        return n;

	        throw new InputArgumentException(answerOutOfBounds);

	    }
	    catch(NumberFormatException nfexc)
	    {
	    	throw new InputArgumentException(noNumber);
	    }
	}
	
	public String getAnswerOutOfList(String phrase,List<String> answerList)
	        throws IOException, InputArgumentException
	{
	    if (answerList.isEmpty() || answerList.contains(null))
	        throw new IllegalArgumentException(answerListBad);

	    int size = answerList.size();

	    System.out.println(phrase);

	    for (int n = 1; n < size + 1; n++)
	    {
	        System.out.println(n + ".) " + answerList.get(n - 1));
	    }

	    int n = getNrInput(
	            chooseANumber,
	            minNrForOutOfList,
	            size);

	    if (n > size || n < 0)
	        throw new InputArgumentException(answerOutOfBounds);

	    return answerList.get(n - 1);
	}	
	
	public LocalDate getDate(String qPhrase, LocalDate begin, LocalDate end) throws IOException, InputArgumentException
	{
		
		
		System.out.println(qPhrase);
		
		int yearBegin = begin.getYear();
		int yearEnd = end.getYear();
		
		int year = getNrInput(whichYear, yearBegin, yearEnd);
		int month = getNrInput(whichMonth,minMonth,maxMonth);
	
		boolean leapYear = Year.isLeap(year);
		Month m = Month.of(month);
		int maxDays = m.length(leapYear);
					
		int day = getNrInput(whichDay, minDay, maxDays);

		LocalDate ld = LocalDate.of(year, month, day);
		
		return ld;
	}
	
	public LocalTime getTime(String qPhrase, LocalTime begin, LocalTime end) throws IOException, InputArgumentException
	{
		
		
		System.out.println(qPhrase);
				
		int hour = getNrInput(whichHour, minHour, maxHour);
		int minute = getNrInput(whichMinute, minMinute, maxMinute);
		
		LocalTime gatheredTime = LocalTime.of(hour, minute);
		
		if(gatheredTime.isBefore(begin))throw new InputArgumentException(gatheredTimeOutOfBounds);
		
		if(gatheredTime.isAfter(end))throw new InputArgumentException(gatheredTimeOutOfBounds);
		
		return gatheredTime;
	}
			
	public LocalDateTime getDateTime(String qPhrase, LocalDateTime begin, LocalDateTime end) throws IOException, InputArgumentException
	{

		
		System.out.println(qPhrase);
		LocalTime beginOfTheDay = LocalTime.of(minHour, minMinute);
		LocalTime endOfTheDay = LocalTime.of(maxHour, maxMinute);
		
		LocalTime lt = getTime(timePlease, beginOfTheDay, endOfTheDay);
		
		LocalDate dateBegin = begin.toLocalDate();
		LocalDate dateEnd = end.toLocalDate();
		
		LocalDate ld = getDate(datePlease, dateBegin, dateEnd);
		
		String beginStr = LittleTimeTools.timeString(begin);
		String endStr = LittleTimeTools.timeString(end);
		
		LocalDateTime ldt = LocalDateTime.of(ld, lt);
		if(ldt.isBefore(begin)|| ldt.isAfter(end))throw new InputArgumentException(beginStr + " to " + endStr + dateTimeOutOfBounds);
	
		return ldt;
	}
	
	public LocalDateTime getDateTimeInOneLine(String phrase, LocalDateTime begin, LocalDateTime end) throws InputArgumentException
	{
		
		
		LocalDateTime ldt = null;
		String answer = getString(phrase);
		answer = answer.trim();
		String pattern = "(\\d+){2}([A-Z]+){3}(\\d+){4}T(\\d+){2}\\:(\\d+){2}";
		if(!answer.matches(pattern))throw new InputArgumentException("That answer doesn't fit pattern: DDMMMYYYYTHH:MM");
		
		String dayNr = answer.substring(0, 2);
		if(dayNr.startsWith("0"))dayNr = dayNr.substring(1);
		
		int day = Integer.parseInt(dayNr);
		if(day>31)throw new InputArgumentException("UnknownDay of Month.");
		
		String month = answer.substring(2, 5);
		if(!monthMap.containsKey(month))throw new InputArgumentException("Unknown Month.");
		Month m = monthMap.get(month);
		
		String year = answer.substring(5, 9);
		while(year.startsWith("0"))year = year.substring(1);
		int jahr = Integer.parseInt(year);
		
		String hour = answer.substring(10, 12);
		if(hour.startsWith("0"))hour = hour.substring(1);
		int stunde = Integer.parseInt(hour);
		if(stunde>23)throw new InputArgumentException("Unknown Hour.");
		
		String minute = answer.substring(13, 15);
		if(minute.startsWith("0"))minute = minute.substring(1);
		int minuteMinute = Integer.parseInt(minute);
		if(minuteMinute>59)throw new InputArgumentException("Unknown Minute.");
		
		LocalDate ld = LocalDate.of(jahr, m, day);
		LocalTime lt = LocalTime.of(stunde, minuteMinute);
		ldt = LocalDateTime.of(ld, lt);
		if(ldt.isBefore(begin)||ldt.isAfter(end))throw new InputArgumentException("This is not in the timeframe.");

		return ldt;
	}
	
	public String forcedString(String question)
	{

		String s = getString(question);
		if(s==null||s.trim().equals(""))
		{
			System.out.println("Empty String! Try Again!");
			return forcedString(question);
		}

		return s;
	}

	public boolean forcedYesOrNo(String question) throws IOException
	{
		boolean q;
		
		try
		{
			q = getYesOrNo(question);
			return q;
		}
		catch (InputArgumentException e)
		{
			System.out.println(e.getMessage());
			return forcedYesOrNo(question);
		}
	}
	
	public String forcedOutOfList(String phrase, List<String> listOfPossibleAnswers) throws IOException
	{
		String answer = "";
		
		try
		{
			answer = getAnswerOutOfList(phrase, listOfPossibleAnswers);
			return answer;
		}
		catch(InputArgumentException iae)
		{
			System.out.println(iae.getMessage());
			return forcedOutOfList(phrase, listOfPossibleAnswers);
		}
	}

	public LocalDateTime forcedDateTimeInOneLine(String phrase, LocalDateTime begin, LocalDateTime end)
	{
		
		LocalDateTime ldt;
		
		try
		{
			ldt = getDateTimeInOneLine(phrase, begin, end);
			return ldt;
		}
		catch(InputArgumentException iae)
		{
			System.out.println(iae.getMessage());
			return forcedDateTimeInOneLine(phrase, begin, end);
		}
	}
	
	public static String translateTimeToAnswerString(LocalDateTime ldt)
	{
		int day = ldt.getDayOfMonth();
		String dayStr = "" + day;
		if(day<10) dayStr = "0" + day;
		
		int month = ldt.getMonthValue();
		Month m = Month.of(month);
		String monthStr = "";
		for(String s: InputStreamSession.monthMap.keySet())
		{
			Month d = InputStreamSession.monthMap.get(s);
			if(m.equals(d))
			{
				monthStr = s;
				break;
			}
		}

		int hour = ldt.getHour();
		String hourStr = ""+hour;
		if(hour<10) hourStr = "0" + hour;
		
		int year = ldt.getYear();
		String yearStr = year+"";
		if(yearStr.length()==3)yearStr = "0" + yearStr;
		if(yearStr.length()==2)yearStr = "00" + yearStr;
		if(yearStr.length()==1)yearStr = "000" + yearStr;

		int minute = ldt.getMinute();
		String minStr = "" + minute;
		if(minute<10) minStr = "0" + minute;
		
		return dayStr + monthStr + year + "T" + hourStr + ":" + minStr;

	}

}