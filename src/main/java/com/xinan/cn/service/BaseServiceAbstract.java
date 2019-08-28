package com.xinan.cn.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseServiceAbstract {
    /**
     * 分页查询结果封装
     *
     * @param count
     * @param data
     * @return
     */
    public Map<String, Object> pageParamJoin(Long count, List<?> data) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", count);
        map.put("data", data);
        return map;
    }
}
