package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TextureModel;
import shaders.staticShader;

public class MasterRenderer {
	
	private staticShader shader = new staticShader();
	private Renderer renderer = new Renderer(shader);
	
	private Map<TextureModel, List<Entity>> entities = new HashMap<TextureModel, List<Entity>>();
	
	public void render(Light sun, Camera camera) {
		renderer.prepare();
		shader.start();
		shader.loadLight(sun);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		
		shader.stop();
		entities.clear();
		
	}
	
	public void processEntity(Entity entity) {
		TextureModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if(batch != null) {
			batch.add(entity);
		}else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}
	
	
	public void deleteAll() {
		shader.deleteAll();
	}

}
