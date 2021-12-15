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
        LinkedBlockingQueue<String> doneList = c.getCfs().getCfsList();
        Iterator ita = doneList.iterator();
        while(ita.hasNext()){
            String modelName = (String)ita.next();
            Boolean flag=true;
            Iterator modelita = st.getModels().iterator();
            while (modelita.hasNext()&&flag){
                Model model = (Model) modelita.next();
                if (model.getName()==modelName) {
                    model.setPublished(true);
                    st.getStudent().addPublication();
                    System.out.println(this.st.getStudent() + " added publication!");
                    flag=false;
                }
            }
            if (flag) {
                st.getStudent().addPapersRead();
                System.out.println(this.st.getStudent() + " added paper read!");
            }
        }

    }
}
