package pt.svcdev.movieapp

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityRVTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun recyclerView_PerformClickAtPosition() {
        onView(withId(R.id.list_movies))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<MoviesRVAdapter.Holder>(
                    2,
                    click()
                )
            )
    }

    @Test
    fun recyclerView_PerformClickOnItem() {
        onView(withId(R.id.list_movies))
            .perform(
                RecyclerViewActions.actionOnItem<MoviesRVAdapter.Holder>(
                    hasDescendant(withText("Spider-Man")),
                    click()
                )
            )
    }

    @Test
    fun recyclerView_PerformCustomClick() {
        onView(withId(R.id.list_movies)).perform(
            RecyclerViewActions.scrollToPosition<MoviesRVAdapter.Holder>(25)
        )
        onView(withId(R.id.list_movies))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<MoviesRVAdapter.Holder>(
                    20,
                    tapOnItemById(R.id.img_movie_poster)
                )
            )
    }

    private fun tapOnItemById(id: Int) = object : ViewAction {
        override fun getConstraints(): Matcher<View>? = null

        override fun getDescription(): String = "Click on view with id"

        override fun perform(uiController: UiController?, view: View) {
            val v = view.findViewById(id) as View
            v.performClick()
        }

    }

    @After
    fun close() {
        scenario.close()
    }

}