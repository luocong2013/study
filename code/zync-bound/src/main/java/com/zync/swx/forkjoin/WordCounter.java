package com.zync.swx.forkjoin;

/**
 * @author LC
 * @version V1.0
 * @project luoc-hanlp
 * @package com.luo.forkjoin
 * @description TODO
 * @date 2017-10-20 16:47
 */
public class WordCounter {
    String[] wordsIn(String line) {
        return line.trim().split("(，|。|:|：|\\.)+");
    }

    Long occurrencesCount(Document document, String searchedWord) {
        long count = 0;
        for (String line : document.getLines()) {
            for (String word : wordsIn(line)) {
                if (word.contains(searchedWord)) {
                    count++;
                }
            }
        }
        return count;
    }

    Long countOccurrencesOnSingleThread(Folder folder, String searchedWord) {
        long count = 0;
        for (Folder subFolder : folder.getSubFolders()) {
            count += countOccurrencesOnSingleThread(subFolder, searchedWord);
        }
        for (Document document : folder.getDocuments()) {
            count += occurrencesCount(document, searchedWord);
        }
        return count;
    }
}
