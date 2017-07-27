#version 150 core

#define PI 3.14

// Inputs
in vec2 Texcoord;
in vec3 FragPos;
in vec2 FragGamePos;

// Uniforms
uniform sampler2D tex1;
uniform sampler2D u_Texture;

const int numOfLights = 5;
const int numOfShadows = 8;

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

float distToLine(vec2 pt1, vec2 pt2, vec2 testPt)
{
  vec2 lineDir = pt2 - pt1;
  vec2 perpDir = vec2(lineDir.y, -lineDir.x);
  vec2 dirToPt1 = pt1 - testPt;
  return abs(dot(normalize(perpDir), dirToPt1));
}

float renderLine(int i, vec2 lightPos, vec2 fragPos){
			if(distToLine(shadows[i], lightPos, fragPos)<=0.04 
				&& distance(fragPos,shadows[i])<distance(shadows[i],lightPos)
				&& distance(fragPos,lightPos)>distance(shadows[i],lightPos)
			){
				//outColor = vec4(lightColor[1], 1.0);
				//return 1;
			}
			return 0;
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

vec2 midPoint(int ia, int ib){
	vec2 a  = shadows[ia];
	vec2 b = shadows[ib];
	
	return vec2(
		(a.x + b.x)/2,
		(a.y + b.y)/2
	);
		
}

float falloff(float distance){
	return (1/pow(distance, 2)) - (1/pow(5,2));
}

void main(){
	
	float lightHalo = 0f;
	float sinx = sin(time);

	float s = 0.05;
	float b = 2;
	float a = 1.6;
	float z = -3;
	float divisor = ((sinx-z)/a)+b;
	lightHalo = ((divisor*s)/distance(FragPos.xy,lightPosition[1].xy))-1;
	float lightZAnim = divisor - 1;
	if(lightHalo<0){
		lightHalo = 0;
	}
	float calculateLighting = 1f;

	if(recievesShadow==1){
		// Find out if this pixel is in a shadow
		vec2 lightPos = lightPosition[1].xy;
		lightPos.y = -lightPos.y;
		vec2 fragPos = FragGamePos;
		//
		for(int i = 0; i < (numOfShadows*4)-1; i+=4){
			
			int bl = i;
			int br = i+1;
			int tr = i + 2;
			int tl = i + 3;
			
			if(	
				renderLine(bl, lightPos, fragPos)==1
			||	renderLine(br, lightPos, fragPos)==1
			|| 	renderLine(tl, lightPos, fragPos)==1
			||	renderLine(tr, lightPos, fragPos)==1
			){
				
			}
			
			float x = distance(lightPos, fragPos);
			if(	renderShadow(tl, tr, lightPos, fragPos)==1){
				
				calculateLighting -= falloff(x);
			}
			if(	renderShadow(tr, br, lightPos, fragPos)==1){
				//float x = distance(midPoint(tr, br), fragPos);
				calculateLighting -= falloff(x);
			}
			if(	renderShadow(br, bl, lightPos, fragPos)==1){
				//float x = distance(midPoint(br, bl), fragPos);
				calculateLighting -= falloff(x);
			}
			if(	renderShadow(bl, tl, lightPos, fragPos)==1){
				//float x = distance(midPoint(bl, tl), fragPos);
				calculateLighting -= falloff(x);
			}
			
			//if(dot(v1,target)+dot(v2,target) > dot(v1, v2)){
			//if(dot(v1,v2)-dot(v1, target)>dot(v1, target)){
			//	outColor = vec4(0.0, 0.0, 0.0, 1.0);
			//	return;
			//}
			if(	PointInTriangle(fragPos, shadows[i], shadows[i+1], lightPos)==1){
					outColor = vec4(1.0, 1.0, 1.0, 1.0);
					//return;
			}
				
				
		}
		
		for(int i = 0; i < (numOfShadows*4)-1; i++){
			for(int j = 1;  j < (numOfShadows*4)-1; j++){
				
				
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
	

	for(int i = 0; i < numOfLights; i++){
		vec3 norm = normalize(vec3(0.0,0.0,1.0));
		vec3 lPos = vec3(lightPosition[i].xy, lightPosition[i].z + lightZAnim - cameraPosition.z);
		vec3 lightDir = normalize(lPos - FragPos);
		float diff = max(dot(norm, lightDir), 0.0);
		vec3 diffuse = diff * lightColor[i];
		result = result + (ambient + diffuse);
	}
	if(calculateLighting < 0.9){
		calculateLighting = 0.9;
	}
	if(calculateLighting > 1){
		calculateLighting = 1;
	}

	result = vec3(lightHalo)+ calculateLighting * result * texture(tex1, Texcoord).xyz;
	outColor = vec4(result,1.0f);

}