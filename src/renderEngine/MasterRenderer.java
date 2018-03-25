package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TextureModel;
import shaders.TerrainShader;
import shaders.staticShader;
import terrains.Terrain;

public class MasterRenderer {
	
	private Matrix4f projectionMatrix;
	
	private staticShader shader = new staticShader();
	private EntityRenderer renderer;
	
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	
	private TerrainRenderer terrainRenderer;
	private TerrainShader terrainShader = new TerrainShader();
	
	private Map<TextureModel, List<Entity>> entities = new HashMap<TextureModel, List<Entity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	
	public MasterRenderer() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		createProjectionMatrix();
		renderer = new EntityRenderer(shader,projectionMatrix);
		terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
	}
	
	public void render(Light sun, Camera camera) {
		prepare();
		shader.start();
		shader.loadLight(sun);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		
		
		terrainShader.start();
		terrainShader.loadLight(sun);
		terrainShader.loadViewMatrix(camera);
		terrainRenderer.render(terrains);
		
		shader.stop();
		terrainShader.stop();
		
		terrains.clear();
		entities.clear();
		
	}
	
	public void processTerrain(Terrain terrain) {
		terrains.add(terrain);
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
	
	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);;
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0.49f, 89f, 0.98f, 1);
	}
	
	private void createProjectionMatrix() {
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV/2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;
        
        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
	}
	
	
	public void deleteAll() {
		terrainShader.deleteAll();
		shader.deleteAll();
	}

}