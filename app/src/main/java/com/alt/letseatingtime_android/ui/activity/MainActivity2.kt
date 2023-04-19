package com.alt.letseatingtime_android.ui.activity

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.alt.letseatingtime_android.R

class MainActivity2 : AppCompatActivity() {
    val TAG: String = "로그"
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        // 생성된 Toast 메시지에 콜백(callback)을 추가합니다. 이 콜백은 Toast 메시지가 화면에서 보여지거나 숨겨지는 시점 등의 이벤트가 발생할 때 호출됩니다.
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).addCallback(object: Toast.Callback() {
            // Toast 메시지가 화면에서 사라질 때 호출되는 콜백
            override fun onToastHidden() {
                super.onToastHidden()
            }
            // onToastShown() : Toast 메시지가 화면에 보여질 때 호출되는 콜백입니다.
            override fun onToastShown() {
                super.onToastShown()
            }
        })

//        DatePickerDialog(this,
//            { p0, p1, p2, p3 -> Log.d(TAG, "year: $p1, month: ${p2 + 1}, day: $p3") }, 2023, 4, 5).show()
//        TimePickerDialog(this,
//            { p0, p1, p2 -> Log.d(TAG, "h: $p1 m: $p2") }, 2023, 4, true).show()
        AlertDialog.Builder(this).run {
            setTitle("title")
            setIcon(R.drawable.btn_submit)
            setMessage("message")
            setPositiveButton("Yes") { dialog, which ->
            }
            setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            show()
            }
        }
    }
}