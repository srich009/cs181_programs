import java.io.*;
import java.util.*;

public class Test6 {
	static public void main(String[] args) throws IOException {
		GraphDrawer gd = new TxtDrawer("-");
		DataDrawer dds = new DataDrawer(gd);
		dds.addFormatter(new FieldFormat());
		dds.addFormatter(new ArrayFormat());
		A[] as = new A[3];
		for(int i=0;i<3;i++) {
			as[i] = new A(i);
			as[i].next = as[0];
		}
		dds.addObject(as);
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
}
