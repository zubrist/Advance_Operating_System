public class Main {

    public static void main(String args[]){

        Process p1 = new Process("localhost",3001,1);
        Process p2 = new Process("localhost",3002,2);
        Process p3 = new Process("localhost",3003,3);

        p1.start();
        p2.start();
        p3.start();

        p1.connect("localhost", 3002);
        p1.connect("localhost", 3003);

        p2.connect("localhost", 3001);
        p2.connect("localhost", 3003);

        p3.connect("localhost", 3001);
        p3.connect("localhost", 3002);

        p1.setSharedResource(1, true);
        p2.setSharedResource(1, true);
        p3.setSharedResource(1, true);

//        Sends a message and starts a new thread for client
        p3.sendMessage();
        p2.sendMessage();
        p1.sendMessage();
//        Sends a message
        p3.sendAnotherMessage();
        p2.sendAnotherMessage();
        p1.sendAnotherMessage();

        try {
            Thread.sleep(150);
            p1.printMessages();
            Thread.sleep(150);
            p2.printMessages();
            Thread.sleep(150);
            p3.printMessages();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.exit(0);
//        p1.closeConnection();
//        p2.closeConnection();
//        p3.closeConnection();
    }
}
