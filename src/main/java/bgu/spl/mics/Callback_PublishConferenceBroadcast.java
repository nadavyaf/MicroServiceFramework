package bgu.spl.mics;

import bgu.spl.mics.application.messages.PublishConferenceBroadcast;
import bgu.spl.mics.application.objects.Model;
import bgu.spl.mics.application.services.StudentService;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

public class Callback_PublishConferenceBroadcast implements Callback<PublishConferenceBroadcast> {
private StudentService st;
    public Callback_PublishConferenceBroadcast(StudentService st){
        this.st = st;
    }
    public void call(PublishConferenceBroadcast c) throws InterruptedException {
        LinkedBlockingQueue<Model> doneList = c.getCfs().getCfsList();
        Iterator ita = doneList.iterator();
        while(ita.hasNext()){
            Model cfmodel = (Model)ita.next();
            Boolean flag=true;
            Iterator modelita = st.getModels().iterator();
            while (modelita.hasNext()&&flag){
                Model stmodel = (Model) modelita.next();
                if (stmodel==cfmodel) {
                    stmodel.setPublished(true);
                    st.getStudent().addPublication();
                    flag=false;
                }
            }
            if (flag) {
                st.getStudent().addPapersRead();
            }
        }

    }
}
