package org.rjgchw.hmall.gateway.web.rest.vo;

import java.util.List;

import org.springframework.cloud.client.ServiceInstance;

/**
 *
 * @author Huangw
 * @date 2021-02-23 17:38
 */
public class RouteVO {

    private String path;

    private String serviceId;

    private List<ServiceInstance> serviceInstances;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public List<ServiceInstance> getServiceInstances() {
        return serviceInstances;
    }

    public void setServiceInstances(List<ServiceInstance> serviceInstances) {
        this.serviceInstances = serviceInstances;
    }

    @Override
    public String toString() {
        return "RouteVO{" +
            "path='" + path + '\'' +
            ", serviceId='" + serviceId + '\'' +
            ", serviceInstances=" + serviceInstances +
            '}';
    }
}
