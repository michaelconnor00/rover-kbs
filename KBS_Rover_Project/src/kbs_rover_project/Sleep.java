package kbs_rover_project;


public class Sleep extends Thread {

    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.err.println("Eception>????");
        }
    }

    public static void main(String args[]) {
        (new Sleep()).start();
    }

}