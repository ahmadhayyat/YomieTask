package com.example.yomietask

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.RadioGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.yomietask.anim.DepthPageTransformer
import com.example.yomietask.anim.ZoomOutTransformer
import com.example.yomietask.databinding.ActivityMainBinding


class MainActivity : FragmentActivity() {
    lateinit var viewPager: ViewPager2
    lateinit var bindings: ActivityMainBinding
    lateinit var sliderArrayList: ArrayList<SliderItem>
    lateinit var sliderPb: ProgressBar
    lateinit var sliderSwitch: SwitchCompat
    lateinit var timer: CountDownTimer
    lateinit var sliderRg: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindings.root)
        initViews()
        initVariables()
        setUpClicks()
        sliderSwitch.isChecked = false
        sliderRg.check(R.id.sliderZoomAnim)
    }


    private fun initViews() {
        viewPager = bindings.viewPager
        sliderPb = bindings.sliderPb
        sliderSwitch = bindings.sliderSw
        sliderRg = bindings.sliderRg
    }

    private fun initVariables() {
        sliderArrayList = ArrayList()
        sliderArrayList.add(SliderItem(R.raw.video_1))
        sliderArrayList.add(SliderItem(R.raw.video_2))
        sliderArrayList.add(SliderItem(R.raw.video_3))
        sliderArrayList.add(SliderItem(R.raw.video_4))
        sliderArrayList.add(SliderItem(R.raw.photo_1))
        sliderArrayList.add(SliderItem(R.raw.photo_2))
        sliderArrayList.add(SliderItem(R.raw.photo_3))
        sliderArrayList.add(SliderItem(R.string.url_1))
        sliderArrayList.add(SliderItem(R.string.url_2))
        viewPager.adapter = SliderAdapter(this, sliderArrayList)

    }

    private fun setUpClicks() {
        sliderSwitch.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                sliderPb.visibility = View.VISIBLE
                sliderPb.progress = 100
                startLoop()
                viewPager.isUserInputEnabled = false
            } else {
                timer.cancel()
                sliderPb.progress = 0
                viewPager.isUserInputEnabled = true
                sliderPb.visibility = View.GONE

            }
        }
        sliderRg.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.sliderZoomAnim -> {
                    viewPager.setPageTransformer(ZoomOutTransformer())
                }
                else -> {
                    viewPager.setPageTransformer(DepthPageTransformer())
                }
            }
        }
    }

    private fun startLoop() {
        timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                sliderPb.progress = sliderPb.progress - 10
                Log.i("TIMER", "$millisUntilFinished")
            }

            override fun onFinish() {
                try {
                    if (viewPager.currentItem == sliderArrayList.size - 1) {
                        viewPager.currentItem = 0
                    } else {
                        viewPager.currentItem = viewPager.currentItem + 1
                    }
                    this.start()
                    sliderPb.progress = 100
                    Log.i("TIMER", "finished")
                } catch (e: Exception) {
                    Log.e("Error", "Error: $e")
                }
            }
        }.start()
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

}