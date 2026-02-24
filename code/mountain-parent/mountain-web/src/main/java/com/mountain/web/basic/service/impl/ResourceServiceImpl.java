package com.mountain.web.basic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mountain.web.basic.mapper.ResourceMapper;
import com.mountain.web.basic.pojo.po.Resource;
import com.mountain.web.basic.service.ResourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 针对表【basic_resource(基础设置-资源)】的数据库操作Service实现
 *
 * @author luocong
 * @version v1.0
 * @since 2026/2/10 15:30
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

    @Override
    public Resource selectByUrlRequestType(String url, String requestType) {
        if (StringUtils.isAnyBlank(url, requestType)) {
            return null;
        }
        return lambdaQuery().eq(Resource::getUrl, url).eq(Resource::getRequestType, requestType).one();
    }
}




