package com.momo.xcode.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.function.BiFunction;

public class ApplyAction implements BiFunction<HttpServletRequest, HttpServletResponse, Object> {

    @Override
    public Object apply(HttpServletRequest request, HttpServletResponse response) {
        return "import or export success";
    }
}
