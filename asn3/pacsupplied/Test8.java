import java.io.*;
import java.util.*;

public class Test8 {
	static public void main(String[] args) throws IOException {
		//GraphDrawer gd = new TxtDrawer("-");
		GraphDrawer gd = new DotDrawer("-");
		DataDrawer dds = new DataDrawer(gd);
		dds.addFormatter(new FieldFormat());
		dds.addFormatter(new ArrayFormat());
		dds.addFormatter(new StringFormat(true));
		ClassA a = new ClassB(3,2);
		dds.addObject(a);
		gd.draw();
	}

	static class ClassA {
		public class AInner {
			public AInner(int y) {
				lst = new int[num];
				for(int i=0;i<num;i++)
					lst[i] = y;
			}
			protected int[] lst;
		}
		public ClassA(int n) {
			num = n;
			in = new AInner[n];
			for(int i=0;i<n;i++)
				in[i] = new AInner(i);
		}

		protected int num;
		protected AInner[] in;
	}

	static class ClassB extends ClassA {

		public class BInner extends AInner {
			public BInner(int y) {
				super(y);
				sum = 0;
				for(int i=0;i<num;i++)
					sum += (lst[i] *= 2);
			}
			private int sum;
		}
		public ClassB(int n, int p) {
			super(n);
			pstr = String.valueOf(p);
			if (p<1) next = null;
			else next = new ClassB(n-1,p-1);
			for(int i=0;i<n;i++)
				if (i%2==0) in[i] = new BInner(i);
		}

		private String pstr;
		ClassB next;
	}
}
