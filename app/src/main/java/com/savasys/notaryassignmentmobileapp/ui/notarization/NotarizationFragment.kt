package com.savasys.notaryassignmentmobileapp.ui.notarization

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.savasys.notaryassignmentmobileapp.R
import com.savasys.notaryassignmentmobileapp.api.EscrowOfficersApi
import com.savasys.notaryassignmentmobileapp.data.model.EscrowOfficer
import com.savasys.notaryassignmentmobileapp.data.model.Notarization
import com.savasys.notaryassignmentmobileapp.data.model.NotarizationStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Text

/**
 * Notarization Fragment.
 * Use the [NotarizationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotarizationFragment(private var notarization: Notarization) : Fragment() {

    private var escrowOfficer: EscrowOfficer? = null

    private fun getSpinnerIndex(spinner: Spinner, value: String) : Int {
        for (index in 0 until spinner.adapter.count) {
            val itemData = spinner.adapter.getItem(index)
            if (itemData.toString().equals(value, true)) {
                return index
            }
        }

        return DEFAULT_SPINNER_ITEM_INDEX
    }

    private fun setNotarizationInformation(view: View) {
        // Notarization Information
        val escrowNumberTV: TextView = view.findViewById(R.id.escrow_number)
        val escrowOfficerTV: TextView = view.findViewById(R.id.escrow_officer)
        escrowNumberTV.text = notarization.escrowNumber

        GlobalScope.launch {
            // Get Escrow Officer from ID.
            escrowOfficer = EscrowOfficersApi.escrowOfficersApiService.getEscrowOfficer(notarization.escrowOfficerId)

            withContext(Dispatchers.Main) {
                "${escrowOfficer?.name} ${escrowOfficer?.lastName} ${escrowOfficer?.phoneNumber}".also { escrowOfficerTV.text = it }
            }
        }

        val categoryTV: TextView = view.findViewById(R.id.notarization_category)
        categoryTV.text = notarization.category.name

        val powerOfAttorneySignTV: TextView = view.findViewById(R.id.power_attorney_sign)
        when {
            notarization.powerOfAttorneySigning -> {
                powerOfAttorneySignTV.text = getString(R.string.yes_string)
            }
            else -> {
                powerOfAttorneySignTV.text = getString(R.string.no_string)
            }
        }

        val feeTV: TextView = view.findViewById(R.id.notarization_fee)
        "$${notarization.fee}".also { feeTV.text = it }

        val dateTV: TextView = view.findViewById(R.id.notarization_date)
        notarization.date.toString().also { dateTV.text = it }

        val statusTV: TextView = view.findViewById(R.id.status)
        statusTV.text = notarization.status.name

        val deliveryMethodSpinner: Spinner = view.findViewById(R.id.delivery_method)

        context?.let {
            ArrayAdapter.createFromResource(it, R.array.notarization_delivery_type_array, android.R.layout.simple_spinner_item).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                deliveryMethodSpinner.adapter = adapter
            }
        }

        val spinnerIndex = getSpinnerIndex(deliveryMethodSpinner, notarization.deliveryMethod.name)
        deliveryMethodSpinner.setSelection(spinnerIndex)

        // TODO Allow setting Delivery Method.
        // https://developer.android.com/guide/topics/ui/controls/spinner

        val commentsTV: TextView = view.findViewById(R.id.comments)
        commentsTV.text = notarization.comments
    }

    private fun setClientInformation(view: View) {
        // Client Information
        val clientNameTV: TextView = view.findViewById(R.id.client_name)
        val clientName = "${notarization.client.name} ${notarization.client.lastName}"
        clientNameTV.text = clientName

        val clientAddressTV: TextView = view.findViewById(R.id.client_address)
        clientAddressTV.text = notarization.client.address

        val clientPhoneNumberTV: TextView = view.findViewById(R.id.client_phone_number)
        clientPhoneNumberTV.text = notarization.client.phoneNumber

        val clientEmailTV: TextView = view.findViewById(R.id.client_email)
        clientEmailTV.text = notarization.client.email
    }

    private fun confirmNotarizationAcceptance() {
        var alertDialog: AlertDialog.Builder = AlertDialog.Builder(context)
        alertDialog.setTitle("Accept Notarization Dialog")
        alertDialog.setMessage("Do you really want to accept this Notarization?")
        alertDialog.setPositiveButton("Yes") { _, _ ->
            // TODO Send update to Server
            notarization.status = NotarizationStatus.ASSIGNED
            activity?.onBackPressed()
            Toast.makeText(context, "Notarization ${notarization.escrowNumber} has been accepted.", Toast.LENGTH_LONG).show()
        }
        alertDialog.setNegativeButton("No") { _, _ -> }
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notarization, container, false)

        // Handle Accept Button
        val acceptButton: Button = view.findViewById(R.id.accept_notarization_button)
        acceptButton.setOnClickListener {
            confirmNotarizationAcceptance()
        }

        setNotarizationInformation(view)
        setClientInformation(view)

        return view
    }

    companion object {

        const val DEFAULT_SPINNER_ITEM_INDEX = 0

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment NotarizationFragment.
         */
        @JvmStatic
        fun newInstance(notarization: Notarization) =
            NotarizationFragment(notarization).apply {
                arguments = Bundle().apply {

                }
            }
    }
}