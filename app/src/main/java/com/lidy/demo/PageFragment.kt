package com.lidy.demo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import android.view.ViewStub


class PageFragment : Fragment() {

    @LayoutRes
    private var sampleLayoutRes: Int = 0
    @LayoutRes
    private var practiceLayoutRes: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (sampleLayoutRes > 0)
            return initView(layoutInflater, container)
        return initView2(layoutInflater, container)
    }

    private fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        val view = inflater.inflate(R.layout.page_fragment, container, false)
        if (sampleLayoutRes > 0) {
            val sampleStub = view.findViewById(R.id.sampleStub) as ViewStub
            sampleStub.layoutResource = sampleLayoutRes
            sampleStub.inflate()
        }
        val practiceStub = view.findViewById(R.id.practiceStub) as ViewStub
        practiceStub.layoutResource = practiceLayoutRes
        practiceStub.inflate()
        return view
    }

    private fun initView2(inflater: LayoutInflater, container: ViewGroup?): View? {
        val view = inflater.inflate(R.layout.fragment_page_1, container, false)
        val practiceStub = view.findViewById(R.id.practiceStub) as ViewStub
        practiceStub.layoutResource = practiceLayoutRes
        practiceStub.inflate()
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sampleLayoutRes = it.getInt("sampleLayoutRes")
            practiceLayoutRes = it.getInt("practiceLayoutRes")
        }
    }

    companion object {

        fun newInstance(@LayoutRes sampleLayoutRes: Int, @LayoutRes practiceLayoutRes: Int): PageFragment {
            val fragment = PageFragment()
            val args = Bundle()
            args.putInt("sampleLayoutRes", sampleLayoutRes)
            args.putInt("practiceLayoutRes", practiceLayoutRes)
            fragment.arguments = args
            return fragment
        }
    }


}
