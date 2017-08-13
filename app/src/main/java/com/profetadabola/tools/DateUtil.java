package com.profetadabola.tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static String formatDateGame(String date){
        String dateFinal = "";
        Date currentDate = null;
        try {
            //Current
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", new Locale("pt","BR"));
            currentDate = dateFormat.parse(date);

            //Format
            DateFormat formatFinal = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
            dateFinal = formatFinal.format(currentDate);


        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }


        return dateFinal;
    }
}
