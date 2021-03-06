package cn.fllday.learn.auth.utils.ip;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author gssznb
 * @Date 2020/5/18
 * @Descript: 获取ip地址工具类
 */
@Slf4j
public class IpUtils {

    /**
     * 本地ip地址
     */
    public static final String LOCAL_IP = "127.0.0.1";
    /**
     * 默认ip地址
     */
    public static final String DEFAULT_IP = "0:0:0:0:0:0:0:1";
    /**
     * 默认ip地址长度
     */
    public static final int DEFAULT_IP_LENGTH = 15;

    /**
     * 获取ip 地址
     * @param request 请求
     * @return ip地址
     */
    public static String getRealIp(HttpServletRequest request){
        //squid 服务代理
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            //apache服务代理
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            //weblogic 代理
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            //有些代理
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            //nginx代理
            ip = request.getHeader("X-Real-IP");
        }

        /*
         * 如果此时还是获取不到ip地址，那么最后就使用request.getRemoteAddr()来获取
         * */
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if(StringUtils.equals(ip,LOCAL_IP) || StringUtils.equals(ip,DEFAULT_IP)){
                //根据网卡取本机配置的IP
                InetAddress iNet = null;
                try {
                    iNet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    log.error("InetAddress getLocalHost error In HttpUtils getRealIpAddress: [ {} ]" ,e);
                }
                ip= iNet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        //"***.***.***.***".length() = 15
        if(!StringUtils.isEmpty(ip) && ip.length()> DEFAULT_IP_LENGTH){
            if(ip.indexOf(",") > 0){
                ip = ip.substring(0,ip.indexOf(","));
            }
        }
        return ip;
    }

}
