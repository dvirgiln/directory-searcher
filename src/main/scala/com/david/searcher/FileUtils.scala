package com.david.searcher

import java.io.File

object FileUtils {
  def getAllFiles(directory: File): List[File]={
    directory.listFiles().foldLeft(List.empty[File]){ (agg, file) =>
      if(file.isDirectory){
        agg ::: getAllFiles(file)
      }
      else{
        file :: agg
      }
    }
  }
}
