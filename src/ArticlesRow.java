import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * 
 * @author Ben Horn
 * @since 1/2018
 * 
 * This class present one article.
 *
 */


public class ArticlesRow {
	public int num;
	public String headLine;
	public String URL;
	public String subHeadLine;
	public String date;
	public String reporter;
	public String site;
	public String body;
	public ArrayList<CommentRow> comments;
	public int numOfWords;

	public static int counter=1;

	

	public ArticlesRow(){
		this.num=counter;
		counter++;
		headLine="";
		subHeadLine="";
		date="";
		URL="";
		reporter="";
		site="";
		body="";
		comments=new ArrayList<CommentRow>() ;
		numOfWords=0;
	}
	
	/**
	 * @return all comment's title+body connected.
	 */
	public String getCommentsString(){
		return CommentRow.wireAllComments(this.comments);
	}


	/**
	 * write this. article
	 */
	public void WriteToFile() {
		XSSFSheet sheet = Main.workbook.getSheet("Articles");
		numOfWords=body.split(" ").length;
		String[] row = {num+"", site, URL, date, reporter, numOfWords+"", headLine, subHeadLine, body,"","", getCommentsString(),"","","",""};
		Funcs.StringArrToLastRow(row, sheet);
	}
	
	/**
	 * write list of articles to file  
	 * @param Reports -list with ArticlesRow objects
	 */
	public static void WriteToFile(List<ArticlesRow> Reports) {
		for(int i=0; i<Reports.size(); i++){
			Reports.get(i).WriteToFile();
			CommentRow.WriteToFile(Reports.get(i).comments);
		}

	}

	/**
	 * update Articlenum for each comment
	 * @param cmmts -List of comments, belong to this article
	 */
	public void setComments(ArrayList<CommentRow> cmmts) {
		if(cmmts==null) return;
		for(int i=0; i<cmmts.size(); i++){
			cmmts.get(i).ArticleNum = this.num;
		}
		this.comments = cmmts;
		
	}


	/**
	 * This functions use to write to csv file, instead of using apache POI for excel
	 * @param writer
	 */
	public void WriteToFile(FileWriter writer) {
		try {
			clean();

			writer.append(""+num+',');
			writer.append(site+',');
			writer.append(headLine+',');
			writer.append(date+',');
			writer.append(reporter+',');
			writer.append(body+',');

			Funcs.apeandComments(writer, getCommentsString());


			writer.append('\n');

			writer.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public void clean(){
		headLine = Funcs.clean(headLine);
		reporter = Funcs.clean(reporter);
		body = Funcs.clean(body);
		date = Funcs.clean(date);
	}

		
	

	




}
