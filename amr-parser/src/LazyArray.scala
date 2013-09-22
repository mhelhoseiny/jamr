package edu.cmu.lti.nlp.amr

import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream
import java.io.BufferedOutputStream
import java.io.OutputStreamWriter
import java.lang.Math.abs
import java.lang.Math.log
import java.lang.Math.exp
import java.lang.Math.random
import java.lang.Math.floor
import java.lang.Math.min
import java.lang.Math.max
import scala.io.Source
import scala.util.matching.Regex
import scala.collection.mutable.Map
import scala.collection.mutable.Set
import scala.collection.mutable.ArrayBuffer
import scala.util.parsing.combinator._

/****************************** Lazy Array *****************************/

class LazyArray[T](iterator : Iterator[T]) {
    private var buffer = ArrayBuffer[T]()
    def apply(index: Int) : T = {
        while (index >= buffer.size) {
            buffer.+=(iterator.next)
        }
        buffer.apply(index)
    }
    def map[B](f: T => B) : LazyArray[B] = {
        buffer ++= iterator
        val newBuffer = buffer.map(f)
        val newArray = LazyArray(iterator.map(f))     // I don't know how to concatenate two iterators
                                                      // Otherwise I would convert the buffer to an iterator, map it and then concatenate the iterators
        newArray.buffer = newBuffer
        return newArray
    }
    def foreach(f: T => Unit) : Unit = {
        buffer.foreach(f)
        while (iterator.hasNext) {
            val x = iterator.next
            buffer += x
            f(x)
        }
    }
}

object LazyArray {
    def apply[T](iterator : Iterator[T]) : LazyArray[T] = {
        return new LazyArray[T](iterator)
    }
}
