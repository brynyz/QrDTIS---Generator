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
import java.io.StringBufferInputStream

private const val TAG = "MainActivity"
private const val QR_SIZE = 1500

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding //access and manipulate view in layout without findViewById() in other words it makes shi easier
    private var selectedPurchase: String? = null //class level variable to access the text inside the radio group listener




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

        //listener for the radio group
        binding.purchaseOptions.setOnCheckedChangeListener{ group, checkedId -> //lambda function

            //Log.d(TAG, "Checked ID: $checkedId") //more logs still cant fix non null
            //Log.d(TAG, "Option1 ID: ${R.id.option1}")
            //Log.d(TAG, "Option2 ID: ${R.id.option2}")
            //Log.d(TAG, "Option3 ID: ${R.id.option3}")

            val selectedOption = when (checkedId){ //'when' has similar logic to switch cases
                R.id.option1 -> binding.option1
                R.id.option2 -> binding.option2
                R.id.option3 -> binding.option3
                else -> null
            }

            selectedOption?.let { //the value will be passed only if a radio group is checked
                selectedPurchase = it.text.toString() //found the issue, I declared a new variable instead of using the global var
            }
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

        val studentData = "${userInput.name}|${userInput.idNum}|${userInput.courseYrSec}|${selectedPurchase}" //we concat the data to store separated by a new line

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