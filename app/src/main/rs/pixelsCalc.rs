#pragma version(1)
#pragma rs java_package_name(alroshapps.camerarealtimefilters)

void root(const uchar4 *in, uchar4 *out, uint32_t x, uint32_t y) {
    float3 pixel = convert_float4(in[0]).rgb;

    if(pixel.z < 128) {
        pixel.z = 0;
    }else{
        pixel.z = 255;
    }
    if(pixel.y < 128) {
        pixel.y = 0;
    }else{
        pixel.y = 255;
    }
    if(pixel.x < 128) {
        pixel.x = 0;
    }else{
        pixel.x = 255;
    }

    out->xyz = convert_uchar3(pixel);
}