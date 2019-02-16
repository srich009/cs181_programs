// a class for coordinating the drawer and formatters to produce
// the output
public class DataDrawer {
	// a constructor, which should take (and save) the drawer to be used
	public DataDrawer(GraphDrawer drawer) {
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
	public void addFormatter(Formatter f) {
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
	public void addObject(Object o) {
		addObjectReal(new Formatter.GenObject(o,false));
	}

	// feel free to add private methods and fields!
}
