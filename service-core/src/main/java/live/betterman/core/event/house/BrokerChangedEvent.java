package live.betterman.core.event.house;

import live.betterman.core.entity.House;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhudawei
 * @date: 2019/2/22
 * @description: 跟单人变更事件
 */
@Data
public class BrokerChangedEvent extends HouseEvent implements Serializable {
    private String oldBrokerId;

    public BrokerChangedEvent(House house, String oldBrokerId){
        super(house);
        this.oldBrokerId = oldBrokerId;
    }
}
