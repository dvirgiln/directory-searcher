package com.david.searcher

import java.io.File
import org.scalatest.mockito.MockitoSugar

import org.mockito.Mockito.when

object DirectoryGenerator extends MockitoSugar {
   def getMockFile(basePath: String, relativePath: String, lines: Seq[String]): File = {
    val f = mock[File]
    when(f.getAbsolutePath).thenReturn(s"$basePath$relativePath")
    when(f.exists()).thenReturn(true)
    when(f.isDirectory).thenReturn(false)
    f
  }

  private def getMockDir(basePath: String, relativePath: String): File = {
    val f = mock[File]
    when(f.getAbsolutePath).thenReturn(s"$basePath$relativePath")
    when(f.exists()).thenReturn(true)
    when(f.isDirectory).thenReturn(true)
    f
  }

  def generateDirectoryTree(basePath: String): Seq[File] = {

    val dir1 = getMockDir(basePath, "one")
    val oneContent = Seq(getMockFile(basePath, "1_f1", Seq("Hola david", "vamos", "come on")),
      getMockFile(basePath, "1_f2", Seq("Hola Francis", "vamos", "come on")),
      getMockFile(basePath, "1_f3", Seq("Hola Michael", "vamos", "yes"))).toArray
    when(dir1.listFiles()).thenReturn(oneContent)

    val dir2 = getMockDir(basePath, "two")
    val secondContent = Seq(getMockFile(basePath, "2_f1", Seq("Hola david", "vamos", "come on")),
      getMockFile(basePath, "2_f2", Seq("Hi david", "vamos", "come on")),
      getMockFile(basePath, "2_f3", Seq("Great david", "Yes", "right now"))).toArray
    when(dir2.listFiles()).thenReturn(secondContent)
    val dir3 = getMockDir(basePath, "three")
    val thirthContent = Seq(dir2, getMockFile(basePath, "3_f2", Seq("Hola david", "vamos", "come on")),
      getMockFile(basePath, "3_f3", Seq("Bye", "go on", "come on"))).toArray
    when(dir3.listFiles()).thenReturn(thirthContent)
    Seq(getMockFile(basePath, "0_f1", Seq("Hola david", "vamos", "come on")),
      getMockFile(basePath, "0_f2", Seq("Hi there", "vamos", "come on")),
      getMockFile(basePath, "0_f3", Seq("let it go", "rafa", "come on")), dir1, dir3)
  }
}
