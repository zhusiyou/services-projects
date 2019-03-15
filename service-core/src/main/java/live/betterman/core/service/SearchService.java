package live.betterman.core.service;

import live.betterman.core.search.SearchModel;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CompletableFuture;
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
    CompletableFuture<Boolean> asyncBulkIndex(List<T> models, String indexName);
    T get(String id) throws IOException;
//    T get(String id, String[] includes) throws IOException;
//    T get(String id, String[] includes, String[] excludes) throws IOException;

}
