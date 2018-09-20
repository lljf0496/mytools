package function;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * fulter 使用
 * @author ljf
 * @time 2018年9月15日
 */
public class futureUtil {

	private ExecutorService executor = Executors.newFixedThreadPool(8);
	
	/**
	 * 异步执行代码 没用返回值
	 * @time 2018年9月15日
	 * @param task 任务
	 */
	public  void  fulterTask(Runnable task){
		 executor.execute(task);
	}
	
	/**
	 * 异步执行代码 有返回值
	 * @time 2018年9月15日
	 * @param task 任务
	 * @return
	 */
	public <T> Future<T>  fulterTask(Callable<T> task){
		Future<T> future= executor.submit(task);
		return future;
	}
	
	/**
	 * 获取异步处理结果
	 * @time 2018年9月15日
	 * @param future 执行任务
	 * @return 结果
	 * @throws Exception
	 */
	public <T> T getFutureRestult(Future<T> future) throws Exception{
		return future.get();
	}

}
