package com.mt.common.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @Author:Wujie
 * @Date: Create in 2018-03-13 15:24
 * @Description:
 * @Modified By:
 */
public class JacksonController extends ObjectMapper {

    private boolean camelCaseToLowerCaseWithUnderscores = false;
    private String dateFormatPattern;

    public void setCamelCaseToLowerCaseWithUnderscores(boolean camelCaseToLowerCaseWithUnderscores){
        this.camelCaseToLowerCaseWithUnderscores = camelCaseToLowerCaseWithUnderscores;
    }

    public void setDateFormatPattern(String dateFormatPattern){
        this.dateFormatPattern = dateFormatPattern;
    }

    public void init(){
        //为空时不显示
        //setSerializationInclusion(JsonInclude.Include.NON_NULL);
        configure(SerializationFeature.INDENT_OUTPUT,true);
        if(camelCaseToLowerCaseWithUnderscores){
            setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        }
        if(StringUtils.isNotEmpty(dateFormatPattern)){
            DateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);
            setDateFormat(dateFormat);
        }
    }
}
