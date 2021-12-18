package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.objects.Model;
import bgu.spl.mics.application.objects.Student;

public class TestModelEvent implements Event<Model> {
private final Model model;
public TestModelEvent(Model model) {
    this.model = model;
}
    public Model getModel() {
        return model;
    }
}