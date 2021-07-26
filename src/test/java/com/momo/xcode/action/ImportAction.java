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
 * 导入示例
 *
 * @author moqinggen
 * @date 2021/07/26
 */
public class ImportAction implements BiFunction<HttpServletRequest, HttpServletResponse, Object> {

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
