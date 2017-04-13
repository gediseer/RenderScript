#pragma version(1)

// The java_package_name directive needs to use your Activity's package path
#pragma rs java_package_name(com.example.administrator.rs)

// Store the input allocation
rs_allocation inputAllocation;
int threshold=3;
uchar4 __attribute__((kernel)) magnify(uchar4 in, int x, int y) {



  int dx=rsRand(threshold);
  int dy=rsRand(threshold);
  if(dx+x<0||dy+y<0){
  uchar4 cur= rsGetElementAt_uchar4(inputAllocation, x, y);
  return cur;//返回输入图像的指定位置的像素点
  }else{

   uchar4 cur= rsGetElementAt_uchar4(inputAllocation, dx+x, dy+y);
return cur;//返回输入图像的指定位置的像素点
  }


}
void init(){
}
void ok(){}