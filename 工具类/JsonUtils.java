package com.crf.signature.utils;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	protected static final Logger logger = Logger.getLogger(JsonUtils.class);

	public static <T> T json2obj(String json, Class<T> type) {
		try {
			return new Jackson2ObjectMapperBuilder().build().readValue(json,
					type);
		} catch (Exception e) {
			logger.error("报错了", e);
			return null;
		}
	}

	public static Map json2map(String json) {
		try {
			Map map = new Jackson2ObjectMapperBuilder().build().readValue(json,Map.class);
			return map;
		} catch (Exception e) {
			logger.error("报错了", e);
			return null;
		}
	}

	public static String obj2json(Object obj) {
		try {
			if (obj == null) {
				return "";
			}
			return new Jackson2ObjectMapperBuilder().build()
					.writeValueAsString(obj);
		} catch (Exception e) {
			logger.error("报错了", e);
			return "";
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> json2List(String json, Class<T> type) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			@SuppressWarnings("deprecation")
			JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, type);
			return (List<T>) mapper.readValue(json, javaType);
		} catch (Exception e) {
			logger.error("报错了", e);
			return null;
		}
	}
}
