package org.rjgchw.hmall.gateway.web.rest.vo;

/**
 * @author Huangw
 * @date 2021-02-19 13:24
 */
public class AuthorityVO {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AuthorityVO{" +
            "name='" + name + '\'' +
            '}';
    }
}
