package com.zync.single.security.jwt.singlesecurityjwt.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zync.single.security.jwt.singlesecurityjwt.web.model.TbUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 用户Mapper
 * @date 2020/8/30 16:50
 */
@Mapper
public interface TbUserMapper extends BaseMapper<TbUser> {
}