package live.betterman.core.search;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * @author: zhudawei
 * @date: 2019/2/27
 * @description:
 */
public interface SearchModel extends Supplier<ElasticSearchModelProperties>, Serializable {
}
