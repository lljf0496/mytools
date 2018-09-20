package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 文件压缩工具
 * 
 * @author ljf
 * @time 2018年6月11日
 */
public class ZipUtil {

	private final static  String DEFAULT_FILE_PATH="";
	
	private boolean saveFile(InputStream is,String fileName) {
		String path=DEFAULT_FILE_PATH+fileName;
		try {
			File newfile=new File(path);
			FileUtils.copyToFile(is, newfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
		
	}
	
	/**
	 * 文件减压缩保存
	 * 
	 * @authour ljf
	 * @time 2018年6月12日
	 * @param file    压缩文件文件 .zip
	 * @param fileListInfo
	 * @param unzipDirPath 解压缩文件目录
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public  void unZipFile(File file, String projCode,String unzipDirPath) throws IOException {
		String originalName = file.getName();
		if (-1 == originalName.indexOf(".zip")) {return ;} // 不解压文件
		File tempFileDir = new File(unzipDirPath);
		if (!tempFileDir.exists()) {
			tempFileDir.mkdirs();
		}
		ZipEntry zipEntry = null;
		File entryFile = null;
		InputStream is=null;
		ZipFile zip = null;
		// 转为zipfile
		try {
			zip = new ZipFile(file, Charset.forName("GBK"));
			@SuppressWarnings("unchecked")
			Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip.entries();
			while (entries.hasMoreElements()) {
				zipEntry = entries.nextElement();
				String ext = "";
				String fileName = zipEntry.getName();
				fileName = -1 < fileName.indexOf("/") ? fileName.substring(fileName.lastIndexOf("/") + 1) : fileName;
				long fileSize = zipEntry.getSize() / 1024;//文件大小 kb
				try {
					ext = FilenameUtils.getExtension(fileName);
				} catch (Exception e) {
					e.printStackTrace();
				} // 无扩展名
				if (StringUtils.isEmpty(ext)) continue;// 无后缀名不储存
				String newChildFilePath = unzipDirPath + fileName;
				entryFile = new File(newChildFilePath);
				if (entryFile.exists()) entryFile.delete();
				entryFile.createNewFile();
				is=zip.getInputStream(zipEntry);
			    saveFile( is, fileName);
			}
		} finally {
			if (is != null) is.close();
		}
	}

	/**
	 * 解压rar格式压缩包。
	 * 
	 * @authour ljf
	 * @time 2018年6月12日
	 * @param sourceRar
	 *            压缩资源
	 * @param destDir
	 *            解压资源
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public static boolean unRARFile(String sourceRar) throws Exception {
		boolean flag = false;
		try {
			if (File.separator.equals("/") && (sourceRar.endsWith(".rar") || sourceRar.endsWith(".RAR"))) {
				Process p = Runtime.getRuntime().exec("unrar e " + sourceRar);
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}


	/**
	 * 
	 * @param zip  压缩的目的地址
	 * @param zipOut
	 * @param srcFile
	 *            被压缩的文件信息
	 * @param path
	 *            在zip中的相对路径
	 * @throws IOException
	 */
	private static void handlerFile(String zipSavePath, ZipOutputStream zipOut, File srcFile, String path)
			throws IOException {
		if (!"".equals(path) && !path.endsWith(File.separator)) {
			path += File.separator;
		}
		if (srcFile.isDirectory()) {
			File[] files = srcFile.listFiles();
			if (files.length == 0) {
				zipOut.putNextEntry(new ZipEntry(path + srcFile.getName() + File.separator));
				zipOut.closeEntry();
			} else {
				for (File file : files) {
					handlerFile(zipSavePath, zipOut, file, path + srcFile.getName());
				}
			}
		} else {
			byte[] buffer = new byte[1024];
			InputStream in = new FileInputStream(srcFile);
			zipOut.putNextEntry(new ZipEntry(path + srcFile.getName()));
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				zipOut.write(buffer, 0, len);
			}
			in.close();
			zipOut.closeEntry();
		}
	}

	/**
	 * 压缩文件 为zip
	 * @authour ljf
	 * @time 2018年6月12日
	 * @param zipSavePath
	 * @param srcFiles
	 * @throws Exception
	 */
	public static void zipFile(String zipSavePath, List<File> srcFiles) throws Exception {
		try {
			if (zipSavePath.endsWith(".zip") || zipSavePath.endsWith(".ZIP")) {
				ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(new File(zipSavePath)));
				srcFiles.stream().forEach(file -> {
					try {
						handlerFile(zipSavePath, zipOut, file, "");
					} catch (IOException e) {
						e.printStackTrace();
						throw new RuntimeException("运行异常");
					}
				});
				zipOut.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
