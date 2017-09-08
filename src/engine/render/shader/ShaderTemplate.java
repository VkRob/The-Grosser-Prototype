package engine.render.shader;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.entity.EntitySprite;
import engine.window.WindowManager;

public class ShaderTemplate {

	private int textureWidth, textureHeight;

	private Shader shader;
	public ShaderUniform uniEntityPosition;
	public ShaderUniform uniCameraPosition;
	public ShaderUniform uniScaling;
	public ShaderUniform uniProj;
	public ShaderUniform uniModel;
	public ShaderUniform uniTexCoords;
	public ShaderUniform uniTint;
	public ShaderUniform uniUsesTexture;

	private void loadEntityPosition() {
		uniEntityPosition = ShaderHelper.createShaderUniform(shader, "entityPos");
		shader.loadUniformVector2f(uniEntityPosition, new Vector2f(0, 0));
	}

	private void loadCameraPosition() {
		uniCameraPosition = ShaderHelper.createShaderUniform(shader, "cameraPos");
		shader.loadUniformVector2f(uniCameraPosition, new Vector2f(0, 0));
	}

	private void loadScaling() {
		uniScaling = ShaderHelper.createShaderUniform(shader, "scaling");
		shader.loadUniformFloat(uniScaling, 0.1f);
	}

	private void loadTint() {
		uniTint = ShaderHelper.createShaderUniform(shader, "tint");
		shader.loadUniformVector3f(uniTint, new Vector3f(1, 1, 1));
	}

	private void loadUsesTexture() {
		uniUsesTexture = ShaderHelper.createShaderUniform(shader, "usesTexture");
		shader.loadUniformBool(uniUsesTexture, true);
	}

	private void loadModelMatrix() {
		Vector2f size = new Vector2f(200.0f, 200.0f);

		Matrix4f modelMat = new Matrix4f();
		modelMat.identity();

		modelMat = modelMat.translate(new Vector3f(new Vector2f(0, 0), 0.0f));

		modelMat = modelMat.translate(new Vector3f(0.5f * size.x, 0.5f * size.y, 0.0f));
		modelMat = modelMat.rotate(0.0f, new Vector3f(0.0f, 0.0f, 1.0f));
		modelMat = modelMat.translate(new Vector3f(-0.5f * size.x, -0.5f * size.y, 0.0f));

		modelMat = modelMat.scale(new Vector3f(size, 1.0f));

		uniModel = new ShaderUniform("model");
		shader.createUniform(uniModel);
		shader.loadUniformMatrix4f(uniModel, modelMat);
	}

	private void loadProjectionMatrix() {
		Matrix4f p = new Matrix4f();
		p.identity();

		int width = WindowManager.getParams().getWidth();
		int height = WindowManager.getParams().getHeight();

		uniProj = new ShaderUniform("proj");
		shader.createUniform(uniProj);
		shader.loadUniformMatrix4f(uniProj, p.ortho(0.0f, (float) width, 0.0f, (float) height, -1.0f, 1.0f));
	}

	private void loadTextureCoords(int width, int height) {
		uniTexCoords = new ShaderUniform("i_textureCoords");
		shader.createUniform(uniTexCoords);
		shader.loadUniformVector4f(uniTexCoords, new Vector4f(0, 0, width, height));
	}

	private void loadTexture(String path) {
		shader.loadTexture(Texture.loadTexture(path));
	}

	private void create() {
		loadEntityPosition();
		loadCameraPosition();
		loadScaling();
		loadTint();
		loadUsesTexture();
		loadModelMatrix();
		loadProjectionMatrix();
		loadTextureCoords(16, 16);
	}

	public ShaderTemplate(Shader shader, String texturePath, int width, int height) {
		this.textureWidth = width;
		this.textureHeight = height;
		this.shader = shader;

		create();
		loadTexture(texturePath);
	}

	public void loadUniformsToShader(Vector2f texCoords, Vector2f entityPosition, Vector2f cameraPosition) {
		Vector2f size = new Vector2f(200.0f, 200.0f);

		shader.use();
		Matrix4f modelMat = new Matrix4f();
		modelMat.identity();

		Vector2f tr = new Vector2f(entityPosition).sub(cameraPosition);
		modelMat = modelMat.translate(new Vector3f(tr.x, tr.y, 0.0f));

		modelMat = modelMat.translate(new Vector3f(0.5f * size.x, 0.5f * size.y, 0.0f));
		modelMat = modelMat.rotate(0.0f, new Vector3f(0.0f, 0.0f, 1.0f));
		modelMat = modelMat.translate(new Vector3f(-0.5f * size.x, -0.5f * size.y, 0.0f));

		modelMat = modelMat.scale(new Vector3f(size, 1.0f));

		shader.loadUniformMatrix4f(uniModel, modelMat);

		shader.loadUniformVector4f(uniTexCoords, new Vector4f(texCoords.x, texCoords.y, textureWidth, textureHeight));
	}

	public void loadEntityMetaDataUniforms(EntitySprite e) {
		shader.use();
		shader.loadUniformVector3f(uniTint, e.getTint());
		shader.loadUniformBool(uniUsesTexture, e.isUsesTexture());
	}

	public void loadMachineMetaDataUniforms() {
		shader.use();
		shader.loadUniformVector3f(uniTint, new Vector3f(1, 1, 1));
		shader.loadUniformBool(uniUsesTexture, true);
	}

}
