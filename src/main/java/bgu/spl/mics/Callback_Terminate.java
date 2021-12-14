package bgu.spl.mics;

import bgu.spl.mics.application.messages.TerminateBroadcast;

public class Callback_Terminate implements Callback<TerminateBroadcast> {
    private MicroService ms;

    public Callback_Terminate(MicroService ms) {
        this.ms = ms;
    }

    @Override
    public void call(TerminateBroadcast c) {
        ms.terminate();
    }
}
