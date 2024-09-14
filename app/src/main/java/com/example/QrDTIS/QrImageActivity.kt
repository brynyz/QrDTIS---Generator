package com.example.QrDTIS

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.QrDTIS.databinding.ActivityMainBinding
import com.example.QrDTIS.databinding.ActivityQrImageBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.File.separator
import java.io.StringBufferInputStream

private const val QR_SIZE = 1000
private const val TAG = "QrImageActivity"

class QrImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQrImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityQrImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        generateQr()
        goToMain()
    }



    private fun generateQr() {
        //retrieve value from main activity
        val getStudentData = intent.getStringExtra("studentData")
        try {
            val encoder = BarcodeEncoder() //function from the "qr-generator" library we installed
            val bitmap = encoder.encodeBitmap(getStudentData, BarcodeFormat.QR_CODE, QR_SIZE, QR_SIZE) //declaring and encoding the QR code by specifying the data, format and size
            binding.generatedQrImage.setImageBitmap(bitmap) //we give the "generatedQrImage" the image bitmap
        }catch (e: WriterException){
            Log.e(TAG, "generateQrCode: ${e.message}", ) //error logs
        }
    }

    //go to main lol
    private fun goToMain() {
        binding.gotoMain.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}