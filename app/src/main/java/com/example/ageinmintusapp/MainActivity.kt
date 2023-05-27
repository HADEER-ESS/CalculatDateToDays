package com.example.ageinmintusapp

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    //make the text private to be Inaccessible by other project classes
    private var datePickerText:TextView? = null
    private var dateInMinutesText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn:Button = findViewById(R.id.btnDatePicker)
        datePickerText = findViewById(R.id.dateTextID)
        dateInMinutesText = findViewById(R.id.DateInMinutesText)
        btn.setOnClickListener {
            showDatePickerFun()
        }
    }
    //Make Date Picker Dialog Appear
    private fun showDatePickerFun(){
        //To use the build-in Calender Dialog
        val datePickerDiagram = java.util.Calendar.getInstance()
        //Those three variables are used in datePicker method
        val year = datePickerDiagram.get(Calendar.YEAR)
        val month = datePickerDiagram.get(Calendar.MONTH)
        val dayOfMonth = datePickerDiagram.get(Calendar.DAY_OF_MONTH)

        val datePickerDialogMethod =
            DatePickerDialog(this ,
//                DatePickerDialog.OnDateSetListener
             { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth+1}/$selectedYear"
                //because datePickerText variable is NULLABLE so we need a "?"
                datePickerText?.text = selectedDate

                //set the format for the date
                val dateFormat = SimpleDateFormat("dd/mm/yyyy" , Locale.ENGLISH)
                 //get the selected date
                val theDate = dateFormat.parse(selectedDate)
                 theDate?.let {
//                     Toast.makeText(this , "The date format Parse $theDate" , Toast.LENGTH_LONG).show()

                     val dateInMinutesTillSelectedDateInMinutes = theDate.time / 60000
                     val dateInMinutesTillSelectedDateInDays = theDate.time / 86400000

                     //get the current date
                     val tillCurrentDateInMillisecond = dateFormat.parse(dateFormat.format(System.currentTimeMillis()))
                     tillCurrentDateInMillisecond?.let {
                         val currentDateInMinutes = tillCurrentDateInMillisecond.time /60000
                         val currentDateInDays = tillCurrentDateInMillisecond.time /86400000

                         val dateDifferentDays = currentDateInDays-dateInMinutesTillSelectedDateInDays //of type long
                         val dateDifferentMinutes = currentDateInMinutes-dateInMinutesTillSelectedDateInMinutes //of type long

//                       Toast.makeText(this , "The date format Parse $dateDifferentMinutes" , Toast.LENGTH_LONG).show()

                         dateInMinutesText?.text = dateDifferentDays.toString()
                     }

                 }
            } ,
                //The Date What The Calendar Will Open From
            year , month , dayOfMonth
        )
        //To prevent user to select a future date
        datePickerDialogMethod.datePicker.maxDate = System.currentTimeMillis() - 86400000
        datePickerDialogMethod.show()
    }
}