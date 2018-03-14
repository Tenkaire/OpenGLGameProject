package engineTester;

import org.lwjgl.opengl.Display;

import models.RawModel;
import models.TextureModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.staticShader;
import textures.ModelTexture;

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
		
		float[] textureCoords = {
				0,0,	//V0
				0,1,	//V1
				1,1,	//V2
				1,0		//V3
		};
		
		RawModel model = loader.loadToVAO(vertices,textureCoords, indices);
		ModelTexture texture = new ModelTexture(loader.loadTexture("boy"));
		TextureModel texturedModel = new TextureModel(model, texture);
		
		while(!Display.isCloseRequested()) {
			
			renderer.prepare();
			//game logic
			// rendering
			shader.start();
			renderer.render(texturedModel);
//			renderer.render(model);
			shader.stop();
			DisplayManager.updateDisplay();
		}
		shader.deleteAll();
		loader.deleteAll();
		
		DisplayManager.updateDisplay();

	}

}
