package com.orxeira.tmdb_browser

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.orxeira.tmdb_browser.ui.MockWebServerRule
import com.orxeira.tmdb_browser.ui.NavHostActivity
import com.orxeira.tmdb_browser.ui.OkHttp3IdlingResource
import com.orxeira.tmdb_browser.ui.fromJson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainInstrumentationTest {

    @get:Rule(order = 0)
    val mockWebServerRule = MockWebServerRule()

    @get:Rule(order = 1)
    val activityRule = ActivityScenarioRule(NavHostActivity::class.java)

    @Before
    fun setup(){
        initTestDI()
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson("popular_tv_shows_1.json")
        )

        val okHttpClient = HttpLoggingInterceptor().run {
            level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder().addInterceptor(this).build()
        }

        val resource = OkHttp3IdlingResource.create("OkHttp", okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }

    @Test
    fun click_a_tvShow_navigates_to_detail(){
        Thread.sleep(3000)
        Espresso.onView(withId(R.id.recycler))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    4, ViewActions.click()
                )
            )

        Espresso.onView(withId(R.id.details_title))
            .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText("Arcane"))))
    }
}