package sg.codengineers.ldo.controller;

public class LDo {
	Controller controller;
	
	public LDo(){
		controller = new Controller();
	}

	public void run(){
		controller.run();
	}
	
	public static void main(String[] args){
		LDo ldo = new LDo();
		ldo.run();
	}
}
