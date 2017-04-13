#pragma version(1)

// The java_package_name directive needs to use your Activity's package path
#pragma rs java_package_name(com.example.administrator.rs)

// Store the input allocation
rs_allocation inputAllocation;

uchar4 __attribute__((kernel)) magnify(uchar4 in, int x, int y) {


  uchar4 cur= rsGetElementAt_uchar4(inputAllocation, x, y);
  if(x==0){
  return cur;
  }
  uchar4 fomr=rsGetElementAt_uchar4(inputAllocation,x-1,y);
  cur=fomr-cur+128;


   return cur;//返回输入图像的指定位置的像素点
}
void init(){
}
void ok(){}