import com.google.api.services.calendar.model.{Calendar => CalendarModel}

class Calendar(description: String) {
}

object Calendar {
  def apply(s: String) = new Calendar(s);

  def get(summary:String):CalendarModel = {
  }
}