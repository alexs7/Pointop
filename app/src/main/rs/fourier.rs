#pragma version(1)
#pragma rs java_package_name(alroshapps.camerarealtimefilters)

rs_allocation inPixels;
int height;
int width;

void root(const uchar4 *in, uchar4 *out, uint32_t x, uint32_t y) {
    float3 fourierPixel = convert_float4(in[0]).rgb;

    if(!rsIsObject(inPixels)){
       rsDebug("inPixel Null", 0);
    };

    int fourierSum = 0;

    for(int k=0; k<=width; ++k){
        for(int l=0; l<=height; ++l){
            //float3 pixel = convert_float4(rsGetElementAt_uchar4(inPixels, k, l)).rgb;
            //float greyOrigPixel = (pixel.r + pixel.g + pixel.b)/3;
            //float angle = 2 * M_PI * ( ((x * k) / width) + ((y * l) / height) );
            //fourierSum = fourierSum + greyOrigPixel*cos(angle);
        };
    };

    //rsDebug("fourierSum", fourierSum);
    fourierPixel.rgb = 255;

    out->xyz = convert_uchar3(fourierPixel);
}