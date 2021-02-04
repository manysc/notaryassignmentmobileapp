package com.savasys.notaryassignmentmobileapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.savasys.notaryassignmentmobileapp.api.NotarizationsApi
import com.savasys.notaryassignmentmobileapp.data.model.Notarization
import com.savasys.notaryassignmentmobileapp.data.model.NotarizationStatus
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NotarizationsViewModel : ViewModel() {

    private fun loadNotarizations(notarizationStatus: NotarizationStatus) {
        GlobalScope.launch {
            availableNotarizations.also { NotarizationsApi.notarizationsApiService.getNotarizationsByStatus(notarizationStatus) }
        }
    }

    private fun loadInProgressNotarizations() {
        GlobalScope.launch {
            // TODO Get In Progress Notarizations by Notary.
            inProgressNotarizations.also { NotarizationsApi.notarizationsApiService.getInProgressNotarizations() }
        }
    }

    val availableNotarizations: MutableLiveData<List<Notarization>> by lazy {
        MutableLiveData<List<Notarization>>().also {
            loadNotarizations(NotarizationStatus.AVAILABLE)
        }
    }

    val inProgressNotarizations: MutableLiveData<List<Notarization>> by lazy {
        MutableLiveData<List<Notarization>>().also {
            loadNotarizations(NotarizationStatus.AVAILABLE)
        }
    }

    fun getAvailableNotarizations(): LiveData<List<Notarization>> {
        return availableNotarizations
    }

    fun getInProgressNotarizations(): LiveData<List<Notarization>> {
        return inProgressNotarizations
    }
}
