package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
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
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class MainGame {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap")); 
		

		
		RawModel model = OBJLoader.loadOBJModel("tree", loader);
		TextureModel staticModel = new TextureModel(model, new ModelTexture(loader.loadTexture("tree")));
		
		
		ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
		fernTextureAtlas.setNumOfRaws(2);
		TextureModel fern = new TextureModel(OBJLoader.loadOBJModel("fern", loader), fernTextureAtlas);
		
		fern.getTexture().setHasTransparency(true);
		
		TextureModel grass = new TextureModel(OBJLoader.loadOBJModel("grassModel", loader), 
				new ModelTexture(loader.loadTexture("grassTexture")));
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		
		TextureModel rock = new TextureModel(OBJLoader.loadOBJModel("rock", loader), 
				new ModelTexture(loader.loadTexture("rock")));
		
		TextureModel ele = new TextureModel(OBJLoader.loadOBJModel("ele", loader), 
				new ModelTexture(loader.loadTexture("ele")));
		
		TextureModel bubbleTree = new TextureModel(OBJLoader.loadOBJModel("lowPolyTree", loader), 
				new ModelTexture(loader.loadTexture("lowPolyTree")));
		
		
		
		
		
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);
//		Entity entity = new Entity(staticModel, new Vector3f(0,0,-25),0,0,0,1);
		Light light = new Light(new Vector3f(3000,2000,3000),new Vector3f(1,1,1));
		Terrain terrain = new Terrain(0,-1, loader,texturePack, blendMap, "heightmap");
		Terrain terrain2 = new Terrain(-1,-1, loader, texturePack, blendMap, "heightmap");

		// just wanna try git if it works.
		
      List<Entity> entities = new ArrayList<Entity>();
      List<Entity> entities1 = new ArrayList<Entity>();
      Random random = new Random();
      for(int i=0;i<500;i++){
    	  float x = random.nextFloat()*800 - 400;
    	  float z = random.nextFloat() * -600;
    	  float y;
    	  if(x < 0 && z < 0){
    		  y = terrain2.getHeightOfTerrain(x,z);
    	  }else{
    		  y = terrain.getHeightOfTerrain(x,z);
    	  }
    	  // how to check whether it is from terrain or terrain2?
          entities.add(new Entity(staticModel, new Vector3f(x,y,z),0,0,0,3));
          x = random.nextFloat()*800 - 400;
    	  z = random.nextFloat() * -600;
    	  if(x < 0 && z < 0){
    		  y = terrain2.getHeightOfTerrain(x,z);
    	  }else{
    		  y = terrain.getHeightOfTerrain(x,z);
    	  }
          entities.add(new Entity(fern, random.nextInt(4), new Vector3f(x,y,z),0,0,0,0.5f)); // atlases
          x = random.nextFloat()*800 - 400;
    	  z = random.nextFloat() * -600;
    	  if(x < 0 && z < 0){
    		  y = terrain2.getHeightOfTerrain(x,z);
    	  }else{
    		  y = terrain.getHeightOfTerrain(x,z);
    	  }
          entities.add(new Entity(grass, new Vector3f(x,y,z),0,0,0,1.5f));
          x = random.nextFloat()*800 - 400;
    	  z = random.nextFloat() * -600;
    	  if(x < 0 && z < 0){
    		  y = terrain2.getHeightOfTerrain(x,z);
    	  }else{
    		  y = terrain.getHeightOfTerrain(x,z);
    	  }
          entities.add(new Entity(bubbleTree, new Vector3f(x,y,z),0,0,0,0.3f));   
      }
      
      for(int i=0;i<10;i++){
    	  float x = random.nextFloat()*800 - 400;
    	  float z = random.nextFloat() * -600;
    	  float y;
    	  if(x < 0 && z < 0){
    		  y = terrain2.getHeightOfTerrain(x,z);
    	  }else{
    		  y = terrain.getHeightOfTerrain(x,z);
    	  }
          entities1.add(new Entity(ele, new Vector3f(x,y,z),0,0,0,2));
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
		
//		RawModel man = OBJLoader.loadOBJModel("man", loader);
//		TextureModel manPlayer = new TextureModel(man, new ModelTexture(loader.loadTexture("manTex")));
//		
//		Player player = new Player(manPlayer, new Vector3f(0, 0, -70), 0,0,0,2);
		
		RawModel bunny = OBJLoader.loadOBJModel("bunny", loader);
		TextureModel bunnyPlayer = new TextureModel(bunny, new ModelTexture(loader.loadTexture("white")));
		
		Player bPlayer = new Player(bunnyPlayer, new Vector3f(0, 0, -70), 0,0,0,0.5f);
		
		Camera camera = new Camera(bPlayer);
		
		while(!Display.isCloseRequested()) {

			camera.move();
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
//			System.out.println("x is " + bPlayer.getPosition().x + "  and z is " + bPlayer.getPosition().z);
//			player.move();
//			renderer.processEntity(player);
			if(bPlayer.getPosition().x < 0 && bPlayer.getPosition().z < 0){
			    bPlayer.move(terrain2);
			}else{
				bPlayer.move(terrain);
			}
			renderer.processEntity(bPlayer);
			for(Entity entity:entities){
        	  	renderer.processEntity(entity);

          	}
			
			for(Entity entity:entities1){
        	  	renderer.processEntity(entity);
//    			entity.increaseRotation(0, 1, 0);
          	}
			
			
			
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		renderer.deleteAll();
		loader.deleteAll();
		
		DisplayManager.updateDisplay();

	}

}
