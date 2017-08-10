package com.ksyun.media.streamer.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.InputStream;

public class BitmapLoader {
    private static final String a = "BitmapLoader";
    private static final int b = 2048;

    public static Bitmap loadBitmap(Context context, String str) {
        return loadBitmap(context, str, b, b);
    }

    public static Bitmap loadBitmap(Context context, String str, int i, int i2) {
        String str2 = "file://";
        String str3 = "assets://";
        if (context == null || str == null || str.isEmpty()) {
            Log.e(a, "loadBitmap " + str + " failed!");
            return null;
        }
        Bitmap decodeFile;
        if (str.startsWith(str2)) {
            decodeFile = BitmapFactory.decodeFile(str.substring(str2.length()));
        } else if (str.startsWith(str3)) {
            decodeFile = a(context, str.substring(str3.length()));
        } else {
            decodeFile = BitmapFactory.decodeFile(str);
            if (decodeFile == null) {
                decodeFile = a(context, str);
            }
        }
        if (decodeFile == null) {
            Log.e(a, "loadBitmap " + str + " failed!");
            return null;
        }
        if (i == 0 && i2 == 0) {
            if (decodeFile.getWidth() > decodeFile.getHeight()) {
                i = b;
            } else {
                i2 = b;
            }
        }
        if ((decodeFile.getWidth() <= i || i <= 0) && (decodeFile.getHeight() <= i2 || i2 <= 0)) {
            return decodeFile;
        }
        if (decodeFile.getWidth() <= i || i <= 0) {
            i = (decodeFile.getWidth() * i2) / decodeFile.getHeight();
        } else {
            i2 = (decodeFile.getHeight() * i) / decodeFile.getWidth();
        }
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(decodeFile, (i / 2) * 2, (i2 / 2) * 2, true);
        decodeFile.recycle();
        return createScaledBitmap;
    }

    private static Bitmap a(Context context, String str) {
        Throwable th;
        Bitmap bitmap = null;
        InputStream open;
        try {
            open = context.getAssets().open(str);
            try {
                bitmap = BitmapFactory.decodeStream(open);
                if (open != null) {
                    try {
                        open.close();
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e2) {
                if (open != null) {
                    try {
                        open.close();
                    } catch (Exception e3) {
                    }
                }
                return bitmap;
            } catch (Throwable th2) {
                th = th2;
                if (open != null) {
                    try {
                        open.close();
                    } catch (Exception e4) {
                    }
                }
                throw th;
            }
        } catch (Exception e5) {
            open = bitmap;
            if (open != null) {
                open.close();
            }
            return bitmap;
        } catch (Throwable th3) {
            Throwable th4 = th3;
            open = bitmap;
            th = th4;
            if (open != null) {
                open.close();
            }
            throw th;
        }
        return bitmap;
    }
}
