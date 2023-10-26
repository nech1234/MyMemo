package com.jaewon.mymemo


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.jaewon.mymemo.MemoActivity
import com.jaewon.mymemo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var tempMemo: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.completeBtn.setOnClickListener {
            val intent = Intent(this, MemoActivity::class.java)
            val memoText = binding.memoEt.text.toString()
            intent.putExtra("memo", memoText)
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        val sharedPreferences = getSharedPreferences("memo", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        tempMemo = binding.memoEt.text.toString()
        if (tempMemo.isNotEmpty()) {
            editor.putString("tempMemo", tempMemo)
            editor.apply()
        }
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = getSharedPreferences("memo", MODE_PRIVATE)
        tempMemo = sharedPreferences.getString("tempMemo", null) ?: ""
        if (tempMemo.isNotEmpty()) {
            showConfirmationDialog(tempMemo)
        }
    }

    private fun showConfirmationDialog(tempMemo: String) {
        val dialogView = layoutInflater.inflate(R.layout.dialog, null)
        val positiveBtn = dialogView.findViewById<Button>(R.id.positive)
        val negativeBtn = dialogView.findViewById<Button>(R.id.negative)

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(dialogView)
        val alertDialog = alertDialogBuilder.create()

        positiveBtn.setOnClickListener {
            alertDialog.dismiss()
            binding.memoEt.setText(tempMemo)
            clearCache()
        }
        negativeBtn.setOnClickListener {
            alertDialog.dismiss()
            binding.memoEt.text?.clear()
            clearCache()

        }
        alertDialog.show()
    }
    private fun clearCache() {
        val sharedPreferences = getSharedPreferences("memo", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("tempMemo") // tempMemo 키에 해당하는 값을 삭제
        editor.apply()
    }
}