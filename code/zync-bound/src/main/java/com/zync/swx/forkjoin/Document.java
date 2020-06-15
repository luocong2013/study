package com.zync.swx.forkjoin;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author LC
 * @version V1.0
 * @project luoc-hanlp
 * @package com.luo.forkjoin
 * @description TODO
 * @date 2017-10-20 16:38
 */
public class Document {
    private final List<String> lines;

    public Document(List<String> lines) {
        this.lines = lines;
    }

    public List<String> getLines() {
        return lines;
    }

    static Document formFile(File file) throws IOException {
        List<String> lines = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"))){
            String line = null;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return new Document(lines);
    }
}
