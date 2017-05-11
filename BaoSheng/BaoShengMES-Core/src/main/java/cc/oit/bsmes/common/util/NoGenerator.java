package cc.oit.bsmes.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by 羽霓 on 2014/4/23.
 */
public class NoGenerator {

    private static Random random = new Random();

    public static String generateNoByDate() {
        DateFormat df = new SimpleDateFormat(DateUtils.DATE_TIMESTAMP_LONG_FORMAT);
        return df.format(new Date()) + random.nextInt(1000);
    }

}
