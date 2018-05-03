package visiter;

import model.BaseBean;

public abstract class IVisiter {
	protected BaseBean obj ;
    public abstract String getNeedContext();
}
