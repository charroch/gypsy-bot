package com.example

import unfiltered.request._
import unfiltered.response._
import com.google.appengine.api.appidentity.AppIdentityServiceFactory
import com.google.api.client.http.json.{JsonHttpRequest, JsonHttpRequestInitializer}
import com.google.api.services.calendar.{CalendarRequest, Calendar}
import com.google.gdata.client.calendar.CalendarService
import java.net.URL
import com.google.gdata.data.calendar.{CalendarEntry, CalendarFeed}

/**unfiltered plan */
class App extends unfiltered.filter.Plan {

  import QParams._

  def intent = {
    case GET(Path("/")) =>
      Ok ~> view(Map.empty)(<p>What say you?</p>)
    case POST(Path(p) & Params(params)) =>
      val vw = view(params) _
      val expected = for {
        int <- lookup("int") is
          int {
            _ + " is not an integer"
          } is
          required("missing int")
        word <- lookup("palindrome") is
          trimmed is
          nonempty("Palindrome is empty") is
          pred(palindrome, _ + " is not a palindrome") is
          required("missing palindrome")
      } yield vw(<p>Yup.
          {int.get}
          is an integer and
          {word.get}
          is a palindrome.</p>)
      expected(params) orFail {
        fails =>
          vw(<ul class="fail">
            {fails.map {
              f => <li>
                {f.error}
              </li>
            }}
          </ul>)
      }
  }

  def palindrome(s: String) = s.toLowerCase.reverse == s.toLowerCase

  def view(params: Map[String, Seq[String]])(body: scala.xml.NodeSeq) = {
    def p(k: String) = params.get(k).flatMap {
      _.headOption
    } getOrElse ("")
    Html(
      <html>
        <head>
            <link rel="stylesheet" type="text/css" href="/css/app.css"/>
          <script type="text/javascript" src="/js/app.js"></script>
        </head>
        <body>
          <div id="container">
            Congradulations. You are running on google's infrastructure.
            {getToken}<hr/>{body}<form method="POST">
            Integer
            <input name="int" value={p("int")}></input>
            Palindrome
              <input name="palindrome" value={p("palindrome")}/>
              <input type="submit"/>
          </form>
          </div>
          <div>{getCalendar}</div>
        </body>
      </html>
    )
  }

  def getToken = {
    import scala.collection.JavaConversions._
    val scopes = "https://www.googleapis.com/auth/urlshortener" :: Nil;
    val appIdentity = AppIdentityServiceFactory.getAppIdentityService();
    val accessToken = appIdentity.getAccessToken(scopes);



    accessToken.getAccessToken + " " + accessToken.getExpirationTime;
  }

  def getCalendar: String = {
    import scala.collection.JavaConversions._

    val myService = new CalendarService("exampleCo-exampleApp-1.0");
    myService.setUserCredentials("bot@novoda.com", "botn1v1da");

    val feedUrl = new URL("http://www.google.com/calendar/feeds/default/allcalendars/full");
    val result = myService.getFeed[CalendarFeed](feedUrl, classOf[CalendarFeed]);

    val entries = result.getEntries()

    entries.foldLeft("Entries (%s): " format entries.size())((a, e) =>
      a + " " + e.getTitle.getPlainText
    )




    //    val resultFeed = myService.getFeed(feedUrl, CalendarFeed.class);
    //
    //    for (int i = 0;      i < resultFeed.getEntries().size();
    //    i ++)
    //    {
    //      CalendarEntry entry = resultFeed.getEntries().get(i);
    //      System.out.println("\t" + entry.getTitle().getPlainText());
    //    }
  }

}
