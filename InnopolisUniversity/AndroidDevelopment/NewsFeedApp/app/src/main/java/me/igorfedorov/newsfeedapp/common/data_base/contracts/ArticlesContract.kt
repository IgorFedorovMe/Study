package me.igorfedorov.newsfeedapp.common.data_base.contracts

object ArticlesContract {

    const val TABLE_NAME = "articles"

    object Columns {
        const val AUTHOR = "author"
        const val CONTENT = "content"
        const val DESCRIPTION = "description"
        const val PUBLISHED_AT = "published_at"
        const val SOURCE = "source"
        const val TITLE = "title"
        const val URL = "url"
        const val URL_TO_IMAGE = "url_to_image"


    }
}