 import java.lang.reflect.*;
 import java.util.*;

class FieldFormat implements Formatter
{
    	// applies is true iff this formatter can be used with the object "info"
    	public boolean applies(GenObject info)
        {
            return true; // FIXME
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
            List<NamedObject> l = new LinkedList<NamedObject>();
            Object ob;
            GenObject gob;
            NamedObject nob;

            // getDeclaredFields

            for( Field field : info.obj.getClass().getDeclaredFields() )
            {
                field.setAccessible(true); // access private

                try
                {
                    ob = field.get(info.obj);

                    if(ob != null)
                    {
                        gob = new GenObject(ob, primitiveWrap(ob.getClass().getName()) ); // check if is primitive for the constructor
                        nob = new NamedObject(field.getName(), gob);
                    }
                    else
                    {
                        gob = new GenObject(ob, true); // set as true
                        nob = new NamedObject(field.getName(), gob);
                    }

                    l.add(nob);
                }
                catch(Exception e)
                {
                    System.out.println("CAUGHT: " + e.toString());
                }
            }

            return l;
        }

    	// returns the name to be used for the object "info"
    	public String className(GenObject info)
        {
            return info.obj.getClass().getName();
        }

        // true if the object is actually supposed to be a primitive object
        // to hold an Integer,
        //   obj has the Integer and isprim=false
        // to hold an int,
        //   obj has an Integer and isprim=true
        // isprim should only be true if obj is
        // Integer, Long, Double, Float, Boolean, Character, Byte, Void, or Short
        // (the primitive type wrapper classes)
        public boolean primitiveWrap(String s)
        {
            // System.out.println(s);
            if(
                s == "java.lang.Integer" || s == "java.lang.Long" || s == "java.lang.Double" ||
                s == "java.lang.Float" || s == "java.lang.Boolean" || s == "java.lang.Character" ||
                s == "java.lang.Byte" || s == "java.lang.Void" || s == "java.lang.Short"
            )
            {
                return true;
            }
            return false;
        }
}