package com.david.searcher

import java.io.File
import java.util.logging.Logger

import scala.concurrent.duration._
object QuickstartApp  extends App {
  val logger = Logger.getLogger(QuickstartApp.getClass.getName)

  if(args.length == 0){
    throw new IllegalArgumentException("No directory has been passed")
  }

  val dir=new File(args(0))
  if(dir.exists() && dir.isDirectory){
    val searchService = new SearcherService(dir)

    val scanner = new java.util.Scanner(System.in)

    while(true){
      print("search> ")
      val line = scanner.nextLine()
      if(line.size>0){
        val words = line.split(" ").map(_.toLowerCase())
        val ranking =searchService.getRankingWords(words, 10)
        ranking.foreach{ case(file, score) => println(s"$file -> ${score}%")}
      }
    }
  }
  else{
    throw new IllegalArgumentException(s"The directory ${args(0)} does not exist or it is not a directory")
  }

}

