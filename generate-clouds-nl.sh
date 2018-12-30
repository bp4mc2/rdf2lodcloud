#!/bin/bash
cd ../lod-cloud-draw
cargo run --release -- ../rdf2lodcloud/output/lod-nl.json ../rdf2lodcloud/clouds/lod-nl.svg -n 10 -i 5000 -c 350 --ident=neighbour --settings=../rdf2lodcloud/clouds/lod-nl.json
