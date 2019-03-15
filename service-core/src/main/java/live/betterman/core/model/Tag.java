package live.betterman.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zhudawei
 * @date: 2019/3/5
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    private String code;
    private String text;
    private String id;
}
