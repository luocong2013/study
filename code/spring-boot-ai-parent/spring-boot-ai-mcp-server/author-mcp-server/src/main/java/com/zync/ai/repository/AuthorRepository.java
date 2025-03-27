package com.zync.ai.repository;

import com.zync.ai.pojo.Author;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author admin
 * @version 1.0
 * @description
 * @since 2025/3/26 19:34
 **/
@Component
public class AuthorRepository {

    private static final Map<String, Author> MAP = new HashMap<>(16);
    static {
        MAP.put("西游记", new Author("吴承恩", "wuchengen@gmail.com"));
        MAP.put("红楼梦", new Author("曹雪芹", "caoxueqin@gmail.com"));
        MAP.put("三国演义", new Author("罗贯中", "luoguanzhong@gmail.com"));
        MAP.put("水浒传", new Author("施耐庵", "shinaian@gmail.com"));
    }


    @Tool(description = "使用文章标题获取文章对应作者详细信息")
    public Author getAuthorByArticleTitle(@ToolParam(description = "文章标题") String articleTitle) {
        return MAP.get(articleTitle);
    }

    @Tool(description = "add two numbers")
    public Integer add(@ToolParam(description = "first number") int a,
                       @ToolParam(description = "second number") int b) {
        return a + b;
    }

    @Tool(description = "get current time")
    public LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }

}
