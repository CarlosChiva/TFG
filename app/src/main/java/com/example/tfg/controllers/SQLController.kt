package com.example.tfg.controllers

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.tfg.models.Datos
import com.example.tfg.models.SQLMaker
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class SQLController(context: Context) {
    var context: Context
    var sqlMaker: SQLMaker
    var sqlQueryer: SQLiteDatabase
    val MEDIDA = "medida"
    val FOREIGN_MEDIDA = "foreignMedida"

    init {
        this.context = context
        this.sqlMaker = SQLMaker(context, "db", null, 1, null)
        this.sqlQueryer = sqlMaker.writableDatabase
    }

    private fun transformDate(localDateTime: LocalDateTime): Timestamp {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, localDateTime.year)
        calendar.set(
            Calendar.MONTH,
            localDateTime.monthValue - 1
        ) // Los meses comienzan en cero en Calendar
        calendar.set(Calendar.DAY_OF_MONTH, localDateTime.dayOfMonth)
        calendar.set(Calendar.HOUR_OF_DAY, localDateTime.hour)
        calendar.set(Calendar.MINUTE, localDateTime.minute)
        calendar.set(Calendar.SECOND, localDateTime.second)
        calendar.set(Calendar.MILLISECOND, 0) // Establecer milisegundos en cero
        return Timestamp(calendar.timeInMillis)

    }

    fun insertIntoMedida(datos: Datos) {
        val date = transformDate(datos.fecha)
        sqlQueryer.execSQL("insert into $MEDIDA values('$date',${datos.glucosa},${datos.pick},${datos.pickIcon},${datos.alarma},${datos.CHfood},${datos.food});")
        closeAll()
    }

    fun insertIntofOREIGNMedida(medidas: List<Int>, fecha: LocalDateTime) {
        val date = transformDate(fecha)
        var indice = 1
        medidas.forEach {
            sqlQueryer.execSQL("insert into $FOREIGN_MEDIDA(fecha,id,glucosa) values('$date',$indice,$it);")
            indice++
        }

    }


    fun loadDatesMedida(): MutableList<Datos> {
        val dateFormat = SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.getDefault())
        val result = sqlQueryer.rawQuery("select * from $MEDIDA", null)
        val datos = mutableListOf<Datos>()
        while (result.moveToNext()) {
            val fecha = dateFormat.parse(result.getString(0))
            datos.add(
                Datos(
                    fecha!!.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                    result.getInt(1),
                    result.getInt(2),
                    getBooleans(result.getInt(3)),
                    getBooleans(result.getInt(4)),
                    result.getInt(5),
                    getBooleans(result.getInt(6))
                )
            )
        }
        result.close()
        closeAll()
        return datos


    }

    fun getBooleans(cursor: Int): Boolean {
    return cursor!=0 && cursor!=null
    }

    private fun closeAll() {
        this.sqlQueryer.close()
        this.sqlMaker.close()
    }

}