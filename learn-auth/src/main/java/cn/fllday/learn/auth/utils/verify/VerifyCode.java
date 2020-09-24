package cn.fllday.learn.auth.utils.verify;

import java.awt.image.BufferedImage;

/**
 * @author gssznb
 * @Date 2020/7/18
 * @Descript: 图片验证生成
 */
public interface VerifyCode {


    /**
     * 根据随机数获取 imageEntity
     * @param imageEntity
     * @return
     */
    void generImage(VerifyImageEntity imageEntity);

}
