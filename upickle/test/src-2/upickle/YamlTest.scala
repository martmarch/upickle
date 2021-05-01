package upickle

import upickle.default._
import utest.{TestSuite, Tests, test}
import upickle.default.{ReadWriter => RW, macroRW}
case class Big(name: String, wage: String, position: String)
object Big{
  implicit val rw: RW[Big] = macroRW
}

object YamlTest extends TestSuite {

  val tests = Tests {
    test("yaml") {
      lazy val yamlExample =
        """name: David
          |wage: 1500
          |position: Developer
          |""".stripMargin

      val parsedYaml = ujson.readYaml(yamlExample)
      val big = readYaml[Big](yamlExample)
      println(parsedYaml)
      println(big)
    }
  }
}