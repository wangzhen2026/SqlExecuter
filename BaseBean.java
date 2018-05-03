package model;
import visiter.IVisiter;
public class BaseBean {
   protected IVisiter visiter = null ;

   public IVisiter getVisiter() {
	  return visiter;
   }

   public void setVisiter(IVisiter visiter) {
	  this.visiter = visiter;
   }
   
   
}
