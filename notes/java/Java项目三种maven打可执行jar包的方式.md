# Java项目三种maven打可执行jar包的方式



```xml
<project>
		<build>
        <finalName>${project.artifactId}-${project.version}</finalName>

        <plugins>
            <!--maven 打可执行jar包 第一种方式 start (maven 命令：mvn package)-->
            <!--jar打包插件（主要是生成META-INF/MANIFEST.MF文件的部分内容和Main-Class）-->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <configuration>
                    <archive>
                        <manifest>
                            <!--是否把第三方jar放到manifest的classpath中-->
                            <addClasspath>true</addClasspath>
                            <!--生成的manifest中classpath的前缀，因为要把第三方jar放到lib目录下，所以classpath的前缀是lib/-->
                            <classpathPrefix>lib/</classpathPrefix>
                            <!--应用的main class-->
                            <mainClass>cn.com.Appincation</mainClass>
                        </manifest>
                    </archive>
                </configuration>
            </plugin>
            <!--dependency插件（用于将依赖拷贝到指定的目录，即lib目录）-->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-dependency-plugin</artifactId>
                <executions>
                    <execution>
                        <id>copy-dependencies</id>
                        <phase>package</phase>
                        <goals>
                            <goal>copy-dependencies</goal>
                        </goals>
                        <configuration>
                            <outputDirectory>${project.build.directory}/lib</outputDirectory>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
            <!--maven 打可执行jar包 第一种方式 end (maven 命令：mvn package)-->

            <!--maven 打可执行jar包 第二种方式 start（maven 命令：mvn package）-->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-assembly-plugin</artifactId>
                <configuration>
                    <archive>
                        <manifest>
                            <mainClass>cn.com.Appincation</mainClass>
                        </manifest>
                    </archive>
                    <descriptorRefs>
                        <descriptorRef>jar-with-dependencies</descriptorRef>
                    </descriptorRefs>
                </configuration>
                <executions>
                    <execution>
                        <id>make-assembly</id>
                        <phase>package</phase>
                        <goals>
                            <goal>single</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <!--maven 打可执行jar包 第二种方式 end（maven 命令：mvn package）-->

            <!--maven 打可执行jar包 第三种方式 start（maven 命令：mvn package）-->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <executions>
                    <execution>
                        <id>make-shade</id>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <transformers>
                                <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                    <mainClass>cn.com.Appincation</mainClass>
                                </transformer>
                            </transformers>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
            <!--maven 打可执行jar包 第三种方式 end（maven 命令：mvn package）-->
        </plugins>
    </build>
</project>
```

