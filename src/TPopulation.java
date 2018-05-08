import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TPopulation {

	private TIndivisual[] fIndivisuals;

	public TPopulation(){
		fIndivisuals = new TIndivisual[0];
	}

	public TPopulation(int populationSize){
		fIndivisuals = new TIndivisual[populationSize];
		for (int i=0;i<fIndivisuals.length;++i){
			fIndivisuals[i]=new TIndivisual();
		}
	}

	public TPopulation(TPopulation src){
		fIndivisuals = new TIndivisual[src.fIndivisuals.length];
		for (int i=0;i<fIndivisuals.length;++i){
			fIndivisuals[i]=new TIndivisual(src.fIndivisuals[i]);
		}
	}

	@Override
	public TPopulation clone(){
		return new TPopulation(this);
	}

	public void setPopulationSize(int popSize){
		if(fIndivisuals.length!=popSize){
			fIndivisuals = new TIndivisual[popSize];
			for (int i=0;i<fIndivisuals.length;++i){
				fIndivisuals[i]=new TIndivisual();
			}
		}
	}

	public TPopulation copyFrom(TPopulation src){
		setPopulationSize(src.fIndivisuals.length);
		for (int i=0;i<fIndivisuals.length;++i){
			fIndivisuals[i].copyFrom(src.fIndivisuals[i]);
		}
		return this;
	}

	public int getPopulationSize(){
		return fIndivisuals.length;
	}

	public void writeTo(PrintWriter pw){
		pw.println(fIndivisuals.length);
		for(int i=0;i<fIndivisuals.length;++i){
			fIndivisuals[i].writeTo(pw);

		}
	}

	public void readFrom(BufferedReader br) throws IOException{
		int popSize =Integer.parseInt(br.readLine());
		setPopulationSize(popSize);
		for (int i=0;i<fIndivisuals.length;++i){
			fIndivisuals[i].readFrom(br);
		}
	}

	@Override
	public String toString(){
		String str = getPopulationSize()+"\n";
		for(int i=0;i<getPopulationSize();++i){
			str+=fIndivisuals[i].toString()+"\n";
		}

		return str;
	}

	public TIndivisual getIndivisual(int index){
		return fIndivisuals[index];
	}

	public void sortByEvaluationValue(){
		for (int i=0;i<getPopulationSize()-1;++i){
			for (int j=getPopulationSize()-1;j>i;--j){
				if(fIndivisuals[j-1].getEvaluationValue()>fIndivisuals[j].getEvaluationValue()){
					TIndivisual tmp = fIndivisuals[j-1];
					fIndivisuals[j-1]=fIndivisuals[j];
					fIndivisuals[j]=tmp;
				}
			}
		}
	}

	public static void main(String arg[])throws IOException{

		//test for constructer
		TPopulation pop1=new TPopulation();
		System.out.println(pop1);

		//test for constructer2
		TPopulation pop2=new TPopulation(4);
		System.out.println(pop2);

		//test setPopulationSize

		pop1.setPopulationSize(4);
		System.out.println(pop1);


		//create 4 Indivisuals for test
		TVector v1 =new TVector();
		TVector v2 =new TVector();
		TVector v3 =new TVector();
		TVector v4 =new TVector();
		v1.setDimension(4);
		v2.setDimension(4);
		v3.setDimension(4);
		v4.setDimension(4);
		for(int i=0;i<v1.getDimension();++i){
			v1.setElement(i, i);
			v2.setElement(i, 2*i+2);
			v3.setElement(i, 3*i+7);
			v4.setElement(i, i*5+1);
		}
		TIndivisual In1 =new TIndivisual();
		TIndivisual In2 =new TIndivisual();
		TIndivisual In3 =new TIndivisual();
		TIndivisual In4 =new TIndivisual();
		In1.getVector().copyFrom(v1);
		In2.getVector().copyFrom(v2);
		In3.getVector().copyFrom(v3);
		In4.getVector().copyFrom(v4);
		In1.setEvaluationValue(3.3);
		In2.setEvaluationValue(2.2);
		In3.setEvaluationValue(4.4);
		In4.setEvaluationValue(1.1);

		//set Indivisuals for pop1
		pop1.getIndivisual(0).copyFrom(In1);
		pop1.getIndivisual(1).copyFrom(In2);
		pop1.getIndivisual(2).copyFrom(In3);
		pop1.getIndivisual(3).copyFrom(In4);

		//test getPopulationSize
		System.out.println(pop1.getPopulationSize());

		//toString test
		System.out.println(pop1.toString());

		//copy constructer test

		TPopulation pop3 =new TPopulation(pop1);
		System.out.println(pop3);
		pop1.setPopulationSize(2);
		System.out.println(pop3);

		//clone test

		TPopulation pop4 =new TPopulation();
		pop4=pop3.clone();
		System.out.println(pop4);
		pop3.setPopulationSize(2);
		System.out.println(pop4);

		//copyFromtest

		TPopulation pop5 = new TPopulation();

		pop5.copyFrom(pop4);
		System.out.println(pop5);
		pop4.setPopulationSize(2);
		System.out.println(pop5);

		//getIndivisusal test

		System.out.println(pop5.getIndivisual(3));

		//sort test
		pop5.sortByEvaluationValue();
		System.out.println(pop5);

		//test getIndivisual
		System.out.println(pop5.getIndivisual(1));

		//write read test

		File file = new File("testPop.txt");
	    FileWriter filewriter = new FileWriter(file);
	    BufferedWriter bw = new BufferedWriter(filewriter);
	    PrintWriter pw = new PrintWriter(bw);



	    pop5.writeTo(pw);
	    pw.close();

	    TPopulation pop6=new TPopulation();
	    try{

	        	FileReader fr = new FileReader(file);
	        	BufferedReader br = new BufferedReader(fr);

	        	pop6.readFrom(br);
	    	}catch(FileNotFoundException e){
	    		System.out.println(e);
	    	}
	    System.out.println(pop6);






	}



}









