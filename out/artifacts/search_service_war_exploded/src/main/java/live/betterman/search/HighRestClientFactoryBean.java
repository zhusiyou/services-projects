package live.betterman.search;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: zhudawei
 * @date: 2019/2/27
 * @description:
 */
public class HighRestClientFactoryBean implements FactoryBean<RestHighLevelClient>, InitializingBean, DisposableBean {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private RestHighLevelClient restHighLevelClient;
    private ElasticSearchConfig esConfig;

    public void setEsConfig(ElasticSearchConfig config) {
        this.esConfig = config;
    }

    @Override
    public void destroy() throws Exception {
        try {
            log.info("Closing elasticSearch  client");
            if(null != restHighLevelClient){
                restHighLevelClient.close();
            }
        } catch (final Exception e) {
            log.error("Error closing ElasticSearch client: ", e);
        }
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public Class<?> getObjectType() {
        return RestHighLevelClient.class;
    }

    @Override
    public RestHighLevelClient getObject() throws Exception {
        return restHighLevelClient;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        createClient();
    }

    private void createClient() throws Exception {
        String clusterNodes = esConfig.getClusterNodes();

        RestClientBuilder restClientBuilder = RestClient.builder(this.getHttpHosts(clusterNodes));

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        //xpack账户认证
        if(StringUtils.hasText(esConfig.getUsername()) && StringUtils.hasText(esConfig.getPassword())){
            credentialsProvider.setCredentials(AuthScope.ANY , new UsernamePasswordCredentials(esConfig.getUsername() , esConfig.getPassword()));
        }

        //认证和线程数
        restClientBuilder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            if(null != esConfig.getThreadCount()){
                httpClientBuilder.setDefaultIOReactorConfig(IOReactorConfig.custom().setIoThreadCount(esConfig.getThreadCount()).build());
            }
            return httpClientBuilder;
        });

        //超时超时设置
        restClientBuilder.setRequestConfigCallback(requestConfigCallback -> {
            if(null != esConfig.getConnectTimeout()){
                requestConfigCallback.setConnectTimeout(esConfig.getConnectTimeout());
            }
            if(null != esConfig.getSocketTimeout()){
                requestConfigCallback.setSocketTimeout(esConfig.getSocketTimeout());
            }
            return requestConfigCallback;
        });

        //重试时间
        if(null != esConfig.getMaxRetryTimeout()){
            restClientBuilder.setMaxRetryTimeoutMillis(esConfig.getMaxRetryTimeout());
        }
        restHighLevelClient = new RestHighLevelClient(restClientBuilder);
    }

    /**
     * description: 创建分割nodes，创建httpHost数组
     * @param
     * @return {@link HttpHost[]}
     * createdBy:wanhao
     * created:2018/11/28
     * */
    public HttpHost[] getHttpHosts(String clusterNodes){
        Assert.hasText(clusterNodes, "Cluster nodes source must not be null or empty!");

        //分割node节点
        String[] nodes = StringUtils.delimitedListToStringArray(clusterNodes, esConfig.getNodesSeparator());

        List<HttpHost> httpHosts = Arrays.stream(nodes).map(node -> {
            HttpHost httpHost;
            //分割host和端口
            String[] hostAndPort = StringUtils.split(node, esConfig.getHostPortSeparator());

            Assert.isTrue(hostAndPort.length == 2,String.format("在[%s]集群节点中, node:[%s]存在错误 ! 格式必须是host:port!", clusterNodes, node));

            String host = hostAndPort[0].trim();
            String port = hostAndPort[1].trim();

            Assert.hasText(host, String.format("在node:[%s]没找到host!", node));
            Assert.hasText(port, String.format("在node:[%s]没找到port!", node));

            if (StringUtils.hasText(esConfig.getScheme())) {
                httpHost = new HttpHost(host, Integer.parseInt(port), esConfig.getScheme());
            } else {
                httpHost = new HttpHost(host, Integer.parseInt(port));
            }
            return httpHost;
        }).collect(Collectors.toList());

        return httpHosts.toArray(new HttpHost[httpHosts.size()]);
//        return ArrayUtil.toArray(httpHosts, HttpHost.class);
    }
}
