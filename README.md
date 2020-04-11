# Java based Webcrawler
This is a java code sample for a Web crawler. It starts with a seeder url passed as a query string to REST API end point. It crawls the starting url and collects all pages referred, subsequently crawls those pages, which keeps continuing until no more pages are there to crawl. At the end of crawling, it produces an output file with list of urls collected from crawling. User can set optional parameters to control the number of pages crawled so that crawling does not go indefinitely.

## Getting started
git clone https://github.com/asethura/WebCrawler.git

## Build package
mvn package

## Run the jar file
java -jar target/WebCrawler*.jar

## Optional parameters that can be set to control the crawling
These parameters can be set in application.properties before packaging or can be passed as java command line options (ex. -Dcrawler.depth.max=2 )

Crawler depth limits the number of levels in the crawling tree
crawler.depth.max=2  

Maximum number of pages crawled
crawler.page.max=100  

Set below property to false if external sites should not be crawled. Crawling will limit to domain of the seeder url 
crawler.external=true  

Crawling output
crawler.output=crawlMap.dat  

