package live.betterman.core.entity;

import lombok.Data;

/**
 * @author: zhudawei
 * @date: 2019/3/13
 * @description:
 */
@Data
public class Community extends DomainEntity {
    private String id;
    private String name;
    private String aliasOne;
    private String aliasTwo;
    private String aliasThree;
}
