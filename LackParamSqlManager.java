package sqlFileAnalysis;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.PreparedSql;

public class LackParamSqlManager extends AbstractLackParamSqlManager {
	private String regEx = ":[^\\s]+\\s?" ;
	
	public LackParamSqlManager(Map<String,String> sqlMap){
		this._sqlMap = sqlMap ;
	}
    
    public String getRegEx() {
		return regEx;
	}

	public void setRegEx(String regEx) {
		this.regEx = regEx;
	}   

    
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		if(!(arg0 instanceof AbstractParamMapManager)){
			return ;
		}
		if(null == arg1){
			this.getSqlNotLackParam(this.regEx);
			return ;
		}
		
	   Map<String,Map<String,String>> paraMap = (Map<String,Map<String,String>>)arg1;
	   if(paraMap.size()<1){
		   return ;
	   }
	   Map<String,String> paraMapForOne = null ;
	   PreparedSql preSql = null ;
	   for(String key:paraMap.keySet()){
		  paraMapForOne = paraMap.get(key);
		  if(paraMapForOne.size()<1|| this.isPreparedParam(paraMapForOne)){
			  preSql = new PreparedSql();
			  preSql.setLackParamSql(this._sqlMap.get(key));
			  preSql.setSqlName(key);
			  preSql.setParaMap(this.removeParas(paraMapForOne));
			  this.preparedSqlQueue.add(preSql); 
		  }
	   }
	   super.setChanged();
	   super.notifyObservers(this.preparedSqlQueue);
	}
	/**
	 * @param regEx
	 * 获得不需要参数准备的Sql语句
	 */
	public void getSqlNotLackParam(String regEx){
		if(null == this._sqlMap){
			return ;
		}
		
		String sql = null ;
		PreparedSql preSql = null ;
		for(String key:this._sqlMap.keySet()){
			sql = this._sqlMap.get(key);
			if(null == sql){
				continue ;
			}
			if(this.containParam(sql, regEx)){
				continue;
			}else{
				preSql = new PreparedSql();
				preSql.setLackParamSql(sql);
				preSql.setSqlName(key);
				preSql.setParaMap(new HashMap<String,String>());
				this.preparedSqlQueue.add(preSql);
			}
		}
		
		if(!this.preparedSqlQueue.isEmpty()){
			super.setChanged();
			super.notifyObservers(this.preparedSqlQueue);
		}
	}
	
	/**
	 * @param sql
	 * @param regEx
	 * @return
	 * 检查sql语句中是否包含参数
	 */
	private boolean containParam(String sql,String regEx){
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(sql);
		if(m.find()){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判定一条SQL语句中的参数是否准备完毕
	 * @param paraMap
	 * @return
	 */
	private boolean isPreparedParam(Map<String,String> paraMap){
		boolean flg = true ;
		if( null == paraMap){
			return flg ;
		}
		for(String k:paraMap.keySet()){
			if("_EOF".equals(paraMap.get(k))){
				flg = false ;
				break ;
			}
		}
		return flg ;
		
	}
	
	/**
	 * 将参数移走
	 * @param paraMapForOne
	 * @return
	 */
	private Map<String,String> removeParas(Map<String,String> paraMapForOne){
		Map<String,String> paras = new HashMap<String,String>();
		paras.putAll(paraMapForOne);
		for(Map.Entry<String, String> ele:paraMapForOne.entrySet()){
			ele.setValue("_EOF");
		}
		return paras ;
		
	}

	
}
