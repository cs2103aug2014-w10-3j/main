//@author A0112171Y

package sg.codengineers.ldo.controller;

public class LDo {
	private static Controller controller;
	
	public LDo(){
		controller = Controller.getInstance();
	}

	public void run(){
		controller.run();
	}
	
	public static void main(String[] args){
		LDo ldo = new LDo();
		ldo.run();
	}
}
