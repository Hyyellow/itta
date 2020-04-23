package com.program.itta.common.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @program: itta
 * @description: COS工具类
 * @author: Mr.Huang
 * @create: 2020-04-23 15:08
 **/
@Component
public class COSClientUtil {
    @Autowired
    private StringRedisTemplate redisTemplate;

    private static long expire_time = 7200;

    private static final String BUCKET_NAME = "hyyyms-1301925880";

    private static final String SECRET_ID = "AKIDYGuOavrlFZeSTIgDAtPyFLAi25rOBp28";

    private static final String SECRET_KEY = "tWtYw3wgqjLgzJM9euvNYZ0A7nRn56pH";

    private static final String OBJECT_PATH = "https://hyyyms-1301925880.cos.ap-guangzhou.myqcloud.com/";

    // 1 初始化用户身份信息(SECRET_ID, SECRET_KEY)
    private static final COSCredentials CRED = new BasicCOSCredentials(SECRET_ID, SECRET_KEY);
    // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
    private static final ClientConfig CLIENT_CONFIG = new ClientConfig(new Region("ap-guangzhou"));
    // 3 生成cos客户端
    private static final COSClient COS_CLIENT = new COSClient(CRED, CLIENT_CONFIG);

    private static COSClient cOSClient;

    public COSClientUtil() {
        cOSClient = new COSClient(CRED, CLIENT_CONFIG);
    }

    /**
     * 销毁
     */
    public void destory() {
        cOSClient.shutdown();
    }

    /**
     * 上传图片
     *
     * @param url
     */
    public void uploadImg2Cos(String url) throws Exception {
        File fileOnServer = new File(url);
        FileInputStream fin;
        try {
            fin = new FileInputStream(fileOnServer);
            String[] split = url.split("/");
            this.uploadFile2Cos(fin, split[split.length - 1]);
        } catch (FileNotFoundException e) {
            throw new Exception("图片上传失败");
        }
    }

    public String uploadGoodsPic(MultipartFile pic, String prefix, String uuidFlag) {
        String picType = pic.getOriginalFilename().substring(pic.getOriginalFilename().lastIndexOf(".") + 1);
        if ("jpg".equals(picType) || "JPG".equals(picType) || "jpeg".equals(picType) || "JPEG".equals(picType) || "png".equals(picType) || "PNG".equals(picType) || "mp4".equals(picType) || "MP4".equals(picType)) {
            try {
                String picPath = uploadFile(pic, prefix, uuidFlag);
                String url = getObjectPath() + picPath;
                return url;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }


    public String uploadFile(MultipartFile file, String prefix, String uuidFlag) throws Exception {
        if (file.getSize() > 50 * 1024 * 1024) {
            throw new Exception("上传图片大小不能超过50M！");
        }
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = prefix + random.nextInt(10000) + System.currentTimeMillis() + substring;
        String url = getObjectPath() + name;
        try {
            redisTemplate.opsForValue().set(uuidFlag, url, expire_time, TimeUnit.SECONDS);
            InputStream inputStream = file.getInputStream();
            this.uploadFile2Cos(inputStream, name);
            return name;
        } catch (Exception e) {
            throw new Exception("图片上传失败");
        }
    }

    /**
     * 获得图片路径
     *
     * @param fileUrl
     * @return
     */
    public String getImgUrl(String fileUrl) {
        return getUrl(fileUrl);
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(String key) {
        // 设置URL过期时间为10年 3600l* 1000*24*365*10
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = COS_CLIENT.generatePresignedUrl(BUCKET_NAME, key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }

    /**
     * 上传到COS服务器 如果同名文件会覆盖服务器上的
     *
     * @param instream 文件流
     * @param fileName 文件名称 包括后缀名
     * @return 出错返回"" ,唯一MD5数字签名
     */
    public String uploadFile2Cos(InputStream instream, String fileName) {
        String ret = "";
        try {
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf(".") + 1)));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            // 上传文件
            PutObjectResult putResult = cOSClient.putObject(BUCKET_NAME, fileName, instream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * Description: 判断Cos服务文件上传时文件的contentType
     *
     * @param filenameExtension 文件后缀
     * @return String
     */
    public static String getcontentType(String filenameExtension) {
        if ("bmp".equalsIgnoreCase(filenameExtension)) {
            return "image/bmp";
        }
        if ("gif".equalsIgnoreCase(filenameExtension)) {
            return "image/gif";
        }
        if ("jpeg".equalsIgnoreCase(filenameExtension) || "jpg".equalsIgnoreCase(filenameExtension)
                || "png".equalsIgnoreCase(filenameExtension)) {
            return "image/jpeg";
        }
        if ("html".equalsIgnoreCase(filenameExtension)) {
            return "text/html";
        }
        if ("txt".equalsIgnoreCase(filenameExtension)) {
            return "text/plain";
        }
        if ("vsd".equalsIgnoreCase(filenameExtension)) {
            return "application/vnd.visio";
        }
        if ("pptx".equalsIgnoreCase(filenameExtension) || "ppt".equalsIgnoreCase(filenameExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if ("docx".equalsIgnoreCase(filenameExtension) || "doc".equalsIgnoreCase(filenameExtension)) {
            return "application/msword";
        }
        if ("xml".equalsIgnoreCase(filenameExtension)) {
            return "text/xml";
        }
        if ("mp4".equalsIgnoreCase(filenameExtension)) {
            return "video/mp4";
        }
        return "image/jpeg";
    }

    public static String getObjectPath() {
        return OBJECT_PATH;
    }

}
