package engine.render;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2f;

import engine.Engine;
import engine.entity.Camera;
import engine.entity.Entity;
import engine.entity.EntityBackground;
import engine.entity.EntityMachine;
import engine.entity.EntitySprite;
import engine.entity.EntityTilemap;
import engine.logic.Scene;

public class RenderCore {

	private static final Logger LOG = LogManager.getLogger(RenderCore.class);

	private RenderGL renderGL;

	public RenderCore() {
		renderGL = new RenderGL();
	}

	public void deinit() {
		LOG.debug("deinit render core");

		renderGL.deinit();
	}

	private void renderBackground(EntityBackground bg) {
		renderGL.clearScreen(bg.getColor());
	}

	private void renderTilemap(EntityTilemap map) {
		renderGL.bindTiles();
		// renderGL.bindQuad();
		map.generateNewMesh(getCurrentCamera().getPosition(), new Vector2f(1, 1));
		renderGL.loadUniformsToTileShader(map.getPosition(), getCurrentCamera().getPosition());
		if (map.isNeedsUpdating()) {
			renderGL.updateTileVBO(map.getVertices(), map.getElements());
			map.setNeedsUpdating(false);
		}

		glDrawElements(GL_TRIANGLES, map.getElements().length, GL_UNSIGNED_INT, 0);
		renderGL.unbind();
	}

	private void renderSprite(EntitySprite sprite) {
		renderGL.updateQuadVBO(sprite.getDimensions());
		renderGL.bindQuad();
		renderGL.loadSpriteMetaDataUniforms(sprite);
		renderGL.loadUniformsToSpriteShader(sprite.getTexCoords(), sprite.getPosition(),
				getCurrentCamera().getPosition());
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
		renderGL.unbind();
	}

	private void renderMachine(EntityMachine machine) {
		renderGL.updateQuadVBO(machine.getDimensions());
		renderGL.bindQuad();
		// renderGL.loadSpriteMetaDataUniforms(machine);
		// TODO ^^^
		renderGL.loadMachineMetaDataUniforms();
		renderGL.loadUniformsToMachineShader(machine.getTexCoords(), machine.getPosition(),
				getCurrentCamera().getPosition());
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
		renderGL.unbind();
	}

	private void renderGuiElement(EntitySprite sprite) {
		renderGL.updateQuadVBO(sprite.getDimensions());
		renderGL.bindQuad();
		renderGL.loadSpriteMetaDataUniforms(sprite);
		renderGL.loadUniformsToSpriteShader(sprite.getTexCoords(), sprite.getPosition(), new Vector2f(0, 0));
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
		renderGL.unbind();
	}

	public void render(Scene currentScene) {
		for (Entity e : currentScene.getEntities()) {

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
			case Entity.TYPE_MACHINE:
				renderMachine((EntityMachine) e);
				break;
			default:
				LOG.error("Entity: " + String.valueOf(e) + " does not have a valid Entity Type.");

				break;
			}

		}
	}

	private Camera getCurrentCamera() {
		return Engine.sceneManager.getCurrentScene().getCamera();
	}

}
