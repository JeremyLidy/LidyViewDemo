package com.lidy.demo

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activtiy_draw_layout.*
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import com.google.android.material.tabs.TabLayout


class DrawActivity : AppCompatActivity() {

    private val pageModels = mutableListOf<PageModel>()

    init {
        pageModels.add(PageModel(R.layout.sample_color, R.string.drawColor, R.layout.practice_color))
        pageModels.add(PageModel(R.layout.sample_color, R.string.drawColor, R.layout.practice_color))
        pageModels.add(PageModel(0, R.string.drawText, R.layout.draw_text_view))
        pageModels.add(PageModel(0, R.string.imageText, R.layout.image_text_view))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activtiy_draw_layout)

        view_pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {

            override fun getItem(position: Int): Fragment {
                val pageModel = pageModels[position]
                return PageFragment.newInstance(pageModel.sampleLayoutRes, pageModel.practiceLayoutRes)
            }

            override fun getCount(): Int {
                return pageModels.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return getString(pageModels[position].titleRes)
            }
        }
        tab_layout.tabGravity = TabLayout.GRAVITY_CENTER
        tab_layout.setupWithViewPager(view_pager)
    }

    private class PageModel(@LayoutRes var sampleLayoutRes: Int, @StringRes var titleRes: Int, @LayoutRes var practiceLayoutRes: Int)

}
