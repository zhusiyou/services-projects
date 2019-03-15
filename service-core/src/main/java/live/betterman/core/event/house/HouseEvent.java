package live.betterman.core.event.house;

import live.betterman.core.entity.House;
import live.betterman.core.event.DomainEvent;
import lombok.Data;

/**
 * @author: zhudawei
 * @date: 2019/2/22
 * @description:
 */
@Data
public class HouseEvent extends DomainEvent {
    private House house;
    public HouseEvent(House house){
        this.house = house;
    }
}
