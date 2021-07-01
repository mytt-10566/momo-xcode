package com.momo.xcode.action;

import java.util.concurrent.Callable;

public class HelloWorldAction implements Callable {

    @Override
    public Object call() throws Exception {
        return "hello World";
    }

}
