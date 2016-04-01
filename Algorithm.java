

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.LinkedList;

import java.util.StringTokenizer;

public class Algorithm {
	public BufferedReader br;
	public String myLine;
	public int totalCostTime;
	public int currentTimer;
	public int quantum;
	public Queues<PCB> jobQueue ;
	public Queues<PCB> readyQueue ;
	public Queues<PCB> blockedQueue ;
	public int jobCompleted ;
	public int aveProTime ;
	public int aveWaitTime ;
	public int aveTurnTime ;
	LinkedList<Integer> myList = new LinkedList<Integer>();
	

	public void FCFS(String filename){
		totalCostTime = 0;
		jobQueue = new Queues<PCB>();
		readyQueue = new Queues<PCB>();
		blockedQueue = new Queues<PCB>();
		aveTurnTime = 0;
		aveWaitTime = 0;
		aveProTime = 0;
		jobCompleted = 0;
		
		try{
			br = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(filename)));
			myLine = br.readLine();
		}catch(IOException e){
			System.out.print("File not found.");
		}

		StringTokenizer tokens = new StringTokenizer(myLine);
		while(myLine != null){
			
			tokens = new StringTokenizer(myLine);
			while(tokens.hasMoreTokens()){
				int number = Integer.parseInt(tokens.nextToken());
				myList.add(number);
			
			}
			PCB pcb = new PCB(myList);
			pcb.set();
			blockedQueue.insert(pcb);
		
			myList.clear();
			
			
			try {
				myLine = br.readLine();
			} catch (IOException e) {
		
				e.printStackTrace();
			}
		}
	
		//int ready = 0;
		int job = 0;
		int indexP = 0;
	
		while(indexP<10){
			readyQueue.insert(blockedQueue.getFirst());
			blockedQueue.removeFirst();
			indexP++;
			
		}
            		
		while(!readyQueue.isEmpty()){
			while(readyQueue.getFirst().arriveTime<=totalCostTime){
				jobQueue.insert(readyQueue.getFirst());
				readyQueue.removeFirst();
				if(readyQueue.isEmpty()) break;
			}
			if(blockedQueue.isEmpty() || readyQueue.size ==10) break;
			readyQueue.insert(blockedQueue.getFirst());
			blockedQueue.removeFirst();
			
		}
	
		
		while(job<jobQueue.size){
			/*
			while(!readyQueue.isEmpty()){
				while(readyQueue.getFirst().arriveTime<=totalCostTime){
					jobQueue.insert(readyQueue.getFirst());
					readyQueue.removeFirst();
					if(readyQueue.isEmpty()) break;
				}
				if(blockedQueue.isEmpty() || readyQueue.size ==10) break;
				readyQueue.insert(blockedQueue.getFirst());
				blockedQueue.removeFirst();
				
			}
			*/		
			PCB currentPCB = jobQueue.get(job);
			currentPCB.currentCpuBurst = currentPCB.costList.getFirst();
			while(currentPCB.currentCpuBurst >0){
				currentPCB.currentCpuBurst--;
				currentPCB.totalCostTime = totalCostTime +1;
				while(!readyQueue.isEmpty()){
					while(readyQueue.getFirst().arriveTime<=totalCostTime){
						jobQueue.insert(readyQueue.getFirst());
						readyQueue.removeFirst();
						if(readyQueue.isEmpty()) break;
					}
					if(blockedQueue.isEmpty() || readyQueue.size ==10) break;
					readyQueue.insert(blockedQueue.getFirst());
					blockedQueue.removeFirst();
					
				}
				totalCostTime ++;
				
				if(totalCostTime %200 ==0){
					System.out.println("ReadyQueue has "+ readyQueue.size+
							", blockedQueue has "+ blockedQueue.size+
							", jobs completed " + jobCompleted);
				}
				
			}
			/*
				while(readyQueue.size<10){
						if(blockedQueue.isEmpty()) break;
							readyQueue.insert(blockedQueue.getFirst());
							blockedQueue.removeFirst();
					
						while(readyQueue.getFirst().arriveTime <= totalCostTime){
							jobQueue.insert(readyQueue.getFirst());
							readyQueue.removeFirst();
							if(readyQueue.isEmpty()) break;
						}
					}

			*/
	
			currentPCB.costList.removeFirst();
			
			currentPCB.burst--;
			
			if(currentPCB.costList.isEmpty()){
				jobCompleted++;
				currentPCB.processTime = currentPCB.totalCostTime + currentPCB.InOut * 10;
				aveProTime += currentPCB.processTime;
				currentPCB.waitingTime = currentPCB.totalCostTime-currentPCB.arriveTime;
				aveWaitTime += currentPCB.waitingTime;
				currentPCB.turnaroundTime = currentPCB.totalCostTime-currentPCB.totalBursts;
				aveTurnTime += currentPCB.turnaroundTime;
				System.out.println(currentPCB.toString());
				jobQueue.remove(job);
				/*
				if(!readyQueue.isEmpty()){
					jobQueue.insert(readyQueue.getFirst());
					readyQueue.removeFirst();
				}
				if(!blockedQueue.isEmpty()){
					readyQueue.insert(blockedQueue.getFirst());
					blockedQueue.removeFirst();
				}
				*/
				if(jobQueue.isEmpty()) break;
			}else {
				currentPCB.IOperformed();
				job++;
				//if(job == jobQueue.size) job = 0;
			}
			if(job == jobQueue.size) job = 0;
		}
		
		System.out.println("FCFS is used. Current clock is "+totalCostTime+
				" with average proessing time "+ aveProTime/526+", average waiting time "+
				aveWaitTime/526+", average turnaround time " + aveTurnTime/526+"!");
		
	}
	
	public int findMin(){
		int min = 0;
		for(int i=1;i<jobQueue.size;i++){
			if(jobQueue.get(min).costList.getFirst() > jobQueue.get(i).costList.getFirst())
				min = i;
		}
		return min;
	}
	
	public void SJF(String filename){
        
		totalCostTime = 0;
		aveTurnTime = 0;
		aveWaitTime = 0;
		aveProTime = 0;
		jobCompleted = 0;
		jobQueue = new Queues<PCB>();
		readyQueue = new Queues<PCB>();
		blockedQueue = new Queues<PCB>();
		
		try{
			br = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(filename)));
			myLine = br.readLine();
		}catch(IOException e){
			System.out.print("File not found.");
		}
	
		StringTokenizer tokens = new StringTokenizer(myLine);
		while(myLine != null){
			
			tokens = new StringTokenizer(myLine);
			while(tokens.hasMoreTokens()){
				int number = Integer.parseInt(tokens.nextToken());
				myList.add(number);
				
			}
			PCB pcb = new PCB(myList);
			pcb.set();
			blockedQueue.insert(pcb);
	
			myList.clear();

			try {
				myLine = br.readLine();
			} catch (IOException e) {
			
				e.printStackTrace();
			}
		}
	
		
		
		int indexP = 0;

		while(indexP<10){
			readyQueue.insert(blockedQueue.getFirst());
	
			blockedQueue.removeFirst();
			indexP++;
		}
		
        /*   		
		while(ready<10){
		
		
			jobQueue.insert(readyQueue.getFirst());
			readyQueue.removeFirst();
			readyQueue.insert(blockedQueue.getFirst());
			blockedQueue.removeFirst();
			ready++;
		}
		*/
		
		while(!readyQueue.isEmpty()){
			while(readyQueue.getFirst().arriveTime<=totalCostTime){
				jobQueue.insert(readyQueue.getFirst());
				readyQueue.removeFirst();
				if(readyQueue.isEmpty()) break;
			}
			if(blockedQueue.isEmpty() || readyQueue.size ==10) break;
			readyQueue.insert(blockedQueue.getFirst());
			blockedQueue.removeFirst();
			
		}
		
		while(!jobQueue.isEmpty()){
			/*
			while(!readyQueue.isEmpty()){
				while(readyQueue.getFirst().arriveTime<=totalCostTime){
					jobQueue.insert(readyQueue.getFirst());
					readyQueue.removeFirst();
					if(readyQueue.isEmpty()) break;
				}
				if(blockedQueue.isEmpty() || readyQueue.size ==10) break;
				readyQueue.insert(blockedQueue.getFirst());
				blockedQueue.removeFirst();
				
			}
			
			while(readyQueue.size<=10){
					if(blockedQueue.isEmpty()) break;
					readyQueue.insert(blockedQueue.getFirst());
					blockedQueue.removeFirst();
						
					while(readyQueue.getFirst().arriveTime <= totalCostTime){
						jobQueue.insert(readyQueue.getFirst());
						readyQueue.removeFirst();
							
						if(readyQueue.isEmpty()) break;
					}
			}
			*/
			int min = findMin();
			PCB currentPCB = jobQueue.get(min);
			currentPCB.currentCpuBurst = currentPCB.costList.getFirst();
			while(currentPCB.currentCpuBurst >0){
				currentPCB.currentCpuBurst--;
				currentPCB.totalCostTime = totalCostTime +1;
				while(!readyQueue.isEmpty()){
					while(readyQueue.getFirst().arriveTime<=totalCostTime){
						jobQueue.insert(readyQueue.getFirst());
						readyQueue.removeFirst();
						if(readyQueue.isEmpty()) break;
					}
					if(blockedQueue.isEmpty() || readyQueue.size ==10) break;
					readyQueue.insert(blockedQueue.getFirst());
					blockedQueue.removeFirst();
					
				}
				totalCostTime ++;
				if(totalCostTime %200 ==0){
					System.out.println("ReadyQueue has "+ readyQueue.size+
							", blockedQueue has "+ blockedQueue.size+
							", jobs completed " + jobCompleted);
				}
			}
			
			/*
			PCB currentPCB = jobQueue.get(min);
			currentPCB.currentCpuBurst = currentPCB.costList.getFirst();
			currentPCB.totalCostTime = currentPCB.currentCpuBurst +10 + totalCostTime;
			totalCostTime += currentPCB.currentCpuBurst;
			*/
			
			currentPCB.costList.removeFirst();

			currentPCB.burst--;

			if(currentPCB.costList.isEmpty()){
				jobCompleted++;
				currentPCB.processTime = currentPCB.totalCostTime + currentPCB.InOut * 10;
				aveProTime += currentPCB.processTime;
				currentPCB.waitingTime = currentPCB.totalCostTime-currentPCB.arriveTime;
				aveWaitTime += currentPCB.waitingTime;
				currentPCB.turnaroundTime = currentPCB.totalCostTime-currentPCB.totalBursts;
				aveTurnTime += currentPCB.turnaroundTime;
				System.out.println(currentPCB.toString());
				jobQueue.remove(min);
				
				/*
				if(!readyQueue.isEmpty()){
					jobQueue.insert(readyQueue.getFirst());
					readyQueue.removeFirst();
				}
				if(!blockedQueue.isEmpty()){
					readyQueue.insert(blockedQueue.getFirst());
					blockedQueue.removeFirst();
				}
				*/

			}else {
				currentPCB.IOperformed();
			}
		}
		System.out.println("SJF is used. Current clock is "+totalCostTime+
				" with average proessing time "+ aveProTime/526+", average waiting time "+
				aveWaitTime/526+", average turnaround time " + aveTurnTime/526+"!");
	}
	
	public void RR(int a, String filename){
		quantum = a;
		totalCostTime = 0; 
		jobQueue = new Queues<PCB>();
		readyQueue = new Queues<PCB>();
		blockedQueue = new Queues<PCB>();
		
		try{
			br = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(filename)));
			myLine = br.readLine();
		}catch(IOException e){
			System.out.print("File not found.");
		}

		StringTokenizer tokens = new StringTokenizer(myLine);
		while(myLine != null){
			
			tokens = new StringTokenizer(myLine);
			while(tokens.hasMoreTokens()){
				int number = Integer.parseInt(tokens.nextToken());
				myList.add(number);
			
			}
			PCB pcb = new PCB(myList);
			pcb.set();
			blockedQueue.insert(pcb);
		
			myList.clear();
			
			
			try {
				myLine = br.readLine();
			} catch (IOException e) {
		
				e.printStackTrace();
			}
		}
	
		int ready = 0;
		int job = 0;
		int indexP = 0;
	
		while(indexP<10){
			readyQueue.insert(blockedQueue.getFirst());

			blockedQueue.removeFirst();
			indexP++;
		}
            		
		while(!readyQueue.isEmpty()){
			while(readyQueue.getFirst().arriveTime<=totalCostTime){
				jobQueue.insert(readyQueue.getFirst());
				readyQueue.removeFirst();
				if(readyQueue.isEmpty()) break;
			}
			if(blockedQueue.isEmpty() || readyQueue.size == 10) break;
			readyQueue.insert(blockedQueue.getFirst());
			blockedQueue.removeFirst();
			
		}
	
		
		while(job<jobQueue.size){
			/*
			while(!readyQueue.isEmpty()){
				while(readyQueue.getFirst().arriveTime<=totalCostTime){
					jobQueue.insert(readyQueue.getFirst());
					readyQueue.removeFirst();
					if(readyQueue.isEmpty()) break;
				}
				if(blockedQueue.isEmpty() || readyQueue.size == 10) break;
				readyQueue.insert(blockedQueue.getFirst());
				blockedQueue.removeFirst();
				
			}
			*/
			int n = quantum;
			PCB currentPCB = jobQueue.get(job);
			currentPCB.currentCpuBurst = currentPCB.costList.getFirst();
			if(currentPCB.currentCpuBurst > quantum){
				while(n >0){
					n--;
					currentPCB.totalCostTime = totalCostTime +1;
					while(!readyQueue.isEmpty()){
						while(readyQueue.getFirst().arriveTime<=totalCostTime){
							jobQueue.insert(readyQueue.getFirst());
							readyQueue.removeFirst();
							if(readyQueue.isEmpty()) break;
						}
						if(blockedQueue.isEmpty() || readyQueue.size ==10) break;
						readyQueue.insert(blockedQueue.getFirst());
						blockedQueue.removeFirst();
						
					}
					totalCostTime ++;
					if(totalCostTime %200 ==0){
						System.out.println("ReadyQueue has "+ readyQueue.size+
								", blockedQueue has "+ blockedQueue.size+
								", jobs completed " + jobCompleted);
					}
				}
				currentPCB.costList.set(0, currentPCB.currentCpuBurst-quantum);
				//currentPCB.totalCostTime = quantum +10 + totalCostTime;
				//totalCostTime += quantum;
			}else{
				while(currentPCB.currentCpuBurst >0){
					currentPCB.currentCpuBurst--;
					currentPCB.totalCostTime = totalCostTime +1;
					while(!readyQueue.isEmpty()){
						while(readyQueue.getFirst().arriveTime<=totalCostTime){
							jobQueue.insert(readyQueue.getFirst());
							readyQueue.removeFirst();
							if(readyQueue.isEmpty()) break;
						}
						if(blockedQueue.isEmpty() || readyQueue.size ==10) break;
						readyQueue.insert(blockedQueue.getFirst());
						blockedQueue.removeFirst();
						
					}
					totalCostTime ++;
					if(totalCostTime %200 ==0){
						System.out.println("ReadyQueue has "+ readyQueue.size+
								", blockedQueue has "+ blockedQueue.size+
								", jobs completed " + jobCompleted);
					}
				}
					currentPCB.costList.removeFirst();
					
			}
		/*
				while(readyQueue.size<10){
						if(blockedQueue.isEmpty()) break;
							readyQueue.insert(blockedQueue.getFirst());
							blockedQueue.removeFirst();
					
						while(readyQueue.getFirst().arriveTime <= totalCostTime){
							jobQueue.insert(readyQueue.getFirst());
							readyQueue.removeFirst();
							if(readyQueue.isEmpty()) break;
						}
					}

		*/
			
			currentPCB.burst--;
			
			if(currentPCB.costList.isEmpty()){
				jobCompleted++;
				currentPCB.processTime = currentPCB.totalCostTime + currentPCB.InOut * 10;
				aveProTime += currentPCB.processTime;
				currentPCB.waitingTime = currentPCB.totalCostTime-currentPCB.arriveTime;
				aveWaitTime += currentPCB.waitingTime;
				currentPCB.turnaroundTime = currentPCB.totalCostTime-currentPCB.totalBursts;
				aveTurnTime += currentPCB.turnaroundTime;
				System.out.println(currentPCB.toString());
				jobQueue.remove(job);
				/*
				if(!readyQueue.isEmpty()){
					jobQueue.insert(readyQueue.getFirst());
					readyQueue.removeFirst();
				}
				if(!blockedQueue.isEmpty()){
					readyQueue.insert(blockedQueue.getFirst());
					blockedQueue.removeFirst();
				}
				*/
				if(jobQueue.isEmpty()) break;
			}else {
				currentPCB.IOperformed();
				job++;
				//if(job == jobQueue.size) job = 0;
			}
			if(job == jobQueue.size) job = 0;
		}
		System.out.println("RR with quantum "+quantum+" is used. Current clock is "+totalCostTime+
				" with average proessing time "+ aveProTime/526+", average waiting time "+
				aveWaitTime/526+", average turnaround time " + aveTurnTime/526+"!");

	}
}
