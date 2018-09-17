package mybatis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

public class CreateMybatisSqlUtils {
	private Connection connection=null;
	private PreparedStatement ps=null;
	
	public void connectDB(){
		String mysqlDriver = "com.mysql.jdbc.Driver";
		String oracleDriver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:mysql://10.2.100.252:3306/gov_ismp_sys?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false";
		String user = "dbamin";
		String password = "Db123456";
		try {
			Class.forName(mysqlDriver);
			connection =DriverManager.getConnection(url,user,password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void closeConnec(){
		if(connection!=null)
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	@Test
	public void executeService(){
		try{
		connectDB();
		createModel("t_proj_document","gov_ismp_sys");
		createSelectAsNameSql("t_proj_document","gov_ismp_sys");
		//createJSONString("sec_assessment_base","gov_ismp");
		//createUpdateSql("t_qua_check","gov_ismp_sys");
		//createInsertSql("t_qua_system","gov_ismp_sys");
		//createIfTableInsertSql("t_qua_workplan","gov_ismp_sys");
		//createUpdateSql("t_qua_supervision_regist","gov_ismp");
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(ps!=null)ps.close();
				closeConnec();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private void createSelectAsNameSql(String tableName, String dbName) throws SQLException {
		String sql="desc "+tableName;
		ps=connection.prepareStatement(sql);
		ResultSet set=ps.executeQuery();
		System.out.println("SELECT ");
		StringBuffer str2=new StringBuffer();
		int t=0;
		while(set.next()){
			t++;
			String result=set.getString(1);
			String[] strs=result.split("_");
			StringBuffer str1=new StringBuffer(strs[0]);
			for(int i=1;i<strs.length;i++){
				String sr=strs[i];
				 char[] ch = sr.toCharArray();  
				    if (ch[0] >= 'a' && ch[0] <= 'z') {  
				        ch[0] = (char) (ch[0] - 32);  
				    }  
				 str1.append(new String(ch)); 
			}
			str2.append(result).append(" AS ").append(str1).append(",");
			str1.setLength(0);
			if(t>=5){
				str2.append("\n");
				t=0;
			}
		}
		String str=str2.toString();
		System.out.println(str.substring(0, str.length()-1));
		System.out.println("FROM "+tableName);
		System.out.println("WHERE ");
		
	}


	public void  createInserSqlString(String tableName,String dbName) throws Exception{
		String sql="desc "+tableName;
		ps=connection.prepareStatement(sql);
		ResultSet set=ps.executeQuery();
		System.out.println("INSERT INTO tableName (");
		StringBuffer str2=new StringBuffer();
		StringBuffer str3=new StringBuffer();
		int t=0;
		while(set.next()){
			t++;
			String result=set.getString(1);
			String[] strs=result.split("_");
			StringBuffer str1=new StringBuffer(strs[0]);
			for(int i=1;i<strs.length;i++){
				String sr=strs[i];
				 char[] ch = sr.toCharArray();  
				    if (ch[0] >= 'a' && ch[0] <= 'z') {  
				        ch[0] = (char) (ch[0] - 32);  
				    }  
				 str1.append(new String(ch)); 
			}
			str2.append("#{").append(str1).append("}").append(",");
			str3.append(result).append(",");
			str1.setLength(0);
			if(t>=5){
				str2.append("\n");
				str3.append("\n");
				t=0;
			}
		}
		System.out.println(str3.toString());
		System.out.println(") VALUES (");
		System.out.println(str2.toString());
		System.out.println(") ");
	}
	
	public void  createJSONString(String tableName,String dbName) throws Exception{
		String sql="SELECT COLUMN_NAME,COLUMN_COMMENT,DATA_TYPE from  INFORMATION_SCHEMA.COLUMNS "+
				"WHERE table_name = '"+tableName+"'"+
				"AND table_schema = '"+dbName+"'";
		ps=connection.prepareStatement(sql);
		ResultSet set=ps.executeQuery();
		System.out.println("{\"params\":[");
		while(set.next()){
			String result=set.getString(1);
			String remark=set.getString(2);
			String[] strs=result.split("_");
			StringBuffer str1=new StringBuffer(strs[0]);
			for(int i=1;i<strs.length;i++){
				String sr=strs[i];
				 char[] ch = sr.toCharArray();  
				    if (ch[0] >= 'a' && ch[0] <= 'z') {  
				        ch[0] = (char) (ch[0] - 32);  
				    }  
				 str1.append(new String(ch)); 
			}
			System.out.println("{\""+str1.toString()+"\":\""+remark+"\"},");
		}
		System.out.println("]");
		
	}
	
	
	public void  createMycode(String tableName,String dbName) throws Exception{
		String sql="SELECT COLUMN_NAME,COLUMN_COMMENT,DATA_TYPE from  INFORMATION_SCHEMA.COLUMNS "+
				"WHERE table_name = '"+tableName+"'"+
				"AND table_schema = '"+dbName+"'";
		ps=connection.prepareStatement(sql);
		ResultSet set=ps.executeQuery();
		int i=0;
		StringBuilder sb1=new StringBuilder();
		StringBuilder sb2=new StringBuilder();
		while(set.next()){
			String result=set.getString(1);
			sb1.append(result).append(",");
			sb2.append("#{").append(result).append("},");
			if(i==10){
				System.out.println(sb1.toString());
				System.out.println(sb2.toString());
				sb1.setLength(0);
				sb2.setLength(0);
				i=0;
			}
			i++;
			//System.out.println("{\"name\":\""+result+"\",\"code\":\""+result+"\"},");
		}
		System.out.println(sb1.toString());
		System.out.println(sb2.toString());
	}
	
	public void createModel(String tableName,String dbName) throws Exception{
		String sql="SELECT COLUMN_NAME,COLUMN_COMMENT,DATA_TYPE from  INFORMATION_SCHEMA.COLUMNS "+
				"WHERE table_name = '"+tableName+"'"+
				"AND table_schema = '"+dbName+"'";
		ps=connection.prepareStatement(sql);
		ResultSet set=ps.executeQuery();
		while(set.next()){
			String result=set.getString(1);
			String result2=set.getString(2);
			String result3=set.getString(3);
			String[] strs=result.split("_");
			String str1=strs[0];
			for(int i=1;i<strs.length;i++){
				String sr=strs[i];
				 char[] ch = sr.toCharArray();  
				    if (ch[0] >= 'a' && ch[0] <= 'z') {  
				        ch[0] = (char) (ch[0] - 32);  
				    }  
				str1 = str1 + new String(ch).toString(); 
			}
			String dateType="String";
			if(result3.equals("date")||result3.equals("datetime")){
				dateType=dataTypeEnum.date.getType();
			}else if(result3.equals("char")||result3.equals("varchar")){
				dateType=dataTypeEnum.string.getType();
			}else if(result3.equals("bigint")||result3.equals("long")){
				dateType=dataTypeEnum.num_long.getType();
			}else if(result3.equals("int")||result3.equals("tinyint")){
				dateType=dataTypeEnum.num_integer.getType();
			}else if(result3.equals("double")||result3.equals("decimal")){
				dateType=dataTypeEnum.num_decimal.getType();
			}
			System.out.println("/**"+result2+"*/");
			//@ApiModelProperty(value="文件id",dataType="String")
			System.out.println("@ApiModelProperty(value=\""+result2+"\",dataType=\""+dateType+"\")");
			System.out.println("private "+dateType+" "+str1+";");
			
		}
	}
	
	
	private enum dataTypeEnum{
		string("String"),
		date("Date"),
		boolean_type("boolean"),
		num_integer("Integer"),
		num_long("Long"),
		num_decimal("BigDecimal");
		
		private String type;
		
		dataTypeEnum(String type){
			this.type=type;
		}
		
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		
	}
	
	public void createInsertSql(String tableName,String dbName) throws Exception{
		String sql="SELECT COLUMN_NAME,COLUMN_COMMENT,DATA_TYPE from  INFORMATION_SCHEMA.COLUMNS "+
				"WHERE table_name = '"+tableName+"'"+
				"AND table_schema = '"+dbName+"'";
		ps=connection.prepareStatement(sql);
		ResultSet set=ps.executeQuery();
		StringBuffer sb1=new StringBuffer();
		StringBuffer sb2=new StringBuffer();
		int t=1;
		while(set.next()){
			String result=set.getString(1);
			String[] strs=result.split("_");
			String str1=strs[0];
			for(int i=1;i<strs.length;i++){
				String sr=strs[i];
				 char[] ch = sr.toCharArray();  
				    if (ch[0] >= 'a' && ch[0] <= 'z') {  
				        ch[0] = (char) (ch[0] - 32);  
				    }  
				str1 = str1 + new String(ch).toString(); 
			}
			
			sb1.append(result).append(",");
			sb2.append("#{").append(str1).append("}").append(",");
			if(t>=5){
				sb1.append("\n");
				sb2.append("\n");
				t=0;
			}
			t++;
		}
		System.out.println("INSERT INTO "+ tableName +" (");
		System.out.println(sb1.toString().substring(0, sb1.toString().length()-1));
		System.out.println(" ) VALUES ( ");
		System.out.println(sb2.toString().substring(0, sb2.toString().length()-1));
		System.out.println(" )");
	}
	
	public void createIfTableInsertSql(String tableName,String dbName) throws Exception{
		String sql="SELECT COLUMN_NAME,COLUMN_COMMENT,DATA_TYPE from  INFORMATION_SCHEMA.COLUMNS "+
				"WHERE table_name = '"+tableName+"'"+
				"AND table_schema = '"+dbName+"'";
		ps=connection.prepareStatement(sql);
		ResultSet set=ps.executeQuery();
		StringBuffer sb1=new StringBuffer();
		StringBuffer sb2=new StringBuffer();
		int t=1;
		while(set.next()){
			String result=set.getString(1);
			String[] strs=result.split("_");
			String str1=strs[0];
			for(int i=1;i<strs.length;i++){
				String sr=strs[i];
				 char[] ch = sr.toCharArray();  
				    if (ch[0] >= 'a' && ch[0] <= 'z') {  
				        ch[0] = (char) (ch[0] - 32);  
				    }  
				str1 = str1 + new String(ch).toString(); 
			}
			sb1.append("<if test=\""+str1+"!=null and "+str1+"!=''\">").append("\n\t");
			if(t>1){
				sb1.append(" , ");	
			}
			sb1.append(result).append("\n\t");
			sb1.append("</if>").append("\n\t");
			
			sb2.append("<if test=\""+str1+"!=null and "+str1+"!=''\">").append("\n\t");
			if(t>1){
				sb2.append(",");	
			}
			sb2.append("#{"+str1+"}").append("\n\t");
			sb2.append("</if>").append("\n\t");
			t++;
		}
		System.out.println("INSERT INTO "+ tableName +" (");
		System.out.println(sb1.toString());
		System.out.println(" ) VALUES ( ");
		System.out.println(sb2.toString());
		System.out.println(" )");
	}
	
	public void createUpdateSql(String tableName,String dbName) throws Exception{
		String sql="desc "+tableName;
		ps=connection.prepareStatement(sql);
		ResultSet set=ps.executeQuery();
		StringBuffer sb1=new StringBuffer();
		while(set.next()){
			String result=set.getString(1);
			String[] strs=result.split("_");
			String str1=strs[0];
			for(int i=1;i<strs.length;i++){
				String sr=strs[i];
				 char[] ch = sr.toCharArray();  
				    if (ch[0] >= 'a' && ch[0] <= 'z') {  
				        ch[0] = (char) (ch[0] - 32);  
				    }  
				str1 = str1 + new String(ch).toString(); 
			}
			sb1.append("<if test=\""+str1+"!=null and "+str1+"!=''\">").append("\n\t");
			sb1.append(result+"=#{").append(str1).append("},").append("\n\t");
			sb1.append("</if>").append("\n\t");
		}
		System.out.println("UPDATE "+tableName +" <set>");
		System.out.println(sb1.toString());
		System.out.println("</set>");
	}
	

}
