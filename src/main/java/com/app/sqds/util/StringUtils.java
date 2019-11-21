package com.app.sqds.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 字符串工具
 *
 * @author ccj
 */
public class StringUtils {

    /**
     * 字符串分割函数
     *
     * @param source 原字符串
     * @return
     * @par分隔符am div
     */
    public static String[] split(String source, String div) {
        int arynum = 0, intIdx = 0, intIdex = 0, div_length = div.length();
        if (source.compareTo("") != 0) {
            if (source.indexOf(div) != -1) {
                intIdx = source.indexOf(div);
                for (int intCount = 1; ; intCount++) {
                    if (source.indexOf(div, intIdx + div_length) != -1) {
                        intIdx = source.indexOf(div, intIdx + div_length);
                        arynum = intCount;
                    } else {
                        arynum += 2;
                        break;
                    }
                }
            } else {
                arynum = 1;
            }
        } else {
            arynum = 0;

        }
        intIdx = 0;
        intIdex = 0;
        String[] returnStr = new String[arynum];

        if (source.compareTo("") != 0) {
            if (source.indexOf(div) != -1) {
                intIdx = (int) source.indexOf(div);
                returnStr[0] = (String) source.substring(0, intIdx);
                for (int intCount = 1; ; intCount++) {
                    if (source.indexOf(div, intIdx + div_length) != -1) {
                        intIdex = (int) source.indexOf(div, intIdx + div_length);
                        returnStr[intCount] = (String) source.substring(intIdx + div_length, intIdex);
                        intIdx = (int) source.indexOf(div, intIdx + div_length);
                    } else {
                        returnStr[intCount] = (String) source.substring(intIdx + div_length, source.length());
                        break;
                    }
                }
            } else {
                returnStr[0] = (String) source.substring(0, source.length());
                return returnStr;
            }
        } else {
            return returnStr;
        }
        return returnStr;
    }

    /**
     * 返回不为NULL的String值
     *
     * @param src
     * @return
     */
    public static String getNotNullString(String src) {
        if (src != null) {
            return src;
        } else {
            return "";
        }
    }

    /**
     * 返回不为NULL的Integer值
     *
     * @param i
     * @return
     */
    public static String getNotNullInt(Integer i) {
        if (i != null) {
            return String.valueOf(i.intValue());
        } else {
            return "";
        }
    }

    /**
     * 返回不为NULL的Long值
     *
     * @param l
     * @return
     */
    public static String getNotNullLong(Long l) {
        if (l != null) {
            return String.valueOf(l.longValue());
        } else {
            return "";
        }
    }

    /**
     * 返回不为NULL的float值
     *
     * @param f
     * @return
     */
    public static String getNotNullFloat(Float f) {
        if (f != null) {
            float tempFloat = f.floatValue();
            int tempInt = (int) tempFloat;
            if ((float) tempInt == tempFloat) {
                return String.valueOf(tempInt);
            } else {
                return String.valueOf(f.floatValue());
            }
        } else {
            return "";
        }
    }

    /**
     * 将 BigDecimal类型转为String，如果输入为null，则返回0.00
     *
     * @param bd
     * @return
     */
    public static String getNotNullDecimal(BigDecimal bd) {
        if (bd == null) {
            return "0.00";
        }
        DecimalFormat df2 = new DecimalFormat("0.00");
        String temp2 = df2.format(bd);
        return temp2;
    }

    /**
     * 将BigDecimal类型的数据转换为String，如果输入为null 或者 0，则返回空字符串
     *
     * @param bd 输入值
     * @return
     */
    public static String getNotNullDecimalStr(BigDecimal bd) {
        if (bd == null) {
            return "";
        }
        String t = bd.toString();
        if (t.endsWith(".00")) {
            return bd.intValue() + "";
        } else {
            return t;
        }
    }

    /**
     * 自定义显示格式
     *
     * @param formatStr 显示格式，如：###,###,###.00
     * @param bd        输入值
     * @return
     */
    public static String getNotNullDecimalStr(String formatStr, BigDecimal bd) {
        if (bd == null) {
            return "";
        }
        try {
            DecimalFormat decimalFormat = new DecimalFormat(formatStr);
            return decimalFormat.format(bd);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 判断是否为非空
     *
     * @return
     */
    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    /**
     * 判断是否为空
     *
     * @return
     */
    public static boolean isEmpty(String string) {
        if (string == null || "".equals(string)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNumeric(String str) {
        if(str == null || str.equals("")) {
            return false;
        } else {
            int sz = str.length();

            for(int i = 0; i < sz; ++i) {
                if(!Character.isDigit(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }
}
