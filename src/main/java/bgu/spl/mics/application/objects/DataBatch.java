package bgu.spl.mics.application.objects;

/**
 * Passive object representing a data used by a model.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */

public class DataBatch {
  private Boolean ProcessedCpu;

    public DataBatch() {
        ProcessedCpu = false;
    }

    public Boolean isProcessedCpu(){
       return ProcessedCpu;
   }
   public void setTrueProcessedCpu(){
        ProcessedCpu = true;
   }
}
