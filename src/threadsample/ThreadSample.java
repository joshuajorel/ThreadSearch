/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package threadsample;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author JoshuaJorel
 */
public class ThreadSample extends Thread{

    /**
     * @param args the command line arguments
     */
    
    private int threadNumber;
    long startTime;
    static int size; //number of threads
    static int limit =30000000; //total number of samples
    static int search;
    static boolean found = false;
    static ArrayList<Integer> dump = new ArrayList<Integer>();
    
    public static void initialize(){
        for(int x=0; x<limit; x++){
            dump.add(x+1);
        }
        
        Collections.shuffle(dump);
    }
    
    public void run(){
        int x = 0;
        long endTime;
        long totalTime;
        int index;
        
        while(!found && x<limit/size){
        
            if(dump.get(x+(limit/size*(threadNumber-1)))==search){
                this.found = true;
                endTime = System.currentTimeMillis();
                index = x+(limit/size*(threadNumber-1));
                
                totalTime = endTime - startTime;
                System.out.println("Number "+search+" found in thread "+threadNumber+" in index "+index+" with time "+totalTime+"ms");
            }else{
                x++;
            }
            
        }
        if(!found){
            endTime = System.currentTimeMillis();
            totalTime = endTime - startTime;
            System.out.println("Unable to find number in thread "+threadNumber+" with execution time "+totalTime+"ms");
        }
    }
    
    public ThreadSample(int x){
        this.threadNumber = x;
    }

    
    public static void main(String[] args) {
        // TODO code application logic here
        
        ThreadSample[] threads;
        
        Scanner reader = new Scanner(System.in);
        
        //ask for number of threads and create array of classes
        System.out.print("Enter number of threads: ");
        ThreadSample.size = reader.nextInt();
        threads = new ThreadSample[ThreadSample.size];
        
        //ask for number to be searched
        System.out.print("Search for: ");
        ThreadSample.search = reader.nextInt();
        
        //initialize thread classes
        for(int x=0;x<ThreadSample.size;x++){
            threads[x] = new ThreadSample(x+1);
        }
        
        //initialize collection and thread size
        System.out.println("Initializing Dump and Threads");
        ThreadSample.initialize();
        
        for(int x=0;x<ThreadSample.size;x++){
            threads[x].startTime = System.currentTimeMillis();
        }
        
        //start threads
        for(int x=0;x<ThreadSample.size;x++){
            threads[x].start();
        }

    }
    
}
