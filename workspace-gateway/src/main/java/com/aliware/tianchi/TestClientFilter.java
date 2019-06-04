package com.aliware.tianchi;

import com.aliware.tianchi.strategy.DynamicCountLoadBalance;
import com.aliware.tianchi.strategy.utils.Counter;
import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;

/**
 * @author daofeng.xjf
 *
 * 客户端过滤器
 * 可选接口
 * 用户可以在客户端拦截请求和响应,捕获 rpc 调用时产生、服务端返回的已知异常。
 */
@Activate(group = Constants.CONSUMER)
public class TestClientFilter implements Filter {

    private static final Logger logger= LoggerFactory.getLogger(TestClientFilter.class);


    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try{
            Result result = invoker.invoke(invocation);
            if(result.hasException() || result.getValue()==null || result.getValue().equals("")){
                logger.info("Consumer receive value = "+result.getValue());
                Counter.getInstance().decrease();

            }
            else{
                Counter.getInstance().increase();
            }
            return result;
        }catch (Exception e){
            Counter.getInstance().decrease();
            throw e;
        }

    }
}
