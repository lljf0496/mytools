package file;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

public class PdfUtil {
	
	private static final int wdFormatPDF = 17;
    private static final int xlTypePDF = 0;
    private static final int ppSaveAsPDF = 32;
    private static final String defaultKey="keys";//默认秘钥 
	
    
    /**
	 * pdf 限制复制 更改内容, 只有打印功能
	 * @authour ljf
	 * @time 2018年6月13日
	 * @param orginFile 原文件
	 * @param projId 项目id
	 * @param parentId 原文件id 
	 * @return
	 * @throws Exception
	 */
	public void encryptPdfFile(File orginFile,String projId,String parentId,String targetPath) throws Exception{
		PdfReader reader = new PdfReader(orginFile.getPath());// 待加水印的文件
		File newfile=new File(targetPath);
		if(newfile.exists()) newfile.delete();
		newfile.createNewFile();
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(newfile));// 加完水印的文件
		stamper.setEncryption(null, defaultKey.getBytes(), PdfWriter.AllowCopy, false);//限制复制 更改信息
		stamper.setEncryption(null, defaultKey.getBytes(), PdfWriter.AllowModifyAnnotations, false);//限制复制 更改信息
		stamper.setEncryption(null, defaultKey.getBytes(), PdfWriter.AllowModifyContents, false);//限制复制 更改信息
		stamper.setEncryption(null, defaultKey.getBytes(), PdfWriter.AllowPrinting, true);//限制复制 更改信息
		stamper.close();
		reader.close();
		newfile.renameTo(orginFile);//文件重命名
	}
	
	@Test
	public  void excuteTest() {
		String wordFile = "E:/test_doc/demo1.doc";
		File file = new File(wordFile);
		String pdfFile = "E:/test_doc/demo1.pdf";
		String htmlFile1 = "E:/test_doc/demo2.html";
		String html="https://zhidao.baidu.com/question/493254354.html";
		System.out.println("开始转换...");
		// 开始时间
		long start = System.currentTimeMillis();
		//wordToPdf(wordFile, pdfFile);
		wordToHtml(wordFile,htmlFile1);
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
	      wps = new ActiveXComponent(ActionXNameEnum.WPS_WORD.getActionName());//wps 服务
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
	
	
	private static boolean checkToPdfFormat(String sourcePath,String targetPath,String ext) {
		return checkToPdfFormat(sourcePath,targetPath,ext,"pdf");
	}
	/**
	 * 校验转pdf 格式校验
	 * @authour ljf
	 * @time 2018年9月17日
	 */
	private static boolean checkToPdfFormat(String sourcePath,String targetPath,String ext,String targetExt) {
		File sourceFile = new File(sourcePath);
		if(StringUtils.isEmpty(targetExt)) {targetExt="pdf";}
		if (!(sourcePath.endsWith(ext) && targetPath.endsWith(targetExt))) {
			String errmsg = "文件格式错误！";
			System.out.println(errmsg);
			return false;
		} else if (!sourceFile.exists()) {
			String msg = "解析文件不存在";
			System.out.println(msg);
			return false;
		}else {
			File target = new File(targetPath);  
			if (target.exists()) {
				target.delete();
			} else {
				target.getParentFile().mkdirs();// 创建父级目录
			}
			return true;
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
		 if(!checkToPdfFormat(wordFile,targetPath,"doc")) { return ;}
		 ActiveXComponent app = null;
	       try {
	    	//创建一个线程
	    	ComThread.InitSTA(true);
	        // 打开word 应用
	        app = new ActiveXComponent(ActionXNameEnum.WPS_WORD.getActionName());//wps 服务 如果安装office 使用Word.Application
	        // 设置word不可见
	        app.setProperty("Visible", false);
	        // 获得word中所有打开的文档
	        Dispatch documents = app.getProperty("Documents").toDispatch();
	        Dispatch document = Dispatch.call(documents, "Open", wordFile, false, true).toDispatch();
	        // 如果文件存在的话，不会覆盖，会直接报错，所以我们需要判断文件是否存在
	        
	        // 另存为，将文档报错为pdf，其中word保存为pdf的格式宏的值是17
			//Dispatch.call(doc, "SaveAs", pdfFile, wdFormatPDF);
	        Dispatch.call(document, "ExportAsFixedFormat", targetPath, wdFormatPDF);
	        // 关闭文档
	        Dispatch.call(document, "Close", false); 
	       }catch(Exception e) {
	    	   System.out.println("转换失败===》");
	    	   e.printStackTrace();
	       }finally {// 关闭office
	    	   app.invoke("Quit", 0);
	       }
	}
	
	/**
	 * word html 转换
	 * @authour ljf
	 * @time 2018年9月18日
	 * @param wordFile word 文件
	 * @param targetPath html 文件
	 */
	public static void wordToHtml(String wordFile,String targetPath) {
		if(!checkToPdfFormat(wordFile,targetPath,"doc","html")) { return ;}
		ActiveXComponent app = null;
		try {
			//创建一个线程
			ComThread.InitSTA(true);
			// 打开word 应用
			app = new ActiveXComponent(ActionXNameEnum.WPS_WORD.getActionName());//wps 服务 如果安装office 使用Word.Application
			// 设置word不可见
			app.setProperty("Visible", false);
			// 获得word中所有打开的文档
			Dispatch documents = app.getProperty("Documents").toDispatch();
			Dispatch document = Dispatch.call(documents, "Open", wordFile, false, true).toDispatch();
			// 如果文件存在的话，不会覆盖，会直接报错，所以我们需要判断文件是否存在
			
			// 另存为，将文档报错为pdf，其中word保存为 pdf的格式宏的值是17
			Dispatch.call(document, "SaveAs", targetPath);
			//Dispatch.call(document, "ExportAsFixedFormat", targetPath, wdFormatPDF);
			// 关闭文档
			Dispatch.call(document, "Close", false); 
		}catch(Exception e) {
			System.out.println("转换失败===》");
			e.printStackTrace();
		}finally {// 关闭office
			app.invoke("Quit", 0);
		}
		
	}
	
	/**
	 * ppt 转 pdf
	 * @authour ljf
	 * @time 2018年9月17日
	 * @param inputFile
	 * @param pdfFile
	 */
	private static void pptToPdf(String pptFile, String targetPath) {
		 if(!checkToPdfFormat(pptFile,targetPath,"ppt")) { return ;}
		ActiveXComponent app =null;
		try {
            ComThread.InitSTA(true);
            app = new ActiveXComponent(ActionXNameEnum.WPS_PPT.getActionName());
            Dispatch ppts = app.getProperty("Presentations").toDispatch();
            Dispatch ppt = Dispatch.call(ppts, "Open", pptFile, true, false).toDispatch();
            Dispatch.invoke(ppt, "SaveAs", Dispatch.Method, new Object[]{
            		targetPath,new Variant(ppSaveAsPDF)},new int[1]);
            Dispatch.call(ppt, "Close");
            app.invoke("Quit");
        } catch (Exception e) {
        	 app.invoke("Quit", 0);
        }
    }
	
	/***
     * 
     * Excel转化成PDF
     * 
     * @param inputFile
     * @param pdfFile
     * @return
     */
    private static void excelToPdf(String excelPath, String targetPath) {
    	ActiveXComponent app =null;
    	if(!checkToPdfFormat(excelPath,targetPath,"xls")) { return ;}
    	try {
            ComThread.InitSTA(true);
            app = new ActiveXComponent(ActionXNameEnum.WPS_EXCEL.getActionName());
            app.setProperty("Visible", false);
            app.setProperty("AutomationSecurity", new Variant(3)); // 禁用宏
            Dispatch excels = app.getProperty("Workbooks").toDispatch();
            Dispatch excel = Dispatch
                    .invoke(excels, "Open", Dispatch.Method,
                            new Object[] { excelPath, new Variant(false), new Variant(false) }, new int[9])
                    .toDispatch();
            // 转换格式
            Dispatch.invoke(excel, "ExportAsFixedFormat", Dispatch.Method, new Object[] { new Variant(0), // PDF格式=0
            		targetPath, new Variant(xlTypePDF) // 0=标准 (生成的PDF图片不会变模糊) 1=最小文件
                                            // (生成的PDF图片糊的一塌糊涂)
            }, new int[1]);

            // 这里放弃使用SaveAs
            /*
             * Dispatch.invoke(excel,"SaveAs",Dispatch.Method,new Object[]{
             * outFile, new Variant(57), new Variant(false), new Variant(57),
             * new Variant(57), new Variant(false), new Variant(true), new
             * Variant(57), new Variant(true), new Variant(true), new
             * Variant(true) },new int[1]);
             */
            Dispatch.call(excel, "Close", new Variant(false));
            ComThread.Release();
        } catch (Exception e) {
        	e.printStackTrace();
        }finally{
        	app.invoke("Quit", 0);
        }
    }
	

}
