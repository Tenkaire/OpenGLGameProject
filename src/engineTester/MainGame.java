package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
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
		staticShader shader = new staticShader();
		Renderer renderer = new Renderer(shader);
		
		float[] vertices = {            
                -0.5f,0.5f,0,   
                -0.5f,-0.5f,0,  
                0.5f,-0.5f,0,   
                0.5f,0.5f,0,        
                 
                -0.5f,0.5f,1,   
                -0.5f,-0.5f,1,  
                0.5f,-0.5f,1,   
                0.5f,0.5f,1,
                 
                0.5f,0.5f,0,    
                0.5f,-0.5f,0,   
                0.5f,-0.5f,1,   
                0.5f,0.5f,1,
                 
                -0.5f,0.5f,0,   
                -0.5f,-0.5f,0,  
                -0.5f,-0.5f,1,  
                -0.5f,0.5f,1,
                 
                -0.5f,0.5f,1,
                -0.5f,0.5f,0,
                0.5f,0.5f,0,
                0.5f,0.5f,1,
                 
                -0.5f,-0.5f,1,
                -0.5f,-0.5f,0,
                0.5f,-0.5f,0,
                0.5f,-0.5f,1
                 
        };
         
        float[] textureCoords = {
                 
                0,0,
                0,1,
                1,1,
                1,0,            
                0,0,
                0,1,
                1,1,
                1,0,            
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0
 
                 
        };
         
        int[] indices = {
                0,1,3,  
                3,1,2,  
                4,5,7,
                7,5,6,
                8,9,11,
                11,9,10,
                12,13,15,
                15,13,14,   
                16,17,19,
                19,17,18,
                20,21,23,
                23,21,22
 
        };
		
		RawModel model = loader.loadToVAO(vertices,textureCoords, indices);
		ModelTexture texture = new ModelTexture(loader.loadTexture("boy"));
		TextureModel staticModel = new TextureModel(model, texture);
		Entity entity = new Entity(staticModel, new Vector3f(0,0,-5),0,0,0,1);
		Camera camera = new Camera();
		
		while(!Display.isCloseRequested()) {
			entity.increaseRotation(1, 1, 0);
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
