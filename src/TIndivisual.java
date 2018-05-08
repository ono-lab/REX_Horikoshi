import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TIndivisual{

	private double fEvaluationValue;
	private TVector fVector;

	public TIndivisual() {
		fEvaluationValue=Double.NaN;
		fVector=new TVector();
	}

	public TIndivisual(TIndivisual src) {
		fEvaluationValue=src.fEvaluationValue;
		fVector=src.fVector;
	}


	public TIndivisual copyFrom(TIndivisual src){
		fEvaluationValue =src.fEvaluationValue;
		fVector.copyFrom(src.fVector);
		return this;
	}

	@Override
	public TIndivisual clone() {
		return new TIndivisual(this);
	}

	public void writeTo(PrintWriter pw) {
		pw.println(fEvaluationValue);
		fVector.writeTo(pw);
	}

	public void readFrom(BufferedReader br) throws IOException{
		fEvaluationValue=Double.parseDouble(br.readLine());
		fVector.readFrom(br);
	}

	@Override
	public String toString() {
		String str =fEvaluationValue+"\n";
		str += fVector.toString();
		return str;
	}

	public double getEvaluationValue() {
		return fEvaluationValue;
	}

	public void setEvaluationValue(double eval) {
		fEvaluationValue=eval;
	}

	public TVector getVector() {
		return fVector;
	}

	public static void main(String[] args)throws IOException{

		System.out.println("Indivisual test"+"\r\n");

		TIndivisual In1=new TIndivisual();
		In1.setEvaluationValue(8);
		System.out.println("EV set :"+In1);

		TVector v1=new TVector();
		v1.setDimension(4);
		for(int i=0;i<v1.getDimension();++i){
			v1.setElement(i, i*2+7);
		}

		In1.getVector().copyFrom(v1);
		System.out.println("Vector set:"+In1);
		System.out.println("EV of In1:"+In1.getEvaluationValue());
		System.out.println("vector of In1:"+In1.getVector());

		File file2 = new File("testIn.txt");
	    FileWriter filewriter2 = new FileWriter(file2);
	    BufferedWriter bw2 = new BufferedWriter(filewriter2);
	    PrintWriter pw2 = new PrintWriter(bw2);

	    In1.writeTo(pw2);
	    pw2.close();

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

	    //copyFromのテスト
	    TIndivisual In4=new TIndivisual();
	    In4.copyFrom(In1);
	    System.out.println("In1"+In1);
	    System.out.println("In4"+In4);
	    In1.setEvaluationValue(123.3);
	    In1.getVector().setElement(1, 1024.2);
	    System.out.println("In1"+In1);
	    System.out.println("In4"+In4);

	}


}



