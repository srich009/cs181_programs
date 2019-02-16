import java.io.*;
import java.util.*;

public class Test4 {
	static public void main(String[] args) throws IOException {
		GraphDrawer gd = new TxtDrawer("-");
		DataDrawer dds = new DataDrawer(gd);
		dds.addFormatter(new FieldFormat());
		A a = new C(3,100.75);
		a.next = new B(-1,-3.14);
		dds.addObject(a);
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

	static class C extends B {
		public C(int ii, double ee) {
			super(ii,ee);
			f = ii+ee;
		}
		private double f;
	}
}
