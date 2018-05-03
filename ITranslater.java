package tools;

import java.util.Map;

public interface ITranslater {
	public<T> T translateMap2Bean(Map<String,Object> map,T beanObj);
}
