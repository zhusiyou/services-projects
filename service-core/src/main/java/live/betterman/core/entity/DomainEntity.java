package live.betterman.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import live.betterman.core.event.DomainEvent;
import lombok.Data;
import lombok.Synchronized;

import java.util.HashSet;
import java.util.Set;


/**
 * @author: zhudawei
 * @date: 2019/2/22
 * @description:
 */
@Data
public class DomainEntity {

    private String version;


    @JsonIgnore
    private Set<DomainEvent> events = new HashSet<>();

    @Synchronized
    public void addEvent(DomainEvent event){
        events.add(event);
    }
}
