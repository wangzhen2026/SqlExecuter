package sqlFileAnalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import tools.ITranslater;

public  class ResultManager extends Observable implements Observer {

	protected ITranslater translater = null ;
	protected Class beanCls = null ;
	protected List<Object> beanList = null;
	
	

	public ResultManager(ITranslater translater, Class beanCls, List<Object> beanList) {
		super();
		this.translater = translater;
		this.beanCls = beanCls;
		this.beanList = beanList;
	}



	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		Map<String,List<Map<String,Object>>> resList = null;
		if(o instanceof AbstractSqlExecuter){
			resList = (Map<String, List<Map<String, Object>>>) arg ;
			if(null != resList){
				this.setChanged();
				this.notifyObservers(resList);
			}
			for(Map.Entry<String, List<Map<String,Object>>> res:resList.entrySet()){
				for(Map<String,Object> ele:res.getValue()){
					try {
						this.beanList.add(translater.translateMap2Bean(ele, beanCls.newInstance()));
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	
}
