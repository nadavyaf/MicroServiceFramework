package bgu.spl.mics.application.services;

import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.TickBroadcast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * TimeService is the global system timer There is only one instance of this micro-service.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other micro-services about the current time tick using {@link //TickBroadcast}.
 * This class may not hold references for objects which it is not responsible for.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class TimeService extends MicroService{
	private Timer clock = new Timer();
	private int speed;
	private int duration;
	TimerTask tick;
	public TimeService(int speed, int duration) {
		super("TimeService");
		this.speed = speed;
		this.duration = duration;
		tick = new TimerTask() {
			@Override
			public void run() {
				MessageBusImpl.getInstance().sendBroadcast(new TickBroadcast());
			}
		};
	}
	protected void initialize() throws InterruptedException {
		this.clock.scheduleAtFixedRate(tick,0,speed); /**Assiph's comments: creates another thread that sends ticks every speed. */
		Thread.sleep(duration);
		clock.cancel();
		this.terminate();
	}

}
