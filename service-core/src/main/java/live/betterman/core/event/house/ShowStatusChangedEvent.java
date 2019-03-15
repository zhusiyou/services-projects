package live.betterman.core.event.house;

import live.betterman.core.entity.House;

/**
 * @author: zhudawei
 * @date: 2019/2/22
 * @description:
 */
public class ShowStatusChangedEvent extends HouseEvent{
    public ShowStatusChangedEvent(House house){
        super(house);
    }
}
