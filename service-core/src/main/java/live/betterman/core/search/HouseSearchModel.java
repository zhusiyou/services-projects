package live.betterman.core.search;


import lombok.Data;

import java.util.Date;

/**
 * @author: zhudawei
 * @date: 2019/2/22
 * @description:
 */
@Data
public class HouseSearchModel implements SearchModel {

    private String houseCode;
    private String version;
    private String roomNumberId;
    private String brokerId;
    private String title;

    private String communityId;
    private String communityName;
    private String[] communityAlias;

    private String addDatetime;

    private String[] tags;
    private double priceTotal;
    private double areaSize;

    private boolean realHouseFlag;
    private boolean showStatus;

    @Override
    public ElasticSearchModelProperties get() {
        return ElasticSearchModelProperties.builder().version(version).id(houseCode).build();
    }
}
