
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TVector {

	private double[] fData;
	public static double EPS=1e-10;

	public TVector(){
		fData=new double[0];
	}

	public TVector(TVector src) {

		fData=new double[src.fData.length];
		for(int i=0;i<fData.length;++i) {
			fData[i]=src.fData[i];
		}
	}

	public TVector copyFrom(TVector src) {//よくわからない
		if(fData.length != src.fData.length) {
			fData=new double[src.fData.length];
		}
		for(int i=0;i<fData.length;++i) {
			fData[i]=src.fData[i];
		}
		return this;
	}

	@Override
	public TVector clone() {
		return new TVector(this);
	}

	public void writeTo(PrintWriter pw) {
		pw.println(fData.length);
		for(int i=0;i<fData.length;++i) {
			pw.print(fData[i]+" ");
		}
		pw.println();
		pw.close();
	}

	public void readFrom(BufferedReader br) throws IOException {
		int dimension =Integer.parseInt(br.readLine());
		setDimension(dimension);
		String[] tokens =br.readLine().split(" ");
		for (int i=0;i<dimension;++i) {
			fData[i]=Double.parseDouble(tokens[i]);
		}
	}

	public void setDimension(int dimension) {
		if(fData.length!=dimension) {
			fData=new double[dimension];
		}
	}

	public int getDimension(){
		return fData.length;
	}

	public double getElement(int index) {
		return fData[index];
	}

	public void setElement(int index,double e) {
		if(index<fData.length) {
			fData[index]=e;
		}
	}

	@Override
	public String toString() {
		String str=" ";
		for(int i=0;i<fData.length;++i) {
			str +=fData[i]+" ";
		}
		return str;
	}

	@Override
	public boolean equals(Object o) {
		TVector v = (TVector)o;
		for(int i=0;i<fData.length;++i) {
			if(Math.abs(fData[i]-v.fData[i])>EPS) {
				return false;

			}
		}
		return true;
	}

	public TVector add(TVector v) {
		for(int i=0;i<fData.length;++i) {
			fData[i]+=v.fData[i];
		}
		return this;
	}

	public TVector subtract(TVector v) {
		for(int i=0;i<fData.length;++i) {
			fData[i]-=v.fData[i];
		}
		return this;
	}

	public double calculateL2norm() {
		double norm=0;
		for(int i=0;i<fData.length;++i) {
			norm+=fData[i]*fData[i];
		}
		return Math.sqrt(norm);
	}
	public TVector scalarProduct(double x) {
		for (int i=0;i<fData.length;++i) {
			fData[i]=fData[i]*x;
		}
		return this;
	}
	public double innerProduct(TVector v) {
		double ans=0;
		for(int i=0;i<fData.length;++i) {
			ans+=fData[i]*v.fData[i];
		}
		return ans;
	}
	public TVector elementwiseProduct(TVector v) {
		for (int i=0;i<fData.length;++i) {
			fData[i]=fData[i]*v.fData[i];
		}
		return this;
	}
	public TVector normalize() {
		double norm=this.calculateL2norm();
		for(int i=0;i<fData.length;++i) {
			fData[i]=fData[i]/norm;
		}
		return this;
	}

}

class Test{
public static void main(String[] args)throws IOException{

	System.out.println("Test start\r\n");

	System.out.println("generate vector v1");

	TVector v1=new TVector();
	String string1=v1.toString();
	System.out.println("just new v1:"+string1);

	v1.setDimension(4);
	for(int i=0;i<v1.getDimension();++i){
		double l=i*2.5;
		v1.setElement(i, l);
	}

	String string2=v1.toString();
	System.out.println("Dimension, elements was set v1:"+string2+"\r\n");

	System.out.println("copy test");


	TVector v2=new TVector(v1);

	TVector v3=new TVector();
	v3=v1.clone();

	TVector v4 =new TVector();
	v4=v1;

	TVector v5 = new TVector();
	v5=v1.copyFrom(v1);

	TVector v6= new TVector();
	v6=v6.copyFrom(v1);

	File file = new File("test1.txt");
    FileWriter filewriter = new FileWriter(file);
    BufferedWriter bw = new BufferedWriter(filewriter);
    PrintWriter pw = new PrintWriter(bw);



    v1.writeTo(pw);

    TVector v7 =new TVector();
    try{
        	File f = new File("test1.txt");
        	FileReader fr = new FileReader(f);
        	BufferedReader br = new BufferedReader(fr);

        	v7.readFrom(br);
    	}catch(FileNotFoundException e){
    		System.out.println(e);
    	}




	System.out.println("Compared with v1");


	System.out.println("TVector(v1);"+v1.equals(v2));
	System.out.println("v1.clone();"+v1.equals(v3));
	System.out.println("v4=v1;"+v1.equals(v4));
	System.out.println("v1.copyFrom(v1);"+v1.equals(v5));
	System.out.println("v6.copuFrom(v1);"+v1.equals(v6));
	System.out.println("Write,read;"+v1.equals(v7)+"\r\n");

	System.out.println("v1 is changed\r\n");

	v1.setElement(2, 10);
	System.out.println("v1:"+v1);

	System.out.println("TVector(v1);"+v1.equals(v2));
	System.out.println("v1.clone();"+v1.equals(v3));
	System.out.println("v4=v1;"+v1.equals(v4));
	System.out.println("v1.copyFrom(v1);"+v1.equals(v5));
	System.out.println("v6.copuFrom(v1);"+v1.equals(v6));
	System.out.println("Write,read;"+v1.equals(v7)+"\r\n");


	System.out.println("generate new vector v8"+"\r\n");

	TVector v8=new TVector();
	v8.setDimension(4);
	for(int i=0;i<v8.getDimension();++i ){
		v8.setElement(i,6);
	}

	TVector v9=new TVector();
	v9=v1.clone();

	System.out.println("v1:"+v9);
	System.out.println("v8:"+v8);
	System.out.println("v1+v8:"+v9.add(v8));
	v9=v1.clone();
	System.out.println("v1-v8:"+v9.subtract(v8));
	v9=v1.clone();
	System.out.println("normalized v1:"+v9.normalize());
	System.out.println("Norm of normalized:"+v9.calculateL2norm());

	v9=v1.clone();
	System.out.println("naiseki v1 and v8"+v9.innerProduct(v8));
	System.out.println("v1*4="+v9.scalarProduct(4));
	v9=v1.clone();
	System.out.println("elementwise product v1 and v8 ="+v9.elementwiseProduct(v8)+"\r\n");


	System.out.println("Indivisual test"+"\r\n");

	TIndivisual In1=new TIndivisual();
	In1.setEvaluationValue(8);
	System.out.println("EV set :"+In1);
	In1.setVector(v1);
	System.out.println("Vector set:"+In1);
	System.out.println("EV of In1:"+In1.getEvaluationValue());
	System.out.println("vector of In1:"+In1.getVector());

	File file2 = new File("test2.txt");
    FileWriter filewriter2 = new FileWriter(file2);
    BufferedWriter bw2 = new BufferedWriter(filewriter2);
    PrintWriter pw2 = new PrintWriter(bw2);

    In1.writeTo(pw2);

    TIndivisual In2 =new TIndivisual();
    try{

        	FileReader fr2 = new FileReader(file2);
        	BufferedReader br2 = new BufferedReader(fr2);

        	In2.readFrom(br2);
        	System.out.println("In2, read from:"+In2);

    	}catch(FileNotFoundException e){
    		System.out.println(e);
    	}

    TIndivisual In3 =new TIndivisual();

    In3 = In1.clone();

    System.out.println("In3, cloned:"+In3+"\r\n");


    System.out.println("set vector without setVector(v)\r\n");

    TVector v10= new TVector();

    v10.setDimension(5);
    for(int i=0;i<v10.getDimension();++i) {
    	v10.setElement(i,i*2+6);
    }

    System.out.println("Generated vector v10:"+v10);

    TIndivisual In4= new TIndivisual();

    In4.setEvaluationValue(33.4);
    In4.getVector().copyFrom(v10);

    System.out.println("set vector v10:"+In4+"\r\n");

    v10.setElement(1,9999);



    System.out.println("v10 was changed:"+v10);
    System.out.println("after v10 changed:"+In4);
















}}



