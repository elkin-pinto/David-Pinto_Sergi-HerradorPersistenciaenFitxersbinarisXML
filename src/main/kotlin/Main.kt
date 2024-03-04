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
fun exercici4(){
    // Path el archivo XML
    val xmlFile = File("restaurants.xml")

    // Leer archivo XML y decode
    val lineasXML = xmlFile.readLines()

    // Pasamos cada linea de XML
    for (linea in lineasXML) {
        val objecte : Restaurant = XML.decodeFromString(linea)

        // Print del nombre de cada restaurante
        println(objecte.name)
    }
}

fun exercici5() {
    // Creamos tres nuevos restaurantes
    val restaurantesNuevos = listOf(
        Restaurant(
            address = Address(
                building = "123",
                coord = listOf(40.712776, -74.005974),
                street = "Example Street 1",
                zipcode = "10001"
            ),
            borough = "Manhattan",
            cuisine = "Italian",
            grades = listOf(
                Grade(date = "2022-01-01", mark = "A", score = 90),
                Grade(date = "2022-02-01", mark = "B", score = 80),
                Grade(date = "2022-03-01", mark = "C", score = 70)
            ),
            name = "Example Restaurant 1",
            restaurant_id = "123456"
        ),
        Restaurant(
            address = Address(
                building = "456",
                coord = listOf(40.7143528, -74.0059731),
                street = "Example Street 2",
                zipcode = "10002"
            ),
            borough = "Brooklyn",
            cuisine = "Mexican",
            grades = listOf(
                Grade(date = "2022-01-01", mark = "A", score = 95),
                Grade(date = "2022-02-01", mark = "A", score = 85),
                Grade(date = "2022-03-01", mark = "B", score = 75)
            ),
            name = "Example Restaurant 2",
            restaurant_id = "456789"
        ),
        Restaurant(
            address = Address(
                building = "789",
                coord = listOf(40.71354, -73.98574),
                street = "Example Street 3",
                zipcode = "10003"
            ),
            borough = "Queens",
            cuisine = "Chinese",
            grades = listOf(
                Grade(date = "2022-01-01", mark = "B", score = 85),
                Grade(date = "2022-02-01", mark = "B", score = 75),
                Grade(date = "2022-03-01", mark = "C", score = 65)
            ),
            name = "Example Restaurant 3",
            restaurant_id = "789012"
        )
    ).forEach(

    )
    // Path del archivo restaurantes.XML
    val xmlFile = File("rastaurants.xml")
    // Pasamos cada objeto restaurante
    for (objecte in restaurantesNuevos) {
        // Serialitzar cada objetos Restaurante a XML
        val lineaXML = XML.encodeToString(restaurantesNuevos) + "\n"
        // Escribir a restaurantes.XML
        xmlFile.writeText(lineaXML)
    }
}


fun main() {
    // exercici2()
    exercici3()
}
