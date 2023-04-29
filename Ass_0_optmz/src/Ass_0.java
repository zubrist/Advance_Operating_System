import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;


public class Ass_0{
	public int e,v;
	
	void read() throws FileNotFoundException{
		//Declaring variables
		int i,j,k,l,src=0,snk=0;
		String data;	
		
		//Declaring file object and pass the file name as argument
		FileReader fp = new FileReader("Graph3.txt");
		//Declaring scanner class object to read file 
		Scanner sc = new Scanner(fp);
		//Declaring scanner class object to read input from user
		Scanner rd = new Scanner(System.in);
		
		//Reading the number of edges and vertices from the file
		data=sc.nextLine();
		e=Integer.parseInt(data.substring(0,1));
		v=Integer.parseInt(data.substring(2,3));
		
		System.out.println("No. of edge is : "+e);
		System.out.println("No. of vertices is : "+v);
		
		//Declaring 2-D array for adjacency matrix
		int adj[][] = new int[v][v];
		//Initializing the adjacency matrix with 0
		for(i=0;i<v;i++){
			for(j=0;j<v;j++){
				adj[i][j] = 0;
			}
		}
		//Creating adjacency Matrix
		while(sc.hasNext()){
			//reading lines one-by-one from file
			data=sc.nextLine();
			adj[Integer.parseInt(data.substring(0,1))][Integer.parseInt(data.substring(2,3))] = 1;
		}
		
		//Display the adjacency matrix
		System.out.println("\nThe Adjacency Matrix is: ");
		for(i=0;i<v;i++){
			for(j=0;j<v;j++){
				System.out.print(" "+adj[i][j]);
			}
			System.out.println();
		}
		detect_initiator(adj);
	}
	
	void detect_initiator(int arr[][]){
		//variable declaration
		int i,j,src=0,snk=0,flag,k;
		int src_arr[]=new int[v];
		int snk_arr[]=new int[v];
		boolean visited[] = new boolean[v];
		
		//checking the in-degree of every vertex
		for(i=0;i<v;i++){
			flag=0;
			for(j=0;j<v;j++){
				if(arr[j][i] != 0){
					flag=1;
					break;
				}
			}
			//storing the vertices with zero in-degree in "src_arr"
			if(flag==0){
				src_arr[src]=i;
				//counting the number of vertices
				src++;
			}
		}
		
		
		if(src>1){
			System.out.println("\nThere is no initiator\nThe graph is dis-connected");
		}
		else{
			System.out.println("\nThe graph is connected");
			if(src==1){
				//System.out.println("\nThe initiator vertex is: "+src_arr[src]);
				dfs(src_arr[src], visited,arr);
				k=0;
				for(j=0;j<v;j++){
					if(visited[j] == true){
						k++;
					}
				}
				//checking whether all the vertices are reached
				if(v-k == 0){
				    System.out.println("\nVertex "+snk_arr[i]+" can be considered as initiator");
				}
				else{
					System.out.println("There is no initiator");
				}
			}
			else{
				System.out.println("There is no source vertex");
				for(i=0;i<v;i++){
					flag=0;
					for(j=0;j<v;j++){
						if(arr[i][j] != 0){
							flag=1;
							break;
						}
					}
					if(flag == 1){
						snk_arr[snk]=i;
						snk++;
					}
				}
				for(i=0;i<snk;i++){
					dfs(snk_arr[i],visited,arr);
					k=0;
					for(j=0;j<v;j++){
						if(visited[j] == true){
							k++;
						}
					}
					//checking whether all the vertices are reached
					if(v-k == 0){
					    System.out.println("\nVertex "+snk_arr[i]+" can be considered as initiator\n");
					}
					   
					//Filling the nodeCovered array with False to
					//calculate the nodes visited from the next vertex
					Arrays.fill(visited, false);
				}
			}
		}
	}
	
	void dfs(int start,boolean[] visited, int arr[][])
    {
   
        // Print the current node
        System.out.print(start + " ");
 
        // Set current node as visited
        visited[start] = true;
 
        // For every node of the graph
        for (int i = 0; i < arr[start].length; i++) {
 
            // If some node is adjacent to the current node
            // and it has not already been visited
            if (arr[start][i] == 1 && (!visited[i])) {
                dfs(i, visited,arr);
            }
        }
    }
	
	public static void main(String[] args) throws FileNotFoundException {
		Ass_0 obj = new Ass_0();
		obj.read();
	}
}
