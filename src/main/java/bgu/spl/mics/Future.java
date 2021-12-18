package bgu.spl.mics;
import java.util.concurrent.TimeUnit;

/**
 * A Future object represents a promised result - an object that will
 * eventually be resolved to hold a result of some operation. The class allows
 * Retrieving the result once it is available.
 * 
 * Only private methods may be added to this class.
 * No public constructor is allowed except for the empty constructor.
 */
public class Future<T> {
	private T result = null;
	/**
	 * This should be the the only public constructor in this class.
	 */
	public Future() { /** assiph's comments: result should be null when we build future.*/

	}
	
	/**
     * retrieves the result the Future object holds if it has been resolved.
     * This is a blocking method! It waits for the computation in case it has
     * not been completed.
     * <p>
     * @return return the result of type T if it is available, if not wait until it is available.
	 * @pre none.
	 * @inv none.
	 * @post not null.
     */
	public T get() throws InterruptedException { /** assiph's comments: get should be implemented with the wait method, try to do without synchronized (no need) */
		synchronized (this) {
			while (result == null)
				this.wait();
		}
				return result;

	}
	
	/**
     * Resolves the result of this Future object.
	 * @pre result!=null && Result==null
	 * @inv none
	 * @post Result=result
     */
	public void resolve (T result) { /** assiph's comments: you shouldn't allow the result to change twice (resolved is allowed only once) do notifyall when finishing, so all the get methods could run again. */
	if (this.result!=null)
		throw new IllegalArgumentException("this future already got resolved.");
	if (result==null)
		throw new IllegalArgumentException("the result we tried to resolve is null, it can't be.");
	synchronized (this) {
		this.result = result;
		this.notifyAll();
		}
	}
	
	/**
     * @return true if this object has been resolved, false otherwise
	 * @pre none
	 * @inv none
	 * @post none
     */
	public boolean isDone() {
		return !(result == null);
	}
	
	/**
     * retrieves the result the Future object holds if it has been resolved,
     * This method is non-blocking, it has a limited amount of time determined
     * by {@code timeout}
     * <p>
     * @param timeout 	the maximal amount of time units to wait for the result.
     * @param unit		the {@link TimeUnit} time units to wait.
     * @return return the result of type T if it is available, if not, 
     * 	       wait for {@code timeout} TimeUnits {@code unit}. If time has
     *         elapsed, return null.
	 * @pre timeout>=0
	 * @inv thread.time <= timeout
	 * @post none
     */
	public T get(long timeout, TimeUnit unit) throws InterruptedException { /** assiph's comments: the thread should time wait - https://www.baeldung.com/java-wait-notify just do wait with timeout, the notify should solve if it came before */
		synchronized (this) {
			if (result == null)
				this.wait(unit.toMillis(timeout));
		}
		return result;
	}
}
