package cn.fllday.learn.auth.utils.oss;

import cn.fllday.learn.constant.AliyunOssConstant;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: gssznb
 */
public class AliyunOssClientPool {

    private static Map<String, OSS> ossClientPools = new HashMap<>();

    private static byte[] mutex = new byte[0];

    /**
     * 根据 bucketName 获取 OSS 对象
     * @param bucketName
     * @return
     */
    public static OSS getOssClient(String bucketName) {
        OSS oss = ossClientPools.get(bucketName);
        if (oss == null) {
            synchronized (mutex) {
                OSS build = new OSSClientBuilder()
                        .build(AliyunOssConstant.OSS_PROTOCOL + AliyunOssConstant.OSS_ENDPOINT,
                                AliyunOssConstant.ACCESS_KEY_ID, AliyunOssConstant.ACCESS_KEY_SECRET);
                ossClientPools.put(bucketName, build);
                return build;
            }
        }
        return oss;
    }

}
