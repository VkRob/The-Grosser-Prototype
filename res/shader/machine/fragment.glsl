#version 150 core

in vec2 Texcoord;

out vec4 outColor;

uniform sampler2D tex;
uniform vec3 tint;
uniform int usesTexture;

void main() {
	if(usesTexture==0){
		outColor = vec4(tint,1);
		return;
	}
	if(tint != vec3(0,0,0)){
		outColor = vec4(tint, 1) * texture(tex, Texcoord); // - vec2(0.0000, 0.0000));
	}else {
		outColor = texture(tex, Texcoord);
	}
}
