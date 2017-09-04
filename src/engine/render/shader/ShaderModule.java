package engine.render.shader;

import org.joml.Vector2f;

import engine.entity.EntitySprite;

public class ShaderModule {

	private Shader shader;
	private ShaderTemplate uniTemplate;

	public ShaderModule(String vertex, String fragment, String texturePath) {
		shader = new Shader(Shader.loadFile(vertex), Shader.loadFile(fragment));
		ShaderHelper.addBasicShaderAttribs(shader);

		uniTemplate = new ShaderTemplate(shader, texturePath);
	}

	public void loadUniformsToShader(Vector2f texCoords, Vector2f entityPosition, Vector2f cameraPosition) {
		uniTemplate.loadUniformsToShader(texCoords, entityPosition, cameraPosition);
	}

	public void loadEntityMetaDataUniforms(EntitySprite e) {
		uniTemplate.loadEntityMetaDataUniforms(e);
	}

	public void loadMachineMetaDataUniforms() {
		uniTemplate.loadMachineMetaDataUniforms();
	}

	public void use() {
		shader.use();
	}

	public Shader getShader() {
		return shader;
	}

	public void setShader(Shader shader) {
		this.shader = shader;
	}

}
