# Netspeak Java Client

Java wrapper for the Python client of the [Netspeak](https://netspeak.org) API.

## Getting started

### Installation

Include the following dependency to your project with a Java build tool or as a jar. 

#### Maven

Add this to your `pom.xml`:
```xml
<dependencies>
    <dependency>
        <groupId>org.netspeak</groupId>
        <artifactId>netspeak-client</artifactId>
        <version>1.0</version>
    </dependency>
</dependencies>

<repositories>
    <repository>
        <id>central</id>
        <name>repo.webis.de-releases</name>
        <url>https://repo.webis.de/artifactory/libs-release-webis-gradle</url>
    </repository>
</repositories>
```

#### Gradle
Add this to your ```build.gradle```:
```
allprojects {
    dependencies {
        implementation: 'org.netspeak:netspeak-client:1.0'
    }
}

repositories {
    maven {
        url = uri('https://repo.webis.de/artifactory/libs-release-webis-gradle')
    }
}
```

### Example

```java
public class NetspeakExample {
    public static void main(String[] args) throws IOException {
        NetspeakClient netspeakClient = null;
        try {
            netspeakClient = new NetspeakClient();
            SearchResults searchResults = netspeakClient.search("how to ? this");
            searchResults.getPhrases().forEach(System.out::println);
            searchResults = netspeakClient.search("see ... works");
            searchResults.getPhrases().forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException("Oh no. Something went wrong :(", e);
        } finally {
            if (netspeakClient != null) {
                netspeakClient.close();
            }
        }
    }
}
```
Output: 
```
17792498317 |                how to use this | 1173949
18070422879 |                 how to do this | 683555
17539336244 |               how to cite this | 238643
17484924196 |            how to replace this | 107430
17459212956 |               how to make this | 99505
17638239519 |                how to fix this | 93255
18090204579 |               how to read this | 79438
17258432776 |                how to get this | 69259
18138655059 |                how to buy this | 68219
18015051915 |              how to solve this | 57270
18005462970 |               see how it works | 153607
17958967532 |                see if it works | 109130
8809913773  |                      see works | 57119
18117207447 |             see how this works | 55568
13100502274 |                 see what works | 51526
13214698030 |                  see the works | 51438
17592059436 |              see if that works | 28690
17555456765 |            see your good works | 28615
17870947984 |             see how that works | 25243
17599975532 |       see how technorati works | 23345
```

## Build from source

### Requirements
* Python 3.10 or newer
* Virtualenv
* Java 15 
* Maven
* Make

### Commands

```bash
git clone git@github.com:netspeak/netspeak-client-java.git
make build
```

