package me.igorfedorov.kinonline.movies_screen_tests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.igorfedorov.kinonline.feature.movies_screen.data.MoviesRepositoryFake
import me.igorfedorov.kinonline.feature.movies_screen.domain.MoviesInteractor
import me.igorfedorov.kinonline.feature.movies_screen.domain.model.Genre
import me.igorfedorov.kinonline.feature.movies_screen.domain.model.Movie
import me.igorfedorov.kinonline.feature.movies_screen.ui.MoviesListViewModel
import me.igorfedorov.kinonline.feature.movies_screen.ui.UIEvent
import me.igorfedorov.kinonline.feature.movies_screen.ui.ViewState
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = CoroutineRule()

    private val viewStateObserver: Observer<ViewState> = mock()

    lateinit var viewModel: MoviesListViewModel
    lateinit var interactor: MoviesInteractor

    @Before
    fun init() {

        interactor = MoviesInteractor(MoviesRepositoryFake())
        val router: Router = mock()

        viewModel = MoviesListViewModel(interactor, router)

        viewModel.viewState.observeForever(viewStateObserver)
    }

    @Test
    fun `Load Movies to ViewState - Successful GetMovies`() {

        // Arrange
        val list = listOf(
            Movie(
                adult = true,
                genre = listOf(Genre("XXX")),
                id = 1,
                originalLanguage = "en",
                originalTitle = "Asses on Fire",
                overview = "Noice",
                popularity = 10.0,
                posterUrl = "AssesOnFirePoster.url",
                releaseDate = "6.9.1969",
                title = "Жопы в огне",
                video = "xxx-asses-on-fire.mp4",
                voteAverage = 10.0,
                voteCount = 1
            ),
            Movie(
                adult = false,
                genre = listOf(Genre("For children")),
                id = 2,
                originalLanguage = "en",
                originalTitle = "Rainbow Unicorn Butterfly Cat",
                overview = "Say whaaaaat",
                popularity = 100.0,
                posterUrl = "RainbowUBCat.url",
                releaseDate = "01.10.2019",
                title = "Радужно-Едиорожная-Бабочковая Кошка",
                video = "rainbow-unicorn-butterfly-cat.mp4",
                voteAverage = 100.0,
                voteCount = 10000
            )
        )

        // Act
        viewModel.processUiEvent(UIEvent.GetMovies)
        val viewState = captureViewState()

        // Assert
        assertEquals(list[0], viewState.movies[0])
        assertEquals(list[1], viewState.movies[1])

    }

    private fun captureViewState(): ViewState = capture {
        verify(viewStateObserver, atLeastOnce()).onChanged(it.capture())

    }

    /* @Test
     fun addition_isCorrect() {
         assertEquals(4, 2 + 2)
     }*/
}

inline fun <reified T : Any> capture(invokeCaptor: (KArgumentCaptor<T>) -> Unit): T {
    val captor = argumentCaptor<T>()
    invokeCaptor(captor)
    return captor.lastValue
}