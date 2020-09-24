package cn.fllday.learn.pojo.user.dto;

import cn.fllday.learn.valid.anno.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

/**
 * @author gssznb
 * @Date 2020/7/16
 * @Descript:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserDTO {

    @Size(min = 6, max = 64, message = "用户名长度在 6 ~ 64 之间")
    private String userName;

    private String nickName;


    private String email;

//    @Pattern(regexp = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\\\d{8}$",
//            message = "您输入的手机号码不符合标准")
    private String phonenumber;

    @Status(required = false, defaultVal = "2", status = {"0", "1", "2"}, message = "请选择正确的性别")
    private String sex;

    private String avatar;

    private String password;

    private String repeatPassword;

    private String remark;

}
