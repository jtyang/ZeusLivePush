package com.ksyun.media.player.util;

import android.util.Log;
import java.io.File;

/* compiled from: IOUtils */
public class b {
    private static final String a;

    static {
        a = b.class.getName();
    }

    public static void a(String str) {
        if (new File(str).delete()) {
            Log.i(a, "Successfully deleted empty directory: " + str);
        } else {
            Log.e(a, "Failed to delete empty directory: " + str);
        }
    }

    public static boolean a(File file) {
        if (file.isDirectory()) {
            String[] list = file.list();
            if (list != null) {
                for (String file2 : list) {
                    if (!a(new File(file, file2))) {
                        return false;
                    }
                }
            } else {
                Log.e(a, "children can not null");
            }
        }
        return file.delete();
    }
}
