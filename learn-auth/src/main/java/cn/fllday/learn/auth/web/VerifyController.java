package cn.fllday.learn.auth.web;

import cn.fllday.learn.auth.utils.ip.IpUtils;
import cn.fllday.learn.auth.utils.verify.DefaultVerifyCodeImpl;
import cn.fllday.learn.auth.utils.verify.VerifyCode;
import cn.fllday.learn.auth.utils.verify.VerifyImageEntity;
import cn.fllday.learn.component.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author gssznb
 * @Date 2020/7/18
 * @Descript:
 */
@Controller
@RequestMapping(value = "verify")
@Slf4j
public class VerifyController {

    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping(value = "/captchaImage")
    public void getVerificationCode(HttpServletRequest request, HttpServletResponse response)  {
        VerifyCode verifyCode = new DefaultVerifyCodeImpl();
        VerifyImageEntity verifyImageEntity = new VerifyImageEntity(150, 50);
        verifyCode.generImage(verifyImageEntity);
        redisUtils.set(IpUtils.getRealIp(request), verifyImageEntity.getCode(), 5 * 60);
        //必须设置响应内容类型为图片，否则前台不识别
        response.setContentType("image/png");
        ServletOutputStream os = null;
        try {
            os = response.getOutputStream();
            ImageIO.write(verifyImageEntity.getImage(), "png", os);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("图片生成失败：写入文件流异常: [ {} ]", e.getLocalizedMessage());
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                log.error("流关闭失败: [ {} ]", e.getLocalizedMessage());
            }
        }
    }


}
