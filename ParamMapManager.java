package sqlFileAnalysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParamMapManager extends  AbstractParamMapManager {
    private Map<String,Map<String,String>> paramMap = null ;
    public ParamMapManager(Map paramMap){
    	this.paramMap = paramMap ;
    }
    
   @Override
    public void updateParamMap(Map simpleParamMap,Map resObjectMap){
    	if(null == this.paramMap||this.paramMap.size()<1){
    		return ;
    	}
    	
    	this.updateParamMapBySimpleParamMap(simpleParamMap);
    	this.updateParamMapByResObjectMap(resObjectMap);
    	super.setChanged();
    	super.notifyObservers(this.paramMap);
    	
    }
    
   @Override
    public void updateParamMapBySimpleParamMap(Map map){
    	if(null == map ||map.size()<1||null == this.paramMap || this.paramMap.size()<1){
    		return ;
    	}
    	String value,keyStr = null ;
    	String[] keyArrayForOne = null ;
    	Map<String,String> paramMapForOne = null ;
    	for(Object key:map.keySet()){
    		keyStr = (String)key;
    		keyArrayForOne = keyStr.split("\\.");
    		if(keyArrayForOne.length!=2){
    			System.out.println(keyStr+"不符合简单参数规则，跳过此次分析");
    			continue;
    		}else{
    			paramMapForOne = this.paramMap.get(keyArrayForOne[0]);
    			try{
    				value = (String)map.get(key);
    				paramMapForOne.put(keyArrayForOne[1],value);
    			}catch(Exception e){
    				System.out.println(keyStr+"配置有误，请检查");
    			}
    		}
    	}
    	super.setChanged();
    	super.notifyObservers(this.paramMap);
    }
    
   @Override
    public void updateParamMapByResObjectMap(Map map){
    	if(null == map||map.size()<1||null == this.paramMap || this.paramMap.size()<1){
    		return ;
    	}
    	String value,keyStr = null ;
    	String[] keyArrayForOne = null ;
    	Map<String,String> paramMapForOne = null ;
    	List resList = null ;
    	for(Object key:map.keySet()){
    		keyStr = (String)key;
    		resList = (List)map.get(key);
    		if(null == resList || resList.size()<1){
    			continue ;
    		}else{
    			for(Object ele :resList){
    				this.updateParamMapByOneResMap(keyStr,(Map)ele,false);	
    				}
    			}
    		}
    	
    	super.setChanged();
        super.notifyObservers(this.paramMap);
    	
    	}
    
    
   @Override
    public void updateParamMapByOneResMap(String executedSqlName,Map resMap,boolean rootFlag){
    	if(null == this.paramMap || this.paramMap.size()<1||null == resMap||resMap.size()<1){
    		return ;
    	}
    	Map<String,String> midMap = this.formatResMapForUpdateParamMap(executedSqlName, resMap);
    	if(null == midMap || midMap.size()<1){
    		return ;
    	}
    	Map<String,String> paramMapForOne = null ;
    	String columnName = null,originalVal = null,valTmp = null ;
    	Map<String,String> paraMapForOneSqlName = null ;
    	for(Map.Entry<String, Map<String,String>> ele:this.paramMap.entrySet()){
    		paraMapForOneSqlName = ele.getValue() ;
    		for(Map.Entry<String, String> e:paraMapForOneSqlName.entrySet()){
    			columnName = e.getKey();
    			originalVal = e.getValue() ;
    			if(midMap.containsKey(columnName)){
    				valTmp = midMap.get(columnName);
    				paraMapForOneSqlName.put(columnName, originalVal+","+valTmp);
    			}
    		}
    	}
    	if(rootFlag){
    		super.setChanged();
        	super.notifyObservers(this.paramMap);
    	}
    	
    }
    
    /**
     * @param executedSqlName
     * @param resMap
     * @return
     * 规范化结果对象map，主要是将对象map的key值，改为三段式：sqlName.record.columName
     */
    private Map<String,String> formatResMapForUpdateParamMap(String executedSqlName,Map<String,Object> resMap){
    	Map<String,String> midMap = null ;
    	if(null == resMap || resMap.size()<1){
    		return midMap;
    	}
    	midMap = new HashMap<String,String>();
    	String columnName = null ,value = null ;
    	Object valueTmp = null ;
    	for(Object key:resMap.keySet()){
    		columnName = (String)key;
    		valueTmp = resMap.get(key);
    		if(null ==valueTmp){
    			continue ;
    		}
    		value = String.valueOf(valueTmp);
    		midMap.put(":"+executedSqlName+".record."+columnName, value);
    	}
    	return midMap ;
    	
    	
    }
    
    @Override
    public void recoverParams(String sqlName){
    	if(null == this.paramMap){
    		return ;
    	}
    	
    	if(null == sqlName){
    		for(String key:this.paramMap.keySet()){
    			this.recoverParams(key);
    		}
    	}else{
    		Map<String,String> paramMapForOne = this.paramMap.get(sqlName);
    		if(null == paramMapForOne || paramMapForOne.size()<1){
    			return ;
    		}
    		for(String key:paramMapForOne.keySet()){
    			paramMapForOne.put(key, "_EOF");
    		}
    	}
    }
    	
}
