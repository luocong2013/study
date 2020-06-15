package com.zync.swx.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * @author LC
 * @version V1.0
 * @project luoc-hanlp
 * @package com.luo.forkjoin
 * @description TODO
 * @date 2017-10-20 16:58
 */
public class DocumentSearchTask extends RecursiveTask<Long> {

    private final Document document;
    private final String searchWord;

    public DocumentSearchTask(Document document, String searchWord) {
        this.document = document;
        this.searchWord = searchWord;
    }

    @Override
    protected Long compute() {
        return new WordCounter().occurrencesCount(document, searchWord);
    }
}
