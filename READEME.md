# Android 防止快速点击插件
**两行代码导入，整个项目全部自动添加防止快速点击的代码。**
#### 导入使用
TODO

#### 思路实现：<br/>
**Android 动态插桩：Gradle + ASM**
1. 在编译期，对项目中的所有类代码进行遍历；
2. 对于 Activity 和 Fragment 的类，在类中加入全局的变量用来计时，并在该类的OnClick方法中加入快速点击的判断；
动态加入的代码如下：
```
//全局定义
    private long lastClickTime = 0L;
    // 两次点击间隔不能少于1000ms
    private static final int FAST_CLICK_DELAY_TIME = 1000;

    //在设置点击事件方法的监听时
    void onClick(View view){
       if (System.currentTimeMillis() - lastClickTime < FAST_CLICK_DELAY_TIME){
           return;
        }
       lastClickTime = System.currentTimeMillis();

       //下面进行其他操作，比如跳转等
       XXX
 }
```
