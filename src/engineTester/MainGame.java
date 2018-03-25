package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TextureModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.staticShader;
import textures.ModelTexture;

public class MainGame {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();

		
		RawModel model = OBJLoader.loadOBJModel("dragon", loader);
		TextureModel staticModel = new TextureModel(model, new ModelTexture(loader.loadTexture("white")));
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);
//		Entity entity = new Entity(staticModel, new Vector3f(0,0,-25),0,0,0,1);
		Light light = new Light(new Vector3f(3000,2000,3000),new Vector3f(1,1,1));
		Camera camera = new Camera();
		// just wanna try git if it works.
		
		List<Entity> allDragon = new ArrayList<Entity>();
		Random random = new Random();
		
		for(int i = 0; i < 10; i++) {
			float x = random.nextFloat() * 100 - 50;
			float y = random.nextFloat() * 100 - 50;
			float z = random.nextFloat() * -300;
			allDragon.add(new Entity(staticModel,new Vector3f(x,y,z),random.nextFloat()*180f, 
					random.nextFloat()*180f, 0f, 1f));
		}
		
		MasterRenderer renderer = new MasterRenderer();
		
		while(!Display.isCloseRequested()) {
//			entity.increaseRotation(0, 1, 0);
			camera.move();
//			entity.move();
			for(Entity dragon: allDragon) {
				renderer.processEntity(dragon);
				dragon.move();
				dragon.increaseRotation(0, 1, 0);
			}
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		renderer.deleteAll();
		loader.deleteAll();
		
		DisplayManager.updateDisplay();

	}

}
