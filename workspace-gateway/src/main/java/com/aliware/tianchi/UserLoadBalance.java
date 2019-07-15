package com.aliware.tianchi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.LoadBalance;

import java.util.List;

/**
 * @author daofeng.xjf
 *
 * 负载均衡扩展接口
 * 必选接口，核心接口
 * 此类可以修改实现，不可以移动类或者修改包名
 * 选手需要基于此类实现自己的负载均衡算法
 */
public class UserLoadBalance implements LoadBalance {

    @Override
    public <T> Invoker<T> select(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException {
//        System.out.println(invokers.get(0).getInterface().getCanonicalName());
        String host = ProviderStatus.next();
        for (Invoker<T> invoker : invokers) {
//            System.out.println(invoker.getUrl().getHost());
            if (invoker.getUrl().getHost().equals(host)) {
                ProviderStatus.request(invoker.getUrl().getHost());
                return invoker;
            }
        }
        return null;
//        return invokers.get(ThreadLocalRandom.current().nextInt(invokers.size()));
    }
}
