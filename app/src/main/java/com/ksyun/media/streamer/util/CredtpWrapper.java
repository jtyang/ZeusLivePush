package com.ksyun.media.streamer.util;

import android.util.Log;
import com.ksyun.media.streamer.framework.CredtpModel;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CredtpWrapper {
    private static String a;
    private static CredtpWrapper b;
    private List<CredtpModel> c;
    private Map<Integer, CredtpModel> d;
    private Map<Integer, String> e;

    private native List<CredtpModel> getContentList();

    static {
        a = "CredtpWrapper";
        LibraryLoader.load();
    }

    public static CredtpWrapper a() {
        if (b == null) {
            synchronized (CredtpWrapper.class) {
                if (b == null) {
                    b = new CredtpWrapper();
                }
            }
        }
        return b;
    }

    private CredtpWrapper() {
        this.c = getContentList();
        this.d = new HashMap();
        this.e = new HashMap();
        int size = this.c.size();
        if (this.c != null) {
            for (int i = 0; i < size; i++) {
                CredtpModel credtpModel = (CredtpModel) this.c.get(i);
                this.d.put(Integer.valueOf(credtpModel.getType()), credtpModel);
            }
        }
    }

    public String a(int i) {
        byte[] bArr = new byte[0];
        if (this.e.containsKey(Integer.valueOf(i))) {
            return (String) this.e.get(Integer.valueOf(i));
        }
        if (this.d.containsKey(Integer.valueOf(i))) {
            byte[] a;
            CredtpModel credtpModel = (CredtpModel) this.d.get(Integer.valueOf(i));
            try {
                a = b.a(e.a(credtpModel.getBody().toCharArray()), credtpModel.getKey().getBytes(b.a));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                a = bArr;
            }
            String str = new String(a);
            a(i, str);
            return str;
        }
        Log.w(a, "do not have the filter shader:" + String.valueOf(i));
        return null;
    }

    private void a(int i, String str) {
        this.e.put(Integer.valueOf(i), str);
    }
}
