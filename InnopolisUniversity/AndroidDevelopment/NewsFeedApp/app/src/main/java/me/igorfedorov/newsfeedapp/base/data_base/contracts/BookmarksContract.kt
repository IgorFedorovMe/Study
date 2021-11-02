package me.igorfedorov.newsfeedapp.base.data_base.contracts

object BookmarksContract {

    const val TABLE_NAME = "bookmarks"

    object Columns {
        const val AUTHOR = "author"
        const val CONTENT = "content"
        const val DESCRIPTION = "description"
        const val PUBLISHED_AT = "published_at"
        const val TITLE = "title"
        const val URL = "url"
        const val URL_TO_IMAGE = "url_to_image"
        const val IS_BOOKMARKED = "is_bookmarked"


    }
}