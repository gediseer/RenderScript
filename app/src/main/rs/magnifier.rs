#pragma version(1)
#pragma rs java_package_name(com.example.administrator.rs)


// Store the input allocation rs_allocation inputAllocation;
// Magnifying
// TODO: here, some checks should be performed to prevent atX and atY to be < 0, as well
// as them to not be greater than width and height
rs_allocation inputAllocation;
int atX;
int atY;
float radius;//放大半径
 float scale;//放大倍率
// The scale is >= 1
uchar4 __attribute__((kernel)) magnify(uchar4 in, int x, int y) {
// Calculates the distance between the touched point and the current kernel
// iteration pixel coordinated
// Reference: http://math.stackexchange.com/a/198769
float pointDistanceFromCircleCenter = sqrt(pow((float)(x - atX),2) + pow((float)(y - atY),2));//勾股定理计算当前像素点到光源中心的距离
// Is this pixel outside the magnify radius?
if(radius < pointDistanceFromCircleCenter) { //如果距离大于指定的光源半径，也就是说当前点不在被照射区域
// In this case, just copy the original image
return in;//把当前点原样返回
}


float diffX = x - atX;
float diffY = y - atY;
// Scales down the distance accordingly to scale and returns the original coordinates
 int originalX = atX + round(diffX / scale);//round四舍五入
 int originalY = atY + round(diffY / scale);
 // Return the original image pixel at the calculated coordinates
 uchar4 cur= rsGetElementAt_uchar4(inputAllocation, originalX, originalY);
  return cur;
   }
