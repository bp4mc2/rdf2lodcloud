# rdf2lodcloud
RDF Dataset description to lodcloud JSON format

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
| - access_url   | dcat:downloadURL | [although dcat:accessURL seems more appropriate, this is not really correct. But how to distinguish?] |
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
