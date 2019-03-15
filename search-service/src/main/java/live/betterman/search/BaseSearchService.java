package live.betterman.search;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import live.betterman.core.search.SearchModel;
import live.betterman.core.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author: zhudawei
 * @date: 2019/2/27
 * @description:
 */
@Slf4j
public abstract class BaseSearchService<T extends SearchModel> implements SearchService<T> {
    @Autowired
    private RestHighLevelClient client;
    private final int maxBulkCount = 2000;

    private final String documentType = ElasticSearchConfig.DOCUMENT_TYPE;
    /**
     * 获取索引名称
     * @return
     */
    protected abstract String getIndexName();

    @Override
    public boolean index(T model) throws IOException{
        String indexName = getIndexName();
        Assert.hasText(indexName, "index name must not be null!");
        return index(model, indexName);
    }

    @Override
    public boolean index(T model, String indexName) throws IOException{
        IndexRequest request = new IndexRequest(indexName, documentType, model.get().getId());
        request.source(JSON.toJSONString(model), XContentType.JSON);

        IndexResponse response = client.index(request);

        System.out.println(response.status());
        System.out.println(response.status().getStatus());

        return response.status().getStatus() - 200 < 99;
    }

    @Override
    public boolean bulkIndex(List<T> models, String indexName) {


        List<Future<Boolean>> futures = new ArrayList<>();

        //使用 guava 开源框架的 ThreadFactoryBuilder 给线程池的线程设置名字
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("bulk-index-thread-%d").build();

        ExecutorService pool = new ThreadPoolExecutor(10, 50, 0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(20),
                namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("es index "+models.size()+" documents");

        int count = 0;

        while (true){
            List<T> list = models.stream().skip(count * maxBulkCount).limit(maxBulkCount).collect(Collectors.toList());
            if(list.size()==0){
                break;
            }

            Future<Boolean> future = pool.submit(()->doBulkIndex(list, indexName));
            futures.add(future);

            if(list.size()<maxBulkCount){
                break;
            }
            count++;
        }

        pool.shutdown();

        boolean result = true;
        for (Future<Boolean> future:futures) {
            try {
                result = result && future.get().booleanValue();
            } catch (InterruptedException e) {
                e.printStackTrace();
                result = false;
                log.error(e.getMessage(), e.getStackTrace());
            } catch (ExecutionException e) {
                e.printStackTrace();
                result = false;
                log.error(e.getMessage(), e.getStackTrace());
            }
        }


        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        return result;

//        try{
//            pool.awaitTermination(20, TimeUnit.SECONDS);
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }finally {
//            stopWatch.stop();
//            System.out.println(stopWatch.prettyPrint());
//        }

//        return futures.stream().allMatch(r->r);
    }

    private boolean doBulkIndex(List<T> models, String indexName) throws IOException {

        if(models==null || models.size()==0){
            return false;
        }
        BulkRequest request = new BulkRequest();

        models.stream().forEach(m->{
            IndexRequest indexRequest = new IndexRequest(indexName, documentType, m.get().getId());
            indexRequest.source(JSON.toJSONString(m), XContentType.JSON);

            request.add(indexRequest);
        });

//        try {
            BulkResponse response = client.bulk(request);
            return response.status().getStatus() - 200 < 99;
//        } catch (IOException e) {
//            e.printStackTrace();
//            log.error(e.getMessage(), e.getStackTrace());
//        }
//        return false;
    }

    @Override
    public boolean bulkIndex(List<T> models){
        String indexName = getIndexName();
        Assert.hasText(indexName, "index name must not be null!");

        return bulkIndex(models, indexName);
    }
}
