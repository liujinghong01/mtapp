package com.mt.common.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * String工具
 * 主要对 StringUtils 的一些方法进行重写,达到更方便的使用
 *
 */
public class StringUtils extends org.apache.commons.lang.StringUtils{
	public static final char UNDERLINE='_';
	
	/**
	 * 一次性判断多个或单个对象为空。
	 * @param objects
	 * @author zhou-baicheng
	 * @return 只要有一个元素为Blank，则返回true
	 */
	public static boolean isBlank(Object...objects){
		Boolean result = false ;
		for (Object object : objects) {
			if(null == object || "".equals(object.toString().trim()) 
					|| "null".equals(object.toString().trim())){
				result = true ; 
				break ; 
			}
		}
		return result ; 
	}
	
	public static String getRandom(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			// 输出字母还是数字
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 字符串
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 取得大写字母还是小写字母
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val.toLowerCase();
	}
	/**
	 * 一次性判断多个或单个对象不为空。
	 * @param objects
     * @author zhou-baicheng
	 * @return 只要有一个元素不为Blank，则返回true
	 */
	public static boolean isNotBlank(Object...objects){
		return !isBlank(objects);
	}
	public static boolean isBlank(String...objects){
		Object[] object = objects ;
		return isBlank(object);
	}
	public static boolean isNotBlank(String...objects){
		Object[] object = objects ;
		return !isBlank(object);
	}
	public static boolean isBlank(String str){
		Object object = str ;
		return isBlank(object);
	}
	public static boolean isNotBlank(String str){
		Object object = str ;
		return !isBlank(object);
	}
	/**
	 * 判断一个字符串在数组中存在几个
	 * @param baseStr
	 * @param strings
	 * @return
	 */
	public static int indexOf(String baseStr,String[] strings){
		
		if(null == baseStr || baseStr.length() == 0 || null == strings)
			return 0;
		
		int i = 0;
		for (String string : strings) {
			boolean result = baseStr.equals(string);
			i = result ? ++i : i;
		}
		return i ;
	}
	/**
	 * 判断一个字符串是否为JSONObject,是返回JSONObject,不是返回null
	 * @param args
	 * @return
	 */
	public static JSONObject isJSONObject(String args) {
		JSONObject result = null ;
		if(isBlank(args)){
			return result ;
		}
		try {
			return JSONObject.fromObject(args.trim());
		} catch (Exception e) {
			return result ;
		}
	}
	/**
	 * 判断一个字符串是否为JSONArray,是返回JSONArray,不是返回null
	 * @param args
	 * @return
	 */
	public static JSONArray isJSONArray(Object args) {
		JSONArray result = new JSONArray();
		if(isBlank(args)){
			return null ;
		}
		if(args instanceof  JSONArray){

			JSONArray arr = (JSONArray)args;
			for (Object json : arr) {
				if(json != null && json instanceof JSONObject){
					result.add(json);
					continue;
				}else{
					result.add(JSONObject.fromObject(json));
				}
			}
			return result;
		}else{
			return null ;
		}

	}
	public static String trimToEmpty(Object str){
	  return (isBlank(str) ? "" : str.toString().trim());
	}

	/**
	 * 将 Strig  进行 BASE64 编码
	 * @param str [要编码的字符串]
	 * @param bf  [true|false,true:去掉结尾补充的'=',false:不做处理]
	 * @return
	 */
    public static String getBASE64(String str,boolean...bf) {
       if (StringUtils.isBlank(str)) return null;
       String base64 = new sun.misc.BASE64Encoder().encode(str.getBytes()) ;
       //去掉 '='
       if(isBlank(bf) && bf[0]){
    	   base64 = base64.replaceAll("=", "");
       }
       return base64;
    }

    /** 将 BASE64 编码的字符串 s 进行解码**/
    public static String getStrByBASE64(String s) {
       if (isBlank(s)) return "";
       BASE64Decoder decoder = new BASE64Decoder();
       try {
          byte[] b = decoder.decodeBuffer(s);
          return new String(b);
       } catch (Exception e) {
          return "";
       }
    }
    /**
     * 把Map转换成get请求参数类型,如 {"name"=20,"age"=30} 转换后变成 name=20&age=30
     * @param map
     * @return
     */
    public static String mapToGet(Map<? extends Object,? extends Object> map){
    	String result = "" ;
    	if(map == null || map.size() ==0){
    		return result ;
    	}
    	Set<? extends Object> keys = map.keySet();
    	for (Object key : keys ) {
    		result += ((String)key + "=" + (String)map.get(key) + "&");
		}

    	return isBlank(result) ? result : result.substring(0,result.length() - 1);
    }
    /**
     * 把一串参数字符串,转换成Map 如"?a=3&b=4" 转换为Map{a=3,b=4}
     * @param args
     * @return
     */
    public static Map<String, ? extends Object> getToMap(String args){
    	if(isBlank(args)){
    		return null ;
    	}
    	args = args.trim();
    	//如果是?开头,把?去掉
    	if(args.startsWith("?")){
    		args = args.substring(1,args.length());
    	}
    	String[] argsArray = args.split("&");

    	Map<String,Object> result = new HashMap<String,Object>();
    	for (String ag : argsArray) {
			if(!isBlank(ag) && ag.indexOf("=")>0){

				String[] keyValue = ag.split("=");
				//如果value或者key值里包含 "="号,以第一个"="号为主 ,如  name=0=3  转换后,{"name":"0=3"}, 如果不满足需求,请勿修改,自行解决.

				String key = keyValue[0];
				String value = "" ;
				for (int i = 1; i < keyValue.length; i++) {
					value += keyValue[i]  + "=";
				}
				value = value.length() > 0 ? value.substring(0,value.length()-1) : value ;
				result.put(key,value);

			}
		}

    	return result ;
    }

    /**
	 * 转换成Unicode
	 * @param str
	 * @return
	 */
    public static String toUnicode(String str) {
        String as[] = new String[str.length()];
        String s1 = "";
        for (int i = 0; i < str.length(); i++) {
        	int v = str.charAt(i);
        	if(v >=19968 && v <= 171941){
	            as[i] = Integer.toHexString(str.charAt(i) & 0xffff);
	            s1 = s1 + "\\u" + as[i];
        	}else{
        		 s1 = s1 + str.charAt(i);
        	}
        }
        return s1;
     }
    /**
     * 合并数据
     * @param v
     * @return
     */
    public static String merge(Object...v){
    	StringBuffer sb = new StringBuffer();
    	for (int i = 0; i < v.length; i++) {
    		sb.append(v[i]);
		}
    	return sb.toString() ;
    }
    /**
     * 字符串转urlcode
     * @param value
     * @return
     */
    public static String strToUrlcode(String value){
    	try {
			value = java.net.URLEncoder.encode(value,"utf-8");
			return value ;
		} catch (UnsupportedEncodingException e) {
			LoggerUtils.error(StringUtils.class, "字符串转换为URLCode失败,value:" + value,e);
			e.printStackTrace();
			return null;
		}
    }
    /**
     * urlcode转字符串
     * @param value
     * @return
     */
    public static String urlcodeToStr(String value){
    	try {
			value = java.net.URLDecoder.decode(value,"utf-8");
			return value ;
		} catch (UnsupportedEncodingException e) {
			LoggerUtils.error(StringUtils.class, "URLCode转换为字符串失败;value:" + value,e);
			e.printStackTrace();
			return null;
		}
    }
    /**
     * 判断字符串是否包含汉字
     * @param txt
     * @return
     */
    public static Boolean containsCN(String txt){
    	if(isBlank(txt)){
    		return false;
    	}
    	for (int i = 0; i < txt.length(); i++) {

    		String bb = txt.substring(i, i + 1);

    		boolean cc = Pattern.matches("[\u4E00-\u9FA5]", bb);
    		if(cc)
    		return cc ;
    	}
		return false;
    }
    /**
     * 去掉HTML代码
     * @param news
     * @return
     */
    public static String removeHtml(String news) {
      String s = news.replaceAll("amp;", "").replaceAll("<","<").replaceAll(">", ">");
      
      Pattern pattern = Pattern.compile("<(span)?\\sstyle.*?style>|(span)?\\sstyle=.*?>", Pattern.DOTALL);
      Matcher matcher = pattern.matcher(s);
      String str = matcher.replaceAll("");
      
      Pattern pattern2 = Pattern.compile("(<[^>]+>)",Pattern.DOTALL);
      Matcher matcher2 = pattern2.matcher(str);
      String strhttp = matcher2.replaceAll(" ");
      
      
      String regEx = "(((http|https|ftp)(\\s)*((\\:)|：))(\\s)*(//|//)(\\s)*)?"
         + "([\\sa-zA-Z0-9(\\.|．)(\\s)*\\-]+((\\:)|(:)[\\sa-zA-Z0-9(\\.|．)&%\\$\\-]+)*@(\\s)*)?"
         + "("
         + "(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])"
         + "(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)"
         + "(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)"
         + "(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])"
         + "|([\\sa-zA-Z0-9\\-]+(\\.|．)(\\s)*)*[\\sa-zA-Z0-9\\-]+(\\.|．)(\\s)*[\\sa-zA-Z]*"
         + ")"
         + "((\\s)*(\\:)|(：)(\\s)*[0-9]+)?"
         + "(/(\\s)*[^/][\\sa-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$\\=~_\\-@]*)*";
      Pattern p1 = Pattern.compile(regEx,Pattern.DOTALL);
      Matcher matchhttp = p1.matcher(strhttp);
      String strnew = matchhttp.replaceAll("").replaceAll("(if[\\s]*\\(|else|elseif[\\s]*\\().*?;", " ");
      
      
      Pattern patterncomma = Pattern.compile("(&[^;]+;)",Pattern.DOTALL);
      Matcher matchercomma = patterncomma.matcher(strnew);
      String strout = matchercomma.replaceAll(" ");
      String answer = strout.replaceAll("[\\pP‘’“”]", " ")
        .replaceAll("\r", " ").replaceAll("\n", " ")
        .replaceAll("\\s", " ").replaceAll("　", "");

      
      return answer;
    }
    /**
	 * 把数组的空数据去掉
	 * @param array
	 * @return
	 */
	public static List<String> array2Empty(String[] array){
		List<String> list = new ArrayList<String>();
		for (String string : array) {
			if(StringUtils.isNotBlank(string)){
				list.add(string);
			}
		}
		return list;
	}
	/**
	 * 把数组转换成set
	 * @param array
	 * @return
	 */
	public static Set<?> array2Set(Object[] array) {
		Set<Object> set = new TreeSet<Object>();
		for (Object id : array) {
			if(null != id){
				set.add(id);
			}
		}
		return set;
	}
	/**
	 * serializable toString
	 * @param serializable
	 * @return
	 */
	public static String toString(Serializable serializable) {
		if(null == serializable){
			return null;
		}
		try {
			return (String)serializable;
		} catch (Exception e) {
			return serializable.toString();
		}
	}


	public static String camelToUnderline(String param){
		if (param==null||"".equals(param.trim())){
			return "";
		}
		int len=param.length();
		StringBuilder sb=new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c=param.charAt(i);
			if (Character.isUpperCase(c)){
				sb.append(UNDERLINE);
				sb.append(Character.toLowerCase(c));
			}else{
				sb.append(c);
			}
		}
		return sb.toString();
	}
	public static String underlineToCamel(String param){
		if (param==null||"".equals(param.trim())){
			return "";
		}
		int len=param.length();
		StringBuilder sb=new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c=param.charAt(i);
			if (c==UNDERLINE){
				if (++i<len){
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			}else{
				sb.append(c);
			}
		}
		return sb.toString();
	}
	public static String underlineToCamel2(String param){
		if (param==null||"".equals(param.trim())){
			return "";
		}
		StringBuilder sb=new StringBuilder(param);
		Matcher mc= Pattern.compile("_").matcher(param);
		int i=0;
		while (mc.find()){
			int position=mc.end()-(i++);
			//String.valueOf(Character.toUpperCase(sb.charAt(position)));
			sb.replace(position-1,position+1,sb.substring(position,position+1).toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * List  驼峰 转成下划线 。  parrm 传NULL  totalcount 传0 时不现实分页的参数
	 */
	public static Map<String,Object> page(Map<String,Object> param , List<Map<String, Object>> list ,String name, int totalCount){
		List<Map<String, Object>> mapList = new ArrayList<>();
		for (Map<String, Object> k : list) {
			Map<String, Object> mapReturn = new HashMap<>();
			for (String m : k.keySet()) {
				mapReturn.put(camelToUnderline(m),k.get(m));
			}
			mapList.add(mapReturn);
		}
		Map<String, Object> map = new HashMap<>();
		if (param!=null&& totalCount>0){
			Integer curr_page = (Integer) param.get("curr_page");
			Integer page_size = (Integer) param.get("page_size");
			map.put("total_page", (totalCount+page_size-1)/page_size==0?1:(totalCount+page_size-1)/page_size);
			map.put("total_count", totalCount);
			map.put("curr_page",curr_page);
			map.put("page_size", page_size);
		}
		map.put(name, mapList);
		return map;
	}
	public static Map<String,Object> pageList(Map<String,Object> param ,String name, int totalCount){
		List<Map<String, Object>> mapList = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
			Integer curr_page = (Integer) param.get("curr_page");
			Integer page_size = (Integer) param.get("page_size");
			map.put("total_page", (totalCount+page_size-1)/page_size==0?1:(totalCount+page_size-1)/page_size);
			map.put("total_count", totalCount);
			map.put("curr_page",curr_page);
			map.put("page_size", page_size);
		map.put(name, mapList);

		return map;
	}


	/**
	 * 将Map的key从下划线转成驼峰
	 * @param map
	 * @return
	 */
	public static Map underlineToCamel(Map<String, Object> map) {
		if (map == null || map.isEmpty() || map.size() < 1) return null;
		Map<String, Object>retMap = new HashMap<>();
		for (String key: map.keySet()) {
			retMap.put(com.mt.common.utils.StringUtils.underlineToCamel(key), map.get(key));
		}
		return retMap;
	}

	/**
	 * 将Map的key从驼峰转为下划线
	 * @param map
	 * @return
	 */
	public static Map camelToUnderline(Map<String, Object> map) {
		if (map == null || map.isEmpty() || map.size() < 1) return null;
		Map<String, Object>retMap = new HashMap<>();
		for (String key: map.keySet()) {
			retMap.put(com.mt.common.utils.StringUtils.camelToUnderline(key), map.get(key));
		}
		return retMap;
	}


	/**
	 * 将mapList的key从驼峰转为下划线
	 * @param mapList
	 * @return
	 */
	public static List<Map<String, Object>> camelToUnderline(List<Map<String, Object>> mapList) {
		if (mapList == null || mapList.isEmpty() || mapList.size() < 1) return null;
		List<Map<String, Object>>  retList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map: mapList) {
			Map<String, Object> retMap = new HashMap<>();
			for (String key : map.keySet()) {
				retMap.put(com.mt.common.utils.StringUtils.camelToUnderline(key), map.get(key));
			}
			retList.add(retMap);
		}
		return retList;
	}



	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
			"g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };

	/**
	 * 生成8位随机数
	 * @return
	 */
	public static String generateShortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();

	}

	public static void main(String[] args) {
		System.out.println(underlineToCamel("client_type_code"));
		System.out.println(underlineToCamel2("client_type_code"));
	}

	/**
	 * 将Map的key从下划线转成驼峰
	 * @param map
	 * @return
	 */
	public static Map mapUnderlineToCamel(Map<String, Object> map) {
		if (map == null || map.isEmpty() || map.size() < 1) { return null; }
		Map<String, Object>retMap = new HashMap<>();
		for (String key: map.keySet()) {
			retMap.put(underlineToCamel(key), map.get(key));
		}
		return retMap;
	}

	/**
	 * 将Map的key从驼峰转为下划线
	 * @param map
	 * @return
	 */
	public static Map mapCamelToUnderline(Map<String, Object> map) {
		if (map == null || map.isEmpty() || map.size() < 1) { return null; }
		Map<String, Object>retMap = new HashMap<>();
		for (String key: map.keySet()) {
			retMap.put(camelToUnderline(key), map.get(key));
		}
		return retMap;
	}

	/**
	 * 将mapList的key从下划线转成驼峰
	 * @param mapList
	 * @return
	 */
	protected List<Map<String, Object>> listUnderlineToCamel(List<Map<String, Object>> mapList) {
		if (mapList == null || mapList.isEmpty() || mapList.size() < 1) { return null; }
		List<Map<String, Object>>  retList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map: mapList) {
			Map<String, Object> retMap = new HashMap<>();
			for (String key : map.keySet()) {
				retMap.put(underlineToCamel(key), map.get(key));
			}
			retList.add(retMap);
		}
		return retList;
	}


	/**
	 * 将mapList的key从驼峰转为下划线
	 * @param mapList
	 * @return
	 */
	protected List<Map<String, Object>> listCamelToUnderline(List<Map<String, Object>> mapList) {
		if (mapList == null || mapList.isEmpty() || mapList.size() < 1) { return null; }
		List<Map<String, Object>>  retList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map: mapList) {
			Map<String, Object> retMap = new HashMap<>();
			for (String key : map.keySet()) {
				retMap.put(camelToUnderline(key), map.get(key));
			}
			retList.add(retMap);
		}
		return retList;
	}

	/**
	 * 将 List<JavaBean>对象转化为List<Map>
	 * @param beanList
	 * @return
	 * @throws Exception
	 */
	public static <T> List<Map<String, Object>> convertListBean2ListMap(List<T> beanList, Class<T> T)
			throws Exception
	{
		List<Map<String, Object>> mapList = new ArrayList<>();
		for (int i = 0, n = beanList.size(); i < n; i++)
		{
			Object bean = beanList.get(i);
			Map<String, Object> map = convertBean2Map(bean);
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 将一个 JavaBean 对象转化为一个 Map
	 * @param bean
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Map<String, Object> convertBean2Map(Object bean)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException
	{
		Class<? extends Object> type = bean.getClass();
		Map<String, Object> returnMap = new HashMap<>();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++)
		{
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!"class".equals(propertyName))
			{
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null)
				{
					returnMap.put(propertyName, result);
				}
				else
				{
					returnMap.put(propertyName, null);
				}
			}
		}
		return returnMap;
	}

}
