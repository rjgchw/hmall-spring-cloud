package org.rjgchw.hmall.common.util;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Huangw
 * @date 2020-01-02 17:00
 */
public class UriUtil {

    private UriUtil() {}

    public static URI createUri(String str) {
        try {
            return new URI(str);
        } catch (URISyntaxException e) {
            throw new UriSyntaxInvalidException();
        }
    }
}
