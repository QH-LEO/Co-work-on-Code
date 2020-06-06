package docker;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Author: Hao Qin
 * @Date: 19-7-26  上午12:51
 * @Version 1.0
 */

public class dockerport {

    public static ArrayBlockingQueue<Integer> abqcon = new ArrayBlockingQueue<Integer>(2000);
    public static synchronized int put(int message) throws InterruptedException {
        abqcon.put(message);
        return abqcon.size();

    }
    public synchronized static int get() throws InterruptedException {
        if (abqcon.size()!=0) {
            int M = abqcon.take();

            return M;
        } else {
            return -1;
        }
    }
    public synchronized static boolean exist(int tar) throws InterruptedException {
        if (abqcon.contains(tar)) {
            return true;
        } else {
            return false;
        }
    }
    public synchronized static int size(){
        return abqcon.size();
    }

}