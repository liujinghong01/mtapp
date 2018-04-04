package com.mt.common.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:Wujie
 * @Date: Create in 2018-03-22 18:45
 * @Description:
 * @Modified By:
 */
public class ValidatorUtil {

    /**
     * hibernate validator 打印错误信息
     * @param result
     * @return
     */
    public static  Map<String,Object> getErrors(BindingResult result){
        Map<String,Object> map = new HashMap<>();
        List<FieldError> list = result.getFieldErrors();
        for (FieldError error : list){
            map.put(error.getField(),error.getDefaultMessage());
        }
       return map;
    }
}
