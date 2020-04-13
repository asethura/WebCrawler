# Java based Webcrawler
This is a java code sample for a Web crawler. It starts with a seeder url passed as a query string to REST API end point. It crawls the starting url and collects all pages referred, subsequently crawls those pages, which keeps continuing until no more pages are there to crawl. At the end of crawling, it returns urls collected from crawling as a JSON api response. User can set optional parameters to control the number of pages crawled so that crawling does not go indefinitely.

## Getting started
git clone https://github.com/asethura/WebCrawler.git

## Build package
cd WebCrawler
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

Additional Crawling output file  
crawler.output=crawlMap.dat  

Port at which API listens  
server.port=8089  

## To start the crawl, pass the seeder url as part of query strin like shown below  
http://localhost:8089/startCrawling?startingUrl=http://example.com  

## BDD and API Tests  
Once the api is started, you can run the BDD Tests and API Test by cloning below repo  
git clone https://github.com/asethura/BDD_API_Tests.git  
cd BDD_API_Tests  
mvn test  