import com.google.api.client.googleapis.services.GoogleClient
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.model.{CalendarListEntry, Calendar => CalendarModel}
import com.google.gdata.data.calendar.CalendarEntry

//class Calendar(description: String) {
//}
//
//object Calendar {
//  def apply(s: String) = new Calendar(s)
//
////  def get(summary: String): CalendarModel = {
////  }
//}


trait CalendarServiceComponent {

  val calendar: Calendar

  class CalendarRepository {
    def create(calendar: Calendar) = println("creating user: " + calendar)

    def delete(calendar: Calendar) = println("deleting user: " + calendar)

    def search(summary: String): Option[CalendarListEntry] = {
      calendar.calendarList().list.execute().getItems().find {
        a: CalendarListEntry => a.getSummary.toLowerCase == summary.toLowerCase
      }
    }

    def get(summary: String): CalendarListEntry = {
      search(summary).getOrElse(

      )
    }

    def create(summary: String): Calendar = {
      val cal = new CalendarModel
      cal.setDescription("A calendar about " + summary)
      cal.setSummary(summary)
      calendar.calendars().insert(cal).execute
    }

    def delete(id:String) {

    }
  }

}

trait GoogleService {

  val client: GoogleClient

  def execute {
  }
}
