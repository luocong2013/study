package com.zync.swx.forkjoin;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

/**
 * @author LC
 * @version V1.0
 * @project luoc-hanlp
 * @package com.luo.forkjoin
 * @description TODO
 * @date 2017-10-20 17:15
 */
public class Client {
    private final ForkJoinPool forkJoinPool = new ForkJoinPool();

    Long countOccurrencesInParallel(Document document, String searchedWord) {
        return forkJoinPool.invoke(new DocumentSearchTask(document, searchedWord));
    }

    Long countOccurrencesInFolder(Folder folder, String searchedWord) {
        return forkJoinPool.invoke(new FolderSearchTask(folder, searchedWord));
    }

    public static void main(String[] args) throws IOException {
        Document document = Document.formFile(new File("E:\\资料\\数据库\\MySQL\\ER-Studio的五种关系说明.txt"));

        Client client = new Client();
        long lon = client.countOccurrencesInParallel(document, "单词解释");
        System.out.println(lon);

        Folder folder = Folder.fromDirectory(new File("E:\\资料\\数据库\\Oracle\\Oracle资料"));
        long count = client.countOccurrencesInFolder(folder, "存储过程");
        System.out.println(count);
    }


}
