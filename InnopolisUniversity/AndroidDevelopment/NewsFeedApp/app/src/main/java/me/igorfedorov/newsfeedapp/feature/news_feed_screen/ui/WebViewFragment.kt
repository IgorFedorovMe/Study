package me.igorfedorov.newsfeedapp.feature.news_feed_screen.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import me.igorfedorov.newsfeedapp.R
import me.igorfedorov.newsfeedapp.databinding.FragmentWebViewBinding
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.di.MAIN_SCREEN_VIEW_MODEL
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.qualifier.named

class WebViewFragment : Fragment(R.layout.fragment_web_view) {

    private val viewModel: NewsFeedScreenViewModel by sharedViewModel(
        qualifier = named(
            MAIN_SCREEN_VIEW_MODEL
        )
    )

    private val binding: FragmentWebViewBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.progressBarWebView.isVisible = true
            }

            override fun onPageCommitVisible(view: WebView?, url: String?) {
                super.onPageCommitVisible(view, url)
                binding.progressBarWebView.isVisible = false
            }
        }
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(navArgs<WebViewFragmentArgs>().value.articleURL)


//        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
//            findNavController().navigate(WebViewFragmentDirections.actionWebViewFragmentToNewsFeedScreenFragment())
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    }

    override fun onStop() {
        viewModel.closeArticleWebView()
        super.onStop()
    }


}