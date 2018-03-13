package engineTester;

import org.lwjgl.opengl.Display;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;

public class MainGame {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		
		float[] vertices = {
				-0.5f, 0.5f, 0f,
				-0.5f, -0.5f, 0f,
				0.5f, -0.5f,0f, // left bottom triangle
				0.5f, 0.5f, 0f // right top triangle
		};
		
		int[] indices = {
				0,1,3,	// top left triangle
				3,1,2	// bottom right triangle
		};
		
		RawModel model = loader.loadToVAO(vertices,indices);
		
		while(!Display.isCloseRequested()) {
			
			renderer.prepare();
			//game logic
			// rendering
			renderer.render(model);
			DisplayManager.updateDisplay();
		}
		
		loader.DeleteAll();
		
		DisplayManager.updateDisplay();

	}

}
