import java.lang.reflect.*;
import java.util.*;

class ArrayFormat implements Formatter
{
    	// applies is true iff this formatter can be used with the object "info"
    	public boolean applies(GenObject info)
        {
            return info.obj.getClass().isArray();
        }

        // returns true iff this formatter would prefer to format the object
    	//  "info" as a string (instead of a list of subcomponents)
    	public boolean preferString(GenObject info)
        {
            return info.isprim;
        }

        // returns a string representation of the object "info"
    	public String getString(GenObject info)
        {
            return info.obj.toString();
        }

        // returns a representation of the object "info" as a set of
    	//   components (fields)
    	public List<NamedObject> getFields(GenObject info)
        {
            Object ob;
            GenObject gob;
            NamedObject nob;
            List<NamedObject> l_nob = new LinkedList<NamedObject>();

            int len = Array.getLength(info.obj); // length of the array object

            // index is name, object at index is genobject

            for( int i = 0; i < len; i++ )
            {
                ob  = Array.get(info.obj,i);

                Class c = ob.getClass();

                try
                {
                    if(ob != null)
                    {
                        gob = new GenObject(ob, primitiveWrap(c) ); // check if is primitive for the constructor
                        nob = new NamedObject("[" + Integer.toString(i) + "]", gob);
                    }
                    else
                    {
                        gob = new GenObject(ob, primitiveWrap(c)); // set as true
                        nob = new NamedObject("[" + Integer.toString(i) + "]", gob);
                    }
                    l_nob.add(nob);
                }
                catch(Exception e)
                {
                    System.out.println("CAUGHT: " + e.toString());
                }
            }

            return l_nob;
        }

    	// returns the name to be used for the object "info"
    	public String className(GenObject info)
        {
            return info.obj.getClass().getCanonicalName();
        }

        public boolean primitiveWrap(Class c)
        {
            if(c.isPrimitive())
            {
                return true;
            }

            if(
                c == Integer.class || c == Long.class || c == Double.class ||
                c == Float.class || c == Boolean.class || c == Character.class ||
                c == Byte.class || c == Void.class || c == Short.class
            )
            {
                return true;
            }
            return false;
        }
}