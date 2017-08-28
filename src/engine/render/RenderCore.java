package engine.render;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;

import org.joml.Vector2f;

import engine.entity.Camera;
import engine.entity.Entity;
import engine.entity.EntityBackground;
import engine.entity.EntitySprite;
import engine.entity.EntityTilemap;
import engine.logic.Scene;
import engine.util.Log;

public class RenderCore {

	private Camera currentCamera;
	private RenderGL renderGL;

	public RenderCore() {
		renderGL = new RenderGL();
	}

	public void deinit() {
		Log.log("deinit");
		renderGL.deinit();
	}

	private void renderBackground(EntityBackground bg) {
		renderGL.clearScreen(bg.getColor());
	}

	private void renderTilemap(EntityTilemap map) {

		renderGL.bindTiles();
		// renderGL.bindQuad();
		map.generateNewMesh(currentCamera.getPosition(), new Vector2f(1, 1));
		renderGL.loadUniformsToTileShader(map.getPosition(), currentCamera.getPosition());
		if (map.isNeedsUpdating()) {
			renderGL.updateTileVBO(map.getVertices(), map.getElements());
			map.setNeedsUpdating(false);
		}

		glDrawElements(GL_TRIANGLES, map.getElements().length, GL_UNSIGNED_INT, 0);
		renderGL.unbind();
	}

	private void renderSprite(EntitySprite sprite) {
		renderGL.bindQuad();
		renderGL.loadUniformsToSpriteShader(sprite.getTexCoords(), sprite.getPosition(),
				currentCamera.getPosition());
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
		renderGL.unbind();
	}

	private void renderGuiElement(EntitySprite sprite) {
		renderGL.bindQuad();
		renderGL.loadUniformsToSpriteShader(sprite.getTexCoords(), sprite.getPosition(), new Vector2f(0, 0));
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
		renderGL.unbind();
	}

	public void render(Scene currentScene) {
		for (Entity e : currentScene.getEntities()) {
			renderGL.loadTintToShaders(e.getTint());
			switch (e.getType()) {
			case Entity.TYPE_BACKGROUND:
				renderBackground((EntityBackground) e);
				break;
			case Entity.TYPE_TILEMAP:
				renderTilemap((EntityTilemap) e);
				break;
			case Entity.TYPE_SPRITE:
				renderSprite((EntitySprite) e);
				break;
			case Entity.TYPE_GUI:
				renderGuiElement((EntitySprite) e);
				break;
			default:
				Log.error("Entity: " + String.valueOf(e) + " does not have a valid Entity Type.");
				break;
			}

		}
	}

	public Camera getCurrentCamera() {
		return currentCamera;
	}

	public void setCurrentCamera(Camera currentCamera) {
		this.currentCamera = currentCamera;
	}
}
