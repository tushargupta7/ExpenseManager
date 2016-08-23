package Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tushar on 19/8/16.
 */

public class Utils {
    public static String getFormattedDate(long timestamp,String option) {
        DateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date netDate = (new Date(timestamp));
        String dateTime= df.format(netDate);
        if(option.equalsIgnoreCase("date")){
            return dateTime.split(" ")[0];
        }
        else return dateTime.split(" ")[1];
    }
}
