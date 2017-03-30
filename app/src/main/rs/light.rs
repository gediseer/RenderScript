#pragma version(1)
#pragma rs java_package_name(com.example.administrator.rs)


rs_allocation inputAllocation;

int light=50;//光照强度
float atX;
float atY;
float radius;

uchar4 __attribute__((kernel)) magnify(uchar4 in, int x, int y) {

float pointDistanceFromCircleCenter = sqrt(pow((float)(x - atX),2) + pow((float)(y - atY),2));//勾股定理计算当前像素点到光源中心的距离

if(radius < pointDistanceFromCircleCenter) { //如果距离大于指定的光源半径，也就是说当前点不在被照射区域
return in;//把当前点原样返回
}

 uchar4 cur= rsGetElementAt_uchar4(inputAllocation, x, y);//拿到当前像素点

float lightRate=1-pointDistanceFromCircleCenter/radius;//越靠近光源中心该比率越大，最终加上分量值后越靠近255
int rate=light*lightRate;

 cur.r=min(255,cur.r+rate);
 cur.g=min(255,cur.g+rate);
 //cur.b=min(255,cur.b+rate);

  return cur;
   }
