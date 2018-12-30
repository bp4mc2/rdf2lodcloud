package nl.architolk.rdf2lodcloud;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import org.apache.commons.io.FilenameUtils;
import org.apache.jena.graph.Factory;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RiotException;
import org.apache.jena.riot.RDFDataMgr;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Convert {

  private static final Logger LOG = LoggerFactory.getLogger(Convert.class);

  private static String BASE_URI = "http://architolk/nl/rdf2lodcloud/";

  private static void loadFile(Model model, String filename) throws FileNotFoundException {

    File file = new File(filename);
    LOG.info("Loading file: {}",file.toString());
    String ext = FilenameUtils.getExtension(file.toString());
    if (ext.equals("trig")) {
      // Trig files contain multiple names graph.
      // We combine those graphs, and add them to the model
      Dataset ds = DatasetFactory.create();
      RDFDataMgr.read(ds, new FileInputStream(file), BASE_URI, Lang.TRIG);
      model.add(ds.getUnionModel());
      ds.close();
    } else {
      model.read(new FileInputStream(file), BASE_URI, ext);
    }
  }

  private static void addResource(JSONObject object, String var, QuerySolution soln) {
    Resource value = soln.getResource(var);
    if (value!=null) {
      object.put(var,value.toString());
    }
  }

  private static void addResourceCode(JSONObject object, String var, String type, QuerySolution soln) {
    Resource value = soln.getResource(var);
    if (value!=null) {
      object.put(var,value.toString().replaceAll("^.*/"+type+"/([^/]+)$","$1"));
    }
  }

  private static void addLiteral(JSONObject object, String var, QuerySolution soln) {
    Literal value = soln.getLiteral(var);
    if (value!=null) {
      object.put(var,value.getString());
    }
  }

  private static void addLanguageLiteral(JSONObject object, String var, QuerySolution soln) {
    Literal value = soln.getLiteral(var);
    if (value!=null) {
      object.put(value.getLanguage(),value.getString());
    } else {
      LOG.warn("No value for {}",var);
    }
  }

  public static void main(String[] args) {

    try {

      LOG.info("Starting");

      Model dataModel = ModelFactory.createModelForGraph(Factory.createDefaultGraph());

      loadFile(dataModel,args[0]);

      JSONObject datasets = new JSONObject();

      try {
        Query query = QueryFactory.create(QUERY.alldatasets);
        QueryExecution qexec = QueryExecutionFactory.create(query,dataModel);
        ResultSet results = qexec.execSelect();
        String oldIdentifier = "";
        JSONObject dataset = null;
        JSONArray links = null;
        JSONArray keywords = null;
        HashMap<String,String> linksHash = new HashMap<String,String>();
        HashMap<String,String> keywordsHash = new HashMap<String,String>();
        for ( ; results.hasNext() ; ) {
          QuerySolution soln = results.nextSolution();
          String identifier = soln.get("identifier").toString();
          if (!identifier.equals(oldIdentifier)) {
            LOG.info("Dataset: {}", soln.get("ds").toString());
            dataset = new JSONObject();
            dataset.put("identifier",identifier);
            links = new JSONArray();
            linksHash.clear();
            dataset.put("links",links);
            addResource(dataset,"license",soln);
            JSONObject descriptions = new JSONObject();
            addLanguageLiteral(descriptions,"description",soln);
            dataset.put("description",descriptions);
            addResourceCode(dataset,"domain","domain",soln);
            addLiteral(dataset,"triples",soln);
            keywords = new JSONArray();
            keywordsHash.clear();
            dataset.put("keywords",keywords);
            addLiteral(dataset,"title",soln);
            addResource(dataset,"website",soln);
            datasets.put(identifier,dataset);
            oldIdentifier = identifier;
          }
          if (links!=null) {
            Resource target = soln.getResource("target");
            if (target!=null) {
              String targetkey = target.toString();
              if (!linksHash.containsKey(targetkey)) {
                JSONObject link = new JSONObject();
                addLiteral(link,"value",soln);
                addResourceCode(link,"target","dataset",soln);
                links.put(link);
                linksHash.put(targetkey,targetkey);
              }
            }
          }
          if (keywords!=null) {
            Literal keyword = soln.getLiteral("keywords");
            if (keyword!=null) {
              String keywordkey = keyword.toString();
              if (!keywordsHash.containsKey(keywordkey)) {
                keywords.put(keywordkey);
                keywordsHash.put(keywordkey,keywordkey);
              }
            }
          }
        }

      } catch (Exception e) {
        LOG.error(e.getMessage());
      }

      FileWriter writer = new FileWriter(args[1]);
      datasets.write(writer);
      writer.close();

    } catch (IOException e) {
      LOG.error(e.getMessage());
    } catch (RiotException e) {
      //Already send to output via SLF4J
    }
  }
}
