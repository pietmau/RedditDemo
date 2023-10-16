package com.pietrantuono.redditdemo

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.pietrantuono.redditdemo.navigation.DETAIL
import com.pietrantuono.redditdemo.navigation.ID
import com.pietrantuono.redditdemo.navigation.POSTS
import com.pietrantuono.redditdemo.navigation.RedditNavHost
import com.pietrantuono.redditdemo.navigation.navigateTo
import com.pietrantuono.redditdemo.ui.AllPosts
import com.pietrantuono.redditdemo.ui.PostsDetail
import com.pietrantuono.redditdemo.ui.theme.RedditDemoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.detector.Detection
import org.tensorflow.lite.task.vision.detector.ObjectDetector

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RedditDemoTheme {
                RedditNavHost { controller ->
                    composable(route = POSTS) {
                        AllPosts { destination ->
                            controller.navigateTo(destination)
                        }
                    }
                    composable(
                        route = "$DETAIL/{$ID}",
                        arguments = listOf(
                            navArgument(ID) {
                                type = NavType.StringType
                            }
                        )
                    ) { backStackEntry ->
                        val postId = backStackEntry.arguments?.getString(ID) ?: return@composable
                        PostsDetail(postId)
                    }
                }
            }
        }

//        val options = TfLiteInitializationOptions.builder().setEnableGpuDelegateSupport(true).build()
//        TfLiteVision.initialize(this, options).addOnSuccessListener {
//            extracted()
//        }.addOnFailureListener {
//            TfLiteVision.initialize(this).addOnSuccessListener {
//                extracted()
//            }.addOnFailureListener {
//                Log.e("foo", "Failed to initialize the TFLite interpreter: ${it.message}")
//            }
//        }
//        val modelName = "mobilenetv1.tflite"
        extracted()
    }

    private fun extracted() {
        lifecycleScope.launch {
            val loader = ImageLoader(this@MainActivity)
            val request = ImageRequest.Builder(this@MainActivity)
                .data("https://images.dog.ceo/breeds/saluki/n02091831_3400.jpg")
                .allowHardware(false) // Disable hardware bitmaps.
                .build()

            val result = (loader.execute(request) as SuccessResult).drawable
            val bitmap = (result as BitmapDrawable).bitmap
            val image = TensorImage.fromBitmap(bitmap)

            val options = ObjectDetector.ObjectDetectorOptions.builder()
                .setMaxResults(5)
                .setScoreThreshold(0.5f)
                .build()
            val detector = ObjectDetector.createFromFileAndOptions(
                this@MainActivity.applicationContext, // the application context
                "mobilenetv1.tflite", // must be same as the filename in assets folder
                options
            )
            val results = detector.detect(image)
            debugPrint(results)
        }
    }
}

private fun debugPrint(results: List<Detection>) {
    val TAG = "foo"

    for ((i, obj) in results.withIndex()) {
        val box = obj.boundingBox

        Log.e(TAG, "Detected object: $i ")
        Log.e(TAG, "  boundingBox: (${box.left}, ${box.top}) - (${box.right},${box.bottom})")

        for ((j, category) in obj.categories.withIndex()) {
            Log.e(TAG, "    Label $j: ${category.label}")
            val confidence: Int = category.score.times(100).toInt()
            Log.e(TAG, "    Confidence: $confidence%")
        }
    }
}
