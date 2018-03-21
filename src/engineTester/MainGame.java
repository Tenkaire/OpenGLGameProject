package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import models.RawModel;
import models.TextureModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.staticShader;
import textures.ModelTexture;

public class MainGame {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		staticShader shader = new staticShader();
		Renderer renderer = new Renderer(shader);
		
		RawModel model = OBJLoader.loadOBJModel("stall", loader);
		ModelTexture texture = new ModelTexture(loader.loadTexture("stallTexture"));
		TextureModel staticModel = new TextureModel(model, texture);
		Entity entity = new Entity(staticModel, new Vector3f(0,0,-20),0,0,0,1);
		Camera camera = new Camera();
		
		while(!Display.isCloseRequested()) {
			entity.increaseRotation(0, 1, 0);
			camera.move();
			renderer.prepare();
			//game logic
			// rendering
			shader.start();
			shader.loadViewMatrix(camera);
			renderer.render(entity, shader);
//			renderer.render(model);
			shader.stop();
			DisplayManager.updateDisplay();
		}
		shader.deleteAll();
		loader.deleteAll();
		
		DisplayManager.updateDisplay();

	}

}
