# CircleProgressBar
本控件是仿一般app欢迎页右上角的圆形加载图标


## 使用方式

        implementation 'com.github.lixido159:circle_progress:Tag'

1.在布局文件添加CircleProgressBar

2.xml属性设置

       app:color_foreground=""  //外圈加载颜色
       app:color_text_background=""  //中间圆背景色
       app:color_background=""  //外圈未加载颜色   
       app:color_text=""        //文字颜色
       app:text_size=""         //文字大小
       app:scale=""             //内半径与外圈宽度的比
       app:duration=""          //转圈结束时间
       app:text_inside=""       //内部文字内容


3.java动态设置  
属性可以通过对应得get/set方法设置  
  
开始旋转动画,指定初始度数,max=360  
``startAnim(int progress)``

    circleProgressBar.setOnRotationListener(new CircleProgressBar.RotationListener() {
            @Override
            public void onStartRotating() {//动画开始
                
            }

            @Override
            public void onRotating(int value) {//进行中,value为进度0-100

            }

            @Override
            public void onFinishRotating() {//动画结束

            }
        });
        
4.默认值  
最简单的话只要宽高都为wrap_content,再指定内部文字就可以使用,默认字体随宽高变大
