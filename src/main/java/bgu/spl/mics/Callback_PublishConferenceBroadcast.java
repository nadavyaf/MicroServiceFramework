package bgu.spl.mics;

import bgu.spl.mics.application.messages.PublishConferenceBroadcast;
import bgu.spl.mics.application.objects.Model;
import bgu.spl.mics.application.services.StudentService;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

public class Callback_PublishConferenceBroadcast implements Callback<PublishConferenceBroadcast> {
    private StudentService student;

    public Callback_PublishConferenceBroadcast(StudentService student) {
        this.student = student;
    }

    @Override
    public void call(PublishConferenceBroadcast c) throws InterruptedException {
        LinkedBlockingQueue<String> doneList = c.getCfs().getCfsList();
        LinkedList<Model> modelList = this.student.getModelList();
        while(!doneList.isEmpty()){
            String completedName = doneList.take();
            Iterator iterator = modelList.iterator();
            boolean flag = true;
                while (iterator.hasNext() && flag) {
                    Model model = (Model) iterator.next();
                    if (model.getName().equals(completedName)) {
                        this.student.getStudent().addPublication();
                        flag = false;
                    }
                }
                if(flag){
                    this.student.getStudent().addPapersRead();
                }
        }
    }
}
