package bgu.spl.mics.application;

import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.application.objects.*;
import bgu.spl.mics.application.services.*;
import com.google.gson.*;

import java.io.*;
import java.util.LinkedList;

/** This is the Main class of Compute Resources Management System application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output a text file.
 */
public class CRMSRunner {
    public static void main(String[] args) throws IOException, InterruptedException {
        FileReader reader = new FileReader(args[0]);
        LinkedList <Thread> threadList = new LinkedList<>();
        JsonElement fileElement = JsonParser.parseReader(reader);
        JsonObject fileObject = fileElement.getAsJsonObject();
        LinkedList <StudentService> studentServiceList = new LinkedList();
        LinkedList <ConferenceService> cfsList = new LinkedList<>();
        int speed = fileObject.get("TickTime").getAsInt();
        int duration = fileObject.get("Duration").getAsInt();
        TimeService clock = new TimeService(speed,duration);
        Thread clockt = new Thread(clock);
        threadList.add(clockt);
        clockt.start();
        JsonArray jsonArrayOfStudents = fileObject.get("Students").getAsJsonArray();
        JsonArray jsonArrayGPU = fileObject.get("GPUS").getAsJsonArray();
        int i =0;
        for (JsonElement gpu : jsonArrayGPU){
            String type="";
            type = gpu.getAsString();
            GPU newGpu=null;
            if (type.equals("RTX3090")){
                newGpu = new GPU(GPU.Type.RTX3090);
            }
            if (type.equals("RTX2080"))
                newGpu = new GPU(GPU.Type.RTX2080);
            if (type.equals("GTX1080"))
                newGpu= new GPU(GPU.Type.GTX1080);
            GPUService gpus = new GPUService("Gpu Service" + i,newGpu);
            Thread gpuservicet = new Thread(gpus);
            threadList.add(gpuservicet);
            gpuservicet.start();
            i++;
        }
        i=0;
        JsonArray jsonArrayCPU = fileObject.get("CPUS").getAsJsonArray();
        for (JsonElement cpu : jsonArrayCPU){
            CPU newCpu = new CPU(cpu.getAsInt());
            CPUService cpus = new CPUService("Cpu Service" + i,newCpu);
            Thread cpuservicet = new Thread(cpus);
            threadList.add(cpuservicet);
            cpuservicet.start();
            i++;
        }
        JsonArray jsonArrayConference = fileObject.get("Conferences").getAsJsonArray();
        for (JsonElement con : jsonArrayConference){
            JsonObject conferenceJsonObject = con.getAsJsonObject();
            String name = conferenceJsonObject.get("name").getAsString();
            int date = conferenceJsonObject.get("date").getAsInt();
            ConfrenceInformation cfi = new ConfrenceInformation(name,date);
            ConferenceService cfs = new ConferenceService(name + " service",cfi);
            cfsList.add(cfs);
            Thread conferencet = new Thread(cfs);
            threadList.add(conferencet);
            conferencet.start();
        }
        for (JsonElement st : jsonArrayOfStudents){
            JsonObject studentJsonObject = st.getAsJsonObject();
            String name = studentJsonObject.get("name").getAsString();
            String depertment = studentJsonObject.get("department").getAsString();
            String statusget = studentJsonObject.get("status").getAsString();
            Student.Degree deg=null;
            if (statusget.equals("MSc"))
            deg= Student.Degree.MSc;
            if (statusget.equals("PhD"))
                deg= Student.Degree.PhD;
            Student student = new Student(name,depertment,deg);
            JsonArray jsonArrayOfModels = studentJsonObject.get("models").getAsJsonArray();
            LinkedList<Model> modelList= new LinkedList<Model>();
            for (JsonElement mdl : jsonArrayOfModels){
                JsonObject modelJsonObject = mdl.getAsJsonObject();
                String modelName = modelJsonObject.get("name").getAsString();
                String typeget = modelJsonObject.get("type").getAsString();
                Data.Type type=null;
                if (typeget.equals("images")||typeget.equals("Images"))
                    type = Data.Type.Images;
                if (typeget.equals("Tabular")||typeget.equals("tabular"))
                    type = Data.Type.Tabular;
                if (typeget.equals("Text")||typeget.equals("text"))
                    type = Data.Type.Text;
                int size = modelJsonObject.get("size").getAsInt();
                Data data = new Data(type,size);
                Model model = new Model(data,modelName,student);
                modelList.add(model);
            }
            StudentService studentService = new StudentService(student.getName() + " service",student,modelList);
            studentServiceList.add(studentService);
            Thread studentservicet = new Thread(studentService);
            threadList.add(studentservicet);
            studentservicet.start();
        }
        for (Thread thread : threadList){
            thread.join();
        }

        //FileReader
        File file = new File("out.json");
        FileWriter fw = new FileWriter("out.json");
        PrintWriter pw = new PrintWriter(fw);
        pw.println("{");
        pw.println("    \"students\": [");
        int num=0;
        for (StudentService studentService : studentServiceList) {
            num++;
            pw.println("        {");
            pw.println("            \"name\": \"" + studentService.getStudent().getName() + "\""  + ",");
            pw.println("            \"department\": \"" + studentService.getStudent().getDepartment() + "\""  + ",");
            pw.println("            \"status\": \"" + studentService.getStudent().getStatus() + "\""  + ",");
            pw.println("            \"publications\": " + studentService.getStudent().getPublications() + ",");
            pw.println("            \"papersRead\": " + studentService.getStudent().getPapersRead()  + ",");
            pw.print("            \"trainedModels\": " + "[");
            boolean hasModel=false;
            boolean first = true;
            for (Model model : studentService.getModels()) {
                if (model.getCurrStatus() == Model.Status.Trained || model.getCurrStatus() == Model.Status.Tested) {
                    if (!hasModel)
                        pw.println();
                    if (!first){
                       pw.println(",");
                    }
                    first=false;
                    hasModel=true;
                    pw.println("                {");
                    pw.println("                    \"name\": "  + "\"" + model.getName() + "\""  + ",");
                    pw.println("                    \"data\": {");
                    pw.println("                        \"type\": " + "\"" + model.getData().getType() + "\""  + ",");
                    pw.println("                        \"size\": " + model.getData().getSize());
                    pw.println("                    },");
                    pw.println("                    \"status\": " + "\"" + model.getCurrStatus() + "\""  + ",");
                    pw.println("                    \"results\": " + "\"" + model.getResult() + "\"");
                    pw.print("                }");
                }
            }
            if (hasModel) {
                pw.println();
                pw.println("            ]");
            }
            else
            pw.println("]");
            if (num!=studentServiceList.size())
            pw.println("        },");
        }
        pw.println("        }");
        pw.println("    ],");
        pw.println("    \"conferences\": [");
        pw.println("        {");
        boolean first=true;
        int last=0;
        for (ConferenceService cfs : cfsList){
            last++;
            if (!first)
                pw.println("        {");
            first=false;
            pw.println("            \"name\": \"" + cfs.getCfi().getName() + "\""  + ",");
            pw.println("            \"date\": " + cfs.getCfi().getDate()  + ",");
            pw.print("            \"publications\":" + " [");
            Boolean firsty = true;
            int modelCount = 0;
            for (Model model : cfs.getCfsList()){
                if (firsty)
                    pw.println();
                firsty=false;
                pw.println("                {");
                pw.println("                    \"name\": \"" + model.getName() + "\"" + ",");
                pw.println("                    \"data\": {");
                pw.println("                        \"type\": \"" + model.getData().getType() + "\""  + ",");
                pw.println("                        \"size\": " + model.getData().getSize());
                pw.println("                    },");
                pw.println("                    \"status\": \"" + model.getCurrStatus() + "\""  + ",");
                pw.println("                    \"results\": \"" + model.getResult() + "\"");
                if( modelCount <cfs.getCfsList().size()-1)
                    pw.println("                },");
                else
                    pw.println("                }");
                modelCount++;

            }
            if (firsty)
                pw.println("]");
            else
            pw.println("            ]");
            if (last<cfsList.size())
            pw.println("        },");
            else
                pw.println("        }");
        }
        pw.println("    ],");
        pw.println("\"" + "gpuTimeUsed\": " + Cluster.getInstance().getStatistics().getGPUTimeUnits() + ",");
        pw.println("\"" + "cpuTimeUsed\": " + Cluster.getInstance().getStatistics().getCPUTimeUnits() + ",");
        pw.println("\"" + "batchesProcessed\": " + Cluster.getInstance().getStatistics().getCPUProcessed());
        pw.println("}");
        pw.close();

    }
}
