package cn.fllday.learn.pojo.user.dto;

import cn.fllday.learn.valid.anno.Status;
import cn.fllday.learn.valid.group.Groups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author gssznb
 * @Date 2020/7/16
 * @Descript:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserDTO extends BaseDTO{

    @NotNull(message = "id 不能为空", groups = Groups.Update.class)
    private Integer id;

    @Length(min = 6, max = 64, message = "用户名长度在 {min} ~ {max} 之间")
    @NotBlank(message = "用户名不能为空")
    private String userName;

    private Integer deptId;

    @Length(max = 16, message = "昵称不能超过 {max} 个字符")
    @NotBlank(message = "昵称不能为空")
    private String nickName;

    private String email;

//    @Pattern(regexp = "^1(3|4|5|6|7|8|9)\d{9}$",
//            message = "您输入的手机号码不符合标准")
    @NotBlank(message = "手机号码不能为空")
    @Length(min = 11, max = 11, message = "手机号码的长度为 {min}")
    private String phonenumber;

    @Status(required = false, defaultVal = "2", status = {"0", "1", "2"}, message = "请选择正确的性别")
    private String sex;

    private String avatar;

    @Length(min = 6, max = 32, message = "密码长度在 {min} ~ {max} 之间")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Length(min = 6, max = 32, message = "重复密码长度在 {min} ~ {max} 之间")
    @NotBlank(message = "重复密码不能为空")
    private String repeatPassword;

    private String remark;



}
