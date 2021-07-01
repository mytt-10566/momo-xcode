# momo-xcode

## 一、介绍
momo-xode 是一个可以在运行环境动态执行java脚本代码的一个依赖jar包，底层原理是java动态编译技术（后续增加javassist实现）。

通过servlet传入java code即可运行，避免了重复打包、编译、部署。

特点：
- 测试、错误排查
- 数据查询、修改

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
### 2.2编写执行java脚本

注：
- 类要实现Callable接口
- 返回的结果可以通过JSON序列化（接口最后会将执行后的结果序列化返回）

```java
package com.momo.xcode.action;

import java.util.concurrent.Callable;

public class HelloWorldAction implements Callable<Object> {

    @Override
    public String call() throws Exception {
        return "Hello world";
    }
}
```

### 2.3 HTTP方式提交脚本并执行

添加`momo-xcode`依赖后，可以通过发起HTTP请求：
- `POST /inner/xcode/call`
  - body：x-www-form-urlencoded

- 参数说明：
  - code：java代码，需要实现Callable接口，示例见2.2
  
- 请求
  - 通过PostMan等工具发起post请求即可  


## 三、扩展

### 3.1 自定义Controller

在引入`momo-xcode`到自己项目后，可以通过自定义Controller，调用动态编译的相关代码，使其符合自己要求。

## 四、后续规划

- 1.支持导入、导出
- 2.支持脚本缓存，避免重复编辑加载
- 3.增加使用javassist实现动态编译
- ...