package file;

import java.io.File;

import org.junit.Test;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class PdfUtil {
	
	
	@Test
	public  void excuteTest() {
		String wordFile = "E:/test_doc/demo1.doc";
		File file = new File(wordFile);
		String pdfFile = "E:/test_doc/demo1.pdf";
		String pdfFile1 = "E:/test_doc/demo2.doc";
		String html="https://zhidao.baidu.com/question/493254354.html";
		System.out.println("开始转换...");
		// 开始时间
		long start = System.currentTimeMillis();
		//wordToPdf(wordFile, pdfFile);
		htmlToWordWPS(html,pdfFile1);
		// 结束时间
		long end = System.currentTimeMillis();
		System.out.println("转换成功，用时：" + (end - start) + "ms");
	}
	
	/**
	 * 
	 * 网页转 word 
	 * @authour ljf
	 * @time 2018年9月17日
	 * @param htmlPath
	 * @param wordPath
	 */
	public static void htmlToWordWPS(String htmlPath, String wordPath) {
	    ComThread.InitSTA();
	    ActiveXComponent wps = null;
	    try {
	      wps = new ActiveXComponent(ActionXNameEnum.WPS.getActionName());//wps 服务
	      wps.setProperty("Visible", false);
	      ActiveXComponent doc = wps.invokeGetComponent("Documents").invokeGetComponent("Open", new Variant(htmlPath));
	      doc.invoke(doc, "SaveAs", Dispatch.Method, new Object[] { wordPath, 0 }, new int[1]);
	      doc.invoke("Close");
	      doc.safeRelease();
	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	      if (wps != null) {
	        wps.invoke("Quit");
	        wps.safeRelease();
	      }
	      ComThread.Release();
	    }
	  }
	
	/**
	 * word 转 pdf
	 * @param wordFile word文件
	 * @param targetPath 生成目标地址
	 * @authour ljf
	 * @time 2018年9月17日
	 */
	public static void wordToPdf(String wordFile,String targetPath) {
		File sourceFile = new File(wordFile);
		if (!(wordFile.endsWith(".doc") && targetPath.endsWith(".pdf"))) {
			String errmsg = "文件格式错误！";
			System.out.println(errmsg);
			return;
		} else if (!sourceFile.exists()) {
			String msg = "解析文件不存在";
			System.out.println(msg);
			return;
		}
		 ActiveXComponent app = null;
	       try {
	    	   //创建一个线程
	    	 //ComThread.InitSTA();  
	        // 打开word 应用
	        app = new ActiveXComponent(ActionXNameEnum.WPS.getActionName());//wps 服务 如果安装office 使用Word.Application
	        // 设置word不可见
	        app.setProperty("Visible", false);
	        // 获得word中所有打开的文档
	        Dispatch documents = app.getProperty("Documents").toDispatch();
	        Dispatch document = Dispatch.call(documents, "Open", wordFile, false, true).toDispatch();
	        // 如果文件存在的话，不会覆盖，会直接报错，所以我们需要判断文件是否存在
	        File target = new File(targetPath);  
			if (target.exists()) {
				target.delete();
			} else {
				target.getParentFile().mkdirs();// 创建父级目录
			}
	        // 另存为，将文档报错为pdf，其中word保存为pdf的格式宏的值是17
			//Dispatch.call(doc, "SaveAs", pdfFile, wdFormatPDF);
	        Dispatch.call(document, "ExportAsFixedFormat", targetPath, 17);//文件另存为 pdf 格式
	        // 关闭文档
	        Dispatch.call(document, "Close", false); 
	       }catch(Exception e) {
	    	   System.out.println("转换失败===》");
	    	   e.printStackTrace();
	       }finally {// 关闭office
	    	   app.invoke("Quit", 0);
	       }
		
	}

}
