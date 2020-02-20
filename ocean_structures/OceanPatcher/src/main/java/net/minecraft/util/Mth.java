package net.minecraft.util;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.IntPredicate;
import java.util.UUID;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Random;

public class Mth {
    public static final float SQRT_OF_TWO;
    private static final float[] SIN;
    private static final Random RANDOM;
    private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION;
    private static final double FRAC_BIAS;
    private static final double[] ASIN_TAB;
    private static final double[] COS_TAB;
    
    public static float sin(final float float1) {
        return Mth.SIN[(int)(float1 * 10430.378f) & 0xFFFF];
    }
    
    public static float cos(final float float1) {
        return Mth.SIN[(int)(float1 * 10430.378f + 16384.0f) & 0xFFFF];
    }
    
    public static float sqrt(final float float1) {
        return (float)Math.sqrt(float1);
    }
    
    public static float sqrt(final double double1) {
        return (float)Math.sqrt(double1);
    }
    
    public static int floor(final float float1) {
        final int integer2 = (int)float1;
        return (float1 < integer2) ? (integer2 - 1) : integer2;
    }
    
    public static int fastFloor(final double double1) {
        return (int)(double1 + 1024.0) - 1024;
    }
    
    public static int floor(final double double1) {
        final int integer3 = (int)double1;
        return (double1 < integer3) ? (integer3 - 1) : integer3;
    }
    
    public static long lfloor(final double double1) {
        final long long3 = (long)double1;
        return (double1 < long3) ? (long3 - 1L) : long3;
    }
    
    public static int absFloor(final double double1) {
        return (int)((double1 >= 0.0) ? double1 : (-double1 + 1.0));
    }
    
    public static float abs(final float float1) {
        return Math.abs(float1);
    }
    
    public static int abs(final int integer) {
        return Math.abs(integer);
    }
    
    public static int ceil(final float float1) {
        final int integer2 = (int)float1;
        return (float1 > integer2) ? (integer2 + 1) : integer2;
    }
    
    public static int ceil(final double double1) {
        final int integer3 = (int)double1;
        return (double1 > integer3) ? (integer3 + 1) : integer3;
    }
    
    public static int clamp(final int integer1, final int integer2, final int integer3) {
        if (integer1 < integer2) {
            return integer2;
        }
        if (integer1 > integer3) {
            return integer3;
        }
        return integer1;
    }
    
    public static long clamp(final long long1, final long long2, final long long3) {
        if (long1 < long2) {
            return long2;
        }
        if (long1 > long3) {
            return long3;
        }
        return long1;
    }
    
    public static float clamp(final float float1, final float float2, final float float3) {
        if (float1 < float2) {
            return float2;
        }
        if (float1 > float3) {
            return float3;
        }
        return float1;
    }
    
    public static double clamp(final double double1, final double double2, final double double3) {
        if (double1 < double2) {
            return double2;
        }
        if (double1 > double3) {
            return double3;
        }
        return double1;
    }
    
    public static double clampedLerp(final double double1, final double double2, final double double3) {
        if (double3 < 0.0) {
            return double1;
        }
        if (double3 > 1.0) {
            return double2;
        }
        return lerp(double3, double1, double2);
    }
    
    public static double absMax(double double1, double double2) {
        if (double1 < 0.0) {
            double1 = -double1;
        }
        if (double2 < 0.0) {
            double2 = -double2;
        }
        return (double1 > double2) ? double1 : double2;
    }
    
    public static int intFloorDiv(final int integer1, final int integer2) {
        return Math.floorDiv(integer1, integer2);
    }
    
    public static int nextInt(final Random random, final int integer2, final int integer3) {
        if (integer2 >= integer3) {
            return integer2;
        }
        return random.nextInt(integer3 - integer2 + 1) + integer2;
    }
    
    public static float nextFloat(final Random random, final float float2, final float float3) {
        if (float2 >= float3) {
            return float2;
        }
        return random.nextFloat() * (float3 - float2) + float2;
    }
    
    public static double nextDouble(final Random random, final double double2, final double double3) {
        if (double2 >= double3) {
            return double2;
        }
        return random.nextDouble() * (double3 - double2) + double2;
    }
    
    public static double average(final long[] arr) {
        long long2 = 0L;
        for (final long long3 : arr) {
            long2 += long3;
        }
        return long2 / (double)arr.length;
    }
    
    public static boolean equal(final float float1, final float float2) {
        return Math.abs(float2 - float1) < 1.0E-5f;
    }
    
    public static boolean equal(final double double1, final double double2) {
        return Math.abs(double2 - double1) < 9.999999747378752E-6;
    }
    
    public static int positiveModulo(final int integer1, final int integer2) {
        return Math.floorMod(integer1, integer2);
    }
    
    public static float positiveModulo(final float float1, final float float2) {
        return (float1 % float2 + float2) % float2;
    }
    
    public static double positiveModulo(final double double1, final double double2) {
        return (double1 % double2 + double2) % double2;
    }
    
    public static int wrapDegrees(final int integer) {
        int integer2 = integer % 360;
        if (integer2 >= 180) {
            integer2 -= 360;
        }
        if (integer2 < -180) {
            integer2 += 360;
        }
        return integer2;
    }
    
    public static float wrapDegrees(final float float1) {
        float float2 = float1 % 360.0f;
        if (float2 >= 180.0f) {
            float2 -= 360.0f;
        }
        if (float2 < -180.0f) {
            float2 += 360.0f;
        }
        return float2;
    }
    
    public static double wrapDegrees(final double double1) {
        double double2 = double1 % 360.0;
        if (double2 >= 180.0) {
            double2 -= 360.0;
        }
        if (double2 < -180.0) {
            double2 += 360.0;
        }
        return double2;
    }
    
    public static float degreesDifference(final float float1, final float float2) {
        return wrapDegrees(float2 - float1);
    }
    
    public static float degreesDifferenceAbs(final float float1, final float float2) {
        return abs(degreesDifference(float1, float2));
    }
    
    public static float rotateIfNecessary(final float float1, final float float2, final float float3) {
        final float float4 = degreesDifference(float1, float2);
        final float float5 = clamp(float4, -float3, float3);
        return float2 - float5;
    }
    
    public static float approach(final float float1, final float float2, float float3) {
        float3 = abs(float3);
        if (float1 < float2) {
            return clamp(float1 + float3, float1, float2);
        }
        return clamp(float1 - float3, float2, float1);
    }
    
    public static float approachDegrees(final float float1, final float float2, final float float3) {
        final float float4 = degreesDifference(float1, float2);
        return approach(float1, float1 + float4, float3);
    }
    
    public static int getInt(final String string, final int integer) {
        return NumberUtils.toInt(string, integer);
    }
    
    public static int getInt(final String string, final int integer2, final int integer3) {
        return Math.max(integer3, getInt(string, integer2));
    }
    
    public static double getDouble(final String string, final double double2) {
        try {
            return Double.parseDouble(string);
        }
        catch (Throwable throwable4) {
            return double2;
        }
    }
    
    public static double getDouble(final String string, final double double2, final double double3) {
        return Math.max(double3, getDouble(string, double2));
    }
    
    public static int smallestEncompassingPowerOfTwo(final int integer) {
        int integer2 = integer - 1;
        integer2 |= integer2 >> 1;
        integer2 |= integer2 >> 2;
        integer2 |= integer2 >> 4;
        integer2 |= integer2 >> 8;
        integer2 |= integer2 >> 16;
        return integer2 + 1;
    }
    
    private static boolean isPowerOfTwo(final int integer) {
        return integer != 0 && (integer & integer - 1) == 0x0;
    }
    
    public static int ceillog2(int integer) {
        integer = (isPowerOfTwo(integer) ? integer : smallestEncompassingPowerOfTwo(integer));
        return Mth.MULTIPLY_DE_BRUIJN_BIT_POSITION[(int)(integer * 125613361L >> 27) & 0x1F];
    }
    
    public static int log2(final int integer) {
        return ceillog2(integer) - (isPowerOfTwo(integer) ? 0 : 1);
    }
    
    public static int roundUp(final int integer1, int integer2) {
        if (integer2 == 0) {
            return 0;
        }
        if (integer1 == 0) {
            return integer2;
        }
        if (integer1 < 0) {
            integer2 *= -1;
        }
        final int integer3 = integer1 % integer2;
        if (integer3 == 0) {
            return integer1;
        }
        return integer1 + integer2 - integer3;
    }
    
    public static int color(final float float1, final float float2, final float float3) {
        return color(floor(float1 * 255.0f), floor(float2 * 255.0f), floor(float3 * 255.0f));
    }
    
    public static int color(final int integer1, final int integer2, final int integer3) {
        int integer4 = integer1;
        integer4 = (integer4 << 8) + integer2;
        integer4 = (integer4 << 8) + integer3;
        return integer4;
    }
    
    public static float frac(final float float1) {
        return float1 - floor(float1);
    }
    
    public static double frac(final double double1) {
        return double1 - lfloor(double1);
    }
    
    public static long getSeed(final int integer1, final int integer2, final int integer3) {
        long long4 = (long)(integer1 * 3129871) ^ integer3 * 116129781L ^ (long)integer2;
        long4 = long4 * long4 * 42317861L + long4 * 11L;
        return long4 >> 16;
    }
    
    public static UUID createInsecureUUID(final Random random) {
        final long long2 = (random.nextLong() & 0xFFFFFFFFFFFF0FFFL) | 0x4000L;
        final long long3 = (random.nextLong() & 0x3FFFFFFFFFFFFFFFL) | Long.MIN_VALUE;
        return new UUID(long2, long3);
    }
    
    public static UUID createInsecureUUID() {
        return createInsecureUUID(Mth.RANDOM);
    }
    
    public static double pct(final double double1, final double double2, final double double3) {
        return (double1 - double2) / (double3 - double2);
    }
    
    public static double atan2(double double1, double double2) {
        final double double3 = double2 * double2 + double1 * double1;
        if (Double.isNaN(double3)) {
            return Double.NaN;
        }
        final boolean boolean7 = double1 < 0.0;
        if (boolean7) {
            double1 = -double1;
        }
        final boolean boolean8 = double2 < 0.0;
        if (boolean8) {
            double2 = -double2;
        }
        final boolean boolean9 = double1 > double2;
        if (boolean9) {
            final double double4 = double2;
            double2 = double1;
            double1 = double4;
        }
        final double double4 = fastInvSqrt(double3);
        double2 *= double4;
        double1 *= double4;
        final double double5 = Mth.FRAC_BIAS + double1;
        final int integer14 = (int)Double.doubleToRawLongBits(double5);
        final double double6 = Mth.ASIN_TAB[integer14];
        final double double7 = Mth.COS_TAB[integer14];
        final double double8 = double5 - Mth.FRAC_BIAS;
        final double double9 = double1 * double7 - double2 * double8;
        final double double10 = (6.0 + double9 * double9) * double9 * 0.16666666666666666;
        double double11 = double6 + double10;
        if (boolean9) {
            double11 = 1.5707963267948966 - double11;
        }
        if (boolean8) {
            double11 = 3.141592653589793 - double11;
        }
        if (boolean7) {
            double11 = -double11;
        }
        return double11;
    }
    
    public static float fastInvSqrt(float float1) {
        final float float2 = 0.5f * float1;
        int integer3 = Float.floatToIntBits(float1);
        integer3 = 1597463007 - (integer3 >> 1);
        float1 = Float.intBitsToFloat(integer3);
        float1 *= 1.5f - float2 * float1 * float1;
        return float1;
    }
    
    public static double fastInvSqrt(double double1) {
        final double double2 = 0.5 * double1;
        long long5 = Double.doubleToRawLongBits(double1);
        long5 = 6910469410427058090L - (long5 >> 1);
        double1 = Double.longBitsToDouble(long5);
        double1 *= 1.5 - double2 * double1 * double1;
        return double1;
    }
    
    public static float fastInvCubeRoot(final float float1) {
        int integer2 = Float.floatToIntBits(float1);
        integer2 = 1419967116 - integer2 / 3;
        float float2 = Float.intBitsToFloat(integer2);
        float2 = 0.6666667f * float2 + 1.0f / (3.0f * float2 * float2 * float1);
        float2 = 0.6666667f * float2 + 1.0f / (3.0f * float2 * float2 * float1);
        return float2;
    }
    
    public static int hsvToRgb(final float float1, final float float2, final float float3) {
        final int integer4 = (int)(float1 * 6.0f) % 6;
        final float float4 = float1 * 6.0f - integer4;
        final float float5 = float3 * (1.0f - float2);
        final float float6 = float3 * (1.0f - float4 * float2);
        final float float7 = float3 * (1.0f - (1.0f - float4) * float2);
        float float8 = 0.0f;
        float float9 = 0.0f;
        float float10 = 0.0f;
        switch (integer4) {
            case 0: {
                float8 = float3;
                float9 = float7;
                float10 = float5;
                break;
            }
            case 1: {
                float8 = float6;
                float9 = float3;
                float10 = float5;
                break;
            }
            case 2: {
                float8 = float5;
                float9 = float3;
                float10 = float7;
                break;
            }
            case 3: {
                float8 = float5;
                float9 = float6;
                float10 = float3;
                break;
            }
            case 4: {
                float8 = float7;
                float9 = float5;
                float10 = float3;
                break;
            }
            case 5: {
                float8 = float3;
                float9 = float5;
                float10 = float6;
                break;
            }
            default: {
                throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + float1 + ", " + float2 + ", " + float3);
            }
        }
        final int integer5 = clamp((int)(float8 * 255.0f), 0, 255);
        final int integer6 = clamp((int)(float9 * 255.0f), 0, 255);
        final int integer7 = clamp((int)(float10 * 255.0f), 0, 255);
        return integer5 << 16 | integer6 << 8 | integer7;
    }
    
    public static int murmurHash3Mixer(int integer) {
        integer ^= integer >>> 16;
        integer *= -2048144789;
        integer ^= integer >>> 13;
        integer *= -1028477387;
        integer ^= integer >>> 16;
        return integer;
    }
    
    public static int binarySearch(int integer1, final int integer2, final IntPredicate intPredicate) {
        int integer3 = integer2 - integer1;
        while (integer3 > 0) {
            final int integer4 = integer3 / 2;
            final int integer5 = integer1 + integer4;
            if (intPredicate.test(integer5)) {
                integer3 = integer4;
            }
            else {
                integer1 = integer5 + 1;
                integer3 -= integer4 + 1;
            }
        }
        return integer1;
    }
    
    public static float lerp(final float float1, final float float2, final float float3) {
        return float2 + float1 * (float3 - float2);
    }
    
    public static double lerp(final double double1, final double double2, final double double3) {
        return double2 + double1 * (double3 - double2);
    }
    
    public static double lerp2(final double double1, final double double2, final double double3, final double double4, final double double5, final double double6) {
        return lerp(double2, lerp(double1, double3, double4), lerp(double1, double5, double6));
    }
    
    public static double lerp3(final double double1, final double double2, final double double3, final double double4, final double double5, final double double6, final double double7, final double double8, final double double9, final double double10, final double double11) {
        return lerp(double3, lerp2(double1, double2, double4, double5, double6, double7), lerp2(double1, double2, double8, double9, double10, double11));
    }
    
    public static double smoothstep(final double double1) {
        return double1 * double1 * double1 * (double1 * (double1 * 6.0 - 15.0) + 10.0);
    }
    
    public static int sign(final double double1) {
        if (double1 == 0.0) {
            return 0;
        }
        return (double1 > 0.0) ? 1 : -1;
    }
    
    public static float rotLerp(final float float1, final float float2, final float float3) {
        return float2 + float1 * wrapDegrees(float3 - float2);
    }
    
    @Deprecated
    public static float rotlerp(final float float1, final float float2, final float float3) {
        float float4;
        for (float4 = float2 - float1; float4 < -180.0f; float4 += 360.0f) {}
        while (float4 >= 180.0f) {
            float4 -= 360.0f;
        }
        return float1 + float3 * float4;
    }
    
    @Deprecated
    public static float rotWrap(double double1) {
        while (double1 >= 180.0) {
            double1 -= 360.0;
        }
        while (double1 < -180.0) {
            double1 += 360.0;
        }
        return (float)double1;
    }
    
    static {
        SQRT_OF_TWO = sqrt(2.0f);
        final AtomicInteger integer2 = new AtomicInteger(0);
        Consumer<float[]> consumer = arr -> {
            for (integer2.set(0); integer2.get() < arr.length; integer2.incrementAndGet()) {
                arr[integer2.get()] = (float)Math.sin(integer2.get() * 3.141592653589793 * 2.0 / 65536.0);
            }
            return;
        };
        float[] sins = new float[65536];
        consumer.accept(sins);
        SIN = sins;
        RANDOM = new Random();
        MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[] { 0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9 };
        FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
        ASIN_TAB = new double[257];
        COS_TAB = new double[257];
        for (int integer3 = 0; integer3 < 257; ++integer3) {
            final double double2 = integer3 / 256.0;
            final double double3 = Math.asin(double2);
            Mth.COS_TAB[integer3] = Math.cos(double3);
            Mth.ASIN_TAB[integer3] = double3;
        }
    }
}
