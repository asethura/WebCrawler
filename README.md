# Java based Webcrawler
This is a java code sample for a Web crawler. It starts with a seeder url passed as a query string to REST API end point. It crawls the starting url and collects all pages referred, subsequently crawls those pages, which keeps continuing until no more pages are there to crawl. At the end of crawling, it produces an output file with list of urls collected from crawling. User can set optional parameters to control the number of pages crawled so that crawling does not go indefinitely.

## Getting started
1. git clone https://github.com/asethura/WebCrawler.git

## Build package
2. mvn package

## Run the jar file
3. java -jar target/WebCrawler*.jar

## Optional parameters that can be set to control the crawling
4. These parameters can be set in application.properties before packaging or can be passed as java command line options
crawler.depth.max=2  
crawler.page.max=100 
crawler.external=true 
crawler.output=crawlMap.dat  

