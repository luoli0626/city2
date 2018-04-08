package com.wan.sys.util;

import com.wan.sys.pojo.ErrorCodeEnum;
import com.wan.sys.pojo.ResponseHead;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

/**
 * @author J
 * 参数验证相关工具方法类
 */
public class ValidUtil {

    public static ResponseHead errorResponse(BindingResult result) {
        ResponseHead r = new ResponseHead();
        List<FieldError> errors = result.getFieldErrors();
        List<String> es = new ArrayList<String>();
        for (FieldError error : errors) {
            es.add(error.getField() + error.getDefaultMessage());
        }
        r.setErrmsg(StringUtils.join(es, ","));
        r.setErrcode(ErrorCodeEnum.FAIL_INVALIDPARAM.getCode());
        return r;
    }
}
