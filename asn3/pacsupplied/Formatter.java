import java.util.List;

public interface Formatter {
	// a class for holding any object
	static public class GenObject {
		public GenObject(Object v, boolean isprimitive) {
			obj = v; isprim = isprimitive;
		}
		public GenObject(Object v, Class cl) {
			this(v,cl.isPrimitive()); // delegates to main constructor
		}
		public Object obj; // object
		public boolean isprim; // true if the object is actually
			// supposed to be a primitive object
			// For example, to hold an Integer,
			//        obj has the Integer and isprim=false
			//   to hold an int,
			//        obj has an Integer and isprim=true
			// isprim should only be true if obj is 
			//  Integer, Long, Double, Float, Boolean, Character,
			//  Byte, Void, or Short
			//  (the primitive type wrapper classes)
	}

	// a class for holding a general object (see above) and its name
	static public class NamedObject {
		public NamedObject(String n, GenObject obj) {
			name = n; value = obj;
		}
		public NamedObject(String n, Object v, boolean isprimitive) {
			this(n,new GenObject(v,isprimitive));
		}
		public NamedObject(String n, Object v, Class cl) {
			this(n,new GenObject(v,cl));
		}
		public String name;
		public GenObject value;
	}

	// applies is true iff this formatter can be used with the object "info"
	public boolean applies(GenObject info);
	// returns true iff this formatter would prefer to format the object
	//  "info" as a string (instead of a list of subcomponents)
	public boolean preferString(GenObject info);
	// returns a string representation of the object "info"
	public String getString(GenObject info);
	// returns a representation of the object "info" as a set of
	//   components (fields)
	public List<NamedObject> getFields(GenObject info);
	// returns the name to be used for the object "info"
	public String className(GenObject info);
}
