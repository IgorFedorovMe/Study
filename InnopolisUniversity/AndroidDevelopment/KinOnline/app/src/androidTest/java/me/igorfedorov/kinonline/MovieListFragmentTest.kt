package me.igorfedorov.kinonline

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MovieListFragmentTest {

    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @Test
    fun `Test-Is-MoviesListFragment-Visible-On-AppLaunch`() {
        onView(withId(R.id.moviesListRecyclerView)).check(matches(isDisplayed()))
    }

    /*@Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("me.igorfedorov.kinonline", appContext.packageName)
    }*/

    /* @Test
     fun openMovieInfo() {
         onView()
     }*/
}