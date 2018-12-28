# rdf2lodcloud
RDF Dataset description to lodcloud JSON format

## Vocabularies

The follow vocabularies are used for the mapping:

| Prefix | Vocabulary | URI |
|--------|------------|-----|
| dct    | Dublin Core terms | http://purl.org/dc/terms/ |
| dcat   | Data Catalog Vocabulary | http://www.w3.org/ns/dcat# |
| void   | Vocabulary of Interlinked Datasets | http://rdfs.org/ns/void# |
| sd     | SPARQL service description | http://www.w3.org/ns/sparql-service-description# |
| http   | HTTP in RDF | http://www.w3.org/2011/http# |
| foaf   | Friend of a friend | http://xmlns.com/foaf/0.1/ |
| vcard  | Ontology for vCard | http://www.w3.org/2006/vcard/ns# |

## Mapping

The following mapping is used:

| JSON | RDF property | Comment |
|------|--------------|---------|
| identifier     | dct:identifier | also used as NAME |
| title          | dct:title | |
| description    | dct:description | including a language tag
| license        | dct:license | |
| website        | dcat:landingPage | |
| links          | void:subset | |
| - target       | void:target | both target are stated in the Linkset, only the "to" target should be used |
| - value        | void:triples | |
| sparql         | void:sparqlEndpoint | details are normalised in sd:Service entries |
| - description  | dct:description | |
| - title        | dct:title | |
| - access_url   | sd:endpoint | sd:endpoint IRI must be the same as the void:sparqlEndpoint IRI |
| - status       | http:sc | "status" describes the http response code, so we can use http:sc, values are instances of http:StatusCode |
| owner          | dcat:publisher | [discussion: maybe we should use prov, because "owner" is probably about the description not the dataset!] |
| - name         | foaf:name | |
| - email        | foaf:mbox | using an IRI starting with `mailto:` |
| image          | foaf:depiction | [discussion: is this the best property?]
| triples        | void:triples | |
| full_download  | dcat:distribution | |
| - status       | http:sc | see above for usage instructions |
| - TITLE        | dct:title | |
| - description  | dct:description | |
| - media_type   | dcat:mediaType | |
| - download_url | dcat:downloadURL | |
| other_download | dcat:distribution | |
| - status       | http:sc | see above for usage instructions |
| - TITLE        | dct:title | |
| - description  | dct:description | |
| - media_type   | dcat:mediaType | |
| - access_url   | dcat:accessURL | |
| contact_point  | dcat:contactPoint | |
| - name         | vcard:fn | |
| - email        | vcard:hasEmail | |
| example        | void:exampleResource | |
| - status       | http:sc | see above for usage instructions |
| - title        | dct:title | |
| - description  | dct:description | |
| - access_url   | | "access_url" must be the same value as void:exampleResource |
| domain         | dcat:theme | [discussion: the actual ConceptScheme doesn't exists as dereferenceable URI's - so which should we use??] |
| keywords       | dcat:keyword | |
| namespace      | void:uriSpace | |
| doi            | | [unknown, always empty] |

As is common in the RDF open world assumption, empty values (`""`) are understand as "don't know the value", and are ommitted from the RDF data.

## example

Using a piece of the dbpedia entry in the lodcloud:

### JSON lodcloud format

```
"dbpedia": {
  "links": [
    {
      "value": "86547",
      "target": "geonames-semantic-web"
    },
    {
      "value": "467101",
      "target": "w3c-wordnet"
    },
    {
      "value": "18100000",
      "target": "yago"
    }
  ],
  "license": "http://www.opendefinition.org/licenses/cc-by-sa",
  "sparql": [
    {
      "status": "OK",
      "description": "SPARQL endpoint",
      "title": "",
      "access_url": "http://dbpedia.org/sparql"
    }
  ],
  "owner": "",
  "image": "",
  "description": {
    "en": "DBpedia.org is a community effort to extract structured information from Wikipedia and to make this information available on the Web. DBpedia allows you to ask sophisticated queries against Wikipedia and to link other datasets on the Web to Wikipedia data.\r\n\r\nThe English version of the DBpedia knowledge base currently describes 6.0M entities of which 4.6M have abstracts, 1.53M have geo coordinates and 1.6M depictions. In total, 5.2M resources are classified in a consistent ontology, consisting of 1.5M persons, 810K places (including 505K populated places), 490K works (including 135K music albums, 106K films and 20K video games), 275K organizations (including 67K companies and 53K educational institutions), 301K species and 5K diseases. The total number of resources in English DBpedia is 16.9M that, besides the 6.0M resources, includes 1.7M skos concepts (categories), 7.3M redirect pages, 260K disambiguation pages and 1.7M intermediate nodes.\r\n \r\nAltogether the DBpedia 2016-04 release consists of 9.5 billion (2015-10: 8.8 billion) pieces of information (RDF triples) out of which 1.3 billion (2015-10: 1.1 billion) were extracted from the English edition of Wikipedia, 5.0 billion (2015-04: 4.4 billion) were extracted from other language editions and 3.2 billion (2015-10: 3.2 billion) from DBpedia Commons and Wikidata. In general, we observed a growth in mapping-based statements of about 2%.\r\n \r\nThorough statistics can be found on the DBpedia website and general information on the DBpedia datasets here."
  },
  "triples": "9500000000",
  "website": "http://dbpedia.org/",
  "title": "DBpedia",
  "other_download": [
    {
      "status": "OK",
      "title": "",
      "media_type": "application/octet-stream",
      "access_url": "http://downloads.dbpedia.org/2016-04/dbpedia_2016-04.owl",
      "description": "RDF Schema"
    },
    {
      "status": "OK",
      "title": "",
      "media_type": "meta/void",
      "access_url": "http://dbpedia.org/void/Dataset",
      "description": "voiD description",
    },
    {
      "status": "FAIL (404)",
      "description": "Semantic Web Sitemap",
      "title": "",
      "media_type": "meta/sitemap",
      "access_url": "http://wiki.dbpedia.org/sitemap"
    }
  ],
  "contact_point": {
    "email": "dbpedia-discussion@lists.sourceforge.net",
    "name": "DBpedia Team - http://wiki.dbpedia.org/Imprint"
  },
  "example": [],
  "keywords": [
    "crossdomain",
    "lod",
    "provenance-metadata",
    "published-by-producer",
    "wikipedia"
  ],
  "namespace": "http://dbpedia.org/resource/",
  "identifier": "dbpedia",
  "full_download": [
    {
      "status": "OK",
      "title": "",
      "media_type": "text/html",
      "description": "Download",
      "download_url": "http://downloads.dbpedia.org/"
    }
  ],
  "doi": "",
  "domain": "cross_domain"
}
```

### RDF format

```
@prefix data <http://bp4mc2.org/example/dataset/>
@prefix service <http://bp4mc2.org/example/service/>
@prefix domain <http://bp4mc2.org/example/domain/>

dataset:dbpedia a void:Dataset;
  void:subset
    [
      void:triples 86547;
      void:target dataset:dbpedia, dataset:geonames-semantic-web
    ],
    [
      void:triples 467101;
      void:target dataset:dbpedia, dataset:w3c-wordnet
    ],
    [
      void:triples 18100000;
      void:target dataset:dbpedia, dataset:yago
    ];
  dct:license <http://www.opendefinition.org/licenses/cc-by-sa>;
  void:sparqlEndpoint <http://dbpedia.org/sparql>;
  dct:description "DBpedia.org is a community effort to extract structured information from Wikipedia and to make this information available on the Web. DBpedia allows you to ask sophisticated queries against Wikipedia and to link other datasets on the Web to Wikipedia data.\r\n\r\nThe English version of the DBpedia knowledge base currently describes 6.0M entities of which 4.6M have abstracts, 1.53M have geo coordinates and 1.6M depictions. In total, 5.2M resources are classified in a consistent ontology, consisting of 1.5M persons, 810K places (including 505K populated places), 490K works (including 135K music albums, 106K films and 20K video games), 275K organizations (including 67K companies and 53K educational institutions), 301K species and 5K diseases. The total number of resources in English DBpedia is 16.9M that, besides the 6.0M resources, includes 1.7M skos concepts (categories), 7.3M redirect pages, 260K disambiguation pages and 1.7M intermediate nodes.\r\n \r\nAltogether the DBpedia 2016-04 release consists of 9.5 billion (2015-10: 8.8 billion) pieces of information (RDF triples) out of which 1.3 billion (2015-10: 1.1 billion) were extracted from the English edition of Wikipedia, 5.0 billion (2015-04: 4.4 billion) were extracted from other language editions and 3.2 billion (2015-10: 3.2 billion) from DBpedia Commons and Wikidata. In general, we observed a growth in mapping-based statements of about 2%.\r\n \r\nThorough statistics can be found on the DBpedia website and general information on the DBpedia datasets here."@en;
  void:triples 9500000000;
  dcat:landingPage <http://dbpedia.org/>;
  dct:title "DBpedia";
  dcat:distribution
    [
      http:sc <http://www.w3.org/2011/http-statusCodes#OK>;
      dcat:mediaType "application/octet-stream";
      dcat:accessURL <http://downloads.dbpedia.org/2016-04/dbpedia_2016-04.owl>;
      dct:description "RDF Schema"
    ],
    [
      http:sc <http://www.w3.org/2011/http-statusCodes#OK>;
      dcat:mediaType "meta/void";
      dcat:accessURL <http://dbpedia.org/void/Dataset>;
      dct:description "voiD description"
    ],
    [
      http:sc <http://www.w3.org/2011/http-statusCodes#NotFound">;
      dct:description "Semantic Web Sitemap",
      dcat:mediaType "meta/sitemap",
      dcat:accessURL "http://wiki.dbpedia.org/sitemap"
    ];
  dcat:contactPoint [
    vcard:hasEmail <dbpedia-discussion@lists.sourceforge.net>;
    vcard:fn "DBpedia Team - http://wiki.dbpedia.org/Imprint"
  ];
  dcat:keyword
    "crossdomain",
    "lod",
    "provenance-metadata",
    "published-by-producer",
    "wikipedia";
  void:uriSpace "http://dbpedia.org/resource/";
  dct:identifier "dbpedia";
  dcat:distribution [
    http:sc <http://www.w3.org/2011/http-statusCodes#OK">;
    dcat:mediaType "text/html";
    dct:description "Download";
    dcat:downloadURL" <http://downloads.dbpedia.org/>
  ];
  dcat:theme domain:cross_domain
.
service:dbpedia a sd:Service;
  dct:description "SPARQL endpoint";
  sd:endpoint <http://dbpedia.org/sparql>;
  http:sc <http://www.w3.org/2011/http-statusCodes#OK>
.
```
