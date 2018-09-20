package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;


/**
 * 文件加密,解密处理
 * @author ljf
 * @time 2018年6月11日
 */
public class FileEncryptUtil {
	
	private static final String defaultKey="";//默认为文件加密秘钥 
	
	private Key getKey() {   
	    try {   
	        KeyGenerator generator = KeyGenerator.getInstance("DES");   
	        generator.init(new SecureRandom(defaultKey.getBytes()));   
	        Key key = generator.generateKey();   
	        generator = null;   
	        return key;
	    } catch (Exception e) {   
	        throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);   
	    }   
	  } 
	
	/**
	 * 文件内容加密
	 * @authour ljf
	 * @time 2018年6月13日
	 * @param file 处理文件
	 * @param destFile 处理后文件
	 * @throws Exception
	 */
	public void encryptFile(File file,File destFile) throws Exception{
		Cipher cipher = Cipher.getInstance("DES");   
	    cipher.init(Cipher.ENCRYPT_MODE, getKey());   
	    InputStream is = new FileInputStream(file);   
	    OutputStream out = new FileOutputStream(destFile);   
	    CipherInputStream cis = new CipherInputStream(is, cipher);   
	    byte[] buffer = new byte[1024];   
	    int r;   
	    while ((r = cis.read(buffer)) > 0) {   
	        out.write(buffer, 0, r);   
	    }   
	    cis.close();   
	    is.close();   
	    out.close();
	}
	
	/**
	 * 文件内容解密
	 *
	 * @authour ljf
	 * @time 2018年6月13日
	 * @param file  处理文件
	 * @param destFile 处理后文件
	 * @throws Exception
	 */
	public void decryptFile(File file,File destFile) throws Exception{
		Cipher cipher = Cipher.getInstance("DES");   
	    cipher.init(Cipher.DECRYPT_MODE, getKey());   
	    InputStream is = new FileInputStream(file);   
	    OutputStream out = new FileOutputStream(destFile);   
	    CipherOutputStream cos = new CipherOutputStream(out, cipher);   
	    byte[] buffer = new byte[1024];   
	    int r;   
	    while ((r = is.read(buffer)) >= 0) {   
	        System.out.println();  
	        cos.write(buffer, 0, r);   
	    }   
	    cos.close();   
	    out.close();   
	    is.close();
	}


}
