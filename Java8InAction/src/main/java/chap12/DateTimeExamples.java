package chap12;

import java.time.*;
import java.time.chrono.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.*;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

public class DateTimeExamples {


    public static void main(String[] args){
    	useLocalDate();
    	useTemporalAdjuster();
    	useDateFormatter();
    	useZoneTime();
    }

    private static void useLocalDate(){
        LocalDate date=LocalDate.of(2014,3,18);
        int year=date.getYear();
        Month month=date.getMonth();
        int day=date.getDayOfMonth();
        DayOfWeek dow=date.getDayOfWeek();
        int len=date.lengthOfMonth();
        boolean leap=date.isLeapYear();
        System.out.println(date.toString()+" "+dow.toString()+" "+leap);
        
        LocalDate today=LocalDate.now();
        System.out.println("当前时间: "+today);
        
        int year2=date.get(ChronoField.YEAR);
        int month2=date.get(ChronoField.MONTH_OF_YEAR);
        int day2=date.get(ChronoField.DAY_OF_MONTH);
        System.out.println("TemporalField获取时间："+year2+":"+month2+":"+day2);

        LocalTime time=LocalTime.of(13,45,20);
        int hour=time.getHour();
        int minute=time.getMinute();
        int second=time.getSecond();
        System.out.println("LocalTime:"+time);

        //解析创建
        LocalDate date2=LocalDate.parse("2014-03-19");
        LocalTime time2=LocalTime.parse("13:45:20");

        LocalDateTime dt1=LocalDateTime.of(2014,Month.MARCH,18,13,45,20);
        LocalDateTime dt2=LocalDateTime.of(date,time);
        LocalDateTime dt3=date.atTime(13,45,20);
        LocalDateTime dt4=date.atTime(time);
        LocalDateTime dt5=time.atDate(date);
        LocalDate date3=dt1.toLocalDate();
        LocalTime time3=dt1.toLocalTime();
        
        Instant instant=Instant.ofEpochSecond(3);
        Instant instant2=Instant.ofEpochSecond(3,0);
        Instant instant3=Instant.ofEpochSecond(2,1_000_000_000);
        Instant instant4=Instant.ofEpochSecond(4,-1_000_000_000);

        //主要用于秒
        Duration d1=Duration.between(time,time2);
        // Duration d2=Duration.between(date,date2);
        Duration d3=Duration.between(instant,instant2);
        //时间比较长
        Period tenDays=Period.between(LocalDate.of(2014,3,8),
                LocalDate.of(2014,3,18));

        Duration threeMinutes=Duration.ofMinutes(3);
        Duration threeMinutes2=Duration.of(3, ChronoUnit.MINUTES);
        Period tenDays2=Period.ofDays(10);
        Period threeWeeks=Period.ofWeeks(3);
        Period twoYearsSixMonthsOneDay=Period.of(2,6,1);
    }

    private static void useTemporalAdjuster(){
        //直观修改
        LocalDate date1=LocalDate.of(2014,3,18);
        LocalDate date2=date1.withYear(2011);
        LocalDate date3=date2.withDayOfMonth(25);
        LocalDate date4=date3.with(ChronoField.MONTH_OF_YEAR,9);
        //相对修改
        LocalDate date5=LocalDate.of(2014,3,18);
        LocalDate date6=date5.plusWeeks(1);
        LocalDate date7=date6.minusYears(3);
        LocalDate date8=date7.plus(6,ChronoUnit.MONTHS);
        
        LocalDate date9=LocalDate.of(2014,3,18);
        LocalDate date10=date9.with(nextOrSame(DayOfWeek.SUNDAY));
        LocalDate date11=date10.with(lastDayOfMonth());

        LocalDate date_nextWorkingDay=date11.with(temporal -> {
           DayOfWeek dow=DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd=1;
            if(dow==DayOfWeek.FRIDAY) dayToAdd=3;
            else if(dow==DayOfWeek.SATURDAY) dayToAdd=2;
            return temporal.plus(dayToAdd,ChronoUnit.DAYS);
        });

        TemporalAdjuster nextWorkingDay= TemporalAdjusters.ofDateAdjuster(
          temporal->{
              DayOfWeek dow=DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
              int dayToAdd=1;
              if(dow==DayOfWeek.FRIDAY) dayToAdd=3;
              if(dow==DayOfWeek.SATURDAY) dayToAdd=2;
              return temporal.plus(dayToAdd,ChronoUnit.DAYS);
          }
        );
        LocalDate date_nextWorkingDay2=date7.with(nextWorkingDay);
    }
    //封装
    public class NextWorkingDay implements TemporalAdjuster{
        @Override
        public Temporal adjustInto(Temporal temporal) {
            DayOfWeek dow=DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd=1;
            if(dow==DayOfWeek.FRIDAY) dayToAdd=3;
            else if(dow==DayOfWeek.SATURDAY) dayToAdd=2;
            return temporal.plus(dayToAdd,ChronoUnit.DAYS);
        }
    }

    private static void useDateFormatter(){
        LocalDate date=LocalDate.of(2014,3,18);
        String s1=date.format(DateTimeFormatter.BASIC_ISO_DATE);    System.out.println(s1);
        String s2=date.format(DateTimeFormatter.ISO_LOCAL_DATE);    System.out.println(s2);

        LocalDate date1=LocalDate.parse("20140318", DateTimeFormatter.BASIC_ISO_DATE);
        LocalDate date2=LocalDate.parse("2014-03-18",DateTimeFormatter.ISO_LOCAL_DATE);

        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date3=LocalDate.of(2014,3,18);
        String formattedDate=date3.format(formatter);
        LocalDate date4=LocalDate.parse(formattedDate,formatter);

        DateTimeFormatter italianFormatter=DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.ITALIAN);
        LocalDate date5=LocalDate.of(2014,3,18);
        String formattedDate2=date5.format(italianFormatter);
        LocalDate date6=LocalDate.parse(formattedDate2,italianFormatter);
        System.out.println(formattedDate2);

        DateTimeFormatter italianFormatter2=new DateTimeFormatterBuilder()
                .appendText(ChronoField.DAY_OF_MONTH)
                .appendLiteral(". ")
                .appendText(ChronoField.MONTH_OF_YEAR)
                .appendLiteral(" ")
                .appendText(ChronoField.YEAR)
                .parseCaseInsensitive()
                .toFormatter(Locale.ITALIAN);
    }

    private static void useZoneTime(){
        ZoneId romeZone=ZoneId.of("Europe/Rome");
        ZoneId zoneId= TimeZone.getDefault().toZoneId();

        LocalDate date=LocalDate.of(2014,Month.MARCH,18);
        ZonedDateTime zdt1=date.atStartOfDay(romeZone);

        LocalDateTime dateTime=LocalDateTime.of(2014,Month.MARCH,18,13,45);
        ZonedDateTime zdt2=dateTime.atZone(romeZone);

        Instant instant=Instant.now();
        ZonedDateTime zdt3=instant.atZone(romeZone);

        LocalDateTime dateTime2=LocalDateTime.of(2014,Month.MARCH,18,13,45);
        //TODO
        // Instant instant2=dateTime2.toInstant(romeZone);

        Instant instant3=Instant.now();
        LocalDateTime timeFromInstant=LocalDateTime.ofInstant(instant3,romeZone);

        ZoneOffset newYorkOffset=ZoneOffset.of("-05:00");
        LocalDateTime dateTime3=LocalDateTime.of(2014,Month.MARCH,18,13,45);
        OffsetDateTime dateTimeInNewYork=OffsetDateTime.of(dateTime3,newYorkOffset);

        LocalDate date2=LocalDate.of(2014,Month.MARCH,18);
        JapaneseDate japaneseDate=JapaneseDate.from(date2);

        Chronology japaneseChronology=Chronology.ofLocale(Locale.JAPAN);
        ChronoLocalDate now=japaneseChronology.dateNow();

        //伊斯兰日历
        HijrahDate ramadanDate=HijrahDate.now().with(ChronoField.DAY_OF_MONTH,1)
                .with(ChronoField.MONTH_OF_YEAR,9);
        System.out.println(
                "Ramadan starts on "+ IsoChronology.INSTANCE.date(ramadanDate)+
                        " and ends on "+
                        IsoChronology.INSTANCE.date(
                                ramadanDate.with(
                                        TemporalAdjusters.lastDayOfMonth()
                                )
                        )
        );

    }















}
