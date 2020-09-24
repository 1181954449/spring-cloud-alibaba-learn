package cn.fllday.learn.auth.utils.verify;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.UUID;

/**
 * @author gssznb
 * @Date 2020/7/18
 * @Descript: 图片生成方式的默认实现
 */
@Component
public class DefaultVerifyCodeImpl implements VerifyCode{

    @Override
    public void generImage(VerifyImageEntity imageEntity) {
        drowRandomImage(imageEntity);
    }


    public void drowRandomImage(VerifyImageEntity imageEntity) {
        Graphics2D graphics = (Graphics2D) imageEntity.getImage().getGraphics();

        //设置画笔颜色-验证码背景色
        graphics.setColor(Color.WHITE);
        //填充背景
        graphics.fillRect(0, 0, imageEntity.getWidth(), imageEntity.getHeight());

        graphics.setFont(new Font("微软雅黑", Font.BOLD, 30));

        //数字和字母的组合

        String baseNumLetter = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";

        StringBuilder sBuffer = new StringBuilder();

        //旋转原点的 x 坐标
        int x = 10;

        String ch = "";

        Random random = new Random();

        for(int i = 0;i < 4;i++){

            graphics.setColor(getRandomColor());

            //设置字体旋转角度
            //角度小于30度
            int degree = random.nextInt() % 30;

            int dot = random.nextInt(baseNumLetter.length());

            ch = baseNumLetter.charAt(dot) + "";

            sBuffer.append(ch);

            //正向旋转

            graphics.rotate(degree * Math.PI / 150, x, 35);

            graphics.drawString(ch, x, 35);

            //反向旋转

            graphics.rotate(-degree * Math.PI / 150, x, 35);

            x += 38;

        }

        //画干扰线

        for (int i = 0; i <6; i++) {

            // 设置随机颜色

            graphics.setColor(getRandomColor());

            // 随机画线

            graphics.drawLine(random.nextInt(imageEntity.getWidth()), random.nextInt(imageEntity.getHeight()),

                    random.nextInt(imageEntity.getWidth()), random.nextInt(imageEntity.getHeight()));

        }

        //添加噪点

        for(int i=0;i<30;i++){

            int x1 = random.nextInt(imageEntity.getWidth());

            int y1 = random.nextInt(imageEntity.getHeight());

            graphics.setColor(getRandomColor());

            graphics.fillRect(x1, y1, 2,2);

        }

        imageEntity.setCode(sBuffer.toString());
    }

    /**
     * 随机取色
     */

    private static Color getRandomColor() {

        Random ran = new Random();

        Color color = new Color(ran.nextInt(256),

                ran.nextInt(256), ran.nextInt(256));

        return color;

    }
}
