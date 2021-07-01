package com.momo.xcode.util;

import com.momo.xcode.common.XCodeConstant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 代码工具类
 *
 * @author moqinggen
 * @date 2021/07/01
 */
public class CodeUtil {

    /**
     * 获取包名
     *
     * @param code java代码
     * @return 包名，如：com.momo.xcode.util
     */
    public static String getPackage(String code) {
        if (null == code || code.isEmpty()) {
            return XCodeConstant.BLANK;
        }

        String regex = "(?im)^\\s*package\\s+([^;]+);";
        Matcher m = Pattern.compile(regex).matcher(code);
        if (m.find()) {
            return m.group(1).trim();
        }
        return XCodeConstant.BLANK;
    }

    /**
     * 获取public类名
     *
     * @param code java 代码
     * @return 类名，如：CodeUtil
     */
    public static String getPublicClassName(String code) {
        if (null == code || code.isEmpty()) {
            return XCodeConstant.BLANK;
        }

        String regex = "(?m)^\\s*public\\s+class\\s+(\\w+)\\b";
        Matcher m = Pattern.compile(regex).matcher(code);
        if (m.find()) {
            return m.group(1).trim();
        }
        return XCodeConstant.BLANK;
    }

    /**
     * 获取public类完全限定类名
     *
     * @param code java 代码
     * @return 完全限定类名，如：com.momo.xcode.util
     */
    public static String getPublicFullClassName(String code) {
        String packageName = getPackage(code);
        String className = getPublicClassName(code);
        return packageName.isEmpty() ? className : packageName + "." + className;
    }

    /**
     * 获取public类名信息
     *
     * @param code java 代码
     * @return 类名的信息，包名、简单类名、完全限定类名
     */
    public static String[] getClassNameInfos(String code) {
        // 简单类名
        String className = getPublicClassName(code);
        if (className.isEmpty()) {
            return null;
        }
        // 包名
        String packageName = getPackage(code);
        String fullClassName = packageName.isEmpty() ? className : packageName + "." + className;
        return new String[]{packageName, className, fullClassName};
    }

    /**
     * fix code
     *
     * @param code
     * @return
     */
    public static String fixCode(String code) {
        return code.replaceAll("//[\\s\\S]*?\\n", "");
    }
}
