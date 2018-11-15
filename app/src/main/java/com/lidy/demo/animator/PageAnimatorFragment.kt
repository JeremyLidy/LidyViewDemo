package com.lidy.demo.animator

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.lidy.demo.R
import com.lidy.demo.Utils
import kotlinx.android.synthetic.main.animator_circle_view.*
import kotlinx.android.synthetic.main.camera_view_layout.*
import kotlinx.android.synthetic.main.fragment_page_layout.*
import kotlinx.android.synthetic.main.province_view_layout.*
import kotlinx.android.synthetic.main.sample_image_layout.*
import android.animation.TypeEvaluator
import java.util.*


class PageAnimatorFragment : Fragment() {

    @LayoutRes
    private var sampleLayoutRes: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_page_layout, container, false)
        val sampleStub = view.findViewById(R.id.sampleStub) as ViewStub
        sampleStub.layoutResource = sampleLayoutRes
        sampleStub.inflate()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        float_action_button.setOnClickListener {

            if (image != null) setAnimator()

            if (circle_view != null) setObjectAnimator()

            if (camera_view != null) setObjectAnimators()

            if (province != null) setProvinceAnimator()

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sampleLayoutRes = it.getInt("sampleLayoutRes")
        }
    }

    private fun setAnimator() {

        image.animate()
                .translationX(Utils.dp2px(140f))
                .translationY(Utils.dp2px(60f))
                .rotation(230f)
                .alpha(0.5f)
                .setStartDelay(100)
                .start()
    }

    private fun setObjectAnimator() {

        /**
         * 第一个参数 view
         * 第二个参数 属性值的字段名称 里面需要 invalidate()
         * 第三个参数 字段的目标值
         */
        ObjectAnimator.ofFloat(circle_view, "radius", Utils.dp2px(150f))
                .start()

    }


    private fun setObjectAnimators() {

        Log.i("PageAnimatorFragment", "setObjectAnimators ")

        val bottomFlipAnimator = ObjectAnimator.ofFloat(camera_view, "bottomFilp", 45f)
                .setDuration(1500L)

        val flipRotationAnimator = ObjectAnimator.ofFloat(camera_view, "flipRotation", 270f)
                .setDuration(1500L)

        val topFlipAnimator = ObjectAnimator.ofFloat(camera_view, "topFlip", -45f)
                .setDuration(1000L)

        /**
         * 动画按照顺序播放
         */
        AnimatorSet().apply {
            playSequentially(bottomFlipAnimator, flipRotationAnimator, topFlipAnimator)
        }.start()


    }

    /**
     * 一起执行动画效果
     */
    private fun setObjectAnimators2() {

        var bottomFlipHolder = PropertyValuesHolder.ofFloat("bottomFilp", 45f)
        var flipRotationHolder = PropertyValuesHolder.ofFloat("flipRotation", 270f)
        var topFlipHolder = PropertyValuesHolder.ofFloat("topFlip", -45f)

        var objectAnimator = ObjectAnimator.ofPropertyValuesHolder(camera_view,bottomFlipHolder,flipRotationHolder,topFlipHolder)
        objectAnimator.duration = 1000L
        objectAnimator.start()

    }

    /**
     * 修改文件中的 String 属性值
     */
    private fun setProvinceAnimator() {
        province.clearAnimation()
        val animator = ObjectAnimator.ofObject(province, "province", ProvinceEvaluator(), "澳门特别行政区")
        animator.duration = 3000
        animator.start()

        animator.reverse()
    }

    internal class ProvinceEvaluator : TypeEvaluator<String> {
        override fun evaluate(fraction: Float, startValue: String, endValue: String): String {
            // 北京市      上海市       fraction 0.5f
            val startIndex = provinces.indexOf(startValue)
            val endIndex = provinces.indexOf(endValue)
            val index = (startIndex + (endIndex - startIndex) * fraction).toInt()
            return provinces[index]
        }
    }


    companion object {

        fun newInstance(@LayoutRes sampleLayoutRes: Int): PageAnimatorFragment {
            val fragment = PageAnimatorFragment()
            val args = Bundle()
            args.putInt("sampleLayoutRes", sampleLayoutRes)
            fragment.arguments = args
            return fragment
        }

        var provinces: MutableList<String> = Arrays.asList("北京市",
                "天津市",
                "上海市",
                "重庆市",
                "河北省",
                "山西省",
                "辽宁省",
                "吉林省",
                "黑龙江省",
                "江苏省",
                "浙江省",
                "安徽省",
                "福建省",
                "江西省",
                "山东省",
                "河南省",
                "湖北省",
                "湖南省",
                "广东省",
                "海南省",
                "四川省",
                "贵州省",
                "云南省",
                "陕西省",
                "甘肃省",
                "青海省",
                "台湾省",
                "内蒙古自治区",
                "广西壮族自治区",
                "西藏自治区",
                "宁夏回族自治区",
                "新疆维吾尔自治区",
                "香港特别行政区",
                "澳门特别行政区")
    }
}
