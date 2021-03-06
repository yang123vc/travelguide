package com.guidewithme.util;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;

import com.guidewithme.article.ArticleInfo;
import com.mapswithme.maps.api.MWMPoint;

public class Utils
{
  public static View hideView(View view)
  {
    view.setVisibility(View.GONE);
    return view;
  }

  public static int random(int max)
  {
    return new Random().nextInt(max);
  }

  public static View showView(View view)
  {
    view.setVisibility(View.VISIBLE);
    return view;
  }

  public static View hideIf(boolean condition, View view)
  {
    if (condition)
      hideView(view);
    else
      showView(view);
    return view;
  }

  public static boolean fileExists(String path)
  {
    final File file = new File(path);
    return file.exists();
  }

  public static boolean isExternalUrl(String path)
  {
    return path.startsWith("http") || path.startsWith("www.");
  }

  public static void fadeIn(Context context, final View target)
  {
    final Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
    final AnimationListener listener = new AnimationListener()
    {

      @Override
      public void onAnimationStart(Animation animation)
      {
        showView(target);
      }

      @Override
      public void onAnimationRepeat(Animation animation)
      {}

      @Override
      public void onAnimationEnd(Animation animation)
      {}
    };
    anim.setAnimationListener(listener);
    target.startAnimation(anim);
  }

  public static void fadeOut(Context context, final View target)
  {
    final Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
    final AnimationListener listener = new AnimationListener()
    {

      @Override
      public void onAnimationStart(Animation animation)
      {}

      @Override
      public void onAnimationRepeat(Animation animation)
      {}

      @Override
      public void onAnimationEnd(Animation animation)
      {
        hideView(target);
      }
    };
    anim.setAnimationListener(listener);
    target.startAnimation(anim);
  }

  public static boolean isPictUrl(String url)
  {
    return url.endsWith(".png") || url.endsWith(".jpg") || url.endsWith(".jpeg") || url.endsWith(".svg");
  }

  public static boolean isApiLevelAbove(int version)
  {
    return Build.VERSION.SDK_INT > version;
  }

  public static void hideKeyboard(Activity activity)
  {
    final InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(
        activity.getWindow().getDecorView().getWindowToken(), 0);
  }

  public static MWMPoint articleInfo2MwmPoint(ArticleInfo info)
  {
    final String url = info.getArticleId();
    final String id = url.substring(0, url.lastIndexOf('.'));
    return new MWMPoint(info.getLat(), info.getLon(), info.getName(), id);
  }


  private final static NumberFormat df = DecimalFormat.getInstance(); // frequently used, so we dont recreate
  static
  {
    df.setMinimumFractionDigits(0);
    df.setMaximumFractionDigits(2);
    df.setRoundingMode(RoundingMode.DOWN);
  }

  public static String formatDataSize(long bytes)
  {
    if (bytes < 0)
      return "";
    final String[] suffixes = {"B", "KB", "MB", "GB", "TB", "EB"};
    final int powerOf1024 = (int) log1024(bytes);
    final double shortValue = bytes/Math.pow(1024, powerOf1024);
    return df.format(shortValue) + suffixes[powerOf1024];
  }

  public static double log1024(double value)
  {
    return Math.log(value)/Math.log(1024);
  }

  public static boolean notNull(Object o)
  {
    return o != null;
  }


  public static boolean isAnchorUrl(String string)
  {
    return !TextUtils.isEmpty(string)
        && string.contains("#");
  }

}
