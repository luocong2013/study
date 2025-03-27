package com.zync.ai.sdk;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import io.modelcontextprotocol.spec.ClientMcpTransport;
import io.modelcontextprotocol.spec.McpSchema;

import java.util.Map;

/**
 * @author admin
 * @version 1.0
 * @description
 * @since 2025/3/27 16:41
 **/
public class ClientWebmvc {

    public static void main(String[] args) {

        ClientMcpTransport transport = new HttpClientSseClientTransport("http://localhost:9001");
        try (var client = McpClient.sync(transport).build()) {

            client.initialize();

            McpSchema.ListToolsResult toolsList = client.listTools();
            System.out.println("Available Tools = " + toolsList);

            McpSchema.CallToolResult authorResult = client.callTool(new McpSchema.CallToolRequest("getAuthorByArticleTitle",
                    Map.of("articleTitle", "三国演义")));
            System.out.println("getAuthorByArticleTitle = " + authorResult.content());

            McpSchema.CallToolResult sumResult = client.callTool(new McpSchema.CallToolRequest("add",
                    Map.of("a", 1, "b", 6)));
            System.out.println("add a+ b =  " + sumResult.content().get(0));


            McpSchema.CallToolResult currentTimResult = client.callTool(new McpSchema.CallToolRequest("getCurrentTime", Map.of()));
            System.out.println("current time Response = " + currentTimResult);
        }
    }
}
