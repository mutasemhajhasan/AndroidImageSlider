package me.mutasem.imagsliderexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
                0,
                "https://upload.wikimedia.org/wikipedia/commons/b/b8/MasjidNabawi.jpg",
                "Al Masjed annabawi"
            ),
            SliderModel(
                1,
                "https://upload.wikimedia.org/wikipedia/commons/2/2c/Medine.jpg",
                "Al Masjed annabawi"
            ),
            SliderModel(
                2,
                "https://upload.wikimedia.org/wikipedia/commons/a/a9/Al-Masjed_Al-Nabawi_2.JPG",
                "Al Masjed annabawi"
            )
        )
        sliderView.setImages(images)
        //duration in ms
        sliderView.setSlideDuration(3000)
        sliderView.setAutoSlide(true)
    }

}