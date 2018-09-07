package com.loper7.layout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.AnimRes;
import android.support.annotation.ColorInt;
import android.support.annotation.Dimension;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * titleBar
 * <p>
 * author:loper7
 * <p>
 * time:2018-1-8
 */
public class TitleBar extends RelativeLayout {

    private String TAG = "TitleBar";

    private Context mContext;

    private static Config mConfig = new Config();

    private static int defaultMenuTextSize;
    private static int defaultTitleTextSize;
    private static int defaultBorderWidth;
    private static int defaultPadding;
    private static boolean defaultCenterTitle;
    private static boolean defaultUseRipple;
    private static boolean defaultShowBorder;
    private static boolean defaultTitleTextBold;
    private static TextUtils.TruncateAt defaultTitleEllipsize = TextUtils.TruncateAt.MARQUEE;
    private static int defaultTitleTextColor;
    private static int defaultMenuTextColor;
    private static int defaultBorderColor;

    private static int defaultBackGroundColor;

    private static int activityEnterAnim;
    private static int activityExitAnim;

    private boolean isCenterTitle;
    private boolean isUseRipple;
    private boolean titleTextBold;
    private boolean isShowBorder;
    private TextUtils.TruncateAt titleEllipsize;

    private TextView tvTitle;
    private TextView tvMenu;
    private ImageView ivBack;
    private View border;

    private LayoutParams backParams;
    private LayoutParams titleParams;
    private LayoutParams menuParams;
    private LayoutParams borderParams;

    private Drawable backImageRes;
    private Drawable menuImageRes;

    private int padding;
    private int titleTextSize;
    private int menuTextSize;
    private int borderWidth;

    private int titleTextColor;
    private int menuTextColor;
    private int backGroundColor;
    private int borderColor;

    private String titleText;
    private String menuText;

    private OnBackListener onBackListener;
    private OnMenuListener onMenuListener;
    private OnTitleListener onTitleListener;


    public TitleBar(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        if (defaultPadding == 0)
            this.defaultPadding = this.dip2px(context, 16.0F);
        if (defaultTitleTextSize == 0)
            this.defaultTitleTextSize = this.sp2px(context, 18.0F);
        if (defaultMenuTextSize == 0)
            this.defaultMenuTextSize = this.sp2px(context, 14.0F);
        if (defaultBorderWidth == 0)
            this.defaultBorderWidth = this.dip2px(context, 0.6f);
        this.getAttr(attrs);
        this.initLayout();
    }


    /**
     * 加载自定义属性
     *
     * @param attrs 自定属性
     */
    private void getAttr(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        backImageRes = typedArray.getDrawable(R.styleable.TitleBar_tBackImage);
        isCenterTitle = typedArray.getBoolean(R.styleable.TitleBar_tCenterTitle, defaultCenterTitle);
        padding = typedArray.getDimensionPixelSize(R.styleable.TitleBar_tPadding, defaultPadding);
        titleTextSize = typedArray.getDimensionPixelSize(R.styleable.TitleBar_tTitleTextSize, defaultTitleTextSize);
        titleTextColor = typedArray.getColor(R.styleable.TitleBar_tTitleTextColor, defaultTitleTextColor);
        titleText = typedArray.getString(R.styleable.TitleBar_tTitleText);
        menuTextSize = typedArray.getDimensionPixelSize(R.styleable.TitleBar_tMenuTextSize, defaultMenuTextSize);
        menuTextColor = typedArray.getColor(R.styleable.TitleBar_tMenuTextColor, defaultMenuTextColor);
        menuText = typedArray.getString(R.styleable.TitleBar_tMenuText);
        menuImageRes = typedArray.getDrawable(R.styleable.TitleBar_tMenuImage);
        isUseRipple = typedArray.getBoolean(R.styleable.TitleBar_tUseRipple, defaultUseRipple);
        backGroundColor = typedArray.getColor(R.styleable.TitleBar_tBackgroundColor, defaultBackGroundColor);
        borderColor = typedArray.getColor(R.styleable.TitleBar_tBorderColor, defaultBorderColor);
        isShowBorder = typedArray.getBoolean(R.styleable.TitleBar_tShowBorder, defaultShowBorder);
        borderWidth = typedArray.getDimensionPixelSize(R.styleable.TitleBar_tBorderWidth, defaultBorderWidth);
        titleTextBold = typedArray.getBoolean(R.styleable.TitleBar_tTitleTextBold, defaultTitleTextBold);

        int ellipsize = typedArray.getInt(R.styleable.TitleBar_tTitleEllipsize, -1);
        switch (ellipsize) {
            case 0:
                titleEllipsize = TextUtils.TruncateAt.START;
                break;
            case 1:
                titleEllipsize = TextUtils.TruncateAt.END;
                break;
            case 2:
                titleEllipsize = TextUtils.TruncateAt.MIDDLE;
                break;
            case 3:
                titleEllipsize = TextUtils.TruncateAt.MARQUEE;
                break;
            default:
                titleEllipsize = defaultTitleEllipsize;
                break;
        }
        typedArray.recycle();
    }

    private void initLayout() {
        this.initBack(backImageRes);
        this.initMenu();
        this.initTitle();
        this.initBorder();
        this.setUseRipple(isUseRipple);
        this.setBackgroundColor(backGroundColor);
    }

    /**
     * 初始化返回按钮
     *
     * @param backImageRes 返回图片资源id
     */
    @SuppressLint("NewApi")
    private void initBack(Drawable backImageRes) {
        backParams = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        backParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        ivBack = new ImageView(mContext);
        ivBack.setId(View.generateViewId());
        ivBack.setPadding(padding, 0, padding, 0);
        ivBack.setLayoutParams(backParams);
        if (backImageRes != null) {
            ivBack.setImageDrawable(backImageRes);
            ivBack.setVisibility(VISIBLE);
        } else {
            ivBack.setVisibility(GONE);
        }

        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBackListener != null) {
                    onBackListener.onBackClick();
                } else {
                    try {
                        Activity activity = getActivity();
                        closeKeyboard(activity);
                        activity.finish();
                        if (activityEnterAnim != 0 && activityExitAnim != 0)
                            activity.overridePendingTransition(activityEnterAnim, activityExitAnim);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        this.addView(ivBack);
    }

    /**
     * 初始化菜单按钮
     */
    @SuppressLint("NewApi")
    private void initMenu() {
        menuParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        menuParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        tvMenu = new TextView(mContext);
        tvMenu.setId(View.generateViewId());
        tvMenu.setGravity(Gravity.CENTER_VERTICAL);
        tvMenu.setPadding(padding, 0, padding, 0);
        tvMenu.setLayoutParams(menuParams);
        tvMenu.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) menuTextSize);
        tvMenu.setTextColor(menuTextColor);
        tvMenu.setText(menuText);
        if (menuImageRes != null) {
            tvMenu.setText("");
            this.setCompoundDrawable(tvMenu, menuImageRes);
        }
        tvMenu.setVisibility((TextUtils.isEmpty(menuText) && menuImageRes == null) ? GONE : VISIBLE);

        tvMenu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMenuListener != null)
                    onMenuListener.onMenuClick();
            }
        });

        this.addView(tvMenu);
    }

    /**
     * 初始化标题
     */
    @SuppressLint("NewApi")
    private void initTitle() {
        titleParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        tvTitle = new TextView(mContext);
        tvTitle.setId(View.generateViewId());
        tvTitle.setGravity(Gravity.CENTER_VERTICAL);
        tvTitle.setLayoutParams(titleParams);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) titleTextSize);
        tvTitle.setText(titleText);
        tvTitle.setTextColor(titleTextColor);
        tvTitle.setSingleLine(true);
        TextPaint paint = tvTitle.getPaint();
        paint.setFakeBoldText(titleTextBold);
        this.setTitleEllipsize(titleEllipsize);
        this.setCenterTitle(isCenterTitle);
        tvTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTitleListener != null)
                    onTitleListener.onTitleClick();
            }
        });

        this.addView(tvTitle);
    }

    /**
     * 初始化底部线
     */
    private void initBorder() {
        borderParams = new LayoutParams(LayoutParams.MATCH_PARENT, borderWidth);
        borderParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        border = new View(mContext);
        border.setBackgroundColor(borderColor);
        border.setLayoutParams(borderParams);
        if (isShowBorder)
            border.setVisibility(VISIBLE);
        else
            border.setVisibility(GONE);

        this.addView(border);
    }


    /**********************************************************公共方法****************************************************************/


    /**
     * 设置返回按钮图片
     *
     * @param drawable drawable
     */
    public void setBackImageDrawable(Drawable drawable) {
        if (drawable == null) {
            ivBack.setVisibility(GONE);
            return;
        }
        ivBack.setVisibility(VISIBLE);
        this.backImageRes = drawable;
        ivBack.setImageDrawable(backImageRes);
        this.setCenterTitle(isCenterTitle);
    }

    /**
     * 设置返回按钮图片
     *
     * @param resId resId
     */
    public void setBackImageResource(@DrawableRes int resId) {
        Drawable drawable = ContextCompat.getDrawable(mContext, resId);
        this.setBackImageDrawable(drawable);
    }

    /**
     * 设置标题文字
     *
     * @param title title
     */
    public void setTitleText(String title) {
        this.titleText = title;
        tvTitle.setText(title);
        this.setCenterTitle(isCenterTitle);
    }

    /**
     * 设置标题文字
     *
     * @param resId resId
     */
    public void setTitleText(@StringRes int resId) {
        this.titleText = mContext.getResources().getText(resId).toString();
        tvTitle.setText(titleText);
        this.setCenterTitle(isCenterTitle);
    }

    /**
     * 设置标题文字颜色
     *
     * @param color color
     */
    public void setTitleTextColor(@ColorInt int color) {
        this.titleTextColor = color;
        tvTitle.setTextColor(titleTextColor);
    }

    /**
     * 设置标题文字大小
     *
     * @param sp sp
     */
    public void setTitleTextSize(@Dimension int sp) {
        this.titleTextSize = sp2px(mContext, sp);
        tvTitle.setTextSize(titleTextSize);
    }

    /**
     * 设置标题显示模式
     *
     * @param isCenterTitle 是否居中
     */
    @SuppressLint("NewApi")
    public void setCenterTitle(boolean isCenterTitle) {
        if (tvTitle == null || titleParams == null)
            return;
        this.isCenterTitle = isCenterTitle;

        if (isCenterTitle && isEnoughAvailableWidth()) {
            titleParams.removeRule(RelativeLayout.RIGHT_OF);
            titleParams.removeRule(RelativeLayout.LEFT_OF);
            titleParams.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
            titleParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            tvTitle.setPadding(0, 0, 0, 0);
        } else {
            titleParams.removeRule(RelativeLayout.CENTER_IN_PARENT);
            titleParams.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
            titleParams.addRule(RelativeLayout.RIGHT_OF, ivBack.getId());
            titleParams.addRule(RelativeLayout.LEFT_OF, tvMenu.getId());
            if (ivBack.getVisibility() == GONE)
                titleParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

            tvTitle.setPadding(ivBack.getVisibility() == GONE ? padding : 0, 0, tvMenu.getVisibility() == GONE ? padding : 0, 0);
        }
    }

    /**
     * 设置标题ellipsize属性
     *
     * @param ellipsize
     */
    public void setTitleEllipsize(TextUtils.TruncateAt ellipsize) {
        this.titleEllipsize = ellipsize;

        tvTitle.setEllipsize(titleEllipsize);
        tvTitle.setSelected(true);
        tvTitle.setFocusable(true);
        tvTitle.setFocusableInTouchMode(true);
    }

    /**
     * 设置菜单按钮图片
     *
     * @param drawable drawable
     */
    public void setMenuImageDrawable(Drawable drawable) {
        if (drawable == null) {
            tvMenu.setVisibility(GONE);
            return;
        }
        this.menuImageRes = drawable;
        this.setCompoundDrawable(tvMenu, drawable);
        tvMenu.setText("");
        tvMenu.setVisibility(VISIBLE);
        this.setCenterTitle(isCenterTitle);
    }

    /**
     * 设置菜单按钮图片
     *
     * @param resId resId
     */
    public void setMenuImageResource(@DrawableRes int resId) {
        Drawable drawable = ContextCompat.getDrawable(mContext, resId);
        this.setMenuImageDrawable(drawable);
    }

    /**
     * 设置菜单文字
     *
     * @param menuText menuText
     */
    public void setMenuText(String menuText) {
        if (TextUtils.isEmpty(menuText)) {
            tvMenu.setVisibility(GONE);
            return;
        }
        this.menuText = menuText;
        tvMenu.setText(menuText);
        tvMenu.setVisibility(VISIBLE);
        this.setCenterTitle(isCenterTitle);
    }

    /**
     * 设置菜单文字
     *
     * @param resId resId
     */
    public void setMenuText(@StringRes int resId) {
        if (resId == 0) {
            tvMenu.setVisibility(GONE);
            return;
        }
        this.menuText = mContext.getResources().getText(resId).toString();
        tvMenu.setText(menuText);
        tvMenu.setVisibility(VISIBLE);
        this.setCenterTitle(isCenterTitle);
    }

    /**
     * 设置菜单文字颜色
     *
     * @param color color
     */
    public void setMenuTextColor(@ColorInt int color) {
        this.menuTextColor = color;
        tvMenu.setTextColor(menuTextColor);
    }

    /**
     * 设置菜单文字大小
     *
     * @param sp sp
     */
    public void setMenuTextSize(@Dimension int sp) {
        this.menuTextSize = sp2px(mContext, sp);
        tvMenu.setTextSize(menuTextSize);
    }

    /**
     * 设置activity进入退出动画
     *
     * @param enter 进入动画
     * @param exit  退出动画
     */
    public void setActivityAnim(@AnimRes int enter, @AnimRes int exit) {
        activityEnterAnim = enter;
        activityExitAnim = exit;
    }

    /**
     * 清除activity动画
     */
    public void clearActivityAnim() {
        activityEnterAnim = 0;
        activityExitAnim = 0;
    }


    /**
     * 返回按钮点击监听
     *
     * @param onBackListener onBackListener
     */
    public void setOnBackListener(OnBackListener onBackListener) {
        this.onBackListener = onBackListener;
    }

    /**
     * 菜单按钮点击监听
     *
     * @param onMenuListener onMenuListener
     */
    public void setOnMenuListener(OnMenuListener onMenuListener) {
        this.onMenuListener = onMenuListener;
    }

    /**
     * 标题点击监听
     *
     * @param onTitleListener onTitleListener
     */
    public void setOnTitleListener(OnTitleListener onTitleListener) {
        this.onTitleListener = onTitleListener;
    }

    /**
     * 设置水波纹
     *
     * @param isUseRipple isUseRipple
     */
    public void setUseRipple(boolean isUseRipple) {
        this.isUseRipple = isUseRipple;
        if (isUseRipple) {
            TypedValue tv = new TypedValue();
            mContext.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, tv, true);

            if (tvMenu != null)
                tvMenu.setBackgroundResource(tv.resourceId);
            if (ivBack != null)
                ivBack.setBackgroundResource(tv.resourceId);
        } else {
            tvMenu.setBackgroundResource(0);
            ivBack.setBackgroundResource(0);
        }
    }

    /**
     * 设置底部分割线是否显示
     *
     * @param isShowBorder isShowBorder
     */
    public void setShowBorder(boolean isShowBorder) {
        this.isShowBorder = isShowBorder;
        if (isShowBorder)
            border.setVisibility(VISIBLE);
        else
            border.setVisibility(GONE);

    }

    /**
     * 设置底部分割线颜色
     *
     * @param color color
     */
    public void setBorderColor(@ColorInt int color) {
        this.borderColor = color;
        if (border != null)
            border.setBackgroundColor(borderColor);
    }

    /**
     * 设置底部分割线的宽度
     *
     * @param dp
     */
    public void setBorderWidth(@Dimension float dp) {
        this.borderWidth = dip2px(mContext, dp);
        borderParams = new LayoutParams(LayoutParams.MATCH_PARENT, borderWidth);
        borderParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        border.setLayoutParams(borderParams);
    }

    /**
     * 设置背景颜色
     *
     * @param color color
     */
    public void setBackGroundColor(@ColorInt int color) {
        this.backGroundColor = color;
        this.setBackgroundColor(backGroundColor);

    }


    /**
     * 获取返回view
     *
     * @return backView
     */
    public ImageView getBackView() {
        if (ivBack == null)
            new NullPointerException("back imageView is null,may be titBar is no initialization");

        return ivBack;
    }


    /**
     * 获取标题view
     *
     * @return titleView
     */
    public TextView getTitleView() {
        if (tvTitle == null)
            new NullPointerException("title textView is null,may be titBar is no initialization");

        return tvTitle;
    }

    /**
     * 获取菜单view
     *
     * @return menuView
     */
    public TextView getMenuView() {
        if (tvMenu == null)
            new NullPointerException("menuView textView is null,may be titBar is no initialization");

        return tvMenu;
    }

    /**
     * 获取底线view
     *
     * @return BorderView
     */
    public View getBorderView() {
        if (border == null)
            new NullPointerException("borderView View is null,may be titBar is no initialization");

        return border;
    }


    /**
     * 获取全局配置的class
     *
     * @return
     */
    public static Config getConfig() {
        return mConfig;
    }


    /**********************************************************end*************************************************************************/

    /**
     * 得到Title文字宽度
     *
     * @param text     文字
     * @param textSize px
     * @return
     */
    private float getTitleTextWidth(String text, int textSize) {
        if (TextUtils.isEmpty(text))
            return 0;

        TextPaint paint = tvTitle.getPaint();
        paint.setTextSize(textSize);
        return paint.measureText(text);
    }

    /**
     * titleBar留给Title的宽度是否足够
     *
     * @return
     */
    private boolean isEnoughAvailableWidth() {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        ivBack.measure(width, height);
        tvMenu.measure(width, height);
        //titleBar留给Title的宽度
        int availableWidth = getWindowWidth(mContext) - (tvMenu.getMeasuredWidth() + ivBack.getMeasuredWidth());
        float titleTextWidth = getTitleTextWidth(titleText, titleTextSize);

        return availableWidth > titleTextWidth;
    }

    /**
     * 根据手机的分辨率dp 转成px(像素)
     */
    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率sp 转成px(像素)
     */
    private static int sp2px(Context context, float spValue) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5F);
    }

    /**
     * 得到屏幕宽度
     *
     * @param context
     * @return
     */
    private int getWindowWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    /**
     * 获取activity
     *
     * @return
     * @throws Exception Unable to get Activity.
     */
    private Activity getActivity() throws Exception {
        Context context = getContext();
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }

        if (context instanceof Activity) {
            return (Activity) context;
        }
        throw new Exception("Unable to get Activity.");
    }

    /**
     * 关闭软键盘
     *
     * @param activity
     */
    private void closeKeyboard(Activity activity) {
        if (activity == null)
            return;

        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * textView上设置图片
     *
     * @param tv       textView上设置图片
     * @param drawable 图片
     */
    private void setCompoundDrawable(TextView tv, Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
        tv.setCompoundDrawables(drawable, null, null, null);//画在左边
    }


    public interface OnBackListener {
        void onBackClick();
    }

    public interface OnMenuListener {
        void onMenuClick();

    }

    public interface OnTitleListener {
        void onTitleClick();

    }


    /**
     * 全局配置的Class，对所有使用到的地方有效
     */
    public static class Config {

        public Config setTitleTextSize(Context context, @Dimension int sp) {
            defaultTitleTextSize = sp2px(context, sp);
            return mConfig;
        }

        public Config setTitleTextColor(@ColorInt int color) {
            defaultTitleTextColor = color;
            return mConfig;
        }

        public Config setMenuTextSize(Context context, @Dimension int sp) {
            defaultMenuTextSize = sp2px(context, sp);
            return mConfig;
        }

        public Config setMenuTextColor(@ColorInt int color) {
            defaultMenuTextColor = color;
            return mConfig;
        }

        public Config setPadding(Context context, @Dimension int dp) {
            defaultPadding = dip2px(context, dp);
            return mConfig;
        }

        public Config setCenterTitle(boolean isCenter) {
            defaultCenterTitle = isCenter;
            return mConfig;
        }

        public Config setUseRipple(boolean isUseRipple) {
            defaultUseRipple = isUseRipple;
            return mConfig;
        }

        public Config setTitleEllipsize(TextUtils.TruncateAt titleEllipsize) {
            defaultTitleEllipsize = titleEllipsize;
            return mConfig;
        }

        public Config setBackgroundColor(@ColorInt int color) {
            defaultBackGroundColor = color;
            return mConfig;
        }

        public Config setBorderColor(@ColorInt int color) {
            defaultBorderColor = color;
            return mConfig;
        }

        public Config setShowBorder(boolean isShow) {
            defaultShowBorder = isShow;
            return mConfig;
        }

        public Config setBorderWidth(Context context, float dp) {
            defaultBorderWidth = dip2px(context, dp);
            return mConfig;
        }

        public Config setTitleTextBold(boolean isBold) {
            defaultTitleTextBold = isBold;
            return mConfig;
        }

        public Config setActivityBackAnim(@AnimRes int enter, @AnimRes int exit) {
            activityEnterAnim = enter;
            activityExitAnim = exit;
            return mConfig;
        }
    }
}
