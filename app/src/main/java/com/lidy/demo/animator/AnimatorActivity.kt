package com.lidy.demo.animator

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.lidy.demo.DrawActivity
import com.lidy.demo.PageFragment
import com.lidy.demo.R
import kotlinx.android.synthetic.main.activtiy_draw_layout.*

/**
 * @author lideyou
 */
class AnimatorActivity : AppCompatActivity() {

    private val pageModels = mutableListOf<PageModel>()

    init {
        pageModels.add(PageModel(R.layout.multi_touch_layout, "tab 0"))
        pageModels.add(PageModel(R.layout.sample_image_layout, "tab 1"))
        pageModels.add(PageModel(R.layout.animator_circle_view, "tab 2"))
        pageModels.add(PageModel(R.layout.camera_view_layout, "camera"))
        pageModels.add(PageModel(R.layout.province_view_layout, "province"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animator_layout)

        view_pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {

            override fun getItem(position: Int): Fragment {
                val pageModel = pageModels[position]
                return PageAnimatorFragment.newInstance(pageModel.sampleLayoutRes)
            }

            override fun getCount(): Int {
                return pageModels.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return pageModels[position].title
            }
        }
        tab_layout.tabGravity = TabLayout.GRAVITY_CENTER
        tab_layout.setupWithViewPager(view_pager)
    }


    companion object {
        class PageModel(@LayoutRes var sampleLayoutRes: Int, var title: String)
    }
}
