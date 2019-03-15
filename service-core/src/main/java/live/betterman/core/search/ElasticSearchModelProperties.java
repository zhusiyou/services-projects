package live.betterman.core.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zhudawei
 * @date: 2019/2/27
 * @description:
 */
@Data
@AllArgsConstructor
@Builder
public class ElasticSearchModelProperties {
    private String version;
    private String id;
}
