package sqlFileAnalysis;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlFileAnalysis {
	
	/**
	 * @param sqlFileName
	 * @param lackParamSqlMap
	 * @param paramMap
	 * @author Administrator
	 * 解析sql配置文件
	 */
	public static void analysis(String sqlFileName,Map<String,String>lackParamSqlMap,Map<String,Object>paramMap){
		
		File sqlFile = new File(sqlFileName);
		if(!sqlFile.isFile()||!sqlFile.exists()){
			System.out.println(sqlFileName +"不存在或不是文件，请检查");
			return;
		}
		
		Properties  propers = new Properties();
		try {
			propers.load(new FileReader(sqlFile));
		    if(propers.size()<1){
		    	System.out.println(sqlFileName+"内容为空,请检查！");
		    	return;
		    }
		    String keyStr,valueStr = null,regEx=":[^\\s]+\\s?" ;
		    Map paramMapForOne = null ;
		    for(Object key:propers.keySet()){
		    	valueStr =(String)propers.get(key);
		    	keyStr = (String)key;
		    	lackParamSqlMap.put(keyStr, valueStr);
		    	
		        paramMapForOne = extParams(valueStr,regEx);
		        paramMap.put(keyStr, paramMapForOne);
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(sqlFileName+"文件格式有误，请检查，应为properties文件");
		}
		
	}
	
	/**
	 * @param valueStr
	 * @param regStr
	 * @return
	 * @author Administrator
	 * 提取符合指定正则表达式的参数
	 */
	private static Map extParams(String valueStr,String regStr){
		Map<String,String> paraMap = new LinkedHashMap<String,String>();
		Pattern p = Pattern.compile(regStr);
		Matcher m = p.matcher(valueStr);
		while(m.find()){
			paraMap.put(m.group().trim(), "_EOF");
		}
		return paraMap ;
	}

}
