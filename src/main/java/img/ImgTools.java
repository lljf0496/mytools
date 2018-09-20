package img;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImgTools {
	
	/**
	 * Thumbnails 详细说明
	 * @authour ljf
	 * @time 2018年9月20日
	 * @param sourceImg 源图片
	 * @param targetImg 目标图片 
	 * @param waterImag 水印图片
	 * @throws IOException
	 */
	public void detail(String sourceImg,String targetImg,String waterImag) throws IOException {
		 /*
         * size(width,height) 若图片横比200小，高比300小，不变
     		* 若图片横比200小，高比300大，高缩小到300，图片比例不变 若图片横比200大，高比300小，横缩小到200，图片比例不变
     		* 若图片横比200大，高比300大，图片按比例缩小，横为200或高为300
         */
		Thumbnails.of(sourceImg).size(200, 300).toFile(targetImg);
		Thumbnails.of(sourceImg).width(200).toFile(targetImg);
		Thumbnails.of(sourceImg).height(300).toFile(targetImg);
		// 按照比例进行缩放
		Thumbnails.of(sourceImg).scale(0.25f).toFile(targetImg);
		// 固定指定大小缩放
        Thumbnails.of(sourceImg).size(120, 120).keepAspectRatio(false).toFile(targetImg);
        // rotate(角度),正数：顺时针 负数：逆时针
        Thumbnails.of(sourceImg).rotate(90).toFile(targetImg);
        // watermark(位置，水印图，透明度)
        Thumbnails.of(sourceImg).size(1280, 1024)
        .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(waterImag)), 0.5f)
        .outputQuality(0.8f).toFile(targetImg);
        // 裁剪 图片中心400*400的区域
        Thumbnails.of(sourceImg)
        .sourceRegion(Positions.CENTER, 400, 400) // 裁剪
        .size(200, 200).keepAspectRatio(false) // 压缩
        .toFile(targetImg);
        // 裁剪 指定位置
        Thumbnails.of(sourceImg)
        .sourceRegion(600, 500, 400, 400)//位置 x y  x1 y1
        .size(200, 200).keepAspectRatio(false)
        .toFile(targetImg);
        //转化图像格式
        Thumbnails.of(sourceImg)
        .outputFormat("png")//转换格式
        .toFile(targetImg);
        
        //输出到BufferedImage
        BufferedImage thumbnail = Thumbnails.of(sourceImg).size(1280, 1024).asBufferedImage();
		
	}
	
	
	
	/**
	 * @authour ljf
	 * @time 2018年9月20日
	 * @param sourceImg 原图文件的路径
	 * @param targetImg 压缩后文件的路径
	 */
	public void imgCompress(String sourceImg,String targetImg) {
		try {
			Thumbnails.of(sourceImg)
			.scale(1f).outputQuality(0.5f) 
			.toFile(targetImg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *   图片压缩 并写出
	 * @authour ljf
	 * @time 2018年9月20日
	 * @param sourceImg
	 * @param out
	 */
	public void imgCompress(String sourceImg,OutputStream out) {
		try {
			Thumbnails.of(sourceImg)
			.scale(1f).outputQuality(0.5f) 
			.toOutputStream(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
