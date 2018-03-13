package engineTester;

import org.lwjgl.opengl.Display;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;
import shaders.staticShader;

public class MainGame {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		staticShader shader = new staticShader();
		
		float[] vertices = {
				-0.5f, 0.5f, 0f,
				-0.5f, -0.5f, 0f,
				0.5f, -0.5f,0f, 
				0.5f, 0.5f, 0f 
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
			shader.start();
			renderer.render(model);
			shader.stop();
			DisplayManager.updateDisplay();
		}
		shader.deleteAll();
		loader.DeleteAll();
		
		DisplayManager.updateDisplay();

	}

}
