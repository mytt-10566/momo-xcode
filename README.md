# momo-xcode

## 一、介绍
momo-xcode 是一个可以在运行环境动态执行java脚本代码的一个依赖jar包，底层原理是java动态编译技术（后续增加javassist实现）。

通过servlet传入java code即可运行，避免了重复打包、编译、部署。

特点：
- 测试、错误排查
- 数据查询、修改
- 导入、导出

## 二、使用说明

### 2.1添加项目依赖

项目打包后，可以添加至本地maven仓库，然后在项目中引用：
```
<dependency>
    <groupId>com.momo</groupId>
    <artifactId>momo-xcode</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 2.2 HTTP方式提交脚本并执行

注：
- SpringBoot项目添加：`@ServletComponentScan(basePackages = "com.momo.xcode")`，扫描`momo-xcode`jar包下的Servlet接口

#### 2.2.1 执行实现Callable接口的代码

添加`momo-xcode`依赖后，可以通过发起HTTP请求：
- `POST /inner/xcode/call`
  - ContentType：application/x-www-form-urlencoded

- 参数说明：
  - code：java代码，需要实现Callable接口
  
- 请求
  - 通过PostMan等工具发起post请求即可  

- 代码示例：
  - 类要实现Callable接口
  - 返回的结果可以通过JSON序列化（接口最后会将执行后的结果序列化返回）
  - 在Spring项目中，可以获取Spring Bean来处理一些数据查询等操作

```java
package com.momo.xcode.action;

import java.util.concurrent.Callable;

public class CallAction implements Callable<Object> {

    @Override
    public String call() throws Exception {
        return "Hello world";
    }
}
```

#### 2.2.2 执行实现BiFunction接口的代码

说明：
- 该接口主要是通过暴露`HttpServletRequest`、`HttpServletResponse`做一些处理，例如读取文件、导出文件等
- 不过也需要注意，权限比较大，可以玩出各种花样


通过发起HTTP请求：
- `POST /inner/xcode/apply`
  - ContentType：application/x-www-form-urlencoded、multipart/form-data

- 参数说明：
  - code：java代码，需要实现BiFunction接口
  - file：可选，上传文件时可以使用该参数
    - ContentType必须为multipart/form-data
    - 文件大小限制：10MB
    - 使用`Part request.getPart("file")`获取上传的文件，这也取决于自己，想取啥参数名就取啥，反正都是自己解析~~
  
- 请求
  - 通过PostMan等工具发起post请求即可  

- 代码示例：
  - 类要实现BiFunction接口
  - 返回的结果可以通过JSON序列化（接口最后会将执行后的结果序列化返回）

```java
package com.momo.xcode.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.function.BiFunction;

/**
 * 导入导出
 *
 * @author moqinggen
 * @date 2021/07/26
 */
public class ApplyTestAction implements BiFunction<HttpServletRequest, HttpServletResponse, Object> {

    @Override
    public Object apply(HttpServletRequest request, HttpServletResponse response) {
        return importDatas(request);
    }

    /**
     * 导入数据
     * <p>设置Content-Type：multipart/form-data，上传文件属性为file
     *
     * @param request
     * @return
     */
    private Object importDatas(HttpServletRequest request) {
        try {
            // 传参时要定义file属性
            Part part = request.getPart("file");

            // 读取文件内容
            return "fileName：" + part.getSubmittedFileName() + " content：" + readFileContent(part.getInputStream());

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private static String readFileContent(InputStream in) {
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sb.append(tempStr).append(" ");
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception ex) {
            }
        }
        return sb.toString();
    }
}
```

## 三、扩展

### 3.1 自定义Controller

在引入`momo-xcode`到自己项目后，可以通过自定义Controller，调用动态编译的相关代码，使其符合自己要求。

## 四、后续规划

- 1.支持脚本缓存，避免重复编辑加载
- 2.增加使用javassist实现动态编译
- ...