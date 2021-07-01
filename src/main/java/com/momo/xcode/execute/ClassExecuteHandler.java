package com.momo.xcode.execute;

import java.util.concurrent.Callable;

/**
 * 类执行处理器
 *
 * @author moqinggen
 * @date
 */
public class ClassExecuteHandler {

    /**
     * 执行实现了Callable接口的方法
     *
     * @param clazz 字节码，要求类实现了Callable接口
     * @return 执行结果
     */
    public static Object execCallable(Class<?> clazz) {
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
}
