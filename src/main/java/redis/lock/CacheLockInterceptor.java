package redis.lock;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.InvocationHandler;

/**
 * 缓存锁实现
 * @author ljf
 * @time 2018年9月19日
 */
public class CacheLockInterceptor implements InvocationHandler {
	
	public static int ERROR_COUNT = 0;
	private Object proxyObj;//代理对象
	
	public CacheLockInterceptor(Object obj) {
		this.proxyObj=obj;
	}

	@Override
	public Object invoke(Object obj, Method method, Object[] args) throws Throwable {
		method.invoke(obj, args);
		return null;
	}

}
