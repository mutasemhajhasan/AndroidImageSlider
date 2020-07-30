package me.mutasem.imagsliderexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.mutasem.imageslider.SliderModel
import me.mutasem.imageslider.SliderView

class MainActivity : AppCompatActivity() {
    lateinit var sliderView: SliderView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sliderView = findViewById(R.id.slider)
        val images = arrayListOf(
            SliderModel(
                "https://upload.wikimedia.org/wikipedia/commons/b/b8/MasjidNabawi.jpg",
                "Al Masjed annabawi"
            ),
            SliderModel(
                "https://upload.wikimedia.org/wikipedia/commons/2/2c/Medine.jpg",
                "Al Masjed annabawi"
            ),
            SliderModel(
                "https://upload.wikimedia.org/wikipedia/commons/a/a9/Al-Masjed_Al-Nabawi_2.JPG",
                "Al Masjed annabawi"
            )
        )
        sliderView.setImages(images)
        sliderView.setSlideDuration(3000)
        sliderView.setAutoSlide(true)
    }

}