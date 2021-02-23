package org.rjgchw.hmall.common.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URI;
import org.junit.jupiter.api.Test;

/**
 * @author Huangw
 * @date 2020-01-02 17:03
 */
class UriUtilTest {

    @Test
    public void should_throw_uri_syntax_invalid_exception_if_invalid_input() {
        assertThrows(UriUtil.UriSyntaxInvalidException.class, () -> {
            UriUtil.createUri("//");
        });
    }

    @Test
    public void should_return_uri_if_valid_input() {
        String uriStr = "/admin/test";
        URI uri = UriUtil.createUri(uriStr);
        assertThat(uriStr).isEqualTo(uri.getPath());
    }
}
