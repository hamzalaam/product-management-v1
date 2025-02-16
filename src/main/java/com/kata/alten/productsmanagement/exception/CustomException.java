package com.kata.alten.productsmanagement.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

/**
 * generic Exception
 */
public class CustomException extends RuntimeException{
    private static final Logger logger = LoggerFactory.getLogger(CustomException.class);
    /**
     * enum for exception
     */
   final private ExceptionEnum exceptionEnum;

    /**
     * product excepttion
     * @param className class name
     * @param exceptionEnum exception enum
     */
    public CustomException(Class<?> className, ExceptionEnum exceptionEnum) {
        super(MessageFormatter.arrayFormat(exceptionEnum.name(), null).getMessage());
        this.exceptionEnum=exceptionEnum;
        logger.error("CustomException: ExceptionEnum: {}, className: {}, methodName: {}", exceptionEnum, className.getSimpleName(), getMethodName());
    }

    /**
     * get method name where exception occured
     * @return method name
     */
    public String getMethodName() {
        return StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
                .walk(stream -> stream.skip(2).findFirst().get())
                .getMethodName();
    }

    /**
     * retrieve exception enum
     * @return
     */
    public ExceptionEnum getExceptionEnum() {
        return exceptionEnum;
    }
}
