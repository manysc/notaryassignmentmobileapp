package com.savasys.notaryassignmentmobileapp.ui.notarization

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import com.savasys.notaryassignmentmobileapp.R
import com.savasys.notaryassignmentmobileapp.api.NotarizationsApi
import com.savasys.notaryassignmentmobileapp.data.NotarizationsViewModel
import com.savasys.notaryassignmentmobileapp.data.model.Notarization
import com.savasys.notaryassignmentmobileapp.data.model.NotarizationStatus
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NotarizationsActivity : AppCompatActivity() {

    private lateinit var fragment: NotarizationsFragment
    private lateinit var transaction: FragmentTransaction

    private fun displayFragment(tabText: String, notarizations: List<Notarization>) {
        fragment = NotarizationsFragment.newInstance(
            tabText,
            1,
            notarizations
        )

        // Display fragment.
        transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.simpleFrameLayout, fragment)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_notarizations)

//        GlobalScope.launch {
//            // TODO Get Available Notarizations by Notary by default.
//            val notarizations = NotarizationsApi.notarizationsApiService.getNotarizationsByStatus(NotarizationStatus.AVAILABLE)
//
//            // Display "To Do" fragment.
//            displayFragment(resources.getString(R.string.to_do_tab), notarizations)
//        }

        val model: NotarizationsViewModel by viewModels()
        model.getAvailableNotarizations().observe(this, { notarizations ->
            // Display "To Do" fragment.
            displayFragment(resources.getString(R.string.to_do_tab), notarizations)
        })

        model.getInProgressNotarizations().observe(this, { notarizations ->
            // Display "In Progress" fragment.
            displayFragment(resources.getString(R.string.in_progress_tab), notarizations)
        })

        GlobalScope.launch {
            // TODO Get Available Notarizations by Notary by default.
            val notarizations = NotarizationsApi.notarizationsApiService.getNotarizationsByStatus(NotarizationStatus.AVAILABLE)
            model.availableNotarizations.postValue(notarizations)
        }

        // Add select listeners to Tabs
        val tabs = findViewById<TabLayout>(R.id.tabs)
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.text) {
                    resources.getString(R.string.to_do_tab) -> {
                        GlobalScope.launch {
                            // TODO Get Available Notarizations by Notary.
                            val notarizations =
                                NotarizationsApi.notarizationsApiService.getNotarizationsByStatus(NotarizationStatus.AVAILABLE)
                            model.availableNotarizations.postValue(notarizations)

//                            displayFragment(tab.text as String, notarizations)
                        }
                    }

                    resources.getString(R.string.in_progress_tab) -> {
                        GlobalScope.launch {
                            // TODO Get In Progress Notarizations by Notary.
                            val notarizations = NotarizationsApi.notarizationsApiService.getInProgressNotarizations()
                            model.inProgressNotarizations.postValue(notarizations)
//                            displayFragment(tab.text as String, notarizations)
                        }
                    }

                    resources.getString(R.string.done_tab) -> {
                        GlobalScope.launch {
                            // TODO Get Done Notarizations by Notary.
                            val notarizations =
                                NotarizationsApi.notarizationsApiService.getInactiveNotarizations()
                            displayFragment(tab.text as String, notarizations)
                        }
                    }
                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) { }

            override fun onTabReselected(p0: TabLayout.Tab?) { }
        })
    }
}