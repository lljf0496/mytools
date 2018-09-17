package Encrypt;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

public class DESEncrypt {
	
	/**
	 * º”√‹
	 * @param datasource
	 * @param password
	 * @return
	 */
	public  static String encrypt( String datasource, String key) {            
        try{
        SecureRandom random = new SecureRandom();
        DESKeySpec desKey = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(desKey);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
        byte[] results= cipher.doFinal(datasource.getBytes("UTF-8")); 
        return Base64.encodeBase64String(results);
        }catch(Exception e){
           e.printStackTrace();
        }
        return null;
	}
	
	/**
	 * Ω‚√‹
	 */
	 public static String decrypt(String dataString, String key){
		 try {
			 if(StringUtils.isEmpty(dataString)) return null;
			 byte[] src=Base64.decodeBase64(dataString);
			 SecureRandom random = new SecureRandom();
			 DESKeySpec desKey = new DESKeySpec(key.getBytes());
			 SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			 SecretKey securekey = keyFactory.generateSecret(desKey);
			 Cipher cipher = Cipher.getInstance("DES");
			 cipher.init(Cipher.DECRYPT_MODE, securekey, random);
			 byte[] resultByte= cipher.doFinal(src);
			 return new String(resultByte,"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
     }
	

}
