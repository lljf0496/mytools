package file;

public enum ActionXNameEnum {
	
	WPS("KWPS.Application","金山wps应用"),
	WORD("Word.Application","office word应用"),
	EXCEL("Excel.Application","office excel应用"),
	IE("InternetExplorer.Application","IE应用"),
	PPT("Powerpoint.Application","office ppt应用");
	
	/**应用名称 */
	private String actionName;
	/**应用说明*/
	private String Desc;
	
	
	private ActionXNameEnum(String actionName, String desc) {
		this.actionName = actionName;
		Desc = desc;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getDesc() {
		return Desc;
	}
	public void setDesc(String desc) {
		Desc = desc;
	}
	
	
}
