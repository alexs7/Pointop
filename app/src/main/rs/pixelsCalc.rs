#pragma version(1)
#pragma rs java_package_name(alroshapps.camerarealtimefilters)

rs_allocation inPixels;
int height;
int width;

void root(const uchar4 *in, uchar4 *out, uint32_t x, uint32_t y) {
    float3 pixel = convert_float4(in[0]).rgb;

    //user later
    //int avgPixelVal = (pixel.r + pixel.g + pixel.b) / 3;
    //pixel.rgb = avgPixelVal;

    //avoid border pixels
    if(x==0 || x==239 || y==0 || y==269){
        float4 elementF4 = rsUnpackColor8888(*(uchar*)rsGetElementAt(inPixels, x, y));
        uchar4 tempPixelChars = rsPackColorTo8888(elementF4);
        float3 tempPixel = convert_float4(tempPixelChars).rgb;

        tempPixel.rb = 0;
        tempPixel.g = 255;
        pixel.rgb = tempPixel.rgb;
    }else{ //do image processing here
        //top left pixel
        float4 elementF4TL = rsUnpackColor8888(*(uchar*)rsGetElementAt(inPixels, x-1, y-1));
        uchar4 pixelCharsTL = rsPackColorTo8888(elementF4TL);
        float3 pixelTL = convert_float4(pixelCharsTL).rgb;

        //upper middle pixel
        float4 elementF4UM = rsUnpackColor8888(*(uchar*)rsGetElementAt(inPixels, x, y-1));
        uchar4 pixelCharsUM = rsPackColorTo8888(elementF4UM);
        float3 pixelUM = convert_float4(pixelCharsUM).rgb;

        //top right pixel
        float4 elementF4TR = rsUnpackColor8888(*(uchar*)rsGetElementAt(inPixels, x+1, y+1));
        uchar4 pixelCharsTR = rsPackColorTo8888(elementF4TR);
        float3 pixelTR = convert_float4(pixelCharsTR).rgb;

        // middle left pixel
        float4 elementF4ML = rsUnpackColor8888(*(uchar*)rsGetElementAt(inPixels, x-1, y));
        uchar4 pixelCharsML = rsPackColorTo8888(elementF4ML);
        float3 pixelML = convert_float4(pixelCharsML).rgb;

        //middle right pixel
        float4 elementF4MR = rsUnpackColor8888(*(uchar*)rsGetElementAt(inPixels, x+1, y));
        uchar4 pixelCharsMR = rsPackColorTo8888(elementF4MR);
        float3 pixelMR = convert_float4(pixelCharsMR).rgb;


        //bottom left pixel
        float4 elementF4BL = rsUnpackColor8888(*(uchar*)rsGetElementAt(inPixels, x-1, y+1));
        uchar4 pixelCharsBL = rsPackColorTo8888(elementF4BL);
        float3 pixelBL = convert_float4(pixelCharsBL).rgb;

        //bottom middle left pixel
        float4 elementF4BML = rsUnpackColor8888(*(uchar*)rsGetElementAt(inPixels, x, y+1));
        uchar4 pixelCharsBML = rsPackColorTo8888(elementF4BML);
        float3 pixelBML = convert_float4(pixelCharsBML).rgb;

        //bottom right pixel
        float4 elementF4BR = rsUnpackColor8888(*(uchar*)rsGetElementAt(inPixels, x+1, y+1));
        uchar4 pixelCharsBR = rsPackColorTo8888(elementF4BR);
        float3 pixelBR = convert_float4(pixelCharsBR).rgb;

        pixel.r = (pixelTL.r + pixelUM.r + pixelTR.r + pixelML.r + pixelMR.r + pixelBL.r + pixelBML.r + pixelBR.r)/8;
        pixel.g = (pixelTL.g + pixelUM.g + pixelTR.g + pixelML.g + pixelMR.g + pixelBL.g + pixelBML.g + pixelBR.g)/8;
        pixel.b = (pixelTL.b + pixelUM.b + pixelTR.b + pixelML.b + pixelMR.b + pixelBL.b + pixelBML.b + pixelBR.b)/8;
    };


    //playing around
    //for(int wx = 0; wx < width; wx++){
    //    for(int hy = 0; hy < height; hy++){
    //        const uchar4 *element = rsGetElementAt(inPixels, wx, hy);
    //        float4 curPixel = rsUnpackColor8888(*element);
    //        float4 output = {curPixel.r, curPixel.g, curPixel.b};
    //        uchar4 curPixelC = rsPackColorTo8888(output);
    //        //rsDebug("tempPixel", curPixelC);
    //        float4 f4 = rsUnpackColor8888(*(uchar*)rsGetElementAt(inPixels, wx, hy));
    //        rsDebug("tempPixel", rsPackColorTo8888(f4).r);
    //    }
    //}

    //need to pass the IN allocation object to access hte nearby pixels, then you can return the 1 pixel!
    //int topRight
    //float4 f4 = rsUnpackColor8888(*(uchar*)rsGetElementAt(inPixels, x+1, y+1));

    out->xyz = convert_uchar3(pixel);
}