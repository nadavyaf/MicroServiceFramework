package bgu.spl.mics.application;

import bgu.spl.mics.application.objects.*;
import bgu.spl.mics.application.services.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

/** This is the Main class of Compute Resources Management System application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output a text file.
 */
public class CRMSRunner {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Hello World!");
        TimeService clock = new TimeService(1000,100000);
        File input = new File("C:/Users/nadav/IdeaProjects/JavaMasterclass/SPL2/example_input.json");
        JsonElement fileElement = JsonParser.parseReader(new FileReader(input));
        JsonObject fileObject = fileElement.getAsJsonObject();
        JsonArray jsonArrayOfStudent = fileObject.get("student").getAsJsonArray();
        List<StudentService> studentServiceList = new LinkedList<>();
        for(JsonElement studentElement : jsonArrayOfStudent) {
            JsonObject studentObject = studentElement.getAsJsonObject();
            String name = studentObject.get("name").getAsString();
            String department = studentObject.get("department").getAsString();
            String degree = studentObject.get("degree").getAsString();
            Student.Degree deg = null;
            if (degree.equals("MSc")) {
                deg = Student.Degree.MSc;
            } else {
                deg = Student.Degree.PhD;
            }
            Student student = new Student(name, department, deg);
            JsonArray jsonArrayofModels = studentObject.get("models").getAsJsonArray();
            List<Model> modelList = new LinkedList<>();
            for (JsonElement modelElement : jsonArrayofModels) {
                JsonObject modelObject = modelElement.getAsJsonObject();
                String modelName = modelObject.get("name").getAsString();
                String type = modelObject.get("type").getAsString();
                Data.Type dataType = null;
                if (dataType.equals("images") || dataType.equals("Images")) {
                    dataType = Data.Type.Images;
                } else if (dataType.equals("Text")) {
                    dataType = Data.Type.Text;
                } else if (dataType.equals("Tabular")) {
                    dataType = Data.Type.Tabular;
                }
                Integer size = modelObject.get("size").getAsInt();
                Data modelData = new Data(dataType, size);
                Model model = new Model(modelName, modelData, student);
                modelList.add(model);
            }
            StudentService sts = new StudentService(name + " service", student, (LinkedList<Model>) modelList);
            studentServiceList.add(sts);
        }
        JsonArray jsonArrayOfGpus = fileObject.get("GPUS").getAsJsonArray();
        List<GPUService> gpuServiceList = new LinkedList<>();
        int i = 0;
        for(JsonElement gpuElement : jsonArrayOfGpus){
            JsonObject gpuObject = gpuElement.getAsJsonObject();
            String gpuType = "";
            GPU.Type type = null;
            if(gpuType.equals("RTX3090")){
                type = GPU.Type.RTX3090;
            }
            else if(gpuType.equals("RTX2080")){
                type = GPU.Type.RTX2080;
            }
            else if(gpuType.equals("GTX1080")){
                type = GPU.Type.GTX1080;
            }
            GPU gpu = new GPU(type);
            GPUService gpus = new GPUService("GPU Service " + i, gpu);
            gpuServiceList.add(gpus);
            i++;
        }
        JsonArray jsonArrayofCpus = fileObject.get("CPUS").getAsJsonArray();
        List<CPUService> cpuServiceList = new LinkedList<>();
        int j = 0;
        for(JsonElement cpuElement : jsonArrayofCpus){
            JsonObject cpuObject = cpuElement.getAsJsonObject();
            Integer cpuCores = cpuObject.get("").getAsInt();
            CPU cpu = new CPU(cpuCores);
            CPUService cpus = new CPUService("CPU Service " + j, cpu);
            cpuServiceList.add(cpus);
            j++;
        }
        JsonArray jsonArrayofConferences = fileObject.get("Conferences").getAsJsonArray();
        List<ConferenceService> conferenceServiceList = new LinkedList<>();
        for(JsonElement conferenceElement : jsonArrayofConferences){
            JsonObject conferenceObject = conferenceElement.getAsJsonObject();
            String name = conferenceObject.get("name").getAsString();
            Integer date = conferenceObject.get("date").getAsInt();
            ConfrenceInformation conference = new ConfrenceInformation(name, date);
            ConferenceService conferenceService = new ConferenceService(name + " service", conference);
            conferenceServiceList.add(conferenceService);
        }
        Integer tickTime = fileObject.get("TickTime").getAsInt();
        Integer duration = fileObject.get("Duration").getAsInt();
        TimeService timeService = new TimeService(tickTime, duration);
    }
}
