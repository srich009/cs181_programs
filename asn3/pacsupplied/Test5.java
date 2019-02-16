import java.io.*;
import java.util.*;

public class Test5 {
	static public void main(String[] args) throws IOException {
		GraphDrawer gd = new TxtDrawer("-");
		DataDrawer dds = new DataDrawer(gd);
		dds.addFormatter(new FieldFormat());
		A a = new B(3,4.5);
		a.next = new A(-1);
		A a2 = new B(0,-100.3);
		a2.next = a.next;
		dds.addObject(a);
		dds.addObject(a2);
		gd.draw();
	}

	static class A {
		public A(int ii) {
			i = ii;
			d = i+0.5;
		}
		private int i;
		private double d;
		public A next;
	}

	static class B extends A {
		public B(int ii, double ee) {
			super(ii);
			e = ee;
		}
		private double e;
	}
}
