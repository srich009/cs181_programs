import java.io.*;
import java.util.*;

public class Test1 {
	static public void main(String[] args) throws IOException {
		GraphDrawer gd = new TxtDrawer("-");
		DataDrawer dds = new DataDrawer(gd);
		dds.addFormatter(new FieldFormat());
		A a = new A(3);
		dds.addObject(a);
		gd.draw();
	}

	static class A {
		public A(int ii) {
			i = ii;
			d = i+0.5;
		}
		public int i;
		public double d;
	}
}
