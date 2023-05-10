import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader

fun loadJsonFromRaw(context: Context, resourceId: Int): String {
    val inputStream = context.resources.openRawResource(resourceId)
    val reader = BufferedReader(InputStreamReader(inputStream))
    val jsonString = StringBuilder()
    var line: String?
    while (reader.readLine().also { line = it } != null) {
        jsonString.append(line)
    }
    reader.close()
    return jsonString.toString()
}

inline fun <reified T> Gson.fromJson(json: String) =
    this.fromJson<T>(json, object : TypeToken<T>() {}.type)
