# Clean existing output
`./gradlew :clean`

# Scrape website and output to `./output/`
`./gradlew :run`

# Clearing cache
Just delete cached API data so that scraper fetches fresh 
products from DriveThru:

`./gradlew :clearCacheData`

Delete the entire cache (including images):

`rm ~/.drivethru`