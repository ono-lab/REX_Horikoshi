import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class TJgg {

	private int fGeneration;
	private int fGenerationEnd;
	private double fTargetEvaluationValue;
	private double fBestEvaluationValue;
	private int fPopulationSize;
	private int fChildrenSize;
	private double fMaxArea;
	private double fMinArea;
	private int fDimension;
	private TPopulation fAllPopulation;
	private TPopulation fParentPopulation;
	private TPopulation fChildrenPopulation;
	private TKtablet fKtablet;
	private TRex fRex;
	private int fEvaluationNumber;
	private Random fRandSelect;


	public TJgg(){
		fGeneration=0;
		fGenerationEnd=0;
		fTargetEvaluationValue=0;
		fBestEvaluationValue=1e+10;
		fAllPopulation = new TPopulation();
		fParentPopulation = new TPopulation();
		fChildrenPopulation = new TPopulation();
		//fRex = new TRex();

	}

	public void setDimension(int dimension){
		fDimension=dimension;
	}

	public void setGenerationEnd(int generationEnd){
		fGenerationEnd=generationEnd;
	}

	public void setTargetEvaluationValue(double min){
		fTargetEvaluationValue=min;
	}

	public TPopulation getAllPopulation(){
		return fAllPopulation;
	}

	public TPopulation getParentPopulation(){
		return fParentPopulation;
	}

	public TPopulation getChildrenPopulation(){
		return fChildrenPopulation;
	}

	public int getEvaluationNumber(){
		return fEvaluationNumber;
	}





	public void createInitialPopulation(){
		fGeneration=0;
		fEvaluationNumber=0;


		Random randCreate = new Random();
		fRandSelect = new Random();

		fAllPopulation=new TPopulation();
		fAllPopulation.setPopulationSize(fPopulationSize);
		fParentPopulation=new TPopulation();
		fParentPopulation.setPopulationSize(fDimension+1);
		fChildrenPopulation=new TPopulation();
		fChildrenPopulation.setPopulationSize(fChildrenSize);
		fRex=new TRex();
		fRex.setRex();
		fRex.setLambda(fChildrenSize);
		fRex.setVariance((double)1/fDimension);




		for (int i=0;i<fPopulationSize;++i){
			fAllPopulation.getIndivisual(i).getVector().setDimension(fDimension);
			for(int j=0;j<fDimension;++j){
				fAllPopulation.getIndivisual(i).getVector().setElement(j, randCreate.nextDouble()*(fMaxArea-fMinArea)-(fMaxArea-fMinArea)/2);
			}
		}



		for(int i=0;i<fAllPopulation.getPopulationSize();++i){
			fAllPopulation.getIndivisual(i).copyFrom(fKtablet.calculateKtablet(fAllPopulation.getIndivisual(i)));
			fEvaluationNumber+=1;

		}
		fAllPopulation.sortByEvaluationValue();
		fBestEvaluationValue=fAllPopulation.getIndivisual(0).getEvaluationValue();
	}

	public void createParent(){
		fGeneration+=1;



		for(int i=0;i<fDimension+1;++i){//n+1個の親を作成



			int selected=fRandSelect.nextInt(fAllPopulation.getPopulationSize()-i);
			fParentPopulation.getIndivisual(i).copyFrom(fAllPopulation.getIndivisual(selected));

			for(int j=selected;j<fAllPopulation.getPopulationSize()-1;++j){
				fAllPopulation.getIndivisual(j).copyFrom(fAllPopulation.getIndivisual(j+1));
			}
		}
	}

	public void giveToRex(){



		fChildrenPopulation.copyFrom(fRex.makeChildren(fParentPopulation));

		for(int i=0;i<fChildrenPopulation.getPopulationSize();++i){
			fChildrenPopulation.getIndivisual(i).copyFrom(fKtablet.calculateKtablet(fChildrenPopulation.getIndivisual(i)));
			fEvaluationNumber+=1;
		}
		fChildrenPopulation.sortByEvaluationValue();
	}

	public void upDatePopulation(){

		for(int i=0;i<fDimension+1;++i){
			fAllPopulation.getIndivisual(fAllPopulation.getPopulationSize()-i-1).copyFrom(fChildrenPopulation.getIndivisual(i));
		}
		fAllPopulation.sortByEvaluationValue();
		if(fBestEvaluationValue>fAllPopulation.getIndivisual(0).getEvaluationValue()){
			fBestEvaluationValue=fAllPopulation.getIndivisual(0).getEvaluationValue();
		}
	}




	public void setJgg(int dimension, double minArea, double maxArea, int populationSize, int childrenSize ,double targetEV,int generationEnd,int k){
		fDimension=dimension;
		fMaxArea=maxArea;
		fMinArea=minArea;
		fPopulationSize=populationSize;
		fChildrenSize=childrenSize;
		fTargetEvaluationValue=targetEV;
		fGenerationEnd=generationEnd;


		fKtablet=new TKtablet();
		fKtablet.setK(k);


	}


	public void startJgg(String name)throws IOException{

		File file = new File(name);
	    FileWriter filewriter = new FileWriter(file);
	    BufferedWriter bw = new BufferedWriter(filewriter);
	    PrintWriter pw = new PrintWriter(bw);

		createInitialPopulation();
		pw.print(fEvaluationNumber+" "+fBestEvaluationValue);
	    pw.println();


		while(fGeneration<fGenerationEnd && fBestEvaluationValue>fTargetEvaluationValue){
			createParent();
			giveToRex();
			upDatePopulation();
			pw.print(fEvaluationNumber+" "+fBestEvaluationValue);
		    pw.println();

		}
		pw.close();

	}

	public static void main(String arg[])throws IOException{

		TJgg jgg= new TJgg();
		jgg.setJgg(2, -5, 5, 10, 1000, 0.1, 100, 1);

		//test for create Initial Population
		jgg.createInitialPopulation();
		System.out.println("Initial pop");
		System.out.println(jgg.getAllPopulation());
		System.out.println(jgg.getEvaluationNumber());

		//test for create Parent
		jgg.createParent();
		System.out.println("Initial pop selected");
		System.out.println(jgg.getAllPopulation());
		System.out.println("parent");
		System.out.println(jgg.getParentPopulation());

		//test for using REX
		jgg.giveToRex();
		System.out.println("children");
		System.out.println(jgg.getChildrenPopulation());

		//test for update Population
		jgg.upDatePopulation();
		System.out.println("second pop");
		System.out.println(jgg.getAllPopulation());
		File file = new File("testjgg.txt");
	    FileWriter filewriter = new FileWriter(file);
	    BufferedWriter bw = new BufferedWriter(filewriter);
	    PrintWriter pw = new PrintWriter(bw);

	    for(int i=0;i<jgg.getChildrenPopulation().getPopulationSize();++i) {
	    	for (int j=0;j<2;++j){
	    		pw.print(jgg.getChildrenPopulation().getIndivisual(i).getVector().getElement(j)+" ");
	    	}
	    	pw.println();
		}

	    pw.close();


	    //full package test
	    System.out.println("full test start");
	    int n=20;
	    TJgg jgg2= new TJgg();
	    jgg2.setJgg(n, -5, 5, 14*n, 5*n, 1.0e-7, 400000, n/4);

	    jgg2.startJgg("chi2n3.txt");
	    System.out.println("finiesh");



	}





}










