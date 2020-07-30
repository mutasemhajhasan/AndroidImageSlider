# AndroidImageSlider
A simple yet effective android image slider

<img src="https://raw.githubusercontent.com/mutasemhajhasan/AndroidImageSlider/master/demo.gif" />

# Usage
## gradle
```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  	dependencies {
	        implementation 'com.github.mutasemhajhasan:AndroidImageSlider:1.0.0'
	}
```
## activity.xml
```xml
    <me.mutasem.imageslider.SliderView
        android:id="@+id/slider"
        app:captionTextSize="16sp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:captionBgColor="#888888"
        app:captionTextColor="#FFFFFF" />
```
## activity.kt
```kotlin
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
	//duration in ms
        sliderView.setSlideDuration(3000)
        sliderView.setAutoSlide(true)
    }
```
