#version 150 core


// Inputs
in vec2 Texcoord;
in vec3 FragPos;
in vec2 FragGamePos;

// Uniforms
uniform sampler2D tex1;
uniform sampler2D u_Texture;

const int numOfLights = 2;
const int numOfShadows = 32;

uniform vec3 lightColor[numOfLights];
uniform vec3 lightPosition[numOfLights];
//uniform float lightSize[numOfLights];

const float lightSize = 5;

uniform vec2 shadows[numOfShadows*4];
uniform float recievesShadow;
        
uniform float time;
 
uniform vec3 cameraPosition; // cameraPosition.z denotes the distance of the camera from the world

// Outputs
out vec4 outColor;

float sign (vec2 p1, vec2 p2, vec2 p3){
    return (p1.x - p3.x) * (p2.y - p3.y) - (p2.x - p3.x) * (p1.y - p3.y);
}

float PointInTriangle (vec2 pt, vec2 v1, vec2 v2, vec2 v3)
{
    bool b1, b2, b3;

    b1 = sign(pt, v1, v2) < 0.0f;
    b2 = sign(pt, v2, v3) < 0.0f;
    b3 = sign(pt, v3, v1) < 0.0f;

    if ((b1 == b2) && (b2 == b3)){
    return 1;
    } else {
    	return 0;
    }
}

float renderShadow(int a1, int a2, vec2 lightPos, vec2 fragPos){
			vec2 target = fragPos - lightPos;
			vec2 v1 = shadows[a1	] - lightPos;
			vec2 v2 = shadows[a2] - lightPos;
			
			if(PointInTriangle (fragPos, shadows[a1], shadows[a2], lightPos)==0 &&
			dot(v1, target) + dot(v2, target) > 1){
				float dotv1 = cross(vec3(v1,0), vec3(target,0)).z;
				float dotv2 = cross(vec3(v2,0), vec3(target,0)).z;
			
				if(	(dotv1<0 && dotv2>0)
				||	(dotv1 >0 && dotv2<0)){
					//outColor = vec4(0.0, 0.0, 0.0, 1.0);
					return 1;
				}
			}
			return 0;
}

float falloff(float distance){
	return (1/pow(distance, 0.4)) - (1/pow(10,0.4));
}

void main(){
	
	float calculateLighting[numOfLights];
	vec2 fragPos = FragGamePos;

	if(recievesShadow==1){
	
		for(int k = 0; k < numOfLights; k++){
		// Find out if this pixel is in a shadow
		vec2 lightPos = lightPosition[k].xy;
		lightPos.y = -lightPos.y;
		
		//
		calculateLighting[k]=1;
		for(int i = 0; i < (numOfShadows*4)-1; i+=4){
			
			int bl = i;
			int br = i+1;
			int tr = i + 2;
			int tl = i + 3;
			
			float x = distance(lightPos, fragPos);
			if(	renderShadow(tl, tr, lightPos, fragPos)==1){
				calculateLighting[k] -= falloff(x);
			}
			if(	renderShadow(tr, br, lightPos, fragPos)==1){
				calculateLighting[k] -= falloff(x);
			}
			if(	renderShadow(br, bl, lightPos, fragPos)==1){
				calculateLighting[k] -= falloff(x);
			}
			if(	renderShadow(bl, tl, lightPos, fragPos)==1){
				calculateLighting[k] -= falloff(x);
			}
			if(calculateLighting[k] < 0.9){
				calculateLighting[k] = 0.9;
			}
			if(calculateLighting[k] > 1){
				calculateLighting[k] = 1;
			}
		}
		}
	}
	
	// Calculate ambient lighting
	float ambientStrength = 0.03f;

	// The Ambient color is always the same color as the first light
	// in the scene
	vec3 ambient = ambientStrength * lightColor[0];

	// Calculate diffuse lighting
	vec3 result = vec3(0,0,0);
	
	fragPos.y = -fragPos.y;
	for(int i = 0; i < numOfLights; i++){
		vec3 norm = normalize(vec3(0.0,0.0,1.0));
		vec3 lPos = vec3(lightPosition[i].xy, lightPosition[i].z - cameraPosition.z);
		vec3 lightDir = normalize(lPos - FragPos);
		float diff = max(dot(norm, lightDir), 0.0);
		
		float z = 0.3;
		float haloMixer = 1/pow(distance(fragPos, lightPosition[i].xy), z);
		float adjuster = 1/pow(1.4, z);
		float haloFinal = haloMixer-adjuster;
		if(haloFinal < 0){
			haloFinal = 0;
		}
	
		vec3 diffuse = mix(diff * lightColor[i], vec3(1.0, 1.0, 1.0), haloFinal);
		result = result + ((calculateLighting[i]*diffuse) + ambient);
	}


	result = result * texture(tex1, Texcoord).xyz;
	outColor = vec4(result,1.0f);

}