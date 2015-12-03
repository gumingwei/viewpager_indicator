# viewpager_indicator
a simple viewpagerindicator
##Demo
![Image text](https://github.com/Mingwei360/viewpager_indicator/blob/master/app/src/main/res/mipmap-hdpi/test.gif)

###xml
```xml
<com.ming.indicator.Indicator
        xmlns:ming="http://schemas.android.com/apk/res-auto"
        android:id="@+id/indicator"
        android:layout_width="match_parent"
        android:layout_height="45dp"
        android:orientation="horizontal"
        ming:cursor_color="@android:color/holo_blue_bright"
        ming:cursor_height="3dp"
        ming:tab_color_light="@android:color/holo_orange_light"
        ming:tab_color_normal="@android:color/holo_blue_bright"/>
```
###java
```java
mIndicator = (Indicator) findViewById(R.id.indicator);
mIndicator.setTabs(mStrings);
mIndicator.setViewPager(mViewPager);
```
[我的博客](http://blog.csdn.net/u013045971 "顾明伟的CSDN博客")
