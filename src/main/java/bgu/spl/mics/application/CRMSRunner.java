package bgu.spl.mics.application;

import bgu.spl.mics.application.services.TimeService;

/** This is the Main class of Compute Resources Management System application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output a text file.
 */
public class CRMSRunner {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        TimeService clock = new TimeService(1000,100000);


    }
}
