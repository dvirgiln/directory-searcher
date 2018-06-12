package com.david.searcher

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{BeforeAndAfterAll, Matchers, Sequential, WordSpec}
import java.io.File
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar

class FileUtilsSpec extends WordSpec with Matchers with MockitoSugar {

  final val basePath = "/temp/david/"

  "FileUtils" should {
    "list all the files in a directory tree" in {

      val directory = mock[File]

      val directoryContent = DirectoryGenerator.generateDirectoryTree(basePath).toArray

      when(directory.listFiles()).thenReturn(directoryContent)

      val results = FileUtils.getAllFiles(directory)

      results.map(_.getAbsolutePath) shouldBe (List("/temp/david/0_f3", "/temp/david/0_f2", "/temp/david/0_f1",
        "/temp/david/1_f3", "/temp/david/1_f2", "/temp/david/1_f1",
        "/temp/david/3_f3", "/temp/david/3_f2", "/temp/david/2_f3",
        "/temp/david/2_f2", "/temp/david/2_f1"))
    }
  }


}
