package com.crf.signature.utils;

import org.apache.commons.lang3.StringUtils;

public class DataMasking {
	   private static final String SYMBOL = "*";
	   
	   /**
	     * 名字脱敏
	     * 规则：姓名为2个字的，遮盖第2个字，姓名为3个字或以上的，只保留头跟尾，遮盖中间的字
	     * @param value
	     * @return
	     */
	    public static String nameToConceal(String value) {
	        if (StringUtils.isBlank(value)) {
	            return value;
	        }
	        int len = value.length();
	        StringBuilder stringBuilder = new StringBuilder();
	        if (len <= 2) {
	            stringBuilder.append(value.substring(0, 1));
	            stringBuilder.append(SYMBOL);
	        } else {
	            int pamafour = len - 2;
	            stringBuilder.append(value.substring(0, 1));
	            for (int i = 0; i < pamafour; i++) {
	                stringBuilder.append(SYMBOL);
	            }
	            stringBuilder.append(value.substring(len - 1, len));
	        }
	        return stringBuilder.toString();
	    }
		
		
		/**
	     * 身份证脱敏
	     * 规则：隐藏身份证后四位
	     * @param value
	     * @return
	     */
	    public static String idNumToConceal2(String value) {
	        if (StringUtils.isBlank(value) || value.length() < 4) {
	            return value;
	        }
	        String strSub = "";
	        if (value.length() >= 4) {
	            strSub = value.replace(value.substring(value.length() - 4, value.length()), "****");
	        }

	        return strSub;
	    }
}
