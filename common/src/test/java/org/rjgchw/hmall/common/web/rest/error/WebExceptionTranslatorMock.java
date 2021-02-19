package org.rjgchw.hmall.common.web.rest.error;

import org.rjgchw.hmall.common.web.rest.error.translator.AbstractWebExceptionTranslator;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author Huangw
 * @date 2019-12-03 22:31
 */
@ControllerAdvice
public class WebExceptionTranslatorMock extends AbstractWebExceptionTranslator {

    public WebExceptionTranslatorMock(Environment env) {
        super(env);
    }

    @Override
    protected boolean containsPackageName(String message) {
        return false;
    }

    @Override
    protected String getApplicationName() {
        return "mock";
    }
}
