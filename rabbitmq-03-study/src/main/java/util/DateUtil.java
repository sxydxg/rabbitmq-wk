package util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类说明
 *
 * @author dingxigui
 * @date 2019/12/18
 */
public class DateUtil {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd:HH:mm");

    public static String toDay(){
        return simpleDateFormat.format(new Date());
    }

    public static void main(String[] args) {
        System.out.println(toDay());
    }
}
