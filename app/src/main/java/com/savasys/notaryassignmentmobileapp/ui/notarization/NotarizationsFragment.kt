package com.savasys.notaryassignmentmobileapp.ui.notarization

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.savasys.notaryassignmentmobileapp.R
import com.savasys.notaryassignmentmobileapp.api.NotarizationsApi
import com.savasys.notaryassignmentmobileapp.data.model.Notarization
import com.savasys.notaryassignmentmobileapp.data.model.NotarizationStatus
import com.savasys.notaryassignmentmobileapp.ui.decorations.DividerItemDecoration
import com.savasys.notaryassignmentmobileapp.ui.notarization.adapters.NotarizationsRecyclerViewAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of Notarizations.
 */
class NotarizationsFragment(notarizations: List<Notarization>) : Fragment(), NotarizationsRecyclerViewAdapter.NotarizationListener {

    private var heading = ""
    private var columnCount = 1
    private var notarizations: List<Notarization> = notarizations
    private val notarizationListener = this

    companion object {

        const val ARG_HEADING = "heading"
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(heading: String, columnCount: Int, notarizations: List<Notarization>) : NotarizationsFragment {
            return NotarizationsFragment(notarizations).apply {
                arguments = Bundle().apply {
                    putString(ARG_HEADING, heading)
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            heading = it.getString(ARG_HEADING)!!
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notarization_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

//                if (heading == resources.getString(R.string.to_do_tab)) {
//                    GlobalScope.launch {
//                        println("Update notarizations for the TO DO tab")
//                        // TODO Get Available Notarizations by Notary.
//                        notarizations =
//                            NotarizationsApi.notarizationsApiService.getNotarizationsByStatus(NotarizationStatus.AVAILABLE)
//                        notarizations[0].fee++
//                        // FIXME android.view.ViewRootImpl$CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views.
//                        adapter?.notifyDataSetChanged()
//                    }
//                }

                adapter = NotarizationsRecyclerViewAdapter(notarizations, notarizationListener)
                view.addItemDecoration(DividerItemDecoration(context))
            }
        }
        return view
    }

    override fun onNotarizationClick(notarization: Notarization) {
        val notarizationFragment = NotarizationFragment.newInstance(notarization)

        // Display fragment.
        val transaction = activity?.supportFragmentManager?.beginTransaction()

        if (transaction != null) {
            transaction.replace(R.id.simpleFrameLayout, notarizationFragment)
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction.addToBackStack(notarizationFragment.tag)
            transaction.commit()
        }
    }
}

