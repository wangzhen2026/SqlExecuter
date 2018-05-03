package tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExMatcher {
   public static boolean getMatheRes(String src ,String regEx){
	   Pattern p = Pattern.compile(regEx);
	   Matcher m = p.matcher(src);
	   return m.matches();
   }
}
