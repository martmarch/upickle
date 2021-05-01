package ujson

import upickle.core.{ObjArrVisitor, Visitor}

/**
 * Basic in-memory string parsing.
 *
 * This is probably the simplest Parser implementation, since there is
 * no UTF-8 decoding, and the data is already fully available.
 *
 * This parser is limited to the maximum string size (~2G). Obviously
 * for large JSON documents it's better to avoid using this parser and
 * go straight from disk, to avoid having to load the whole thing into
 * memory at once. So this limit will probably not be a problem in
 * practice.
 */
private[ujson] final class StringParser[J](s: String) extends CharParser[J]{
  private[this] val sLength = s.length
  override def growBuffer(until: Int): Unit = ()
  def readDataIntoBuffer(buffer: Array[Char], bufferOffset: Int) = {
    if(buffer == null) (s.toCharArray, sLength == 0, sLength)
    else (buffer, true, -1)
  }
  final def close() = ()
}

object StringParser extends Transformer[String]{
  def transform[T](j: String, f: Visitor[_, T]) = new StringParser(j).parse(f)

  override def transformYaml[T](j: String, f: Visitor[_, T]): T = new StringParser(j).parseYaml(f)
}