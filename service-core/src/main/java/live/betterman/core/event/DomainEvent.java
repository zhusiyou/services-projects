package live.betterman.core.event;

import lombok.Data;

/**
 * @author: zhudawei
 * @date: 2019/2/22
 * @description:
 */
@Data
public class DomainEvent {
    private String eventName;
    public String getEventName(){
        return this.getClass().getName();
    }
}
