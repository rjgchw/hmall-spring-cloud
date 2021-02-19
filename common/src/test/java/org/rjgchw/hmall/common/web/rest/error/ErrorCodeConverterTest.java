package org.rjgchw.hmall.common.web.rest.error;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Huangw
 * @date 2020-01-02 16:20
 */
public class ErrorCodeConverterTest {

    @Test
    public void should_throw_unknown_error_code_exception_if_code_is_null() {
        assertThrows(UnknownErrorCodeException.class, ()->{
            ErrorCodeConverter.getErrorCode(null);
        });
    }

    @Test
    public void should_throw_unknown_error_code_exception_if_code_is_empty() {
        assertThrows(UnknownErrorCodeException.class, ()->{
            ErrorCodeConverter.getErrorCode("");
        });
    }

    @Test
    public void should_throw_unknown_error_code_exception_if_code_is_unknown() {
        assertThrows(UnknownErrorCodeException.class, ()->{
            ErrorCodeConverter.getErrorCode("UNKNOWN");
        });
    }

}
