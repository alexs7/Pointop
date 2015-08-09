#pragma version(1)
#pragma rs java_package_name(com.apps.alexs7.pointop)

rs_allocation inPixels;
int height;
int width;

uchar4 RS_KERNEL root(uchar4 in, uint32_t x, uint32_t y) {

  uchar4 pixel = in.rgba;

  if(x==0 || x==width || y==0 || y==height){
      pixel.rb = 0;
      pixel.g = 255;
  }else{ //do image processing here
      uchar4 pixelNH = rsGetElementAt_uchar4(inPixels, x+1, y);
      uchar4 pixelNV = rsGetElementAt_uchar4(inPixels, x, y+1);

      int grayAvg = (pixel.r + pixel.g + pixel.b)/3;
      int grayAvgNH = (pixelNH.r + pixelNH.g + pixelNH.b)/3;
      int grayAvgNV = (pixelNV.r + pixelNV.g + pixelNV.b)/3;

      int edgePixelValue = abs(2 * grayAvg - grayAvgNH + grayAvgNV);
      pixel.rgb = edgePixelValue;
  };

  return pixel;
}