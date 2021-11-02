package me.igorfedorov.newsfeedapp.feature.search_news_screen.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import me.igorfedorov.newsfeedapp.R
import me.igorfedorov.newsfeedapp.base.utils.hideKeyboard
import me.igorfedorov.newsfeedapp.base.utils.setAdapterAndCleanupOnDetachFromWindow
import me.igorfedorov.newsfeedapp.base.utils.setData
import me.igorfedorov.newsfeedapp.base.utils.textChangeFlow
import me.igorfedorov.newsfeedapp.databinding.FragmentSearchNewsScreenBinding
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.ui.adapter.ArticlesAdapter
import me.igorfedorov.newsfeedapp.feature.search_news_screen.ui.utils.SortOrder
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SearchNewsScreenFragment : Fragment(R.layout.fragment_search_news_screen) {

    private val viewModel: SearchNewsScreenViewModel by viewModel()

    private val binding: FragmentSearchNewsScreenBinding by viewBinding(
        FragmentSearchNewsScreenBinding::bind
    )

    private val articlesAdapter: ArticlesAdapter by lazy {
        ArticlesAdapter(
            onItemClickListener = {

            },
            onBookmarkClick = {

            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFlow()

        initAdapter()

        hideKeyboardOnScroll()

        viewModel.viewState.observe(viewLifecycleOwner, ::render)

    }

    private fun render(viewState: ViewState) {
        articlesAdapter.setData(viewState.articles)
    }

    private fun initAdapter() {
        binding.searchNewsRecyclerView.apply {
            setAdapterAndCleanupOnDetachFromWindow(articlesAdapter)
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
    }

    private fun initFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            val searchTextFlow = binding.searchNewsEditText.textChangeFlow().onStart { emit("") }
            val sortOrderFlow = setSpinner().onStart { emit(SortOrder.PUBLISHED_AT.apiName) }
            viewModel.processUiEvent(UIEvent.SearchArticles(searchTextFlow, sortOrderFlow))
        }
    }

    private fun setSpinner(): Flow<String> {
        return callbackFlow {
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.sort_order_array,
                android.R.layout.simple_spinner_item
            ).also { arrayAdapter ->
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.sortOrderSpinner.adapter = arrayAdapter
            }
            val listener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    when (p0?.getItemAtPosition(p2)) {
                        SortOrder.PUBLISHED_AT.spinnerName -> {
                            trySendBlocking(SortOrder.PUBLISHED_AT.apiName)
                        }
                        SortOrder.POPULARITY.spinnerName -> {
                            trySendBlocking(SortOrder.POPULARITY.apiName)
                        }
                        SortOrder.RELEVANCY.spinnerName -> {
                            trySendBlocking(SortOrder.RELEVANCY.apiName)
                        }
                        else -> Timber.d("POS = ${p0?.getItemAtPosition(p2)}")
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
            binding.sortOrderSpinner.onItemSelectedListener = listener
            awaitClose {
                binding.sortOrderSpinner.onItemSelectedListener = null
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun hideKeyboardOnScroll() {
        binding.searchNewsRecyclerView.setOnTouchListener { _, _ ->
            hideKeyboard()
            false
        }
    }
}