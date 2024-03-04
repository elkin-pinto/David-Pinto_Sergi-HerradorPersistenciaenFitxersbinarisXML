import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.*

@Serializable
data class Restaurant(
    val address: Address,
    val borough: String,
    val cuisine: String,
    val grades: List<Grade>,
    val name: String,
    val restaurant_id: String
)

@Serializable
data class Address(
    val building: String,
    val coord: List<Double>,
    val street: String,
    val zipcode: String
)

@Serializable
data class Grade(
    val date: String,
    val mark: String,
    val score: Int
)


fun exercici2(){
    // Leer el archivo JSON
    val jsonString = File("restaurants.json").readLines() // nos devuelve todas las lineas como string

    val llista: MutableList<Restaurant> = mutableListOf()

    jsonString.forEach { jsonLine ->
        val restaurant = Json.decodeFromString<Restaurant>(jsonLine) // Lo deserializamos a restaurant
        llista.add(restaurant) // añadimos a la lista de restaurants aquest objecte
    }

    // Escribir los objetos en el archivo binario
    val file = ObjectOutputStream(FileOutputStream("restaurants.dat"))

    file.writeObject(llista.joinToString("\n"))

    // Leer los objetos del archivo binario (solo para demostración)
    val fileInput = ObjectInputStream(FileInputStream("restaurants.dat"))
    val restoredRestaurants = fileInput.readObject()

    // Imprimir los restaurantes restaurados (solo para demonstration)
    println(restoredRestaurants)
}

fun main() {

}
