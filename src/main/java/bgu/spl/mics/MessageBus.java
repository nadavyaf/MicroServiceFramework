package bgu.spl.mics;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * The message-bus is a shared object used for communication between
 * micro-services.
 * It should be implemented as a thread-safe singleton.
 * The message-bus implementation must be thread-safe as
 * it is shared between all the micro-services in the system.
 * You must not alter any of the given methods of this interface. 
 * You cannot add methods to this interface.
 */
public interface MessageBus {

    /**
     * Subscribes {@code m} to receive {@link Event}s of type {@code type}.
     * <p>
     * @param <T>  The type of the result expected by the completed event.
     * @param type The type to subscribe to,
     * @param m    The subscribing micro-service.
     * @pre none
     * @inv none
     * @post m.isSubscribed(Event)
     */
    <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m);
    /**
     * Subscribes {@code m} to receive {@link Broadcast}s of type {@code type}.
     * <p>
     * @param type 	The type to subscribe to.
     * @param m    	The subscribing micro-service.
     * @pre !m.isSubscribed(Broadcast)
     * @inv none
     * @post m.isSubscribed(Broadcast)
     */
    void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m);

    /**
     * Notifies the MessageBus that the event {@code e} is completed and its
     * result was {@code result}.
     * When this method is called, the message-bus will resolve the {@link Future}
     * object associated with {@link Event} {@code e}.
     * <p>
     * @param <T>    The type of the result expected by the completed event.
     * @param e      The completed event.
     * @param result The resolved result of the completed event.
     * @pre e!= null && result!=null
     * @inv none
     * @post future.get(0,Seconds) = result;
     */
    <T> void complete(Event<T> e, T result);

    /**
     * Adds the {@link Broadcast} {@code b} to the message queues of all the
     * micro-services subscribed to {@code b.getClass()}.
     * <p>
     * @param b 	The message to added to the queues.
     * @pre none
     * @inv none
     * @post {m:m is a Microservice, and m.isSubscribed(Broadcast)}, @postQueuemap.get(m.getName()).size+1 = @preQueuemap.get(m.getName()).size
     */
    void sendBroadcast(Broadcast b);

    /**
     * Adds the {@link Event} {@code e} to the message queue of one of the
     * micro-services subscribed to {@code e.getClass()} in a round-robin
     * fashion. This method should be non-blocking.
     * <p>
     * @param <T>    	The type of the result expected by the event and its corresponding future object.
     * @param e     	The event to add to the queue.
     * @return {@link Future<T>} object to be resolved once the processing is complete,
     * 	       null in case no micro-service has subscribed to {@code e.getClass()}.
     * @pre e != null
     * @inv none
     * @post e.isComplete() && future.get(0,Seconds) = result;
     */
    <T> Future<T> sendEvent(Event<T> e);

    /**
     * Allocates a message-queue for the {@link MicroService} {@code m}.
     * <p>
     * @param m the micro-service to create a queue for.
     * @pre none
     * @inv none
     * @post Queuemap.containsKey(m.getName()) && @postQueuemap.size = @preQueuemap.size + 1
     */
    void register(MicroService m);

    /**
     * Removes the message queue allocated to {@code m} via the call to
     * {@link #register(bgu.spl.mics.MicroService)} and cleans all references
     * related to {@code m} in this message-bus. If {@code m} was not
     * registered, nothing should happen.
     * <p>
     * @param m the micro-service to unregister.
     * @pre none
     * @inv none
     * @post @postm.LinkedBlockingQueue = null && !(@postQueuemap.containsKey(m.getName())) && @postQueuemap.size = @preQueuemap.size - 1
     * @post if !(Queuemap.containsKey(m.getName())) then @postQueuemap.size = @preQueuemap.size
     */
    void unregister(MicroService m);

    /**
     * Using this method, a <b>registered</b> micro-service can take message
     * from its allocated queue.
     * This method is blocking meaning that if no messages
     * are available in the micro-service queue it
     * should wait until a message becomes available.
     * The method should throw the {@link IllegalStateException} in the case
     * where {@code m} was never registered.
     * <p>
     * @param m The micro-service requesting to take a message from its message
     *          queue.
     * @return The next message in the {@code m}'s queue (blocking).
     * @throws InterruptedException if interrupted while waiting for a message
     *                              to became available.
     * @pre !m.isRegistered
     * @inv should be blocked as long as the LinkedBlockingList.isEmpty (implemented in the object itself).
     * @post return @prem.LinkedBlockingQueue.peek() && @postm.LinkedBlockingQueue.size = @prem.LinkedBlockingQueue.size -1
     */
    Message awaitMessage(MicroService m) throws InterruptedException;
    
}
