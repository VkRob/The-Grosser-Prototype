package engine.render.shader;

public class ShaderHelper {
	public static ShaderUniform createShaderUniform(Shader shader, String name) {
		ShaderUniform shaderUniform = new ShaderUniform(name);
		shader.createUniform(shaderUniform);
		return shaderUniform;
	}

	public static void addBasicShaderAttribs(Shader shader) {
		shader.addAttrib(new ShaderAttrib("position", 2));
		shader.addAttrib(new ShaderAttrib("texcoord", 2));
		shader.pushAttribs();
	}
}
