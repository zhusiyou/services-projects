package live.betterman.core.entity;

import lombok.Data;

/**
 * @author: zhudawei
 * @date: 2019/2/26
 * @description:经纪人
 */
@Data
public class Broker {

    /**
     * 经纪人ID
     */
    private String brokerId;
    /**
     * 经纪人工号
     */
    private String brokerNo;

    private String brokerName;

    private String brokerPhone;

    private String avatar;
    /**
     * 经纪人二维码
     */
    private String brokerQRCodePic;
    /**
     * 经纪人信息卡图片
     */
    private String brokerIDCardPic;

//    /// <summary>
//    /// 从业年限：1
//    /// </summary>
//    private double BrokerSeniority
//    /// <summary>
//    /// 服务商圈
//    /// </summary>
//    private List<ServiceCycle> ServiceCycles
//    /// <summary>
//    /// 经纪人特长列表
//    /// </summary>
//    private List<BrokerTag> BrokerTags
//    /// <summary>
//    /// 熟悉的小区列表
//    /// </summary>
//    private List<KnowCommunity> KnowCommunitys

    /**
     * 经纪人状态
     */
    private String status;

//    /// <summary>
//    /// 经纪人类别
//    /// </summary>
//    private String Type


//    /// <summary>
//    /// 客户对经纪人评论
//    /// </summary>
//    private List<BrokerComment> BrokerComment{ get; set; }
//    /// <summary>
//    /// 经纪人带看量(总量；二手房+租房)
//    /// modify by diyaguang 20180709 修改带看总量，该值不需要赋值，直接统计使用。
//    /// </summary>
//    private int BrokerFollowCount {
//        get { return BrokerHouseFollowCount + BrokerRentFollowCount; }
//    }
//    /// <summary>
//    /// 经纪人二手房带看量
//    /// </summary>
//    private int BrokerHouseFollowCount{ get; set; }
//    /// <summary>
//    /// 经纪人租房带看量
//    /// </summary>
//    private int BrokerRentFollowCount
//    /// <summary>
//    /// 环信账号
//    /// </summary>
//    private String EmobuserName
//    /// <summary>
//    /// 环信密码
//    /// </summary>
//    private String EmobPassword


    /**
     * 所属店id
     */
    private String storeId;

    /**
     * 职位ID
     */
    private String positionId;

    /**
     * 经纪人Rank分值
     */
    private int rankValue;
}
