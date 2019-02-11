package com.lidy.demo.animator

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.lidy.demo.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class AnimatorActivityTest {

    @Rule
    val mainActivityActivityTestRule = ActivityTestRule(AnimatorActivity::class.java)

    @Test
    fun testAutoComplete() {
        onView(withId(R.id.tab_layout)).perform()
    }
}
