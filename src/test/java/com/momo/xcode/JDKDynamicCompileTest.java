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
                "public class HelloWorldAction implements Callable {\n" +
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
}
