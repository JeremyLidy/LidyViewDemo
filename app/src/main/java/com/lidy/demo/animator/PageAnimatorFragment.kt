package com.lidy.demo.animator

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.lidy.demo.R
import com.lidy.demo.Utils
import kotlinx.android.synthetic.main.animator_circle_view.*
import kotlinx.android.synthetic.main.fragment_page_layout.*
import kotlinx.android.synthetic.main.sample_image_layout.*

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
            if (image != null) {
                setAnimator()
            }

            if (circle_view != null) setObjectAnimator()

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


    companion object {

        fun newInstance(@LayoutRes sampleLayoutRes: Int): PageAnimatorFragment {
            val fragment = PageAnimatorFragment()
            val args = Bundle()
            args.putInt("sampleLayoutRes", sampleLayoutRes)
            fragment.arguments = args
            return fragment
        }
    }
}
