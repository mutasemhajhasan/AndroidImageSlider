package me.mutasem.imageslider

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso
import java.util.ArrayList

class SlideAdapter(var items: ArrayList<SliderModel>) :
    RecyclerView.Adapter<SlideAdapter.SlideHolder>() {
    private lateinit var _recyclerView: RecyclerView
     val recyclerView: RecyclerView get() = _recyclerView
    private var captionTextSize: Float = 0f

    @ColorInt
    private var captionColor: Int = 0

    @ColorInt
    private var captionBgColor: Int = 0

    class SlideHolder(view: View) : RecyclerView.ViewHolder(view) {
        val slideImage: ImageView = view.findViewById(R.id.slideImage)
        val slideCaption: TextView = view.findViewById(R.id.sliderCaption)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideHolder {
        return SlideHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_slide, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SlideHolder, position: Int) {
        Picasso.get().load(items[position].imageUrl)
            .placeholder(android.R.drawable.progress_indeterminate_horizontal)
//            .error(R.drawable.ic_error)

            .into(holder.slideImage)
        holder.slideCaption.text = items[position].caption
        holder.slideCaption.setTextColor(captionColor)
        holder.slideCaption.setBackgroundColor(captionBgColor)
        if (captionTextSize > 0)
            holder.slideCaption.setTextSize(TypedValue.COMPLEX_UNIT_PX, captionTextSize)
    }

    fun setCaptionAttrs(captionColor: Int, captionBgColor: Int, captionTextSize: Float) {
        this.captionColor = captionColor
        this.captionBgColor = captionBgColor
        this.captionTextSize = captionTextSize
        notifyDataSetChanged()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this._recyclerView = recyclerView
        super.onAttachedToRecyclerView(recyclerView)
    }
}