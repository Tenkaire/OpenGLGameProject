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
import renderEngine.EntityRenderer;
import shaders.staticShader;
import terrains.Terrain;
import textures.ModelTexture;

public class MainGame {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();

		
		RawModel model = OBJLoader.loadOBJModel("tree", loader);
		TextureModel staticModel = new TextureModel(model, new ModelTexture(loader.loadTexture("tree")));
		
		TextureModel fern = new TextureModel(OBJLoader.loadOBJModel("fern", loader), 
				new ModelTexture(loader.loadTexture("fern")));
		fern.getTexture().setHasTransparency(true);
		
		TextureModel grass = new TextureModel(OBJLoader.loadOBJModel("grassModel", loader), 
				new ModelTexture(loader.loadTexture("grassTexture")));
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		
		TextureModel rock = new TextureModel(OBJLoader.loadOBJModel("rock", loader), 
				new ModelTexture(loader.loadTexture("rock")));
		
		
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);
//		Entity entity = new Entity(staticModel, new Vector3f(0,0,-25),0,0,0,1);
		Light light = new Light(new Vector3f(3000,2000,3000),new Vector3f(1,1,1));
		Terrain terrain = new Terrain(0,-1, loader, new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain2 = new Terrain(-1,-1, loader, new ModelTexture(loader.loadTexture("grass")));
		Camera camera = new Camera();
		// just wanna try git if it works.
		
      List<Entity> entities = new ArrayList<Entity>();
      List<Entity> entities1 = new ArrayList<Entity>();
      Random random = new Random();
      for(int i=0;i<500;i++){
          entities.add(new Entity(staticModel, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,3));
          entities.add(new Entity(fern, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,0.5f));
          entities.add(new Entity(grass, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,1.5f));
      }
      
      for(int i=0;i<200;i++){
          entities.add(new Entity(rock, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,0.5f));
      }
//		
//		for(int i = 0; i < 10; i++) {
//			float x = random.nextFloat() * 100 - 50;
//			float y = random.nextFloat() * 100 - 50;
//			float z = random.nextFloat() * -300;
//			allDragon.add(new Entity(staticModel,new Vector3f(x,y,z),random.nextFloat()*180f, 
//					random.nextFloat()*180f, 0f, 1f));
//		}
		
		MasterRenderer renderer = new MasterRenderer();
		
		while(!Display.isCloseRequested()) {

			camera.move();
			for(Entity entity:entities){
        	  	renderer.processEntity(entity);
//    			entity.increaseRotation(0, 1, 0);
          	}
			
//			for(Entity entity:entities1){
//        	  	renderer.processEntity(entity);
//    			entity.increaseRotation(0, 1, 0);
//          	}
			
			
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		renderer.deleteAll();
		loader.deleteAll();
		
		DisplayManager.updateDisplay();

	}

}
