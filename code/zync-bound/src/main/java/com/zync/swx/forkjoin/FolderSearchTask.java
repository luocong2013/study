package com.zync.swx.forkjoin;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * @author LC
 * @version V1.0
 * @project luoc-hanlp
 * @package com.luo.forkjoin
 * @description TODO
 * @date 2017-10-20 17:05
 */
public class FolderSearchTask extends RecursiveTask<Long> {

    private final Folder folder;
    private final String searchWord;

    public FolderSearchTask(Folder folder, String searchWord) {
        this.folder = folder;
        this.searchWord = searchWord;
    }

    @Override
    protected Long compute() {
        long count = 0L;
        List<RecursiveTask<Long>> forks = new LinkedList<>();
        for (Folder subFolder : folder.getSubFolders()) {
            FolderSearchTask task = new FolderSearchTask(subFolder, searchWord);
            forks.add(task);
            task.fork();
        }
        for (Document document : folder.getDocuments()) {
            DocumentSearchTask task = new DocumentSearchTask(document, searchWord);
            forks.add(task);
            task.fork();
        }
        for (RecursiveTask<Long> task : forks) {
            count += task.join();
        }
        return count;
    }
}
