package live.betterman.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zhudawei
 * @date: 2019/2/27
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElasticSearchConfig {
    private String nodesSeparator = ",";

    private String hostPortSeparator = ":";
    public static String DOCUMENT_TYPE = "doc";
    /**
     * description: es集群名称
     * remark:
     * */
    private String clusterName;

    /**
     * description: es节点，使用[',']隔开，端口使用9200,例[localhost:9200,192.168.1.1:9200]
     * remark:
     * */
    private String clusterNodes;

    /**
     * description: 协议(默认'http')
     * remark:
     * */
    private String scheme;

    /**
     * description: xpack用户名
     * remark:
     * */
    private String username;

    /**
     * description: xpack密码
     * remark:
     * */
    private String password;

    /**
     * description: 最大重试超时,默认30秒
     * remark:
     * */
    private Integer maxRetryTimeout;

    /**
     * description: 连接超时,默认1秒
     * remark:
     * */
    private Integer connectTimeout;

    /**
     * description: socket超时,默认30秒
     * remark:
     * */
    private Integer socketTimeout;

    /**
     * description: 线程数(默认Runtime.getRuntime().availableProcessors())
     * remark:
     * */
    private Integer threadCount;
}
