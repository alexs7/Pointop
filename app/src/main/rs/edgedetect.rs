#pragma version(1)
#pragma rs java_package_name(com.apps.alexs7.pointop)

//rs_allocation inPixels;
//int height;
//int width;

uchar4 RS_KERNEL root(uchar4 in, uint32_t x, uint32_t y) {
  // x and y aren't used, so you can remove those from the above signature too.
  uchar4 out;
  float3 pixel = convert_float4(in).rgb;

  pixel.r = (pixel.r + pixel.g + pixel.b)/3;
  // This seems buggy to me below, since pixel.r was just modified.
  // I think you need another temporary variable (assuming you are trying to make this work and getting weird behavior).
  pixel.g = (pixel.r + pixel.g + pixel.b)/3;
  pixel.b = (pixel.r + pixel.g + pixel.b)/3;

  //int topRight
  //float4 f4 = rsUnpackColor8888(*(uchar*)rsGetElementAt(inPixels, x+1, y+1));

  out.xyz = convert_uchar3(pixel);
  return out;
}