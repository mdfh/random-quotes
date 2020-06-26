package com.assignment.quote.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.assignment.quote.EventObserver
import com.assignment.quote.R
import com.assignment.quote.databinding.HomeFragmentBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.home_fragment.*
import javax.inject.Inject

/**
 * This is the home screen fragment of the app
 *
 * Created by Faraz on 26/06/20.
 */
class HomeFragment : DaggerFragment() {

    private lateinit var viewDataBinding: HomeFragmentBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<HomeViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.home_fragment, container, false)
        viewDataBinding = HomeFragmentBinding.bind(root).apply {
            this.viewmodel = viewModel
        }
        // Set the lifecycle owner to the lifecycle of the view
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupObservers()
        setupFab()
    }

    private fun setupFab() {
        activity?.findViewById<FloatingActionButton>(R.id.fab_add_task)?.let {
            it.setOnClickListener {
                viewModel.onRefreshQuoteButtonClick()
            }
        }
    }

    private fun setupObservers() {
        viewModel.quote.observe(viewLifecycleOwner, Observer {
            quote.text = it.quote
            author.text = it.author
        })

        viewModel.noInternet.observe(viewLifecycleOwner, EventObserver {
            MaterialDialog(requireContext()).show {
                title(R.string.no_connection)
                message(R.string.please_connect_to_the_internet)
                icon(R.drawable.ic_no_internet)
                positiveButton(R.string.close) { dialog ->
                    dialog.dismiss()
                }
            }
        })

        viewModel.dataError.observe(viewLifecycleOwner, EventObserver {
            MaterialDialog(requireContext()).show {
                title(R.string.error)
                message(R.string.some_error_occurred)
                icon(R.drawable.ic_error)
                positiveButton(R.string.close) { dialog ->
                    dialog.dismiss()
                }
            }
        })

    }
}
