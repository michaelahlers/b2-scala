import play.api.libs.json._

case class Foo(s: String, i: Int)

implicit val F: Format[Foo] = Json.format

//Json.toJson(Foo(null, null))
Json.parse("""{"s":null,"i":null}""").as[Foo]