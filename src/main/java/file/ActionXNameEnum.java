package file;

public enum ActionXNameEnum {
	
	WPS_WORD("KWPS.Application","金山wps应用"),
	WPS_PPT("KWPP.Application","金山wps ppt应用"),
	WPS_EXCEL("KET.Application","金山wps excel应用"),
	MICRO_WORD("Word.Application","office word应用"),
	MICRO_EXCEL("Excel.Application","office excel应用"),
	MICRO_PPT("Powerpoint.Application","office ppt应用"),
	IE("InternetExplorer.Application","IE应用");
	
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
