/*
 * SPDX-FileCopyrightText: 2024 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.glimpse.models

import android.database.Cursor
import androidx.core.database.getStringOrNull

class ColumnIndexCache(private val cursor: Cursor, projection: Array<String>) {
    private val indexMap = projection.associateWith { cursor.getColumnIndexOrThrow(it) }

    fun getInt(columnName: String) = cursor.getInt(indexMap[columnName]!!)
    fun getLong(columnName: String) = cursor.getLong(indexMap[columnName]!!)
    fun getBoolean(columnName: String) = cursor.getInt(indexMap[columnName]!!) != 0
    fun getString(columnName: String): String = cursor.getString(indexMap[columnName]!!)
    fun getStringOrNull(columnName: String) = cursor.getStringOrNull(indexMap[columnName]!!)
}
