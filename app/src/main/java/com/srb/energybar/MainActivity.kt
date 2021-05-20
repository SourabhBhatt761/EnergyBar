package com.srb.energybar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.srb.energybar.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(){

    private lateinit var _binding : ActivityMainBinding
    private val binding get() = _binding

    private lateinit var mAdapter: SeekBarRecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        mAdapter = SeekBarRecyclerView(this)
        binding.seekRv.adapter = mAdapter
        binding.seekRv.layoutManager = LinearLayoutManager(this)

    }




}