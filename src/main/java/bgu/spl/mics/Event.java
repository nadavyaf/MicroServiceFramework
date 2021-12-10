package bgu.spl.mics;

/**
 * A "Marker" interface extending {@link Message}. A micro-service that sends an
 * Event message expects to receive a result of type {@code <T>} when a
 * micro-service that received the request has completed handling it.
 * When sending an event, it will be received only by a single subscriber in a
 * Round-Robin fashion.
 */
public interface Event<T> extends Message {/** Assiph's Comments:We need to create event for each event. */

}

/**
 * Assiph's comments: Each event should hold a result, note that when we finish our event, we will pass this result (with resolve method)
 * to the future object that is connected to the service that sent the event (not sure if we need to add a field of future for each service
 * or not yet).
 */
