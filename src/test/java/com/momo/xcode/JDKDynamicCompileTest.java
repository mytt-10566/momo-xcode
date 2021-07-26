package com.momo.xcode;

import com.momo.xcode.compile.jdk.JDKDynamicCompiler;
import org.junit.Test;

public class JDKDynamicCompileTest {

    @Test
    public void testJdkDynamicCompile() throws Throwable {
        String code = "package com.momo.xcode.action;\n" +
                "\n" +
                "import java.util.concurrent.Callable;\n" +
                "\n" +
                "public class CallAction implements Callable {\n" +
                "\n" +
                "    @Override\n" +
                "    public Object call() throws Exception {\n" +
                "        return \"hello World\";\n" +
                "    }\n" +
                "\n" +
                "}\n";

        Object result = JDKDynamicCompiler.compile(code);
        System.out.println(result);
    }

    @Test
    public void testJdkDynamicCompileForBiFunction() throws Throwable {
        String code = "package com.momo.xcode.action;\n" +
                "\n" +
                "import javax.servlet.http.HttpServletRequest;\n" +
                "import javax.servlet.http.HttpServletResponse;\n" +
                "import java.util.function.BiFunction;\n" +
                "\n" +
                "public class ApplyAction implements BiFunction<HttpServletRequest, HttpServletResponse, Object> {\n" +
                "\n" +
                "    @Override\n" +
                "    public Object apply(HttpServletRequest request, HttpServletResponse response) {\n" +
                "        return \"import or export success\";\n" +
                "    }\n" +
                "}";

        Class<?> clazz = JDKDynamicCompiler.compile(code);
        System.out.println(clazz);
    }
}
