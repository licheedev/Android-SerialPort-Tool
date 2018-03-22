package com.licheedev.serialtool.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lzy on 2017/4/19 0019.
 */

public class TimeUtil {

    public static final SimpleDateFormat DEFAULT_FORMAT =
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static String currentTime() {
        Date date = new Date();
        return DEFAULT_FORMAT.format(date);
    }
}
