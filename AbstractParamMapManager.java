package sqlFileAnalysis;

import java.util.Map;
import java.util.Observable;

public abstract class AbstractParamMapManager extends Observable {
     private Map<String,Map<String,String>> paramMap = null ;
     /**
      * @param simpleParamMap
      * @param resObjectMap
      * @author Administrator
      * 使用简单参数，返回值参数更新sql语句参数map
      */
     public abstract void updateParamMap(Map simpleParamMap,Map resObjectMap);
     /**
      * @param map
      * @author Administrator
      * 使用简单参数更新sql参数Map
      */
     public abstract void updateParamMapBySimpleParamMap(Map map);
     /**
      * @param map
      * @author Administrator
      * 使用查询到的结果列表更新sql参数Map
      */
     public abstract void updateParamMapByResObjectMap(Map map);
     /**
      * @param executedSqlName
      * @param resMap
      * @author Administrator
      * 使用一个查询结果对象更新sql参数列表Map
      */
     public abstract void updateParamMapByOneResMap(String executedSqlName,Map resMap,boolean rootFlag);
     /**
      * @param sqlName
      * 恢复参数到初始状态
      */
     public abstract void recoverParams(String sqlName);
}
