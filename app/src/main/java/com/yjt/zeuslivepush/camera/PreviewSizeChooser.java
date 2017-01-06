package com.yjt.zeuslivepush.camera;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public final class PreviewSizeChooser {

    private final Comparator<Size> _PreferSmallerPixelCount = new Comparator<Size>() {
        public int compare(Size lhs, Size rhs) {
            return PreviewSizeChooser.this.getPixelCount(lhs) - PreviewSizeChooser.this.getPixelCount(rhs);
        }
    };
    private boolean _AspectRatioDeduction;

    public PreviewSizeChooser(boolean aspect_ratio_deduction) {
        this._AspectRatioDeduction = aspect_ratio_deduction;
    }

    protected float getAspectRatio(Size size) {
        return (float) size.width / (float) size.height;
    }

    protected int getPixelCount(Size size) {
        return size.width * size.height;
    }

    protected boolean isLargerThan(Size size, int width, int height) {
        return size.width >= width && size.height >= height;
    }

    public Size choose(CameraCharacteristics chara, int w, int h, int display_orientation) {
        int width = w;
        int height = h;
        switch (display_orientation) {
            case 90:
            case 270:
                width = h;
                height = w;
            default:
                Size[] supported_list = chara.getPreviewSizeList();
                float aspect_ratio = 0.0F;
                if (this._AspectRatioDeduction) {
                    Size preferred = chara.getPreferredPreviewSizeForVideo();
                    if (preferred != null) {
                        aspect_ratio = this.getAspectRatio(preferred);
                    }
                }

                ArrayList<Size> strict_list = new ArrayList();
                ArrayList<Size> loose_list = new ArrayList();
                for (Size s : supported_list) {
                    if (isLargerThan(s, width, height)) {
                        loose_list.add(s);
                        if (aspect_ratio == 0.0F || this.getAspectRatio(s) == aspect_ratio) {
                            strict_list.add(s);
                        }
                    }
                }
                if (strict_list.isEmpty()) {
                    if (loose_list.isEmpty()) {
                        return null;
                    }

                    strict_list = loose_list;
                }
                Collections.sort(strict_list, this._PreferSmallerPixelCount);
                return (Size) strict_list.get(0);
        }
    }

    public static Size getOptimalPreviewSize(Size[] sizes, int w, int h) {
        double ASPECT_TOLERANCE = 0.1D;
        double targetRatio = (double) w / (double) h;
        if (sizes == null) {
            return null;
        }
        Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;
        int targetHeight = h;

        for (Size size : sizes) {
            double ratio = (double) size.width / (double) size.height;
            if (Math.abs(ratio - targetRatio) <= 0.1D && (double) Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = (double) Math.abs(size.height - targetHeight);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Size size : sizes) {
                if ((double) Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = (double) Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }
}
