package project.lanshan.javarpc;

public class TestDemo {
	private String A;
	private String B;
	public TestDemo(){
		this.A = "initA";
		this.B = "initB";
	}
	public TestDemo(String A,String B){
		this.A = A;
		this.B = B;
	}
	public void TestArg(String C,int D){
		System.out.println(C+"  "+D);
	}
	public String getA(){
		return A;
	}
	public String getB(){
		return B;
	}
}
