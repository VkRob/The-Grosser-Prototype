#version 150 core


// Inputs
in vec2 Texcoord;
in vec3 FragPos;
in vec2 FragGamePos;

// Uniforms
uniform sampler2D tex1;
uniform sampler2D u_Texture;

uniform int numOfLights;
const int numOfLightsInternal = 16;
const int numOfShadows = 16;

uniform vec3 lightColor[numOfLightsInternal];
uniform vec3 lightPosition[numOfLightsInternal];
//uniform float lightSize[numOfLightsInternal];

const float lightSize = 5;

uniform vec2 shadows[numOfShadows*4];
uniform float recievesShadow;
uniform vec2 animationFrame;
        
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

float falloff(float d){
	return (1/pow(d, 0.4)) - (0.001/pow(20,0.4));
	//return 1;
}

float lightFalloff(float d, float radius){
	float att = clamp(1.0 - d*d/(radius*radius), 0.0, 1.0); 
	att *= att;
	return att;
}

void main(){

vec2 finTexCoord = Texcoord;
	
	if(animationFrame.x != 99){
		finTexCoord = finTexCoord / 4;
		finTexCoord = finTexCoord + (animationFrame/4);
	}
	//see through stuff
	vec3 sampleTexture = texture(tex1, finTexCoord).xyz;
	if(sampleTexture.x > 0.9 && sampleTexture.y < 0.1 && sampleTexture.z > 0.9){

		discard;
	}
	
	float calculateLighting[numOfLightsInternal];
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
				//calculateLighting[k] = 0;
			}
			if(	renderShadow(tr, br, lightPos, fragPos)==1){
				calculateLighting[k] -= falloff(x);
				//calculateLighting[k] = 0;
			}
			if(	renderShadow(br, bl, lightPos, fragPos)==1){
				calculateLighting[k] -= falloff(x);
				//calculateLighting[k] = 0;
			}
			if(	renderShadow(bl, tl, lightPos, fragPos)==1){
				calculateLighting[k] -= falloff(x);
				//calculateLighting[k] = 0;
			}
			if(calculateLighting[k] < 0.03){
				calculateLighting[k] = 0.03;
			}
			if(calculateLighting[k] > 1){
				calculateLighting[k] = 1;
			}
		}
		}
	}else{
		for(int k = 0; k < numOfLights; k++){
			calculateLighting[k] = 0.04;
		}
	}
	
	// Calculate ambient lighting
	float ambientStrength = 0.07f;
	
	// The Ambient color is always the same color as the first light
	// in the scene
	vec3 ambient = ambientStrength * lightColor[0];

	// Calculate diffuse lighting
	vec3 result = vec3(0,0,0);
	
	fragPos.y = -fragPos.y;
	for(int i = 0; i < numOfLights; i++){
		//vec3 norm = normalize(vec3(0.0,0.0,1.0));
		//vec3 lPos = vec3(lightPosition[i].xy, lightPosition[i].z - 20);
		//vec3 lightDir = normalize(lPos - FragPos);
		//float diff = max(dot(norm, lightDir), 0.0);
		float diff = lightFalloff(distance(lightPosition[i].xy, FragPos.xy), lightPosition[i].z);
		if(diff >= 0.005f && diff <= 0.01 && lightColor[i]!=vec3(0,1,0) && lightColor[i]!=vec3(1,1,1)){
			outColor = vec4(lightColor[i],1.0);
			return;
		}
		float z = 0.3;
		float haloMixer = 1/pow(distance(fragPos, lightPosition[i].xy)*lightPosition[i].z, z);
		float adjuster = 1/pow(1.4, z);
		float haloFinal = haloMixer-adjuster;
		if(haloFinal < 0){
			haloFinal = 0;
		}
		
	
		vec3 diffuse = mix(diff * lightColor[i], vec3(1.0, 1.0, 1.0), haloFinal);
		result = result + ((calculateLighting[i]*diffuse));
		
	}

	
	vec3 fin = (ambient + result) * texture(tex1, finTexCoord).xyz;
	outColor = vec4(fin,1.0f);

}