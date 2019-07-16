import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class CoreNLP{


  public static void main(String[] args){

    MaxentTagger maxentTagger = new MaxentTagger("edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger");

    String tag = maxentTagger.tagString("He half closed his eyes and searched the horizon.");
    String[] eachTag = tag.split("\\s+");
    //System.out.println("Word      " + "Standford tag");
    //System.out.println("----------------------------------");
    for(int i = 0; i< eachTag.length; i++) {
      System.out.print(eachTag[i].split("_")[0] +"_"+ eachTag[i].split("_")[1]+" ");
    }
  
  }


}
