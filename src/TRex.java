import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class TRex {

	private int fLambda;
	private double fVariance;
	private TPopulation fChildrenPop;
	private Random fRand;



	public TRex(){
		fLambda=0;
		fVariance=0.0;


	}

	public TRex(TRex src){
		fLambda=src.fLambda;
		fVariance =src.fVariance;
		fChildrenPop=src.fChildrenPop;
		fRand =src.fRand;
	}

	public TRex copyFrom(TRex src){
		fLambda=src.fLambda;
		fVariance =src.fVariance;
		fChildrenPop=src.fChildrenPop;
		fRand =src.fRand;

		return this;
	}

	@Override
	public TRex clone(){
		return new TRex(this);
	}

	public void setLambda(int lambda){
		fLambda=lambda;
	}

	public void setVariance(double variance){
		fVariance=variance;
	}
	public int getLambda(){
		return fLambda;
	}

	public double getVariance(){
		return fVariance;
	}

	public void setRex(){

		fChildrenPop=new TPopulation();
		fRand =new Random();
	}

	public TPopulation makeChildren(TPopulation parentPop){



		fChildrenPop.setPopulationSize(fLambda);



		//zero vector を用意
		TVector zeroVector =new TVector();
		TVector meanVector = new TVector();
		TVector sumVector =new TVector();
		TVector subVector = new TVector();
		zeroVector.setDimension(parentPop.getIndivisual(0).getVector().getDimension());
		for(int i=0;i<zeroVector.getDimension();++i){
			zeroVector.setElement(i, 0);
		}

		meanVector.copyFrom(zeroVector);

		for (int i=0;i<parentPop.getPopulationSize();++i){
			meanVector.add(parentPop.getIndivisual(i).getVector());
		}
		meanVector.scalarProduct(1.0/(parentPop.getPopulationSize()));






		for(int i=0;i<fLambda;i++){
			sumVector.copyFrom(zeroVector);


			for (int j=0;j<parentPop.getPopulationSize();++j){
				subVector.copyFrom(zeroVector);
				subVector.add(parentPop.getIndivisual(j).getVector());
				subVector.subtract(meanVector);
				subVector.scalarProduct(fRand.nextGaussian()*Math.sqrt(fVariance));
				sumVector.add(subVector);
			}
			fChildrenPop.getIndivisual(i).getVector().copyFrom(sumVector.add(meanVector));

		}

		return fChildrenPop;
	}

	public static void main(String arg[])throws IOException{


		//make children test
		TVector v1=new TVector();
		TVector v2=new TVector();
		TVector v3=new TVector();
		v1.setDimension(2);
		v2.setDimension(2);
		v3.setDimension(2);
		v1.setElement(0, -1);
		v1.setElement(1, 0);
		v2.setElement(0, 1);
		v2.setElement(1, 0);
		v3.setElement(0, 1);
		v3.setElement(1, 5);
		TIndivisual in1=new TIndivisual();
		TIndivisual in2=new TIndivisual();
		TIndivisual in3=new TIndivisual();
		in1.getVector().copyFrom(v1);
		in2.getVector().copyFrom(v2);
		in3.getVector().copyFrom(v3);
		TPopulation pop1=new TPopulation();
		pop1.setPopulationSize(3);
		System.out.println(pop1);
		pop1.getIndivisual(0).copyFrom(in1);
		pop1.getIndivisual(1).copyFrom(in2);
		pop1.getIndivisual(2).copyFrom(in3);
		System.out.println(pop1);

		TRex rex =new TRex();


			//set get test

		rex.setLambda(1000);
		rex.setVariance(0.5);
		rex.setRex();

		System.out.println(rex.getLambda());
		System.out.println(rex.getVariance());


		TPopulation newPop= new TPopulation();
		newPop.copyFrom(rex.makeChildren(pop1));
		System.out.println(newPop);
		System.out.println(pop1);
		newPop.copyFrom(rex.makeChildren(pop1));
		System.out.println(newPop);

		File file = new File("testrex.txt");
	    FileWriter filewriter = new FileWriter(file);
	    BufferedWriter bw = new BufferedWriter(filewriter);
	    PrintWriter pw = new PrintWriter(bw);

	    for(int i=0;i<newPop.getPopulationSize();++i) {
	    	for (int j=0;j<2;++j){
	    		pw.print(newPop.getIndivisual(i).getVector().getElement(j)+" ");
	    	}
	    	pw.println();
		}

	    pw.close();

	  //copy test

		TRex rex2=new TRex(rex);
		TRex rex3= new TRex();
		TRex rex4=rex.clone();
		rex3.copyFrom(rex);
		System.out.println(rex2.getLambda());
		System.out.println(rex2.getVariance());
		System.out.println(rex3.getLambda());
		System.out.println(rex3.getVariance());
		System.out.println(rex4.getLambda());
		System.out.println(rex4.getVariance());


	}





}











