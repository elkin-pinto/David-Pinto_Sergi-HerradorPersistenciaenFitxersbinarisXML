import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import nl.adaptivity.xmlutil.serialization.XML
import java.io.*
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.io.path.writeText


@Serializable
data class Restaurant(
    val address: Address,
    val borough: String,
    val cuisine: String,
    val grades: List<Grade>,
    val name: String,
    val restaurant_id: String
) : java.io.Serializable

@Serializable
data class Address(
    val building: String,
    val coord: List<Double>,
    val street: String,
    val zipcode: String
) : java.io.Serializable

@Serializable
data class Grade(
    val date: String,
    val mark: String,
    val score: Int
) : java.io.Serializable


fun exercici2() {
    // Leer el archivo JSON
    val jsonString = File("restaurants.json").readLines()

    val llista: MutableList<Restaurant> = mutableListOf()

    jsonString.forEach { jsonLine ->
        val restaurant = Json.decodeFromString<Restaurant>(jsonLine)
        llista.add(restaurant)
    }

    // Escribir los objetos en el archivo binario
    val file = ObjectOutputStream(FileOutputStream("restaurants.dat"))

    // Escribir cada objeto en el archivo
    llista.forEach { restaurant ->
        file.writeObject(restaurant)
    }

    file.close() // Cerrar el flujo de salida

    // Leer los objetos del archivo binario
    val fileInput = ObjectInputStream(FileInputStream("restaurants.dat"))

    // Crear una lista para almacenar los objetos leídos
    val restoredRestaurants = mutableListOf<Restaurant>()

    // Leer y almacenar cada objeto en la lista
    var continuar = true

    try {
        while (continuar) {
            val restaurant = fileInput.readObject() as Restaurant
            restoredRestaurants.add(restaurant)
        }
    } catch (eof: EOFException) {
        continuar = false // Establecer continuar en false cuando se alcanza el final del archivo
    } finally {
        fileInput.close() // Cerrar el flujo de entrada
    }

    // Imprimir los restaurantes restaurados
    restoredRestaurants.forEach { println(it) }
}

fun exercici3() {
    val fileInput = ObjectInputStream(FileInputStream("restaurants.dat"))

    val restaurants = mutableListOf<Restaurant>()

    var continuar = true
    try {
        while (continuar) {
            val obj = fileInput.readObject()
            if (obj is Restaurant) {
                restaurants.add(obj)
            }
        }
    } catch (eof: EOFException) {
       continuar = false // Se alcanzó el final del archivo
    } finally {
        fileInput.close()
    }

    val xmlString = XML.encodeToString(restaurants)
    File("restaurants.xml").writeText(xmlString)
}

fun main() {
    // exercici2()
    exercici3()
}