package com.crf.signature.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class Tools {
	
	/**
     * 功能:构造方法
     * @author 满口蛀牙
     * @date 2015年08月12日
     * @param null
     * @return null
     * @throws null
     **/
	public Tools() {
		// TODO Auto-generated constructor stub
	}
	
	/**
     * 功能:返回时间戳
     * @author 满口蛀牙
     * @date 2015年08月08日
     * @param null
     * @return String timeNow
     * @throws null
     **/
	public static long getCurrentUnixTime(){
		long timeNow=System.currentTimeMillis();
		return timeNow;
	}
	
	/**
     * 功能:字符串去除空格
     * @author 满口蛀牙
     * @date 2015年08月08日
     * @param null
     * @return String
     * @throws null
     **/
	public static String trimAll(String agr){
		return agr.replaceAll("\\s*", "");
	}
	
	/** 
     * 功能:时间戳转换成日期格式字符串 
     * @author 满口蛀牙
     * @date 2015年08月08日
     * @param Long timeStamp 精确到秒的字符串
     * @param String format  输出格式
     * @return String
     */  
    public static String timeStamp2Date(Long timeStamp,String format) {
    	if(timeStamp==null){
    		return "";
    	}
        if(format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";  
        SimpleDateFormat sdf = new SimpleDateFormat(format);  
        return sdf.format(new Date(timeStamp));  
    }
    
    /** 
     * 功能:日期格式字符串转换成时间戳 
     * @author 满口蛀牙
     * @date 2015年08月08日
     * @param String date_str 字符串日期 
     * @param String format 如：yyyy-MM-dd HH:mm:ss 
     * @return String
     */  
    public static String date2TimeStamp(String date_str,String format){  
        try {  
            SimpleDateFormat sdf = new SimpleDateFormat(format);  
            return String.valueOf(sdf.parse(date_str).getTime());  //如果需要10位的时间戳则除1000
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return "";  
    }
    
    /** 
     * 功能:获取几天前的时间
     * @author 满口蛀牙
     * @date 2016年3月17日
     * @param int day 
     * @param String format 如：yyyy-MM-dd HH:mm:ss
     * @return String
     */ 
    public static String getBeforeData(int day,String format){
    	Date d=new Date();  
		SimpleDateFormat df=new SimpleDateFormat(format);
		//System.out.println("今天的日期："+df.format(d));
		return df.format(new Date(d.getTime() - day * 24 * 60 * 60 * 1000L));
    }
    
    /** 
     * 功能:获取几天后的时间
     * @author 满口蛀牙
     * @date 2016年3月17日
     * @param int day 
     * @param String format 如：yyyy-MM-dd HH:mm:ss
     * @return String
     */ 
    public static String getAfterData(int day,String format){
    	Date d=new Date();  
		SimpleDateFormat df=new SimpleDateFormat(format);
		return df.format(new Date(d.getTime() + day * 24 * 60 * 60 * 1000L));
    }
    
    /** 
     * 功能:获取客户端mac地址
     * @author 满口蛀牙
     * @date 2015年10月08日
     * @param String ip 连接到服务器滴ip地址 
     * @return String
     */
    public static String getMac(String ip) {
		String str = "";
		String macAddress = "";
		try {
			//Process p = Runtime.getRuntime().exec("nbtstat -a " + ip);
			String cmd = "nbtstat -A " + ip;
			File file = new File("C:\\Windows\\SysWOW64");
			if(file.exists()){
			    cmd = "c:\\Windows\\sysnative\\nbtstat.exe -A " + ip;
			}
			Process p = Runtime.getRuntime().exec(cmd);
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (int i = 1; i < 100; i++) {
				str = input.readLine();
				if (str != null) {
					// if (str.indexOf("MAC Address") > 1) {
					if (str.indexOf("MAC") > 1) {
						macAddress = str.substring(str.indexOf("=") + 2,
								str.length());
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		return macAddress;
	}
    
    /** 
     * 功能:数字字符串分割成整形数组
     * @author 满口蛀牙
     * @date 2016年02月26日
     * @param string 需要分割的字符串
     * @param regex 分隔符
     * @return String
     */
    public static int[] string2array(String string,String regex){
    	String[] tempString=string.split(regex);
		int[] tempInt=new int[tempString.length];
		for(int i=0;i<tempString.length;i++){
			tempInt[i]=Integer.parseInt(tempString[i]);
		}
		return tempInt;
    }
    
    /**
     * 验证邮箱
     * @param email
     * @return
     */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

    /**
     * 验证手机号码
     * @param mobiles
     * @return
     */
	public static boolean checkMobileNumber(String mobileNumber) {
		boolean flag = false;
		try {
			Pattern regex = Pattern
					.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
			Matcher matcher = regex.matcher(mobileNumber);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	 /**
     * 格式string
     * @param string
     * @return
     */
	public static String formartString(String string){
		String[] str=string.split(",");
		String myStr="";
		for(int i=0;i<str.length;i++){
			myStr+='"'+str[i]+'"'+',';
		}
		if(str.length>0){
			myStr=myStr.substring(0, (myStr.length()-1));
		}
		return myStr;
	}
	
	 /**
     * JSON string 转map
     * @param Map<String,String>
     * @return
     */
	public static Map<String,String> json2Map(String json){
		JSONObject jsonObject=JSON.parseObject(json);
		Map<String, String> map=new HashMap<String, String>();
		Set<String> keySet=jsonObject.keySet();
		Iterator<String> keys=keySet.iterator();
		String key=null;
		String value=null;
		while (keys.hasNext()) {
			key=keys.next();
			value=jsonObject.getString(key);
			map.put(key, value);
		}
		return map;
		
	}
	
	
}
