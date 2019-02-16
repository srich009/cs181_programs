import java.io.*;

public class TxtDrawer implements GraphDrawer {

	public class TxtNode implements GraphDrawer.Node {
		// a constructor here...
		public void addValueField(String name, String value) {
			// code here
		}

		public void addPtrField(String name, Node tonode) {
			// code here
			// tonode will be a TxtNode, but you'll have to cast it
		}

		// fields and other methods here
	}


	public TxtDrawer(String filename) {
		// save filename and open it later (see below)
		fn = filename;
		// other initialization here...
	}


	public Node addNode(String name) {
		// your code here (should return a TxtNode)
	}

	public void draw() {
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
		out.close();
	}

//-----------------------------
	private String fn;
	// your private fields and methods...
}
