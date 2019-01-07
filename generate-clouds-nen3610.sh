#!/bin/bash
cd ../lod-cloud-draw
cargo run --release -- ../rdf2lodcloud/output/lod-nen3610.json ../rdf2lodcloud/clouds/lod-nen3610.svg -c 350 -d 60 --ident=neighbour --settings=../rdf2lodcloud/clouds/lod-nen3610.json
cd ../rdf2lodcloud/clouds
convert -density 300 -alpha off lod-nen3610.svg lod-nen3610.png
