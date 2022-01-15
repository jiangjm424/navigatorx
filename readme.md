### jetpack fragment navigator时fragment的复用实现
> 使用方式
1. 首先将navgatorx模块以源代码的方式引入自己的的项目中
2. 准备好自己的meun和navigation文件，可以参考项目中的代码
3. 在布局文件中加入PersistNavHostFragment, 注意： **app:defaultNavHost="false"**  该项不能为true, 否则会引起按返回键时，导航控件会切到开始的fragment，但是主页面没有切。这个后面有空时再去分析
```xml
    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/nav_host_fragment_activity_main"
        android:name="cn.jm.happy.navfragment.persist.PersistNavHostFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:defaultNavHost="false"
        app:layout_constraintBottom_toTopOf="@id/nav_view"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:navGraph="@navigation/mobile_navigation" />
```
4. 在activity中初始navcontroller （注意，如果第三步中使用的fragment标签，则可以直接使用navigator中的函数findNavController获取， 否则请使用findNavControllerX，此问题请参考参考中的链接）
```kt
val navController = findNavControllerX(supportFragmentManager, R.id.nav_host_fragment_activity_main)
navView.setupWithNavController(navController)
```

参考  
https://stackoverflow.com/questions/58703451/fragmentcontainerview-as-navhostfragment
感谢  
https://www.jianshu.com/p/eae6cbeb4177