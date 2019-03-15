package live.betterman.community.service.impl;

import live.betterman.community.dao.CommunityDao;
import live.betterman.core.service.CommunityService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * @author: zhudawei
 * @date: 2019/3/13
 * @description:
 */
public class CommunityServiceImpl implements CommunityService {
    @Autowired
    private CommunityDao communityDao;

    @Override
    public String getName(String id) {
        if(!StringUtils.hasText(id)){
            return null;
        }

        var entity = communityDao.getById(id);
        if(null != entity){
            return entity.getName();
        }
        return null;
    }

    @Override
    public String[] getAlias(String id) {
        if(!StringUtils.hasText(id)){
            return new String[0];
        }
        var entity = communityDao.getById(id);
        if(null != entity){
            return new String[]{
                    entity.getAliasOne(),
                    entity.getAliasTwo(),
                    entity.getAliasThree()
            };
        }
        return new String[0];
    }
}
