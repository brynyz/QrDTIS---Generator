package com.example.QrDTIS

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.QrDTIS.databinding.ActivityMainBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.File.separator
import java.io.StringBufferInputStream

private const val TAG = "MainActivity"
private const val QR_SIZE = 1500

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding //access and manipulate view in layout without findViewById() in other words it makes shi easier
    private var selectedPurchases = mutableListOf<String>() //v




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater) //more viewbinding setup
        setContentView(binding.root) //another viewbinding setup so we can finally use it

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets -> //honestly dont know what this does, it came default with the empty activity
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //listeners for the checkboxes
        binding.option1.setOnCheckedChangeListener {_, isChecked ->
            handleCheckBox(binding.option1.text.toString(), isChecked)
        }
        binding.option2.setOnCheckedChangeListener {_, isChecked ->
            handleCheckBox(binding.option2.text.toString(), isChecked)
        }
        binding.option3.setOnCheckedChangeListener {_, isChecked ->
            handleCheckBox(binding.option3.text.toString(), isChecked)
        }
        binding.option4.setOnCheckedChangeListener {_, isChecked ->
            handleCheckBox(binding.option4.text.toString(), isChecked)
        }

        registerUiListener()
    }

    //function to handle checkbox lol
    private fun handleCheckBox(option: String, isChecked: Boolean) {
        if (isChecked) {
            selectedPurchases.add(option) //function of mutable list, concatenates another checked option
        } else{
            selectedPurchases.remove(option) //another function of mutable list
        }
    }

    private fun registerUiListener() {
        binding.generateQrBtn.setOnClickListener { //we set an on click listener to the generate button so it executes the function when we press the button, straight forward
            generateQrCode()
        }
    }


    data class UserInputs(
        val name: String,
        val idNum : String,
        val courseYrSec: String
    ) //we store multiple inputs inside data class


    private fun generateQrCode() {
        val userInput = UserInputs( // we initialize the value of the data class
            name = binding.name.text.toString(),
            idNum = binding.idNum.text.toString(),
            courseYrSec = binding.courseYearSection.text.toString()
        )

        val purchases = selectedPurchases.joinToString(separator = ", ") //storing the list of selected purchase separated by ", "

        val studentData = ":${userInput.name}\n:${userInput.idNum}\n:${userInput.courseYrSec}\n:${purchases}" //we concat the data to store separated by a new line

        Log.d(TAG, "studentData: $studentData", )

        try {
            val encoder = BarcodeEncoder() //function from the "qr-generator" library we installed
            val bitmap = encoder.encodeBitmap(studentData, BarcodeFormat.QR_CODE, QR_SIZE, QR_SIZE) //declaring and encoding the QR code by specifying the data, format and size
            binding.generatedQrImage.setImageBitmap(bitmap) //we give the "generatedQrImage" the image bitmap
        }catch (e: WriterException){
            Log.e(TAG, "generateQrCode: ${e.message}", ) //error logs
        }
    }
}