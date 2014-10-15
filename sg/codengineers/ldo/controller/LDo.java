package sg.codengineers.ldo.controller;

public class LDo {
	private static Controller controller;
	
	public LDo(){
		controller = Controller.getControllerInstance();
	}
	
	public void run(){
		controller.run();
	}
	
	public static void main(String[] args){
		LDo ldo = new LDo();
		ldo.run();
	}
}
