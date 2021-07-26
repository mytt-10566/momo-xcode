package com.momo.xcode.action;

import java.util.concurrent.Callable;

public class CallAction implements Callable {

    @Override
    public Object call() throws Exception {
        return "hello World";
    }

}
