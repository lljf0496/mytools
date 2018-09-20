package redis.lock;

import redis.lock.annotation.CacheLock;
import redis.lock.annotation.LockedObject;

public interface SeckillInterface {
	
	@CacheLock(lockedPrefix="TEST_PREFIX")
	public void secKill(String arg1,@LockedObject Long arg2);
}
