package com.momo.xcode.execute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;

/**
 * 类执行处理器
 *
 * @author moqinggen
 * @date 2021/07/26
 */
public class ClassExecuteHandler {

    /**
     * 执行实现了Callable接口的方法
     *
     * @param clazz 字节码，要求类实现了Callable接口
     * @return 执行结果
     */
    public static Object execCall(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }

        try {
            // 执行方法
            Object instance = clazz.newInstance();
            if (!(instance instanceof Callable)) {
                throw new RuntimeException("仅支持Callable接口");
            }

            // 执行
            return ((Callable) instance).call();
        } catch (Exception e) {
            return "exec callable error, msg=" + e.getMessage();
        }
    }

    /**
     * 执行实现了BiFunction接口的方法
     *
     * @param clazz 字节码，要求类实现了BiFunction接口
     * @return 执行结果
     */
    public static Object execApply(Class<?> clazz, HttpServletRequest request, HttpServletResponse response) {
        if (clazz == null) {
            return null;
        }

        try {
            // 执行方法
            Object instance = clazz.newInstance();
            if (!(instance instanceof BiFunction)) {
                throw new RuntimeException("仅支持BiFunction接口");
            }

            // 执行
            BiFunction<HttpServletRequest, HttpServletResponse, Object> biFunctionInstance = (BiFunction) instance;
            return biFunctionInstance.apply(request, response);
        } catch (Exception e) {
            return "exec callable error, msg=" + e.getMessage();
        }
    }
}
