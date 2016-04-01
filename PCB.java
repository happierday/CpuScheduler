import java.util.LinkedList;


public class PCB {
	public int jobID;
	public String state;
	public int currentCounter;
	public int cpuBurst;
	public int burst ;
	public int totalBursts = 0;
	public int currentCpuBurst=0;
	public int InOut = 0;
	public int totalCostTime = 0;
	public int processTime = 0;
	public int waitingTime = 0;
	public int turnaroundTime = 0;
	public LinkedList<Integer> list =  new LinkedList<Integer>();
	public LinkedList<Integer> costList = new LinkedList<Integer>(); 
	public int arriveTime;
	
	PCB(LinkedList<Integer> list){
        	this.list = list;  	
	}
	
	public void IOperformed(){
		InOut++;
	}
	
	public void set(){
		int i = 3;

		jobID = list.get(0);
		arriveTime = list.get(1);
		cpuBurst = list.get(2);
		burst = cpuBurst;
		while(i<list.size()){
			costList.add(list.get(i));

			i++;
		}
		for(i =0;i<costList.size();i++){
			totalBursts +=costList.get(i);
		}
	}
	
	public int getFirst(){
		return costList.getFirst();
	}
	
	public int setCost(int n){
		currentCpuBurst = list.get(n);
		return currentCpuBurst;
	}
	
	
	public String printInfor(){
		return  "Process "+jobID +
				 " finished " +currentCpuBurst+" cupBurst at time "+
				totalCostTime + "; " + cpuBurst + " left!";
	}
	
	public String toString(){
		return "process "+jobID + " arrived at " + arriveTime+
				", completed at "+ totalCostTime +" with " + processTime
				+" processTime, "+waitingTime+ " waiting time, and "+
				+turnaroundTime +" turnaround Time!";
	}
	
}
