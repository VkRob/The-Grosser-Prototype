package grosser.engine.shader;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import grosser.engine.core.DistanceSorter;
import grosser.engine.core.Render;
import grosser.engine.math.Vector2f;
import grosser.engine.math.Vector3f;
import grosser.engine.shader.attrib.ShaderAttribVector2f;
import grosser.engine.shader.uniform.ShaderUniformFloat;
import grosser.engine.shader.uniform.ShaderUniformInt;
import grosser.engine.shader.uniform.ShaderUniformMatrix4f;
import grosser.engine.shader.uniform.ShaderUniformVector2f;
import grosser.engine.shader.uniform.ShaderUniformVector2fv;
import grosser.engine.shader.uniform.ShaderUniformVector3f;
import grosser.engine.shader.uniform.ShaderUniformVector3fv;
import grosser.prototype.entity.Tile;
import grosser.prototype.entity.WorldRenderer;

public class EntityShader {

	private WorldRenderer renderer;

	private ShaderUniformVector2fv uniformTilePositions;
	private ShaderUniformVector3f uniformCameraPosition;
	private ShaderUniformMatrix4f uniformModelMatrix;
	private ShaderUniformMatrix4f uniformProjectionMatrix;
	private ShaderUniformVector3fv uniformLightColor;
	private ShaderUniformVector3fv uniformLightPosition;
	private ShaderUniformInt uniformIsSecondRenderPass;
	private ShaderUniformVector2f uniformOcclusion_resolution;
	private ShaderUniformVector2fv uniformShadows;
	private ShaderUniformFloat uniformRecievesShadow;
	private ShaderUniformInt uniformNumOfLights;
	private ShaderUniformFloat uniformTime;
	private ShaderUniformVector2f uniformAnimationFrame;

	private ShaderProgram shader;
	private ShaderTexture texture;

	private int shaderProgramID;

	public EntityShader(WorldRenderer renderer, float[] pixels) {
		this(renderer, pixels, false);
	}

	public EntityShader(WorldRenderer renderer, float[] pixels, boolean player) {
		this.renderer = renderer;
		shader = new ShaderProgram("outColor");
		shaderProgramID = shader.getShaderProgramID();
		shader.use();

		shader.addShaderAttrib(new ShaderAttribVector2f("position", shaderProgramID));
		shader.addShaderAttrib(new ShaderAttribVector2f("texcoord", shaderProgramID));
		// shader.addShaderAttrib(new ShaderAttribVector2f("objectPosition",
		// shaderProgramID));
		shader.pushAttribPointers();

		uniformCameraPosition = new ShaderUniformVector3f("cameraPosition", shaderProgramID);
		// uniformTilePosition = new ShaderUniformVector2f("objectPosition",
		// shaderProgramID);
		uniformModelMatrix = new ShaderUniformMatrix4f("model", shaderProgramID);
		uniformProjectionMatrix = new ShaderUniformMatrix4f("proj", shaderProgramID);
		uniformLightColor = new ShaderUniformVector3fv("lightColor", shaderProgramID);
		uniformLightPosition = new ShaderUniformVector3fv("lightPosition", shaderProgramID);
		uniformIsSecondRenderPass = new ShaderUniformInt("isSecondRenderPass", shaderProgramID);
		uniformOcclusion_resolution = new ShaderUniformVector2f("occlusion_resolution", shaderProgramID);
		uniformShadows = new ShaderUniformVector2fv("shadows", shaderProgramID);
		uniformTilePositions = new ShaderUniformVector2fv("tilePositions", shaderProgramID);
		uniformRecievesShadow = new ShaderUniformFloat("recievesShadow", shaderProgramID);
		uniformTime = new ShaderUniformFloat("time", shaderProgramID);
		uniformNumOfLights = new ShaderUniformInt("numOfLights", shaderProgramID);
		uniformAnimationFrame = new ShaderUniformVector2f("animationFrame", shaderProgramID);

		shader.addShaderUniform(uniformCameraPosition);
		// shader.addShaderUniform(uniformTilePosition);
		shader.addShaderUniform(uniformModelMatrix);
		shader.addShaderUniform(uniformProjectionMatrix);
		shader.addShaderUniform(uniformLightColor);
		shader.addShaderUniform(uniformLightPosition);
		shader.addShaderUniform(uniformIsSecondRenderPass);
		shader.addShaderUniform(uniformOcclusion_resolution);
		shader.addShaderUniform(uniformShadows);
		shader.addShaderUniform(uniformRecievesShadow);
		shader.addShaderUniform(uniformTime);
		shader.addShaderUniform(uniformTilePositions);
		shader.addShaderUniform(uniformNumOfLights);
		shader.addShaderUniform(uniformAnimationFrame);

		/* NEAREST = Pixelated, LINEAR = Blurred */
		if (!player)
			texture = new ShaderTexture("texture", pixels, 16, 16, GL_NEAREST);
		else
			texture = new ShaderTexture("texture", pixels, 128, 128, GL_NEAREST);
		shader.addShaderTexture(texture);
	}

	private ArrayList<Vector3f> scaleArray(ArrayList<Vector3f> in) {
		ArrayList<Vector3f> out = new ArrayList<Vector3f>();
		for (Vector3f v : in) {
			out.add(v.scale(Tile.SIZE));
		}
		return out;
	}

	public void prepareShader(int vao, ArrayList<Tile> shadows, ArrayList<Vector2f> tileVerts) {
		prepareShader(vao, shadows, tileVerts, new Vector2f(99, 99));
	}

	public void prepareShader(int vao, ArrayList<Tile> shadows, ArrayList<Vector2f> tileVerts, Vector2f animation) {
		// time += 1f / 60f;
		shader.use();

		glBindVertexArray(vao);
		glActiveTexture(texture.getTextureBankID());
		glBindTexture(GL_TEXTURE_2D, texture.getMyID());

		uniformModelMatrix.sendValueToShader(renderer.getModelMatrix());
		uniformProjectionMatrix.sendValueToShader(renderer.getRender().getProjectionMatrix());
		renderer.getRender();
		uniformCameraPosition
				.sendValueToShader(new Vector3f(renderer.getRender().getCamera().getEndPosition(renderer.getRender()),
						Render.getCameraZoom()).scale(Tile.SIZE));

		uniformLightColor.sendValueToShader(renderer.getLightColors());
		uniformLightPosition.sendValueToShader(scaleArray(renderer.getLightPositions()));
		uniformNumOfLights.sendValueToShader(renderer.getLights().size());

		uniformIsSecondRenderPass.sendValueToShader(0);
		uniformRecievesShadow.sendValueToShader(1);
		uniformAnimationFrame.sendValueToShader(animation);
		// uniformTime.sendValueToShader(time);

		ArrayList<Vector2f> verts = new ArrayList<Vector2f>();
		for (Tile t : shadows) {
			Vector2f v = new Vector2f(t.getTilePosition());
			v.y = -v.y;
			verts.add(v);
		}

		Comparator<Vector2f> distanceSorter = new DistanceSorter(
				new Vector2f(renderer.getRender().getCamera().getEndPosition(renderer.getRender()).x,
						-renderer.getRender().getCamera().getEndPosition(renderer.getRender()).y));
		Collections.sort(verts, distanceSorter);

		ArrayList<Vector2f> send = new ArrayList<Vector2f>();
		for (int i = 0; i < verts.size(); i++) {
			Vector2f g = verts.get(i);
			send.add(g.add(new Vector2f(-0.5f, 0.5f)));
			send.add(g.add(new Vector2f(0.5f, 0.5f)));
			send.add(g.add(new Vector2f(0.5f, -0.5f)));
			send.add(g.add(new Vector2f(-0.5f, -0.5f)));
		}

		uniformShadows.sendValueToShader(send);

		uniformTilePositions.sendValueToShader(tileVerts);
	}
}
