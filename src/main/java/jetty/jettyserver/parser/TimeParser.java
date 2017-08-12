package jetty.jettyserver.parser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

/**
 * Created by ganku on 8/13/17.
 */
public class TimeParser {

    //testCase
    public static void main(String[] args) throws ParseException {
        long v=getTimeStamp("07/04/2000,12:45:08");
        System.out.println(v);
        Date d=new Date(1491549308000l);
        System.out.println(d);
    }

    public static long getTimeStamp(String dateString) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy','HH:mm:ss");
        Date date = dateFormat.parse(dateString);
        return date.getTime();
    }
}
