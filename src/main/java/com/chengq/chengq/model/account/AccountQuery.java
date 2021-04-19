package com.chengq.chengq.model.account;

import com.chengq.chengq.ulit.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;


@Data
public class AccountQuery extends PageQuery {

    @ApiModelProperty(value = "名字", example = "zhiling")
    @NotBlank(message = "名字不能为空")
    @Length(message = "名称不能超过个{max}字符", max = 10)
    private String username;


    @Range(message = "年龄范围为{min}到{max}之间", min = 1, max = 100)
    @NotNull (message = "年龄不能为空")
    private Integer age;
}
