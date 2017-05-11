package cc.oit.bsmes.wip.dto;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class MethodReturnDto {

	private boolean success;
    private String message;
    private Map<String, Object> jsonMap = new HashMap<String, Object>();
	public MethodReturnDto(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	@SuppressWarnings("unchecked")
	public MethodReturnDto(boolean success, String message, String jsonText) {
		super();
		this.success = success;
		this.message = message;
		jsonMap.putAll(JSON.parseObject(jsonText, Map.class));
	}
	public MethodReturnDto(boolean success, String message, Map<String, Object> map) {
		super();
		this.success = success;
		this.message = message;
		jsonMap.putAll(map);
	}
	public MethodReturnDto(boolean success) {
		super();
		this.success = success;
	}
	public void putItem(String key, Object obj) {
		jsonMap.put(key, obj);
	}
    
}
