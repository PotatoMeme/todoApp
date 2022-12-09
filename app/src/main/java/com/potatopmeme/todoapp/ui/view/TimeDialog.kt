package com.potatopmeme.todoapp.ui.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.potatopmeme.todoapp.databinding.DialogTimepickerBinding

class TimeDialog(private val context :AppCompatActivity,private val time: String = "")  {
    private lateinit var binding :DialogTimepickerBinding
    private val dlg = Dialog(context)

    private lateinit var listener : TimeDialogOKClickedListener

    fun show(){
        binding = DialogTimepickerBinding.inflate(context.layoutInflater)

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(binding.root)
        //dlg.setCancelable(false)
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (!time.isNullOrEmpty()){
            val datas = time.split(" : ")
            binding.timePicker.hour = datas[0].toInt()
            binding.timePicker.minute = datas[1].toInt()
        }
        binding.tvOk.setOnClickListener {
            listener.onOKClicked("${binding.timePicker.hour} : ${binding.timePicker.minute}")
            dlg.dismiss()
        }

        binding.tvCancel.setOnClickListener {
            dlg.dismiss()
        }

        dlg.show()
    }

    fun setOnOKClickedListener(listener: (String) -> Unit) {
        this.listener = object: TimeDialogOKClickedListener {
            override fun onOKClicked(content: String) {
                listener(content)
            }
        }
    }


    interface TimeDialogOKClickedListener {
        fun onOKClicked(content : String)
    }
}