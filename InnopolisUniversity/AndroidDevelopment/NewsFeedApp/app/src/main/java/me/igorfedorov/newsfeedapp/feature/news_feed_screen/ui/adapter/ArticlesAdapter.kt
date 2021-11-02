package me.igorfedorov.newsfeedapp.feature.news_feed_screen.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import me.igorfedorov.newsfeedapp.R
import me.igorfedorov.newsfeedapp.base.utils.dateFromISO8601
import me.igorfedorov.newsfeedapp.base.utils.loadImage
import me.igorfedorov.newsfeedapp.base.utils.setThrottledClickListener
import me.igorfedorov.newsfeedapp.databinding.ItemArticleBinding
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.domain.model.Article

class ArticlesAdapter(
    onItemClickListener: (article: Article) -> Unit,
    onBookmarkClick: (article: Article) -> Unit
) :
    ListDelegationAdapter<List<Article>>() {

    init {
        delegatesManager.addDelegate(
            articleAdapterDelegate(
                onItemClickListener,
                onBookmarkClick
            )
        )
    }

    private fun articleAdapterDelegate(
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
                    binding.articleImageImageView.loadImage(item.urlToImage) {
                        centerCrop()
                    }
                    addToBookmarksIcon.setImageResource(R.drawable.add_to_bookmarks)
                    if (item.isBookmarked) addToBookmarksIcon.setImageResource(R.drawable.remove_from_bookmarks)
                }
            }
        }

    class ArticleDiffUtilCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}