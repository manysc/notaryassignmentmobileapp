package com.savasys.notaryassignmentmobileapp.ui.notarization.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.savasys.notaryassignmentmobileapp.R
import com.savasys.notaryassignmentmobileapp.data.model.Notarization
import com.savasys.notaryassignmentmobileapp.utils.DateUtils

/**
 * [RecyclerView.Adapter] that can display a [Notarization].
 */
class NotarizationsRecyclerViewAdapter(
    private var notarizations: List<Notarization>, notarizationListener: NotarizationListener
) : RecyclerView.Adapter<NotarizationsRecyclerViewAdapter.ViewHolder>() {

    private val notarizationListener = notarizationListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_notarizations, parent, false)

        return ViewHolder(view, notarizationListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notarization = notarizations[position]

        val feeToString = "$${notarization.fee.toInt()}"
        holder.fee.text = feeToString

        val dateToString = DateUtils.getDateTimeFromLocalDateFormat(notarization.date)
        holder.date.text = dateToString

        holder.address.text = notarization.client.address
    }

    override fun getItemCount(): Int = notarizations.size

    inner class ViewHolder(view: View, private val notarizationListener: NotarizationListener) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val fee: TextView = view.findViewById(R.id.fee)
        val date: TextView = view.findViewById(R.id.date)
        val address: TextView = view.findViewById(R.id.address)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            notarizationListener.onNotarizationClick(notarizations[adapterPosition])
        }

        override fun toString(): String {
            return super.toString() + "fee:${fee.text} date:${date.text} address:${address.text}"
        }
    }

    interface NotarizationListener {

        fun onNotarizationClick(notarization: Notarization)
    }
}