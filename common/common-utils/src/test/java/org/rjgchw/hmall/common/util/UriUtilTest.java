package org.rjgchw.hmall.common.util;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Huangw
 * @date 2020-01-02 17:03
 */
class UriUtilTest {

    @Test
    public void should_throw_uri_syntax_invalid_exception_when_input_is_invalid() {
        assertThrows(UriSyntaxInvalidException.class, () -> {
            UriUtil.createUri("//");
        });
    }

    @Test
    public void should_return_uri_when_input_is_valid() {
        String uriStr = "/admin/test";
        URI uri = UriUtil.createUri(uriStr);
        assertEquals(uriStr, uri.getPath());
    }
}
