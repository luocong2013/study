package com.customzied.common.validation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * List对象校验
 *
 * @author luocong
 * @version v1.0
 * @since 2023/11/9 16:50
 */
@Getter
@Setter
public class ValidList<T> {

    /**
     * List数组对象数据
     */
    @Valid
    @NotEmpty(message = "list不能为空")
    @Size(max = 200, message = "list数组大小不能超过200")
    private List<T> list;
}
