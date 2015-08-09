#pragma version(1)
#pragma rs java_package_name(com.apps.alexs7.pointop)

rs_allocation inPixels;

//TODO: Needs review
uchar4 RS_KERNEL root(uchar4 in, uint32_t x, uint32_t y) {

  uchar4 pixel = in.rgba;
  uchar4 grayPixel;

  grayPixel.r = (pixel.r + pixel.g + pixel.b)/3;
  grayPixel.g = (pixel.r + pixel.g + pixel.b)/3;
  grayPixel.b = (pixel.r + pixel.g + pixel.b)/3;
  grayPixel.a = pixel.a;

  return grayPixel;
}