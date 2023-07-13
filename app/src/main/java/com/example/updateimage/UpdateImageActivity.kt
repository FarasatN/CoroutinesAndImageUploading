package com.example.updateimage

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapFactory.decodeFile
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import java.io.IOException


val url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS8_2mqGlQy5cqLeFjXOOT9drGPCsWHNpxdIXIzZa2DQsMCrVyL7sYP-xisktnOPGH2oQw&usqp=CAU"
//val base64String = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAApgAAAKYB3X3/OAAAABl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAAANCSURBVEiJtZZPbBtFFMZ/M7ubXdtdb1xSFyeilBapySVU8h8OoFaooFSqiihIVIpQBKci6KEg9Q6H9kovIHoCIVQJJCKE1ENFjnAgcaSGC6rEnxBwA04Tx43t2FnvDAfjkNibxgHxnWb2e/u992bee7tCa00YFsffekFY+nUzFtjW0LrvjRXrCDIAaPLlW0nHL0SsZtVoaF98mLrx3pdhOqLtYPHChahZcYYO7KvPFxvRl5XPp1sN3adWiD1ZAqD6XYK1b/dvE5IWryTt2udLFedwc1+9kLp+vbbpoDh+6TklxBeAi9TL0taeWpdmZzQDry0AcO+jQ12RyohqqoYoo8RDwJrU+qXkjWtfi8Xxt58BdQuwQs9qC/afLwCw8tnQbqYAPsgxE1S6F3EAIXux2oQFKm0ihMsOF71dHYx+f3NND68ghCu1YIoePPQN1pGRABkJ6Bus96CutRZMydTl+TvuiRW1m3n0eDl0vRPcEysqdXn+jsQPsrHMquGeXEaY4Yk4wxWcY5V/9scqOMOVUFthatyTy8QyqwZ+kDURKoMWxNKr2EeqVKcTNOajqKoBgOE28U4tdQl5p5bwCw7BWquaZSzAPlwjlithJtp3pTImSqQRrb2Z8PHGigD4RZuNX6JYj6wj7O4TFLbCO/Mn/m8R+h6rYSUb3ekokRY6f/YukArN979jcW+V/S8g0eT/N3VN3kTqWbQ428m9/8k0P/1aIhF36PccEl6EhOcAUCrXKZXXWS3XKd2vc/TRBG9O5ELC17MmWubD2nKhUKZa26Ba2+D3P+4/MNCFwg59oWVeYhkzgN/JDR8deKBoD7Y+ljEjGZ0sosXVTvbc6RHirr2reNy1OXd6pJsQ+gqjk8VWFYmHrwBzW/n+uMPFiRwHB2I7ih8ciHFxIkd/3Omk5tCDV1t+2nNu5sxxpDFNx+huNhVT3/zMDz8usXC3ddaHBj1GHj/As08fwTS7Kt1HBTmyN29vdwAw+/wbwLVOJ3uAD1wi/dUH7Qei66PfyuRj4Ik9is+hglfbkbfR3cnZm7chlUWLdwmprtCohX4HUtlOcQjLYCu+fzGJH2QRKvP3UNz8bWk1qMxjGTOMThZ3kvgLI5AzFfo379UAAAAASUVORK5CYII="

class UpdateImageActivity : AppCompatActivity(){

    fun uriToBase64(contentResolver: ContentResolver, imageUri: Uri): String? {
        val inputStream = contentResolver.openInputStream(imageUri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val imageBytes = outputStream.toByteArray()
        return android.util.Base64.encodeToString(imageBytes, android.util.Base64.DEFAULT)
    }

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_image)

        val imageView = findViewById<ImageView>(R.id.imageView)

        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
                try {
                    val bytes = contentResolver.openInputStream(uri)?.readBytes()
                    val base64String = uriToBase64(contentResolver, uri)
                    val bs64 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri)
                    val bytesStr = Base64.encodeToString(bytes, Base64.DEFAULT)
                    val myBitmap = decodeFile(base64String)
//                    val myBitmap = decodeFile(bs64)
                    val stream = ByteArrayOutputStream()
                    myBitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    val byteArrayLocal: ByteArray = stream.toByteArray()
                    myBitmap?.recycle()


                    val decodedString: ByteArray = Base64.decode(bytesStr, Base64.DEFAULT)
                    val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                    val stream2 = ByteArrayOutputStream()
                    decodedByte.compress(Bitmap.CompressFormat.PNG, 5, stream2)
                    val byteArrayLocal2: ByteArray = stream2.toByteArray()
                    decodedByte.recycle()
                    val bytesStr2 = Base64.encodeToString(byteArrayLocal2, Base64.DEFAULT)



                    Glide
                        .with(this)
                        .load(byteArrayLocal2)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .centerCrop()
                        .into(imageView)


                    Log.v("TAGGGG","bytesStr/bytesStr2: ${bytesStr.length} / ${bytesStr2.length}")

                    Log.v("TAGGGG","byteArrayLocal/byteArrayLocal2: ${byteArrayLocal.size} / ${byteArrayLocal2.size}")


                } catch (error: IOException) {
                    error.printStackTrace() // This exception always occurs
                }finally {
                }

            } else {
                Log.v("PhotoPicker", "No media selected")
            }
        }


        imageView.setOnClickListener {
//                    Picasso.get()
//                    .load(url)
//                    .placeholder(R.drawable.ic_launcher_foreground)
//                    .error(R.drawable.ic_launcher_foreground)
//                    .into(imageView)

//            val byteArray = Base64.decode(base64String, Base64.DEFAULT)
//            Glide
//                .with(this)
////            .asBitmap()
//            .load(url)
////                .load(byteArray)
//                .centerCrop()
//                .placeholder(R.drawable.ic_launcher_foreground)
//                .into(imageView)

//            getSharedPreferences("updatedImage", Context.MODE_PRIVATE).edit().putString("url",url).apply()



            val mimeType = "image/*"
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.SingleMimeType(mimeType)))

            Log.v("TAG", "updated")
        }
    }
}