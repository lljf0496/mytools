package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;

import org.apache.commons.io.IOUtils;

/**
 * 处理文件编码 
 * @author ljf
 * @time 2018年9月18日
 */
public class FileCoding {
	
	public static void htmlToUtf(String sourcePath) {
		
		
	}
	/**
	 * 
	 * html 重新编码
	 * @authour ljf
	 * @time 2018年9月18日
	 * @param sourcePath
	 * @param targetpath
	 */
	public static void htmlToUtf(String sourcePath,String targetpath) {
		BufferedReader br=null;
		BufferedWriter bw=null;
		try {
			 br=new BufferedReader(new FileReader(sourcePath));
			 bw=new BufferedWriter(new FileWriter(targetpath));
			String str=null;
			while((str=br.readLine())!=null) {
				String strs=new String(str.getBytes("gbk"),"utf-8");
				bw.write(strs);
			}
			File file=new File(sourcePath);
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally {
			try {
				if(br!=null) br.close();
				if(bw!=null) bw.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

}
