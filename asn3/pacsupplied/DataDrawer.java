import java.util.*;

// a class for coordinating the drawer and formatters to produce
// the output
public class DataDrawer {

	private GraphDrawer g_drawer;
	private LinkedList<Formatter> formatters;
	Map<Object,GraphDrawer.Node> o_map;

	// a constructor, which should take (and save) the drawer to be used
	public DataDrawer(GraphDrawer drawer) {
		g_drawer = drawer;
		formatters = new LinkedList<Formatter>();
		o_map = new HashMap<Object,GraphDrawer.Node>();
	}

	// Multiple formatters might be used.  When an object is to be
	// displayed, the formatters should be queried (in reverse order
	// from the order in which they were added) to find the first
	// one that applies to the object.
	//
	// That formatter should then be used to determine if the object
	// can be displayed "inline" as text or whether it needs its own node.
	// If it need its own node, the formatter returns the field names and
	// subobjects.
	public void addFormatter(Formatter f)
	{
		formatters.addFirst(f);
	}

	// This adds an object (and everything it is connected to!)
	// to the drawer's output (that is, it should be calling the methods
	// of the GraphDrawer given in the constructor)
	//
	// Note that each object (if it is isn't own node) should only
	// be added ONCE.  So, if we get to the same object in two different
	// ways, we should not regenerate  the object, but rather just point
	// to the previously created object.
	public void addObject(Object o)
	{
		addObjectReal(new Formatter.GenObject(o,false));
	}

	// feel free to add private methods and fields!
	private void addObjectReal(Formatter.GenObject g)
	{
		Formatter fmat = null;
		for( Formatter f : formatters) // get the formatter
		{
			if(f.applies(g))
			{
				fmat = f;
				break;
			}
		}

		List<Formatter.NamedObject> n_list = fmat.getFields(g);
		GraphDrawer.Node g_node = null;

		// System.out.println(fmat.className(g));

		if(!o_map.containsKey(g.obj))
		{
			g_node = g_drawer.addNode(fmat.className(g)); // initial
			o_map.put(g.obj,g_node); // GenObject to GraphDrawer.Node
		}
		else
		{
			g_node = o_map.get(g.obj);
		}

		// either add a node or add a pointer/value field
		// there is a node for every field that is not primitive
		// if it is primitive then print as a striing
		// if not primitive then point at the node created before

		for(Formatter.NamedObject n : n_list)
		{
			if(n.value.isprim) // addValueField
			{
				// System.out.println(n.name);
				g_node.addValueField(n.name, n.value.obj.toString()); // string name, string value
			}
			else // addPtrField
			{
				// make new node and addPtrField

				// System.out.println(n.name);
				// System.out.println(n.value.obj);
				// System.out.println(n.value.obj.getClass());
				// System.out.println(n.value.isprim);

				// check if n.value.obj is in map
				// if so then pass that inot addPtrField
				// else create a new node add that to the hash
				// and add that as the pointer field
				// but this also needs an object to get the fields for the node
				// recursive helper

				if(n.value.obj == null) // null pointers get value fields because pointer doesnt exist
				{
					g_node.addValueField(n.name, ""); // string name, string value
					continue;
				}

				if(o_map.containsKey(n.value.obj))
				{
					g_node.addPtrField(n.name,o_map.get(n.value.obj));
				}
				else
				{
					GraphDrawer.Node g_new = makeNode(fmat.className(n.value));
					o_map.put(n.value.obj, g_new); // add to map
					addObjectReal(n.value);
					g_node.addPtrField(n.name, g_new);
				}
			}
		}

		return;
	}

	private GraphDrawer.Node makeNode(String s)
	{
		return g_drawer.addNode(s);
	}
}
