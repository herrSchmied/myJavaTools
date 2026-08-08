package allgemein;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import javafx.util.Pair;
import someMath.NaturalNumber;
import someMath.NaturalNumberException;


/**
 * ExactPeriode is relative it depends on two LocalDateTime's (ldt's)
 * fromLDT and toLDT. Because a year is not always 365 Days. And a 
 * Month is variable, in days, too. I'm trying to make this class 
 * immutable. I don't think it makes sense to have a Method that 
 * gives you an exact or otherwise Periode if you don't have to 
 * ldt's to relate to. So i don't have it. In The Periode class of 
 * the SDK there are such Methods like ofXXX(params).
 */

	/*
	 * TODO: Still Refactoring!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 */

public class ExactPeriode 
{

	private final NaturalNumber years;
	private final NaturalNumber months;
	private final NaturalNumber days;
	private final NaturalNumber hours;
	private final NaturalNumber minutes;
	private final NaturalNumber seconds;
	private final NaturalNumber nanos;

	private final LocalDateTime fromLDT;
	private final LocalDateTime toLDT;
	private final boolean isNegative;

	
	public ExactPeriode(LocalDateTime fromLDT, LocalDateTime toLDT) throws NaturalNumberException
	{
		
		this.fromLDT = fromLDT;//To ensure Immutability.
		
		
		this.toLDT = toLDT;//To ensure Immutability.
		
    	if(fromLDT.isBefore(toLDT))isNegative = false;
    	else isNegative = true;    		
    	
    	LocalDateTime fromLDTTemp = fromLDT;
    	LocalDateTime toLDTTemp = toLDT; 
    	
    	if(isNegative)
    	{ 
    		fromLDTTemp = toLDT;
    		toLDTTemp = fromLDT;
    	}

    	years = new NaturalNumber((long) fromLDTTemp.until( toLDTTemp, ChronoUnit.YEARS ));
    	fromLDTTemp = fromLDTTemp.plusYears( years.getNumberCore() );
    	
    	months = new NaturalNumber((long) fromLDTTemp.until( toLDTTemp, ChronoUnit.MONTHS ));
    	fromLDTTemp = fromLDTTemp.plusMonths( months.getNumberCore() );
    	
    	days = new NaturalNumber((long) fromLDTTemp.until( toLDTTemp, ChronoUnit.DAYS ));
    	fromLDTTemp = fromLDTTemp.plusDays( days.getNumberCore() );

    	hours = new NaturalNumber((long) fromLDTTemp.until( toLDTTemp, ChronoUnit.HOURS ));
    	fromLDTTemp = fromLDTTemp.plusHours( hours.getNumberCore() );
    	
    	minutes = new NaturalNumber((long) fromLDTTemp.until( toLDTTemp, ChronoUnit.MINUTES ));
    	fromLDTTemp = fromLDTTemp.plusMinutes( minutes.getNumberCore() );

    	seconds = new NaturalNumber((long) fromLDTTemp.until( toLDTTemp, ChronoUnit.SECONDS ));
    	fromLDTTemp = fromLDTTemp.plusSeconds( seconds.getNumberCore() );
    	
    	nanos = new NaturalNumber((long) fromLDTTemp.until(toLDTTemp, ChronoUnit.NANOS ));
	}

	public ExactPeriode plusYears(NaturalNumber yearsPlus) throws NaturalNumberException
	{
		
		LocalDateTime newToLDT = this.toLDT.plusYears(yearsPlus.getNumberCore());		
		return new ExactPeriode(fromLDT, newToLDT);
	}

	public ExactPeriode plusMonth(NaturalNumber monthPlus) throws NaturalNumberException
	{

		LocalDateTime newToLDT = this.toLDT.plusMonths(monthPlus.getNumberCore());
		return new ExactPeriode(fromLDT, newToLDT);
	}
	
	public ExactPeriode plusDays(NaturalNumber daysPlus) throws NaturalNumberException
	{
		
		LocalDateTime newToLDT = this.toLDT.plusDays(daysPlus.getNumberCore());
		return new ExactPeriode(fromLDT, newToLDT);
	}
	
	public ExactPeriode plusHours(NaturalNumber hoursPlus) throws NaturalNumberException
	{
		
		LocalDateTime newToLDT = this.toLDT.plusHours(hoursPlus.getNumberCore());
		return new ExactPeriode(fromLDT, newToLDT);
	}
	
	public ExactPeriode plusMinutes(NaturalNumber minutesPlus) throws NaturalNumberException
	{

		LocalDateTime newToLDT = this.toLDT.plusMinutes(minutesPlus.getNumberCore());
		return new ExactPeriode(fromLDT, newToLDT);
	}
	
	public ExactPeriode plusSeconds(NaturalNumber secondsPlus) throws NaturalNumberException
	{

		LocalDateTime newToLDT = this.toLDT.plusSeconds(secondsPlus.getNumberCore());
		return new ExactPeriode(fromLDT, newToLDT);
	}
	
	public ExactPeriode plusNanos(NaturalNumber nanoSecondsPlus) throws NaturalNumberException
	{
		
		LocalDateTime newToLDT = this.toLDT.plusNanos(nanoSecondsPlus.getNumberCore());
		return new ExactPeriode(fromLDT, newToLDT);
	}

	public ExactPeriode minusYears(NaturalNumber yearsMinus) throws NaturalNumberException
	{
	
		LocalDateTime newToLDT = this.toLDT.minusYears(yearsMinus.getNumberCore());
		return new ExactPeriode(fromLDT, newToLDT);
	}

	public ExactPeriode minusMonth(NaturalNumber monthMinus) throws NaturalNumberException
	{

		LocalDateTime newToLDT = this.toLDT.minusMonths(monthMinus.getNumberCore());
		return new ExactPeriode(fromLDT, newToLDT);
	}
	
	public ExactPeriode minusDays(NaturalNumber daysMinus) throws NaturalNumberException
	{

		LocalDateTime newToLDT = this.toLDT.minusDays(daysMinus.getNumberCore());
		return new ExactPeriode(fromLDT, newToLDT);
	}
	
	public ExactPeriode minusHours(NaturalNumber hoursMinus) throws NaturalNumberException
	{

		LocalDateTime newToLDT = this.toLDT.minusHours(hoursMinus.getNumberCore());
		return new ExactPeriode(fromLDT, newToLDT);
	}
	
	public ExactPeriode minusMinutes(NaturalNumber minutesMinus) throws NaturalNumberException
	{

		LocalDateTime newToLDT = this.toLDT.minusMinutes(minutesMinus.getNumberCore());
		return new ExactPeriode(fromLDT, newToLDT);
	}
	
	public ExactPeriode minusSeconds(NaturalNumber secondsMinus) throws NaturalNumberException
	{

		LocalDateTime newToLDT = this.toLDT.minusSeconds(secondsMinus.getNumberCore());
		return new ExactPeriode(fromLDT, newToLDT);
	}

	public ExactPeriode minusNanos(NaturalNumber nanosMinus) throws NaturalNumberException
	{

		LocalDateTime newToLDT = this.toLDT.minusNanos(nanosMinus.getNumberCore());
		
		return new ExactPeriode(fromLDT, newToLDT);
	}

	public int getAbsoluteDays() throws NaturalNumberException
	{
		Pair<LocalDateTime, LocalDateTime> pair = flipWhenNegative(fromLDT, toLDT);
		LocalDateTime fromLDTTemp = pair.getKey();
		LocalDateTime toLDTTemp = pair.getValue();
			
		return (int) fromLDTTemp.until( toLDTTemp, ChronoUnit.DAYS );

	}
	
	public int getAbsoluteHours() throws NaturalNumberException
	{

		
		Pair<LocalDateTime, LocalDateTime> pair = flipWhenNegative(fromLDT, toLDT);
		LocalDateTime fromLDTTemp = pair.getKey();
		LocalDateTime toLDTTemp = pair.getValue();

		
		return (int) fromLDTTemp.until( toLDTTemp, ChronoUnit.HOURS );
	}
	
	public int getAbsoluteMinutes() throws NaturalNumberException
	{
		Pair<LocalDateTime, LocalDateTime> pair = flipWhenNegative(fromLDT, toLDT);
		LocalDateTime fromLDTTemp = pair.getKey();
		LocalDateTime toLDTTemp = pair.getValue();

		
		return (int) fromLDTTemp.until( toLDTTemp, ChronoUnit.MINUTES );
	}
	
	public int getAbsoluteSeconds() throws NaturalNumberException
	{
		
		Pair<LocalDateTime, LocalDateTime> pair = flipWhenNegative(fromLDT, toLDT);
		LocalDateTime fromLDTTemp = pair.getKey();
		LocalDateTime toLDTTemp = pair.getValue();
		
		return (int) fromLDTTemp.until( toLDTTemp, ChronoUnit.SECONDS );
	}
	
	public int getAbsoluteNanos() throws NaturalNumberException
	{
		
		Pair<LocalDateTime, LocalDateTime> pair = flipWhenNegative(fromLDT, toLDT);
		LocalDateTime fromLDTTemp = pair.getKey();
		LocalDateTime toLDTTemp = pair.getValue();
		
		return (int) fromLDTTemp.until( toLDTTemp, ChronoUnit.NANOS );
	}

	public Pair<LocalDateTime, LocalDateTime> flipWhenNegative(LocalDateTime fromLDT, LocalDateTime toLDT) throws NaturalNumberException
	{
		if(new ExactPeriode(fromLDT, toLDT).isNegative)return new Pair<LocalDateTime, LocalDateTime>(toLDT, fromLDT);
		else return new Pair<LocalDateTime, LocalDateTime>(fromLDT, toLDT);
	}
	
	public NaturalNumber getYears()
	{
		return years;
	}
	
	public NaturalNumber getMonths()
	{
		return months;
	}
	
	public NaturalNumber getDays()
	{
		return days;
	}
	
	public NaturalNumber getHours()
	{
		return hours;
	}
	
	public NaturalNumber getMinutes()
	{
		return minutes;
	}
	
	public NaturalNumber getSeconds()
	{
		return seconds;
	}
	
	public NaturalNumber getNanos()
	{
		return nanos;
	}

	public LocalDateTime getFromLDT()
	{
		
		LocalDate ld = fromLDT.toLocalDate();
		LocalTime lt = fromLDT.toLocalTime();
		
		return LocalDateTime.of(ld, lt);//Giving out a copy in hope fromLDT can't
										//be changed from the outside.
	}
	
	public LocalDateTime getToLDT()
	{
		
		LocalDate ld = toLDT.toLocalDate();
		LocalTime lt = toLDT.toLocalTime();
		
		return LocalDateTime.of(ld, lt);//Giving out a copy in hope fromLDT can't
		//be changed from the outside.
	}
	
	public boolean getSign()
	{
		return !isNegative;
	}

	public String toString()
	{
		
		String output;
		
		if(isNegative)output = "*";
		else output = 	"";
			
		output = output	+ "Years: " + years + "."
						+ " Months: "+ months + "."
						+ " Days: " + days + "."
						+ " Hours: " + hours + "."
						+ " Minutes: " + minutes + "."
						+ " Seconds: " + seconds + ".";
		
		return output;
	}
}