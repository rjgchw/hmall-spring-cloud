package org.rjgchw.hmall.common.web.rest.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 错误码转换器，将校验出错的错误码转换为可读性更强的错误码
 * @author huangwei
 */
public final class ErrorCodeConverter {

    private static final Logger log = LoggerFactory.getLogger(ErrorCodeConverter.class);

    private static final Map<String, UnprocessableRequestErrorCode> ERROR_CODE_MAP = new HashMap<>(32);

    static {
        ERROR_CODE_MAP.put("NotBlank".toUpperCase(), UnprocessableRequestErrorCode.MISSING_FIELD);
        ERROR_CODE_MAP.put("NotEmpty".toUpperCase(), UnprocessableRequestErrorCode.MISSING_FIELD);
        ERROR_CODE_MAP.put("NotNull".toUpperCase(), UnprocessableRequestErrorCode.MISSING_FIELD);
        ERROR_CODE_MAP.put("Size".toUpperCase(), UnprocessableRequestErrorCode.INVALID);
        ERROR_CODE_MAP.put("Pattern".toUpperCase(), UnprocessableRequestErrorCode.INVALID);
        ERROR_CODE_MAP.put("Email".toUpperCase(), UnprocessableRequestErrorCode.INVALID);
    }

    private ErrorCodeConverter() {}

    public static UnprocessableRequestErrorCode getErrorCode(String code) {
        if (code == null || "".equals(code)) {
            log.error("Unknown Unprocessable request error code: {}", code);
            throw new UnknownErrorCodeException();
        }
        UnprocessableRequestErrorCode result = ERROR_CODE_MAP.get(code.toUpperCase());
        if (result == null) {
            log.error("Unknown Unprocessable request error code: {}", code);
            throw new UnknownErrorCodeException();
        }
        return result;
    }
}
