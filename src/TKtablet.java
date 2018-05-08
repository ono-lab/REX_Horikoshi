import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TKtablet {


	private int fK;

	public TKtablet(){
		fK=(int)Double.NaN;
	}

	public TKtablet(TKtablet src){
		fK=src.fK;
	}

	public TKtablet copyFrom(TKtablet src){
		fK=src.fK;
		return this;
	}

	@Override
	public TKtablet clone(){
		return new TKtablet(this);
	}

	public void writeTo(PrintWriter pw){
		pw.println(fK+"\n");

	}

	public void readFrom(BufferedReader br)throws IOException{
		int k=Integer.parseInt(br.readLine());
		setK(k);
	}

	@Override
	public String toString(){
		String str ="";
		str+=fK;
		return str;
	}

	public void setK(int k){
		fK=k;
	}

	public int getK(){
		return fK;
	}

	public TIndivisual calculateKtablet(TIndivisual ind){

		double ans =0.0;
		for(int i=0;i<fK;++i){
			ans+=ind.getVector().getElement(i)*ind.getVector().getElement(i);
		}
		for(int j=fK;j<ind.getVector().getDimension();++j){
			ans+=ind.getVector().getElement(j)*ind.getVector().getElement(j)*10000;
		}
		ind.setEvaluationValue(ans);
		return ind;
	}

	public static void main(String arg[])throws IOException{

		//test for constructor
		TKtablet k1 =new TKtablet();

		//test for setK
		k1.setK(3);

		//test for getK
		System.out.println(k1.getK());

		//test for toString
		System.out.println(k1.toString());

		//test for copy constructor
		TKtablet k2 = new TKtablet(k1);
		System.out.println(k2);


		//test for clone
		TKtablet k3= k1.clone();
		System.out.println(k3);

		//test for copy from

		TKtablet k4= new TKtablet();
		k4.copyFrom(k1);
		System.out.println(k4);


		//make Indivisual
		TVector v1 =new TVector();
		v1.setDimension(6);
		for (int i=0;i<v1.getDimension();++i){
			v1.setElement(i, i+1);
		}
		TIndivisual in1=new TIndivisual();
		in1.getVector().copyFrom(v1);
		System.out.println(in1);

		//test for calculateKtablet
		System.out.println(k1.calculateKtablet(in1));


		//read write test

		File file = new File("testKtablet.txt");
	    FileWriter filewriter = new FileWriter(file);
	    BufferedWriter bw = new BufferedWriter(filewriter);
	    PrintWriter pw = new PrintWriter(bw);



	    k4.writeTo(pw);
	    pw.close();

	    TKtablet k5=new TKtablet();
	    try{

	        	FileReader fr = new FileReader(file);
	        	BufferedReader br = new BufferedReader(fr);

	        	k5.readFrom(br);
	    	}catch(FileNotFoundException e){
	    		System.out.println(e);
	    	}
	    System.out.println(k5);


	}


}
