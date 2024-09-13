package com.example.QrDTIS

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.QrDTIS.databinding.ActivityMainBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder

private const val TAG = "MainActivity"
private const val QR_SIZE = 1000
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding //access and manipulate view in layout without findViewById() in other words it makes shi easier



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater) //more viewbind setup
        setContentView(binding.root) //another viewbind setup so we can finally use it

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets -> //honestly dont know what this does, it came default with the empty activity
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        registerUiListener()
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
        val studentData = "${userInput.name}|${userInput.idNum}|${userInput.courseYrSec}" //we concat the data to store separated by a new line

        Log.e(TAG, "studentData: $studentData", )

        try {
            val encoder = BarcodeEncoder() //function from the "qr-generator" library we installed
            val bitmap = encoder.encodeBitmap(studentData, BarcodeFormat.QR_CODE, QR_SIZE, QR_SIZE) //declaring and encoding the QR code by specifying the data, format and size
            binding.generatedQrImage.setImageBitmap(bitmap) //we give the "generatedQrImage" the image bitmap
        }catch (e: WriterException){
            Log.e(TAG, "generateQrCode: ${e.message}", ) //logs
        }
    }


}