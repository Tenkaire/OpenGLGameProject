package engineTester;

import org.lwjgl.opengl.Display;

import renderEngine.DisplayManager;

public class MainGame {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		
		while(!Display.isCloseRequested()) {
			//game logic
			// rendering
			DisplayManager.updateDisplay();
		}
		
		DisplayManager.updateDisplay();

	}

}
