#pragma version(1)
#pragma rs java_package_name(com.apps.alexs7.pointop)

rs_allocation inPixels;
uint32_t height;
uint32_t width;

uchar4 RS_KERNEL root(uchar4 in, uint32_t x, uint32_t y) {

    uchar4 originalPixel = in.rgba;
    uchar4 pixel;
    float fourierValue = 0.0;
    float sum = 0.0;
    float angle = 0.0;
    float pixelAvgValue = 0.0;
    float maxMagnitude = 0.0;
    float constant = 0.0;

    for(int n1=0; n1<=height; n1++){
        for(int n2=0; n2<=width; n2++){
            pixel = rsGetElementAt_uchar4(inPixels, n1, n2);
            pixelAvgValue = (pixel.r + pixel.g + pixel.b)/3;
            angle = 2 * M_PI * ( (n1 * x / (height + 1)) + (n2 * y / (width + 1)) );
            fourierValue = pixelAvgValue * cos(degrees(angle));
            if(maxMagnitude < fourierValue){
                maxMagnitude = fourierValue;
            }
            sum = sum + fourierValue;
        };
    };

    originalPixel.rgb = (255/(log1p(fabs(maxMagnitude))))*log1p(fabs(sum));
    return originalPixel;
}