package com.mt.common.core.support.validator;

import com.google.common.collect.Maps;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberToCarrierMapper;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder;

import java.util.Locale;
import java.util.Map;

/**
 * Created by lrd on 2017/9/11.
 */
public final class LibphonenumberUsage {

    private static final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    private static PhoneNumberToCarrierMapper carrier = PhoneNumberToCarrierMapper.getInstance();

    private static PhoneNumberOfflineGeocoder geocoder = PhoneNumberOfflineGeocoder.getInstance();

    private static final String DEFAULT_COUNTRY = "CN";

    private static final String[] phoneCases = new String[] {
            "008618618888515",  //中国
            "00886988887718",   //台湾
            "006589999994",     //新加坡
            "15910000718",      //中国
            "008209999704546",  //Korea
            "17098888155"       //中国170
    };

    private static final String[] countryCodes = new String[]{

            "886",      //台湾
            "65",       //新加坡
            "86",       //中国
            "82",       //Korea
            "86"        //中国170
    };
    private static final String[] phones = new String[]{
            "9728888718",        //台湾
            "82888894",         //新加坡
            "15919999718",      //中国
            "1074999946",       //Korea
            "17098888155"       //中国170
    };

    public static final Map<String, String> CHINESE_CARRIER_MAPPER = Maps.newHashMap();
    static {
        CHINESE_CARRIER_MAPPER.put("China Mobile", "中国移动");
        CHINESE_CARRIER_MAPPER.put("China Unicom", "中国联通");
        CHINESE_CARRIER_MAPPER.put("China Telecom", "中国电信");
    }

    public static void main(String[] args) {
     //   parsePhone();

        validPhone();

  //     phoneCarrierCase();

       // phoneGeoCase();
    }

    /**
     * 电话解析case
     */
    public static final void parsePhone() {


        for(String phone : phoneCases) {
            Phonenumber.PhoneNumber pn = doParse(phone);
            System.out.println(phone + " --> " + pn.getCountryCode() + ", " + pn.getNationalNumber());
        }
    }

    /**
     * 解析逻辑
     */
    public static final Phonenumber.PhoneNumber doParse(String phone) {
        try {
            return phoneNumberUtil.parse(phone, DEFAULT_COUNTRY);
        } catch (NumberParseException e) {
            throw new NumberFormatException("invalid phone number: " + phone);
        }
    }

    /**
     * 电话解析case
     */
    public static final void validPhone() {
        for (int i = 0; i < countryCodes.length; i++) {
            boolean valid = doValid(countryCodes[i], phones[i]);
            System.out.println(countryCodes[i] + " , " + phones[i] + " --> " + valid);
        }
    }

    /**
     * 手机校验逻辑
     */
    public static final boolean doValid(String countryCode, String phoneNumber){
        //暂时不验证手机号有效性
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) return false;
        return true;
//        int ccode = Integer.parseInt(countryCode);
//        long phone = Long.parseLong(phoneNumber);
//
//        Phonenumber.PhoneNumber pn = new Phonenumber.PhoneNumber();
//        pn.setCountryCode(ccode);
//        pn.setNationalNumber(phone);
//
//        return phoneNumberUtil.isValidNumber(pn);
    }

    public static final void phoneCarrierCase() {
        for (int i = 0; i < countryCodes.length; i++) {
            String carrier = doCarrier(countryCodes[i], phones[i]);
            System.out.println(countryCodes[i] + " , " + phones[i] + " --> " + (CHINESE_CARRIER_MAPPER.containsKey(carrier)?CHINESE_CARRIER_MAPPER.get(carrier):carrier));
        }
    }

    /**
     * 手机运营商
     */
    public static final String doCarrier(String countryCode, String phoneNumber){
        int ccode = Integer.parseInt(countryCode);
        long phone = Long.parseLong(phoneNumber);

        Phonenumber.PhoneNumber pn = new Phonenumber.PhoneNumber();
        pn.setCountryCode(ccode);
        pn.setNationalNumber(phone);

        //返回结果只有英文，自己转成成中文
        return carrier.getNameForNumber(pn, Locale.ENGLISH);
    }

    public static final void phoneGeoCase() {
        for (int i = 0; i < countryCodes.length; i++) {
            String geo = doGeo(countryCodes[i], phones[i]);
            System.out.println(countryCodes[i] + " , " + phones[i] + " --> " + geo);
        }
    }

    /**
     * 手机归属地
     */
    public static final String doGeo(String countryCode, String phoneNumber){
        int ccode = Integer.parseInt(countryCode);
        long phone = Long.parseLong(phoneNumber);

        Phonenumber.PhoneNumber pn = new Phonenumber.PhoneNumber();
        pn.setCountryCode(ccode);
        pn.setNationalNumber(phone);

        return geocoder.getDescriptionForNumber(pn, Locale.CHINESE);
    }

}
