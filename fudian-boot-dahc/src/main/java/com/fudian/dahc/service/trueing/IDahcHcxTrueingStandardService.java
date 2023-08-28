package com.fudian.dahc.service.trueing;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author fudian
 * @date 2023-02-09
 */
public interface IDahcHcxTrueingStandardService
{
    /**
     *批量添加核查标准
     * @return java.lang.Boolean
     * @author MCY
     * @date 2023/2/9 15:16
     */
    Boolean addTrueingStandard(List<String> list, String trueingId);
    /**
     *根据id获取核查标准
     * @return java.lang.String
     * @author MCY
     * @date 2023/2/9 16:43
     */
    String queryById(String trueingId);

    /**
     *删除关联数据
     * @return java.lang.String
     * @author MCY
     * @date 2023/2/9 16:43
     */
    Boolean deleteById(String trueingId);
}
