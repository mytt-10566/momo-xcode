package com.momo.xcode.api;

import com.alibaba.fastjson.JSON;
import com.momo.xcode.compile.jdk.JDKDynamicCompiler;
import com.momo.xcode.execute.ClassExecuteHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 动态编译代码，并执行call方法
 *
 * @author moqinggen
 * @date 2021/07/01
 */
@WebServlet("/inner/xcode/call")
public class CallableServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        // 代码，要求类必须实现Callable接口
        String code = request.getParameter("code");
        PrintWriter out = null;
        try {
            out = response.getWriter();

            Class<?> newClass = JDKDynamicCompiler.compile(code);
            Object result = ClassExecuteHandler.execCallable(newClass);
            out.print(JSON.toJSONString(result));
        } catch (Exception e) {
            out.print(JSON.toJSONString(e));
        } catch (Throwable throwable) {
            out.print(JSON.toJSONString(throwable));
        } finally {
            out.flush();
            out.close();
        }
    }

}
