package mq;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Yd on  2017-12-15
 * @Description：
 **/
@Data
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = -5988796023666818544L;
    private String name;
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
