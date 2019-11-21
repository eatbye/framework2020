package com.app.sqds.util;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * request.getParameter封装
 *
 * @author ccj
 */
public class ParamUtils {
    private ParamUtils() {
    }

    /**
     * 取得表单提交的数据，返回字符类型
     *
     * @param request
     * @param s             表单域名称
     * @param defaultString 默认值
     * @return
     * @throws Exception
     */
    public static String getString(ServletRequest request, String s, String defaultString) {
        String s1 = getStringValues(request, s);
        if (s1 == null || "".equals(s1)) {
            return defaultString;
        } else {
            return s1.trim();
        }
    }

    /**
     * 取得表单提交的数据，返回数值型
     *
     * @param request
     * @param s
     * @param defaultInt
     * @return
     */
    public static int getInt(ServletRequest request, String s, int defaultInt) {
        int j = 0;
        try {
            String temp = getString(request, s);
            if (temp == null) {
                j = defaultInt;
            } else if (temp.trim().equals("")) {
                j = defaultInt;
            } else {
                j = Integer.parseInt(temp);
            }
        } catch (NumberFormatException e) {
            j = 0;
        }
        return j;
    }


    /**
     * 取得表单提交的数据，返回数据封装类型
     *
     * @param request
     * @param s
     * @param defaultInt
     * @return
     */
    public static Integer getInteger(ServletRequest request, String s, int defaultInt) {
        int v = ParamUtils.getInt(request, s, defaultInt);
        if (v == defaultInt) {
            return null;
        } else {
            return new Integer(v);
        }
    }


    /**
     * 取得表单提交的数据，返回long类型
     *
     * @param request
     * @param s
     * @param defaultLong
     * @return
     */
    public static long getLong(ServletRequest request, String s, long defaultLong) {
        long l = 0;
        try {
            String temp = getString(request, s);
            if (temp == null) {
                l = defaultLong;
            } else {
                l = Long.parseLong(temp);
            }
        } catch (NumberFormatException e) {
            l = 0;
        }
        return l;
    }


    /**
     * 取得表单提交的数据，返回float类型
     *
     * @param request
     * @param s
     * @param defaultFloat
     * @return
     */
    public static float getFloat(HttpServletRequest request, String s, float defaultFloat) {
        float l = 0;
        try {
            String temp = getString(request, s);
            if (temp == null) {
                l = defaultFloat;
            } else {
                l = Float.parseFloat(temp);
            }
        } catch (NumberFormatException e) {
            l = 0;
        }
        return l;
    }

    /**
     * 取得表单提交的数据，返回double类型
     *
     * @param request
     * @param s
     * @param defaultDouble
     * @return
     */
    public static double getDouble(HttpServletRequest request, String s, double defaultDouble) {
        double l = 0;
        try {
            String temp = getString(request, s);
            if (temp == null) {
                l = defaultDouble;
            } else {
                l = Double.parseDouble(temp);
            }
        } catch (NumberFormatException e) {
            l = 0;
        }
        return l;
    }


    /**
     * 取得表单提交的数据，返回Decimal类型
     *
     * @param request
     * @param s
     * @param defaultDouble
     * @return
     */
    public static BigDecimal getDecimal(HttpServletRequest request, String s, double defaultDouble) {
        BigDecimal l = null;
        try {
            String temp = getString(request, s);
            if (temp == null) {
                //				l = defaultDouble;
                l = null;
            } else {
                l = new BigDecimal(temp);
            }
        } catch (NumberFormatException e) {
            //			l = 0;
        }
        return l;
    }


    /**
     * 取得字符数组，通常用于checkbox取值
     *
     * @param request
     * @param name
     * @return
     */
    public static String[] getStringParameters(ServletRequest request, String name) {
        String[] array = request.getParameterValues(name);
        if (array == null) {
            return new String[0];
        }
        return array;
    }

    /**
     * 取得数值数组，通常用于checkbox取值
     *
     * @param name
     * @return
     */
    public static int[] getIntParameters(ServletRequest request, String name) {
        String[] array = ParamUtils.getStringParameters(request, name);
        int[] intArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            intArray[i] = Integer.parseInt(array[i]);
        }
        return intArray;
    }

    private static String getString(ServletRequest request, String s) {
        String temp = null;
        try {
            temp = request.getParameter(s);
        } catch (Exception exception) {
            System.out.println(exception);
        }
        return temp;
    }

    private static String getStringValues(ServletRequest request, String s) {
        String[] temp = null;
        try {
            temp = request.getParameterValues(s);
        } catch (Exception exception) {
            System.out.println(exception);
        }
        String s1 = "";
        for (int i = 0; temp != null && i < temp.length; i++) {
            s1 = s1 + temp[i] + ",";
        }
        if (s1.endsWith(",")) {
            s1 = s1.substring(0, s1.length() - 1);
        }
        return s1;
    }
}
