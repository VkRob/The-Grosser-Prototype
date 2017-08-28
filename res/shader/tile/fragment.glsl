#version 150 core

in vec2 Texcoord;

out vec4 outColor;

uniform sampler2D tex;
uniform vec3 tint;
uniform int usesTexture;

void main() {
	outColor = texture(tex, Texcoord);
}
