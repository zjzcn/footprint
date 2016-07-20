package com.footprint.server.common.exception;

public class ExceptionUtils {

    public static void throwRuntime(String msg) {
    	throw new RuntimeException(msg);
    }
    
    public static void throwRuntime(String msg, Exception e) {
    	throw new RuntimeException(msg, e);
    }
    
    public static void throwIllegalArgument(String msg) {
    	throw new IllegalArgumentException(msg);
    }
    
    public static void throwIllegalArgument(String msg, Exception e) {
    	throw new IllegalArgumentException(msg, e);
    }
    
    public static void throwBiz(String msg) {
    	throw new BizException(msg);
    }
    
    public static void throwBiz(String msg, Exception e) {
    	throw new BizException(msg, e);
    }

}
