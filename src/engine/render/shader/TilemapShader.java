package engine.render.shader;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.window.WindowManager;

public class TilemapShader {

	private Shader tileShader;
	private ShaderUniform uniEntityPosition;
	private ShaderUniform uniCameraPosition;
	private ShaderUniform uniScaling;
	private ShaderUniform uniProj;
	private ShaderUniform uniModel;
	private ShaderUniform uniTint;

	public void use() {
		tileShader.use();
	}

	public void deinit() {
		tileShader.deinit();
	}

	public Shader getShader() {
		return tileShader;
	}

	public void loadUniformsToShader(Vector2f entityPosition, Vector2f cameraPosition) {

		Vector2f size = new Vector2f(200.0f, 200.0f);

		tileShader.use();
		Matrix4f modelMat = new Matrix4f();
		modelMat.identity();

		Vector2f tr = new Vector2f(entityPosition).sub(cameraPosition);
		modelMat = modelMat.translate(new Vector3f(tr.x, tr.y, 0.0f));

		modelMat = modelMat.translate(new Vector3f(0.5f * size.x, 0.5f * size.y, 0.0f));
		modelMat = modelMat.rotate(0.0f, new Vector3f(0.0f, 0.0f, 1.0f));
		modelMat = modelMat.translate(new Vector3f(-0.5f * size.x, -0.5f * size.y, 0.0f));

		modelMat = modelMat.scale(new Vector3f(size, 1.0f));

		tileShader.loadUniformMatrix4f(uniModel, modelMat);
	}

	public void init() {
		tileShader = new Shader(Shader.loadFile("/shader/vertex.glsl"), Shader.loadFile("/shader/fragment.glsl"));
		tileShader.addAttrib(new ShaderAttrib("position", 2));
		tileShader.addAttrib(new ShaderAttrib("texcoord", 2));
		tileShader.pushAttribs();

		uniEntityPosition = new ShaderUniform("entityPos");
		tileShader.createUniform(uniEntityPosition);
		tileShader.loadUniformVector2f(uniEntityPosition, new Vector2f(0, 0));

		uniCameraPosition = new ShaderUniform("cameraPos");
		tileShader.createUniform(uniCameraPosition);
		tileShader.loadUniformVector2f(uniCameraPosition, new Vector2f(0, 0));

		uniScaling = new ShaderUniform("scaling");
		tileShader.createUniform(uniScaling);
		tileShader.loadUniformFloat(uniScaling, 0.1f);

		uniTint = new ShaderUniform("tint");
		tileShader.createUniform(uniTint);
		tileShader.loadUniformVector3f(uniTint, new Vector3f(1, 1, 1));

		Vector2f size = new Vector2f(200.0f, 200.0f);

		Matrix4f modelMat = new Matrix4f();
		modelMat.identity();

		modelMat = modelMat.translate(new Vector3f(new Vector2f(0, 0), 0.0f));

		modelMat = modelMat.translate(new Vector3f(0.5f * size.x, 0.5f * size.y, 0.0f));
		modelMat = modelMat.rotate(0.0f, new Vector3f(0.0f, 0.0f, 1.0f));
		modelMat = modelMat.translate(new Vector3f(-0.5f * size.x, -0.5f * size.y, 0.0f));

		modelMat = modelMat.scale(new Vector3f(size, 1.0f));

		uniModel = new ShaderUniform("model");
		tileShader.createUniform(uniModel);
		tileShader.loadUniformMatrix4f(uniModel, modelMat);

		Matrix4f p = new Matrix4f();
		p.identity();

		int width = WindowManager.getParams().getWidth();
		int height = WindowManager.getParams().getHeight();

		uniProj = new ShaderUniform("proj");
		tileShader.createUniform(uniProj);
		tileShader.loadUniformMatrix4f(uniProj, p.ortho(0.0f, (float) width, 0.0f, (float) height, -1.0f, 1.0f));

		tileShader.loadTexture(Texture.loadTexture("/texture/test.png"));
	}

	public void loadUniformsTint(Vector3f tint) {
		tileShader.use();
		tileShader.loadUniformVector3f(uniTint, tint);
	}
}
