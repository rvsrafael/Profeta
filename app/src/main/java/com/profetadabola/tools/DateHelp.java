package com.profetadabola.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rafa on 30/07/17.
 */

public class DateHelp {

    public static Date formatDateDefautl(String dtStart) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = null;
        try {
            date = format.parse(dtStart);
        } catch (Exception e){

        }
        return date;

    }

}
