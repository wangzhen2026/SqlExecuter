package tools;

import java.lang.reflect.Method;
import java.util.Map;

public class Translater implements ITranslater {
    /**
     * 将Map对象转换成bean对象
     * @param map
     * @param beanObj
     * @return
     */
    public<T> T translateMap2Bean(Map<String,Object> map,T beanObj){
    	
    	if(null == map ){
    		return beanObj ;
    	}
    	
    	Class<T> cls = (Class<T>) beanObj.getClass();
    	String colName = null ,setterName = null;
    	Object val = null ;
    	Class valCls = null ;
    	Method method = null ;
    	for(Map.Entry<String, Object> col:map.entrySet()){
    		colName = col.getKey() ;
    		val = col.getValue();
    		setterName = "set"+colName ;
    		valCls = this.getClassObj(val);
    		
    		try {
				method = cls.getMethod(setterName, valCls);
				method.invoke(beanObj, valCls);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue ;
			}
    	}
    	return beanObj ;
    	
    }
    
    /**
     * 获得常见类型的Class对象
     * @param obj
     * @return
     */
    private Class getClassObj(Object obj){
    	if(obj instanceof String){
    		return String.class;
    	}else if(obj instanceof Integer){
    		return Integer.class;
    	}else if(obj instanceof Float){
    		return Float.class ;
    	}else{
    		return null ;
    	}
    }
}
