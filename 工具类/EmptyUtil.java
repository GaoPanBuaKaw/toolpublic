package com.crf.signature.utils;
import java.util.List;
import java.util.Map;


/**
 * @author Administrator
 * 
 */
public class EmptyUtil {
	/**
	 * 判断List集合不为空,为空返回false 不为空返回true
	 * 
	 * @param <T>
	 * @param lists
	 * @return
	 */
	public static <T> boolean isNotEmpty(List<T> lists) {
		if (null == lists) {
			return false;
		} else if (lists.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * 判断List集合是否为空,为空返回true 不为空返回false
	 * 
	 * @param <T>
	 * @param lists
	 * @return
	 */
	public static <T> boolean isEmpty(List<T> lists) {
		if (null == lists) {
			return true;
		} else if (lists.isEmpty()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断List集合是否为空,为空返回true 不为空返回false
	 * 
	 * @param <T>
	 * @param lists
	 * @return
	 */
	public static <T> boolean isEmpty(T[] array) {
		if (null == array) {
			return true;
		} else if (0 == array.length) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串不为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		if (null == str) {
			return false;
		} else if ("".equals(str.trim())) {
			return false;
		}
		return true;
	}
	
	/**
	 * 判断Object不为空
	 * 
	 * @param str
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isNotEmpty(Map map) {
		if (null == map) {
			return false;
		}else if(map.isEmpty()){
			return false;
		}
		return true;
	}
	
	/**
	 * 判断Object不为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(Object str) {
		if (null == str) {
			return false;
		}
		return true;
	}
	
	/**
	 * 判断Object为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(Object str) {
		if (null == str) {
			return true;
		}else if(str.toString().length() == 0){
			return true;
		}
		return false;
	}
	

	/**
	 * 判断字符串为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (null == str) {
			return true;
		} else if ("".equals(str.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串为Null
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		if (null == str) {
			return true;
		}
		return false;
	}

	public static String isNullChar(String value) {
		if (isNotEmpty(value)) {
			if (",".equals(value.trim())) {
				return null;
			}
		}
		return value;
	}
}
