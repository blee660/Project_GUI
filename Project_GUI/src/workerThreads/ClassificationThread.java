package workerThreads;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import pdfClasses.PDF;

public class ClassificationThread extends TemplateThread{
/**
 * 
 * create new field "keyword" for PDFs. 
 * 
 * Store keywords into keyword field, also into an array of ALL keywords
 * 
 * display array of all keywords into classification window
 * 
 * 
 * */
	public void taskLogic(PDF pdf) {
//		String abstract_text = pdf.getMetadata().getAbstractx();
		String abstract_text = "One of the key problems with Software Architecture Documents (ADs) is the difficulty of finding needed information from them. Most of the existing studies focus on the production aspect of ADs or Architectural Knowledge (AK), to support information finding. This research focuses on the consumption aspect of ADs, in particular on the chunking of architectural information in ADs based on consumers’ usage of ADs, and understanding the foraging behaviour of the consumers. We proposed the notion of chunk to alleviate information searching problem. As a collection of related pieces of architectural information needed for a particular task, a chunk simplifies finding of information by consumers engage with similar tasks, by enabling related architectural information which otherwise may be dispersed in an AD to be retrieved collectively as a unit. We identified chunks by finding ‘commonality’ in the consumers’ usage of the information in ADs when engaged with certain information-seeking task. We collected two types of consumers’ usage data to identify chunks: annotation data (such as ratings, highlighted content, and so on) and interaction data (which captures consumers’ interactions with the pages of a document and elements on the pages). We developed KaitoroCap, an online prototype tool, for creating ADs, collecting annotation data, and interaction data (manifested as consumers’ exploration paths through documents).We found that chunks based on consumers’ usage data (in particular annotation data) of ADs exist. We found that consumers’ ratings of sections show potential in producing satisfactory chunks for the respective task, when the contributing consumers have strong software architecture background and explore the documents in local electronic or printed environment. The goodness of a chunk was determined using criteria which trade- off the recall and precision measures of the chunk. These measures were calculated by benchmarking against the oracle set for the task. The goodness of a chunk approximates to its support for information searching in the document for similar information-seeking task. We found some differences between industry practitioners and academic professionals in terms of chunks found and information needed. Our investigation into the foraging behaviour of the consumers of ADs showed that: main logical components and use cases are common forages; foraging sequences starting with the overview of a document, main logical components, quality requirements, use cases and external dependencies, support the understanding of the described software architecture; referencing of Table of Contents, exploration based on titles and subtitles, skipping sections, and forward-browsing long sections are popular foraging styles, but backtracking to earlier sections is unpopular; main features of ADs that support understanding of the described software architecture are diagrams, views and design decisions, and the main hindrance is too much text with a lack of diagrams. In conclusion, our research produced some early insights into usage-based chunking of architectural information and architectural information foraging. However, much still needs to be done to gain better insights into supporting information searching in ADs based on their actual consumption by consumers, with many exciting challenges-cum-opportunities waiting to be addressed";
		pdf.getMetadata().setAbstractx(abstract_text);
		
		
		
	}
}