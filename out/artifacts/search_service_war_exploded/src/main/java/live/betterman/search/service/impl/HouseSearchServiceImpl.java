package live.betterman.search.service.impl;

import live.betterman.core.search.HouseSearchModel;
import live.betterman.core.service.HouseSearchService;
import live.betterman.search.BaseSearchService;
import org.springframework.stereotype.Service;

/**
 * @author: zhudawei
 * @date: 2019/2/26
 * @description:
 */
@Service
public class HouseSearchServiceImpl extends BaseSearchService<HouseSearchModel> implements HouseSearchService {

    private static String indexAliasName = "test-house";

    @Override
    protected String getIndexName() {
        return indexAliasName;
    }

}
