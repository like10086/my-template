package com.personal.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: CommonResult
 * @Description: 通用返回类
 * @author: like
 * @date 2024/6/5 15:35
 */
@Data
@ApiModel(value = "通用返回类")
public class CommonResult<T> {
    @ApiModelProperty(value = "返回状态码 200:成功，500:失败")
    private Integer code;

    @ApiModelProperty(value = "返回信息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private T data;

    public CommonResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(200, "操作成功", data);
    }

    public static <T> CommonResult<T> failed(T data) {
        return new CommonResult<>(500, "操作失败", data);
    }

    public static CommonResult<Void> success() {
        return success(null);
    }

    public static CommonResult<Void> failed() {
        return failed(null);
    }

    public static <T> CommonResult<T> error(Integer code, String message) {
        return new CommonResult<>(code, message, null);
    }

    @Override
    public String toString() {
        return "CommonResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
