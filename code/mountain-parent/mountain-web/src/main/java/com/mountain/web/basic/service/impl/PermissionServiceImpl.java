package com.mountain.web.basic.service.impl;

import com.mountain.web.basic.pojo.po.Resource;
import com.mountain.web.basic.pojo.po.RoleResource;
import com.mountain.web.basic.service.PermissionService;
import com.mountain.web.basic.service.ResourceService;
import com.mountain.web.basic.service.RoleResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 鉴权服务接口实现类
 *
 * @author luocong
 * @version 1.0
 * @since 2026/2/24 14:53
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final ResourceService resourceService;
    private final RoleResourceService roleResourceService;

    @Override
    public List<String> listAuthorities(String url, String requestType) {
        Resource resource = resourceService.selectByUrlRequestType(url, requestType);
        if (resource == null) {
            return null;
        }
        return listAuthorities(resource);
    }

    /**
     * 递归获取url对应权限的角色编码集合
     * 如url对应权限控制类型是角色授权，则查询角色权限配置信息
     * 如url对应授权控制类型是跟随父资源授权，则递归查询父资源的权限角色编码集合
     */
    private List<String> listAuthorities(Resource resource) {
        switch (resource.getAuthorizeType()) {
            case ROLE_AUTHORIZE -> {
                List<RoleResource> roleResources = roleResourceService.selectByResourceCode(resource.getResourceCode());
                return roleResources.stream().map(RoleResource::getRoleCode).collect(Collectors.toList());
            }
            case PARENT_AUTHORIZE -> {
                Resource parentResource = resourceService.getById(resource.getParentId());
                if (parentResource == null) {
                    log.warn("the parent resource is null, resourceId: {}", resource.getId());
                    throw new AccessDeniedException("资源配置错误！请联系管理员！");
                }
                return listAuthorities(parentResource);
            }
        }
        return List.of();
    }


}
