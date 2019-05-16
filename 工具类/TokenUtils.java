package com.crf.signature.utils;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import com.alibaba.fastjson.JSON;
import com.crf.signature.config.CrfConstant;
import com.crf.signature.encrypt.rsa.RSAUtils;
import com.crf.signature.entities.common.Header;
import com.crf.signature.entities.common.JWT;

public class TokenUtils {
	
	public static int MAX_AGE=10*60;//cooke存活时间(秒)
	
	public static String COOKIE_NAME="u_token";
	
	/**
	 * 生成token
	 * **/
	public static String generateToken(String username,Long groupId) throws Exception{
		Date reqDate=new Date();
		Date expireDate=DateUtil.addSecond(reqDate, MAX_AGE);
		JWT jwt=new JWT();
		jwt.setIat(reqDate.getTime());
		jwt.setExp(expireDate.getTime());
		jwt.setUname(username);
		jwt.setGroup(groupId);
		String enJwtString=Base64Utils.encodeToString(JSON.toJSONString(jwt).getBytes());
		Header header=new Header();
		String enHeader=Base64Utils.encodeToString(JSON.toJSONString(header).getBytes());
		String token=enJwtString+","+enHeader;
		byte[] enToken=RSAUtils.encryptByPublicKey(token.getBytes(), CrfConstant.RSA_PUB_KEY);
		return Base64Utils.encodeToString(enToken);
	}
	
	/**
	 * 解析token
	 * @throws Exception 
	 * **/
	public static JWT parseToken(String token) throws Exception{
		byte[] tokenByte=Base64Utils.decode(token.getBytes());
		byte[] tokendecrypt=RSAUtils.decryptByPrivateKey(tokenByte, CrfConstant.RSA_PRI_KEY);
		String jwtStringcString=new String(tokendecrypt).split(",")[0];
		byte[] jsonStrng=Base64Utils.decodeFromString(jwtStringcString);
		JWT jwt=JSON.parseObject(new String(jsonStrng), JWT.class);
		return jwt;
	}
	
	/**
	 * 校验token
	 * @throws Exception 
	 * **/
	public static boolean validateToken(String token) throws Exception{
		JWT jwt=parseToken(token);
		Date timeNow=new Date();
		if(jwt.getExp()>timeNow.getTime()){
			return true;
		}
		return false;
	}

}
