#pragma version(1)
#pragma rs java_package_name(alroshapps.camerarealtimefilters)

rs_allocation inPixels;
int height;
int width;
int threeBythree[];

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
        //float3 pixelTL = convert_float4(pixelCharsTL).rgb;

        float3 pixelTL = convert_float4(rsGetElementAt_uchar4(inPixels, x-1, y-1)).rgb;
        threeBythree[0] = (pixelTL.r + pixelTL.g + pixelTL.b)/3;

        //upper middle pixel
        float4 elementF4UM = rsUnpackColor8888(*(uchar*)rsGetElementAt(inPixels, x, y-1));
        uchar4 pixelCharsUM = rsPackColorTo8888(elementF4UM);
        float3 pixelUM = convert_float4(pixelCharsUM).rgb;
        threeBythree[1] = (pixelUM.r + pixelUM.g + pixelUM.b)/3;

        //top right pixel
        float4 elementF4TR = rsUnpackColor8888(*(uchar*)rsGetElementAt(inPixels, x+1, y+1));
        uchar4 pixelCharsTR = rsPackColorTo8888(elementF4TR);
        float3 pixelTR = convert_float4(pixelCharsTR).rgb;
        threeBythree[2] = (pixelTR.r + pixelTR.g + pixelTR.b)/3;

        // middle left pixel
        float4 elementF4ML = rsUnpackColor8888(*(uchar*)rsGetElementAt(inPixels, x-1, y));
        uchar4 pixelCharsML = rsPackColorTo8888(elementF4ML);
        float3 pixelML = convert_float4(pixelCharsML).rgb;
        threeBythree[3] = (pixelML.r + pixelML.g + pixelML.b)/3;

        //middle right pixel
        float4 elementF4MR = rsUnpackColor8888(*(uchar*)rsGetElementAt(inPixels, x+1, y));
        uchar4 pixelCharsMR = rsPackColorTo8888(elementF4MR);
        float3 pixelMR = convert_float4(pixelCharsMR).rgb;
        threeBythree[4] = (pixelMR.r + pixelMR.g + pixelMR.b)/3;

        //middle center pixel (the same as pixel)
        float4 elementF4MC = rsUnpackColor8888(*(uchar*)rsGetElementAt(inPixels, x, y));
        uchar4 pixelCharsMC = rsPackColorTo8888(elementF4MC);
        float3 pixelMC = convert_float4(pixelCharsMC).rgb;
        threeBythree[5] = (pixelMC.r + pixelMC.g + pixelMC.b)/3;

        //bottom left pixel
        float4 elementF4BL = rsUnpackColor8888(*(uchar*)rsGetElementAt(inPixels, x-1, y+1));
        uchar4 pixelCharsBL = rsPackColorTo8888(elementF4BL);
        float3 pixelBL = convert_float4(pixelCharsBL).rgb;
        threeBythree[6] = (pixelBL.r + pixelBL.g + pixelBL.b)/3;

        //bottom middle left pixel
        float4 elementF4BML = rsUnpackColor8888(*(uchar*)rsGetElementAt(inPixels, x, y+1));
        uchar4 pixelCharsBML = rsPackColorTo8888(elementF4BML);
        float3 pixelBML = convert_float4(pixelCharsBML).rgb;
        threeBythree[7] = (pixelBML.r + pixelBML.g + pixelBML.b)/3;

        //bottom right pixel
        float4 elementF4BR = rsUnpackColor8888(*(uchar*)rsGetElementAt(inPixels, x+1, y+1));
        uchar4 pixelCharsBR = rsPackColorTo8888(elementF4BR);
        float3 pixelBR = convert_float4(pixelCharsBR).rgb;
        threeBythree[8] = (pixelBR.r + pixelBR.g + pixelBR.b)/3;

        //pixel.r = (pixelTL.r + pixelUM.r + pixelTR.r + pixelML.r + pixelMR.r + pixelBL.r + pixelBML.r + pixelBR.r)/8;
        //pixel.g = (pixelTL.g + pixelUM.g + pixelTR.g + pixelML.g + pixelMR.g + pixelBL.g + pixelBML.g + pixelBR.g)/8;
        //pixel.b = (pixelTL.b + pixelUM.b + pixelTR.b + pixelML.b + pixelMR.b + pixelBL.b + pixelBML.b + pixelBR.b)/8;

        //for (int i = 1; i < 9 ; i++) {
        //    int j = i;
        //    while( j > 0 && threeBythree[j-1] > threeBythree[j]){
        //        int tempV = threeBythree[j-1];
        //        threeBythree[j-1] = threeBythree[j];
        //        threeBythree[j] = tempV;
        //        j = j - 1;
        //    };
        //};

        //pixel.r =  threeBythree[4];
        //pixel.g =  threeBythree[4];
        //pixel.b =  threeBythree[4];

        int averageValue = 0;
        for (int i = 0; i < 9 ; i++) {
            //rsDebug("threeBythree", threeBythree[i]);
            averageValue = averageValue + threeBythree[i];
        };

        //rsDebug("averagevalue", averageValue);
        pixel.r =  averageValue/9;
        pixel.g =  averageValue/9;
        pixel.b =  averageValue/9;

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