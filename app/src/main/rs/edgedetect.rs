#pragma version(1)
#pragma rs java_package_name(com.apps.alexs7.pointop)

rs_allocation inPixels;
int height;
int width;

uchar4 RS_KERNEL root(uchar4 in, uint32_t x, uint32_t y) {

  uchar4 pixel = in.rgba;

  pixel.r = (pixel.r + pixel.g + pixel.b)/3;
  pixel.g = (pixel.r + pixel.g + pixel.b)/3;
  pixel.b = (pixel.r + pixel.g + pixel.b)/3;

  return pixel;
}