package com.fudian.dahc.util.common;

import com.fudian.common.utils.StringUtils;
import com.fudian.dahc.util.mybatispuls.RequestDataHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用工具类
 * 2023/3/16
 */
public class CurrencyUtil {


    /**
     * 求百分比
     * @param number1
     * @param number2
     * @param formatNumber
     * @return
     */
    public static Double getPercentage(Integer number1, Integer number2, int formatNumber) {
        double number100;
        if (number1 != 0 && number2 != 0) {
//            DecimalFormat df = new DecimalFormat("0.##");
            //求%
            double numberA = (double) number1 / number2 * 100;
            //四舍五入保留2位小数
            number100 = new BigDecimal(numberA).setScale(formatNumber, BigDecimal.ROUND_HALF_UP).doubleValue();
            return number100;
        } else {
            number100 = 0.0;
            return number100;
        }
    }

    /**
     * 比较两个集合的相似度
     */
    public static Map<String, String> Intersection(Map<String, String> mp1, Map<String, String> mp2) {
        List<String> strRemove = new ArrayList<>();
        Map<String, String> res = new HashMap<>(mp1);
        for (String s : res.keySet()) {
            //mp1中存在但mp2中不存在
            if (!mp2.containsKey(s)) {
                strRemove.add(s);
            } else {  //mp1中存在mp2中存在
                if (mp1.get(s).equals(mp2.get(s))) {
                    res.put(s, mp2.get(s));
                } else {
                    strRemove.add(s);
                }
            }
        }
        for (String s : strRemove) {
            res.remove(s);
        }
        return res;
    }

    /**
     * list转String
     * @param list
     * @param separator
     * @return
     */
    public static String listToString(List list, char separator) {
        return StringUtils.join(list.toArray(), separator);
    }

    /**
     * 替换数据库表头
     */
    public static void dynamicSelectionTableByTableName(String tableName) {
        RequestDataHelper.setRequestData(new HashMap<String, Object>() {{
            put("dahc_file", tableName);
        }});
    }
}
