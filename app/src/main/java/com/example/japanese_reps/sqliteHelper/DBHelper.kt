package com.example.japanese_reps.sqliteHelper

import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

fun createAndPopulateDatabase() {
    // Step 1: Connect to the database
    val connection: Connection = DriverManager.getConnection("jdbc:sqlite:mydatabase.db")

    // Step 2: Create the table
    val statement: Statement = connection.createStatement()
    val createTableQuery = """
        CREATE TABLE IF NOT EXISTS mytable (
            id INTEGER PRIMARY KEY,
            english TEXT,
            romanji TEXT,
            hiragana TEXT,
            katakana TEXT,
            kanji TEXT,
            most_common_form TEXT
        )
    """
    statement.execute(createTableQuery)

    // Step 3: Insert data into the table
    val insertDataQuery = """
        INSERT INTO mytable (id, english, romanji, hiragana, katakana, kanji, most_common_form)
        VALUES
            (1, 'Hello', 'Konnichiwa', 'こんにちは', 'コンニチハ', '今日は', 'Konnichiwa'),
            (2, 'Goodbye', 'Sayonara', 'さようなら', 'サヨウナラ', 'さようなら', 'Sayonara')
    """
    statement.execute(insertDataQuery)

    // Step 4: Close the database connection
    statement.close()
    connection.close()
}
