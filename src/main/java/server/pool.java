package server;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Author: Hao Qin
 * @Date: 19-7-26  上午12:51
 * @Version 1.0
 */

    public class pool {
        private static ArrayBlockingQueue abqcon = new ArrayBlockingQueue(200);
       public static synchronized int put(String message) throws InterruptedException {
           abqcon.put(message);
           return abqcon.size();

    }
        public synchronized static String get() throws InterruptedException {
           if (abqcon.size()!=0) {
               String M = (String) abqcon.take();

               return M;
           } else {
               return "null";
           }
        }
        public synchronized static int size(){
           return abqcon.size();
        }

}
