package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback_Terminate;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.TerminateBroadcast;
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
	private int counter;
	private TimerTask tick;
	private TimerTask endTick;
	private Callback_Terminate terminate;
	public TimeService(int speed, int duration) {
		super("TimeService");
		this.speed = speed;
		this.counter = 0;
		this.duration = duration;
		terminate = new Callback_Terminate(this);
		tick = new TimerTask() {
			public void run() {
				try {
					MessageBusImpl.getInstance().sendBroadcast(new TickBroadcast());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		endTick = new TimerTask() {
			public void run() {
				try {
					clock.cancel();
					MessageBusImpl.getInstance().sendBroadcast(new TerminateBroadcast());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
	}
	protected void initialize() throws InterruptedException {
		MessageBusImpl.getInstance().register(this);
		this.subscribeBroadcast(TerminateBroadcast.class,terminate);
		this.clock.scheduleAtFixedRate(tick,0,speed);
		this.clock.schedule(endTick,(duration*speed)-speed);//does an extra tick if we don't subscribe by speed.
	}

	}
