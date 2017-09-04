package engine.render.shader;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.entity.Entity;
import engine.entity.EntitySprite;
import engine.window.WindowManager;

public class SpriteShader {
	private Shader shader;

	private ShaderUniform uniQEntityPosition;
	private ShaderUniform uniQCameraPosition;
	private ShaderUniform uniQScaling;
	private ShaderUniform uniQProj;
	private ShaderUniform uniQModel;
	private ShaderUniform uniTexCoords;
	private ShaderUniform uniTint;
	private ShaderUniform uniUsesTexture;

	public void init() {
		shader = new Shader(Shader.loadFile("/shader/sprite/vertex.glsl"),
				Shader.loadFile("/shader/sprite/fragment.glsl"));
		shader.addAttrib(new ShaderAttrib("position", 2));
		shader.addAttrib(new ShaderAttrib("texcoord", 2));
		shader.pushAttribs();

		uniQEntityPosition = new ShaderUniform("entityPos");
		shader.createUniform(uniQEntityPosition);
		shader.loadUniformVector2f(uniQEntityPosition, new Vector2f(0, 0));

		uniQCameraPosition = new ShaderUniform("cameraPos");
		shader.createUniform(uniQCameraPosition);
		shader.loadUniformVector2f(uniQCameraPosition, new Vector2f(0, 0));

		uniQScaling = new ShaderUniform("scaling");
		shader.createUniform(uniQScaling);
		shader.loadUniformFloat(uniQScaling, 0.1f);

		uniTint = new ShaderUniform("tint");
		shader.createUniform(uniTint);
		shader.loadUniformVector3f(uniTint, new Vector3f(1, 1, 1));

		uniUsesTexture = new ShaderUniform("usesTexture");
		shader.createUniform(uniUsesTexture);
		shader.loadUniformBool(uniUsesTexture, true);

		Vector2f size = new Vector2f(200.0f, 200.0f);

		Matrix4f modelMat = new Matrix4f();
		modelMat.identity();

		modelMat = modelMat.translate(new Vector3f(new Vector2f(0, 0), 0.0f));

		modelMat = modelMat.translate(new Vector3f(0.5f * size.x, 0.5f * size.y, 0.0f));
		modelMat = modelMat.rotate(0.0f, new Vector3f(0.0f, 0.0f, 1.0f));
		modelMat = modelMat.translate(new Vector3f(-0.5f * size.x, -0.5f * size.y, 0.0f));

		modelMat = modelMat.scale(new Vector3f(size, 1.0f));

		uniQModel = new ShaderUniform("model");
		shader.createUniform(uniQModel);
		shader.loadUniformMatrix4f(uniQModel, modelMat);

		Matrix4f p = new Matrix4f();
		p.identity();

		int width = WindowManager.getParams().getWidth();
		int height = WindowManager.getParams().getHeight();

		uniQProj = new ShaderUniform("proj");
		shader.createUniform(uniQProj);
		shader.loadUniformMatrix4f(uniQProj, p.ortho(0.0f, (float) width, 0.0f, (float) height, -1.0f, 1.0f));

		uniTexCoords = new ShaderUniform("i_textureCoords");
		shader.createUniform(uniTexCoords);
		shader.loadUniformVector4f(uniTexCoords, new Vector4f(0, 0, 16, 16));

		shader.loadTexture(Texture.loadTexture("/texture/test2.png"));
	}

	public void use() {
		shader.use();
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

		shader.loadUniformMatrix4f(uniQModel, modelMat);

		shader.loadUniformVector4f(uniTexCoords, new Vector4f(texCoords.x, texCoords.y, 16, 16));

	}

	public Shader getShader() {
		return shader;
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
