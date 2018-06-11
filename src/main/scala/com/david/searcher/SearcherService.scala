package com.david.searcher

import java.io.File

class SearcherService(directory: File) {

  type WordIndex = Map[String, List[Int]]
  type FileIds = Map[Int, String]

  def load(): (WordIndex, FileIds) = {
    val allFiles = FileUtils.getAllFiles(directory)

    val (wordIndex, fileIds, nextFileId) = allFiles.foldLeft((Map[String, List[Int]](), Map[Int, String](), 0)) {
      case ((wordIndex, fileIds, nextFileId), file) =>
        val words = scala.io.Source.fromFile(file).getLines().flatMap(_.split(" ").map(_.toLowerCase())).toSet

        //Convert the current words into a WordIndex format to allow a groupBy
        val currentMap: WordIndex = words.map((_ -> List(nextFileId))).toMap

        val wordIndexes = currentMap.toSeq ++ wordIndex.toSeq
        val grouped = wordIndexes.groupBy(_._1)

        //Converting the grouped words into a WordIndex
        val aggregatedWords: WordIndex = grouped.map { case (word, values) => (word, values.map(_._2).flatten.toList) }

        val newFileIds = fileIds + (nextFileId -> file.getAbsolutePath.replaceFirst(directory.getAbsolutePath, ""))
        (aggregatedWords, newFileIds, nextFileId + 1)
    }
    (wordIndex, fileIds)
  }

  val (wordsInFiles, fileIdsMap) = load()

  def getRankingWords(words: Seq[String], maxLength: Int): Seq[(String, Int)] = {
    val numberWords = words.size
    val fileIds = words.flatMap(word => wordsInFiles.get(word)).flatten

    val initialRanking = fileIdsMap.keys.map((_ -> 0)).toMap
    val ranking = fileIds.foldLeft(initialRanking) { (agg, id) =>
      agg + (id -> (agg.get(id).get + 1))
    }

    val rankingCalculated = ranking.map(a => (fileIdsMap.get(a._1).get -> ((a._2.toDouble / numberWords.toDouble) * 100).toInt))
    val returned = rankingCalculated.iterator.toSeq.sortWith((first, second) => first._2 > second._2)
    returned
  }

}
