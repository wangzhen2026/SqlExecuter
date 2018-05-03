package sqlFileAnalysis;

import java.util.Map;
import java.util.Observable;

public abstract class AbstractParamMapManager extends Observable {
     private Map<String,Map<String,String>> paramMap = null ;
     /**
      * @param simpleParamMap
      * @param resObjectMap
      * @author Administrator
      * ʹ�ü򵥲���������ֵ��������sql������map
      */
     public abstract void updateParamMap(Map simpleParamMap,Map resObjectMap);
     /**
      * @param map
      * @author Administrator
      * ʹ�ü򵥲�������sql����Map
      */
     public abstract void updateParamMapBySimpleParamMap(Map map);
     /**
      * @param map
      * @author Administrator
      * ʹ�ò�ѯ���Ľ���б����sql����Map
      */
     public abstract void updateParamMapByResObjectMap(Map map);
     /**
      * @param executedSqlName
      * @param resMap
      * @author Administrator
      * ʹ��һ����ѯ����������sql�����б�Map
      */
     public abstract void updateParamMapByOneResMap(String executedSqlName,Map resMap,boolean rootFlag);
     /**
      * @param sqlName
      * �ָ���������ʼ״̬
      */
     public abstract void recoverParams(String sqlName);
}
