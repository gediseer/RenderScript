#pragma version(1)

// The java_package_name directive needs to use your Activity's package path
#pragma rs java_package_name(com.example.administrator.rs)

// Store the input allocation
rs_allocation inputAllocation;

uchar4 __attribute__((kernel)) magnify(uchar4 in, int x, int y) {


  uchar4 cur= rsGetElementAt_uchar4(inputAllocation, x, y);

 cur.r=min(255,0.393*cur.r+0.769*cur.g+0.189*cur.b);
 cur.g=min(255,0.349*cur.r+0.686*cur.g+0.168*cur.b);
 cur.b=min(255,0.272*cur.r+0.534*cur.g+0.131*cur.b);
   return cur;//返回输入图像的指定位置的像素点
}
void init(){
}
