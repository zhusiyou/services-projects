package live.betterman.core.entity;

import lombok.Data;

/**
 * @author: zhudawei
 * @date: 2019/2/26
 * @description: 门店
 */
@Data
public class Store {
    private String id;

    /**
     * 门店名称
     */
    private String storeName;
    /**
     * 营业执照
     */
    private String licensePicPath;
}
