package live.betterman.core.entity;

import lombok.Data;
import lombok.var;

/**
 * @author: zhudawei
 * @date: 2019/2/22
 * @description:
 */
@Data
public class House extends DomainEntity{
    private String id;

    /**
     * 房间号，与租房同用，出现在同一套房子既租又卖
     */
    private String roomNumberId;
    /**
     * 房源编号:FY88416246
     */
    private String houseCode;
    private String brokerId;
    /**
     * 小区ID:22
     */
    private String communityId;
    private double priceTotal;
    /**
     * 面积:100
     */
    private double areaSize;
    /**
     * 朝向:东南
     */
    private String direction;
    private String floor;

    private double rank;
    private String status;
    private String businessState;
    private boolean realHouseFlag;
    private boolean showStatus;

    @Override
    public boolean equals(Object another){
        if(!(another instanceof House)){
            return false;
        }
        var anotherHouse = (House)another;
        return this.brokerId == anotherHouse.brokerId
                && this.priceTotal == anotherHouse.priceTotal;
    }

    @Override
    public int hashCode() {
        return this.houseCode.hashCode();
    }
}
