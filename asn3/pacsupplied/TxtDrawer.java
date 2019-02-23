import java.io.*;
import java.util.*;

public class TxtDrawer implements GraphDrawer {

	private String fn;
	// your private fields and methods...
	private int number_node = 0;
	private List<TxtNode> t_list = null;
	private Map<TxtNode,Integer> t_map = null;

	public class TxtNode implements GraphDrawer.Node {
		// a constructor here...
		private String faketab = "        "; // 8 spaces
		public int node_num = 0;
		public String node_name = "";
		public String name_fld = "";
		public String val_fld = "";
		public TxtNode txt_node = null;

		public String trash = "";

		public TxtNode(String name, int num) //
		{
			node_name = name;
			node_num = num;

			trash = "node" + Integer.toString(num) + ": " + name + "\n";
		}

		public void addValueField(String name, String value) {
			// code here
			// name : value
			name_fld = name;
			val_fld = value;

			String x = faketab + name + ": " + value + "\n";
			trash += x;
		}

		public void addPtrField(String name, Node tonode) {
			// code here
			// tonode will be a TxtNode, but you'll have to cast it

			//  name : -> node #
			// find the specfic node to get the node number
			TxtNode tt = (TxtNode)tonode;

			// System.out.println("bbb " + name + " " + tt.node_name + " " + t_map.get(tt));
			String x = faketab + name + ": -> node " + t_map.get(tt) + "\n";
			trash += x;

		}

	} // end TxtNode


	public TxtDrawer(String filename) {
		// save filename and open it later (see below)
		fn = filename;
		// other initialization here...
		t_list = new LinkedList<TxtNode>();
		t_map = new HashMap<TxtNode,Integer>();
	}

	public Node addNode(String name)
	{
		// your code here (should return a TxtNode)
		// System.out.println("aaa " + name + " " + number_node);

		// keep track of number of nodes, and which node is each number
		// hashmap of node and number
		TxtNode tn = new TxtNode(name,number_node);

		if(!t_map.containsKey(tn)) // TxtNode -> int
		{
			t_map.put(tn,number_node);
		}

		number_node++;
		t_list.add(tn);

		return tn;
	}

	public void draw()
	{
		PrintWriter out;
		if (fn!="-") {
			try {
				out = new PrintWriter(new FileWriter(fn));
			} catch (IOException ioe) {
				System.out.println("could not open "+fn+" for writing");
				return;
			}
		} else {
			out = new PrintWriter(System.out);
		}

		// now use out.println(...) and similar...
		// your code here...
		for(TxtNode t : t_list)
		{
			out.println(t.trash);
		}

		out.close();
	}
}

/*

--------------------
node 0: Test1.A
        i: 3
        d: 3.5

--------------------
node 0: Test2.A
        i: 3
        d: 3.5

--------------------
node 0: Test3.A
        i: 3
        d: 3.5
        next: -> node 1

node 1: Test3.A
        i: -1
        d: -0.5
        next:

--------------------
node 0: Test4.C
        i: 3
        d: 3.5
        next: -> node 1
        e: 100.75
        f: 103.75

node 1: Test4.B
        i: -1
        d: -0.5
        next:
        e: -3.14

--------------------
node 0: Test5.B
        i: 3
        d: 3.5
        next: -> node 1
        e: 4.5

node 1: Test5.A
        i: -1
        d: -0.5
        next:

node 2: Test5.B
        i: 0
        d: 0.5
        next: -> node 1
        e: -100.3

--------------------
node 0: Test6.A[]
        [0]: -> node 1
        [1]: -> node 2
        [2]: -> node 3

node 1: Test6.A
        i: 0
        d: 0.5
        next: -> node 1

node 2: Test6.A
        i: 1
        d: 1.5
        next: -> node 1

node 3: Test6.A
        i: 2
        d: 2.5
        next: -> node 1

--------------------
node 0: Test7.A[]
        [0]: -> node 1
        [1]: -> node 2
        [2]: -> node 3

node 1: Test7.A
        i: 0
        d: 0.5
        name: "zero"
        next: -> node 1

node 2: Test7.A
        i: 1
        d: 1.5
        name: "positive"
        next: -> node 1

node 3: Test7.A
        i: 2
        d: 2.5
        name: "positive"
        next: -> node 1

--------------------
*/