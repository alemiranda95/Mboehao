package io.github.jokoframework.utilities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.androidplot.xy.XYPlot;

import io.github.jokoframework.constants.AppConstants;

/**
 * Created by joaquin on 23/08/17.
 * @author joaquin
 * @author afeltes
 */
public class ImageUtils {

    private static final String DEFAULT_FONT_SIZE = "2";
    private static final String HD_FONT_SIZE = "3";
    private static final String TABLET_FONT_SIZE = "4";

    private ImageUtils() {
    }

    public static Bitmap getScaledBitmap(Resources resources, XYPlot plot, int imageId) {
        Bitmap bitmap = BitmapFactory.decodeResource(resources, imageId);
        double plotWidth = (double) plot.getWidth() * 0.5;
        double plotHeight = (double) plot.getHeight() * 0.5;

        int flagWidth;
        int flagHeight;
        int minFlagSize = AppConstants.MIN_WARNING_POINTS_SIZE;
        int minWidth = (int) plotWidth / 6;
        int minHeight = (int) plotHeight / 5;
        int minFlagDimension = minHeight < minWidth ? minHeight : minWidth;

        if (minFlagDimension > minFlagSize) {
            flagWidth = minFlagDimension;
            flagHeight = flagWidth;
        } else if (minFlagDimension < minFlagSize) {
            flagWidth = minFlagSize;
            flagHeight = flagWidth;
        } else {
            flagWidth = AppConstants.DEFAULT_WARNING_POINTS_SIZE;
            flagHeight = flagWidth;
        }

        bitmap = Bitmap.createScaledBitmap(bitmap, flagWidth, flagHeight, false);
        return bitmap;
    }

    public static Point getScreenSizeInPixels(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public static Bitmap getScaledBitmapForDailyTest(Resources resources, Point canvasSize, int imageId) {
        Bitmap bitmap = BitmapFactory.decodeResource(resources, imageId);
        double canvasHeight = (double) canvasSize.y;
        int imageWidth;
        int imageHeight;
        int minSize = (int) (canvasHeight * 0.25);
        float aspectRatio = bitmap.getWidth() /
                (float) bitmap.getHeight();

        if (canvasHeight > AppConstants.HD_VERTICAL_RESOLUTION) {
            imageHeight = minSize;
            imageWidth = Math.round(imageHeight * aspectRatio);
        } else if (canvasHeight <= AppConstants.HD_VERTICAL_RESOLUTION && canvasHeight > AppConstants.HVGA_VERTICAL_RESOLUTION) {
            imageHeight = (int) (canvasHeight * 0.2);
            if (AppConstants.MIN_DAILY_TEST_IMAGE_MIN_HEIGHT > imageHeight) {
                imageHeight = AppConstants.MIN_DAILY_TEST_IMAGE_MIN_HEIGHT;
            }
            imageWidth = Math.round(imageHeight * aspectRatio);
        } else {
            imageHeight = AppConstants.MIN_DAILY_TEST_IMAGE_MIN_HEIGHT;
            imageWidth = Math.round(imageHeight * aspectRatio);
        }

        bitmap = Bitmap.createScaledBitmap(bitmap, imageWidth, imageHeight, false);
        return bitmap;
    }

    public static String getHtmlScaledFontSize(Context context) {
        String fontSize = DEFAULT_FONT_SIZE;
        Point screenSize = getScreenSizeInPixels(context);
        if (screenSize != null) {
            if (AppConstants.TABLET_VERTICAL_RESOLUTION >= screenSize.y) {
                fontSize = TABLET_FONT_SIZE;
            } else if (AppConstants.HD_VERTICAL_RESOLUTION >= screenSize.y) {
                fontSize = HD_FONT_SIZE;
            }
        }
        return fontSize;
    }
}
