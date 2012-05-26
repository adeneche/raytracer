package htracer.utility;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class RNG extends Random {
	private static RNG singleton;
	
	public static RNG instance(long seed) {
		if (singleton == null) singleton = new RNG(seed);
		return singleton;
	}
	
	public static RNG instance() {
		return instance(System.nanoTime());
	}
	
  private Lock l = new ReentrantLock();
  private long u;
  private long v = 4101842887655102017L;
  private long w = 1;
  
  public RNG() {
    this(System.nanoTime());
  }
  public RNG(long seed) {
    l.lock();
    u = seed ^ v;
    nextLong();
    v = u;
    nextLong();
    w = v;
    nextLong();
    l.unlock();
  }
  
  public long nextLong() {
    l.lock();
    try {
      u = u * 2862933555777941757L + 7046029254386353087L;
      v ^= v >>> 17;
      v ^= v << 31;
      v ^= v >>> 8;
      w = 4294957665L * (w & 0xffffffff) + (w >>> 32);
      long x = u ^ (u << 21);
      x ^= x >>> 35;
      x ^= x << 4;
      long ret = (x + v) ^ w;
      return ret;
    } finally {
      l.unlock();
    }
  }
  
  protected int next(int bits) {
    return (int) (nextLong() >>> (64-bits));
  }

}