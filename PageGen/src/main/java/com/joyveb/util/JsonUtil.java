package com.joyveb.util;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.util.TokenBuffer;

public class JsonUtil {
	static ObjectMapper mapper = new ObjectMapper();
	static 
	{
		mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(Feature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
		mapper.configure(Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		System.out.println("jsonutilgen init");

	}

	public static <T> T json2Bean(JsonNode node, Class<T> clazz) {
		try {
			return mapper.readValue(node, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	
	public static <T> T json2Bean(String jsonTxt, Class<T> clazz) {
		try {
			return mapper.readValue(jsonTxt, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static JsonNode bean2Json(Object bean) {
		try {
			if(bean==null)
			{
				return null;
			}
			TokenBuffer buffer = new TokenBuffer(mapper);
			mapper.writeValue(buffer, bean);
			JsonNode node = mapper.readTree(buffer.asParser());
			return node;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static ArrayNode newArrayNode() {
		return mapper.createArrayNode();
	}

	public static ArrayNode toArrayNode(String jsontxt) {
		try {
			return mapper.readValue(jsontxt, ArrayNode.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
