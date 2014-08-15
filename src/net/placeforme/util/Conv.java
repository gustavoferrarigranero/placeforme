package net.placeforme.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Gustavo on 14/08/2014.
 */
public class Conv {
    public static java.sql.Date stringToSqlDate(String data) throws ParseException {
        return new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(data).getTime());
    }
}
