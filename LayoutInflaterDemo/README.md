
# 目的
对LayoutInflater源码中的 inflate方法参数进行说明

参考链接：
http://blog.csdn.net/yanbober/article/details/45970721


从上面分析的ViewGroup子类LinearLayout的onLayout实现代码可以看出，一般情况下layout过程会参考measure过程中计算得到的mMeasuredWidth和mMeasuredHeight来安排子View在父View中显示的位置，但这不是必须的，measure过程得到的结果可能完全没有实际用处，特别是对于一些自定义的ViewGroup，其子View的个数、位置和大小都是固定的，这时候我们可以忽略整个measure过程，只在layout函数中传入的4个参数来安排每个子View的具体位置。

到这里就不得不提getWidth()、getHeight()和getMeasuredWidth()、getMeasuredHeight()这两对方法之间的区别（上面分析measure过程已经说过getMeasuredWidth()、getMeasuredHeight()必须在onMeasure之后使用才有效）。可以看出来getWidth()与getHeight()方法必须在layout(int l, int t, int r, int b)执行之后才有效。