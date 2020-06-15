package com.zync.swx.forkjoin;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author LC
 * @version V1.0
 * @project luoc-hanlp
 * @package com.luo.forkjoin
 * @description TODO
 * @date 2017-10-20 16:42
 */
public class Folder {
    private final List<Folder> subFolders;
    private final List<Document> documents;

    public Folder(List<Folder> subFolders, List<Document> documents) {
        this.subFolders = subFolders;
        this.documents = documents;
    }

    public List<Folder> getSubFolders() {
        return subFolders;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    static Folder fromDirectory(File dir) throws IOException {
        List<Document> documents = new LinkedList<>();
        List<Folder> subFolders = new LinkedList<>();
        for (File entry : dir.listFiles()) {
            if (entry.isDirectory()) {
                subFolders.add(Folder.fromDirectory(entry));
            } else {
                documents.add(Document.formFile(entry));
            }
        }
        return new Folder(subFolders, documents);
    }
}
