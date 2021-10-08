package org.branux.weather.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateConverter {

    private static final String DATE_FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd";

    public Date getDateFromString(String dateStr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_YEAR_MONTH_DAY);
        return dateFormat.parse(dateStr);
    }

    public Date getFixedDate(Date date) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        Date dt = c.getTime();
        return dt;
    }
}
