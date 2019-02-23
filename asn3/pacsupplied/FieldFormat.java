 import java.lang.reflect.*;
 import java.util.*;

class FieldFormat implements Formatter
{
    	// applies is true iff this formatter can be used with the object "info"
    	public boolean applies(GenObject info)
        {
            return true;
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
            List<Class> l_class = new LinkedList<Class>();

            // while getSuperClass is not null
            Class cur = info.obj.getClass();
            while(cur.getSuperclass() != null)
            {
                l_class.add(cur);
                cur = cur.getSuperclass();
            }

            Collections.reverse(l_class); // have to get in reverse order for the super class down to sub classes
            for(Class c : l_class)
            {
                // getDeclaredFields
                for( Field field : c.getDeclaredFields() )
                {
                    try
                    {
                        field.setAccessible(true); // access private
                        ob = field.get(info.obj);

                        if(ob != null)
                        {
                            gob = new GenObject(ob, primitiveWrap(field.getType())); // check if is primitive for the constructor
                            nob = new NamedObject(field.getName(), gob);
                        }
                        else
                        {
                            gob = new GenObject(ob, primitiveWrap(field.getType())); // set as true
                            nob = new NamedObject(field.getName(), gob);
                        }
                        l_nob.add(nob);
                    }
                    catch(Exception e)
                    {
                        System.out.println("CAUGHT: " + e.toString());
                    }
                }
            }

            return l_nob;
        }

    	// returns the name to be used for the object "info"
    	public String className(GenObject info)
        {
            return info.obj.getClass().getCanonicalName();
        }

        // true if the object is actually supposed to be a primitive object
        // to hold an Integer,
        //   obj has the Integer and isprim=false
        // to hold an int,
        //   obj has an Integer and isprim=true
        // isprim should only be true if obj is
        // Integer, Long, Double, Float, Boolean, Character, Byte, Void, or Short
        // (the primitive type wrapper classes)
        public boolean primitiveWrap(Class c)
        {
            if(c.isPrimitive())
            {
                return true;
            }

            // if(
            //     c == Integer.class || c == Long.class || c == Double.class ||
            //     c == Float.class || c == Boolean.class || c == Character.class ||
            //     c == Byte.class || c == Void.class || c == Short.class
            // )
            // {
            //     return true;
            // }
            return false;
        }

}