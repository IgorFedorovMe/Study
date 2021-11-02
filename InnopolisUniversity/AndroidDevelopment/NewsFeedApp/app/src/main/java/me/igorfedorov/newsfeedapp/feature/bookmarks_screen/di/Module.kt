package me.igorfedorov.newsfeedapp.feature.bookmarks_screen.di

import me.igorfedorov.newsfeedapp.feature.bookmarks_screen.domain.BookmarksInteractor
import me.igorfedorov.newsfeedapp.feature.bookmarks_screen.ui.BookmarksScreenViewModel
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.NewsRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val bookmarksModule = module {

    single<BookmarksInteractor> {
        BookmarksInteractor(newsRepository = get<NewsRepository>())
    }

    viewModel<BookmarksScreenViewModel> {
        BookmarksScreenViewModel(get<BookmarksInteractor>())
    }

}