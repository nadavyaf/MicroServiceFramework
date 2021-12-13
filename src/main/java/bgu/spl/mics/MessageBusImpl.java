package bgu.spl.mics;

import bgu.spl.mics.application.objects.GPU;
import bgu.spl.mics.application.services.GPUService;

import javax.swing.plaf.metal.MetalIconFactory;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 *
 *
 */
public class MessageBusImpl implements MessageBus {
	private ConcurrentHashMap <Class<? extends Message>, BlockingQueue<MicroService>> messageMap = new ConcurrentHashMap<>();
	private ConcurrentHashMap <MicroService, LinkedBlockingDeque<Message>> microMap = new ConcurrentHashMap<>();
	private ConcurrentHashMap <Event, Future> futureMap = new ConcurrentHashMap<>();
	private static class SingeltonHolder{//Java things, this way when we import messagebusimpl, it will not create any instance (since the funcion is private), but when we call the function, it will just call the .instance once.
		private static MessageBusImpl instance = new MessageBusImpl();
	}

	public static MessageBusImpl getInstance() {
		return SingeltonHolder.instance;
			}

	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {/**Assiph's comments: if the message doesn't exist,
	 add it with a new LinkedBlockingQueue (FIFO) and then just add the element into that queue.*/
		messageMap.putIfAbsent(type,new LinkedBlockingQueue<>());
		messageMap.get(type).add(m);
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		messageMap.putIfAbsent(type,new LinkedBlockingQueue<>());
		messageMap.get(type).add(m);
	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		futureMap.remove(e).resolve(result);

	}
	public void sendBroadcast(Broadcast b) throws InterruptedException {
		BlockingQueue<MicroService> services = messageMap.get(b);
		//the Iterator is weakly consistent - meaning it will not follow any changes that happens in the BlockingQueue after it started.
		for (MicroService m : services) {
			microMap.get(m).putFirst(b);
			m.notifyAll();
		}
	}

	/**
	 *
	 * Assiph's thoughts: send this Event (or broadcast in a different function) and in this block you will also use the function
	 * complete with 2 args: 1.this event, 2.function the initializes the right microservice (it will be a callback to this service
	 * so when the service finishes, you get a result, and also it runs on a different thread and the thread of the messagebus can just
	 * continue).
	 * when we get the result, we will create a Future object, use result on it and return it, this should return it to the student in the
	 * right way. ***the student will get it in the main (CRMSRunner) or in it's StudentService, not sure yet.***
	 */
	@Override
	public <T> Future<T> sendEvent(Event<T> e) throws InterruptedException { /** Assiph's Comments: Will be used by a studentService (or student,not sure) for example
	 it will enter the event to the right queue using the Messagebus (with get instance) and then use the get method (blocking).*/
		if (messageMap.get(e).isEmpty())
			return null;
		Future <T> ans = new Future<>();
		futureMap.putIfAbsent(e,ans);
		BlockingQueue <MicroService> service = messageMap.get(e);
		synchronized (e.getClass()) {
			MicroService m = service.take();
			service.put(m);
			microMap.get(m).putLast(e);
			m.notifyAll();
		}
		return ans;
	}

	@Override
	public void register(MicroService m) {
		microMap.putIfAbsent(m,new LinkedBlockingDeque<>());
	}

	@Override
	public void unregister(MicroService m) {
		microMap.remove(m);
		for (Map.Entry<Class<? extends Message>, BlockingQueue<MicroService>> entry: messageMap.entrySet()){
			BlockingQueue<MicroService> value = entry.getValue();
			value.remove(m);
		}
	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		BlockingQueue<Message> events = microMap.get(m);
		if (events==null)
			throw new IllegalArgumentException("The microservice is not registered!");
		if (m.getClass().isInstance(GPUService.class)){
			GPUService cast = (GPUService) m;
		while (events.isEmpty()||(cast.getGpu().getModel()!=null && events.peek() instanceof Event))
			m.wait();
		}
			return events.take(); // this also handles the other options, it waits until we have something in the queue, and then takes it out.
	}

	public Boolean isMicroServiceRegistered(MicroService m){
		return !(microMap.get(m) == null);
	}

	public Boolean isMicroServiceEventRegistered(MicroService m,Event e) {
		return messageMap.get(e).contains(m);
	}

	public Boolean isMicroServiceBroadCastRegistered(MicroService m,Broadcast b){
		return messageMap.get(b).contains(m);
	}
}
