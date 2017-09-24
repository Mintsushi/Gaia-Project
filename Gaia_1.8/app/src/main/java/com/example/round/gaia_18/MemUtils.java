package com.example.round.gaia_18;

/**
 * Created by Round on 2017-09-24.
 */

public class MemUtils {

    public static final float BYTE_IN_MB = 1024.0f * 1024.0f;

    public static float megabytesFree(){
        final Runtime rt = Runtime.getRuntime();
        final float bytesUsed = rt.totalMemory();
        final float mbUsed = bytesUsed / BYTE_IN_MB;
        final Float mbFree = megabytesAvailable() - mbUsed;

        return mbFree;
    }

    public static float megabytesAvailable(){
        final Runtime rt = Runtime.getRuntime();
        final float bytesAvailable = rt.maxMemory();

        return bytesAvailable / BYTE_IN_MB;
    }
}
