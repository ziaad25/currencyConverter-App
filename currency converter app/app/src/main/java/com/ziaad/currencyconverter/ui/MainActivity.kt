package com.ziaad.currencyconverter.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout
import com.ziaad.currencyconverter.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    private val EgyptianPound = "Egyptian Pound"
    private val americanDollar = "American Dollar"
    private val AED = "AED"
    private val GBP = "GBP"

    //  we made the dollar is the base currency and others values depend on it
    var value = mapOf(
        EgyptianPound to 19.14,
        americanDollar to 1.0,
        AED to 3.67,
        GBP to 0.83

    )

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        populationMenue()




        editAmount.addTextChangedListener {
            Result()
        }
        autoCompleteFrom.setOnItemClickListener { adapterView, view, i, l ->
            enableAmount()
            Result()


        }
        autoCompleteTo.setOnItemClickListener { adapterView, view, i, l ->
            enableAmount()
            Result()
        }


    }


    // for add the elements in the dropMenu
    fun populationMenue() {
        var currency = listOf(EgyptianPound, americanDollar, AED, GBP)
        var adapter = ArrayAdapter(this, R.layout.menu_autocomplete_to, currency)
        autoCompleteTo.setAdapter(adapter)
        autoCompleteFrom.setAdapter(adapter)

    }

    fun Result() {
        if (txtInputAmount.NotEmpty() && txtInputFrom.NotEmpty() && txtInputTo.NotEmpty()) {
            var from = value[autoCompleteFrom.text.toString()]
            var to = value[autoCompleteTo.text.toString()]
            var amount = editAmount.text.toString().toDouble()
            var result = amount.times(to!!).div(from!!)
            var resulformated = String.format("%.3f", result)
            editResult.setText(resulformated)
        }
        else{
            editResult.setText(" ")
        }
    }

    fun enableAmount() {
        if (txtInputFrom.NotEmpty() && txtInputTo.NotEmpty()) {
            editAmount.isEnabled = true
        }
    }

    private fun TextInputLayout.NotEmpty(): Boolean {
        return if (this.editText?.text!!.isEmpty()) {
            this.error = "Fill the box"
            false
        } else {
            this.isErrorEnabled = false
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareAction -> {

                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT,
                    " ${editAmount.text.toString()} ${autoCompleteFrom.text.toString()} is equal to ${editResult.text.toString()} ${autoCompleteTo.text.toString()} ")
                startActivity(intent)


            }

        }

        return true
    }

}


