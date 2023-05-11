import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.google.gson.Gson

class MyDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "mydatabase.db"
        private const val DATABASE_VERSION = 1

        // Define your table and column names here
        private const val TABLE_NAME = "mytable"
        private const val COLUMN_ID = "id"
        private const val COLUMN_ENGLISH = "english"
        private const val COLUMN_ROMANJI = "romanji"
        private const val COLUMN_HIRAGANA = "hiragana"
        private const val COLUMN_KATAKANA = "katakana"
        private const val COLUMN_KANJI = "kanji"
        private const val COLUMN_MOST_COMMON_FORM = "most_common_form"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Create the table
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY,
                $COLUMN_ENGLISH TEXT,
                $COLUMN_ROMANJI TEXT,
                $COLUMN_HIRAGANA TEXT,
                $COLUMN_KATAKANA TEXT,
                $COLUMN_KANJI TEXT,
                $COLUMN_MOST_COMMON_FORM TEXT
            )
        """
        db?.execSQL(createTableQuery)

        // Insert data into the table
        val insertDataQuery = """
            INSERT INTO $TABLE_NAME ($COLUMN_ID, $COLUMN_ENGLISH, $COLUMN_ROMANJI, $COLUMN_HIRAGANA, $COLUMN_KATAKANA, $COLUMN_KANJI, $COLUMN_MOST_COMMON_FORM)
            VALUES
                (1, 'Hello', 'Konnichiwa', 'こんにちは', 'コンニチハ', '今日は', 'Konnichiwa'),
                (2, 'Goodbye', 'Sayonara', 'さようなら', 'サヨウナラ', 'さようなら', 'Sayonara'),
                (3, "Thank you", "Arigato", "ありがとう", "アリガトウ", "ありがとうございます", "Arigato"),
                (4, "Yes", "Hai", "はい", "ハイ", "はい", "Hai"),
                (5, "No", "Iie", "いいえ", "イイエ", "いいえ", "Iie")
        """
        db?.execSQL(insertDataQuery)
    }

    fun getAllTableValues(): List<MyTableData> {
        val dataList = mutableListOf<MyTableData>()

        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = readableDatabase
        val cursor: Cursor? = db.rawQuery(selectQuery, null)

        cursor?.use {
            val idIndex = it.getColumnIndexOrThrow(COLUMN_ID)
            val englishIndex = it.getColumnIndexOrThrow(COLUMN_ENGLISH)
            val romanjiIndex = it.getColumnIndexOrThrow(COLUMN_ROMANJI)
            val hiraganaIndex = it.getColumnIndexOrThrow(COLUMN_HIRAGANA)
            val katakanaIndex = it.getColumnIndexOrThrow(COLUMN_KATAKANA)
            val kanjiIndex = it.getColumnIndexOrThrow(COLUMN_KANJI)
            val mostCommonFormIndex = it.getColumnIndexOrThrow(COLUMN_MOST_COMMON_FORM)

            while (it.moveToNext()) {
                val id = it.getInt(idIndex)
                val english = it.getString(englishIndex)
                val romanji = it.getString(romanjiIndex)
                val hiragana = it.getString(hiraganaIndex)
                val katakana = it.getString(katakanaIndex)
                val kanji = it.getString(kanjiIndex)
                val mostCommonForm = it.getString(mostCommonFormIndex)

                val data = MyTableData(id, english, romanji, hiragana, katakana, kanji, mostCommonForm)
                dataList.add(data)
            }
        }

        cursor?.close()
        db.close()

        return dataList
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Perform necessary actions when upgrading the database
    }

    fun importJsonDataFromRaw(context: Context, resourceId: Int) {
        val jsonString = loadJsonFromRaw(context, resourceId)
        val dataList: List<MyTableData> = Gson().fromJson(jsonString)

        val db = writableDatabase
        db.beginTransaction()

        try {
            for (data in dataList) {
                val values = ContentValues().apply {
                    put(COLUMN_ID, data.id)
                    put(COLUMN_ENGLISH, data.english)
                    put(COLUMN_ROMANJI, data.romanji)
                    put(COLUMN_HIRAGANA, data.hiragana)
                    put(COLUMN_KATAKANA, data.katakana)
                    put(COLUMN_KANJI, data.kanji)
                    put(COLUMN_MOST_COMMON_FORM, data.mostCommonForm)
                }

                db.insert(TABLE_NAME, null, values)
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }

        db.close()
    }
}

data class MyTableData(
    val id: Int,
    val english: String,
    val romanji: String,
    val hiragana: String,
    val katakana: String,
    val kanji: String,
    val mostCommonForm: String
)
