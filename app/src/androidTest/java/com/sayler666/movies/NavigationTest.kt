package com.sayler666.movies

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.sayler666.movies.di.RepositoryModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
@UninstallModules(RepositoryModule::class)
@HiltAndroidTest
class NavigationTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun topRatedNavigateToDetailsItem0() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.movie_recycler_view))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withText("Title1")).check(matches(isDisplayed()))
        onView(withId(R.id.poster)).check(matches(isDisplayed()))
        onView(withText("overview1")).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun topRatedNavigateToDetailsItem1() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.movie_recycler_view))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))

        onView(withText("Title2")).check(matches(isDisplayed()))
        onView(withId(R.id.poster)).check(matches(isDisplayed()))
        onView(withText("overview2")).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun detailsUpButtonNavigateToTopRated() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.movie_recycler_view))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withContentDescription("Navigate up")).perform(click())

        onView(withId(R.id.movie_recycler_view)).check(matches(isDisplayed()))

        activityScenario.close()
    }
}