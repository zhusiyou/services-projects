package live.betterman.core.service;

import live.betterman.core.search.SearchModel;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author: zhudawei
 * @date: 2019/2/27
 * @description:
 */
public interface SearchService<T extends SearchModel> {
    boolean index(T model) throws IOException;
    boolean index(T model, String indexName) throws IOException;
    boolean bulkIndex(List<T> models);
    boolean bulkIndex(List<T> models, String indexName);
}
