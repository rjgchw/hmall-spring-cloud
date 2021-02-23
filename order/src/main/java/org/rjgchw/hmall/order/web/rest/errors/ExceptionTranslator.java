package org.rjgchw.hmall.order.web.rest.errors;

import org.apache.commons.lang3.StringUtils;
import org.rjgchw.hmall.common.web.rest.error.translator.AbstractWebExceptionTranslator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 *
 * @author Huangw
 * @date 2021-02-23 17:00
 */
@ControllerAdvice
public class ExceptionTranslator extends AbstractWebExceptionTranslator {

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public ExceptionTranslator(Environment env) {
        super(env);
    }

    @Override
    protected boolean containsPackageName(String message) {
        return StringUtils.containsAny(message, "org.", "java.", "net.", "javax.", "com.", "io.", "de.", "org.rjgchw.hmall.order");
    }

    @Override
    protected String getApplicationName() {
        return applicationName;
    }
}
