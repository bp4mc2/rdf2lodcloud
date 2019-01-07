package nl.architolk.rdf2lodcloud;

public class QUERY {

  public static String alldatasets =
    "PREFIX void: <http://rdfs.org/ns/void#> " +
    "PREFIX dct: <http://purl.org/dc/terms/> " +
    "PREFIX dcat: <http://www.w3.org/ns/dcat#> " +
    "SELECT" +
    "  ?ds ?identifier" +
    "  ?target" +
    "  ?value" +
    "  ?license" +
    "  ?description" +
    "  ?domain" +
    "  ?triples" +
    "  ?keywords" +
    "  ?title" +
    "  ?website" +
    " WHERE {" +
    "  ?ds a void:Dataset." +
    "  ?ds dct:identifier ?identifier." +
    "  OPTIONAL {" +
    "    ?ds void:subset ?subset." +
    "    ?subset void:target ?target. FILTER(?target!=?ds) " +
    "    ?subset void:triples ?value" +
    "  }" +
    "  OPTIONAL {?ds dct:license ?license}" +
    "  ?ds dct:description ?description." +
    "  ?ds dcat:theme ?domain." +
    "  ?ds void:triples ?triples." +
    "  OPTIONAL {?ds dcat:keyword ?keywords}" +
    "  ?ds dct:title ?title." +
    "  OPTIONAL {?ds dcat:landingPage ?website}" +
    "}" +
    "ORDER BY ?identifier";

}
