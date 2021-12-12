package bgu.spl.mics;

import bgu.spl.mics.application.messages.TerminateBroadcast;

public class Callback_Terminate implements Callback<TerminateBroadcast> {

    public void call(TerminateBroadcast c) throws InterruptedException {
    c.getMicroService().terminate();
    }
}
