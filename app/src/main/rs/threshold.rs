#pragma version(1)
#pragma rs java_package_name(com.apps.alexs7.pointop)

rs_allocation inPixels;
int height;
int width;

uchar4 RS_KERNEL root(uchar4 in, uint32_t x, uint32_t y) {

  uchar4 pixel = in.rgba;

  float avg_value = (pixel.r + pixel.g + pixel.b)/3;

  if(avg_value > 127){
    pixel.rgb = 255;
  }else{
    pixel.rgb = 0;
  }

  return pixel;
}