package com.hytech.amapdemo

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cn.like.nightmodel.NightModelManager
import com.amap.api.maps.AMap
import com.amap.api.navi.AMapNavi
import com.amap.api.navi.AMapNaviViewOptions
import com.hytech.amapdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private lateinit var mAMapNaviViewOptions: AMapNaviViewOptions

    private var mAMapNavi: AMapNavi? = null
    private var aMap: AMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        NightModelManager.getInstance().attach(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mapView.onCreate(savedInstanceState)
        initNavi()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        NightModelManager.getInstance().detach(this)
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    private fun initNavi() {
        mAMapNaviViewOptions = binding.mapView.viewOptions
        mAMapNaviViewOptions.isSettingMenuEnabled = false //设置菜单按钮是否在导航界面显示
        mAMapNaviViewOptions.isTrafficBarEnabled = true //设置 返回路况光柱条是否显示（只适用于驾车导航，需要联网）
        mAMapNaviViewOptions.isLayoutVisible = false //是否显示导航视图
        mAMapNaviViewOptions.isSecondActionVisible = false//设置是否显示下下个路口的转向引导，默认不显示
        mAMapNaviViewOptions.setPointToCenter(0.5, 0.5)//设置自车位置居中
        mAMapNaviViewOptions.setLeaderLineEnabled(Color.RED) //设置牵引线颜色，为ARGB格式。不显示牵引线时，颜色设置为-1即可。

        mAMapNaviViewOptions.lockMapDelayed = 6 * 1000//设置锁定地图延迟毫秒数。
        mAMapNaviViewOptions.isAutoChangeZoom = true//设置是否开启动态比例尺 (锁车态下自动进行地图缩放变化)
        mAMapNaviViewOptions.isCompassEnabled = true//设置指南针图标否在导航界面显示，默认显示。true，显示；false，隐藏。
        mAMapNaviViewOptions.isLaneInfoShow = true//设置是否显示道路信息view
        mAMapNaviViewOptions.setModeCrossDisplayShow(true) //设置是否显示路口放大图(路口模型图)
        mAMapNaviViewOptions.isRouteListButtonShow = true //是否显示路线全览按钮
        mAMapNaviViewOptions.isAutoLockCar = true //设置6秒后是否自动锁车
        mAMapNaviViewOptions.isAutoDrawRoute = false //设置是否自动画路
        mAMapNaviViewOptions.isAfterRouteAutoGray = true //通过路线是否自动置灰
        mAMapNaviViewOptions.isAutoDisplayOverview = true //设置是否自动全览模式，即在算路成功后自动进入全览模式
        mAMapNaviViewOptions.isScreenAlwaysBright = false //true-常亮 false-不常亮
        binding.mapView.viewOptions = mAMapNaviViewOptions
        mAMapNavi = AMapNavi.getInstance(this)
        mAMapNavi?.setUseInnerVoice(true, true)
        mAMapNavi?.naviSetting?.setMonitorCameraEnabled(true)
        if (aMap == null) {
            aMap = binding.mapView.map
        }
        aMap?.uiSettings?.isRotateGesturesEnabled = false
    }

    fun switchTheme(view: View) {
        if (NightModelManager.getInstance().isCurrentNightModel(this)) {
            NightModelManager.getInstance().applyDayModel(this)
        } else {
            NightModelManager.getInstance().applyNightModel(this)
        }
    }
}