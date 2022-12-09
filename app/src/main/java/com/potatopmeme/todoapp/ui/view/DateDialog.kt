package com.potatopmeme.todoapp.ui.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.potatopmeme.todoapp.databinding.DialogDatepickerBinding
import com.potatopmeme.todoapp.databinding.DialogTimepickerBinding

class DateDialog(private val context :AppCompatActivity) {
    private lateinit var binding : DialogDatepickerBinding
    private val dlg = Dialog(context)

    private lateinit var listener : DateDialogOKClickedListener

    fun show(){
        binding = DialogDatepickerBinding.inflate(context.layoutInflater)

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(binding.root)
        //dlg.setCancelable(false)
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        binding.tvOk.setOnClickListener {
            listener.onOKClicked(
                "${binding.datePicker.year} / ${binding.datePicker.month+1}  / ${binding.datePicker.dayOfMonth}"
                ,binding.datePicker.year*10000+(binding.datePicker.month+1)*100+binding.datePicker.dayOfMonth
            )
            dlg.dismiss()
        }

        binding.tvCancel.setOnClickListener {
            dlg.dismiss()
        }

        dlg.show()
    }

    fun setOnOKClickedListener(listener: (String,Int) -> Unit) {
        this.listener = object: DateDialogOKClickedListener {
            override fun onOKClicked(dateStr: String,date:Int) {
                listener(dateStr,date)
            }

        }
    }


    interface DateDialogOKClickedListener {
        fun onOKClicked(dateStr: String,date:Int)
    }
}