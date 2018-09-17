package bean;

public class VerficatResult {
	
	private boolean isOk;
	private String errmsg;
	
	public VerficatResult(boolean isOk, String msg) {
		this.isOk=isOk;
		this.errmsg=msg;
	}

	public boolean getIsOk() {
		return isOk;
	}
	
	public void setIsOk(boolean isOk) {
		this.isOk = isOk;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	

}
