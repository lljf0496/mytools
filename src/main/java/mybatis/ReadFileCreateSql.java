package mybatis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class ReadFileCreateSql {
	
	@Test
	public void creatSql(){
		String path="E:/demo/demo1.txt";
		FileInputStream fis=null;
		BufferedReader br=null;
		 InputStreamReader isr=null;
		try {
			File file=new File(path);
			if(!file.exists()) return;
			 fis=new FileInputStream(file);
			 isr=new InputStreamReader(fis);
			 crateEnity(isr);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(isr!=null) isr.close();
				if(fis!=null)fis.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public void crateEnity(InputStreamReader isr){
		BufferedReader br=null;
		 br=new BufferedReader(isr);
		 try {
			 String data=null;
			 while((data=br.readLine())!=null){
				 String[] strs=data.split("&");
				 String type=strs[2];
				 String abstractinon=strs[0];
				 String field=strs[1];
				 if(StringUtils.isEmpty(type)) type="String";
				 System.out.print(field+",");
				 //System.out.println("/**"+abstractinon+"*/");
				 //System.out.println("private "+type+" "+field+";");
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
				isr.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}

	public void createTableSql(String tableAbstruction,String tableName,InputStreamReader isr){
		System.out.println("CREATE TABLE `"+tableName+"` (");
		BufferedReader br=null;
		try {
				br=new BufferedReader(isr);
				String data=null;
				while((data=br.readLine())!=null){
					String[] strs=data.split("&");
					if("id".equals(strs[1])){
						System.out.println("`"+strs[1]+"` varchar(64) NOT NULL COMMENT '"+strs[0]+"',");
					}else{
						System.out.println("`"+strs[1]+"` varchar(50) DEFAULT NULL COMMENT '"+strs[0]+"',");
					}
				}
				System.out.println("PRIMARY KEY (`id`)");
				System.out.println(") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='"+tableAbstruction+"';");
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					br.close();
					isr.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
	}
	
	/*
	 * CREATE TABLE `app_feedback` (
  `id` varchar(64) NOT NULL COMMENT 'id主键',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(50) DEFAULT NULL COMMENT '创建时间',
  `describe_` varchar(500) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='app用户反馈表';

	 * 
	 */
	
	public void createInsertSql(String tableName,InputStreamReader isr){
		BufferedReader br=null;
		StringBuffer sb1=new StringBuffer();
		StringBuffer sb2=new StringBuffer();
		try {
			br=new BufferedReader(isr);
			String data=null;
			int t=1;
			while((data=br.readLine())!=null){
				String[] strs=data.split("&");
				String field=strs[1];
				String field1=strs[1];
				sb1.append("<if test=\""+field+"!=null and "+field+"!=''\">").append("\n\t");
				if(t>1){
					sb1.append(" , ");	
				}
				sb1.append(field1).append("\n\t");
				sb1.append("</if>").append("\n\t");
				
				sb2.append("<if test=\""+field+"!=null and "+field+"!=''\">").append("\n\t");
				if(t>1){
					sb2.append(",");	
				}
				sb2.append("#{"+field+"}").append("\n\t");
				sb2.append("</if>").append("\n\t");
				t++;
			}
			System.out.println("INSERT INTO "+ tableName +" (");
			System.out.println(sb1.toString());
			System.out.println(" ) VALUES ( ");
			System.out.println(sb2.toString());
			System.out.println(" )");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
				isr.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
