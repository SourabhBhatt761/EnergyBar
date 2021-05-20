package com.srb.energybar


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.srb.energybar.databinding.ItemSeekBarBinding


class SeekBarRecyclerView(private val context : Context) : RecyclerView.Adapter<SeekBarRecyclerView.ViewHolder>() {

    val numberOfSeekBars = mutableListOf(SeekBarClass("1", "100"))

    class ViewHolder(val binding: ItemSeekBarBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        return ViewHolder(
            ItemSeekBarBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = numberOfSeekBars[position]

        holder.binding.mainData = list
        holder.binding.executePendingBindings()

            createNewBar(list, holder.binding, position)


    }

    override fun getItemCount(): Int = numberOfSeekBars.size


    //function to create new seek bar
    private fun createNewBar(list: SeekBarClass, binding: ItemSeekBarBinding, position: Int) {

        binding.seekBar.apply {
            min = list.min.toInt()
            max = list.max.toInt()
            progress = list.max.toInt()
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.rightTv.text = progress.toString()

                if (progress == list.min.toInt()){

                    if (position == 0){
                        binding.leftTv.visibility = View.INVISIBLE
                        binding.rightTv.visibility = View.INVISIBLE
                        binding.leftIv.apply {
                            visibility = View.VISIBLE
                            setBackgroundResource(R.drawable.delete_bg_all)
                        }
                        binding.rightIv.apply {
                            setBackgroundResource(R.drawable.delete_bg_all)
                            visibility = View.VISIBLE
                        }
                    }else {
                        binding.leftTv.visibility = View.GONE
                        binding.rightTv.visibility = View.GONE
                        binding.leftIv.apply {
                            visibility = View.VISIBLE
                            setBackgroundResource(R.drawable.delete_bg)
                        }
                        binding.rightIv.apply {
                            setBackgroundResource(R.drawable.delete_bg)
                            visibility = View.VISIBLE
                        }
                    }
                }else {
                    binding.leftIv.visibility = View.GONE
                    binding.rightIv.visibility = View.GONE
                    binding.leftTv.apply {
                        visibility = View.VISIBLE
                        setBackgroundResource(R.drawable.tv_bg)
                    }
                    binding.rightTv.apply {
                        setBackgroundResource(R.drawable.tv_bg)
                        visibility = View.VISIBLE
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar!!.progress == list.min.toInt()){
                    if (position == 0){

                        numberOfSeekBars.clear()
                        numberOfSeekBars.add(0, SeekBarClass("1","100"))
                        notifyDataSetChanged()
                    }else{
                        numberOfSeekBars[position - 1] =
                            SeekBarClass(numberOfSeekBars[position - 1].min,list.max)
                        numberOfSeekBars.removeAt(position)
                        notifyDataSetChanged()
                    }
                } else if(((list.max.toInt() - seekBar.progress) > 2) ) {
                    val add = SeekBarClass((binding.seekBar.progress + 1).toString(), list.max)
                    numberOfSeekBars.add(position + 1, add)

                    numberOfSeekBars[position] =
                        SeekBarClass(list.min, binding.seekBar.progress.toString())

                    notifyDataSetChanged()
                }else{
                    Toast.makeText(context,"Minimum segment length is 2! ",Toast.LENGTH_SHORT).show()
                    binding.seekBar.progress = list.max.toInt()
                }
            }
        })

    }

}