## TitleBar
:star::tada: https://github.com/loperSeven/TitleBar ，满足各种日常使用的标题栏，不要让你的时间浪费在写标题栏上。

## 序言
android support library 推出的ToolBar拓展性很高，也是官方代替ActionBar的一个控件，ToolBar的优点在于可以配合其他控件写出爆炸性的UI交互，
但是在日常开发中，我们用不到这些东西，也正是因为这样，才自己写了个TitleBar。

## 截图
![](https://github.com/loperSeven/TitleBar/blob/master/img/mv.gif)
###### ps：MIUI系统默认的跳转和代码设置的跳转动画，刚好一样所以看不出来效果。

## 特性

* 极简使用方法
* 全局配置控件属性参数 （代码内>xml内>全局配置）
* 返回按钮点击事件默认监听执行返回操作（不用每个界面去写finish，它帮你做到了），自定义监听覆盖默认返回操作
* 标题显示模式人性化
* 支持水波纹点击效果

## 使用
#### 依赖
 * Gradle
   ```Java
   dependencies {
            compile 'com.github.loperSeven:TitleBar:1.0.2'
    }
    ```
 * maven
   ```Java
   <dependency>
        <groupId>com.github.loperSeven</groupId>
        <artifactId>TitleBar</artifactId>
        <version>1.0.2</version>
    </dependency>
   ```
   
 * 下载代码
    * 下载layout依赖包，以module方式导入项目 
    * 将layout中的TitleBar和res->values->attr中的TitleBar自定义属性copy到相应位置
   

#### 全局配置
```Java
   TitleBar.getConfig()
                .setTitleTextSize(getApplicationContext(), 16)
                .setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTextBlackLight))
                .setMenuTextSize(getApplicationContext(), 14)
                .setMenuTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTextBlackLight))
                .setPadding(getApplicationContext(), 16)
                .setCenterTitle(false)
                .setUseRipple(false)
                .setTitleEllipsize(TextUtils.TruncateAt.MARQUEE)
                .setBackgroundColor(Color.WHITE)
                .setBorderColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBorder))
                .setShowBorder(false)
                .setBorderWidth(getApplicationContext(), 0.6f)
                .setActivityBackAnim(R.anim.activity_backward_enter, R.anim.activity_backward_exit);
```
以上代码为设置TitleBar的默认属性，`可选择性设置也可不设置，设置后则不用在xml或代码中重复设置`，再次调用设置可覆盖之前设置，可在项目任一地方使用，前提是在你的使用titleBar之前: smile:，建议放在启动页或者Application中。（当其中某一项不满足某个场景时可在xml中或者代码中直接设置覆盖，不会影响项目中其他titleBar）


#### xml
```Java
 <com.loper7.layout.TitleBar
        android:id="@+id/main_titleBar"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        app:tBackgroundColor="@color/colorPrimary"
        app:tMenuImage="@mipmap/ic_menu_white"
        app:tTitleText="TITLEBAR"
        app:tTitleTextColor="@color/colorTextWhite"
        app:tTitleTextSize="16sp" />
```


#### 代码中

代码中可像其他View一样
```Java
 TitleBar titleBar=new TitleBar(this);
 ...
 addView(titleBar);
```
绑定LayoutParams，通过titleBar内提供的属性方法设置titleBar属性，最后添加在父View中。





<br>
<br>
<br>

### ---------------------------------下面是所有公共方法和自定义属性---------------------------------
<br>
<br>
<br>

## API

Name | Param | remark
------------ | ------------- | -------------
setBackImageDrawable | `setBackImageDrawable(Drawable drawable)` | 设置返回按钮图片
setBackImageResource | `setBackImageResource(@DrawableRes int resId)` | 设置返回按钮图片
setTitleText | `setTitleText(String title),setTitleText(@StringRes int resId)` | 设置标题文字
setTitleTextColor | `setTitleTextColor(@ColorInt int color)` | 设置标题文字颜色
setTitleTextSize | `setTitleTextSize(@Dimension int sp)` | 设置标题文字大小
setCenterTitle | `setCenterTitle(boolean isCenterTitle)` | 设置标题显示模式（true:居中显示 false:局左显示）
setTitleEllipsize | `setTitleEllipsize(TextUtils.TruncateAt ellipsize)` | 设置标题ellipsize属性
setMenuImageDrawable | `setMenuImageDrawable(Drawable drawable)` | 设置菜单按钮图片（菜单图片和文字不能同时出现，图片优先于文字显示，此逻辑若有问题请提出Issues，后面修改）
setMenuImageResource | `setMenuImageResource(@DrawableRes int resId)` | 设置菜单按钮图片
setMenuText | `setMenuText(String menuText),setMenuText(@StringRes int resId)` | 设置菜单文字
setMenuTextColor | `setMenuTextColor(@ColorInt int color)` | 设置菜单文字颜色
setMenuTextSize | `setMenuTextSize(@Dimension int sp)` | 设置菜单文字大小
setActivityAnim | `setActivityAnim(@AnimRes int enter, @AnimRes int exit)` | 设置activity进入退出动画
clearActivityAnim | `clearActivityAnim()` | 清除activity动画
setUseRipple | `setUseRipple(boolean isUseRipple)` | 设置水波纹
setShowBorder | `setShowBorder(boolean isShowBorder)` | 设置底部分割线是否显示
setBorderColor | `setBorderColor(@ColorInt int color)` | 设置底部分割线颜色
setBorderWidth | `setBorderWidth(@Dimension float dp)` | 设置底部分割线的宽度
setBackGroundColor | `setBackGroundColor(@ColorInt int color)` | 设置背景颜色
setOnBackListener | `setOnBackListener(OnBackListener onBackListener)` | 返回按钮点击监听，默认监听为退出当前activity，返回前一个activity
setOnMenuListener | `setOnMenuListener(OnMenuListener onMenuListener)` | 菜单按钮点击监听setOnTitleListener
setOnTitleListener | `setOnTitleListener(OnTitleListener onTitleListener)` | 标题点击监听
getConfig | `static Config getConfig()` | 获取全局配置的class


## 自定义属性

Name | Value | remark
------------ | ------------- | -------------
tCenterTitle | `boolean` | 设置标题显示模式（true:居中显示 false:局左显示）
tBackImage | `reference` | 设置返回按钮图片
tPadding | `dimension` | 设置子控件们的间距
tTitleTextSize | `dimension` | 设置标题文字大小
tTitleTextColor | `color` | 设置标题文字颜色
tTitleText | `string` | 设置标题文字
tMenuTextSize | `dimension` | 设置菜单文字大小
tMenuTextColor | `color` | 设置菜单文字颜色
tMenuImage | `reference` | 设置菜单图片（菜单图片和文字不能同时出现，图片优先于文字显示，此逻辑若有问题请提出Issues，后面修改）
tMenuText | `string` | 设置菜单文字
tUseRipple | `boolean` | 设置是否使用水波纹
tBackgroundColor | `color` | 设置背景颜色
tBorderColor | `color` | 设置下划线颜色
tShowBorder | `boolean` | 设置是否显示下划线
tBorderWidth | `dimension` | 设置下划线宽度
tTitleEllipsize | `enum` | 设置标题过长时显示方式


## 写在最后

喜欢请点star:star::kissing_heart:，意见、建议、bug、新的功能请提Issues，会不定期查看并更新，谢谢阅读。


