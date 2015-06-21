#pragma version(1)
#pragma rs java_package_name(alroshapps.camerarealtimefilters)

rs_allocation inPixels;
int height;
int width;
int threeBythree[];

void root(const uchar4 *in, uchar4 *out, uint32_t x, uint32_t y) {
    float3 pixel = convert_float4(in[0]).rgb;

    if(x==0 || x==239 || y==0 || y==269){
        pixel.rb = 0;
        pixel.g = 255;
    }else{ //do image processing here

        float3 pixelNH = convert_float4(rsGetElementAt_uchar4(inPixels, x+1, y)).rgb;
        float3 pixelNV = convert_float4(rsGetElementAt_uchar4(inPixels, x, y+1)).rgb;

        int grayAvg = (pixel.r + pixel.g + pixel.b)/3;
        int grayAvgNH = (pixelNH.r + pixelNH.g + pixelNH.b)/3;
        int grayAvgNV = (pixelNV.r + pixelNV.g + pixelNV.b)/3;

        int edgeOperatorValue = 2*grayAvg - grayAvgNH - grayAvgNV;

        if(edgeOperatorValue < 0){
            edgeOperatorValue = -1 * edgeOperatorValue;
        };

        pixel.r = edgeOperatorValue;
        pixel.g = edgeOperatorValue;
        pixel.b = edgeOperatorValue;
    };

    out->xyz = convert_uchar3(pixel);
}