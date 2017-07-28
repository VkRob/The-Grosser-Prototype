#version 150 core

// Inputs
in vec2 position;
in vec2 texcoord;

// Outputs
out vec2 Texcoord;
out vec3 FragPos;
out vec2 FragGamePos;

// Uniforms
uniform mat4 model;
uniform mat4 proj;
uniform vec3 cameraPosition;// cameraPosition.z denotes the
												// distance of the camera from
												// the world
uniform vec2 objectPosition;

// Program
void main(){
	Texcoord = texcoord;
	FragPos = vec3(model * vec4(objectPosition + position, cameraPosition.z, 1.0));
	FragGamePos = objectPosition + position;
	gl_Position = proj * model * vec4(objectPosition + position - cameraPosition.xy, cameraPosition.z, 1.0);
}