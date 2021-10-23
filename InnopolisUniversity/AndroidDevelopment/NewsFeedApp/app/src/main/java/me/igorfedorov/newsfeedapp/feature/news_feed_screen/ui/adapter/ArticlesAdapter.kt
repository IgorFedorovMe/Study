package me.igorfedorov.newsfeedapp.feature.news_feed_screen.ui.adapter

import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import me.igorfedorov.newsfeedapp.R
import me.igorfedorov.newsfeedapp.base.utils.dateFromISO8601
import me.igorfedorov.newsfeedapp.base.utils.setThrottledClickListener
import me.igorfedorov.newsfeedapp.databinding.ItemArticleBinding
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.domain.model.Article

fun articleAdapterDelegate(
    onItemClickListener: (article: Article) -> Unit,
    onBookmarkClick: (article: Article) -> Unit
) =
    adapterDelegateViewBinding<Article, Article, ItemArticleBinding>(
        { layoutInflater, parent -> ItemArticleBinding.inflate(layoutInflater, parent, false) }
    ) {

        binding.root.setThrottledClickListener {
            onItemClickListener(item)
        }

        binding.addToBookmarksIcon.setThrottledClickListener {
            onBookmarkClick(item)
        }

        bind {
            binding.apply {
                authorTextView.text = item.author
                descriptionTextView.text = item.description
                publishedAtTextView.text = item.publishedAt?.dateFromISO8601()
                Glide.with(binding.root).load(item.urlToImage)
                    .centerCrop()
                    .placeholder(R.drawable.news_feed_variant_outline)
                    .into(binding.articleImageImageView)
                addToBookmarksIcon.setImageResource(R.drawable.add_to_bookmarks)
                if (item.isBookmarked) addToBookmarksIcon.setImageResource(R.drawable.remove_from_bookmarks)
            }
        }
    }
