package com.hytech.amapdemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.amap.api.maps.offlinemap.OfflineMapActivity
import com.amap.api.navi.AMapNavi
import com.amap.api.navi.AMapNaviViewOptions
import com.amap.api.navi.enums.NaviType
import com.amap.api.navi.enums.PathPlanningStrategy
import com.amap.api.navi.model.AMapCalcRouteResult
import com.amap.api.navi.model.NaviLatLng
import com.blankj.utilcode.util.LogUtils
import com.hytech.amapdemo.databinding.ActivityMain1Binding


class MainActivity1 : BaseActivity() {

    lateinit var binding: ActivityMain1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        mAMapNaviView = binding.mapView
        mAMapNaviView.onCreate(savedInstanceState)
        mAMapNaviView.setAMapNaviViewListener(this)

        val options = AMapNaviViewOptions()
        options.tilt = 0
        mAMapNaviView.setViewOptions(options)
    }


    override fun onInitNaviSuccess() {
        super.onInitNaviSuccess()
        calculateDriveRoute()
    }

    override fun onCalculateRouteSuccess(aMapCalcRouteResult: AMapCalcRouteResult?) {
        super.onCalculateRouteSuccess(aMapCalcRouteResult)
        LogUtils.d("路径规划成功",aMapCalcRouteResult)
//        mAMapNavi.startNavi(NaviType.EMULATOR)
    }

    fun startNavi(view: View){
        calculateDriveRoute()
    }


    fun startOfflineMap(view: View){
        startActivity(Intent(this, OfflineMapActivity::class.java))
    }


    fun calculateDriveRoute(){
        LogUtils.d("开始规划路径",mAMapNavi)
        /**
         * 方法: int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, multipleroute); 参数:
         *
         * @congestion 躲避拥堵
         * @avoidhightspeed 不走高速
         * @cost 避免收费
         * @hightspeed 高速优先
         * @multipleroute 多路径
         *
         * 说明: 以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
         * 注意: 不走高速与高速优先不能同时为true 高速优先与避免收费不能同时为true
         */
        var strategy = 0
        try {
            //再次强调，最后一个参数为true时代表多路径，否则代表单路径
            strategy = mAMapNavi.strategyConvert(true, false, false, false, false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList, strategy)
    }

}