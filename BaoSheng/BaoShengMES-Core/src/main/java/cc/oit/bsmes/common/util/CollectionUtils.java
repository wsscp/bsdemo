package cc.oit.bsmes.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtils {
	
	protected final static Logger logger = LoggerFactory.getLogger(CollectionUtils.class);   

	public static <T> List<List<T>> splitList(List<T> values, Integer size) {
		if (values == null || values.size() == 0)
			return null;

		if (size == null || size <= 0)
			size = 1000;
		
		List<List<T>> results = new ArrayList<List<T>>();
		
		for (int i = 0; i < values.size(); ++i) {
			if (i % size == 0) {
				List<T> result = new ArrayList<T>();
				results.add(result);
			}
			
			results.get(i / size).add(values.get(i));
		}

		return results;
	} 
	
	public static <T> List<T> convertToArrayList(T[] values){
		if (values == null || values.length == 0)
			return null;
		List<T> results = new ArrayList<T>();
		for(int i = 0 ; i< values.length ;i++){
			results.add(values[i]);
		}
		return results;
	}
}
















