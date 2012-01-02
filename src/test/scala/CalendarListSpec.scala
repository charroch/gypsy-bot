import com.google.api.client.json.CustomizeJsonParser
import com.google.api.client.json.jackson.JacksonFactory
import com.google.api.services.calendar.model.CalendarList
import org.specs2.mutable._

class CalendarSpec extends Specification {

  val jsonFactory = new JacksonFactory

  "A calendar" should {
    "validate presence of a particular description" in {
      val json = """{"items":[{"description":"description","id":"someId@google.com"}]}"""
      val parser = jsonFactory.createJsonParser(json)
      val calendarList = parser.parse(classOf[CalendarList], new CustomizeJsonParser)
      
      val calendar = Calendar("Adfonic")
      // calendar.create unless calendar.exists?

      "Hello world" must have size (11)
    }
  }

 // Today I worked on Adfonic
  // findOrCreateByDescription("Adfonic");
}