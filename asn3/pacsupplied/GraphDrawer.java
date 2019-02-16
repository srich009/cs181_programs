public interface GraphDrawer {
	// interface describing a node -- to be implemented as seen fit
	// for the implementation of GraphDrawer
	static public interface Node {
		// adds a field to the node that has a non-pointer value
		public void addValueField(String name, String value);
		// adds a field to the node that points to another node
		public void addPtrField(String name, Node tonode);
	}
	// creates a new node with name "name"
	public Node addNode(String name);
	// draws the entire graph
	public void draw();
}

