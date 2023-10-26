package com.jaewon.mymemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jaewon.mymemo.databinding.ActivityMemoBinding

class MemoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMemoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(intent.hasExtra("memo")){
            binding.memoText.text = intent.getStringExtra("memo")!!
        }
    }
}