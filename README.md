# DriveThruRPG OSR module scraper
[DriveThruRPG](https://www.drivethrurpg.com) is mostly built around seeing what's currently "hot", which makes sense and is good news if you have just published something and want it to get seen! But sometimes I just want to see:

What are the absolute best old school modules (at least as far as sales data can tell you)?

I wrote this little script to find all the "Platinum", "Mithral", and "Adamantine" selling products that are either

* Official TSR modules for AD&D and earlier editions
* Compatible with "OSR"

And which are in the "Campaigns, Adventures & Modules" subcategory of "Supplements & Expansions. I run this script monthly and publish the results to https://weavingstories.co.uk/work/drivethru-bestsellers/

# Run it yourself
## Scrape website and output to `./output/`
`./gradlew :run`

## Clean existing output
`./gradlew :clean`

## Clearing cache
Just delete cached API data so that scraper fetches fresh 
products from DriveThru:

`./gradlew :clearCacheData`

Delete the entire cache (including images):

`rm ~/.drivethru`
