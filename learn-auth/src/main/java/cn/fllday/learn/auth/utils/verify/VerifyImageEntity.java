package cn.fllday.learn.auth.utils.verify;

import lombok.*;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * @author gssznb
 * @Date 2020/7/18
 * @Descript:
 */
@Getter
@Setter
public class VerifyImageEntity implements Serializable {


    private String code;

    private int width;

    private int height;

    private int typeIntRgb;

    private BufferedImage image;

    public VerifyImageEntity(int width, int height) {
        this(width, height, BufferedImage.TYPE_INT_RGB);
    }

    private VerifyImageEntity(int width, int height, int typeIntRgb) {
        this.width = width;
        this.height = height;
        this.typeIntRgb = typeIntRgb;
        image = new BufferedImage(width, height, typeIntRgb);
    }
}
