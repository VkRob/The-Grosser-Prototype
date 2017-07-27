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

uniform vec2 shadows[numOfShadows];
 
uniform vec3 cameraPosition; // cameraPosition.z denotes the distance of the camera from the world

// Outputs
out vec4 outColor;

float cross2f(vec2 a, vec2 b){
	return a.x*b.y-a.y*b.x;
}


float distToLine(vec2 pt1, vec2 pt2, vec2 testPt)
{
  vec2 lineDir = pt2 - pt1;
  vec2 perpDir = vec2(lineDir.y, -lineDir.x);
  vec2 dirToPt1 = pt1 - testPt;
  return abs(dot(normalize(perpDir), dirToPt1));
}

float renderLine(vec2 a, vec2 b, vec2 frag){
	if(    distToLine(a, b, frag)<=0.1
	&& distance(a, b) > distance(frag, a)
	&& distance(a, b) > distance(frag, b)){
		outColor = vec4(1.0, 1.0, 1.0, 1.0);
		return 1;
	}
	return 0;
}

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

//Line Intersects Line?
float doBoundingBoxesIntersect(vec2 a0, vec2 a1, vec2 b0, vec2 b1){
	if(				a0.x<=b1.x
			&&	a1.x>=b0.x
			&&	a0.y<=b1.y
			&& 	a1.y>=b0.y){
		return 1;
	} 
	return 0;
}

float isPointOnLine(vec2 a1, vec2 a2, vec2 b){
	vec2 a1tmp = vec2(0,0);
	vec2 a2tmp = vec2(a2.x-a1.x, b.y - a1.y);
	vec2 btmp = vec2(b.x - a1.x, b.y - a1.y);
	float r = cross2f(a2tmp, btmp);
	
	if(abs(r) < 0.1f){
	return 1;
	}
	return 0;
}


float isPointRightOfLine(vec2 a1, vec2 a2, vec2 b){
	vec2 aTemp1 = vec2(0,0);
	vec2 aTemp2 = vec2(a2.x - a1.x, a2.y - a1.y);
	vec2 bTemp = 	vec2(b.x - a1.x, b.y - a1.y);
	
	if(cross2f(aTemp2, bTemp) < 0){
		return 1;
	}else{
		return 0;
	}
}

float lineSegmentTouchesOrCrossesLine(vec2 a1, vec2 a2, vec2 b1, vec2 b2){
	if(isPointOnLine(a1, a2, b1)==1 || isPointOnLine(a1, a2, b2) == 1 || isPointRightOfLine(a1, a2, b1) == 1 || isPointRightOfLine(a1, a2, b2)==1){
		return 1;
	}
	return 0;
}

float doLinesIntersect(vec2 a1, vec2 a2, vec2 b1, vec2 b2){
	if(		doBoundingBoxesIntersect(a1, a2, b1, b2)==1
	&&	lineSegmentTouchesOrCrossesLine(a1, a2, b1, b2)==1
	&&	lineSegmentTouchesOrCrossesLine(b1, b2, a1, a2)==1){
		return 1;
	}
	return 0;
	
}

void main(){

	if(distance(FragPos.xy,lightPosition[1].xy) < 0.2){
			outColor = vec4(1.0, 1.0, 1.0, 1.0);
			return;
	}

	// Find out if this pixel is in a shadow
	vec2 lightPos = lightPosition[1].xy;
	lightPos.y = -lightPos.y;
	vec2 fragPos = FragGamePos;
	
	// 1) Calculate rays
	vec2 ASV[numOfShadows*4]; //All Shadow Vertices
	for(int i = 0; i < numOfShadows-1; i++){
		vec2 tl = vec2(shadows[i].x-0.5, shadows[i].y+0.5);
		vec2 tr = vec2(shadows[i].x+0.5, shadows[i].y+0.5);
		vec2 bl = vec2(shadows[i].x-0.5, shadows[i].y-0.5);
		vec2 br = vec2(shadows[i].x+0.5, shadows[i].y-0.5);
		
		ASV[(i*4)+0] = tl;
		ASV[(i*4)+1] = tr;
		ASV[(i*4)+2] = br;
		ASV[(i*4)+3] = bl;
		//tl = vec2(i,0);
		//tr = vec2(i+1,0);
		//bl = vec2(i,-1);
		//br = vec2(i+1,-1);
		
		
		
		//if(renderLine(lightPos, vec2(0,0),fragPos)==1){
		//return;
		//}
		//if(renderLine(tl, lightPos, fragPos)==1 
		//|| renderLine(tr, lightPos, fragPos)==1 
		//|| renderLine(bl, lightPos, fragPos)==1 
		//|| renderLine(tr, lightPos, fragPos)==1){
		//	return;
		//}
		
		if (	PointInTriangle(fragPos, tl, tr, lightPos)==1
		||   	PointInTriangle(fragPos, tr, br, lightPos)==1
		||   	PointInTriangle(fragPos, br, bl, lightPos)==1
		||   	PointInTriangle(fragPos, bl, tl, lightPos)==1){
			
		}
	}
	int hits = 0;			
	vec2 outside = vec2(9999,9999);
	for(int i = 0; i < (numOfShadows*4); i+=1){
			if(	
					PointInTriangle(fragPos, ASV[i+0], ASV[i+1], lightPos)==1){
			//||	PointInTriangle(fragPos, ASV[i+1], ASV[i+2], lightPos)==1
			//||	PointInTriangle(fragPos, ASV[i+2], ASV[i+3], lightPos)==1
			
			//||	PointInTriangle(fragPos, ASV[i+3], ASV[i+0], lightPos)==1){
				outColor = vec4(1.0, 0.0, 0.0, 1.0);
				return;
			}
			
			//Run a ray from outside the polygon to the frag position. if it collides with the edges an odd number of times, the frag position is inside, vice versa.

//ASV[i+0], ASV[i+1]
			if (doLinesIntersect(outside, fragPos, vec2(-9999,0),vec2(99999,0))==1){
				hits++;
			}
	}
	if(hits!=0){
		//outColor = vec4(0.0, 0.0, 1.0, 1.0);
		//return;
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
		vec3 lPos = vec3(lightPosition[i].xy, lightPosition[i].z - cameraPosition.z);
		vec3 lightDir = normalize(lPos - FragPos);
		float diff = max(dot(norm, lightDir), 0.0);
		vec3 diffuse = diff * lightColor[i];
		result = result + (ambient + diffuse);
	}

	result = result * texture(tex1, Texcoord).xyz;
	outColor = vec4(result,1.0f);

}