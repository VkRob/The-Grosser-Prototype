package engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL41.*;
import static org.lwjgl.opengl.GL42.*;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.opengl.GL44.*;
import static org.lwjgl.opengl.GL45.*;

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

public class Render {

	int uniModel;
	int uniProj;
	int uniView;
	int uniCameraPos;

	Matrix4f model_matrix; // Model Transformation
	Matrix4f projection_matrix;
	Matrix4f view_matrix;

	Vector3f camera_pos = new Vector3f(1.0f, 1.0f, 3.0f);
	float camera_speed = 0.1f;

	private static final int sizeOf_GL_FLOAT = 4;

	private static final int TEXTURE_MODE = GL_NEAREST;
	// NEAREST = Pixelated/8-bit
	// LINEAR = Blurred

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

		// Activate the vbo
		glBindBuffer(GL_ARRAY_BUFFER, vbo);

		// Copy the vertex data to the vbo
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

		int ebo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, elements, GL_STATIC_DRAW);

		int shaderProgram = new ShaderProgram().getShaderProgramID();

		// Assign outColor shader variable to location 0
		glBindFragDataLocation(shaderProgram, 0, "outColor");

		// Link & begin the shader program
		glLinkProgram(shaderProgram);
		glUseProgram(shaderProgram);

		// Find the "position" variable in the shader program & enable it
		int posAttrib = glGetAttribLocation(shaderProgram, "position");
		glEnableVertexAttribArray(posAttrib);

		// Format the attrib, stride = 5 because we have 2f vector + 3f color
		// For whatever reason, stride = 0 when there is only one kind of data
		// (ie only vector2f positions)
		/*
		 * int index, int size, int type, boolean normalized, int stride, long
		 * buffer_buffer_offset
		 */
		// glVertexAttribPointer(posAttrib, 2, GL_FLOAT, false, 7 *
		// sizeOf_GL_FLOAT, 0);

		int colAttrib = glGetAttribLocation(shaderProgram, "color");
		glEnableVertexAttribArray(colAttrib);

		int texAttrib = glGetAttribLocation(shaderProgram, "texcoord");
		glEnableVertexAttribArray(texAttrib);

		glVertexAttribPointer(posAttrib, 2, GL_FLOAT, false, 4 * sizeOf_GL_FLOAT, 0);
		glVertexAttribPointer(texAttrib, 2, GL_FLOAT, false, 4 * sizeOf_GL_FLOAT, 2 * sizeOf_GL_FLOAT);

		// // Set the value of the uniform in the fragment shader
		// uniColor = glGetUniformLocation(shaderProgram, "triangleColor");
		// glUniform3f(uniColor, 0.0f, 0.0f, 0.0f);

		// Set up camera uniform
		uniCameraPos = glGetUniformLocation(shaderProgram, "cameraPosition");
		glUniform2f(uniCameraPos, 0.0f, 0.0f);

		// // Create and apply the view matrix
		// view_matrix = Matrix4f.lookAt(new Vector3f(1.0f, 1.0f, 3.0f), new
		// Vector3f(0.0f, 0.0f, 0.0f),
		// new Vector3f(0.0f, 0.0f, 1.0f));
		// uniView = glGetUniformLocation(shaderProgram, "view");
		// glUniformMatrix4fv(uniView, false, view_matrix.toBuffer());

		// Create and apply the projection matrix
		projection_matrix = Matrix4f.perspective(45.0f, Main.WIDTH / Main.HEIGHT, 1.0f, 10.0f);
		uniProj = glGetUniformLocation(shaderProgram, "proj");
		glUniformMatrix4fv(uniProj, false, projection_matrix.toBuffer());

		// Setup the model matrix uniform
		uniModel = glGetUniformLocation(shaderProgram, "model");

		float pixels1[] = {

				0.0f, 0.0f, 1.0f,

				1.0f, 1.0f, 1.0f,

				1.0f, 1.0f, 1.0f,

				0.0f, 0.0f, 1.0f };

		float pixels2[] = {

				1.0f, 0.0f, 0.0f,

				1.0f, 1.0f, 1.0f,

				1.0f, 1.0f, 1.0f,

				1.0f, 0.0f, 0.0f };

		int tex[] = { glGenTextures(), glGenTextures() };

		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, tex[0]);

		// glGenerateMipmap(GL_TEXTURE_2D);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, 2, 2, 0, GL_RGB, GL_FLOAT, pixels1);

		// Specify what happens when a texture coord >1 or <0 happens
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT); // S = X,
																		// repeat,
																		// X = X
																		// % 1;
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT); // T = Y,
																		// same
																		// as
																		// above

		// Performance heavy way of scaling textures --- use mipmaps instead
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, TEXTURE_MODE);// when
																			// downscaling
																			// a
																			// texture
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, TEXTURE_MODE);// when
																			// upscaling
																			// a
																			// texture
		glUniform1i(glGetUniformLocation(shaderProgram, "tex1"), 0);

		glActiveTexture(GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_2D, tex[1]);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, 2, 2, 0, GL_RGB, GL_FLOAT, pixels2);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, TEXTURE_MODE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, TEXTURE_MODE);
		glUniform1i(glGetUniformLocation(shaderProgram, "tex2"), 1);

		// Create and apply the model matrix
		model_matrix = Matrix4f.rotate(180f, new Vector3f(1.0f, 0.0f, 0.0f));
		glUniformMatrix4fv(uniModel, false, model_matrix.toBuffer());

		// view_matrix = Matrix4f.lookAt(camera_pos, new Vector3f(camera_pos.x -
		// 1f, camera_pos.y - 1f, 0f), new Vector3f(0.0f, 0.0f, 1.0f));
		// glUniformMatrix4fv(uniView, false, view_matrix.toBuffer());

	}

	public void render() {

		// Update camera pos
		glUniform2f(uniCameraPos, camera_pos.x, camera_pos.y);

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
