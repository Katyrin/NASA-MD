package com.katyrin.nasa_md

import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText
import com.katyrin.nasa_md.view.home.HomeFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class HomeFragmentTest {

    private lateinit var scenario: FragmentScenario<HomeFragment>

    @Before
    fun initTest() {
        scenario = launchFragmentInContainer(bundleOf(), themeResId = R.style.Theme_NASAMD)
    }

    @Test
    fun fragment_AssertNotNull() {
        scenario.onFragment { fragment ->
            assertNotNull(fragment)
        }
    }

    @Test
    fun fragmentImageView_NotNull() {
        scenario.onFragment { fragment ->
            val imageView = fragment.requireActivity().findViewById<ImageView>(R.id.image_view)
            assertNotNull(imageView)
        }
    }

    @Test
    fun fragmentTextView_HasText(): Unit = runBlocking {
        delay(1000)
        scenario.onFragment { fragment ->
            val bottomSheetTextView = fragment.requireActivity()
                .findViewById<TextView>(R.id.bottom_sheet_description_header)
            assertEquals("A Comet and a Crab", bottomSheetTextView.text)
        }
    }

    @Test
    fun fragmentImageView_IsGONE() {
        scenario.onFragment { fragment ->
            val imageView = fragment.requireActivity().findViewById<ImageView>(R.id.image_view)
            assertEquals(View.GONE, imageView.visibility)
        }
    }

    @Test
    fun fragmentImageView_IsVisible(): Unit = runBlocking {
        delay(2000)
        scenario.onFragment { fragment ->
            val imageView = fragment.requireActivity().findViewById<ImageView>(R.id.image_view)
            assertEquals(View.VISIBLE, imageView.visibility)
        }
    }

    @Test
    fun fragmentButton_IsChangeText(): Unit = runBlocking {
        delay(1000)
        scenario.onFragment { fragment ->
            runBlocking {
                val yesterdayChip = fragment.requireActivity().findViewById<Chip>(R.id.yesterday)
                val dayBeforeYesterdayChip =
                    fragment.requireActivity().findViewById<Chip>(R.id.day_before_yesterday)
                val bottomSheetTextView = fragment.requireActivity()
                    .findViewById<TextView>(R.id.bottom_sheet_description_header)
                val textToday = bottomSheetTextView.text

                yesterdayChip.performClick()
                delay(1000)
                val yesterdayToday = bottomSheetTextView.text
                assertNotNull(textToday.toString(), yesterdayToday)

                dayBeforeYesterdayChip.performClick()
                delay(1000)
                val dayBeforeYesterdayToday = bottomSheetTextView.text
                assertNotNull(yesterdayToday.toString(), dayBeforeYesterdayToday)
            }
        }
    }

    @Test
    fun fragmentInputLayout_IsInLayout(): Unit = runBlocking {
        delay(1000)
        scenario.onFragment { fragment ->
            runBlocking {
                val wikiButton =
                    fragment.requireActivity().findViewById<AppCompatImageView>(R.id.wiki_button)
                val inputLayout =
                    fragment.requireActivity().findViewById<TextInputEditText>(R.id.input_edit_text)
                assertTrue(wikiButton.isVisible)

                wikiButton.performClick()
                delay(1000)
                assertTrue(inputLayout.isVisible)
            }
        }
    }
}