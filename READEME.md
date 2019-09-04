//全局定义
    private long lastClickTime = 0L;
    // 两次点击间隔不能少于1000ms
    private static final int FAST_CLICK_DELAY_TIME = 1000;

    //在设置Item的监听时
    item.setOnItemClickListener(xxx){
       if (System.currentTimeMillis() - lastClickTime < FAST_CLICK_DELAY_TIME){
           return;
        }
       lastClickTime = System.currentTimeMillis();

       //下面进行其他操作，比如跳转等
       XXX
 }