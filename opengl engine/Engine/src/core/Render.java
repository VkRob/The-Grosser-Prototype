package core;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import main.Main;

//import static org.lwjgl.opengl.GL11.*;
//import static org.lwjgl.opengl.GL12.*;
//import static org.lwjgl.opengl.GL13.*;
//import static org.lwjgl.opengl.GL14.*;
//import static org.lwjgl.opengl.GL15.*;
//import static org.lwjgl.opengl.GL20.*;
//import static org.lwjgl.opengl.GL21.*;
//import static org.lwjgl.opengl.GL30.*;
//import static org.lwjgl.opengl.GL31.*;
//import static org.lwjgl.opengl.GL32.*;
//import static org.lwjgl.opengl.GL33.*;
//import static org.lwjgl.opengl.GL40.*;
//import static org.lwjgl.opengl.GL41.*;
//import static org.lwjgl.opengl.GL42.*;
//import static org.lwjgl.opengl.GL43.*;
//import static org.lwjgl.opengl.GL44.*;
//import static org.lwjgl.opengl.GL45.*;

import math.Matrix4f;
import math.Vector3f;
import shader.ShaderProgram;
import shader.ShaderTexture;
import shader.attrib.ShaderAttribVector2f;
import shader.uniform.ShaderUniformMatrix4f;
import shader.uniform.ShaderUniformVector3f;

public class Render {

	int uniModel;
	int uniProj;
	int uniView;

	Matrix4f modelMatrix; // Model Transformation
	Matrix4f projectionMatrix;
	Matrix4f view_matrix;

	Vector3f camera_pos = new Vector3f(1.0f, 1.0f, 5.0f);
	float camera_speed = 0.1f;

	public static final int sizeOf_GL_FLOAT = 4;

	private ShaderUniformVector3f uniformCameraPosition;
	private ShaderUniformMatrix4f uniformModelMatrix;
	private ShaderUniformMatrix4f uniformProjectionMatrix;

	public Render() {

		// Create a VAO
		int vao;
		vao = glGenVertexArrays();
		glBindVertexArray(vao);

		// Create a Vertex Buffer to carry the vertexes
		int vbo = glGenBuffers();

		// Create a list of vertices w/ color data
		// pos (vec2f) + color (vec3f) + texture coordinates (vec2f)
		float vertices[] = {
				// Positions (2f), Texture Coordinates (2f)
				-0.5f, 0.5f, 0.0f, 0.0f, // Top-left
				0.5f, 0.5f, 1.0f, 0.0f, // Top-right
				0.5f, -0.5f, 1.0f, 1.0f, // Bottom-right
				-0.5f, -0.5f, 0.0f, 1.0f // Bottom-left
		};

		int elements[] = {
				// Triangles to be rendered
				0, 1, 2, // Triangle 1
				2, 3, 0 // Triangle 2
		};

		float pixels1[] = {

				0.0f, 0.0f, 1.0f, /* */ 1.0f, 1.0f, 1.0f,

				1.0f, 1.0f, 1.0f, /* */ 0.0f, 0.0f, 1.0f };

//		float pixels2[] = {
//
//				1.0f, 0.0f, 0.0f, /* */ 1.0f, 1.0f, 1.0f,
//
//				1.0f, 1.0f, 1.0f, /* */ 1.0f, 0.0f, 0.0f };
		
		float pixels2[] = ImageLoader.getTexturePixels("/test.png");

		// Activate the vbo
		glBindBuffer(GL_ARRAY_BUFFER, vbo);

		// Copy the vertex data to the vbo
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

		int ebo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, elements, GL_STATIC_DRAW);

		modelMatrix = Matrix4f.rotate(180f, new Vector3f(1.0f, 0.0f, 0.0f));
		projectionMatrix = Matrix4f.perspective(45.0f, Main.WIDTH / Main.HEIGHT, 1.0f, 10.0f);

		ShaderProgram shader = new ShaderProgram("outColor");
		int shaderProgram = shader.getShaderProgramID();

		shader.addShaderAttrib(new ShaderAttribVector2f("position", shaderProgram));
		shader.addShaderAttrib(new ShaderAttribVector2f("texcoord", shaderProgram));
		shader.pushAttribPointers();

		uniformCameraPosition = new ShaderUniformVector3f("cameraPosition", shaderProgram);
		uniformModelMatrix = new ShaderUniformMatrix4f("model", shaderProgram);
		uniformProjectionMatrix = new ShaderUniformMatrix4f("proj", shaderProgram);

		shader.addShaderUniform(uniformCameraPosition);
		shader.addShaderUniform(uniformModelMatrix);
		shader.addShaderUniform(uniformProjectionMatrix);

		uniformModelMatrix.sendValueToShader(modelMatrix);
		uniformProjectionMatrix.sendValueToShader(projectionMatrix);

		/* NEAREST = Pixelated, LINEAR = Blurred */
		ShaderTexture tex1 = new ShaderTexture("tex1", pixels1, 64, 64, GL_NEAREST);
		ShaderTexture tex2 = new ShaderTexture("tex2", pixels2, 64, 64, GL_NEAREST);

		shader.addShaderTexture(tex1);
		shader.addShaderTexture(tex2);

	}

	public void render() {

		// Update camera pos
		uniformCameraPosition.sendValueToShader(camera_pos);

		// Draw
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

	}

	public void moveW() {
		camera_pos.y -= camera_speed;
	}

	public void moveS() {
		camera_pos.y += camera_speed;
	}

	public void moveA() {
		camera_pos.x -= camera_speed;
	}

	public void moveD() {
		camera_pos.x += camera_speed;
	}
}
