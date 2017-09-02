#version 150 core

in vec2 texcoord;
in vec2 position;

uniform mat4 model;
uniform mat4 proj;
uniform vec4 i_textureCoords;

out vec2 Texcoord;

void main() {
	Texcoord = texcoord;
	if (i_textureCoords.z != 0 && i_textureCoords.w != 0) {
		//Top left
		if (texcoord.x == 0 && texcoord.y == 1) {
			Texcoord.x = i_textureCoords.x / i_textureCoords.z;
			Texcoord.y = i_textureCoords.y / i_textureCoords.w;
		}
		//Bottom left
		if (texcoord.x == 0 && texcoord.y == 0) {
			Texcoord.x = i_textureCoords.x / i_textureCoords.z;
			Texcoord.y = (i_textureCoords.y + 1) / i_textureCoords.w;
		}
		//Top right
		if (texcoord.x == 1 && texcoord.y == 1) {
			Texcoord.x = (i_textureCoords.x + 1) / i_textureCoords.z;
			Texcoord.y = i_textureCoords.y / i_textureCoords.w;
		}
		//Bottom right
		if (texcoord.x == 1 && texcoord.y == 0) {
			Texcoord.x = (i_textureCoords.x + 1) / i_textureCoords.z;
			Texcoord.y = (i_textureCoords.y + 1) / i_textureCoords.w;
		}
	}
	gl_Position = proj * model * vec4(position / 5, 0.0, 1.0);
}
