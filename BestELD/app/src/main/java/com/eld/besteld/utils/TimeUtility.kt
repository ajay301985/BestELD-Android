package com.eld.besteld.utils

import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*


//https://mkyong.com/java8/java-8-convert-localdatetime-to-timestamp/
//https://stackoverflow.com/questions/61756104/kotlin-localdatetime-as-long
class TimeUtility {
    companion object {
        fun debug(debugMessage : String) {
            println("[DEBUG] $debugMessage")
        }

        fun currentDateUTC(): LocalDateTime {
            var localeTime = LocalDateTime.now(ZoneOffset.UTC);
            var time123 = localeTime.minute
            val nowInSeconds: Long = localeTime.toEpochSecond(ZoneOffset.UTC)

            var date = LocalDate.now()
            var zoneId = ZoneId.of("UTC")
            var epoch = date.atStartOfDay(zoneId).toEpochSecond();
            print("tetstt")

            return  localeTime
        }

        fun getCurrentDateTimeInterval(): Long {
            var date = LocalDate.now()
            var zoneId = ZoneId.of("UTC")
            var seconds = date.atStartOfDay(zoneId).toEpochSecond();
            return seconds
        }

        fun getStartOfTheDay(localDate: LocalDateTime) {
            //val startOfDay: LocalDateTime = localDate.ats()
            //print(startOfDay.toString())
        }

        fun getTimeIntervalForStartOfTheDay(localDate: LocalDateTime) {
            //var startDate = getStartOfTheDay(localDate)
            var abc = currentDateUTC()
            val oldDate: LocalDate = LocalDate.of(1975, Month.JANUARY, 1)
            val newDate: LocalDate = LocalDate.of(2021, Month.MARCH, 12)

            var period = Period.between(oldDate, newDate)
            var dayObj = period.days
//            val diff: Long = newDate - oldDate

            print("dfdf")
        }

        fun timeForDateString(dateString: String): String {
//            String str = "2016-03-04 11:30";
            val timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm") //2021-03-11T19:01:39.428
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
            val dateTime = LocalDateTime.parse(dateString)
            val timeString = dateTime.hour.toString() + ":" + dateTime.minute.toString()
            return timeString
        }

        fun timeToStartOfTheDay(inDate: LocalDateTime) {
            //var dateTeimObj = LocalDateTime.now()
            var utcTime = inDate.atZone(ZoneOffset.UTC);
            print(utcTime)
        }

        fun startOfTheDay(inDate: Date): Int {
            val instant: Instant = inDate.toInstant()

            //Converting the Date to LocalDate

            //Converting the Date to LocalDate
            val localDate: LocalDate = instant.atZone(ZoneOffset.UTC).toLocalDate()
            println("Local Date is: $localDate")


            val test1 = localDate.atStartOfDay(ZoneOffset.UTC)
            val test2 = localDate.atStartOfDay()

            return  6000909
        }

    }
}

/*
public class TimeExample {

    public static void main(String[] args) {

        //  LocalDateTime to Timestamp
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);

        System.out.println(now);            // 2019-06-14T15:50:36.068076300
        System.out.println(timestamp);      // 2019-06-14 15:50:36.0680763

        //  Timestamp to LocalDateTime
        LocalDateTime localDateTime = timestamp.toLocalDateTime();

        System.out.println(localDateTime);  // 2019-06-14T15:50:36.068076300

    }
}

public class TimeExample2 {

    public static void main(String[] args) {

        //  LocalDate to Timestamp
        LocalDate now = LocalDate.now();
        Timestamp timestamp = Timestamp.valueOf(now.atStartOfDay());

        System.out.println(now);        // 2019-06-14
        System.out.println(timestamp);  // 2019-06-14 00:00:00.0

        //  Timestamp to LocalDate
        LocalDate localDate = timestamp.toLocalDateTime().toLocalDate();
        System.out.println(localDate);  // 2019-06-14

    }
}
 */