package file;

import java.io.InputStream;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import sun.misc.BASE64Encoder;

public class down {
	
	/**
	 *  文件下载 
	 *
	 * @authour ljf
	 * @time 2018年9月20日
	 * @param res 相应
	 * @param req 请求
	 * @param fileName 文件名称
	 * @param is //文件输入流
	 * @return
	 * @throws Exception
	 */
	
	private boolean downFiles(HttpServletResponse res,HttpServletRequest req,String fileName,InputStream is) throws Exception {
		try{
			synchronized (this) {
				res.setContentType("application/octet-stream; charset=utf-8");
				String userAgent=req.getHeader("user-agent").toLowerCase();
				if(userAgent.contains("safari") && !userAgent.contains("chrome")) {//mac safari
					String codefileName=URLEncoder.encode(fileName, "UTF-8");
					fileName += "\"" + codefileName +"\"; filename*=utf-8''"+codefileName;
				}else if(userAgent.contains("firefox")) {// firefox 
					fileName = "=?UTF-8?B?" + new BASE64Encoder().encode(fileName.getBytes("UTF-8"))+ "?=";
				}else {//IE chrome
					fileName=URLEncoder.encode(fileName, "UTF-8");
				}
				res.setHeader("Content-Disposition", "attachment; filename=" +fileName);
				IOUtils.copy(is, res.getOutputStream());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (is!=null)is.close();
		}
		return true;
	}

}
