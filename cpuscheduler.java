
public class cpuscheduler{
	
	public static void main(String []args){
		
		Algorithm sc = new Algorithm();
	
		if(args[0].equals("FCFS")){
			sc.FCFS(args[1]);
		}else if(args[0].equals("SJF")){
			sc.SJF(args[1]);
		}else{
			int n = Integer.parseInt(args[1]);
			sc.RR(n,args[2]);
		}
		
	}
	
}