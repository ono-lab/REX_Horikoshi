import java.io.BufferedReader;
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

	public void setVector(TVector v) {//不要
		fVector = v;
	}
}



