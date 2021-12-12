package bgu.spl.mics;

/**
 * a callback is a function designed to be called when a message is received.
 */
public interface Callback<T> {/** Assiph's comments: Need to create a callback for each event. This callback should make things run on
 a different thread, this is why it has a single method (call) so we can */

    public void call(T c) throws InterruptedException;

}
